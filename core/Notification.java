package prr.core;

import java.io.Serializable;

public class Notification implements Serializable {
    //private static final long serialVersionUID = 202208091753L;
    private NotificationType _type;
    private Terminal _notifyingTerminal;

    public Notification(NotificationType _type, Terminal _notifyingTerminal) {
        this._type = _type;
        this._notifyingTerminal = _notifyingTerminal;
    }

    /**
     * This method will return a string with the formatted notification
     * @return
     */
    public String formattedNotification(){
        return  _type.name() + "|" + _notifyingTerminal.getID();
    }
}
