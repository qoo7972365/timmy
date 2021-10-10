package java.awt.peer;

import java.awt.Component;
import java.awt.Window;

public interface KeyboardFocusManagerPeer {
  void setCurrentFocusedWindow(Window paramWindow);
  
  Window getCurrentFocusedWindow();
  
  void setCurrentFocusOwner(Component paramComponent);
  
  Component getCurrentFocusOwner();
  
  void clearGlobalFocusOwner(Window paramWindow);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/peer/KeyboardFocusManagerPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */