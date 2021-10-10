package javax.jws;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Oneway {}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/jws/Oneway.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */