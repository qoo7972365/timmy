package java.nio.file;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;

public abstract class FileSystem implements Closeable {
  public abstract FileSystemProvider provider();
  
  public abstract void close() throws IOException;
  
  public abstract boolean isOpen();
  
  public abstract boolean isReadOnly();
  
  public abstract String getSeparator();
  
  public abstract Iterable<Path> getRootDirectories();
  
  public abstract Iterable<FileStore> getFileStores();
  
  public abstract Set<String> supportedFileAttributeViews();
  
  public abstract Path getPath(String paramString, String... paramVarArgs);
  
  public abstract PathMatcher getPathMatcher(String paramString);
  
  public abstract UserPrincipalLookupService getUserPrincipalLookupService();
  
  public abstract WatchService newWatchService() throws IOException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/FileSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */