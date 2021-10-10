package java.lang.management;

import java.lang.management.PlatformManagedObject;

public interface MemoryManagerMXBean extends PlatformManagedObject {
  String getName();
  
  boolean isValid();
  
  String[] getMemoryPoolNames();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/management/MemoryManagerMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */