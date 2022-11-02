package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalMode;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {

  private Terminal _terminal;
  private Network _context;
  DoSilenceTerminal(Network context, Terminal terminal) {
    super(Label.MUTE_TERMINAL, context, terminal);
    _terminal = terminal;
    _context = context;
  }
  
  @Override
  protected final void execute() throws CommandException {
    if(_terminal.getMode().equals(TerminalMode.SILENCE)){
      _display.addLine(Message.alreadySilent());
      _display.display();
    }
    else if(!_terminal.getMode().equals(TerminalMode.OFF) && !_terminal.getMode().equals(TerminalMode.BUSY)){
      _context.setMode(TerminalMode.SILENCE, _terminal);
    }
  }
}
