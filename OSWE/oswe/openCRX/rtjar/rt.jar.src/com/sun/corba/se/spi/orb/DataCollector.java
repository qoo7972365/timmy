package com.sun.corba.se.spi.orb;

import java.util.Properties;

public interface DataCollector {
  boolean isApplet();
  
  boolean initialHostIsLocal();
  
  void setParser(PropertyParser paramPropertyParser);
  
  Properties getProperties();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orb/DataCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */