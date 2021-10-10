package com.sun.org.apache.xalan.internal.xsltc;

import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

public interface Translet {
  void transform(DOM paramDOM, SerializationHandler paramSerializationHandler) throws TransletException;
  
  void transform(DOM paramDOM, SerializationHandler[] paramArrayOfSerializationHandler) throws TransletException;
  
  void transform(DOM paramDOM, DTMAxisIterator paramDTMAxisIterator, SerializationHandler paramSerializationHandler) throws TransletException;
  
  Object addParameter(String paramString, Object paramObject);
  
  void buildKeys(DOM paramDOM, DTMAxisIterator paramDTMAxisIterator, SerializationHandler paramSerializationHandler, int paramInt) throws TransletException;
  
  void addAuxiliaryClass(Class paramClass);
  
  Class getAuxiliaryClass(String paramString);
  
  String[] getNamesArray();
  
  String[] getUrisArray();
  
  int[] getTypesArray();
  
  String[] getNamespaceArray();
  
  boolean overrideDefaultParser();
  
  void setOverrideDefaultParser(boolean paramBoolean);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/Translet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */