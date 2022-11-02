package prr.app.terminal;

import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {

  private Terminal _terminal;
  private Network _context;

  DoShowOngoingCommunication(Network context, Terminal terminal) {
    super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
    _terminal = terminal;
    _context = context;
  }
  
  @Override
  protected final void execute() throws CommandException {
    Communication c = _terminal.getOnGoing();
    if(c == null){
      _display.addLine(Message.noOngoingCommunication());
      _display.display();
    }
    else {
      _display.addLine(_terminal.getOnGoing().formattedCommunication());
      _display.display();
    }
  }
}
