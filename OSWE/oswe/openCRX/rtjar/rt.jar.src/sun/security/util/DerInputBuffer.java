/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Date;
/*     */ import sun.util.calendar.CalendarDate;
/*     */ import sun.util.calendar.CalendarSystem;
/*     */ import sun.util.calendar.Gregorian;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DerInputBuffer
/*     */   extends ByteArrayInputStream
/*     */   implements Cloneable
/*     */ {
/*     */   boolean allowBER = true;
/*     */   
/*     */   DerInputBuffer(byte[] paramArrayOfbyte) {
/*  51 */     this(paramArrayOfbyte, true);
/*     */   }
/*     */   
/*     */   DerInputBuffer(byte[] paramArrayOfbyte, boolean paramBoolean) {
/*  55 */     super(paramArrayOfbyte);
/*  56 */     this.allowBER = paramBoolean;
/*     */   }
/*     */   
/*     */   DerInputBuffer(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean) {
/*  60 */     super(paramArrayOfbyte, paramInt1, paramInt2);
/*  61 */     this.allowBER = paramBoolean;
/*     */   }
/*     */   
/*     */   DerInputBuffer dup() {
/*     */     try {
/*  66 */       DerInputBuffer derInputBuffer = (DerInputBuffer)clone();
/*  67 */       derInputBuffer.mark(2147483647);
/*  68 */       return derInputBuffer;
/*  69 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*  70 */       throw new IllegalArgumentException(cloneNotSupportedException.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   byte[] toByteArray() {
/*  75 */     int i = available();
/*  76 */     if (i <= 0)
/*  77 */       return null; 
/*  78 */     byte[] arrayOfByte = new byte[i];
/*     */     
/*  80 */     System.arraycopy(this.buf, this.pos, arrayOfByte, 0, i);
/*  81 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   int peek() throws IOException {
/*  85 */     if (this.pos >= this.count) {
/*  86 */       throw new IOException("out of data");
/*     */     }
/*  88 */     return this.buf[this.pos];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  96 */     if (paramObject instanceof DerInputBuffer) {
/*  97 */       return equals((DerInputBuffer)paramObject);
/*     */     }
/*  99 */     return false;
/*     */   }
/*     */   
/*     */   boolean equals(DerInputBuffer paramDerInputBuffer) {
/* 103 */     if (this == paramDerInputBuffer) {
/* 104 */       return true;
/*     */     }
/* 106 */     int i = available();
/* 107 */     if (paramDerInputBuffer.available() != i)
/* 108 */       return false; 
/* 109 */     for (byte b = 0; b < i; b++) {
/* 110 */       if (this.buf[this.pos + b] != paramDerInputBuffer.buf[paramDerInputBuffer.pos + b]) {
/* 111 */         return false;
/*     */       }
/*     */     } 
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 123 */     int i = 0;
/*     */     
/* 125 */     int j = available();
/* 126 */     int k = this.pos;
/*     */     
/* 128 */     for (byte b = 0; b < j; b++)
/* 129 */       i += this.buf[k + b] * b; 
/* 130 */     return i;
/*     */   }
/*     */   
/*     */   void truncate(int paramInt) throws IOException {
/* 134 */     if (paramInt > available())
/* 135 */       throw new IOException("insufficient data"); 
/* 136 */     this.count = this.pos + paramInt;
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
/*     */   BigInteger getBigInteger(int paramInt, boolean paramBoolean) throws IOException {
/* 148 */     if (paramInt > available()) {
/* 149 */       throw new IOException("short read of integer");
/*     */     }
/* 151 */     if (paramInt == 0) {
/* 152 */       throw new IOException("Invalid encoding: zero length Int value");
/*     */     }
/*     */     
/* 155 */     byte[] arrayOfByte = new byte[paramInt];
/*     */     
/* 157 */     System.arraycopy(this.buf, this.pos, arrayOfByte, 0, paramInt);
/* 158 */     skip(paramInt);
/*     */ 
/*     */     
/* 161 */     if (!this.allowBER && paramInt >= 2 && arrayOfByte[0] == 0 && arrayOfByte[1] >= 0) {
/* 162 */       throw new IOException("Invalid encoding: redundant leading 0s");
/*     */     }
/*     */     
/* 165 */     if (paramBoolean) {
/* 166 */       return new BigInteger(1, arrayOfByte);
/*     */     }
/* 168 */     return new BigInteger(arrayOfByte);
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
/*     */   public int getInteger(int paramInt) throws IOException {
/* 183 */     BigInteger bigInteger = getBigInteger(paramInt, false);
/* 184 */     if (bigInteger.compareTo(BigInteger.valueOf(-2147483648L)) < 0) {
/* 185 */       throw new IOException("Integer below minimum valid value");
/*     */     }
/* 187 */     if (bigInteger.compareTo(BigInteger.valueOf(2147483647L)) > 0) {
/* 188 */       throw new IOException("Integer exceeds maximum valid value");
/*     */     }
/* 190 */     return bigInteger.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBitString(int paramInt) throws IOException {
/* 198 */     if (paramInt > available()) {
/* 199 */       throw new IOException("short read of bit string");
/*     */     }
/* 201 */     if (paramInt == 0) {
/* 202 */       throw new IOException("Invalid encoding: zero length bit string");
/*     */     }
/*     */     
/* 205 */     byte b = this.buf[this.pos];
/* 206 */     if (b < 0 || b > 7) {
/* 207 */       throw new IOException("Invalid number of padding bits");
/*     */     }
/*     */     
/* 210 */     byte[] arrayOfByte = new byte[paramInt - 1];
/* 211 */     System.arraycopy(this.buf, this.pos + 1, arrayOfByte, 0, paramInt - 1);
/* 212 */     if (b != 0)
/*     */     {
/* 214 */       arrayOfByte[paramInt - 2] = (byte)(arrayOfByte[paramInt - 2] & 255 << b);
/*     */     }
/* 216 */     skip(paramInt);
/* 217 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getBitString() throws IOException {
/* 224 */     return getBitString(available());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BitArray getUnalignedBitString() throws IOException {
/* 232 */     if (this.pos >= this.count) {
/* 233 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 238 */     int i = available();
/* 239 */     int j = this.buf[this.pos] & 0xFF;
/* 240 */     if (j > 7) {
/* 241 */       throw new IOException("Invalid value for unused bits: " + j);
/*     */     }
/* 243 */     byte[] arrayOfByte = new byte[i - 1];
/*     */     
/* 245 */     boolean bool = (arrayOfByte.length == 0) ? false : (arrayOfByte.length * 8 - j);
/*     */     
/* 247 */     System.arraycopy(this.buf, this.pos + 1, arrayOfByte, 0, i - 1);
/*     */     
/* 249 */     BitArray bitArray = new BitArray(bool, arrayOfByte);
/* 250 */     this.pos = this.count;
/* 251 */     return bitArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getUTCTime(int paramInt) throws IOException {
/* 260 */     if (paramInt > available()) {
/* 261 */       throw new IOException("short read of DER UTC Time");
/*     */     }
/* 263 */     if (paramInt < 11 || paramInt > 17) {
/* 264 */       throw new IOException("DER UTC Time length error");
/*     */     }
/* 266 */     return getTime(paramInt, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getGeneralizedTime(int paramInt) throws IOException {
/* 275 */     if (paramInt > available()) {
/* 276 */       throw new IOException("short read of DER Generalized Time");
/*     */     }
/* 278 */     if (paramInt < 13 || paramInt > 23) {
/* 279 */       throw new IOException("DER Generalized Time length error");
/*     */     }
/* 281 */     return getTime(paramInt, true);
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
/*     */   private Date getTime(int paramInt, boolean paramBoolean) throws IOException {
/*     */     int i;
/*     */     byte b;
/*     */     int i2, i3;
/* 312 */     String str = null;
/*     */     
/* 314 */     if (paramBoolean) {
/* 315 */       str = "Generalized";
/* 316 */       i = 1000 * Character.digit((char)this.buf[this.pos++], 10);
/* 317 */       i += 100 * Character.digit((char)this.buf[this.pos++], 10);
/* 318 */       i += 10 * Character.digit((char)this.buf[this.pos++], 10);
/* 319 */       i += Character.digit((char)this.buf[this.pos++], 10);
/* 320 */       paramInt -= 2;
/*     */     } else {
/* 322 */       str = "UTC";
/* 323 */       i = 10 * Character.digit((char)this.buf[this.pos++], 10);
/* 324 */       i += Character.digit((char)this.buf[this.pos++], 10);
/*     */       
/* 326 */       if (i < 50) {
/* 327 */         i += 2000;
/*     */       } else {
/* 329 */         i += 1900;
/*     */       } 
/*     */     } 
/* 332 */     int j = 10 * Character.digit((char)this.buf[this.pos++], 10);
/* 333 */     j += Character.digit((char)this.buf[this.pos++], 10);
/*     */     
/* 335 */     int k = 10 * Character.digit((char)this.buf[this.pos++], 10);
/* 336 */     k += Character.digit((char)this.buf[this.pos++], 10);
/*     */     
/* 338 */     int m = 10 * Character.digit((char)this.buf[this.pos++], 10);
/* 339 */     m += Character.digit((char)this.buf[this.pos++], 10);
/*     */     
/* 341 */     int n = 10 * Character.digit((char)this.buf[this.pos++], 10);
/* 342 */     n += Character.digit((char)this.buf[this.pos++], 10);
/*     */     
/* 344 */     paramInt -= 10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     int i1 = 0;
/* 353 */     if (paramInt > 2 && paramInt < 12) {
/* 354 */       b = 10 * Character.digit((char)this.buf[this.pos++], 10);
/* 355 */       b += Character.digit((char)this.buf[this.pos++], 10);
/* 356 */       paramInt -= 2;
/*     */       
/* 358 */       if (this.buf[this.pos] == 46 || this.buf[this.pos] == 44) {
/* 359 */         paramInt--;
/* 360 */         this.pos++;
/*     */         
/* 362 */         byte b1 = 0;
/* 363 */         int i4 = this.pos;
/* 364 */         while (this.buf[i4] != 90 && this.buf[i4] != 43 && this.buf[i4] != 45) {
/*     */ 
/*     */           
/* 367 */           i4++;
/* 368 */           b1++;
/*     */         } 
/* 370 */         switch (b1) {
/*     */           case 3:
/* 372 */             i1 += 100 * Character.digit((char)this.buf[this.pos++], 10);
/* 373 */             i1 += 10 * Character.digit((char)this.buf[this.pos++], 10);
/* 374 */             i1 += Character.digit((char)this.buf[this.pos++], 10);
/*     */             break;
/*     */           case 2:
/* 377 */             i1 += 100 * Character.digit((char)this.buf[this.pos++], 10);
/* 378 */             i1 += 10 * Character.digit((char)this.buf[this.pos++], 10);
/*     */             break;
/*     */           case 1:
/* 381 */             i1 += 100 * Character.digit((char)this.buf[this.pos++], 10);
/*     */             break;
/*     */           default:
/* 384 */             throw new IOException("Parse " + str + " time, unsupported precision for seconds value");
/*     */         } 
/*     */         
/* 387 */         paramInt -= b1;
/*     */       } 
/*     */     } else {
/* 390 */       b = 0;
/*     */     } 
/* 392 */     if (j == 0 || k == 0 || j > 12 || k > 31 || m >= 24 || n >= 60 || b >= 60)
/*     */     {
/*     */       
/* 395 */       throw new IOException("Parse " + str + " time, invalid format");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 401 */     Gregorian gregorian = CalendarSystem.getGregorianCalendar();
/* 402 */     CalendarDate calendarDate = gregorian.newCalendarDate(null);
/* 403 */     calendarDate.setDate(i, j, k);
/* 404 */     calendarDate.setTimeOfDay(m, n, b, i1);
/* 405 */     long l = gregorian.getTime(calendarDate);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 410 */     if (paramInt != 1 && paramInt != 5) {
/* 411 */       throw new IOException("Parse " + str + " time, invalid offset");
/*     */     }
/*     */ 
/*     */     
/* 415 */     switch (this.buf[this.pos++]) {
/*     */       case 43:
/* 417 */         i2 = 10 * Character.digit((char)this.buf[this.pos++], 10);
/* 418 */         i2 += Character.digit((char)this.buf[this.pos++], 10);
/* 419 */         i3 = 10 * Character.digit((char)this.buf[this.pos++], 10);
/* 420 */         i3 += Character.digit((char)this.buf[this.pos++], 10);
/*     */         
/* 422 */         if (i2 >= 24 || i3 >= 60) {
/* 423 */           throw new IOException("Parse " + str + " time, +hhmm");
/*     */         }
/* 425 */         l -= ((i2 * 60 + i3) * 60 * 1000);
/*     */ 
/*     */       
/*     */       case 45:
/* 429 */         i2 = 10 * Character.digit((char)this.buf[this.pos++], 10);
/* 430 */         i2 += Character.digit((char)this.buf[this.pos++], 10);
/* 431 */         i3 = 10 * Character.digit((char)this.buf[this.pos++], 10);
/* 432 */         i3 += Character.digit((char)this.buf[this.pos++], 10);
/*     */         
/* 434 */         if (i2 >= 24 || i3 >= 60) {
/* 435 */           throw new IOException("Parse " + str + " time, -hhmm");
/*     */         }
/* 437 */         l += ((i2 * 60 + i3) * 60 * 1000);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 90:
/* 446 */         return new Date(l);
/*     */     } 
/*     */     throw new IOException("Parse " + str + " time, garbage offset");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/DerInputBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */