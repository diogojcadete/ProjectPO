package prr.core;

import java.io.Serial;
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

//Grupo 79, Diogo Cadete 102477, João Maia 103845

public class Network implements Serializable {

  /** Serial number for serialization. */
  @Serial
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods

  private final List<Terminal> _terminals;
  private List<Communication> _communications;
  private final List<Client> _clients;
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

  /**
    |--------------------------|********************|--------------------------|
    |--------------------------|***CLIENT METHODS***|--------------------------|
    |--------------------------|********************|--------------------------|
 */

  /**
   * This method will register a client
   * @param clientKey
   * @param name
   * @param taxNumber
   * @return newClient
   * @throws DuplicateClientKeyException
   */
  public Client registerClient(String clientKey, String name, int taxNumber) throws DuplicateClientKeyException {
    checkRegisterClientException(clientKey);
    Client newClient = new Client(clientKey,name, taxNumber);
    _clients.add(newClient);
    return newClient;
  }

  /**
   * This method will check if there is an exception
   * @param clientKey
   * @throws DuplicateClientKeyException
   */
  public void checkRegisterClientException(String clientKey) throws DuplicateClientKeyException {
    for(Client c: _clients){
      if(c.getKey().equals(clientKey)){
        throw new DuplicateClientKeyException();
      }
    }
  }

  /**
   * This method will return a formatted string of every Client
   * @return strClients.toString()
   */
  public String showClients(){
    StringBuilder strClients = new StringBuilder();
    for(int i= 0; i< _clients.size()-1;i++){
      strClients.append(_clients.get(i).formattedClient()+"\n");
    }
    if(_clients.size()>0) {
      strClients.append(_clients.get(_clients.size() - 1).formattedClient());
    }
    return strClients.toString();
  }

  /**
   * This method will return a formatted string of a Client
   * @param clientID
   * @return c1.formattedClient()
   * @throws UnknownClientKeyException
   */
  public String showClient(String clientID) throws UnknownClientKeyException {
    Client c1 = searchClient(clientID);
    if(c1 == null){
      throw new UnknownClientKeyException(clientID);
    }
    return c1.formattedClient();
  }

  /**
   * This method will return the list that contains all clients
   * @return _clients
   */
  public List<Client> getClients(){
    return _clients;
  }

  /**
   * This method allows us to search for a client with his ID
   * @param clientID
   * @return c
   */
  public Client searchClient(String clientID){
    for(Client c:_clients){
      if(clientID.equals(c.getKey())){
        return c;
      }
    }
    return null;
  }

  /**
   * This method activates the reception of notifications of failed Communications
   * @param clientID
   */
  public void activateFailedComms(String clientID){
    Client c1 = searchClient(clientID);
    c1.setReceiveNotificationsON();
  }

  /**
   * This method deactivates the reception of notifications of failed Communications
   * @param clientID
   */
  public void deActivateFailedComms(String clientID){
    Client c1 = searchClient(clientID);
    c1.setReceiveNotificationsOFF();
  }


/**
   |--------------------------|********************|--------------------------|
   |--------------------------|**TERMINAL METHODS**|--------------------------|
   |--------------------------|********************|--------------------------|
 */

  /**
   *
   * This method registers a terminal if given the following information:
   *   -Terminal Type: "BASIC" or "FANCY"
   *   -The Client ID linked to the terminal we are creating
   *   -The Terminal ID of the terminal we are creating
   * @param terminalType
   * @param terminalID
   * @param clientKey
   * @return f1 or b1
   * @throws DuplicateTerminalKeyException
   * @throws InvalidTerminalKeyException
   * @throws UnknownClientKeyException
   */
  public Terminal registerTerminal (String terminalType, String terminalID, String clientKey) throws DuplicateTerminalKeyException, InvalidTerminalKeyException, UnknownClientKeyException{
    Client c1 = searchClient(clientKey);
    checkRegisterTerminalExceptions(terminalID, clientKey);
    switch (terminalType) {
      case "FANCY" -> {
        FancyTerminal f1 = new FancyTerminal(terminalID, "FANCY", c1, TerminalMode.IDLE);
        _terminals.add(f1);
        c1.addTerminal(f1);
        return f1;
      }
      case "BASIC" -> {
        BasicTerminal b1 = new BasicTerminal(terminalID, "BASIC", c1, TerminalMode.IDLE);
        _terminals.add(b1);
        c1.addTerminal(b1);
        return b1;
      }
    }
    return null;
  }

  /**
   * This method checks if there ary any exceptions
   * @param terminalID
   * @param clientKey
   * @throws UnknownClientKeyException
   * @throws InvalidTerminalKeyException
   * @throws DuplicateTerminalKeyException
   */
  public void checkRegisterTerminalExceptions(String terminalID, String clientKey) throws UnknownClientKeyException, InvalidTerminalKeyException, DuplicateTerminalKeyException {
    Client c1 = searchClient(clientKey);
    if(c1==null) {
      throw new UnknownClientKeyException(clientKey);
    }
    if(terminalID.length() != 6){
      throw new InvalidTerminalKeyException();
    }
    if(terminalID.matches(".*[a-zA-Z].*")){
      throw new InvalidTerminalKeyException();
    }
    for(Terminal t: _terminals){
      if(t.getID().equals(terminalID)){
        throw new DuplicateTerminalKeyException();
      }
    }
  }

  /**
   * This method allows us to search for a Terminal with the terminalID
   * @param terminalID
   * @return t
   */
  public Terminal searchTerminal(String terminalID){
    for(Terminal t:_terminals){
      if(terminalID.equals(t.getID())){
        return t;
      }
    }
    return null;
  }

  /**
   * This method allows us to get the list that contains all terminals
   * @return _terminals
   */
  public List<Terminal> getTerminals(){
    return _terminals;
  }

  /**
   * This method will return a formatted string of every terminal
   * @return strTerminals.toString()
   */
  public String showTerminals(List<Terminal> terminals){
    StringBuilder strTerminals = new StringBuilder();
      for(int i= 0; i< terminals.size()-1;i++){
        strTerminals.append(terminals.get(i).formattedTerminal()).append("\n");
      }
    if(terminals.size()>0) {
      strTerminals.append(terminals.get(terminals.size()-1).formattedTerminal());
    }
    return strTerminals.toString();
  }

  /**
   * This method will return a list of unused Terminals
   * @return _unusedTerminals
   */
  public List<Terminal> getUnusedTerminals(){
    List<Terminal> _terminals = getTerminals();
    List<Terminal> _unusedTerminals = new ArrayList<Terminal>();
    for(Terminal t: _terminals){
      if (!t.wasUsed())
        _unusedTerminals.add(t);
    }
    return _unusedTerminals;
  }

  /**
   * This method will send a text communication to a terminal
   * if the Terminal sending the text isn't turned OFF or
   * if it doesn't have a communication ongoing
   * @param from
   * @param toKey
   * @param msg
   */

  public void sendTextCommunication(Terminal from, String toKey, String msg){
    Terminal t1 = searchTerminal(toKey);
    if(from.get_mode() != TerminalMode.OFF && !(from.canEndCurrentCommunication())) {
      from.makeSMS(t1,msg);
    }
  }

  /**
   * This method will add a friend to the terminal if the terminals aren't already friends
   * @param terminalID
   * @param friendID
   */
  public void addFriend(String terminalID, String friendID){
    Terminal t1 = searchTerminal(terminalID);
    Terminal t2 = searchTerminal(friendID);

    if(!t1.checkFriends(t2)){
      t1.addFriend(t2);
    }
  }

  /**
   * This method will start the interactive communication selected by the user
   * @param from
   * @param toKey
   * @param type
   */
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

  /**
   * This method will end the ongoing communication of the respective terminal (if there is any)
   * @param from
   * @param duration
   */
  public void endOnGoingCommunication(Terminal from, int duration){
    if (from.canEndCurrentCommunication()){
      from.endOnGoingCommunication(duration);
    }
  }

  /**
   *
   *  |--------------------------|**************************|--------------------------|
   *  |--------------------------|**NOTIFICATIONS METHODS**|--------------------------|
   *  |--------------------------|************************|--------------------------|
   */

  /**
   * This method will return the list that contains all notifications
   * @return _notifications
   */
  public List<Notification> getNotifications(){
    return _notifications;
  }

  /**
   * This method will return a formatted of every notification
   * @param clientID
   * @return strNotifications.toString()
   */
  public String showAllNotifications(String clientID){
    Client c = searchClient(clientID);
    StringBuilder strNotifications = new StringBuilder();
    for(Notification n: c.getNotifications()){
      strNotifications.append(n.formattedNotification());
    }
    return strNotifications.toString();
  }
}


