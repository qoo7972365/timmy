package javax.swing.event;

import java.util.EventListener;

public interface PopupMenuListener extends EventListener {
  void popupMenuWillBecomeVisible(PopupMenuEvent paramPopupMenuEvent);
  
  void popupMenuWillBecomeInvisible(PopupMenuEvent paramPopupMenuEvent);
  
  void popupMenuCanceled(PopupMenuEvent paramPopupMenuEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/PopupMenuListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */