/*    */ package sun.font;
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
/*    */ class NativeStrikeDisposer
/*    */   extends FontStrikeDisposer
/*    */ {
/*    */   long pNativeScalerContext;
/*    */   
/*    */   public NativeStrikeDisposer(Font2D paramFont2D, FontStrikeDesc paramFontStrikeDesc, long paramLong, int[] paramArrayOfint) {
/* 56 */     super(paramFont2D, paramFontStrikeDesc, 0L, paramArrayOfint);
/* 57 */     this.pNativeScalerContext = paramLong;
/*    */   }
/*    */ 
/*    */   
/*    */   public NativeStrikeDisposer(Font2D paramFont2D, FontStrikeDesc paramFontStrikeDesc, long paramLong, long[] paramArrayOflong) {
/* 62 */     super(paramFont2D, paramFontStrikeDesc, 0L, paramArrayOflong);
/* 63 */     this.pNativeScalerContext = paramLong;
/*    */   }
/*    */ 
/*    */   
/*    */   public NativeStrikeDisposer(Font2D paramFont2D, FontStrikeDesc paramFontStrikeDesc, long paramLong) {
/* 68 */     super(paramFont2D, paramFontStrikeDesc, 0L);
/* 69 */     this.pNativeScalerContext = paramLong;
/*    */   }
/*    */   
/*    */   public NativeStrikeDisposer(Font2D paramFont2D, FontStrikeDesc paramFontStrikeDesc) {
/* 73 */     super(paramFont2D, paramFontStrikeDesc);
/*    */   }
/*    */   
/*    */   public synchronized void dispose() {
/* 77 */     if (!this.disposed) {
/* 78 */       if (this.pNativeScalerContext != 0L) {
/* 79 */         freeNativeScalerContext(this.pNativeScalerContext);
/*    */       }
/* 81 */       super.dispose();
/*    */     } 
/*    */   }
/*    */   
/*    */   private native void freeNativeScalerContext(long paramLong);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/NativeStrikeDisposer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */