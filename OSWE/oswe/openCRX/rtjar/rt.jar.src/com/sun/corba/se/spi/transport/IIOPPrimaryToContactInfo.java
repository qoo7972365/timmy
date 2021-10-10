package com.sun.corba.se.spi.transport;

import com.sun.corba.se.pept.transport.ContactInfo;
import java.util.List;

public interface IIOPPrimaryToContactInfo {
  void reset(ContactInfo paramContactInfo);
  
  boolean hasNext(ContactInfo paramContactInfo1, ContactInfo paramContactInfo2, List paramList);
  
  ContactInfo next(ContactInfo paramContactInfo1, ContactInfo paramContactInfo2, List paramList);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/transport/IIOPPrimaryToContactInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */