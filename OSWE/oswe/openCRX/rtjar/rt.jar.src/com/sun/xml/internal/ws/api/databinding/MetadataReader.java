package com.sun.xml.internal.ws.api.databinding;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public interface MetadataReader {
  Annotation[] getAnnotations(Method paramMethod);
  
  Annotation[][] getParameterAnnotations(Method paramMethod);
  
  <A extends Annotation> A getAnnotation(Class<A> paramClass, Method paramMethod);
  
  <A extends Annotation> A getAnnotation(Class<A> paramClass, Class<?> paramClass1);
  
  Annotation[] getAnnotations(Class<?> paramClass);
  
  void getProperties(Map<String, Object> paramMap, Class<?> paramClass);
  
  void getProperties(Map<String, Object> paramMap, Method paramMethod);
  
  void getProperties(Map<String, Object> paramMap, Method paramMethod, int paramInt);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/databinding/MetadataReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */