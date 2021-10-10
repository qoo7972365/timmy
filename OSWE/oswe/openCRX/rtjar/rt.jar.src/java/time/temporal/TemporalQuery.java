package java.time.temporal;

@FunctionalInterface
public interface TemporalQuery<R> {
  R queryFrom(TemporalAccessor paramTemporalAccessor);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/temporal/TemporalQuery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */