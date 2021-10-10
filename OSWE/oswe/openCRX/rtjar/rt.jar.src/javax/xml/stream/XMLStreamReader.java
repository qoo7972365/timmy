package javax.xml.stream;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

public interface XMLStreamReader extends XMLStreamConstants {
  Object getProperty(String paramString) throws IllegalArgumentException;
  
  int next() throws XMLStreamException;
  
  void require(int paramInt, String paramString1, String paramString2) throws XMLStreamException;
  
  String getElementText() throws XMLStreamException;
  
  int nextTag() throws XMLStreamException;
  
  boolean hasNext() throws XMLStreamException;
  
  void close() throws XMLStreamException;
  
  String getNamespaceURI(String paramString);
  
  boolean isStartElement();
  
  boolean isEndElement();
  
  boolean isCharacters();
  
  boolean isWhiteSpace();
  
  String getAttributeValue(String paramString1, String paramString2);
  
  int getAttributeCount();
  
  QName getAttributeName(int paramInt);
  
  String getAttributeNamespace(int paramInt);
  
  String getAttributeLocalName(int paramInt);
  
  String getAttributePrefix(int paramInt);
  
  String getAttributeType(int paramInt);
  
  String getAttributeValue(int paramInt);
  
  boolean isAttributeSpecified(int paramInt);
  
  int getNamespaceCount();
  
  String getNamespacePrefix(int paramInt);
  
  String getNamespaceURI(int paramInt);
  
  NamespaceContext getNamespaceContext();
  
  int getEventType();
  
  String getText();
  
  char[] getTextCharacters();
  
  int getTextCharacters(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3) throws XMLStreamException;
  
  int getTextStart();
  
  int getTextLength();
  
  String getEncoding();
  
  boolean hasText();
  
  Location getLocation();
  
  QName getName();
  
  String getLocalName();
  
  boolean hasName();
  
  String getNamespaceURI();
  
  String getPrefix();
  
  String getVersion();
  
  boolean isStandalone();
  
  boolean standaloneSet();
  
  String getCharacterEncodingScheme();
  
  String getPITarget();
  
  String getPIData();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/stream/XMLStreamReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */