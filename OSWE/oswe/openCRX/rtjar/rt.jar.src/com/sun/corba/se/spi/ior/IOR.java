package com.sun.corba.se.spi.ior;

import com.sun.corba.se.spi.ior.iiop.IIOPProfile;
import com.sun.corba.se.spi.orb.ORB;
import java.util.Iterator;
import java.util.List;

public interface IOR extends List, Writeable, MakeImmutable {
  ORB getORB();
  
  String getTypeId();
  
  Iterator iteratorById(int paramInt);
  
  String stringify();
  
  org.omg.IOP.IOR getIOPIOR();
  
  boolean isNil();
  
  boolean isEquivalent(IOR paramIOR);
  
  IORTemplateList getIORTemplates();
  
  IIOPProfile getProfile();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/IOR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */