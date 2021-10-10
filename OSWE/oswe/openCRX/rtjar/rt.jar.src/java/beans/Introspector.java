/*      */ package java.beans;
/*      */ 
/*      */ import com.sun.beans.TypeResolver;
/*      */ import com.sun.beans.WeakCache;
/*      */ import com.sun.beans.finder.ClassFinder;
/*      */ import com.sun.beans.finder.MethodFinder;
/*      */ import java.awt.Component;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.ArrayList;
/*      */ import java.util.EventListener;
/*      */ import java.util.EventObject;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TooManyListenersException;
/*      */ import java.util.TreeMap;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Introspector
/*      */ {
/*      */   public static final int USE_ALL_BEANINFO = 1;
/*      */   public static final int IGNORE_IMMEDIATE_BEANINFO = 2;
/*      */   public static final int IGNORE_ALL_BEANINFO = 3;
/*  110 */   private static final WeakCache<Class<?>, Method[]> declaredMethodCache = (WeakCache)new WeakCache<>();
/*      */   
/*      */   private Class<?> beanClass;
/*      */   
/*      */   private BeanInfo explicitBeanInfo;
/*      */   private BeanInfo superBeanInfo;
/*      */   private BeanInfo[] additionalBeanInfo;
/*      */   private boolean propertyChangeSource = false;
/*  118 */   private static Class<EventListener> eventListenerType = EventListener.class;
/*      */   
/*      */   private String defaultEventName;
/*      */   
/*      */   private String defaultPropertyName;
/*  123 */   private int defaultEventIndex = -1;
/*  124 */   private int defaultPropertyIndex = -1;
/*      */ 
/*      */   
/*      */   private Map<String, MethodDescriptor> methods;
/*      */ 
/*      */   
/*      */   private Map<String, PropertyDescriptor> properties;
/*      */ 
/*      */   
/*      */   private Map<String, EventSetDescriptor> events;
/*      */   
/*  135 */   private static final EventSetDescriptor[] EMPTY_EVENTSETDESCRIPTORS = new EventSetDescriptor[0];
/*      */ 
/*      */ 
/*      */   
/*      */   static final String ADD_PREFIX = "add";
/*      */ 
/*      */ 
/*      */   
/*      */   static final String REMOVE_PREFIX = "remove";
/*      */ 
/*      */ 
/*      */   
/*      */   static final String GET_PREFIX = "get";
/*      */ 
/*      */ 
/*      */   
/*      */   static final String SET_PREFIX = "set";
/*      */ 
/*      */ 
/*      */   
/*      */   static final String IS_PREFIX = "is";
/*      */ 
/*      */   
/*      */   private HashMap<String, List<PropertyDescriptor>> pdStore;
/*      */ 
/*      */ 
/*      */   
/*      */   public static BeanInfo getBeanInfo(Class<?> paramClass) throws IntrospectionException {
/*      */     BeanInfo beanInfo;
/*  164 */     if (!ReflectUtil.isPackageAccessible(paramClass)) {
/*  165 */       return (new Introspector(paramClass, null, 1)).getBeanInfo();
/*      */     }
/*  167 */     ThreadGroupContext threadGroupContext = ThreadGroupContext.getContext();
/*      */     
/*  169 */     synchronized (declaredMethodCache) {
/*  170 */       beanInfo = threadGroupContext.getBeanInfo(paramClass);
/*      */     } 
/*  172 */     if (beanInfo == null) {
/*  173 */       beanInfo = (new Introspector(paramClass, null, 1)).getBeanInfo();
/*  174 */       synchronized (declaredMethodCache) {
/*  175 */         threadGroupContext.putBeanInfo(paramClass, beanInfo);
/*      */       } 
/*      */     } 
/*  178 */     return beanInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BeanInfo getBeanInfo(Class<?> paramClass, int paramInt) throws IntrospectionException {
/*  204 */     return getBeanInfo(paramClass, null, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BeanInfo getBeanInfo(Class<?> paramClass1, Class<?> paramClass2) throws IntrospectionException {
/*  224 */     return getBeanInfo(paramClass1, paramClass2, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BeanInfo getBeanInfo(Class<?> paramClass1, Class<?> paramClass2, int paramInt) throws IntrospectionException {
/*      */     BeanInfo beanInfo;
/*  258 */     if (paramClass2 == null && paramInt == 1) {
/*      */       
/*  260 */       beanInfo = getBeanInfo(paramClass1);
/*      */     } else {
/*  262 */       beanInfo = (new Introspector(paramClass1, paramClass2, paramInt)).getBeanInfo();
/*      */     } 
/*  264 */     return beanInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String decapitalize(String paramString) {
/*  285 */     if (paramString == null || paramString.length() == 0) {
/*  286 */       return paramString;
/*      */     }
/*  288 */     if (paramString.length() > 1 && Character.isUpperCase(paramString.charAt(1)) && 
/*  289 */       Character.isUpperCase(paramString.charAt(0))) {
/*  290 */       return paramString;
/*      */     }
/*  292 */     char[] arrayOfChar = paramString.toCharArray();
/*  293 */     arrayOfChar[0] = Character.toLowerCase(arrayOfChar[0]);
/*  294 */     return new String(arrayOfChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getBeanInfoSearchPath() {
/*  308 */     return ThreadGroupContext.getContext().getBeanInfoFinder().getPackages();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setBeanInfoSearchPath(String[] paramArrayOfString) {
/*  328 */     SecurityManager securityManager = System.getSecurityManager();
/*  329 */     if (securityManager != null) {
/*  330 */       securityManager.checkPropertiesAccess();
/*      */     }
/*  332 */     ThreadGroupContext.getContext().getBeanInfoFinder().setPackages(paramArrayOfString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushCaches() {
/*  344 */     synchronized (declaredMethodCache) {
/*  345 */       ThreadGroupContext.getContext().clearBeanInfoCache();
/*  346 */       declaredMethodCache.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void flushFromCaches(Class<?> paramClass) {
/*  366 */     if (paramClass == null) {
/*  367 */       throw new NullPointerException();
/*      */     }
/*  369 */     synchronized (declaredMethodCache) {
/*  370 */       ThreadGroupContext.getContext().removeBeanInfo(paramClass);
/*  371 */       declaredMethodCache.put(paramClass, null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BeanInfo getBeanInfo() throws IntrospectionException {
/*  425 */     BeanDescriptor beanDescriptor = getTargetBeanDescriptor();
/*  426 */     MethodDescriptor[] arrayOfMethodDescriptor = getTargetMethodInfo();
/*  427 */     EventSetDescriptor[] arrayOfEventSetDescriptor = getTargetEventInfo();
/*  428 */     PropertyDescriptor[] arrayOfPropertyDescriptor = getTargetPropertyInfo();
/*      */     
/*  430 */     int i = getTargetDefaultEventIndex();
/*  431 */     int j = getTargetDefaultPropertyIndex();
/*      */     
/*  433 */     return new GenericBeanInfo(beanDescriptor, arrayOfEventSetDescriptor, i, arrayOfPropertyDescriptor, j, arrayOfMethodDescriptor, this.explicitBeanInfo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BeanInfo findExplicitBeanInfo(Class<?> paramClass) {
/*  448 */     return (BeanInfo)ThreadGroupContext.getContext().getBeanInfoFinder().find(paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyDescriptor[] getTargetPropertyInfo() {
/*  460 */     PropertyDescriptor[] arrayOfPropertyDescriptor1 = null;
/*  461 */     if (this.explicitBeanInfo != null) {
/*  462 */       arrayOfPropertyDescriptor1 = getPropertyDescriptors(this.explicitBeanInfo);
/*      */     }
/*      */     
/*  465 */     if (arrayOfPropertyDescriptor1 == null && this.superBeanInfo != null)
/*      */     {
/*  467 */       addPropertyDescriptors(getPropertyDescriptors(this.superBeanInfo));
/*      */     }
/*      */     
/*  470 */     for (byte b = 0; b < this.additionalBeanInfo.length; b++) {
/*  471 */       addPropertyDescriptors(this.additionalBeanInfo[b].getPropertyDescriptors());
/*      */     }
/*      */     
/*  474 */     if (arrayOfPropertyDescriptor1 != null) {
/*      */       
/*  476 */       addPropertyDescriptors(arrayOfPropertyDescriptor1);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  483 */       Method[] arrayOfMethod = getPublicDeclaredMethods(this.beanClass);
/*      */ 
/*      */       
/*  486 */       for (byte b1 = 0; b1 < arrayOfMethod.length; b1++) {
/*  487 */         Method method = arrayOfMethod[b1];
/*  488 */         if (method != null) {
/*      */ 
/*      */ 
/*      */           
/*  492 */           int i = method.getModifiers();
/*  493 */           if (!Modifier.isStatic(i)) {
/*      */ 
/*      */             
/*  496 */             String str = method.getName();
/*  497 */             Class[] arrayOfClass = method.getParameterTypes();
/*  498 */             Class<?> clazz = method.getReturnType();
/*  499 */             int j = arrayOfClass.length;
/*  500 */             PropertyDescriptor propertyDescriptor = null;
/*      */             
/*  502 */             if (str.length() > 3 || str.startsWith("is"))
/*      */             
/*      */             { 
/*      */               try {
/*      */ 
/*      */ 
/*      */                 
/*  509 */                 if (j == 0) {
/*  510 */                   if (str.startsWith("get")) {
/*      */                     
/*  512 */                     propertyDescriptor = new PropertyDescriptor(this.beanClass, str.substring(3), method, null);
/*  513 */                   } else if (clazz == boolean.class && str.startsWith("is")) {
/*      */                     
/*  515 */                     propertyDescriptor = new PropertyDescriptor(this.beanClass, str.substring(2), method, null);
/*      */                   } 
/*  517 */                 } else if (j == 1) {
/*  518 */                   if (int.class.equals(arrayOfClass[0]) && str.startsWith("get")) {
/*  519 */                     propertyDescriptor = new IndexedPropertyDescriptor(this.beanClass, str.substring(3), null, null, method, null);
/*  520 */                   } else if (void.class.equals(clazz) && str.startsWith("set")) {
/*      */                     
/*  522 */                     propertyDescriptor = new PropertyDescriptor(this.beanClass, str.substring(3), null, method);
/*  523 */                     if (throwsException(method, PropertyVetoException.class)) {
/*  524 */                       propertyDescriptor.setConstrained(true);
/*      */                     }
/*      */                   } 
/*  527 */                 } else if (j == 2 && 
/*  528 */                   void.class.equals(clazz) && int.class.equals(arrayOfClass[0]) && str.startsWith("set")) {
/*  529 */                   propertyDescriptor = new IndexedPropertyDescriptor(this.beanClass, str.substring(3), null, null, null, method);
/*  530 */                   if (throwsException(method, PropertyVetoException.class)) {
/*  531 */                     propertyDescriptor.setConstrained(true);
/*      */                   }
/*      */                 }
/*      */               
/*  535 */               } catch (IntrospectionException introspectionException) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  540 */                 propertyDescriptor = null;
/*      */               } 
/*      */               
/*  543 */               if (propertyDescriptor != null)
/*      */               
/*      */               { 
/*  546 */                 if (this.propertyChangeSource) {
/*  547 */                   propertyDescriptor.setBound(true);
/*      */                 }
/*  549 */                 addPropertyDescriptor(propertyDescriptor); }  } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  553 */     }  processPropertyDescriptors();
/*      */ 
/*      */ 
/*      */     
/*  557 */     PropertyDescriptor[] arrayOfPropertyDescriptor2 = (PropertyDescriptor[])this.properties.values().toArray((Object[])new PropertyDescriptor[this.properties.size()]);
/*      */ 
/*      */     
/*  560 */     if (this.defaultPropertyName != null) {
/*  561 */       for (byte b1 = 0; b1 < arrayOfPropertyDescriptor2.length; b1++) {
/*  562 */         if (this.defaultPropertyName.equals(arrayOfPropertyDescriptor2[b1].getName())) {
/*  563 */           this.defaultPropertyIndex = b1;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  568 */     return arrayOfPropertyDescriptor2;
/*      */   }
/*      */   
/*  571 */   private Introspector(Class<?> paramClass1, Class<?> paramClass2, int paramInt) throws IntrospectionException { this.pdStore = new HashMap<>(); this.beanClass = paramClass1; if (paramClass2 != null) { boolean bool = false; for (Class<?> clazz1 = paramClass1.getSuperclass(); clazz1 != null; clazz1 = clazz1.getSuperclass()) { if (clazz1 == paramClass2)
/*      */           bool = true;  }  if (!bool)
/*      */         throw new IntrospectionException(paramClass2.getName() + " not superclass of " + paramClass1.getName());  }  if (paramInt == 1)
/*      */       this.explicitBeanInfo = findExplicitBeanInfo(paramClass1);  Class<?> clazz = paramClass1.getSuperclass(); if (clazz != paramClass2) { int i = paramInt; if (i == 2)
/*      */         i = 1;  this.superBeanInfo = getBeanInfo(clazz, paramClass2, i); }  if (this.explicitBeanInfo != null)
/*      */       this.additionalBeanInfo = this.explicitBeanInfo.getAdditionalBeanInfo();  if (this.additionalBeanInfo == null)
/*  577 */       this.additionalBeanInfo = new BeanInfo[0];  } private void addPropertyDescriptor(PropertyDescriptor paramPropertyDescriptor) { String str = paramPropertyDescriptor.getName();
/*  578 */     List<PropertyDescriptor> list = this.pdStore.get(str);
/*  579 */     if (list == null) {
/*  580 */       list = new ArrayList();
/*  581 */       this.pdStore.put(str, list);
/*      */     } 
/*  583 */     if (this.beanClass != paramPropertyDescriptor.getClass0()) {
/*      */ 
/*      */ 
/*      */       
/*  587 */       Method method1 = paramPropertyDescriptor.getReadMethod();
/*  588 */       Method method2 = paramPropertyDescriptor.getWriteMethod();
/*  589 */       boolean bool = true;
/*  590 */       if (method1 != null) bool = (bool && method1.getGenericReturnType() instanceof Class) ? true : false; 
/*  591 */       if (method2 != null) bool = (bool && method2.getGenericParameterTypes()[0] instanceof Class) ? true : false; 
/*  592 */       if (paramPropertyDescriptor instanceof IndexedPropertyDescriptor) {
/*  593 */         IndexedPropertyDescriptor indexedPropertyDescriptor = (IndexedPropertyDescriptor)paramPropertyDescriptor;
/*  594 */         Method method3 = indexedPropertyDescriptor.getIndexedReadMethod();
/*  595 */         Method method4 = indexedPropertyDescriptor.getIndexedWriteMethod();
/*  596 */         if (method3 != null) bool = (bool && method3.getGenericReturnType() instanceof Class) ? true : false; 
/*  597 */         if (method4 != null) bool = (bool && method4.getGenericParameterTypes()[1] instanceof Class) ? true : false; 
/*  598 */         if (!bool) {
/*  599 */           paramPropertyDescriptor = new IndexedPropertyDescriptor(indexedPropertyDescriptor);
/*  600 */           paramPropertyDescriptor.updateGenericsFor(this.beanClass);
/*      */         }
/*      */       
/*  603 */       } else if (!bool) {
/*  604 */         paramPropertyDescriptor = new PropertyDescriptor(paramPropertyDescriptor);
/*  605 */         paramPropertyDescriptor.updateGenericsFor(this.beanClass);
/*      */       } 
/*      */     } 
/*  608 */     list.add(paramPropertyDescriptor); }
/*      */ 
/*      */   
/*      */   private void addPropertyDescriptors(PropertyDescriptor[] paramArrayOfPropertyDescriptor) {
/*  612 */     if (paramArrayOfPropertyDescriptor != null) {
/*  613 */       for (PropertyDescriptor propertyDescriptor : paramArrayOfPropertyDescriptor) {
/*  614 */         addPropertyDescriptor(propertyDescriptor);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private PropertyDescriptor[] getPropertyDescriptors(BeanInfo paramBeanInfo) {
/*  620 */     PropertyDescriptor[] arrayOfPropertyDescriptor = paramBeanInfo.getPropertyDescriptors();
/*  621 */     int i = paramBeanInfo.getDefaultPropertyIndex();
/*  622 */     if (0 <= i && i < arrayOfPropertyDescriptor.length) {
/*  623 */       this.defaultPropertyName = arrayOfPropertyDescriptor[i].getName();
/*      */     }
/*  625 */     return arrayOfPropertyDescriptor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processPropertyDescriptors() {
/*  633 */     if (this.properties == null) {
/*  634 */       this.properties = new TreeMap<>();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  642 */     Iterator<List> iterator = this.pdStore.values().iterator();
/*  643 */     while (iterator.hasNext()) {
/*  644 */       PropertyDescriptor propertyDescriptor1 = null, propertyDescriptor2 = null, propertyDescriptor3 = null;
/*  645 */       IndexedPropertyDescriptor indexedPropertyDescriptor1 = null, indexedPropertyDescriptor2 = null, indexedPropertyDescriptor3 = null;
/*      */       
/*  647 */       List<PropertyDescriptor> list = iterator.next();
/*      */       
/*      */       byte b;
/*      */       
/*  651 */       for (b = 0; b < list.size(); b++) {
/*  652 */         propertyDescriptor1 = list.get(b);
/*  653 */         if (propertyDescriptor1 instanceof IndexedPropertyDescriptor) {
/*  654 */           indexedPropertyDescriptor1 = (IndexedPropertyDescriptor)propertyDescriptor1;
/*  655 */           if (indexedPropertyDescriptor1.getIndexedReadMethod() != null) {
/*  656 */             if (indexedPropertyDescriptor2 != null) {
/*  657 */               indexedPropertyDescriptor2 = new IndexedPropertyDescriptor(indexedPropertyDescriptor2, indexedPropertyDescriptor1);
/*      */             } else {
/*  659 */               indexedPropertyDescriptor2 = indexedPropertyDescriptor1;
/*      */             }
/*      */           
/*      */           }
/*  663 */         } else if (propertyDescriptor1.getReadMethod() != null) {
/*  664 */           String str = propertyDescriptor1.getReadMethod().getName();
/*  665 */           if (propertyDescriptor2 != null) {
/*      */ 
/*      */             
/*  668 */             String str1 = propertyDescriptor2.getReadMethod().getName();
/*  669 */             if (str1.equals(str) || !str1.startsWith("is")) {
/*  670 */               propertyDescriptor2 = new PropertyDescriptor(propertyDescriptor2, propertyDescriptor1);
/*      */             }
/*      */           } else {
/*  673 */             propertyDescriptor2 = propertyDescriptor1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  681 */       for (b = 0; b < list.size(); b++) {
/*  682 */         propertyDescriptor1 = list.get(b);
/*  683 */         if (propertyDescriptor1 instanceof IndexedPropertyDescriptor) {
/*  684 */           indexedPropertyDescriptor1 = (IndexedPropertyDescriptor)propertyDescriptor1;
/*  685 */           if (indexedPropertyDescriptor1.getIndexedWriteMethod() != null) {
/*  686 */             if (indexedPropertyDescriptor2 != null) {
/*  687 */               if (isAssignable(indexedPropertyDescriptor2.getIndexedPropertyType(), indexedPropertyDescriptor1.getIndexedPropertyType())) {
/*  688 */                 if (indexedPropertyDescriptor3 != null) {
/*  689 */                   indexedPropertyDescriptor3 = new IndexedPropertyDescriptor(indexedPropertyDescriptor3, indexedPropertyDescriptor1);
/*      */                 } else {
/*  691 */                   indexedPropertyDescriptor3 = indexedPropertyDescriptor1;
/*      */                 }
/*      */               
/*      */               }
/*  695 */             } else if (indexedPropertyDescriptor3 != null) {
/*  696 */               indexedPropertyDescriptor3 = new IndexedPropertyDescriptor(indexedPropertyDescriptor3, indexedPropertyDescriptor1);
/*      */             } else {
/*  698 */               indexedPropertyDescriptor3 = indexedPropertyDescriptor1;
/*      */             }
/*      */           
/*      */           }
/*      */         }
/*  703 */         else if (propertyDescriptor1.getWriteMethod() != null) {
/*  704 */           if (propertyDescriptor2 != null) {
/*  705 */             if (isAssignable(propertyDescriptor2.getPropertyType(), propertyDescriptor1.getPropertyType())) {
/*  706 */               if (propertyDescriptor3 != null) {
/*  707 */                 propertyDescriptor3 = new PropertyDescriptor(propertyDescriptor3, propertyDescriptor1);
/*      */               } else {
/*  709 */                 propertyDescriptor3 = propertyDescriptor1;
/*      */               }
/*      */             
/*      */             }
/*  713 */           } else if (propertyDescriptor3 != null) {
/*  714 */             propertyDescriptor3 = new PropertyDescriptor(propertyDescriptor3, propertyDescriptor1);
/*      */           } else {
/*  716 */             propertyDescriptor3 = propertyDescriptor1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  727 */       propertyDescriptor1 = null; indexedPropertyDescriptor1 = null;
/*      */       
/*  729 */       if (indexedPropertyDescriptor2 != null && indexedPropertyDescriptor3 != null) {
/*      */ 
/*      */         
/*  732 */         if (propertyDescriptor2 == propertyDescriptor3 || propertyDescriptor2 == null) {
/*  733 */           propertyDescriptor1 = propertyDescriptor3;
/*  734 */         } else if (propertyDescriptor3 == null) {
/*  735 */           propertyDescriptor1 = propertyDescriptor2;
/*  736 */         } else if (propertyDescriptor3 instanceof IndexedPropertyDescriptor) {
/*  737 */           propertyDescriptor1 = mergePropertyWithIndexedProperty(propertyDescriptor2, (IndexedPropertyDescriptor)propertyDescriptor3);
/*  738 */         } else if (propertyDescriptor2 instanceof IndexedPropertyDescriptor) {
/*  739 */           propertyDescriptor1 = mergePropertyWithIndexedProperty(propertyDescriptor3, (IndexedPropertyDescriptor)propertyDescriptor2);
/*      */         } else {
/*  741 */           propertyDescriptor1 = mergePropertyDescriptor(propertyDescriptor2, propertyDescriptor3);
/*      */         } 
/*  743 */         if (indexedPropertyDescriptor2 == indexedPropertyDescriptor3) {
/*  744 */           indexedPropertyDescriptor1 = indexedPropertyDescriptor2;
/*      */         } else {
/*  746 */           indexedPropertyDescriptor1 = mergePropertyDescriptor(indexedPropertyDescriptor2, indexedPropertyDescriptor3);
/*      */         } 
/*  748 */         if (propertyDescriptor1 == null) {
/*  749 */           propertyDescriptor1 = indexedPropertyDescriptor1;
/*      */         } else {
/*  751 */           Class<?> clazz1 = propertyDescriptor1.getPropertyType();
/*  752 */           Class<?> clazz2 = indexedPropertyDescriptor1.getIndexedPropertyType();
/*  753 */           if (clazz1.isArray() && clazz1.getComponentType() == clazz2) {
/*  754 */             propertyDescriptor1 = propertyDescriptor1.getClass0().isAssignableFrom(indexedPropertyDescriptor1.getClass0()) ? new IndexedPropertyDescriptor(propertyDescriptor1, indexedPropertyDescriptor1) : new IndexedPropertyDescriptor(indexedPropertyDescriptor1, propertyDescriptor1);
/*      */           
/*      */           }
/*  757 */           else if (propertyDescriptor1.getClass0().isAssignableFrom(indexedPropertyDescriptor1.getClass0())) {
/*  758 */             propertyDescriptor1 = propertyDescriptor1.getClass0().isAssignableFrom(indexedPropertyDescriptor1.getClass0()) ? new PropertyDescriptor(propertyDescriptor1, indexedPropertyDescriptor1) : new PropertyDescriptor(indexedPropertyDescriptor1, propertyDescriptor1);
/*      */           }
/*      */           else {
/*      */             
/*  762 */             propertyDescriptor1 = indexedPropertyDescriptor1;
/*      */           } 
/*      */         } 
/*  765 */       } else if (propertyDescriptor2 != null && propertyDescriptor3 != null) {
/*  766 */         if (indexedPropertyDescriptor2 != null) {
/*  767 */           propertyDescriptor2 = mergePropertyWithIndexedProperty(propertyDescriptor2, indexedPropertyDescriptor2);
/*      */         }
/*  769 */         if (indexedPropertyDescriptor3 != null) {
/*  770 */           propertyDescriptor3 = mergePropertyWithIndexedProperty(propertyDescriptor3, indexedPropertyDescriptor3);
/*      */         }
/*      */         
/*  773 */         if (propertyDescriptor2 == propertyDescriptor3) {
/*  774 */           propertyDescriptor1 = propertyDescriptor2;
/*  775 */         } else if (propertyDescriptor3 instanceof IndexedPropertyDescriptor) {
/*  776 */           propertyDescriptor1 = mergePropertyWithIndexedProperty(propertyDescriptor2, (IndexedPropertyDescriptor)propertyDescriptor3);
/*  777 */         } else if (propertyDescriptor2 instanceof IndexedPropertyDescriptor) {
/*  778 */           propertyDescriptor1 = mergePropertyWithIndexedProperty(propertyDescriptor3, (IndexedPropertyDescriptor)propertyDescriptor2);
/*      */         } else {
/*  780 */           propertyDescriptor1 = mergePropertyDescriptor(propertyDescriptor2, propertyDescriptor3);
/*      */         } 
/*  782 */       } else if (indexedPropertyDescriptor3 != null) {
/*      */         
/*  784 */         propertyDescriptor1 = indexedPropertyDescriptor3;
/*      */         
/*  786 */         if (propertyDescriptor3 != null) {
/*  787 */           propertyDescriptor1 = mergePropertyDescriptor(indexedPropertyDescriptor3, propertyDescriptor3);
/*      */         }
/*  789 */         if (propertyDescriptor2 != null) {
/*  790 */           propertyDescriptor1 = mergePropertyDescriptor(indexedPropertyDescriptor3, propertyDescriptor2);
/*      */         }
/*  792 */       } else if (indexedPropertyDescriptor2 != null) {
/*      */         
/*  794 */         propertyDescriptor1 = indexedPropertyDescriptor2;
/*      */         
/*  796 */         if (propertyDescriptor2 != null) {
/*  797 */           propertyDescriptor1 = mergePropertyDescriptor(indexedPropertyDescriptor2, propertyDescriptor2);
/*      */         }
/*  799 */         if (propertyDescriptor3 != null) {
/*  800 */           propertyDescriptor1 = mergePropertyDescriptor(indexedPropertyDescriptor2, propertyDescriptor3);
/*      */         }
/*  802 */       } else if (propertyDescriptor3 != null) {
/*      */         
/*  804 */         propertyDescriptor1 = propertyDescriptor3;
/*  805 */       } else if (propertyDescriptor2 != null) {
/*      */         
/*  807 */         propertyDescriptor1 = propertyDescriptor2;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  814 */       if (propertyDescriptor1 instanceof IndexedPropertyDescriptor) {
/*  815 */         indexedPropertyDescriptor1 = (IndexedPropertyDescriptor)propertyDescriptor1;
/*  816 */         if (indexedPropertyDescriptor1.getIndexedReadMethod() == null && indexedPropertyDescriptor1.getIndexedWriteMethod() == null) {
/*  817 */           propertyDescriptor1 = new PropertyDescriptor(indexedPropertyDescriptor1);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  824 */       if (propertyDescriptor1 == null && list.size() > 0) {
/*  825 */         propertyDescriptor1 = list.get(0);
/*      */       }
/*      */       
/*  828 */       if (propertyDescriptor1 != null) {
/*  829 */         this.properties.put(propertyDescriptor1.getName(), propertyDescriptor1);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean isAssignable(Class<?> paramClass1, Class<?> paramClass2) {
/*  835 */     return (paramClass1 == null || paramClass2 == null) ? ((paramClass1 == paramClass2)) : paramClass1.isAssignableFrom(paramClass2);
/*      */   }
/*      */   
/*      */   private PropertyDescriptor mergePropertyWithIndexedProperty(PropertyDescriptor paramPropertyDescriptor, IndexedPropertyDescriptor paramIndexedPropertyDescriptor) {
/*  839 */     Class<?> clazz = paramPropertyDescriptor.getPropertyType();
/*  840 */     if (clazz.isArray() && clazz.getComponentType() == paramIndexedPropertyDescriptor.getIndexedPropertyType()) {
/*  841 */       return paramPropertyDescriptor.getClass0().isAssignableFrom(paramIndexedPropertyDescriptor.getClass0()) ? new IndexedPropertyDescriptor(paramPropertyDescriptor, paramIndexedPropertyDescriptor) : new IndexedPropertyDescriptor(paramIndexedPropertyDescriptor, paramPropertyDescriptor);
/*      */     }
/*      */ 
/*      */     
/*  845 */     return paramPropertyDescriptor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyDescriptor mergePropertyDescriptor(IndexedPropertyDescriptor paramIndexedPropertyDescriptor, PropertyDescriptor paramPropertyDescriptor) {
/*  856 */     PropertyDescriptor propertyDescriptor = null;
/*      */     
/*  858 */     Class<?> clazz1 = paramPropertyDescriptor.getPropertyType();
/*  859 */     Class<?> clazz2 = paramIndexedPropertyDescriptor.getIndexedPropertyType();
/*      */     
/*  861 */     if (clazz1.isArray() && clazz1.getComponentType() == clazz2) {
/*  862 */       if (paramPropertyDescriptor.getClass0().isAssignableFrom(paramIndexedPropertyDescriptor.getClass0())) {
/*  863 */         propertyDescriptor = new IndexedPropertyDescriptor(paramPropertyDescriptor, paramIndexedPropertyDescriptor);
/*      */       } else {
/*  865 */         propertyDescriptor = new IndexedPropertyDescriptor(paramIndexedPropertyDescriptor, paramPropertyDescriptor);
/*      */       } 
/*  867 */     } else if (paramIndexedPropertyDescriptor.getReadMethod() == null && paramIndexedPropertyDescriptor.getWriteMethod() == null) {
/*  868 */       if (paramPropertyDescriptor.getClass0().isAssignableFrom(paramIndexedPropertyDescriptor.getClass0())) {
/*  869 */         PropertyDescriptor propertyDescriptor1 = new PropertyDescriptor(paramPropertyDescriptor, paramIndexedPropertyDescriptor);
/*      */       } else {
/*  871 */         PropertyDescriptor propertyDescriptor1 = new PropertyDescriptor(paramIndexedPropertyDescriptor, paramPropertyDescriptor);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  876 */     else if (paramPropertyDescriptor.getClass0().isAssignableFrom(paramIndexedPropertyDescriptor.getClass0())) {
/*  877 */       propertyDescriptor = paramIndexedPropertyDescriptor;
/*      */     } else {
/*  879 */       propertyDescriptor = paramPropertyDescriptor;
/*      */ 
/*      */       
/*  882 */       Method method1 = propertyDescriptor.getWriteMethod();
/*  883 */       Method method2 = propertyDescriptor.getReadMethod();
/*      */       
/*  885 */       if (method2 == null && method1 != null) {
/*  886 */         method2 = findMethod(propertyDescriptor.getClass0(), "get" + 
/*  887 */             NameGenerator.capitalize(propertyDescriptor.getName()), 0);
/*  888 */         if (method2 != null) {
/*      */           try {
/*  890 */             propertyDescriptor.setReadMethod(method2);
/*  891 */           } catch (IntrospectionException introspectionException) {}
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  896 */       if (method1 == null && method2 != null) {
/*  897 */         method1 = findMethod(propertyDescriptor.getClass0(), "set" + 
/*  898 */             NameGenerator.capitalize(propertyDescriptor.getName()), 1, new Class[] {
/*  899 */               FeatureDescriptor.getReturnType(propertyDescriptor.getClass0(), method2) });
/*  900 */         if (method1 != null) {
/*      */           try {
/*  902 */             propertyDescriptor.setWriteMethod(method1);
/*  903 */           } catch (IntrospectionException introspectionException) {}
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  910 */     return propertyDescriptor;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyDescriptor mergePropertyDescriptor(PropertyDescriptor paramPropertyDescriptor1, PropertyDescriptor paramPropertyDescriptor2) {
/*  916 */     if (paramPropertyDescriptor1.getClass0().isAssignableFrom(paramPropertyDescriptor2.getClass0())) {
/*  917 */       return new PropertyDescriptor(paramPropertyDescriptor1, paramPropertyDescriptor2);
/*      */     }
/*  919 */     return new PropertyDescriptor(paramPropertyDescriptor2, paramPropertyDescriptor1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private IndexedPropertyDescriptor mergePropertyDescriptor(IndexedPropertyDescriptor paramIndexedPropertyDescriptor1, IndexedPropertyDescriptor paramIndexedPropertyDescriptor2) {
/*  926 */     if (paramIndexedPropertyDescriptor1.getClass0().isAssignableFrom(paramIndexedPropertyDescriptor2.getClass0())) {
/*  927 */       return new IndexedPropertyDescriptor(paramIndexedPropertyDescriptor1, paramIndexedPropertyDescriptor2);
/*      */     }
/*  929 */     return new IndexedPropertyDescriptor(paramIndexedPropertyDescriptor2, paramIndexedPropertyDescriptor1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EventSetDescriptor[] getTargetEventInfo() throws IntrospectionException {
/*      */     EventSetDescriptor[] arrayOfEventSetDescriptor2;
/*  938 */     if (this.events == null) {
/*  939 */       this.events = new HashMap<>();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  944 */     EventSetDescriptor[] arrayOfEventSetDescriptor1 = null;
/*  945 */     if (this.explicitBeanInfo != null) {
/*  946 */       arrayOfEventSetDescriptor1 = this.explicitBeanInfo.getEventSetDescriptors();
/*  947 */       int i = this.explicitBeanInfo.getDefaultEventIndex();
/*  948 */       if (i >= 0 && i < arrayOfEventSetDescriptor1.length) {
/*  949 */         this.defaultEventName = arrayOfEventSetDescriptor1[i].getName();
/*      */       }
/*      */     } 
/*      */     
/*  953 */     if (arrayOfEventSetDescriptor1 == null && this.superBeanInfo != null) {
/*      */       
/*  955 */       arrayOfEventSetDescriptor2 = this.superBeanInfo.getEventSetDescriptors(); int i;
/*  956 */       for (i = 0; i < arrayOfEventSetDescriptor2.length; i++) {
/*  957 */         addEvent(arrayOfEventSetDescriptor2[i]);
/*      */       }
/*  959 */       i = this.superBeanInfo.getDefaultEventIndex();
/*  960 */       if (i >= 0 && i < arrayOfEventSetDescriptor2.length) {
/*  961 */         this.defaultEventName = arrayOfEventSetDescriptor2[i].getName();
/*      */       }
/*      */     } 
/*      */     byte b;
/*  965 */     for (b = 0; b < this.additionalBeanInfo.length; b++) {
/*  966 */       EventSetDescriptor[] arrayOfEventSetDescriptor = this.additionalBeanInfo[b].getEventSetDescriptors();
/*  967 */       if (arrayOfEventSetDescriptor != null) {
/*  968 */         for (byte b1 = 0; b1 < arrayOfEventSetDescriptor.length; b1++) {
/*  969 */           addEvent(arrayOfEventSetDescriptor[b1]);
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/*  974 */     if (arrayOfEventSetDescriptor1 != null) {
/*      */       
/*  976 */       for (b = 0; b < arrayOfEventSetDescriptor1.length; b++) {
/*  977 */         addEvent(arrayOfEventSetDescriptor1[b]);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  985 */       Method[] arrayOfMethod = getPublicDeclaredMethods(this.beanClass);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  990 */       HashMap<Object, Object> hashMap1 = null;
/*  991 */       HashMap<Object, Object> hashMap2 = null;
/*  992 */       HashMap<Object, Object> hashMap3 = null;
/*      */       
/*  994 */       for (byte b1 = 0; b1 < arrayOfMethod.length; b1++) {
/*  995 */         Method method = arrayOfMethod[b1];
/*  996 */         if (method != null) {
/*      */ 
/*      */ 
/*      */           
/* 1000 */           int i = method.getModifiers();
/* 1001 */           if (!Modifier.isStatic(i)) {
/*      */ 
/*      */             
/* 1004 */             String str = method.getName();
/*      */             
/* 1006 */             if (str.startsWith("add") || str.startsWith("remove") || str
/* 1007 */               .startsWith("get"))
/*      */             {
/*      */ 
/*      */               
/* 1011 */               if (str.startsWith("add")) {
/* 1012 */                 Class<?> clazz = method.getReturnType();
/* 1013 */                 if (clazz == void.class) {
/* 1014 */                   Type[] arrayOfType = method.getGenericParameterTypes();
/* 1015 */                   if (arrayOfType.length == 1) {
/* 1016 */                     Class<?> clazz1 = TypeResolver.erase(TypeResolver.resolveInClass(this.beanClass, arrayOfType[0]));
/* 1017 */                     if (isSubclass(clazz1, eventListenerType)) {
/* 1018 */                       String str1 = str.substring(3);
/* 1019 */                       if (str1.length() > 0 && clazz1
/* 1020 */                         .getName().endsWith(str1)) {
/* 1021 */                         if (hashMap1 == null) {
/* 1022 */                           hashMap1 = new HashMap<>();
/*      */                         }
/* 1024 */                         hashMap1.put(str1, method);
/*      */                       }
/*      */                     
/*      */                     } 
/*      */                   } 
/*      */                 } 
/* 1030 */               } else if (str.startsWith("remove")) {
/* 1031 */                 Class<?> clazz = method.getReturnType();
/* 1032 */                 if (clazz == void.class) {
/* 1033 */                   Type[] arrayOfType = method.getGenericParameterTypes();
/* 1034 */                   if (arrayOfType.length == 1) {
/* 1035 */                     Class<?> clazz1 = TypeResolver.erase(TypeResolver.resolveInClass(this.beanClass, arrayOfType[0]));
/* 1036 */                     if (isSubclass(clazz1, eventListenerType)) {
/* 1037 */                       String str1 = str.substring(6);
/* 1038 */                       if (str1.length() > 0 && clazz1
/* 1039 */                         .getName().endsWith(str1)) {
/* 1040 */                         if (hashMap2 == null) {
/* 1041 */                           hashMap2 = new HashMap<>();
/*      */                         }
/* 1043 */                         hashMap2.put(str1, method);
/*      */                       }
/*      */                     
/*      */                     } 
/*      */                   } 
/*      */                 } 
/* 1049 */               } else if (str.startsWith("get")) {
/* 1050 */                 Class[] arrayOfClass = method.getParameterTypes();
/* 1051 */                 if (arrayOfClass.length == 0) {
/* 1052 */                   Class<?> clazz = FeatureDescriptor.getReturnType(this.beanClass, method);
/* 1053 */                   if (clazz.isArray()) {
/* 1054 */                     Class<?> clazz1 = clazz.getComponentType();
/* 1055 */                     if (isSubclass(clazz1, eventListenerType)) {
/* 1056 */                       String str1 = str.substring(3, str.length() - 1);
/* 1057 */                       if (str1.length() > 0 && clazz1
/* 1058 */                         .getName().endsWith(str1)) {
/* 1059 */                         if (hashMap3 == null) {
/* 1060 */                           hashMap3 = new HashMap<>();
/*      */                         }
/* 1062 */                         hashMap3.put(str1, method);
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               }  } 
/*      */           } 
/*      */         } 
/* 1070 */       }  if (hashMap1 != null && hashMap2 != null) {
/*      */ 
/*      */         
/* 1073 */         Iterator<String> iterator = hashMap1.keySet().iterator();
/* 1074 */         while (iterator.hasNext()) {
/* 1075 */           String str1 = iterator.next();
/*      */ 
/*      */           
/* 1078 */           if (hashMap2.get(str1) == null || !str1.endsWith("Listener")) {
/*      */             continue;
/*      */           }
/* 1081 */           String str2 = decapitalize(str1.substring(0, str1.length() - 8));
/* 1082 */           Method method1 = (Method)hashMap1.get(str1);
/* 1083 */           Method method2 = (Method)hashMap2.get(str1);
/* 1084 */           Method method3 = null;
/* 1085 */           if (hashMap3 != null) {
/* 1086 */             method3 = (Method)hashMap3.get(str1);
/*      */           }
/* 1088 */           Class<?> clazz = FeatureDescriptor.getParameterTypes(this.beanClass, method1)[0];
/*      */ 
/*      */           
/* 1091 */           Method[] arrayOfMethod1 = getPublicDeclaredMethods(clazz);
/* 1092 */           ArrayList<Method> arrayList = new ArrayList(arrayOfMethod1.length);
/* 1093 */           for (byte b2 = 0; b2 < arrayOfMethod1.length; b2++) {
/* 1094 */             if (arrayOfMethod1[b2] != null)
/*      */             {
/*      */ 
/*      */               
/* 1098 */               if (isEventHandler(arrayOfMethod1[b2]))
/* 1099 */                 arrayList.add(arrayOfMethod1[b2]); 
/*      */             }
/*      */           } 
/* 1102 */           Method[] arrayOfMethod2 = arrayList.<Method>toArray(new Method[arrayList.size()]);
/*      */           
/* 1104 */           EventSetDescriptor eventSetDescriptor = new EventSetDescriptor(str2, clazz, arrayOfMethod2, method1, method2, method3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1111 */           if (throwsException(method1, TooManyListenersException.class))
/*      */           {
/* 1113 */             eventSetDescriptor.setUnicast(true);
/*      */           }
/* 1115 */           addEvent(eventSetDescriptor);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1120 */     if (this.events.size() == 0) {
/* 1121 */       arrayOfEventSetDescriptor2 = EMPTY_EVENTSETDESCRIPTORS;
/*      */     } else {
/*      */       
/* 1124 */       arrayOfEventSetDescriptor2 = new EventSetDescriptor[this.events.size()];
/* 1125 */       arrayOfEventSetDescriptor2 = (EventSetDescriptor[])this.events.values().toArray((Object[])arrayOfEventSetDescriptor2);
/*      */ 
/*      */       
/* 1128 */       if (this.defaultEventName != null) {
/* 1129 */         for (byte b1 = 0; b1 < arrayOfEventSetDescriptor2.length; b1++) {
/* 1130 */           if (this.defaultEventName.equals(arrayOfEventSetDescriptor2[b1].getName())) {
/* 1131 */             this.defaultEventIndex = b1;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/* 1136 */     return arrayOfEventSetDescriptor2;
/*      */   }
/*      */   
/*      */   private void addEvent(EventSetDescriptor paramEventSetDescriptor) {
/* 1140 */     String str = paramEventSetDescriptor.getName();
/* 1141 */     if (paramEventSetDescriptor.getName().equals("propertyChange")) {
/* 1142 */       this.propertyChangeSource = true;
/*      */     }
/* 1144 */     EventSetDescriptor eventSetDescriptor1 = this.events.get(str);
/* 1145 */     if (eventSetDescriptor1 == null) {
/* 1146 */       this.events.put(str, paramEventSetDescriptor);
/*      */       return;
/*      */     } 
/* 1149 */     EventSetDescriptor eventSetDescriptor2 = new EventSetDescriptor(eventSetDescriptor1, paramEventSetDescriptor);
/* 1150 */     this.events.put(str, eventSetDescriptor2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MethodDescriptor[] getTargetMethodInfo() {
/* 1158 */     if (this.methods == null) {
/* 1159 */       this.methods = new HashMap<>(100);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1164 */     MethodDescriptor[] arrayOfMethodDescriptor1 = null;
/* 1165 */     if (this.explicitBeanInfo != null) {
/* 1166 */       arrayOfMethodDescriptor1 = this.explicitBeanInfo.getMethodDescriptors();
/*      */     }
/*      */     
/* 1169 */     if (arrayOfMethodDescriptor1 == null && this.superBeanInfo != null) {
/*      */       
/* 1171 */       MethodDescriptor[] arrayOfMethodDescriptor = this.superBeanInfo.getMethodDescriptors();
/* 1172 */       for (byte b1 = 0; b1 < arrayOfMethodDescriptor.length; b1++) {
/* 1173 */         addMethod(arrayOfMethodDescriptor[b1]);
/*      */       }
/*      */     } 
/*      */     byte b;
/* 1177 */     for (b = 0; b < this.additionalBeanInfo.length; b++) {
/* 1178 */       MethodDescriptor[] arrayOfMethodDescriptor = this.additionalBeanInfo[b].getMethodDescriptors();
/* 1179 */       if (arrayOfMethodDescriptor != null) {
/* 1180 */         for (byte b1 = 0; b1 < arrayOfMethodDescriptor.length; b1++) {
/* 1181 */           addMethod(arrayOfMethodDescriptor[b1]);
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/* 1186 */     if (arrayOfMethodDescriptor1 != null) {
/*      */       
/* 1188 */       for (b = 0; b < arrayOfMethodDescriptor1.length; b++) {
/* 1189 */         addMethod(arrayOfMethodDescriptor1[b]);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1197 */       Method[] arrayOfMethod = getPublicDeclaredMethods(this.beanClass);
/*      */ 
/*      */       
/* 1200 */       for (byte b1 = 0; b1 < arrayOfMethod.length; b1++) {
/* 1201 */         Method method = arrayOfMethod[b1];
/* 1202 */         if (method != null) {
/*      */ 
/*      */           
/* 1205 */           MethodDescriptor methodDescriptor = new MethodDescriptor(method);
/* 1206 */           addMethod(methodDescriptor);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1211 */     MethodDescriptor[] arrayOfMethodDescriptor2 = new MethodDescriptor[this.methods.size()];
/* 1212 */     arrayOfMethodDescriptor2 = (MethodDescriptor[])this.methods.values().toArray((Object[])arrayOfMethodDescriptor2);
/*      */     
/* 1214 */     return arrayOfMethodDescriptor2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMethod(MethodDescriptor paramMethodDescriptor) {
/* 1221 */     String str1 = paramMethodDescriptor.getName();
/*      */     
/* 1223 */     MethodDescriptor methodDescriptor1 = this.methods.get(str1);
/* 1224 */     if (methodDescriptor1 == null) {
/*      */       
/* 1226 */       this.methods.put(str1, paramMethodDescriptor);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1233 */     String[] arrayOfString1 = paramMethodDescriptor.getParamNames();
/* 1234 */     String[] arrayOfString2 = methodDescriptor1.getParamNames();
/*      */     
/* 1236 */     boolean bool = false;
/* 1237 */     if (arrayOfString1.length == arrayOfString2.length) {
/* 1238 */       bool = true;
/* 1239 */       for (byte b = 0; b < arrayOfString1.length; b++) {
/* 1240 */         if (arrayOfString1[b] != arrayOfString2[b]) {
/* 1241 */           bool = false;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1246 */     if (bool) {
/* 1247 */       MethodDescriptor methodDescriptor = new MethodDescriptor(methodDescriptor1, paramMethodDescriptor);
/* 1248 */       this.methods.put(str1, methodDescriptor);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 1255 */     String str2 = makeQualifiedMethodName(str1, arrayOfString1);
/* 1256 */     methodDescriptor1 = this.methods.get(str2);
/* 1257 */     if (methodDescriptor1 == null) {
/* 1258 */       this.methods.put(str2, paramMethodDescriptor);
/*      */       return;
/*      */     } 
/* 1261 */     MethodDescriptor methodDescriptor2 = new MethodDescriptor(methodDescriptor1, paramMethodDescriptor);
/* 1262 */     this.methods.put(str2, methodDescriptor2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String makeQualifiedMethodName(String paramString, String[] paramArrayOfString) {
/* 1269 */     StringBuffer stringBuffer = new StringBuffer(paramString);
/* 1270 */     stringBuffer.append('=');
/* 1271 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 1272 */       stringBuffer.append(':');
/* 1273 */       stringBuffer.append(paramArrayOfString[b]);
/*      */     } 
/* 1275 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   private int getTargetDefaultEventIndex() {
/* 1279 */     return this.defaultEventIndex;
/*      */   }
/*      */   
/*      */   private int getTargetDefaultPropertyIndex() {
/* 1283 */     return this.defaultPropertyIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   private BeanDescriptor getTargetBeanDescriptor() {
/* 1288 */     if (this.explicitBeanInfo != null) {
/* 1289 */       BeanDescriptor beanDescriptor = this.explicitBeanInfo.getBeanDescriptor();
/* 1290 */       if (beanDescriptor != null) {
/* 1291 */         return beanDescriptor;
/*      */       }
/*      */     } 
/*      */     
/* 1295 */     return new BeanDescriptor(this.beanClass, findCustomizerClass(this.beanClass));
/*      */   }
/*      */   
/*      */   private static Class<?> findCustomizerClass(Class<?> paramClass) {
/* 1299 */     String str = paramClass.getName() + "Customizer";
/*      */     try {
/* 1301 */       paramClass = ClassFinder.findClass(str, paramClass.getClassLoader());
/*      */ 
/*      */       
/* 1304 */       if (Component.class.isAssignableFrom(paramClass) && Customizer.class.isAssignableFrom(paramClass)) {
/* 1305 */         return paramClass;
/*      */       }
/*      */     }
/* 1308 */     catch (Exception exception) {}
/*      */ 
/*      */     
/* 1311 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isEventHandler(Method paramMethod) {
/* 1317 */     Type[] arrayOfType = paramMethod.getGenericParameterTypes();
/* 1318 */     if (arrayOfType.length != 1) {
/* 1319 */       return false;
/*      */     }
/* 1321 */     return isSubclass(TypeResolver.erase(TypeResolver.resolveInClass(this.beanClass, arrayOfType[0])), EventObject.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Method[] getPublicDeclaredMethods(Class<?> paramClass) {
/* 1330 */     if (!ReflectUtil.isPackageAccessible(paramClass)) {
/* 1331 */       return new Method[0];
/*      */     }
/* 1333 */     synchronized (declaredMethodCache) {
/* 1334 */       Method[] arrayOfMethod = declaredMethodCache.get(paramClass);
/* 1335 */       if (arrayOfMethod == null) {
/* 1336 */         arrayOfMethod = paramClass.getMethods();
/* 1337 */         for (byte b = 0; b < arrayOfMethod.length; b++) {
/* 1338 */           Method method = arrayOfMethod[b];
/* 1339 */           if (!method.getDeclaringClass().equals(paramClass)) {
/* 1340 */             arrayOfMethod[b] = null;
/*      */           } else {
/*      */             
/*      */             try {
/* 1344 */               method = MethodFinder.findAccessibleMethod(method);
/* 1345 */               Class<?> clazz = method.getDeclaringClass();
/* 1346 */               arrayOfMethod[b] = (clazz.equals(paramClass) || clazz.isInterface()) ? method : null;
/*      */ 
/*      */             
/*      */             }
/* 1350 */             catch (NoSuchMethodException noSuchMethodException) {}
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1356 */         declaredMethodCache.put(paramClass, arrayOfMethod);
/*      */       } 
/* 1358 */       return arrayOfMethod;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Method internalFindMethod(Class<?> paramClass, String paramString, int paramInt, Class[] paramArrayOfClass) {
/* 1375 */     Method method = null;
/*      */     
/* 1377 */     for (Class<?> clazz = paramClass; clazz != null; clazz = clazz.getSuperclass()) {
/* 1378 */       Method[] arrayOfMethod = getPublicDeclaredMethods(clazz);
/* 1379 */       for (byte b1 = 0; b1 < arrayOfMethod.length; b1++) {
/* 1380 */         method = arrayOfMethod[b1];
/* 1381 */         if (method == null) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1386 */         if (method.getName().equals(paramString)) {
/* 1387 */           Type[] arrayOfType = method.getGenericParameterTypes();
/* 1388 */           if (arrayOfType.length == paramInt) {
/* 1389 */             if (paramArrayOfClass != null) {
/* 1390 */               boolean bool = false;
/* 1391 */               if (paramInt > 0) {
/* 1392 */                 for (byte b2 = 0; b2 < paramInt; b2++) {
/* 1393 */                   if (TypeResolver.erase(TypeResolver.resolveInClass(paramClass, arrayOfType[b2])) != paramArrayOfClass[b2]) {
/* 1394 */                     bool = true;
/*      */                   }
/*      */                 } 
/*      */                 
/* 1398 */                 if (bool) {
/*      */                   continue;
/*      */                 }
/*      */               } 
/*      */             } 
/* 1403 */             return method;
/*      */           } 
/*      */         }  continue;
/*      */       } 
/*      */     } 
/* 1408 */     method = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1413 */     Class[] arrayOfClass = paramClass.getInterfaces();
/* 1414 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/*      */ 
/*      */ 
/*      */       
/* 1418 */       method = internalFindMethod(arrayOfClass[b], paramString, paramInt, null);
/* 1419 */       if (method != null) {
/*      */         break;
/*      */       }
/*      */     } 
/* 1423 */     return method;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Method findMethod(Class<?> paramClass, String paramString, int paramInt) {
/* 1430 */     return findMethod(paramClass, paramString, paramInt, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Method findMethod(Class<?> paramClass, String paramString, int paramInt, Class[] paramArrayOfClass) {
/* 1447 */     if (paramString == null) {
/* 1448 */       return null;
/*      */     }
/* 1450 */     return internalFindMethod(paramClass, paramString, paramInt, paramArrayOfClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isSubclass(Class<?> paramClass1, Class<?> paramClass2) {
/* 1463 */     if (paramClass1 == paramClass2) {
/* 1464 */       return true;
/*      */     }
/* 1466 */     if (paramClass1 == null || paramClass2 == null) {
/* 1467 */       return false;
/*      */     }
/* 1469 */     for (Class<?> clazz = paramClass1; clazz != null; clazz = clazz.getSuperclass()) {
/* 1470 */       if (clazz == paramClass2) {
/* 1471 */         return true;
/*      */       }
/* 1473 */       if (paramClass2.isInterface()) {
/* 1474 */         Class[] arrayOfClass = clazz.getInterfaces();
/* 1475 */         for (byte b = 0; b < arrayOfClass.length; b++) {
/* 1476 */           if (isSubclass(arrayOfClass[b], paramClass2)) {
/* 1477 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1482 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean throwsException(Method paramMethod, Class<?> paramClass) {
/* 1489 */     Class[] arrayOfClass = paramMethod.getExceptionTypes();
/* 1490 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 1491 */       if (arrayOfClass[b] == paramClass) {
/* 1492 */         return true;
/*      */       }
/*      */     } 
/* 1495 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Object instantiate(Class<?> paramClass, String paramString) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
/* 1507 */     ClassLoader classLoader = paramClass.getClassLoader();
/* 1508 */     Class<?> clazz = ClassFinder.findClass(paramString, classLoader);
/* 1509 */     return clazz.newInstance();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/Introspector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */