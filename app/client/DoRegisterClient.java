package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import prr.core.exception.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    addStringField("clientID", Message.key());
    addStringField("name", Message.name());
    addIntegerField("taxID", Message.taxId());
  }
  
  @Override
  protected final void execute() throws CommandException {
    String clientID = stringField("clientID");
    String name = stringField("name");
    int taxID = integerField("taxID");

    try{
      Client newClient = _receiver.registerClient(clientID,name, taxID);
    }
    catch(DuplicateClientKeyException exp){
      throw new prr.app.exception.DuplicateClientKeyException(clientID);
    }
  }
}
