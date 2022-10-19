package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
//FIXME add more imports if needed

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

  DoShowAllClients(Network receiver) {
    super(Label.SHOW_ALL_CLIENTS, receiver);
  }
  
  @Override
  protected final void execute() throws CommandException {
    List<Client> _clients = _receiver.getClients();
    for(Client client: _clients){
      _display.addLine(client.formattedClient());
    }
    _display.display();
  }
}
