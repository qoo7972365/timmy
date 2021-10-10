package com.sun.xml.internal.ws.spi.db;

public interface PropertyGetter {
  Class getType();
  
  <A> A getAnnotation(Class<A> paramClass);
  
  Object get(Object paramObject);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/PropertyGetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */