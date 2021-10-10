/*    */ package org.omg.DynamicAny.DynAnyFactoryPackage;
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
/*    */ public final class InconsistentTypeCode
/*    */   extends UserException
/*    */ {
/*    */   public InconsistentTypeCode() {
/* 16 */     super(InconsistentTypeCodeHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InconsistentTypeCode(String paramString) {
/* 22 */     super(InconsistentTypeCodeHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/DynAnyFactoryPackage/InconsistentTypeCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */