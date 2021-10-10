package java.nio.file.attribute;

import java.io.IOException;

public interface DosFileAttributeView extends BasicFileAttributeView {
  String name();
  
  DosFileAttributes readAttributes() throws IOException;
  
  void setReadOnly(boolean paramBoolean) throws IOException;
  
  void setHidden(boolean paramBoolean) throws IOException;
  
  void setSystem(boolean paramBoolean) throws IOException;
  
  void setArchive(boolean paramBoolean) throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/attribute/DosFileAttributeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */