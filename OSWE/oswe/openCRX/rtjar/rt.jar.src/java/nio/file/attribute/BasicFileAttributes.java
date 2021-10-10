package java.nio.file.attribute;

public interface BasicFileAttributes {
  FileTime lastModifiedTime();
  
  FileTime lastAccessTime();
  
  FileTime creationTime();
  
  boolean isRegularFile();
  
  boolean isDirectory();
  
  boolean isSymbolicLink();
  
  boolean isOther();
  
  long size();
  
  Object fileKey();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/attribute/BasicFileAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */