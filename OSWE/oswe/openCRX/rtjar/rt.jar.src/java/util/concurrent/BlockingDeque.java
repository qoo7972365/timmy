package java.util.concurrent;

import java.util.Deque;
import java.util.Iterator;

public interface BlockingDeque<E> extends BlockingQueue<E>, Deque<E> {
  void addFirst(E paramE);
  
  void addLast(E paramE);
  
  boolean offerFirst(E paramE);
  
  boolean offerLast(E paramE);
  
  void putFirst(E paramE) throws InterruptedException;
  
  void putLast(E paramE) throws InterruptedException;
  
  boolean offerFirst(E paramE, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException;
  
  boolean offerLast(E paramE, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException;
  
  E takeFirst() throws InterruptedException;
  
  E takeLast() throws InterruptedException;
  
  E pollFirst(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException;
  
  E pollLast(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException;
  
  boolean removeFirstOccurrence(Object paramObject);
  
  boolean removeLastOccurrence(Object paramObject);
  
  boolean add(E paramE);
  
  boolean offer(E paramE);
  
  void put(E paramE) throws InterruptedException;
  
  boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException;
  
  E remove();
  
  E poll();
  
  E take() throws InterruptedException;
  
  E poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException;
  
  E element();
  
  E peek();
  
  boolean remove(Object paramObject);
  
  boolean contains(Object paramObject);
  
  int size();
  
  Iterator<E> iterator();
  
  void push(E paramE);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/BlockingDeque.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */