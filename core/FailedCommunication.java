package prr.core;

import java.util.List;

public class FailedCommunication {
    private Terminal _terminalAttempt;
    private Terminal _terminalFailed;

    public FailedCommunication(Terminal terminalAttempt, Terminal terminalFailed){
        _terminalAttempt = terminalAttempt;
        _terminalFailed = terminalFailed;
    }
    public Terminal getTerminalAttempt(){
        return _terminalAttempt;
    }
    public Terminal getTerminalFailed(){
        return _terminalFailed;
    }

}
