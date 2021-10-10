package com.sun.org.apache.xerces.internal.xs;

public interface XSComplexTypeDefinition extends XSTypeDefinition {
  public static final short CONTENTTYPE_EMPTY = 0;
  
  public static final short CONTENTTYPE_SIMPLE = 1;
  
  public static final short CONTENTTYPE_ELEMENT = 2;
  
  public static final short CONTENTTYPE_MIXED = 3;
  
  short getDerivationMethod();
  
  boolean getAbstract();
  
  XSObjectList getAttributeUses();
  
  XSWildcard getAttributeWildcard();
  
  short getContentType();
  
  XSSimpleTypeDefinition getSimpleType();
  
  XSParticle getParticle();
  
  boolean isProhibitedSubstitution(short paramShort);
  
  short getProhibitedSubstitutions();
  
  XSObjectList getAnnotations();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/XSComplexTypeDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */