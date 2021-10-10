/*     */ package sun.rmi.server;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.StubNotFoundException;
/*     */ import java.rmi.server.LogStream;
/*     */ import java.rmi.server.RemoteObjectInvocationHandler;
/*     */ import java.rmi.server.RemoteRef;
/*     */ import java.rmi.server.RemoteStub;
/*     */ import java.rmi.server.Skeleton;
/*     */ import java.rmi.server.SkeletonNotFoundException;
/*     */ import java.security.AccessController;
/*     */ import java.security.DigestOutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Util
/*     */ {
/*  71 */   static final int logLevel = LogStream.parseLevel(
/*  72 */       AccessController.<String>doPrivileged(new GetPropertyAction("sun.rmi.server.logLevel")));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final Log serverRefLog = Log.getLog("sun.rmi.server.ref", "transport", logLevel);
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static final boolean ignoreStubClasses = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("java.rmi.server.ignoreStubClasses")))
/*     */     
/*  83 */     .booleanValue();
/*     */ 
/*     */ 
/*     */   
/*  87 */   private static final Map<Class<?>, Void> withoutStubs = Collections.synchronizedMap(new WeakHashMap<>(11));
/*     */ 
/*     */   
/*  90 */   private static final Class<?>[] stubConsParamTypes = new Class[] { RemoteRef.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Remote createProxy(Class<?> paramClass, RemoteRef paramRemoteRef, boolean paramBoolean) throws StubNotFoundException {
/*     */     Class<?> clazz;
/*     */     try {
/* 132 */       clazz = getRemoteClass(paramClass);
/* 133 */     } catch (ClassNotFoundException classNotFoundException) {
/* 134 */       throw new StubNotFoundException("object does not implement a remote interface: " + paramClass
/*     */           
/* 136 */           .getName());
/*     */     } 
/*     */     
/* 139 */     if (paramBoolean || (!ignoreStubClasses && 
/* 140 */       stubClassExists(clazz)))
/*     */     {
/* 142 */       return createStub(clazz, paramRemoteRef);
/*     */     }
/*     */     
/* 145 */     final ClassLoader loader = paramClass.getClassLoader();
/* 146 */     final Class[] interfaces = getRemoteInterfaces(paramClass);
/* 147 */     final RemoteObjectInvocationHandler handler = new RemoteObjectInvocationHandler(paramRemoteRef);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 153 */       return AccessController.<Remote>doPrivileged(new PrivilegedAction<Remote>() {
/*     */             public Remote run() {
/* 155 */               return (Remote)Proxy.newProxyInstance(loader, interfaces, handler);
/*     */             }
/*     */           });
/*     */     }
/* 159 */     catch (IllegalArgumentException illegalArgumentException) {
/* 160 */       throw new StubNotFoundException("unable to create proxy", illegalArgumentException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean stubClassExists(Class<?> paramClass) {
/* 171 */     if (!withoutStubs.containsKey(paramClass)) {
/*     */       try {
/* 173 */         Class.forName(paramClass.getName() + "_Stub", false, paramClass
/*     */             
/* 175 */             .getClassLoader());
/* 176 */         return true;
/*     */       }
/* 178 */       catch (ClassNotFoundException classNotFoundException) {
/* 179 */         withoutStubs.put(paramClass, null);
/*     */       } 
/*     */     }
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> getRemoteClass(Class<?> paramClass) throws ClassNotFoundException {
/* 193 */     while (paramClass != null) {
/* 194 */       Class[] arrayOfClass = paramClass.getInterfaces();
/* 195 */       for (int i = arrayOfClass.length - 1; i >= 0; i--) {
/* 196 */         if (Remote.class.isAssignableFrom(arrayOfClass[i]))
/* 197 */           return paramClass; 
/*     */       } 
/* 199 */       paramClass = paramClass.getSuperclass();
/*     */     } 
/* 201 */     throw new ClassNotFoundException("class does not implement java.rmi.Remote");
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
/*     */   private static Class<?>[] getRemoteInterfaces(Class<?> paramClass) {
/* 215 */     ArrayList<Class<?>> arrayList = new ArrayList();
/* 216 */     getRemoteInterfaces(arrayList, paramClass);
/* 217 */     return (Class[])arrayList.<Class<?>[]>toArray((Class<?>[][])new Class[arrayList.size()]);
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
/*     */   private static void getRemoteInterfaces(ArrayList<Class<?>> paramArrayList, Class<?> paramClass) {
/* 229 */     Class<?> clazz = paramClass.getSuperclass();
/* 230 */     if (clazz != null) {
/* 231 */       getRemoteInterfaces(paramArrayList, clazz);
/*     */     }
/*     */     
/* 234 */     Class[] arrayOfClass = paramClass.getInterfaces();
/* 235 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 236 */       Class<?> clazz1 = arrayOfClass[b];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 242 */       if (Remote.class.isAssignableFrom(clazz1) && 
/* 243 */         !paramArrayList.contains(clazz1)) {
/* 244 */         Method[] arrayOfMethod = clazz1.getMethods();
/* 245 */         for (byte b1 = 0; b1 < arrayOfMethod.length; b1++) {
/* 246 */           checkMethod(arrayOfMethod[b1]);
/*     */         }
/* 248 */         paramArrayList.add(clazz1);
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
/*     */   private static void checkMethod(Method paramMethod) {
/* 262 */     Class[] arrayOfClass = paramMethod.getExceptionTypes();
/* 263 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 264 */       if (arrayOfClass[b].isAssignableFrom(RemoteException.class))
/*     */         return; 
/*     */     } 
/* 267 */     throw new IllegalArgumentException("illegal remote method encountered: " + paramMethod);
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
/*     */   private static RemoteStub createStub(Class<?> paramClass, RemoteRef paramRemoteRef) throws StubNotFoundException {
/* 283 */     String str = paramClass.getName() + "_Stub";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 292 */       Class<?> clazz = Class.forName(str, false, paramClass.getClassLoader());
/* 293 */       Constructor<?> constructor = clazz.getConstructor(stubConsParamTypes);
/* 294 */       return (RemoteStub)constructor.newInstance(new Object[] { paramRemoteRef });
/*     */     }
/* 296 */     catch (ClassNotFoundException classNotFoundException) {
/* 297 */       throw new StubNotFoundException("Stub class not found: " + str, classNotFoundException);
/*     */     }
/* 299 */     catch (NoSuchMethodException noSuchMethodException) {
/* 300 */       throw new StubNotFoundException("Stub class missing constructor: " + str, noSuchMethodException);
/*     */     }
/* 302 */     catch (InstantiationException instantiationException) {
/* 303 */       throw new StubNotFoundException("Can't create instance of stub class: " + str, instantiationException);
/*     */     }
/* 305 */     catch (IllegalAccessException illegalAccessException) {
/* 306 */       throw new StubNotFoundException("Stub class constructor not public: " + str, illegalAccessException);
/*     */     }
/* 308 */     catch (InvocationTargetException invocationTargetException) {
/* 309 */       throw new StubNotFoundException("Exception creating instance of stub class: " + str, invocationTargetException);
/*     */     }
/* 311 */     catch (ClassCastException classCastException) {
/* 312 */       throw new StubNotFoundException("Stub class not instance of RemoteStub: " + str, classCastException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Skeleton createSkeleton(Remote paramRemote) throws SkeletonNotFoundException {
/*     */     Class<?> clazz;
/*     */     try {
/* 325 */       clazz = getRemoteClass(paramRemote.getClass());
/* 326 */     } catch (ClassNotFoundException classNotFoundException) {
/* 327 */       throw new SkeletonNotFoundException("object does not implement a remote interface: " + paramRemote
/*     */           
/* 329 */           .getClass().getName());
/*     */     } 
/*     */ 
/*     */     
/* 333 */     String str = clazz.getName() + "_Skel";
/*     */     try {
/* 335 */       Class<?> clazz1 = Class.forName(str, false, clazz.getClassLoader());
/*     */       
/* 337 */       return (Skeleton)clazz1.newInstance();
/* 338 */     } catch (ClassNotFoundException classNotFoundException) {
/* 339 */       throw new SkeletonNotFoundException("Skeleton class not found: " + str, classNotFoundException);
/*     */     }
/* 341 */     catch (InstantiationException instantiationException) {
/* 342 */       throw new SkeletonNotFoundException("Can't create skeleton: " + str, instantiationException);
/*     */     }
/* 344 */     catch (IllegalAccessException illegalAccessException) {
/* 345 */       throw new SkeletonNotFoundException("No public constructor: " + str, illegalAccessException);
/*     */     }
/* 347 */     catch (ClassCastException classCastException) {
/* 348 */       throw new SkeletonNotFoundException("Skeleton not of correct class: " + str, classCastException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long computeMethodHash(Method paramMethod) {
/* 359 */     long l = 0L;
/* 360 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(127);
/*     */     try {
/* 362 */       MessageDigest messageDigest = MessageDigest.getInstance("SHA");
/* 363 */       DataOutputStream dataOutputStream = new DataOutputStream(new DigestOutputStream(byteArrayOutputStream, messageDigest));
/*     */ 
/*     */       
/* 366 */       String str = getMethodNameAndDescriptor(paramMethod);
/* 367 */       if (serverRefLog.isLoggable(Log.VERBOSE)) {
/* 368 */         serverRefLog.log(Log.VERBOSE, "string used for method hash: \"" + str + "\"");
/*     */       }
/*     */       
/* 371 */       dataOutputStream.writeUTF(str);
/*     */ 
/*     */       
/* 374 */       dataOutputStream.flush();
/* 375 */       byte[] arrayOfByte = messageDigest.digest();
/* 376 */       for (byte b = 0; b < Math.min(8, arrayOfByte.length); b++) {
/* 377 */         l += (arrayOfByte[b] & 0xFF) << b * 8;
/*     */       }
/* 379 */     } catch (IOException iOException) {
/*     */       
/* 381 */       l = -1L;
/* 382 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 383 */       throw new SecurityException(noSuchAlgorithmException.getMessage());
/*     */     } 
/* 385 */     return l;
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
/*     */   private static String getMethodNameAndDescriptor(Method paramMethod) {
/* 397 */     StringBuffer stringBuffer = new StringBuffer(paramMethod.getName());
/* 398 */     stringBuffer.append('(');
/* 399 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 400 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 401 */       stringBuffer.append(getTypeDescriptor(arrayOfClass[b]));
/*     */     }
/* 403 */     stringBuffer.append(')');
/* 404 */     Class<?> clazz = paramMethod.getReturnType();
/* 405 */     if (clazz == void.class) {
/* 406 */       stringBuffer.append('V');
/*     */     } else {
/* 408 */       stringBuffer.append(getTypeDescriptor(clazz));
/*     */     } 
/* 410 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getTypeDescriptor(Class<?> paramClass) {
/* 418 */     if (paramClass.isPrimitive()) {
/* 419 */       if (paramClass == int.class)
/* 420 */         return "I"; 
/* 421 */       if (paramClass == boolean.class)
/* 422 */         return "Z"; 
/* 423 */       if (paramClass == byte.class)
/* 424 */         return "B"; 
/* 425 */       if (paramClass == char.class)
/* 426 */         return "C"; 
/* 427 */       if (paramClass == short.class)
/* 428 */         return "S"; 
/* 429 */       if (paramClass == long.class)
/* 430 */         return "J"; 
/* 431 */       if (paramClass == float.class)
/* 432 */         return "F"; 
/* 433 */       if (paramClass == double.class)
/* 434 */         return "D"; 
/* 435 */       if (paramClass == void.class) {
/* 436 */         return "V";
/*     */       }
/* 438 */       throw new Error("unrecognized primitive type: " + paramClass);
/*     */     } 
/* 440 */     if (paramClass.isArray())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 448 */       return paramClass.getName().replace('.', '/');
/*     */     }
/* 450 */     return "L" + paramClass.getName().replace('.', '/') + ";";
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
/*     */   public static String getUnqualifiedName(Class<?> paramClass) {
/* 463 */     String str = paramClass.getName();
/* 464 */     return str.substring(str.lastIndexOf('.') + 1);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/server/Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */