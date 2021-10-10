package com.sun.management;

import java.lang.management.OperatingSystemMXBean;
import jdk.Exported;

@Exported
public interface OperatingSystemMXBean extends OperatingSystemMXBean {
  long getCommittedVirtualMemorySize();
  
  long getTotalSwapSpaceSize();
  
  long getFreeSwapSpaceSize();
  
  long getProcessCpuTime();
  
  long getFreePhysicalMemorySize();
  
  long getTotalPhysicalMemorySize();
  
  double getSystemCpuLoad();
  
  double getProcessCpuLoad();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/management/OperatingSystemMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */