package java.awt.event;

import java.util.EventListener;

public interface MouseListener extends EventListener {
  void mouseClicked(MouseEvent paramMouseEvent);
  
  void mousePressed(MouseEvent paramMouseEvent);
  
  void mouseReleased(MouseEvent paramMouseEvent);
  
  void mouseEntered(MouseEvent paramMouseEvent);
  
  void mouseExited(MouseEvent paramMouseEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/event/MouseListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */