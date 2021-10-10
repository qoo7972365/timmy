package javax.sql;

import java.util.EventListener;

public interface ConnectionEventListener extends EventListener {
  void connectionClosed(ConnectionEvent paramConnectionEvent);
  
  void connectionErrorOccurred(ConnectionEvent paramConnectionEvent);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sql/ConnectionEventListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */