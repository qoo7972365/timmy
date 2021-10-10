/*    */ package com.sun.corba.se.spi.extension;
/*    */ 
/*    */ import org.omg.CORBA.LocalObject;
/*    */ import org.omg.CORBA.Policy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZeroPortPolicy
/*    */   extends LocalObject
/*    */   implements Policy
/*    */ {
/* 36 */   private static ZeroPortPolicy policy = new ZeroPortPolicy(true);
/*    */   
/*    */   private boolean flag = true;
/*    */ 
/*    */   
/*    */   private ZeroPortPolicy(boolean paramBoolean) {
/* 42 */     this.flag = paramBoolean;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 47 */     return "ZeroPortPolicy[" + this.flag + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean forceZeroPort() {
/* 52 */     return this.flag;
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized ZeroPortPolicy getPolicy() {
/* 57 */     return policy;
/*    */   }
/*    */ 
/*    */   
/*    */   public int policy_type() {
/* 62 */     return 1398079489;
/*    */   }
/*    */ 
/*    */   
/*    */   public Policy copy() {
/* 67 */     return this;
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/extension/ZeroPortPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */