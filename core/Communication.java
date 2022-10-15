package prr.core;

abstract class Communication {
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

    public boolean checkIsPaid(){

    }

    public String toString(){

    }

    protected double computeCost(TariffPlan plan){

    }

    protected int getSize(){

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public boolean is_isPaid() {
        return _isPaid;
    }

    public void set_isPaid(boolean _isPaid) {
        this._isPaid = _isPaid;
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

