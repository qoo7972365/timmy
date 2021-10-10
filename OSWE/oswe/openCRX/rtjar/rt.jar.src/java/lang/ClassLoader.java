/*      */ package java.lang;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.URL;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.CodeSource;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.security.cert.Certificate;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Stack;
/*      */ import java.util.Vector;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import sun.misc.CompoundEnumeration;
/*      */ import sun.misc.Launcher;
/*      */ import sun.misc.PerfCounter;
/*      */ import sun.misc.Resource;
/*      */ import sun.misc.URLClassPath;
/*      */ import sun.misc.VM;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.security.util.SecurityConstants;
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
/*      */ public abstract class ClassLoader
/*      */ {
/*      */   private final ClassLoader parent;
/*      */   private final ConcurrentHashMap<String, Object> parallelLockMap;
/*      */   private final Map<String, Certificate[]> package2certs;
/*      */   
/*      */   static {
/*  183 */     registerNatives();
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
/*      */   private static class ParallelLoaders
/*      */   {
/*  199 */     private static final Set<Class<? extends ClassLoader>> loaderTypes = Collections.newSetFromMap(new WeakHashMap<>());
/*      */     
/*      */     static {
/*  202 */       synchronized (loaderTypes) { loaderTypes.add(ClassLoader.class); }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean register(Class<? extends ClassLoader> param1Class) {
/*  211 */       synchronized (loaderTypes) {
/*  212 */         if (loaderTypes.contains(param1Class.getSuperclass())) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  218 */           loaderTypes.add(param1Class);
/*  219 */           return true;
/*      */         } 
/*  221 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean isRegistered(Class<? extends ClassLoader> param1Class) {
/*  231 */       synchronized (loaderTypes) {
/*  232 */         return loaderTypes.contains(param1Class);
/*      */       } 
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
/*  247 */   private static final Certificate[] nocerts = new Certificate[0];
/*      */ 
/*      */ 
/*      */   
/*  251 */   private final Vector<Class<?>> classes = new Vector<>();
/*      */ 
/*      */ 
/*      */   
/*  255 */   private final ProtectionDomain defaultDomain = new ProtectionDomain(new CodeSource(null, (Certificate[])null), null, this, null);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addClass(Class<?> paramClass) {
/*  261 */     this.classes.addElement(paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  267 */   private final HashMap<String, Package> packages = new HashMap<>(); private static ClassLoader scl; private static boolean sclSet;
/*      */   
/*      */   private static Void checkCreateClassLoader() {
/*  270 */     SecurityManager securityManager = System.getSecurityManager();
/*  271 */     if (securityManager != null) {
/*  272 */       securityManager.checkCreateClassLoader();
/*      */     }
/*  274 */     return null;
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
/*      */   protected ClassLoader(ClassLoader paramClassLoader) {
/*  311 */     this(checkCreateClassLoader(), paramClassLoader);
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
/*      */   protected ClassLoader() {
/*  330 */     this(checkCreateClassLoader(), getSystemClassLoader());
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
/*      */   public Class<?> loadClass(String paramString) throws ClassNotFoundException {
/*  352 */     return loadClass(paramString, false);
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
/*      */   protected Class<?> loadClass(String paramString, boolean paramBoolean) throws ClassNotFoundException {
/*  399 */     synchronized (getClassLoadingLock(paramString)) {
/*      */       
/*  401 */       Class<?> clazz = findLoadedClass(paramString);
/*  402 */       if (clazz == null) {
/*  403 */         long l = System.nanoTime();
/*      */         try {
/*  405 */           if (this.parent != null) {
/*  406 */             clazz = this.parent.loadClass(paramString, false);
/*      */           } else {
/*  408 */             clazz = findBootstrapClassOrNull(paramString);
/*      */           } 
/*  410 */         } catch (ClassNotFoundException classNotFoundException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  415 */         if (clazz == null) {
/*      */ 
/*      */           
/*  418 */           long l1 = System.nanoTime();
/*  419 */           clazz = findClass(paramString);
/*      */ 
/*      */           
/*  422 */           PerfCounter.getParentDelegationTime().addTime(l1 - l);
/*  423 */           PerfCounter.getFindClassTime().addElapsedTimeFrom(l1);
/*  424 */           PerfCounter.getFindClasses().increment();
/*      */         } 
/*      */       } 
/*  427 */       if (paramBoolean) {
/*  428 */         resolveClass(clazz);
/*      */       }
/*  430 */       return clazz;
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
/*      */   protected Object getClassLoadingLock(String paramString) {
/*  455 */     Object object = this;
/*  456 */     if (this.parallelLockMap != null) {
/*  457 */       Object object1 = new Object();
/*  458 */       object = this.parallelLockMap.putIfAbsent(paramString, object1);
/*  459 */       if (object == null) {
/*  460 */         object = object1;
/*      */       }
/*      */     } 
/*  463 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Class<?> loadClassInternal(String paramString) throws ClassNotFoundException {
/*  472 */     if (this.parallelLockMap == null) {
/*  473 */       synchronized (this) {
/*  474 */         return loadClass(paramString);
/*      */       } 
/*      */     }
/*  477 */     return loadClass(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkPackageAccess(Class<?> paramClass, ProtectionDomain paramProtectionDomain) {
/*  483 */     final SecurityManager sm = System.getSecurityManager();
/*  484 */     if (securityManager != null) {
/*  485 */       if (ReflectUtil.isNonPublicProxyClass(paramClass)) {
/*  486 */         for (Class<?> clazz : paramClass.getInterfaces()) {
/*  487 */           checkPackageAccess(clazz, paramProtectionDomain);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*  492 */       final String name = paramClass.getName();
/*  493 */       final int i = str.lastIndexOf('.');
/*  494 */       if (i != -1) {
/*  495 */         AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */               public Void run() {
/*  497 */                 sm.checkPackageAccess(name.substring(0, i));
/*  498 */                 return null;
/*      */               }
/*      */             }new AccessControlContext(new ProtectionDomain[] { paramProtectionDomain }));
/*      */       }
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
/*      */   protected Class<?> findClass(String paramString) throws ClassNotFoundException {
/*  524 */     throw new ClassNotFoundException(paramString);
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
/*      */   @Deprecated
/*      */   protected final Class<?> defineClass(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ClassFormatError {
/*  572 */     return defineClass(null, paramArrayOfbyte, paramInt1, paramInt2, null);
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
/*      */   protected final Class<?> defineClass(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws ClassFormatError {
/*  636 */     return defineClass(paramString, paramArrayOfbyte, paramInt1, paramInt2, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ProtectionDomain preDefineClass(String paramString, ProtectionDomain paramProtectionDomain) {
/*  647 */     if (!checkName(paramString)) {
/*  648 */       throw new NoClassDefFoundError("IllegalName: " + paramString);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  653 */     if (paramString != null && paramString.startsWith("java.")) {
/*  654 */       throw new SecurityException("Prohibited package name: " + paramString
/*      */           
/*  656 */           .substring(0, paramString.lastIndexOf('.')));
/*      */     }
/*  658 */     if (paramProtectionDomain == null) {
/*  659 */       paramProtectionDomain = this.defaultDomain;
/*      */     }
/*      */     
/*  662 */     if (paramString != null) checkCerts(paramString, paramProtectionDomain.getCodeSource());
/*      */     
/*  664 */     return paramProtectionDomain;
/*      */   }
/*      */ 
/*      */   
/*      */   private String defineClassSourceLocation(ProtectionDomain paramProtectionDomain) {
/*  669 */     CodeSource codeSource = paramProtectionDomain.getCodeSource();
/*  670 */     String str = null;
/*  671 */     if (codeSource != null && codeSource.getLocation() != null) {
/*  672 */       str = codeSource.getLocation().toString();
/*      */     }
/*  674 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   private void postDefineClass(Class<?> paramClass, ProtectionDomain paramProtectionDomain) {
/*  679 */     if (paramProtectionDomain.getCodeSource() != null) {
/*  680 */       Certificate[] arrayOfCertificate = paramProtectionDomain.getCodeSource().getCertificates();
/*  681 */       if (arrayOfCertificate != null) {
/*  682 */         setSigners(paramClass, (Object[])arrayOfCertificate);
/*      */       }
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
/*      */   protected final Class<?> defineClass(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, ProtectionDomain paramProtectionDomain) throws ClassFormatError {
/*  755 */     paramProtectionDomain = preDefineClass(paramString, paramProtectionDomain);
/*  756 */     String str = defineClassSourceLocation(paramProtectionDomain);
/*  757 */     Class<?> clazz = defineClass1(paramString, paramArrayOfbyte, paramInt1, paramInt2, paramProtectionDomain, str);
/*  758 */     postDefineClass(clazz, paramProtectionDomain);
/*  759 */     return clazz;
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
/*      */   protected final Class<?> defineClass(String paramString, ByteBuffer paramByteBuffer, ProtectionDomain paramProtectionDomain) throws ClassFormatError {
/*  828 */     int i = paramByteBuffer.remaining();
/*      */ 
/*      */     
/*  831 */     if (!paramByteBuffer.isDirect()) {
/*  832 */       if (paramByteBuffer.hasArray()) {
/*  833 */         return defineClass(paramString, paramByteBuffer.array(), paramByteBuffer
/*  834 */             .position() + paramByteBuffer.arrayOffset(), i, paramProtectionDomain);
/*      */       }
/*      */ 
/*      */       
/*  838 */       byte[] arrayOfByte = new byte[i];
/*  839 */       paramByteBuffer.get(arrayOfByte);
/*  840 */       return defineClass(paramString, arrayOfByte, 0, i, paramProtectionDomain);
/*      */     } 
/*      */ 
/*      */     
/*  844 */     paramProtectionDomain = preDefineClass(paramString, paramProtectionDomain);
/*  845 */     String str = defineClassSourceLocation(paramProtectionDomain);
/*  846 */     Class<?> clazz = defineClass2(paramString, paramByteBuffer, paramByteBuffer.position(), i, paramProtectionDomain, str);
/*  847 */     postDefineClass(clazz, paramProtectionDomain);
/*  848 */     return clazz;
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
/*      */   private boolean checkName(String paramString) {
/*  863 */     if (paramString == null || paramString.length() == 0)
/*  864 */       return true; 
/*  865 */     if (paramString.indexOf('/') != -1 || (
/*  866 */       !VM.allowArraySyntax() && paramString.charAt(0) == '['))
/*  867 */       return false; 
/*  868 */     return true;
/*      */   }
/*      */   
/*      */   private void checkCerts(String paramString, CodeSource paramCodeSource) {
/*  872 */     int i = paramString.lastIndexOf('.');
/*  873 */     String str = (i == -1) ? "" : paramString.substring(0, i);
/*      */     
/*  875 */     Certificate[] arrayOfCertificate1 = null;
/*  876 */     if (paramCodeSource != null) {
/*  877 */       arrayOfCertificate1 = paramCodeSource.getCertificates();
/*      */     }
/*  879 */     Certificate[] arrayOfCertificate2 = null;
/*  880 */     if (this.parallelLockMap == null) {
/*  881 */       synchronized (this) {
/*  882 */         arrayOfCertificate2 = this.package2certs.get(str);
/*  883 */         if (arrayOfCertificate2 == null) {
/*  884 */           this.package2certs.put(str, (arrayOfCertificate1 == null) ? nocerts : arrayOfCertificate1);
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/*  889 */       arrayOfCertificate2 = ((ConcurrentHashMap<String, Certificate[]>)this.package2certs).putIfAbsent(str, (arrayOfCertificate1 == null) ? nocerts : arrayOfCertificate1);
/*      */     } 
/*  891 */     if (arrayOfCertificate2 != null && !compareCerts(arrayOfCertificate2, arrayOfCertificate1)) {
/*  892 */       throw new SecurityException("class \"" + paramString + "\"'s signer information does not match signer information of other classes in the same package");
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
/*      */   private boolean compareCerts(Certificate[] paramArrayOfCertificate1, Certificate[] paramArrayOfCertificate2) {
/*  905 */     if (paramArrayOfCertificate2 == null || paramArrayOfCertificate2.length == 0) {
/*  906 */       return (paramArrayOfCertificate1.length == 0);
/*      */     }
/*      */ 
/*      */     
/*  910 */     if (paramArrayOfCertificate2.length != paramArrayOfCertificate1.length) {
/*  911 */       return false;
/*      */     }
/*      */     
/*      */     byte b;
/*      */     
/*  916 */     for (b = 0; b < paramArrayOfCertificate2.length; b++) {
/*  917 */       boolean bool = false;
/*  918 */       for (byte b1 = 0; b1 < paramArrayOfCertificate1.length; b1++) {
/*  919 */         if (paramArrayOfCertificate2[b].equals(paramArrayOfCertificate1[b1])) {
/*  920 */           bool = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*  924 */       if (!bool) return false;
/*      */     
/*      */     } 
/*      */     
/*  928 */     for (b = 0; b < paramArrayOfCertificate1.length; b++) {
/*  929 */       boolean bool = false;
/*  930 */       for (byte b1 = 0; b1 < paramArrayOfCertificate2.length; b1++) {
/*  931 */         if (paramArrayOfCertificate1[b].equals(paramArrayOfCertificate2[b1])) {
/*  932 */           bool = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*  936 */       if (!bool) return false;
/*      */     
/*      */     } 
/*  939 */     return true;
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
/*      */   protected final void resolveClass(Class<?> paramClass) {
/*  958 */     resolveClass0(paramClass);
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
/*      */   protected final Class<?> findSystemClass(String paramString) throws ClassNotFoundException {
/*  988 */     ClassLoader classLoader = getSystemClassLoader();
/*  989 */     if (classLoader == null) {
/*  990 */       if (!checkName(paramString))
/*  991 */         throw new ClassNotFoundException(paramString); 
/*  992 */       Class<?> clazz = findBootstrapClass(paramString);
/*  993 */       if (clazz == null) {
/*  994 */         throw new ClassNotFoundException(paramString);
/*      */       }
/*  996 */       return clazz;
/*      */     } 
/*  998 */     return classLoader.loadClass(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Class<?> findBootstrapClassOrNull(String paramString) {
/* 1007 */     if (!checkName(paramString)) return null;
/*      */     
/* 1009 */     return findBootstrapClass(paramString);
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
/*      */   protected final Class<?> findLoadedClass(String paramString) {
/* 1030 */     if (!checkName(paramString))
/* 1031 */       return null; 
/* 1032 */     return findLoadedClass0(paramString);
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
/*      */   protected final void setSigners(Class<?> paramClass, Object[] paramArrayOfObject) {
/* 1050 */     paramClass.setSigners(paramArrayOfObject);
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
/*      */   public URL getResource(String paramString) {
/*      */     URL uRL;
/* 1084 */     if (this.parent != null) {
/* 1085 */       uRL = this.parent.getResource(paramString);
/*      */     } else {
/* 1087 */       uRL = getBootstrapResource(paramString);
/*      */     } 
/* 1089 */     if (uRL == null) {
/* 1090 */       uRL = findResource(paramString);
/*      */     }
/* 1092 */     return uRL;
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
/*      */   public Enumeration<URL> getResources(String paramString) throws IOException {
/* 1130 */     Enumeration[] arrayOfEnumeration = new Enumeration[2];
/* 1131 */     if (this.parent != null) {
/* 1132 */       arrayOfEnumeration[0] = this.parent.getResources(paramString);
/*      */     } else {
/* 1134 */       arrayOfEnumeration[0] = getBootstrapResources(paramString);
/*      */     } 
/* 1136 */     arrayOfEnumeration[1] = findResources(paramString);
/*      */     
/* 1138 */     return (Enumeration<URL>)new CompoundEnumeration(arrayOfEnumeration);
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
/*      */   protected URL findResource(String paramString) {
/* 1154 */     return null;
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
/*      */   protected Enumeration<URL> findResources(String paramString) throws IOException {
/* 1175 */     return Collections.emptyEnumeration();
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
/*      */   @CallerSensitive
/*      */   protected static boolean registerAsParallelCapable() {
/* 1198 */     Class<? extends ClassLoader> clazz = Reflection.getCallerClass().asSubclass(ClassLoader.class);
/* 1199 */     return ParallelLoaders.register(clazz);
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
/*      */   public static URL getSystemResource(String paramString) {
/* 1216 */     ClassLoader classLoader = getSystemClassLoader();
/* 1217 */     if (classLoader == null) {
/* 1218 */       return getBootstrapResource(paramString);
/*      */     }
/* 1220 */     return classLoader.getResource(paramString);
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
/*      */   public static Enumeration<URL> getSystemResources(String paramString) throws IOException {
/* 1246 */     ClassLoader classLoader = getSystemClassLoader();
/* 1247 */     if (classLoader == null) {
/* 1248 */       return getBootstrapResources(paramString);
/*      */     }
/* 1250 */     return classLoader.getResources(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static URL getBootstrapResource(String paramString) {
/* 1257 */     URLClassPath uRLClassPath = getBootstrapClassPath();
/* 1258 */     Resource resource = uRLClassPath.getResource(paramString);
/* 1259 */     return (resource != null) ? resource.getURL() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Enumeration<URL> getBootstrapResources(String paramString) throws IOException {
/* 1269 */     final Enumeration e = getBootstrapClassPath().getResources(paramString);
/* 1270 */     return new Enumeration<URL>() {
/*      */         public URL nextElement() {
/* 1272 */           return ((Resource)e.nextElement()).getURL();
/*      */         }
/*      */         public boolean hasMoreElements() {
/* 1275 */           return e.hasMoreElements();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   static URLClassPath getBootstrapClassPath() {
/* 1282 */     return Launcher.getBootstrapClassPath();
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
/*      */   public InputStream getResourceAsStream(String paramString) {
/* 1301 */     URL uRL = getResource(paramString);
/*      */     try {
/* 1303 */       return (uRL != null) ? uRL.openStream() : null;
/* 1304 */     } catch (IOException iOException) {
/* 1305 */       return null;
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
/*      */   public static InputStream getSystemResourceAsStream(String paramString) {
/* 1323 */     URL uRL = getSystemResource(paramString);
/*      */     try {
/* 1325 */       return (uRL != null) ? uRL.openStream() : null;
/* 1326 */     } catch (IOException iOException) {
/* 1327 */       return null;
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
/*      */   @CallerSensitive
/*      */   public final ClassLoader getParent() {
/* 1361 */     if (this.parent == null)
/* 1362 */       return null; 
/* 1363 */     SecurityManager securityManager = System.getSecurityManager();
/* 1364 */     if (securityManager != null)
/*      */     {
/*      */ 
/*      */       
/* 1368 */       checkClassLoaderPermission(this.parent, Reflection.getCallerClass());
/*      */     }
/* 1370 */     return this.parent;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public static ClassLoader getSystemClassLoader() {
/* 1430 */     initSystemClassLoader();
/* 1431 */     if (scl == null) {
/* 1432 */       return null;
/*      */     }
/* 1434 */     SecurityManager securityManager = System.getSecurityManager();
/* 1435 */     if (securityManager != null) {
/* 1436 */       checkClassLoaderPermission(scl, Reflection.getCallerClass());
/*      */     }
/* 1438 */     return scl;
/*      */   }
/*      */   
/*      */   private static synchronized void initSystemClassLoader() {
/* 1442 */     if (!sclSet) {
/* 1443 */       if (scl != null)
/* 1444 */         throw new IllegalStateException("recursive invocation"); 
/* 1445 */       Launcher launcher = Launcher.getLauncher();
/* 1446 */       if (launcher != null) {
/* 1447 */         Throwable throwable = null;
/* 1448 */         scl = launcher.getClassLoader();
/*      */         try {
/* 1450 */           scl = AccessController.<ClassLoader>doPrivileged(new SystemClassLoaderAction(scl));
/*      */         }
/* 1452 */         catch (PrivilegedActionException privilegedActionException) {
/* 1453 */           throwable = privilegedActionException.getCause();
/* 1454 */           if (throwable instanceof java.lang.reflect.InvocationTargetException) {
/* 1455 */             throwable = throwable.getCause();
/*      */           }
/*      */         } 
/* 1458 */         if (throwable != null) {
/* 1459 */           if (throwable instanceof Error) {
/* 1460 */             throw (Error)throwable;
/*      */           }
/*      */           
/* 1463 */           throw new Error(throwable);
/*      */         } 
/*      */       } 
/*      */       
/* 1467 */       sclSet = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void initLibraryPaths() {
/* 1478 */     usr_paths = initializePath("java.library.path");
/* 1479 */     sys_paths = initializePath("sun.boot.library.path");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isAncestor(ClassLoader paramClassLoader) {
/* 1485 */     ClassLoader classLoader = this;
/*      */     while (true) {
/* 1487 */       classLoader = classLoader.parent;
/* 1488 */       if (paramClassLoader == classLoader) {
/* 1489 */         return true;
/*      */       }
/* 1491 */       if (classLoader == null) {
/* 1492 */         return false;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean needsClassLoaderPermissionCheck(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2) {
/* 1503 */     if (paramClassLoader1 == paramClassLoader2) {
/* 1504 */       return false;
/*      */     }
/* 1506 */     if (paramClassLoader1 == null) {
/* 1507 */       return false;
/*      */     }
/* 1509 */     return !paramClassLoader2.isAncestor(paramClassLoader1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static ClassLoader getClassLoader(Class<?> paramClass) {
/* 1515 */     if (paramClass == null) {
/* 1516 */       return null;
/*      */     }
/*      */     
/* 1519 */     return paramClass.getClassLoader0();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkClassLoaderPermission(ClassLoader paramClassLoader, Class<?> paramClass) {
/* 1528 */     SecurityManager securityManager = System.getSecurityManager();
/* 1529 */     if (securityManager != null) {
/*      */       
/* 1531 */       ClassLoader classLoader = getClassLoader(paramClass);
/* 1532 */       if (needsClassLoaderPermissionCheck(classLoader, paramClassLoader)) {
/* 1533 */         securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Package definePackage(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, URL paramURL) throws IllegalArgumentException {
/* 1596 */     synchronized (this.packages) {
/* 1597 */       Package package_ = getPackage(paramString1);
/* 1598 */       if (package_ != null) {
/* 1599 */         throw new IllegalArgumentException(paramString1);
/*      */       }
/* 1601 */       package_ = new Package(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramURL, this);
/*      */ 
/*      */       
/* 1604 */       this.packages.put(paramString1, package_);
/* 1605 */       return package_;
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
/*      */   protected Package getPackage(String paramString) {
/*      */     Package package_;
/* 1623 */     synchronized (this.packages) {
/* 1624 */       package_ = this.packages.get(paramString);
/*      */     } 
/* 1626 */     if (package_ == null) {
/* 1627 */       if (this.parent != null) {
/* 1628 */         package_ = this.parent.getPackage(paramString);
/*      */       } else {
/* 1630 */         package_ = Package.getSystemPackage(paramString);
/*      */       } 
/* 1632 */       if (package_ != null) {
/* 1633 */         synchronized (this.packages) {
/* 1634 */           Package package_1 = this.packages.get(paramString);
/* 1635 */           if (package_1 == null) {
/* 1636 */             this.packages.put(paramString, package_);
/*      */           } else {
/* 1638 */             package_ = package_1;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 1643 */     return package_;
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
/*      */   protected Package[] getPackages() {
/*      */     HashMap<String, Package> hashMap;
/*      */     Package[] arrayOfPackage;
/* 1657 */     synchronized (this.packages) {
/* 1658 */       hashMap = new HashMap<>(this.packages);
/*      */     } 
/*      */     
/* 1661 */     if (this.parent != null) {
/* 1662 */       arrayOfPackage = this.parent.getPackages();
/*      */     } else {
/* 1664 */       arrayOfPackage = Package.getSystemPackages();
/*      */     } 
/* 1666 */     if (arrayOfPackage != null) {
/* 1667 */       for (byte b = 0; b < arrayOfPackage.length; b++) {
/* 1668 */         String str = arrayOfPackage[b].getName();
/* 1669 */         if (hashMap.get(str) == null) {
/* 1670 */           hashMap.put(str, arrayOfPackage[b]);
/*      */         }
/*      */       } 
/*      */     }
/* 1674 */     return (Package[])hashMap.values().toArray((Object[])new Package[hashMap.size()]);
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
/*      */   protected String findLibrary(String paramString) {
/* 1698 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class NativeLibrary
/*      */   {
/*      */     long handle;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int jniVersion;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Class<?> fromClass;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String name;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isBuiltin;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean loaded;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NativeLibrary(Class<?> param1Class, String param1String, boolean param1Boolean) {
/* 1737 */       this.name = param1String;
/* 1738 */       this.fromClass = param1Class;
/* 1739 */       this.isBuiltin = param1Boolean;
/*      */     }
/*      */     
/*      */     protected void finalize() {
/* 1743 */       synchronized (ClassLoader.loadedLibraryNames) {
/* 1744 */         if (this.fromClass.getClassLoader() != null && this.loaded) {
/*      */           
/* 1746 */           int i = ClassLoader.loadedLibraryNames.size();
/* 1747 */           for (byte b = 0; b < i; b++) {
/* 1748 */             if (this.name.equals(ClassLoader.loadedLibraryNames.elementAt(b))) {
/* 1749 */               ClassLoader.loadedLibraryNames.removeElementAt(b);
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/* 1754 */           ClassLoader.nativeLibraryContext.push(this);
/*      */           try {
/* 1756 */             unload(this.name, this.isBuiltin);
/*      */           } finally {
/* 1758 */             ClassLoader.nativeLibraryContext.pop();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     static Class<?> getFromClass() {
/* 1766 */       return (ClassLoader.nativeLibraryContext.peek()).fromClass;
/*      */     }
/*      */     native void load(String param1String, boolean param1Boolean);
/*      */     native long find(String param1String);
/*      */     native void unload(String param1String, boolean param1Boolean); }
/* 1771 */   private static Vector<String> loadedLibraryNames = new Vector<>();
/*      */ 
/*      */   
/* 1774 */   private static Vector<NativeLibrary> systemNativeLibraries = new Vector<>();
/*      */   private Vector<NativeLibrary> nativeLibraries;
/*      */   
/*      */   private ClassLoader(Void paramVoid, ClassLoader paramClassLoader) {
/* 1778 */     this.nativeLibraries = new Vector<>();
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
/* 1983 */     this.defaultAssertionStatus = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1991 */     this.packageAssertionStatus = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1998 */     this.classAssertionStatus = null;
/*      */     this.parent = paramClassLoader;
/*      */     if (ParallelLoaders.isRegistered((Class)getClass())) {
/*      */       this.parallelLockMap = new ConcurrentHashMap<>();
/*      */       this.package2certs = (Map)new ConcurrentHashMap<>();
/*      */       this.assertionLock = new Object();
/*      */     } else {
/*      */       this.parallelLockMap = null;
/*      */       this.package2certs = (Map)new Hashtable<>();
/*      */       this.assertionLock = this;
/*      */     } 
/*      */   }
/*      */   private static Stack<NativeLibrary> nativeLibraryContext = new Stack<>();
/*      */   private static String[] usr_paths;
/*      */   private static String[] sys_paths;
/*      */   final Object assertionLock;
/*      */   private boolean defaultAssertionStatus;
/*      */   
/* 2016 */   public void setDefaultAssertionStatus(boolean paramBoolean) { synchronized (this.assertionLock) {
/* 2017 */       if (this.classAssertionStatus == null) {
/* 2018 */         initializeJavaAssertionMaps();
/*      */       }
/* 2020 */       this.defaultAssertionStatus = paramBoolean;
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<String, Boolean> packageAssertionStatus;
/*      */ 
/*      */   
/*      */   Map<String, Boolean> classAssertionStatus;
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] initializePath(String paramString) {
/*      */     String str1 = System.getProperty(paramString, "");
/*      */     String str2 = File.pathSeparator;
/*      */     int i = str1.length();
/*      */     int j = str1.indexOf(str2);
/*      */     int m = 0;
/*      */     while (j >= 0) {
/*      */       m++;
/*      */       j = str1.indexOf(str2, j + 1);
/*      */     } 
/*      */     String[] arrayOfString = new String[m + 1];
/*      */     m = j = 0;
/*      */     int k = str1.indexOf(str2);
/*      */     while (k >= 0) {
/*      */       if (k - j > 0) {
/*      */         arrayOfString[m++] = str1.substring(j, k);
/*      */       } else if (k - j == 0) {
/*      */         arrayOfString[m++] = ".";
/*      */       } 
/*      */       j = k + 1;
/*      */       k = str1.indexOf(str2, j);
/*      */     } 
/*      */     arrayOfString[m] = str1.substring(j, i);
/*      */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPackageAssertionStatus(String paramString, boolean paramBoolean) {
/* 2063 */     synchronized (this.assertionLock) {
/* 2064 */       if (this.packageAssertionStatus == null) {
/* 2065 */         initializeJavaAssertionMaps();
/*      */       }
/* 2067 */       this.packageAssertionStatus.put(paramString, Boolean.valueOf(paramBoolean));
/*      */     } 
/*      */   } static void loadLibrary(Class<?> paramClass, String paramString, boolean paramBoolean) { ClassLoader classLoader = (paramClass == null) ? null : paramClass.getClassLoader(); assert sys_paths != null : "should be initialized at this point"; assert usr_paths != null : "should be initialized at this point"; if (paramBoolean) {
/*      */       if (loadLibrary0(paramClass, new File(paramString)))
/*      */         return;  throw new UnsatisfiedLinkError("Can't load library: " + paramString);
/*      */     }  if (classLoader != null) {
/*      */       String str = classLoader.findLibrary(paramString); if (str != null) {
/*      */         File file = new File(str); if (!file.isAbsolute())
/*      */           throw new UnsatisfiedLinkError("ClassLoader.findLibrary failed to return an absolute path: " + str);  if (loadLibrary0(paramClass, file))
/*      */           return;  throw new UnsatisfiedLinkError("Can't load " + str);
/*      */       } 
/*      */     }  byte b; for (b = 0; b < sys_paths.length; b++) {
/*      */       File file = new File(sys_paths[b], System.mapLibraryName(paramString)); if (loadLibrary0(paramClass, file))
/*      */         return; 
/*      */       file = ClassLoaderHelper.mapAlternativeName(file);
/*      */       if (file != null && loadLibrary0(paramClass, file))
/*      */         return; 
/*      */     } 
/*      */     if (classLoader != null)
/*      */       for (b = 0; b < usr_paths.length; b++) {
/*      */         File file = new File(usr_paths[b], System.mapLibraryName(paramString));
/*      */         if (loadLibrary0(paramClass, file))
/*      */           return; 
/*      */         file = ClassLoaderHelper.mapAlternativeName(file);
/*      */         if (file != null && loadLibrary0(paramClass, file))
/*      */           return; 
/*      */       }  
/* 2094 */     throw new UnsatisfiedLinkError("no " + paramString + " in java.library.path"); } public void setClassAssertionStatus(String paramString, boolean paramBoolean) { synchronized (this.assertionLock) {
/* 2095 */       if (this.classAssertionStatus == null) {
/* 2096 */         initializeJavaAssertionMaps();
/*      */       }
/* 2098 */       this.classAssertionStatus.put(paramString, Boolean.valueOf(paramBoolean));
/*      */     }  }
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
/*      */   public void clearAssertionStatus() {
/* 2116 */     synchronized (this.assertionLock) {
/* 2117 */       this.classAssertionStatus = new HashMap<>();
/* 2118 */       this.packageAssertionStatus = new HashMap<>();
/* 2119 */       this.defaultAssertionStatus = false;
/*      */     } 
/*      */   } private static boolean loadLibrary0(Class<?> paramClass, final File file) { String str = findBuiltinLib(file.getName()); boolean bool = (str != null) ? true : false; if (!bool) { boolean bool1 = (AccessController.doPrivileged(new PrivilegedAction() {
/*      */             public Object run() { return file.exists() ? Boolean.TRUE : null; }
/*      */           }) != null) ? true : false; if (!bool1)
/*      */         return false;  try { str = file.getCanonicalPath(); }
/*      */       catch (IOException iOException) { return false; }
/*      */        }
/*      */      ClassLoader classLoader = (paramClass == null) ? null : paramClass.getClassLoader(); Vector<NativeLibrary> vector = (classLoader != null) ? classLoader.nativeLibraries : systemNativeLibraries; synchronized (vector) { int i = vector.size(); for (byte b = 0; b < i; b++) { NativeLibrary nativeLibrary = vector.elementAt(b); if (str.equals(nativeLibrary.name))
/*      */           return true;  }
/*      */        synchronized (loadedLibraryNames) { if (loadedLibraryNames.contains(str))
/*      */           throw new UnsatisfiedLinkError("Native Library " + str + " already loaded in another classloader");  int j = nativeLibraryContext.size(); for (byte b1 = 0; b1 < j; b1++) { NativeLibrary nativeLibrary1 = nativeLibraryContext.elementAt(b1); if (str.equals(nativeLibrary1.name)) { if (classLoader == nativeLibrary1.fromClass.getClassLoader())
/*      */               return true;  throw new UnsatisfiedLinkError("Native Library " + str + " is being loaded in another classloader"); }
/*      */            }
/*      */          NativeLibrary nativeLibrary = new NativeLibrary(paramClass, str, bool); nativeLibraryContext.push(nativeLibrary); try {
/*      */           nativeLibrary.load(str, bool);
/*      */         } finally {
/*      */           nativeLibraryContext.pop();
/*      */         }  if (nativeLibrary.loaded) {
/*      */           loadedLibraryNames.addElement(str); vector.addElement(nativeLibrary); return true;
/*      */         }  return false; }
/*      */        }
/*      */      } static long findNative(ClassLoader paramClassLoader, String paramString) { Vector<NativeLibrary> vector = (paramClassLoader != null) ? paramClassLoader.nativeLibraries : systemNativeLibraries; synchronized (vector) {
/*      */       int i = vector.size(); for (byte b = 0; b < i; b++) {
/*      */         NativeLibrary nativeLibrary = vector.elementAt(b); long l = nativeLibrary.find(paramString); if (l != 0L)
/*      */           return l; 
/*      */       } 
/* 2146 */     }  return 0L; } boolean desiredAssertionStatus(String paramString) { synchronized (this.assertionLock) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2151 */       Boolean bool = this.classAssertionStatus.get(paramString);
/* 2152 */       if (bool != null) {
/* 2153 */         return bool.booleanValue();
/*      */       }
/*      */       
/* 2156 */       int i = paramString.lastIndexOf(".");
/* 2157 */       if (i < 0) {
/* 2158 */         bool = this.packageAssertionStatus.get(null);
/* 2159 */         if (bool != null)
/* 2160 */           return bool.booleanValue(); 
/*      */       } 
/* 2162 */       while (i > 0) {
/* 2163 */         paramString = paramString.substring(0, i);
/* 2164 */         bool = this.packageAssertionStatus.get(paramString);
/* 2165 */         if (bool != null)
/* 2166 */           return bool.booleanValue(); 
/* 2167 */         i = paramString.lastIndexOf(".", i - 1);
/*      */       } 
/*      */ 
/*      */       
/* 2171 */       return this.defaultAssertionStatus;
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initializeJavaAssertionMaps() {
/* 2180 */     this.classAssertionStatus = new HashMap<>();
/* 2181 */     this.packageAssertionStatus = new HashMap<>();
/* 2182 */     AssertionStatusDirectives assertionStatusDirectives = retrieveDirectives();
/*      */     byte b;
/* 2184 */     for (b = 0; b < assertionStatusDirectives.classes.length; b++) {
/* 2185 */       this.classAssertionStatus.put(assertionStatusDirectives.classes[b], 
/* 2186 */           Boolean.valueOf(assertionStatusDirectives.classEnabled[b]));
/*      */     }
/* 2188 */     for (b = 0; b < assertionStatusDirectives.packages.length; b++) {
/* 2189 */       this.packageAssertionStatus.put(assertionStatusDirectives.packages[b], 
/* 2190 */           Boolean.valueOf(assertionStatusDirectives.packageEnabled[b]));
/*      */     }
/* 2192 */     this.defaultAssertionStatus = assertionStatusDirectives.deflt;
/*      */   }
/*      */   
/*      */   private static native void registerNatives();
/*      */   
/*      */   private native Class<?> defineClass0(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, ProtectionDomain paramProtectionDomain);
/*      */   
/*      */   private native Class<?> defineClass1(String paramString1, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, ProtectionDomain paramProtectionDomain, String paramString2);
/*      */   
/*      */   private native Class<?> defineClass2(String paramString1, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, ProtectionDomain paramProtectionDomain, String paramString2);
/*      */   
/*      */   private native void resolveClass0(Class<?> paramClass);
/*      */   
/*      */   private native Class<?> findBootstrapClass(String paramString);
/*      */   
/*      */   private final native Class<?> findLoadedClass0(String paramString);
/*      */   
/*      */   private static native String findBuiltinLib(String paramString);
/*      */   
/*      */   private static native AssertionStatusDirectives retrieveDirectives();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/ClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */