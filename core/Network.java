package prr.core;

import java.io.Serial;
import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import prr.app.exception.UnknownClientKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.comparator.ClientComparator;
import prr.core.comparator.TerminalComparator;
import prr.core.exception.*;



/**
 * Class Store implements a store.
 */

//Grupo 79, Diogo Cadete 102477, João Maia 103845

public class Network implements Serializable {

  /**
   * Serial number for serialization.
   */
  @Serial
  private static final long serialVersionUID = 202208091753L;

  private final List<Terminal> _terminals;
  private List<Communication> _communications;
  private final List<Client> _clients;
  private List<TariffPlan> _tariffPlans;
  private List<Notification> _notifications;

  public Network() {
    this._terminals = new ArrayList<>();
    this._communications = new ArrayList<>();
    this._clients = new ArrayList<>();
    this._tariffPlans = new ArrayList<>();

  }

  /**
   * Read text input file and create corresponding domain entities.
   *
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException                if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
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
   *
   * @param clientKey
   * @param name
   * @param taxNumber
   * @return newClient
   * @throws DuplicateClientKeyException
   */
  public Client registerClient(String clientKey, String name, int taxNumber) throws DuplicateClientKeyException {
    checkRegisterClientException(clientKey);
    Client newClient = new Client(clientKey, name, taxNumber);
    _clients.add(newClient);
    Collections.sort(_clients,new ClientComparator());
    return newClient;
  }

  /**
   * This method will check if there is an exception
   *
   * @param clientKey
   * @throws DuplicateClientKeyException
   */
  public void checkRegisterClientException(String clientKey) throws DuplicateClientKeyException {
    for (Client c : _clients) {
      if (c.getKey().equals(clientKey)) {
        throw new DuplicateClientKeyException();
      }
    }
  }

  /**
   * This method will return a formatted string of every Client
   * @param clients
   * @return strClients.toString()
   */
  public String showClients(List<Client> clients) {
    StringBuilder strClients = new StringBuilder();
    for (int i = 0; i < clients.size() - 1; i++) {
      strClients.append(clients.get(i).formattedClient()).append("\n");
    }
    if (clients.size() > 0) {
      strClients.append(clients.get(clients.size() - 1).formattedClient());
    }
    return strClients.toString();
  }

  /**
   * This method will return positive clients
   * @return showClients(_positives)
   */
  public String showPositiveClients() {
    ArrayList<Client> _positives = new ArrayList<>();
    for(Client c: _clients){
      if (c.getPayments() == c.getDebts())
        _positives.add(c);
    }
    return showClients(_positives);
  }

  /**
   * This method will return negative clients
   * @return showClients(_negativos)
   */
  public String showNegativeClients() {
    ArrayList<Client> _negativos = new ArrayList<>();
    for(Client c: _clients){
      if (c.getPayments() < c.getDebts())
        _negativos.add(c);
    }
    return showClients(_negativos);
  }

  /**
   * This method will return a formatted string of a Client
   *
   * @param clientID
   * @return c1.formattedClient()
   * @throws UnknownClientKeyException
   */
  public String showClient(String clientID) throws UnknownClientKeyException {
    Client c1 = searchClient(clientID);
    if (c1 == null) {
      throw new UnknownClientKeyException(clientID);
    }
    return c1.formattedClient();
  }

  /**
   * This methods evaluates the upgrade for a client
   * @param clientId
   */
  public void evaluateUpgrade(String clientId) {
    Client c = searchClient(clientId);
    if (c != null) {
      c.upgradeClient();
    }
  }

  /**
   * This method will check for unknown client key exceptions
   * @param clientID
   * @throws UnknownClientKeyException
   */
  public void checkClientKeyExceptions(String clientID) throws UnknownClientKeyException {
    Client client = searchClient(clientID);
    if(client == null){
      throw new UnknownClientKeyException(clientID);
    }
  }


  /**
   * This method allows us to search for a client with his ID
   *
   * @param clientID
   * @return c
   */
  public Client searchClient(String clientID) {
    for (Client c : _clients) {
      if (clientID.equals(c.getKey())) {
        return c;
      }
    }
    return null;
  }

  /**
   * This method activates the reception of notifications of failed Communications
   *
   * @param clientID
   */
  public void activateFailedComms(String clientID) {
    Client c1 = searchClient(clientID);
    c1.setReceiveNotificationsON();
  }

  /**
   * This method deactivates the reception of notifications of failed Communications
   *
   * @param clientID
   */
  public void deActivateFailedComms(String clientID) {
    Client c1 = searchClient(clientID);
    c1.setReceiveNotificationsOFF();
  }

  /**
   * This method will return the list that contains all clients
   *
   * @return _clients
   */
  public List<Client> getClients() {
    return _clients;
  }


/**
 |--------------------------|********************|--------------------------|
 |--------------------------|**TERMINAL METHODS**|--------------------------|
 |--------------------------|********************|--------------------------|
 */

  /**
   * This method registers a terminal if given the following information:
   * -Terminal Type: "BASIC" or "FANCY"
   * -The Client ID linked to the terminal we are creating
   * -The Terminal ID of the terminal we are creating
   *
   * @param terminalType
   * @param terminalID
   * @param clientKey
   * @return f1 or b1
   * @throws DuplicateTerminalKeyException
   * @throws InvalidTerminalKeyException
   * @throws UnknownClientKeyException
   */
  public Terminal registerTerminal(String terminalType, String terminalID, String clientKey) throws DuplicateTerminalKeyException, InvalidTerminalKeyException, UnknownClientKeyException {
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
   * This method checks terminal key exceptions
   * @param terminalID
   * @throws UnknownTerminalKeyException
   */
  public void checkTerminalKeyExceptions(String terminalID) throws UnknownTerminalKeyException{
    Terminal terminal = searchTerminal(terminalID);
    if(terminal == null){
      throw  new UnknownTerminalKeyException(terminalID);
    }
  }

  /**
   * This method checks if there ary any exceptions
   *
   * @param terminalID
   * @param clientKey
   * @throws UnknownClientKeyException
   * @throws InvalidTerminalKeyException
   * @throws DuplicateTerminalKeyException
   */
  public void checkRegisterTerminalExceptions(String terminalID, String clientKey) throws UnknownClientKeyException, InvalidTerminalKeyException, DuplicateTerminalKeyException {
    Client c1 = searchClient(clientKey);
    if (c1 == null) {
      throw new UnknownClientKeyException(clientKey);
    }
    if (terminalID.length() != 6) {
      throw new InvalidTerminalKeyException();
    }
    if (terminalID.matches(".*[a-zA-Z].*")) {
      throw new InvalidTerminalKeyException();
    }
    for (Terminal t : _terminals) {
      if (t.getID().equals(terminalID)) {
        throw new DuplicateTerminalKeyException();
      }
    }
  }

  /**
   * This method allows us to search for a Terminal with the terminalID
   *
   * @param terminalID
   * @return t
   */
  public Terminal searchTerminal(String terminalID) {
    for (Terminal t : _terminals) {
      if (terminalID.equals(t.getID())) {
        return t;
      }
    }
    return null;
  }


  /**
   * This method will return a formatted string of every terminal
   * @param terminals
   * @return strTerminals.toString()
   */
  public String showTerminals(List<Terminal> terminals) {
    StringBuilder strTerminals = new StringBuilder();
    Collections.sort(terminals,new TerminalComparator());
    for (int i = 0; i < terminals.size() - 1; i++) {
      strTerminals.append(terminals.get(i).formattedTerminal()).append("\n");
    }
    if (terminals.size() > 0) {
      strTerminals.append(terminals.get(terminals.size() - 1).formattedTerminal());
    }
    return strTerminals.toString();
  }

  /**
   * This method will return terminals with a positive balance
   * @return showTerminals(_positives)
   */
  public String showPositiveTerminals(){
    ArrayList<Terminal> _positives = new ArrayList<>();
    for(Terminal t: _terminals){
      if (t.getPayments() > t.getDebt())
        _positives.add(t);
    }
    return showTerminals(_positives);
  }

  /**
   * This method will return a list of unused Terminals
   *
   * @return _unusedTerminals
   */
  public List<Terminal> getUnusedTerminals() {
    List<Terminal> _terminals = getTerminals();
    List<Terminal> _unusedTerminals = new ArrayList<Terminal>();
    for (Terminal t : _terminals) {
      if (!t.wasUsed())
        _unusedTerminals.add(t);
    }
    return _unusedTerminals;
  }

  /**
   * This method will send a text communication to a terminal
   * if the Terminal sending the text isn't turned OFF or
   * if it doesn't have a communication ongoing
   *
   * @param from
   * @param toKey
   * @param msg
   */

  public void sendTextCommunication(Terminal from, String toKey, String msg) throws UnknownTerminalKeyException {
    Terminal t1 = searchTerminal(toKey);
    checkTerminalException(toKey);
    if (from.getMode() != TerminalMode.OFF && !(from.canEndCurrentCommunication())) {
      TextCommunication communication = from.makeSMS(t1, msg);
      _communications.add(communication);
      receiveTextDebt(from, communication);
      evaluateUpgrade(from.getID());
    }
  }

  /**
   * This method will receive the debt of a text communication
   * @param from
   * @param c
   */
  public void receiveTextDebt(Terminal from, TextCommunication c){
    Client owner = from.getOwner();
    long val = c.computeCost(owner.getTariffPlan());
    owner.updateDebts(val);
    from.updateDebtValue(val);
    c.updateCost(val);
  }

  /**
   * This method will add a friend to the terminal if the terminals aren't already friends
   *
   * @param terminalID
   * @param friendID
   */
  public void addFriend(String terminalID, String friendID) throws UnknownTerminalKeyException {
    checkTerminalException(friendID);
    Terminal t1 = searchTerminal(terminalID);
    Terminal t2 = searchTerminal(friendID);
    if (!(t1.getID().equals(t2.getID())) && !(t1.getFriends().contains(t2))) {
      t1.addFriend(t2);
    }
  }

  /**
   * This method will remove a friend of a terminal
   * @param terminalID
   * @param enemyID
   * @throws UnknownTerminalKeyException
   */
  public void removeFriend(String terminalID, String enemyID) throws UnknownTerminalKeyException{
    checkTerminalException(enemyID);
    Terminal t1 = searchTerminal(terminalID);
    Terminal t2 = searchTerminal(enemyID);
    if (!(t1.getID().equals(t2.getID())) && (t1.getFriends().contains(t2))) {
      t1.removeFriend(t2);
    }
  }

  /**
   * This method will set the mode of the terminak
   * @param mode
   * @param terminal
   */
  public void setMode(TerminalMode mode, Terminal terminal){
    if(mode.equals(TerminalMode.IDLE)){
      terminal.setOn();
    }
    else if(mode.equals(TerminalMode.BUSY)){
      terminal.setBusy();
    }
    else if(mode.equals(TerminalMode.OFF)){
      terminal.setOff();
    }
    else if(mode.equals(TerminalMode.SILENCE)){
      terminal.setOnSilent();
    }
  }



  /**
   * This method will start the interactive communication selected by the user
   *
   * @param from
   * @param toKey
   * @param type
   */
  public void startInteractiveCommunication(Terminal from, String toKey, String type) throws UnknownTerminalKeyException {
    String str1 = "VIDEO";
    String str2 = "VOICE";
    Terminal terminalTo = searchTerminal(toKey);
    checkTerminalException(toKey);

    if (str1.equals(type)) {
       VideoCommunication interactiveCommunication = new VideoCommunication(from, terminalTo);
        from._onGoingCommunicationFrom = interactiveCommunication;
        from._onGoingCommunication = interactiveCommunication;
        terminalTo._onGoingCommunication = interactiveCommunication;
        from.addMadeCommunications(interactiveCommunication);
        terminalTo.addReceivedCommunications(interactiveCommunication);
        _communications.add(interactiveCommunication);
        evaluateUpgrade(from.getID());
    } else if (str2.equals(type)) {
       VoiceCommunication interactiveCommunication = new VoiceCommunication(from, terminalTo);
        from._onGoingCommunicationFrom = interactiveCommunication;
        from._onGoingCommunication = interactiveCommunication;
        terminalTo._onGoingCommunication = interactiveCommunication;
        _communications.add(interactiveCommunication);
        from.addMadeCommunications(interactiveCommunication);
        terminalTo.addReceivedCommunications(interactiveCommunication);
        evaluateUpgrade(from.getID());
    }
  }

  /**
   * This method will create a failed Communication
   * @param from
   * @param to
   */
  public void addFailedCommunication(Terminal from, Terminal to){
    FailedCommunication _failed = new FailedCommunication(from, to);
    to.addFailedCommunication(_failed);
  }

  /**
   * This method will check for Terminal Key Exception
   * @param toKey
   * @throws UnknownTerminalKeyException
   */
  private void checkTerminalException(String toKey) throws UnknownTerminalKeyException {
    Terminal t = searchTerminal(toKey);
    if(t == null){
      throw new UnknownTerminalKeyException(toKey);
    }
  }


  /**
   * This method will end the ongoing communication of the respective terminal (if there is any)
   *
   * @param from
   * @param duration
   */
  public void endOnGoingCommunication(Terminal from, int duration) {
    if (from.canEndCurrentCommunication()) {
      Terminal terminalTo = from.getOnGoing().getTo();
      from.endOnGoingCommunication(duration);
      if(from.getOwner().getReceiveNotifications()){
        Notification n = new Notification(NotificationType.B2I, terminalTo);
        for(Client c: terminalTo.getFailedCommsClients()){
          c.addNotification(n);
        }
      }
    }
  }

  /**
   * This method allows us to get the list that contains all terminals
   *
   * @return _terminals
   */
  public List<Terminal> getTerminals() {
    return _terminals;
  }


  /**
   *
   *  |--------------------------|**************************|--------------------------|
   *  |--------------------------|**NOTIFICATIONS METHODS**|--------------------------|
   *  |--------------------------|************************|--------------------------|
   */

  /**
   * This method will return the list that contains all notifications
   *
   * @return _notifications
   */
  public List<Notification> getNotifications() {
    return _notifications;
  }

  /**
   * This method will return a formatted of every notification
   *
   * @param clientID
   * @return strNotifications.toString()
   */
  public String showAllNotifications(String clientID) {
    Client c = searchClient(clientID);
    StringBuilder strNotifications = new StringBuilder();
    int i;
    if (c.getNotifications().size() > 0) {
      for (i = 0; i < c.getNotifications().size() - 1; i++) {
        if (c.getNotificationI(i).getSent() == false){
        strNotifications.append(c.getNotificationI(i).formattedNotificationM());
        }
      }
      if (c.getNotificationI(i).getSent() == false) {
        strNotifications.append(c.getNotificationI(i).formattedNotification());
      }
    }
    c.cleanAllNotifications();
    return strNotifications.toString();
  }

  /**
   * |--------------------------|**************************|--------------------------|
   * |--------------------------|**COMMUNICATION METHODS**|--------------------------|
   * |--------------------------|************************|--------------------------|
   */

  /**
   * This method will return a string that shows every formatted Communication
   * @param communications
   * @return
   */
  public String showCommunications(List<Communication> communications) {
    StringBuilder strCommunications = new StringBuilder();
    for (int i = 0; i < communications.size() - 1; i++) {
      strCommunications.append(communications.get(i).formattedCommunication()).append("\n");
    }
    if (communications.size() > 0) {
      strCommunications.append(communications.get(communications.size() - 1).formattedCommunication());
    }
    return strCommunications.toString();
  }

  /**
   * This method will show the communications of a client
   * @param communications
   * @param clientID
   * @return
   * @throws UnknownClientKeyException
   */
  public String showCommunicationsClient(List<Communication> communications,String clientID) throws UnknownClientKeyException{
    StringBuilder strCommunications = new StringBuilder();
    checkClientKeyExceptions(clientID);
    for (int i = 0; i < communications.size() - 1; i++) {
      strCommunications.append(communications.get(i).formattedCommunication()).append("\n");
    }
    if (communications.size() > 0) {
      strCommunications.append(communications.get(communications.size() - 1).formattedCommunication());
    }
    return strCommunications.toString();
  }

  /**
   * This method will  get the communications made by a client
   * @param client
   * @return
   */
  public List<Communication> getCommunicationsFromClient(Client client){
    return client.getMadeCommunication();
  }

  /**
   * This method will get the communications received by a client
   * @param client
   * @return
   */
  public List<Communication> getCommunicationsToClient(Client client){
    return client.getReceivedCommunications();
  }

  /**
   * This method will make payment
   * @param t
   * @param comId
   */
  public void makePayment(Terminal t, String comId){
    Communication c = searchCommunication(comId);
    long valor = c.getCost();
    t.updatePayments(valor);
    t.updateDebtValue(-valor);
    c.payComm();
    t.getOwner().updateDebts(valor);
    t.getOwner().updateDebts(-valor);
  }

  /**
   * This method will search communications using the communication ID
   * @param commID
   * @return
   */
  public Communication searchCommunication(String commID) {
    int comID = Integer.parseInt(commID);
    for (Communication c : _communications) {
      if (comID == c.getID()) {
        return c;
      }
    }
    return null;
  }

  /**
   * This method will return a list that contains every communication
   * @return _communications
   */
  public List<Communication> getCommunications() {
    return _communications;
  }

}


