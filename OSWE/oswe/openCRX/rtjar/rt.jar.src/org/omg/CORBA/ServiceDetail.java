/*    */ package org.omg.CORBA;
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
/*    */ 
/*    */ public final class ServiceDetail
/*    */   implements IDLEntity
/*    */ {
/*    */   public int service_detail_type;
/*    */   public byte[] service_detail;
/*    */   
/*    */   public ServiceDetail() {}
/*    */   
/*    */   public ServiceDetail(int paramInt, byte[] paramArrayOfbyte) {
/* 65 */     this.service_detail_type = paramInt;
/* 66 */     this.service_detail = paramArrayOfbyte;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/ServiceDetail.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */