package com.sun.xml.internal.bind.unmarshaller;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public interface InfosetScanner<XmlNode> {
  void scan(XmlNode paramXmlNode) throws SAXException;
  
  void setContentHandler(ContentHandler paramContentHandler);
  
  ContentHandler getContentHandler();
  
  XmlNode getCurrentElement();
  
  LocatorEx getLocator();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/unmarshaller/InfosetScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */