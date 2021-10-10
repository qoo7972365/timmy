package java.lang.management;

import java.lang.management.PlatformManagedObject;

public interface ClassLoadingMXBean extends PlatformManagedObject {
  long getTotalLoadedClassCount();
  
  int getLoadedClassCount();
  
  long getUnloadedClassCount();
  
  boolean isVerbose();
  
  void setVerbose(boolean paramBoolean);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/management/ClassLoadingMXBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */