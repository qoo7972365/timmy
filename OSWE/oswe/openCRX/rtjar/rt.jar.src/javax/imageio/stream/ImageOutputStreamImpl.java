/*     */ package javax.imageio.stream;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.UTFDataFormatException;
/*     */ import java.nio.ByteOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ImageOutputStreamImpl
/*     */   extends ImageInputStreamImpl
/*     */   implements ImageOutputStream
/*     */ {
/*     */   public abstract void write(int paramInt) throws IOException;
/*     */   
/*     */   public void write(byte[] paramArrayOfbyte) throws IOException {
/*  51 */     write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */   
/*     */   public abstract void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   public void writeBoolean(boolean paramBoolean) throws IOException {
/*  57 */     write(paramBoolean ? 1 : 0);
/*     */   }
/*     */   
/*     */   public void writeByte(int paramInt) throws IOException {
/*  61 */     write(paramInt);
/*     */   }
/*     */   
/*     */   public void writeShort(int paramInt) throws IOException {
/*  65 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/*  66 */       this.byteBuf[0] = (byte)(paramInt >>> 8);
/*  67 */       this.byteBuf[1] = (byte)(paramInt >>> 0);
/*     */     } else {
/*  69 */       this.byteBuf[0] = (byte)(paramInt >>> 0);
/*  70 */       this.byteBuf[1] = (byte)(paramInt >>> 8);
/*     */     } 
/*  72 */     write(this.byteBuf, 0, 2);
/*     */   }
/*     */   
/*     */   public void writeChar(int paramInt) throws IOException {
/*  76 */     writeShort(paramInt);
/*     */   }
/*     */   
/*     */   public void writeInt(int paramInt) throws IOException {
/*  80 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/*  81 */       this.byteBuf[0] = (byte)(paramInt >>> 24);
/*  82 */       this.byteBuf[1] = (byte)(paramInt >>> 16);
/*  83 */       this.byteBuf[2] = (byte)(paramInt >>> 8);
/*  84 */       this.byteBuf[3] = (byte)(paramInt >>> 0);
/*     */     } else {
/*  86 */       this.byteBuf[0] = (byte)(paramInt >>> 0);
/*  87 */       this.byteBuf[1] = (byte)(paramInt >>> 8);
/*  88 */       this.byteBuf[2] = (byte)(paramInt >>> 16);
/*  89 */       this.byteBuf[3] = (byte)(paramInt >>> 24);
/*     */     } 
/*  91 */     write(this.byteBuf, 0, 4);
/*     */   }
/*     */   
/*     */   public void writeLong(long paramLong) throws IOException {
/*  95 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/*  96 */       this.byteBuf[0] = (byte)(int)(paramLong >>> 56L);
/*  97 */       this.byteBuf[1] = (byte)(int)(paramLong >>> 48L);
/*  98 */       this.byteBuf[2] = (byte)(int)(paramLong >>> 40L);
/*  99 */       this.byteBuf[3] = (byte)(int)(paramLong >>> 32L);
/* 100 */       this.byteBuf[4] = (byte)(int)(paramLong >>> 24L);
/* 101 */       this.byteBuf[5] = (byte)(int)(paramLong >>> 16L);
/* 102 */       this.byteBuf[6] = (byte)(int)(paramLong >>> 8L);
/* 103 */       this.byteBuf[7] = (byte)(int)(paramLong >>> 0L);
/*     */     } else {
/* 105 */       this.byteBuf[0] = (byte)(int)(paramLong >>> 0L);
/* 106 */       this.byteBuf[1] = (byte)(int)(paramLong >>> 8L);
/* 107 */       this.byteBuf[2] = (byte)(int)(paramLong >>> 16L);
/* 108 */       this.byteBuf[3] = (byte)(int)(paramLong >>> 24L);
/* 109 */       this.byteBuf[4] = (byte)(int)(paramLong >>> 32L);
/* 110 */       this.byteBuf[5] = (byte)(int)(paramLong >>> 40L);
/* 111 */       this.byteBuf[6] = (byte)(int)(paramLong >>> 48L);
/* 112 */       this.byteBuf[7] = (byte)(int)(paramLong >>> 56L);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     write(this.byteBuf, 0, 4);
/* 119 */     write(this.byteBuf, 4, 4);
/*     */   }
/*     */   
/*     */   public void writeFloat(float paramFloat) throws IOException {
/* 123 */     writeInt(Float.floatToIntBits(paramFloat));
/*     */   }
/*     */   
/*     */   public void writeDouble(double paramDouble) throws IOException {
/* 127 */     writeLong(Double.doubleToLongBits(paramDouble));
/*     */   }
/*     */   
/*     */   public void writeBytes(String paramString) throws IOException {
/* 131 */     int i = paramString.length();
/* 132 */     for (byte b = 0; b < i; b++) {
/* 133 */       write((byte)paramString.charAt(b));
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeChars(String paramString) throws IOException {
/* 138 */     int i = paramString.length();
/*     */     
/* 140 */     byte[] arrayOfByte = new byte[i * 2];
/* 141 */     byte b = 0;
/* 142 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 143 */       for (byte b1 = 0; b1 < i; b1++) {
/* 144 */         char c = paramString.charAt(b1);
/* 145 */         arrayOfByte[b++] = (byte)(c >>> 8);
/* 146 */         arrayOfByte[b++] = (byte)(c >>> 0);
/*     */       } 
/*     */     } else {
/* 149 */       for (byte b1 = 0; b1 < i; b1++) {
/* 150 */         char c = paramString.charAt(b1);
/* 151 */         arrayOfByte[b++] = (byte)(c >>> 0);
/* 152 */         arrayOfByte[b++] = (byte)(c >>> 8);
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     write(arrayOfByte, 0, i * 2);
/*     */   }
/*     */   
/*     */   public void writeUTF(String paramString) throws IOException {
/* 160 */     int i = paramString.length();
/* 161 */     byte b1 = 0;
/* 162 */     char[] arrayOfChar = new char[i];
/* 163 */     byte b2 = 0;
/*     */     
/* 165 */     paramString.getChars(0, i, arrayOfChar, 0);
/*     */     
/* 167 */     for (byte b3 = 0; b3 < i; b3++) {
/* 168 */       char c = arrayOfChar[b3];
/* 169 */       if (c >= '\001' && c <= '') {
/* 170 */         b1++;
/* 171 */       } else if (c > '߿') {
/* 172 */         b1 += 3;
/*     */       } else {
/* 174 */         b1 += 2;
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     if (b1 > '￿') {
/* 179 */       throw new UTFDataFormatException("utflen > 65536!");
/*     */     }
/*     */     
/* 182 */     byte[] arrayOfByte = new byte[b1 + 2];
/* 183 */     arrayOfByte[b2++] = (byte)(b1 >>> 8 & 0xFF);
/* 184 */     arrayOfByte[b2++] = (byte)(b1 >>> 0 & 0xFF);
/* 185 */     for (byte b4 = 0; b4 < i; b4++) {
/* 186 */       char c = arrayOfChar[b4];
/* 187 */       if (c >= '\001' && c <= '') {
/* 188 */         arrayOfByte[b2++] = (byte)c;
/* 189 */       } else if (c > '߿') {
/* 190 */         arrayOfByte[b2++] = (byte)(0xE0 | c >> 12 & 0xF);
/* 191 */         arrayOfByte[b2++] = (byte)(0x80 | c >> 6 & 0x3F);
/* 192 */         arrayOfByte[b2++] = (byte)(0x80 | c >> 0 & 0x3F);
/*     */       } else {
/* 194 */         arrayOfByte[b2++] = (byte)(0xC0 | c >> 6 & 0x1F);
/* 195 */         arrayOfByte[b2++] = (byte)(0x80 | c >> 0 & 0x3F);
/*     */       } 
/*     */     } 
/* 198 */     write(arrayOfByte, 0, b1 + 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeShorts(short[] paramArrayOfshort, int paramInt1, int paramInt2) throws IOException {
/* 203 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfshort.length || paramInt1 + paramInt2 < 0) {
/* 204 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > s.length!");
/*     */     }
/*     */ 
/*     */     
/* 208 */     byte[] arrayOfByte = new byte[paramInt2 * 2];
/* 209 */     byte b = 0;
/* 210 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 211 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 212 */         short s = paramArrayOfshort[paramInt1 + b1];
/* 213 */         arrayOfByte[b++] = (byte)(s >>> 8);
/* 214 */         arrayOfByte[b++] = (byte)(s >>> 0);
/*     */       } 
/*     */     } else {
/* 217 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 218 */         short s = paramArrayOfshort[paramInt1 + b1];
/* 219 */         arrayOfByte[b++] = (byte)(s >>> 0);
/* 220 */         arrayOfByte[b++] = (byte)(s >>> 8);
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     write(arrayOfByte, 0, paramInt2 * 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeChars(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/* 229 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfchar.length || paramInt1 + paramInt2 < 0) {
/* 230 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > c.length!");
/*     */     }
/*     */ 
/*     */     
/* 234 */     byte[] arrayOfByte = new byte[paramInt2 * 2];
/* 235 */     byte b = 0;
/* 236 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 237 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 238 */         char c = paramArrayOfchar[paramInt1 + b1];
/* 239 */         arrayOfByte[b++] = (byte)(c >>> 8);
/* 240 */         arrayOfByte[b++] = (byte)(c >>> 0);
/*     */       } 
/*     */     } else {
/* 243 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 244 */         char c = paramArrayOfchar[paramInt1 + b1];
/* 245 */         arrayOfByte[b++] = (byte)(c >>> 0);
/* 246 */         arrayOfByte[b++] = (byte)(c >>> 8);
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     write(arrayOfByte, 0, paramInt2 * 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeInts(int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException {
/* 255 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfint.length || paramInt1 + paramInt2 < 0) {
/* 256 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > i.length!");
/*     */     }
/*     */ 
/*     */     
/* 260 */     byte[] arrayOfByte = new byte[paramInt2 * 4];
/* 261 */     byte b = 0;
/* 262 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 263 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 264 */         int i = paramArrayOfint[paramInt1 + b1];
/* 265 */         arrayOfByte[b++] = (byte)(i >>> 24);
/* 266 */         arrayOfByte[b++] = (byte)(i >>> 16);
/* 267 */         arrayOfByte[b++] = (byte)(i >>> 8);
/* 268 */         arrayOfByte[b++] = (byte)(i >>> 0);
/*     */       } 
/*     */     } else {
/* 271 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 272 */         int i = paramArrayOfint[paramInt1 + b1];
/* 273 */         arrayOfByte[b++] = (byte)(i >>> 0);
/* 274 */         arrayOfByte[b++] = (byte)(i >>> 8);
/* 275 */         arrayOfByte[b++] = (byte)(i >>> 16);
/* 276 */         arrayOfByte[b++] = (byte)(i >>> 24);
/*     */       } 
/*     */     } 
/*     */     
/* 280 */     write(arrayOfByte, 0, paramInt2 * 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeLongs(long[] paramArrayOflong, int paramInt1, int paramInt2) throws IOException {
/* 285 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOflong.length || paramInt1 + paramInt2 < 0) {
/* 286 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > l.length!");
/*     */     }
/*     */ 
/*     */     
/* 290 */     byte[] arrayOfByte = new byte[paramInt2 * 8];
/* 291 */     byte b = 0;
/* 292 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 293 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 294 */         long l = paramArrayOflong[paramInt1 + b1];
/* 295 */         arrayOfByte[b++] = (byte)(int)(l >>> 56L);
/* 296 */         arrayOfByte[b++] = (byte)(int)(l >>> 48L);
/* 297 */         arrayOfByte[b++] = (byte)(int)(l >>> 40L);
/* 298 */         arrayOfByte[b++] = (byte)(int)(l >>> 32L);
/* 299 */         arrayOfByte[b++] = (byte)(int)(l >>> 24L);
/* 300 */         arrayOfByte[b++] = (byte)(int)(l >>> 16L);
/* 301 */         arrayOfByte[b++] = (byte)(int)(l >>> 8L);
/* 302 */         arrayOfByte[b++] = (byte)(int)(l >>> 0L);
/*     */       } 
/*     */     } else {
/* 305 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 306 */         long l = paramArrayOflong[paramInt1 + b1];
/* 307 */         arrayOfByte[b++] = (byte)(int)(l >>> 0L);
/* 308 */         arrayOfByte[b++] = (byte)(int)(l >>> 8L);
/* 309 */         arrayOfByte[b++] = (byte)(int)(l >>> 16L);
/* 310 */         arrayOfByte[b++] = (byte)(int)(l >>> 24L);
/* 311 */         arrayOfByte[b++] = (byte)(int)(l >>> 32L);
/* 312 */         arrayOfByte[b++] = (byte)(int)(l >>> 40L);
/* 313 */         arrayOfByte[b++] = (byte)(int)(l >>> 48L);
/* 314 */         arrayOfByte[b++] = (byte)(int)(l >>> 56L);
/*     */       } 
/*     */     } 
/*     */     
/* 318 */     write(arrayOfByte, 0, paramInt2 * 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFloats(float[] paramArrayOffloat, int paramInt1, int paramInt2) throws IOException {
/* 323 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOffloat.length || paramInt1 + paramInt2 < 0) {
/* 324 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > f.length!");
/*     */     }
/*     */ 
/*     */     
/* 328 */     byte[] arrayOfByte = new byte[paramInt2 * 4];
/* 329 */     byte b = 0;
/* 330 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 331 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 332 */         int i = Float.floatToIntBits(paramArrayOffloat[paramInt1 + b1]);
/* 333 */         arrayOfByte[b++] = (byte)(i >>> 24);
/* 334 */         arrayOfByte[b++] = (byte)(i >>> 16);
/* 335 */         arrayOfByte[b++] = (byte)(i >>> 8);
/* 336 */         arrayOfByte[b++] = (byte)(i >>> 0);
/*     */       } 
/*     */     } else {
/* 339 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 340 */         int i = Float.floatToIntBits(paramArrayOffloat[paramInt1 + b1]);
/* 341 */         arrayOfByte[b++] = (byte)(i >>> 0);
/* 342 */         arrayOfByte[b++] = (byte)(i >>> 8);
/* 343 */         arrayOfByte[b++] = (byte)(i >>> 16);
/* 344 */         arrayOfByte[b++] = (byte)(i >>> 24);
/*     */       } 
/*     */     } 
/*     */     
/* 348 */     write(arrayOfByte, 0, paramInt2 * 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[] paramArrayOfdouble, int paramInt1, int paramInt2) throws IOException {
/* 353 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfdouble.length || paramInt1 + paramInt2 < 0) {
/* 354 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > d.length!");
/*     */     }
/*     */ 
/*     */     
/* 358 */     byte[] arrayOfByte = new byte[paramInt2 * 8];
/* 359 */     byte b = 0;
/* 360 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 361 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 362 */         long l = Double.doubleToLongBits(paramArrayOfdouble[paramInt1 + b1]);
/* 363 */         arrayOfByte[b++] = (byte)(int)(l >>> 56L);
/* 364 */         arrayOfByte[b++] = (byte)(int)(l >>> 48L);
/* 365 */         arrayOfByte[b++] = (byte)(int)(l >>> 40L);
/* 366 */         arrayOfByte[b++] = (byte)(int)(l >>> 32L);
/* 367 */         arrayOfByte[b++] = (byte)(int)(l >>> 24L);
/* 368 */         arrayOfByte[b++] = (byte)(int)(l >>> 16L);
/* 369 */         arrayOfByte[b++] = (byte)(int)(l >>> 8L);
/* 370 */         arrayOfByte[b++] = (byte)(int)(l >>> 0L);
/*     */       } 
/*     */     } else {
/* 373 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 374 */         long l = Double.doubleToLongBits(paramArrayOfdouble[paramInt1 + b1]);
/* 375 */         arrayOfByte[b++] = (byte)(int)(l >>> 0L);
/* 376 */         arrayOfByte[b++] = (byte)(int)(l >>> 8L);
/* 377 */         arrayOfByte[b++] = (byte)(int)(l >>> 16L);
/* 378 */         arrayOfByte[b++] = (byte)(int)(l >>> 24L);
/* 379 */         arrayOfByte[b++] = (byte)(int)(l >>> 32L);
/* 380 */         arrayOfByte[b++] = (byte)(int)(l >>> 40L);
/* 381 */         arrayOfByte[b++] = (byte)(int)(l >>> 48L);
/* 382 */         arrayOfByte[b++] = (byte)(int)(l >>> 56L);
/*     */       } 
/*     */     } 
/*     */     
/* 386 */     write(arrayOfByte, 0, paramInt2 * 8);
/*     */   }
/*     */   
/*     */   public void writeBit(int paramInt) throws IOException {
/* 390 */     writeBits(0x1L & paramInt, 1);
/*     */   }
/*     */   
/*     */   public void writeBits(long paramLong, int paramInt) throws IOException {
/* 394 */     checkClosed();
/*     */     
/* 396 */     if (paramInt < 0 || paramInt > 64) {
/* 397 */       throw new IllegalArgumentException("Bad value for numBits!");
/*     */     }
/* 399 */     if (paramInt == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 407 */     if (getStreamPosition() > 0L || this.bitOffset > 0) {
/* 408 */       int i = this.bitOffset;
/* 409 */       int j = read();
/* 410 */       if (j != -1) {
/* 411 */         seek(getStreamPosition() - 1L);
/*     */       } else {
/* 413 */         j = 0;
/*     */       } 
/*     */       
/* 416 */       if (paramInt + i < 8) {
/*     */         
/* 418 */         int k = 8 - i + paramInt;
/* 419 */         int m = -1 >>> 32 - paramInt;
/* 420 */         j &= m << k ^ 0xFFFFFFFF;
/* 421 */         j = (int)(j | (paramLong & m) << k);
/* 422 */         write(j);
/* 423 */         seek(getStreamPosition() - 1L);
/* 424 */         this.bitOffset = i + paramInt;
/* 425 */         paramInt = 0;
/*     */       } else {
/*     */         
/* 428 */         int k = 8 - i;
/* 429 */         int m = -1 >>> 32 - k;
/* 430 */         j &= m ^ 0xFFFFFFFF;
/* 431 */         j = (int)(j | paramLong >> paramInt - k & m);
/*     */ 
/*     */         
/* 434 */         write(j);
/* 435 */         paramInt -= k;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 440 */     if (paramInt > 7) {
/* 441 */       int i = paramInt % 8;
/* 442 */       for (int j = paramInt / 8; j > 0; j--) {
/* 443 */         int k = (j - 1) * 8 + i;
/* 444 */         int m = (int)((k == 0) ? (paramLong & 0xFFL) : (paramLong >> k & 0xFFL));
/*     */ 
/*     */         
/* 447 */         write(m);
/*     */       } 
/* 449 */       paramInt = i;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 455 */     if (paramInt != 0) {
/*     */ 
/*     */       
/* 458 */       int i = 0;
/* 459 */       i = read();
/* 460 */       if (i != -1) {
/* 461 */         seek(getStreamPosition() - 1L);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 466 */         i = 0;
/*     */       } 
/*     */       
/* 469 */       int j = 8 - paramInt;
/* 470 */       int k = -1 >>> 32 - paramInt;
/* 471 */       i &= k << j ^ 0xFFFFFFFF;
/* 472 */       i = (int)(i | (paramLong & k) << j);
/*     */       
/* 474 */       write(i);
/* 475 */       seek(getStreamPosition() - 1L);
/* 476 */       this.bitOffset = paramInt;
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
/*     */   protected final void flushBits() throws IOException {
/* 490 */     checkClosed();
/* 491 */     if (this.bitOffset != 0) {
/* 492 */       int i = this.bitOffset;
/* 493 */       int j = read();
/* 494 */       if (j < 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 499 */         j = 0;
/* 500 */         this.bitOffset = 0;
/*     */       } else {
/*     */         
/* 503 */         seek(getStreamPosition() - 1L);
/* 504 */         j &= -1 << 8 - i;
/*     */       } 
/* 506 */       write(j);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/stream/ImageOutputStreamImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */