package com.sun.xml.internal.bind.v2.runtime.unmarshaller;

import org.xml.sax.Attributes;

public interface AttributesEx extends Attributes {
  CharSequence getData(int paramInt);
  
  CharSequence getData(String paramString1, String paramString2);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/AttributesEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */