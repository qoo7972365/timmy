package com.sun.xml.internal.bind.v2.model.runtime;

import com.sun.xml.internal.bind.v2.model.core.ClassInfo;
import com.sun.xml.internal.bind.v2.model.core.ElementInfo;
import com.sun.xml.internal.bind.v2.model.core.ElementPropertyInfo;
import com.sun.xml.internal.bind.v2.model.core.NonElement;
import java.lang.reflect.Type;
import javax.xml.bind.JAXBElement;

public interface RuntimeElementInfo extends ElementInfo<Type, Class>, RuntimeElement {
  RuntimeClassInfo getScope();
  
  RuntimeElementPropertyInfo getProperty();
  
  Class<? extends JAXBElement> getType();
  
  RuntimeNonElement getContentType();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/runtime/RuntimeElementInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */