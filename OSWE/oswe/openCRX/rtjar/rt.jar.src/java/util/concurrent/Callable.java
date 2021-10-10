package java.util.concurrent;

@FunctionalInterface
public interface Callable<V> {
  V call() throws Exception;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/Callable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */