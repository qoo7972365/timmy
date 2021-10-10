/*    */ package org.omg.PortableInterceptor;
/*    */ 
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ForwardRequest
/*    */   extends UserException
/*    */ {
/* 17 */   public Object forward = null;
/*    */ 
/*    */   
/*    */   public ForwardRequest() {
/* 21 */     super(ForwardRequestHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public ForwardRequest(Object paramObject) {
/* 26 */     super(ForwardRequestHelper.id());
/* 27 */     this.forward = paramObject;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ForwardRequest(String paramString, Object paramObject) {
/* 33 */     super(ForwardRequestHelper.id() + "  " + paramString);
/* 34 */     this.forward = paramObject;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/ForwardRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */