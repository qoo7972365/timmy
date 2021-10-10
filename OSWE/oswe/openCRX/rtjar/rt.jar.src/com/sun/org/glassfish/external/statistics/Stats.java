package com.sun.org.glassfish.external.statistics;

public interface Stats {
  Statistic getStatistic(String paramString);
  
  String[] getStatisticNames();
  
  Statistic[] getStatistics();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/glassfish/external/statistics/Stats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */