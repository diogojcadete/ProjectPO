package prr.core;

import java.io.Serializable;

public abstract class Communication implements Serializable {
    private static final long serialVersionUID = 202208091753L;
    private static int _sharedID = 0;
    private boolean _isPaid;
    protected long _cost = 0;
    protected boolean _isOnGoing;
    private String _type;
    private Terminal _from;
    private Terminal _to;
    private int _size;
    private int _id;

    public Communication(Terminal _from, Terminal _to) {
        _sharedID++;
        this._id = _sharedID;
        this._from = _from;
        this._to = _to;
    }


    public int getID() {
        return _id;
    }

    public boolean getIsPaid() {
        return _isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        _isPaid = isPaid;
    }

    public long getCost() {
        return _cost;
    }

    public void setCost(long _cost) {
        this._cost = _cost;
    }

    public boolean getIsOnGoing() {
        return _isOnGoing;
    }


    public void setIsOnGoing(boolean _isOnGoing) {
        this._isOnGoing = _isOnGoing;
    }

    public void endOnGoing(int size){
        _isOnGoing = false;
        long val;
        Client owner = _from.getOwner();
        this._size = size;
        if(_from._onGoingCommunication instanceof VoiceCommunication){
            val = ((VoiceCommunication) _from._onGoingCommunication).computeCost(owner.get_tariffPlan());
        }
        else{
            val = ((VideoCommunication) _from._onGoingCommunication).computeCost(owner.get_tariffPlan());
        }

        owner.updateDebts(val);
        _from.updateDebtValue(val);
        _cost = val;

        _from._onGoingCommunication = null;
        _to._onGoingCommunication = null;
        _from._onGoingCommunicationFrom = null;
        _from.setOn();
        _to.setOn();

    }

    public void setType(String type){
        _type = type;
    }
    public String getType(){
        return _type;
    }
    protected int getSize(){
        return _size;
    }
    protected void setSize(int size){
        _size = size;
    }

    public void payComm(){
        _isPaid = true;
    }
    public Terminal getFrom() {
        return _from;
    }

    public void updateCost(long val){
        _cost = val;
    }


    public Terminal getTo() {
        return _to;
    }
    public String formattedCommunication() {
        if (_isOnGoing) {
            return _type + "|" + this.getID() + "|" + this.getFrom().getID() + "|" + this.getTo().getID() + "|" + _size + "|" + _cost + "|ONGOING";
        }
        return _type + "|" + this.getID() + "|" + this.getFrom().getID() + "|" + this.getTo().getID() + "|" + _size + "|" + _cost + "|FINISHED";
    }
}

