package com.sun.corba.se.spi.ior;

import org.omg.CORBA.ORB;

public interface TaggedComponent extends Identifiable {
  org.omg.IOP.TaggedComponent getIOPComponent(ORB paramORB);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/TaggedComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */