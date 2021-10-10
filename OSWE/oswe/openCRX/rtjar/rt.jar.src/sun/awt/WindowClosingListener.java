package sun.awt;

import java.awt.event.WindowEvent;

public interface WindowClosingListener {
  RuntimeException windowClosingNotify(WindowEvent paramWindowEvent);
  
  RuntimeException windowClosingDelivered(WindowEvent paramWindowEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/WindowClosingListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */