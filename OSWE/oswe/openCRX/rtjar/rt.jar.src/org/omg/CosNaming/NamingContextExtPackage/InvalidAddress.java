/*    */ package org.omg.CosNaming.NamingContextExtPackage;
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
/*    */ public final class InvalidAddress
/*    */   extends UserException
/*    */ {
/*    */   public InvalidAddress() {
/* 16 */     super(InvalidAddressHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidAddress(String paramString) {
/* 22 */     super(InvalidAddressHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/NamingContextExtPackage/InvalidAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */