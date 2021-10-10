package org.w3c.dom;

public interface NamedNodeMap {
  Node getNamedItem(String paramString);
  
  Node setNamedItem(Node paramNode) throws DOMException;
  
  Node removeNamedItem(String paramString) throws DOMException;
  
  Node item(int paramInt);
  
  int getLength();
  
  Node getNamedItemNS(String paramString1, String paramString2) throws DOMException;
  
  Node setNamedItemNS(Node paramNode) throws DOMException;
  
  Node removeNamedItemNS(String paramString1, String paramString2) throws DOMException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/NamedNodeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */