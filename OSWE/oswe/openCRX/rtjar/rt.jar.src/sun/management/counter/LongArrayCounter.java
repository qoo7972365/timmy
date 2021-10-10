package sun.management.counter;

public interface LongArrayCounter extends Counter {
  long[] longArrayValue();
  
  long longAt(int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/counter/LongArrayCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */