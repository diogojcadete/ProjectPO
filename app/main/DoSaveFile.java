package prr.app.main;

import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.io.IOException;
//FIXME add more imports if needed

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);

  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.save();
    } catch (MissingFileAssociationException e) {
      String filename = Form.requestString(Message.newSaveAs());
      try {
        _receiver.saveAs(filename);
      } catch (IOException | MissingFileAssociationException exc) {
        exc.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

