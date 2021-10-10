package com.sun.org.apache.xerces.internal.xs;

public interface XSModelGroup extends XSTerm {
  public static final short COMPOSITOR_SEQUENCE = 1;
  
  public static final short COMPOSITOR_CHOICE = 2;
  
  public static final short COMPOSITOR_ALL = 3;
  
  short getCompositor();
  
  XSObjectList getParticles();
  
  XSAnnotation getAnnotation();
  
  XSObjectList getAnnotations();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/XSModelGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */