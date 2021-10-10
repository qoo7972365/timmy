package java.nio.file;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public interface WatchService extends Closeable {
  void close() throws IOException;
  
  WatchKey poll();
  
  WatchKey poll(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException;
  
  WatchKey take() throws InterruptedException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/WatchService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */