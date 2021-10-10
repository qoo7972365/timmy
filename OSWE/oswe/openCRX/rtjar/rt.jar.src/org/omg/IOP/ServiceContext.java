/*    */ package org.omg.IOP;
/*    */ 
/*    */ import org.omg.CORBA.portable.IDLEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServiceContext
/*    */   implements IDLEntity
/*    */ {
/* 15 */   public int context_id = 0;
/*    */ 
/*    */   
/* 18 */   public byte[] context_data = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServiceContext() {}
/*    */ 
/*    */   
/*    */   public ServiceContext(int paramInt, byte[] paramArrayOfbyte) {
/* 26 */     this.context_id = paramInt;
/* 27 */     this.context_data = paramArrayOfbyte;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/ServiceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */