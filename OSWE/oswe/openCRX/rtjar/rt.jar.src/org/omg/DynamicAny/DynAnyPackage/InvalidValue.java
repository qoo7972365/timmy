/*    */ package org.omg.DynamicAny.DynAnyPackage;
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
/*    */ public final class InvalidValue
/*    */   extends UserException
/*    */ {
/*    */   public InvalidValue() {
/* 16 */     super(InvalidValueHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidValue(String paramString) {
/* 22 */     super(InvalidValueHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/DynAnyPackage/InvalidValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */