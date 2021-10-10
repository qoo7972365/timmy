/*     */ package sun.font;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Locale;
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
/*     */ public class TrueTypeGlyphMapper
/*     */   extends CharToGlyphMapper
/*     */ {
/*     */   static final char REVERSE_SOLIDUS = '\\';
/*     */   static final char JA_YEN = '¥';
/*     */   static final char JA_FULLWIDTH_TILDE_CHAR = '～';
/*     */   static final char JA_WAVE_DASH_CHAR = '〜';
/*  42 */   static final boolean isJAlocale = Locale.JAPAN.equals(Locale.getDefault());
/*     */   
/*     */   private final boolean needsJAremapping;
/*     */   private boolean remapJAWaveDash;
/*     */   TrueTypeFont font;
/*     */   CMap cmap;
/*     */   int numGlyphs;
/*     */   
/*     */   public TrueTypeGlyphMapper(TrueTypeFont paramTrueTypeFont) {
/*  51 */     this.font = paramTrueTypeFont;
/*     */     try {
/*  53 */       this.cmap = CMap.initialize(paramTrueTypeFont);
/*  54 */     } catch (Exception exception) {
/*  55 */       this.cmap = null;
/*     */     } 
/*  57 */     if (this.cmap == null) {
/*  58 */       handleBadCMAP();
/*     */     }
/*  60 */     this.missingGlyph = 0;
/*  61 */     ByteBuffer byteBuffer = paramTrueTypeFont.getTableBuffer(1835104368);
/*  62 */     if (byteBuffer != null && byteBuffer.capacity() >= 6) {
/*  63 */       this.numGlyphs = byteBuffer.getChar(4);
/*     */     } else {
/*  65 */       handleBadCMAP();
/*     */     } 
/*  67 */     if (FontUtilities.isSolaris && isJAlocale && paramTrueTypeFont.supportsJA()) {
/*  68 */       this.needsJAremapping = true;
/*  69 */       if (FontUtilities.isSolaris8 && 
/*  70 */         getGlyphFromCMAP(12316) == this.missingGlyph) {
/*  71 */         this.remapJAWaveDash = true;
/*     */       }
/*     */     } else {
/*  74 */       this.needsJAremapping = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getNumGlyphs() {
/*  79 */     return this.numGlyphs;
/*     */   }
/*     */   
/*     */   private char getGlyphFromCMAP(int paramInt) {
/*     */     try {
/*  84 */       char c = this.cmap.getGlyph(paramInt);
/*  85 */       if (c < this.numGlyphs || c >= '￾')
/*     */       {
/*  87 */         return c;
/*     */       }
/*  89 */       if (FontUtilities.isLogging()) {
/*  90 */         FontUtilities.getLogger()
/*  91 */           .warning(this.font + " out of range glyph id=" + 
/*  92 */             Integer.toHexString(c) + " for char " + 
/*  93 */             Integer.toHexString(paramInt));
/*     */       }
/*  95 */       return (char)this.missingGlyph;
/*     */     }
/*  97 */     catch (Exception exception) {
/*  98 */       handleBadCMAP();
/*  99 */       return (char)this.missingGlyph;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleBadCMAP() {
/* 104 */     if (FontUtilities.isLogging()) {
/* 105 */       FontUtilities.getLogger().severe("Null Cmap for " + this.font + "substituting for this font");
/*     */     }
/*     */     
/* 108 */     SunFontManager.getInstance().deRegisterBadFont(this.font);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     this.cmap = CMap.theNullCmap;
/*     */   }
/*     */   
/*     */   private final char remapJAChar(char paramChar) {
/* 117 */     switch (paramChar) {
/*     */       case '\\':
/* 119 */         return '¥';
/*     */ 
/*     */ 
/*     */       
/*     */       case '〜':
/* 124 */         if (this.remapJAWaveDash)
/* 125 */           return '～';  break;
/*     */     } 
/* 127 */     return paramChar;
/*     */   }
/*     */   
/*     */   private final int remapJAIntChar(int paramInt) {
/* 131 */     switch (paramInt) {
/*     */       case 92:
/* 133 */         return 165;
/*     */ 
/*     */ 
/*     */       
/*     */       case 12316:
/* 138 */         if (this.remapJAWaveDash)
/* 139 */           return 65374;  break;
/*     */     } 
/* 141 */     return paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public int charToGlyph(char paramChar) {
/* 146 */     if (this.needsJAremapping) {
/* 147 */       paramChar = remapJAChar(paramChar);
/*     */     }
/* 149 */     char c = getGlyphFromCMAP(paramChar);
/* 150 */     if (this.font.checkUseNatives() && c < this.font.glyphToCharMap.length) {
/* 151 */       this.font.glyphToCharMap[c] = paramChar;
/*     */     }
/* 153 */     return c;
/*     */   }
/*     */   
/*     */   public int charToGlyph(int paramInt) {
/* 157 */     if (this.needsJAremapping) {
/* 158 */       paramInt = remapJAIntChar(paramInt);
/*     */     }
/* 160 */     char c = getGlyphFromCMAP(paramInt);
/* 161 */     if (this.font.checkUseNatives() && c < this.font.glyphToCharMap.length) {
/* 162 */       this.font.glyphToCharMap[c] = (char)paramInt;
/*     */     }
/* 164 */     return c;
/*     */   }
/*     */   
/*     */   public void charsToGlyphs(int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 168 */     for (byte b = 0; b < paramInt; b++) {
/* 169 */       if (this.needsJAremapping) {
/* 170 */         paramArrayOfint2[b] = getGlyphFromCMAP(remapJAIntChar(paramArrayOfint1[b]));
/*     */       } else {
/* 172 */         paramArrayOfint2[b] = getGlyphFromCMAP(paramArrayOfint1[b]);
/*     */       } 
/* 174 */       if (this.font.checkUseNatives() && paramArrayOfint2[b] < this.font.glyphToCharMap.length)
/*     */       {
/* 176 */         this.font.glyphToCharMap[paramArrayOfint2[b]] = (char)paramArrayOfint1[b];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void charsToGlyphs(int paramInt, char[] paramArrayOfchar, int[] paramArrayOfint) {
/* 183 */     for (byte b = 0; b < paramInt; b++) {
/*     */       int i;
/* 185 */       if (this.needsJAremapping) {
/* 186 */         i = remapJAChar(paramArrayOfchar[b]);
/*     */       } else {
/* 188 */         i = paramArrayOfchar[b];
/*     */       } 
/*     */       
/* 191 */       if (i >= 55296 && i <= 56319 && b < paramInt - 1) {
/*     */         
/* 193 */         char c = paramArrayOfchar[b + 1];
/*     */         
/* 195 */         if (c >= '?' && c <= '?') {
/*     */           
/* 197 */           i = (i - 55296) * 1024 + c - 56320 + 65536;
/*     */ 
/*     */           
/* 200 */           paramArrayOfint[b] = getGlyphFromCMAP(i);
/* 201 */           b++;
/* 202 */           paramArrayOfint[b] = 65535;
/*     */           continue;
/*     */         } 
/*     */       } 
/* 206 */       paramArrayOfint[b] = getGlyphFromCMAP(i);
/*     */       
/* 208 */       if (this.font.checkUseNatives() && paramArrayOfint[b] < this.font.glyphToCharMap.length)
/*     */       {
/* 210 */         this.font.glyphToCharMap[paramArrayOfint[b]] = (char)i;
/*     */       }
/*     */       continue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean charsToGlyphsNS(int paramInt, char[] paramArrayOfchar, int[] paramArrayOfint) {
/* 223 */     for (byte b = 0; b < paramInt; b++) {
/*     */       int i;
/* 225 */       if (this.needsJAremapping) {
/* 226 */         i = remapJAChar(paramArrayOfchar[b]);
/*     */       } else {
/* 228 */         i = paramArrayOfchar[b];
/*     */       } 
/*     */       
/* 231 */       if (i >= 55296 && i <= 56319 && b < paramInt - 1) {
/*     */         
/* 233 */         char c = paramArrayOfchar[b + 1];
/*     */         
/* 235 */         if (c >= '?' && c <= '?') {
/*     */           
/* 237 */           i = (i - 55296) * 1024 + c - 56320 + 65536;
/*     */           
/* 239 */           paramArrayOfint[b + 1] = 65535;
/*     */         } 
/*     */       } 
/*     */       
/* 243 */       paramArrayOfint[b] = getGlyphFromCMAP(i);
/* 244 */       if (this.font.checkUseNatives() && paramArrayOfint[b] < this.font.glyphToCharMap.length)
/*     */       {
/* 246 */         this.font.glyphToCharMap[paramArrayOfint[b]] = (char)i;
/*     */       }
/*     */       
/* 249 */       if (i >= 768) {
/*     */ 
/*     */         
/* 252 */         if (FontUtilities.isComplexCharCode(i)) {
/* 253 */           return true;
/*     */         }
/* 255 */         if (i >= 65536) {
/* 256 */           b++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 261 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasSupplementaryChars() {
/* 268 */     return (this.cmap instanceof CMap.CMapFormat8 || this.cmap instanceof CMap.CMapFormat10 || this.cmap instanceof CMap.CMapFormat12);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/TrueTypeGlyphMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */