package prr.core;

abstract class TariffPlan {

    private static final long serialVersionUID = 202208091753L;
    private String _name;

    public TariffPlan(String _name) {
        this._name = _name;
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
        if(com.get_from().checkFriends(com.get_to())) {
            if (cl.getLevel().name().equals("NORMAL")) return (20.00 * com.getDuration())/2;
            else return (10.00 * com.getDuration())/2;
        }
        if (cl.getLevel().name().equals("NORMAL")) return 20.00 * com.getDuration();
        else return 10.00 * com.getDuration();
    }
    protected double computeCost(Client cl, VideoCommunication com){
        if(com.get_from().checkFriends(com.get_to())) {
            if (cl.getLevel().name().equals("NORMAL")) return (30.00 * com.getDuration())/2;
            else if (cl.getLevel().name().equals("GOLD")) return (20.00 * com.getDuration())/2;
            else return (10.00 * com.getDuration())/2;
        }
        if (cl.getLevel().name().equals("NORMAL")) return 30.00 * com.getDuration();
        else if (cl.getLevel().name().equals("GOLD")) return 20.00 * com.getDuration();
        else return 10.00 * com.getDuration();
    }

    public String getName() {
        return _name;
    }


}

