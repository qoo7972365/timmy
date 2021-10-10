package com.sun.corba.se.pept.transport;

public interface Selector {
  void setTimeout(long paramLong);
  
  long getTimeout();
  
  void registerInterestOps(EventHandler paramEventHandler);
  
  void registerForEvent(EventHandler paramEventHandler);
  
  void unregisterForEvent(EventHandler paramEventHandler);
  
  void close();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/pept/transport/Selector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */