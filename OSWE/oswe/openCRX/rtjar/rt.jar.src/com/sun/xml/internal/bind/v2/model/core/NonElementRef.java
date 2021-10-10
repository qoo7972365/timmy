package com.sun.xml.internal.bind.v2.model.core;

public interface NonElementRef<T, C> {
  NonElement<T, C> getTarget();
  
  PropertyInfo<T, C> getSource();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/core/NonElementRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */