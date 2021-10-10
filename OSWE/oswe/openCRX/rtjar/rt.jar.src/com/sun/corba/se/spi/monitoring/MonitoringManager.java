package com.sun.corba.se.spi.monitoring;

import java.io.Closeable;

public interface MonitoringManager extends Closeable {
  MonitoredObject getRootMonitoredObject();
  
  void clearState();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/monitoring/MonitoringManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */