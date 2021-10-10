/*    */ package org.omg.PortableServer;
/*    */ 
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ForwardRequest
/*    */   extends UserException
/*    */ {
/* 13 */   public Object forward_reference = null;
/*    */ 
/*    */   
/*    */   public ForwardRequest() {
/* 17 */     super(ForwardRequestHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public ForwardRequest(Object paramObject) {
/* 22 */     super(ForwardRequestHelper.id());
/* 23 */     this.forward_reference = paramObject;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ForwardRequest(String paramString, Object paramObject) {
/* 29 */     super(ForwardRequestHelper.id() + "  " + paramString);
/* 30 */     this.forward_reference = paramObject;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/ForwardRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */