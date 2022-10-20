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
    addStringField("clientID", Message.key());
  }

  @Override
  protected final void execute() throws CommandException {
    String client = stringField("clientID");
    String clientInfo = _receiver.showClient(client);
    String notification = _receiver.showAllNotifications(client);
    _display.addLine(clientInfo);
    if (!notification.isEmpty()) {
      _display.addLine(notification);
    }
    _display.display();


  }
}
