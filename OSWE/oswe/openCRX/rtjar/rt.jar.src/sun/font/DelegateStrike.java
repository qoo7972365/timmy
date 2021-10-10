/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DelegateStrike
/*     */   extends NativeStrike
/*     */ {
/*     */   private FontStrike delegateStrike;
/*     */   
/*     */   DelegateStrike(NativeFont paramNativeFont, FontStrikeDesc paramFontStrikeDesc, FontStrike paramFontStrike) {
/*  46 */     super(paramNativeFont, paramFontStrikeDesc);
/*  47 */     this.delegateStrike = paramFontStrike;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   StrikeMetrics getFontMetrics() {
/*  59 */     if (this.strikeMetrics == null) {
/*  60 */       if (this.pScalerContext != 0L) {
/*  61 */         this.strikeMetrics = super.getFontMetrics();
/*     */       }
/*  63 */       if (this.strikeMetrics == null) {
/*  64 */         this.strikeMetrics = this.delegateStrike.getFontMetrics();
/*     */       }
/*     */     } 
/*  67 */     return this.strikeMetrics;
/*     */   }
/*     */   
/*     */   void getGlyphImagePtrs(int[] paramArrayOfint, long[] paramArrayOflong, int paramInt) {
/*  71 */     this.delegateStrike.getGlyphImagePtrs(paramArrayOfint, paramArrayOflong, paramInt);
/*     */   }
/*     */   
/*     */   long getGlyphImagePtr(int paramInt) {
/*  75 */     return this.delegateStrike.getGlyphImagePtr(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   void getGlyphImageBounds(int paramInt, Point2D.Float paramFloat, Rectangle paramRectangle) {
/*  80 */     this.delegateStrike.getGlyphImageBounds(paramInt, paramFloat, paramRectangle);
/*     */   }
/*     */   
/*     */   Point2D.Float getGlyphMetrics(int paramInt) {
/*  84 */     return this.delegateStrike.getGlyphMetrics(paramInt);
/*     */   }
/*     */   
/*     */   float getGlyphAdvance(int paramInt) {
/*  88 */     return this.delegateStrike.getGlyphAdvance(paramInt);
/*     */   }
/*     */   
/*     */   Point2D.Float getCharMetrics(char paramChar) {
/*  92 */     return this.delegateStrike.getCharMetrics(paramChar);
/*     */   }
/*     */   
/*     */   float getCodePointAdvance(int paramInt) {
/*  96 */     if (paramInt < 0 || paramInt >= 65536) {
/*  97 */       paramInt = 65535;
/*     */     }
/*  99 */     return this.delegateStrike.getGlyphAdvance(paramInt);
/*     */   }
/*     */   
/*     */   Rectangle2D.Float getGlyphOutlineBounds(int paramInt) {
/* 103 */     return this.delegateStrike.getGlyphOutlineBounds(paramInt);
/*     */   }
/*     */   
/*     */   GeneralPath getGlyphOutline(int paramInt, float paramFloat1, float paramFloat2) {
/* 107 */     return this.delegateStrike.getGlyphOutline(paramInt, paramFloat1, paramFloat2);
/*     */   }
/*     */   
/*     */   GeneralPath getGlyphVectorOutline(int[] paramArrayOfint, float paramFloat1, float paramFloat2) {
/* 111 */     return this.delegateStrike.getGlyphVectorOutline(paramArrayOfint, paramFloat1, paramFloat2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/DelegateStrike.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */