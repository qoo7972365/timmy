package java.lang.management;

import java.lang.management.MemoryManagerMXBean;

public interface GarbageCollectorMXBean extends MemoryManagerMXBean {
  long getCollectionCount();
  
  long getCollectionTime();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/management/GarbageCollectorMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */