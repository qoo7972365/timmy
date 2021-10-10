package com.sun.xml.internal.bind.v2.model.core;

import javax.xml.namespace.QName;

public interface TypeRef<T, C> extends NonElementRef<T, C> {
  QName getTagName();
  
  boolean isNillable();
  
  String getDefaultValue();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/core/TypeRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */