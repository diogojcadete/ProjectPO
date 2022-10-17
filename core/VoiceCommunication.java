package prr.core;

public class VoiceCommunication extends InteractiveCommunication{
    public VoiceCommunication(Terminal _from, Terminal _to) {
        super(_from, _to);
        _from.addMadeCommunications(this);
        _to.addReceivedCommunications(this);
    }


    protected double computeCost(TariffPlan plan) {
        if(get_from().checkFriends(get_to())) {
            if (plan.getName().equals("NORMAL")) return (20.00 * this.getDuration())/2;
            else return (10.00 * getDuration())/2;
        }
        if (plan.getName().equals("NORMAL")) return 20.00 * this.getDuration();
        else return 10.00 * getDuration();
    }
}
