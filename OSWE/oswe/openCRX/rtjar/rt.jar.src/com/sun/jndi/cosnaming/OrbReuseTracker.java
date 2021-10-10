/*    */ package com.sun.jndi.cosnaming;
/*    */ 
/*    */ import org.omg.CORBA.ORB;
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
/*    */ class OrbReuseTracker
/*    */ {
/*    */   int referenceCnt;
/*    */   ORB orb;
/*    */   private static final boolean debug = false;
/*    */   
/*    */   OrbReuseTracker(ORB paramORB) {
/* 44 */     this.orb = paramORB;
/* 45 */     this.referenceCnt++;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   synchronized void incRefCount() {
/* 52 */     this.referenceCnt++;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   synchronized void decRefCount() {
/* 59 */     this.referenceCnt--;
/*    */ 
/*    */ 
/*    */     
/* 63 */     if (this.referenceCnt == 0)
/*    */     {
/*    */ 
/*    */       
/* 67 */       this.orb.destroy();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/cosnaming/OrbReuseTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */