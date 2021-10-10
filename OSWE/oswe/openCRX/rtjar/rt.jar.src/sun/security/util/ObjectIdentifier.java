/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectIdentifier
/*     */   implements Serializable
/*     */ {
/*  60 */   private byte[] encoding = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile transient String stringForm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 8697030238860181294L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   private Object components = null;
/*     */ 
/*     */ 
/*     */   
/* 104 */   private int componentLen = -1;
/*     */ 
/*     */   
/*     */   private transient boolean componentsCalculated = false;
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 111 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 113 */     if (this.encoding == null) {
/* 114 */       int[] arrayOfInt = (int[])this.components;
/* 115 */       if (this.componentLen > arrayOfInt.length) {
/* 116 */         this.componentLen = arrayOfInt.length;
/*     */       }
/* 118 */       init(arrayOfInt, this.componentLen);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 124 */     if (!this.componentsCalculated) {
/* 125 */       int[] arrayOfInt = toIntArray();
/* 126 */       if (arrayOfInt != null) {
/* 127 */         this.components = arrayOfInt;
/* 128 */         this.componentLen = arrayOfInt.length;
/*     */       } else {
/* 130 */         this.components = HugeOidNotSupportedByOldJDK.theOne;
/*     */       } 
/* 132 */       this.componentsCalculated = true;
/*     */     } 
/* 134 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */   
/*     */   static class HugeOidNotSupportedByOldJDK implements Serializable {
/*     */     private static final long serialVersionUID = 1L;
/* 139 */     static HugeOidNotSupportedByOldJDK theOne = new HugeOidNotSupportedByOldJDK();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdentifier(String paramString) throws IOException {
/* 148 */     byte b1 = 46;
/* 149 */     int i = 0;
/* 150 */     int j = 0;
/*     */     
/* 152 */     int k = 0;
/* 153 */     byte[] arrayOfByte = new byte[paramString.length()];
/* 154 */     int m = 0;
/* 155 */     byte b2 = 0;
/*     */     
/*     */     try {
/* 158 */       String str = null;
/*     */       do {
/* 160 */         int n = 0;
/* 161 */         j = paramString.indexOf(b1, i);
/* 162 */         if (j == -1) {
/* 163 */           str = paramString.substring(i);
/* 164 */           n = paramString.length() - i;
/*     */         } else {
/* 166 */           str = paramString.substring(i, j);
/* 167 */           n = j - i;
/*     */         } 
/*     */         
/* 170 */         if (n > 9) {
/* 171 */           BigInteger bigInteger = new BigInteger(str);
/* 172 */           if (!b2) {
/* 173 */             checkFirstComponent(bigInteger);
/* 174 */             m = bigInteger.intValue();
/*     */           } else {
/* 176 */             if (b2 == 1) {
/* 177 */               checkSecondComponent(m, bigInteger);
/* 178 */               bigInteger = bigInteger.add(BigInteger.valueOf((40 * m)));
/*     */             } else {
/* 180 */               checkOtherComponent(b2, bigInteger);
/*     */             } 
/* 182 */             k += pack7Oid(bigInteger, arrayOfByte, k);
/*     */           } 
/*     */         } else {
/* 185 */           int i1 = Integer.parseInt(str);
/* 186 */           if (!b2) {
/* 187 */             checkFirstComponent(i1);
/* 188 */             m = i1;
/*     */           } else {
/* 190 */             if (b2 == 1) {
/* 191 */               checkSecondComponent(m, i1);
/* 192 */               i1 += 40 * m;
/*     */             } else {
/* 194 */               checkOtherComponent(b2, i1);
/*     */             } 
/* 196 */             k += pack7Oid(i1, arrayOfByte, k);
/*     */           } 
/*     */         } 
/* 199 */         i = j + 1;
/* 200 */         b2++;
/* 201 */       } while (j != -1);
/*     */       
/* 203 */       checkCount(b2);
/* 204 */       this.encoding = new byte[k];
/* 205 */       System.arraycopy(arrayOfByte, 0, this.encoding, 0, k);
/* 206 */       this.stringForm = paramString;
/* 207 */     } catch (IOException iOException) {
/* 208 */       throw iOException;
/* 209 */     } catch (Exception exception) {
/* 210 */       throw new IOException("ObjectIdentifier() -- Invalid format: " + exception
/* 211 */           .toString(), exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectIdentifier(int[] paramArrayOfint) throws IOException {
/* 221 */     checkCount(paramArrayOfint.length);
/* 222 */     checkFirstComponent(paramArrayOfint[0]);
/* 223 */     checkSecondComponent(paramArrayOfint[0], paramArrayOfint[1]);
/* 224 */     for (byte b = 2; b < paramArrayOfint.length; b++)
/* 225 */       checkOtherComponent(b, paramArrayOfint[b]); 
/* 226 */     init(paramArrayOfint, paramArrayOfint.length);
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
/*     */   public ObjectIdentifier(DerInputStream paramDerInputStream) throws IOException {
/* 255 */     byte b = (byte)paramDerInputStream.getByte();
/* 256 */     if (b != 6) {
/* 257 */       throw new IOException("ObjectIdentifier() -- data isn't an object ID (tag = " + b + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 262 */     int i = paramDerInputStream.getLength();
/* 263 */     if (i > paramDerInputStream.available()) {
/* 264 */       throw new IOException("ObjectIdentifier() -- length exceedsdata available.  Length: " + i + ", Available: " + paramDerInputStream
/*     */           
/* 266 */           .available());
/*     */     }
/* 268 */     this.encoding = new byte[i];
/* 269 */     paramDerInputStream.getBytes(this.encoding);
/* 270 */     check(this.encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ObjectIdentifier(DerInputBuffer paramDerInputBuffer) throws IOException {
/* 280 */     DerInputStream derInputStream = new DerInputStream(paramDerInputBuffer);
/* 281 */     this.encoding = new byte[derInputStream.available()];
/* 282 */     derInputStream.getBytes(this.encoding);
/* 283 */     check(this.encoding);
/*     */   }
/*     */   
/*     */   private void init(int[] paramArrayOfint, int paramInt) {
/* 287 */     int i = 0;
/* 288 */     byte[] arrayOfByte = new byte[paramInt * 5 + 1];
/*     */     
/* 290 */     if (paramArrayOfint[1] < Integer.MAX_VALUE - paramArrayOfint[0] * 40) {
/* 291 */       i += pack7Oid(paramArrayOfint[0] * 40 + paramArrayOfint[1], arrayOfByte, i);
/*     */     } else {
/* 293 */       BigInteger bigInteger = BigInteger.valueOf(paramArrayOfint[1]);
/* 294 */       bigInteger = bigInteger.add(BigInteger.valueOf((paramArrayOfint[0] * 40)));
/* 295 */       i += pack7Oid(bigInteger, arrayOfByte, i);
/*     */     } 
/*     */     
/* 298 */     for (byte b = 2; b < paramInt; b++) {
/* 299 */       i += pack7Oid(paramArrayOfint[b], arrayOfByte, i);
/*     */     }
/* 301 */     this.encoding = new byte[i];
/* 302 */     System.arraycopy(arrayOfByte, 0, this.encoding, 0, i);
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
/*     */   public static ObjectIdentifier newInternal(int[] paramArrayOfint) {
/*     */     try {
/* 316 */       return new ObjectIdentifier(paramArrayOfint);
/* 317 */     } catch (IOException iOException) {
/* 318 */       throw new RuntimeException(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 328 */     paramDerOutputStream.write((byte)6, this.encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean equals(ObjectIdentifier paramObjectIdentifier) {
/* 336 */     return equals(paramObjectIdentifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 346 */     if (this == paramObject) {
/* 347 */       return true;
/*     */     }
/* 349 */     if (!(paramObject instanceof ObjectIdentifier)) {
/* 350 */       return false;
/*     */     }
/* 352 */     ObjectIdentifier objectIdentifier = (ObjectIdentifier)paramObject;
/* 353 */     return Arrays.equals(this.encoding, objectIdentifier.encoding);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 358 */     return Arrays.hashCode(this.encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] toIntArray() {
/* 368 */     int i = this.encoding.length;
/* 369 */     int[] arrayOfInt = new int[20];
/* 370 */     byte b1 = 0;
/* 371 */     int j = 0;
/* 372 */     for (byte b2 = 0; b2 < i; b2++) {
/* 373 */       if ((this.encoding[b2] & 0x80) == 0) {
/*     */         
/* 375 */         if (b2 - j + 1 > 4) {
/* 376 */           BigInteger bigInteger = new BigInteger(pack(this.encoding, j, b2 - j + 1, 7, 8));
/* 377 */           if (j == 0) {
/* 378 */             arrayOfInt[b1++] = 2;
/* 379 */             BigInteger bigInteger1 = bigInteger.subtract(BigInteger.valueOf(80L));
/* 380 */             if (bigInteger1.compareTo(BigInteger.valueOf(2147483647L)) == 1) {
/* 381 */               return null;
/*     */             }
/* 383 */             arrayOfInt[b1++] = bigInteger1.intValue();
/*     */           } else {
/*     */             
/* 386 */             if (bigInteger.compareTo(BigInteger.valueOf(2147483647L)) == 1) {
/* 387 */               return null;
/*     */             }
/* 389 */             arrayOfInt[b1++] = bigInteger.intValue();
/*     */           } 
/*     */         } else {
/*     */           
/* 393 */           int k = 0;
/* 394 */           for (int m = j; m <= b2; m++) {
/* 395 */             k <<= 7;
/* 396 */             byte b = this.encoding[m];
/* 397 */             k |= b & Byte.MAX_VALUE;
/*     */           } 
/* 399 */           if (j == 0) {
/* 400 */             if (k < 80) {
/* 401 */               arrayOfInt[b1++] = k / 40;
/* 402 */               arrayOfInt[b1++] = k % 40;
/*     */             } else {
/* 404 */               arrayOfInt[b1++] = 2;
/* 405 */               arrayOfInt[b1++] = k - 80;
/*     */             } 
/*     */           } else {
/* 408 */             arrayOfInt[b1++] = k;
/*     */           } 
/*     */         } 
/* 411 */         j = b2 + 1;
/*     */       } 
/* 413 */       if (b1 >= arrayOfInt.length) {
/* 414 */         arrayOfInt = Arrays.copyOf(arrayOfInt, b1 + 10);
/*     */       }
/*     */     } 
/* 417 */     return Arrays.copyOf(arrayOfInt, b1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 428 */     String str = this.stringForm;
/* 429 */     if (str == null) {
/* 430 */       int i = this.encoding.length;
/* 431 */       StringBuffer stringBuffer = new StringBuffer(i * 4);
/*     */       
/* 433 */       int j = 0;
/* 434 */       for (byte b = 0; b < i; b++) {
/* 435 */         if ((this.encoding[b] & 0x80) == 0) {
/*     */           
/* 437 */           if (j) {
/* 438 */             stringBuffer.append('.');
/*     */           }
/* 440 */           if (b - j + 1 > 4) {
/* 441 */             BigInteger bigInteger = new BigInteger(pack(this.encoding, j, b - j + 1, 7, 8));
/* 442 */             if (j == 0) {
/*     */ 
/*     */               
/* 445 */               stringBuffer.append("2.");
/* 446 */               stringBuffer.append(bigInteger.subtract(BigInteger.valueOf(80L)));
/*     */             } else {
/* 448 */               stringBuffer.append(bigInteger);
/*     */             } 
/*     */           } else {
/* 451 */             int k = 0;
/* 452 */             for (int m = j; m <= b; m++) {
/* 453 */               k <<= 7;
/* 454 */               byte b1 = this.encoding[m];
/* 455 */               k |= b1 & Byte.MAX_VALUE;
/*     */             } 
/* 457 */             if (j == 0) {
/* 458 */               if (k < 80) {
/* 459 */                 stringBuffer.append(k / 40);
/* 460 */                 stringBuffer.append('.');
/* 461 */                 stringBuffer.append(k % 40);
/*     */               } else {
/* 463 */                 stringBuffer.append("2.");
/* 464 */                 stringBuffer.append(k - 80);
/*     */               } 
/*     */             } else {
/* 467 */               stringBuffer.append(k);
/*     */             } 
/*     */           } 
/* 470 */           j = b + 1;
/*     */         } 
/*     */       } 
/* 473 */       str = stringBuffer.toString();
/* 474 */       this.stringForm = str;
/*     */     } 
/* 476 */     return str;
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
/*     */   private static byte[] pack(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 505 */     assert paramInt3 > 0 && paramInt3 <= 8 : "input NUB must be between 1 and 8";
/* 506 */     assert paramInt4 > 0 && paramInt4 <= 8 : "output NUB must be between 1 and 8";
/*     */     
/* 508 */     if (paramInt3 == paramInt4) {
/* 509 */       return (byte[])paramArrayOfbyte.clone();
/*     */     }
/*     */     
/* 512 */     int i = paramInt2 * paramInt3;
/* 513 */     byte[] arrayOfByte = new byte[(i + paramInt4 - 1) / paramInt4];
/*     */ 
/*     */     
/* 516 */     int j = 0;
/*     */ 
/*     */     
/* 519 */     int k = (i + paramInt4 - 1) / paramInt4 * paramInt4 - i;
/*     */     
/* 521 */     while (j < i) {
/* 522 */       int m = paramInt3 - j % paramInt3;
/* 523 */       if (m > paramInt4 - k % paramInt4) {
/* 524 */         m = paramInt4 - k % paramInt4;
/*     */       }
/*     */       
/* 527 */       arrayOfByte[k / paramInt4] = (byte)(arrayOfByte[k / paramInt4] | (paramArrayOfbyte[paramInt1 + j / paramInt3] + 256 >> paramInt3 - j % paramInt3 - m & (1 << m) - 1) << paramInt4 - k % paramInt4 - m);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 532 */       j += m;
/* 533 */       k += m;
/*     */     } 
/* 535 */     return arrayOfByte;
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
/*     */   private static int pack7Oid(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) {
/* 548 */     byte[] arrayOfByte = pack(paramArrayOfbyte1, paramInt1, paramInt2, 8, 7);
/* 549 */     int i = arrayOfByte.length - 1;
/* 550 */     for (int j = arrayOfByte.length - 2; j >= 0; j--) {
/* 551 */       if (arrayOfByte[j] != 0) {
/* 552 */         i = j;
/*     */       }
/* 554 */       arrayOfByte[j] = (byte)(arrayOfByte[j] | 0x80);
/*     */     } 
/* 556 */     System.arraycopy(arrayOfByte, i, paramArrayOfbyte2, paramInt3, arrayOfByte.length - i);
/* 557 */     return arrayOfByte.length - i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int pack8(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) {
/* 568 */     byte[] arrayOfByte = pack(paramArrayOfbyte1, paramInt1, paramInt2, 7, 8);
/* 569 */     int i = arrayOfByte.length - 1;
/* 570 */     for (int j = arrayOfByte.length - 2; j >= 0; j--) {
/* 571 */       if (arrayOfByte[j] != 0) {
/* 572 */         i = j;
/*     */       }
/*     */     } 
/* 575 */     System.arraycopy(arrayOfByte, i, paramArrayOfbyte2, paramInt3, arrayOfByte.length - i);
/* 576 */     return arrayOfByte.length - i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int pack7Oid(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
/* 583 */     byte[] arrayOfByte = new byte[4];
/* 584 */     arrayOfByte[0] = (byte)(paramInt1 >> 24);
/* 585 */     arrayOfByte[1] = (byte)(paramInt1 >> 16);
/* 586 */     arrayOfByte[2] = (byte)(paramInt1 >> 8);
/* 587 */     arrayOfByte[3] = (byte)paramInt1;
/* 588 */     return pack7Oid(arrayOfByte, 0, 4, paramArrayOfbyte, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int pack7Oid(BigInteger paramBigInteger, byte[] paramArrayOfbyte, int paramInt) {
/* 595 */     byte[] arrayOfByte = paramBigInteger.toByteArray();
/* 596 */     return pack7Oid(arrayOfByte, 0, arrayOfByte.length, paramArrayOfbyte, paramInt);
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
/*     */   private static void check(byte[] paramArrayOfbyte) throws IOException {
/* 612 */     int i = paramArrayOfbyte.length;
/* 613 */     if (i < 1 || (paramArrayOfbyte[i - 1] & 0x80) != 0)
/*     */     {
/* 615 */       throw new IOException("ObjectIdentifier() -- Invalid DER encoding, not ended");
/*     */     }
/*     */     
/* 618 */     for (byte b = 0; b < i; b++) {
/*     */       
/* 620 */       if (paramArrayOfbyte[b] == Byte.MIN_VALUE && (b == 0 || (paramArrayOfbyte[b - 1] & 0x80) == 0))
/*     */       {
/* 622 */         throw new IOException("ObjectIdentifier() -- Invalid DER encoding, useless extra octet detected");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkCount(int paramInt) throws IOException {
/* 628 */     if (paramInt < 2) {
/* 629 */       throw new IOException("ObjectIdentifier() -- Must be at least two oid components ");
/*     */     }
/*     */   }
/*     */   
/*     */   private static void checkFirstComponent(int paramInt) throws IOException {
/* 634 */     if (paramInt < 0 || paramInt > 2) {
/* 635 */       throw new IOException("ObjectIdentifier() -- First oid component is invalid ");
/*     */     }
/*     */   }
/*     */   
/*     */   private static void checkFirstComponent(BigInteger paramBigInteger) throws IOException {
/* 640 */     if (paramBigInteger.signum() == -1 || paramBigInteger
/* 641 */       .compareTo(BigInteger.valueOf(2L)) == 1) {
/* 642 */       throw new IOException("ObjectIdentifier() -- First oid component is invalid ");
/*     */     }
/*     */   }
/*     */   
/*     */   private static void checkSecondComponent(int paramInt1, int paramInt2) throws IOException {
/* 647 */     if (paramInt2 < 0 || (paramInt1 != 2 && paramInt2 > 39)) {
/* 648 */       throw new IOException("ObjectIdentifier() -- Second oid component is invalid ");
/*     */     }
/*     */   }
/*     */   
/*     */   private static void checkSecondComponent(int paramInt, BigInteger paramBigInteger) throws IOException {
/* 653 */     if (paramBigInteger.signum() == -1 || (paramInt != 2 && paramBigInteger
/*     */       
/* 655 */       .compareTo(BigInteger.valueOf(39L)) == 1)) {
/* 656 */       throw new IOException("ObjectIdentifier() -- Second oid component is invalid ");
/*     */     }
/*     */   }
/*     */   
/*     */   private static void checkOtherComponent(int paramInt1, int paramInt2) throws IOException {
/* 661 */     if (paramInt2 < 0) {
/* 662 */       throw new IOException("ObjectIdentifier() -- oid component #" + (paramInt1 + 1) + " must be non-negative ");
/*     */     }
/*     */   }
/*     */   
/*     */   private static void checkOtherComponent(int paramInt, BigInteger paramBigInteger) throws IOException {
/* 667 */     if (paramBigInteger.signum() == -1)
/* 668 */       throw new IOException("ObjectIdentifier() -- oid component #" + (paramInt + 1) + " must be non-negative "); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/ObjectIdentifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */