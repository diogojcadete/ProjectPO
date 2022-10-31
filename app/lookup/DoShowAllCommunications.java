package prr.app.lookup;

import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
//FIXME more imports if needed

/**
 * Command for showing all communications.
 */
class DoShowAllCommunications extends Command<Network> {

  DoShowAllCommunications(Network receiver) {
    super(Label.SHOW_ALL_COMMUNICATIONS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    List<Communication> _communication = _receiver.getCommunications();
    _display.add(_receiver.showCommunications(_communication));
    _display.display();
  }
}
