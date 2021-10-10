package com.sun.xml.internal.bind.v2.model.core;

public interface EnumConstant<T, C> {
  EnumLeafInfo<T, C> getEnclosingClass();
  
  String getLexicalValue();
  
  String getName();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/core/EnumConstant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */