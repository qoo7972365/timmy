package javax.naming.event;

import java.util.EventListener;

public interface NamingListener extends EventListener {
  void namingExceptionThrown(NamingExceptionEvent paramNamingExceptionEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/event/NamingListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */