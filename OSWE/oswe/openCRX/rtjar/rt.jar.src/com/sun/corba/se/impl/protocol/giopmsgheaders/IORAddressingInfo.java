/*    */ package com.sun.corba.se.impl.protocol.giopmsgheaders;
/*    */ 
/*    */ import org.omg.CORBA.portable.IDLEntity;
/*    */ import org.omg.IOP.IOR;
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
/*    */ public final class IORAddressingInfo
/*    */   implements IDLEntity
/*    */ {
/* 38 */   public int selected_profile_index = 0;
/* 39 */   public IOR ior = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public IORAddressingInfo() {}
/*    */ 
/*    */   
/*    */   public IORAddressingInfo(int paramInt, IOR paramIOR) {
/* 47 */     this.selected_profile_index = paramInt;
/* 48 */     this.ior = paramIOR;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/giopmsgheaders/IORAddressingInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */