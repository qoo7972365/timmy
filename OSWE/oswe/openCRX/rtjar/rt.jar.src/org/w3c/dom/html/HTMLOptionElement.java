package org.w3c.dom.html;

public interface HTMLOptionElement extends HTMLElement {
  HTMLFormElement getForm();
  
  boolean getDefaultSelected();
  
  void setDefaultSelected(boolean paramBoolean);
  
  String getText();
  
  int getIndex();
  
  boolean getDisabled();
  
  void setDisabled(boolean paramBoolean);
  
  String getLabel();
  
  void setLabel(String paramString);
  
  boolean getSelected();
  
  void setSelected(boolean paramBoolean);
  
  String getValue();
  
  void setValue(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/html/HTMLOptionElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */