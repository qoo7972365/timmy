package javax.xml.ws;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Action {
  String input() default "";
  
  String output() default "";
  
  FaultAction[] fault() default {};
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/ws/Action.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */