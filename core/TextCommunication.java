package prr.core;

public class TextCommunication extends Communication{

    private static final long serialVersionUID = 202208091753L;
    private final String _message;

    public TextCommunication(Terminal _from, Terminal _to, String _message) {
        super(_from, _to);
        this._message = _message;
        _from.addMadeCommunications(this);
        _to.addReceivedCommunications(this);

    }

    protected double computeCost(TariffPlan plan) {
        int size = getSize();
        // Se for menor que 50 o tamanho
        if (size < 50) {
            if (plan.getName().equals("PLATINUM")) return 0.00;
            else return 10.00;
        }
        // Se for menor que 100
        else if (size < 100) {
            if (plan.getName().equals("NORMAL")) return 16.00;
            else if (plan.getName().equals("GOLD")) return 10.00;
            else return 4.00;
        }
        // Se for maior que 100
        if (plan.getName().equals("PLATINUM")) return 4.00;
        else return (double) size * 2;
    }

    private void updateDebt(TariffPlan plan){
        double n = this.computeCost(plan);
        this.get_from().updateDebtValue(n);
        this.get_from().get_owner().updateDebts(n);
    }


    protected int getSize(){
        return _message.length();
    }

    public String getMessage(){
        return _message;
    }
}
