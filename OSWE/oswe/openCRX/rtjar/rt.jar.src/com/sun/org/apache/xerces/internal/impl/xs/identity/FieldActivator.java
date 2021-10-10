package com.sun.org.apache.xerces.internal.impl.xs.identity;

public interface FieldActivator {
  void startValueScopeFor(IdentityConstraint paramIdentityConstraint, int paramInt);
  
  XPathMatcher activateField(Field paramField, int paramInt);
  
  void setMayMatch(Field paramField, Boolean paramBoolean);
  
  Boolean mayMatch(Field paramField);
  
  void endValueScopeFor(IdentityConstraint paramIdentityConstraint, int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/identity/FieldActivator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */