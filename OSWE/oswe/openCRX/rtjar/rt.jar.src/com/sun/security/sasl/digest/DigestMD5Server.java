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
/*     */ import javax.security.sasl.AuthorizeCallback;
/*     */ import javax.security.sasl.RealmCallback;
/*     */ import javax.security.sasl.SaslException;
/*     */ import javax.security.sasl.SaslServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DigestMD5Server
/*     */   extends DigestMD5Base
/*     */   implements SaslServer
/*     */ {
/*  87 */   private static final String MY_CLASS_NAME = DigestMD5Server.class.getName();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String UTF8_DIRECTIVE = "charset=utf-8,";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String ALGORITHM_DIRECTIVE = "algorithm=md5-sess";
/*     */ 
/*     */   
/*     */   private static final int NONCE_COUNT_VALUE = 1;
/*     */ 
/*     */   
/*     */   private static final String UTF8_PROPERTY = "com.sun.security.sasl.digest.utf8";
/*     */ 
/*     */   
/*     */   private static final String REALM_PROPERTY = "com.sun.security.sasl.digest.realm";
/*     */ 
/*     */   
/* 107 */   private static final String[] DIRECTIVE_KEY = new String[] { "username", "realm", "nonce", "cnonce", "nonce-count", "qop", "digest-uri", "response", "maxbuf", "charset", "cipher", "authzid", "auth-param" };
/*     */ 
/*     */   
/*     */   private static final int USERNAME = 0;
/*     */ 
/*     */   
/*     */   private static final int REALM = 1;
/*     */   
/*     */   private static final int NONCE = 2;
/*     */   
/*     */   private static final int CNONCE = 3;
/*     */   
/*     */   private static final int NONCE_COUNT = 4;
/*     */   
/*     */   private static final int QOP = 5;
/*     */   
/*     */   private static final int DIGEST_URI = 6;
/*     */   
/*     */   private static final int RESPONSE = 7;
/*     */   
/*     */   private static final int MAXBUF = 8;
/*     */   
/*     */   private static final int CHARSET = 9;
/*     */   
/*     */   private static final int CIPHER = 10;
/*     */   
/*     */   private static final int AUTHZID = 11;
/*     */   
/*     */   private static final int AUTH_PARAM = 12;
/*     */   
/*     */   private String specifiedQops;
/*     */   
/*     */   private byte[] myCiphers;
/*     */   
/*     */   private List<String> serverRealms;
/*     */ 
/*     */   
/*     */   DigestMD5Server(String paramString1, String paramString2, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/* 145 */     super(paramMap, MY_CLASS_NAME, 1, paramString1 + "/" + ((paramString2 == null) ? "*" : paramString2), paramCallbackHandler);
/*     */ 
/*     */ 
/*     */     
/* 149 */     this.serverRealms = new ArrayList<>();
/*     */     
/* 151 */     this.useUTF8 = true;
/*     */     
/* 153 */     if (paramMap != null) {
/* 154 */       this.specifiedQops = (String)paramMap.get("javax.security.sasl.qop");
/* 155 */       if ("false".equals(paramMap.get("com.sun.security.sasl.digest.utf8"))) {
/* 156 */         this.useUTF8 = false;
/* 157 */         logger.log(Level.FINE, "DIGEST80:Server supports ISO-Latin-1");
/*     */       } 
/*     */       
/* 160 */       String str = (String)paramMap.get("com.sun.security.sasl.digest.realm");
/* 161 */       if (str != null) {
/* 162 */         StringTokenizer stringTokenizer = new StringTokenizer(str, ", \t\n");
/* 163 */         int i = stringTokenizer.countTokens();
/* 164 */         String str1 = null;
/* 165 */         for (byte b = 0; b < i; b++) {
/* 166 */           str1 = stringTokenizer.nextToken();
/* 167 */           logger.log(Level.FINE, "DIGEST81:Server supports realm {0}", str1);
/*     */           
/* 169 */           this.serverRealms.add(str1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     this.encoding = this.useUTF8 ? "UTF8" : "8859_1";
/*     */ 
/*     */     
/* 177 */     if (this.serverRealms.isEmpty()) {
/* 178 */       if (paramString2 == null) {
/* 179 */         throw new SaslException("A realm must be provided in props or serverName");
/*     */       }
/*     */       
/* 182 */       this.serverRealms.add(paramString2);
/*     */     } 
/*     */   }
/*     */   public byte[] evaluateResponse(byte[] paramArrayOfbyte) throws SaslException {
/*     */     byte[] arrayOfByte;
/*     */     String str;
/* 188 */     if (paramArrayOfbyte.length > 4096) {
/* 189 */       throw new SaslException("DIGEST-MD5: Invalid digest response length. Got:  " + paramArrayOfbyte.length + " Expected < " + 'က');
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     switch (this.step) {
/*     */       case 1:
/* 197 */         if (paramArrayOfbyte.length != 0) {
/* 198 */           throw new SaslException("DIGEST-MD5 must not have an initial response");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 203 */         str = null;
/* 204 */         if ((this.allQop & 0x4) != 0) {
/* 205 */           this.myCiphers = getPlatformCiphers();
/* 206 */           StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */ 
/*     */           
/* 210 */           for (byte b = 0; b < CIPHER_TOKENS.length; b++) {
/* 211 */             if (this.myCiphers[b] != 0) {
/* 212 */               if (stringBuffer.length() > 0) {
/* 213 */                 stringBuffer.append(',');
/*     */               }
/* 215 */               stringBuffer.append(CIPHER_TOKENS[b]);
/*     */             } 
/*     */           } 
/* 218 */           str = stringBuffer.toString();
/*     */         } 
/*     */         
/*     */         try {
/* 222 */           arrayOfByte = generateChallenge(this.serverRealms, this.specifiedQops, str);
/*     */ 
/*     */           
/* 225 */           this.step = 3;
/* 226 */           return arrayOfByte;
/* 227 */         } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 228 */           throw new SaslException("DIGEST-MD5: Error encoding challenge", unsupportedEncodingException);
/*     */         }
/* 230 */         catch (IOException iOException) {
/* 231 */           throw new SaslException("DIGEST-MD5: Error generating challenge", iOException);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/*     */         try {
/* 242 */           byte[][] arrayOfByte1 = parseDirectives(paramArrayOfbyte, DIRECTIVE_KEY, (List<byte[]>)null, 1);
/*     */           
/* 244 */           arrayOfByte = validateClientResponse(arrayOfByte1);
/* 245 */         } catch (SaslException saslException) {
/* 246 */           throw saslException;
/* 247 */         } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 248 */           throw new SaslException("DIGEST-MD5: Error validating client response", unsupportedEncodingException);
/*     */         } finally {
/*     */           
/* 251 */           this.step = 0;
/*     */         } 
/*     */         
/* 254 */         this.completed = true;
/*     */ 
/*     */         
/* 257 */         if (this.integrity && this.privacy) {
/* 258 */           this.secCtx = new DigestMD5Base.DigestPrivacy(this, false);
/* 259 */         } else if (this.integrity) {
/* 260 */           this.secCtx = new DigestMD5Base.DigestIntegrity(this, false);
/*     */         } 
/*     */         
/* 263 */         return arrayOfByte;
/*     */     } 
/*     */ 
/*     */     
/* 267 */     throw new SaslException("DIGEST-MD5: Server at illegal state");
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
/*     */   
/*     */   private byte[] generateChallenge(List<String> paramList, String paramString1, String paramString2) throws UnsupportedEncodingException, IOException {
/* 297 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*     */ 
/*     */     
/* 300 */     for (byte b = 0; paramList != null && b < paramList.size(); b++) {
/* 301 */       byteArrayOutputStream.write("realm=\"".getBytes(this.encoding));
/* 302 */       writeQuotedStringValue(byteArrayOutputStream, ((String)paramList.get(b)).getBytes(this.encoding));
/* 303 */       byteArrayOutputStream.write(34);
/* 304 */       byteArrayOutputStream.write(44);
/*     */     } 
/*     */ 
/*     */     
/* 308 */     byteArrayOutputStream.write("nonce=\"".getBytes(this.encoding));
/* 309 */     this.nonce = generateNonce();
/* 310 */     writeQuotedStringValue(byteArrayOutputStream, this.nonce);
/* 311 */     byteArrayOutputStream.write(34);
/* 312 */     byteArrayOutputStream.write(44);
/*     */ 
/*     */ 
/*     */     
/* 316 */     if (paramString1 != null) {
/* 317 */       byteArrayOutputStream.write("qop=\"".getBytes(this.encoding));
/*     */       
/* 319 */       writeQuotedStringValue(byteArrayOutputStream, paramString1.getBytes(this.encoding));
/* 320 */       byteArrayOutputStream.write(34);
/* 321 */       byteArrayOutputStream.write(44);
/*     */     } 
/*     */ 
/*     */     
/* 325 */     if (this.recvMaxBufSize != 65536) {
/* 326 */       byteArrayOutputStream.write(("maxbuf=\"" + this.recvMaxBufSize + "\",").getBytes(this.encoding));
/*     */     }
/*     */ 
/*     */     
/* 330 */     if (this.useUTF8) {
/* 331 */       byteArrayOutputStream.write("charset=utf-8,".getBytes(this.encoding));
/*     */     }
/*     */     
/* 334 */     if (paramString2 != null) {
/* 335 */       byteArrayOutputStream.write("cipher=\"".getBytes(this.encoding));
/*     */       
/* 337 */       writeQuotedStringValue(byteArrayOutputStream, paramString2.getBytes(this.encoding));
/* 338 */       byteArrayOutputStream.write(34);
/* 339 */       byteArrayOutputStream.write(44);
/*     */     } 
/*     */ 
/*     */     
/* 343 */     byteArrayOutputStream.write("algorithm=md5-sess".getBytes(this.encoding));
/*     */     
/* 345 */     return byteArrayOutputStream.toByteArray();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] validateClientResponse(byte[][] paramArrayOfbyte) throws SaslException, UnsupportedEncodingException {
/*     */     String str1;
/*     */     byte b;
/*     */     char[] arrayOfChar;
/* 390 */     if (paramArrayOfbyte[9] != null)
/*     */     {
/*     */       
/* 393 */       if (!this.useUTF8 || 
/* 394 */         !"utf-8".equals(new String(paramArrayOfbyte[9], this.encoding))) {
/* 395 */         throw new SaslException("DIGEST-MD5: digest response format violation. Incompatible charset value: " + new String(paramArrayOfbyte[9]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 404 */     int i = (paramArrayOfbyte[8] == null) ? 65536 : Integer.parseInt(new String(paramArrayOfbyte[8], this.encoding));
/*     */ 
/*     */ 
/*     */     
/* 408 */     this
/* 409 */       .sendMaxBufSize = (this.sendMaxBufSize == 0) ? i : Math.min(this.sendMaxBufSize, i);
/*     */ 
/*     */ 
/*     */     
/* 413 */     if (paramArrayOfbyte[0] != null) {
/* 414 */       str1 = new String(paramArrayOfbyte[0], this.encoding);
/* 415 */       logger.log(Level.FINE, "DIGEST82:Username: {0}", str1);
/*     */     } else {
/* 417 */       throw new SaslException("DIGEST-MD5: digest response format violation. Missing username.");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 422 */     this.negotiatedRealm = (paramArrayOfbyte[1] != null) ? new String(paramArrayOfbyte[1], this.encoding) : "";
/*     */     
/* 424 */     logger.log(Level.FINE, "DIGEST83:Client negotiated realm: {0}", this.negotiatedRealm);
/*     */ 
/*     */     
/* 427 */     if (!this.serverRealms.contains(this.negotiatedRealm))
/*     */     {
/*     */       
/* 430 */       throw new SaslException("DIGEST-MD5: digest response format violation. Nonexistent realm: " + this.negotiatedRealm);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     if (paramArrayOfbyte[2] == null) {
/* 437 */       throw new SaslException("DIGEST-MD5: digest response format violation. Missing nonce.");
/*     */     }
/*     */     
/* 440 */     byte[] arrayOfByte1 = paramArrayOfbyte[2];
/* 441 */     if (!Arrays.equals(arrayOfByte1, this.nonce)) {
/* 442 */       throw new SaslException("DIGEST-MD5: digest response format violation. Mismatched nonce.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 447 */     if (paramArrayOfbyte[3] == null) {
/* 448 */       throw new SaslException("DIGEST-MD5: digest response format violation. Missing cnonce.");
/*     */     }
/*     */     
/* 451 */     byte[] arrayOfByte2 = paramArrayOfbyte[3];
/*     */ 
/*     */     
/* 454 */     if (paramArrayOfbyte[4] != null && 1 != 
/* 455 */       Integer.parseInt(new String(paramArrayOfbyte[4], this.encoding), 16))
/*     */     {
/* 457 */       throw new SaslException("DIGEST-MD5: digest response format violation. Nonce count does not match: " + new String(paramArrayOfbyte[4]));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 463 */     this.negotiatedQop = (paramArrayOfbyte[5] != null) ? new String(paramArrayOfbyte[5], this.encoding) : "auth";
/*     */ 
/*     */     
/* 466 */     logger.log(Level.FINE, "DIGEST84:Client negotiated qop: {0}", this.negotiatedQop);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 471 */     switch (this.negotiatedQop) {
/*     */       case "auth":
/* 473 */         b = 1;
/*     */         break;
/*     */       case "auth-int":
/* 476 */         b = 2;
/* 477 */         this.integrity = true;
/* 478 */         this.rawSendSize = this.sendMaxBufSize - 16;
/*     */         break;
/*     */       case "auth-conf":
/* 481 */         b = 4;
/* 482 */         this.integrity = this.privacy = true;
/* 483 */         this.rawSendSize = this.sendMaxBufSize - 26;
/*     */         break;
/*     */       default:
/* 486 */         throw new SaslException("DIGEST-MD5: digest response format violation. Invalid QOP: " + this.negotiatedQop);
/*     */     } 
/*     */     
/* 489 */     if ((b & this.allQop) == 0) {
/* 490 */       throw new SaslException("DIGEST-MD5: server does not support  qop: " + this.negotiatedQop);
/*     */     }
/*     */ 
/*     */     
/* 494 */     if (this.privacy) {
/* 495 */       this.negotiatedCipher = (paramArrayOfbyte[10] != null) ? new String(paramArrayOfbyte[10], this.encoding) : null;
/*     */       
/* 497 */       if (this.negotiatedCipher == null) {
/* 498 */         throw new SaslException("DIGEST-MD5: digest response format violation. No cipher specified.");
/*     */       }
/*     */ 
/*     */       
/* 502 */       byte b1 = -1;
/* 503 */       logger.log(Level.FINE, "DIGEST85:Client negotiated cipher: {0}", this.negotiatedCipher);
/*     */ 
/*     */ 
/*     */       
/* 507 */       for (byte b2 = 0; b2 < CIPHER_TOKENS.length; b2++) {
/* 508 */         if (this.negotiatedCipher.equals(CIPHER_TOKENS[b2]) && this.myCiphers[b2] != 0) {
/*     */           
/* 510 */           b1 = b2;
/*     */           break;
/*     */         } 
/*     */       } 
/* 514 */       if (b1 == -1) {
/* 515 */         throw new SaslException("DIGEST-MD5: server does not support cipher: " + this.negotiatedCipher);
/*     */       }
/*     */ 
/*     */       
/* 519 */       if ((CIPHER_MASKS[b1] & 0x4) != 0) {
/* 520 */         this.negotiatedStrength = "high";
/* 521 */       } else if ((CIPHER_MASKS[b1] & 0x2) != 0) {
/* 522 */         this.negotiatedStrength = "medium";
/*     */       } else {
/*     */         
/* 525 */         this.negotiatedStrength = "low";
/*     */       } 
/*     */       
/* 528 */       logger.log(Level.FINE, "DIGEST86:Negotiated strength: {0}", this.negotiatedStrength);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 533 */     String str2 = (paramArrayOfbyte[6] != null) ? new String(paramArrayOfbyte[6], this.encoding) : null;
/*     */ 
/*     */     
/* 536 */     if (str2 != null) {
/* 537 */       logger.log(Level.FINE, "DIGEST87:digest URI: {0}", str2);
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
/* 549 */     if (uriMatches(this.digestUri, str2)) {
/* 550 */       this.digestUri = str2;
/*     */     } else {
/* 552 */       throw new SaslException("DIGEST-MD5: digest response format violation. Mismatched URI: " + str2 + "; expecting: " + this.digestUri);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 558 */     byte[] arrayOfByte3 = paramArrayOfbyte[7];
/* 559 */     if (arrayOfByte3 == null) {
/* 560 */       throw new SaslException("DIGEST-MD5: digest response format  violation. Missing response.");
/*     */     }
/*     */ 
/*     */     
/*     */     byte[] arrayOfByte4;
/*     */     
/* 566 */     String str3 = ((arrayOfByte4 = paramArrayOfbyte[11]) != null) ? new String(arrayOfByte4, this.encoding) : str1;
/*     */ 
/*     */     
/* 569 */     if (arrayOfByte4 != null) {
/* 570 */       logger.log(Level.FINE, "DIGEST88:Authzid: {0}", new String(arrayOfByte4));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 580 */       RealmCallback realmCallback = new RealmCallback("DIGEST-MD5 realm: ", this.negotiatedRealm);
/*     */       
/* 582 */       NameCallback nameCallback = new NameCallback("DIGEST-MD5 authentication ID: ", str1);
/*     */ 
/*     */ 
/*     */       
/* 586 */       PasswordCallback passwordCallback = new PasswordCallback("DIGEST-MD5 password: ", false);
/*     */ 
/*     */       
/* 589 */       this.cbh.handle(new Callback[] { realmCallback, nameCallback, passwordCallback });
/* 590 */       arrayOfChar = passwordCallback.getPassword();
/* 591 */       passwordCallback.clearPassword();
/*     */     }
/* 593 */     catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 594 */       throw new SaslException("DIGEST-MD5: Cannot perform callback to acquire password", unsupportedCallbackException);
/*     */     
/*     */     }
/* 597 */     catch (IOException iOException) {
/* 598 */       throw new SaslException("DIGEST-MD5: IO error acquiring password", iOException);
/*     */     } 
/*     */ 
/*     */     
/* 602 */     if (arrayOfChar == null) {
/* 603 */       throw new SaslException("DIGEST-MD5: cannot acquire password for " + str1 + " in realm : " + this.negotiatedRealm);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*     */       byte[] arrayOfByte;
/*     */ 
/*     */       
/*     */       try {
/* 613 */         arrayOfByte = generateResponseValue("AUTHENTICATE", this.digestUri, this.negotiatedQop, str1, this.negotiatedRealm, arrayOfChar, this.nonce, arrayOfByte2, 1, arrayOfByte4);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 618 */       catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 619 */         throw new SaslException("DIGEST-MD5: problem duplicating client response", noSuchAlgorithmException);
/*     */       }
/* 621 */       catch (IOException iOException) {
/* 622 */         throw new SaslException("DIGEST-MD5: problem duplicating client response", iOException);
/*     */       } 
/*     */ 
/*     */       
/* 626 */       if (!Arrays.equals(arrayOfByte3, arrayOfByte)) {
/* 627 */         throw new SaslException("DIGEST-MD5: digest response format violation. Mismatched response.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 633 */         AuthorizeCallback authorizeCallback = new AuthorizeCallback(str1, str3);
/*     */         
/* 635 */         this.cbh.handle(new Callback[] { authorizeCallback });
/*     */         
/* 637 */         if (authorizeCallback.isAuthorized()) {
/* 638 */           this.authzid = authorizeCallback.getAuthorizedID();
/*     */         } else {
/* 640 */           throw new SaslException("DIGEST-MD5: " + str1 + " is not authorized to act as " + str3);
/*     */         }
/*     */       
/* 643 */       } catch (SaslException saslException) {
/* 644 */         throw saslException;
/* 645 */       } catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 646 */         throw new SaslException("DIGEST-MD5: Cannot perform callback to check authzid", unsupportedCallbackException);
/*     */       }
/* 648 */       catch (IOException iOException) {
/* 649 */         throw new SaslException("DIGEST-MD5: IO error checking authzid", iOException);
/*     */       } 
/*     */ 
/*     */       
/* 653 */       return generateResponseAuth(str1, arrayOfChar, arrayOfByte2, 1, arrayOfByte4);
/*     */     }
/*     */     finally {
/*     */       
/* 657 */       for (byte b1 = 0; b1 < arrayOfChar.length; b1++) {
/* 658 */         arrayOfChar[b1] = Character.MIN_VALUE;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean uriMatches(String paramString1, String paramString2) {
/* 665 */     if (paramString1.equalsIgnoreCase(paramString2)) {
/* 666 */       return true;
/*     */     }
/*     */     
/* 669 */     if (paramString1.endsWith("/*")) {
/* 670 */       int i = paramString1.length() - 1;
/* 671 */       String str1 = paramString1.substring(0, i);
/* 672 */       String str2 = paramString2.substring(0, i);
/* 673 */       return str1.equalsIgnoreCase(str2);
/*     */     } 
/* 675 */     return false;
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
/*     */   private byte[] generateResponseAuth(String paramString, char[] paramArrayOfchar, byte[] paramArrayOfbyte1, int paramInt, byte[] paramArrayOfbyte2) throws SaslException {
/*     */     try {
/* 698 */       byte[] arrayOfByte1 = generateResponseValue("", this.digestUri, this.negotiatedQop, paramString, this.negotiatedRealm, paramArrayOfchar, this.nonce, paramArrayOfbyte1, paramInt, paramArrayOfbyte2);
/*     */ 
/*     */ 
/*     */       
/* 702 */       byte[] arrayOfByte2 = new byte[arrayOfByte1.length + 8];
/* 703 */       System.arraycopy("rspauth=".getBytes(this.encoding), 0, arrayOfByte2, 0, 8);
/* 704 */       System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 8, arrayOfByte1.length);
/*     */ 
/*     */       
/* 707 */       return arrayOfByte2;
/*     */     }
/* 709 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 710 */       throw new SaslException("DIGEST-MD5: problem generating response", noSuchAlgorithmException);
/* 711 */     } catch (IOException iOException) {
/* 712 */       throw new SaslException("DIGEST-MD5: problem generating response", iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getAuthorizationID() {
/* 717 */     if (this.completed) {
/* 718 */       return this.authzid;
/*     */     }
/* 720 */     throw new IllegalStateException("DIGEST-MD5 server negotiation not complete");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/digest/DigestMD5Server.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */