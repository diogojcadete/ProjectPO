package prr.core;

public enum TerminalType {
    BASIC("BASIC"),
    FANCY("FANCY");

    public final String label;
    private TerminalType(String label){
        this.label = label;
    }

}

