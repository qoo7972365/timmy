package com.sun.corba.se.impl.protocol.giopmsgheaders;

import com.sun.corba.se.spi.ior.ObjectKey;
import com.sun.corba.se.spi.servicecontext.ServiceContexts;
import org.omg.CORBA.Principal;

public interface RequestMessage extends Message {
  public static final byte RESPONSE_EXPECTED_BIT = 1;
  
  ServiceContexts getServiceContexts();
  
  int getRequestId();
  
  boolean isResponseExpected();
  
  byte[] getReserved();
  
  ObjectKey getObjectKey();
  
  String getOperation();
  
  Principal getPrincipal();
  
  void setThreadPoolToUse(int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/giopmsgheaders/RequestMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */