package com.sun.corba.se.spi.oa;

import com.sun.corba.se.spi.ior.ObjectAdapterId;
import com.sun.corba.se.spi.orb.ORB;

public interface ObjectAdapterFactory {
  void init(ORB paramORB);
  
  void shutdown(boolean paramBoolean);
  
  ObjectAdapter find(ObjectAdapterId paramObjectAdapterId);
  
  ORB getORB();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/oa/ObjectAdapterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */