package com.sun.xml.internal.org.jvnet.fastinfoset.stax;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;

public interface LowLevelFastInfosetStreamWriter {
  void initiateLowLevelWriting() throws XMLStreamException;
  
  int getNextElementIndex();
  
  int getNextAttributeIndex();
  
  int getLocalNameIndex();
  
  int getNextLocalNameIndex();
  
  void writeLowLevelTerminationAndMark() throws IOException;
  
  void writeLowLevelStartElementIndexed(int paramInt1, int paramInt2) throws IOException;
  
  boolean writeLowLevelStartElement(int paramInt, String paramString1, String paramString2, String paramString3) throws IOException;
  
  void writeLowLevelStartNamespaces() throws IOException;
  
  void writeLowLevelNamespace(String paramString1, String paramString2) throws IOException;
  
  void writeLowLevelEndNamespaces() throws IOException;
  
  void writeLowLevelStartAttributes() throws IOException;
  
  void writeLowLevelAttributeIndexed(int paramInt) throws IOException;
  
  boolean writeLowLevelAttribute(String paramString1, String paramString2, String paramString3) throws IOException;
  
  void writeLowLevelAttributeValue(String paramString) throws IOException;
  
  void writeLowLevelStartNameLiteral(int paramInt, String paramString1, byte[] paramArrayOfbyte, String paramString2) throws IOException;
  
  void writeLowLevelStartNameLiteral(int paramInt1, String paramString1, int paramInt2, String paramString2) throws IOException;
  
  void writeLowLevelEndStartElement() throws IOException;
  
  void writeLowLevelEndElement() throws IOException;
  
  void writeLowLevelText(char[] paramArrayOfchar, int paramInt) throws IOException;
  
  void writeLowLevelText(String paramString) throws IOException;
  
  void writeLowLevelOctets(byte[] paramArrayOfbyte, int paramInt) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/fastinfoset/stax/LowLevelFastInfosetStreamWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */