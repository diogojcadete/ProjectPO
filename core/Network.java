package prr.core;

import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import prr.core.exception.DuplicateTerminalKeyException;
import prr.core.exception.InvalidTerminalKeyException;
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

  /*
    |--------------------------|********************|--------------------------|
    |--------------------------|***CLIENT METHODS***|--------------------------|
    |--------------------------|********************|--------------------------|
 */

  //This method registers a new client
  public void registerClient(String name, String key, int taxNumber){
    Client newClient = new Client(key, name, taxNumber);
    this._clients.add(newClient);
  }

  //This method will be called by a lookup command
  public List<Client> getClients(){
    return _clients;
  }

  //This method allows us to search for a client with his ID
  public Client searchClient(String clientID){
    for(Client c:_clients){
      if(clientID.equals(c.get_key())){
        return c;
      }
    }
    return null;
  }

  //This method activates the reception of notifications of failed Communications
  public void activateFailedComms(String clientID){
    Client c1 = searchClient(clientID);
    c1.set_receiveNotificationsON();
  }

  //This method deactivates the reception of notifications of failed Communications
  public void deactivateFailedComms(String clientID){
    Client c1 = searchClient(clientID);
    c1.set_receiveNotificationsOFF();
  }


/* |--------------------------|********************|--------------------------|
   |--------------------------|**TERMINAL METHODS**|--------------------------|
   |--------------------------|********************|--------------------------|
 */

  /* This method registers a terminal if given the following information:
      -Terminal Type: "BASIC" or "FANCY"
      -The Client ID linked to the terminal we are creating
      -The Terminal ID of the terminal we are creating
  */
  public Terminal registerTerminal (String terminalType, String clientKey, String terminalID) throws DuplicateTerminalKeyException, InvalidTerminalKeyException {
    Client c1 = searchClient(clientKey);
    if(terminalID.length() != 6){
      throw new InvalidTerminalKeyException();
    }
    for(Terminal t: _terminals){
      if(t.getID().equals(terminalID)){
        throw new DuplicateTerminalKeyException();
      }
    }
    switch(terminalType){
      case "FANCY":
        return new FancyTerminal(terminalID, "FANCY", c1, TerminalMode.ON);
      case "BASIC":
        return new BasicTerminal(terminalID, "BASIC", c1, TerminalMode.ON);
    }


  }

  //This method allows us to search for a client with his ID
  public Terminal searchTerminal(String terminalID){
    for(Terminal t:_terminals){
      if(terminalID.equals(t.getID())){
        return t;
      }
    }
    return null;
  }

  //This method will be called by a lookup command
  public List<Terminal> getTerminals(){
    return _terminals;
  }

  public List<Terminal> getUnusedTerminals(){
    List<Terminal> _terminals = getTerminals();
    List<Terminal> _final = new ArrayList<Terminal>();
    for(Terminal t: _terminals){
      if (!t.wasUsed())
        _final.add(t);
    }
    return _final;
  }

  /* This method will send a text communication to a terminal
     if the Terminal sending the text isn't turned OFF or
     if it doesn't have a communication ongoing
   */
  public void sendTextCommunication(Terminal from, String toKey, String msg){
    Terminal t1 = searchTerminal(toKey);
    if(from.get_mode() != TerminalMode.OFF && !(from.canEndCurrentCommunication())) {
      from.makeSMS(t1,msg);
    }
  }

  //This method will add a friend to the terminal if the terminals aren't already friends
  public void addFriend(String terminalID, String friendID){
    Terminal t1 = searchTerminal(terminalID);
    Terminal t2 = searchTerminal(friendID);

    if(!t1.checkFriends(t2)){
      t1.addFriend(t2);
    }
  }


  //This method will start the interactive communication selected by the user
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

  //This method will end the ongoing communication of the respective terminal (if there is any)
  public void endOnGoingCommunication(Terminal from, int duration){
    if (from.canEndCurrentCommunication()){
      from.endOnGoingCommunication(duration);
    }

  }
}


