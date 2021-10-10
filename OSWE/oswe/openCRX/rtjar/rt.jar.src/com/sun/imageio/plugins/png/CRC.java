/*    */ package com.sun.imageio.plugins.png;
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
/*    */ class CRC
/*    */ {
/* 56 */   private static int[] crcTable = new int[256];
/* 57 */   private int crc = -1;
/*    */ 
/*    */   
/*    */   static {
/* 61 */     for (byte b = 0; b < 'Ā'; b++) {
/* 62 */       int i = b;
/* 63 */       for (byte b1 = 0; b1 < 8; b1++) {
/* 64 */         if ((i & 0x1) == 1) {
/* 65 */           i = 0xEDB88320 ^ i >>> 1;
/*    */         } else {
/* 67 */           i >>>= 1;
/*    */         } 
/*    */         
/* 70 */         crcTable[b] = i;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 78 */     this.crc = -1;
/*    */   }
/*    */   
/*    */   public void update(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 82 */     for (byte b = 0; b < paramInt2; b++) {
/* 83 */       this.crc = crcTable[(this.crc ^ paramArrayOfbyte[paramInt1 + b]) & 0xFF] ^ this.crc >>> 8;
/*    */     }
/*    */   }
/*    */   
/*    */   public void update(int paramInt) {
/* 88 */     this.crc = crcTable[(this.crc ^ paramInt) & 0xFF] ^ this.crc >>> 8;
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 92 */     return this.crc ^ 0xFFFFFFFF;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/png/CRC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */