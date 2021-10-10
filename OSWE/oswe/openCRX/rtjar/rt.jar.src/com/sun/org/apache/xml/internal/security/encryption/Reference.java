package com.sun.org.apache.xml.internal.security.encryption;

import java.util.Iterator;
import org.w3c.dom.Element;

public interface Reference {
  String getType();
  
  String getURI();
  
  void setURI(String paramString);
  
  Iterator<Element> getElementRetrievalInformation();
  
  void addElementRetrievalInformation(Element paramElement);
  
  void removeElementRetrievalInformation(Element paramElement);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/encryption/Reference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */