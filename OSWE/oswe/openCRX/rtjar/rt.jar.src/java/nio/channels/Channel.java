package java.nio.channels;

import java.io.Closeable;
import java.io.IOException;

public interface Channel extends Closeable {
  boolean isOpen();
  
  void close() throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/channels/Channel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */