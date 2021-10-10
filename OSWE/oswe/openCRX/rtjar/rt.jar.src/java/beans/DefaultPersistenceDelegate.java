/*     */ package java.beans;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.EventListener;
/*     */ import java.util.Objects;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.event.ChangeListener;
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
/*     */ public class DefaultPersistenceDelegate
/*     */   extends PersistenceDelegate
/*     */ {
/*  61 */   private static final String[] EMPTY = new String[0];
/*     */ 
/*     */   
/*     */   private final String[] constructor;
/*     */ 
/*     */   
/*     */   private Boolean definesEquals;
/*     */ 
/*     */   
/*     */   public DefaultPersistenceDelegate() {
/*  71 */     this.constructor = EMPTY;
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
/*     */   public DefaultPersistenceDelegate(String[] paramArrayOfString) {
/*  96 */     this.constructor = (paramArrayOfString == null) ? EMPTY : (String[])paramArrayOfString.clone();
/*     */   }
/*     */   
/*     */   private static boolean definesEquals(Class<?> paramClass) {
/*     */     try {
/* 101 */       return (paramClass == paramClass.getMethod("equals", new Class[] { Object.class }).getDeclaringClass());
/*     */     }
/* 103 */     catch (NoSuchMethodException noSuchMethodException) {
/* 104 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean definesEquals(Object paramObject) {
/* 109 */     if (this.definesEquals != null) {
/* 110 */       return (this.definesEquals == Boolean.TRUE);
/*     */     }
/*     */     
/* 113 */     boolean bool = definesEquals(paramObject.getClass());
/* 114 */     this.definesEquals = bool ? Boolean.TRUE : Boolean.FALSE;
/* 115 */     return bool;
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
/*     */   protected boolean mutatesTo(Object paramObject1, Object paramObject2) {
/* 136 */     return (this.constructor.length == 0 || !definesEquals(paramObject1)) ? super
/* 137 */       .mutatesTo(paramObject1, paramObject2) : paramObject1
/* 138 */       .equals(paramObject2);
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
/*     */   protected Expression instantiate(Object paramObject, Encoder paramEncoder) {
/* 157 */     int i = this.constructor.length;
/* 158 */     Class<?> clazz = paramObject.getClass();
/* 159 */     Object[] arrayOfObject = new Object[i];
/* 160 */     for (byte b = 0; b < i; b++) {
/*     */       try {
/* 162 */         Method method = findMethod(clazz, this.constructor[b]);
/* 163 */         arrayOfObject[b] = MethodUtil.invoke(method, paramObject, new Object[0]);
/*     */       }
/* 165 */       catch (Exception exception) {
/* 166 */         paramEncoder.getExceptionListener().exceptionThrown(exception);
/*     */       } 
/*     */     } 
/* 169 */     return new Expression(paramObject, paramObject.getClass(), "new", arrayOfObject);
/*     */   }
/*     */   
/*     */   private Method findMethod(Class<?> paramClass, String paramString) {
/* 173 */     if (paramString == null) {
/* 174 */       throw new IllegalArgumentException("Property name is null");
/*     */     }
/* 176 */     PropertyDescriptor propertyDescriptor = getPropertyDescriptor(paramClass, paramString);
/* 177 */     if (propertyDescriptor == null) {
/* 178 */       throw new IllegalStateException("Could not find property by the name " + paramString);
/*     */     }
/* 180 */     Method method = propertyDescriptor.getReadMethod();
/* 181 */     if (method == null) {
/* 182 */       throw new IllegalStateException("Could not find getter for the property " + paramString);
/*     */     }
/* 184 */     return method;
/*     */   }
/*     */   
/*     */   private void doProperty(Class<?> paramClass, PropertyDescriptor paramPropertyDescriptor, Object paramObject1, Object paramObject2, Encoder paramEncoder) throws Exception {
/* 188 */     Method method1 = paramPropertyDescriptor.getReadMethod();
/* 189 */     Method method2 = paramPropertyDescriptor.getWriteMethod();
/*     */     
/* 191 */     if (method1 != null && method2 != null) {
/* 192 */       Expression expression1 = new Expression(paramObject1, method1.getName(), new Object[0]);
/* 193 */       Expression expression2 = new Expression(paramObject2, method1.getName(), new Object[0]);
/* 194 */       Object object1 = expression1.getValue();
/* 195 */       Object object2 = expression2.getValue();
/* 196 */       paramEncoder.writeExpression(expression1);
/* 197 */       if (!Objects.equals(object2, paramEncoder.get(object1))) {
/*     */         
/* 199 */         Object[] arrayOfObject = (Object[])paramPropertyDescriptor.getValue("enumerationValues");
/* 200 */         if (arrayOfObject instanceof Object[] && Array.getLength(arrayOfObject) % 3 == 0) {
/* 201 */           Object[] arrayOfObject1 = arrayOfObject; int i;
/* 202 */           for (i = 0; i < arrayOfObject1.length; i += 3) {
/*     */             try {
/* 204 */               Field field = paramClass.getField((String)arrayOfObject1[i]);
/* 205 */               if (field.get(null).equals(object1)) {
/* 206 */                 paramEncoder.remove(object1);
/* 207 */                 paramEncoder.writeExpression(new Expression(object1, field, "get", new Object[] { null }));
/*     */               }
/*     */             
/* 210 */             } catch (Exception exception) {}
/*     */           } 
/*     */         } 
/* 213 */         invokeStatement(paramObject1, method2.getName(), new Object[] { object1 }, paramEncoder);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void invokeStatement(Object paramObject, String paramString, Object[] paramArrayOfObject, Encoder paramEncoder) {
/* 219 */     paramEncoder.writeStatement(new Statement(paramObject, paramString, paramArrayOfObject));
/*     */   }
/*     */   
/*     */   private void initBean(Class<?> paramClass, Object paramObject1, Object paramObject2, Encoder paramEncoder) {
/*     */     BeanInfo beanInfo;
/* 224 */     for (Field field : paramClass.getFields()) {
/* 225 */       if (ReflectUtil.isPackageAccessible(field.getDeclaringClass())) {
/*     */ 
/*     */         
/* 228 */         int i = field.getModifiers();
/* 229 */         if (!Modifier.isFinal(i) && !Modifier.isStatic(i) && !Modifier.isTransient(i))
/*     */           
/*     */           try {
/*     */             
/* 233 */             Expression expression1 = new Expression(field, "get", new Object[] { paramObject1 });
/* 234 */             Expression expression2 = new Expression(field, "get", new Object[] { paramObject2 });
/* 235 */             Object object1 = expression1.getValue();
/* 236 */             Object object2 = expression2.getValue();
/* 237 */             paramEncoder.writeExpression(expression1);
/* 238 */             if (!Objects.equals(object2, paramEncoder.get(object1))) {
/* 239 */               paramEncoder.writeStatement(new Statement(field, "set", new Object[] { paramObject1, object1 }));
/*     */             }
/*     */           }
/* 242 */           catch (Exception exception) {
/* 243 */             paramEncoder.getExceptionListener().exceptionThrown(exception);
/*     */           }  
/*     */       } 
/*     */     } 
/*     */     try {
/* 248 */       beanInfo = Introspector.getBeanInfo(paramClass);
/* 249 */     } catch (IntrospectionException introspectionException) {
/*     */       return;
/*     */     } 
/*     */     
/* 253 */     for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
/* 254 */       if (!propertyDescriptor.isTransient()) {
/*     */         
/*     */         try {
/*     */           
/* 258 */           doProperty(paramClass, propertyDescriptor, paramObject1, paramObject2, paramEncoder);
/*     */         }
/* 260 */         catch (Exception exception) {
/* 261 */           paramEncoder.getExceptionListener().exceptionThrown(exception);
/*     */         } 
/*     */       }
/*     */     } 
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
/* 289 */     if (!Component.class.isAssignableFrom(paramClass)) {
/*     */       return;
/*     */     }
/* 292 */     for (EventSetDescriptor eventSetDescriptor : beanInfo.getEventSetDescriptors()) {
/* 293 */       if (!eventSetDescriptor.isTransient()) {
/*     */ 
/*     */         
/* 296 */         Class<?> clazz = eventSetDescriptor.getListenerType();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 301 */         if (clazz != ComponentListener.class)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 312 */           if (clazz != ChangeListener.class || paramClass != JMenuItem.class) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 317 */             EventListener[] arrayOfEventListener1 = new EventListener[0];
/* 318 */             EventListener[] arrayOfEventListener2 = new EventListener[0];
/*     */             try {
/* 320 */               Method method = eventSetDescriptor.getGetListenerMethod();
/* 321 */               arrayOfEventListener1 = (EventListener[])MethodUtil.invoke(method, paramObject1, new Object[0]);
/* 322 */               arrayOfEventListener2 = (EventListener[])MethodUtil.invoke(method, paramObject2, new Object[0]);
/*     */             }
/* 324 */             catch (Exception exception) {
/*     */               try {
/* 326 */                 Method method = paramClass.getMethod("getListeners", new Class[] { Class.class });
/* 327 */                 arrayOfEventListener1 = (EventListener[])MethodUtil.invoke(method, paramObject1, new Object[] { clazz });
/* 328 */                 arrayOfEventListener2 = (EventListener[])MethodUtil.invoke(method, paramObject2, new Object[] { clazz });
/*     */               }
/* 330 */               catch (Exception exception1) {
/*     */                 return;
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 337 */             String str1 = eventSetDescriptor.getAddListenerMethod().getName();
/* 338 */             for (int i = arrayOfEventListener2.length; i < arrayOfEventListener1.length; i++) {
/*     */               
/* 340 */               invokeStatement(paramObject1, str1, new Object[] { arrayOfEventListener1[i] }, paramEncoder);
/*     */             } 
/*     */             
/* 343 */             String str2 = eventSetDescriptor.getRemoveListenerMethod().getName();
/* 344 */             for (int j = arrayOfEventListener1.length; j < arrayOfEventListener2.length; j++) {
/* 345 */               invokeStatement(paramObject1, str2, new Object[] { arrayOfEventListener2[j] }, paramEncoder);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
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
/*     */   protected void initialize(Class<?> paramClass, Object paramObject1, Object paramObject2, Encoder paramEncoder) {
/* 404 */     super.initialize(paramClass, paramObject1, paramObject2, paramEncoder);
/* 405 */     if (paramObject1.getClass() == paramClass) {
/* 406 */       initBean(paramClass, paramObject1, paramObject2, paramEncoder);
/*     */     }
/*     */   }
/*     */   
/*     */   private static PropertyDescriptor getPropertyDescriptor(Class<?> paramClass, String paramString) {
/*     */     try {
/* 412 */       for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(paramClass).getPropertyDescriptors()) {
/* 413 */         if (paramString.equals(propertyDescriptor.getName()))
/* 414 */           return propertyDescriptor; 
/*     */       } 
/* 416 */     } catch (IntrospectionException introspectionException) {}
/*     */     
/* 418 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/DefaultPersistenceDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */