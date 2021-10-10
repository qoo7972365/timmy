/*    */ package com.sun.security.jgss;
/*    */ 
/*    */ import jdk.Exported;
/*    */ import sun.misc.HexDumpEncoder;
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
/*    */ 
/*    */ 
/*    */ @Exported
/*    */ public final class AuthorizationDataEntry
/*    */ {
/*    */   private final int type;
/*    */   private final byte[] data;
/*    */   
/*    */   public AuthorizationDataEntry(int paramInt, byte[] paramArrayOfbyte) {
/* 44 */     this.type = paramInt;
/* 45 */     this.data = (byte[])paramArrayOfbyte.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getType() {
/* 53 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getData() {
/* 61 */     return (byte[])this.data.clone();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     return "AuthorizationDataEntry: type=" + this.type + ", data=" + this.data.length + " bytes:\n" + (new HexDumpEncoder())
/*    */       
/* 67 */       .encodeBuffer(this.data);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/jgss/AuthorizationDataEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */