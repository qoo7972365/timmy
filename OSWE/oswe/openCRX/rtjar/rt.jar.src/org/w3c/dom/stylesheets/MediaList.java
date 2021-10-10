package org.w3c.dom.stylesheets;

import org.w3c.dom.DOMException;

public interface MediaList {
  String getMediaText();
  
  void setMediaText(String paramString) throws DOMException;
  
  int getLength();
  
  String item(int paramInt);
  
  void deleteMedium(String paramString) throws DOMException;
  
  void appendMedium(String paramString) throws DOMException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/stylesheets/MediaList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */