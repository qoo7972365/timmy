/*    */ package com.sun.xml.internal.ws.api.server;
/*    */ 
/*    */ import com.sun.istack.internal.NotNull;
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
/*    */ public abstract class ContainerResolver
/*    */ {
/* 47 */   private static final ThreadLocalContainerResolver DEFAULT = new ThreadLocalContainerResolver();
/*    */   
/* 49 */   private static volatile ContainerResolver theResolver = DEFAULT;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setInstance(ContainerResolver resolver) {
/* 58 */     if (resolver == null)
/* 59 */       resolver = DEFAULT; 
/* 60 */     theResolver = resolver;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static ContainerResolver getInstance() {
/* 69 */     return theResolver;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ThreadLocalContainerResolver getDefault() {
/* 78 */     return DEFAULT;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public abstract Container getContainer();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/ContainerResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */