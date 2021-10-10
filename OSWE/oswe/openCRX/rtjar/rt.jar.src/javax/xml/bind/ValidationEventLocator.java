package javax.xml.bind;

import java.net.URL;
import org.w3c.dom.Node;

public interface ValidationEventLocator {
  URL getURL();
  
  int getOffset();
  
  int getLineNumber();
  
  int getColumnNumber();
  
  Object getObject();
  
  Node getNode();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/ValidationEventLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */