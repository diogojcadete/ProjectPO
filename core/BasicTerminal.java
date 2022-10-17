package prr.core;

public class BasicTerminal extends Terminal{
    public BasicTerminal(String _id, String _type, Client _owner, TerminalMode _mode) {
        super(_id, _type, _owner, _mode);
    }
    public void makeVideoCall(Terminal to){

    }

    protected void acceptVideoCall(Terminal from){

    }

}
