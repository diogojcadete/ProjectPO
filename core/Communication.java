package prr.core;

abstract class Communication {
    private int _id;
    private boolean _isPaid;
    private double _cost;
    private boolean _isOnGoing;

    public Communication(int _id, boolean _isPaid, double _cost, boolean _isOnGoing) {
        this._id = _id;
        this._isPaid = _isPaid;
        this._cost = _cost;
        this._isOnGoing = _isOnGoing;
    }

    public String toString(){

    }

    protected double computeCost(TariffPlan plan){

    }

    protected int getSize(){

    }
}

