package com.sun.xml.internal.bind.v2.model.impl;

import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
import com.sun.xml.internal.bind.v2.model.nav.Navigator;

public interface ModelBuilderI<T, C, F, M> {
  Navigator<T, C, F, M> getNavigator();
  
  AnnotationReader<T, C, F, M> getReader();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/ModelBuilderI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */