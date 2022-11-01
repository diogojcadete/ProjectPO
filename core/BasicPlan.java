package prr.core;

import java.io.Serializable;

public class BasicPlan extends TariffPlan implements Serializable {

    private static final long serialVersionUID = 202208091753L;
    public BasicPlan(String _name) {
        super(_name);
    }
    protected double computeCost(Client cl, TextCommunication com){
        int size = com.getSize();
        // Se for menor que 50 o tamanho
        if (size < 50) {
            if (cl.getLevel().name().equals("PLATINUM")) return 0.00;
            else return 10.00;
        }
        // Se for menor que 100
        else if (size < 100) {
            if (cl.getLevel().name().equals("NORMAL")) return 16.00;
            else if (cl.getLevel().name().equals("GOLD")) return 10.00;
            else return 4.00;
        }
        // Se for maior que 100
        if (cl.getLevel().name().equals("PLATINUM")) return 4.00;
        else return (double) size * 2;

    }
    protected double computeCost(Client cl, VoiceCommunication com){
        if(com.getFrom().checkFriends(com.getTo())) {
            if (cl.getLevel().name().equals("NORMAL")) return (20.00 * com.getSize())/2;
            else return (10.00 * com.getSize())/2;
        }
        if (cl.getLevel().name().equals("NORMAL")) return 20.00 * com.getSize();
        else return 10.00 * com.getSize();
    }
    protected double computeCost(Client cl, VideoCommunication com){
        if(com.getFrom().checkFriends(com.getTo())) {
            if (cl.getLevel().name().equals("NORMAL")) return (30.00 * com.getSize())/2;
            else if (cl.getLevel().name().equals("GOLD")) return (20.00 * com.getSize())/2;
            else return (10.00 * com.getSize())/2;
        }
        if (cl.getLevel().name().equals("NORMAL")) return 30.00 * com.getSize();
        else if (cl.getLevel().name().equals("GOLD")) return 20.00 * com.getSize();
        else return 10.00 * com.getSize();
    }
}
