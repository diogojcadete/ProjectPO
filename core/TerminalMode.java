package prr.core;

public enum TerminalMode {
    BUSY ("BUSY"),
    ON ("ON"),
    SILENCE ("SILENCE"),
    OFF ("OFF");

    String _label;

    private TerminalMode(String label){
        _label = label;
    }

}
