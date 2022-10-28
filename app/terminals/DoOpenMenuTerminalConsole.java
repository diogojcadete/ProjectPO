package prr.app.terminals;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import prr.core.NetworkManager;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
//FIXME add mode import if needed

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

  DoOpenMenuTerminalConsole(Network receiver) {
    super(Label.OPEN_MENU_TERMINAL, receiver);
    addStringField("terminalID", Message.terminalKey());


    //FIXME add command fields
  }

  @Override
  protected final void execute() throws CommandException {
    String terminalID = stringField("terminalID");
    List<Terminal> terminals = _receiver.getTerminals();
    Terminal t = _receiver.searchTerminal(terminalID);
    if(t == null){
      throw new UnknownTerminalKeyException(terminalID);
    }
    else {
      new prr.app.terminal.Menu(_receiver, t).open();
    }




    //FIXME implement command
    // create an instance of prr.app.terminal.Menu with the
    // selected Terminal and open it
  }
}
