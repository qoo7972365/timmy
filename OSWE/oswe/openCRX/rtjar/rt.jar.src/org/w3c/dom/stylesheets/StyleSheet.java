package org.w3c.dom.stylesheets;

import org.w3c.dom.Node;

public interface StyleSheet {
  String getType();
  
  boolean getDisabled();
  
  void setDisabled(boolean paramBoolean);
  
  Node getOwnerNode();
  
  StyleSheet getParentStyleSheet();
  
  String getHref();
  
  String getTitle();
  
  MediaList getMedia();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/w3c/dom/stylesheets/StyleSheet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */