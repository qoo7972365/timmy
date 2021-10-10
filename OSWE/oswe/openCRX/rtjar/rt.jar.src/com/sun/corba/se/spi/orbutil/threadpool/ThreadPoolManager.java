package com.sun.corba.se.spi.orbutil.threadpool;

import java.io.Closeable;

public interface ThreadPoolManager extends Closeable {
  ThreadPool getThreadPool(String paramString) throws NoSuchThreadPoolException;
  
  ThreadPool getThreadPool(int paramInt) throws NoSuchThreadPoolException;
  
  int getThreadPoolNumericId(String paramString);
  
  String getThreadPoolStringId(int paramInt);
  
  ThreadPool getDefaultThreadPool();
  
  ThreadPoolChooser getThreadPoolChooser(String paramString);
  
  ThreadPoolChooser getThreadPoolChooser(int paramInt);
  
  void setThreadPoolChooser(String paramString, ThreadPoolChooser paramThreadPoolChooser);
  
  int getThreadPoolChooserNumericId(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orbutil/threadpool/ThreadPoolManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */