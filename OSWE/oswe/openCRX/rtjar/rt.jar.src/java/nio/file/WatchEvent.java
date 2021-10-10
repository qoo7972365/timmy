package java.nio.file;

public interface WatchEvent<T> {
  Kind<T> kind();
  
  int count();
  
  T context();
  
  public static interface Modifier {
    String name();
  }
  
  public static interface Kind<T> {
    String name();
    
    Class<T> type();
  }
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/WatchEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */