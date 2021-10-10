package com.sun.management;

import jdk.Exported;

@Exported
public interface UnixOperatingSystemMXBean extends OperatingSystemMXBean {
  long getOpenFileDescriptorCount();
  
  long getMaxFileDescriptorCount();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/management/UnixOperatingSystemMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */