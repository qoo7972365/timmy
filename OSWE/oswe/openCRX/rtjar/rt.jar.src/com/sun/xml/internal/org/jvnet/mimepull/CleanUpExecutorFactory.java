/*    */ package com.sun.xml.internal.org.jvnet.mimepull;
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
/*    */ public abstract class CleanUpExecutorFactory
/*    */ {
/* 31 */   private static final String DEFAULT_PROPERTY_NAME = CleanUpExecutorFactory.class
/* 32 */     .getName();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CleanUpExecutorFactory newInstance() {
/*    */     try {
/* 39 */       return (CleanUpExecutorFactory)FactoryFinder.find(DEFAULT_PROPERTY_NAME);
/* 40 */     } catch (Exception e) {
/* 41 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract Executor getExecutor();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/CleanUpExecutorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */