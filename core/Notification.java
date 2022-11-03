package prr.core;

import java.io.Serializable;

public class Notification implements Serializable {
    //private static final long serialVersionUID = 202208091753L;
    private NotificationType _type;
    private final String _origem;
    private Client _client;
    private Terminal _terminal;

    public Notification(String origem, Terminal t) {
        _origem = origem;
        _client = t.getOwner();
        _terminal = t;
    }

    public String getOrigem(){
        return _origem;
    }

    public NotificationType getType() {
        return _type;
    }

    public void setType(NotificationType _type) {
        this._type = _type;
    }


    public Client getClient(){
        return _client;
    }

    public void set02S(){
        _type = NotificationType.O2S;
    }
    public void set02I(){
        _type = NotificationType.O2I;
    }
    public void setB2I(){
        _type = NotificationType.B2I;
    }
    public void setS2I(){
        _type = NotificationType.S2I;
    }
    public String formattedNotification(){
        return  _type.name() + "|" + _terminal.getID();
    }
}
