/*    */ package com.sun.xml.internal.ws.api.server;
/*    */ 
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*    */ import com.sun.xml.internal.ws.server.ServerRtException;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractInstanceResolver<T>
/*    */   extends InstanceResolver<T>
/*    */ {
/*    */   protected static ResourceInjector getResourceInjector(WSEndpoint endpoint) {
/* 52 */     ResourceInjector ri = endpoint.getContainer().<ResourceInjector>getSPI(ResourceInjector.class);
/* 53 */     if (ri == null)
/* 54 */       ri = ResourceInjector.STANDALONE; 
/* 55 */     return ri;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static void invokeMethod(@Nullable final Method method, final Object instance, Object... args) {
/* 62 */     if (method == null)
/* 63 */       return;  AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*    */           public Void run() {
/*    */             try {
/* 66 */               if (!method.isAccessible()) {
/* 67 */                 method.setAccessible(true);
/*    */               }
/* 69 */               MethodUtil.invoke(instance, method, args);
/* 70 */             } catch (IllegalAccessException e) {
/* 71 */               throw new ServerRtException("server.rt.err", new Object[] { e });
/* 72 */             } catch (InvocationTargetException e) {
/* 73 */               throw new ServerRtException("server.rt.err", new Object[] { e });
/*    */             } 
/* 75 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected final Method findAnnotatedMethod(Class clazz, Class<? extends Annotation> annType) {
/* 85 */     boolean once = false;
/* 86 */     Method r = null;
/* 87 */     for (Method method : clazz.getDeclaredMethods()) {
/* 88 */       if (method.getAnnotation(annType) != null) {
/* 89 */         if (once)
/* 90 */           throw new ServerRtException(ServerMessages.ANNOTATION_ONLY_ONCE(annType), new Object[0]); 
/* 91 */         if ((method.getParameterTypes()).length != 0)
/* 92 */           throw new ServerRtException(ServerMessages.NOT_ZERO_PARAMETERS(method), new Object[0]); 
/* 93 */         r = method;
/* 94 */         once = true;
/*    */       } 
/*    */     } 
/* 97 */     return r;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/AbstractInstanceResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */