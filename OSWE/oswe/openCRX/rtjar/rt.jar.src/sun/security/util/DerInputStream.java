/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DerInputStream
/*     */ {
/*     */   DerInputBuffer buffer;
/*     */   public byte tag;
/*     */   
/*     */   public DerInputStream(byte[] paramArrayOfbyte) throws IOException {
/*  80 */     init(paramArrayOfbyte, 0, paramArrayOfbyte.length, true);
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
/*     */   public DerInputStream(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean) throws IOException {
/*  99 */     init(paramArrayOfbyte, paramInt1, paramInt2, paramBoolean);
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
/*     */   public DerInputStream(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 114 */     init(paramArrayOfbyte, paramInt1, paramInt2, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean) throws IOException {
/* 121 */     if (paramInt1 + 2 > paramArrayOfbyte.length || paramInt1 + paramInt2 > paramArrayOfbyte.length) {
/* 122 */       throw new IOException("Encoding bytes too short");
/*     */     }
/*     */     
/* 125 */     if (DerIndefLenConverter.isIndefinite(paramArrayOfbyte[paramInt1 + 1])) {
/* 126 */       if (!paramBoolean) {
/* 127 */         throw new IOException("Indefinite length BER encoding found");
/*     */       }
/* 129 */       byte[] arrayOfByte = new byte[paramInt2];
/* 130 */       System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, paramInt2);
/*     */       
/* 132 */       DerIndefLenConverter derIndefLenConverter = new DerIndefLenConverter();
/* 133 */       this.buffer = new DerInputBuffer(derIndefLenConverter.convert(arrayOfByte), paramBoolean);
/*     */     } else {
/*     */       
/* 136 */       this.buffer = new DerInputBuffer(paramArrayOfbyte, paramInt1, paramInt2, paramBoolean);
/*     */     } 
/* 138 */     this.buffer.mark(2147483647);
/*     */   }
/*     */   
/*     */   DerInputStream(DerInputBuffer paramDerInputBuffer) {
/* 142 */     this.buffer = paramDerInputBuffer;
/* 143 */     this.buffer.mark(2147483647);
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
/*     */   public DerInputStream subStream(int paramInt, boolean paramBoolean) throws IOException {
/* 158 */     DerInputBuffer derInputBuffer = this.buffer.dup();
/*     */     
/* 160 */     derInputBuffer.truncate(paramInt);
/* 161 */     if (paramBoolean) {
/* 162 */       this.buffer.skip(paramInt);
/*     */     }
/* 164 */     return new DerInputStream(derInputBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toByteArray() {
/* 172 */     return this.buffer.toByteArray();
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
/*     */   public int getInteger() throws IOException {
/* 191 */     if (this.buffer.read() != 2) {
/* 192 */       throw new IOException("DER input, Integer tag error");
/*     */     }
/* 194 */     return this.buffer.getInteger(getLength(this.buffer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getBigInteger() throws IOException {
/* 203 */     if (this.buffer.read() != 2) {
/* 204 */       throw new IOException("DER input, Integer tag error");
/*     */     }
/* 206 */     return this.buffer.getBigInteger(getLength(this.buffer), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getPositiveBigInteger() throws IOException {
/* 217 */     if (this.buffer.read() != 2) {
/* 218 */       throw new IOException("DER input, Integer tag error");
/*     */     }
/* 220 */     return this.buffer.getBigInteger(getLength(this.buffer), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnumerated() throws IOException {
/* 229 */     if (this.buffer.read() != 10) {
/* 230 */       throw new IOException("DER input, Enumerated tag error");
/*     */     }
/* 232 */     return this.buffer.getInteger(getLength(this.buffer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBitString() throws IOException {
/* 240 */     if (this.buffer.read() != 3) {
/* 241 */       throw new IOException("DER input not an bit string");
/*     */     }
/* 243 */     return this.buffer.getBitString(getLength(this.buffer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BitArray getUnalignedBitString() throws IOException {
/* 251 */     if (this.buffer.read() != 3) {
/* 252 */       throw new IOException("DER input not a bit string");
/*     */     }
/* 254 */     int i = getLength(this.buffer) - 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     int j = this.buffer.read();
/* 261 */     if (j < 0) {
/* 262 */       throw new IOException("Unused bits of bit string invalid");
/*     */     }
/* 264 */     int k = i * 8 - j;
/* 265 */     if (k < 0) {
/* 266 */       throw new IOException("Valid bits of bit string invalid");
/*     */     }
/*     */     
/* 269 */     byte[] arrayOfByte = new byte[i];
/*     */     
/* 271 */     if (i != 0 && this.buffer.read(arrayOfByte) != i) {
/* 272 */       throw new IOException("Short read of DER bit string");
/*     */     }
/*     */     
/* 275 */     return new BitArray(k, arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getOctetString() throws IOException {
/* 282 */     if (this.buffer.read() != 4) {
/* 283 */       throw new IOException("DER input not an octet string");
/*     */     }
/* 285 */     int i = getLength(this.buffer);
/* 286 */     byte[] arrayOfByte = new byte[i];
/* 287 */     if (i != 0 && this.buffer.read(arrayOfByte) != i) {
/* 288 */       throw new IOException("Short read of DER octet string");
/*     */     }
/* 290 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getBytes(byte[] paramArrayOfbyte) throws IOException {
/* 297 */     if (paramArrayOfbyte.length != 0 && this.buffer.read(paramArrayOfbyte) != paramArrayOfbyte.length) {
/* 298 */       throw new IOException("Short read of DER octet string");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getNull() throws IOException {
/* 306 */     if (this.buffer.read() != 5 || this.buffer.read() != 0) {
/* 307 */       throw new IOException("getNull, bad data");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdentifier getOID() throws IOException {
/* 314 */     return new ObjectIdentifier(this);
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
/*     */   public DerValue[] getSequence(int paramInt) throws IOException {
/* 328 */     this.tag = (byte)this.buffer.read();
/* 329 */     if (this.tag != 48)
/* 330 */       throw new IOException("Sequence tag error"); 
/* 331 */     return readVector(paramInt);
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
/*     */   public DerValue[] getSet(int paramInt) throws IOException {
/* 345 */     this.tag = (byte)this.buffer.read();
/* 346 */     if (this.tag != 49)
/* 347 */       throw new IOException("Set tag error"); 
/* 348 */     return readVector(paramInt);
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
/*     */   public DerValue[] getSet(int paramInt, boolean paramBoolean) throws IOException {
/* 364 */     this.tag = (byte)this.buffer.read();
/* 365 */     if (!paramBoolean && 
/* 366 */       this.tag != 49) {
/* 367 */       throw new IOException("Set tag error");
/*     */     }
/*     */     
/* 370 */     return readVector(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DerValue[] readVector(int paramInt) throws IOException {
/*     */     DerInputStream derInputStream;
/* 381 */     byte b = (byte)this.buffer.read();
/* 382 */     int i = getLength(b, this.buffer);
/*     */     
/* 384 */     if (i == -1) {
/*     */       
/* 386 */       int k = this.buffer.available();
/* 387 */       byte b2 = 2;
/* 388 */       byte[] arrayOfByte = new byte[k + b2];
/* 389 */       arrayOfByte[0] = this.tag;
/* 390 */       arrayOfByte[1] = b;
/* 391 */       DataInputStream dataInputStream = new DataInputStream(this.buffer);
/* 392 */       dataInputStream.readFully(arrayOfByte, b2, k);
/* 393 */       dataInputStream.close();
/* 394 */       DerIndefLenConverter derIndefLenConverter = new DerIndefLenConverter();
/* 395 */       this.buffer = new DerInputBuffer(derIndefLenConverter.convert(arrayOfByte), this.buffer.allowBER);
/*     */       
/* 397 */       if (this.tag != this.buffer.read()) {
/* 398 */         throw new IOException("Indefinite length encoding not supported");
/*     */       }
/* 400 */       i = getLength(this.buffer);
/*     */     } 
/*     */     
/* 403 */     if (i == 0)
/*     */     {
/*     */       
/* 406 */       return new DerValue[0];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 412 */     if (this.buffer.available() == i) {
/* 413 */       derInputStream = this;
/*     */     } else {
/* 415 */       derInputStream = subStream(i, true);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 420 */     Vector<DerValue> vector = new Vector(paramInt);
/*     */ 
/*     */     
/*     */     do {
/* 424 */       DerValue derValue = new DerValue(derInputStream.buffer, this.buffer.allowBER);
/* 425 */       vector.addElement(derValue);
/* 426 */     } while (derInputStream.available() > 0);
/*     */     
/* 428 */     if (derInputStream.available() != 0) {
/* 429 */       throw new IOException("Extra data at end of vector");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 434 */     int j = vector.size();
/* 435 */     DerValue[] arrayOfDerValue = new DerValue[j];
/*     */     
/* 437 */     for (byte b1 = 0; b1 < j; b1++) {
/* 438 */       arrayOfDerValue[b1] = vector.elementAt(b1);
/*     */     }
/* 440 */     return arrayOfDerValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerValue getDerValue() throws IOException {
/* 451 */     return new DerValue(this.buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUTF8String() throws IOException {
/* 458 */     return readString((byte)12, "UTF-8", "UTF8");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrintableString() throws IOException {
/* 465 */     return readString((byte)19, "Printable", "ASCII");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getT61String() throws IOException {
/* 476 */     return readString((byte)20, "T61", "ISO-8859-1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIA5String() throws IOException {
/* 483 */     return readString((byte)22, "IA5", "ASCII");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBMPString() throws IOException {
/* 490 */     return readString((byte)30, "BMP", "UnicodeBigUnmarked");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGeneralString() throws IOException {
/* 498 */     return readString((byte)27, "General", "ASCII");
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
/*     */   private String readString(byte paramByte, String paramString1, String paramString2) throws IOException {
/* 513 */     if (this.buffer.read() != paramByte) {
/* 514 */       throw new IOException("DER input not a " + paramString1 + " string");
/*     */     }
/*     */     
/* 517 */     int i = getLength(this.buffer);
/* 518 */     byte[] arrayOfByte = new byte[i];
/* 519 */     if (i != 0 && this.buffer.read(arrayOfByte) != i) {
/* 520 */       throw new IOException("Short read of DER " + paramString1 + " string");
/*     */     }
/*     */     
/* 523 */     return new String(arrayOfByte, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getUTCTime() throws IOException {
/* 530 */     if (this.buffer.read() != 23)
/* 531 */       throw new IOException("DER input, UTCtime tag invalid "); 
/* 532 */     return this.buffer.getUTCTime(getLength(this.buffer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getGeneralizedTime() throws IOException {
/* 539 */     if (this.buffer.read() != 24)
/* 540 */       throw new IOException("DER input, GeneralizedTime tag invalid "); 
/* 541 */     return this.buffer.getGeneralizedTime(getLength(this.buffer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getByte() throws IOException {
/* 549 */     return 0xFF & this.buffer.read();
/*     */   }
/*     */   
/*     */   public int peekByte() throws IOException {
/* 553 */     return this.buffer.peek();
/*     */   }
/*     */ 
/*     */   
/*     */   int getLength() throws IOException {
/* 558 */     return getLength(this.buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getLength(InputStream paramInputStream) throws IOException {
/* 569 */     return getLength(paramInputStream.read(), paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getLength(int paramInt, InputStream paramInputStream) throws IOException {
/*     */     int i;
/* 581 */     if (paramInt == -1) {
/* 582 */       throw new IOException("Short read of DER length");
/*     */     }
/*     */     
/* 585 */     String str = "DerInputStream.getLength(): ";
/* 586 */     int j = paramInt;
/* 587 */     if ((j & 0x80) == 0) {
/* 588 */       i = j;
/*     */     } else {
/* 590 */       j &= 0x7F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 596 */       if (j == 0)
/* 597 */         return -1; 
/* 598 */       if (j < 0 || j > 4) {
/* 599 */         throw new IOException(str + "lengthTag=" + j + ", " + ((j < 0) ? "incorrect DER encoding." : "too big."));
/*     */       }
/*     */       
/* 602 */       i = 0xFF & paramInputStream.read();
/* 603 */       j--;
/* 604 */       if (i == 0)
/*     */       {
/* 606 */         throw new IOException(str + "Redundant length bytes found");
/*     */       }
/* 608 */       while (j-- > 0) {
/* 609 */         i <<= 8;
/* 610 */         i += 0xFF & paramInputStream.read();
/*     */       } 
/* 612 */       if (i < 0)
/* 613 */         throw new IOException(str + "Invalid length bytes"); 
/* 614 */       if (i <= 127) {
/* 615 */         throw new IOException(str + "Should use short form for length");
/*     */       }
/*     */     } 
/* 618 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mark(int paramInt) {
/* 625 */     this.buffer.mark(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 633 */     this.buffer.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int available() {
/* 641 */     return this.buffer.available();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/DerInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */