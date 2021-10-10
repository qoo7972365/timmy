package org.w3c.dom;

public interface Attr extends Node {
  String getName();
  
  boolean getSpecified();
  
  String getValue();
  
  void setValue(String paramString) throws DOMException;
  
  Element getOwnerElement();
  
  TypeInfo getSchemaTypeInfo();
  
  boolean isId();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/Attr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */