/*    */ package org.omg.PortableServer.POAPackage;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class InvalidPolicy
/*    */   extends UserException
/*    */ {
/* 13 */   public short index = 0;
/*    */ 
/*    */   
/*    */   public InvalidPolicy() {
/* 17 */     super(InvalidPolicyHelper.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public InvalidPolicy(short paramShort) {
/* 22 */     super(InvalidPolicyHelper.id());
/* 23 */     this.index = paramShort;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidPolicy(String paramString, short paramShort) {
/* 29 */     super(InvalidPolicyHelper.id() + "  " + paramString);
/* 30 */     this.index = paramShort;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/POAPackage/InvalidPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */