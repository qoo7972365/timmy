package com.sun.corba.se.spi.ior;

import java.util.Iterator;

public interface ObjectAdapterId extends Writeable {
  int getNumLevels();
  
  Iterator iterator();
  
  String[] getAdapterName();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/ObjectAdapterId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */