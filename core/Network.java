package prr.core;

import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.*;

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
  private List<Notification> _notifications;

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
    Parser _parser = new Parser(this);
    _parser.parseFile(filename);

  }

  /*
    |--------------------------|********************|--------------------------|
    |--------------------------|***CLIENT METHODS***|--------------------------|
    |--------------------------|********************|--------------------------|
 */

  //This method registers a new client
  public Client registerClient(String key, String name, int taxNumber) throws DuplicateClientKeyException {
    for(Client c: _clients){
      if(c.getKey().equals(key)){
        throw new DuplicateClientKeyException();
      }
    }
    Client newClient = new Client(key,name, taxNumber);
    _clients.add(newClient);
    return newClient;
  }

  //This method will be called by a lookup command
  public String showClients(){
    StringBuilder str = new StringBuilder();
    for(int i= 0; i< _clients.size()-1;i++){
      str.append(_clients.get(i).formattedClient()+"\n");
    }
    if(_clients.size()>0) {
      str.append(_clients.get(_clients.size() - 1).formattedClient());
    }
    return str.toString();
  }

  public String showClient(String clientID) throws UnknownClientKeyException {
    Client c1 = searchClient(clientID);
    if(c1 == null){
      throw new UnknownClientKeyException(clientID);
    }
    return c1.formattedClient();
  }

  public List<Client> getClients(){
    return _clients;
  }

  //This method allows us to search for a client with his ID
  public Client searchClient(String clientID){
    for(Client c:_clients){
      if(clientID.equals(c.getKey())){
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
  public Terminal registerTerminal (String terminalType, String clientKey, String terminalID) throws DuplicateTerminalKeyException, InvalidTerminalKeyException, UnknownClientKeyException{
    Client c1 = searchClient(clientKey);
    if(c1==null) {
      throw new UnknownClientKeyException(clientKey);
    }
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
       FancyTerminal f1 = new FancyTerminal(terminalID, "FANCY", c1, TerminalMode.ON);
       _terminals.add(f1);
       c1.addTerminal(f1);
       return f1;
      case "BASIC":
        BasicTerminal b1= new BasicTerminal(terminalID, "BASIC", c1, TerminalMode.ON);
        _terminals.add(b1);
        c1.addTerminal(b1);
        return b1;
    }
    return null;
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

  public String showTerminals(){
    StringBuilder str = new StringBuilder();
    for(Terminal t:_terminals){
      str.append(t.formattedTerminal());
    }
    return str.toString();
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

  public List<Notification> getNotifications(){
    return _notifications;
  }

  public String showAllNotifications(String clientID){
    Client c = searchClient(clientID);
    StringBuilder sb = new StringBuilder();
    for(Notification n: c.getNotifications()){
      sb.append(n.formattedNotification());
    }
    return sb.toString();
  }
}


