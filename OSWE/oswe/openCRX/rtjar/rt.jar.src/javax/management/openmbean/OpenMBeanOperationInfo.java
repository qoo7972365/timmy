package javax.management.openmbean;

import javax.management.MBeanParameterInfo;

public interface OpenMBeanOperationInfo {
  String getDescription();
  
  String getName();
  
  MBeanParameterInfo[] getSignature();
  
  int getImpact();
  
  String getReturnType();
  
  OpenType<?> getReturnOpenType();
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  String toString();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/openmbean/OpenMBeanOperationInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */