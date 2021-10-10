package sun.reflect;

import java.lang.reflect.InvocationTargetException;

public interface MethodAccessor {
  Object invoke(Object paramObject, Object[] paramArrayOfObject) throws IllegalArgumentException, InvocationTargetException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/MethodAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */