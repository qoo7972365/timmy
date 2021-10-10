package com.sun.xml.internal.bind.v2.runtime.unmarshaller;

import org.xml.sax.SAXException;

public interface Intercepter {
  Object intercept(UnmarshallingContext.State paramState, Object paramObject) throws SAXException;
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/Intercepter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */