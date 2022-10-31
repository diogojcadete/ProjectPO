package prr.core.exception;

import pt.tecnico.uilib.menus.CommandException;

/**
 * 
 */
public class UnavailableFileException extends CommandException {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  /** The requested filename. */
  String _filename;
  
  /**
   * @param filename 
   */
  public UnavailableFileException(String filename) {
    super("Erro a processar ficheiro " + filename);
    _filename = filename;
  }
  
  /**
   * @return the requested filename
   */
  public String getFilename() {
    return _filename;
  }
}
