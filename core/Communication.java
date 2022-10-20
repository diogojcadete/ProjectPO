package prr.core;

import java.io.Serializable;

abstract class Communication implements Serializable {
    private static final long serialVersionUID = 202208091753L;
    private static int _id = 0;
    private boolean _isPaid;
    protected double _cost = 0;
    protected boolean _isOnGoing;
    private Terminal _from;
    private Terminal _to;

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

    public int get_id() {
        return _id;
    }

    public boolean isPaid() {
        return _isPaid;
    }

    public void set_isPaid(boolean isPaid) {
        _isPaid = isPaid;
    }

    public double get_cost() {
        return _cost;
    }

    public void set_cost(double _cost) {
        this._cost = _cost;
    }

    public boolean get_isOnGoing() {
        return _isOnGoing;
    }

    public void set_isOnGoing(boolean _isOnGoing) {
        this._isOnGoing = _isOnGoing;
    }

    public Terminal get_from() {
        return _from;
    }

    public Terminal get_to() {
        return _to;
    }
}

