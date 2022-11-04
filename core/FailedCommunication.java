package prr.core;
public class FailedCommunication {
    private final Terminal _terminalAttempt;

    public FailedCommunication(Terminal terminalAttempt){
        _terminalAttempt = terminalAttempt;
    }
    public Terminal getTerminalAttempt(){
        return _terminalAttempt;
    }
}
