/*    */ package org.omg.DynamicAny;
/*    */ 
/*    */ import org.omg.CORBA.Any;
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
/*    */ public final class NameValuePair
/*    */   implements IDLEntity
/*    */ {
/* 17 */   public String id = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   public Any value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public NameValuePair() {}
/*    */ 
/*    */   
/*    */   public NameValuePair(String paramString, Any paramAny) {
/* 30 */     this.id = paramString;
/* 31 */     this.value = paramAny;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/NameValuePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */