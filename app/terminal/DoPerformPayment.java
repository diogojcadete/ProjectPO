package prr.app.terminal;

import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {
  Network _network;
  Terminal _terminal;

  DoPerformPayment(Network context, Terminal terminal) {
    super(Label.PERFORM_PAYMENT, context, terminal);
    addStringField("communicationId", Message.commKey());
    _network = context;
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    Communication c = _network.searchCommunication(stringField("communicationId"));
    if (c != null && _terminal.getMadeCommunications().contains(c) && !c.getIsPaid())
      _network.makePayment(_terminal, stringField("communicationId"));
    else{
      _display.addLine(Message.invalidCommunication());
      _display.display();
    }

  }
}
