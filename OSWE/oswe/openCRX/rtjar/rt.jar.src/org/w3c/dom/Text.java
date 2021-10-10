package org.w3c.dom;

public interface Text extends CharacterData {
  Text splitText(int paramInt) throws DOMException;
  
  boolean isElementContentWhitespace();
  
  String getWholeText();
  
  Text replaceWholeText(String paramString) throws DOMException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/Text.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */