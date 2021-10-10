package com.sun.corba.se.spi.ior;

import com.sun.corba.se.spi.orb.ORB;
import com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
import org.omg.CORBA.ORB;

public interface ObjectKey extends Writeable {
  ObjectId getId();
  
  ObjectKeyTemplate getTemplate();
  
  byte[] getBytes(ORB paramORB);
  
  CorbaServerRequestDispatcher getServerRequestDispatcher(ORB paramORB);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/ObjectKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */