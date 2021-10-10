package sun.awt;

import java.awt.SecondaryLoop;

public interface FwDispatcher {
  boolean isDispatchThread();
  
  void scheduleDispatch(Runnable paramRunnable);
  
  SecondaryLoop createSecondaryLoop();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/FwDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */