package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.SelectableChannel;

abstract class FileDispatcher extends NativeDispatcher {
  public static final int NO_LOCK = -1;
  
  public static final int LOCKED = 0;
  
  public static final int RET_EX_LOCK = 1;
  
  public static final int INTERRUPTED = 2;
  
  abstract long seek(FileDescriptor paramFileDescriptor, long paramLong) throws IOException;
  
  abstract int force(FileDescriptor paramFileDescriptor, boolean paramBoolean) throws IOException;
  
  abstract int truncate(FileDescriptor paramFileDescriptor, long paramLong) throws IOException;
  
  abstract long size(FileDescriptor paramFileDescriptor) throws IOException;
  
  abstract int lock(FileDescriptor paramFileDescriptor, boolean paramBoolean1, long paramLong1, long paramLong2, boolean paramBoolean2) throws IOException;
  
  abstract void release(FileDescriptor paramFileDescriptor, long paramLong1, long paramLong2) throws IOException;
  
  abstract FileDescriptor duplicateForMapping(FileDescriptor paramFileDescriptor) throws IOException;
  
  abstract boolean canTransferToDirectly(SelectableChannel paramSelectableChannel);
  
  abstract boolean transferToDirectlyNeedsPositionLock();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/FileDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */