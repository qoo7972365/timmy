/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permissions;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanPermission;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.OperationsException;
/*     */ import javax.management.ReflectionException;
/*     */ import javax.management.RuntimeErrorException;
/*     */ import javax.management.RuntimeMBeanException;
/*     */ import javax.management.RuntimeOperationsException;
/*     */ import sun.reflect.misc.ConstructorUtil;
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
/*     */ public class MBeanInstantiator
/*     */ {
/*     */   private final ModifiableClassLoaderRepository clr;
/*     */   
/*     */   MBeanInstantiator(ModifiableClassLoaderRepository paramModifiableClassLoaderRepository) {
/*  71 */     this.clr = paramModifiableClassLoaderRepository;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void testCreation(Class<?> paramClass) throws NotCompliantMBeanException {
/*  81 */     Introspector.testCreation(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findClassWithDefaultLoaderRepository(String paramString) throws ReflectionException {
/*     */     Class<?> clazz;
/*  92 */     if (paramString == null) {
/*  93 */       throw new RuntimeOperationsException(new IllegalArgumentException("The class name cannot be null"), "Exception occurred during object instantiation");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  98 */     ReflectUtil.checkPackageAccess(paramString);
/*     */     try {
/* 100 */       if (this.clr == null) throw new ClassNotFoundException(paramString); 
/* 101 */       clazz = this.clr.loadClass(paramString);
/*     */     }
/* 103 */     catch (ClassNotFoundException classNotFoundException) {
/* 104 */       throw new ReflectionException(classNotFoundException, "The MBean class could not be loaded by the default loader repository");
/*     */     } 
/*     */ 
/*     */     
/* 108 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findClass(String paramString, ClassLoader paramClassLoader) throws ReflectionException {
/* 119 */     return loadClass(paramString, paramClassLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findClass(String paramString, ObjectName paramObjectName) throws ReflectionException, InstanceNotFoundException {
/* 129 */     if (paramObjectName == null) {
/* 130 */       throw new RuntimeOperationsException(new IllegalArgumentException(), "Null loader passed in parameter");
/*     */     }
/*     */ 
/*     */     
/* 134 */     ClassLoader classLoader = null;
/* 135 */     synchronized (this) {
/* 136 */       classLoader = getClassLoader(paramObjectName);
/*     */     } 
/* 138 */     if (classLoader == null) {
/* 139 */       throw new InstanceNotFoundException("The loader named " + paramObjectName + " is not registered in the MBeanServer");
/*     */     }
/*     */     
/* 142 */     return findClass(paramString, classLoader);
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
/*     */   public Class<?>[] findSignatureClasses(String[] paramArrayOfString, ClassLoader paramClassLoader) throws ReflectionException {
/* 154 */     if (paramArrayOfString == null) return null; 
/* 155 */     ClassLoader classLoader = paramClassLoader;
/* 156 */     int i = paramArrayOfString.length;
/* 157 */     Class[] arrayOfClass = new Class[i];
/*     */     
/* 159 */     if (i == 0) return arrayOfClass; 
/*     */     try {
/* 161 */       for (byte b = 0; b < i; b++) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 166 */         Class clazz = primitiveClasses.get(paramArrayOfString[b]);
/* 167 */         if (clazz != null) {
/* 168 */           arrayOfClass[b] = clazz;
/*     */         }
/*     */         else {
/*     */           
/* 172 */           ReflectUtil.checkPackageAccess(paramArrayOfString[b]);
/*     */ 
/*     */ 
/*     */           
/* 176 */           if (classLoader != null)
/*     */           
/*     */           { 
/*     */             
/* 180 */             arrayOfClass[b] = Class.forName(paramArrayOfString[b], false, classLoader); }
/*     */           
/*     */           else
/*     */           
/* 184 */           { arrayOfClass[b] = findClass(paramArrayOfString[b], 
/* 185 */                 getClass().getClassLoader()); } 
/*     */         } 
/*     */       } 
/* 188 */     } catch (ClassNotFoundException classNotFoundException) {
/* 189 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINEST)) {
/* 190 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINEST, MBeanInstantiator.class
/* 191 */             .getName(), "findSignatureClasses", "The parameter class could not be found", classNotFoundException);
/*     */       }
/*     */ 
/*     */       
/* 195 */       throw new ReflectionException(classNotFoundException, "The parameter class could not be found");
/*     */     }
/* 197 */     catch (RuntimeException runtimeException) {
/* 198 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINEST)) {
/* 199 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINEST, MBeanInstantiator.class
/* 200 */             .getName(), "findSignatureClasses", "Unexpected exception", runtimeException);
/*     */       }
/*     */ 
/*     */       
/* 204 */       throw runtimeException;
/*     */     } 
/* 206 */     return arrayOfClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object instantiate(Class<?> paramClass) throws ReflectionException, MBeanException {
/*     */     Object object;
/* 217 */     checkMBeanPermission(paramClass, (String)null, (ObjectName)null, "instantiate");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     Constructor<?> constructor = findConstructor(paramClass, null);
/* 224 */     if (constructor == null) {
/* 225 */       throw new ReflectionException(new NoSuchMethodException("No such constructor"));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 230 */       ReflectUtil.checkPackageAccess(paramClass);
/* 231 */       ensureClassAccess(paramClass);
/* 232 */       object = constructor.newInstance(new Object[0]);
/* 233 */     } catch (InvocationTargetException invocationTargetException) {
/*     */       
/* 235 */       Throwable throwable = invocationTargetException.getTargetException();
/* 236 */       if (throwable instanceof RuntimeException) {
/* 237 */         throw new RuntimeMBeanException((RuntimeException)throwable, "RuntimeException thrown in the MBean's empty constructor");
/*     */       }
/* 239 */       if (throwable instanceof Error) {
/* 240 */         throw new RuntimeErrorException((Error)throwable, "Error thrown in the MBean's empty constructor");
/*     */       }
/*     */       
/* 243 */       throw new MBeanException((Exception)throwable, "Exception thrown in the MBean's empty constructor");
/*     */     
/*     */     }
/* 246 */     catch (NoSuchMethodError noSuchMethodError) {
/* 247 */       throw new ReflectionException(new NoSuchMethodException("No constructor"), "No such constructor");
/*     */     
/*     */     }
/* 250 */     catch (InstantiationException instantiationException) {
/* 251 */       throw new ReflectionException(instantiationException, "Exception thrown trying to invoke the MBean's empty constructor");
/*     */     }
/* 253 */     catch (IllegalAccessException illegalAccessException) {
/* 254 */       throw new ReflectionException(illegalAccessException, "Exception thrown trying to invoke the MBean's empty constructor");
/*     */     }
/* 256 */     catch (IllegalArgumentException illegalArgumentException) {
/* 257 */       throw new ReflectionException(illegalArgumentException, "Exception thrown trying to invoke the MBean's empty constructor");
/*     */     } 
/*     */     
/* 260 */     return object;
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
/*     */   public Object instantiate(Class<?> paramClass, Object[] paramArrayOfObject, String[] paramArrayOfString, ClassLoader paramClassLoader) throws ReflectionException, MBeanException {
/*     */     Class<?>[] arrayOfClass;
/*     */     Object object;
/* 275 */     checkMBeanPermission(paramClass, (String)null, (ObjectName)null, "instantiate");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 285 */       ClassLoader classLoader = paramClass.getClassLoader();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 290 */       arrayOfClass = (paramArrayOfString == null) ? null : findSignatureClasses(paramArrayOfString, classLoader);
/*     */     
/*     */     }
/* 293 */     catch (IllegalArgumentException illegalArgumentException) {
/* 294 */       throw new ReflectionException(illegalArgumentException, "The constructor parameter classes could not be loaded");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 299 */     Constructor<?> constructor = findConstructor(paramClass, arrayOfClass);
/*     */     
/* 301 */     if (constructor == null) {
/* 302 */       throw new ReflectionException(new NoSuchMethodException("No such constructor"));
/*     */     }
/*     */     
/*     */     try {
/* 306 */       ReflectUtil.checkPackageAccess(paramClass);
/* 307 */       ensureClassAccess(paramClass);
/* 308 */       object = constructor.newInstance(paramArrayOfObject);
/*     */     }
/* 310 */     catch (NoSuchMethodError noSuchMethodError) {
/* 311 */       throw new ReflectionException(new NoSuchMethodException("No such constructor found"), "No such constructor");
/*     */ 
/*     */     
/*     */     }
/* 315 */     catch (InstantiationException instantiationException) {
/* 316 */       throw new ReflectionException(instantiationException, "Exception thrown trying to invoke the MBean's constructor");
/*     */     
/*     */     }
/* 319 */     catch (IllegalAccessException illegalAccessException) {
/* 320 */       throw new ReflectionException(illegalAccessException, "Exception thrown trying to invoke the MBean's constructor");
/*     */     
/*     */     }
/* 323 */     catch (InvocationTargetException invocationTargetException) {
/*     */       
/* 325 */       Throwable throwable = invocationTargetException.getTargetException();
/* 326 */       if (throwable instanceof RuntimeException) {
/* 327 */         throw new RuntimeMBeanException((RuntimeException)throwable, "RuntimeException thrown in the MBean's constructor");
/*     */       }
/* 329 */       if (throwable instanceof Error) {
/* 330 */         throw new RuntimeErrorException((Error)throwable, "Error thrown in the MBean's constructor");
/*     */       }
/*     */       
/* 333 */       throw new MBeanException((Exception)throwable, "Exception thrown in the MBean's constructor");
/*     */     } 
/*     */ 
/*     */     
/* 337 */     return object;
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
/*     */   public ObjectInputStream deserialize(ClassLoader paramClassLoader, byte[] paramArrayOfbyte) throws OperationsException {
/*     */     ObjectInputStreamWithLoader objectInputStreamWithLoader;
/* 355 */     if (paramArrayOfbyte == null) {
/* 356 */       throw new RuntimeOperationsException(new IllegalArgumentException(), "Null data passed in parameter");
/*     */     }
/*     */     
/* 359 */     if (paramArrayOfbyte.length == 0) {
/* 360 */       throw new RuntimeOperationsException(new IllegalArgumentException(), "Empty data passed in parameter");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/*     */     try {
/* 370 */       objectInputStreamWithLoader = new ObjectInputStreamWithLoader(byteArrayInputStream, paramClassLoader);
/* 371 */     } catch (IOException iOException) {
/* 372 */       throw new OperationsException("An IOException occurred trying to de-serialize the data");
/*     */     } 
/*     */ 
/*     */     
/* 376 */     return objectInputStreamWithLoader;
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
/*     */   public ObjectInputStream deserialize(String paramString, ObjectName paramObjectName, byte[] paramArrayOfbyte, ClassLoader paramClassLoader) throws InstanceNotFoundException, OperationsException, ReflectionException {
/*     */     Class<?> clazz;
/*     */     ObjectInputStreamWithLoader objectInputStreamWithLoader;
/* 412 */     if (paramArrayOfbyte == null) {
/* 413 */       throw new RuntimeOperationsException(new IllegalArgumentException(), "Null data passed in parameter");
/*     */     }
/*     */     
/* 416 */     if (paramArrayOfbyte.length == 0) {
/* 417 */       throw new RuntimeOperationsException(new IllegalArgumentException(), "Empty data passed in parameter");
/*     */     }
/*     */     
/* 420 */     if (paramString == null) {
/* 421 */       throw new RuntimeOperationsException(new IllegalArgumentException(), "Null className passed in parameter");
/*     */     }
/*     */ 
/*     */     
/* 425 */     ReflectUtil.checkPackageAccess(paramString);
/*     */     
/* 427 */     if (paramObjectName == null) {
/*     */       
/* 429 */       clazz = findClass(paramString, paramClassLoader);
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/* 434 */         ClassLoader classLoader = null;
/*     */         
/* 436 */         classLoader = getClassLoader(paramObjectName);
/* 437 */         if (classLoader == null)
/* 438 */           throw new ClassNotFoundException(paramString); 
/* 439 */         clazz = Class.forName(paramString, false, classLoader);
/*     */       }
/* 441 */       catch (ClassNotFoundException classNotFoundException) {
/* 442 */         throw new ReflectionException(classNotFoundException, "The MBean class could not be loaded by the " + paramObjectName
/*     */             
/* 444 */             .toString() + " class loader");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 452 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte);
/*     */     
/*     */     try {
/* 455 */       objectInputStreamWithLoader = new ObjectInputStreamWithLoader(byteArrayInputStream, clazz.getClassLoader());
/* 456 */     } catch (IOException iOException) {
/* 457 */       throw new OperationsException("An IOException occurred trying to de-serialize the data");
/*     */     } 
/*     */ 
/*     */     
/* 461 */     return objectInputStreamWithLoader;
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
/*     */   public Object instantiate(String paramString) throws ReflectionException, MBeanException {
/* 491 */     return instantiate(paramString, (Object[])null, (String[])null, (ClassLoader)null);
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
/*     */   public Object instantiate(String paramString, ObjectName paramObjectName, ClassLoader paramClassLoader) throws ReflectionException, MBeanException, InstanceNotFoundException {
/* 527 */     return instantiate(paramString, paramObjectName, (Object[])null, (String[])null, paramClassLoader);
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
/*     */   public Object instantiate(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString, ClassLoader paramClassLoader) throws ReflectionException, MBeanException {
/* 565 */     Class<?> clazz = findClassWithDefaultLoaderRepository(paramString);
/* 566 */     return instantiate(clazz, paramArrayOfObject, paramArrayOfString, paramClassLoader);
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
/*     */   public Object instantiate(String paramString, ObjectName paramObjectName, Object[] paramArrayOfObject, String[] paramArrayOfString, ClassLoader paramClassLoader) throws ReflectionException, MBeanException, InstanceNotFoundException {
/*     */     Class<?> clazz;
/* 615 */     if (paramObjectName == null) {
/* 616 */       clazz = findClass(paramString, paramClassLoader);
/*     */     } else {
/* 618 */       clazz = findClass(paramString, paramObjectName);
/*     */     } 
/* 620 */     return instantiate(clazz, paramArrayOfObject, paramArrayOfString, paramClassLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModifiableClassLoaderRepository getClassLoaderRepository() {
/* 628 */     checkMBeanPermission((String)null, (String)null, (ObjectName)null, "getClassLoaderRepository");
/* 629 */     return this.clr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class<?> loadClass(String paramString, ClassLoader paramClassLoader) throws ReflectionException {
/*     */     Class<?> clazz;
/* 639 */     if (paramString == null) {
/* 640 */       throw new RuntimeOperationsException(new IllegalArgumentException("The class name cannot be null"), "Exception occurred during object instantiation");
/*     */     }
/*     */ 
/*     */     
/* 644 */     ReflectUtil.checkPackageAccess(paramString);
/*     */     try {
/* 646 */       if (paramClassLoader == null)
/* 647 */         paramClassLoader = MBeanInstantiator.class.getClassLoader(); 
/* 648 */       if (paramClassLoader != null) {
/* 649 */         clazz = Class.forName(paramString, false, paramClassLoader);
/*     */       } else {
/* 651 */         clazz = Class.forName(paramString);
/*     */       } 
/* 653 */     } catch (ClassNotFoundException classNotFoundException) {
/* 654 */       throw new ReflectionException(classNotFoundException, "The MBean class could not be loaded");
/*     */     } 
/*     */     
/* 657 */     return clazz;
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
/*     */   static Class<?>[] loadSignatureClasses(String[] paramArrayOfString, ClassLoader paramClassLoader) throws ReflectionException {
/* 670 */     if (paramArrayOfString == null) return null;
/*     */     
/* 672 */     ClassLoader classLoader = (paramClassLoader == null) ? MBeanInstantiator.class.getClassLoader() : paramClassLoader;
/* 673 */     int i = paramArrayOfString.length;
/* 674 */     Class[] arrayOfClass = new Class[i];
/*     */     
/* 676 */     if (i == 0) return arrayOfClass; 
/*     */     try {
/* 678 */       for (byte b = 0; b < i; b++) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 683 */         Class clazz = primitiveClasses.get(paramArrayOfString[b]);
/* 684 */         if (clazz != null)
/* 685 */         { arrayOfClass[b] = clazz;
/*     */ 
/*     */           
/*     */            }
/*     */         
/*     */         else
/*     */         
/*     */         { 
/*     */ 
/*     */           
/* 695 */           ReflectUtil.checkPackageAccess(paramArrayOfString[b]);
/* 696 */           arrayOfClass[b] = Class.forName(paramArrayOfString[b], false, classLoader); } 
/*     */       } 
/* 698 */     } catch (ClassNotFoundException classNotFoundException) {
/* 699 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINEST)) {
/* 700 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINEST, MBeanInstantiator.class
/* 701 */             .getName(), "findSignatureClasses", "The parameter class could not be found", classNotFoundException);
/*     */       }
/*     */ 
/*     */       
/* 705 */       throw new ReflectionException(classNotFoundException, "The parameter class could not be found");
/*     */     }
/* 707 */     catch (RuntimeException runtimeException) {
/* 708 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINEST)) {
/* 709 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINEST, MBeanInstantiator.class
/* 710 */             .getName(), "findSignatureClasses", "Unexpected exception", runtimeException);
/*     */       }
/*     */ 
/*     */       
/* 714 */       throw runtimeException;
/*     */     } 
/* 716 */     return arrayOfClass;
/*     */   }
/*     */   
/*     */   private Constructor<?> findConstructor(Class<?> paramClass, Class<?>[] paramArrayOfClass) {
/*     */     try {
/* 721 */       return ConstructorUtil.getConstructor(paramClass, paramArrayOfClass);
/* 722 */     } catch (Exception exception) {
/* 723 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 728 */   private static final Map<String, Class<?>> primitiveClasses = Util.newMap();
/*     */   static {
/* 730 */     for (Class<?> clazz : new Class[] { byte.class, short.class, int.class, long.class, float.class, double.class, char.class, boolean.class
/*     */       })
/*     */     {
/* 733 */       primitiveClasses.put(clazz.getName(), clazz);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkMBeanPermission(Class<?> paramClass, String paramString1, ObjectName paramObjectName, String paramString2) {
/* 740 */     if (paramClass != null) {
/* 741 */       checkMBeanPermission(paramClass.getName(), paramString1, paramObjectName, paramString2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkMBeanPermission(String paramString1, String paramString2, ObjectName paramObjectName, String paramString3) throws SecurityException {
/* 750 */     SecurityManager securityManager = System.getSecurityManager();
/* 751 */     if (securityManager != null) {
/* 752 */       MBeanPermission mBeanPermission = new MBeanPermission(paramString1, paramString2, paramObjectName, paramString3);
/*     */ 
/*     */ 
/*     */       
/* 756 */       securityManager.checkPermission(mBeanPermission);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void ensureClassAccess(Class paramClass) throws IllegalAccessException {
/* 763 */     int i = paramClass.getModifiers();
/* 764 */     if (!Modifier.isPublic(i)) {
/* 765 */       throw new IllegalAccessException("Class is not public and can't be instantiated");
/*     */     }
/*     */   }
/*     */   
/*     */   private ClassLoader getClassLoader(final ObjectName name) {
/* 770 */     if (this.clr == null) {
/* 771 */       return null;
/*     */     }
/*     */     
/* 774 */     Permissions permissions = new Permissions();
/* 775 */     permissions.add(new MBeanPermission("*", null, name, "getClassLoader"));
/* 776 */     ProtectionDomain protectionDomain = new ProtectionDomain(null, permissions);
/* 777 */     ProtectionDomain[] arrayOfProtectionDomain = { protectionDomain };
/* 778 */     AccessControlContext accessControlContext = new AccessControlContext(arrayOfProtectionDomain);
/* 779 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public ClassLoader run() {
/* 781 */             return MBeanInstantiator.this.clr.getClassLoader(name);
/*     */           }
/*     */         },  accessControlContext);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/MBeanInstantiator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */