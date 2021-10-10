package sun.reflect.annotation;

import java.io.Serializable;

public abstract class ExceptionProxy implements Serializable {
  protected abstract RuntimeException generateException();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/annotation/ExceptionProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */