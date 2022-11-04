package prr.core;

import java.io.Serializable;

public class FancyTerminal extends Terminal implements Serializable {
    //private static final long serialVersionUID = 202208091753L;
    public FancyTerminal(String _id, String _type, Client _owner, TerminalMode _mode) {
        super(_id, _type, _owner, _mode);
    }
}
