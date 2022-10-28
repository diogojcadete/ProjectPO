package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

  DoDisableClientNotifications(Network receiver) {
    super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("clientKey", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    String _clientKey = stringField("clientKey");
    Client _client = _receiver.searchClient(_clientKey);
    if(!_client.getReceiveNotifications()){
      System.out.println(Message.clientNotificationsAlreadyDisabled());
    }
    _receiver.deActivateFailedComms(_clientKey);
  }
}
