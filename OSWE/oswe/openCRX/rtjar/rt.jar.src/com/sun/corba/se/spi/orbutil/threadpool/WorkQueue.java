package com.sun.corba.se.spi.orbutil.threadpool;

public interface WorkQueue {
  void addWork(Work paramWork);
  
  String getName();
  
  long totalWorkItemsAdded();
  
  int workItemsInQueue();
  
  long averageTimeInQueue();
  
  void setThreadPool(ThreadPool paramThreadPool);
  
  ThreadPool getThreadPool();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orbutil/threadpool/WorkQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */