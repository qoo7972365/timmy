/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.lang.reflect.ReflectPermission;
/*     */ import java.lang.reflect.WeakCache;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.function.BiFunction;
/*     */ import sun.misc.ProxyGenerator;
/*     */ import sun.misc.VM;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ import sun.security.util.SecurityConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Proxy
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2222568056686623797L;
/* 233 */   private static final Class<?>[] constructorParams = new Class[] { InvocationHandler.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   private static final WeakCache<ClassLoader, Class<?>[], Class<?>> proxyClassCache = (WeakCache)new WeakCache<>(new KeyFactory(), new ProxyClassFactory());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InvocationHandler h;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Proxy() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Proxy(InvocationHandler paramInvocationHandler) {
/* 265 */     Objects.requireNonNull(paramInvocationHandler);
/* 266 */     this.h = paramInvocationHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public static Class<?> getProxyClass(ClassLoader paramClassLoader, Class<?>... paramVarArgs) throws IllegalArgumentException {
/* 365 */     Class[] arrayOfClass = (Class[])paramVarArgs.clone();
/* 366 */     SecurityManager securityManager = System.getSecurityManager();
/* 367 */     if (securityManager != null) {
/* 368 */       checkProxyAccess(Reflection.getCallerClass(), paramClassLoader, arrayOfClass);
/*     */     }
/*     */     
/* 371 */     return getProxyClass0(paramClassLoader, arrayOfClass);
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
/*     */   private static void checkProxyAccess(Class<?> paramClass, ClassLoader paramClassLoader, Class<?>... paramVarArgs) {
/* 396 */     SecurityManager securityManager = System.getSecurityManager();
/* 397 */     if (securityManager != null) {
/* 398 */       ClassLoader classLoader = paramClass.getClassLoader();
/* 399 */       if (VM.isSystemDomainLoader(paramClassLoader) && !VM.isSystemDomainLoader(classLoader)) {
/* 400 */         securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*     */       }
/* 402 */       ReflectUtil.checkProxyPackageAccess(classLoader, paramVarArgs);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> getProxyClass0(ClassLoader paramClassLoader, Class<?>... paramVarArgs) {
/* 412 */     if (paramVarArgs.length > 65535) {
/* 413 */       throw new IllegalArgumentException("interface limit exceeded");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     return proxyClassCache.get(paramClassLoader, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 425 */   private static final Object key0 = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class Key1
/*     */     extends WeakReference<Class<?>>
/*     */   {
/*     */     private final int hash;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Key1(Class<?> param1Class) {
/* 439 */       super(param1Class);
/* 440 */       this.hash = param1Class.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 445 */       return this.hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/*     */       Class<?> clazz;
/* 451 */       return (this == param1Object || (param1Object != null && param1Object
/*     */         
/* 453 */         .getClass() == Key1.class && (
/* 454 */         clazz = get()) != null && clazz == ((Key1)param1Object)
/* 455 */         .get()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Key2
/*     */     extends WeakReference<Class<?>>
/*     */   {
/*     */     private final int hash;
/*     */     private final WeakReference<Class<?>> ref2;
/*     */     
/*     */     Key2(Class<?> param1Class1, Class<?> param1Class2) {
/* 467 */       super(param1Class1);
/* 468 */       this.hash = 31 * param1Class1.hashCode() + param1Class2.hashCode();
/* 469 */       this.ref2 = new WeakReference<>(param1Class2);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 474 */       return this.hash;
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/*     */       Class<?> clazz1;
/*     */       Class<?> clazz2;
/* 480 */       return (this == param1Object || (param1Object != null && param1Object
/*     */         
/* 482 */         .getClass() == Key2.class && (
/* 483 */         clazz1 = get()) != null && clazz1 == ((Key2)param1Object)
/* 484 */         .get() && (
/* 485 */         clazz2 = this.ref2.get()) != null && clazz2 == ((Key2)param1Object).ref2
/* 486 */         .get()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class KeyX
/*     */   {
/*     */     private final int hash;
/*     */     
/*     */     private final WeakReference<Class<?>>[] refs;
/*     */ 
/*     */     
/*     */     KeyX(Class<?>[] param1ArrayOfClass) {
/* 500 */       this.hash = Arrays.hashCode((Object[])param1ArrayOfClass);
/* 501 */       this.refs = (WeakReference<Class<?>>[])new WeakReference[param1ArrayOfClass.length];
/* 502 */       for (byte b = 0; b < param1ArrayOfClass.length; b++) {
/* 503 */         this.refs[b] = new WeakReference<>(param1ArrayOfClass[b]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 509 */       return this.hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 514 */       return (this == param1Object || (param1Object != null && param1Object
/*     */         
/* 516 */         .getClass() == KeyX.class && 
/* 517 */         equals(this.refs, ((KeyX)param1Object).refs)));
/*     */     }
/*     */ 
/*     */     
/*     */     private static boolean equals(WeakReference<Class<?>>[] param1ArrayOfWeakReference1, WeakReference<Class<?>>[] param1ArrayOfWeakReference2) {
/* 522 */       if (param1ArrayOfWeakReference1.length != param1ArrayOfWeakReference2.length) {
/* 523 */         return false;
/*     */       }
/* 525 */       for (byte b = 0; b < param1ArrayOfWeakReference1.length; b++) {
/* 526 */         Class<?> clazz = param1ArrayOfWeakReference1[b].get();
/* 527 */         if (clazz == null || clazz != param1ArrayOfWeakReference2[b].get()) {
/* 528 */           return false;
/*     */         }
/*     */       } 
/* 531 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class KeyFactory
/*     */     implements BiFunction<ClassLoader, Class<?>[], Object>
/*     */   {
/*     */     private KeyFactory() {}
/*     */ 
/*     */     
/*     */     public Object apply(ClassLoader param1ClassLoader, Class<?>[] param1ArrayOfClass) {
/* 544 */       switch (param1ArrayOfClass.length) { case 1:
/* 545 */           return new Proxy.Key1(param1ArrayOfClass[0]);
/* 546 */         case 2: return new Proxy.Key2(param1ArrayOfClass[0], param1ArrayOfClass[1]);
/* 547 */         case 0: return Proxy.key0; }
/* 548 */        return new Proxy.KeyX(param1ArrayOfClass);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ProxyClassFactory
/*     */     implements BiFunction<ClassLoader, Class<?>[], Class<?>>
/*     */   {
/*     */     private static final String proxyClassNamePrefix = "$Proxy";
/*     */ 
/*     */ 
/*     */     
/*     */     private ProxyClassFactory() {}
/*     */ 
/*     */     
/* 564 */     private static final AtomicLong nextUniqueNumber = new AtomicLong();
/*     */ 
/*     */ 
/*     */     
/*     */     public Class<?> apply(ClassLoader param1ClassLoader, Class<?>[] param1ArrayOfClass) {
/* 569 */       IdentityHashMap<Object, Object> identityHashMap = new IdentityHashMap<>(param1ArrayOfClass.length);
/* 570 */       for (Class<?> clazz1 : param1ArrayOfClass) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 575 */         Class<?> clazz2 = null;
/*     */         try {
/* 577 */           clazz2 = Class.forName(clazz1.getName(), false, param1ClassLoader);
/* 578 */         } catch (ClassNotFoundException classNotFoundException) {}
/*     */         
/* 580 */         if (clazz2 != clazz1) {
/* 581 */           throw new IllegalArgumentException(clazz1 + " is not visible from class loader");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 588 */         if (!clazz2.isInterface()) {
/* 589 */           throw new IllegalArgumentException(clazz2
/* 590 */               .getName() + " is not an interface");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 595 */         if (identityHashMap.put(clazz2, Boolean.TRUE) != null) {
/* 596 */           throw new IllegalArgumentException("repeated interface: " + clazz2
/* 597 */               .getName());
/*     */         }
/*     */       } 
/*     */       
/* 601 */       String str1 = null;
/* 602 */       byte b = 17;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 609 */       for (Class<?> clazz : param1ArrayOfClass) {
/* 610 */         int i = clazz.getModifiers();
/* 611 */         if (!Modifier.isPublic(i)) {
/* 612 */           b = 16;
/* 613 */           String str3 = clazz.getName();
/* 614 */           int j = str3.lastIndexOf('.');
/* 615 */           String str4 = (j == -1) ? "" : str3.substring(0, j + 1);
/* 616 */           if (str1 == null) {
/* 617 */             str1 = str4;
/* 618 */           } else if (!str4.equals(str1)) {
/* 619 */             throw new IllegalArgumentException("non-public interfaces from different packages");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 625 */       if (str1 == null)
/*     */       {
/* 627 */         str1 = "com.sun.proxy.";
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 633 */       long l = nextUniqueNumber.getAndIncrement();
/* 634 */       String str2 = str1 + "$Proxy" + l;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 639 */       byte[] arrayOfByte = ProxyGenerator.generateProxyClass(str2, param1ArrayOfClass, b);
/*     */       
/*     */       try {
/* 642 */         return Proxy.defineClass0(param1ClassLoader, str2, arrayOfByte, 0, arrayOfByte.length);
/*     */       }
/* 644 */       catch (ClassFormatError classFormatError) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 652 */         throw new IllegalArgumentException(classFormatError.toString());
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
/*     */   @CallerSensitive
/*     */   public static Object newProxyInstance(ClassLoader paramClassLoader, Class<?>[] paramArrayOfClass, InvocationHandler paramInvocationHandler) throws IllegalArgumentException {
/* 708 */     Objects.requireNonNull(paramInvocationHandler);
/*     */     
/* 710 */     Class[] arrayOfClass = (Class[])paramArrayOfClass.clone();
/* 711 */     SecurityManager securityManager = System.getSecurityManager();
/* 712 */     if (securityManager != null) {
/* 713 */       checkProxyAccess(Reflection.getCallerClass(), paramClassLoader, arrayOfClass);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 719 */     Class<?> clazz = getProxyClass0(paramClassLoader, arrayOfClass);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 725 */       if (securityManager != null) {
/* 726 */         checkNewProxyPermission(Reflection.getCallerClass(), clazz);
/*     */       }
/*     */       
/* 729 */       final Constructor<?> cons = clazz.getConstructor(constructorParams);
/* 730 */       InvocationHandler invocationHandler = paramInvocationHandler;
/* 731 */       if (!Modifier.isPublic(clazz.getModifiers())) {
/* 732 */         AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */               public Void run() {
/* 734 */                 cons.setAccessible(true);
/* 735 */                 return null;
/*     */               }
/*     */             });
/*     */       }
/* 739 */       return constructor.newInstance(new Object[] { paramInvocationHandler });
/* 740 */     } catch (IllegalAccessException|InstantiationException illegalAccessException) {
/* 741 */       throw new InternalError(illegalAccessException.toString(), illegalAccessException);
/* 742 */     } catch (InvocationTargetException invocationTargetException) {
/* 743 */       Throwable throwable = invocationTargetException.getCause();
/* 744 */       if (throwable instanceof RuntimeException) {
/* 745 */         throw (RuntimeException)throwable;
/*     */       }
/* 747 */       throw new InternalError(throwable.toString(), throwable);
/*     */     }
/* 749 */     catch (NoSuchMethodException noSuchMethodException) {
/* 750 */       throw new InternalError(noSuchMethodException.toString(), noSuchMethodException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkNewProxyPermission(Class<?> paramClass1, Class<?> paramClass2) {
/* 755 */     SecurityManager securityManager = System.getSecurityManager();
/* 756 */     if (securityManager != null && 
/* 757 */       ReflectUtil.isNonPublicProxyClass(paramClass2)) {
/* 758 */       ClassLoader classLoader1 = paramClass1.getClassLoader();
/* 759 */       ClassLoader classLoader2 = paramClass2.getClassLoader();
/*     */ 
/*     */ 
/*     */       
/* 763 */       int i = paramClass2.getName().lastIndexOf('.');
/* 764 */       String str1 = (i == -1) ? "" : paramClass2.getName().substring(0, i);
/*     */       
/* 766 */       i = paramClass1.getName().lastIndexOf('.');
/* 767 */       String str2 = (i == -1) ? "" : paramClass1.getName().substring(0, i);
/*     */       
/* 769 */       if (classLoader2 != classLoader1 || !str1.equals(str2)) {
/* 770 */         securityManager.checkPermission(new ReflectPermission("newProxyInPackage." + str1));
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
/*     */   public static boolean isProxyClass(Class<?> paramClass) {
/* 791 */     return (Proxy.class.isAssignableFrom(paramClass) && proxyClassCache.containsValue(paramClass));
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
/*     */   @CallerSensitive
/*     */   public static InvocationHandler getInvocationHandler(Object paramObject) throws IllegalArgumentException {
/* 815 */     if (!isProxyClass(paramObject.getClass())) {
/* 816 */       throw new IllegalArgumentException("not a proxy instance");
/*     */     }
/*     */     
/* 819 */     Proxy proxy = (Proxy)paramObject;
/* 820 */     InvocationHandler invocationHandler = proxy.h;
/* 821 */     if (System.getSecurityManager() != null) {
/* 822 */       Class<?> clazz = invocationHandler.getClass();
/* 823 */       Class clazz1 = Reflection.getCallerClass();
/* 824 */       if (ReflectUtil.needsPackageAccessCheck(clazz1.getClassLoader(), clazz
/* 825 */           .getClassLoader()))
/*     */       {
/* 827 */         ReflectUtil.checkPackageAccess(clazz);
/*     */       }
/*     */     } 
/*     */     
/* 831 */     return invocationHandler;
/*     */   }
/*     */   
/*     */   private static native Class<?> defineClass0(ClassLoader paramClassLoader, String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/Proxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */