package prr.core;

import java.io.Serializable;

public class TextCommunication extends Communication implements Serializable {
    private static final long serialVersionUID = 202208091753L;
    private final String _message;

    public TextCommunication(Terminal _from, Terminal _to, String _message) {
        super(_from, _to);
        this._message = _message;
         setSize(_message.length());
        _from.addMadeCommunications(this);
        _to.addReceivedCommunications(this);
        this.setType("TEXT");

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
        this.getFrom().updateDebtValue(n);
        this.getFrom().getOwner().updateDebts(n);
    }




    public String getMessage(){
        return _message;
    }

}
