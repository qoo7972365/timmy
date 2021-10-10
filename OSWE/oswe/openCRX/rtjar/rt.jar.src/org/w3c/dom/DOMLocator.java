package org.w3c.dom;

public interface DOMLocator {
  int getLineNumber();
  
  int getColumnNumber();
  
  int getByteOffset();
  
  int getUtf16Offset();
  
  Node getRelatedNode();
  
  String getUri();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/DOMLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */