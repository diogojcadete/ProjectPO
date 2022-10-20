package prr.core;

import java.io.Serializable;

public class FancyTerminal extends Terminal implements Serializable {
    public FancyTerminal(String _id, String _type, Client _owner, TerminalMode _mode) {
        super(_id, _type, _owner, _mode);
    }
    public void makeVideoCall(Terminal to){
        Communication c1 = new VideoCommunication(this, to);
        this.addMadeCommunications(c1);
    }

    protected void acceptVideoCall(Terminal from){

    }
}
