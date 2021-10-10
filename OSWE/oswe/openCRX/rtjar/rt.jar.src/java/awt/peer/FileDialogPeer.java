package java.awt.peer;

import java.io.FilenameFilter;

public interface FileDialogPeer extends DialogPeer {
  void setFile(String paramString);
  
  void setDirectory(String paramString);
  
  void setFilenameFilter(FilenameFilter paramFilenameFilter);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/peer/FileDialogPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */