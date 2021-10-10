package javax.xml.bind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface XmlAnyElement {
  boolean lax() default false;
  
  Class<? extends DomHandler> value() default W3CDomHandler.class;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/annotation/XmlAnyElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */