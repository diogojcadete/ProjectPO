package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {
  Terminal _terminal;
  Network _network;
  DoAddFriend(Network context, Terminal terminal) {
    super(Label.ADD_FRIEND, context, terminal);
    addStringField("amigoId", Message.terminalKey());
    _terminal = terminal;
    _network = context;
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _network.addFriend(_terminal.getID(), stringField("amigoId"));
    } catch(UnknownTerminalKeyException e){
      throw new UnknownTerminalKeyException(stringField("amigoId"));
    }
    }
}
