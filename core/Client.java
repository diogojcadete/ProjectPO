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

    public String getKey() {
        return _key;
    }

    public void addTerminal(Terminal t){
        _terminals.add(t);
    }

    public String get_name() {
        return _name;
    }

    public int get_taxNumber() {
        return _taxNumber;
    }
    public long getSaldo(){
        return (_payments - _debts);
    }
    public void set_taxNumber(int _taxNumber) {
        this._taxNumber = _taxNumber;
    }

    public ClientLevel getLevel(){
        return _level;
    }

    public void set_level(ClientLevel level) {
        _level = level;
    }

    public boolean getReceiveNotifications() {
        return _receiveNotifications;
    }

    public void setReceiveNotificationsON() {
        this._receiveNotifications = true;
    }
    public void setReceiveNotificationsOFF() {
        this._receiveNotifications = false;
    }

    public List<Communication> getMadeCommunication(){
        return _madeCommunications;
    }
    public List<Communication> getReceivedCommunications(){
        return _receivedCommunications;
    }
    public List<Terminal> getTerminals() {
        return _terminals;
    }

    public void set_terminals(List<Terminal> _terminals) {
        this._terminals = _terminals;
    }

    public TariffPlan get_tariffPlan() {
        return _tariffPlan;
    }

    public void set_tariffPlan(TariffPlan _tariffPlan) {
        this._tariffPlan = _tariffPlan;
    }

    public void updatePayments(double n){
        _payments += n;
    }

    public void updateDebts(double n){
        _debts += n;
    }

    public long getPayments(){
        return _payments;
    }

    public long getDebts(){
        return _debts;
    }

    public void addNotification(Notification n){
        _notifications.add(n);
    }

    public void eraseAllNotifications(){
        _notifications.clear();
    }
    /**
     *
     * @return
     */
    public String notificationsToString(){
        if(this._receiveNotifications)
            return "YES";
        return "NO";
    }
    public String formattedClient(){
        if(_terminals != null) {
            return "CLIENT" + "|" + _key + "|" + _name + "|" +
                    _taxNumber + "|" + _level.name() + "|" + this.notificationsToString() + "|"
                    + _terminals.size() + "|" + _payments + "|" + _debts ;
        }
        return "CLIENT" + "|" + _key + "|" + _name + "|" +
                _taxNumber + "|" + _level.name() + "|" + this.notificationsToString() + "|0|0|0";
    }

    public String showNotification(){
        StringBuilder strCommunications = new StringBuilder();
        for (int i = 0; i < _notifications.size() - 1; i++) {
            strCommunications.append(_notifications.get(i).formattedNotification()).append("\n");
        }
        if (_notifications.size() > 0) {
            strCommunications.append(_notifications.get(_notifications.size() - 1).formattedNotification());
        }
        return strCommunications.toString();
    }

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



    public List<Notification> getNotifications(){
        return _notifications;
    }

    public void addMadeCommunication(Communication communication) {
        _madeCommunications.add(communication);
    }

    public void addReceivedCommunication(Communication communication) {
        _receivedCommunications.add(communication);
    }
}
