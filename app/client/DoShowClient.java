package prr.app.client;


import prr.core.Network;
import prr.core.Client;
import prr.app.exception.UnknownClientKeyException;
import prr.core.Notification;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
//FIXME add more imports if needed

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

  DoShowClient(Network receiver) {
    super(Label.SHOW_CLIENT, receiver);
    addStringField("clientID",Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    List<Client> _clients = _receiver.getClients();
    String clientID = stringField("clientID");
    Client c = _receiver.searchClient(clientID);
    if(c == null){
      throw new UnknownClientKeyException(clientID);
    }
    List<Notification> _notifications = _receiver.getNotifications();
    _display.addLine(c.formattedClient());
    for(Notification n: _notifications){
      _display.addLine(n.formattedNotification());
    }
    _display.display();

  }
}
