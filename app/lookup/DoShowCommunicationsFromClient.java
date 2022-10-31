package prr.app.lookup;

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
    addStringField("idClient", Message.clientKey());
    //FIXME add command fields
  }

  @Override
  protected final void execute() throws CommandException {
    Client _client = _receiver.searchClient(stringField("idClient"));
    List<Communication> _communicationFromClient = _client.getCommunication();
    _display.add(_receiver.showCommunications(_communicationFromClient));
    _display.display();
  }
}
