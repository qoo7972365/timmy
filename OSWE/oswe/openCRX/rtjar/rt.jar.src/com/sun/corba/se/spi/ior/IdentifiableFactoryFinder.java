package com.sun.corba.se.spi.ior;

import org.omg.CORBA_2_3.portable.InputStream;

public interface IdentifiableFactoryFinder {
  Identifiable create(int paramInt, InputStream paramInputStream);
  
  void registerFactory(IdentifiableFactory paramIdentifiableFactory);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/IdentifiableFactoryFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */