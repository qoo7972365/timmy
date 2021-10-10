/*    */ package org.omg.IOP;
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
/*    */ public final class TaggedComponent
/*    */   implements IDLEntity
/*    */ {
/* 15 */   public int tag = 0;
/*    */ 
/*    */   
/* 18 */   public byte[] component_data = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public TaggedComponent() {}
/*    */ 
/*    */   
/*    */   public TaggedComponent(int paramInt, byte[] paramArrayOfbyte) {
/* 26 */     this.tag = paramInt;
/* 27 */     this.component_data = paramArrayOfbyte;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/TaggedComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */