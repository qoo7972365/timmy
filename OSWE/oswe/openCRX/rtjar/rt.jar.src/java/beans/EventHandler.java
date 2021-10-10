/*     */ package java.beans;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventHandler
/*     */   implements InvocationHandler
/*     */ {
/*     */   private Object target;
/*     */   private String action;
/*     */   private final String eventPropertyName;
/*     */   private final String listenerMethodName;
/* 284 */   private final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ConstructorProperties({"target", "action", "eventPropertyName", "listenerMethodName"})
/*     */   public EventHandler(Object paramObject, String paramString1, String paramString2, String paramString3) {
/* 313 */     this.target = paramObject;
/* 314 */     this.action = paramString1;
/* 315 */     if (paramObject == null) {
/* 316 */       throw new NullPointerException("target must be non-null");
/*     */     }
/* 318 */     if (paramString1 == null) {
/* 319 */       throw new NullPointerException("action must be non-null");
/*     */     }
/* 321 */     this.eventPropertyName = paramString2;
/* 322 */     this.listenerMethodName = paramString3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getTarget() {
/* 332 */     return this.target;
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
/*     */   public String getAction() {
/* 345 */     return this.action;
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
/*     */   public String getEventPropertyName() {
/* 357 */     return this.eventPropertyName;
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
/*     */   public String getListenerMethodName() {
/* 370 */     return this.listenerMethodName;
/*     */   }
/*     */   
/*     */   private Object applyGetters(Object paramObject, String paramString) {
/* 374 */     if (paramString == null || paramString.equals("")) {
/* 375 */       return paramObject;
/*     */     }
/* 377 */     int i = paramString.indexOf('.');
/* 378 */     if (i == -1) {
/* 379 */       i = paramString.length();
/*     */     }
/* 381 */     String str1 = paramString.substring(0, i);
/* 382 */     String str2 = paramString.substring(Math.min(i + 1, paramString.length()));
/*     */     
/*     */     try {
/* 385 */       Method method = null;
/* 386 */       if (paramObject != null) {
/* 387 */         method = Statement.getMethod(paramObject.getClass(), "get" + 
/* 388 */             NameGenerator.capitalize(str1), new Class[0]);
/*     */         
/* 390 */         if (method == null) {
/* 391 */           method = Statement.getMethod(paramObject.getClass(), "is" + 
/* 392 */               NameGenerator.capitalize(str1), new Class[0]);
/*     */         }
/*     */         
/* 395 */         if (method == null) {
/* 396 */           method = Statement.getMethod(paramObject.getClass(), str1, new Class[0]);
/*     */         }
/*     */       } 
/* 399 */       if (method == null) {
/* 400 */         throw new RuntimeException("No method called: " + str1 + " defined on " + paramObject);
/*     */       }
/*     */       
/* 403 */       Object object = MethodUtil.invoke(method, paramObject, new Object[0]);
/* 404 */       return applyGetters(object, str2);
/*     */     }
/* 406 */     catch (Exception exception) {
/* 407 */       throw new RuntimeException("Failed to call method: " + str1 + " on " + paramObject, exception);
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
/*     */   public Object invoke(final Object proxy, final Method method, final Object[] arguments) {
/* 424 */     AccessControlContext accessControlContext = this.acc;
/* 425 */     if (accessControlContext == null && System.getSecurityManager() != null) {
/* 426 */       throw new SecurityException("AccessControlContext is not set");
/*     */     }
/* 428 */     return AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Object run() {
/* 430 */             return EventHandler.this.invokeInternal(proxy, method, arguments);
/*     */           }
/*     */         }accessControlContext);
/*     */   }
/*     */   
/*     */   private Object invokeInternal(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) {
/* 436 */     String str = paramMethod.getName();
/* 437 */     if (paramMethod.getDeclaringClass() == Object.class) {
/*     */       
/* 439 */       if (str.equals("hashCode"))
/* 440 */         return new Integer(System.identityHashCode(paramObject)); 
/* 441 */       if (str.equals("equals"))
/* 442 */         return (paramObject == paramArrayOfObject[0]) ? Boolean.TRUE : Boolean.FALSE; 
/* 443 */       if (str.equals("toString")) {
/* 444 */         return paramObject.getClass().getName() + '@' + Integer.toHexString(paramObject.hashCode());
/*     */       }
/*     */     } 
/*     */     
/* 448 */     if (this.listenerMethodName == null || this.listenerMethodName.equals(str)) {
/* 449 */       Class[] arrayOfClass = null;
/* 450 */       Object[] arrayOfObject = null;
/*     */       
/* 452 */       if (this.eventPropertyName == null) {
/* 453 */         arrayOfObject = new Object[0];
/* 454 */         arrayOfClass = new Class[0];
/*     */       } else {
/*     */         
/* 457 */         Object object = applyGetters(paramArrayOfObject[0], getEventPropertyName());
/* 458 */         arrayOfObject = new Object[] { object };
/*     */         
/* 460 */         arrayOfClass = new Class[] { (object == null) ? null : object.getClass() };
/*     */       } 
/*     */       try {
/* 463 */         int i = this.action.lastIndexOf('.');
/* 464 */         if (i != -1) {
/* 465 */           this.target = applyGetters(this.target, this.action.substring(0, i));
/* 466 */           this.action = this.action.substring(i + 1);
/*     */         } 
/* 468 */         Method method = Statement.getMethod(this.target
/* 469 */             .getClass(), this.action, arrayOfClass);
/* 470 */         if (method == null) {
/* 471 */           method = Statement.getMethod(this.target.getClass(), "set" + 
/* 472 */               NameGenerator.capitalize(this.action), arrayOfClass);
/*     */         }
/* 474 */         if (method == null) {
/* 475 */           String str1 = (arrayOfClass.length == 0) ? " with no arguments" : (" with argument " + arrayOfClass[0]);
/*     */ 
/*     */           
/* 478 */           throw new RuntimeException("No method called " + this.action + " on " + this.target
/*     */               
/* 480 */               .getClass() + str1);
/*     */         } 
/* 482 */         return MethodUtil.invoke(method, this.target, arrayOfObject);
/*     */       }
/* 484 */       catch (IllegalAccessException illegalAccessException) {
/* 485 */         throw new RuntimeException(illegalAccessException);
/*     */       }
/* 487 */       catch (InvocationTargetException invocationTargetException) {
/* 488 */         Throwable throwable = invocationTargetException.getTargetException();
/* 489 */         throw (throwable instanceof RuntimeException) ? (RuntimeException)throwable : new RuntimeException(throwable);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 494 */     return null;
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
/*     */   public static <T> T create(Class<T> paramClass, Object paramObject, String paramString) {
/* 535 */     return create(paramClass, paramObject, paramString, null, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T create(Class<T> paramClass, Object paramObject, String paramString1, String paramString2) {
/* 594 */     return create(paramClass, paramObject, paramString1, paramString2, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T create(Class<T> paramClass, Object paramObject, String paramString1, String paramString2, String paramString3) {
/* 687 */     final EventHandler handler = new EventHandler(paramObject, paramString1, paramString2, paramString3);
/*     */ 
/*     */     
/* 690 */     if (paramClass == null) {
/* 691 */       throw new NullPointerException("listenerInterface must be non-null");
/*     */     }
/*     */     
/* 694 */     final ClassLoader loader = getClassLoader(paramClass);
/* 695 */     final Class[] interfaces = { paramClass };
/* 696 */     return AccessController.doPrivileged(new PrivilegedAction<T>()
/*     */         {
/*     */           public T run() {
/* 699 */             return (T)Proxy.newProxyInstance(loader, interfaces, handler);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static ClassLoader getClassLoader(Class<?> paramClass) {
/* 705 */     ReflectUtil.checkPackageAccess(paramClass);
/* 706 */     ClassLoader classLoader = paramClass.getClassLoader();
/* 707 */     if (classLoader == null) {
/* 708 */       classLoader = Thread.currentThread().getContextClassLoader();
/* 709 */       if (classLoader == null) {
/* 710 */         classLoader = ClassLoader.getSystemClassLoader();
/*     */       }
/*     */     } 
/* 713 */     return classLoader;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/EventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */