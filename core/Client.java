package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable {
    private static final long serialVersionUID = 202208091753L;
    private String _key;
    private String _name;
    private int _taxNumber;
    private ClientLevel _level;
    private boolean _receiveNotifications;
    private List<Terminal> _terminals;
    private TariffPlan _tariffPlan;
    private List<Notification> _notifications;
    private List<Communication> _communications;
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
        this._communications = new ArrayList<>();
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

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_taxNumber() {
        return _taxNumber;
    }

    public void set_taxNumber(int _taxNumber) {
        this._taxNumber = _taxNumber;
    }

    public ClientLevel getLevel(){
        return _level;
    }

    public void set_level(ClientLevel _level) {
        this._level = _level;
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

    public List<Terminal> getTerminals() {
        return _terminals;
    }

    public List<Communication> getCommunication(){
        return _communications;
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

    public List<Notification> getNotifications(){
        return _notifications;
    }

    public void addCommunication(Communication c){
        _communications.add(c);
    }

    public void updatePayments(long val){
        _payments += val;
    }
}
