package com.sun.org.apache.xerces.internal.xs;

public interface XSWildcard extends XSTerm {
  public static final short NSCONSTRAINT_ANY = 1;
  
  public static final short NSCONSTRAINT_NOT = 2;
  
  public static final short NSCONSTRAINT_LIST = 3;
  
  public static final short PC_STRICT = 1;
  
  public static final short PC_SKIP = 2;
  
  public static final short PC_LAX = 3;
  
  short getConstraintType();
  
  StringList getNsConstraintList();
  
  short getProcessContents();
  
  XSAnnotation getAnnotation();
  
  XSObjectList getAnnotations();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/XSWildcard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */