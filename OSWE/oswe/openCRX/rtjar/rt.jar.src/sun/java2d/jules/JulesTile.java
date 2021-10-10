/*    */ package sun.java2d.jules;
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
/*    */ public class JulesTile
/*    */ {
/*    */   byte[] imgBuffer;
/* 30 */   long pixmanImgPtr = 0L;
/*    */ 
/*    */   
/*    */   int tilePos;
/*    */ 
/*    */   
/*    */   public byte[] getImgBuffer() {
/* 37 */     if (this.imgBuffer == null) {
/* 38 */       this.imgBuffer = new byte[1024];
/*    */     }
/*    */     
/* 41 */     return this.imgBuffer;
/*    */   }
/*    */   
/*    */   public long getPixmanImgPtr() {
/* 45 */     return this.pixmanImgPtr;
/*    */   }
/*    */   
/*    */   public void setPixmanImgPtr(long paramLong) {
/* 49 */     this.pixmanImgPtr = paramLong;
/*    */   }
/*    */   
/*    */   public boolean hasBuffer() {
/* 53 */     return (this.imgBuffer != null);
/*    */   }
/*    */   
/*    */   public int getTilePos() {
/* 57 */     return this.tilePos;
/*    */   }
/*    */   
/*    */   public void setTilePos(int paramInt) {
/* 61 */     this.tilePos = paramInt;
/*    */   }
/*    */   
/*    */   public void setImgBuffer(byte[] paramArrayOfbyte) {
/* 65 */     this.imgBuffer = paramArrayOfbyte;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/jules/JulesTile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */