/*     */ package javax.imageio.stream;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Stack;
/*     */ import javax.imageio.IIOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ImageInputStreamImpl
/*     */   implements ImageInputStream
/*     */ {
/*  46 */   private Stack markByteStack = new Stack();
/*     */   
/*  48 */   private Stack markBitStack = new Stack();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isClosed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BYTE_BUF_LENGTH = 8192;
/*     */ 
/*     */ 
/*     */   
/*  62 */   byte[] byteBuf = new byte[8192];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   protected ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long streamPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int bitOffset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   protected long flushedPos = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void checkClosed() throws IOException {
/* 109 */     if (this.isClosed) {
/* 110 */       throw new IOException("closed");
/*     */     }
/*     */   }
/*     */   
/*     */   public void setByteOrder(ByteOrder paramByteOrder) {
/* 115 */     this.byteOrder = paramByteOrder;
/*     */   }
/*     */   
/*     */   public ByteOrder getByteOrder() {
/* 119 */     return this.byteOrder;
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
/*     */   public abstract int read() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] paramArrayOfbyte) throws IOException {
/* 155 */     return read(paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*     */   public abstract int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(IIOByteBuffer paramIIOByteBuffer, int paramInt) throws IOException {
/* 188 */     if (paramInt < 0) {
/* 189 */       throw new IndexOutOfBoundsException("len < 0!");
/*     */     }
/* 191 */     if (paramIIOByteBuffer == null) {
/* 192 */       throw new NullPointerException("buf == null!");
/*     */     }
/*     */     
/* 195 */     byte[] arrayOfByte = new byte[paramInt];
/* 196 */     paramInt = read(arrayOfByte, 0, paramInt);
/*     */     
/* 198 */     paramIIOByteBuffer.setData(arrayOfByte);
/* 199 */     paramIIOByteBuffer.setOffset(0);
/* 200 */     paramIIOByteBuffer.setLength(paramInt);
/*     */   }
/*     */   
/*     */   public boolean readBoolean() throws IOException {
/* 204 */     int i = read();
/* 205 */     if (i < 0) {
/* 206 */       throw new EOFException();
/*     */     }
/* 208 */     return (i != 0);
/*     */   }
/*     */   
/*     */   public byte readByte() throws IOException {
/* 212 */     int i = read();
/* 213 */     if (i < 0) {
/* 214 */       throw new EOFException();
/*     */     }
/* 216 */     return (byte)i;
/*     */   }
/*     */   
/*     */   public int readUnsignedByte() throws IOException {
/* 220 */     int i = read();
/* 221 */     if (i < 0) {
/* 222 */       throw new EOFException();
/*     */     }
/* 224 */     return i;
/*     */   }
/*     */   
/*     */   public short readShort() throws IOException {
/* 228 */     if (read(this.byteBuf, 0, 2) != 2) {
/* 229 */       throw new EOFException();
/*     */     }
/*     */     
/* 232 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 233 */       return (short)((this.byteBuf[0] & 0xFF) << 8 | (this.byteBuf[1] & 0xFF) << 0);
/*     */     }
/*     */     
/* 236 */     return (short)((this.byteBuf[1] & 0xFF) << 8 | (this.byteBuf[0] & 0xFF) << 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int readUnsignedShort() throws IOException {
/* 242 */     return readShort() & 0xFFFF;
/*     */   }
/*     */   
/*     */   public char readChar() throws IOException {
/* 246 */     return (char)readShort();
/*     */   }
/*     */   
/*     */   public int readInt() throws IOException {
/* 250 */     if (read(this.byteBuf, 0, 4) != 4) {
/* 251 */       throw new EOFException();
/*     */     }
/*     */     
/* 254 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 255 */       return (this.byteBuf[0] & 0xFF) << 24 | (this.byteBuf[1] & 0xFF) << 16 | (this.byteBuf[2] & 0xFF) << 8 | (this.byteBuf[3] & 0xFF) << 0;
/*     */     }
/*     */ 
/*     */     
/* 259 */     return (this.byteBuf[3] & 0xFF) << 24 | (this.byteBuf[2] & 0xFF) << 16 | (this.byteBuf[1] & 0xFF) << 8 | (this.byteBuf[0] & 0xFF) << 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long readUnsignedInt() throws IOException {
/* 266 */     return readInt() & 0xFFFFFFFFL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long readLong() throws IOException {
/* 273 */     int i = readInt();
/* 274 */     int j = readInt();
/*     */     
/* 276 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 277 */       return (i << 32L) + (j & 0xFFFFFFFFL);
/*     */     }
/* 279 */     return (j << 32L) + (i & 0xFFFFFFFFL);
/*     */   }
/*     */ 
/*     */   
/*     */   public float readFloat() throws IOException {
/* 284 */     return Float.intBitsToFloat(readInt());
/*     */   }
/*     */   
/*     */   public double readDouble() throws IOException {
/* 288 */     return Double.longBitsToDouble(readLong());
/*     */   }
/*     */   
/*     */   public String readLine() throws IOException {
/* 292 */     StringBuffer stringBuffer = new StringBuffer();
/* 293 */     int i = -1;
/* 294 */     boolean bool = false;
/*     */     
/* 296 */     while (!bool) {
/* 297 */       long l; switch (i = read()) {
/*     */         case -1:
/*     */         case 10:
/* 300 */           bool = true;
/*     */           continue;
/*     */         case 13:
/* 303 */           bool = true;
/* 304 */           l = getStreamPosition();
/* 305 */           if (read() != 10) {
/* 306 */             seek(l);
/*     */           }
/*     */           continue;
/*     */       } 
/* 310 */       stringBuffer.append((char)i);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 315 */     if (i == -1 && stringBuffer.length() == 0) {
/* 316 */       return null;
/*     */     }
/* 318 */     return stringBuffer.toString();
/*     */   }
/*     */   public String readUTF() throws IOException {
/*     */     String str;
/* 322 */     this.bitOffset = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 327 */     ByteOrder byteOrder = getByteOrder();
/* 328 */     setByteOrder(ByteOrder.BIG_ENDIAN);
/*     */ 
/*     */     
/*     */     try {
/* 332 */       str = DataInputStream.readUTF(this);
/* 333 */     } catch (IOException iOException) {
/*     */       
/* 335 */       setByteOrder(byteOrder);
/* 336 */       throw iOException;
/*     */     } 
/*     */     
/* 339 */     setByteOrder(byteOrder);
/* 340 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 345 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfbyte.length || paramInt1 + paramInt2 < 0) {
/* 346 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > b.length!");
/*     */     }
/*     */ 
/*     */     
/* 350 */     while (paramInt2 > 0) {
/* 351 */       int i = read(paramArrayOfbyte, paramInt1, paramInt2);
/* 352 */       if (i == -1) {
/* 353 */         throw new EOFException();
/*     */       }
/* 355 */       paramInt1 += i;
/* 356 */       paramInt2 -= i;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void readFully(byte[] paramArrayOfbyte) throws IOException {
/* 361 */     readFully(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(short[] paramArrayOfshort, int paramInt1, int paramInt2) throws IOException {
/* 366 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfshort.length || paramInt1 + paramInt2 < 0) {
/* 367 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > s.length!");
/*     */     }
/*     */ 
/*     */     
/* 371 */     while (paramInt2 > 0) {
/* 372 */       int i = Math.min(paramInt2, this.byteBuf.length / 2);
/* 373 */       readFully(this.byteBuf, 0, i * 2);
/* 374 */       toShorts(this.byteBuf, paramArrayOfshort, paramInt1, i);
/* 375 */       paramInt1 += i;
/* 376 */       paramInt2 -= i;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/* 382 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfchar.length || paramInt1 + paramInt2 < 0) {
/* 383 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > c.length!");
/*     */     }
/*     */ 
/*     */     
/* 387 */     while (paramInt2 > 0) {
/* 388 */       int i = Math.min(paramInt2, this.byteBuf.length / 2);
/* 389 */       readFully(this.byteBuf, 0, i * 2);
/* 390 */       toChars(this.byteBuf, paramArrayOfchar, paramInt1, i);
/* 391 */       paramInt1 += i;
/* 392 */       paramInt2 -= i;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException {
/* 398 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfint.length || paramInt1 + paramInt2 < 0) {
/* 399 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > i.length!");
/*     */     }
/*     */ 
/*     */     
/* 403 */     while (paramInt2 > 0) {
/* 404 */       int i = Math.min(paramInt2, this.byteBuf.length / 4);
/* 405 */       readFully(this.byteBuf, 0, i * 4);
/* 406 */       toInts(this.byteBuf, paramArrayOfint, paramInt1, i);
/* 407 */       paramInt1 += i;
/* 408 */       paramInt2 -= i;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(long[] paramArrayOflong, int paramInt1, int paramInt2) throws IOException {
/* 414 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOflong.length || paramInt1 + paramInt2 < 0) {
/* 415 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > l.length!");
/*     */     }
/*     */ 
/*     */     
/* 419 */     while (paramInt2 > 0) {
/* 420 */       int i = Math.min(paramInt2, this.byteBuf.length / 8);
/* 421 */       readFully(this.byteBuf, 0, i * 8);
/* 422 */       toLongs(this.byteBuf, paramArrayOflong, paramInt1, i);
/* 423 */       paramInt1 += i;
/* 424 */       paramInt2 -= i;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(float[] paramArrayOffloat, int paramInt1, int paramInt2) throws IOException {
/* 430 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOffloat.length || paramInt1 + paramInt2 < 0) {
/* 431 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > f.length!");
/*     */     }
/*     */ 
/*     */     
/* 435 */     while (paramInt2 > 0) {
/* 436 */       int i = Math.min(paramInt2, this.byteBuf.length / 4);
/* 437 */       readFully(this.byteBuf, 0, i * 4);
/* 438 */       toFloats(this.byteBuf, paramArrayOffloat, paramInt1, i);
/* 439 */       paramInt1 += i;
/* 440 */       paramInt2 -= i;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(double[] paramArrayOfdouble, int paramInt1, int paramInt2) throws IOException {
/* 446 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfdouble.length || paramInt1 + paramInt2 < 0) {
/* 447 */       throw new IndexOutOfBoundsException("off < 0 || len < 0 || off + len > d.length!");
/*     */     }
/*     */ 
/*     */     
/* 451 */     while (paramInt2 > 0) {
/* 452 */       int i = Math.min(paramInt2, this.byteBuf.length / 8);
/* 453 */       readFully(this.byteBuf, 0, i * 8);
/* 454 */       toDoubles(this.byteBuf, paramArrayOfdouble, paramInt1, i);
/* 455 */       paramInt1 += i;
/* 456 */       paramInt2 -= i;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void toShorts(byte[] paramArrayOfbyte, short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 461 */     byte b = 0;
/* 462 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 463 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 464 */         byte b2 = paramArrayOfbyte[b];
/* 465 */         int i = paramArrayOfbyte[b + 1] & 0xFF;
/* 466 */         paramArrayOfshort[paramInt1 + b1] = (short)(b2 << 8 | i);
/* 467 */         b += 2;
/*     */       } 
/*     */     } else {
/* 470 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 471 */         byte b2 = paramArrayOfbyte[b + 1];
/* 472 */         int i = paramArrayOfbyte[b] & 0xFF;
/* 473 */         paramArrayOfshort[paramInt1 + b1] = (short)(b2 << 8 | i);
/* 474 */         b += 2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void toChars(byte[] paramArrayOfbyte, char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 480 */     byte b = 0;
/* 481 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 482 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 483 */         byte b2 = paramArrayOfbyte[b];
/* 484 */         int i = paramArrayOfbyte[b + 1] & 0xFF;
/* 485 */         paramArrayOfchar[paramInt1 + b1] = (char)(b2 << 8 | i);
/* 486 */         b += 2;
/*     */       } 
/*     */     } else {
/* 489 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 490 */         byte b2 = paramArrayOfbyte[b + 1];
/* 491 */         int i = paramArrayOfbyte[b] & 0xFF;
/* 492 */         paramArrayOfchar[paramInt1 + b1] = (char)(b2 << 8 | i);
/* 493 */         b += 2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void toInts(byte[] paramArrayOfbyte, int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 499 */     byte b = 0;
/* 500 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 501 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 502 */         byte b2 = paramArrayOfbyte[b];
/* 503 */         int i = paramArrayOfbyte[b + 1] & 0xFF;
/* 504 */         int j = paramArrayOfbyte[b + 2] & 0xFF;
/* 505 */         int k = paramArrayOfbyte[b + 3] & 0xFF;
/* 506 */         paramArrayOfint[paramInt1 + b1] = b2 << 24 | i << 16 | j << 8 | k;
/* 507 */         b += 4;
/*     */       } 
/*     */     } else {
/* 510 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 511 */         byte b2 = paramArrayOfbyte[b + 3];
/* 512 */         int i = paramArrayOfbyte[b + 2] & 0xFF;
/* 513 */         int j = paramArrayOfbyte[b + 1] & 0xFF;
/* 514 */         int k = paramArrayOfbyte[b] & 0xFF;
/* 515 */         paramArrayOfint[paramInt1 + b1] = b2 << 24 | i << 16 | j << 8 | k;
/* 516 */         b += 4;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void toLongs(byte[] paramArrayOfbyte, long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 522 */     byte b = 0;
/* 523 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 524 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 525 */         byte b2 = paramArrayOfbyte[b];
/* 526 */         int i = paramArrayOfbyte[b + 1] & 0xFF;
/* 527 */         int j = paramArrayOfbyte[b + 2] & 0xFF;
/* 528 */         int k = paramArrayOfbyte[b + 3] & 0xFF;
/* 529 */         byte b3 = paramArrayOfbyte[b + 4];
/* 530 */         int m = paramArrayOfbyte[b + 5] & 0xFF;
/* 531 */         int n = paramArrayOfbyte[b + 6] & 0xFF;
/* 532 */         int i1 = paramArrayOfbyte[b + 7] & 0xFF;
/*     */         
/* 534 */         int i2 = b2 << 24 | i << 16 | j << 8 | k;
/* 535 */         int i3 = b3 << 24 | m << 16 | n << 8 | i1;
/*     */         
/* 537 */         paramArrayOflong[paramInt1 + b1] = i2 << 32L | i3 & 0xFFFFFFFFL;
/* 538 */         b += 8;
/*     */       } 
/*     */     } else {
/* 541 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 542 */         byte b2 = paramArrayOfbyte[b + 7];
/* 543 */         int i = paramArrayOfbyte[b + 6] & 0xFF;
/* 544 */         int j = paramArrayOfbyte[b + 5] & 0xFF;
/* 545 */         int k = paramArrayOfbyte[b + 4] & 0xFF;
/* 546 */         byte b3 = paramArrayOfbyte[b + 3];
/* 547 */         int m = paramArrayOfbyte[b + 2] & 0xFF;
/* 548 */         int n = paramArrayOfbyte[b + 1] & 0xFF;
/* 549 */         int i1 = paramArrayOfbyte[b] & 0xFF;
/*     */         
/* 551 */         int i2 = b2 << 24 | i << 16 | j << 8 | k;
/* 552 */         int i3 = b3 << 24 | m << 16 | n << 8 | i1;
/*     */         
/* 554 */         paramArrayOflong[paramInt1 + b1] = i2 << 32L | i3 & 0xFFFFFFFFL;
/* 555 */         b += 8;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void toFloats(byte[] paramArrayOfbyte, float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/* 561 */     byte b = 0;
/* 562 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 563 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 564 */         byte b2 = paramArrayOfbyte[b];
/* 565 */         int i = paramArrayOfbyte[b + 1] & 0xFF;
/* 566 */         int j = paramArrayOfbyte[b + 2] & 0xFF;
/* 567 */         int k = paramArrayOfbyte[b + 3] & 0xFF;
/* 568 */         int m = b2 << 24 | i << 16 | j << 8 | k;
/* 569 */         paramArrayOffloat[paramInt1 + b1] = Float.intBitsToFloat(m);
/* 570 */         b += 4;
/*     */       } 
/*     */     } else {
/* 573 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 574 */         byte b2 = paramArrayOfbyte[b + 3];
/* 575 */         int i = paramArrayOfbyte[b + 2] & 0xFF;
/* 576 */         int j = paramArrayOfbyte[b + 1] & 0xFF;
/* 577 */         int k = paramArrayOfbyte[b + 0] & 0xFF;
/* 578 */         int m = b2 << 24 | i << 16 | j << 8 | k;
/* 579 */         paramArrayOffloat[paramInt1 + b1] = Float.intBitsToFloat(m);
/* 580 */         b += 4;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void toDoubles(byte[] paramArrayOfbyte, double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/* 586 */     byte b = 0;
/* 587 */     if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
/* 588 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 589 */         byte b2 = paramArrayOfbyte[b];
/* 590 */         int i = paramArrayOfbyte[b + 1] & 0xFF;
/* 591 */         int j = paramArrayOfbyte[b + 2] & 0xFF;
/* 592 */         int k = paramArrayOfbyte[b + 3] & 0xFF;
/* 593 */         byte b3 = paramArrayOfbyte[b + 4];
/* 594 */         int m = paramArrayOfbyte[b + 5] & 0xFF;
/* 595 */         int n = paramArrayOfbyte[b + 6] & 0xFF;
/* 596 */         int i1 = paramArrayOfbyte[b + 7] & 0xFF;
/*     */         
/* 598 */         int i2 = b2 << 24 | i << 16 | j << 8 | k;
/* 599 */         int i3 = b3 << 24 | m << 16 | n << 8 | i1;
/* 600 */         long l = i2 << 32L | i3 & 0xFFFFFFFFL;
/*     */         
/* 602 */         paramArrayOfdouble[paramInt1 + b1] = Double.longBitsToDouble(l);
/* 603 */         b += 8;
/*     */       } 
/*     */     } else {
/* 606 */       for (byte b1 = 0; b1 < paramInt2; b1++) {
/* 607 */         byte b2 = paramArrayOfbyte[b + 7];
/* 608 */         int i = paramArrayOfbyte[b + 6] & 0xFF;
/* 609 */         int j = paramArrayOfbyte[b + 5] & 0xFF;
/* 610 */         int k = paramArrayOfbyte[b + 4] & 0xFF;
/* 611 */         byte b3 = paramArrayOfbyte[b + 3];
/* 612 */         int m = paramArrayOfbyte[b + 2] & 0xFF;
/* 613 */         int n = paramArrayOfbyte[b + 1] & 0xFF;
/* 614 */         int i1 = paramArrayOfbyte[b] & 0xFF;
/*     */         
/* 616 */         int i2 = b2 << 24 | i << 16 | j << 8 | k;
/* 617 */         int i3 = b3 << 24 | m << 16 | n << 8 | i1;
/* 618 */         long l = i2 << 32L | i3 & 0xFFFFFFFFL;
/*     */         
/* 620 */         paramArrayOfdouble[paramInt1 + b1] = Double.longBitsToDouble(l);
/* 621 */         b += 8;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getStreamPosition() throws IOException {
/* 627 */     checkClosed();
/* 628 */     return this.streamPos;
/*     */   }
/*     */   
/*     */   public int getBitOffset() throws IOException {
/* 632 */     checkClosed();
/* 633 */     return this.bitOffset;
/*     */   }
/*     */   
/*     */   public void setBitOffset(int paramInt) throws IOException {
/* 637 */     checkClosed();
/* 638 */     if (paramInt < 0 || paramInt > 7) {
/* 639 */       throw new IllegalArgumentException("bitOffset must be betwwen 0 and 7!");
/*     */     }
/* 641 */     this.bitOffset = paramInt;
/*     */   }
/*     */   
/*     */   public int readBit() throws IOException {
/* 645 */     checkClosed();
/*     */ 
/*     */     
/* 648 */     int i = this.bitOffset + 1 & 0x7;
/*     */     
/* 650 */     int j = read();
/* 651 */     if (j == -1) {
/* 652 */       throw new EOFException();
/*     */     }
/*     */     
/* 655 */     if (i != 0) {
/*     */       
/* 657 */       seek(getStreamPosition() - 1L);
/*     */       
/* 659 */       j >>= 8 - i;
/*     */     } 
/* 661 */     this.bitOffset = i;
/*     */     
/* 663 */     return j & 0x1;
/*     */   }
/*     */   
/*     */   public long readBits(int paramInt) throws IOException {
/* 667 */     checkClosed();
/*     */     
/* 669 */     if (paramInt < 0 || paramInt > 64) {
/* 670 */       throw new IllegalArgumentException();
/*     */     }
/* 672 */     if (paramInt == 0) {
/* 673 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/* 677 */     int i = paramInt + this.bitOffset;
/*     */ 
/*     */     
/* 680 */     int j = this.bitOffset + paramInt & 0x7;
/*     */ 
/*     */     
/* 683 */     long l = 0L;
/* 684 */     while (i > 0) {
/* 685 */       int k = read();
/* 686 */       if (k == -1) {
/* 687 */         throw new EOFException();
/*     */       }
/*     */       
/* 690 */       l <<= 8L;
/* 691 */       l |= k;
/* 692 */       i -= 8;
/*     */     } 
/*     */ 
/*     */     
/* 696 */     if (j != 0) {
/* 697 */       seek(getStreamPosition() - 1L);
/*     */     }
/* 699 */     this.bitOffset = j;
/*     */ 
/*     */     
/* 702 */     l >>>= -i;
/*     */ 
/*     */     
/* 705 */     l &= -1L >>> 64 - paramInt;
/*     */     
/* 707 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long length() {
/* 718 */     return -1L;
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
/*     */   public int skipBytes(int paramInt) throws IOException {
/* 737 */     long l = getStreamPosition();
/* 738 */     seek(l + paramInt);
/* 739 */     return (int)(getStreamPosition() - l);
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
/*     */   public long skipBytes(long paramLong) throws IOException {
/* 758 */     long l = getStreamPosition();
/* 759 */     seek(l + paramLong);
/* 760 */     return getStreamPosition() - l;
/*     */   }
/*     */   
/*     */   public void seek(long paramLong) throws IOException {
/* 764 */     checkClosed();
/*     */ 
/*     */     
/* 767 */     if (paramLong < this.flushedPos) {
/* 768 */       throw new IndexOutOfBoundsException("pos < flushedPos!");
/*     */     }
/*     */     
/* 771 */     this.streamPos = paramLong;
/* 772 */     this.bitOffset = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mark() {
/*     */     try {
/* 781 */       this.markByteStack.push(Long.valueOf(getStreamPosition()));
/* 782 */       this.markBitStack.push(Integer.valueOf(getBitOffset()));
/* 783 */     } catch (IOException iOException) {}
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
/*     */   public void reset() throws IOException {
/* 797 */     if (this.markByteStack.empty()) {
/*     */       return;
/*     */     }
/*     */     
/* 801 */     long l = ((Long)this.markByteStack.pop()).longValue();
/* 802 */     if (l < this.flushedPos) {
/* 803 */       throw new IIOException("Previous marked position has been discarded!");
/*     */     }
/*     */     
/* 806 */     seek(l);
/*     */     
/* 808 */     int i = ((Integer)this.markBitStack.pop()).intValue();
/* 809 */     setBitOffset(i);
/*     */   }
/*     */   
/*     */   public void flushBefore(long paramLong) throws IOException {
/* 813 */     checkClosed();
/* 814 */     if (paramLong < this.flushedPos) {
/* 815 */       throw new IndexOutOfBoundsException("pos < flushedPos!");
/*     */     }
/* 817 */     if (paramLong > getStreamPosition()) {
/* 818 */       throw new IndexOutOfBoundsException("pos > getStreamPosition()!");
/*     */     }
/*     */     
/* 821 */     this.flushedPos = paramLong;
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/* 825 */     flushBefore(getStreamPosition());
/*     */   }
/*     */   
/*     */   public long getFlushedPosition() {
/* 829 */     return this.flushedPos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCached() {
/* 837 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCachedMemory() {
/* 845 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCachedFile() {
/* 853 */     return false;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 857 */     checkClosed();
/*     */     
/* 859 */     this.isClosed = true;
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
/*     */   protected void finalize() throws Throwable {
/* 872 */     if (!this.isClosed) {
/*     */       try {
/* 874 */         close();
/* 875 */       } catch (IOException iOException) {}
/*     */     }
/*     */     
/* 878 */     super.finalize();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/stream/ImageInputStreamImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */