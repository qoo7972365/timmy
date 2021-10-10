package com.sun.org.apache.xerces.internal.xs;

public interface XSTypeDefinition extends XSObject {
  public static final short COMPLEX_TYPE = 15;
  
  public static final short SIMPLE_TYPE = 16;
  
  short getTypeCategory();
  
  XSTypeDefinition getBaseType();
  
  boolean isFinal(short paramShort);
  
  short getFinal();
  
  boolean getAnonymous();
  
  boolean derivedFromType(XSTypeDefinition paramXSTypeDefinition, short paramShort);
  
  boolean derivedFrom(String paramString1, String paramString2, short paramShort);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/XSTypeDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */