package com.sun.xml.internal.bind.v2.model.core;

public interface EnumLeafInfo<T, C> extends LeafInfo<T, C> {
  C getClazz();
  
  NonElement<T, C> getBaseType();
  
  Iterable<? extends EnumConstant> getConstants();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/core/EnumLeafInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */