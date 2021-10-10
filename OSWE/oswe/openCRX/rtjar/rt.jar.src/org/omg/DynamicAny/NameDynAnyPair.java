/*    */ package org.omg.DynamicAny;
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
/*    */ public final class NameDynAnyPair
/*    */   implements IDLEntity
/*    */ {
/* 17 */   public String id = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   public DynAny value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public NameDynAnyPair() {}
/*    */ 
/*    */   
/*    */   public NameDynAnyPair(String paramString, DynAny paramDynAny) {
/* 30 */     this.id = paramString;
/* 31 */     this.value = paramDynAny;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/NameDynAnyPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */