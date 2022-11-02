package prr.app.lookup;

import prr.app.exception.UnknownClientKeyException;
import prr.app.terminal.Message;
import prr.core.Client;
import prr.core.Communication;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
//FIXME add more imports if needed

/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

  DoShowCommunicationsToClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
    addStringField("clientID", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException {
    String clientID = stringField("clientID");
    Client _client = _receiver.searchClient(clientID);
    try {
      _receiver.checkClientKeyExceptions(clientID);
      List<Communication> _receivedCommunications = _receiver.getCommunicationsToClient(_client);//Tenho que por a excecao no get?
      _display.add(_receiver.showCommunicationsClient(_receivedCommunications, clientID));
      _display.display();
    } catch (UnknownClientKeyException e) {
      throw new UnknownClientKeyException(clientID);
    }
  }
}
