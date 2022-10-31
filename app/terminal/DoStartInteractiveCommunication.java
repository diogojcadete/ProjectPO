package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {
  private Terminal _terminal;
  private Network _context;
  DoStartInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    String[] _options = {"VOICE", "VIDEO"};
    addStringField("toTerminalID", Message.terminalKey());
    addOptionField("communicationType", Message.commType(), _options);
    _terminal = terminal;
    _context = context;
  }
  
  @Override
  protected final void execute() throws CommandException {
    String toTerminalID = stringField("toTerminalID");
    Terminal terminalTo = _context.searchTerminal(toTerminalID);
    String terminalFrom = _terminal.getID();
    String communicationType = optionField("communicationType");
  }
}
