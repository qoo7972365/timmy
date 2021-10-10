package javax.management.monitor;

import javax.management.ObjectName;

public interface MonitorMBean {
  void start();
  
  void stop();
  
  void addObservedObject(ObjectName paramObjectName) throws IllegalArgumentException;
  
  void removeObservedObject(ObjectName paramObjectName);
  
  boolean containsObservedObject(ObjectName paramObjectName);
  
  ObjectName[] getObservedObjects();
  
  @Deprecated
  ObjectName getObservedObject();
  
  @Deprecated
  void setObservedObject(ObjectName paramObjectName);
  
  String getObservedAttribute();
  
  void setObservedAttribute(String paramString);
  
  long getGranularityPeriod();
  
  void setGranularityPeriod(long paramLong) throws IllegalArgumentException;
  
  boolean isActive();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/monitor/MonitorMBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */