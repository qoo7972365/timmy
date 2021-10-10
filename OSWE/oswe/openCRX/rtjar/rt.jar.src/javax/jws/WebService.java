package javax.jws;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface WebService {
  String name() default "";
  
  String targetNamespace() default "";
  
  String serviceName() default "";
  
  String portName() default "";
  
  String wsdlLocation() default "";
  
  String endpointInterface() default "";
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/jws/WebService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */