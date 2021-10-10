package com.sun.org.apache.xerces.internal.xs;

public interface XSNotationDeclaration extends XSObject {
  String getSystemId();
  
  String getPublicId();
  
  XSAnnotation getAnnotation();
  
  XSObjectList getAnnotations();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/XSNotationDeclaration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */