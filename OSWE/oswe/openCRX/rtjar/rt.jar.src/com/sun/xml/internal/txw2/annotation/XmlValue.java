package com.sun.xml.internal.txw2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface XmlValue {}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/annotation/XmlValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */