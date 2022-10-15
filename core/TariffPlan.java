package prr.core;

abstract class TariffPlan {
    private String _name;

    public TariffPlan(String _name) {
        this._name = _name;
    }
    protected double computeCost(Client cl, TextCommunication com){

    }
    protected double computeCost(Client cl, VoiceCommunication com){

    }
    protected double computeCost(Client cl, VideoCommunication com){

    }

    public String getName() {
        return _name;
    }


}

