package javax.xml.ws;

import java.util.concurrent.Future;

public interface Dispatch<T> extends BindingProvider {
  T invoke(T paramT);
  
  Response<T> invokeAsync(T paramT);
  
  Future<?> invokeAsync(T paramT, AsyncHandler<T> paramAsyncHandler);
  
  void invokeOneWay(T paramT);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/ws/Dispatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */