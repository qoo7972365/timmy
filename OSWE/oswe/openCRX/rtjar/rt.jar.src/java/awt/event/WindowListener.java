package java.awt.event;

import java.util.EventListener;

public interface WindowListener extends EventListener {
  void windowOpened(WindowEvent paramWindowEvent);
  
  void windowClosing(WindowEvent paramWindowEvent);
  
  void windowClosed(WindowEvent paramWindowEvent);
  
  void windowIconified(WindowEvent paramWindowEvent);
  
  void windowDeiconified(WindowEvent paramWindowEvent);
  
  void windowActivated(WindowEvent paramWindowEvent);
  
  void windowDeactivated(WindowEvent paramWindowEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/event/WindowListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */