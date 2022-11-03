package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalMode;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {

  private Terminal _terminal;
  private Network _context;
  DoTurnOnTerminal(Network context, Terminal terminal) {
    super(Label.POWER_ON, context, terminal);
    _terminal = terminal;
    _context = context;
  }
  
  @Override
  protected final void execute() throws CommandException {
    if(_terminal.getMode().equals(TerminalMode.IDLE)){
      _display.addLine(Message.alreadyOn());
      _display.display();
    }
    else if(!_terminal.equals(TerminalMode.BUSY)){
      _context.setMode(TerminalMode.IDLE, _terminal);
      _terminal.sendNotificationsI();
    }
  }
}
