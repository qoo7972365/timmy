package com.sun.org.apache.xerces.internal.xs;

public interface XSAttributeGroupDefinition extends XSObject {
  XSObjectList getAttributeUses();
  
  XSWildcard getAttributeWildcard();
  
  XSAnnotation getAnnotation();
  
  XSObjectList getAnnotations();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/XSAttributeGroupDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */