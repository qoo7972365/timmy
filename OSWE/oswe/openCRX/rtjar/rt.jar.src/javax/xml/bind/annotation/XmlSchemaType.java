package javax.xml.bind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PACKAGE})
public @interface XmlSchemaType {
  String name();
  
  String namespace() default "http://www.w3.org/2001/XMLSchema";
  
  Class type() default DEFAULT.class;
  
  public static final class DEFAULT {}
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/annotation/XmlSchemaType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */