package java.util.concurrent.locks;

import java.util.concurrent.TimeUnit;

public interface Lock {
  void lock();
  
  void lockInterruptibly() throws InterruptedException;
  
  boolean tryLock();
  
  boolean tryLock(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException;
  
  void unlock();
  
  Condition newCondition();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/locks/Lock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */