package com.sun.corba.se.spi.ior;

import com.sun.corba.se.spi.orb.ORB;

public interface IORFactory extends Writeable, MakeImmutable {
  IOR makeIOR(ORB paramORB, String paramString, ObjectId paramObjectId);
  
  boolean isEquivalent(IORFactory paramIORFactory);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/IORFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */