package com.sun.org.apache.xerces.internal.xni.parser;

import com.sun.org.apache.xerces.internal.xni.XNIException;

public interface XMLErrorHandler {
  void warning(String paramString1, String paramString2, XMLParseException paramXMLParseException) throws XNIException;
  
  void error(String paramString1, String paramString2, XMLParseException paramXMLParseException) throws XNIException;
  
  void fatalError(String paramString1, String paramString2, XMLParseException paramXMLParseException) throws XNIException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xni/parser/XMLErrorHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */