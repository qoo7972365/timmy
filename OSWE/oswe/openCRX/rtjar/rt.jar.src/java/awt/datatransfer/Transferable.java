package java.awt.datatransfer;

import java.io.IOException;

public interface Transferable {
  DataFlavor[] getTransferDataFlavors();
  
  boolean isDataFlavorSupported(DataFlavor paramDataFlavor);
  
  Object getTransferData(DataFlavor paramDataFlavor) throws UnsupportedFlavorException, IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/datatransfer/Transferable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */