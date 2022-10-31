package prr.core;

import java.io.Serializable;

public abstract class Communication implements Serializable {
    private static final long serialVersionUID = 202208091753L;
    private int _id = 0;
    private boolean _isPaid;
    protected long _cost = 0;
    protected boolean _isOnGoing;
    private String _type;
    private Terminal _from;
    private Terminal _to;

    private int _size;

    public Communication(Terminal _from, Terminal _to) {
        this._id += 1;
        this._from = _from;
        this._to = _to;
    }
/*
    public boolean checkIsPaid(TariffPlan plan) {

    }

    public String toString() {

    }
*/

    public int getID() {
        return _id;
    }

    public boolean isPaid() {
        return _isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        _isPaid = isPaid;
    }

    public double getCost() {
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


    public Terminal getFrom() {
        return _from;
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

