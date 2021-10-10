package java.nio.channels;

public interface CompletionHandler<V, A> {
  void completed(V paramV, A paramA);
  
  void failed(Throwable paramThrowable, A paramA);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/channels/CompletionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */