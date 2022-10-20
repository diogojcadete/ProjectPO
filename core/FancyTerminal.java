package prr.core;

public class FancyTerminal extends Terminal {

    private static final long serialVersionUID = 202208091753L;
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
