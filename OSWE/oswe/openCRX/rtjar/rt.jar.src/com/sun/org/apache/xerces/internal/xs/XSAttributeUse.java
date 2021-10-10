package com.sun.org.apache.xerces.internal.xs;

public interface XSAttributeUse extends XSObject {
  boolean getRequired();
  
  XSAttributeDeclaration getAttrDeclaration();
  
  short getConstraintType();
  
  String getConstraintValue();
  
  Object getActualVC() throws XSException;
  
  short getActualVCType() throws XSException;
  
  ShortList getItemValueTypes() throws XSException;
  
  XSObjectList getAnnotations();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/XSAttributeUse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */