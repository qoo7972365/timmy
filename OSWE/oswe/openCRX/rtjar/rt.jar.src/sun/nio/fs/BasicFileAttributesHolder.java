package sun.nio.fs;

import java.nio.file.attribute.BasicFileAttributes;

public interface BasicFileAttributesHolder {
  BasicFileAttributes get();
  
  void invalidate();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/BasicFileAttributesHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */