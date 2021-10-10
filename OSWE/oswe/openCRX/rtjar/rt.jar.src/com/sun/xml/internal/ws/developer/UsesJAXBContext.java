package com.sun.xml.internal.ws.developer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@WebServiceFeatureAnnotation(id = "http://jax-ws.dev.java.net/features/uses-jaxb-context", bean = UsesJAXBContextFeature.class)
public @interface UsesJAXBContext {
  Class<? extends JAXBContextFactory> value();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/developer/UsesJAXBContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */