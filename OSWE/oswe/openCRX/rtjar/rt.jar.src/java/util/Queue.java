package java.util;

public interface Queue<E> extends Collection<E> {
  boolean add(E paramE);
  
  boolean offer(E paramE);
  
  E remove();
  
  E poll();
  
  E element();
  
  E peek();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Queue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */