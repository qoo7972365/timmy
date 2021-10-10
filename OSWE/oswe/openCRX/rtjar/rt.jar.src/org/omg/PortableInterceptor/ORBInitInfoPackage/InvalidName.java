/*    */ package org.omg.PortableInterceptor.ORBInitInfoPackage;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class InvalidName
/*    */   extends UserException
/*    */ {
/*    */   public InvalidName() {
/* 16 */     super(InvalidNameHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidName(String paramString) {
/* 22 */     super(InvalidNameHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/ORBInitInfoPackage/InvalidName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */