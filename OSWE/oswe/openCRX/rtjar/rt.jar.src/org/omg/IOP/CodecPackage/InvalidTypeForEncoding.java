/*    */ package org.omg.IOP.CodecPackage;
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
/*    */ public final class InvalidTypeForEncoding
/*    */   extends UserException
/*    */ {
/*    */   public InvalidTypeForEncoding() {
/* 16 */     super(InvalidTypeForEncodingHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidTypeForEncoding(String paramString) {
/* 22 */     super(InvalidTypeForEncodingHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/CodecPackage/InvalidTypeForEncoding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */