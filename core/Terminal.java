package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  // FIXME define methods

  private String _id;
  private String _type;
  private double _debt;
  private double _payments;
  private Client _owner;
  private Client _toNotify;
  private TerminalMode _mode;
  private List<Terminal> _friends;
  private List<Communication> _madeCommunications;
  private List<Communication> _receivedCommunications;
  private InteractiveCommunication _onGoingCommunication;


  public Terminal(String _id, String _type, Client _owner, TerminalMode _mode) {
    this._id = _id;
    this._owner = _owner;
    this._mode = _mode;
    this._friends = new ArrayList<>();
    this._madeCommunications = new ArrayList<>();
    this._receivedCommunications = new ArrayList<>();
  }

  public void addFriend(Terminal friend){
    if(!(friend.get_id().equals(this._id))){
      _friends.add(friend);
    }
  }
  public void makeSMS(Terminal to, String message){
    Communication c1 = new TextCommunication(this, to, message);
    _madeCommunications.add(c1);
  }
  public void makeVoiceCall(Terminal to){
    Communication c1 = new VoiceCommunication(this, to);
    _madeCommunications.add(c1);
  }
  protected void acceptVoiceCall(Terminal to){

  }
  public void endOnGoingCommunication(int size){
   _onGoingCommunication._isOnGoing = false;
   _onGoingCommunication = null;
  }
  public boolean setOnIdle(){
    _mode = TerminalMode.BUSY;
  }
  public boolean setOnSilent(){
    _mode = TerminalMode.SILENCE;
  }
  public void turnOff(){
    this._mode = TerminalMode.OFF;
  }

  public String getID() {
    return _id;
  }

  public String get_type() {
    return _type;
  }

  public double get_debt() {
    return _debt;
  }

  public double get_payments() {
    return _payments;
  }

  public Client get_owner() {
    return _owner;
  }

  public TerminalMode get_mode() {
    return _mode;
  }

  public List<Terminal> get_friends() {
    return _friends;
  }

  public Client get_toNotify() {
    return _toNotify;
  }

  public void updateDebtValue(double n){
    this._debt += n;
  }
  public void addMadeCommunications(Communication communication){
    _madeCommunications.add(communication);
  }
  public void addReceivedCommunications(Communication communication){
    _receivedCommunications.add(communication);
  }

  public boolean checkFriends(Terminal friendRequest){
    return this._friends.contains(friendRequest);
  }

  public boolean equals(Terminal t){
    return this._id.equals(t._id);
  }
  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    if(_onGoingCommunication == null){
      return false;
    }
    return true;
  }

  public String friendsToString(){
    String strFriends = "";
    int j = _friends.size()-1;
    for(int i = 0; i < _friends.size()-1; i++){
      strFriends += _friends.get(i).getID() + ",";
    }
    strFriends += _friends.get(j).getID();
  }
  public String formattedTerminal(){
    return "" + _type + "|" + _id + "|" + _owner.get_key() + "|" + _mode.name() + "|" + _payments + "|" + _debt + "|" + this.friendsToString();
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    // FIXME add implementation code
  }
}
