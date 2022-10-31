package prr.core.comparator;

import prr.core.Client;
import prr.core.Terminal;

import java.util.Comparator;

public class TerminalComparator implements Comparator<Terminal> {
    public int compare(Terminal o1, Terminal o2) {
        return o1.getID().toLowerCase().compareTo(o2.getID().toLowerCase());
    }
}
