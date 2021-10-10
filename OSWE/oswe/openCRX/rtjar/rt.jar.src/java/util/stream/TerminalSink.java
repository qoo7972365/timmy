package java.util.stream;

import java.util.function.Supplier;

interface TerminalSink<T, R> extends Sink<T>, Supplier<R> {}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/TerminalSink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */