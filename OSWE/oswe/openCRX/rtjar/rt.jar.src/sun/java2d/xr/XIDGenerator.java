/*    */ package sun.java2d.xr;
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
/*    */ public class XIDGenerator
/*    */ {
/*    */   private static final int XID_BUFFER_SIZE = 512;
/* 39 */   int[] xidBuffer = new int[512];
/* 40 */   int currentIndex = 512;
/*    */ 
/*    */   
/*    */   public int getNextXID() {
/* 44 */     if (this.currentIndex >= 512) {
/* 45 */       bufferXIDs(this.xidBuffer, this.xidBuffer.length);
/* 46 */       this.currentIndex = 0;
/*    */     } 
/*    */     
/* 49 */     return this.xidBuffer[this.currentIndex++];
/*    */   }
/*    */   
/*    */   private static native void bufferXIDs(int[] paramArrayOfint, int paramInt);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XIDGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */