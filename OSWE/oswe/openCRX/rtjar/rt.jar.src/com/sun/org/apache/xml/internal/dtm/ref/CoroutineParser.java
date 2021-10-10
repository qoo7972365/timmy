package com.sun.org.apache.xml.internal.dtm.ref;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;

public interface CoroutineParser {
  int getParserCoroutineID();
  
  CoroutineManager getCoroutineManager();
  
  void setContentHandler(ContentHandler paramContentHandler);
  
  void setLexHandler(LexicalHandler paramLexicalHandler);
  
  Object doParse(InputSource paramInputSource, int paramInt);
  
  Object doMore(boolean paramBoolean, int paramInt);
  
  void doTerminate(int paramInt);
  
  void init(CoroutineManager paramCoroutineManager, int paramInt, XMLReader paramXMLReader);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/dtm/ref/CoroutineParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */