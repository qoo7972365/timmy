package com.sun.org.apache.xerces.internal.xs;

public interface XSFacet extends XSObject {
  short getFacetKind();
  
  String getLexicalFacetValue();
  
  boolean getFixed();
  
  XSAnnotation getAnnotation();
  
  XSObjectList getAnnotations();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/XSFacet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */