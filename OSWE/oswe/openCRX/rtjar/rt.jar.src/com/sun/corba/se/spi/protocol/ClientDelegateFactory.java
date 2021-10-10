package com.sun.corba.se.spi.protocol;

import com.sun.corba.se.spi.transport.CorbaContactInfoList;

public interface ClientDelegateFactory {
  CorbaClientDelegate create(CorbaContactInfoList paramCorbaContactInfoList);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/protocol/ClientDelegateFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */