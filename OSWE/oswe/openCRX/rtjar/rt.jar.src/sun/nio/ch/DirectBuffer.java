package sun.nio.ch;

import sun.misc.Cleaner;

public interface DirectBuffer {
  long address();
  
  Object attachment();
  
  Cleaner cleaner();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/DirectBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */