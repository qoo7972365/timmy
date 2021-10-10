/*    */ package com.sun.xml.internal.ws.server;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.server.AbstractInstanceResolver;
/*    */ import com.sun.xml.internal.ws.api.server.ResourceInjector;
/*    */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*    */ import com.sun.xml.internal.ws.api.server.WSWebServiceContext;
/*    */ import java.lang.reflect.Method;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.PreDestroy;
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
/*    */ public abstract class AbstractMultiInstanceResolver<T>
/*    */   extends AbstractInstanceResolver<T>
/*    */ {
/*    */   protected final Class<T> clazz;
/*    */   private WSWebServiceContext webServiceContext;
/*    */   protected WSEndpoint owner;
/*    */   private final Method postConstructMethod;
/*    */   private final Method preDestroyMethod;
/*    */   private ResourceInjector resourceInjector;
/*    */   
/*    */   public AbstractMultiInstanceResolver(Class<T> clazz) {
/* 55 */     this.clazz = clazz;
/*    */     
/* 57 */     this.postConstructMethod = findAnnotatedMethod(clazz, PostConstruct.class);
/* 58 */     this.preDestroyMethod = findAnnotatedMethod(clazz, PreDestroy.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final void prepare(T t) {
/* 66 */     assert this.webServiceContext != null;
/*    */     
/* 68 */     this.resourceInjector.inject(this.webServiceContext, t);
/* 69 */     invokeMethod(this.postConstructMethod, t, new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final T create() {
/* 76 */     T t = (T)createNewInstance(this.clazz);
/* 77 */     prepare(t);
/* 78 */     return t;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start(WSWebServiceContext wsc, WSEndpoint endpoint) {
/* 83 */     this.resourceInjector = getResourceInjector(endpoint);
/* 84 */     this.webServiceContext = wsc;
/* 85 */     this.owner = endpoint;
/*    */   }
/*    */   
/*    */   protected final void dispose(T instance) {
/* 89 */     invokeMethod(this.preDestroyMethod, instance, new Object[0]);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/AbstractMultiInstanceResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */