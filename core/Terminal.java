package prr.core;
import prr.core.comparator.TerminalComparator;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

  /**
   * Serial number for serialization.
   */
  @Serial
  private static final long serialVersionUID = 202208091753L;
  private final String _id;
  private final String _type;
  private long _debt;
  private long _payments;
  private final Client _owner;
  private TerminalMode _mode;
  private TerminalMode previousMode;
  protected InteractiveCommunication _onGoingCommunication;
  protected InteractiveCommunication _onGoingCommunicationFrom;
  private List<Terminal> _friends;
  private List<Communication> _madeCommunications;
  private List<Communication> _receivedCommunications;
  private List<FailedCommunication> _failedCommunications;
  private List<Client> _failedCommClient;

  public Terminal(String _id, String _type, Client _owner, TerminalMode _mode) {
    this._id = _id;
    this._type = _type;
    this._owner = _owner;
    this._mode = _mode;
    this._friends = new ArrayList<>();
    this._madeCommunications = new ArrayList<>();
    this._receivedCommunications = new ArrayList<>();
    this._failedCommunications = new ArrayList<>();
    this._failedCommClient = new ArrayList<>();
  }
  /**
   * This method will turn on the terminal and send the notification
   */
  public void setOn() {
    TerminalMode previousMode = this.getMode();
    _mode = TerminalMode.IDLE;
    if (previousMode.equals(TerminalMode.OFF)) {
      Notification n = new Notification(NotificationType.O2I, this);
      for (Client c : this.getFailedCommsClients()) {
        if (c.getReceiveNotifications()) {
          c.addNotification(n);
        }
      }
    } else if (previousMode.equals(TerminalMode.SILENCE)) {
      Notification n = new Notification(NotificationType.S2I, this);
      for (Client c : this.getFailedCommsClients()) {
        if (c.getReceiveNotifications()) {
          c.addNotification(n);
        }
      }
    }
  }

  /**
   * This method sets the previous mode
   * @param mode TerminalMode to change the current mode of the current terminal
   */
  public void setPreviousMode(TerminalMode mode){
    previousMode = mode;
  }

  /**
   * This method will the set the terminal to silence and will send a notification
   */
  public void setOnSilent() {
    _mode = TerminalMode.SILENCE;
    Notification n = new Notification(NotificationType.O2S, this);
    for(Client c: this.getFailedCommsClients()){
      if(c.getReceiveNotifications()){
        c.addNotification(n);
      }
    }
  }

  /**
   * This method will set the terminal to busy
   */
  public void setBusy(){
    _mode = TerminalMode.BUSY;
  }

  /**
   * This method will turn off the terminal
   */
  public void setOff() {
    _mode = TerminalMode.OFF;
  }

  /**
   * This method sets the current mode to the previous mode
   */
  public void setOnPreviousMode(){
    _mode = previousMode;
  }
  /**
   * This method will return the id
   * @return _id
   */
  public String getID() {
    return _id;
  }

  /**
   * This method will return the type
   * @return _type
   */
  public String getType() {
    return _type;
  }

  /**
   * This method will return the debt
   * @return _debt
   */
  public long getDebt() {
    return _debt;
  }

  /**
   * This method will return the payments
   * @return _payments
   */
  public long getPayments() {
    return _payments;
  }

  /**
   * This me
   * @return _owner
   */
  public Client getOwner() {
    return _owner;
  }

  /**
   * This method will return the ongoing communication
   * @return _onGoingCommunication
   */
  public Communication getOnGoing(){
    return _onGoingCommunication;
  }

  /**
   * This method will return the mode of the terminal
   * @return _mode
   */
  public TerminalMode getMode() {
    return _mode;
  }

  /**
   * This method will return the list of friends
   * @return _friends
   */
  public List<Terminal> getFriends() {
    return _friends;
  }

  /**
   * This method will return the list of made communications
   * @return _madeCommunications
   */
  public List<Communication> getMadeCommunications(){
    return _madeCommunications;
  }

  /**
   * This method will return the failed Communications
   * @return _failedCommClient
   */
  public List<Client> getFailedCommsClients(){
    for(FailedCommunication f: _failedCommunications){
      _failedCommClient.add(f.getTerminalAttempt()._owner);
    }
    return  _failedCommClient;
  }

  /**
   * This method will add a friend
   * @param friend Terminal with the friend to add
   */
  public void addFriend(Terminal friend) {
      _friends.add(friend);
    _friends.sort(new TerminalComparator());
  }

  /**
   * This method will remove a friend
   * @param enemy Terminal of the "friend" to remove
   */
  public void removeFriend(Terminal enemy){
    _friends.remove(enemy);
  }
  /**
   * This method will make a text communication
   * @param to      Terminal witch we will send the message
   * @param message String with the message
   * @return TextCommunication
   */
  public TextCommunication makeSMS(Terminal to, String message) {
    return new TextCommunication(this, to, message);
  }

  /**
   * This method will end the ongoing communication
   * @param size  int with the time of the communication
   */
  public void endOnGoingCommunication(int size) {
    _onGoingCommunication.endOnGoing(size);
    _onGoingCommunication = null;
  }

  /**
   * This method will turn off the terminal
   */

  public void turnOff() {
    this._mode = TerminalMode.OFF;
  }

  /**
   * This method will update the debt value
   * @param n long with a new debt value
   */
  public void updateDebtValue(long n) {
    this._debt += n;
  }

  /**
   * This method will add the made communications
   * @param communication Communication type to add
   */
  public void addMadeCommunications(Communication communication) {
    _madeCommunications.add(communication);
    _owner.addMadeCommunication(communication);
  }

  /**
   * This method will add the received communications
   * @param communication Communication type to add
   */
  public void addReceivedCommunications(Communication communication) {
    _receivedCommunications.add(communication);
    _owner.addReceivedCommunication(communication);
  }

  /**
   * This method will check if two terminals are friends
   * @param friendRequest Terminal with a possible friend to check
   * @return boolean with true if friends or false if not
   */
  public boolean checkFriends(Terminal friendRequest) {
    return this._friends.contains(friendRequest);
  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   * it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    return _onGoingCommunicationFrom != null;
  }

  /**
   *
   * @param f FailedCommunication to add
   */
  public void addFailedCommunication(FailedCommunication f){
     _failedCommunications.add(f);
  }

  /**
   * This method will update the payments
   */
  public void updatePayments(long val){
    _payments += val;
  }

  /**
   * This method return a string of friends
   * @return strFriends
   */
  public String friendsToString() {
    StringBuilder strFriends = new StringBuilder();
    int j = _friends.size() - 1;
    for (int i = 0; i < _friends.size() - 1; i++) {
      strFriends.append(_friends.get(i).getID()).append(",");
    }
    strFriends.append(_friends.get(j).getID());
    return strFriends.toString();
  }

  /**
   * This method returns a string of formatted terminals
   * @return String
   */
  public String formattedTerminal() {
    if (_friends.size() != 0) {
      return "" + _type + "|" + _id + "|" + _owner.getKey() + "|" + _mode.name() + "|" + _payments + "|" + _debt + "|" + this.friendsToString();
    } else {
      return "" + _type + "|" + _id + "|" + _owner.getKey() + "|" + _mode.name() + "|" + _payments + "|" + _debt;
    }
  }

  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    return !_mode.equals(TerminalMode.BUSY) && !_mode.equals(TerminalMode.OFF);
  }

  /**
   * This method will check if the terminal was used
   * @return boolean
   */
  public boolean wasUsed() {
    return (!_madeCommunications.isEmpty() && !_receivedCommunications.isEmpty());
  }

}


