package java.util.concurrent;

public interface RunnableFuture<V> extends Runnable, Future<V> {
  void run();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/RunnableFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */