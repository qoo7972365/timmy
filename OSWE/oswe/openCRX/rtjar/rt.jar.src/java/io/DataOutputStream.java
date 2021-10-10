/*     */ package java.io;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataOutputStream
/*     */   extends FilterOutputStream
/*     */   implements DataOutput
/*     */ {
/*     */   protected int written;
/*  48 */   private byte[] bytearr = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] writeBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataOutputStream(OutputStream paramOutputStream) {
/*  60 */     super(paramOutputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     this.writeBuffer = new byte[8];
/*     */   }
/*     */   
/*     */   private void incCount(int paramInt) {
/*     */     int i = this.written + paramInt;
/*     */     if (i < 0) {
/*     */       i = Integer.MAX_VALUE;
/*     */     }
/*     */     this.written = i;
/*     */   }
/*     */   
/*     */   public final void writeLong(long paramLong) throws IOException {
/* 216 */     this.writeBuffer[0] = (byte)(int)(paramLong >>> 56L);
/* 217 */     this.writeBuffer[1] = (byte)(int)(paramLong >>> 48L);
/* 218 */     this.writeBuffer[2] = (byte)(int)(paramLong >>> 40L);
/* 219 */     this.writeBuffer[3] = (byte)(int)(paramLong >>> 32L);
/* 220 */     this.writeBuffer[4] = (byte)(int)(paramLong >>> 24L);
/* 221 */     this.writeBuffer[5] = (byte)(int)(paramLong >>> 16L);
/* 222 */     this.writeBuffer[6] = (byte)(int)(paramLong >>> 8L);
/* 223 */     this.writeBuffer[7] = (byte)(int)(paramLong >>> 0L);
/* 224 */     this.out.write(this.writeBuffer, 0, 8);
/* 225 */     incCount(8);
/*     */   } public synchronized void write(int paramInt) throws IOException {
/*     */     this.out.write(paramInt);
/*     */     incCount(1);
/*     */   } public synchronized void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*     */     this.out.write(paramArrayOfbyte, paramInt1, paramInt2);
/*     */     incCount(paramInt2);
/*     */   } public void flush() throws IOException {
/*     */     this.out.flush();
/*     */   } public final void writeBoolean(boolean paramBoolean) throws IOException {
/*     */     this.out.write(paramBoolean ? 1 : 0);
/*     */     incCount(1);
/*     */   } public final void writeByte(int paramInt) throws IOException {
/*     */     this.out.write(paramInt);
/*     */     incCount(1);
/*     */   }
/*     */   public final void writeFloat(float paramFloat) throws IOException {
/* 242 */     writeInt(Float.floatToIntBits(paramFloat));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void writeShort(int paramInt) throws IOException {
/*     */     this.out.write(paramInt >>> 8 & 0xFF);
/*     */     this.out.write(paramInt >>> 0 & 0xFF);
/*     */     incCount(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void writeDouble(double paramDouble) throws IOException {
/* 259 */     writeLong(Double.doubleToLongBits(paramDouble));
/*     */   }
/*     */   public final void writeChar(int paramInt) throws IOException {
/*     */     this.out.write(paramInt >>> 8 & 0xFF);
/*     */     this.out.write(paramInt >>> 0 & 0xFF);
/*     */     incCount(2);
/*     */   }
/*     */   public final void writeInt(int paramInt) throws IOException {
/*     */     this.out.write(paramInt >>> 24 & 0xFF);
/*     */     this.out.write(paramInt >>> 16 & 0xFF);
/*     */     this.out.write(paramInt >>> 8 & 0xFF);
/*     */     this.out.write(paramInt >>> 0 & 0xFF);
/*     */     incCount(4);
/*     */   }
/*     */   public final void writeBytes(String paramString) throws IOException {
/* 274 */     int i = paramString.length();
/* 275 */     for (byte b = 0; b < i; b++) {
/* 276 */       this.out.write((byte)paramString.charAt(b));
/*     */     }
/* 278 */     incCount(i);
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
/*     */   public final void writeChars(String paramString) throws IOException {
/* 294 */     int i = paramString.length();
/* 295 */     for (byte b = 0; b < i; b++) {
/* 296 */       char c = paramString.charAt(b);
/* 297 */       this.out.write(c >>> 8 & 0xFF);
/* 298 */       this.out.write(c >>> 0 & 0xFF);
/*     */     } 
/* 300 */     incCount(i * 2);
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
/*     */   public final void writeUTF(String paramString) throws IOException {
/* 323 */     writeUTF(paramString, this);
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
/*     */   static int writeUTF(String paramString, DataOutput paramDataOutput) throws IOException {
/* 347 */     int i = paramString.length();
/* 348 */     byte b1 = 0;
/* 349 */     byte b2 = 0;
/*     */ 
/*     */     
/* 352 */     for (byte b3 = 0; b3 < i; b3++) {
/* 353 */       char c = paramString.charAt(b3);
/* 354 */       if (c >= '\001' && c <= '') {
/* 355 */         b1++;
/* 356 */       } else if (c > '߿') {
/* 357 */         b1 += 3;
/*     */       } else {
/* 359 */         b1 += 2;
/*     */       } 
/*     */     } 
/*     */     
/* 363 */     if (b1 > '￿') {
/* 364 */       throw new UTFDataFormatException("encoded string too long: " + b1 + " bytes");
/*     */     }
/*     */     
/* 367 */     byte[] arrayOfByte = null;
/* 368 */     if (paramDataOutput instanceof DataOutputStream) {
/* 369 */       DataOutputStream dataOutputStream = (DataOutputStream)paramDataOutput;
/* 370 */       if (dataOutputStream.bytearr == null || dataOutputStream.bytearr.length < b1 + 2)
/* 371 */         dataOutputStream.bytearr = new byte[b1 * 2 + 2]; 
/* 372 */       arrayOfByte = dataOutputStream.bytearr;
/*     */     } else {
/* 374 */       arrayOfByte = new byte[b1 + 2];
/*     */     } 
/*     */     
/* 377 */     arrayOfByte[b2++] = (byte)(b1 >>> 8 & 0xFF);
/* 378 */     arrayOfByte[b2++] = (byte)(b1 >>> 0 & 0xFF);
/*     */     
/* 380 */     byte b4 = 0;
/* 381 */     for (b4 = 0; b4 < i; b4++) {
/* 382 */       char c = paramString.charAt(b4);
/* 383 */       if (c < '\001' || c > '')
/* 384 */         break;  arrayOfByte[b2++] = (byte)c;
/*     */     } 
/*     */     
/* 387 */     for (; b4 < i; b4++) {
/* 388 */       char c = paramString.charAt(b4);
/* 389 */       if (c >= '\001' && c <= '') {
/* 390 */         arrayOfByte[b2++] = (byte)c;
/*     */       }
/* 392 */       else if (c > '߿') {
/* 393 */         arrayOfByte[b2++] = (byte)(0xE0 | c >> 12 & 0xF);
/* 394 */         arrayOfByte[b2++] = (byte)(0x80 | c >> 6 & 0x3F);
/* 395 */         arrayOfByte[b2++] = (byte)(0x80 | c >> 0 & 0x3F);
/*     */       } else {
/* 397 */         arrayOfByte[b2++] = (byte)(0xC0 | c >> 6 & 0x1F);
/* 398 */         arrayOfByte[b2++] = (byte)(0x80 | c >> 0 & 0x3F);
/*     */       } 
/*     */     } 
/* 401 */     paramDataOutput.write(arrayOfByte, 0, b1 + 2);
/* 402 */     return b1 + 2;
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
/*     */   public final int size() {
/* 414 */     return this.written;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/DataOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */