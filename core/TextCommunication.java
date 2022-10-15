package prr.core;

public class TextCommunication extends Communication{
    private String _message;

    public TextCommunication(Terminal _from, Terminal _to, String _message) {
        super(_from, _to);
        this._message = _message;
        _from.addMadeCommunications(this);
        _to.addReceivedCommunications(this);

    }

    protected double computeCost(TariffPlan plan){

    }

    protected int getSize(){

    }

    public String getMessage(){
        return _message;
    }
}
