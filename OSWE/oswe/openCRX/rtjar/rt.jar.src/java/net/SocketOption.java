package java.net;

public interface SocketOption<T> {
  String name();
  
  Class<T> type();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/SocketOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */