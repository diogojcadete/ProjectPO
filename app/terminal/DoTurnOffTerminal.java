package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalMode;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {

  private Terminal _terminal;
  private Network _context;
  DoTurnOffTerminal(Network context, Terminal terminal) {
    super(Label.POWER_OFF, context, terminal);
    _terminal = terminal;
    _context = context;
  }
  
  @Override
  protected final void execute() throws CommandException {
    if(_terminal.getMode().equals(TerminalMode.OFF)){
      _display.addLine(Message.alreadyOff());
      _display.display();
    }
    else if(!_terminal.equals(TerminalMode.BUSY)){
      _context.setMode(TerminalMode.OFF, _terminal);
    }
  }
}
