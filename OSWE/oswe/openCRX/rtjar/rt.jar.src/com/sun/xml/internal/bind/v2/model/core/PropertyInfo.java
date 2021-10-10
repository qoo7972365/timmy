package com.sun.xml.internal.bind.v2.model.core;

import com.sun.istack.internal.Nullable;
import com.sun.xml.internal.bind.v2.model.annotation.AnnotationSource;
import java.util.Collection;
import javax.activation.MimeType;
import javax.xml.namespace.QName;

public interface PropertyInfo<T, C> extends AnnotationSource {
  TypeInfo<T, C> parent();
  
  String getName();
  
  String displayName();
  
  boolean isCollection();
  
  Collection<? extends TypeInfo<T, C>> ref();
  
  PropertyKind kind();
  
  Adapter<T, C> getAdapter();
  
  ID id();
  
  MimeType getExpectedMimeType();
  
  boolean inlineBinaryData();
  
  @Nullable
  QName getSchemaType();
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/core/PropertyInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */