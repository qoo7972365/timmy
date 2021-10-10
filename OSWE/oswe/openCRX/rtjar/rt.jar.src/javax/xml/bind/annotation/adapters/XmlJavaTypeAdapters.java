package javax.xml.bind.annotation.adapters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE})
public @interface XmlJavaTypeAdapters {
  XmlJavaTypeAdapter[] value();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/annotation/adapters/XmlJavaTypeAdapters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */