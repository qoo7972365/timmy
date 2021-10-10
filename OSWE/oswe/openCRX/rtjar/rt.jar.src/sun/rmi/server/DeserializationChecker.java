package sun.rmi.server;

import java.io.ObjectStreamClass;
import java.lang.reflect.Method;

public interface DeserializationChecker {
  void check(Method paramMethod, ObjectStreamClass paramObjectStreamClass, int paramInt1, int paramInt2);
  
  void checkProxyClass(Method paramMethod, String[] paramArrayOfString, int paramInt1, int paramInt2);
  
  default void end(int paramInt) {}
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/server/DeserializationChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */