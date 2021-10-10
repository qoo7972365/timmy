/*     */ package sun.reflect.misc;
/*     */ 
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Proxy;
/*     */ import sun.reflect.Reflection;
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
/*     */ public final class ReflectUtil
/*     */ {
/*     */   public static final String PROXY_PACKAGE = "com.sun.proxy";
/*     */   
/*     */   public static Class<?> forName(String paramString) throws ClassNotFoundException {
/*  44 */     checkPackageAccess(paramString);
/*  45 */     return Class.forName(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object newInstance(Class<?> paramClass) throws InstantiationException, IllegalAccessException {
/*  50 */     checkPackageAccess(paramClass);
/*  51 */     return paramClass.newInstance();
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
/*     */   public static void ensureMemberAccess(Class<?> paramClass1, Class<?> paramClass2, Object paramObject, int paramInt) throws IllegalAccessException {
/*  64 */     if (paramObject == null && Modifier.isProtected(paramInt)) {
/*  65 */       int i = paramInt;
/*  66 */       i &= 0xFFFFFFFB;
/*  67 */       i |= 0x1;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  72 */       Reflection.ensureMemberAccess(paramClass1, paramClass2, paramObject, i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  81 */         i &= 0xFFFFFFFE;
/*  82 */         Reflection.ensureMemberAccess(paramClass1, paramClass2, paramObject, i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*  91 */       } catch (IllegalAccessException illegalAccessException) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  96 */         if (isSubclassOf(paramClass1, paramClass2)) {
/*     */           return;
/*     */         }
/*  99 */         throw illegalAccessException;
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     Reflection.ensureMemberAccess(paramClass1, paramClass2, paramObject, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isSubclassOf(Class<?> paramClass1, Class<?> paramClass2) {
/* 113 */     while (paramClass1 != null) {
/* 114 */       if (paramClass1 == paramClass2) {
/* 115 */         return true;
/*     */       }
/* 117 */       paramClass1 = paramClass1.getSuperclass();
/*     */     } 
/* 119 */     return false;
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
/*     */   public static void conservativeCheckMemberAccess(Member paramMember) throws SecurityException {
/* 132 */     SecurityManager securityManager = System.getSecurityManager();
/* 133 */     if (securityManager == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     Class<?> clazz = paramMember.getDeclaringClass();
/*     */     
/* 146 */     checkPackageAccess(clazz);
/*     */     
/* 148 */     if (Modifier.isPublic(paramMember.getModifiers()) && 
/* 149 */       Modifier.isPublic(clazz.getModifiers())) {
/*     */       return;
/*     */     }
/*     */     
/* 153 */     securityManager.checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkPackageAccess(Class<?> paramClass) {
/* 164 */     checkPackageAccess(paramClass.getName());
/* 165 */     if (isNonPublicProxyClass(paramClass)) {
/* 166 */       checkProxyPackageAccess(paramClass);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkPackageAccess(String paramString) {
/* 177 */     SecurityManager securityManager = System.getSecurityManager();
/* 178 */     if (securityManager != null) {
/* 179 */       String str = paramString.replace('/', '.');
/* 180 */       if (str.startsWith("[")) {
/* 181 */         int j = str.lastIndexOf('[') + 2;
/* 182 */         if (j > 1 && j < str.length()) {
/* 183 */           str = str.substring(j);
/*     */         }
/*     */       } 
/* 186 */       int i = str.lastIndexOf('.');
/* 187 */       if (i != -1) {
/* 188 */         securityManager.checkPackageAccess(str.substring(0, i));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isPackageAccessible(Class<?> paramClass) {
/*     */     try {
/* 195 */       checkPackageAccess(paramClass);
/* 196 */     } catch (SecurityException securityException) {
/* 197 */       return false;
/*     */     } 
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isAncestor(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2) {
/* 205 */     ClassLoader classLoader = paramClassLoader2;
/*     */     while (true) {
/* 207 */       classLoader = classLoader.getParent();
/* 208 */       if (paramClassLoader1 == classLoader) {
/* 209 */         return true;
/*     */       }
/* 211 */       if (classLoader == null) {
/* 212 */         return false;
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
/*     */   public static boolean needsPackageAccessCheck(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2) {
/* 227 */     if (paramClassLoader1 == null || paramClassLoader1 == paramClassLoader2) {
/* 228 */       return false;
/*     */     }
/* 230 */     if (paramClassLoader2 == null) {
/* 231 */       return true;
/*     */     }
/* 233 */     return !isAncestor(paramClassLoader1, paramClassLoader2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkProxyPackageAccess(Class<?> paramClass) {
/* 243 */     SecurityManager securityManager = System.getSecurityManager();
/* 244 */     if (securityManager != null)
/*     */     {
/* 246 */       if (Proxy.isProxyClass(paramClass)) {
/* 247 */         for (Class<?> clazz : paramClass.getInterfaces()) {
/* 248 */           checkPackageAccess(clazz);
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
/*     */   public static void checkProxyPackageAccess(ClassLoader paramClassLoader, Class<?>... paramVarArgs) {
/* 265 */     SecurityManager securityManager = System.getSecurityManager();
/* 266 */     if (securityManager != null) {
/* 267 */       for (Class<?> clazz : paramVarArgs) {
/* 268 */         ClassLoader classLoader = clazz.getClassLoader();
/* 269 */         if (needsPackageAccessCheck(paramClassLoader, classLoader)) {
/* 270 */           checkPackageAccess(clazz);
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
/*     */   public static boolean isNonPublicProxyClass(Class<?> paramClass) {
/* 286 */     String str1 = paramClass.getName();
/* 287 */     int i = str1.lastIndexOf('.');
/* 288 */     String str2 = (i != -1) ? str1.substring(0, i) : "";
/* 289 */     return (Proxy.isProxyClass(paramClass) && !str2.equals("com.sun.proxy"));
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
/*     */   public static void checkProxyMethod(Object paramObject, Method paramMethod) {
/* 303 */     if (paramObject == null || !Proxy.isProxyClass(paramObject.getClass())) {
/* 304 */       throw new IllegalArgumentException("Not a Proxy instance");
/*     */     }
/* 306 */     if (Modifier.isStatic(paramMethod.getModifiers())) {
/* 307 */       throw new IllegalArgumentException("Can't handle static method");
/*     */     }
/*     */     
/* 310 */     Class<?> clazz = paramMethod.getDeclaringClass();
/* 311 */     if (clazz == Object.class) {
/* 312 */       String str = paramMethod.getName();
/* 313 */       if (str.equals("hashCode") || str.equals("equals") || str.equals("toString")) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 318 */     if (isSuperInterface(paramObject.getClass(), clazz)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 323 */     throw new IllegalArgumentException("Can't handle: " + paramMethod);
/*     */   }
/*     */   
/*     */   private static boolean isSuperInterface(Class<?> paramClass1, Class<?> paramClass2) {
/* 327 */     for (Class<?> clazz : paramClass1.getInterfaces()) {
/* 328 */       if (clazz == paramClass2) {
/* 329 */         return true;
/*     */       }
/* 331 */       if (isSuperInterface(clazz, paramClass2)) {
/* 332 */         return true;
/*     */       }
/*     */     } 
/* 335 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isVMAnonymousClass(Class<?> paramClass) {
/* 344 */     return (paramClass.getName().indexOf("/") > -1);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/misc/ReflectUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */