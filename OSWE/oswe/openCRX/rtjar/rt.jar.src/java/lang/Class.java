/*      */ package java.lang;
/*      */ 
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.lang.reflect.AnnotatedElement;
/*      */ import java.lang.reflect.AnnotatedType;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Executable;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.GenericArrayType;
/*      */ import java.lang.reflect.GenericDeclaration;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.lang.reflect.Type;
/*      */ import java.lang.reflect.TypeVariable;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permissions;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.misc.VM;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.ConstantPool;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.ReflectionFactory;
/*      */ import sun.reflect.annotation.AnnotationParser;
/*      */ import sun.reflect.annotation.AnnotationSupport;
/*      */ import sun.reflect.annotation.AnnotationType;
/*      */ import sun.reflect.annotation.TypeAnnotationParser;
/*      */ import sun.reflect.generics.factory.CoreReflectionFactory;
/*      */ import sun.reflect.generics.factory.GenericsFactory;
/*      */ import sun.reflect.generics.repository.ClassRepository;
/*      */ import sun.reflect.generics.repository.ConstructorRepository;
/*      */ import sun.reflect.generics.repository.MethodRepository;
/*      */ import sun.reflect.generics.scope.ClassScope;
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
/*      */ public final class Class<T>
/*      */   implements Serializable, GenericDeclaration, Type, AnnotatedElement
/*      */ {
/*      */   private static final int ANNOTATION = 8192;
/*      */   private static final int ENUM = 16384;
/*      */   private static final int SYNTHETIC = 4096;
/*      */   private volatile transient Constructor<T> cachedConstructor;
/*      */   private volatile transient Class<?> newInstanceCallerCache;
/*      */   private transient String name;
/*      */   private final ClassLoader classLoader;
/*      */   private static ProtectionDomain allPermDomain;
/*      */   
/*      */   static {
/*  129 */     registerNatives();
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
/*      */   public String toString() {
/*      */     return (isInterface() ? "interface " : (isPrimitive() ? "" : "class ")) + getName();
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
/*      */   public String toGenericString() {
/*      */     if (isPrimitive()) {
/*      */       return toString();
/*      */     }
/*      */     StringBuilder stringBuilder = new StringBuilder();
/*      */     int i = getModifiers() & Modifier.classModifiers();
/*      */     if (i != 0) {
/*      */       stringBuilder.append(Modifier.toString(i));
/*      */       stringBuilder.append(' ');
/*      */     } 
/*      */     if (isAnnotation()) {
/*      */       stringBuilder.append('@');
/*      */     }
/*      */     if (isInterface()) {
/*      */       stringBuilder.append("interface");
/*      */     } else if (isEnum()) {
/*      */       stringBuilder.append("enum");
/*      */     } else {
/*      */       stringBuilder.append("class");
/*      */     } 
/*      */     stringBuilder.append(' ');
/*      */     stringBuilder.append(getName());
/*      */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])getTypeParameters();
/*      */     if (arrayOfTypeVariable.length > 0) {
/*      */       boolean bool = true;
/*      */       stringBuilder.append('<');
/*      */       for (TypeVariable typeVariable : arrayOfTypeVariable) {
/*      */         if (!bool) {
/*      */           stringBuilder.append(',');
/*      */         }
/*      */         stringBuilder.append(typeVariable.getTypeName());
/*      */         bool = false;
/*      */       } 
/*      */       stringBuilder.append('>');
/*      */     } 
/*      */     return stringBuilder.toString();
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
/*      */   @CallerSensitive
/*      */   public static Class<?> forName(String paramString) throws ClassNotFoundException {
/*      */     Class<?> clazz = Reflection.getCallerClass();
/*      */     return forName0(paramString, true, ClassLoader.getClassLoader(clazz), clazz);
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
/*      */   @CallerSensitive
/*      */   public static Class<?> forName(String paramString, boolean paramBoolean, ClassLoader paramClassLoader) throws ClassNotFoundException {
/*      */     Class<?> clazz = null;
/*      */     SecurityManager securityManager = System.getSecurityManager();
/*      */     if (securityManager != null) {
/*      */       clazz = Reflection.getCallerClass();
/*      */       if (VM.isSystemDomainLoader(paramClassLoader)) {
/*      */         ClassLoader classLoader = ClassLoader.getClassLoader(clazz);
/*      */         if (!VM.isSystemDomainLoader(classLoader)) {
/*      */           securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     return forName0(paramString, paramBoolean, paramClassLoader, clazz);
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
/*      */   @CallerSensitive
/*      */   public T newInstance() throws InstantiationException, IllegalAccessException {
/*      */     if (System.getSecurityManager() != null) {
/*      */       checkMemberAccess(0, Reflection.getCallerClass(), false);
/*      */     }
/*      */     if (this.cachedConstructor == null) {
/*      */       if (this == Class.class) {
/*      */         throw new IllegalAccessException("Can not call newInstance() on the Class for java.lang.Class");
/*      */       }
/*      */       try {
/*      */         Class[] arrayOfClass = new Class[0];
/*      */         final Constructor<T> c = getConstructor0(arrayOfClass, 1);
/*      */         AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */             {
/*      */               public Void run() {
/*      */                 c.setAccessible(true);
/*      */                 return null;
/*      */               }
/*      */             });
/*      */         this.cachedConstructor = constructor1;
/*      */       } catch (NoSuchMethodException noSuchMethodException) {
/*      */         throw (InstantiationException)(new InstantiationException(getName())).initCause(noSuchMethodException);
/*      */       } 
/*      */     } 
/*      */     Constructor<T> constructor = this.cachedConstructor;
/*      */     int i = constructor.getModifiers();
/*      */     if (!Reflection.quickCheckMemberAccess(this, i)) {
/*      */       Class<?> clazz = Reflection.getCallerClass();
/*      */       if (this.newInstanceCallerCache != clazz) {
/*      */         Reflection.ensureMemberAccess(clazz, this, null, i);
/*      */         this.newInstanceCallerCache = clazz;
/*      */       } 
/*      */     } 
/*      */     try {
/*      */       return constructor.newInstance((Object[])null);
/*      */     } catch (InvocationTargetException invocationTargetException) {
/*      */       Unsafe.getUnsafe().throwException(invocationTargetException.getTargetException());
/*      */       return null;
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
/*      */   public boolean isAnnotation() {
/*      */     return ((getModifiers() & 0x2000) != 0);
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
/*      */   public boolean isSynthetic() {
/*      */     return ((getModifiers() & 0x1000) != 0);
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
/*      */   public String getName() {
/*      */     String str = this.name;
/*      */     if (str == null) {
/*      */       this.name = str = getName0();
/*      */     }
/*      */     return str;
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
/*      */   @CallerSensitive
/*      */   public ClassLoader getClassLoader() {
/*      */     ClassLoader classLoader = getClassLoader0();
/*      */     if (classLoader == null) {
/*      */       return null;
/*      */     }
/*      */     SecurityManager securityManager = System.getSecurityManager();
/*      */     if (securityManager != null) {
/*      */       ClassLoader.checkClassLoaderPermission(classLoader, Reflection.getCallerClass());
/*      */     }
/*      */     return classLoader;
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
/*      */   ClassLoader getClassLoader0() {
/*      */     return this.classLoader;
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
/*      */   public TypeVariable<Class<T>>[] getTypeParameters() {
/*      */     ClassRepository classRepository = getGenericInfo();
/*      */     if (classRepository != null) {
/*      */       return (TypeVariable<Class<T>>[])classRepository.getTypeParameters();
/*      */     }
/*      */     return (TypeVariable<Class<T>>[])new TypeVariable[0];
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
/*      */   public Type getGenericSuperclass() {
/*      */     ClassRepository classRepository = getGenericInfo();
/*      */     if (classRepository == null) {
/*      */       return getSuperclass();
/*      */     }
/*      */     if (isInterface()) {
/*      */       return null;
/*      */     }
/*      */     return classRepository.getSuperclass();
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
/*      */   public Package getPackage() {
/*      */     return Package.getPackage(this);
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
/*      */   public Class<?>[] getInterfaces() {
/*      */     ReflectionData<T> reflectionData = reflectionData();
/*      */     if (reflectionData == null) {
/*      */       return getInterfaces0();
/*      */     }
/*      */     Class<?>[] arrayOfClass = reflectionData.interfaces;
/*      */     if (arrayOfClass == null) {
/*      */       arrayOfClass = getInterfaces0();
/*      */       reflectionData.interfaces = arrayOfClass;
/*      */     } 
/*      */     return (Class[])arrayOfClass.clone();
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
/*      */   public Type[] getGenericInterfaces() {
/*      */     ClassRepository classRepository = getGenericInfo();
/*      */     return (classRepository == null) ? (Type[])getInterfaces() : classRepository.getSuperInterfaces();
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
/*      */   @CallerSensitive
/*      */   public Method getEnclosingMethod() throws SecurityException {
/*      */     EnclosingMethodInfo enclosingMethodInfo = getEnclosingMethodInfo();
/*      */     if (enclosingMethodInfo == null) {
/*      */       return null;
/*      */     }
/*      */     if (!enclosingMethodInfo.isMethod()) {
/*      */       return null;
/*      */     }
/*      */     MethodRepository methodRepository = MethodRepository.make(enclosingMethodInfo.getDescriptor(), getFactory());
/*      */     Class<?> clazz1 = toClass(methodRepository.getReturnType());
/*      */     Type[] arrayOfType = methodRepository.getParameterTypes();
/*      */     Class[] arrayOfClass = new Class[arrayOfType.length];
/*      */     for (byte b = 0; b < arrayOfClass.length; b++) {
/*      */       arrayOfClass[b] = toClass(arrayOfType[b]);
/*      */     }
/*      */     Class<?> clazz2 = enclosingMethodInfo.getEnclosingClass();
/*      */     clazz2.checkMemberAccess(1, Reflection.getCallerClass(), true);
/*      */     for (Method method : clazz2.getDeclaredMethods()) {
/*      */       if (method.getName().equals(enclosingMethodInfo.getName())) {
/*      */         Class[] arrayOfClass1 = method.getParameterTypes();
/*      */         if (arrayOfClass1.length == arrayOfClass.length) {
/*      */           boolean bool = true;
/*      */           for (byte b1 = 0; b1 < arrayOfClass1.length; b1++) {
/*      */             if (!arrayOfClass1[b1].equals(arrayOfClass[b1])) {
/*      */               bool = false;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */           if (bool && method.getReturnType().equals(clazz1)) {
/*      */             return method;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     throw new InternalError("Enclosing method not found");
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
/*      */   private EnclosingMethodInfo getEnclosingMethodInfo() {
/*      */     Object[] arrayOfObject = getEnclosingMethod0();
/*      */     if (arrayOfObject == null) {
/*      */       return null;
/*      */     }
/*      */     return new EnclosingMethodInfo(arrayOfObject);
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
/*      */   private static final class EnclosingMethodInfo
/*      */   {
/*      */     private Class<?> enclosingClass;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String name;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String descriptor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private EnclosingMethodInfo(Object[] param1ArrayOfObject) {
/*      */       if (param1ArrayOfObject.length != 3) {
/*      */         throw new InternalError("Malformed enclosing method information");
/*      */       }
/*      */       try {
/*      */         this.enclosingClass = (Class)param1ArrayOfObject[0];
/*      */         assert this.enclosingClass != null;
/*      */         this.name = (String)param1ArrayOfObject[1];
/*      */         this.descriptor = (String)param1ArrayOfObject[2];
/*      */         assert this.name == this.descriptor;
/*      */       } catch (ClassCastException classCastException) {
/*      */         throw new InternalError("Invalid type in enclosing method information", classCastException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isPartial() {
/*      */       return (this.enclosingClass == null || this.name == null || this.descriptor == null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isConstructor() {
/*      */       return (!isPartial() && "<init>".equals(this.name));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isMethod() {
/*      */       return (!isPartial() && !isConstructor() && !"<clinit>".equals(this.name));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Class<?> getEnclosingClass() {
/*      */       return this.enclosingClass;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String getName() {
/*      */       return this.name;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String getDescriptor() {
/*      */       return this.descriptor;
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
/*      */   private static Class<?> toClass(Type paramType) {
/*      */     if (paramType instanceof GenericArrayType) {
/*      */       return Array.newInstance(toClass(((GenericArrayType)paramType).getGenericComponentType()), 0).getClass();
/*      */     }
/*      */     return (Class)paramType;
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
/*      */   @CallerSensitive
/*      */   public Constructor<?> getEnclosingConstructor() throws SecurityException {
/*      */     EnclosingMethodInfo enclosingMethodInfo = getEnclosingMethodInfo();
/*      */     if (enclosingMethodInfo == null) {
/*      */       return null;
/*      */     }
/*      */     if (!enclosingMethodInfo.isConstructor()) {
/*      */       return null;
/*      */     }
/*      */     ConstructorRepository constructorRepository = ConstructorRepository.make(enclosingMethodInfo.getDescriptor(), getFactory());
/*      */     Type[] arrayOfType = constructorRepository.getParameterTypes();
/*      */     Class[] arrayOfClass = new Class[arrayOfType.length];
/*      */     for (byte b = 0; b < arrayOfClass.length; b++) {
/*      */       arrayOfClass[b] = toClass(arrayOfType[b]);
/*      */     }
/*      */     Class<?> clazz = enclosingMethodInfo.getEnclosingClass();
/*      */     clazz.checkMemberAccess(1, Reflection.getCallerClass(), true);
/*      */     for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
/*      */       Class[] arrayOfClass1 = constructor.getParameterTypes();
/*      */       if (arrayOfClass1.length == arrayOfClass.length) {
/*      */         boolean bool = true;
/*      */         for (byte b1 = 0; b1 < arrayOfClass1.length; b1++) {
/*      */           if (!arrayOfClass1[b1].equals(arrayOfClass[b1])) {
/*      */             bool = false;
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         if (bool) {
/*      */           return constructor;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     throw new InternalError("Enclosing constructor not found");
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
/*      */   @CallerSensitive
/*      */   public Class<?> getDeclaringClass() throws SecurityException {
/*      */     Class<?> clazz = getDeclaringClass0();
/*      */     if (clazz != null) {
/*      */       clazz.checkPackageAccess(ClassLoader.getClassLoader(Reflection.getCallerClass()), true);
/*      */     }
/*      */     return clazz;
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
/*      */   @CallerSensitive
/*      */   public Class<?> getEnclosingClass() throws SecurityException {
/*      */     Class<?> clazz;
/*      */     EnclosingMethodInfo enclosingMethodInfo = getEnclosingMethodInfo();
/*      */     if (enclosingMethodInfo == null) {
/*      */       clazz = getDeclaringClass();
/*      */     } else {
/*      */       Class<?> clazz1 = enclosingMethodInfo.getEnclosingClass();
/*      */       if (clazz1 == this || clazz1 == null) {
/*      */         throw new InternalError("Malformed enclosing method information");
/*      */       }
/*      */       clazz = clazz1;
/*      */     } 
/*      */     if (clazz != null) {
/*      */       clazz.checkPackageAccess(ClassLoader.getClassLoader(Reflection.getCallerClass()), true);
/*      */     }
/*      */     return clazz;
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
/*      */   public String getSimpleName() {
/*      */     if (isArray()) {
/*      */       return getComponentType().getSimpleName() + "[]";
/*      */     }
/*      */     String str = getSimpleBinaryName();
/*      */     if (str == null) {
/*      */       str = getName();
/*      */       return str.substring(str.lastIndexOf(".") + 1);
/*      */     } 
/*      */     int i = str.length();
/*      */     if (i < 1 || str.charAt(0) != '$') {
/*      */       throw new InternalError("Malformed class name");
/*      */     }
/*      */     byte b = 1;
/*      */     while (b < i && isAsciiDigit(str.charAt(b))) {
/*      */       b++;
/*      */     }
/*      */     return str.substring(b);
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
/*      */   public String getTypeName() {
/*      */     if (isArray()) {
/*      */       try {
/*      */         Class<?> clazz = this;
/*      */         byte b1 = 0;
/*      */         while (clazz.isArray()) {
/*      */           b1++;
/*      */           clazz = clazz.getComponentType();
/*      */         } 
/*      */         StringBuilder stringBuilder = new StringBuilder();
/*      */         stringBuilder.append(clazz.getName());
/*      */         for (byte b2 = 0; b2 < b1; b2++) {
/*      */           stringBuilder.append("[]");
/*      */         }
/*      */         return stringBuilder.toString();
/*      */       } catch (Throwable throwable) {}
/*      */     }
/*      */     return getName();
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
/*      */   private Class(ClassLoader paramClassLoader) {
/* 2488 */     this.classRedefinedCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3330 */     this.enumConstants = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3352 */     this.enumConstantDirectory = null; this.classLoader = paramClassLoader;
/*      */   } private static boolean isAsciiDigit(char paramChar) { return ('0' <= paramChar && paramChar <= '9'); } public String getCanonicalName() { if (isArray()) { String str1 = getComponentType().getCanonicalName(); if (str1 != null) return str1 + "[]";  return null; }  if (isLocalOrAnonymousClass()) return null;  Class<?> clazz = getEnclosingClass(); if (clazz == null) return getName();  String str = clazz.getCanonicalName(); if (str == null) return null;  return str + "." + getSimpleName(); } public boolean isAnonymousClass() { return "".equals(getSimpleName()); } public boolean isLocalClass() { return (isLocalOrAnonymousClass() && !isAnonymousClass()); } public boolean isMemberClass() { return (getSimpleBinaryName() != null && !isLocalOrAnonymousClass()); } private String getSimpleBinaryName() { Class<?> clazz = getEnclosingClass(); if (clazz == null) return null;  try { return getName().substring(clazz.getName().length()); } catch (IndexOutOfBoundsException indexOutOfBoundsException) { throw new InternalError("Malformed class name", indexOutOfBoundsException); }  } private boolean isLocalOrAnonymousClass() { return (getEnclosingMethodInfo() != null); } @CallerSensitive public Class<?>[] getClasses() { checkMemberAccess(0, Reflection.getCallerClass(), false); return AccessController.<Class<?>[]>doPrivileged((PrivilegedAction)new PrivilegedAction<Class<?>[]>() { public Class<?>[] run() { ArrayList<Class<?>> arrayList = new ArrayList(); Class clazz = Class.this; while (clazz != null) { Class[] arrayOfClass = clazz.getDeclaredClasses(); for (byte b = 0; b < arrayOfClass.length; b++) { if (Modifier.isPublic(arrayOfClass[b].getModifiers())) arrayList.add(arrayOfClass[b]);  }  clazz = clazz.getSuperclass(); }  return (Class[])arrayList.<Class<?>[]>toArray((Class<?>[][])new Class[0]); } }
/*      */       ); } @CallerSensitive public Field[] getFields() throws SecurityException { checkMemberAccess(0, Reflection.getCallerClass(), true); return copyFields(privateGetPublicFields(null)); } @CallerSensitive public Method[] getMethods() throws SecurityException { checkMemberAccess(0, Reflection.getCallerClass(), true); return copyMethods(privateGetPublicMethods()); } @CallerSensitive public Constructor<?>[] getConstructors() throws SecurityException { checkMemberAccess(0, Reflection.getCallerClass(), true); return (Constructor<?>[])copyConstructors(privateGetDeclaredConstructors(true)); }
/*      */   @CallerSensitive public Field getField(String paramString) throws NoSuchFieldException, SecurityException { checkMemberAccess(0, Reflection.getCallerClass(), true); Field field = getField0(paramString); if (field == null) throw new NoSuchFieldException(paramString);  return field; }
/*      */   @CallerSensitive public Method getMethod(String paramString, Class<?>... paramVarArgs) throws NoSuchMethodException, SecurityException { checkMemberAccess(0, Reflection.getCallerClass(), true); Method method = getMethod0(paramString, paramVarArgs, true); if (method == null) throw new NoSuchMethodException(getName() + "." + paramString + argumentTypesToString(paramVarArgs));  return method; }
/*      */   @CallerSensitive public Constructor<T> getConstructor(Class<?>... paramVarArgs) throws NoSuchMethodException, SecurityException { checkMemberAccess(0, Reflection.getCallerClass(), true); return getConstructor0(paramVarArgs, 0); }
/*      */   @CallerSensitive public Class<?>[] getDeclaredClasses() throws SecurityException { checkMemberAccess(1, Reflection.getCallerClass(), false); return getDeclaredClasses0(); }
/*      */   @CallerSensitive public Field[] getDeclaredFields() throws SecurityException { checkMemberAccess(1, Reflection.getCallerClass(), true); return copyFields(privateGetDeclaredFields(false)); }
/*      */   @CallerSensitive public Method[] getDeclaredMethods() throws SecurityException { checkMemberAccess(1, Reflection.getCallerClass(), true); return copyMethods(privateGetDeclaredMethods(false)); }
/*      */   @CallerSensitive public Constructor<?>[] getDeclaredConstructors() throws SecurityException { checkMemberAccess(1, Reflection.getCallerClass(), true); return (Constructor<?>[])copyConstructors(privateGetDeclaredConstructors(false)); }
/*      */   @CallerSensitive public Field getDeclaredField(String paramString) throws NoSuchFieldException, SecurityException { checkMemberAccess(1, Reflection.getCallerClass(), true); Field field = searchFields(privateGetDeclaredFields(false), paramString); if (field == null) throw new NoSuchFieldException(paramString);  return field; }
/*      */   @CallerSensitive public Method getDeclaredMethod(String paramString, Class<?>... paramVarArgs) throws NoSuchMethodException, SecurityException { checkMemberAccess(1, Reflection.getCallerClass(), true); Method method = searchMethods(privateGetDeclaredMethods(false), paramString, paramVarArgs); if (method == null) throw new NoSuchMethodException(getName() + "." + paramString + argumentTypesToString(paramVarArgs));  return method; }
/*      */   @CallerSensitive public Constructor<T> getDeclaredConstructor(Class<?>... paramVarArgs) throws NoSuchMethodException, SecurityException { checkMemberAccess(1, Reflection.getCallerClass(), true); return getConstructor0(paramVarArgs, 1); }
/*      */   public InputStream getResourceAsStream(String paramString) { paramString = resolveName(paramString); ClassLoader classLoader = getClassLoader0(); if (classLoader == null) return ClassLoader.getSystemResourceAsStream(paramString);  return classLoader.getResourceAsStream(paramString); }
/*      */   public URL getResource(String paramString) { paramString = resolveName(paramString); ClassLoader classLoader = getClassLoader0(); if (classLoader == null) return ClassLoader.getSystemResource(paramString);  return classLoader.getResource(paramString); }
/*      */   public ProtectionDomain getProtectionDomain() { SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkPermission(SecurityConstants.GET_PD_PERMISSION);  ProtectionDomain protectionDomain = getProtectionDomain0(); if (protectionDomain == null) { if (allPermDomain == null) { Permissions permissions = new Permissions(); permissions.add(SecurityConstants.ALL_PERMISSION); allPermDomain = new ProtectionDomain(null, permissions); }  protectionDomain = allPermDomain; }  return protectionDomain; }
/* 3368 */   public T cast(Object paramObject) { if (paramObject != null && !isInstance(paramObject))
/* 3369 */       throw new ClassCastException(cannotCastMsg(paramObject)); 
/* 3370 */     return (T)paramObject; }
/*      */   private void checkMemberAccess(int paramInt, Class<?> paramClass, boolean paramBoolean) { SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) { ClassLoader classLoader1 = ClassLoader.getClassLoader(paramClass); ClassLoader classLoader2 = getClassLoader0(); if (paramInt != 0 && classLoader1 != classLoader2) securityManager.checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);  checkPackageAccess(classLoader1, paramBoolean); }  }
/*      */   private void checkPackageAccess(ClassLoader paramClassLoader, boolean paramBoolean) { SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) { ClassLoader classLoader = getClassLoader0(); if (ReflectUtil.needsPackageAccessCheck(paramClassLoader, classLoader)) { String str = getName(); int i = str.lastIndexOf('.'); if (i != -1) { String str1 = str.substring(0, i); if (!Proxy.isProxyClass(this) || ReflectUtil.isNonPublicProxyClass(this)) securityManager.checkPackageAccess(str1);  }  }  if (paramBoolean && Proxy.isProxyClass(this)) ReflectUtil.checkProxyPackageAccess(paramClassLoader, getInterfaces());  }  } private String resolveName(String paramString) { if (paramString == null) return paramString;  if (!paramString.startsWith("/")) { Class<?> clazz = this; while (clazz.isArray()) clazz = clazz.getComponentType();  String str = clazz.getName(); int i = str.lastIndexOf('.'); if (i != -1) paramString = str.substring(0, i).replace('.', '/') + "/" + paramString;  } else { paramString = paramString.substring(1); }  return paramString; } private static class Atomic {
/*      */     private static final Unsafe unsafe = Unsafe.getUnsafe(); private static final long reflectionDataOffset; private static final long annotationTypeOffset; private static final long annotationDataOffset; static { Field[] arrayOfField = Class.class.getDeclaredFields0(false); reflectionDataOffset = objectFieldOffset(arrayOfField, "reflectionData"); annotationTypeOffset = objectFieldOffset(arrayOfField, "annotationType"); annotationDataOffset = objectFieldOffset(arrayOfField, "annotationData"); } private static long objectFieldOffset(Field[] param1ArrayOfField, String param1String) { Field field = Class.searchFields(param1ArrayOfField, param1String); if (field == null) throw new Error("No " + param1String + " field found in java.lang.Class");  return unsafe.objectFieldOffset(field); } static <T> boolean casReflectionData(Class<?> param1Class, SoftReference<Class.ReflectionData<T>> param1SoftReference1, SoftReference<Class.ReflectionData<T>> param1SoftReference2) { return unsafe.compareAndSwapObject(param1Class, reflectionDataOffset, param1SoftReference1, param1SoftReference2); } static <T> boolean casAnnotationType(Class<?> param1Class, AnnotationType param1AnnotationType1, AnnotationType param1AnnotationType2) { return unsafe.compareAndSwapObject(param1Class, annotationTypeOffset, param1AnnotationType1, param1AnnotationType2); } static <T> boolean casAnnotationData(Class<?> param1Class, Class.AnnotationData param1AnnotationData1, Class.AnnotationData param1AnnotationData2) { return unsafe.compareAndSwapObject(param1Class, annotationDataOffset, param1AnnotationData1, param1AnnotationData2); } } private static boolean useCaches = true; private volatile transient SoftReference<ReflectionData<T>> reflectionData; private volatile transient int classRedefinedCount; private volatile transient ClassRepository genericInfo; private static final long serialVersionUID = 3206093459760846163L; private static class ReflectionData<T> {
/* 3374 */     volatile Field[] declaredFields; volatile Field[] publicFields; volatile Method[] declaredMethods; volatile Method[] publicMethods; volatile Constructor<T>[] declaredConstructors; volatile Constructor<T>[] publicConstructors; volatile Field[] declaredPublicFields; volatile Method[] declaredPublicMethods; volatile Class<?>[] interfaces; final int redefinedCount; ReflectionData(int param1Int) { this.redefinedCount = param1Int; } } private ReflectionData<T> reflectionData() { SoftReference<ReflectionData<T>> softReference = this.reflectionData; int i = this.classRedefinedCount; ReflectionData<T> reflectionData; if (useCaches && softReference != null && (reflectionData = (ReflectionData)softReference.get()) != null && reflectionData.redefinedCount == i) return reflectionData;  return newReflectionData(softReference, i); } private ReflectionData<T> newReflectionData(SoftReference<ReflectionData<T>> paramSoftReference, int paramInt) { if (!useCaches) return null;  ReflectionData<T> reflectionData; do { ReflectionData<T> reflectionData1 = new ReflectionData(paramInt); if (Atomic.casReflectionData(this, paramSoftReference, new SoftReference<>(reflectionData1))) return reflectionData1;  paramSoftReference = this.reflectionData; paramInt = this.classRedefinedCount; } while (paramSoftReference == null || (reflectionData = (ReflectionData)paramSoftReference.get()) == null || reflectionData.redefinedCount != paramInt); return reflectionData; } private String cannotCastMsg(Object paramObject) { return "Cannot cast " + paramObject.getClass().getName() + " to " + getName(); }
/*      */   private GenericsFactory getFactory() { return CoreReflectionFactory.make(this, ClassScope.make(this)); }
/*      */   private ClassRepository getGenericInfo() { ClassRepository classRepository = this.genericInfo; if (classRepository == null) { String str = getGenericSignature0(); if (str == null) { classRepository = ClassRepository.NONE; } else { classRepository = ClassRepository.make(str, getFactory()); }  this.genericInfo = classRepository; }  return (classRepository != ClassRepository.NONE) ? classRepository : null; }
/*      */   static byte[] getExecutableTypeAnnotationBytes(Executable paramExecutable) { return getReflectionFactory().getExecutableTypeAnnotationBytes(paramExecutable); }
/*      */   private Field[] privateGetDeclaredFields(boolean paramBoolean) { checkInitted(); ReflectionData<T> reflectionData = reflectionData(); if (reflectionData != null) { Field[] arrayOfField1 = paramBoolean ? reflectionData.declaredPublicFields : reflectionData.declaredFields; if (arrayOfField1 != null) return arrayOfField1;  }  Field[] arrayOfField = Reflection.filterFields(this, getDeclaredFields0(paramBoolean)); if (reflectionData != null) if (paramBoolean) { reflectionData.declaredPublicFields = arrayOfField; } else { reflectionData.declaredFields = arrayOfField; }   return arrayOfField; }
/*      */   private Field[] privateGetPublicFields(Set<Class<?>> paramSet) { checkInitted(); ReflectionData<T> reflectionData = reflectionData(); if (reflectionData != null) { Field[] arrayOfField = reflectionData.publicFields; if (arrayOfField != null) return arrayOfField;  }  ArrayList<Field> arrayList = new ArrayList(); if (paramSet == null) paramSet = new HashSet<>();  Field[] arrayOfField2 = privateGetDeclaredFields(true); addAll(arrayList, arrayOfField2); for (Class<?> clazz : getInterfaces()) { if (!paramSet.contains(clazz)) { paramSet.add(clazz); addAll(arrayList, clazz.privateGetPublicFields(paramSet)); }  }  if (!isInterface()) { Class<? super T> clazz = getSuperclass(); if (clazz != null) addAll(arrayList, clazz.privateGetPublicFields(paramSet));  }  Field[] arrayOfField1 = new Field[arrayList.size()]; arrayList.toArray(arrayOfField1); if (reflectionData != null) reflectionData.publicFields = arrayOfField1;  return arrayOfField1; }
/*      */   private static void addAll(Collection<Field> paramCollection, Field[] paramArrayOfField) { for (byte b = 0; b < paramArrayOfField.length; b++) paramCollection.add(paramArrayOfField[b]);  }
/*      */   private Constructor<T>[] privateGetDeclaredConstructors(boolean paramBoolean) { Constructor[] arrayOfConstructor; checkInitted(); ReflectionData<T> reflectionData = reflectionData(); if (reflectionData != null) { Constructor<T>[] arrayOfConstructor1 = paramBoolean ? reflectionData.publicConstructors : reflectionData.declaredConstructors; if (arrayOfConstructor1 != null) return arrayOfConstructor1;  }  if (isInterface()) { Constructor[] arrayOfConstructor1 = new Constructor[0]; arrayOfConstructor = arrayOfConstructor1; } else { arrayOfConstructor = (Constructor[])getDeclaredConstructors0(paramBoolean); }  if (reflectionData != null) if (paramBoolean) { reflectionData.publicConstructors = (Constructor<T>[])arrayOfConstructor; } else { reflectionData.declaredConstructors = (Constructor<T>[])arrayOfConstructor; }   return (Constructor<T>[])arrayOfConstructor; }
/*      */   private Method[] privateGetDeclaredMethods(boolean paramBoolean) { checkInitted(); ReflectionData<T> reflectionData = reflectionData(); if (reflectionData != null) { Method[] arrayOfMethod1 = paramBoolean ? reflectionData.declaredPublicMethods : reflectionData.declaredMethods; if (arrayOfMethod1 != null) return arrayOfMethod1;  }  Method[] arrayOfMethod = Reflection.filterMethods(this, getDeclaredMethods0(paramBoolean)); if (reflectionData != null) if (paramBoolean) { reflectionData.declaredPublicMethods = arrayOfMethod; } else { reflectionData.declaredMethods = arrayOfMethod; }   return arrayOfMethod; }
/*      */   static class MethodArray {
/*      */     private Method[] methods;
/*      */     private int length;
/*      */     private int defaults;
/*      */     MethodArray() { this(20); }
/*      */     MethodArray(int param1Int) { if (param1Int < 2) throw new IllegalArgumentException("Size should be 2 or more");  this.methods = new Method[param1Int]; this.length = 0; this.defaults = 0; }
/*      */     boolean hasDefaults() { return (this.defaults != 0); }
/*      */     void add(Method param1Method) { if (this.length == this.methods.length) this.methods = Arrays.<Method>copyOf(this.methods, 2 * this.methods.length);  this.methods[this.length++] = param1Method; if (param1Method != null && param1Method.isDefault()) this.defaults++;  }
/*      */     void addAll(Method[] param1ArrayOfMethod) { for (byte b = 0; b < param1ArrayOfMethod.length; b++) add(param1ArrayOfMethod[b]);  }
/*      */     void addAll(MethodArray param1MethodArray) { for (byte b = 0; b < param1MethodArray.length(); b++) add(param1MethodArray.get(b));  }
/*      */     void addIfNotPresent(Method param1Method) { for (byte b = 0; b < this.length; b++) { Method method = this.methods[b]; if (method == param1Method || (method != null && method.equals(param1Method))) return;  }  add(param1Method); }
/*      */     void addAllIfNotPresent(MethodArray param1MethodArray) { for (byte b = 0; b < param1MethodArray.length(); b++) { Method method = param1MethodArray.get(b); if (method != null) addIfNotPresent(method);  }  }
/*      */     void addInterfaceMethods(Method[] param1ArrayOfMethod) { for (Method method : param1ArrayOfMethod) { if (!Modifier.isStatic(method.getModifiers())) add(method);  }  }
/*      */     int length() { return this.length; }
/*      */     Method get(int param1Int) { return this.methods[param1Int]; } Method getFirst() { for (Method method : this.methods) { if (method != null) return method;  }  return null; } void removeByNameAndDescriptor(Method param1Method) { for (byte b = 0; b < this.length; b++) { Method method = this.methods[b]; if (method != null && matchesNameAndDescriptor(method, param1Method)) remove(b);  }  } private void remove(int param1Int) { if (this.methods[param1Int] != null && this.methods[param1Int].isDefault()) this.defaults--;  this.methods[param1Int] = null; } private boolean matchesNameAndDescriptor(Method param1Method1, Method param1Method2) { return (param1Method1.getReturnType() == param1Method2.getReturnType() && param1Method1.getName() == param1Method2.getName() && Class.arrayContentsEq((Object[])param1Method1.getParameterTypes(), (Object[])param1Method2.getParameterTypes())); } void compactAndTrim() { byte b1 = 0; for (byte b2 = 0; b2 < this.length; b2++) { Method method = this.methods[b2]; if (method != null) { if (b2 != b1) this.methods[b1] = method;  b1++; }  }  if (b1 != this.methods.length) this.methods = Arrays.<Method>copyOf(this.methods, b1);  } void removeLessSpecifics() { if (!hasDefaults()) return;  for (byte b = 0; b < this.length; b++) { Method method = get(b); if (method != null && method.isDefault()) for (byte b1 = 0; b1 < this.length; b1++) { if (b != b1) { Method method1 = get(b1); if (method1 != null) if (matchesNameAndDescriptor(method, method1)) if (hasMoreSpecificClass(method, method1)) remove(b1);    }  }   }  } Method[] getArray() { return this.methods; } static boolean hasMoreSpecificClass(Method param1Method1, Method param1Method2) { Class<?> clazz1 = param1Method1.getDeclaringClass(); Class<?> clazz2 = param1Method2.getDeclaringClass(); return (clazz1 != clazz2 && clazz2.isAssignableFrom(clazz1)); }
/*      */   } private Method[] privateGetPublicMethods() { checkInitted(); ReflectionData<T> reflectionData = reflectionData(); if (reflectionData != null) { Method[] arrayOfMethod = reflectionData.publicMethods; if (arrayOfMethod != null) return arrayOfMethod;  }  MethodArray methodArray1 = new MethodArray(); Method[] arrayOfMethod2 = privateGetDeclaredMethods(true); methodArray1.addAll(arrayOfMethod2); MethodArray methodArray2 = new MethodArray(); for (Class<?> clazz : getInterfaces()) methodArray2.addInterfaceMethods(clazz.privateGetPublicMethods());  if (!isInterface()) { Class<? super T> clazz = getSuperclass(); if (clazz != null) { MethodArray methodArray = new MethodArray(); methodArray.addAll(clazz.privateGetPublicMethods()); for (byte b1 = 0; b1 < methodArray.length(); b1++) { Method method = methodArray.get(b1); if (method != null && !Modifier.isAbstract(method.getModifiers()) && !method.isDefault()) methodArray2.removeByNameAndDescriptor(method);  }  methodArray.addAll(methodArray2); methodArray2 = methodArray; }  }  for (byte b = 0; b < methodArray1.length(); b++) { Method method = methodArray1.get(b); methodArray2.removeByNameAndDescriptor(method); }  methodArray1.addAllIfNotPresent(methodArray2); methodArray1.removeLessSpecifics(); methodArray1.compactAndTrim(); Method[] arrayOfMethod1 = methodArray1.getArray(); if (reflectionData != null) reflectionData.publicMethods = arrayOfMethod1;  return arrayOfMethod1; } private static Field searchFields(Field[] paramArrayOfField, String paramString) { String str = paramString.intern(); for (byte b = 0; b < paramArrayOfField.length; b++) { if (paramArrayOfField[b].getName() == str) return getReflectionFactory().copyField(paramArrayOfField[b]);  }  return null; } private Field getField0(String paramString) throws NoSuchFieldException { Field field; if ((field = searchFields(privateGetDeclaredFields(true), paramString)) != null) return field;  Class[] arrayOfClass = getInterfaces(); for (byte b = 0; b < arrayOfClass.length; b++) { Class clazz = arrayOfClass[b]; if ((field = clazz.getField0(paramString)) != null) return field;  }  if (!isInterface()) { Class<? super T> clazz = getSuperclass(); if (clazz != null && (field = clazz.getField0(paramString)) != null) return field;  }  return null; } private static Method searchMethods(Method[] paramArrayOfMethod, String paramString, Class<?>[] paramArrayOfClass) { Method method = null; String str = paramString.intern(); for (byte b = 0; b < paramArrayOfMethod.length; b++) { Method method1 = paramArrayOfMethod[b]; if (method1.getName() == str && arrayContentsEq((Object[])paramArrayOfClass, (Object[])method1.getParameterTypes()) && (method == null || method.getReturnType().isAssignableFrom(method1.getReturnType()))) method = method1;  }  return (method == null) ? method : getReflectionFactory().copyMethod(method); } private Method getMethod0(String paramString, Class<?>[] paramArrayOfClass, boolean paramBoolean) { MethodArray methodArray = new MethodArray(2); Method method = privateGetMethodRecursive(paramString, paramArrayOfClass, paramBoolean, methodArray); if (method != null) return method;  methodArray.removeLessSpecifics(); return methodArray.getFirst(); } private Method privateGetMethodRecursive(String paramString, Class<?>[] paramArrayOfClass, boolean paramBoolean, MethodArray paramMethodArray) { Method method; if ((method = searchMethods(privateGetDeclaredMethods(true), paramString, paramArrayOfClass)) != null) if (paramBoolean || !Modifier.isStatic(method.getModifiers())) return method;   if (!isInterface()) { Class<? super T> clazz = getSuperclass(); if (clazz != null && (method = clazz.getMethod0(paramString, paramArrayOfClass, true)) != null) return method;  }  Class[] arrayOfClass = getInterfaces(); for (Class clazz : arrayOfClass) { if ((method = clazz.getMethod0(paramString, paramArrayOfClass, false)) != null) paramMethodArray.add(method);  }  return null; } private Constructor<T> getConstructor0(Class<?>[] paramArrayOfClass, int paramInt) throws NoSuchMethodException { Constructor[] arrayOfConstructor = (Constructor[])privateGetDeclaredConstructors((paramInt == 0)); for (Constructor constructor : arrayOfConstructor) { if (arrayContentsEq((Object[])paramArrayOfClass, (Object[])constructor.getParameterTypes())) return getReflectionFactory().copyConstructor(constructor);  }  throw new NoSuchMethodException(getName() + ".<init>" + argumentTypesToString(paramArrayOfClass)); } private static boolean arrayContentsEq(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2) { if (paramArrayOfObject1 == null) return (paramArrayOfObject2 == null || paramArrayOfObject2.length == 0);  if (paramArrayOfObject2 == null) return (paramArrayOfObject1.length == 0);  if (paramArrayOfObject1.length != paramArrayOfObject2.length) return false;  for (byte b = 0; b < paramArrayOfObject1.length; b++) { if (paramArrayOfObject1[b] != paramArrayOfObject2[b]) return false;  }  return true; } private static Field[] copyFields(Field[] paramArrayOfField) { Field[] arrayOfField = new Field[paramArrayOfField.length]; ReflectionFactory reflectionFactory = getReflectionFactory(); for (byte b = 0; b < paramArrayOfField.length; b++) arrayOfField[b] = reflectionFactory.copyField(paramArrayOfField[b]);  return arrayOfField; } private static Method[] copyMethods(Method[] paramArrayOfMethod) { Method[] arrayOfMethod = new Method[paramArrayOfMethod.length]; ReflectionFactory reflectionFactory = getReflectionFactory(); for (byte b = 0; b < paramArrayOfMethod.length; b++) arrayOfMethod[b] = reflectionFactory.copyMethod(paramArrayOfMethod[b]);  return arrayOfMethod; } private static <U> Constructor<U>[] copyConstructors(Constructor<U>[] paramArrayOfConstructor) { Constructor[] arrayOfConstructor = (Constructor[])paramArrayOfConstructor.clone(); ReflectionFactory reflectionFactory = getReflectionFactory(); for (byte b = 0; b < arrayOfConstructor.length; b++) arrayOfConstructor[b] = reflectionFactory.copyConstructor(arrayOfConstructor[b]);  return (Constructor<U>[])arrayOfConstructor; } private static String argumentTypesToString(Class<?>[] paramArrayOfClass) { StringBuilder stringBuilder = new StringBuilder(); stringBuilder.append("("); if (paramArrayOfClass != null) for (byte b = 0; b < paramArrayOfClass.length; b++) { if (b > 0) stringBuilder.append(", ");  Class<?> clazz = paramArrayOfClass[b]; stringBuilder.append((clazz == null) ? "null" : clazz.getName()); }   stringBuilder.append(")"); return stringBuilder.toString(); } private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0]; private static ReflectionFactory reflectionFactory; public boolean desiredAssertionStatus() { ClassLoader classLoader = getClassLoader(); if (classLoader == null) return desiredAssertionStatus0(this);  synchronized (classLoader.assertionLock) { if (classLoader.classAssertionStatus != null) return classLoader.desiredAssertionStatus(getName());  }  return desiredAssertionStatus0(this); } public boolean isEnum() { return ((getModifiers() & 0x4000) != 0 && getSuperclass() == Enum.class); } private static ReflectionFactory getReflectionFactory() { if (reflectionFactory == null) reflectionFactory = AccessController.<ReflectionFactory>doPrivileged((PrivilegedAction<ReflectionFactory>)new ReflectionFactory.GetReflectionFactoryAction());  return reflectionFactory; } private static boolean initted = false; private volatile transient T[] enumConstants; private volatile transient Map<String, T> enumConstantDirectory; private volatile transient AnnotationData annotationData; private volatile transient AnnotationType annotationType; transient ClassValue.ClassValueMap classValueMap; private static void checkInitted() { if (initted) return;  AccessController.doPrivileged(new PrivilegedAction<Void>() { public Void run() { if (System.out == null) return null;  String str = System.getProperty("sun.reflect.noCaches"); if (str != null && str.equals("true")) Class.useCaches = false;  Class.initted = true; return null; } }
/*      */       ); } public T[] getEnumConstants() { T[] arrayOfT = getEnumConstantsShared(); return (arrayOfT != null) ? (T[])arrayOfT.clone() : null; } T[] getEnumConstantsShared() { if (this.enumConstants == null) { if (!isEnum()) return null;  try { final Method values = getMethod("values", new Class[0]); AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */               public Void run() { values.setAccessible(true); return null; }
/* 3401 */             }); Object[] arrayOfObject = (Object[])method.invoke(null, new Object[0]); this.enumConstants = (T[])arrayOfObject; } catch (InvocationTargetException|NoSuchMethodException|IllegalAccessException invocationTargetException) { return null; }  }  return this.enumConstants; } Map<String, T> enumConstantDirectory() { if (this.enumConstantDirectory == null) { T[] arrayOfT = getEnumConstantsShared(); if (arrayOfT == null) throw new IllegalArgumentException(getName() + " is not an enum type");  HashMap<Object, Object> hashMap = new HashMap<>(2 * arrayOfT.length); for (T t : arrayOfT) hashMap.put(((Enum)t).name(), t);  this.enumConstantDirectory = (Map)hashMap; }  return this.enumConstantDirectory; } public <U> Class<? extends U> asSubclass(Class<U> paramClass) { if (paramClass.isAssignableFrom(this)) {
/* 3402 */       return this;
/*      */     }
/* 3404 */     throw new ClassCastException(toString()); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <A extends Annotation> A getAnnotation(Class<A> paramClass) {
/* 3413 */     Objects.requireNonNull(paramClass);
/*      */     
/* 3415 */     return (A)(annotationData()).annotations.get(paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAnnotationPresent(Class<? extends Annotation> paramClass) {
/* 3425 */     return super.isAnnotationPresent(paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <A extends Annotation> A[] getAnnotationsByType(Class<A> paramClass) {
/* 3434 */     Objects.requireNonNull(paramClass);
/*      */     
/* 3436 */     AnnotationData annotationData = annotationData();
/* 3437 */     return AnnotationSupport.getAssociatedAnnotations(annotationData.declaredAnnotations, this, paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Annotation[] getAnnotations() {
/* 3446 */     return AnnotationParser.toArray((annotationData()).annotations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <A extends Annotation> A getDeclaredAnnotation(Class<A> paramClass) {
/* 3456 */     Objects.requireNonNull(paramClass);
/*      */     
/* 3458 */     return (A)(annotationData()).declaredAnnotations.get(paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<A> paramClass) {
/* 3467 */     Objects.requireNonNull(paramClass);
/*      */     
/* 3469 */     return AnnotationSupport.getDirectlyAndIndirectlyPresent((annotationData()).declaredAnnotations, paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Annotation[] getDeclaredAnnotations() {
/* 3477 */     return AnnotationParser.toArray((annotationData()).declaredAnnotations);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class AnnotationData
/*      */   {
/*      */     final Map<Class<? extends Annotation>, Annotation> annotations;
/*      */     
/*      */     final Map<Class<? extends Annotation>, Annotation> declaredAnnotations;
/*      */     
/*      */     final int redefinedCount;
/*      */ 
/*      */     
/*      */     AnnotationData(Map<Class<? extends Annotation>, Annotation> param1Map1, Map<Class<? extends Annotation>, Annotation> param1Map2, int param1Int) {
/* 3491 */       this.annotations = param1Map1;
/* 3492 */       this.declaredAnnotations = param1Map2;
/* 3493 */       this.redefinedCount = param1Int;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private AnnotationData annotationData() {
/*      */     AnnotationData annotationData1;
/*      */     AnnotationData annotationData2;
/*      */     do {
/* 3503 */       annotationData1 = this.annotationData;
/* 3504 */       int i = this.classRedefinedCount;
/* 3505 */       if (annotationData1 != null && annotationData1.redefinedCount == i)
/*      */       {
/* 3507 */         return annotationData1;
/*      */       }
/*      */       
/* 3510 */       annotationData2 = createAnnotationData(i);
/*      */     }
/* 3512 */     while (!Atomic.casAnnotationData(this, annotationData1, annotationData2));
/*      */     
/* 3514 */     return annotationData2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AnnotationData createAnnotationData(int paramInt) {
/* 3521 */     Map<Class<? extends Annotation>, Annotation> map1 = AnnotationParser.parseAnnotations(getRawAnnotations(), getConstantPool(), this);
/* 3522 */     Class<? super T> clazz = getSuperclass();
/* 3523 */     Map<Class<? extends Annotation>, Annotation> map2 = null;
/* 3524 */     if (clazz != null) {
/*      */       
/* 3526 */       Map<Class<? extends Annotation>, Annotation> map = (clazz.annotationData()).annotations;
/* 3527 */       for (Map.Entry<Class<? extends Annotation>, Annotation> entry : map.entrySet()) {
/* 3528 */         Class<? extends Annotation> clazz1 = (Class)entry.getKey();
/* 3529 */         if (AnnotationType.getInstance(clazz1).isInherited()) {
/* 3530 */           if (map2 == null) {
/* 3531 */             map2 = (Map)new LinkedHashMap<>((Math.max(map1
/* 3532 */                   .size(), 
/* 3533 */                   Math.min(12, map1.size() + map.size())) * 4 + 2) / 3);
/*      */           }
/*      */ 
/*      */           
/* 3537 */           map2.put(clazz1, entry.getValue());
/*      */         } 
/*      */       } 
/*      */     } 
/* 3541 */     if (map2 == null) {
/*      */       
/* 3543 */       map2 = map1;
/*      */     } else {
/*      */       
/* 3546 */       map2.putAll(map1);
/*      */     } 
/* 3548 */     return new AnnotationData(map2, map1, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean casAnnotationType(AnnotationType paramAnnotationType1, AnnotationType paramAnnotationType2) {
/* 3557 */     return Atomic.casAnnotationType(this, paramAnnotationType1, paramAnnotationType2);
/*      */   }
/*      */   
/*      */   AnnotationType getAnnotationType() {
/* 3561 */     return this.annotationType;
/*      */   }
/*      */   
/*      */   Map<Class<? extends Annotation>, Annotation> getDeclaredAnnotationMap() {
/* 3565 */     return (annotationData()).declaredAnnotations;
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
/*      */   public AnnotatedType getAnnotatedSuperclass() {
/* 3593 */     if (this == Object.class || 
/* 3594 */       isInterface() || 
/* 3595 */       isArray() || 
/* 3596 */       isPrimitive() || this == void.class)
/*      */     {
/* 3598 */       return null;
/*      */     }
/*      */     
/* 3601 */     return TypeAnnotationParser.buildAnnotatedSuperclass(getRawTypeAnnotations(), getConstantPool(), this);
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
/*      */   public AnnotatedType[] getAnnotatedInterfaces() {
/* 3636 */     return TypeAnnotationParser.buildAnnotatedInterfaces(getRawTypeAnnotations(), getConstantPool(), this);
/*      */   }
/*      */   
/*      */   private static native void registerNatives();
/*      */   
/*      */   private static native Class<?> forName0(String paramString, boolean paramBoolean, ClassLoader paramClassLoader, Class<?> paramClass) throws ClassNotFoundException;
/*      */   
/*      */   public native boolean isInstance(Object paramObject);
/*      */   
/*      */   public native boolean isAssignableFrom(Class<?> paramClass);
/*      */   
/*      */   public native boolean isInterface();
/*      */   
/*      */   public native boolean isArray();
/*      */   
/*      */   public native boolean isPrimitive();
/*      */   
/*      */   private native String getName0();
/*      */   
/*      */   public native Class<? super T> getSuperclass();
/*      */   
/*      */   private native Class<?>[] getInterfaces0();
/*      */   
/*      */   public native Class<?> getComponentType();
/*      */   
/*      */   public native int getModifiers();
/*      */   
/*      */   public native Object[] getSigners();
/*      */   
/*      */   native void setSigners(Object[] paramArrayOfObject);
/*      */   
/*      */   private native Object[] getEnclosingMethod0();
/*      */   
/*      */   private native Class<?> getDeclaringClass0();
/*      */   
/*      */   private native ProtectionDomain getProtectionDomain0();
/*      */   
/*      */   static native Class<?> getPrimitiveClass(String paramString);
/*      */   
/*      */   private native String getGenericSignature0();
/*      */   
/*      */   native byte[] getRawAnnotations();
/*      */   
/*      */   native byte[] getRawTypeAnnotations();
/*      */   
/*      */   native ConstantPool getConstantPool();
/*      */   
/*      */   private native Field[] getDeclaredFields0(boolean paramBoolean);
/*      */   
/*      */   private native Method[] getDeclaredMethods0(boolean paramBoolean);
/*      */   
/*      */   private native Constructor<T>[] getDeclaredConstructors0(boolean paramBoolean);
/*      */   
/*      */   private native Class<?>[] getDeclaredClasses0();
/*      */   
/*      */   private static native boolean desiredAssertionStatus0(Class<?> paramClass);
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/Class.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */