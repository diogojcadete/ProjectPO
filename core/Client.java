package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable {
    private static final long serialVersionUID = 202208091753L;
    private final String _key;
    private String _name;
    private int _taxNumber;
    private ClientLevel _level;
    private boolean _receiveNotifications;
    private List<Terminal> _terminals;
    private TariffPlan _tariffPlan;
    private List<Notification> _notifications;
    private List<Communication> _madeCommunications;
    private List<Communication> _receivedCommunications;

    private long _payments;
    private long _debts;


    public Client(String _key, String _name, int _taxNumber) {
        this._key = _key;
        this._name = _name;
        this._taxNumber = _taxNumber;
        this._level = ClientLevel.NORMAL;
        this._terminals = new ArrayList<>();
        this._notifications = new ArrayList<>();
        this._receiveNotifications = true;
        this._madeCommunications = new ArrayList<>();
        this._receivedCommunications = new ArrayList<>();
        this._tariffPlan = new BasicPlan("BasicPlan");
    }

    /**
     * This method will add a Terminal
     * @param t
     */
    public void addTerminal(Terminal t){
        _terminals.add(t);
    }

    /**
     * This method will add a notification
     * @param n
     */
    public void addNotification(Notification n){
        _notifications.add(n);
    }

    /**
     * This method will add a made communication
     * @param communication
     */
    public void addMadeCommunication(Communication communication) {
        _madeCommunications.add(communication);
    }

    /**
     * This method will add a received Communication
     * @param communication
     */
    public void addReceivedCommunication(Communication communication) {
        _receivedCommunications.add(communication);
    }

    /**
     * This method will turn on the reception of notifications
     */
    public void setReceiveNotificationsON() {
        this._receiveNotifications = true;
    }

    /**
     * This method will turn off the reception of notifications
     */
    public void setReceiveNotificationsOFF() {
        this._receiveNotifications = false;
    }

    /**
     * This method will get the key
     * @return _key
     */
    public String getKey() {
        return _key;
    }

    /**
     * This method will return the client level
     * @return _level
     */
    public ClientLevel getLevel(){
        return _level;
    }

    /**
     * This method will return if the reception of notifications is on or off
     * @return
     */
    public boolean getReceiveNotifications() {
        return _receiveNotifications;
    }

    /**
     * This method will return the communications made by a client
     * @return _madeCommunications
     */
    public List<Communication> getMadeCommunication(){
        return _madeCommunications;
    }

    /**
     * This method will return the communications received by a client
     * @return _receivedCommunications
     */
    public List<Communication> getReceivedCommunications(){
        return _receivedCommunications;
    }

    /**
     * This method will return the debts of a client
     * @return _debts
     */
    public long getDebts(){
        return _debts;
    }

    /**
     * This method will return the payments of a client
     * @return _payments
     */
    public long getPayments(){
        return _payments;
    }

    /**
     * This method will return the tariff Plan of a client
     * @return _tariffPlan
     */
    public TariffPlan getTariffPlan() {
        return _tariffPlan;
    }

    /**
     * This method will return the list that contains all notifications
     * @return _notifications
     */
    public List<Notification> getNotifications(){
        return _notifications;
    }

    /**
     * This method will update the payments of a client
     * @param val
     */

    public void updatePayments(long val){
        _payments += val;
    }

    /**
     * This method will update the debts of a client
     * @param n
     */
    public void updateDebts(double n){
        _debts += n;
    }

    /**
     * This method will clean the notification of a client
     */
    public void cleanAllNotifications(){
        _notifications.clear();
    }

    /**
     * This method will return if the reception of notifications is on or off
     * @return "YES" or "NO"
     */
    public String notificationsToString(){
        if(this._receiveNotifications)
            return "YES";
        return "NO";
    }

    /**
     * This method will upgrade the level of the client
     */
    public void upgradeClient(){
        long val = _payments - _debts;
        if (_level.name().equals("NORMAL")){
            if (val > 500)
                _level = ClientLevel.GOLD;
        }
        else if (_level.name().equals("GOLD")) {
            if (val < 0)
                _level = ClientLevel.NORMAL;
        }
        else{
            if (val < 0)
                _level = ClientLevel.NORMAL;
        }
    }

    /**
     * This method will return a string that contains the information of a client
     * @return str
     */
    public String formattedClient(){
        if(_terminals != null) {
            return "CLIENT" + "|" + _key + "|" + _name + "|" +
                    _taxNumber + "|" + _level.name() + "|" + this.notificationsToString() + "|"
                    + _terminals.size() + "|" + _payments + "|" + _debts ;
        }
        return "CLIENT" + "|" + _key + "|" + _name + "|" +
                _taxNumber + "|" + _level.name() + "|" + this.notificationsToString() + "|0|0|0";
    }

}
