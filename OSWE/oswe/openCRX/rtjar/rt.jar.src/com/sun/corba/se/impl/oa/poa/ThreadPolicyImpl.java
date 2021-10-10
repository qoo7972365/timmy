/*    */ package com.sun.corba.se.impl.oa.poa;
/*    */ 
/*    */ import org.omg.CORBA.LocalObject;
/*    */ import org.omg.CORBA.Policy;
/*    */ import org.omg.PortableServer.ThreadPolicy;
/*    */ import org.omg.PortableServer.ThreadPolicyValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ThreadPolicyImpl
/*    */   extends LocalObject
/*    */   implements ThreadPolicy
/*    */ {
/*    */   private ThreadPolicyValue value;
/*    */   
/*    */   public ThreadPolicyImpl(ThreadPolicyValue paramThreadPolicyValue) {
/* 35 */     this.value = paramThreadPolicyValue;
/*    */   }
/*    */   
/*    */   public ThreadPolicyValue value() {
/* 39 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int policy_type() {
/* 44 */     return 16;
/*    */   }
/*    */   
/*    */   public Policy copy() {
/* 48 */     return (Policy)new ThreadPolicyImpl(this.value);
/*    */   }
/*    */   
/*    */   public void destroy() {
/* 52 */     this.value = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return "ThreadPolicy[" + (
/* 60 */       (this.value.value() == 1) ? "SINGLE_THREAD_MODEL" : "ORB_CTRL_MODEL]");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/ThreadPolicyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */