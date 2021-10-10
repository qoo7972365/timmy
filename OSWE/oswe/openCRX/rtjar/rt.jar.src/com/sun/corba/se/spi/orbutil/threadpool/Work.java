package com.sun.corba.se.spi.orbutil.threadpool;

public interface Work {
  void doWork();
  
  void setEnqueueTime(long paramLong);
  
  long getEnqueueTime();
  
  String getName();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orbutil/threadpool/Work.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */