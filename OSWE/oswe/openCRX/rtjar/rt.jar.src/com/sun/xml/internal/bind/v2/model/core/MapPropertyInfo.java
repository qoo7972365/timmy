package com.sun.xml.internal.bind.v2.model.core;

import javax.xml.namespace.QName;

public interface MapPropertyInfo<T, C> extends PropertyInfo<T, C> {
  QName getXmlName();
  
  boolean isCollectionNillable();
  
  NonElement<T, C> getKeyType();
  
  NonElement<T, C> getValueType();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/core/MapPropertyInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */