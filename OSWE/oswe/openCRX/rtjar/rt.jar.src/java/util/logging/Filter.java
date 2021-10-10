package java.util.logging;

@FunctionalInterface
public interface Filter {
  boolean isLoggable(LogRecord paramLogRecord);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/Filter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */