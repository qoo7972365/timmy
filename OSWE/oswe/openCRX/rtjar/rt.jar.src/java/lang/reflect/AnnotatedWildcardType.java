package java.lang.reflect;

import java.lang.reflect.AnnotatedType;

public interface AnnotatedWildcardType extends AnnotatedType {
  AnnotatedType[] getAnnotatedLowerBounds();
  
  AnnotatedType[] getAnnotatedUpperBounds();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/AnnotatedWildcardType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */