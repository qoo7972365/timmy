package com.sun.org.apache.xerces.internal.xs;

public interface XSNamespaceItem {
  String getSchemaNamespace();
  
  XSNamedMap getComponents(short paramShort);
  
  XSObjectList getAnnotations();
  
  XSElementDeclaration getElementDeclaration(String paramString);
  
  XSAttributeDeclaration getAttributeDeclaration(String paramString);
  
  XSTypeDefinition getTypeDefinition(String paramString);
  
  XSAttributeGroupDefinition getAttributeGroup(String paramString);
  
  XSModelGroupDefinition getModelGroupDefinition(String paramString);
  
  XSNotationDeclaration getNotationDeclaration(String paramString);
  
  StringList getDocumentLocations();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xs/XSNamespaceItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */