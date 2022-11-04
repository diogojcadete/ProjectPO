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

    /**
     * This method will say if the Communication is paid or not
     * @param isPaid
     */
    public void setIsPaid(boolean isPaid) {
        _isPaid = isPaid;
    }

    /**
     * This method will set the size of the communication
     * @param size
     */
    protected void setSize(int size){
        _size = size;
    }

    /**
     * This method will set the cost of a communication
     * @param _cost
     */
    public void setCost(long _cost) {
        this._cost = _cost;
    }

    /**
     * This method will set the onGoing Communication
     * @param _isOnGoing
     */
    public void setIsOnGoing(boolean _isOnGoing) {
        this._isOnGoing = _isOnGoing;
    }

    /**
     * This method will set the type of the communication
     * @param type
     */
    public void setType(String type){
        _type = type;
    }

    /**
     * This method will return the id of the communication
     * @return _id
     */
    public int getID() {
        return _id;
    }

    /**
     * This method will say if the communication is paid or not
     * @return _isPaid
     */
    public boolean getIsPaid() {
        return _isPaid;
    }

    /**
     * This method will return the cost of a communication
     * @return _cost
     */
    public long getCost() {
        return _cost;
    }

    /**
     * This method will return the onGoing Communication
     * @return _isOnGoing
     */
    public boolean getIsOnGoing() {
        return _isOnGoing;
    }

    /**
     * This method will return the type of the notification
     * @return _type
     */
    public String getType(){
        return _type;
    }

    /**
     * This method will return the size of the Communication
     * @return _size
     */
    protected int getSize(){
        return _size;
    }

    /**
     * This method will return the terminal that made the communication
     * @return _from
     */
    public Terminal getFrom() {
        return _from;
    }

    /**
     * This method will return the terminal that received the communication
     * @return _to
     */
    public Terminal getTo() {
        return _to;
    }

    /**
     * This method will end the on going communication
     * @param size
     */

    public void endOnGoing(int size){
        _isOnGoing = false;
        long val;
        Client owner = _from.getOwner();
        this._size = size;
        if(_from._onGoingCommunication instanceof VoiceCommunication){
            val = ((VoiceCommunication) _from._onGoingCommunication).computeCost(owner.getTariffPlan());
        }
        else{
            val = ((VideoCommunication) _from._onGoingCommunication).computeCost(owner.getTariffPlan());
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

    /**
     * This method will pay the communication
     */

    public void payComm(){
        _isPaid = true;
    }

    /**
     * This method will update the cost
     * @param val
     */

    public void updateCost(long val){
        _cost = val;
    }

    /**
     * This method will return a string with the formatted Communication
     * @return
     */
    public String formattedCommunication() {
        if (_isOnGoing) {
            return _type + "|" + this.getID() + "|" + this.getFrom().getID() + "|" + this.getTo().getID() + "|" + _size + "|" + _cost + "|ONGOING";
        }
        return _type + "|" + this.getID() + "|" + this.getFrom().getID() + "|" + this.getTo().getID() + "|" + _size + "|" + _cost + "|FINISHED";
    }
}

