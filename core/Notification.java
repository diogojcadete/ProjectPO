package prr.core;

import java.io.Serializable;

public class Notification implements Serializable {
    private NotificationType _type;
    private Terminal _notifyingTerminal;

    public Notification(NotificationType _type, Terminal _notifyingTerminal) {
        this._type = _type;
        this._notifyingTerminal = _notifyingTerminal;
    }

    public NotificationType get_type() {
        return _type;
    }

    public void set_type(NotificationType _type) {
        this._type = _type;
    }

    public Terminal get_notifyingTerminal() {
        return _notifyingTerminal;
    }

    public void set_notifyingTerminal(Terminal _notifyingTerminal) {
        this._notifyingTerminal = _notifyingTerminal;
    }

    public String formattedNotification(){
        return ""+ _type.name() + "|" + _notifyingTerminal;
    }
}
