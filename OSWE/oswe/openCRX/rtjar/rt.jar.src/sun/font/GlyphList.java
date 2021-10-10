/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.font.GlyphVector;
/*     */ import sun.java2d.loops.FontInfo;
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
/*     */ 
/*     */ 
/*     */ public final class GlyphList
/*     */ {
/*     */   private static final int MINGRAYLENGTH = 1024;
/*     */   private static final int MAXGRAYLENGTH = 8192;
/*     */   private static final int DEFAULT_LENGTH = 32;
/*     */   int glyphindex;
/*     */   int[] metrics;
/*     */   byte[] graybits;
/*     */   Object strikelist;
/* 106 */   int len = 0;
/* 107 */   int maxLen = 0;
/* 108 */   int maxPosLen = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   int[] glyphData;
/*     */ 
/*     */ 
/*     */   
/*     */   char[] chData;
/*     */ 
/*     */ 
/*     */   
/*     */   long[] images;
/*     */ 
/*     */ 
/*     */   
/*     */   float[] positions;
/*     */ 
/*     */ 
/*     */   
/*     */   float x;
/*     */ 
/*     */ 
/*     */   
/*     */   float y;
/*     */ 
/*     */ 
/*     */   
/*     */   float gposx;
/*     */ 
/*     */ 
/*     */   
/*     */   float gposy;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean usePositions;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean lcdRGBOrder;
/*     */ 
/*     */   
/*     */   boolean lcdSubPixPos;
/*     */ 
/*     */   
/* 154 */   private static GlyphList reusableGL = new GlyphList();
/*     */ 
/*     */   
/*     */   private static boolean inUse;
/*     */ 
/*     */ 
/*     */   
/*     */   void ensureCapacity(int paramInt) {
/* 162 */     if (paramInt < 0) {
/* 163 */       paramInt = 0;
/*     */     }
/* 165 */     if (this.usePositions && paramInt > this.maxPosLen) {
/* 166 */       this.positions = new float[paramInt * 2 + 2];
/* 167 */       this.maxPosLen = paramInt;
/*     */     } 
/*     */     
/* 170 */     if (this.maxLen == 0 || paramInt > this.maxLen) {
/* 171 */       this.glyphData = new int[paramInt];
/* 172 */       this.chData = new char[paramInt];
/* 173 */       this.images = new long[paramInt];
/* 174 */       this.maxLen = paramInt;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GlyphList getInstance() {
/* 194 */     if (inUse) {
/* 195 */       return new GlyphList();
/*     */     }
/* 197 */     synchronized (GlyphList.class) {
/* 198 */       if (inUse) {
/* 199 */         return new GlyphList();
/*     */       }
/* 201 */       inUse = true;
/* 202 */       return reusableGL;
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
/*     */   public boolean setFromString(FontInfo paramFontInfo, String paramString, float paramFloat1, float paramFloat2) {
/* 236 */     this.x = paramFloat1;
/* 237 */     this.y = paramFloat2;
/* 238 */     this.strikelist = paramFontInfo.fontStrike;
/* 239 */     this.lcdRGBOrder = paramFontInfo.lcdRGBOrder;
/* 240 */     this.lcdSubPixPos = paramFontInfo.lcdSubPixPos;
/* 241 */     this.len = paramString.length();
/* 242 */     ensureCapacity(this.len);
/* 243 */     paramString.getChars(0, this.len, this.chData, 0);
/* 244 */     return mapChars(paramFontInfo, this.len);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setFromChars(FontInfo paramFontInfo, char[] paramArrayOfchar, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2) {
/* 249 */     this.x = paramFloat1;
/* 250 */     this.y = paramFloat2;
/* 251 */     this.strikelist = paramFontInfo.fontStrike;
/* 252 */     this.lcdRGBOrder = paramFontInfo.lcdRGBOrder;
/* 253 */     this.lcdSubPixPos = paramFontInfo.lcdSubPixPos;
/* 254 */     this.len = paramInt2;
/* 255 */     if (paramInt2 < 0) {
/* 256 */       this.len = 0;
/*     */     } else {
/* 258 */       this.len = paramInt2;
/*     */     } 
/* 260 */     ensureCapacity(this.len);
/* 261 */     System.arraycopy(paramArrayOfchar, paramInt1, this.chData, 0, this.len);
/* 262 */     return mapChars(paramFontInfo, this.len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean mapChars(FontInfo paramFontInfo, int paramInt) {
/* 269 */     if (paramFontInfo.font2D.getMapper().charsToGlyphsNS(paramInt, this.chData, this.glyphData)) {
/* 270 */       return false;
/*     */     }
/* 272 */     paramFontInfo.fontStrike.getGlyphImagePtrs(this.glyphData, this.images, paramInt);
/* 273 */     this.glyphindex = -1;
/* 274 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFromGlyphVector(FontInfo paramFontInfo, GlyphVector paramGlyphVector, float paramFloat1, float paramFloat2) {
/* 280 */     this.x = paramFloat1;
/* 281 */     this.y = paramFloat2;
/* 282 */     this.lcdRGBOrder = paramFontInfo.lcdRGBOrder;
/* 283 */     this.lcdSubPixPos = paramFontInfo.lcdSubPixPos;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 288 */     StandardGlyphVector standardGlyphVector = StandardGlyphVector.getStandardGV(paramGlyphVector, paramFontInfo);
/*     */     
/* 290 */     this.usePositions = standardGlyphVector.needsPositions(paramFontInfo.devTx);
/* 291 */     this.len = standardGlyphVector.getNumGlyphs();
/* 292 */     ensureCapacity(this.len);
/* 293 */     this.strikelist = standardGlyphVector.setupGlyphImages(this.images, this.usePositions ? this.positions : null, paramFontInfo.devTx);
/*     */ 
/*     */     
/* 296 */     this.glyphindex = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getBounds() {
/* 304 */     if (this.glyphindex >= 0) {
/* 305 */       throw new InternalError("calling getBounds after setGlyphIndex");
/*     */     }
/* 307 */     if (this.metrics == null) {
/* 308 */       this.metrics = new int[5];
/*     */     }
/*     */ 
/*     */     
/* 312 */     this.gposx = this.x + 0.5F;
/* 313 */     this.gposy = this.y + 0.5F;
/* 314 */     fillBounds(this.metrics);
/* 315 */     return this.metrics;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGlyphIndex(int paramInt) {
/* 325 */     this.glyphindex = paramInt;
/* 326 */     if (this.images[paramInt] == 0L) {
/* 327 */       this.metrics[0] = (int)this.gposx;
/* 328 */       this.metrics[1] = (int)this.gposy;
/* 329 */       this.metrics[2] = 0;
/* 330 */       this.metrics[3] = 0;
/* 331 */       this.metrics[4] = 0;
/*     */       
/*     */       return;
/*     */     } 
/* 335 */     float f1 = StrikeCache.unsafe.getFloat(this.images[paramInt] + StrikeCache.topLeftXOffset);
/*     */     
/* 337 */     float f2 = StrikeCache.unsafe.getFloat(this.images[paramInt] + StrikeCache.topLeftYOffset);
/*     */     
/* 339 */     if (this.usePositions) {
/* 340 */       this.metrics[0] = (int)Math.floor((this.positions[paramInt << 1] + this.gposx + f1));
/* 341 */       this.metrics[1] = (int)Math.floor((this.positions[(paramInt << 1) + 1] + this.gposy + f2));
/*     */     } else {
/* 343 */       this.metrics[0] = (int)Math.floor((this.gposx + f1));
/* 344 */       this.metrics[1] = (int)Math.floor((this.gposy + f2));
/*     */       
/* 346 */       this.gposx += StrikeCache.unsafe
/* 347 */         .getFloat(this.images[paramInt] + StrikeCache.xAdvanceOffset);
/* 348 */       this.gposy += StrikeCache.unsafe
/* 349 */         .getFloat(this.images[paramInt] + StrikeCache.yAdvanceOffset);
/*     */     } 
/* 351 */     this.metrics[2] = StrikeCache.unsafe
/* 352 */       .getChar(this.images[paramInt] + StrikeCache.widthOffset);
/* 353 */     this.metrics[3] = StrikeCache.unsafe
/* 354 */       .getChar(this.images[paramInt] + StrikeCache.heightOffset);
/* 355 */     this.metrics[4] = StrikeCache.unsafe
/* 356 */       .getChar(this.images[paramInt] + StrikeCache.rowBytesOffset);
/*     */   }
/*     */   
/*     */   public int[] getMetrics() {
/* 360 */     return this.metrics;
/*     */   }
/*     */   
/*     */   public byte[] getGrayBits() {
/* 364 */     int i = this.metrics[4] * this.metrics[3];
/* 365 */     if (this.graybits == null) {
/* 366 */       this.graybits = new byte[Math.max(i, 1024)];
/*     */     }
/* 368 */     else if (i > this.graybits.length) {
/* 369 */       this.graybits = new byte[i];
/*     */     } 
/*     */     
/* 372 */     if (this.images[this.glyphindex] == 0L) {
/* 373 */       return this.graybits;
/*     */     }
/*     */     
/* 376 */     long l = StrikeCache.unsafe.getAddress(this.images[this.glyphindex] + StrikeCache.pixelDataOffset);
/*     */ 
/*     */     
/* 379 */     if (l == 0L) {
/* 380 */       return this.graybits;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 388 */     for (byte b = 0; b < i; b++) {
/* 389 */       this.graybits[b] = StrikeCache.unsafe.getByte(l + b);
/*     */     }
/* 391 */     return this.graybits;
/*     */   }
/*     */   
/*     */   public long[] getImages() {
/* 395 */     return this.images;
/*     */   }
/*     */   
/*     */   public boolean usePositions() {
/* 399 */     return this.usePositions;
/*     */   }
/*     */   
/*     */   public float[] getPositions() {
/* 403 */     return this.positions;
/*     */   }
/*     */   
/*     */   public float getX() {
/* 407 */     return this.x;
/*     */   }
/*     */   
/*     */   public float getY() {
/* 411 */     return this.y;
/*     */   }
/*     */   
/*     */   public Object getStrike() {
/* 415 */     return this.strikelist;
/*     */   }
/*     */   
/*     */   public boolean isSubPixPos() {
/* 419 */     return this.lcdSubPixPos;
/*     */   }
/*     */   
/*     */   public boolean isRGBOrder() {
/* 423 */     return this.lcdRGBOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 431 */     if (this == reusableGL) {
/* 432 */       if (this.graybits != null && this.graybits.length > 8192) {
/* 433 */         this.graybits = null;
/*     */       }
/* 435 */       this.usePositions = false;
/* 436 */       this.strikelist = null;
/* 437 */       inUse = false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumGlyphs() {
/* 453 */     return this.len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillBounds(int[] paramArrayOfint) {
/* 461 */     int i = StrikeCache.topLeftXOffset;
/* 462 */     int j = StrikeCache.topLeftYOffset;
/* 463 */     int k = StrikeCache.widthOffset;
/* 464 */     int m = StrikeCache.heightOffset;
/* 465 */     int n = StrikeCache.xAdvanceOffset;
/* 466 */     int i1 = StrikeCache.yAdvanceOffset;
/*     */     
/* 468 */     if (this.len == 0) {
/* 469 */       paramArrayOfint[3] = 0; paramArrayOfint[2] = 0; paramArrayOfint[1] = 0; paramArrayOfint[0] = 0;
/*     */       
/*     */       return;
/*     */     } 
/* 473 */     float f2 = Float.POSITIVE_INFINITY, f1 = f2;
/* 474 */     float f4 = Float.NEGATIVE_INFINITY, f3 = f4;
/*     */     
/* 476 */     byte b1 = 0;
/* 477 */     float f5 = this.x + 0.5F;
/* 478 */     float f6 = this.y + 0.5F;
/*     */ 
/*     */     
/* 481 */     for (byte b2 = 0; b2 < this.len; b2++) {
/* 482 */       if (this.images[b2] != 0L) {
/*     */ 
/*     */         
/* 485 */         float f9, f10, f7 = StrikeCache.unsafe.getFloat(this.images[b2] + i);
/* 486 */         float f8 = StrikeCache.unsafe.getFloat(this.images[b2] + j);
/* 487 */         char c1 = StrikeCache.unsafe.getChar(this.images[b2] + k);
/* 488 */         char c2 = StrikeCache.unsafe.getChar(this.images[b2] + m);
/*     */         
/* 490 */         if (this.usePositions) {
/* 491 */           f9 = this.positions[b1++] + f7 + f5;
/* 492 */           f10 = this.positions[b1++] + f8 + f6;
/*     */         } else {
/* 494 */           f9 = f5 + f7;
/* 495 */           f10 = f6 + f8;
/* 496 */           f5 += StrikeCache.unsafe.getFloat(this.images[b2] + n);
/* 497 */           f6 += StrikeCache.unsafe.getFloat(this.images[b2] + i1);
/*     */         } 
/* 499 */         float f11 = f9 + c1;
/* 500 */         float f12 = f10 + c2;
/* 501 */         if (f1 > f9) f1 = f9; 
/* 502 */         if (f2 > f10) f2 = f10; 
/* 503 */         if (f3 < f11) f3 = f11; 
/* 504 */         if (f4 < f12) f4 = f12;
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 509 */     paramArrayOfint[0] = (int)Math.floor(f1);
/* 510 */     paramArrayOfint[1] = (int)Math.floor(f2);
/* 511 */     paramArrayOfint[2] = (int)Math.floor(f3);
/* 512 */     paramArrayOfint[3] = (int)Math.floor(f4);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/GlyphList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */