/*     */ package com.sun.beans.decoder;
/*     */ 
/*     */ import com.sun.beans.finder.MethodFinder;
/*     */ import java.beans.IndexedPropertyDescriptor;
/*     */ import java.beans.IntrospectionException;
/*     */ import java.beans.Introspector;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PropertyElementHandler
/*     */   extends AccessorElementHandler
/*     */ {
/*     */   static final String GETTER = "get";
/*     */   static final String SETTER = "set";
/*     */   private Integer index;
/*     */   
/*     */   public void addAttribute(String paramString1, String paramString2) {
/* 103 */     if (paramString1.equals("index")) {
/* 104 */       this.index = Integer.valueOf(paramString2);
/*     */     } else {
/* 106 */       super.addAttribute(paramString1, paramString2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isArgument() {
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getValue(String paramString) {
/*     */     try {
/* 132 */       return getPropertyValue(getContextBean(), paramString, this.index);
/*     */     }
/* 134 */     catch (Exception exception) {
/* 135 */       getOwner().handleException(exception);
/*     */       
/* 137 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setValue(String paramString, Object paramObject) {
/*     */     try {
/* 149 */       setPropertyValue(getContextBean(), paramString, this.index, paramObject);
/*     */     }
/* 151 */     catch (Exception exception) {
/* 152 */       getOwner().handleException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object getPropertyValue(Object paramObject, String paramString, Integer paramInteger) throws IllegalAccessException, IntrospectionException, InvocationTargetException, NoSuchMethodException {
/* 171 */     Class<?> clazz = paramObject.getClass();
/* 172 */     if (paramInteger == null)
/* 173 */       return MethodUtil.invoke(findGetter(clazz, paramString, new Class[0]), paramObject, new Object[0]); 
/* 174 */     if (clazz.isArray() && paramString == null) {
/* 175 */       return Array.get(paramObject, paramInteger.intValue());
/*     */     }
/* 177 */     return MethodUtil.invoke(findGetter(clazz, paramString, new Class[] { int.class }), paramObject, new Object[] { paramInteger });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setPropertyValue(Object paramObject1, String paramString, Integer paramInteger, Object paramObject2) throws IllegalAccessException, IntrospectionException, InvocationTargetException, NoSuchMethodException {
/* 196 */     Class<?> clazz1 = paramObject1.getClass();
/*     */     
/* 198 */     Class<?> clazz2 = (paramObject2 != null) ? paramObject2.getClass() : null;
/*     */ 
/*     */     
/* 201 */     if (paramInteger == null) {
/* 202 */       MethodUtil.invoke(findSetter(clazz1, paramString, new Class[] { clazz2 }), paramObject1, new Object[] { paramObject2 });
/* 203 */     } else if (clazz1.isArray() && paramString == null) {
/* 204 */       Array.set(paramObject1, paramInteger.intValue(), paramObject2);
/*     */     } else {
/* 206 */       MethodUtil.invoke(findSetter(clazz1, paramString, new Class[] { int.class, clazz2 }), paramObject1, new Object[] { paramInteger, paramObject2 });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Method findGetter(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) throws IntrospectionException, NoSuchMethodException {
/* 222 */     if (paramString == null) {
/* 223 */       return MethodFinder.findInstanceMethod(paramClass, "get", paramVarArgs);
/*     */     }
/* 225 */     PropertyDescriptor propertyDescriptor = getProperty(paramClass, paramString);
/* 226 */     if (paramVarArgs.length == 0) {
/* 227 */       Method method = propertyDescriptor.getReadMethod();
/* 228 */       if (method != null) {
/* 229 */         return method;
/*     */       }
/* 231 */     } else if (propertyDescriptor instanceof IndexedPropertyDescriptor) {
/* 232 */       IndexedPropertyDescriptor indexedPropertyDescriptor = (IndexedPropertyDescriptor)propertyDescriptor;
/* 233 */       Method method = indexedPropertyDescriptor.getIndexedReadMethod();
/* 234 */       if (method != null) {
/* 235 */         return method;
/*     */       }
/*     */     } 
/* 238 */     throw new IntrospectionException("Could not find getter for the " + paramString + " property");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Method findSetter(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) throws IntrospectionException, NoSuchMethodException {
/* 253 */     if (paramString == null) {
/* 254 */       return MethodFinder.findInstanceMethod(paramClass, "set", paramVarArgs);
/*     */     }
/* 256 */     PropertyDescriptor propertyDescriptor = getProperty(paramClass, paramString);
/* 257 */     if (paramVarArgs.length == 1) {
/* 258 */       Method method = propertyDescriptor.getWriteMethod();
/* 259 */       if (method != null) {
/* 260 */         return method;
/*     */       }
/* 262 */     } else if (propertyDescriptor instanceof IndexedPropertyDescriptor) {
/* 263 */       IndexedPropertyDescriptor indexedPropertyDescriptor = (IndexedPropertyDescriptor)propertyDescriptor;
/* 264 */       Method method = indexedPropertyDescriptor.getIndexedWriteMethod();
/* 265 */       if (method != null) {
/* 266 */         return method;
/*     */       }
/*     */     } 
/* 269 */     throw new IntrospectionException("Could not find setter for the " + paramString + " property");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PropertyDescriptor getProperty(Class<?> paramClass, String paramString) throws IntrospectionException {
/* 282 */     for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(paramClass).getPropertyDescriptors()) {
/* 283 */       if (paramString.equals(propertyDescriptor.getName())) {
/* 284 */         return propertyDescriptor;
/*     */       }
/*     */     } 
/* 287 */     throw new IntrospectionException("Could not find the " + paramString + " property descriptor");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/decoder/PropertyElementHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */