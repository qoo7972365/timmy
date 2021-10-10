package com.sun.org.glassfish.external.statistics;

public interface Statistic {
  String getName();
  
  String getUnit();
  
  String getDescription();
  
  long getStartTime();
  
  long getLastSampleTime();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/glassfish/external/statistics/Statistic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */