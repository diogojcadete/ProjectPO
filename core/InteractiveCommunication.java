package prr.core;

import java.io.Serializable;

abstract class InteractiveCommunication extends Communication implements Serializable {
    private static final long serialVersionUID = 202208091753L;

    public InteractiveCommunication(Terminal _from, Terminal _to){
        super(_from, _to);
    }
}



