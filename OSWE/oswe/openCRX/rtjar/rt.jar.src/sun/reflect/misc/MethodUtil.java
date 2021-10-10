/*     */ package sun.reflect.misc;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.AccessController;
/*     */ import java.security.AllPermission;
/*     */ import java.security.CodeSource;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.SecureClassLoader;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import sun.misc.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MethodUtil
/*     */   extends SecureClassLoader
/*     */ {
/*     */   private static final String MISC_PKG = "sun.reflect.misc.";
/*     */   private static final String TRAMPOLINE = "sun.reflect.misc.Trampoline";
/*  82 */   private static final Method bounce = getTrampoline();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Method getMethod(Class<?> paramClass, String paramString, Class<?>[] paramArrayOfClass) throws NoSuchMethodException {
/*  90 */     ReflectUtil.checkPackageAccess(paramClass);
/*  91 */     return paramClass.getMethod(paramString, paramArrayOfClass);
/*     */   }
/*     */   
/*     */   public static Method[] getMethods(Class<?> paramClass) {
/*  95 */     ReflectUtil.checkPackageAccess(paramClass);
/*  96 */     return paramClass.getMethods();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Method[] getPublicMethods(Class<?> paramClass) {
/* 107 */     if (System.getSecurityManager() == null) {
/* 108 */       return paramClass.getMethods();
/*     */     }
/* 110 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 111 */     while (paramClass != null) {
/* 112 */       boolean bool = getInternalPublicMethods(paramClass, (Map)hashMap);
/* 113 */       if (bool) {
/*     */         break;
/*     */       }
/* 116 */       getInterfaceMethods(paramClass, (Map)hashMap);
/* 117 */       paramClass = paramClass.getSuperclass();
/*     */     } 
/* 119 */     return (Method[])hashMap.values().toArray((Object[])new Method[hashMap.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getInterfaceMethods(Class<?> paramClass, Map<Signature, Method> paramMap) {
/* 127 */     Class[] arrayOfClass = paramClass.getInterfaces();
/* 128 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 129 */       Class<?> clazz = arrayOfClass[b];
/* 130 */       boolean bool = getInternalPublicMethods(clazz, paramMap);
/* 131 */       if (!bool) {
/* 132 */         getInterfaceMethods(clazz, paramMap);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean getInternalPublicMethods(Class<?> paramClass, Map<Signature, Method> paramMap) {
/* 143 */     Method[] arrayOfMethod = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 150 */       if (!Modifier.isPublic(paramClass.getModifiers())) {
/* 151 */         return false;
/*     */       }
/* 153 */       if (!ReflectUtil.isPackageAccessible(paramClass)) {
/* 154 */         return false;
/*     */       }
/*     */       
/* 157 */       arrayOfMethod = paramClass.getMethods();
/* 158 */     } catch (SecurityException securityException) {
/* 159 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     boolean bool = true; byte b;
/* 169 */     for (b = 0; b < arrayOfMethod.length; b++) {
/* 170 */       Class<?> clazz = arrayOfMethod[b].getDeclaringClass();
/* 171 */       if (!Modifier.isPublic(clazz.getModifiers())) {
/* 172 */         bool = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 177 */     if (bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       for (b = 0; b < arrayOfMethod.length; b++) {
/* 183 */         addMethod(paramMap, arrayOfMethod[b]);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 190 */       for (b = 0; b < arrayOfMethod.length; b++) {
/* 191 */         Class<?> clazz = arrayOfMethod[b].getDeclaringClass();
/* 192 */         if (paramClass.equals(clazz)) {
/* 193 */           addMethod(paramMap, arrayOfMethod[b]);
/*     */         }
/*     */       } 
/*     */     } 
/* 197 */     return bool;
/*     */   }
/*     */   
/*     */   private static void addMethod(Map<Signature, Method> paramMap, Method paramMethod) {
/* 201 */     Signature signature = new Signature(paramMethod);
/* 202 */     if (!paramMap.containsKey(signature)) {
/* 203 */       paramMap.put(signature, paramMethod);
/* 204 */     } else if (!paramMethod.getDeclaringClass().isInterface()) {
/*     */ 
/*     */ 
/*     */       
/* 208 */       Method method = paramMap.get(signature);
/* 209 */       if (method.getDeclaringClass().isInterface()) {
/* 210 */         paramMap.put(signature, paramMethod);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Signature
/*     */   {
/*     */     private String methodName;
/*     */     
/*     */     private Class<?>[] argClasses;
/*     */     
/* 223 */     private volatile int hashCode = 0;
/*     */     
/*     */     Signature(Method param1Method) {
/* 226 */       this.methodName = param1Method.getName();
/* 227 */       this.argClasses = param1Method.getParameterTypes();
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 231 */       if (this == param1Object) {
/* 232 */         return true;
/*     */       }
/* 234 */       Signature signature = (Signature)param1Object;
/* 235 */       if (!this.methodName.equals(signature.methodName)) {
/* 236 */         return false;
/*     */       }
/* 238 */       if (this.argClasses.length != signature.argClasses.length) {
/* 239 */         return false;
/*     */       }
/* 241 */       for (byte b = 0; b < this.argClasses.length; b++) {
/* 242 */         if (this.argClasses[b] != signature.argClasses[b]) {
/* 243 */           return false;
/*     */         }
/*     */       } 
/* 246 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 254 */       if (this.hashCode == 0) {
/* 255 */         int i = 17;
/* 256 */         i = 37 * i + this.methodName.hashCode();
/* 257 */         if (this.argClasses != null) {
/* 258 */           for (byte b = 0; b < this.argClasses.length; b++)
/*     */           {
/* 260 */             i = 37 * i + ((this.argClasses[b] == null) ? 0 : this.argClasses[b].hashCode());
/*     */           }
/*     */         }
/* 263 */         this.hashCode = i;
/*     */       } 
/* 265 */       return this.hashCode;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object invoke(Method paramMethod, Object paramObject, Object[] paramArrayOfObject) throws InvocationTargetException, IllegalAccessException {
/*     */     try {
/* 276 */       return bounce.invoke(null, new Object[] { paramMethod, paramObject, paramArrayOfObject });
/* 277 */     } catch (InvocationTargetException invocationTargetException) {
/* 278 */       Throwable throwable = invocationTargetException.getCause();
/*     */       
/* 280 */       if (throwable instanceof InvocationTargetException)
/* 281 */         throw (InvocationTargetException)throwable; 
/* 282 */       if (throwable instanceof IllegalAccessException)
/* 283 */         throw (IllegalAccessException)throwable; 
/* 284 */       if (throwable instanceof RuntimeException)
/* 285 */         throw (RuntimeException)throwable; 
/* 286 */       if (throwable instanceof Error) {
/* 287 */         throw (Error)throwable;
/*     */       }
/* 289 */       throw new Error("Unexpected invocation error", throwable);
/*     */     }
/* 291 */     catch (IllegalAccessException illegalAccessException) {
/*     */       
/* 293 */       throw new Error("Unexpected invocation error", illegalAccessException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Method getTrampoline() {
/*     */     try {
/* 299 */       return AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/* 302 */               Class clazz = MethodUtil.getTrampolineClass();
/* 303 */               Class[] arrayOfClass = { Method.class, Object.class, Object[].class };
/*     */ 
/*     */               
/* 306 */               Method method = clazz.getDeclaredMethod("invoke", arrayOfClass);
/* 307 */               method.setAccessible(true);
/* 308 */               return method;
/*     */             }
/*     */           });
/* 311 */     } catch (Exception exception) {
/* 312 */       throw new InternalError("bouncer cannot be found", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized Class<?> loadClass(String paramString, boolean paramBoolean) throws ClassNotFoundException {
/* 321 */     ReflectUtil.checkPackageAccess(paramString);
/* 322 */     Class<?> clazz = findLoadedClass(paramString);
/* 323 */     if (clazz == null) {
/*     */       try {
/* 325 */         clazz = findClass(paramString);
/* 326 */       } catch (ClassNotFoundException classNotFoundException) {}
/*     */ 
/*     */       
/* 329 */       if (clazz == null) {
/* 330 */         clazz = getParent().loadClass(paramString);
/*     */       }
/*     */     } 
/* 333 */     if (paramBoolean) {
/* 334 */       resolveClass(clazz);
/*     */     }
/* 336 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class<?> findClass(String paramString) throws ClassNotFoundException {
/* 343 */     if (!paramString.startsWith("sun.reflect.misc.")) {
/* 344 */       throw new ClassNotFoundException(paramString);
/*     */     }
/* 346 */     String str = paramString.replace('.', '/').concat(".class");
/* 347 */     URL uRL = getResource(str);
/* 348 */     if (uRL != null) {
/*     */       try {
/* 350 */         return defineClass(paramString, uRL);
/* 351 */       } catch (IOException iOException) {
/* 352 */         throw new ClassNotFoundException(paramString, iOException);
/*     */       } 
/*     */     }
/* 355 */     throw new ClassNotFoundException(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> defineClass(String paramString, URL paramURL) throws IOException {
/* 364 */     byte[] arrayOfByte = getBytes(paramURL);
/* 365 */     CodeSource codeSource = new CodeSource(null, (Certificate[])null);
/* 366 */     if (!paramString.equals("sun.reflect.misc.Trampoline")) {
/* 367 */       throw new IOException("MethodUtil: bad name " + paramString);
/*     */     }
/* 369 */     return defineClass(paramString, arrayOfByte, 0, arrayOfByte.length, codeSource);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] getBytes(URL paramURL) throws IOException {
/* 377 */     URLConnection uRLConnection = paramURL.openConnection();
/* 378 */     if (uRLConnection instanceof HttpURLConnection) {
/* 379 */       HttpURLConnection httpURLConnection = (HttpURLConnection)uRLConnection;
/* 380 */       int j = httpURLConnection.getResponseCode();
/* 381 */       if (j >= 400) {
/* 382 */         throw new IOException("open HTTP connection failed.");
/*     */       }
/*     */     } 
/* 385 */     int i = uRLConnection.getContentLength();
/* 386 */     try (BufferedInputStream null = new BufferedInputStream(uRLConnection.getInputStream())) {
/* 387 */       byte[] arrayOfByte = IOUtils.readAllBytes(bufferedInputStream);
/* 388 */       if (i != -1 && arrayOfByte.length != i)
/* 389 */         throw new EOFException("Expected:" + i + ", read:" + arrayOfByte.length); 
/* 390 */       return arrayOfByte;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PermissionCollection getPermissions(CodeSource paramCodeSource) {
/* 397 */     PermissionCollection permissionCollection = super.getPermissions(paramCodeSource);
/* 398 */     permissionCollection.add(new AllPermission());
/* 399 */     return permissionCollection;
/*     */   }
/*     */   
/*     */   private static Class<?> getTrampolineClass() {
/*     */     try {
/* 404 */       return Class.forName("sun.reflect.misc.Trampoline", true, new MethodUtil());
/* 405 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */       
/* 407 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/misc/MethodUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */