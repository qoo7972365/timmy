package java.util.concurrent;

public interface CompletionService<V> {
  Future<V> submit(Callable<V> paramCallable);
  
  Future<V> submit(Runnable paramRunnable, V paramV);
  
  Future<V> take() throws InterruptedException;
  
  Future<V> poll();
  
  Future<V> poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/CompletionService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */