package com.sun.xml.internal.stream.buffer.sax;

import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class DefaultWithLexicalHandler extends DefaultHandler implements LexicalHandler {
  public void comment(char[] ch, int start, int length) throws SAXException {}
  
  public void startDTD(String name, String publicId, String systemId) throws SAXException {}
  
  public void endDTD() throws SAXException {}
  
  public void startEntity(String name) throws SAXException {}
  
  public void endEntity(String name) throws SAXException {}
  
  public void startCDATA() throws SAXException {}
  
  public void endCDATA() throws SAXException {}
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/buffer/sax/DefaultWithLexicalHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */