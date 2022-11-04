package prr.app.terminal;

import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

  private final Network _network;
  private final Terminal _terminal;
  DoEndInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, Terminal::canEndCurrentCommunication);
    addIntegerField("duration", Message.duration());
    _network = context;
    _terminal = terminal;

  }
  
  @Override
  protected final void execute() throws CommandException {
    int _duration = integerField("duration");
    Communication c = _terminal.getOnGoing();
    _network.endOnGoingCommunication(_terminal,_duration);
    _display.addLine(Message.communicationCost(c.getCost()));
    _display.display();
  }
}
