/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandleImpl;
/*     */ import java.lang.invoke.MethodHandleProxies;
/*     */ import java.lang.invoke.MethodHandleStatics;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import sun.invoke.WrapperInstance;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
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
/*     */ public class MethodHandleProxies
/*     */ {
/*     */   @CallerSensitive
/*     */   public static <T> T asInterfaceInstance(final Class<T> intfc, final MethodHandle target) {
/*     */     MethodHandle methodHandle;
/*     */     Object object;
/* 155 */     if (!intfc.isInterface() || !Modifier.isPublic(intfc.getModifiers())) {
/* 156 */       throw MethodHandleStatics.newIllegalArgumentException("not a public interface", intfc.getName());
/*     */     }
/* 158 */     if (System.getSecurityManager() != null) {
/* 159 */       Class<?> clazz = Reflection.getCallerClass();
/* 160 */       final ClassLoader loader = (clazz != null) ? clazz.getClassLoader() : null;
/* 161 */       ReflectUtil.checkProxyPackageAccess(classLoader1, new Class[] { intfc });
/* 162 */       methodHandle = (classLoader1 != null) ? bindCaller(target, clazz) : target;
/*     */     } else {
/* 164 */       methodHandle = target;
/*     */     } 
/* 166 */     ClassLoader classLoader = intfc.getClassLoader();
/* 167 */     if (classLoader == null) {
/* 168 */       final ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 169 */       classLoader = (classLoader1 != null) ? classLoader1 : ClassLoader.getSystemClassLoader();
/*     */     } 
/* 171 */     final Method[] methods = getSingleNameMethods(intfc);
/* 172 */     if (arrayOfMethod == null)
/* 173 */       throw MethodHandleStatics.newIllegalArgumentException("not a single-method interface", intfc.getName()); 
/* 174 */     final MethodHandle[] vaTargets = new MethodHandle[arrayOfMethod.length];
/* 175 */     for (byte b = 0; b < arrayOfMethod.length; b++) {
/* 176 */       Method method = arrayOfMethod[b];
/* 177 */       object = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
/* 178 */       MethodHandle methodHandle1 = methodHandle.asType((MethodType)object);
/* 179 */       methodHandle1 = methodHandle1.asType(methodHandle1.type().changeReturnType(Object.class));
/* 180 */       arrayOfMethodHandle[b] = methodHandle1.asSpreader(Object[].class, object.parameterCount());
/*     */     } 
/*     */     
/* 183 */     final ConcurrentHashMap defaultMethodMap = hasDefaultMethods(intfc) ? new ConcurrentHashMap<>() : null;
/* 184 */     final InvocationHandler ih = new InvocationHandler() {
/*     */         private Object getArg(String param1String) {
/* 186 */           if (param1String == "getWrapperInstanceTarget") return target; 
/* 187 */           if (param1String == "getWrapperInstanceType") return intfc; 
/* 188 */           throw new AssertionError();
/*     */         }
/*     */         public Object invoke(Object param1Object, Method param1Method, Object[] param1ArrayOfObject) throws Throwable {
/* 191 */           for (byte b = 0; b < methods.length; b++) {
/* 192 */             if (param1Method.equals(methods[b]))
/* 193 */               return vaTargets[b].invokeExact(param1ArrayOfObject); 
/*     */           } 
/* 195 */           if (param1Method.getDeclaringClass() == WrapperInstance.class)
/* 196 */             return getArg(param1Method.getName()); 
/* 197 */           if (MethodHandleProxies.isObjectMethod(param1Method))
/* 198 */             return MethodHandleProxies.callObjectMethod(param1Object, param1Method, param1ArrayOfObject); 
/* 199 */           if (MethodHandleProxies.isDefaultMethod(param1Method)) {
/* 200 */             return MethodHandleProxies.callDefaultMethod(defaultMethodMap, param1Object, intfc, param1Method, param1ArrayOfObject);
/*     */           }
/* 202 */           throw MethodHandleStatics.newInternalError("bad proxy method: " + param1Method);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 207 */     if (System.getSecurityManager() != null) {
/*     */ 
/*     */       
/* 210 */       final ClassLoader loader = classLoader;
/* 211 */       object = AccessController.doPrivileged(new PrivilegedAction() {
/*     */             public Object run() {
/* 213 */               return Proxy.newProxyInstance(loader, new Class[] { this.val$intfc, WrapperInstance.class }, ih);
/*     */             }
/*     */           });
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 220 */       object = Proxy.newProxyInstance(classLoader, new Class[] { intfc, WrapperInstance.class }, invocationHandler);
/*     */     } 
/*     */ 
/*     */     
/* 224 */     return intfc.cast(object);
/*     */   }
/*     */   
/*     */   private static MethodHandle bindCaller(MethodHandle paramMethodHandle, Class<?> paramClass) {
/* 228 */     MethodHandle methodHandle = MethodHandleImpl.bindCaller(paramMethodHandle, paramClass);
/* 229 */     if (paramMethodHandle.isVarargsCollector()) {
/* 230 */       MethodType methodType = methodHandle.type();
/* 231 */       int i = methodType.parameterCount();
/* 232 */       return methodHandle.asVarargsCollector(methodType.parameterType(i - 1));
/*     */     } 
/* 234 */     return methodHandle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWrapperInstance(Object paramObject) {
/* 244 */     return paramObject instanceof WrapperInstance;
/*     */   }
/*     */   
/*     */   private static WrapperInstance asWrapperInstance(Object paramObject) {
/*     */     try {
/* 249 */       if (paramObject != null)
/* 250 */         return (WrapperInstance)paramObject; 
/* 251 */     } catch (ClassCastException classCastException) {}
/*     */     
/* 253 */     throw MethodHandleStatics.newIllegalArgumentException("not a wrapper instance");
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
/*     */   public static MethodHandle wrapperInstanceTarget(Object paramObject) {
/* 267 */     return asWrapperInstance(paramObject).getWrapperInstanceTarget();
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
/*     */   public static Class<?> wrapperInstanceType(Object paramObject) {
/* 280 */     return asWrapperInstance(paramObject).getWrapperInstanceType();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isObjectMethod(Method paramMethod) {
/* 285 */     switch (paramMethod.getName()) {
/*     */       case "toString":
/* 287 */         return (paramMethod.getReturnType() == String.class && (paramMethod
/* 288 */           .getParameterTypes()).length == 0);
/*     */       case "hashCode":
/* 290 */         return (paramMethod.getReturnType() == int.class && (paramMethod
/* 291 */           .getParameterTypes()).length == 0);
/*     */       case "equals":
/* 293 */         return (paramMethod.getReturnType() == boolean.class && (paramMethod
/* 294 */           .getParameterTypes()).length == 1 && paramMethod
/* 295 */           .getParameterTypes()[0] == Object.class);
/*     */     } 
/* 297 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object callObjectMethod(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) {
/* 302 */     assert isObjectMethod(paramMethod) : paramMethod;
/* 303 */     switch (paramMethod.getName()) {
/*     */       case "toString":
/* 305 */         return paramObject.getClass().getName() + "@" + Integer.toHexString(paramObject.hashCode());
/*     */       case "hashCode":
/* 307 */         return Integer.valueOf(System.identityHashCode(paramObject));
/*     */       case "equals":
/* 309 */         return Boolean.valueOf((paramObject == paramArrayOfObject[0]));
/*     */     } 
/* 311 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Method[] getSingleNameMethods(Class<?> paramClass) {
/* 316 */     ArrayList<Method> arrayList = new ArrayList();
/* 317 */     String str = null;
/* 318 */     for (Method method : paramClass.getMethods()) {
/* 319 */       if (!isObjectMethod(method) && 
/* 320 */         Modifier.isAbstract(method.getModifiers())) {
/* 321 */         String str1 = method.getName();
/* 322 */         if (str == null) {
/* 323 */           str = str1;
/* 324 */         } else if (!str.equals(str1)) {
/* 325 */           return null;
/* 326 */         }  arrayList.add(method);
/*     */       } 
/* 328 */     }  if (str == null) return null; 
/* 329 */     return arrayList.<Method>toArray(new Method[arrayList.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isDefaultMethod(Method paramMethod) {
/* 334 */     return !Modifier.isAbstract(paramMethod.getModifiers());
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasDefaultMethods(Class<?> paramClass) {
/* 339 */     for (Method method : paramClass.getMethods()) {
/* 340 */       if (!isObjectMethod(method) && 
/* 341 */         !Modifier.isAbstract(method.getModifiers())) {
/* 342 */         return true;
/*     */       }
/*     */     } 
/* 345 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object callDefaultMethod(ConcurrentHashMap<Method, MethodHandle> paramConcurrentHashMap, Object paramObject, Class<?> paramClass, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
/* 351 */     assert isDefaultMethod(paramMethod) && !isObjectMethod(paramMethod) : paramMethod;
/*     */ 
/*     */     
/* 354 */     MethodHandle methodHandle = paramConcurrentHashMap.computeIfAbsent(paramMethod, paramMethod -> {
/*     */           try {
/*     */             MethodHandle methodHandle = MethodHandles.Lookup.IMPL_LOOKUP.findSpecial(paramClass, paramMethod.getName(), MethodType.methodType(paramMethod.getReturnType(), paramMethod.getParameterTypes()), paramObject.getClass());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             return methodHandle.asSpreader(Object[].class, paramMethod.getParameterCount());
/* 363 */           } catch (NoSuchMethodException|IllegalAccessException noSuchMethodException) {
/*     */             throw new InternalError(noSuchMethodException);
/*     */           } 
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 370 */     return methodHandle.invoke(paramObject, paramArrayOfObject);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/MethodHandleProxies.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */