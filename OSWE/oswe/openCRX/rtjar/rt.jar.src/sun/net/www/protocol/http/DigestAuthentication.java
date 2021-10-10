/*     */ package sun.net.www.protocol.http;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.PasswordAuthentication;
/*     */ import java.net.ProtocolException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import java.util.StringTokenizer;
/*     */ import sun.net.NetProperties;
/*     */ import sun.net.www.HeaderParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DigestAuthentication
/*     */   extends AuthenticationInfo
/*     */ {
/*     */   private static final long serialVersionUID = 100L;
/*     */   private String authMethod;
/*     */   private static final String compatPropName = "http.auth.digest.quoteParameters";
/*     */   private static final boolean delimCompatFlag;
/*     */   Parameters params;
/*     */   
/*     */   static {
/*  64 */     Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */         {
/*     */           public Boolean run() {
/*  67 */             return NetProperties.getBoolean("http.auth.digest.quoteParameters");
/*     */           }
/*     */         });
/*     */     
/*  71 */     delimCompatFlag = (bool == null) ? false : bool.booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   static class Parameters
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -3584543755194526252L;
/*     */     
/*     */     private boolean serverQop;
/*     */     
/*     */     private String opaque;
/*     */     private String cnonce;
/*     */     private String nonce;
/*     */     private String algorithm;
/*  86 */     private int NCcount = 0;
/*     */ 
/*     */     
/*     */     private String cachedHA1;
/*     */ 
/*     */     
/*     */     private boolean redoCachedHA1 = true;
/*     */ 
/*     */     
/*     */     private static final int cnonceRepeat = 5;
/*     */ 
/*     */     
/*     */     private static final int cnoncelen = 40;
/*     */ 
/*     */     
/* 101 */     private static Random random = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int cnonce_count;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean authQop() {
/* 114 */       return this.serverQop;
/*     */     }
/*     */     synchronized void incrementNC() {
/* 117 */       this.NCcount++;
/*     */     }
/*     */     synchronized int getNCCount() {
/* 120 */       return this.NCcount;
/*     */     }
/*     */     
/* 123 */     Parameters() { this.cnonce_count = 0; this.serverQop = false; this.opaque = null;
/*     */       this.algorithm = null;
/*     */       this.cachedHA1 = null;
/*     */       this.nonce = null;
/* 127 */       setNewCnonce(); } synchronized String getCnonce() { if (this.cnonce_count >= 5) {
/* 128 */         setNewCnonce();
/*     */       }
/* 130 */       this.cnonce_count++;
/* 131 */       return this.cnonce; }
/*     */     
/*     */     synchronized void setNewCnonce() {
/* 134 */       byte[] arrayOfByte = new byte[20];
/* 135 */       char[] arrayOfChar = new char[40];
/* 136 */       random.nextBytes(arrayOfByte);
/* 137 */       for (byte b = 0; b < 20; b++) {
/* 138 */         int i = arrayOfByte[b] + 128;
/* 139 */         arrayOfChar[b * 2] = (char)(65 + i / 16);
/* 140 */         arrayOfChar[b * 2 + 1] = (char)(65 + i % 16);
/*     */       } 
/* 142 */       this.cnonce = new String(arrayOfChar, 0, 40);
/* 143 */       this.cnonce_count = 0;
/* 144 */       this.redoCachedHA1 = true;
/*     */     }
/*     */     
/*     */     synchronized void setQop(String param1String) {
/* 148 */       if (param1String != null) {
/* 149 */         StringTokenizer stringTokenizer = new StringTokenizer(param1String, " ");
/* 150 */         while (stringTokenizer.hasMoreTokens()) {
/* 151 */           if (stringTokenizer.nextToken().equalsIgnoreCase("auth")) {
/* 152 */             this.serverQop = true;
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/* 157 */       this.serverQop = false;
/*     */     }
/*     */     
/* 160 */     synchronized String getOpaque() { return this.opaque; } synchronized void setOpaque(String param1String) {
/* 161 */       this.opaque = param1String;
/*     */     } synchronized String getNonce() {
/* 163 */       return this.nonce;
/*     */     }
/*     */     synchronized void setNonce(String param1String) {
/* 166 */       if (!param1String.equals(this.nonce)) {
/* 167 */         this.nonce = param1String;
/* 168 */         this.NCcount = 0;
/* 169 */         this.redoCachedHA1 = true;
/*     */       } 
/*     */     }
/*     */     
/*     */     synchronized String getCachedHA1() {
/* 174 */       if (this.redoCachedHA1) {
/* 175 */         return null;
/*     */       }
/* 177 */       return this.cachedHA1;
/*     */     }
/*     */ 
/*     */     
/*     */     synchronized void setCachedHA1(String param1String) {
/* 182 */       this.cachedHA1 = param1String;
/* 183 */       this.redoCachedHA1 = false;
/*     */     }
/*     */     
/* 186 */     synchronized String getAlgorithm() { return this.algorithm; } synchronized void setAlgorithm(String param1String) {
/* 187 */       this.algorithm = param1String;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigestAuthentication(boolean paramBoolean, URL paramURL, String paramString1, String paramString2, PasswordAuthentication paramPasswordAuthentication, Parameters paramParameters) {
/* 198 */     super(paramBoolean ? 112 : 115, AuthScheme.DIGEST, paramURL, paramString1);
/*     */ 
/*     */ 
/*     */     
/* 202 */     this.authMethod = paramString2;
/* 203 */     this.pw = paramPasswordAuthentication;
/* 204 */     this.params = paramParameters;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DigestAuthentication(boolean paramBoolean, String paramString1, int paramInt, String paramString2, String paramString3, PasswordAuthentication paramPasswordAuthentication, Parameters paramParameters) {
/* 210 */     super(paramBoolean ? 112 : 115, AuthScheme.DIGEST, paramString1, paramInt, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     this.authMethod = paramString3;
/* 216 */     this.pw = paramPasswordAuthentication;
/* 217 */     this.params = paramParameters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsPreemptiveAuthorization() {
/* 225 */     return true;
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
/*     */   public String getHeaderValue(URL paramURL, String paramString) {
/* 244 */     return getHeaderValueImpl(paramURL.getFile(), paramString);
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
/*     */   String getHeaderValue(String paramString1, String paramString2) {
/* 262 */     return getHeaderValueImpl(paramString1, paramString2);
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
/*     */   public boolean isAuthorizationStale(String paramString) {
/* 275 */     HeaderParser headerParser = new HeaderParser(paramString);
/* 276 */     String str1 = headerParser.findValue("stale");
/* 277 */     if (str1 == null || !str1.equals("true"))
/* 278 */       return false; 
/* 279 */     String str2 = headerParser.findValue("nonce");
/* 280 */     if (str2 == null || "".equals(str2)) {
/* 281 */       return false;
/*     */     }
/* 283 */     this.params.setNonce(str2);
/* 284 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setHeaders(HttpURLConnection paramHttpURLConnection, HeaderParser paramHeaderParser, String paramString) {
/*     */     String str2;
/* 296 */     this.params.setNonce(paramHeaderParser.findValue("nonce"));
/* 297 */     this.params.setOpaque(paramHeaderParser.findValue("opaque"));
/* 298 */     this.params.setQop(paramHeaderParser.findValue("qop"));
/*     */     
/* 300 */     String str1 = "";
/*     */     
/* 302 */     if (this.type == 'p' && paramHttpURLConnection
/* 303 */       .tunnelState() == HttpURLConnection.TunnelState.SETUP) {
/* 304 */       str1 = HttpURLConnection.connectRequestURI(paramHttpURLConnection.getURL());
/* 305 */       str2 = HttpURLConnection.HTTP_CONNECT;
/*     */     } else {
/*     */       try {
/* 308 */         str1 = paramHttpURLConnection.getRequestURI();
/* 309 */       } catch (IOException iOException) {}
/* 310 */       str2 = paramHttpURLConnection.getMethod();
/*     */     } 
/*     */     
/* 313 */     if (this.params.nonce == null || this.authMethod == null || this.pw == null || this.realm == null) {
/* 314 */       return false;
/*     */     }
/* 316 */     if (this.authMethod.length() >= 1)
/*     */     {
/*     */ 
/*     */       
/* 320 */       this
/* 321 */         .authMethod = Character.toUpperCase(this.authMethod.charAt(0)) + this.authMethod.substring(1).toLowerCase();
/*     */     }
/* 323 */     String str3 = paramHeaderParser.findValue("algorithm");
/* 324 */     if (str3 == null || "".equals(str3)) {
/* 325 */       str3 = "MD5";
/*     */     }
/* 327 */     this.params.setAlgorithm(str3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     if (this.params.authQop()) {
/* 334 */       this.params.setNewCnonce();
/*     */     }
/*     */     
/* 337 */     String str4 = getHeaderValueImpl(str1, str2);
/* 338 */     if (str4 != null) {
/* 339 */       paramHttpURLConnection.setAuthenticationProperty(getHeaderName(), str4);
/* 340 */       return true;
/*     */     } 
/* 342 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getHeaderValueImpl(String paramString1, String paramString2) {
/*     */     String str1, str8, str9;
/* 351 */     char[] arrayOfChar = this.pw.getPassword();
/* 352 */     boolean bool = this.params.authQop();
/* 353 */     String str2 = this.params.getOpaque();
/* 354 */     String str3 = this.params.getCnonce();
/* 355 */     String str4 = this.params.getNonce();
/* 356 */     String str5 = this.params.getAlgorithm();
/* 357 */     this.params.incrementNC();
/* 358 */     int i = this.params.getNCCount();
/* 359 */     String str6 = null;
/*     */     
/* 361 */     if (i != -1) {
/* 362 */       str6 = Integer.toHexString(i).toLowerCase();
/* 363 */       int j = str6.length();
/* 364 */       if (j < 8) {
/* 365 */         str6 = zeroPad[j] + str6;
/*     */       }
/*     */     } 
/*     */     try {
/* 369 */       str1 = computeDigest(true, this.pw.getUserName(), arrayOfChar, this.realm, paramString2, paramString1, str4, str3, str6);
/*     */     }
/* 371 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 372 */       return null;
/*     */     } 
/*     */     
/* 375 */     String str7 = "\"";
/* 376 */     if (bool) {
/* 377 */       str7 = "\", nc=" + str6;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 382 */     if (delimCompatFlag) {
/*     */       
/* 384 */       str8 = ", algorithm=\"" + str5 + "\"";
/* 385 */       str9 = ", qop=\"auth\"";
/*     */     } else {
/*     */       
/* 388 */       str8 = ", algorithm=" + str5;
/* 389 */       str9 = ", qop=auth";
/*     */     } 
/*     */ 
/*     */     
/* 393 */     String str10 = this.authMethod + " username=\"" + this.pw.getUserName() + "\", realm=\"" + this.realm + "\", nonce=\"" + str4 + str7 + ", uri=\"" + paramString1 + "\", response=\"" + str1 + "\"" + str8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 400 */     if (str2 != null) {
/* 401 */       str10 = str10 + ", opaque=\"" + str2 + "\"";
/*     */     }
/* 403 */     if (str3 != null) {
/* 404 */       str10 = str10 + ", cnonce=\"" + str3 + "\"";
/*     */     }
/* 406 */     if (bool) {
/* 407 */       str10 = str10 + str9;
/*     */     }
/* 409 */     return str10;
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkResponse(String paramString1, String paramString2, URL paramURL) throws IOException {
/* 414 */     checkResponse(paramString1, paramString2, paramURL.getFile());
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkResponse(String paramString1, String paramString2, String paramString3) throws IOException {
/* 419 */     char[] arrayOfChar = this.pw.getPassword();
/* 420 */     String str1 = this.pw.getUserName();
/* 421 */     boolean bool = this.params.authQop();
/* 422 */     String str2 = this.params.getOpaque();
/* 423 */     String str3 = this.params.cnonce;
/* 424 */     String str4 = this.params.getNonce();
/* 425 */     String str5 = this.params.getAlgorithm();
/* 426 */     int i = this.params.getNCCount();
/* 427 */     String str6 = null;
/*     */     
/* 429 */     if (paramString1 == null) {
/* 430 */       throw new ProtocolException("No authentication information in response");
/*     */     }
/*     */     
/* 433 */     if (i != -1) {
/* 434 */       str6 = Integer.toHexString(i).toUpperCase();
/* 435 */       int j = str6.length();
/* 436 */       if (j < 8)
/* 437 */         str6 = zeroPad[j] + str6; 
/*     */     } 
/*     */     try {
/* 440 */       String str7 = computeDigest(false, str1, arrayOfChar, this.realm, paramString2, paramString3, str4, str3, str6);
/*     */       
/* 442 */       HeaderParser headerParser = new HeaderParser(paramString1);
/* 443 */       String str8 = headerParser.findValue("rspauth");
/* 444 */       if (str8 == null) {
/* 445 */         throw new ProtocolException("No digest in response");
/*     */       }
/* 447 */       if (!str8.equals(str7)) {
/* 448 */         throw new ProtocolException("Response digest invalid");
/*     */       }
/*     */       
/* 451 */       String str9 = headerParser.findValue("nextnonce");
/* 452 */       if (str9 != null && !"".equals(str9)) {
/* 453 */         this.params.setNonce(str9);
/*     */       }
/*     */     }
/* 456 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 457 */       throw new ProtocolException("Unsupported algorithm in response");
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
/*     */   private String computeDigest(boolean paramBoolean, String paramString1, char[] paramArrayOfchar, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws NoSuchAlgorithmException {
/* 470 */     String str1, str3, str5, str2 = this.params.getAlgorithm();
/* 471 */     boolean bool = str2.equalsIgnoreCase("MD5-sess");
/*     */     
/* 473 */     MessageDigest messageDigest = MessageDigest.getInstance(bool ? "MD5" : str2);
/*     */     
/* 475 */     if (bool) {
/* 476 */       if ((str1 = this.params.getCachedHA1()) == null) {
/* 477 */         str3 = paramString1 + ":" + paramString2 + ":";
/* 478 */         String str7 = encode(str3, paramArrayOfchar, messageDigest);
/* 479 */         String str6 = str7 + ":" + paramString5 + ":" + paramString6;
/* 480 */         str1 = encode(str6, (char[])null, messageDigest);
/* 481 */         this.params.setCachedHA1(str1);
/*     */       } 
/*     */     } else {
/* 484 */       String str = paramString1 + ":" + paramString2 + ":";
/* 485 */       str1 = encode(str, paramArrayOfchar, messageDigest);
/*     */     } 
/*     */ 
/*     */     
/* 489 */     if (paramBoolean) {
/* 490 */       str3 = paramString3 + ":" + paramString4;
/*     */     } else {
/* 492 */       str3 = ":" + paramString4;
/*     */     } 
/* 494 */     String str4 = encode(str3, (char[])null, messageDigest);
/*     */ 
/*     */     
/* 497 */     if (this.params.authQop()) {
/* 498 */       str5 = str1 + ":" + paramString5 + ":" + paramString7 + ":" + paramString6 + ":auth:" + str4;
/*     */     }
/*     */     else {
/*     */       
/* 502 */       str5 = str1 + ":" + paramString5 + ":" + str4;
/*     */     } 
/*     */ 
/*     */     
/* 506 */     return encode(str5, (char[])null, messageDigest);
/*     */   }
/*     */ 
/*     */   
/* 510 */   private static final char[] charArray = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 515 */   private static final String[] zeroPad = new String[] { "00000000", "0000000", "000000", "00000", "0000", "000", "00", "0" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String encode(String paramString, char[] paramArrayOfchar, MessageDigest paramMessageDigest) {
/*     */     try {
/* 522 */       paramMessageDigest.update(paramString.getBytes("ISO-8859-1"));
/* 523 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */       assert false;
/*     */     } 
/* 526 */     if (paramArrayOfchar != null) {
/* 527 */       byte[] arrayOfByte1 = new byte[paramArrayOfchar.length];
/* 528 */       for (byte b1 = 0; b1 < paramArrayOfchar.length; b1++)
/* 529 */         arrayOfByte1[b1] = (byte)paramArrayOfchar[b1]; 
/* 530 */       paramMessageDigest.update(arrayOfByte1);
/* 531 */       Arrays.fill(arrayOfByte1, (byte)0);
/*     */     } 
/* 533 */     byte[] arrayOfByte = paramMessageDigest.digest();
/*     */     
/* 535 */     StringBuffer stringBuffer = new StringBuffer(arrayOfByte.length * 2);
/* 536 */     for (byte b = 0; b < arrayOfByte.length; b++) {
/* 537 */       int i = arrayOfByte[b] >>> 4 & 0xF;
/* 538 */       stringBuffer.append(charArray[i]);
/* 539 */       i = arrayOfByte[b] & 0xF;
/* 540 */       stringBuffer.append(charArray[i]);
/*     */     } 
/* 542 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/DigestAuthentication.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */