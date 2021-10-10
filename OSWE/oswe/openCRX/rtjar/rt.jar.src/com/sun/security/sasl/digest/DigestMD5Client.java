/*     */ package com.sun.security.sasl.digest;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.NameCallback;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.security.sasl.RealmCallback;
/*     */ import javax.security.sasl.RealmChoiceCallback;
/*     */ import javax.security.sasl.SaslClient;
/*     */ import javax.security.sasl.SaslException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DigestMD5Client
/*     */   extends DigestMD5Base
/*     */   implements SaslClient
/*     */ {
/*  99 */   private static final String MY_CLASS_NAME = DigestMD5Client.class.getName();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String CIPHER_PROPERTY = "com.sun.security.sasl.digest.cipher";
/*     */ 
/*     */   
/* 106 */   private static final String[] DIRECTIVE_KEY = new String[] { "realm", "qop", "algorithm", "nonce", "maxbuf", "charset", "cipher", "rspauth", "stale" };
/*     */ 
/*     */   
/*     */   private static final int REALM = 0;
/*     */ 
/*     */   
/*     */   private static final int QOP = 1;
/*     */ 
/*     */   
/*     */   private static final int ALGORITHM = 2;
/*     */ 
/*     */   
/*     */   private static final int NONCE = 3;
/*     */ 
/*     */   
/*     */   private static final int MAXBUF = 4;
/*     */ 
/*     */   
/*     */   private static final int CHARSET = 5;
/*     */ 
/*     */   
/*     */   private static final int CIPHER = 6;
/*     */ 
/*     */   
/*     */   private static final int RESPONSE_AUTH = 7;
/*     */ 
/*     */   
/*     */   private static final int STALE = 8;
/*     */ 
/*     */   
/*     */   private int nonceCount;
/*     */ 
/*     */   
/*     */   private String specifiedCipher;
/*     */ 
/*     */   
/*     */   private byte[] cnonce;
/*     */ 
/*     */   
/*     */   private String username;
/*     */   
/*     */   private char[] passwd;
/*     */   
/*     */   private byte[] authzidBytes;
/*     */ 
/*     */   
/*     */   DigestMD5Client(String paramString1, String paramString2, String paramString3, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/* 153 */     super(paramMap, MY_CLASS_NAME, 2, paramString2 + "/" + paramString3, paramCallbackHandler);
/*     */ 
/*     */     
/* 156 */     if (paramString1 != null) {
/* 157 */       this.authzid = paramString1;
/*     */       try {
/* 159 */         this.authzidBytes = paramString1.getBytes("UTF8");
/*     */       }
/* 161 */       catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 162 */         throw new SaslException("DIGEST-MD5: Error encoding authzid value into UTF-8", unsupportedEncodingException);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 167 */     if (paramMap != null) {
/* 168 */       this.specifiedCipher = (String)paramMap.get("com.sun.security.sasl.digest.cipher");
/*     */       
/* 170 */       logger.log(Level.FINE, "DIGEST60:Explicitly specified cipher: {0}", this.specifiedCipher);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasInitialResponse() {
/* 181 */     return false;
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
/*     */   public byte[] evaluateChallenge(byte[] paramArrayOfbyte) throws SaslException {
/*     */     byte[][] arrayOfByte;
/*     */     ArrayList<byte[]> arrayList;
/* 201 */     if (paramArrayOfbyte.length > 2048) {
/* 202 */       throw new SaslException("DIGEST-MD5: Invalid digest-challenge length. Got:  " + paramArrayOfbyte.length + " Expected < " + 'ࠀ');
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     switch (this.step) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 215 */         arrayList = new ArrayList(3);
/* 216 */         arrayOfByte = parseDirectives(paramArrayOfbyte, DIRECTIVE_KEY, (List<byte[]>)arrayList, 0);
/*     */ 
/*     */         
/*     */         try {
/* 220 */           processChallenge(arrayOfByte, (List<byte[]>)arrayList);
/* 221 */           checkQopSupport(arrayOfByte[1], arrayOfByte[6]);
/* 222 */           this.step++;
/* 223 */           return generateClientResponse(arrayOfByte[5]);
/* 224 */         } catch (SaslException saslException) {
/* 225 */           this.step = 0;
/* 226 */           clearPassword();
/* 227 */           throw saslException;
/* 228 */         } catch (IOException iOException) {
/* 229 */           this.step = 0;
/* 230 */           clearPassword();
/* 231 */           throw new SaslException("DIGEST-MD5: Error generating digest response-value", iOException);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/*     */         try {
/* 239 */           arrayOfByte = parseDirectives(paramArrayOfbyte, DIRECTIVE_KEY, (List<byte[]>)null, 0);
/*     */           
/* 241 */           validateResponseValue(arrayOfByte[7]);
/*     */ 
/*     */ 
/*     */           
/* 245 */           if (this.integrity && this.privacy) {
/* 246 */             this.secCtx = new DigestMD5Base.DigestPrivacy(this, true);
/* 247 */           } else if (this.integrity) {
/* 248 */             this.secCtx = new DigestMD5Base.DigestIntegrity(this, true);
/*     */           } 
/*     */           
/* 251 */           return null;
/*     */         } finally {
/* 253 */           clearPassword();
/* 254 */           this.step = 0;
/* 255 */           this.completed = true;
/*     */         } 
/*     */     } 
/*     */ 
/*     */     
/* 260 */     throw new SaslException("DIGEST-MD5: Client at illegal state");
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
/*     */   private void processChallenge(byte[][] paramArrayOfbyte, List<byte[]> paramList) throws SaslException, UnsupportedEncodingException {
/* 278 */     if (paramArrayOfbyte[5] != null) {
/* 279 */       if (!"utf-8".equals(new String(paramArrayOfbyte[5], this.encoding))) {
/* 280 */         throw new SaslException("DIGEST-MD5: digest-challenge format violation. Unrecognised charset value: " + new String(paramArrayOfbyte[5]));
/*     */       }
/*     */ 
/*     */       
/* 284 */       this.encoding = "UTF8";
/* 285 */       this.useUTF8 = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 290 */     if (paramArrayOfbyte[2] == null) {
/* 291 */       throw new SaslException("DIGEST-MD5: Digest-challenge format violation: algorithm directive missing");
/*     */     }
/* 293 */     if (!"md5-sess".equals(new String(paramArrayOfbyte[2], this.encoding))) {
/* 294 */       throw new SaslException("DIGEST-MD5: Digest-challenge format violation. Invalid value for 'algorithm' directive: " + paramArrayOfbyte[2]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     if (paramArrayOfbyte[3] == null) {
/* 301 */       throw new SaslException("DIGEST-MD5: Digest-challenge format violation: nonce directive missing");
/*     */     }
/*     */     
/* 304 */     this.nonce = paramArrayOfbyte[3];
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 309 */       String[] arrayOfString = null;
/*     */       
/* 311 */       if (paramArrayOfbyte[0] != null) {
/* 312 */         if (paramList == null || paramList.size() <= 1) {
/*     */           
/* 314 */           this.negotiatedRealm = new String(paramArrayOfbyte[0], this.encoding);
/*     */         } else {
/* 316 */           arrayOfString = new String[paramList.size()];
/* 317 */           for (byte b = 0; b < arrayOfString.length; b++) {
/* 318 */             arrayOfString[b] = new String(paramList
/* 319 */                 .get(b), this.encoding);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 324 */       NameCallback nameCallback = (this.authzid == null) ? new NameCallback("DIGEST-MD5 authentication ID: ") : new NameCallback("DIGEST-MD5 authentication ID: ", this.authzid);
/*     */ 
/*     */       
/* 327 */       PasswordCallback passwordCallback = new PasswordCallback("DIGEST-MD5 password: ", false);
/*     */ 
/*     */       
/* 330 */       if (arrayOfString == null) {
/*     */ 
/*     */         
/* 333 */         RealmCallback realmCallback = (this.negotiatedRealm == null) ? new RealmCallback("DIGEST-MD5 realm: ") : new RealmCallback("DIGEST-MD5 realm: ", this.negotiatedRealm);
/*     */ 
/*     */ 
/*     */         
/* 337 */         this.cbh.handle(new Callback[] { realmCallback, nameCallback, passwordCallback });
/*     */ 
/*     */         
/* 340 */         this.negotiatedRealm = realmCallback.getText();
/* 341 */         if (this.negotiatedRealm == null) {
/* 342 */           this.negotiatedRealm = "";
/*     */         }
/*     */       } else {
/* 345 */         RealmChoiceCallback realmChoiceCallback = new RealmChoiceCallback("DIGEST-MD5 realm: ", arrayOfString, 0, false);
/*     */ 
/*     */ 
/*     */         
/* 349 */         this.cbh.handle(new Callback[] { realmChoiceCallback, nameCallback, passwordCallback });
/*     */ 
/*     */         
/* 352 */         int[] arrayOfInt = realmChoiceCallback.getSelectedIndexes();
/* 353 */         if (arrayOfInt == null || arrayOfInt[0] < 0 || arrayOfInt[0] >= arrayOfString.length)
/*     */         {
/*     */           
/* 356 */           throw new SaslException("DIGEST-MD5: Invalid realm chosen");
/*     */         }
/* 358 */         this.negotiatedRealm = arrayOfString[arrayOfInt[0]];
/*     */       } 
/*     */       
/* 361 */       this.passwd = passwordCallback.getPassword();
/* 362 */       passwordCallback.clearPassword();
/* 363 */       this.username = nameCallback.getName();
/*     */     }
/* 365 */     catch (SaslException saslException) {
/* 366 */       throw saslException;
/*     */     }
/* 368 */     catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 369 */       throw new SaslException("DIGEST-MD5: Cannot perform callback to acquire realm, authentication ID or password", unsupportedCallbackException);
/*     */     
/*     */     }
/* 372 */     catch (IOException iOException) {
/* 373 */       throw new SaslException("DIGEST-MD5: Error acquiring realm, authentication ID or password", iOException);
/*     */     } 
/*     */ 
/*     */     
/* 377 */     if (this.username == null || this.passwd == null) {
/* 378 */       throw new SaslException("DIGEST-MD5: authentication ID and password must be specified");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     int i = (paramArrayOfbyte[4] == null) ? 65536 : Integer.parseInt(new String(paramArrayOfbyte[4], this.encoding));
/* 386 */     this
/*     */       
/* 388 */       .sendMaxBufSize = (this.sendMaxBufSize == 0) ? i : Math.min(this.sendMaxBufSize, i);
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
/*     */   private void checkQopSupport(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws IOException {
/*     */     String str;
/* 404 */     if (paramArrayOfbyte1 == null) {
/* 405 */       str = "auth";
/*     */     } else {
/* 407 */       str = new String(paramArrayOfbyte1, this.encoding);
/*     */     } 
/*     */ 
/*     */     
/* 411 */     String[] arrayOfString = new String[3];
/* 412 */     byte[] arrayOfByte = parseQop(str, arrayOfString, true);
/*     */     
/* 414 */     byte b = combineMasks(arrayOfByte);
/*     */     
/* 416 */     switch (findPreferredMask(b, this.qop)) {
/*     */       case 0:
/* 418 */         throw new SaslException("DIGEST-MD5: No common protection layer between client and server");
/*     */ 
/*     */       
/*     */       case 1:
/* 422 */         this.negotiatedQop = "auth";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 427 */         this.negotiatedQop = "auth-int";
/* 428 */         this.integrity = true;
/* 429 */         this.rawSendSize = this.sendMaxBufSize - 16;
/*     */         break;
/*     */       
/*     */       case 4:
/* 433 */         this.negotiatedQop = "auth-conf";
/* 434 */         this.privacy = this.integrity = true;
/* 435 */         this.rawSendSize = this.sendMaxBufSize - 26;
/* 436 */         checkStrengthSupport(paramArrayOfbyte2);
/*     */         break;
/*     */     } 
/*     */     
/* 440 */     if (logger.isLoggable(Level.FINE)) {
/* 441 */       logger.log(Level.FINE, "DIGEST61:Raw send size: {0}", new Integer(this.rawSendSize));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkStrengthSupport(byte[] paramArrayOfbyte) throws IOException {
/* 460 */     if (paramArrayOfbyte == null) {
/* 461 */       throw new SaslException("DIGEST-MD5: server did not specify cipher to use for 'auth-conf'");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 466 */     String str1 = new String(paramArrayOfbyte, this.encoding);
/* 467 */     StringTokenizer stringTokenizer = new StringTokenizer(str1, ", \t\n");
/* 468 */     int i = stringTokenizer.countTokens();
/* 469 */     String str2 = null;
/* 470 */     byte[] arrayOfByte1 = { 0, 0, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 475 */     String[] arrayOfString = new String[arrayOfByte1.length];
/*     */ 
/*     */     
/* 478 */     for (byte b1 = 0; b1 < i; b1++) {
/* 479 */       str2 = stringTokenizer.nextToken();
/* 480 */       for (byte b3 = 0; b3 < CIPHER_TOKENS.length; b3++) {
/* 481 */         if (str2.equals(CIPHER_TOKENS[b3])) {
/* 482 */           arrayOfByte1[b3] = (byte)(arrayOfByte1[b3] | CIPHER_MASKS[b3]);
/* 483 */           arrayOfString[b3] = str2;
/* 484 */           logger.log(Level.FINE, "DIGEST62:Server supports {0}", str2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 490 */     byte[] arrayOfByte2 = getPlatformCiphers();
/*     */ 
/*     */     
/* 493 */     byte b = 0;
/* 494 */     for (byte b2 = 0; b2 < arrayOfByte1.length; b2++) {
/* 495 */       arrayOfByte1[b2] = (byte)(arrayOfByte1[b2] & arrayOfByte2[b2]);
/* 496 */       b = (byte)(b | arrayOfByte1[b2]);
/*     */     } 
/*     */     
/* 499 */     if (b == 0) {
/* 500 */       throw new SaslException("DIGEST-MD5: Client supports none of these cipher suites: " + str1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 508 */     this.negotiatedCipher = findCipherAndStrength(arrayOfByte1, arrayOfString);
/*     */     
/* 510 */     if (this.negotiatedCipher == null) {
/* 511 */       throw new SaslException("DIGEST-MD5: Unable to negotiate a strength level for 'auth-conf'");
/*     */     }
/*     */     
/* 514 */     logger.log(Level.FINE, "DIGEST63:Cipher suite: {0}", this.negotiatedCipher);
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
/*     */   private String findCipherAndStrength(byte[] paramArrayOfbyte, String[] paramArrayOfString) {
/* 529 */     for (byte b = 0; b < this.strength.length; b++) {
/* 530 */       byte b1; if ((b1 = this.strength[b]) != 0) {
/* 531 */         for (byte b2 = 0; b2 < paramArrayOfbyte.length; b2++) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 536 */           if (b1 == paramArrayOfbyte[b2] && (this.specifiedCipher == null || this.specifiedCipher
/*     */             
/* 538 */             .equals(paramArrayOfString[b2]))) {
/* 539 */             switch (b1) {
/*     */               case 4:
/* 541 */                 this.negotiatedStrength = "high";
/*     */                 break;
/*     */               case 2:
/* 544 */                 this.negotiatedStrength = "medium";
/*     */                 break;
/*     */               case 1:
/* 547 */                 this.negotiatedStrength = "low";
/*     */                 break;
/*     */             } 
/*     */             
/* 551 */             return paramArrayOfString[b2];
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 557 */     return null;
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
/*     */   private byte[] generateClientResponse(byte[] paramArrayOfbyte) throws IOException {
/* 576 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*     */     
/* 578 */     if (this.useUTF8) {
/* 579 */       byteArrayOutputStream.write("charset=".getBytes(this.encoding));
/* 580 */       byteArrayOutputStream.write(paramArrayOfbyte);
/* 581 */       byteArrayOutputStream.write(44);
/*     */     } 
/*     */     
/* 584 */     byteArrayOutputStream.write(("username=\"" + 
/* 585 */         quotedStringValue(this.username) + "\",").getBytes(this.encoding));
/*     */     
/* 587 */     if (this.negotiatedRealm.length() > 0) {
/* 588 */       byteArrayOutputStream.write(("realm=\"" + 
/* 589 */           quotedStringValue(this.negotiatedRealm) + "\",").getBytes(this.encoding));
/*     */     }
/*     */     
/* 592 */     byteArrayOutputStream.write("nonce=\"".getBytes(this.encoding));
/* 593 */     writeQuotedStringValue(byteArrayOutputStream, this.nonce);
/* 594 */     byteArrayOutputStream.write(34);
/* 595 */     byteArrayOutputStream.write(44);
/*     */     
/* 597 */     this.nonceCount = getNonceCount(this.nonce);
/* 598 */     byteArrayOutputStream.write(("nc=" + 
/* 599 */         nonceCountToHex(this.nonceCount) + ",").getBytes(this.encoding));
/*     */     
/* 601 */     this.cnonce = generateNonce();
/* 602 */     byteArrayOutputStream.write("cnonce=\"".getBytes(this.encoding));
/* 603 */     writeQuotedStringValue(byteArrayOutputStream, this.cnonce);
/* 604 */     byteArrayOutputStream.write("\",".getBytes(this.encoding));
/* 605 */     byteArrayOutputStream.write(("digest-uri=\"" + this.digestUri + "\",").getBytes(this.encoding));
/*     */     
/* 607 */     byteArrayOutputStream.write("maxbuf=".getBytes(this.encoding));
/* 608 */     byteArrayOutputStream.write(String.valueOf(this.recvMaxBufSize).getBytes(this.encoding));
/* 609 */     byteArrayOutputStream.write(44);
/*     */     
/*     */     try {
/* 612 */       byteArrayOutputStream.write("response=".getBytes(this.encoding));
/* 613 */       byteArrayOutputStream.write(generateResponseValue("AUTHENTICATE", this.digestUri, this.negotiatedQop, this.username, this.negotiatedRealm, this.passwd, this.nonce, this.cnonce, this.nonceCount, this.authzidBytes));
/*     */ 
/*     */ 
/*     */       
/* 617 */       byteArrayOutputStream.write(44);
/* 618 */     } catch (Exception exception) {
/* 619 */       throw new SaslException("DIGEST-MD5: Error generating response value", exception);
/*     */     } 
/*     */ 
/*     */     
/* 623 */     byteArrayOutputStream.write(("qop=" + this.negotiatedQop).getBytes(this.encoding));
/*     */     
/* 625 */     if (this.negotiatedCipher != null) {
/* 626 */       byteArrayOutputStream.write((",cipher=\"" + this.negotiatedCipher + "\"").getBytes(this.encoding));
/*     */     }
/*     */     
/* 629 */     if (this.authzidBytes != null) {
/* 630 */       byteArrayOutputStream.write(",authzid=\"".getBytes(this.encoding));
/* 631 */       writeQuotedStringValue(byteArrayOutputStream, this.authzidBytes);
/* 632 */       byteArrayOutputStream.write("\"".getBytes(this.encoding));
/*     */     } 
/*     */     
/* 635 */     if (byteArrayOutputStream.size() > 4096) {
/* 636 */       throw new SaslException("DIGEST-MD5: digest-response size too large. Length: " + byteArrayOutputStream
/* 637 */           .size());
/*     */     }
/* 639 */     return byteArrayOutputStream.toByteArray();
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
/*     */   private void validateResponseValue(byte[] paramArrayOfbyte) throws SaslException {
/* 657 */     if (paramArrayOfbyte == null) {
/* 658 */       throw new SaslException("DIGEST-MD5: Authenication failed. Expecting 'rspauth' authentication success message");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 663 */       byte[] arrayOfByte = generateResponseValue("", this.digestUri, this.negotiatedQop, this.username, this.negotiatedRealm, this.passwd, this.nonce, this.cnonce, this.nonceCount, this.authzidBytes);
/*     */ 
/*     */       
/* 666 */       if (!Arrays.equals(arrayOfByte, paramArrayOfbyte))
/*     */       {
/* 668 */         throw new SaslException("Server's rspauth value does not match what client expects");
/*     */       }
/*     */     }
/* 671 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 672 */       throw new SaslException("Problem generating response value for verification", noSuchAlgorithmException);
/*     */     }
/* 674 */     catch (IOException iOException) {
/* 675 */       throw new SaslException("Problem generating response value for verification", iOException);
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
/*     */   private static int getNonceCount(byte[] paramArrayOfbyte) {
/* 689 */     return 1;
/*     */   }
/*     */   
/*     */   private void clearPassword() {
/* 693 */     if (this.passwd != null) {
/* 694 */       for (byte b = 0; b < this.passwd.length; b++) {
/* 695 */         this.passwd[b] = Character.MIN_VALUE;
/*     */       }
/* 697 */       this.passwd = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/digest/DigestMD5Client.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */