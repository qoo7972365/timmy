/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.ImmutableDescriptor;
/*     */ import javax.management.IntrospectionException;
/*     */ import javax.management.MBeanAttributeInfo;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanOperationInfo;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.NotificationBroadcaster;
/*     */ import javax.management.NotificationBroadcasterSupport;
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
/*     */ class StandardMBeanIntrospector
/*     */   extends MBeanIntrospector<Method>
/*     */ {
/*  47 */   private static final StandardMBeanIntrospector instance = new StandardMBeanIntrospector();
/*     */ 
/*     */   
/*     */   static StandardMBeanIntrospector getInstance() {
/*  51 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   MBeanIntrospector.PerInterfaceMap<Method> getPerInterfaceMap() {
/*  56 */     return perInterfaceMap;
/*     */   }
/*     */ 
/*     */   
/*     */   MBeanIntrospector.MBeanInfoMap getMBeanInfoMap() {
/*  61 */     return mbeanInfoMap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MBeanAnalyzer<Method> getAnalyzer(Class<?> paramClass) throws NotCompliantMBeanException {
/*  67 */     return MBeanAnalyzer.analyzer(paramClass, this);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isMXBean() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   Method mFrom(Method paramMethod) {
/*  77 */     return paramMethod;
/*     */   }
/*     */ 
/*     */   
/*     */   String getName(Method paramMethod) {
/*  82 */     return paramMethod.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   Type getGenericReturnType(Method paramMethod) {
/*  87 */     return paramMethod.getGenericReturnType();
/*     */   }
/*     */ 
/*     */   
/*     */   Type[] getGenericParameterTypes(Method paramMethod) {
/*  92 */     return paramMethod.getGenericParameterTypes();
/*     */   }
/*     */ 
/*     */   
/*     */   String[] getSignature(Method paramMethod) {
/*  97 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/*  98 */     String[] arrayOfString = new String[arrayOfClass.length];
/*  99 */     for (byte b = 0; b < arrayOfClass.length; b++)
/* 100 */       arrayOfString[b] = arrayOfClass[b].getName(); 
/* 101 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void checkMethod(Method paramMethod) {}
/*     */ 
/*     */ 
/*     */   
/*     */   Object invokeM2(Method paramMethod, Object paramObject1, Object[] paramArrayOfObject, Object paramObject2) throws InvocationTargetException, IllegalAccessException, MBeanException {
/* 112 */     return MethodUtil.invoke(paramMethod, paramObject1, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean validParameter(Method paramMethod, Object paramObject1, int paramInt, Object paramObject2) {
/* 117 */     return isValidParameter(paramMethod, paramObject1, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MBeanAttributeInfo getMBeanAttributeInfo(String paramString, Method paramMethod1, Method paramMethod2) {
/*     */     try {
/* 126 */       return new MBeanAttributeInfo(paramString, "Attribute exposed for management", paramMethod1, paramMethod2);
/*     */     }
/* 128 */     catch (IntrospectionException introspectionException) {
/* 129 */       throw new RuntimeException(introspectionException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MBeanOperationInfo getMBeanOperationInfo(String paramString, Method paramMethod) {
/* 137 */     return new MBeanOperationInfo("Operation exposed for management", paramMethod);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Descriptor getBasicMBeanDescriptor() {
/* 145 */     return ImmutableDescriptor.EMPTY_DESCRIPTOR;
/*     */   }
/*     */ 
/*     */   
/*     */   Descriptor getMBeanDescriptor(Class<?> paramClass) {
/* 150 */     boolean bool = isDefinitelyImmutableInfo(paramClass);
/* 151 */     return new ImmutableDescriptor(new String[] { "mxbean=false", "immutableInfo=" + bool });
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
/*     */   static boolean isDefinitelyImmutableInfo(Class<?> paramClass) {
/* 164 */     if (!NotificationBroadcaster.class.isAssignableFrom(paramClass))
/* 165 */       return true; 
/* 166 */     synchronized (definitelyImmutable) {
/* 167 */       Boolean bool = definitelyImmutable.get(paramClass);
/* 168 */       if (bool == null) {
/* 169 */         Class<NotificationBroadcasterSupport> clazz = NotificationBroadcasterSupport.class;
/*     */         
/* 171 */         if (clazz.isAssignableFrom(paramClass)) {
/*     */           try {
/* 173 */             Method method = paramClass.getMethod("getNotificationInfo", new Class[0]);
/* 174 */             bool = Boolean.valueOf((method.getDeclaringClass() == clazz));
/* 175 */           } catch (Exception exception) {
/*     */             
/* 177 */             return false;
/*     */           } 
/*     */         } else {
/* 180 */           bool = Boolean.valueOf(false);
/* 181 */         }  definitelyImmutable.put(paramClass, bool);
/*     */       } 
/* 183 */       return bool.booleanValue();
/*     */     } 
/*     */   }
/* 186 */   private static final WeakHashMap<Class<?>, Boolean> definitelyImmutable = new WeakHashMap<>();
/*     */ 
/*     */ 
/*     */   
/* 190 */   private static final MBeanIntrospector.PerInterfaceMap<Method> perInterfaceMap = new MBeanIntrospector.PerInterfaceMap<>();
/*     */   
/* 192 */   private static final MBeanIntrospector.MBeanInfoMap mbeanInfoMap = new MBeanIntrospector.MBeanInfoMap();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/StandardMBeanIntrospector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */