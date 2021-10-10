package java.lang.management;

import java.lang.management.PlatformManagedObject;

public interface OperatingSystemMXBean extends PlatformManagedObject {
  String getName();
  
  String getArch();
  
  String getVersion();
  
  int getAvailableProcessors();
  
  double getSystemLoadAverage();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/management/OperatingSystemMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */