package prr.app.terminals;
import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {
  DoOpenMenuTerminalConsole(Network receiver) {
    super(Label.OPEN_MENU_TERMINAL, receiver);
    addStringField("terminalID", Message.terminalKey());
  }
  @Override
  protected final void execute() throws CommandException {
    String terminalID = stringField("terminalID");
    Terminal t = _receiver.searchTerminal(terminalID);
    if(t == null){
      throw new UnknownTerminalKeyException(terminalID);
    }
    else {
      new prr.app.terminal.Menu(_receiver, t).open();
    }
  }
}
