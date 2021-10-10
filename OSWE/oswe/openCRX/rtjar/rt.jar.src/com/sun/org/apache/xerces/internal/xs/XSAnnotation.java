package com.sun.org.apache.xerces.internal.xs;

public interface XSAnnotation extends XSObject {
  public static final short W3C_DOM_ELEMENT = 1;
  
  public static final short SAX_CONTENTHANDLER = 2;
  
  public static final short W3C_DOM_DOCUMENT = 3;
  
  boolean writeAnnotation(Object paramObject, short paramShort);
  
  String getAnnotationString();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/XSAnnotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */