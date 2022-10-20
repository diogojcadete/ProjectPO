package prr.core;

abstract class InteractiveCommunication extends Communication{

    private static final long serialVersionUID = 202208091753L;
    private int _duration;

    public InteractiveCommunication(Terminal _from, Terminal _to){
        super(_from, _to);
    }

    protected int getDuration(){
        return _duration;
    }

}



