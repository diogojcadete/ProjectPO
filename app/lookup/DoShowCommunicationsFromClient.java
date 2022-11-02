package prr.app.lookup;

import prr.app.exception.UnknownClientKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import prr.app.terminal.Message;
import prr.core.Client;
import prr.core.Communication;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
//FIXME add more imports if needed

/**
 * Show communications from a client.
 */
class DoShowCommunicationsFromClient extends Command<Network> {

  DoShowCommunicationsFromClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_FROM_CLIENT, receiver);
    addStringField("clientID", Message.clientKey());
    //FIXME add command fields
  }

  @Override
  protected final void execute() throws CommandException {
    String clientID = stringField("clientID");
    Client _client = _receiver.searchClient(clientID);
    try {
        _receiver.checkClientKeyExceptions(clientID);
        List<Communication> _madeCommunications = _receiver.getCommunicationsFromClient(_client);
        _display.add(_receiver.showCommunicationsClient(_madeCommunications, clientID));
        _display.display();
      } catch (UnknownClientKeyException e) {
      throw new UnknownClientKeyException(clientID);
    }
  }
}
