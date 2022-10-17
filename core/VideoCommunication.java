package prr.core;

public class VideoCommunication extends InteractiveCommunication{
    public VideoCommunication(Terminal _from, Terminal _to) {
        super(_from, _to);
        _from.addMadeCommunications(this);
        _to.addReceivedCommunications(this);
    }

    protected double computeCost(TariffPlan plan) {
        if (plan.getName().equals("NORMAL")) return 30.00;
        else if (plan.getName().equals("GOLD")) return 20.00;
        else return 10.00;
    }


}
