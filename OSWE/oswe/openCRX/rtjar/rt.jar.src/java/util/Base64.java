/*     */ package java.util;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Base64
/*     */ {
/*     */   public static Encoder getEncoder() {
/*  88 */     return Encoder.RFC4648;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Encoder getUrlEncoder() {
/*  99 */     return Encoder.RFC4648_URLSAFE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Encoder getMimeEncoder() {
/* 109 */     return Encoder.RFC2045;
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
/*     */   public static Encoder getMimeEncoder(int paramInt, byte[] paramArrayOfbyte) {
/* 131 */     Objects.requireNonNull(paramArrayOfbyte);
/* 132 */     int[] arrayOfInt = Decoder.fromBase64;
/* 133 */     for (byte b : paramArrayOfbyte) {
/* 134 */       if (arrayOfInt[b & 0xFF] != -1)
/* 135 */         throw new IllegalArgumentException("Illegal base64 line separator character 0x" + 
/* 136 */             Integer.toString(b, 16)); 
/*     */     } 
/* 138 */     if (paramInt <= 0) {
/* 139 */       return Encoder.RFC4648;
/*     */     }
/* 141 */     return new Encoder(false, paramArrayOfbyte, paramInt >> 2 << 2, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Decoder getDecoder() {
/* 151 */     return Decoder.RFC4648;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Decoder getUrlDecoder() {
/* 162 */     return Decoder.RFC4648_URLSAFE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Decoder getMimeDecoder() {
/* 172 */     return Decoder.RFC2045;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Encoder
/*     */   {
/*     */     private final byte[] newline;
/*     */ 
/*     */ 
/*     */     
/*     */     private final int linemax;
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean isURL;
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean doPadding;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Encoder(boolean param1Boolean1, byte[] param1ArrayOfbyte, int param1Int, boolean param1Boolean2) {
/* 198 */       this.isURL = param1Boolean1;
/* 199 */       this.newline = param1ArrayOfbyte;
/* 200 */       this.linemax = param1Int;
/* 201 */       this.doPadding = param1Boolean2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     private static final char[] toBase64 = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     private static final char[] toBase64URL = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_' };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int MIMELINEMAX = 76;
/*     */ 
/*     */ 
/*     */     
/* 231 */     private static final byte[] CRLF = new byte[] { 13, 10 };
/*     */     
/* 233 */     static final Encoder RFC4648 = new Encoder(false, null, -1, true);
/* 234 */     static final Encoder RFC4648_URLSAFE = new Encoder(true, null, -1, true);
/* 235 */     static final Encoder RFC2045 = new Encoder(false, CRLF, 76, true);
/*     */     
/*     */     private final int outLength(int param1Int) {
/* 238 */       int i = 0;
/* 239 */       if (this.doPadding) {
/* 240 */         i = 4 * (param1Int + 2) / 3;
/*     */       } else {
/* 242 */         int j = param1Int % 3;
/* 243 */         i = 4 * param1Int / 3 + ((j == 0) ? 0 : (j + 1));
/*     */       } 
/* 245 */       if (this.linemax > 0)
/* 246 */         i += (i - 1) / this.linemax * this.newline.length; 
/* 247 */       return i;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] encode(byte[] param1ArrayOfbyte) {
/* 261 */       int i = outLength(param1ArrayOfbyte.length);
/* 262 */       byte[] arrayOfByte = new byte[i];
/* 263 */       int j = encode0(param1ArrayOfbyte, 0, param1ArrayOfbyte.length, arrayOfByte);
/* 264 */       if (j != arrayOfByte.length)
/* 265 */         return Arrays.copyOf(arrayOfByte, j); 
/* 266 */       return arrayOfByte;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int encode(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2) {
/* 289 */       int i = outLength(param1ArrayOfbyte1.length);
/* 290 */       if (param1ArrayOfbyte2.length < i) {
/* 291 */         throw new IllegalArgumentException("Output byte array is too small for encoding all input bytes");
/*     */       }
/* 293 */       return encode0(param1ArrayOfbyte1, 0, param1ArrayOfbyte1.length, param1ArrayOfbyte2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String encodeToString(byte[] param1ArrayOfbyte) {
/* 315 */       byte[] arrayOfByte = encode(param1ArrayOfbyte);
/* 316 */       return new String(arrayOfByte, 0, 0, arrayOfByte.length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ByteBuffer encode(ByteBuffer param1ByteBuffer) {
/* 334 */       int i = outLength(param1ByteBuffer.remaining());
/* 335 */       byte[] arrayOfByte = new byte[i];
/* 336 */       int j = 0;
/* 337 */       if (param1ByteBuffer.hasArray()) {
/* 338 */         j = encode0(param1ByteBuffer.array(), param1ByteBuffer
/* 339 */             .arrayOffset() + param1ByteBuffer.position(), param1ByteBuffer
/* 340 */             .arrayOffset() + param1ByteBuffer.limit(), arrayOfByte);
/*     */         
/* 342 */         param1ByteBuffer.position(param1ByteBuffer.limit());
/*     */       } else {
/* 344 */         byte[] arrayOfByte1 = new byte[param1ByteBuffer.remaining()];
/* 345 */         param1ByteBuffer.get(arrayOfByte1);
/* 346 */         j = encode0(arrayOfByte1, 0, arrayOfByte1.length, arrayOfByte);
/*     */       } 
/* 348 */       if (j != arrayOfByte.length)
/* 349 */         arrayOfByte = Arrays.copyOf(arrayOfByte, j); 
/* 350 */       return ByteBuffer.wrap(arrayOfByte);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OutputStream wrap(OutputStream param1OutputStream) {
/* 368 */       Objects.requireNonNull(param1OutputStream);
/* 369 */       return new Base64.EncOutputStream(param1OutputStream, this.isURL ? toBase64URL : toBase64, this.newline, this.linemax, this.doPadding);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Encoder withoutPadding() {
/* 386 */       if (!this.doPadding)
/* 387 */         return this; 
/* 388 */       return new Encoder(this.isURL, this.newline, this.linemax, false);
/*     */     }
/*     */     
/*     */     private int encode0(byte[] param1ArrayOfbyte1, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte2) {
/* 392 */       char[] arrayOfChar = this.isURL ? toBase64URL : toBase64;
/* 393 */       int i = param1Int1;
/* 394 */       int j = (param1Int2 - param1Int1) / 3 * 3;
/* 395 */       int k = param1Int1 + j;
/* 396 */       if (this.linemax > 0 && j > this.linemax / 4 * 3)
/* 397 */         j = this.linemax / 4 * 3; 
/* 398 */       int m = 0;
/* 399 */       while (i < k) {
/* 400 */         int n = Math.min(i + j, k); int i1, i2;
/* 401 */         for (i1 = i, i2 = m; i1 < n; ) {
/* 402 */           int i3 = (param1ArrayOfbyte1[i1++] & 0xFF) << 16 | (param1ArrayOfbyte1[i1++] & 0xFF) << 8 | param1ArrayOfbyte1[i1++] & 0xFF;
/*     */ 
/*     */           
/* 405 */           param1ArrayOfbyte2[i2++] = (byte)arrayOfChar[i3 >>> 18 & 0x3F];
/* 406 */           param1ArrayOfbyte2[i2++] = (byte)arrayOfChar[i3 >>> 12 & 0x3F];
/* 407 */           param1ArrayOfbyte2[i2++] = (byte)arrayOfChar[i3 >>> 6 & 0x3F];
/* 408 */           param1ArrayOfbyte2[i2++] = (byte)arrayOfChar[i3 & 0x3F];
/*     */         } 
/* 410 */         i1 = (n - i) / 3 * 4;
/* 411 */         m += i1;
/* 412 */         i = n;
/* 413 */         if (i1 == this.linemax && i < param1Int2) {
/* 414 */           for (byte b : this.newline) {
/* 415 */             param1ArrayOfbyte2[m++] = b;
/*     */           }
/*     */         }
/*     */       } 
/* 419 */       if (i < param1Int2) {
/* 420 */         int n = param1ArrayOfbyte1[i++] & 0xFF;
/* 421 */         param1ArrayOfbyte2[m++] = (byte)arrayOfChar[n >> 2];
/* 422 */         if (i == param1Int2) {
/* 423 */           param1ArrayOfbyte2[m++] = (byte)arrayOfChar[n << 4 & 0x3F];
/* 424 */           if (this.doPadding) {
/* 425 */             param1ArrayOfbyte2[m++] = 61;
/* 426 */             param1ArrayOfbyte2[m++] = 61;
/*     */           } 
/*     */         } else {
/* 429 */           int i1 = param1ArrayOfbyte1[i++] & 0xFF;
/* 430 */           param1ArrayOfbyte2[m++] = (byte)arrayOfChar[n << 4 & 0x3F | i1 >> 4];
/* 431 */           param1ArrayOfbyte2[m++] = (byte)arrayOfChar[i1 << 2 & 0x3F];
/* 432 */           if (this.doPadding) {
/* 433 */             param1ArrayOfbyte2[m++] = 61;
/*     */           }
/*     */         } 
/*     */       } 
/* 437 */       return m;
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
/*     */   public static class Decoder
/*     */   {
/*     */     private final boolean isURL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean isMIME;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Decoder(boolean param1Boolean1, boolean param1Boolean2) {
/* 473 */       this.isURL = param1Boolean1;
/* 474 */       this.isMIME = param1Boolean2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 485 */     private static final int[] fromBase64 = new int[256];
/*     */     static {
/* 487 */       Arrays.fill(fromBase64, -1); byte b;
/* 488 */       for (b = 0; b < Base64.Encoder.toBase64.length; b++)
/* 489 */         fromBase64[Base64.Encoder.toBase64[b]] = b; 
/* 490 */       fromBase64[61] = -2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 497 */     private static final int[] fromBase64URL = new int[256];
/*     */     
/*     */     static {
/* 500 */       Arrays.fill(fromBase64URL, -1);
/* 501 */       for (b = 0; b < Base64.Encoder.toBase64URL.length; b++)
/* 502 */         fromBase64URL[Base64.Encoder.toBase64URL[b]] = b; 
/* 503 */       fromBase64URL[61] = -2;
/*     */     }
/*     */     
/* 506 */     static final Decoder RFC4648 = new Decoder(false, false);
/* 507 */     static final Decoder RFC4648_URLSAFE = new Decoder(true, false);
/* 508 */     static final Decoder RFC2045 = new Decoder(false, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] decode(byte[] param1ArrayOfbyte) {
/* 525 */       byte[] arrayOfByte = new byte[outLength(param1ArrayOfbyte, 0, param1ArrayOfbyte.length)];
/* 526 */       int i = decode0(param1ArrayOfbyte, 0, param1ArrayOfbyte.length, arrayOfByte);
/* 527 */       if (i != arrayOfByte.length) {
/* 528 */         arrayOfByte = Arrays.copyOf(arrayOfByte, i);
/*     */       }
/* 530 */       return arrayOfByte;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] decode(String param1String) {
/* 549 */       return decode(param1String.getBytes(StandardCharsets.ISO_8859_1));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int decode(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2) {
/* 578 */       int i = outLength(param1ArrayOfbyte1, 0, param1ArrayOfbyte1.length);
/* 579 */       if (param1ArrayOfbyte2.length < i) {
/* 580 */         throw new IllegalArgumentException("Output byte array is too small for decoding all input bytes");
/*     */       }
/* 582 */       return decode0(param1ArrayOfbyte1, 0, param1ArrayOfbyte1.length, param1ArrayOfbyte2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ByteBuffer decode(ByteBuffer param1ByteBuffer) {
/* 607 */       int i = param1ByteBuffer.position(); try {
/*     */         byte[] arrayOfByte1;
/*     */         boolean bool;
/*     */         int j;
/* 611 */         if (param1ByteBuffer.hasArray()) {
/* 612 */           arrayOfByte1 = param1ByteBuffer.array();
/* 613 */           bool = param1ByteBuffer.arrayOffset() + param1ByteBuffer.position();
/* 614 */           j = param1ByteBuffer.arrayOffset() + param1ByteBuffer.limit();
/* 615 */           param1ByteBuffer.position(param1ByteBuffer.limit());
/*     */         } else {
/* 617 */           arrayOfByte1 = new byte[param1ByteBuffer.remaining()];
/* 618 */           param1ByteBuffer.get(arrayOfByte1);
/* 619 */           bool = false;
/* 620 */           j = arrayOfByte1.length;
/*     */         } 
/* 622 */         byte[] arrayOfByte2 = new byte[outLength(arrayOfByte1, bool, j)];
/* 623 */         return ByteBuffer.wrap(arrayOfByte2, 0, decode0(arrayOfByte1, bool, j, arrayOfByte2));
/* 624 */       } catch (IllegalArgumentException illegalArgumentException) {
/* 625 */         param1ByteBuffer.position(i);
/* 626 */         throw illegalArgumentException;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream wrap(InputStream param1InputStream) {
/* 646 */       Objects.requireNonNull(param1InputStream);
/* 647 */       return new Base64.DecInputStream(param1InputStream, this.isURL ? fromBase64URL : fromBase64, this.isMIME);
/*     */     }
/*     */     
/*     */     private int outLength(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/* 651 */       int[] arrayOfInt = this.isURL ? fromBase64URL : fromBase64;
/* 652 */       int i = 0;
/* 653 */       int j = param1Int2 - param1Int1;
/* 654 */       if (j == 0)
/* 655 */         return 0; 
/* 656 */       if (j < 2) {
/* 657 */         if (this.isMIME && arrayOfInt[0] == -1)
/* 658 */           return 0; 
/* 659 */         throw new IllegalArgumentException("Input byte[] should at least have 2 bytes for base64 bytes");
/*     */       } 
/*     */       
/* 662 */       if (this.isMIME) {
/*     */ 
/*     */         
/* 665 */         byte b = 0;
/* 666 */         while (param1Int1 < param1Int2) {
/* 667 */           int k = param1ArrayOfbyte[param1Int1++] & 0xFF;
/* 668 */           if (k == 61) {
/* 669 */             j -= param1Int2 - param1Int1 + 1;
/*     */             break;
/*     */           } 
/* 672 */           if ((k = arrayOfInt[k]) == -1)
/* 673 */             b++; 
/*     */         } 
/* 675 */         j -= b;
/*     */       }
/* 677 */       else if (param1ArrayOfbyte[param1Int2 - 1] == 61) {
/* 678 */         i++;
/* 679 */         if (param1ArrayOfbyte[param1Int2 - 2] == 61) {
/* 680 */           i++;
/*     */         }
/*     */       } 
/* 683 */       if (i == 0 && (j & 0x3) != 0)
/* 684 */         i = 4 - (j & 0x3); 
/* 685 */       return 3 * (j + 3) / 4 - i;
/*     */     }
/*     */     
/*     */     private int decode0(byte[] param1ArrayOfbyte1, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte2) {
/* 689 */       int[] arrayOfInt = this.isURL ? fromBase64URL : fromBase64;
/* 690 */       byte b1 = 0;
/* 691 */       int i = 0;
/* 692 */       byte b2 = 18;
/* 693 */       while (param1Int1 < param1Int2) {
/* 694 */         int j = param1ArrayOfbyte1[param1Int1++] & 0xFF;
/* 695 */         if ((j = arrayOfInt[j]) < 0) {
/* 696 */           if (j == -2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 702 */             if ((b2 == 6 && (param1Int1 == param1Int2 || param1ArrayOfbyte1[param1Int1++] != 61)) || b2 == 18)
/*     */             {
/* 704 */               throw new IllegalArgumentException("Input byte array has wrong 4-byte ending unit");
/*     */             }
/*     */             
/*     */             break;
/*     */           } 
/* 709 */           if (this.isMIME) {
/*     */             continue;
/*     */           }
/* 712 */           throw new IllegalArgumentException("Illegal base64 character " + 
/*     */               
/* 714 */               Integer.toString(param1ArrayOfbyte1[param1Int1 - 1], 16));
/*     */         } 
/* 716 */         i |= j << b2;
/* 717 */         b2 -= 6;
/* 718 */         if (b2 < 0) {
/* 719 */           param1ArrayOfbyte2[b1++] = (byte)(i >> 16);
/* 720 */           param1ArrayOfbyte2[b1++] = (byte)(i >> 8);
/* 721 */           param1ArrayOfbyte2[b1++] = (byte)i;
/* 722 */           b2 = 18;
/* 723 */           i = 0;
/*     */         } 
/*     */       } 
/*     */       
/* 727 */       if (b2 == 6) {
/* 728 */         param1ArrayOfbyte2[b1++] = (byte)(i >> 16);
/* 729 */       } else if (b2 == 0) {
/* 730 */         param1ArrayOfbyte2[b1++] = (byte)(i >> 16);
/* 731 */         param1ArrayOfbyte2[b1++] = (byte)(i >> 8);
/* 732 */       } else if (b2 == 12) {
/*     */         
/* 734 */         throw new IllegalArgumentException("Last unit does not have enough valid bits");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 739 */       while (param1Int1 < param1Int2) {
/* 740 */         if (this.isMIME && arrayOfInt[param1ArrayOfbyte1[param1Int1++]] < 0)
/*     */           continue; 
/* 742 */         throw new IllegalArgumentException("Input byte array has incorrect ending byte at " + param1Int1);
/*     */       } 
/*     */       
/* 745 */       return b1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class EncOutputStream
/*     */     extends FilterOutputStream
/*     */   {
/* 754 */     private int leftover = 0; private int b0;
/*     */     private int b1;
/*     */     private int b2;
/*     */     private boolean closed = false;
/*     */     private final char[] base64;
/*     */     private final byte[] newline;
/*     */     private final int linemax;
/*     */     private final boolean doPadding;
/* 762 */     private int linepos = 0;
/*     */ 
/*     */     
/*     */     EncOutputStream(OutputStream param1OutputStream, char[] param1ArrayOfchar, byte[] param1ArrayOfbyte, int param1Int, boolean param1Boolean) {
/* 766 */       super(param1OutputStream);
/* 767 */       this.base64 = param1ArrayOfchar;
/* 768 */       this.newline = param1ArrayOfbyte;
/* 769 */       this.linemax = param1Int;
/* 770 */       this.doPadding = param1Boolean;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(int param1Int) throws IOException {
/* 775 */       byte[] arrayOfByte = new byte[1];
/* 776 */       arrayOfByte[0] = (byte)(param1Int & 0xFF);
/* 777 */       write(arrayOfByte, 0, 1);
/*     */     }
/*     */     
/*     */     private void checkNewline() throws IOException {
/* 781 */       if (this.linepos == this.linemax) {
/* 782 */         this.out.write(this.newline);
/* 783 */         this.linepos = 0;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 789 */       if (this.closed)
/* 790 */         throw new IOException("Stream is closed"); 
/* 791 */       if (param1Int1 < 0 || param1Int2 < 0 || param1Int2 > param1ArrayOfbyte.length - param1Int1)
/* 792 */         throw new ArrayIndexOutOfBoundsException(); 
/* 793 */       if (param1Int2 == 0)
/*     */         return; 
/* 795 */       if (this.leftover != 0) {
/* 796 */         if (this.leftover == 1) {
/* 797 */           this.b1 = param1ArrayOfbyte[param1Int1++] & 0xFF;
/* 798 */           param1Int2--;
/* 799 */           if (param1Int2 == 0) {
/* 800 */             this.leftover++;
/*     */             return;
/*     */           } 
/*     */         } 
/* 804 */         this.b2 = param1ArrayOfbyte[param1Int1++] & 0xFF;
/* 805 */         param1Int2--;
/* 806 */         checkNewline();
/* 807 */         this.out.write(this.base64[this.b0 >> 2]);
/* 808 */         this.out.write(this.base64[this.b0 << 4 & 0x3F | this.b1 >> 4]);
/* 809 */         this.out.write(this.base64[this.b1 << 2 & 0x3F | this.b2 >> 6]);
/* 810 */         this.out.write(this.base64[this.b2 & 0x3F]);
/* 811 */         this.linepos += 4;
/*     */       } 
/* 813 */       int i = param1Int2 / 3;
/* 814 */       this.leftover = param1Int2 - i * 3;
/* 815 */       while (i-- > 0) {
/* 816 */         checkNewline();
/* 817 */         int j = (param1ArrayOfbyte[param1Int1++] & 0xFF) << 16 | (param1ArrayOfbyte[param1Int1++] & 0xFF) << 8 | param1ArrayOfbyte[param1Int1++] & 0xFF;
/*     */ 
/*     */         
/* 820 */         this.out.write(this.base64[j >>> 18 & 0x3F]);
/* 821 */         this.out.write(this.base64[j >>> 12 & 0x3F]);
/* 822 */         this.out.write(this.base64[j >>> 6 & 0x3F]);
/* 823 */         this.out.write(this.base64[j & 0x3F]);
/* 824 */         this.linepos += 4;
/*     */       } 
/* 826 */       if (this.leftover == 1) {
/* 827 */         this.b0 = param1ArrayOfbyte[param1Int1++] & 0xFF;
/* 828 */       } else if (this.leftover == 2) {
/* 829 */         this.b0 = param1ArrayOfbyte[param1Int1++] & 0xFF;
/* 830 */         this.b1 = param1ArrayOfbyte[param1Int1++] & 0xFF;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 836 */       if (!this.closed) {
/* 837 */         this.closed = true;
/* 838 */         if (this.leftover == 1) {
/* 839 */           checkNewline();
/* 840 */           this.out.write(this.base64[this.b0 >> 2]);
/* 841 */           this.out.write(this.base64[this.b0 << 4 & 0x3F]);
/* 842 */           if (this.doPadding) {
/* 843 */             this.out.write(61);
/* 844 */             this.out.write(61);
/*     */           } 
/* 846 */         } else if (this.leftover == 2) {
/* 847 */           checkNewline();
/* 848 */           this.out.write(this.base64[this.b0 >> 2]);
/* 849 */           this.out.write(this.base64[this.b0 << 4 & 0x3F | this.b1 >> 4]);
/* 850 */           this.out.write(this.base64[this.b1 << 2 & 0x3F]);
/* 851 */           if (this.doPadding) {
/* 852 */             this.out.write(61);
/*     */           }
/*     */         } 
/* 855 */         this.leftover = 0;
/* 856 */         this.out.close();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class DecInputStream
/*     */     extends InputStream
/*     */   {
/*     */     private final InputStream is;
/*     */     
/*     */     private final boolean isMIME;
/*     */     private final int[] base64;
/* 869 */     private int bits = 0;
/* 870 */     private int nextin = 18;
/*     */     
/* 872 */     private int nextout = -8;
/*     */ 
/*     */     
/*     */     private boolean eof = false;
/*     */     
/*     */     private boolean closed = false;
/*     */     
/*     */     private byte[] sbBuf;
/*     */ 
/*     */     
/*     */     DecInputStream(InputStream param1InputStream, int[] param1ArrayOfint, boolean param1Boolean) {
/* 883 */       this.sbBuf = new byte[1];
/*     */       this.is = param1InputStream;
/*     */       this.base64 = param1ArrayOfint;
/*     */       this.isMIME = param1Boolean; } public int read() throws IOException {
/* 887 */       return (read(this.sbBuf, 0, 1) == -1) ? -1 : (this.sbBuf[0] & 0xFF);
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 892 */       if (this.closed)
/* 893 */         throw new IOException("Stream is closed"); 
/* 894 */       if (this.eof && this.nextout < 0)
/* 895 */         return -1; 
/* 896 */       if (param1Int1 < 0 || param1Int2 < 0 || param1Int2 > param1ArrayOfbyte.length - param1Int1)
/* 897 */         throw new IndexOutOfBoundsException(); 
/* 898 */       int i = param1Int1;
/* 899 */       if (this.nextout >= 0)
/*     */         while (true) {
/* 901 */           if (param1Int2 == 0)
/* 902 */             return param1Int1 - i; 
/* 903 */           param1ArrayOfbyte[param1Int1++] = (byte)(this.bits >> this.nextout);
/* 904 */           param1Int2--;
/* 905 */           this.nextout -= 8;
/* 906 */           if (this.nextout < 0) {
/* 907 */             this.bits = 0; break;
/*     */           } 
/* 909 */         }   while (param1Int2 > 0) {
/* 910 */         int j = this.is.read();
/* 911 */         if (j == -1) {
/* 912 */           this.eof = true;
/* 913 */           if (this.nextin != 18) {
/* 914 */             if (this.nextin == 12) {
/* 915 */               throw new IOException("Base64 stream has one un-decoded dangling byte.");
/*     */             }
/*     */             
/* 918 */             param1ArrayOfbyte[param1Int1++] = (byte)(this.bits >> 16);
/* 919 */             param1Int2--;
/* 920 */             if (this.nextin == 0) {
/* 921 */               if (param1Int2 == 0) {
/* 922 */                 this.bits >>= 8;
/* 923 */                 this.nextout = 0;
/*     */               } else {
/* 925 */                 param1ArrayOfbyte[param1Int1++] = (byte)(this.bits >> 8);
/*     */               } 
/*     */             }
/*     */           } 
/* 929 */           if (param1Int1 == i) {
/* 930 */             return -1;
/*     */           }
/* 932 */           return param1Int1 - i;
/*     */         } 
/* 934 */         if (j == 61) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 939 */           if (this.nextin == 18 || this.nextin == 12 || (this.nextin == 6 && this.is
/* 940 */             .read() != 61)) {
/* 941 */             throw new IOException("Illegal base64 ending sequence:" + this.nextin);
/*     */           }
/* 943 */           param1ArrayOfbyte[param1Int1++] = (byte)(this.bits >> 16);
/* 944 */           param1Int2--;
/* 945 */           if (this.nextin == 0) {
/* 946 */             if (param1Int2 == 0) {
/* 947 */               this.bits >>= 8;
/* 948 */               this.nextout = 0;
/*     */             } else {
/* 950 */               param1ArrayOfbyte[param1Int1++] = (byte)(this.bits >> 8);
/*     */             } 
/*     */           }
/* 953 */           this.eof = true;
/*     */           break;
/*     */         } 
/* 956 */         if ((j = this.base64[j]) == -1) {
/* 957 */           if (this.isMIME) {
/*     */             continue;
/*     */           }
/* 960 */           throw new IOException("Illegal base64 character " + 
/* 961 */               Integer.toString(j, 16));
/*     */         } 
/* 963 */         this.bits |= j << this.nextin;
/* 964 */         if (this.nextin == 0) {
/* 965 */           this.nextin = 18;
/* 966 */           this.nextout = 16;
/* 967 */           while (this.nextout >= 0) {
/* 968 */             param1ArrayOfbyte[param1Int1++] = (byte)(this.bits >> this.nextout);
/* 969 */             param1Int2--;
/* 970 */             this.nextout -= 8;
/* 971 */             if (param1Int2 == 0 && this.nextout >= 0) {
/* 972 */               return param1Int1 - i;
/*     */             }
/*     */           } 
/* 975 */           this.bits = 0; continue;
/*     */         } 
/* 977 */         this.nextin -= 6;
/*     */       } 
/*     */       
/* 980 */       return param1Int1 - i;
/*     */     }
/*     */ 
/*     */     
/*     */     public int available() throws IOException {
/* 985 */       if (this.closed)
/* 986 */         throw new IOException("Stream is closed"); 
/* 987 */       return this.is.available();
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 992 */       if (!this.closed) {
/* 993 */         this.closed = true;
/* 994 */         this.is.close();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Base64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */