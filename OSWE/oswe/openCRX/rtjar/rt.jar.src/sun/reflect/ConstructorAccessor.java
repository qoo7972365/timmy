package sun.reflect;

import java.lang.reflect.InvocationTargetException;

public interface ConstructorAccessor {
  Object newInstance(Object[] paramArrayOfObject) throws InstantiationException, IllegalArgumentException, InvocationTargetException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/ConstructorAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */