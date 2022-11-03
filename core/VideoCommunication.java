package prr.core;

import java.io.Serializable;

public class VideoCommunication extends InteractiveCommunication implements Serializable {
    private static final long serialVersionUID = 202208091753L;
    public VideoCommunication(Terminal _from, Terminal _to) {
        super(_from, _to);
        //if(_to.canStartCommunication()) {
            _from.addMadeCommunications(this);
            _to.addReceivedCommunications(this);
            this.setType("VIDEO");
            this.setIsOnGoing(true);
            _from.setBusy();
            _to.setBusy();
        //}
    }

    protected long computeCost(TariffPlan plan) {
        if(getFrom().checkFriends(getTo())) {
            if (plan.getName().equals("NORMAL")) return (30 * this.getSize())/2;
            else if (plan.getName().equals("GOLD")) return (20 * this.getSize())/2;
            else return (10 * this.getSize())/2;
        }
        if (plan.getName().equals("NORMAL")) return 30 * this.getSize();
        else if (plan.getName().equals("GOLD")) return 20 * this.getSize();
        else return 10 * this.getSize();
    }

}
