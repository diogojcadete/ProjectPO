package prr.core.comparator;

import prr.core.Client;

import java.util.Comparator;

public class ClientComparator implements Comparator<Client> {


    @Override
    public int compare(Client o1, Client o2) {
        return o1.getKey().toLowerCase().compareTo(o2.getKey().toLowerCase());
    }
}
