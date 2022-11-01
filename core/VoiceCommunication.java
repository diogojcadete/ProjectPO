package prr.core;

import java.io.Serializable;

public class VoiceCommunication extends InteractiveCommunication implements Serializable {
    private static final long serialVersionUID = 202208091753L;
    public VoiceCommunication(Terminal _from, Terminal _to) {
        super(_from, _to);
        _from.addMadeCommunications(this);
        _to.addReceivedCommunications(this);
        this.setType("VOICE");
        this.setIsOnGoing(true);
        _from.setBusy();
        _to.setBusy();
    }


    protected long computeCost(TariffPlan plan) {
        if(getFrom().checkFriends(getTo())) {
            if (plan.getName().equals("NORMAL"))
                return (20 * this.getDuration())/2;
            return (10 * getDuration())/2;
        }
        if (plan.getName().equals("NORMAL"))
            return 20 * this.getDuration();
        return 10 * this.getDuration();
    }

    private void updateDebt(TariffPlan plan){
        double n = this.computeCost(plan);
        this.getFrom().updateDebtValue(n);
        this.getFrom().getOwner().updateDebts(n);
    }
}
