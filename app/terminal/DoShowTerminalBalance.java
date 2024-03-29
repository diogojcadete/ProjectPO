package prr.app.terminal;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
/**
 * Show balance.
 */
class DoShowTerminalBalance extends TerminalCommand {
  private final Terminal _terminal;
  DoShowTerminalBalance(Network context, Terminal terminal) {
    super(Label.SHOW_BALANCE, context, terminal);
    _terminal = terminal;
  }
  @Override
  protected final void execute() throws CommandException {
    _display.addLine(Message.terminalPaymentsAndDebts(_terminal.getID(), _terminal.getPayments(), _terminal.getDebt()));
    _display.display();
  }
}
