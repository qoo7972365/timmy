/*      */ package com.sun.security.sasl.digest;
/*      */ 
/*      */ import com.sun.security.sasl.util.AbstractSaslImpl;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.math.BigInteger;
/*      */ import java.security.InvalidAlgorithmParameterException;
/*      */ import java.security.InvalidKeyException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.spec.InvalidKeySpecException;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.crypto.IllegalBlockSizeException;
/*      */ import javax.crypto.Mac;
/*      */ import javax.crypto.NoSuchPaddingException;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.crypto.SecretKeyFactory;
/*      */ import javax.crypto.spec.DESKeySpec;
/*      */ import javax.crypto.spec.DESedeKeySpec;
/*      */ import javax.crypto.spec.IvParameterSpec;
/*      */ import javax.crypto.spec.SecretKeySpec;
/*      */ import javax.security.auth.callback.CallbackHandler;
/*      */ import javax.security.sasl.SaslException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ abstract class DigestMD5Base
/*      */   extends AbstractSaslImpl
/*      */ {
/*   76 */   private static final String DI_CLASS_NAME = DigestIntegrity.class.getName();
/*   77 */   private static final String DP_CLASS_NAME = DigestPrivacy.class.getName();
/*      */   
/*      */   protected static final int MAX_CHALLENGE_LENGTH = 2048;
/*      */   
/*      */   protected static final int MAX_RESPONSE_LENGTH = 4096;
/*      */   
/*      */   protected static final int DEFAULT_MAXBUF = 65536;
/*      */   
/*      */   protected static final int DES3 = 0;
/*      */   protected static final int RC4 = 1;
/*      */   protected static final int DES = 2;
/*      */   protected static final int RC4_56 = 3;
/*      */   protected static final int RC4_40 = 4;
/*   90 */   protected static final String[] CIPHER_TOKENS = new String[] { "3des", "rc4", "des", "rc4-56", "rc4-40" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   95 */   private static final String[] JCE_CIPHER_NAME = new String[] { "DESede/CBC/NoPadding", "RC4", "DES/CBC/NoPadding" };
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final byte DES_3_STRENGTH = 4;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final byte RC4_STRENGTH = 4;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final byte DES_STRENGTH = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final byte RC4_56_STRENGTH = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final byte RC4_40_STRENGTH = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final byte UNSET = 0;
/*      */ 
/*      */ 
/*      */   
/*  123 */   protected static final byte[] CIPHER_MASKS = new byte[] { 4, 4, 2, 2, 1 };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String SECURITY_LAYER_MARKER = ":00000000000000000000000000000000";
/*      */ 
/*      */ 
/*      */   
/*  132 */   protected static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*      */   
/*      */   protected int step;
/*      */   
/*      */   protected CallbackHandler cbh;
/*      */   
/*      */   protected SecurityCtx secCtx;
/*      */   
/*      */   protected byte[] H_A1;
/*      */   
/*      */   protected byte[] nonce;
/*      */   
/*      */   protected String negotiatedStrength;
/*      */   
/*      */   protected String negotiatedCipher;
/*      */   
/*      */   protected String negotiatedQop;
/*      */   
/*      */   protected String negotiatedRealm;
/*      */   
/*      */   protected boolean useUTF8 = false;
/*      */   
/*  154 */   protected String encoding = "8859_1";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String digestUri;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String authzid;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DigestMD5Base(Map<String, ?> paramMap, String paramString1, int paramInt, String paramString2, CallbackHandler paramCallbackHandler) throws SaslException {
/*  174 */     super(paramMap, paramString1);
/*      */     
/*  176 */     this.step = paramInt;
/*  177 */     this.digestUri = paramString2;
/*  178 */     this.cbh = paramCallbackHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMechanismName() {
/*  187 */     return "DIGEST-MD5";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/*  203 */     if (!this.completed) {
/*  204 */       throw new IllegalStateException("DIGEST-MD5 authentication not completed");
/*      */     }
/*      */ 
/*      */     
/*  208 */     if (this.secCtx == null) {
/*  209 */       throw new IllegalStateException("Neither integrity nor privacy was negotiated");
/*      */     }
/*      */ 
/*      */     
/*  213 */     return this.secCtx.unwrap(paramArrayOfbyte, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/*  229 */     if (!this.completed) {
/*  230 */       throw new IllegalStateException("DIGEST-MD5 authentication not completed");
/*      */     }
/*      */ 
/*      */     
/*  234 */     if (this.secCtx == null) {
/*  235 */       throw new IllegalStateException("Neither integrity nor privacy was negotiated");
/*      */     }
/*      */ 
/*      */     
/*  239 */     return this.secCtx.wrap(paramArrayOfbyte, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void dispose() throws SaslException {
/*  243 */     if (this.secCtx != null) {
/*  244 */       this.secCtx = null;
/*      */     }
/*      */   }
/*      */   
/*      */   public Object getNegotiatedProperty(String paramString) {
/*  249 */     if (this.completed) {
/*  250 */       if (paramString.equals("javax.security.sasl.strength"))
/*  251 */         return this.negotiatedStrength; 
/*  252 */       if (paramString.equals("javax.security.sasl.bound.server.name")) {
/*  253 */         return this.digestUri.substring(this.digestUri.indexOf('/') + 1);
/*      */       }
/*  255 */       return super.getNegotiatedProperty(paramString);
/*      */     } 
/*      */     
/*  258 */     throw new IllegalStateException("DIGEST-MD5 authentication not completed");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  275 */   private static final char[] pem_array = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int RAW_NONCE_SIZE = 30;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int ENCODED_NONCE_SIZE = 40;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final byte[] generateNonce() {
/*  296 */     Random random = new Random();
/*  297 */     byte[] arrayOfByte1 = new byte[30];
/*  298 */     random.nextBytes(arrayOfByte1);
/*      */     
/*  300 */     byte[] arrayOfByte2 = new byte[40];
/*      */ 
/*      */ 
/*      */     
/*  304 */     byte b1 = 0;
/*  305 */     for (byte b2 = 0; b2 < arrayOfByte1.length; b2 += 3) {
/*  306 */       byte b3 = arrayOfByte1[b2];
/*  307 */       byte b4 = arrayOfByte1[b2 + 1];
/*  308 */       byte b5 = arrayOfByte1[b2 + 2];
/*  309 */       arrayOfByte2[b1++] = (byte)pem_array[b3 >>> 2 & 0x3F];
/*  310 */       arrayOfByte2[b1++] = (byte)pem_array[(b3 << 4 & 0x30) + (b4 >>> 4 & 0xF)];
/*  311 */       arrayOfByte2[b1++] = (byte)pem_array[(b4 << 2 & 0x3C) + (b5 >>> 6 & 0x3)];
/*  312 */       arrayOfByte2[b1++] = (byte)pem_array[b5 & 0x3F];
/*      */     } 
/*      */     
/*  315 */     return arrayOfByte2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void writeQuotedStringValue(ByteArrayOutputStream paramByteArrayOutputStream, byte[] paramArrayOfbyte) {
/*  329 */     int i = paramArrayOfbyte.length;
/*      */     
/*  331 */     for (byte b = 0; b < i; b++) {
/*  332 */       byte b1 = paramArrayOfbyte[b];
/*  333 */       if (needEscape((char)b1)) {
/*  334 */         paramByteArrayOutputStream.write(92);
/*      */       }
/*  336 */       paramByteArrayOutputStream.write(b1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean needEscape(String paramString) {
/*  343 */     int i = paramString.length();
/*  344 */     for (byte b = 0; b < i; b++) {
/*  345 */       if (needEscape(paramString.charAt(b))) {
/*  346 */         return true;
/*      */       }
/*      */     } 
/*  349 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean needEscape(char paramChar) {
/*  354 */     return (paramChar == '"' || paramChar == '\\' || paramChar == '' || (paramChar >= '\000' && paramChar <= '\037' && paramChar != '\r' && paramChar != '\t' && paramChar != '\n'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String quotedStringValue(String paramString) {
/*  363 */     if (needEscape(paramString)) {
/*  364 */       int i = paramString.length();
/*  365 */       char[] arrayOfChar = new char[i + i];
/*  366 */       byte b1 = 0;
/*      */       
/*  368 */       for (byte b2 = 0; b2 < i; b2++) {
/*  369 */         char c = paramString.charAt(b2);
/*  370 */         if (needEscape(c)) {
/*  371 */           arrayOfChar[b1++] = '\\';
/*      */         }
/*  373 */         arrayOfChar[b1++] = c;
/*      */       } 
/*  375 */       return new String(arrayOfChar, 0, b1);
/*      */     } 
/*  377 */     return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] binaryToHex(byte[] paramArrayOfbyte) throws UnsupportedEncodingException {
/*  390 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  392 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/*  393 */       if ((paramArrayOfbyte[b] & 0xFF) < 16) {
/*  394 */         stringBuffer.append("0" + 
/*  395 */             Integer.toHexString(paramArrayOfbyte[b] & 0xFF));
/*      */       } else {
/*  397 */         stringBuffer.append(
/*  398 */             Integer.toHexString(paramArrayOfbyte[b] & 0xFF));
/*      */       } 
/*      */     } 
/*  401 */     return stringBuffer.toString().getBytes(this.encoding);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] stringToByte_8859_1(String paramString) throws SaslException {
/*  414 */     char[] arrayOfChar = paramString.toCharArray();
/*      */     
/*      */     try {
/*  417 */       if (this.useUTF8) {
/*  418 */         for (byte b = 0; b < arrayOfChar.length; b++) {
/*  419 */           if (arrayOfChar[b] > 'ÿ') {
/*  420 */             return paramString.getBytes("UTF8");
/*      */           }
/*      */         } 
/*      */       }
/*  424 */       return paramString.getBytes("8859_1");
/*  425 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*  426 */       throw new SaslException("cannot encode string in UTF8 or 8859-1 (Latin-1)", unsupportedEncodingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected static byte[] getPlatformCiphers() {
/*  432 */     byte[] arrayOfByte = new byte[CIPHER_TOKENS.length];
/*      */     
/*  434 */     for (byte b = 0; b < JCE_CIPHER_NAME.length; b++) {
/*      */ 
/*      */       
/*      */       try {
/*  438 */         Cipher.getInstance(JCE_CIPHER_NAME[b]);
/*      */         
/*  440 */         logger.log(Level.FINE, "DIGEST01:Platform supports {0}", JCE_CIPHER_NAME[b]);
/*  441 */         arrayOfByte[b] = (byte)(arrayOfByte[b] | CIPHER_MASKS[b]);
/*  442 */       } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*      */       
/*  444 */       } catch (NoSuchPaddingException noSuchPaddingException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  449 */     if (arrayOfByte[1] != 0) {
/*  450 */       arrayOfByte[3] = (byte)(arrayOfByte[3] | CIPHER_MASKS[3]);
/*  451 */       arrayOfByte[4] = (byte)(arrayOfByte[4] | CIPHER_MASKS[4]);
/*      */     } 
/*      */     
/*  454 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte[] generateResponseValue(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, char[] paramArrayOfchar, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt, byte[] paramArrayOfbyte3) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
/*  485 */     MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  494 */     ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
/*  495 */     byteArrayOutputStream1.write((paramString1 + ":" + paramString2).getBytes(this.encoding));
/*  496 */     if (paramString3.equals("auth-conf") || paramString3
/*  497 */       .equals("auth-int")) {
/*      */       
/*  499 */       logger.log(Level.FINE, "DIGEST04:QOP: {0}", paramString3);
/*      */       
/*  501 */       byteArrayOutputStream1.write(":00000000000000000000000000000000".getBytes(this.encoding));
/*      */     } 
/*      */     
/*  504 */     if (logger.isLoggable(Level.FINE)) {
/*  505 */       logger.log(Level.FINE, "DIGEST05:A2: {0}", byteArrayOutputStream1.toString());
/*      */     }
/*      */     
/*  508 */     messageDigest.update(byteArrayOutputStream1.toByteArray());
/*  509 */     byte[] arrayOfByte3 = messageDigest.digest();
/*  510 */     byte[] arrayOfByte2 = binaryToHex(arrayOfByte3);
/*      */     
/*  512 */     if (logger.isLoggable(Level.FINE)) {
/*  513 */       logger.log(Level.FINE, "DIGEST06:HEX(H(A2)): {0}", new String(arrayOfByte2));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  520 */     ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
/*  521 */     byteArrayOutputStream2.write(stringToByte_8859_1(paramString4));
/*  522 */     byteArrayOutputStream2.write(58);
/*      */     
/*  524 */     byteArrayOutputStream2.write(stringToByte_8859_1(paramString5));
/*  525 */     byteArrayOutputStream2.write(58);
/*  526 */     byteArrayOutputStream2.write(stringToByte_8859_1(new String(paramArrayOfchar)));
/*      */     
/*  528 */     messageDigest.update(byteArrayOutputStream2.toByteArray());
/*  529 */     arrayOfByte3 = messageDigest.digest();
/*      */     
/*  531 */     if (logger.isLoggable(Level.FINE)) {
/*  532 */       logger.log(Level.FINE, "DIGEST07:H({0}) = {1}", new Object[] { byteArrayOutputStream2
/*  533 */             .toString(), new String(binaryToHex(arrayOfByte3)) });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  541 */     ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
/*  542 */     byteArrayOutputStream3.write(arrayOfByte3);
/*  543 */     byteArrayOutputStream3.write(58);
/*  544 */     byteArrayOutputStream3.write(paramArrayOfbyte1);
/*  545 */     byteArrayOutputStream3.write(58);
/*  546 */     byteArrayOutputStream3.write(paramArrayOfbyte2);
/*      */     
/*  548 */     if (paramArrayOfbyte3 != null) {
/*  549 */       byteArrayOutputStream3.write(58);
/*  550 */       byteArrayOutputStream3.write(paramArrayOfbyte3);
/*      */     } 
/*  552 */     messageDigest.update(byteArrayOutputStream3.toByteArray());
/*  553 */     arrayOfByte3 = messageDigest.digest();
/*  554 */     this.H_A1 = arrayOfByte3;
/*  555 */     byte[] arrayOfByte1 = binaryToHex(arrayOfByte3);
/*      */     
/*  557 */     if (logger.isLoggable(Level.FINE)) {
/*  558 */       logger.log(Level.FINE, "DIGEST08:H(A1) = {0}", new String(arrayOfByte1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  564 */     ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
/*  565 */     byteArrayOutputStream4.write(arrayOfByte1);
/*  566 */     byteArrayOutputStream4.write(58);
/*  567 */     byteArrayOutputStream4.write(paramArrayOfbyte1);
/*  568 */     byteArrayOutputStream4.write(58);
/*  569 */     byteArrayOutputStream4.write(nonceCountToHex(paramInt).getBytes(this.encoding));
/*  570 */     byteArrayOutputStream4.write(58);
/*  571 */     byteArrayOutputStream4.write(paramArrayOfbyte2);
/*  572 */     byteArrayOutputStream4.write(58);
/*  573 */     byteArrayOutputStream4.write(paramString3.getBytes(this.encoding));
/*  574 */     byteArrayOutputStream4.write(58);
/*  575 */     byteArrayOutputStream4.write(arrayOfByte2);
/*      */     
/*  577 */     if (logger.isLoggable(Level.FINE)) {
/*  578 */       logger.log(Level.FINE, "DIGEST09:KD: {0}", byteArrayOutputStream4.toString());
/*      */     }
/*      */     
/*  581 */     messageDigest.update(byteArrayOutputStream4.toByteArray());
/*  582 */     arrayOfByte3 = messageDigest.digest();
/*      */     
/*  584 */     byte[] arrayOfByte4 = binaryToHex(arrayOfByte3);
/*      */     
/*  586 */     if (logger.isLoggable(Level.FINE)) {
/*  587 */       logger.log(Level.FINE, "DIGEST10:response-value: {0}", new String(arrayOfByte4));
/*      */     }
/*      */     
/*  590 */     return arrayOfByte4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String nonceCountToHex(int paramInt) {
/*  600 */     String str = Integer.toHexString(paramInt);
/*  601 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  603 */     if (str.length() < 8) {
/*  604 */       for (byte b = 0; b < 8 - str.length(); b++) {
/*  605 */         stringBuffer.append("0");
/*      */       }
/*      */     }
/*      */     
/*  609 */     return stringBuffer.toString() + str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static byte[][] parseDirectives(byte[] paramArrayOfbyte, String[] paramArrayOfString, List<byte[]> paramList, int paramInt) throws SaslException {
/*  624 */     byte[][] arrayOfByte = new byte[paramArrayOfString.length][];
/*      */     
/*  626 */     ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream(10);
/*  627 */     ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream(10);
/*  628 */     boolean bool1 = true;
/*  629 */     boolean bool2 = false;
/*  630 */     boolean bool3 = false;
/*      */ 
/*      */     
/*  633 */     int i = skipLws(paramArrayOfbyte, 0);
/*  634 */     while (i < paramArrayOfbyte.length) {
/*  635 */       byte b = paramArrayOfbyte[i];
/*      */       
/*  637 */       if (bool1) {
/*  638 */         if (b == 44) {
/*  639 */           if (byteArrayOutputStream1.size() != 0) {
/*  640 */             throw new SaslException("Directive key contains a ',':" + byteArrayOutputStream1);
/*      */           }
/*      */ 
/*      */           
/*  644 */           i = skipLws(paramArrayOfbyte, i + 1); continue;
/*      */         } 
/*  646 */         if (b == 61) {
/*  647 */           if (byteArrayOutputStream1.size() == 0) {
/*  648 */             throw new SaslException("Empty directive key");
/*      */           }
/*  650 */           bool1 = false;
/*  651 */           i = skipLws(paramArrayOfbyte, i + 1);
/*      */ 
/*      */           
/*  654 */           if (i < paramArrayOfbyte.length) {
/*  655 */             if (paramArrayOfbyte[i] == 34) {
/*  656 */               bool2 = true;
/*  657 */               i++;
/*      */             }  continue;
/*      */           } 
/*  660 */           throw new SaslException("Valueless directive found: " + byteArrayOutputStream1
/*  661 */               .toString());
/*      */         } 
/*  663 */         if (isLws(b)) {
/*      */           
/*  665 */           i = skipLws(paramArrayOfbyte, i + 1);
/*      */ 
/*      */           
/*  668 */           if (i < paramArrayOfbyte.length) {
/*  669 */             if (paramArrayOfbyte[i] != 61)
/*  670 */               throw new SaslException("'=' expected after key: " + byteArrayOutputStream1
/*  671 */                   .toString()); 
/*      */             continue;
/*      */           } 
/*  674 */           throw new SaslException("'=' expected after key: " + byteArrayOutputStream1
/*  675 */               .toString());
/*      */         } 
/*      */         
/*  678 */         byteArrayOutputStream1.write(b);
/*  679 */         i++; continue;
/*      */       } 
/*  681 */       if (bool2) {
/*      */         
/*  683 */         if (b == 92) {
/*      */           
/*  685 */           i++;
/*  686 */           if (i < paramArrayOfbyte.length) {
/*  687 */             byteArrayOutputStream2.write(paramArrayOfbyte[i]);
/*  688 */             i++;
/*      */             continue;
/*      */           } 
/*  691 */           throw new SaslException("Unmatched quote found for directive: " + byteArrayOutputStream1
/*      */               
/*  693 */               .toString() + " with value: " + byteArrayOutputStream2.toString());
/*      */         } 
/*  695 */         if (b == 34) {
/*      */           
/*  697 */           i++;
/*  698 */           bool2 = false;
/*  699 */           bool3 = true; continue;
/*      */         } 
/*  701 */         byteArrayOutputStream2.write(b);
/*  702 */         i++;
/*      */         continue;
/*      */       } 
/*  705 */       if (isLws(b) || b == 44) {
/*      */ 
/*      */         
/*  708 */         extractDirective(byteArrayOutputStream1.toString(), byteArrayOutputStream2.toByteArray(), paramArrayOfString, arrayOfByte, paramList, paramInt);
/*      */         
/*  710 */         byteArrayOutputStream1.reset();
/*  711 */         byteArrayOutputStream2.reset();
/*  712 */         bool1 = true;
/*  713 */         bool2 = bool3 = false;
/*  714 */         i = skipLws(paramArrayOfbyte, i + 1); continue;
/*      */       } 
/*  716 */       if (bool3) {
/*  717 */         throw new SaslException("Expecting comma or linear whitespace after quoted string: \"" + byteArrayOutputStream2
/*      */             
/*  719 */             .toString() + "\"");
/*      */       }
/*  721 */       byteArrayOutputStream2.write(b);
/*  722 */       i++;
/*      */     } 
/*      */ 
/*      */     
/*  726 */     if (bool2) {
/*  727 */       throw new SaslException("Unmatched quote found for directive: " + byteArrayOutputStream1
/*  728 */           .toString() + " with value: " + byteArrayOutputStream2
/*  729 */           .toString());
/*      */     }
/*      */ 
/*      */     
/*  733 */     if (byteArrayOutputStream1.size() > 0) {
/*  734 */       extractDirective(byteArrayOutputStream1.toString(), byteArrayOutputStream2.toByteArray(), paramArrayOfString, arrayOfByte, paramList, paramInt);
/*      */     }
/*      */ 
/*      */     
/*  738 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isLws(byte paramByte) {
/*  745 */     switch (paramByte) {
/*      */       case 9:
/*      */       case 10:
/*      */       case 13:
/*      */       case 32:
/*  750 */         return true;
/*      */     } 
/*  752 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int skipLws(byte[] paramArrayOfbyte, int paramInt) {
/*      */     int i;
/*  758 */     for (i = paramInt; i < paramArrayOfbyte.length; i++) {
/*  759 */       if (!isLws(paramArrayOfbyte[i])) {
/*  760 */         return i;
/*      */       }
/*      */     } 
/*  763 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void extractDirective(String paramString, byte[] paramArrayOfbyte, String[] paramArrayOfString, byte[][] paramArrayOfbyte1, List<byte[]> paramList, int paramInt) throws SaslException {
/*  778 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  779 */       if (paramString.equalsIgnoreCase(paramArrayOfString[b])) {
/*  780 */         if (paramArrayOfbyte1[b] == null) {
/*  781 */           paramArrayOfbyte1[b] = paramArrayOfbyte;
/*  782 */           if (logger.isLoggable(Level.FINE)) {
/*  783 */             logger.log(Level.FINE, "DIGEST11:Directive {0} = {1}", new Object[] { paramArrayOfString[b], new String(paramArrayOfbyte1[b]) });
/*      */           }
/*      */           
/*      */           break;
/*      */         } 
/*  788 */         if (paramList != null && b == paramInt) {
/*      */           
/*  790 */           if (paramList.isEmpty()) {
/*  791 */             paramList.add(paramArrayOfbyte1[b]);
/*      */           }
/*  793 */           paramList.add(paramArrayOfbyte); break;
/*      */         } 
/*  795 */         throw new SaslException("DIGEST-MD5: peer sent more than one " + paramString + " directive: " + new String(paramArrayOfbyte));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class DigestIntegrity
/*      */     implements SecurityCtx
/*      */   {
/*      */     private static final String CLIENT_INT_MAGIC = "Digest session key to client-to-server signing key magic constant";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final String SVR_INT_MAGIC = "Digest session key to server-to-client signing key magic constant";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected byte[] myKi;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected byte[] peerKi;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  829 */     protected int mySeqNum = 0;
/*  830 */     protected int peerSeqNum = 0;
/*      */ 
/*      */     
/*  833 */     protected final byte[] messageType = new byte[2];
/*  834 */     protected final byte[] sequenceNum = new byte[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DigestIntegrity(boolean param1Boolean) throws SaslException {
/*      */       try {
/*  847 */         generateIntegrityKeyPair(param1Boolean);
/*      */       }
/*  849 */       catch (UnsupportedEncodingException unsupportedEncodingException) {
/*  850 */         throw new SaslException("DIGEST-MD5: Error encoding strings into UTF-8", unsupportedEncodingException);
/*      */       
/*      */       }
/*  853 */       catch (IOException iOException) {
/*  854 */         throw new SaslException("DIGEST-MD5: Error accessing buffers required to create integrity key pairs", iOException);
/*      */       
/*      */       }
/*  857 */       catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*  858 */         throw new SaslException("DIGEST-MD5: Unsupported digest algorithm used to create integrity key pairs", noSuchAlgorithmException);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  863 */       DigestMD5Base.intToNetworkByteOrder(1, this.messageType, 0, 2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void generateIntegrityKeyPair(boolean param1Boolean) throws UnsupportedEncodingException, IOException, NoSuchAlgorithmException {
/*  881 */       byte[] arrayOfByte1 = "Digest session key to client-to-server signing key magic constant".getBytes(DigestMD5Base.this.encoding);
/*  882 */       byte[] arrayOfByte2 = "Digest session key to server-to-client signing key magic constant".getBytes(DigestMD5Base.this.encoding);
/*      */       
/*  884 */       MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/*      */ 
/*      */       
/*  887 */       byte[] arrayOfByte3 = new byte[DigestMD5Base.this.H_A1.length + arrayOfByte1.length];
/*      */ 
/*      */       
/*  890 */       System.arraycopy(DigestMD5Base.this.H_A1, 0, arrayOfByte3, 0, DigestMD5Base.this.H_A1.length);
/*  891 */       System.arraycopy(arrayOfByte1, 0, arrayOfByte3, DigestMD5Base.this.H_A1.length, arrayOfByte1.length);
/*  892 */       messageDigest.update(arrayOfByte3);
/*  893 */       byte[] arrayOfByte4 = messageDigest.digest();
/*      */ 
/*      */ 
/*      */       
/*  897 */       System.arraycopy(arrayOfByte2, 0, arrayOfByte3, DigestMD5Base.this.H_A1.length, arrayOfByte2.length);
/*      */       
/*  899 */       messageDigest.update(arrayOfByte3);
/*  900 */       byte[] arrayOfByte5 = messageDigest.digest();
/*      */       
/*  902 */       if (DigestMD5Base.logger.isLoggable(Level.FINER)) {
/*  903 */         DigestMD5Base.traceOutput(DigestMD5Base.DI_CLASS_NAME, "generateIntegrityKeyPair", "DIGEST12:Kic: ", arrayOfByte4);
/*      */         
/*  905 */         DigestMD5Base.traceOutput(DigestMD5Base.DI_CLASS_NAME, "generateIntegrityKeyPair", "DIGEST13:Kis: ", arrayOfByte5);
/*      */       } 
/*      */ 
/*      */       
/*  909 */       if (param1Boolean) {
/*  910 */         this.myKi = arrayOfByte4;
/*  911 */         this.peerKi = arrayOfByte5;
/*      */       } else {
/*  913 */         this.myKi = arrayOfByte5;
/*  914 */         this.peerKi = arrayOfByte4;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] wrap(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws SaslException {
/*  933 */       if (param1Int2 == 0) {
/*  934 */         return DigestMD5Base.EMPTY_BYTE_ARRAY;
/*      */       }
/*      */ 
/*      */       
/*  938 */       byte[] arrayOfByte1 = new byte[param1Int2 + 10 + 2 + 4];
/*      */ 
/*      */       
/*  941 */       System.arraycopy(param1ArrayOfbyte, param1Int1, arrayOfByte1, 0, param1Int2);
/*      */       
/*  943 */       incrementSeqNum();
/*      */ 
/*      */       
/*  946 */       byte[] arrayOfByte2 = getHMAC(this.myKi, this.sequenceNum, param1ArrayOfbyte, param1Int1, param1Int2);
/*      */       
/*  948 */       if (DigestMD5Base.logger.isLoggable(Level.FINEST)) {
/*  949 */         DigestMD5Base.traceOutput(DigestMD5Base.DI_CLASS_NAME, "wrap", "DIGEST14:outgoing: ", param1ArrayOfbyte, param1Int1, param1Int2);
/*      */         
/*  951 */         DigestMD5Base.traceOutput(DigestMD5Base.DI_CLASS_NAME, "wrap", "DIGEST15:seqNum: ", this.sequenceNum);
/*      */         
/*  953 */         DigestMD5Base.traceOutput(DigestMD5Base.DI_CLASS_NAME, "wrap", "DIGEST16:MAC: ", arrayOfByte2);
/*      */       } 
/*      */ 
/*      */       
/*  957 */       System.arraycopy(arrayOfByte2, 0, arrayOfByte1, param1Int2, 10);
/*      */ 
/*      */       
/*  960 */       System.arraycopy(this.messageType, 0, arrayOfByte1, param1Int2 + 10, 2);
/*      */ 
/*      */       
/*  963 */       System.arraycopy(this.sequenceNum, 0, arrayOfByte1, param1Int2 + 12, 4);
/*  964 */       if (DigestMD5Base.logger.isLoggable(Level.FINEST)) {
/*  965 */         DigestMD5Base.traceOutput(DigestMD5Base.DI_CLASS_NAME, "wrap", "DIGEST17:wrapped: ", arrayOfByte1);
/*      */       }
/*  967 */       return arrayOfByte1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] unwrap(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws SaslException {
/*  988 */       if (param1Int2 == 0) {
/*  989 */         return DigestMD5Base.EMPTY_BYTE_ARRAY;
/*      */       }
/*      */ 
/*      */       
/*  993 */       byte[] arrayOfByte1 = new byte[10];
/*  994 */       byte[] arrayOfByte2 = new byte[param1Int2 - 16];
/*  995 */       byte[] arrayOfByte3 = new byte[2];
/*  996 */       byte[] arrayOfByte4 = new byte[4];
/*      */ 
/*      */       
/*  999 */       System.arraycopy(param1ArrayOfbyte, param1Int1, arrayOfByte2, 0, arrayOfByte2.length);
/* 1000 */       System.arraycopy(param1ArrayOfbyte, param1Int1 + arrayOfByte2.length, arrayOfByte1, 0, 10);
/* 1001 */       System.arraycopy(param1ArrayOfbyte, param1Int1 + arrayOfByte2.length + 10, arrayOfByte3, 0, 2);
/* 1002 */       System.arraycopy(param1ArrayOfbyte, param1Int1 + arrayOfByte2.length + 12, arrayOfByte4, 0, 4);
/*      */ 
/*      */       
/* 1005 */       byte[] arrayOfByte5 = getHMAC(this.peerKi, arrayOfByte4, arrayOfByte2, 0, arrayOfByte2.length);
/*      */       
/* 1007 */       if (DigestMD5Base.logger.isLoggable(Level.FINEST)) {
/* 1008 */         DigestMD5Base.traceOutput(DigestMD5Base.DI_CLASS_NAME, "unwrap", "DIGEST18:incoming: ", arrayOfByte2);
/*      */         
/* 1010 */         DigestMD5Base.traceOutput(DigestMD5Base.DI_CLASS_NAME, "unwrap", "DIGEST19:MAC: ", arrayOfByte1);
/*      */         
/* 1012 */         DigestMD5Base.traceOutput(DigestMD5Base.DI_CLASS_NAME, "unwrap", "DIGEST20:messageType: ", arrayOfByte3);
/*      */         
/* 1014 */         DigestMD5Base.traceOutput(DigestMD5Base.DI_CLASS_NAME, "unwrap", "DIGEST21:sequenceNum: ", arrayOfByte4);
/*      */         
/* 1016 */         DigestMD5Base.traceOutput(DigestMD5Base.DI_CLASS_NAME, "unwrap", "DIGEST22:expectedMAC: ", arrayOfByte5);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1021 */       if (!Arrays.equals(arrayOfByte1, arrayOfByte5)) {
/*      */         
/* 1023 */         DigestMD5Base.logger.log(Level.INFO, "DIGEST23:Unmatched MACs");
/* 1024 */         return DigestMD5Base.EMPTY_BYTE_ARRAY;
/*      */       } 
/*      */ 
/*      */       
/* 1028 */       if (this.peerSeqNum != DigestMD5Base.networkByteOrderToInt(arrayOfByte4, 0, 4)) {
/* 1029 */         throw new SaslException("DIGEST-MD5: Out of order sequencing of messages from server. Got: " + DigestMD5Base
/*      */             
/* 1031 */             .networkByteOrderToInt(arrayOfByte4, 0, 4) + " Expected: " + this.peerSeqNum);
/*      */       }
/*      */ 
/*      */       
/* 1035 */       if (!Arrays.equals(this.messageType, arrayOfByte3)) {
/* 1036 */         throw new SaslException("DIGEST-MD5: invalid message type: " + DigestMD5Base
/* 1037 */             .networkByteOrderToInt(arrayOfByte3, 0, 2));
/*      */       }
/*      */ 
/*      */       
/* 1041 */       this.peerSeqNum++;
/* 1042 */       return arrayOfByte2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected byte[] getHMAC(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2, byte[] param1ArrayOfbyte3, int param1Int1, int param1Int2) throws SaslException {
/* 1060 */       byte[] arrayOfByte = new byte[4 + param1Int2];
/* 1061 */       System.arraycopy(param1ArrayOfbyte2, 0, arrayOfByte, 0, 4);
/* 1062 */       System.arraycopy(param1ArrayOfbyte3, param1Int1, arrayOfByte, 4, param1Int2);
/*      */       
/*      */       try {
/* 1065 */         SecretKeySpec secretKeySpec = new SecretKeySpec(param1ArrayOfbyte1, "HmacMD5");
/* 1066 */         Mac mac = Mac.getInstance("HmacMD5");
/* 1067 */         mac.init(secretKeySpec);
/* 1068 */         mac.update(arrayOfByte);
/* 1069 */         byte[] arrayOfByte1 = mac.doFinal();
/*      */ 
/*      */         
/* 1072 */         byte[] arrayOfByte2 = new byte[10];
/* 1073 */         System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, 10);
/*      */         
/* 1075 */         return arrayOfByte2;
/* 1076 */       } catch (InvalidKeyException invalidKeyException) {
/* 1077 */         throw new SaslException("DIGEST-MD5: Invalid bytes used for key of HMAC-MD5 hash.", invalidKeyException);
/*      */       }
/* 1079 */       catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 1080 */         throw new SaslException("DIGEST-MD5: Error creating instance of MD5 digest algorithm", noSuchAlgorithmException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void incrementSeqNum() {
/* 1089 */       DigestMD5Base.intToNetworkByteOrder(this.mySeqNum++, this.sequenceNum, 0, 4);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final class DigestPrivacy
/*      */     extends DigestIntegrity
/*      */     implements SecurityCtx
/*      */   {
/*      */     private static final String CLIENT_CONF_MAGIC = "Digest H(A1) to client-to-server sealing key magic constant";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final String SVR_CONF_MAGIC = "Digest H(A1) to server-to-client sealing key magic constant";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Cipher encCipher;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Cipher decCipher;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DigestPrivacy(boolean param1Boolean) throws SaslException {
/* 1127 */       super(param1Boolean);
/*      */       
/*      */       try {
/* 1130 */         generatePrivacyKeyPair(param1Boolean);
/*      */       }
/* 1132 */       catch (SaslException saslException) {
/* 1133 */         throw saslException;
/*      */       }
/* 1135 */       catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 1136 */         throw new SaslException("DIGEST-MD5: Error encoding string value into UTF-8", unsupportedEncodingException);
/*      */       
/*      */       }
/* 1139 */       catch (IOException iOException) {
/* 1140 */         throw new SaslException("DIGEST-MD5: Error accessing buffers required to generate cipher keys", iOException);
/*      */       }
/* 1142 */       catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 1143 */         throw new SaslException("DIGEST-MD5: Error creating instance of required cipher or digest", noSuchAlgorithmException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void generatePrivacyKeyPair(boolean param1Boolean) throws IOException, UnsupportedEncodingException, NoSuchAlgorithmException, SaslException {
/*      */       byte b;
/* 1165 */       byte[] arrayOfByte6, arrayOfByte7, arrayOfByte1 = "Digest H(A1) to client-to-server sealing key magic constant".getBytes(DigestMD5Base.this.encoding);
/* 1166 */       byte[] arrayOfByte2 = "Digest H(A1) to server-to-client sealing key magic constant".getBytes(DigestMD5Base.this.encoding);
/*      */ 
/*      */       
/* 1169 */       MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/*      */ 
/*      */       
/* 1172 */       if (DigestMD5Base.this.negotiatedCipher.equals(DigestMD5Base.CIPHER_TOKENS[4])) {
/* 1173 */         b = 5;
/* 1174 */       } else if (DigestMD5Base.this.negotiatedCipher.equals(DigestMD5Base.CIPHER_TOKENS[3])) {
/* 1175 */         b = 7;
/*      */       } else {
/* 1177 */         b = 16;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1182 */       byte[] arrayOfByte3 = new byte[b + arrayOfByte1.length];
/* 1183 */       System.arraycopy(DigestMD5Base.this.H_A1, 0, arrayOfByte3, 0, b);
/*      */ 
/*      */       
/* 1186 */       System.arraycopy(arrayOfByte1, 0, arrayOfByte3, b, arrayOfByte1.length);
/* 1187 */       messageDigest.update(arrayOfByte3);
/* 1188 */       byte[] arrayOfByte4 = messageDigest.digest();
/*      */ 
/*      */ 
/*      */       
/* 1192 */       System.arraycopy(arrayOfByte2, 0, arrayOfByte3, b, arrayOfByte2.length);
/* 1193 */       messageDigest.update(arrayOfByte3);
/* 1194 */       byte[] arrayOfByte5 = messageDigest.digest();
/*      */       
/* 1196 */       if (DigestMD5Base.logger.isLoggable(Level.FINER)) {
/* 1197 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "generatePrivacyKeyPair", "DIGEST24:Kcc: ", arrayOfByte4);
/*      */         
/* 1199 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "generatePrivacyKeyPair", "DIGEST25:Kcs: ", arrayOfByte5);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1206 */       if (param1Boolean) {
/* 1207 */         arrayOfByte6 = arrayOfByte4;
/* 1208 */         arrayOfByte7 = arrayOfByte5;
/*      */       } else {
/* 1210 */         arrayOfByte6 = arrayOfByte5;
/* 1211 */         arrayOfByte7 = arrayOfByte4;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1219 */         if (DigestMD5Base.this.negotiatedCipher.indexOf(DigestMD5Base.CIPHER_TOKENS[1]) > -1) {
/* 1220 */           this.encCipher = Cipher.getInstance("RC4");
/* 1221 */           this.decCipher = Cipher.getInstance("RC4");
/*      */           
/* 1223 */           SecretKeySpec secretKeySpec1 = new SecretKeySpec(arrayOfByte6, "RC4");
/* 1224 */           SecretKeySpec secretKeySpec2 = new SecretKeySpec(arrayOfByte7, "RC4");
/*      */           
/* 1226 */           this.encCipher.init(1, secretKeySpec1);
/* 1227 */           this.decCipher.init(2, secretKeySpec2);
/*      */         }
/* 1229 */         else if (DigestMD5Base.this.negotiatedCipher.equals(DigestMD5Base.CIPHER_TOKENS[2]) || DigestMD5Base.this.negotiatedCipher
/* 1230 */           .equals(DigestMD5Base.CIPHER_TOKENS[0])) {
/*      */           String str1, str2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1238 */           if (DigestMD5Base.this.negotiatedCipher.equals(DigestMD5Base.CIPHER_TOKENS[2])) {
/* 1239 */             str1 = "DES/CBC/NoPadding";
/* 1240 */             str2 = "des";
/*      */           } else {
/*      */             
/* 1243 */             str1 = "DESede/CBC/NoPadding";
/* 1244 */             str2 = "desede";
/*      */           } 
/*      */           
/* 1247 */           this.encCipher = Cipher.getInstance(str1);
/* 1248 */           this.decCipher = Cipher.getInstance(str1);
/*      */           
/* 1250 */           SecretKey secretKey1 = DigestMD5Base.makeDesKeys(arrayOfByte6, str2);
/* 1251 */           SecretKey secretKey2 = DigestMD5Base.makeDesKeys(arrayOfByte7, str2);
/*      */ 
/*      */           
/* 1254 */           IvParameterSpec ivParameterSpec1 = new IvParameterSpec(arrayOfByte6, 8, 8);
/* 1255 */           IvParameterSpec ivParameterSpec2 = new IvParameterSpec(arrayOfByte7, 8, 8);
/*      */ 
/*      */           
/* 1258 */           this.encCipher.init(1, secretKey1, ivParameterSpec1);
/* 1259 */           this.decCipher.init(2, secretKey2, ivParameterSpec2);
/*      */           
/* 1261 */           if (DigestMD5Base.logger.isLoggable(Level.FINER)) {
/* 1262 */             DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "generatePrivacyKeyPair", "DIGEST26:" + DigestMD5Base.this.negotiatedCipher + " IVcc: ", ivParameterSpec1
/*      */                 
/* 1264 */                 .getIV());
/* 1265 */             DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "generatePrivacyKeyPair", "DIGEST27:" + DigestMD5Base.this.negotiatedCipher + " IVcs: ", ivParameterSpec2
/*      */                 
/* 1267 */                 .getIV());
/* 1268 */             DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "generatePrivacyKeyPair", "DIGEST28:" + DigestMD5Base.this.negotiatedCipher + " encryption key: ", secretKey1
/*      */                 
/* 1270 */                 .getEncoded());
/* 1271 */             DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "generatePrivacyKeyPair", "DIGEST29:" + DigestMD5Base.this.negotiatedCipher + " decryption key: ", secretKey2
/*      */                 
/* 1273 */                 .getEncoded());
/*      */           } 
/*      */         } 
/* 1276 */       } catch (InvalidKeySpecException invalidKeySpecException) {
/* 1277 */         throw new SaslException("DIGEST-MD5: Unsupported key specification used.", invalidKeySpecException);
/*      */       }
/* 1279 */       catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/* 1280 */         throw new SaslException("DIGEST-MD5: Invalid cipher algorithem parameter used to create cipher instance", invalidAlgorithmParameterException);
/*      */       }
/* 1282 */       catch (NoSuchPaddingException noSuchPaddingException) {
/* 1283 */         throw new SaslException("DIGEST-MD5: Unsupported padding used for chosen cipher", noSuchPaddingException);
/*      */       }
/* 1285 */       catch (InvalidKeyException invalidKeyException) {
/* 1286 */         throw new SaslException("DIGEST-MD5: Invalid data used to initialize keys", invalidKeyException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] wrap(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws SaslException {
/*      */       byte[] arrayOfByte2, arrayOfByte4;
/* 1309 */       if (param1Int2 == 0) {
/* 1310 */         return DigestMD5Base.EMPTY_BYTE_ARRAY;
/*      */       }
/*      */ 
/*      */       
/* 1314 */       incrementSeqNum();
/* 1315 */       byte[] arrayOfByte1 = getHMAC(this.myKi, this.sequenceNum, param1ArrayOfbyte, param1Int1, param1Int2);
/*      */       
/* 1317 */       if (DigestMD5Base.logger.isLoggable(Level.FINEST)) {
/* 1318 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "wrap", "DIGEST30:Outgoing: ", param1ArrayOfbyte, param1Int1, param1Int2);
/*      */         
/* 1320 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "wrap", "seqNum: ", this.sequenceNum);
/*      */         
/* 1322 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "wrap", "MAC: ", arrayOfByte1);
/*      */       } 
/*      */ 
/*      */       
/* 1326 */       int i = this.encCipher.getBlockSize();
/*      */       
/* 1328 */       if (i > 1) {
/* 1329 */         int j = i - (param1Int2 + 10) % i;
/* 1330 */         arrayOfByte2 = new byte[j];
/* 1331 */         for (byte b = 0; b < j; b++) {
/* 1332 */           arrayOfByte2[b] = (byte)j;
/*      */         }
/*      */       } else {
/* 1335 */         arrayOfByte2 = DigestMD5Base.EMPTY_BYTE_ARRAY;
/*      */       } 
/*      */       
/* 1338 */       byte[] arrayOfByte3 = new byte[param1Int2 + arrayOfByte2.length + 10];
/*      */ 
/*      */       
/* 1341 */       System.arraycopy(param1ArrayOfbyte, param1Int1, arrayOfByte3, 0, param1Int2);
/* 1342 */       System.arraycopy(arrayOfByte2, 0, arrayOfByte3, param1Int2, arrayOfByte2.length);
/* 1343 */       System.arraycopy(arrayOfByte1, 0, arrayOfByte3, param1Int2 + arrayOfByte2.length, 10);
/*      */       
/* 1345 */       if (DigestMD5Base.logger.isLoggable(Level.FINEST)) {
/* 1346 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "wrap", "DIGEST31:{msg, pad, KicMAC}: ", arrayOfByte3);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1354 */         arrayOfByte4 = this.encCipher.update(arrayOfByte3);
/*      */         
/* 1356 */         if (arrayOfByte4 == null)
/*      */         {
/* 1358 */           throw new IllegalBlockSizeException("" + arrayOfByte3.length);
/*      */         }
/* 1360 */       } catch (IllegalBlockSizeException illegalBlockSizeException) {
/* 1361 */         throw new SaslException("DIGEST-MD5: Invalid block size for cipher", illegalBlockSizeException);
/*      */       } 
/*      */ 
/*      */       
/* 1365 */       byte[] arrayOfByte5 = new byte[arrayOfByte4.length + 2 + 4];
/* 1366 */       System.arraycopy(arrayOfByte4, 0, arrayOfByte5, 0, arrayOfByte4.length);
/* 1367 */       System.arraycopy(this.messageType, 0, arrayOfByte5, arrayOfByte4.length, 2);
/* 1368 */       System.arraycopy(this.sequenceNum, 0, arrayOfByte5, arrayOfByte4.length + 2, 4);
/*      */       
/* 1370 */       if (DigestMD5Base.logger.isLoggable(Level.FINEST)) {
/* 1371 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "wrap", "DIGEST32:Wrapped: ", arrayOfByte5);
/*      */       }
/*      */       
/* 1374 */       return arrayOfByte5;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] unwrap(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws SaslException {
/*      */       byte[] arrayOfByte4;
/* 1394 */       if (param1Int2 == 0) {
/* 1395 */         return DigestMD5Base.EMPTY_BYTE_ARRAY;
/*      */       }
/*      */       
/* 1398 */       byte[] arrayOfByte1 = new byte[param1Int2 - 6];
/* 1399 */       byte[] arrayOfByte2 = new byte[2];
/* 1400 */       byte[] arrayOfByte3 = new byte[4];
/*      */ 
/*      */       
/* 1403 */       System.arraycopy(param1ArrayOfbyte, param1Int1, arrayOfByte1, 0, arrayOfByte1.length);
/*      */       
/* 1405 */       System.arraycopy(param1ArrayOfbyte, param1Int1 + arrayOfByte1.length, arrayOfByte2, 0, 2);
/*      */       
/* 1407 */       System.arraycopy(param1ArrayOfbyte, param1Int1 + arrayOfByte1.length + 2, arrayOfByte3, 0, 4);
/*      */ 
/*      */       
/* 1410 */       if (DigestMD5Base.logger.isLoggable(Level.FINEST)) {
/* 1411 */         DigestMD5Base.logger.log(Level.FINEST, "DIGEST33:Expecting sequence num: {0}", new Integer(this.peerSeqNum));
/*      */ 
/*      */         
/* 1414 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "unwrap", "DIGEST34:incoming: ", arrayOfByte1);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1424 */         arrayOfByte4 = this.decCipher.update(arrayOfByte1);
/*      */         
/* 1426 */         if (arrayOfByte4 == null)
/*      */         {
/* 1428 */           throw new IllegalBlockSizeException("" + arrayOfByte1.length);
/*      */         }
/* 1430 */       } catch (IllegalBlockSizeException illegalBlockSizeException) {
/* 1431 */         throw new SaslException("DIGEST-MD5: Illegal block sizes used with chosen cipher", illegalBlockSizeException);
/*      */       } 
/*      */ 
/*      */       
/* 1435 */       byte[] arrayOfByte5 = new byte[arrayOfByte4.length - 10];
/* 1436 */       byte[] arrayOfByte6 = new byte[10];
/*      */       
/* 1438 */       System.arraycopy(arrayOfByte4, 0, arrayOfByte5, 0, arrayOfByte5.length);
/*      */       
/* 1440 */       System.arraycopy(arrayOfByte4, arrayOfByte5.length, arrayOfByte6, 0, 10);
/*      */ 
/*      */       
/* 1443 */       if (DigestMD5Base.logger.isLoggable(Level.FINEST)) {
/* 1444 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "unwrap", "DIGEST35:Unwrapped (w/padding): ", arrayOfByte5);
/*      */         
/* 1446 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "unwrap", "DIGEST36:MAC: ", arrayOfByte6);
/* 1447 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "unwrap", "DIGEST37:messageType: ", arrayOfByte2);
/*      */         
/* 1449 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "unwrap", "DIGEST38:sequenceNum: ", arrayOfByte3);
/*      */       } 
/*      */ 
/*      */       
/* 1453 */       int i = arrayOfByte5.length;
/* 1454 */       int j = this.decCipher.getBlockSize();
/* 1455 */       if (j > 1) {
/*      */         
/* 1457 */         i -= arrayOfByte5[arrayOfByte5.length - 1];
/* 1458 */         if (i < 0) {
/*      */           
/* 1460 */           if (DigestMD5Base.logger.isLoggable(Level.INFO)) {
/* 1461 */             DigestMD5Base.logger.log(Level.INFO, "DIGEST39:Incorrect padding: {0}", new Byte(arrayOfByte5[arrayOfByte5.length - 1]));
/*      */           }
/*      */ 
/*      */           
/* 1465 */           return DigestMD5Base.EMPTY_BYTE_ARRAY;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1470 */       byte[] arrayOfByte7 = getHMAC(this.peerKi, arrayOfByte3, arrayOfByte5, 0, i);
/*      */ 
/*      */       
/* 1473 */       if (DigestMD5Base.logger.isLoggable(Level.FINEST)) {
/* 1474 */         DigestMD5Base.traceOutput(DigestMD5Base.DP_CLASS_NAME, "unwrap", "DIGEST40:KisMAC: ", arrayOfByte7);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1479 */       if (!Arrays.equals(arrayOfByte6, arrayOfByte7)) {
/*      */         
/* 1481 */         DigestMD5Base.logger.log(Level.INFO, "DIGEST41:Unmatched MACs");
/* 1482 */         return DigestMD5Base.EMPTY_BYTE_ARRAY;
/*      */       } 
/*      */ 
/*      */       
/* 1486 */       if (this.peerSeqNum != DigestMD5Base.networkByteOrderToInt(arrayOfByte3, 0, 4)) {
/* 1487 */         throw new SaslException("DIGEST-MD5: Out of order sequencing of messages from server. Got: " + DigestMD5Base
/*      */             
/* 1489 */             .networkByteOrderToInt(arrayOfByte3, 0, 4) + " Expected: " + this.peerSeqNum);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1494 */       if (!Arrays.equals(this.messageType, arrayOfByte2)) {
/* 1495 */         throw new SaslException("DIGEST-MD5: invalid message type: " + DigestMD5Base
/* 1496 */             .networkByteOrderToInt(arrayOfByte2, 0, 2));
/*      */       }
/*      */ 
/*      */       
/* 1500 */       this.peerSeqNum++;
/*      */       
/* 1502 */       if (i == arrayOfByte5.length) {
/* 1503 */         return arrayOfByte5;
/*      */       }
/*      */       
/* 1506 */       byte[] arrayOfByte8 = new byte[i];
/* 1507 */       System.arraycopy(arrayOfByte5, 0, arrayOfByte8, 0, i);
/* 1508 */       return arrayOfByte8;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1515 */   private static final BigInteger MASK = new BigInteger("7f", 16);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setParityBit(byte[] paramArrayOfbyte) {
/* 1522 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/* 1523 */       int i = paramArrayOfbyte[b] & 0xFE;
/* 1524 */       i |= Integer.bitCount(i) & 0x1 ^ 0x1;
/* 1525 */       paramArrayOfbyte[b] = (byte)i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] addDesParity(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 1539 */     if (paramInt2 != 7) {
/* 1540 */       throw new IllegalArgumentException("Invalid length of DES Key Value:" + paramInt2);
/*      */     }
/*      */     
/* 1543 */     byte[] arrayOfByte1 = new byte[7];
/* 1544 */     System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte1, 0, paramInt2);
/*      */     
/* 1546 */     byte[] arrayOfByte2 = new byte[8];
/* 1547 */     BigInteger bigInteger = new BigInteger(arrayOfByte1);
/*      */ 
/*      */     
/* 1550 */     for (int i = arrayOfByte2.length - 1; i >= 0; i--) {
/* 1551 */       arrayOfByte2[i] = bigInteger.and(MASK).toByteArray()[0];
/* 1552 */       arrayOfByte2[i] = (byte)(arrayOfByte2[i] << 1);
/* 1553 */       bigInteger = bigInteger.shiftRight(7);
/*      */     } 
/* 1555 */     setParityBit(arrayOfByte2);
/* 1556 */     return arrayOfByte2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static SecretKey makeDesKeys(byte[] paramArrayOfbyte, String paramString) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
/*      */     DESedeKeySpec dESedeKeySpec;
/* 1579 */     byte[] arrayOfByte2, arrayOfByte3, arrayOfByte1 = addDesParity(paramArrayOfbyte, 0, 7);
/*      */     
/* 1581 */     DESKeySpec dESKeySpec = null;
/*      */     
/* 1583 */     SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(paramString);
/* 1584 */     switch (paramString) {
/*      */       case "des":
/* 1586 */         dESKeySpec = new DESKeySpec(arrayOfByte1, 0);
/* 1587 */         if (logger.isLoggable(Level.FINEST)) {
/* 1588 */           traceOutput(DP_CLASS_NAME, "makeDesKeys", "DIGEST42:DES key input: ", paramArrayOfbyte);
/*      */           
/* 1590 */           traceOutput(DP_CLASS_NAME, "makeDesKeys", "DIGEST43:DES key parity-adjusted: ", arrayOfByte1);
/*      */           
/* 1592 */           traceOutput(DP_CLASS_NAME, "makeDesKeys", "DIGEST44:DES key material: ", dESKeySpec
/* 1593 */               .getKey());
/* 1594 */           logger.log(Level.FINEST, "DIGEST45: is parity-adjusted? {0}", 
/* 1595 */               Boolean.valueOf(DESKeySpec.isParityAdjusted(arrayOfByte1, 0)));
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1624 */         return secretKeyFactory.generateSecret(dESKeySpec);case "desede": arrayOfByte2 = addDesParity(paramArrayOfbyte, 7, 7); arrayOfByte3 = new byte[arrayOfByte1.length * 2 + arrayOfByte2.length]; System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length); System.arraycopy(arrayOfByte2, 0, arrayOfByte3, arrayOfByte1.length, arrayOfByte2.length); System.arraycopy(arrayOfByte1, 0, arrayOfByte3, arrayOfByte1.length + arrayOfByte2.length, arrayOfByte1.length); dESedeKeySpec = new DESedeKeySpec(arrayOfByte3, 0); if (logger.isLoggable(Level.FINEST)) { traceOutput(DP_CLASS_NAME, "makeDesKeys", "DIGEST46:3DES key input: ", paramArrayOfbyte); traceOutput(DP_CLASS_NAME, "makeDesKeys", "DIGEST47:3DES key ede: ", arrayOfByte3); traceOutput(DP_CLASS_NAME, "makeDesKeys", "DIGEST48:3DES key material: ", dESedeKeySpec.getKey()); logger.log(Level.FINEST, "DIGEST49: is parity-adjusted? ", Boolean.valueOf(DESedeKeySpec.isParityAdjusted(arrayOfByte3, 0))); }  return secretKeyFactory.generateSecret(dESedeKeySpec);
/*      */     } 
/*      */     throw new IllegalArgumentException("Invalid DES strength:" + paramString);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/digest/DigestMD5Base.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */