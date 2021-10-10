/*     */ package com.sun.org.apache.xml.internal.security.utils;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigInteger;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static final int BASE64DEFAULTLENGTH = 76;
/*     */   private static final int BASELENGTH = 255;
/*     */   private static final int LOOKUPLENGTH = 64;
/*     */   private static final int TWENTYFOURBITGROUP = 24;
/*     */   private static final int EIGHTBIT = 8;
/*     */   private static final int SIXTEENBIT = 16;
/*     */   private static final int FOURBYTE = 4;
/*     */   private static final int SIGN = -128;
/*     */   private static final char PAD = '=';
/*  61 */   private static final byte[] base64Alphabet = new byte[255];
/*  62 */   private static final char[] lookUpBase64Alphabet = new char[64];
/*     */   static {
/*     */     byte b1;
/*  65 */     for (b1 = 0; b1 < 'ÿ'; b1++) {
/*  66 */       base64Alphabet[b1] = -1;
/*     */     }
/*  68 */     for (b1 = 90; b1 >= 65; b1--) {
/*  69 */       base64Alphabet[b1] = (byte)(b1 - 65);
/*     */     }
/*  71 */     for (b1 = 122; b1 >= 97; b1--) {
/*  72 */       base64Alphabet[b1] = (byte)(b1 - 97 + 26);
/*     */     }
/*     */     
/*  75 */     for (b1 = 57; b1 >= 48; b1--) {
/*  76 */       base64Alphabet[b1] = (byte)(b1 - 48 + 52);
/*     */     }
/*     */     
/*  79 */     base64Alphabet[43] = 62;
/*  80 */     base64Alphabet[47] = 63;
/*     */     
/*  82 */     for (b1 = 0; b1 <= 25; b1++) {
/*  83 */       lookUpBase64Alphabet[b1] = (char)(65 + b1);
/*     */     }
/*     */     byte b2;
/*  86 */     for (b1 = 26, b2 = 0; b1 <= 51; b1++, b2++) {
/*  87 */       lookUpBase64Alphabet[b1] = (char)(97 + b2);
/*     */     }
/*     */     
/*  90 */     for (b1 = 52, b2 = 0; b1 <= 61; b1++, b2++) {
/*  91 */       lookUpBase64Alphabet[b1] = (char)(48 + b2);
/*     */     }
/*  93 */     lookUpBase64Alphabet[62] = '+';
/*  94 */     lookUpBase64Alphabet[63] = '/';
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
/*     */   static final byte[] getBytes(BigInteger paramBigInteger, int paramInt) {
/* 115 */     paramInt = paramInt + 7 >> 3 << 3;
/*     */     
/* 117 */     if (paramInt < paramBigInteger.bitLength()) {
/* 118 */       throw new IllegalArgumentException(I18n.translate("utils.Base64.IllegalBitlength"));
/*     */     }
/*     */     
/* 121 */     byte[] arrayOfByte1 = paramBigInteger.toByteArray();
/*     */     
/* 123 */     if (paramBigInteger.bitLength() % 8 != 0 && paramBigInteger
/* 124 */       .bitLength() / 8 + 1 == paramInt / 8) {
/* 125 */       return arrayOfByte1;
/*     */     }
/*     */ 
/*     */     
/* 129 */     boolean bool = false;
/* 130 */     int i = arrayOfByte1.length;
/*     */     
/* 132 */     if (paramBigInteger.bitLength() % 8 == 0) {
/* 133 */       bool = true;
/*     */       
/* 135 */       i--;
/*     */     } 
/*     */     
/* 138 */     int j = paramInt / 8 - i;
/* 139 */     byte[] arrayOfByte2 = new byte[paramInt / 8];
/*     */     
/* 141 */     System.arraycopy(arrayOfByte1, bool, arrayOfByte2, j, i);
/*     */     
/* 143 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String encode(BigInteger paramBigInteger) {
/* 153 */     return encode(getBytes(paramBigInteger, paramBigInteger.bitLength()));
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
/*     */   public static final byte[] encode(BigInteger paramBigInteger, int paramInt) {
/* 170 */     paramInt = paramInt + 7 >> 3 << 3;
/*     */     
/* 172 */     if (paramInt < paramBigInteger.bitLength()) {
/* 173 */       throw new IllegalArgumentException(I18n.translate("utils.Base64.IllegalBitlength"));
/*     */     }
/*     */     
/* 176 */     byte[] arrayOfByte1 = paramBigInteger.toByteArray();
/*     */     
/* 178 */     if (paramBigInteger.bitLength() % 8 != 0 && paramBigInteger
/* 179 */       .bitLength() / 8 + 1 == paramInt / 8) {
/* 180 */       return arrayOfByte1;
/*     */     }
/*     */ 
/*     */     
/* 184 */     boolean bool = false;
/* 185 */     int i = arrayOfByte1.length;
/*     */     
/* 187 */     if (paramBigInteger.bitLength() % 8 == 0) {
/* 188 */       bool = true;
/*     */       
/* 190 */       i--;
/*     */     } 
/*     */     
/* 193 */     int j = paramInt / 8 - i;
/* 194 */     byte[] arrayOfByte2 = new byte[paramInt / 8];
/*     */     
/* 196 */     System.arraycopy(arrayOfByte1, bool, arrayOfByte2, j, i);
/*     */     
/* 198 */     return arrayOfByte2;
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
/*     */   public static final BigInteger decodeBigIntegerFromElement(Element paramElement) throws Base64DecodingException {
/* 210 */     return new BigInteger(1, decode(paramElement));
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
/*     */   public static final BigInteger decodeBigIntegerFromText(Text paramText) throws Base64DecodingException {
/* 222 */     return new BigInteger(1, decode(paramText.getData()));
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
/*     */   public static final void fillElementWithBigInteger(Element paramElement, BigInteger paramBigInteger) {
/* 234 */     String str = encode(paramBigInteger);
/*     */     
/* 236 */     if (!XMLUtils.ignoreLineBreaks() && str.length() > 76) {
/* 237 */       str = "\n" + str + "\n";
/*     */     }
/*     */     
/* 240 */     Document document = paramElement.getOwnerDocument();
/* 241 */     Text text = document.createTextNode(str);
/*     */     
/* 243 */     paramElement.appendChild(text);
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
/*     */   public static final byte[] decode(Element paramElement) throws Base64DecodingException {
/* 259 */     Node node = paramElement.getFirstChild();
/* 260 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 262 */     while (node != null) {
/* 263 */       if (node.getNodeType() == 3) {
/* 264 */         Text text = (Text)node;
/*     */         
/* 266 */         stringBuffer.append(text.getData());
/*     */       } 
/* 268 */       node = node.getNextSibling();
/*     */     } 
/*     */     
/* 271 */     return decode(stringBuffer.toString());
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
/*     */   public static final Element encodeToElement(Document paramDocument, String paramString, byte[] paramArrayOfbyte) {
/* 284 */     Element element = XMLUtils.createElementInSignatureSpace(paramDocument, paramString);
/* 285 */     Text text = paramDocument.createTextNode(encode(paramArrayOfbyte));
/*     */     
/* 287 */     element.appendChild(text);
/*     */     
/* 289 */     return element;
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
/*     */   public static final byte[] decode(byte[] paramArrayOfbyte) throws Base64DecodingException {
/* 301 */     return decodeInternal(paramArrayOfbyte, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String encode(byte[] paramArrayOfbyte) {
/* 312 */     return XMLUtils.ignoreLineBreaks() ? 
/* 313 */       encode(paramArrayOfbyte, 2147483647) : 
/* 314 */       encode(paramArrayOfbyte, 76);
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
/*     */   public static final byte[] decode(BufferedReader paramBufferedReader) throws IOException, Base64DecodingException {
/* 330 */     byte[] arrayOfByte = null;
/* 331 */     UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = null;
/*     */     try {
/* 333 */       unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
/*     */       
/*     */       String str;
/* 336 */       while (null != (str = paramBufferedReader.readLine())) {
/* 337 */         byte[] arrayOfByte1 = decode(str);
/* 338 */         unsyncByteArrayOutputStream.write(arrayOfByte1);
/*     */       } 
/* 340 */       arrayOfByte = unsyncByteArrayOutputStream.toByteArray();
/*     */     } finally {
/* 342 */       unsyncByteArrayOutputStream.close();
/*     */     } 
/*     */     
/* 345 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   protected static final boolean isWhiteSpace(byte paramByte) {
/* 349 */     return (paramByte == 32 || paramByte == 13 || paramByte == 10 || paramByte == 9);
/*     */   }
/*     */   
/*     */   protected static final boolean isPad(byte paramByte) {
/* 353 */     return (paramByte == 61);
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
/*     */   public static final String encode(byte[] paramArrayOfbyte, int paramInt) {
/* 371 */     if (paramInt < 4) {
/* 372 */       paramInt = Integer.MAX_VALUE;
/*     */     }
/*     */     
/* 375 */     if (paramArrayOfbyte == null) {
/* 376 */       return null;
/*     */     }
/*     */     
/* 379 */     int i = paramArrayOfbyte.length * 8;
/* 380 */     if (i == 0) {
/* 381 */       return "";
/*     */     }
/*     */     
/* 384 */     int j = i % 24;
/* 385 */     int k = i / 24;
/* 386 */     int m = (j != 0) ? (k + 1) : k;
/* 387 */     int n = paramInt / 4;
/* 388 */     int i1 = (m - 1) / n;
/* 389 */     char[] arrayOfChar = null;
/*     */     
/* 391 */     arrayOfChar = new char[m * 4 + i1];
/*     */     
/* 393 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0, b5 = 0;
/* 394 */     byte b6 = 0;
/* 395 */     byte b7 = 0;
/* 396 */     byte b8 = 0;
/*     */     byte b9;
/* 398 */     for (b9 = 0; b9 < i1; b9++) {
/* 399 */       for (byte b = 0; b < 19; b++) {
/* 400 */         b3 = paramArrayOfbyte[b7++];
/* 401 */         b4 = paramArrayOfbyte[b7++];
/* 402 */         b5 = paramArrayOfbyte[b7++];
/*     */         
/* 404 */         b2 = (byte)(b4 & 0xF);
/* 405 */         b1 = (byte)(b3 & 0x3);
/*     */         
/* 407 */         byte b10 = ((b3 & 0xFFFFFF80) == 0) ? (byte)(b3 >> 2) : (byte)(b3 >> 2 ^ 0xC0);
/*     */         
/* 409 */         byte b11 = ((b4 & 0xFFFFFF80) == 0) ? (byte)(b4 >> 4) : (byte)(b4 >> 4 ^ 0xF0);
/* 410 */         byte b12 = ((b5 & 0xFFFFFF80) == 0) ? (byte)(b5 >> 6) : (byte)(b5 >> 6 ^ 0xFC);
/*     */ 
/*     */         
/* 413 */         arrayOfChar[b6++] = lookUpBase64Alphabet[b10];
/* 414 */         arrayOfChar[b6++] = lookUpBase64Alphabet[b11 | b1 << 4];
/* 415 */         arrayOfChar[b6++] = lookUpBase64Alphabet[b2 << 2 | b12];
/* 416 */         arrayOfChar[b6++] = lookUpBase64Alphabet[b5 & 0x3F];
/*     */         
/* 418 */         b8++;
/*     */       } 
/* 420 */       arrayOfChar[b6++] = '\n';
/*     */     } 
/*     */     
/* 423 */     for (; b8 < k; b8++) {
/* 424 */       b3 = paramArrayOfbyte[b7++];
/* 425 */       b4 = paramArrayOfbyte[b7++];
/* 426 */       b5 = paramArrayOfbyte[b7++];
/*     */       
/* 428 */       b2 = (byte)(b4 & 0xF);
/* 429 */       b1 = (byte)(b3 & 0x3);
/*     */       
/* 431 */       b9 = ((b3 & 0xFFFFFF80) == 0) ? (byte)(b3 >> 2) : (byte)(b3 >> 2 ^ 0xC0);
/*     */       
/* 433 */       byte b10 = ((b4 & 0xFFFFFF80) == 0) ? (byte)(b4 >> 4) : (byte)(b4 >> 4 ^ 0xF0);
/* 434 */       byte b11 = ((b5 & 0xFFFFFF80) == 0) ? (byte)(b5 >> 6) : (byte)(b5 >> 6 ^ 0xFC);
/*     */ 
/*     */       
/* 437 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b9];
/* 438 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b10 | b1 << 4];
/* 439 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b2 << 2 | b11];
/* 440 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b5 & 0x3F];
/*     */     } 
/*     */ 
/*     */     
/* 444 */     if (j == 8) {
/* 445 */       b3 = paramArrayOfbyte[b7];
/* 446 */       b1 = (byte)(b3 & 0x3);
/* 447 */       b9 = ((b3 & 0xFFFFFF80) == 0) ? (byte)(b3 >> 2) : (byte)(b3 >> 2 ^ 0xC0);
/* 448 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b9];
/* 449 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b1 << 4];
/* 450 */       arrayOfChar[b6++] = '=';
/* 451 */       arrayOfChar[b6++] = '=';
/* 452 */     } else if (j == 16) {
/* 453 */       b3 = paramArrayOfbyte[b7];
/* 454 */       b4 = paramArrayOfbyte[b7 + 1];
/* 455 */       b2 = (byte)(b4 & 0xF);
/* 456 */       b1 = (byte)(b3 & 0x3);
/*     */       
/* 458 */       b9 = ((b3 & 0xFFFFFF80) == 0) ? (byte)(b3 >> 2) : (byte)(b3 >> 2 ^ 0xC0);
/* 459 */       byte b = ((b4 & 0xFFFFFF80) == 0) ? (byte)(b4 >> 4) : (byte)(b4 >> 4 ^ 0xF0);
/*     */       
/* 461 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b9];
/* 462 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b | b1 << 4];
/* 463 */       arrayOfChar[b6++] = lookUpBase64Alphabet[b2 << 2];
/* 464 */       arrayOfChar[b6++] = '=';
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 469 */     return new String(arrayOfChar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final byte[] decode(String paramString) throws Base64DecodingException {
/* 480 */     if (paramString == null) {
/* 481 */       return null;
/*     */     }
/* 483 */     byte[] arrayOfByte = new byte[paramString.length()];
/* 484 */     int i = getBytesInternal(paramString, arrayOfByte);
/* 485 */     return decodeInternal(arrayOfByte, i);
/*     */   }
/*     */   
/*     */   protected static final int getBytesInternal(String paramString, byte[] paramArrayOfbyte) {
/* 489 */     int i = paramString.length();
/*     */     
/* 491 */     byte b1 = 0;
/* 492 */     for (byte b2 = 0; b2 < i; b2++) {
/* 493 */       byte b = (byte)paramString.charAt(b2);
/* 494 */       if (!isWhiteSpace(b)) {
/* 495 */         paramArrayOfbyte[b1++] = b;
/*     */       }
/*     */     } 
/* 498 */     return b1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final byte[] decodeInternal(byte[] paramArrayOfbyte, int paramInt) throws Base64DecodingException {
/* 504 */     if (paramInt == -1) {
/* 505 */       paramInt = removeWhiteSpace(paramArrayOfbyte);
/*     */     }
/*     */     
/* 508 */     if (paramInt % 4 != 0) {
/* 509 */       throw new Base64DecodingException("decoding.divisible.four");
/*     */     }
/*     */ 
/*     */     
/* 513 */     int i = paramInt / 4;
/*     */     
/* 515 */     if (i == 0) {
/* 516 */       return new byte[0];
/*     */     }
/*     */     
/* 519 */     byte[] arrayOfByte = null;
/* 520 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
/*     */     
/* 522 */     int j = 0;
/* 523 */     int k = 0;
/* 524 */     int m = 0;
/*     */ 
/*     */     
/* 527 */     m = (i - 1) * 4;
/* 528 */     k = (i - 1) * 3;
/*     */     
/* 530 */     b1 = base64Alphabet[paramArrayOfbyte[m++]];
/* 531 */     b2 = base64Alphabet[paramArrayOfbyte[m++]];
/* 532 */     if (b1 == -1 || b2 == -1)
/*     */     {
/* 534 */       throw new Base64DecodingException("decoding.general");
/*     */     }
/*     */     
/*     */     byte b5;
/*     */     
/* 539 */     b3 = base64Alphabet[b5 = paramArrayOfbyte[m++]]; byte b6;
/* 540 */     b4 = base64Alphabet[b6 = paramArrayOfbyte[m++]];
/* 541 */     if (b3 == -1 || b4 == -1) {
/*     */       
/* 543 */       if (isPad(b5) && isPad(b6)) {
/* 544 */         if ((b2 & 0xF) != 0) {
/* 545 */           throw new Base64DecodingException("decoding.general");
/*     */         }
/* 547 */         arrayOfByte = new byte[k + 1];
/* 548 */         arrayOfByte[k] = (byte)(b1 << 2 | b2 >> 4);
/* 549 */       } else if (!isPad(b5) && isPad(b6)) {
/* 550 */         if ((b3 & 0x3) != 0) {
/* 551 */           throw new Base64DecodingException("decoding.general");
/*     */         }
/* 553 */         arrayOfByte = new byte[k + 2];
/* 554 */         arrayOfByte[k++] = (byte)(b1 << 2 | b2 >> 4);
/* 555 */         arrayOfByte[k] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/*     */       } else {
/*     */         
/* 558 */         throw new Base64DecodingException("decoding.general");
/*     */       } 
/*     */     } else {
/*     */       
/* 562 */       arrayOfByte = new byte[k + 3];
/* 563 */       arrayOfByte[k++] = (byte)(b1 << 2 | b2 >> 4);
/* 564 */       arrayOfByte[k++] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/* 565 */       arrayOfByte[k++] = (byte)(b3 << 6 | b4);
/*     */     } 
/* 567 */     k = 0;
/* 568 */     m = 0;
/*     */     
/* 570 */     for (j = i - 1; j > 0; j--) {
/* 571 */       b1 = base64Alphabet[paramArrayOfbyte[m++]];
/* 572 */       b2 = base64Alphabet[paramArrayOfbyte[m++]];
/* 573 */       b3 = base64Alphabet[paramArrayOfbyte[m++]];
/* 574 */       b4 = base64Alphabet[paramArrayOfbyte[m++]];
/*     */       
/* 576 */       if (b1 == -1 || b2 == -1 || b3 == -1 || b4 == -1)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 581 */         throw new Base64DecodingException("decoding.general");
/*     */       }
/*     */       
/* 584 */       arrayOfByte[k++] = (byte)(b1 << 2 | b2 >> 4);
/* 585 */       arrayOfByte[k++] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/* 586 */       arrayOfByte[k++] = (byte)(b3 << 6 | b4);
/*     */     } 
/* 588 */     return arrayOfByte;
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
/*     */   public static final void decode(String paramString, OutputStream paramOutputStream) throws Base64DecodingException, IOException {
/* 601 */     byte[] arrayOfByte = new byte[paramString.length()];
/* 602 */     int i = getBytesInternal(paramString, arrayOfByte);
/* 603 */     decode(arrayOfByte, paramOutputStream, i);
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
/*     */   public static final void decode(byte[] paramArrayOfbyte, OutputStream paramOutputStream) throws Base64DecodingException, IOException {
/* 616 */     decode(paramArrayOfbyte, paramOutputStream, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final void decode(byte[] paramArrayOfbyte, OutputStream paramOutputStream, int paramInt) throws Base64DecodingException, IOException {
/* 622 */     if (paramInt == -1) {
/* 623 */       paramInt = removeWhiteSpace(paramArrayOfbyte);
/*     */     }
/*     */     
/* 626 */     if (paramInt % 4 != 0) {
/* 627 */       throw new Base64DecodingException("decoding.divisible.four");
/*     */     }
/*     */ 
/*     */     
/* 631 */     int i = paramInt / 4;
/*     */     
/* 633 */     if (i == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 638 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
/*     */     
/* 640 */     int j = 0;
/* 641 */     byte b = 0;
/*     */ 
/*     */     
/* 644 */     for (j = i - 1; j > 0; j--) {
/* 645 */       b1 = base64Alphabet[paramArrayOfbyte[b++]];
/* 646 */       b2 = base64Alphabet[paramArrayOfbyte[b++]];
/* 647 */       b3 = base64Alphabet[paramArrayOfbyte[b++]];
/* 648 */       b4 = base64Alphabet[paramArrayOfbyte[b++]];
/* 649 */       if (b1 == -1 || b2 == -1 || b3 == -1 || b4 == -1)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 654 */         throw new Base64DecodingException("decoding.general");
/*     */       }
/*     */       
/* 657 */       paramOutputStream.write((byte)(b1 << 2 | b2 >> 4));
/* 658 */       paramOutputStream.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
/* 659 */       paramOutputStream.write((byte)(b3 << 6 | b4));
/*     */     } 
/* 661 */     b1 = base64Alphabet[paramArrayOfbyte[b++]];
/* 662 */     b2 = base64Alphabet[paramArrayOfbyte[b++]];
/*     */ 
/*     */     
/* 665 */     if (b1 == -1 || b2 == -1)
/*     */     {
/* 667 */       throw new Base64DecodingException("decoding.general");
/*     */     }
/*     */     
/*     */     byte b5;
/* 671 */     b3 = base64Alphabet[b5 = paramArrayOfbyte[b++]]; byte b6;
/* 672 */     b4 = base64Alphabet[b6 = paramArrayOfbyte[b++]];
/* 673 */     if (b3 == -1 || b4 == -1) {
/* 674 */       if (isPad(b5) && isPad(b6)) {
/* 675 */         if ((b2 & 0xF) != 0) {
/* 676 */           throw new Base64DecodingException("decoding.general");
/*     */         }
/* 678 */         paramOutputStream.write((byte)(b1 << 2 | b2 >> 4));
/* 679 */       } else if (!isPad(b5) && isPad(b6)) {
/* 680 */         if ((b3 & 0x3) != 0) {
/* 681 */           throw new Base64DecodingException("decoding.general");
/*     */         }
/* 683 */         paramOutputStream.write((byte)(b1 << 2 | b2 >> 4));
/* 684 */         paramOutputStream.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
/*     */       } else {
/*     */         
/* 687 */         throw new Base64DecodingException("decoding.general");
/*     */       } 
/*     */     } else {
/*     */       
/* 691 */       paramOutputStream.write((byte)(b1 << 2 | b2 >> 4));
/* 692 */       paramOutputStream.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
/* 693 */       paramOutputStream.write((byte)(b3 << 6 | b4));
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
/*     */   
/*     */   public static final void decode(InputStream paramInputStream, OutputStream paramOutputStream) throws Base64DecodingException, IOException {
/* 708 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
/*     */     
/* 710 */     byte b = 0;
/* 711 */     byte[] arrayOfByte = new byte[4];
/*     */     
/*     */     int i;
/* 714 */     while ((i = paramInputStream.read()) > 0) {
/* 715 */       byte b9 = (byte)i;
/* 716 */       if (isWhiteSpace(b9)) {
/*     */         continue;
/*     */       }
/* 719 */       if (isPad(b9)) {
/* 720 */         arrayOfByte[b++] = b9;
/* 721 */         if (b == 3) {
/* 722 */           arrayOfByte[b++] = (byte)paramInputStream.read();
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/* 727 */       arrayOfByte[b++] = b9; if (b9 == -1)
/*     */       {
/* 729 */         throw new Base64DecodingException("decoding.general");
/*     */       }
/*     */       
/* 732 */       if (b != 4) {
/*     */         continue;
/*     */       }
/* 735 */       b = 0;
/* 736 */       b1 = base64Alphabet[arrayOfByte[0]];
/* 737 */       b2 = base64Alphabet[arrayOfByte[1]];
/* 738 */       b3 = base64Alphabet[arrayOfByte[2]];
/* 739 */       b4 = base64Alphabet[arrayOfByte[3]];
/*     */       
/* 741 */       paramOutputStream.write((byte)(b1 << 2 | b2 >> 4));
/* 742 */       paramOutputStream.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
/* 743 */       paramOutputStream.write((byte)(b3 << 6 | b4));
/*     */     } 
/*     */     
/* 746 */     byte b5 = arrayOfByte[0], b6 = arrayOfByte[1], b7 = arrayOfByte[2], b8 = arrayOfByte[3];
/* 747 */     b1 = base64Alphabet[b5];
/* 748 */     b2 = base64Alphabet[b6];
/* 749 */     b3 = base64Alphabet[b7];
/* 750 */     b4 = base64Alphabet[b8];
/* 751 */     if (b3 == -1 || b4 == -1) {
/* 752 */       if (isPad(b7) && isPad(b8)) {
/* 753 */         if ((b2 & 0xF) != 0) {
/* 754 */           throw new Base64DecodingException("decoding.general");
/*     */         }
/* 756 */         paramOutputStream.write((byte)(b1 << 2 | b2 >> 4));
/* 757 */       } else if (!isPad(b7) && isPad(b8)) {
/* 758 */         b3 = base64Alphabet[b7];
/* 759 */         if ((b3 & 0x3) != 0) {
/* 760 */           throw new Base64DecodingException("decoding.general");
/*     */         }
/* 762 */         paramOutputStream.write((byte)(b1 << 2 | b2 >> 4));
/* 763 */         paramOutputStream.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
/*     */       } else {
/*     */         
/* 766 */         throw new Base64DecodingException("decoding.general");
/*     */       } 
/*     */     } else {
/*     */       
/* 770 */       paramOutputStream.write((byte)(b1 << 2 | b2 >> 4));
/* 771 */       paramOutputStream.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
/* 772 */       paramOutputStream.write((byte)(b3 << 6 | b4));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int removeWhiteSpace(byte[] paramArrayOfbyte) {
/* 783 */     if (paramArrayOfbyte == null) {
/* 784 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 788 */     byte b1 = 0;
/* 789 */     int i = paramArrayOfbyte.length;
/* 790 */     for (byte b2 = 0; b2 < i; b2++) {
/* 791 */       byte b = paramArrayOfbyte[b2];
/* 792 */       if (!isWhiteSpace(b)) {
/* 793 */         paramArrayOfbyte[b1++] = b;
/*     */       }
/*     */     } 
/* 796 */     return b1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/Base64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */