package java.lang.reflect;

import java.lang.reflect.Type;

public interface ParameterizedType extends Type {
  Type[] getActualTypeArguments();
  
  Type getRawType();
  
  Type getOwnerType();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/ParameterizedType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */