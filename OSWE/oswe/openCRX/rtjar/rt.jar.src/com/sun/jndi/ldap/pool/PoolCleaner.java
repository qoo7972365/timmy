/*    */ package com.sun.jndi.ldap.pool;
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
/*    */ public final class PoolCleaner
/*    */   implements Runnable
/*    */ {
/*    */   private final Pool[] pools;
/*    */   private final long period;
/*    */   
/*    */   public PoolCleaner(long paramLong, Pool[] paramArrayOfPool) {
/* 43 */     this.period = paramLong;
/* 44 */     this.pools = (Pool[])paramArrayOfPool.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     while (true) {
/* 51 */       synchronized (this) {
/*    */         
/*    */         try {
/* 54 */           wait(this.period);
/* 55 */         } catch (InterruptedException interruptedException) {}
/*    */ 
/*    */ 
/*    */         
/* 59 */         long l = System.currentTimeMillis() - this.period;
/* 60 */         for (byte b = 0; b < this.pools.length; b++) {
/* 61 */           if (this.pools[b] != null)
/* 62 */             this.pools[b].expire(l); 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/pool/PoolCleaner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */