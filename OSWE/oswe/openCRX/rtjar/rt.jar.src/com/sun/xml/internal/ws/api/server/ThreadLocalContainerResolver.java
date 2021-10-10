/*    */ package com.sun.xml.internal.ws.api.server;
/*    */ 
/*    */ import java.util.concurrent.Executor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThreadLocalContainerResolver
/*    */   extends ContainerResolver
/*    */ {
/* 49 */   private ThreadLocal<Container> containerThreadLocal = new ThreadLocal<Container>()
/*    */     {
/*    */       protected Container initialValue() {
/* 52 */         return Container.NONE;
/*    */       }
/*    */     };
/*    */   
/*    */   public Container getContainer() {
/* 57 */     return this.containerThreadLocal.get();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Container enterContainer(Container container) {
/* 66 */     Container old = this.containerThreadLocal.get();
/* 67 */     this.containerThreadLocal.set(container);
/* 68 */     return old;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void exitContainer(Container old) {
/* 76 */     this.containerThreadLocal.set(old);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Executor wrapExecutor(final Container container, final Executor ex) {
/* 86 */     if (ex == null) {
/* 87 */       return null;
/*    */     }
/* 89 */     return new Executor()
/*    */       {
/*    */         public void execute(final Runnable command) {
/* 92 */           ex.execute(new Runnable()
/*    */               {
/*    */                 public void run() {
/* 95 */                   Container old = ThreadLocalContainerResolver.this.enterContainer(container);
/*    */                   try {
/* 97 */                     command.run();
/*    */                   } finally {
/* 99 */                     ThreadLocalContainerResolver.this.exitContainer(old);
/*    */                   } 
/*    */                 }
/*    */               });
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/ThreadLocalContainerResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */