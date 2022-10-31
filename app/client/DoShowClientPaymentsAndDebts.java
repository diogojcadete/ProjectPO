package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

  DoShowClientPaymentsAndDebts(Network receiver) {
    super(Label.SHOW_CLIENT_BALANCE, receiver);
    addStringField("clientKey", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    String clientID = stringField("clientKey");
    Client c = _receiver.searchClient(clientID);

    _display.addLine(Message.clientPaymentsAndDebts(clientID, c.getPayments(), c.getDebts()));
    _display.display();
  }
}
