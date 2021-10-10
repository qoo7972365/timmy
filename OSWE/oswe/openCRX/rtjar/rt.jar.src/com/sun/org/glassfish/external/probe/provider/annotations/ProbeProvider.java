package com.sun.org.glassfish.external.probe.provider.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ProbeProvider {
  String providerName() default "";
  
  String moduleProviderName() default "";
  
  String moduleName() default "";
  
  String probeProviderName() default "";
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/glassfish/external/probe/provider/annotations/ProbeProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */