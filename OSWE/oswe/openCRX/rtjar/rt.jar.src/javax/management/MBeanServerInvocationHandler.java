/*     */ package javax.management;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.MXBeanProxy;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Arrays;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanServerInvocationHandler
/*     */   implements InvocationHandler
/*     */ {
/*     */   public MBeanServerInvocationHandler(MBeanServerConnection paramMBeanServerConnection, ObjectName paramObjectName) {
/* 114 */     this(paramMBeanServerConnection, paramObjectName, false);
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
/*     */   public MBeanServerInvocationHandler(MBeanServerConnection paramMBeanServerConnection, ObjectName paramObjectName, boolean paramBoolean) {
/* 141 */     if (paramMBeanServerConnection == null) {
/* 142 */       throw new IllegalArgumentException("Null connection");
/*     */     }
/* 144 */     if (Proxy.isProxyClass(paramMBeanServerConnection.getClass()) && 
/* 145 */       MBeanServerInvocationHandler.class.isAssignableFrom(
/* 146 */         Proxy.getInvocationHandler(paramMBeanServerConnection).getClass())) {
/* 147 */       throw new IllegalArgumentException("Wrapping MBeanServerInvocationHandler");
/*     */     }
/*     */     
/* 150 */     if (paramObjectName == null) {
/* 151 */       throw new IllegalArgumentException("Null object name");
/*     */     }
/* 153 */     this.connection = paramMBeanServerConnection;
/* 154 */     this.objectName = paramObjectName;
/* 155 */     this.isMXBean = paramBoolean;
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
/*     */   public MBeanServerConnection getMBeanServerConnection() {
/* 167 */     return this.connection;
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
/*     */   public ObjectName getObjectName() {
/* 179 */     return this.objectName;
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
/*     */   public boolean isMXBean() {
/* 191 */     return this.isMXBean;
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
/*     */   public static <T> T newProxyInstance(MBeanServerConnection paramMBeanServerConnection, ObjectName paramObjectName, Class<T> paramClass, boolean paramBoolean) {
/* 240 */     return JMX.newMBeanProxy(paramMBeanServerConnection, paramObjectName, paramClass, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
/* 245 */     Class<?> clazz = paramMethod.getDeclaringClass();
/*     */     
/* 247 */     if (clazz.equals(NotificationBroadcaster.class) || clazz
/* 248 */       .equals(NotificationEmitter.class)) {
/* 249 */       return invokeBroadcasterMethod(paramObject, paramMethod, paramArrayOfObject);
/*     */     }
/*     */     
/* 252 */     if (shouldDoLocally(paramObject, paramMethod)) {
/* 253 */       return doLocally(paramObject, paramMethod, paramArrayOfObject);
/*     */     }
/*     */     try {
/* 256 */       if (isMXBean()) {
/* 257 */         MXBeanProxy mXBeanProxy = findMXBeanProxy(clazz);
/* 258 */         return mXBeanProxy.invoke(this.connection, this.objectName, paramMethod, paramArrayOfObject);
/*     */       } 
/* 260 */       String str = paramMethod.getName();
/* 261 */       Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 262 */       Class<?> clazz1 = paramMethod.getReturnType();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 267 */       boolean bool = (paramArrayOfObject == null) ? false : paramArrayOfObject.length;
/*     */       
/* 269 */       if (str.startsWith("get") && str
/* 270 */         .length() > 3 && !bool && 
/*     */         
/* 272 */         !clazz1.equals(void.class)) {
/* 273 */         return this.connection.getAttribute(this.objectName, str
/* 274 */             .substring(3));
/*     */       }
/*     */       
/* 277 */       if (str.startsWith("is") && str
/* 278 */         .length() > 2 && !bool && (clazz1
/*     */         
/* 280 */         .equals(boolean.class) || clazz1
/* 281 */         .equals(Boolean.class))) {
/* 282 */         return this.connection.getAttribute(this.objectName, str
/* 283 */             .substring(2));
/*     */       }
/*     */       
/* 286 */       if (str.startsWith("set") && str
/* 287 */         .length() > 3 && bool == true && clazz1
/*     */         
/* 289 */         .equals(void.class)) {
/* 290 */         Attribute attribute = new Attribute(str.substring(3), paramArrayOfObject[0]);
/* 291 */         this.connection.setAttribute(this.objectName, attribute);
/* 292 */         return null;
/*     */       } 
/*     */       
/* 295 */       String[] arrayOfString = new String[arrayOfClass.length];
/* 296 */       for (byte b = 0; b < arrayOfClass.length; b++)
/* 297 */         arrayOfString[b] = arrayOfClass[b].getName(); 
/* 298 */       return this.connection.invoke(this.objectName, str, paramArrayOfObject, arrayOfString);
/*     */     
/*     */     }
/* 301 */     catch (MBeanException mBeanException) {
/* 302 */       throw mBeanException.getTargetException();
/* 303 */     } catch (RuntimeMBeanException runtimeMBeanException) {
/* 304 */       throw runtimeMBeanException.getTargetException();
/* 305 */     } catch (RuntimeErrorException runtimeErrorException) {
/* 306 */       throw runtimeErrorException.getTargetError();
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
/*     */   private static MXBeanProxy findMXBeanProxy(Class<?> paramClass) {
/* 323 */     synchronized (mxbeanProxies) {
/*     */       
/* 325 */       WeakReference<MXBeanProxy> weakReference = mxbeanProxies.get(paramClass);
/* 326 */       MXBeanProxy mXBeanProxy = (weakReference == null) ? null : weakReference.get();
/* 327 */       if (mXBeanProxy == null) {
/*     */         try {
/* 329 */           mXBeanProxy = new MXBeanProxy(paramClass);
/* 330 */         } catch (IllegalArgumentException illegalArgumentException1) {
/*     */           
/* 332 */           String str = "Cannot make MXBean proxy for " + paramClass.getName() + ": " + illegalArgumentException1.getMessage();
/*     */           
/* 334 */           IllegalArgumentException illegalArgumentException2 = new IllegalArgumentException(str, illegalArgumentException1.getCause());
/* 335 */           illegalArgumentException2.setStackTrace(illegalArgumentException1.getStackTrace());
/* 336 */           throw illegalArgumentException2;
/*     */         } 
/* 338 */         mxbeanProxies.put(paramClass, new WeakReference<>(mXBeanProxy));
/*     */       } 
/*     */       
/* 341 */       return mXBeanProxy;
/*     */     } 
/*     */   }
/*     */   
/* 345 */   private static final WeakHashMap<Class<?>, WeakReference<MXBeanProxy>> mxbeanProxies = new WeakHashMap<>();
/*     */   private final MBeanServerConnection connection;
/*     */   
/*     */   private Object invokeBroadcasterMethod(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Exception {
/* 349 */     String str = paramMethod.getName();
/* 350 */     boolean bool = (paramArrayOfObject == null) ? false : paramArrayOfObject.length;
/*     */     
/* 352 */     if (str.equals("addNotificationListener")) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 357 */       if (bool != 3) {
/* 358 */         String str1 = "Bad arg count to addNotificationListener: " + bool;
/*     */         
/* 360 */         throw new IllegalArgumentException(str1);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 365 */       NotificationListener notificationListener = (NotificationListener)paramArrayOfObject[0];
/* 366 */       NotificationFilter notificationFilter = (NotificationFilter)paramArrayOfObject[1];
/* 367 */       Object object = paramArrayOfObject[2];
/* 368 */       this.connection.addNotificationListener(this.objectName, notificationListener, notificationFilter, object);
/*     */ 
/*     */ 
/*     */       
/* 372 */       return null;
/*     */     } 
/* 374 */     if (str.equals("removeNotificationListener")) {
/*     */       NotificationFilter notificationFilter;
/*     */       
/*     */       Object object;
/* 378 */       NotificationListener notificationListener = (NotificationListener)paramArrayOfObject[0];
/*     */       
/* 380 */       switch (bool) {
/*     */         case true:
/* 382 */           this.connection.removeNotificationListener(this.objectName, notificationListener);
/* 383 */           return null;
/*     */         
/*     */         case true:
/* 386 */           notificationFilter = (NotificationFilter)paramArrayOfObject[1];
/* 387 */           object = paramArrayOfObject[2];
/* 388 */           this.connection.removeNotificationListener(this.objectName, notificationListener, notificationFilter, object);
/*     */ 
/*     */ 
/*     */           
/* 392 */           return null;
/*     */       } 
/*     */       
/* 395 */       String str1 = "Bad arg count to removeNotificationListener: " + bool;
/*     */       
/* 397 */       throw new IllegalArgumentException(str1);
/*     */     } 
/*     */     
/* 400 */     if (str.equals("getNotificationInfo")) {
/*     */       
/* 402 */       if (paramArrayOfObject != null) {
/* 403 */         throw new IllegalArgumentException("getNotificationInfo has args");
/*     */       }
/*     */ 
/*     */       
/* 407 */       MBeanInfo mBeanInfo = this.connection.getMBeanInfo(this.objectName);
/* 408 */       return mBeanInfo.getNotifications();
/*     */     } 
/*     */     
/* 411 */     throw new IllegalArgumentException("Bad method name: " + str);
/*     */   }
/*     */   
/*     */   private final ObjectName objectName;
/*     */   
/*     */   private boolean shouldDoLocally(Object paramObject, Method paramMethod) {
/* 417 */     String str = paramMethod.getName();
/* 418 */     if ((str.equals("hashCode") || str.equals("toString")) && (paramMethod
/* 419 */       .getParameterTypes()).length == 0 && 
/* 420 */       isLocal(paramObject, paramMethod))
/* 421 */       return true; 
/* 422 */     if (str.equals("equals") && 
/* 423 */       Arrays.equals((Object[])paramMethod.getParameterTypes(), (Object[])new Class[] { Object.class
/*     */         
/* 425 */         }) && isLocal(paramObject, paramMethod))
/* 426 */       return true; 
/* 427 */     if (str.equals("finalize") && (paramMethod
/* 428 */       .getParameterTypes()).length == 0) {
/* 429 */       return true;
/*     */     }
/* 431 */     return false;
/*     */   }
/*     */   private final boolean isMXBean;
/*     */   private Object doLocally(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) {
/* 435 */     String str = paramMethod.getName();
/*     */     
/* 437 */     if (str.equals("equals")) {
/*     */       
/* 439 */       if (this == paramArrayOfObject[0]) {
/* 440 */         return Boolean.valueOf(true);
/*     */       }
/*     */       
/* 443 */       if (!(paramArrayOfObject[0] instanceof Proxy)) {
/* 444 */         return Boolean.valueOf(false);
/*     */       }
/*     */ 
/*     */       
/* 448 */       InvocationHandler invocationHandler = Proxy.getInvocationHandler(paramArrayOfObject[0]);
/*     */       
/* 450 */       if (invocationHandler == null || !(invocationHandler instanceof MBeanServerInvocationHandler))
/*     */       {
/* 452 */         return Boolean.valueOf(false);
/*     */       }
/*     */       
/* 455 */       MBeanServerInvocationHandler mBeanServerInvocationHandler = (MBeanServerInvocationHandler)invocationHandler;
/*     */ 
/*     */       
/* 458 */       return Boolean.valueOf((this.connection.equals(mBeanServerInvocationHandler.connection) && this.objectName
/* 459 */           .equals(mBeanServerInvocationHandler.objectName) && paramObject
/* 460 */           .getClass().equals(paramArrayOfObject[0].getClass())));
/* 461 */     }  if (str.equals("toString")) {
/* 462 */       return (isMXBean() ? "MX" : "M") + "BeanProxy(" + this.connection + "[" + this.objectName + "])";
/*     */     }
/* 464 */     if (str.equals("hashCode"))
/* 465 */       return Integer.valueOf(this.objectName.hashCode() + this.connection.hashCode()); 
/* 466 */     if (str.equals("finalize"))
/*     */     {
/* 468 */       return null;
/*     */     }
/*     */     
/* 471 */     throw new RuntimeException("Unexpected method name: " + str);
/*     */   }
/*     */   
/*     */   private static boolean isLocal(Object paramObject, Method paramMethod) {
/* 475 */     Class[] arrayOfClass1 = paramObject.getClass().getInterfaces();
/* 476 */     if (arrayOfClass1 == null) {
/* 477 */       return true;
/*     */     }
/*     */     
/* 480 */     String str = paramMethod.getName();
/* 481 */     Class[] arrayOfClass2 = paramMethod.getParameterTypes();
/* 482 */     for (Class clazz : arrayOfClass1) {
/*     */       try {
/* 484 */         clazz.getMethod(str, arrayOfClass2);
/* 485 */         return false;
/* 486 */       } catch (NoSuchMethodException noSuchMethodException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 491 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/MBeanServerInvocationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */