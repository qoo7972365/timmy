package com.sun.xml.internal.txw2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface XmlElement {
  String value() default "";
  
  String ns() default "##default";
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/annotation/XmlElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */