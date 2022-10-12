package prr.core;

abstract class InteractiveCommunication extends Communication{
    private int _duration;

    public InteractiveCommunication(int _id, boolean _isPaid, double _cost, boolean _isOnGoing, int _duration) {
        super(_id, _isPaid, _cost, _isOnGoing);
        this._duration = _duration;
    }
    protected int getSize(){

    }
}
