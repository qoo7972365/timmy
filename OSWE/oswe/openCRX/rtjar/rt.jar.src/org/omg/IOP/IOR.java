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
/*    */ public final class IOR
/*    */   implements IDLEntity
/*    */ {
/* 15 */   public String type_id = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public TaggedProfile[] profiles = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public IOR() {}
/*    */ 
/*    */   
/*    */   public IOR(String paramString, TaggedProfile[] paramArrayOfTaggedProfile) {
/* 29 */     this.type_id = paramString;
/* 30 */     this.profiles = paramArrayOfTaggedProfile;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/IOR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */