package java.lang.annotation;

import java.lang.annotation.Annotation;

public interface Annotation {
  boolean equals(Object paramObject);
  
  int hashCode();
  
  String toString();
  
  Class<? extends Annotation> annotationType();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/annotation/Annotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */