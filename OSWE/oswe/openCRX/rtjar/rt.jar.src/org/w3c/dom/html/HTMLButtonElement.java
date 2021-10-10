package org.w3c.dom.html;

public interface HTMLButtonElement extends HTMLElement {
  HTMLFormElement getForm();
  
  String getAccessKey();
  
  void setAccessKey(String paramString);
  
  boolean getDisabled();
  
  void setDisabled(boolean paramBoolean);
  
  String getName();
  
  void setName(String paramString);
  
  int getTabIndex();
  
  void setTabIndex(int paramInt);
  
  String getType();
  
  String getValue();
  
  void setValue(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/html/HTMLButtonElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */