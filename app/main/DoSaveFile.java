package prr.app.main;

import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import java.io.IOException;
//FIXME add more imports if needed

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);
    addStringField("fileName", Message.newSaveAs());
  }
  
  @Override
  protected final void execute() {
    try {
      _receiver.save();
    } catch (MissingFileAssociationException e) {
      try {
        String filename = Form.requestString(Message.newSaveAs());
        _receiver.saveAs(filename);
      } catch (IOException eS) {
        eS.printStackTrace();
      } catch (MissingFileAssociationException ex) {
        throw new RuntimeException(ex);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

