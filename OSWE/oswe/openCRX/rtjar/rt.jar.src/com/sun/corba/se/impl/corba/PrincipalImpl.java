/*    */ package com.sun.corba.se.impl.corba;
/*    */ 
/*    */ import org.omg.CORBA.Principal;
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
/*    */ public class PrincipalImpl
/*    */   extends Principal
/*    */ {
/*    */   private byte[] value;
/*    */   
/*    */   public void name(byte[] paramArrayOfbyte) {
/* 41 */     this.value = paramArrayOfbyte;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] name() {
/* 46 */     return this.value;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/corba/PrincipalImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */