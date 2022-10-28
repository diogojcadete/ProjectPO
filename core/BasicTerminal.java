package prr.core;

import java.io.Serializable;

public class BasicTerminal extends Terminal implements Serializable {

    //private static final long serialVersionUID = 202208091753L;
    public BasicTerminal(String _id, String _type, Client _owner, TerminalMode _mode) {
        super(_id, _type, _owner, _mode);
    }
    public void makeVideoCall(Terminal to){

    }

    protected void acceptVideoCall(Terminal from){

    }

}
