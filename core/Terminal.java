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
  // FIXME define contructor(s)
  // FIXME define methods

  private String _id;
  private String _mode;
  private double _debt;
  private double _payments;
  private Client _owner;
  private TerminalMode _mode1;//?
  private List<Terminal> _friends;
  private Client _toNotify;
  private List<Communication> _madeCommunications;
  private List<Communication> _receivedCommunications;
  private InteractiveCommunication _onGoingCommunication;


  public Terminal(String _id, String _mode, double _debt, double _payments, Client _owner, TerminalMode _mode1) {
    this._id = _id;
    this._mode = _mode;
    this._debt = _debt;
    this._payments = _payments;
    this._owner = _owner;
    this._mode1 = _mode1;
    this._friends = new ArrayList<>();
  }

  public void addFriend(Terminal friend){
    if(friend.get_id() != this._id){
      _friends.add(friend);
    }
  }
  public void makeSMS(Terminal to, String message){

  }

  protected void acceptSMS(Terminal from){

  }

  public void makeVoiceCall(Terminal to){

  }

  protected void acceptVoiceCall(Terminal to){

  }

  public void makeVideoCall(Terminal to){

  }

  protected void acceptVideoCall(Terminal to){

  }

  public void endOnGoingCommunication(int size){

  }

  public boolean setOnIdle(){

  }

  public boolean setOnSilent(){

  }

  public boolean turnOff(){

  }

  public String get_id() {
    return _id;
  }

  public String get_mode() {
    return _mode;
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

  public TerminalMode get_mode1() {
    return _mode1;
  }

  public List<Terminal> get_friends() {
    return _friends;
  }

  public Client get_toNotify() {
    return _toNotify;
  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    // FIXME add implementation code
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
