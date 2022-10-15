package prr.core;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String _key;
    private String _name;
    private int _taxNumber;
    private ClientLevel _level;
    private boolean _receiveNotifications;
    private List<Terminal> _terminals;
    private TariffPlan _tariffPlan;
    private List<Notification> _notifications;


    public Client(String _key, String _name, int _taxNumber) {
        this._key = _key;
        this._name = _name;
        this._taxNumber = _taxNumber;
        this._level = ClientLevel.NORMAL;
        this._terminals = new ArrayList<>();
        this._notifications = new ArrayList<>();
        this._receiveNotifications = true;
    }

    public String get_key() {
        return _key;
    }

    public void set_key(String _key) {
        this._key = _key;
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

    public ClientLevel get_level() {
        return _level;
    }

    public void set_level(ClientLevel _level) {
        this._level = _level;
    }

    public boolean is_receiveNotifications() {
        return _receiveNotifications;
    }

    public void set_receiveNotifications(boolean _receiveNotifications) {
        this._receiveNotifications = _receiveNotifications;
    }

    public List<Terminal> get_terminals() {
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
}
