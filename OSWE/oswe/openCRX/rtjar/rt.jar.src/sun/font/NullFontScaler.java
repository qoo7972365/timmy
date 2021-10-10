/*    */ package sun.font;
/*    */ 
/*    */ import java.awt.geom.GeneralPath;
/*    */ import java.awt.geom.Point2D;
/*    */ import java.awt.geom.Rectangle2D;
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
/*    */ class NullFontScaler
/*    */   extends FontScaler
/*    */ {
/*    */   NullFontScaler() {}
/*    */   
/*    */   public NullFontScaler(Font2D paramFont2D, int paramInt1, boolean paramBoolean, int paramInt2) {}
/*    */   
/*    */   StrikeMetrics getFontMetrics(long paramLong) {
/* 39 */     return new StrikeMetrics(240.0F, 240.0F, 240.0F, 240.0F, 240.0F, 240.0F, 240.0F, 240.0F, 240.0F, 240.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   float getGlyphAdvance(long paramLong, int paramInt) {
/* 44 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   void getGlyphMetrics(long paramLong, int paramInt, Point2D.Float paramFloat) {
/* 49 */     paramFloat.x = 0.0F;
/* 50 */     paramFloat.y = 0.0F;
/*    */   }
/*    */   
/*    */   Rectangle2D.Float getGlyphOutlineBounds(long paramLong, int paramInt) {
/* 54 */     return new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   GeneralPath getGlyphOutline(long paramLong, int paramInt, float paramFloat1, float paramFloat2) {
/* 59 */     return new GeneralPath();
/*    */   }
/*    */ 
/*    */   
/*    */   GeneralPath getGlyphVectorOutline(long paramLong, int[] paramArrayOfint, int paramInt, float paramFloat1, float paramFloat2) {
/* 64 */     return new GeneralPath();
/*    */   }
/*    */   long getLayoutTableCache() {
/* 67 */     return 0L;
/*    */   }
/*    */   
/*    */   long createScalerContext(double[] paramArrayOfdouble, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, boolean paramBoolean) {
/* 71 */     return getNullScalerContext();
/*    */   }
/*    */ 
/*    */   
/*    */   void invalidateScalerContext(long paramLong) {}
/*    */ 
/*    */   
/*    */   int getNumGlyphs() throws FontScalerException {
/* 79 */     return 1;
/*    */   }
/*    */   
/*    */   int getMissingGlyphCode() throws FontScalerException {
/* 83 */     return 0;
/*    */   }
/*    */   
/*    */   int getGlyphCode(char paramChar) throws FontScalerException {
/* 87 */     return 0;
/*    */   }
/*    */   
/*    */   long getUnitsPerEm() {
/* 91 */     return 2048L;
/*    */   }
/*    */ 
/*    */   
/*    */   Point2D.Float getGlyphPoint(long paramLong, int paramInt1, int paramInt2) {
/* 96 */     return null;
/*    */   }
/*    */   
/*    */   static native long getNullScalerContext();
/*    */   
/*    */   native long getGlyphImage(long paramLong, int paramInt);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/NullFontScaler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */