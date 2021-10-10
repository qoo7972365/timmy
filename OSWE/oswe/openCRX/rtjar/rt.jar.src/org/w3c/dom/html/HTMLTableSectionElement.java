package org.w3c.dom.html;

import org.w3c.dom.DOMException;

public interface HTMLTableSectionElement extends HTMLElement {
  String getAlign();
  
  void setAlign(String paramString);
  
  String getCh();
  
  void setCh(String paramString);
  
  String getChOff();
  
  void setChOff(String paramString);
  
  String getVAlign();
  
  void setVAlign(String paramString);
  
  HTMLCollection getRows();
  
  HTMLElement insertRow(int paramInt) throws DOMException;
  
  void deleteRow(int paramInt) throws DOMException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/html/HTMLTableSectionElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */