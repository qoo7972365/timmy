package com.sun.org.glassfish.external.arc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PACKAGE})
public @interface Taxonomy {
  Stability stability() default Stability.UNSPECIFIED;
  
  String description() default "";
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/glassfish/external/arc/Taxonomy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */