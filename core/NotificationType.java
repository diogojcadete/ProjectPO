package prr.core;

public enum NotificationType {
    O2S ("O2S"),
    O2I ("O2I"),
    B2S ("B2S"),
    B2I ("B2I");

    String _label;
    private NotificationType(String label){
        _label = label;
    }
}
