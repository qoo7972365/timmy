package com.sun.org.apache.xml.internal.serializer;

import org.xml.sax.Attributes;

public interface SerializerTrace {
  public static final int EVENTTYPE_STARTDOCUMENT = 1;
  
  public static final int EVENTTYPE_ENDDOCUMENT = 2;
  
  public static final int EVENTTYPE_STARTELEMENT = 3;
  
  public static final int EVENTTYPE_ENDELEMENT = 4;
  
  public static final int EVENTTYPE_CHARACTERS = 5;
  
  public static final int EVENTTYPE_IGNORABLEWHITESPACE = 6;
  
  public static final int EVENTTYPE_PI = 7;
  
  public static final int EVENTTYPE_COMMENT = 8;
  
  public static final int EVENTTYPE_ENTITYREF = 9;
  
  public static final int EVENTTYPE_CDATA = 10;
  
  public static final int EVENTTYPE_OUTPUT_PSEUDO_CHARACTERS = 11;
  
  public static final int EVENTTYPE_OUTPUT_CHARACTERS = 12;
  
  boolean hasTraceListeners();
  
  void fireGenerateEvent(int paramInt);
  
  void fireGenerateEvent(int paramInt, String paramString, Attributes paramAttributes);
  
  void fireGenerateEvent(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3);
  
  void fireGenerateEvent(int paramInt, String paramString1, String paramString2);
  
  void fireGenerateEvent(int paramInt, String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serializer/SerializerTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */