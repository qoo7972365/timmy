package jdk.internal.util.xml;

public interface XMLStreamWriter {
  public static final String DEFAULT_XML_VERSION = "1.0";
  
  public static final String DEFAULT_ENCODING = "UTF-8";
  
  void writeStartElement(String paramString) throws XMLStreamException;
  
  void writeEmptyElement(String paramString) throws XMLStreamException;
  
  void writeEndElement() throws XMLStreamException;
  
  void writeEndDocument() throws XMLStreamException;
  
  void close() throws XMLStreamException;
  
  void flush() throws XMLStreamException;
  
  void writeAttribute(String paramString1, String paramString2) throws XMLStreamException;
  
  void writeCData(String paramString) throws XMLStreamException;
  
  void writeDTD(String paramString) throws XMLStreamException;
  
  void writeStartDocument() throws XMLStreamException;
  
  void writeStartDocument(String paramString) throws XMLStreamException;
  
  void writeStartDocument(String paramString1, String paramString2) throws XMLStreamException;
  
  void writeCharacters(String paramString) throws XMLStreamException;
  
  void writeCharacters(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws XMLStreamException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/util/xml/XMLStreamWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */