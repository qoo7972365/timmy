/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import com.sun.jmx.remote.util.EnvHelp;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.lang.reflect.UndeclaredThrowableException;
/*     */ import java.security.AccessController;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.management.AttributeNotFoundException;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.DescriptorKey;
/*     */ import javax.management.DynamicMBean;
/*     */ import javax.management.ImmutableDescriptor;
/*     */ import javax.management.MBeanInfo;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ import sun.reflect.misc.ReflectUtil;
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
/*     */ public class Introspector
/*     */ {
/*     */   public static final boolean ALLOW_NONPUBLIC_MBEAN;
/*     */   
/*     */   static {
/*  70 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("jdk.jmx.mbeans.allowNonPublic"));
/*  71 */     ALLOW_NONPUBLIC_MBEAN = Boolean.parseBoolean(str);
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
/*     */   public static final boolean isDynamic(Class<?> paramClass) {
/* 115 */     return DynamicMBean.class.isAssignableFrom(paramClass);
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
/*     */   
/*     */   public static void testCreation(Class<?> paramClass) throws NotCompliantMBeanException {
/* 135 */     int i = paramClass.getModifiers();
/* 136 */     if (Modifier.isAbstract(i) || Modifier.isInterface(i)) {
/* 137 */       throw new NotCompliantMBeanException("MBean class must be concrete");
/*     */     }
/*     */ 
/*     */     
/* 141 */     Constructor[] arrayOfConstructor = (Constructor[])paramClass.getConstructors();
/* 142 */     if (arrayOfConstructor.length == 0) {
/* 143 */       throw new NotCompliantMBeanException("MBean class must have public constructor");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkCompliance(Class<?> paramClass) throws NotCompliantMBeanException {
/* 151 */     if (DynamicMBean.class.isAssignableFrom(paramClass)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 157 */       getStandardMBeanInterface(paramClass);
/*     */       return;
/* 159 */     } catch (NotCompliantMBeanException notCompliantMBeanException2) {
/* 160 */       NotCompliantMBeanException notCompliantMBeanException1 = notCompliantMBeanException2;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 166 */         getMXBeanInterface(paramClass);
/*     */         return;
/* 168 */       } catch (NotCompliantMBeanException notCompliantMBeanException) {
/* 169 */         notCompliantMBeanException2 = notCompliantMBeanException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 175 */         String str = "MBean class " + paramClass.getName() + " does not implement DynamicMBean, and neither follows the Standard MBean conventions (" + notCompliantMBeanException1.toString() + ") nor the MXBean conventions (" + notCompliantMBeanException2.toString() + ")";
/* 176 */         throw new NotCompliantMBeanException(str);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public static <T> DynamicMBean makeDynamicMBean(T paramT) throws NotCompliantMBeanException {
/* 181 */     if (paramT instanceof DynamicMBean)
/* 182 */       return (DynamicMBean)paramT; 
/* 183 */     Class<?> clazz = paramT.getClass();
/* 184 */     Class<T> clazz1 = null;
/*     */     try {
/* 186 */       clazz1 = (Class)Util.<Class<?>>cast(getStandardMBeanInterface(clazz));
/* 187 */     } catch (NotCompliantMBeanException notCompliantMBeanException) {}
/*     */ 
/*     */ 
/*     */     
/* 191 */     if (clazz1 != null) {
/* 192 */       return new StandardMBeanSupport(paramT, clazz1);
/*     */     }
/*     */     try {
/* 195 */       clazz1 = Util.<Class<T>>cast(getMXBeanInterface(clazz));
/* 196 */     } catch (NotCompliantMBeanException notCompliantMBeanException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     if (clazz1 != null)
/* 202 */       return new MXBeanSupport(paramT, clazz1); 
/* 203 */     checkCompliance(clazz);
/* 204 */     throw new NotCompliantMBeanException("Not compliant");
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
/*     */ 
/*     */   
/*     */   public static MBeanInfo testCompliance(Class<?> paramClass) throws NotCompliantMBeanException {
/* 225 */     if (isDynamic(paramClass)) {
/* 226 */       return null;
/*     */     }
/* 228 */     return testCompliance(paramClass, null);
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
/*     */   public static void testComplianceMXBeanInterface(Class<?> paramClass) throws NotCompliantMBeanException {
/* 241 */     MXBeanIntrospector.getInstance().getAnalyzer(paramClass);
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
/*     */   public static void testComplianceMBeanInterface(Class<?> paramClass) throws NotCompliantMBeanException {
/* 254 */     StandardMBeanIntrospector.getInstance().getAnalyzer(paramClass);
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
/*     */ 
/*     */   
/*     */   public static synchronized MBeanInfo testCompliance(Class<?> paramClass1, Class<?> paramClass2) throws NotCompliantMBeanException {
/* 275 */     if (paramClass2 == null)
/* 276 */       paramClass2 = getStandardMBeanInterface(paramClass1); 
/* 277 */     ReflectUtil.checkPackageAccess(paramClass2);
/* 278 */     StandardMBeanIntrospector standardMBeanIntrospector = StandardMBeanIntrospector.getInstance();
/* 279 */     return getClassMBeanInfo(standardMBeanIntrospector, paramClass1, paramClass2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <M> MBeanInfo getClassMBeanInfo(MBeanIntrospector<M> paramMBeanIntrospector, Class<?> paramClass1, Class<?> paramClass2) throws NotCompliantMBeanException {
/* 286 */     PerInterface<M> perInterface = paramMBeanIntrospector.getPerInterface(paramClass2);
/* 287 */     return paramMBeanIntrospector.getClassMBeanInfo(paramClass1, perInterface);
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
/*     */   public static Class<?> getMBeanInterface(Class<?> paramClass) {
/* 304 */     if (isDynamic(paramClass)) return null; 
/*     */     try {
/* 306 */       return getStandardMBeanInterface(paramClass);
/* 307 */     } catch (NotCompliantMBeanException notCompliantMBeanException) {
/* 308 */       return null;
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
/*     */   public static <T> Class<? super T> getStandardMBeanInterface(Class<T> paramClass) throws NotCompliantMBeanException {
/* 324 */     Class<T> clazz = paramClass;
/* 325 */     Class<? super T> clazz1 = null;
/* 326 */     while (clazz != null) {
/*     */       
/* 328 */       clazz1 = findMBeanInterface(clazz, clazz.getName());
/* 329 */       if (clazz1 != null)
/* 330 */         break;  clazz = (Class)clazz.getSuperclass();
/*     */     } 
/* 332 */     if (clazz1 != null) {
/* 333 */       return clazz1;
/*     */     }
/*     */     
/* 336 */     String str = "Class " + paramClass.getName() + " is not a JMX compliant Standard MBean";
/*     */     
/* 338 */     throw new NotCompliantMBeanException(str);
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
/*     */   public static <T> Class<? super T> getMXBeanInterface(Class<T> paramClass) throws NotCompliantMBeanException {
/*     */     try {
/* 355 */       return MXBeanSupport.findMXBeanInterface(paramClass);
/* 356 */     } catch (Exception exception) {
/* 357 */       throw throwException(paramClass, exception);
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
/*     */   private static <T> Class<? super T> findMBeanInterface(Class<T> paramClass, String paramString) {
/* 374 */     Class<T> clazz = paramClass;
/* 375 */     while (clazz != null) {
/* 376 */       Class[] arrayOfClass = clazz.getInterfaces();
/* 377 */       int i = arrayOfClass.length;
/* 378 */       for (byte b = 0; b < i; b++) {
/* 379 */         Class<?> clazz1 = Util.<Class<?>>cast(arrayOfClass[b]);
/* 380 */         clazz1 = implementsMBean(clazz1, paramString);
/* 381 */         if (clazz1 != null) return (Class)clazz1; 
/*     */       } 
/* 383 */       clazz = (Class)clazz.getSuperclass();
/*     */     } 
/* 385 */     return null;
/*     */   }
/*     */   
/*     */   public static Descriptor descriptorForElement(AnnotatedElement paramAnnotatedElement) {
/* 389 */     if (paramAnnotatedElement == null)
/* 390 */       return ImmutableDescriptor.EMPTY_DESCRIPTOR; 
/* 391 */     Annotation[] arrayOfAnnotation = paramAnnotatedElement.getAnnotations();
/* 392 */     return descriptorForAnnotations(arrayOfAnnotation);
/*     */   }
/*     */   
/*     */   public static Descriptor descriptorForAnnotations(Annotation[] paramArrayOfAnnotation) {
/* 396 */     if (paramArrayOfAnnotation.length == 0)
/* 397 */       return ImmutableDescriptor.EMPTY_DESCRIPTOR; 
/* 398 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 399 */     for (Annotation annotation : paramArrayOfAnnotation) {
/* 400 */       Class<? extends Annotation> clazz = annotation.annotationType();
/* 401 */       Method[] arrayOfMethod = clazz.getMethods();
/* 402 */       boolean bool = false;
/* 403 */       for (Method method : arrayOfMethod) {
/* 404 */         DescriptorKey descriptorKey = method.<DescriptorKey>getAnnotation(DescriptorKey.class);
/* 405 */         if (descriptorKey != null) {
/* 406 */           String str = descriptorKey.value();
/*     */ 
/*     */           
/*     */           try {
/* 410 */             if (!bool) {
/* 411 */               ReflectUtil.checkPackageAccess(clazz);
/* 412 */               bool = true;
/*     */             } 
/* 414 */             object1 = MethodUtil.invoke(method, annotation, null);
/* 415 */           } catch (RuntimeException runtimeException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 421 */             throw runtimeException;
/* 422 */           } catch (Exception exception) {
/*     */             
/* 424 */             throw new UndeclaredThrowableException(exception);
/*     */           } 
/* 426 */           Object object1 = annotationToField(object1);
/* 427 */           Object object2 = hashMap.put(str, object1);
/* 428 */           if (object2 != null && !equals(object2, object1)) {
/* 429 */             String str1 = "Inconsistent values for descriptor field " + str + " from annotations: " + object1 + " :: " + object2;
/*     */ 
/*     */             
/* 432 */             throw new IllegalArgumentException(str1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 438 */     if (hashMap.isEmpty()) {
/* 439 */       return ImmutableDescriptor.EMPTY_DESCRIPTOR;
/*     */     }
/* 441 */     return new ImmutableDescriptor((Map)hashMap);
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
/*     */   static NotCompliantMBeanException throwException(Class<?> paramClass, Throwable paramThrowable) throws NotCompliantMBeanException, SecurityException {
/* 458 */     if (paramThrowable instanceof SecurityException)
/* 459 */       throw (SecurityException)paramThrowable; 
/* 460 */     if (paramThrowable instanceof NotCompliantMBeanException) {
/* 461 */       throw (NotCompliantMBeanException)paramThrowable;
/*     */     }
/* 463 */     String str1 = (paramClass == null) ? "null class" : paramClass.getName();
/*     */     
/* 465 */     String str2 = (paramThrowable == null) ? "Not compliant" : paramThrowable.getMessage();
/* 466 */     NotCompliantMBeanException notCompliantMBeanException = new NotCompliantMBeanException(str1 + ": " + str2);
/*     */     
/* 468 */     notCompliantMBeanException.initCause(paramThrowable);
/* 469 */     throw notCompliantMBeanException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object annotationToField(Object paramObject) {
/* 477 */     if (paramObject == null)
/* 478 */       return null; 
/* 479 */     if (paramObject instanceof Number || paramObject instanceof String || paramObject instanceof Character || paramObject instanceof Boolean || paramObject instanceof String[])
/*     */     {
/*     */       
/* 482 */       return paramObject;
/*     */     }
/*     */     
/* 485 */     Class<?> clazz = paramObject.getClass();
/* 486 */     if (clazz.isArray()) {
/* 487 */       if (clazz.getComponentType().isPrimitive())
/* 488 */         return paramObject; 
/* 489 */       Object[] arrayOfObject = (Object[])paramObject;
/* 490 */       String[] arrayOfString = new String[arrayOfObject.length];
/* 491 */       for (byte b = 0; b < arrayOfObject.length; b++)
/* 492 */         arrayOfString[b] = (String)annotationToField(arrayOfObject[b]); 
/* 493 */       return arrayOfString;
/*     */     } 
/* 495 */     if (paramObject instanceof Class)
/* 496 */       return ((Class)paramObject).getName(); 
/* 497 */     if (paramObject instanceof Enum) {
/* 498 */       return ((Enum)paramObject).name();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 505 */     if (Proxy.isProxyClass(clazz))
/* 506 */       clazz = clazz.getInterfaces()[0]; 
/* 507 */     throw new IllegalArgumentException("Illegal type for annotation element using @DescriptorKey: " + clazz
/* 508 */         .getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equals(Object paramObject1, Object paramObject2) {
/* 515 */     return Arrays.deepEquals(new Object[] { paramObject1 }, new Object[] { paramObject2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> Class<? super T> implementsMBean(Class<T> paramClass, String paramString) {
/* 525 */     String str = paramString + "MBean";
/* 526 */     if (paramClass.getName().equals(str)) {
/* 527 */       return paramClass;
/*     */     }
/* 529 */     Class[] arrayOfClass = paramClass.getInterfaces();
/* 530 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 531 */       if (arrayOfClass[b].getName().equals(str) && (
/* 532 */         Modifier.isPublic(arrayOfClass[b].getModifiers()) || ALLOW_NONPUBLIC_MBEAN))
/*     */       {
/* 534 */         return Util.<Class<? super T>>cast(arrayOfClass[b]);
/*     */       }
/*     */     } 
/*     */     
/* 538 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object elementFromComplex(Object paramObject, String paramString) throws AttributeNotFoundException {
/*     */     try {
/* 544 */       if (paramObject.getClass().isArray() && paramString.equals("length"))
/* 545 */         return Integer.valueOf(Array.getLength(paramObject)); 
/* 546 */       if (paramObject instanceof CompositeData) {
/* 547 */         return ((CompositeData)paramObject).get(paramString);
/*     */       }
/*     */ 
/*     */       
/* 551 */       Class<?> clazz = paramObject.getClass();
/* 552 */       Method method = null;
/* 553 */       if (BeansHelper.isAvailable()) {
/* 554 */         Object object = BeansHelper.getBeanInfo(clazz);
/* 555 */         Object[] arrayOfObject = BeansHelper.getPropertyDescriptors(object);
/* 556 */         for (Object object1 : arrayOfObject) {
/* 557 */           if (BeansHelper.getPropertyName(object1).equals(paramString)) {
/* 558 */             method = BeansHelper.getReadMethod(object1);
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 565 */         method = SimpleIntrospector.getReadMethod(clazz, paramString);
/*     */       } 
/* 567 */       if (method != null) {
/* 568 */         ReflectUtil.checkPackageAccess(method.getDeclaringClass());
/* 569 */         return MethodUtil.invoke(method, paramObject, (Object[])new Class[0]);
/*     */       } 
/*     */       
/* 572 */       throw new AttributeNotFoundException("Could not find the getter method for the property " + paramString + " using the Java Beans introspector");
/*     */ 
/*     */     
/*     */     }
/* 576 */     catch (InvocationTargetException invocationTargetException) {
/* 577 */       throw new IllegalArgumentException(invocationTargetException);
/* 578 */     } catch (AttributeNotFoundException attributeNotFoundException) {
/* 579 */       throw attributeNotFoundException;
/* 580 */     } catch (Exception exception) {
/* 581 */       throw (AttributeNotFoundException)EnvHelp.initCause(new AttributeNotFoundException(exception
/* 582 */             .getMessage()), exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SimpleIntrospector
/*     */   {
/*     */     private static final String GET_METHOD_PREFIX = "get";
/*     */ 
/*     */ 
/*     */     
/*     */     private static final String IS_METHOD_PREFIX = "is";
/*     */ 
/*     */ 
/*     */     
/* 600 */     private static final Map<Class<?>, SoftReference<List<Method>>> cache = Collections.synchronizedMap(new WeakHashMap<>());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static List<Method> getCachedMethods(Class<?> param1Class) {
/* 609 */       SoftReference<List> softReference = (SoftReference)cache.get(param1Class);
/* 610 */       if (softReference != null) {
/* 611 */         List<Method> list = softReference.get();
/* 612 */         if (list != null)
/* 613 */           return list; 
/*     */       } 
/* 615 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static boolean isReadMethod(Method param1Method) {
/* 625 */       int i = param1Method.getModifiers();
/* 626 */       if (Modifier.isStatic(i)) {
/* 627 */         return false;
/*     */       }
/* 629 */       String str = param1Method.getName();
/* 630 */       Class[] arrayOfClass = param1Method.getParameterTypes();
/* 631 */       int j = arrayOfClass.length;
/*     */       
/* 633 */       if (j == 0 && str.length() > 2) {
/*     */         
/* 635 */         if (str.startsWith("is")) {
/* 636 */           return (param1Method.getReturnType() == boolean.class);
/*     */         }
/* 638 */         if (str.length() > 3 && str.startsWith("get"))
/* 639 */           return (param1Method.getReturnType() != void.class); 
/*     */       } 
/* 641 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static List<Method> getReadMethods(Class<?> param1Class) {
/* 651 */       List<Method> list1 = getCachedMethods(param1Class);
/* 652 */       if (list1 != null) {
/* 653 */         return list1;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 658 */       List<Method> list2 = StandardMBeanIntrospector.getInstance().getMethods(param1Class);
/* 659 */       list2 = MBeanAnalyzer.eliminateCovariantMethods(list2);
/*     */ 
/*     */       
/* 662 */       LinkedList<Method> linkedList = new LinkedList();
/* 663 */       for (Method method : list2) {
/* 664 */         if (isReadMethod(method)) {
/*     */           
/* 666 */           if (method.getName().startsWith("is")) {
/* 667 */             linkedList.add(0, method); continue;
/*     */           } 
/* 669 */           linkedList.add(method);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 675 */       cache.put(param1Class, new SoftReference<>(linkedList));
/*     */       
/* 677 */       return linkedList;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Method getReadMethod(Class<?> param1Class, String param1String) {
/* 687 */       param1String = param1String.substring(0, 1).toUpperCase(Locale.ENGLISH) + param1String.substring(1);
/* 688 */       String str1 = "get" + param1String;
/* 689 */       String str2 = "is" + param1String;
/* 690 */       for (Method method : getReadMethods(param1Class)) {
/* 691 */         String str = method.getName();
/* 692 */         if (str.equals(str2) || str.equals(str1)) {
/* 693 */           return method;
/*     */         }
/*     */       } 
/* 696 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class BeansHelper
/*     */   {
/* 706 */     private static final Class<?> introspectorClass = getClass("java.beans.Introspector");
/* 707 */     private static final Class<?> beanInfoClass = (introspectorClass == null) ? null : 
/* 708 */       getClass("java.beans.BeanInfo");
/* 709 */     private static final Class<?> getPropertyDescriptorClass = (beanInfoClass == null) ? null : 
/* 710 */       getClass("java.beans.PropertyDescriptor");
/*     */ 
/*     */     
/* 713 */     private static final Method getBeanInfo = getMethod(introspectorClass, "getBeanInfo", new Class[] { Class.class });
/*     */     
/* 715 */     private static final Method getPropertyDescriptors = getMethod(beanInfoClass, "getPropertyDescriptors", new Class[0]);
/*     */     
/* 717 */     private static final Method getPropertyName = getMethod(getPropertyDescriptorClass, "getName", new Class[0]);
/*     */     
/* 719 */     private static final Method getReadMethod = getMethod(getPropertyDescriptorClass, "getReadMethod", new Class[0]);
/*     */     
/*     */     private static Class<?> getClass(String param1String) {
/*     */       try {
/* 723 */         return Class.forName(param1String, true, null);
/* 724 */       } catch (ClassNotFoundException classNotFoundException) {
/* 725 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static Method getMethod(Class<?> param1Class, String param1String, Class<?>... param1VarArgs) {
/* 732 */       if (param1Class != null) {
/*     */         try {
/* 734 */           return param1Class.getMethod(param1String, param1VarArgs);
/* 735 */         } catch (NoSuchMethodException noSuchMethodException) {
/* 736 */           throw new AssertionError(noSuchMethodException);
/*     */         } 
/*     */       }
/* 739 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static boolean isAvailable() {
/* 749 */       return (introspectorClass != null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Object getBeanInfo(Class<?> param1Class) throws Exception {
/*     */       try {
/* 757 */         return getBeanInfo.invoke(null, new Object[] { param1Class });
/* 758 */       } catch (InvocationTargetException invocationTargetException) {
/* 759 */         Throwable throwable = invocationTargetException.getCause();
/* 760 */         if (throwable instanceof Exception)
/* 761 */           throw (Exception)throwable; 
/* 762 */         throw new AssertionError(invocationTargetException);
/* 763 */       } catch (IllegalAccessException illegalAccessException) {
/* 764 */         throw new AssertionError(illegalAccessException);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Object[] getPropertyDescriptors(Object param1Object) {
/*     */       try {
/* 773 */         return (Object[])getPropertyDescriptors.invoke(param1Object, new Object[0]);
/* 774 */       } catch (InvocationTargetException invocationTargetException) {
/* 775 */         Throwable throwable = invocationTargetException.getCause();
/* 776 */         if (throwable instanceof RuntimeException)
/* 777 */           throw (RuntimeException)throwable; 
/* 778 */         throw new AssertionError(invocationTargetException);
/* 779 */       } catch (IllegalAccessException illegalAccessException) {
/* 780 */         throw new AssertionError(illegalAccessException);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static String getPropertyName(Object param1Object) {
/*     */       try {
/* 789 */         return (String)getPropertyName.invoke(param1Object, new Object[0]);
/* 790 */       } catch (InvocationTargetException invocationTargetException) {
/* 791 */         Throwable throwable = invocationTargetException.getCause();
/* 792 */         if (throwable instanceof RuntimeException)
/* 793 */           throw (RuntimeException)throwable; 
/* 794 */         throw new AssertionError(invocationTargetException);
/* 795 */       } catch (IllegalAccessException illegalAccessException) {
/* 796 */         throw new AssertionError(illegalAccessException);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Method getReadMethod(Object param1Object) {
/*     */       try {
/* 805 */         return (Method)getReadMethod.invoke(param1Object, new Object[0]);
/* 806 */       } catch (InvocationTargetException invocationTargetException) {
/* 807 */         Throwable throwable = invocationTargetException.getCause();
/* 808 */         if (throwable instanceof RuntimeException)
/* 809 */           throw (RuntimeException)throwable; 
/* 810 */         throw new AssertionError(invocationTargetException);
/* 811 */       } catch (IllegalAccessException illegalAccessException) {
/* 812 */         throw new AssertionError(illegalAccessException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/Introspector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */