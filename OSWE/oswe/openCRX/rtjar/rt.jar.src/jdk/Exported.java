package jdk;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Exported
public @interface Exported {
  boolean value() default true;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/Exported.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */