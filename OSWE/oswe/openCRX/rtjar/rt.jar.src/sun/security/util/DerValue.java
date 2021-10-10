/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Date;
/*     */ import sun.misc.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DerValue
/*     */ {
/*     */   public static final byte TAG_UNIVERSAL = 0;
/*     */   public static final byte TAG_APPLICATION = 64;
/*     */   public static final byte TAG_CONTEXT = -128;
/*     */   public static final byte TAG_PRIVATE = -64;
/*     */   public byte tag;
/*     */   protected DerInputBuffer buffer;
/*     */   public final DerInputStream data;
/*     */   private int length;
/*     */   public static final byte tag_Boolean = 1;
/*     */   public static final byte tag_Integer = 2;
/*     */   public static final byte tag_BitString = 3;
/*     */   public static final byte tag_OctetString = 4;
/*     */   public static final byte tag_Null = 5;
/*     */   public static final byte tag_ObjectId = 6;
/*     */   public static final byte tag_Enumerated = 10;
/*     */   public static final byte tag_UTF8String = 12;
/*     */   public static final byte tag_PrintableString = 19;
/*     */   public static final byte tag_T61String = 20;
/*     */   public static final byte tag_IA5String = 22;
/*     */   public static final byte tag_UtcTime = 23;
/*     */   public static final byte tag_GeneralizedTime = 24;
/*     */   public static final byte tag_GeneralString = 27;
/*     */   public static final byte tag_UniversalString = 28;
/*     */   public static final byte tag_BMPString = 30;
/*     */   public static final byte tag_Sequence = 48;
/*     */   public static final byte tag_SequenceOf = 48;
/*     */   public static final byte tag_Set = 49;
/*     */   public static final byte tag_SetOf = 49;
/*     */   
/*     */   public boolean isUniversal() {
/* 167 */     return ((this.tag & 0xC0) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isApplication() {
/* 172 */     return ((this.tag & 0xC0) == 64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isContextSpecific() {
/* 178 */     return ((this.tag & 0xC0) == 128);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isContextSpecific(byte paramByte) {
/* 184 */     if (!isContextSpecific()) {
/* 185 */       return false;
/*     */     }
/* 187 */     return ((this.tag & 0x1F) == paramByte);
/*     */   }
/*     */   boolean isPrivate() {
/* 190 */     return ((this.tag & 0xC0) == 192);
/*     */   }
/*     */   public boolean isConstructed() {
/* 193 */     return ((this.tag & 0x20) == 32);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConstructed(byte paramByte) {
/* 199 */     if (!isConstructed()) {
/* 200 */       return false;
/*     */     }
/* 202 */     return ((this.tag & 0x1F) == paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerValue(String paramString) throws IOException {
/* 209 */     boolean bool = true;
/* 210 */     for (byte b = 0; b < paramString.length(); b++) {
/* 211 */       if (!isPrintableStringChar(paramString.charAt(b))) {
/* 212 */         bool = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 217 */     this.data = init(bool ? 19 : 12, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerValue(byte paramByte, String paramString) throws IOException {
/* 226 */     this.data = init(paramByte, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   DerValue(byte paramByte, byte[] paramArrayOfbyte, boolean paramBoolean) {
/* 232 */     this.tag = paramByte;
/* 233 */     this.buffer = new DerInputBuffer((byte[])paramArrayOfbyte.clone(), paramBoolean);
/* 234 */     this.length = paramArrayOfbyte.length;
/* 235 */     this.data = new DerInputStream(this.buffer);
/* 236 */     this.data.mark(2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerValue(byte paramByte, byte[] paramArrayOfbyte) {
/* 246 */     this(paramByte, paramArrayOfbyte, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DerValue(DerInputBuffer paramDerInputBuffer) throws IOException {
/* 256 */     this.tag = (byte)paramDerInputBuffer.read();
/* 257 */     byte b = (byte)paramDerInputBuffer.read();
/* 258 */     this.length = DerInputStream.getLength(b, paramDerInputBuffer);
/* 259 */     if (this.length == -1) {
/* 260 */       DerInputBuffer derInputBuffer = paramDerInputBuffer.dup();
/* 261 */       int i = derInputBuffer.available();
/* 262 */       byte b1 = 2;
/* 263 */       byte[] arrayOfByte = new byte[i + b1];
/* 264 */       arrayOfByte[0] = this.tag;
/* 265 */       arrayOfByte[1] = b;
/* 266 */       DataInputStream dataInputStream = new DataInputStream(derInputBuffer);
/* 267 */       dataInputStream.readFully(arrayOfByte, b1, i);
/* 268 */       dataInputStream.close();
/* 269 */       DerIndefLenConverter derIndefLenConverter = new DerIndefLenConverter();
/* 270 */       derInputBuffer = new DerInputBuffer(derIndefLenConverter.convert(arrayOfByte), paramDerInputBuffer.allowBER);
/* 271 */       if (this.tag != derInputBuffer.read()) {
/* 272 */         throw new IOException("Indefinite length encoding not supported");
/*     */       }
/* 274 */       this.length = DerInputStream.getLength(derInputBuffer);
/* 275 */       this.buffer = derInputBuffer.dup();
/* 276 */       this.buffer.truncate(this.length);
/* 277 */       this.data = new DerInputStream(this.buffer);
/*     */ 
/*     */ 
/*     */       
/* 281 */       paramDerInputBuffer.skip((this.length + b1));
/*     */     } else {
/*     */       
/* 284 */       this.buffer = paramDerInputBuffer.dup();
/* 285 */       this.buffer.truncate(this.length);
/* 286 */       this.data = new DerInputStream(this.buffer);
/*     */       
/* 288 */       paramDerInputBuffer.skip(this.length);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   DerValue(byte[] paramArrayOfbyte, boolean paramBoolean) throws IOException {
/* 295 */     this.data = init(true, new ByteArrayInputStream(paramArrayOfbyte), paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerValue(byte[] paramArrayOfbyte) throws IOException {
/* 306 */     this(paramArrayOfbyte, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DerValue(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean) throws IOException {
/* 313 */     this.data = init(true, new ByteArrayInputStream(paramArrayOfbyte, paramInt1, paramInt2), paramBoolean);
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
/*     */   public DerValue(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 326 */     this(paramArrayOfbyte, paramInt1, paramInt2, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   DerValue(InputStream paramInputStream, boolean paramBoolean) throws IOException {
/* 332 */     this.data = init(false, paramInputStream, paramBoolean);
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
/*     */   public DerValue(InputStream paramInputStream) throws IOException {
/* 345 */     this(paramInputStream, true);
/*     */   }
/*     */   private DerInputStream init(byte paramByte, String paramString) throws IOException {
/*     */     byte[] arrayOfByte;
/*     */     DerInputStream derInputStream;
/* 350 */     String str = null;
/*     */     
/* 352 */     this.tag = paramByte;
/*     */     
/* 354 */     switch (paramByte) {
/*     */       case 19:
/*     */       case 22:
/*     */       case 27:
/* 358 */         str = "ASCII";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 375 */         arrayOfByte = paramString.getBytes(str);
/* 376 */         this.length = arrayOfByte.length;
/* 377 */         this.buffer = new DerInputBuffer(arrayOfByte, true);
/* 378 */         derInputStream = new DerInputStream(this.buffer);
/* 379 */         derInputStream.mark(2147483647);
/* 380 */         return derInputStream;case 20: str = "ISO-8859-1"; arrayOfByte = paramString.getBytes(str); this.length = arrayOfByte.length; this.buffer = new DerInputBuffer(arrayOfByte, true); derInputStream = new DerInputStream(this.buffer); derInputStream.mark(2147483647); return derInputStream;case 30: str = "UnicodeBigUnmarked"; arrayOfByte = paramString.getBytes(str); this.length = arrayOfByte.length; this.buffer = new DerInputBuffer(arrayOfByte, true); derInputStream = new DerInputStream(this.buffer); derInputStream.mark(2147483647); return derInputStream;case 12: str = "UTF8"; arrayOfByte = paramString.getBytes(str); this.length = arrayOfByte.length; this.buffer = new DerInputBuffer(arrayOfByte, true); derInputStream = new DerInputStream(this.buffer); derInputStream.mark(2147483647); return derInputStream;
/*     */     } 
/*     */     throw new IllegalArgumentException("Unsupported DER string type");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DerInputStream init(boolean paramBoolean1, InputStream paramInputStream, boolean paramBoolean2) throws IOException {
/* 389 */     this.tag = (byte)paramInputStream.read();
/* 390 */     byte b = (byte)paramInputStream.read();
/* 391 */     this.length = DerInputStream.getLength(b, paramInputStream);
/* 392 */     if (this.length == -1) {
/* 393 */       int i = paramInputStream.available();
/* 394 */       byte b1 = 2;
/* 395 */       byte[] arrayOfByte1 = new byte[i + b1];
/* 396 */       arrayOfByte1[0] = this.tag;
/* 397 */       arrayOfByte1[1] = b;
/* 398 */       DataInputStream dataInputStream = new DataInputStream(paramInputStream);
/* 399 */       dataInputStream.readFully(arrayOfByte1, b1, i);
/* 400 */       dataInputStream.close();
/* 401 */       DerIndefLenConverter derIndefLenConverter = new DerIndefLenConverter();
/* 402 */       paramInputStream = new ByteArrayInputStream(derIndefLenConverter.convert(arrayOfByte1));
/* 403 */       if (this.tag != paramInputStream.read()) {
/* 404 */         throw new IOException("Indefinite length encoding not supported");
/*     */       }
/* 406 */       this.length = DerInputStream.getLength(paramInputStream);
/*     */     } 
/*     */     
/* 409 */     if (paramBoolean1 && paramInputStream.available() != this.length) {
/* 410 */       throw new IOException("extra data given to DerValue constructor");
/*     */     }
/* 412 */     byte[] arrayOfByte = IOUtils.readExactlyNBytes(paramInputStream, this.length);
/*     */     
/* 414 */     this.buffer = new DerInputBuffer(arrayOfByte, paramBoolean2);
/* 415 */     return new DerInputStream(this.buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 423 */     paramDerOutputStream.write(this.tag);
/* 424 */     paramDerOutputStream.putLength(this.length);
/*     */     
/* 426 */     if (this.length > 0) {
/* 427 */       byte[] arrayOfByte = new byte[this.length];
/*     */       
/* 429 */       synchronized (this.data) {
/* 430 */         this.buffer.reset();
/* 431 */         if (this.buffer.read(arrayOfByte) != this.length) {
/* 432 */           throw new IOException("short DER value read (encode)");
/*     */         }
/* 434 */         paramDerOutputStream.write(arrayOfByte);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public final DerInputStream getData() {
/* 440 */     return this.data;
/*     */   }
/*     */   
/*     */   public final byte getTag() {
/* 444 */     return this.tag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean() throws IOException {
/* 453 */     if (this.tag != 1) {
/* 454 */       throw new IOException("DerValue.getBoolean, not a BOOLEAN " + this.tag);
/*     */     }
/* 456 */     if (this.length != 1) {
/* 457 */       throw new IOException("DerValue.getBoolean, invalid length " + this.length);
/*     */     }
/*     */     
/* 460 */     if (this.buffer.read() != 0) {
/* 461 */       return true;
/*     */     }
/* 463 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdentifier getOID() throws IOException {
/* 472 */     if (this.tag != 6)
/* 473 */       throw new IOException("DerValue.getOID, not an OID " + this.tag); 
/* 474 */     return new ObjectIdentifier(this.buffer);
/*     */   }
/*     */   
/*     */   private byte[] append(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 478 */     if (paramArrayOfbyte1 == null) {
/* 479 */       return paramArrayOfbyte2;
/*     */     }
/* 481 */     byte[] arrayOfByte = new byte[paramArrayOfbyte1.length + paramArrayOfbyte2.length];
/* 482 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, 0, paramArrayOfbyte1.length);
/* 483 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte, paramArrayOfbyte1.length, paramArrayOfbyte2.length);
/*     */     
/* 485 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getOctetString() throws IOException {
/* 495 */     if (this.tag != 4 && !isConstructed((byte)4)) {
/* 496 */       throw new IOException("DerValue.getOctetString, not an Octet String: " + this.tag);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 501 */     if (this.length == 0) {
/* 502 */       return new byte[0];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 508 */     DerInputBuffer derInputBuffer = this.buffer;
/* 509 */     if (derInputBuffer.available() < this.length) {
/* 510 */       throw new IOException("short read on DerValue buffer");
/*     */     }
/* 512 */     byte[] arrayOfByte = new byte[this.length];
/* 513 */     derInputBuffer.read(arrayOfByte);
/*     */     
/* 515 */     if (isConstructed()) {
/* 516 */       DerInputStream derInputStream = new DerInputStream(arrayOfByte, 0, arrayOfByte.length, this.buffer.allowBER);
/*     */       
/* 518 */       arrayOfByte = null;
/* 519 */       while (derInputStream.available() != 0) {
/* 520 */         arrayOfByte = append(arrayOfByte, derInputStream.getOctetString());
/*     */       }
/*     */     } 
/* 523 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInteger() throws IOException {
/* 532 */     if (this.tag != 2) {
/* 533 */       throw new IOException("DerValue.getInteger, not an int " + this.tag);
/*     */     }
/* 535 */     return this.buffer.getInteger(this.data.available());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getBigInteger() throws IOException {
/* 544 */     if (this.tag != 2)
/* 545 */       throw new IOException("DerValue.getBigInteger, not an int " + this.tag); 
/* 546 */     return this.buffer.getBigInteger(this.data.available(), false);
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
/* 557 */     if (this.tag != 2)
/* 558 */       throw new IOException("DerValue.getBigInteger, not an int " + this.tag); 
/* 559 */     return this.buffer.getBigInteger(this.data.available(), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnumerated() throws IOException {
/* 568 */     if (this.tag != 10) {
/* 569 */       throw new IOException("DerValue.getEnumerated, incorrect tag: " + this.tag);
/*     */     }
/*     */     
/* 572 */     return this.buffer.getInteger(this.data.available());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBitString() throws IOException {
/* 581 */     if (this.tag != 3) {
/* 582 */       throw new IOException("DerValue.getBitString, not a bit string " + this.tag);
/*     */     }
/*     */     
/* 585 */     return this.buffer.getBitString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BitArray getUnalignedBitString() throws IOException {
/* 594 */     if (this.tag != 3) {
/* 595 */       throw new IOException("DerValue.getBitString, not a bit string " + this.tag);
/*     */     }
/*     */     
/* 598 */     return this.buffer.getUnalignedBitString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAsString() throws IOException {
/* 607 */     if (this.tag == 12)
/* 608 */       return getUTF8String(); 
/* 609 */     if (this.tag == 19)
/* 610 */       return getPrintableString(); 
/* 611 */     if (this.tag == 20)
/* 612 */       return getT61String(); 
/* 613 */     if (this.tag == 22) {
/* 614 */       return getIA5String();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 619 */     if (this.tag == 30)
/* 620 */       return getBMPString(); 
/* 621 */     if (this.tag == 27) {
/* 622 */       return getGeneralString();
/*     */     }
/* 624 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBitString(boolean paramBoolean) throws IOException {
/* 635 */     if (!paramBoolean && 
/* 636 */       this.tag != 3) {
/* 637 */       throw new IOException("DerValue.getBitString, not a bit string " + this.tag);
/*     */     }
/*     */     
/* 640 */     return this.buffer.getBitString();
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
/*     */   public BitArray getUnalignedBitString(boolean paramBoolean) throws IOException {
/* 652 */     if (!paramBoolean && 
/* 653 */       this.tag != 3) {
/* 654 */       throw new IOException("DerValue.getBitString, not a bit string " + this.tag);
/*     */     }
/*     */     
/* 657 */     return this.buffer.getUnalignedBitString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getDataBytes() throws IOException {
/* 665 */     byte[] arrayOfByte = new byte[this.length];
/* 666 */     synchronized (this.data) {
/* 667 */       this.data.reset();
/* 668 */       this.data.getBytes(arrayOfByte);
/*     */     } 
/* 670 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrintableString() throws IOException {
/* 680 */     if (this.tag != 19) {
/* 681 */       throw new IOException("DerValue.getPrintableString, not a string " + this.tag);
/*     */     }
/*     */     
/* 684 */     return new String(getDataBytes(), "ASCII");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getT61String() throws IOException {
/* 693 */     if (this.tag != 20) {
/* 694 */       throw new IOException("DerValue.getT61String, not T61 " + this.tag);
/*     */     }
/*     */     
/* 697 */     return new String(getDataBytes(), "ISO-8859-1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIA5String() throws IOException {
/* 706 */     if (this.tag != 22) {
/* 707 */       throw new IOException("DerValue.getIA5String, not IA5 " + this.tag);
/*     */     }
/*     */     
/* 710 */     return new String(getDataBytes(), "ASCII");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBMPString() throws IOException {
/* 720 */     if (this.tag != 30) {
/* 721 */       throw new IOException("DerValue.getBMPString, not BMP " + this.tag);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 726 */     return new String(getDataBytes(), "UnicodeBigUnmarked");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUTF8String() throws IOException {
/* 736 */     if (this.tag != 12) {
/* 737 */       throw new IOException("DerValue.getUTF8String, not UTF-8 " + this.tag);
/*     */     }
/*     */     
/* 740 */     return new String(getDataBytes(), "UTF8");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGeneralString() throws IOException {
/* 750 */     if (this.tag != 27) {
/* 751 */       throw new IOException("DerValue.getGeneralString, not GeneralString " + this.tag);
/*     */     }
/*     */     
/* 754 */     return new String(getDataBytes(), "ASCII");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getUTCTime() throws IOException {
/* 763 */     if (this.tag != 23) {
/* 764 */       throw new IOException("DerValue.getUTCTime, not a UtcTime: " + this.tag);
/*     */     }
/* 766 */     return this.buffer.getUTCTime(this.data.available());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getGeneralizedTime() throws IOException {
/* 775 */     if (this.tag != 24) {
/* 776 */       throw new IOException("DerValue.getGeneralizedTime, not a GeneralizedTime: " + this.tag);
/*     */     }
/*     */     
/* 779 */     return this.buffer.getGeneralizedTime(this.data.available());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 789 */     if (paramObject instanceof DerValue) {
/* 790 */       return equals((DerValue)paramObject);
/*     */     }
/* 792 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(DerValue paramDerValue) {
/* 803 */     if (this == paramDerValue) {
/* 804 */       return true;
/*     */     }
/* 806 */     if (this.tag != paramDerValue.tag) {
/* 807 */       return false;
/*     */     }
/* 809 */     if (this.data == paramDerValue.data) {
/* 810 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 814 */     return 
/* 815 */       (System.identityHashCode(this.data) > System.identityHashCode(paramDerValue.data)) ? 
/* 816 */       doEquals(this, paramDerValue) : 
/* 817 */       doEquals(paramDerValue, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean doEquals(DerValue paramDerValue1, DerValue paramDerValue2) {
/* 824 */     synchronized (paramDerValue1.data) {
/* 825 */       synchronized (paramDerValue2.data) {
/* 826 */         paramDerValue1.data.reset();
/* 827 */         paramDerValue2.data.reset();
/* 828 */         return paramDerValue1.buffer.equals(paramDerValue2.buffer);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     try {
/* 841 */       String str = getAsString();
/* 842 */       if (str != null)
/* 843 */         return "\"" + str + "\""; 
/* 844 */       if (this.tag == 5)
/* 845 */         return "[DerValue, null]"; 
/* 846 */       if (this.tag == 6) {
/* 847 */         return "OID." + getOID();
/*     */       }
/*     */ 
/*     */       
/* 851 */       return "[DerValue, tag = " + this.tag + ", length = " + this.length + "]";
/*     */     }
/* 853 */     catch (IOException iOException) {
/* 854 */       throw new IllegalArgumentException("misformatted DER value");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toByteArray() throws IOException {
/* 865 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */     
/* 867 */     encode(derOutputStream);
/* 868 */     this.data.reset();
/* 869 */     return derOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerInputStream toDerInputStream() throws IOException {
/* 879 */     if (this.tag == 48 || this.tag == 49)
/* 880 */       return new DerInputStream(this.buffer); 
/* 881 */     throw new IOException("toDerInputStream rejects tag type " + this.tag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/* 888 */     return this.length;
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
/*     */   public static boolean isPrintableStringChar(char paramChar) {
/* 909 */     if ((paramChar >= 'a' && paramChar <= 'z') || (paramChar >= 'A' && paramChar <= 'Z') || (paramChar >= '0' && paramChar <= '9'))
/*     */     {
/* 911 */       return true;
/*     */     }
/* 913 */     switch (paramChar) {
/*     */       case ' ':
/*     */       case '\'':
/*     */       case '(':
/*     */       case ')':
/*     */       case '+':
/*     */       case ',':
/*     */       case '-':
/*     */       case '.':
/*     */       case '/':
/*     */       case ':':
/*     */       case '=':
/*     */       case '?':
/* 926 */         return true;
/*     */     } 
/* 928 */     return false;
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
/*     */   public static byte createTag(byte paramByte1, boolean paramBoolean, byte paramByte2) {
/* 943 */     byte b = (byte)(paramByte1 | paramByte2);
/* 944 */     if (paramBoolean) {
/* 945 */       b = (byte)(b | 0x20);
/*     */     }
/* 947 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTag(byte paramByte) {
/* 957 */     this.tag = paramByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 966 */     return toString().hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/DerValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */