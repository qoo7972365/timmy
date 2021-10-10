package javax.swing.event;

import java.util.EventListener;

public interface AncestorListener extends EventListener {
  void ancestorAdded(AncestorEvent paramAncestorEvent);
  
  void ancestorRemoved(AncestorEvent paramAncestorEvent);
  
  void ancestorMoved(AncestorEvent paramAncestorEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/AncestorListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */