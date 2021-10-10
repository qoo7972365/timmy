/*    */ package com.sun.corba.se.impl.util;
/*    */ 
/*    */ import java.util.EmptyStackException;
/*    */ import java.util.Stack;
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
/*    */ class RepositoryIdPool
/*    */   extends Stack
/*    */ {
/* 42 */   private static int MAX_CACHE_SIZE = 4;
/*    */   
/*    */   private RepositoryIdCache cache;
/*    */   
/*    */   public final synchronized RepositoryId popId() {
/*    */     try {
/* 48 */       return (RepositoryId)pop();
/*    */     }
/* 50 */     catch (EmptyStackException emptyStackException) {
/* 51 */       increasePool(5);
/* 52 */       return (RepositoryId)pop();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   final void increasePool(int paramInt) {
/* 60 */     for (int i = paramInt; i > 0; i--) {
/* 61 */       push((E)new RepositoryId());
/*    */     }
/*    */   }
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
/*    */   final void setCaches(RepositoryIdCache paramRepositoryIdCache) {
/* 82 */     this.cache = paramRepositoryIdCache;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/util/RepositoryIdPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */