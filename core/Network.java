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
  public void registerClient(String name, String key, int taxNumber){
    Client newClient = new Client(key, name, taxNumber);
    this._clients.add(newClient);
  }

  public List<Client> getClients(){
    return _clients;
  }

  public Client searchClient(String clientID){
    for(Client c:_clients){
      if(clientID.equals(c.get_key())){
        return c;
      }
    }
    return null;
  }

  public void activateFailedComms(String clientID){
    Client c1 = searchClient(clientID);
    c1.set_receiveNotificationsON();
  }
  public void deactivateFailedComms(String clientID){
    Client c1 = searchClient(clientID);
    c1.set_receiveNotificationsOFF();
  }


  //TERMINAL METHODS

  public Terminal registerTerminal(String terminalType, String clientKey, String terminalID){
    Client c1 = searchClient(clientKey);
    switch(terminalType){
      case "FANCY":
        return new FancyTerminal(terminalID, "FANCY", c1, TerminalMode.ON);
      case "BASIC":
        return new BasicTerminal(terminalID, "BASIC", c1, TerminalMode.ON);
    }

  }
  public Terminal searchTerminal(String terminalID){
    for(Terminal t:_terminals){
      if(terminalID.equals(t.get_id())){
        return t;
      }
    }
    return null;
  }
  public List<Terminal> getTerminals(){
    return _terminals;
  }

  public void sendTextCommunication(Terminal from, String toKey, String msg){
    Terminal t1 = searchTerminal(toKey);
    if(from.get_mode() != TerminalMode.OFF && from.canEndCurrentCommunication() == false) {
      from.makeSMS(t1,msg);
    }
  }

  public void addFriend(String terminalID, String friendID){
    Terminal t1 = searchTerminal(terminalID);
    Terminal t2 = searchTerminal(friendID);

    if(!t1.checkFriends(t2)){
      t1.addFriend(t2);
    }
  }

  public void startInteractiveCommunication(Terminal from, String toKey, String type){
    String str1 = "VIDEO";
    String str2 = "VOICE";
    Terminal terminalTo = searchTerminal(toKey);

    if(str1.equals(type)){
      Communication interactiveCommunication = new VideoCommunication(from, terminalTo);
    }
    else if (str2.equals(type)) {
      Communication interactiveCommunication = new VoiceCommunication(from, terminalTo);
    }
  }

  public void endOnGoingCommunication(Terminal from, int duration){
    if (from.canEndCurrentCommunication()){
      from.endOnGoingCommunication(duration);
    }

  }
}


