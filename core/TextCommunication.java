package prr.core;

public class TextCommunication extends Communication{
    private String _message;

    public TextCommunication(int _id, boolean _isPaid, double _cost, boolean _isOnGoing, String _message) {
        super(_id, _isPaid, _cost, _isOnGoing);
        this._message = _message;
    }
    protected double computeCost(TariffPlan plan){

    }

    protected int getSize(){

    }
}
