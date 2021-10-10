/*     */ package com.sun.xml.internal.ws.api.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*     */ import com.sun.xml.internal.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.internal.ws.server.ServerRtException;
/*     */ import com.sun.xml.internal.ws.server.SingletonResolver;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.Provider;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InstanceResolver<T>
/*     */ {
/*     */   @NotNull
/*     */   public abstract T resolve(@NotNull Packet paramPacket);
/*     */   
/*     */   public void postInvoke(@NotNull Packet request, @NotNull T servant) {}
/*     */   
/*     */   public void start(@NotNull WSWebServiceContext wsc, @NotNull WSEndpoint endpoint) {
/* 120 */     start(wsc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(@NotNull WebServiceContext wsc) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> InstanceResolver<T> createSingleton(T singleton) {
/*     */     SingletonResolver singletonResolver;
/* 146 */     assert singleton != null;
/* 147 */     InstanceResolver<?> ir = createFromInstanceResolverAnnotation(singleton.getClass());
/* 148 */     if (ir == null)
/* 149 */       singletonResolver = new SingletonResolver(singleton); 
/* 150 */     return (InstanceResolver<T>)singletonResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> InstanceResolver<T> createDefault(@NotNull Class<T> clazz, boolean bool) {
/* 160 */     return createDefault(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> InstanceResolver<T> createDefault(@NotNull Class<T> clazz) {
/*     */     SingletonResolver singletonResolver;
/* 167 */     InstanceResolver<T> ir = createFromInstanceResolverAnnotation(clazz);
/* 168 */     if (ir == null)
/* 169 */       singletonResolver = new SingletonResolver(createNewInstance(clazz)); 
/* 170 */     return (InstanceResolver<T>)singletonResolver;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> InstanceResolver<T> createFromInstanceResolverAnnotation(@NotNull Class<T> clazz) {
/*     */     Annotation[] arrayOfAnnotation;
/*     */     int i;
/*     */     byte b;
/* 178 */     for (arrayOfAnnotation = clazz.getAnnotations(), i = arrayOfAnnotation.length, b = 0; b < i; ) { Annotation a = arrayOfAnnotation[b];
/* 179 */       InstanceResolverAnnotation ira = a.annotationType().<InstanceResolverAnnotation>getAnnotation(InstanceResolverAnnotation.class);
/* 180 */       if (ira == null) { b++; continue; }
/* 181 */        Class<? extends InstanceResolver> ir = ira.value();
/*     */       try {
/* 183 */         return ir.getConstructor(new Class[] { Class.class }).newInstance(new Object[] { clazz });
/* 184 */       } catch (InstantiationException e) {
/* 185 */         throw new WebServiceException(ServerMessages.FAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(ir
/* 186 */               .getName(), a.annotationType(), clazz.getName()));
/* 187 */       } catch (IllegalAccessException e) {
/* 188 */         throw new WebServiceException(ServerMessages.FAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(ir
/* 189 */               .getName(), a.annotationType(), clazz.getName()));
/* 190 */       } catch (InvocationTargetException e) {
/* 191 */         throw new WebServiceException(ServerMessages.FAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(ir
/* 192 */               .getName(), a.annotationType(), clazz.getName()));
/* 193 */       } catch (NoSuchMethodException e) {
/* 194 */         throw new WebServiceException(ServerMessages.FAILED_TO_INSTANTIATE_INSTANCE_RESOLVER(ir
/* 195 */               .getName(), a.annotationType(), clazz.getName()));
/*     */       }  }
/*     */ 
/*     */     
/* 199 */     return null;
/*     */   }
/*     */   
/*     */   protected static <T> T createNewInstance(Class<T> cl) {
/*     */     try {
/* 204 */       return cl.newInstance();
/* 205 */     } catch (InstantiationException e) {
/* 206 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 207 */       throw new ServerRtException(
/* 208 */           WsservletMessages.ERROR_IMPLEMENTOR_FACTORY_NEW_INSTANCE_FAILED(cl), new Object[0]);
/* 209 */     } catch (IllegalAccessException e) {
/* 210 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 211 */       throw new ServerRtException(
/* 212 */           WsservletMessages.ERROR_IMPLEMENTOR_FACTORY_NEW_INSTANCE_FAILED(cl), new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Invoker createInvoker() {
/* 220 */     return new Invoker()
/*     */       {
/*     */         public void start(@NotNull WSWebServiceContext wsc, @NotNull WSEndpoint endpoint) {
/* 223 */           InstanceResolver.this.start(wsc, endpoint);
/*     */         }
/*     */ 
/*     */         
/*     */         public void dispose() {
/* 228 */           InstanceResolver.this.dispose();
/*     */         }
/*     */ 
/*     */         
/*     */         public Object invoke(Packet p, Method m, Object... args) throws InvocationTargetException, IllegalAccessException {
/* 233 */           T t = InstanceResolver.this.resolve(p);
/*     */           try {
/* 235 */             return MethodUtil.invoke(t, m, args);
/*     */           } finally {
/* 237 */             InstanceResolver.this.postInvoke(p, t);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public <U> U invokeProvider(@NotNull Packet p, U arg) {
/* 243 */           T t = InstanceResolver.this.resolve(p);
/*     */           try {
/* 245 */             return (U)((Provider)t).invoke(arg);
/*     */           } finally {
/* 247 */             InstanceResolver.this.postInvoke(p, t);
/*     */           } 
/*     */         }
/*     */         
/*     */         public String toString() {
/* 252 */           return "Default Invoker over " + InstanceResolver.this.toString();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/* 258 */   private static final Logger logger = Logger.getLogger("com.sun.xml.internal.ws.server");
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/InstanceResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */