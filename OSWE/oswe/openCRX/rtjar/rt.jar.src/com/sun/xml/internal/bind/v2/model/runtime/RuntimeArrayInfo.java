package com.sun.xml.internal.bind.v2.model.runtime;

import com.sun.xml.internal.bind.v2.model.core.ArrayInfo;
import com.sun.xml.internal.bind.v2.model.core.NonElement;
import java.lang.reflect.Type;

public interface RuntimeArrayInfo extends ArrayInfo<Type, Class>, RuntimeNonElement {
  Class getType();
  
  RuntimeNonElement getItemType();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/runtime/RuntimeArrayInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */