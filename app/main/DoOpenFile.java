package prr.app.main;

import prr.core.NetworkManager;
import prr.app.exception.FileOpenFailedException;
import prr.core.exception.ImportFileException;
import prr.core.exception.UnavailableFileException;
import prr.core.exception.UnrecognizedEntryException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.io.IOException;
//Add more imports if needed

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

  DoOpenFile(NetworkManager receiver) {
    super(Label.OPEN_FILE, receiver);
    addStringField("fileName", Message.openFile());

  }
  
  @Override
  protected final void execute() throws CommandException {
      try {
           String filename = Form.requestString(Message.openFile());
           _receiver.importFile(filename);
      } catch (ImportFileException e) {
          throw new FileOpenFailedException(e);
      }


  }
}
