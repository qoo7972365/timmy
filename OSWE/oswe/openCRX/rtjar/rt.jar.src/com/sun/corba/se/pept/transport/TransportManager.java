package com.sun.corba.se.pept.transport;

import java.util.Collection;

public interface TransportManager {
  ByteBufferPool getByteBufferPool(int paramInt);
  
  OutboundConnectionCache getOutboundConnectionCache(ContactInfo paramContactInfo);
  
  Collection getOutboundConnectionCaches();
  
  InboundConnectionCache getInboundConnectionCache(Acceptor paramAcceptor);
  
  Collection getInboundConnectionCaches();
  
  Selector getSelector(int paramInt);
  
  void registerAcceptor(Acceptor paramAcceptor);
  
  Collection getAcceptors();
  
  void unregisterAcceptor(Acceptor paramAcceptor);
  
  void close();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/pept/transport/TransportManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */