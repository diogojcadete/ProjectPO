package prr.core;

public enum TerminalMode {
    BUSY ("BUSY"),
    ON ("IDLE"),
    SILENCE ("SILENCE"),
    OFF ("OFF");

    String _label;

    private TerminalMode(String label){
        _label = label;
    }

}
