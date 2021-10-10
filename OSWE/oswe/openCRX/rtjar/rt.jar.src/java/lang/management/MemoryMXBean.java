package java.lang.management;

import java.lang.management.MemoryUsage;
import java.lang.management.PlatformManagedObject;

public interface MemoryMXBean extends PlatformManagedObject {
  int getObjectPendingFinalizationCount();
  
  MemoryUsage getHeapMemoryUsage();
  
  MemoryUsage getNonHeapMemoryUsage();
  
  boolean isVerbose();
  
  void setVerbose(boolean paramBoolean);
  
  void gc();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/management/MemoryMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */