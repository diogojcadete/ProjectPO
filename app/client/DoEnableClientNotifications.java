package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
//FIXME add more imports if needed

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

  DoEnableClientNotifications(Network receiver) {
    super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("clientKey", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    String _clientKey = stringField("clientKey");
    Client _client = _receiver.searchClient(_clientKey);
    if(_client.getReceiveNotifications()){
      System.out.println(Message.clientNotificationsAlreadyEnabled());
    }
    _receiver.activateFailedComms(_clientKey);
  }
}
