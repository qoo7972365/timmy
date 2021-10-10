/*    */ package com.sun.corba.se.impl.orbutil.concurrent;
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
/*    */ public class SyncUtil
/*    */ {
/*    */   public static void acquire(Sync paramSync) {
/* 39 */     boolean bool = false;
/* 40 */     while (!bool) {
/*    */       try {
/* 42 */         paramSync.acquire();
/* 43 */         bool = true;
/* 44 */       } catch (InterruptedException interruptedException) {
/* 45 */         bool = false;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/concurrent/SyncUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */