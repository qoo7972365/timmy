package com.sun.corba.se.spi.transport;

import com.sun.corba.se.spi.ior.IOR;
import com.sun.corba.se.spi.orb.ORB;

public interface CorbaContactInfoListFactory {
  void setORB(ORB paramORB);
  
  CorbaContactInfoList create(IOR paramIOR);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/transport/CorbaContactInfoListFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */