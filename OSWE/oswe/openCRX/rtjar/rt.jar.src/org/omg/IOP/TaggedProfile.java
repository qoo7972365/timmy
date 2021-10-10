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
/*    */ public final class TaggedProfile
/*    */   implements IDLEntity
/*    */ {
/* 15 */   public int tag = 0;
/*    */ 
/*    */   
/* 18 */   public byte[] profile_data = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public TaggedProfile() {}
/*    */ 
/*    */   
/*    */   public TaggedProfile(int paramInt, byte[] paramArrayOfbyte) {
/* 26 */     this.tag = paramInt;
/* 27 */     this.profile_data = paramArrayOfbyte;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/TaggedProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */