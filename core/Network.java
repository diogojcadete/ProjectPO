package prr.core;

import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import prr.core.exception.UnrecognizedEntryException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods

  private List<Terminal> _terminals;
  private List<Communication> _communications;
  private List<Client> _clients;
  private List<TariffPlan> _tariffPlans;

  public Network() {
    this._terminals = new ArrayList<>();
    this._communications = new ArrayList<>();
    this._clients = new ArrayList<>();
    this._tariffPlans =  new ArrayList<>();
  }

  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    //FIXME implement method
  }
  public void registerClient(String name, int taxNumber, String key){
    Client newClient = new Client(key, name, taxNumber);
    this._clients.add(newClient);
  }

  public void registerTerminal(TerminalType terminalType, String clientKey, String terminalID){
    switch(terminalType.toString()){

    }
  }
  public List<Terminal> getTerminals(){
    return _terminals;
  }
  public List<Client> getClients(){
    return _clients;
  }
  public void activateFailedComms(String )
  public void sendTextCommunication(Terminal from, String toKey, String msg){
    Communication textMessage = new TextCommunication(from,);
  }

  public void startInteractiveCommunication(Terminal from, String toKey, String type){
    String str1 = "VIDEO";
    String str2 = "VOICE";

    if(str1.equals(type)){
      Communication interactiveCommunication = new VideoCommunication()
    }
  }
}


