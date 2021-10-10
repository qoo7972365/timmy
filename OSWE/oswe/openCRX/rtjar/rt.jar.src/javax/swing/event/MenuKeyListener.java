package javax.swing.event;

import java.util.EventListener;

public interface MenuKeyListener extends EventListener {
  void menuKeyTyped(MenuKeyEvent paramMenuKeyEvent);
  
  void menuKeyPressed(MenuKeyEvent paramMenuKeyEvent);
  
  void menuKeyReleased(MenuKeyEvent paramMenuKeyEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/MenuKeyListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */