package com.sun.corba.se.spi.ior.iiop;

import com.sun.corba.se.spi.ior.TaggedProfileTemplate;

public interface IIOPProfileTemplate extends TaggedProfileTemplate {
  GIOPVersion getGIOPVersion();
  
  IIOPAddress getPrimaryAddress();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/iiop/IIOPProfileTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */