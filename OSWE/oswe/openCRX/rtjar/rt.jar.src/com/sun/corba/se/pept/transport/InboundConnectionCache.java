package com.sun.corba.se.pept.transport;

public interface InboundConnectionCache extends ConnectionCache {
  Connection get(Acceptor paramAcceptor);
  
  void put(Acceptor paramAcceptor, Connection paramConnection);
  
  void remove(Connection paramConnection);
  
  Acceptor getAcceptor();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/pept/transport/InboundConnectionCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */