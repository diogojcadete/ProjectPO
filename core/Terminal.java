package prr.core;

import prr.core.comparator.ClientComparator;
import prr.core.comparator.TerminalComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

  /**
   * Serial number for serialization.
   */
  private static final long serialVersionUID = 202208091753L;


  private String _id;
  private String _type;
  private long _debt;
  private long _payments;
  private Client _owner;
  private Client _toNotify;
  private TerminalMode _mode;
  private List<Terminal> _friends;
  private List<Communication> _madeCommunications;
  private List<Communication> _receivedCommunications;
  protected InteractiveCommunication _onGoingCommunication;
  protected InteractiveCommunication _onGoingCommunicationFrom;

  private List<FailedCommunication> _failedCommunications;
  List<Client> _failedCommClient;

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
   * this method will set the on going communication
   * @param c
   */
  public void setOnGoing(VoiceCommunication c){
    _onGoingCommunication = c;
  }

  /**
   * this method will set the on going communication
   * @param c
   */

  public void setOnGoing(VideoCommunication c){
    _onGoingCommunication = c;
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
   * @param friend
   */
  public void addFriend(Terminal friend) {
      _friends.add(friend);
    Collections.sort(_friends,new TerminalComparator());
  }

  /**
   * This method will remove a friend
   * @param enemy
   */
  public void removeFriend(Terminal enemy){
    _friends.remove(enemy);
  }

  /**
   * This method will make a text communication
   * @param to
   * @param message
   * @return
   */
  public TextCommunication makeSMS(Terminal to, String message) {
    TextCommunication c1 = new TextCommunication(this, to, message);
  //  _madeCommunications.add(c1);
    return c1;
  }

  /**
   * This method will make a voice call
   * @param to
   */
  public void makeVoiceCall(Terminal to) {
    Communication c1 = new VoiceCommunication(this, to);
    _madeCommunications.add(c1);
  }

  /**
   * This method will end the ongoing communication
   * @param size
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
   * @param n
   */
  public void updateDebtValue(double n) {
    this._debt += n;
  }

  /**
   * This method will add the made communications
   * @param communication
   */
  public void addMadeCommunications(Communication communication) {
    _madeCommunications.add(communication);
    _owner.addMadeCommunication(communication);
  }

  /**
   * This method will add the received communications
   * @param communication
   */
  public void addReceivedCommunications(Communication communication) {
    _receivedCommunications.add(communication);
    _owner.addReceivedCommunication(communication);
  }

  /**
   * This method will check if two terminals are friends
   * @param friendRequest
   * @return
   */
  public boolean checkFriends(Terminal friendRequest) {
    return this._friends.contains(friendRequest);
  }

  /**
   * This method will check if two terminals are equals
   * @param t
   * @return
   */
  public boolean equals(Terminal t) {
    return this._id.equals(t._id);
  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   * it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    if (_onGoingCommunicationFrom == null) {
      return false;
    }
    return true;
  }

  /**
   * This method will failed communications
   * @param f
   */
  public void addFailedCommunication(FailedCommunication f){
     _failedCommunications.add(f);
  }

  /**
   * This method will update the payments
   * @param val
   */
  public void updatePayments(long val){
    _payments += val;
  }

  /**
   * This method return a string of friends
   * @return strFriends
   */
  public String friendsToString() {
    String strFriends = "";
    int j = _friends.size() - 1;
    for (int i = 0; i < _friends.size() - 1; i++) {
      strFriends += _friends.get(i).getID() + ",";
    }
    strFriends += _friends.get(j).getID();
    return strFriends;
  }

  /**
   * This method returns a string of formatted terminals
   * @return
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
    if(_mode.equals(TerminalMode.BUSY) || _mode.equals(TerminalMode.OFF)){
      return false;
    }
   return true;
  }

  /**
   * This method will check if the terminal was used
   * @return
   */
  public boolean wasUsed() {
    return (!_madeCommunications.isEmpty() && !_receivedCommunications.isEmpty());
  }

}


