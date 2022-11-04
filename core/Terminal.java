package prr.core;

import prr.core.comparator.ClientComparator;
import prr.core.comparator.TerminalComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */ {

  /**
   * Serial number for serialization.
   */
  private static final long serialVersionUID = 202208091753L;

  // FIXME define attributes
  // FIXME define methods

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

  public void addFriend(Terminal friend) {
      _friends.add(friend);
    Collections.sort(_friends,new TerminalComparator());
  }

  public void removeFriend(Terminal enemy){
    _friends.remove(enemy);
  }

  public TextCommunication makeSMS(Terminal to, String message) {
    TextCommunication c1 = new TextCommunication(this, to, message);
  //  _madeCommunications.add(c1);
    return c1;
  }

  public void makeVoiceCall(Terminal to) {
    Communication c1 = new VoiceCommunication(this, to);
    _madeCommunications.add(c1);
  }

  protected void acceptVoiceCall(Terminal to) {

  }

  public void endOnGoingCommunication(int size) {
    _onGoingCommunication.endOnGoing(size);
    _onGoingCommunication = null;
  }

  public void setOnGoing(VoiceCommunication c){
    _onGoingCommunication = c;
  }
  public void setOnGoing(VideoCommunication c){
    _onGoingCommunication = c;
  }

  public void setOn() {
    TerminalMode previousMode = this.getMode();
    _mode = TerminalMode.IDLE;
    if(previousMode.equals(TerminalMode.OFF)) {
      Notification n = new Notification(NotificationType.O2I, this);
      for (Client c : this.getFailedCommsClients()) {
        if (c.getReceiveNotifications()) {
          c.addNotification(n);
          this._failedCommClient.remove(c);
          for(FailedCommunication f: _failedCommunications){
           if()
          }
          this._failedCommunications.remove(c)
        }
      }
      this.getFailedCommsClients().clear();
    }
    else if(previousMode.equals(TerminalMode.SILENCE)){
      Notification n = new Notification(NotificationType.S2I, this);
        for(Client c: this.getFailedCommsClients()){
          if(c.getReceiveNotifications()){
            c.addNotification(n);
            this._failedCommClient.remove(c);
          }
          this._failedCommunications.clear();
          this.getFailedCommsClients().clear();
      }
    }
  }

  public void setOnSilent() {
    _mode = TerminalMode.SILENCE;
    Notification n = new Notification(NotificationType.O2S, this);
    for(Client c: this.getFailedCommsClients()){
      if(c.getReceiveNotifications()){
        c.addNotification(n);
      }
    }
  }

  public void turnOff() {
    this._mode = TerminalMode.OFF;
  }

  public String getID() {
    return _id;
  }

  public String getType() {
    return _type;
  }

  public long getDebt() {
    return _debt;
  }

  public long getPayments() {
    return _payments;
  }

  public Client getOwner() {
    return _owner;
  }

  public Communication getOnGoing(){
    return _onGoingCommunication;
  }

  public TerminalMode getMode() {
    return _mode;
  }

  public List<Terminal> getFriends() {
    return _friends;
  }

  public List<Communication> getMadeCommunications(){
    return _madeCommunications;
  }

  public Client getToNotify() {
    return _toNotify;
  }

  public void updateDebtValue(double n) {
    this._debt += n;
  }

  public void setBusy(){
    _mode = TerminalMode.BUSY;
  }

  public void addMadeCommunications(Communication communication) {
    _madeCommunications.add(communication);
    _owner.addMadeCommunication(communication);
  }

  public void addReceivedCommunications(Communication communication) {
    _receivedCommunications.add(communication);
    _owner.addReceivedCommunication(communication);
  }

  public boolean checkFriends(Terminal friendRequest) {
    return this._friends.contains(friendRequest);
  }

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

  public void addFailedCommunication(FailedCommunication f){
     _failedCommunications.add(f);
  }
  public List<Client> getFailedCommsClients(){
    for(FailedCommunication f: _failedCommunications){
      _failedCommClient.add(f.getTerminalAttempt()._owner);
    }
    return  _failedCommClient;
  }


  public void setOnGoingFrom(VoiceCommunication c){
    _onGoingCommunicationFrom = c;
  }
  public void setOnGoingFrom(VideoCommunication c){
    _onGoingCommunicationFrom = c;
  }

  public void updatePayments(long val){
    _payments += val;
  }

  public String friendsToString() {
    String strFriends = "";
    int j = _friends.size() - 1;
    for (int i = 0; i < _friends.size() - 1; i++) {
      strFriends += _friends.get(i).getID() + ",";
    }
    strFriends += _friends.get(j).getID();
    return strFriends;
  }

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

  public boolean wasUsed() {
    return (!_madeCommunications.isEmpty() && !_receivedCommunications.isEmpty());
  }

  public void setOff() {
    _mode = TerminalMode.OFF;
  }
}


