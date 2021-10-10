/*    */ package org.omg.CosNaming;
/*    */ 
/*    */ import org.omg.CORBA.portable.IDLEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NameComponent
/*    */   implements IDLEntity
/*    */ {
/* 13 */   public String id = null;
/* 14 */   public String kind = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public NameComponent() {}
/*    */ 
/*    */   
/*    */   public NameComponent(String paramString1, String paramString2) {
/* 22 */     this.id = paramString1;
/* 23 */     this.kind = paramString2;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/NameComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */