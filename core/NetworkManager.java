package prr.core;
import java.io.*;

import prr.core.exception.ImportFileException;
import prr.core.exception.MissingFileAssociationException;
import prr.core.exception.UnavailableFileException;
import prr.core.exception.UnrecognizedEntryException;

//FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

  /** The network itself. */
  private String _filename = "";
  private Network _network = new Network();

  private boolean check = true;
  
  public Network getNetwork() {
    return _network;
  }
  
  /**
   * @param filename name of the file containing the serialized application's state
   *        to load.
   * @throws UnavailableFileException if the specified file does not exist or there is
   *         an error while processing this file.
   */
  public void load(String filename) throws UnavailableFileException, UnrecognizedEntryException, IOException, ClassNotFoundException {
    try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename))){
      _network = (Network) input.readObject();
      _filename = filename;
      check = true;
    }
  }
  
  /**
   * Saves the serialized application's state into the file associated to the current network.
   *
   * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   */
  public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
    if(_filename == ""){
      throw new MissingFileAssociationException();
    }
    if(check){
      try{
        ObjectOutputStream out = new ObjectOutputStream((new FileOutputStream(_filename)));
        out.writeObject(_network);
        out.close();
        check = false;
      }catch(FileNotFoundException e){
        throw new FileNotFoundException();
      }
    }


  }

  private void writeFile(String fileName){
    try (var fos = new FileOutputStream(fileName)){
      var bos = new ByteArrayOutputStream();
      var objectOutputStream = new ObjectOutputStream(bos);
      objectOutputStream.writeObject(_network);
      fos.write(bos.toByteArray());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  /**
   * Saves the serialized application's state into the specified file. The current network is
   * associated to this file.
   *
   * @param filename the name of the file.
   * @throws FileNotFoundException if for some reason the file cannot be created or opened.
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   */
  public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
    writeFile(filename);
  }
  
  /**
   * Read text input file and create domain entities..
   * 
   * @param filename name of the text input file
   * @throws ImportFileException
   */
  public void importFile(String filename) throws ImportFileException {
    try {
      _network.importFile(filename);
    } catch (IOException | UnrecognizedEntryException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(filename, e);
    }
  }  
}
