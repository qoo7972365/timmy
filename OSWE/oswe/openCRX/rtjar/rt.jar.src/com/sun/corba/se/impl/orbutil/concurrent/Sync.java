package com.sun.corba.se.impl.orbutil.concurrent;

public interface Sync {
  public static final long ONE_SECOND = 1000L;
  
  public static final long ONE_MINUTE = 60000L;
  
  public static final long ONE_HOUR = 3600000L;
  
  public static final long ONE_DAY = 86400000L;
  
  public static final long ONE_WEEK = 604800000L;
  
  public static final long ONE_YEAR = 31556952000L;
  
  public static final long ONE_CENTURY = 3155695200000L;
  
  void acquire() throws InterruptedException;
  
  boolean attempt(long paramLong) throws InterruptedException;
  
  void release();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/concurrent/Sync.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */