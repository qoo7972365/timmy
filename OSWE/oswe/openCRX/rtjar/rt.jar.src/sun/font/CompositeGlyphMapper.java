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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompositeGlyphMapper
/*     */   extends CharToGlyphMapper
/*     */ {
/*     */   public static final int SLOTMASK = -16777216;
/*     */   public static final int GLYPHMASK = 16777215;
/*     */   public static final int NBLOCKS = 216;
/*     */   public static final int BLOCKSZ = 256;
/*     */   public static final int MAXUNICODE = 55296;
/*     */   CompositeFont font;
/*     */   CharToGlyphMapper[] slotMappers;
/*     */   int[][] glyphMaps;
/*     */   private boolean hasExcludes;
/*     */   
/*     */   public CompositeGlyphMapper(CompositeFont paramCompositeFont) {
/*  61 */     this.font = paramCompositeFont;
/*  62 */     initMapper();
/*     */ 
/*     */ 
/*     */     
/*  66 */     this.hasExcludes = (paramCompositeFont.exclusionRanges != null && paramCompositeFont.maxIndices != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int compositeGlyphCode(int paramInt1, int paramInt2) {
/*  71 */     return paramInt1 << 24 | paramInt2 & 0xFFFFFF;
/*     */   }
/*     */   
/*     */   private final void initMapper() {
/*  75 */     if (this.missingGlyph == -1) {
/*  76 */       if (this.glyphMaps == null) {
/*  77 */         this.glyphMaps = new int[216][];
/*     */       }
/*  79 */       this.slotMappers = new CharToGlyphMapper[this.font.numSlots];
/*     */       
/*  81 */       this.missingGlyph = this.font.getSlotFont(0).getMissingGlyphCode();
/*  82 */       this.missingGlyph = compositeGlyphCode(0, this.missingGlyph);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getCachedGlyphCode(int paramInt) {
/*  87 */     if (paramInt >= 55296) {
/*  88 */       return -1;
/*     */     }
/*     */     int[] arrayOfInt;
/*  91 */     if ((arrayOfInt = this.glyphMaps[paramInt >> 8]) == null) {
/*  92 */       return -1;
/*     */     }
/*  94 */     return arrayOfInt[paramInt & 0xFF];
/*     */   }
/*     */   
/*     */   private void setCachedGlyphCode(int paramInt1, int paramInt2) {
/*  98 */     if (paramInt1 >= 55296) {
/*     */       return;
/*     */     }
/* 101 */     int i = paramInt1 >> 8;
/* 102 */     if (this.glyphMaps[i] == null) {
/* 103 */       this.glyphMaps[i] = new int[256];
/* 104 */       for (byte b = 0; b < 'Ā'; b++) {
/* 105 */         this.glyphMaps[i][b] = -1;
/*     */       }
/*     */     } 
/* 108 */     this.glyphMaps[i][paramInt1 & 0xFF] = paramInt2;
/*     */   }
/*     */   
/*     */   private final CharToGlyphMapper getSlotMapper(int paramInt) {
/* 112 */     CharToGlyphMapper charToGlyphMapper = this.slotMappers[paramInt];
/* 113 */     if (charToGlyphMapper == null) {
/* 114 */       charToGlyphMapper = this.font.getSlotFont(paramInt).getMapper();
/* 115 */       this.slotMappers[paramInt] = charToGlyphMapper;
/*     */     } 
/* 117 */     return charToGlyphMapper;
/*     */   }
/*     */ 
/*     */   
/*     */   private final int convertToGlyph(int paramInt) {
/* 122 */     for (byte b = 0; b < this.font.numSlots; b++) {
/* 123 */       if (!this.hasExcludes || !this.font.isExcludedChar(b, paramInt)) {
/* 124 */         CharToGlyphMapper charToGlyphMapper = getSlotMapper(b);
/* 125 */         int i = charToGlyphMapper.charToGlyph(paramInt);
/* 126 */         if (i != charToGlyphMapper.getMissingGlyphCode()) {
/* 127 */           i = compositeGlyphCode(b, i);
/* 128 */           setCachedGlyphCode(paramInt, i);
/* 129 */           return i;
/*     */         } 
/*     */       } 
/*     */     } 
/* 133 */     return this.missingGlyph;
/*     */   }
/*     */   
/*     */   public int getNumGlyphs() {
/* 137 */     int i = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     for (byte b = 0; b < 1; b++) {
/* 148 */       CharToGlyphMapper charToGlyphMapper = this.slotMappers[b];
/* 149 */       if (charToGlyphMapper == null) {
/* 150 */         charToGlyphMapper = this.font.getSlotFont(b).getMapper();
/* 151 */         this.slotMappers[b] = charToGlyphMapper;
/*     */       } 
/* 153 */       i += charToGlyphMapper.getNumGlyphs();
/*     */     } 
/* 155 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int charToGlyph(int paramInt) {
/* 160 */     int i = getCachedGlyphCode(paramInt);
/* 161 */     if (i == -1) {
/* 162 */       i = convertToGlyph(paramInt);
/*     */     }
/* 164 */     return i;
/*     */   }
/*     */   
/*     */   public int charToGlyph(int paramInt1, int paramInt2) {
/* 168 */     if (paramInt2 >= 0) {
/* 169 */       CharToGlyphMapper charToGlyphMapper = getSlotMapper(paramInt2);
/* 170 */       int i = charToGlyphMapper.charToGlyph(paramInt1);
/* 171 */       if (i != charToGlyphMapper.getMissingGlyphCode()) {
/* 172 */         return compositeGlyphCode(paramInt2, i);
/*     */       }
/*     */     } 
/* 175 */     return charToGlyph(paramInt1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int charToGlyph(char paramChar) {
/* 180 */     int i = getCachedGlyphCode(paramChar);
/* 181 */     if (i == -1) {
/* 182 */       i = convertToGlyph(paramChar);
/*     */     }
/* 184 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean charsToGlyphsNS(int paramInt, char[] paramArrayOfchar, int[] paramArrayOfint) {
/* 194 */     for (byte b = 0; b < paramInt; b++) {
/* 195 */       int i = paramArrayOfchar[b];
/*     */       
/* 197 */       if (i >= 55296 && i <= 56319 && b < paramInt - 1) {
/*     */         
/* 199 */         char c = paramArrayOfchar[b + 1];
/*     */         
/* 201 */         if (c >= '?' && c <= '?') {
/*     */           
/* 203 */           i = (i - 55296) * 1024 + c - 56320 + 65536;
/*     */           
/* 205 */           paramArrayOfint[b + 1] = 65535;
/*     */         } 
/*     */       } 
/*     */       
/* 209 */       int j = paramArrayOfint[b] = getCachedGlyphCode(i);
/* 210 */       if (j == -1) {
/* 211 */         paramArrayOfint[b] = convertToGlyph(i);
/*     */       }
/*     */       
/* 214 */       if (i >= 768) {
/*     */ 
/*     */         
/* 217 */         if (FontUtilities.isComplexCharCode(i)) {
/* 218 */           return true;
/*     */         }
/* 220 */         if (i >= 65536) {
/* 221 */           b++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void charsToGlyphs(int paramInt, char[] paramArrayOfchar, int[] paramArrayOfint) {
/* 233 */     for (byte b = 0; b < paramInt; b++) {
/* 234 */       int i = paramArrayOfchar[b];
/*     */       
/* 236 */       if (i >= 55296 && i <= 56319 && b < paramInt - 1) {
/*     */         
/* 238 */         char c = paramArrayOfchar[b + 1];
/*     */         
/* 240 */         if (c >= '?' && c <= '?') {
/*     */           
/* 242 */           i = (i - 55296) * 1024 + c - 56320 + 65536;
/*     */ 
/*     */           
/* 245 */           int k = paramArrayOfint[b] = getCachedGlyphCode(i);
/* 246 */           if (k == -1) {
/* 247 */             paramArrayOfint[b] = convertToGlyph(i);
/*     */           }
/* 249 */           b++;
/* 250 */           paramArrayOfint[b] = 65535;
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 255 */       int j = paramArrayOfint[b] = getCachedGlyphCode(i);
/* 256 */       if (j == -1)
/* 257 */         paramArrayOfint[b] = convertToGlyph(i); 
/*     */       continue;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void charsToGlyphs(int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 263 */     for (byte b = 0; b < paramInt; b++) {
/* 264 */       int i = paramArrayOfint1[b];
/*     */       
/* 266 */       paramArrayOfint2[b] = getCachedGlyphCode(i);
/* 267 */       if (paramArrayOfint2[b] == -1)
/* 268 */         paramArrayOfint2[b] = convertToGlyph(i); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/CompositeGlyphMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */