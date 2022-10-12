package prr.core;

public class FancyTerminal extends Terminal {
    public FancyTerminal(String _id, String _mode, double _debt, double _payments, Client _owner, TerminalMode _mode1) {
        super(_id, _mode, _debt, _payments, _owner, _mode1);
    }
    public void makeVideoCall(Terminal to){

    }

    protected void acceptVideoCall(Terminal from){

    }
}
