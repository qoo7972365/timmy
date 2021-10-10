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
/*    */ 
/*    */ public class CopyObjectPolicy
/*    */   extends LocalObject
/*    */   implements Policy
/*    */ {
/*    */   private final int value;
/*    */   
/*    */   public CopyObjectPolicy(int paramInt) {
/* 40 */     this.value = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 45 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int policy_type() {
/* 50 */     return 1398079490;
/*    */   }
/*    */ 
/*    */   
/*    */   public Policy copy() {
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void destroy() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "CopyObjectPolicy[" + this.value + "]";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/extension/CopyObjectPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */