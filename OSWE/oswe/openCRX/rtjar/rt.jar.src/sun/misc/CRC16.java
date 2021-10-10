/*    */ package sun.misc;
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
/*    */ public class CRC16
/*    */ {
/* 40 */   public int value = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(byte paramByte) {
/* 47 */     int i = paramByte;
/* 48 */     for (byte b = 7; b >= 0; b--) {
/* 49 */       i <<= 1;
/* 50 */       int j = i >>> 8 & 0x1;
/* 51 */       if ((this.value & 0x8000) != 0) {
/* 52 */         this.value = (this.value << 1) + j ^ 0x1021;
/*    */       } else {
/* 54 */         this.value = (this.value << 1) + j;
/*    */       } 
/*    */     } 
/* 57 */     this.value &= 0xFFFF;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 63 */     this.value = 0;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/CRC16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */