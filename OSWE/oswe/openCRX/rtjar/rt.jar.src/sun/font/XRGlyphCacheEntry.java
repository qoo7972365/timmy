/*     */ package sun.font;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
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
/*     */ public class XRGlyphCacheEntry
/*     */ {
/*     */   long glyphInfoPtr;
/*     */   int lastUsed;
/*     */   boolean pinned;
/*     */   int xOff;
/*     */   int yOff;
/*     */   int glyphSet;
/*     */   
/*     */   public XRGlyphCacheEntry(long paramLong, GlyphList paramGlyphList) {
/*  48 */     this.glyphInfoPtr = paramLong;
/*     */ 
/*     */     
/*  51 */     this.xOff = Math.round(getXAdvance());
/*  52 */     this.yOff = Math.round(getYAdvance());
/*     */   }
/*     */   
/*     */   public int getXOff() {
/*  56 */     return this.xOff;
/*     */   }
/*     */   
/*     */   public int getYOff() {
/*  60 */     return this.yOff;
/*     */   }
/*     */   
/*     */   public void setGlyphSet(int paramInt) {
/*  64 */     this.glyphSet = paramInt;
/*     */   }
/*     */   
/*     */   public int getGlyphSet() {
/*  68 */     return this.glyphSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getGlyphID(long paramLong) {
/*  78 */     return (int)StrikeCache.unsafe.getAddress(paramLong + StrikeCache.cacheCellOffset);
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
/*     */   public static void setGlyphID(long paramLong, int paramInt) {
/*  92 */     StrikeCache.unsafe.putAddress(paramLong + StrikeCache.cacheCellOffset, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGlyphID() {
/*  97 */     return getGlyphID(this.glyphInfoPtr);
/*     */   }
/*     */   
/*     */   public void setGlyphID(int paramInt) {
/* 101 */     setGlyphID(this.glyphInfoPtr, paramInt);
/*     */   }
/*     */   
/*     */   public float getXAdvance() {
/* 105 */     return StrikeCache.unsafe.getFloat(this.glyphInfoPtr + StrikeCache.xAdvanceOffset);
/*     */   }
/*     */   
/*     */   public float getYAdvance() {
/* 109 */     return StrikeCache.unsafe.getFloat(this.glyphInfoPtr + StrikeCache.yAdvanceOffset);
/*     */   }
/*     */   
/*     */   public int getSourceRowBytes() {
/* 113 */     return StrikeCache.unsafe.getShort(this.glyphInfoPtr + StrikeCache.rowBytesOffset);
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 117 */     return StrikeCache.unsafe.getShort(this.glyphInfoPtr + StrikeCache.widthOffset);
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 121 */     return StrikeCache.unsafe.getShort(this.glyphInfoPtr + StrikeCache.heightOffset);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writePixelData(ByteArrayOutputStream paramByteArrayOutputStream, boolean paramBoolean) {
/* 126 */     long l = StrikeCache.unsafe.getAddress(this.glyphInfoPtr + StrikeCache.pixelDataOffset);
/*     */     
/* 128 */     if (l == 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     int i = getWidth();
/* 133 */     int j = getHeight();
/* 134 */     int k = getSourceRowBytes();
/* 135 */     int m = getPaddedWidth(paramBoolean);
/*     */     
/* 137 */     if (!paramBoolean) {
/* 138 */       for (byte b = 0; b < j; b++) {
/* 139 */         for (byte b1 = 0; b1 < m; b1++) {
/* 140 */           if (b1 < i) {
/* 141 */             paramByteArrayOutputStream.write(StrikeCache.unsafe.getByte(l + (b * k + b1)));
/*     */           } else {
/*     */             
/* 144 */             paramByteArrayOutputStream.write(0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 149 */       for (byte b = 0; b < j; b++) {
/* 150 */         int n = b * k;
/* 151 */         int i1 = i * 3;
/* 152 */         byte b1 = 0;
/* 153 */         while (b1 < i1) {
/* 154 */           paramByteArrayOutputStream.write(StrikeCache.unsafe
/* 155 */               .getByte(l + (n + b1 + 2)));
/* 156 */           paramByteArrayOutputStream.write(StrikeCache.unsafe
/* 157 */               .getByte(l + (n + b1 + 1)));
/* 158 */           paramByteArrayOutputStream.write(StrikeCache.unsafe
/* 159 */               .getByte(l + (n + b1 + 0)));
/* 160 */           paramByteArrayOutputStream.write(255);
/* 161 */           b1 += 3;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getTopLeftXOffset() {
/* 168 */     return StrikeCache.unsafe.getFloat(this.glyphInfoPtr + StrikeCache.topLeftXOffset);
/*     */   }
/*     */   
/*     */   public float getTopLeftYOffset() {
/* 172 */     return StrikeCache.unsafe.getFloat(this.glyphInfoPtr + StrikeCache.topLeftYOffset);
/*     */   }
/*     */   
/*     */   public long getGlyphInfoPtr() {
/* 176 */     return this.glyphInfoPtr;
/*     */   }
/*     */   
/*     */   public boolean isGrayscale(boolean paramBoolean) {
/* 180 */     return (getSourceRowBytes() == getWidth() && (getWidth() != 0 || getHeight() != 0 || !paramBoolean));
/*     */   }
/*     */   
/*     */   public int getPaddedWidth(boolean paramBoolean) {
/* 184 */     int i = getWidth();
/* 185 */     return isGrayscale(paramBoolean) ? ((int)Math.ceil(i / 4.0D) * 4) : i;
/*     */   }
/*     */   
/*     */   public int getDestinationRowBytes(boolean paramBoolean) {
/* 189 */     boolean bool = isGrayscale(paramBoolean);
/* 190 */     return bool ? getPaddedWidth(bool) : (getWidth() * 4);
/*     */   }
/*     */   
/*     */   public int getGlyphDataLenth(boolean paramBoolean) {
/* 194 */     return getDestinationRowBytes(paramBoolean) * getHeight();
/*     */   }
/*     */   
/*     */   public void setPinned() {
/* 198 */     this.pinned = true;
/*     */   }
/*     */   
/*     */   public void setUnpinned() {
/* 202 */     this.pinned = false;
/*     */   }
/*     */   
/*     */   public int getLastUsed() {
/* 206 */     return this.lastUsed;
/*     */   }
/*     */   
/*     */   public void setLastUsed(int paramInt) {
/* 210 */     this.lastUsed = paramInt;
/*     */   }
/*     */   
/*     */   public int getPixelCnt() {
/* 214 */     return getWidth() * getHeight();
/*     */   }
/*     */   
/*     */   public boolean isPinned() {
/* 218 */     return this.pinned;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/XRGlyphCacheEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */