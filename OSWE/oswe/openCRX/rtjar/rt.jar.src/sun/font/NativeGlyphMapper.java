/*     */ package sun.font;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NativeGlyphMapper
/*     */   extends CharToGlyphMapper
/*     */ {
/*     */   NativeFont font;
/*     */   XMap xmapper;
/*     */   int numGlyphs;
/*     */   
/*     */   NativeGlyphMapper(NativeFont paramNativeFont) {
/*  52 */     this.font = paramNativeFont;
/*  53 */     this.xmapper = XMap.getXMapper(this.font.encoding);
/*  54 */     this.numGlyphs = paramNativeFont.getNumGlyphs();
/*  55 */     this.missingGlyph = 0;
/*     */   }
/*     */   
/*     */   public int getNumGlyphs() {
/*  59 */     return this.numGlyphs;
/*     */   }
/*     */   
/*     */   public int charToGlyph(char paramChar) {
/*  63 */     if (paramChar >= this.xmapper.convertedGlyphs.length) {
/*  64 */       return 0;
/*     */     }
/*  66 */     return this.xmapper.convertedGlyphs[paramChar];
/*     */   }
/*     */ 
/*     */   
/*     */   public int charToGlyph(int paramInt) {
/*  71 */     if (paramInt >= this.xmapper.convertedGlyphs.length) {
/*  72 */       return 0;
/*     */     }
/*  74 */     return this.xmapper.convertedGlyphs[paramInt];
/*     */   }
/*     */ 
/*     */   
/*     */   public void charsToGlyphs(int paramInt, char[] paramArrayOfchar, int[] paramArrayOfint) {
/*  79 */     for (byte b = 0; b < paramInt; b++) {
/*  80 */       char c = paramArrayOfchar[b];
/*  81 */       if (c >= this.xmapper.convertedGlyphs.length) {
/*  82 */         paramArrayOfint[b] = 0;
/*     */       } else {
/*  84 */         paramArrayOfint[b] = this.xmapper.convertedGlyphs[c];
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean charsToGlyphsNS(int paramInt, char[] paramArrayOfchar, int[] paramArrayOfint) {
/*  90 */     charsToGlyphs(paramInt, paramArrayOfchar, paramArrayOfint);
/*  91 */     return false;
/*     */   }
/*     */   
/*     */   public void charsToGlyphs(int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/*  95 */     for (byte b = 0; b < paramInt; b++) {
/*  96 */       char c = (char)paramArrayOfint1[b];
/*  97 */       if (c >= this.xmapper.convertedGlyphs.length) {
/*  98 */         paramArrayOfint2[b] = 0;
/*     */       } else {
/* 100 */         paramArrayOfint2[b] = this.xmapper.convertedGlyphs[c];
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/NativeGlyphMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */