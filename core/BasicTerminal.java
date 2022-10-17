package prr.core;

public class BasicTerminal extends Terminal{
    public BasicTerminal(String _id, double _debt, double _payments, Client _owner, TerminalMode _mode) {
        super(_id, _debt, _payments, _owner, _mode);
    }
    public void makeVideoCall(Terminal to){

    }

    protected void acceptVideoCall(Terminal from){

    }

}
