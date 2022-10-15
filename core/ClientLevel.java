package prr.core;

public enum ClientLevel {
    NORMAL("NORMAL"),
    GOLD("GOLD"),
    PLATINUM("PLATINUM");

    String _label;

    private ClientLevel(String label){
        _label = label;
    }
}
