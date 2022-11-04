package prr.core;

import java.io.Serializable;

public class Notification implements Serializable {
    //private static final long serialVersionUID = 202208091753L;
    private NotificationType _type;
    private Terminal _notifyingTerminal;

    private Boolean _sent;

    public Notification(NotificationType _type, Terminal _notifyingTerminal) {
        this._type = _type;
        this._notifyingTerminal = _notifyingTerminal;
        _sent = false;
    }

    public Boolean getSent(){
        return _sent;
    }

    /**
     * This method will return a string with the formatted notification
     * @return
     */
    public String formattedNotificationM(){
        _sent = true;
        return  _type.name() + "|" + _notifyingTerminal.getID() + "\n";
    }
    public String formattedNotification(){
        _sent = true;
        return  _type.name() + "|" + _notifyingTerminal.getID();
    }
}
