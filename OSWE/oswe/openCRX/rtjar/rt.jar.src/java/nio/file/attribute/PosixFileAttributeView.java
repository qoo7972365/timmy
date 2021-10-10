package java.nio.file.attribute;

import java.io.IOException;
import java.util.Set;

public interface PosixFileAttributeView extends BasicFileAttributeView, FileOwnerAttributeView {
  String name();
  
  PosixFileAttributes readAttributes() throws IOException;
  
  void setPermissions(Set<PosixFilePermission> paramSet) throws IOException;
  
  void setGroup(GroupPrincipal paramGroupPrincipal) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/attribute/PosixFileAttributeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */