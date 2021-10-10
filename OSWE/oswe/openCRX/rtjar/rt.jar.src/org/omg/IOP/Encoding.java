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
/*    */ 
/*    */ 
/*    */ public final class Encoding
/*    */   implements IDLEntity
/*    */ {
/* 17 */   public short format = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   public byte major_version = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public byte minor_version = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   public Encoding() {}
/*    */ 
/*    */   
/*    */   public Encoding(short paramShort, byte paramByte1, byte paramByte2) {
/* 35 */     this.format = paramShort;
/* 36 */     this.major_version = paramByte1;
/* 37 */     this.minor_version = paramByte2;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/Encoding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */