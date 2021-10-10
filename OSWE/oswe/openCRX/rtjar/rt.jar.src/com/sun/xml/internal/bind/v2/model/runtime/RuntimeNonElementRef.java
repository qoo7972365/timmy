package com.sun.xml.internal.bind.v2.model.runtime;

import com.sun.xml.internal.bind.v2.model.core.NonElement;
import com.sun.xml.internal.bind.v2.model.core.NonElementRef;
import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
import com.sun.xml.internal.bind.v2.runtime.Transducer;
import java.lang.reflect.Type;

public interface RuntimeNonElementRef extends NonElementRef<Type, Class> {
  RuntimeNonElement getTarget();
  
  RuntimePropertyInfo getSource();
  
  Transducer getTransducer();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/runtime/RuntimeNonElementRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */