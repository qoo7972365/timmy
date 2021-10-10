package com.sun.org.apache.xml.internal.serialize;

import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

public interface DOMSerializer {
  void serialize(Element paramElement) throws IOException;
  
  void serialize(Document paramDocument) throws IOException;
  
  void serialize(DocumentFragment paramDocumentFragment) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serialize/DOMSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */