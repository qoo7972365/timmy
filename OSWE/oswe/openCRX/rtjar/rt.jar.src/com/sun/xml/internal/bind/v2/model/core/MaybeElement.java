package com.sun.xml.internal.bind.v2.model.core;

import javax.xml.namespace.QName;

public interface MaybeElement<T, C> extends NonElement<T, C> {
  boolean isElement();
  
  QName getElementName();
  
  Element<T, C> asElement();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/core/MaybeElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */