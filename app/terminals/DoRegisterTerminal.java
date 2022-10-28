package prr.app.terminals;

import prr.app.exception.DuplicateClientKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.ClientLevel;
import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import prr.core.Terminal;
import prr.core.exception.DuplicateTerminalKeyException;
import prr.core.exception.InvalidTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    String[] _options = {"BASIC", "FANCY"};
    addStringField("terminalID", Message.terminalKey());
    addOptionField("terminalType", Message.terminalType(), _options);
    addStringField("clientID", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException {
    String terminalID = stringField("terminalID");
    String terminalType = stringField("terminalType");
    String clientID = stringField("clientID");
    try {
      Terminal terminal = _receiver.registerTerminal(terminalType, terminalID, clientID);
    } catch (DuplicateTerminalKeyException e) {
      throw new prr.app.exception.DuplicateTerminalKeyException(terminalID);
    } catch (InvalidTerminalKeyException e) {
      throw new prr.app.exception.InvalidTerminalKeyException(terminalID);
    }
  }
}
