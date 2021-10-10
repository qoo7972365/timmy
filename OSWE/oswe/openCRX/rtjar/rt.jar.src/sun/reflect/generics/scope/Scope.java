package sun.reflect.generics.scope;

import java.lang.reflect.TypeVariable;

public interface Scope {
  TypeVariable<?> lookup(String paramString);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/scope/Scope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */