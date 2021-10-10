package javax.swing.event;

import java.util.EventListener;

public interface MenuListener extends EventListener {
  void menuSelected(MenuEvent paramMenuEvent);
  
  void menuDeselected(MenuEvent paramMenuEvent);
  
  void menuCanceled(MenuEvent paramMenuEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/MenuListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */