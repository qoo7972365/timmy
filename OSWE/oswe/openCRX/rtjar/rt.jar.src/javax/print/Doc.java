package javax.print;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import javax.print.attribute.DocAttributeSet;

public interface Doc {
  DocFlavor getDocFlavor();
  
  Object getPrintData() throws IOException;
  
  DocAttributeSet getAttributes();
  
  Reader getReaderForText() throws IOException;
  
  InputStream getStreamForBytes() throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/Doc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */