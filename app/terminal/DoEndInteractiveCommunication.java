package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

  private Network _network;
  private Terminal _terminal;
  DoEndInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
    addIntegerField("duration", Message.duration());
    _network = context;
    _terminal = terminal;

  }
  
  @Override
  protected final void execute() throws CommandException {
    int _duration = integerField("duration");
    _network.endOnGoingCommunication(_terminal,_duration);
  }
}
