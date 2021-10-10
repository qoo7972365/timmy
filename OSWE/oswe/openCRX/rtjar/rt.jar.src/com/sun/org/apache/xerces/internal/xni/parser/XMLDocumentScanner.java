package com.sun.org.apache.xerces.internal.xni.parser;

import com.sun.org.apache.xerces.internal.xni.XNIException;
import java.io.IOException;

public interface XMLDocumentScanner extends XMLDocumentSource {
  void setInputSource(XMLInputSource paramXMLInputSource) throws IOException;
  
  boolean scanDocument(boolean paramBoolean) throws IOException, XNIException;
  
  int next() throws XNIException, IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xni/parser/XMLDocumentScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */