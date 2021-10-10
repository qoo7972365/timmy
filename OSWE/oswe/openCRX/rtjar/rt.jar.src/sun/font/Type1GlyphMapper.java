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
/*     */ public final class Type1GlyphMapper
/*     */   extends CharToGlyphMapper
/*     */ {
/*     */   Type1Font font;
/*     */   FontScaler scaler;
/*     */   
/*     */   public Type1GlyphMapper(Type1Font paramType1Font) {
/*  40 */     this.font = paramType1Font;
/*  41 */     initMapper();
/*     */   }
/*     */   
/*     */   private void initMapper() {
/*  45 */     this.scaler = this.font.getScaler();
/*     */     try {
/*  47 */       this.missingGlyph = this.scaler.getMissingGlyphCode();
/*  48 */     } catch (FontScalerException fontScalerException) {
/*  49 */       this.scaler = FontScaler.getNullScaler();
/*     */       try {
/*  51 */         this.missingGlyph = this.scaler.getMissingGlyphCode();
/*  52 */       } catch (FontScalerException fontScalerException1) {
/*  53 */         this.missingGlyph = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getNumGlyphs() {
/*     */     try {
/*  60 */       return this.scaler.getNumGlyphs();
/*  61 */     } catch (FontScalerException fontScalerException) {
/*  62 */       this.scaler = FontScaler.getNullScaler();
/*  63 */       return getNumGlyphs();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getMissingGlyphCode() {
/*  68 */     return this.missingGlyph;
/*     */   }
/*     */   
/*     */   public boolean canDisplay(char paramChar) {
/*     */     try {
/*  73 */       return (this.scaler.getGlyphCode(paramChar) != this.missingGlyph);
/*  74 */     } catch (FontScalerException fontScalerException) {
/*  75 */       this.scaler = FontScaler.getNullScaler();
/*  76 */       return canDisplay(paramChar);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int charToGlyph(char paramChar) {
/*     */     try {
/*  82 */       return this.scaler.getGlyphCode(paramChar);
/*  83 */     } catch (FontScalerException fontScalerException) {
/*  84 */       this.scaler = FontScaler.getNullScaler();
/*  85 */       return charToGlyph(paramChar);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int charToGlyph(int paramInt) {
/*  90 */     if (paramInt < 0 || paramInt > 65535) {
/*  91 */       return this.missingGlyph;
/*     */     }
/*     */     try {
/*  94 */       return this.scaler.getGlyphCode((char)paramInt);
/*  95 */     } catch (FontScalerException fontScalerException) {
/*  96 */       this.scaler = FontScaler.getNullScaler();
/*  97 */       return charToGlyph(paramInt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void charsToGlyphs(int paramInt, char[] paramArrayOfchar, int[] paramArrayOfint) {
/* 107 */     for (byte b = 0; b < paramInt; b++) {
/* 108 */       int i = paramArrayOfchar[b];
/*     */       
/* 110 */       if (i >= 55296 && i <= 56319 && b < paramInt - 1) {
/*     */         
/* 112 */         char c = paramArrayOfchar[b + 1];
/*     */         
/* 114 */         if (c >= '?' && c <= '?') {
/*     */           
/* 116 */           i = (i - 55296) * 1024 + c - 56320 + 65536;
/*     */           
/* 118 */           paramArrayOfint[b + 1] = 65535;
/*     */         } 
/*     */       } 
/* 121 */       paramArrayOfint[b] = charToGlyph(i);
/* 122 */       if (i >= 65536) {
/* 123 */         b++;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void charsToGlyphs(int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 133 */     for (byte b = 0; b < paramInt; b++) {
/* 134 */       paramArrayOfint2[b] = charToGlyph(paramArrayOfint1[b]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean charsToGlyphsNS(int paramInt, char[] paramArrayOfchar, int[] paramArrayOfint) {
/* 146 */     for (byte b = 0; b < paramInt; b++) {
/* 147 */       int i = paramArrayOfchar[b];
/*     */       
/* 149 */       if (i >= 55296 && i <= 56319 && b < paramInt - 1) {
/*     */         
/* 151 */         char c = paramArrayOfchar[b + 1];
/*     */         
/* 153 */         if (c >= '?' && c <= '?') {
/*     */           
/* 155 */           i = (i - 55296) * 1024 + c - 56320 + 65536;
/*     */           
/* 157 */           paramArrayOfint[b + 1] = 65535;
/*     */         } 
/*     */       } 
/*     */       
/* 161 */       paramArrayOfint[b] = charToGlyph(i);
/*     */       
/* 163 */       if (i >= 768) {
/*     */ 
/*     */         
/* 166 */         if (FontUtilities.isComplexCharCode(i)) {
/* 167 */           return true;
/*     */         }
/* 169 */         if (i >= 65536) {
/* 170 */           b++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/Type1GlyphMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */