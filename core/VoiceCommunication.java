package prr.core;

import java.io.Serializable;

public class VoiceCommunication extends InteractiveCommunication implements Serializable {
    private static final long serialVersionUID = 202208091753L;
    public VoiceCommunication(Terminal _from, Terminal _to) {
        super(_from, _to);
        //if(_to.canStartCommunication()){
            _from.addMadeCommunications(this);
            _to.addReceivedCommunications(this);
            this.setType("VOICE");
            this.setIsOnGoing(true);
            _from.setBusy();
            _to.setBusy();
       // }
    }


    protected long computeCost(TariffPlan plan) {
        if(getFrom().checkFriends(getTo())) {
            if (plan.getName().equals("NORMAL"))
                return (20 * this.getSize())/2;
            return (10 * getSize())/2;
        }
        if (plan.getName().equals("NORMAL"))
            return 20 * this.getSize();
        return 10 * this.getSize();
    }

}
