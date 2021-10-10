/*     */ package java.rmi.server;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.UnexpectedException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import sun.rmi.server.Util;
/*     */ import sun.rmi.server.WeakClassHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RemoteObjectInvocationHandler
/*     */   extends RemoteObject
/*     */   implements InvocationHandler
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*     */   private static final boolean allowFinalizeInvocation;
/*     */   
/*     */   static {
/*  65 */     final String propName = "sun.rmi.server.invocationhandler.allowFinalizeInvocation";
/*  66 */     String str2 = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run()
/*     */           {
/*  70 */             return System.getProperty(propName);
/*     */           }
/*     */         });
/*  73 */     if ("".equals(str2)) {
/*  74 */       allowFinalizeInvocation = true;
/*     */     } else {
/*  76 */       allowFinalizeInvocation = Boolean.parseBoolean(str2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private static final MethodToHash_Maps methodToHash_Maps = new MethodToHash_Maps();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RemoteObjectInvocationHandler(RemoteRef paramRemoteRef) {
/*  96 */     super(paramRemoteRef);
/*  97 */     if (paramRemoteRef == null) {
/*  98 */       throw new NullPointerException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
/* 166 */     if (!Proxy.isProxyClass(paramObject.getClass())) {
/* 167 */       throw new IllegalArgumentException("not a proxy");
/*     */     }
/*     */     
/* 170 */     if (Proxy.getInvocationHandler(paramObject) != this) {
/* 171 */       throw new IllegalArgumentException("handler mismatch");
/*     */     }
/*     */     
/* 174 */     if (paramMethod.getDeclaringClass() == Object.class)
/* 175 */       return invokeObjectMethod(paramObject, paramMethod, paramArrayOfObject); 
/* 176 */     if ("finalize".equals(paramMethod.getName()) && paramMethod.getParameterCount() == 0 && !allowFinalizeInvocation)
/*     */     {
/* 178 */       return null;
/*     */     }
/* 180 */     return invokeRemoteMethod(paramObject, paramMethod, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object invokeObjectMethod(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) {
/* 191 */     String str = paramMethod.getName();
/*     */     
/* 193 */     if (str.equals("hashCode")) {
/* 194 */       return Integer.valueOf(hashCode());
/*     */     }
/* 196 */     if (str.equals("equals")) {
/* 197 */       Object object = paramArrayOfObject[0];
/*     */       InvocationHandler invocationHandler;
/* 199 */       return 
/* 200 */         Boolean.valueOf((paramObject == object || (object != null && 
/*     */           
/* 202 */           Proxy.isProxyClass(object.getClass()) && 
/* 203 */           invocationHandler = Proxy.getInvocationHandler(object) instanceof RemoteObjectInvocationHandler && 
/* 204 */           equals(invocationHandler))));
/*     */     } 
/* 206 */     if (str.equals("toString")) {
/* 207 */       return proxyToString(paramObject);
/*     */     }
/*     */     
/* 210 */     throw new IllegalArgumentException("unexpected Object method: " + paramMethod);
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
/*     */   private Object invokeRemoteMethod(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Exception {
/*     */     try {
/* 224 */       if (!(paramObject instanceof Remote)) {
/* 225 */         throw new IllegalArgumentException("proxy not Remote instance");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 230 */       Class<?> clazz = paramMethod.getDeclaringClass();
/* 231 */       if (!Remote.class.isAssignableFrom(clazz)) {
/* 232 */         throw new RemoteException("Method is not Remote: " + clazz + "::" + paramMethod);
/*     */       }
/*     */       
/* 235 */       return this.ref.invoke((Remote)paramObject, paramMethod, paramArrayOfObject, 
/* 236 */           getMethodHash(paramMethod));
/* 237 */     } catch (Exception exception) {
/* 238 */       if (!(exception instanceof RuntimeException)) {
/* 239 */         Class<?> clazz1 = paramObject.getClass();
/*     */         try {
/* 241 */           paramMethod = clazz1.getMethod(paramMethod.getName(), paramMethod
/* 242 */               .getParameterTypes());
/* 243 */         } catch (NoSuchMethodException noSuchMethodException) {
/* 244 */           throw (IllegalArgumentException)(new IllegalArgumentException())
/* 245 */             .initCause(noSuchMethodException);
/*     */         } 
/* 247 */         Class<?> clazz2 = exception.getClass();
/* 248 */         for (Class<?> clazz : paramMethod.getExceptionTypes()) {
/* 249 */           if (clazz.isAssignableFrom(clazz2)) {
/* 250 */             throw exception;
/*     */           }
/*     */         } 
/* 253 */         exception = new UnexpectedException("unexpected exception", exception);
/*     */       } 
/* 255 */       throw exception;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String proxyToString(Object paramObject) {
/* 264 */     Class[] arrayOfClass = paramObject.getClass().getInterfaces();
/* 265 */     if (arrayOfClass.length == 0) {
/* 266 */       return "Proxy[" + this + "]";
/*     */     }
/* 268 */     String str = arrayOfClass[0].getName();
/* 269 */     if (str.equals("java.rmi.Remote") && arrayOfClass.length > 1) {
/* 270 */       str = arrayOfClass[1].getName();
/*     */     }
/* 272 */     int i = str.lastIndexOf('.');
/* 273 */     if (i >= 0) {
/* 274 */       str = str.substring(i + 1);
/*     */     }
/* 276 */     return "Proxy[" + str + "," + this + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObjectNoData() throws InvalidObjectException {
/* 283 */     throw new InvalidObjectException("no data in stream; class: " + 
/* 284 */         getClass().getName());
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
/*     */   private static long getMethodHash(Method paramMethod) {
/* 298 */     return ((Long)methodToHash_Maps.get(paramMethod.getDeclaringClass()).get(paramMethod)).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MethodToHash_Maps
/*     */     extends WeakClassHashMap<Map<Method, Long>>
/*     */   {
/*     */     protected Map<Method, Long> computeValue(Class<?> param1Class) {
/* 311 */       return new WeakHashMap<Method, Long>() {
/*     */           public synchronized Long get(Object param2Object) {
/* 313 */             Long long_ = super.get(param2Object);
/* 314 */             if (long_ == null) {
/* 315 */               Method method = (Method)param2Object;
/* 316 */               long_ = Long.valueOf(Util.computeMethodHash(method));
/* 317 */               put(method, long_);
/*     */             } 
/* 319 */             return long_;
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/server/RemoteObjectInvocationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */