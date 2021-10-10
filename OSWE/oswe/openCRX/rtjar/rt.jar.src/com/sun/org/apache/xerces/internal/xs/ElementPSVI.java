package com.sun.org.apache.xerces.internal.xs;

public interface ElementPSVI extends ItemPSVI {
  XSElementDeclaration getElementDeclaration();
  
  XSNotationDeclaration getNotation();
  
  boolean getNil();
  
  XSModel getSchemaInformation();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/ElementPSVI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */