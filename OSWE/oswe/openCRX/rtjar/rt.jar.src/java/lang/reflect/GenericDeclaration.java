package java.lang.reflect;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.TypeVariable;

public interface GenericDeclaration extends AnnotatedElement {
  TypeVariable<?>[] getTypeParameters();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/GenericDeclaration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */