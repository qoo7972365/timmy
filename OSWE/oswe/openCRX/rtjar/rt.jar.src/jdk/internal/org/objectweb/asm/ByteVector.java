/*     */ package jdk.internal.org.objectweb.asm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteVector
/*     */ {
/*     */   byte[] data;
/*     */   int length;
/*     */   
/*     */   public ByteVector() {
/*  84 */     this.data = new byte[64];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteVector(int paramInt) {
/*  95 */     this.data = new byte[paramInt];
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
/*     */   public ByteVector putByte(int paramInt) {
/* 107 */     int i = this.length;
/* 108 */     if (i + 1 > this.data.length) {
/* 109 */       enlarge(1);
/*     */     }
/* 111 */     this.data[i++] = (byte)paramInt;
/* 112 */     this.length = i;
/* 113 */     return this;
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
/*     */   ByteVector put11(int paramInt1, int paramInt2) {
/* 127 */     int i = this.length;
/* 128 */     if (i + 2 > this.data.length) {
/* 129 */       enlarge(2);
/*     */     }
/* 131 */     byte[] arrayOfByte = this.data;
/* 132 */     arrayOfByte[i++] = (byte)paramInt1;
/* 133 */     arrayOfByte[i++] = (byte)paramInt2;
/* 134 */     this.length = i;
/* 135 */     return this;
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
/*     */   public ByteVector putShort(int paramInt) {
/* 147 */     int i = this.length;
/* 148 */     if (i + 2 > this.data.length) {
/* 149 */       enlarge(2);
/*     */     }
/* 151 */     byte[] arrayOfByte = this.data;
/* 152 */     arrayOfByte[i++] = (byte)(paramInt >>> 8);
/* 153 */     arrayOfByte[i++] = (byte)paramInt;
/* 154 */     this.length = i;
/* 155 */     return this;
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
/*     */   ByteVector put12(int paramInt1, int paramInt2) {
/* 169 */     int i = this.length;
/* 170 */     if (i + 3 > this.data.length) {
/* 171 */       enlarge(3);
/*     */     }
/* 173 */     byte[] arrayOfByte = this.data;
/* 174 */     arrayOfByte[i++] = (byte)paramInt1;
/* 175 */     arrayOfByte[i++] = (byte)(paramInt2 >>> 8);
/* 176 */     arrayOfByte[i++] = (byte)paramInt2;
/* 177 */     this.length = i;
/* 178 */     return this;
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
/*     */   public ByteVector putInt(int paramInt) {
/* 190 */     int i = this.length;
/* 191 */     if (i + 4 > this.data.length) {
/* 192 */       enlarge(4);
/*     */     }
/* 194 */     byte[] arrayOfByte = this.data;
/* 195 */     arrayOfByte[i++] = (byte)(paramInt >>> 24);
/* 196 */     arrayOfByte[i++] = (byte)(paramInt >>> 16);
/* 197 */     arrayOfByte[i++] = (byte)(paramInt >>> 8);
/* 198 */     arrayOfByte[i++] = (byte)paramInt;
/* 199 */     this.length = i;
/* 200 */     return this;
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
/*     */   public ByteVector putLong(long paramLong) {
/* 212 */     int i = this.length;
/* 213 */     if (i + 8 > this.data.length) {
/* 214 */       enlarge(8);
/*     */     }
/* 216 */     byte[] arrayOfByte = this.data;
/* 217 */     int j = (int)(paramLong >>> 32L);
/* 218 */     arrayOfByte[i++] = (byte)(j >>> 24);
/* 219 */     arrayOfByte[i++] = (byte)(j >>> 16);
/* 220 */     arrayOfByte[i++] = (byte)(j >>> 8);
/* 221 */     arrayOfByte[i++] = (byte)j;
/* 222 */     j = (int)paramLong;
/* 223 */     arrayOfByte[i++] = (byte)(j >>> 24);
/* 224 */     arrayOfByte[i++] = (byte)(j >>> 16);
/* 225 */     arrayOfByte[i++] = (byte)(j >>> 8);
/* 226 */     arrayOfByte[i++] = (byte)j;
/* 227 */     this.length = i;
/* 228 */     return this;
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
/*     */   public ByteVector putUTF8(String paramString) {
/* 240 */     int i = paramString.length();
/* 241 */     if (i > 65535) {
/* 242 */       throw new IllegalArgumentException();
/*     */     }
/* 244 */     int j = this.length;
/* 245 */     if (j + 2 + i > this.data.length) {
/* 246 */       enlarge(2 + i);
/*     */     }
/* 248 */     byte[] arrayOfByte = this.data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     arrayOfByte[j++] = (byte)(i >>> 8);
/* 256 */     arrayOfByte[j++] = (byte)i;
/* 257 */     for (byte b = 0; b < i; b++) {
/* 258 */       char c = paramString.charAt(b);
/* 259 */       if (c >= '\001' && c <= '') {
/* 260 */         arrayOfByte[j++] = (byte)c;
/*     */       } else {
/* 262 */         this.length = j;
/* 263 */         return encodeUTF8(paramString, b, 65535);
/*     */       } 
/*     */     } 
/* 266 */     this.length = j;
/* 267 */     return this;
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
/*     */   ByteVector encodeUTF8(String paramString, int paramInt1, int paramInt2) {
/* 288 */     int i = paramString.length();
/* 289 */     int j = paramInt1;
/*     */     int k;
/* 291 */     for (k = paramInt1; k < i; k++) {
/* 292 */       char c = paramString.charAt(k);
/* 293 */       if (c >= '\001' && c <= '') {
/* 294 */         j++;
/* 295 */       } else if (c > '߿') {
/* 296 */         j += 3;
/*     */       } else {
/* 298 */         j += 2;
/*     */       } 
/*     */     } 
/* 301 */     if (j > paramInt2) {
/* 302 */       throw new IllegalArgumentException();
/*     */     }
/* 304 */     k = this.length - paramInt1 - 2;
/* 305 */     if (k >= 0) {
/* 306 */       this.data[k] = (byte)(j >>> 8);
/* 307 */       this.data[k + 1] = (byte)j;
/*     */     } 
/* 309 */     if (this.length + j - paramInt1 > this.data.length) {
/* 310 */       enlarge(j - paramInt1);
/*     */     }
/* 312 */     int m = this.length;
/* 313 */     for (int n = paramInt1; n < i; n++) {
/* 314 */       char c = paramString.charAt(n);
/* 315 */       if (c >= '\001' && c <= '') {
/* 316 */         this.data[m++] = (byte)c;
/* 317 */       } else if (c > '߿') {
/* 318 */         this.data[m++] = (byte)(0xE0 | c >> 12 & 0xF);
/* 319 */         this.data[m++] = (byte)(0x80 | c >> 6 & 0x3F);
/* 320 */         this.data[m++] = (byte)(0x80 | c & 0x3F);
/*     */       } else {
/* 322 */         this.data[m++] = (byte)(0xC0 | c >> 6 & 0x1F);
/* 323 */         this.data[m++] = (byte)(0x80 | c & 0x3F);
/*     */       } 
/*     */     } 
/* 326 */     this.length = m;
/* 327 */     return this;
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
/*     */   public ByteVector putByteArray(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 344 */     if (this.length + paramInt2 > this.data.length) {
/* 345 */       enlarge(paramInt2);
/*     */     }
/* 347 */     if (paramArrayOfbyte != null) {
/* 348 */       System.arraycopy(paramArrayOfbyte, paramInt1, this.data, this.length, paramInt2);
/*     */     }
/* 350 */     this.length += paramInt2;
/* 351 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void enlarge(int paramInt) {
/* 362 */     int i = 2 * this.data.length;
/* 363 */     int j = this.length + paramInt;
/* 364 */     byte[] arrayOfByte = new byte[(i > j) ? i : j];
/* 365 */     System.arraycopy(this.data, 0, arrayOfByte, 0, this.length);
/* 366 */     this.data = arrayOfByte;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/ByteVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */