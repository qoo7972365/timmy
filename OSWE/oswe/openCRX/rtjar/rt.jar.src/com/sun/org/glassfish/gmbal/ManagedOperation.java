package com.sun.org.glassfish.gmbal;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagedOperation {
  String id() default "";
  
  Impact impact() default Impact.UNKNOWN;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/glassfish/gmbal/ManagedOperation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */