/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DerOutputStream
/*     */   extends ByteArrayOutputStream
/*     */   implements DerEncoder
/*     */ {
/*     */   public DerOutputStream(int paramInt) {
/*  61 */     super(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerOutputStream() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte paramByte, byte[] paramArrayOfbyte) throws IOException {
/*  78 */     write(paramByte);
/*  79 */     putLength(paramArrayOfbyte.length);
/*  80 */     write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*     */   public void write(byte paramByte, DerOutputStream paramDerOutputStream) throws IOException {
/*  93 */     write(paramByte);
/*  94 */     putLength(paramDerOutputStream.count);
/*  95 */     write(paramDerOutputStream.buf, 0, paramDerOutputStream.count);
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
/*     */   public void writeImplicit(byte paramByte, DerOutputStream paramDerOutputStream) throws IOException {
/* 117 */     write(paramByte);
/* 118 */     write(paramDerOutputStream.buf, 1, paramDerOutputStream.count - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putDerValue(DerValue paramDerValue) throws IOException {
/* 125 */     paramDerValue.encode(this);
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
/*     */   public void putBoolean(boolean paramBoolean) throws IOException {
/* 140 */     write(1);
/* 141 */     putLength(1);
/* 142 */     if (paramBoolean) {
/* 143 */       write(255);
/*     */     } else {
/* 145 */       write(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putEnumerated(int paramInt) throws IOException {
/* 154 */     write(10);
/* 155 */     putIntegerContents(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putInteger(BigInteger paramBigInteger) throws IOException {
/* 164 */     write(2);
/* 165 */     byte[] arrayOfByte = paramBigInteger.toByteArray();
/* 166 */     putLength(arrayOfByte.length);
/* 167 */     write(arrayOfByte, 0, arrayOfByte.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putInteger(Integer paramInteger) throws IOException {
/* 175 */     putInteger(paramInteger.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putInteger(int paramInt) throws IOException {
/* 183 */     write(2);
/* 184 */     putIntegerContents(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   private void putIntegerContents(int paramInt) throws IOException {
/* 189 */     byte[] arrayOfByte = new byte[4];
/* 190 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 194 */     arrayOfByte[3] = (byte)(paramInt & 0xFF);
/* 195 */     arrayOfByte[2] = (byte)((paramInt & 0xFF00) >>> 8);
/* 196 */     arrayOfByte[1] = (byte)((paramInt & 0xFF0000) >>> 16);
/* 197 */     arrayOfByte[0] = (byte)((paramInt & 0xFF000000) >>> 24);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     if (arrayOfByte[0] == -1) {
/*     */ 
/*     */ 
/*     */       
/* 206 */       for (byte b = 0; b < 3 && 
/* 207 */         arrayOfByte[b] == -1 && (arrayOfByte[b + 1] & 0x80) == 128; b++)
/*     */       {
/* 209 */         b1++;
/*     */       
/*     */       }
/*     */     }
/* 213 */     else if (arrayOfByte[0] == 0) {
/*     */ 
/*     */ 
/*     */       
/* 217 */       for (byte b = 0; b < 3 && 
/* 218 */         arrayOfByte[b] == 0 && (arrayOfByte[b + 1] & 0x80) == 0; b++)
/*     */       {
/* 220 */         b1++;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 226 */     putLength(4 - b1);
/* 227 */     for (byte b2 = b1; b2 < 4; b2++) {
/* 228 */       write(arrayOfByte[b2]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putBitString(byte[] paramArrayOfbyte) throws IOException {
/* 238 */     write(3);
/* 239 */     putLength(paramArrayOfbyte.length + 1);
/* 240 */     write(0);
/* 241 */     write(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putUnalignedBitString(BitArray paramBitArray) throws IOException {
/* 251 */     byte[] arrayOfByte = paramBitArray.toByteArray();
/*     */     
/* 253 */     write(3);
/* 254 */     putLength(arrayOfByte.length + 1);
/* 255 */     write(arrayOfByte.length * 8 - paramBitArray.length());
/* 256 */     write(arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putTruncatedUnalignedBitString(BitArray paramBitArray) throws IOException {
/* 266 */     putUnalignedBitString(paramBitArray.truncate());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putOctetString(byte[] paramArrayOfbyte) throws IOException {
/* 275 */     write((byte)4, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putNull() throws IOException {
/* 283 */     write(5);
/* 284 */     putLength(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putOID(ObjectIdentifier paramObjectIdentifier) throws IOException {
/* 292 */     paramObjectIdentifier.encode(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putSequence(DerValue[] paramArrayOfDerValue) throws IOException {
/* 301 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */ 
/*     */     
/* 304 */     for (byte b = 0; b < paramArrayOfDerValue.length; b++) {
/* 305 */       paramArrayOfDerValue[b].encode(derOutputStream);
/*     */     }
/* 307 */     write((byte)48, derOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putSet(DerValue[] paramArrayOfDerValue) throws IOException {
/* 318 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */ 
/*     */     
/* 321 */     for (byte b = 0; b < paramArrayOfDerValue.length; b++) {
/* 322 */       paramArrayOfDerValue[b].encode(derOutputStream);
/*     */     }
/* 324 */     write((byte)49, derOutputStream);
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
/*     */   public void putOrderedSetOf(byte paramByte, DerEncoder[] paramArrayOfDerEncoder) throws IOException {
/* 338 */     putOrderedSet(paramByte, paramArrayOfDerEncoder, lexOrder);
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
/*     */   public void putOrderedSet(byte paramByte, DerEncoder[] paramArrayOfDerEncoder) throws IOException {
/* 352 */     putOrderedSet(paramByte, paramArrayOfDerEncoder, tagOrder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 359 */   private static ByteArrayLexOrder lexOrder = new ByteArrayLexOrder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 365 */   private static ByteArrayTagOrder tagOrder = new ByteArrayTagOrder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void putOrderedSet(byte paramByte, DerEncoder[] paramArrayOfDerEncoder, Comparator<byte[]> paramComparator) throws IOException {
/* 375 */     DerOutputStream[] arrayOfDerOutputStream = new DerOutputStream[paramArrayOfDerEncoder.length];
/*     */     
/* 377 */     for (byte b1 = 0; b1 < paramArrayOfDerEncoder.length; b1++) {
/* 378 */       arrayOfDerOutputStream[b1] = new DerOutputStream();
/* 379 */       paramArrayOfDerEncoder[b1].derEncode(arrayOfDerOutputStream[b1]);
/*     */     } 
/*     */ 
/*     */     
/* 383 */     byte[][] arrayOfByte = new byte[arrayOfDerOutputStream.length][];
/* 384 */     for (byte b2 = 0; b2 < arrayOfDerOutputStream.length; b2++) {
/* 385 */       arrayOfByte[b2] = arrayOfDerOutputStream[b2].toByteArray();
/*     */     }
/* 387 */     Arrays.sort(arrayOfByte, (Comparator)paramComparator);
/*     */     
/* 389 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 390 */     for (byte b3 = 0; b3 < arrayOfDerOutputStream.length; b3++) {
/* 391 */       derOutputStream.write(arrayOfByte[b3]);
/*     */     }
/* 393 */     write(paramByte, derOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putUTF8String(String paramString) throws IOException {
/* 401 */     writeString(paramString, (byte)12, "UTF8");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putPrintableString(String paramString) throws IOException {
/* 408 */     writeString(paramString, (byte)19, "ASCII");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putT61String(String paramString) throws IOException {
/* 419 */     writeString(paramString, (byte)20, "ISO-8859-1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putIA5String(String paramString) throws IOException {
/* 426 */     writeString(paramString, (byte)22, "ASCII");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putBMPString(String paramString) throws IOException {
/* 433 */     writeString(paramString, (byte)30, "UnicodeBigUnmarked");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putGeneralString(String paramString) throws IOException {
/* 440 */     writeString(paramString, (byte)27, "ASCII");
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
/*     */   private void writeString(String paramString1, byte paramByte, String paramString2) throws IOException {
/* 454 */     byte[] arrayOfByte = paramString1.getBytes(paramString2);
/* 455 */     write(paramByte);
/* 456 */     putLength(arrayOfByte.length);
/* 457 */     write(arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putUTCTime(Date paramDate) throws IOException {
/* 467 */     putTime(paramDate, (byte)23);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putGeneralizedTime(Date paramDate) throws IOException {
/* 477 */     putTime(paramDate, (byte)24);
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
/*     */   private void putTime(Date paramDate, byte paramByte) throws IOException {
/* 493 */     TimeZone timeZone = TimeZone.getTimeZone("GMT");
/* 494 */     String str = null;
/*     */     
/* 496 */     if (paramByte == 23) {
/* 497 */       str = "yyMMddHHmmss'Z'";
/*     */     } else {
/* 499 */       paramByte = 24;
/* 500 */       str = "yyyyMMddHHmmss'Z'";
/*     */     } 
/*     */     
/* 503 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, Locale.US);
/* 504 */     simpleDateFormat.setTimeZone(timeZone);
/* 505 */     byte[] arrayOfByte = simpleDateFormat.format(paramDate).getBytes("ISO-8859-1");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 511 */     write(paramByte);
/* 512 */     putLength(arrayOfByte.length);
/* 513 */     write(arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putLength(int paramInt) throws IOException {
/* 523 */     if (paramInt < 128) {
/* 524 */       write((byte)paramInt);
/*     */     }
/* 526 */     else if (paramInt < 256) {
/* 527 */       write(-127);
/* 528 */       write((byte)paramInt);
/*     */     }
/* 530 */     else if (paramInt < 65536) {
/* 531 */       write(-126);
/* 532 */       write((byte)(paramInt >> 8));
/* 533 */       write((byte)paramInt);
/*     */     }
/* 535 */     else if (paramInt < 16777216) {
/* 536 */       write(-125);
/* 537 */       write((byte)(paramInt >> 16));
/* 538 */       write((byte)(paramInt >> 8));
/* 539 */       write((byte)paramInt);
/*     */     } else {
/*     */       
/* 542 */       write(-124);
/* 543 */       write((byte)(paramInt >> 24));
/* 544 */       write((byte)(paramInt >> 16));
/* 545 */       write((byte)(paramInt >> 8));
/* 546 */       write((byte)paramInt);
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
/*     */   public void putTag(byte paramByte1, boolean paramBoolean, byte paramByte2) {
/* 560 */     byte b = (byte)(paramByte1 | paramByte2);
/* 561 */     if (paramBoolean) {
/* 562 */       b = (byte)(b | 0x20);
/*     */     }
/* 564 */     write(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void derEncode(OutputStream paramOutputStream) throws IOException {
/* 574 */     paramOutputStream.write(toByteArray());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/DerOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */