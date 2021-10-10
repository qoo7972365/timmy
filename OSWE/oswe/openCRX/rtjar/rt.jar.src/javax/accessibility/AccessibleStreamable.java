package javax.accessibility;

import java.awt.datatransfer.DataFlavor;
import java.io.InputStream;

public interface AccessibleStreamable {
  DataFlavor[] getMimeTypes();
  
  InputStream getStream(DataFlavor paramDataFlavor);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/accessibility/AccessibleStreamable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */