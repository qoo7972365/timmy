package javax.jws;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface WebMethod {
  String operationName() default "";
  
  String action() default "";
  
  boolean exclude() default false;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/jws/WebMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */