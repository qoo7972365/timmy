package javax.jws.soap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Deprecated
public @interface SOAPMessageHandlers {
  SOAPMessageHandler[] value();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/jws/soap/SOAPMessageHandlers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */