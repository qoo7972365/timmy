package org.w3c.dom.html;

import org.w3c.dom.DOMException;

public interface HTMLTableRowElement extends HTMLElement {
  int getRowIndex();
  
  int getSectionRowIndex();
  
  HTMLCollection getCells();
  
  String getAlign();
  
  void setAlign(String paramString);
  
  String getBgColor();
  
  void setBgColor(String paramString);
  
  String getCh();
  
  void setCh(String paramString);
  
  String getChOff();
  
  void setChOff(String paramString);
  
  String getVAlign();
  
  void setVAlign(String paramString);
  
  HTMLElement insertCell(int paramInt) throws DOMException;
  
  void deleteCell(int paramInt) throws DOMException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/html/HTMLTableRowElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */