/*    */ package com.sun.corba.se.impl.corba;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.ORB;
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
/*    */ 
/*    */ public class AsynchInvoke
/*    */   implements Runnable
/*    */ {
/*    */   private RequestImpl _req;
/*    */   private ORB _orb;
/*    */   private boolean _notifyORB;
/*    */   
/*    */   public AsynchInvoke(ORB paramORB, RequestImpl paramRequestImpl, boolean paramBoolean) {
/* 55 */     this._orb = paramORB;
/* 56 */     this._req = paramRequestImpl;
/* 57 */     this._notifyORB = paramBoolean;
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
/*    */   public void run() {
/* 72 */     this._req.doInvocation();
/*    */ 
/*    */ 
/*    */     
/* 76 */     synchronized (this._req) {
/*    */ 
/*    */       
/* 79 */       this._req.gotResponse = true;
/*    */ 
/*    */       
/* 82 */       this._req.notify();
/*    */     } 
/*    */     
/* 85 */     if (this._notifyORB == true)
/* 86 */       this._orb.notifyORB(); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/corba/AsynchInvoke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */