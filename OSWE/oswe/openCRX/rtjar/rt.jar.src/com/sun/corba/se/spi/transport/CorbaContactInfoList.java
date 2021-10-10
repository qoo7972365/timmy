package com.sun.corba.se.spi.transport;

import com.sun.corba.se.pept.transport.ContactInfoList;
import com.sun.corba.se.spi.ior.IOR;
import com.sun.corba.se.spi.protocol.LocalClientRequestDispatcher;

public interface CorbaContactInfoList extends ContactInfoList {
  void setTargetIOR(IOR paramIOR);
  
  IOR getTargetIOR();
  
  void setEffectiveTargetIOR(IOR paramIOR);
  
  IOR getEffectiveTargetIOR();
  
  LocalClientRequestDispatcher getLocalClientRequestDispatcher();
  
  int hashCode();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/transport/CorbaContactInfoList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */