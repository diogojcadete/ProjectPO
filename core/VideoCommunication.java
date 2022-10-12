package prr.core;

public class VideoCommunication extends InteractiveCommunication{
    public VideoCommunication(int _id, boolean _isPaid, double _cost, boolean _isOnGoing, int _duration) {
        super(_id, _isPaid, _cost, _isOnGoing, _duration);
    }
    protected double computeCost(TariffPlan plan){

    }
}
