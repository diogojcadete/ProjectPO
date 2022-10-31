package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.menus.Command;

//FIXME add more imports if needed

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {
  private Terminal _terminal;
  private Network _context;
  DoSendTextCommunication(Network context, Terminal terminal) {
    super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("toTerminalID", Message.terminalKey());
    addStringField("Message", Message.textMessage());
    _terminal = terminal;
    _context = context;
  }

  @Override
  protected final void execute() throws CommandException {
    String toTerminalID = stringField("toTerminalID");
    String message = stringField("Message");
    _context.sendTextCommunication(_terminal, toTerminalID, message);
  }
} 
