package prr.core;

public class VoiceCommunication extends InteractiveCommunication{
    public VoiceCommunication(Terminal _from, Terminal _to) {
        super(_from, _to);
        _from.addMadeCommunications(this);
        _to.addReceivedCommunications(this);
    }

    protected double computeCost(TariffPlan plan){

    }
}
