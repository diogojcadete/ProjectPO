package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {
  Network _network;
  Terminal _terminal;

  DoRemoveFriend(Network context, Terminal terminal) {
    super(Label.REMOVE_FRIEND, context, terminal);
    addStringField("enemyId", Message.terminalKey());
    _network = context;
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _network.removeFriend(_terminal.getID(), stringField("enemyId"));
    } catch(UnknownTerminalKeyException e){
      throw new UnknownTerminalKeyException(stringField("enemyId"));
    }
  }
}
