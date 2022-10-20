package prr.app.main;

import prr.core.NetworkManager;
import prr.app.exception.FileOpenFailedException;
import prr.core.exception.ImportFileException;
import prr.core.exception.UnavailableFileException;
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
          _receiver.load(Message.openFile());
           String filename = Form.requestString(Message.openFile());
           _receiver.importFile(filename);
      } catch (UnavailableFileException e) {
        throw new FileOpenFailedException(e);
      } catch (ImportFileException e){
          throw new FileOpenFailedException(e);
      }


  }
}
