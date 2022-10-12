package prr.core;

public class BasicPlan extends TariffPlan{
    public BasicPlan(String _name) {
        super(_name);
    }
    protected double computeCost(Client cl, TextCommunication com){

    }
    protected double computeCost(Client cl, VoiceCommunication com){

    }
    protected double computeCost(Client cl, VideoCommunication com){

    }
}
