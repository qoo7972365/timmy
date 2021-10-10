package javax.xml.bind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface XmlType {
  String name() default "##default";
  
  String[] propOrder() default {""};
  
  String namespace() default "##default";
  
  Class factoryClass() default DEFAULT.class;
  
  String factoryMethod() default "";
  
  public static final class DEFAULT {}
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/annotation/XmlType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */