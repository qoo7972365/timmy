package java.lang.management;

import java.lang.management.PlatformManagedObject;

public interface CompilationMXBean extends PlatformManagedObject {
  String getName();
  
  boolean isCompilationTimeMonitoringSupported();
  
  long getTotalCompilationTime();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/management/CompilationMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */