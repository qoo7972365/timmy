package sun.management;

import java.util.List;
import sun.management.counter.Counter;

public interface HotspotMemoryMBean {
  List<Counter> getInternalMemoryCounters();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/HotspotMemoryMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */