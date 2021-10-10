package com.sun.corba.se.pept.broker;

import com.sun.corba.se.pept.protocol.ClientInvocationInfo;
import com.sun.corba.se.pept.transport.TransportManager;

public interface Broker {
  ClientInvocationInfo createOrIncrementInvocationInfo();
  
  ClientInvocationInfo getInvocationInfo();
  
  void releaseOrDecrementInvocationInfo();
  
  TransportManager getTransportManager();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/pept/broker/Broker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */