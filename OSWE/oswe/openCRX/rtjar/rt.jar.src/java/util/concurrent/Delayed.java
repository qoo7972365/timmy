package java.util.concurrent;

public interface Delayed extends Comparable<Delayed> {
  long getDelay(TimeUnit paramTimeUnit);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/Delayed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */