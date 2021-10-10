/*    */ package com.sun.corba.se.impl.ior;
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
/*    */ public class ObjectAdapterIdNumber
/*    */   extends ObjectAdapterIdArray
/*    */ {
/*    */   private int poaid;
/*    */   
/*    */   public ObjectAdapterIdNumber(int paramInt) {
/* 42 */     super("OldRootPOA", Integer.toString(paramInt));
/* 43 */     this.poaid = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOldPOAId() {
/* 48 */     return this.poaid;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/ObjectAdapterIdNumber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */