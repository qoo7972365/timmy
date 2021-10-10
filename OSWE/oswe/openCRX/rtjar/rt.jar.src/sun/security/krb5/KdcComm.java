/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.Security;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import sun.security.krb5.internal.KRBError;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.NetClient;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class KdcComm
/*     */ {
/*     */   private static int defaultKdcRetryLimit;
/*     */   private static int defaultKdcTimeout;
/*     */   private static int defaultUdpPrefLimit;
/*  75 */   private static final boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String BAD_POLICY_KEY = "krb5.kdc.bad.policy";
/*     */ 
/*     */ 
/*     */   
/*     */   private enum BpType
/*     */   {
/*  85 */     NONE, TRY_LAST, TRY_LESS;
/*     */   }
/*  87 */   private static int tryLessMaxRetries = 1;
/*  88 */   private static int tryLessTimeout = 5000;
/*     */   private static BpType badPolicy;
/*     */   private String realm;
/*     */   
/*     */   static {
/*  93 */     initStatic();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initStatic() {
/* 100 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run() {
/* 103 */             return Security.getProperty("krb5.kdc.bad.policy");
/*     */           }
/*     */         });
/* 106 */     if (str != null) {
/* 107 */       str = str.toLowerCase(Locale.ENGLISH);
/* 108 */       String[] arrayOfString = str.split(":");
/* 109 */       if ("tryless".equals(arrayOfString[0])) {
/* 110 */         if (arrayOfString.length > 1) {
/* 111 */           String[] arrayOfString1 = arrayOfString[1].split(",");
/*     */           try {
/* 113 */             int m = Integer.parseInt(arrayOfString1[0]);
/* 114 */             if (arrayOfString1.length > 1) {
/* 115 */               tryLessTimeout = Integer.parseInt(arrayOfString1[1]);
/*     */             }
/*     */             
/* 118 */             tryLessMaxRetries = m;
/* 119 */           } catch (NumberFormatException numberFormatException) {
/*     */ 
/*     */             
/* 122 */             if (DEBUG) {
/* 123 */               System.out.println("Invalid krb5.kdc.bad.policy parameter for tryLess: " + str + ", use default");
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 129 */         badPolicy = BpType.TRY_LESS;
/* 130 */       } else if ("trylast".equals(arrayOfString[0])) {
/* 131 */         badPolicy = BpType.TRY_LAST;
/*     */       } else {
/* 133 */         badPolicy = BpType.NONE;
/*     */       } 
/*     */     } else {
/* 136 */       badPolicy = BpType.NONE;
/*     */     } 
/*     */ 
/*     */     
/* 140 */     int i = -1;
/* 141 */     int j = -1;
/* 142 */     int k = -1;
/*     */     
/*     */     try {
/* 145 */       Config config = Config.getInstance();
/* 146 */       String str1 = config.get(new String[] { "libdefaults", "kdc_timeout" });
/* 147 */       i = parseTimeString(str1);
/*     */       
/* 149 */       str1 = config.get(new String[] { "libdefaults", "max_retries" });
/* 150 */       j = parsePositiveIntString(str1);
/* 151 */       str1 = config.get(new String[] { "libdefaults", "udp_preference_limit" });
/* 152 */       k = parsePositiveIntString(str1);
/* 153 */     } catch (Exception exception) {
/*     */       
/* 155 */       if (DEBUG) {
/* 156 */         System.out.println("Exception in getting KDC communication settings, using default value " + exception
/*     */             
/* 158 */             .getMessage());
/*     */       }
/*     */     } 
/* 161 */     defaultKdcTimeout = (i > 0) ? i : 30000;
/* 162 */     defaultKdcRetryLimit = (j > 0) ? j : 3;
/*     */ 
/*     */     
/* 165 */     if (k < 0) {
/* 166 */       defaultUdpPrefLimit = 1465;
/* 167 */     } else if (k > 32700) {
/* 168 */       defaultUdpPrefLimit = 32700;
/*     */     } else {
/* 170 */       defaultUdpPrefLimit = k;
/*     */     } 
/*     */     
/* 173 */     KdcAccessibility.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KdcComm(String paramString) throws KrbException {
/* 182 */     if (paramString == null) {
/* 183 */       paramString = Config.getInstance().getDefaultRealm();
/* 184 */       if (paramString == null) {
/* 185 */         throw new KrbException(60, "Cannot find default realm");
/*     */       }
/*     */     } 
/*     */     
/* 189 */     this.realm = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] send(byte[] paramArrayOfbyte) throws IOException, KrbException {
/* 194 */     int i = getRealmSpecificValue(this.realm, "udp_preference_limit", defaultUdpPrefLimit);
/*     */ 
/*     */     
/* 197 */     boolean bool = (i > 0 && paramArrayOfbyte != null && paramArrayOfbyte.length > i) ? true : false;
/*     */ 
/*     */     
/* 200 */     return send(paramArrayOfbyte, bool);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] send(byte[] paramArrayOfbyte, boolean paramBoolean) throws IOException, KrbException {
/* 206 */     if (paramArrayOfbyte == null)
/* 207 */       return null; 
/* 208 */     Config config = Config.getInstance();
/*     */     
/* 210 */     if (this.realm == null) {
/* 211 */       this.realm = config.getDefaultRealm();
/* 212 */       if (this.realm == null) {
/* 213 */         throw new KrbException(60, "Cannot find default realm");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 218 */     String str = config.getKDCList(this.realm);
/* 219 */     if (str == null) {
/* 220 */       throw new KrbException("Cannot get kdc for realm " + this.realm);
/*     */     }
/*     */     
/* 223 */     Iterator<String> iterator = KdcAccessibility.list(str).iterator();
/* 224 */     if (!iterator.hasNext()) {
/* 225 */       throw new KrbException("Cannot get kdc for realm " + this.realm);
/*     */     }
/* 227 */     byte[] arrayOfByte = null;
/*     */     try {
/* 229 */       arrayOfByte = sendIfPossible(paramArrayOfbyte, iterator.next(), paramBoolean);
/* 230 */     } catch (Exception exception) {
/* 231 */       boolean bool = false;
/* 232 */       while (iterator.hasNext()) {
/*     */         try {
/* 234 */           arrayOfByte = sendIfPossible(paramArrayOfbyte, iterator.next(), paramBoolean);
/* 235 */           bool = true;
/*     */           break;
/* 237 */         } catch (Exception exception1) {}
/*     */       } 
/* 239 */       if (!bool) throw exception; 
/*     */     } 
/* 241 */     if (arrayOfByte == null) {
/* 242 */       throw new IOException("Cannot get a KDC reply");
/*     */     }
/* 244 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] sendIfPossible(byte[] paramArrayOfbyte, String paramString, boolean paramBoolean) throws IOException, KrbException {
/*     */     try {
/* 253 */       byte[] arrayOfByte = send(paramArrayOfbyte, paramString, paramBoolean);
/* 254 */       KRBError kRBError = null;
/*     */       try {
/* 256 */         kRBError = new KRBError(arrayOfByte);
/* 257 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/* 260 */       if (kRBError != null && kRBError.getErrorCode() == 52)
/*     */       {
/* 262 */         arrayOfByte = send(paramArrayOfbyte, paramString, true);
/*     */       }
/* 264 */       KdcAccessibility.removeBad(paramString);
/* 265 */       return arrayOfByte;
/* 266 */     } catch (Exception exception) {
/* 267 */       if (DEBUG) {
/* 268 */         System.out.println(">>> KrbKdcReq send: error trying " + paramString);
/*     */         
/* 270 */         exception.printStackTrace(System.out);
/*     */       } 
/* 272 */       KdcAccessibility.addBad(paramString);
/* 273 */       throw exception;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] send(byte[] paramArrayOfbyte, String paramString, boolean paramBoolean) throws IOException, KrbException {
/* 282 */     if (paramArrayOfbyte == null) {
/* 283 */       return null;
/*     */     }
/* 285 */     int i = 88;
/* 286 */     int j = getRealmSpecificValue(this.realm, "max_retries", defaultKdcRetryLimit);
/*     */     
/* 288 */     int k = getRealmSpecificValue(this.realm, "kdc_timeout", defaultKdcTimeout);
/*     */     
/* 290 */     if (badPolicy == BpType.TRY_LESS && KdcAccessibility
/* 291 */       .isBad(paramString)) {
/* 292 */       if (j > tryLessMaxRetries) {
/* 293 */         j = tryLessMaxRetries;
/*     */       }
/* 295 */       if (k > tryLessTimeout) {
/* 296 */         k = tryLessTimeout;
/*     */       }
/*     */     } 
/*     */     
/* 300 */     String str1 = null;
/* 301 */     String str2 = null;
/*     */     
/* 303 */     if (paramString.charAt(0) == '[') {
/* 304 */       int m = paramString.indexOf(']', 1);
/* 305 */       if (m == -1) {
/* 306 */         throw new IOException("Illegal KDC: " + paramString);
/*     */       }
/* 308 */       str1 = paramString.substring(1, m);
/* 309 */       if (m != paramString.length() - 1) {
/* 310 */         if (paramString.charAt(m + 1) != ':') {
/* 311 */           throw new IOException("Illegal KDC: " + paramString);
/*     */         }
/* 313 */         str2 = paramString.substring(m + 2);
/*     */       } 
/*     */     } else {
/* 316 */       int m = paramString.indexOf(':');
/* 317 */       if (m == -1) {
/* 318 */         str1 = paramString;
/*     */       } else {
/* 320 */         int n = paramString.indexOf(':', m + 1);
/* 321 */         if (n > 0) {
/* 322 */           str1 = paramString;
/*     */         } else {
/* 324 */           str1 = paramString.substring(0, m);
/* 325 */           str2 = paramString.substring(m + 1);
/*     */         } 
/*     */       } 
/*     */     } 
/* 329 */     if (str2 != null) {
/* 330 */       int m = parsePositiveIntString(str2);
/* 331 */       if (m > 0) {
/* 332 */         i = m;
/*     */       }
/*     */     } 
/* 335 */     if (DEBUG) {
/* 336 */       System.out.println(">>> KrbKdcReq send: kdc=" + str1 + (paramBoolean ? " TCP:" : " UDP:") + i + ", timeout=" + k + ", number of retries =" + j + ", #bytes=" + paramArrayOfbyte.length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 345 */     KdcCommunication kdcCommunication = new KdcCommunication(str1, i, paramBoolean, k, j, paramArrayOfbyte);
/*     */     
/*     */     try {
/* 348 */       byte[] arrayOfByte = AccessController.<byte[]>doPrivileged(kdcCommunication);
/* 349 */       if (DEBUG) {
/* 350 */         System.out.println(">>> KrbKdcReq send: #bytes read=" + ((arrayOfByte != null) ? arrayOfByte.length : 0));
/*     */       }
/*     */       
/* 353 */       return arrayOfByte;
/* 354 */     } catch (PrivilegedActionException privilegedActionException) {
/* 355 */       Exception exception = privilegedActionException.getException();
/* 356 */       if (exception instanceof IOException) {
/* 357 */         throw (IOException)exception;
/*     */       }
/* 359 */       throw (KrbException)exception;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class KdcCommunication
/*     */     implements PrivilegedExceptionAction<byte[]>
/*     */   {
/*     */     private String kdc;
/*     */     
/*     */     private int port;
/*     */     private boolean useTCP;
/*     */     private int timeout;
/*     */     private int retries;
/*     */     private byte[] obuf;
/*     */     
/*     */     public KdcCommunication(String param1String, int param1Int1, boolean param1Boolean, int param1Int2, int param1Int3, byte[] param1ArrayOfbyte) {
/* 376 */       this.kdc = param1String;
/* 377 */       this.port = param1Int1;
/* 378 */       this.useTCP = param1Boolean;
/* 379 */       this.timeout = param1Int2;
/* 380 */       this.retries = param1Int3;
/* 381 */       this.obuf = param1ArrayOfbyte;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] run() throws IOException, KrbException {
/* 389 */       byte[] arrayOfByte = null;
/*     */       
/* 391 */       for (byte b = 1; b <= this.retries; b++) {
/* 392 */         String str = this.useTCP ? "TCP" : "UDP";
/* 393 */         if (KdcComm.DEBUG) {
/* 394 */           System.out.println(">>> KDCCommunication: kdc=" + this.kdc + " " + str + ":" + this.port + ", timeout=" + this.timeout + ",Attempt =" + b + ", #bytes=" + this.obuf.length);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 401 */           try (NetClient null = NetClient.getInstance(str, this.kdc, this.port, this.timeout)) {
/*     */             
/* 403 */             netClient.send(this.obuf);
/* 404 */             arrayOfByte = netClient.receive();
/*     */           }  break;
/* 406 */         } catch (SocketTimeoutException socketTimeoutException) {
/* 407 */           if (KdcComm.DEBUG) {
/* 408 */             System.out.println("SocketTimeOutException with attempt: " + b);
/*     */           }
/*     */           
/* 411 */           if (b == this.retries) {
/* 412 */             arrayOfByte = null;
/* 413 */             throw socketTimeoutException;
/*     */           } 
/*     */         } 
/*     */       } 
/* 417 */       return arrayOfByte;
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
/*     */   private static int parseTimeString(String paramString) {
/* 429 */     if (paramString == null) {
/* 430 */       return -1;
/*     */     }
/* 432 */     if (paramString.endsWith("s")) {
/* 433 */       int i = parsePositiveIntString(paramString.substring(0, paramString.length() - 1));
/* 434 */       return (i < 0) ? -1 : (i * 1000);
/*     */     } 
/* 436 */     return parsePositiveIntString(paramString);
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
/*     */   private int getRealmSpecificValue(String paramString1, String paramString2, int paramInt) {
/* 453 */     int i = paramInt;
/*     */     
/* 455 */     if (paramString1 == null) return i;
/*     */     
/* 457 */     int j = -1;
/*     */     
/*     */     try {
/* 460 */       String str = Config.getInstance().get(new String[] { "realms", paramString1, paramString2 });
/* 461 */       if (paramString2.equals("kdc_timeout")) {
/* 462 */         j = parseTimeString(str);
/*     */       } else {
/* 464 */         j = parsePositiveIntString(str);
/*     */       } 
/* 466 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 470 */     if (j > 0) i = j;
/*     */     
/* 472 */     return i;
/*     */   }
/*     */   
/*     */   private static int parsePositiveIntString(String paramString) {
/* 476 */     if (paramString == null) {
/* 477 */       return -1;
/*     */     }
/* 479 */     int i = -1;
/*     */     
/*     */     try {
/* 482 */       i = Integer.parseInt(paramString);
/* 483 */     } catch (Exception exception) {
/* 484 */       return -1;
/*     */     } 
/*     */     
/* 487 */     if (i >= 0) {
/* 488 */       return i;
/*     */     }
/* 490 */     return -1;
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
/*     */   static class KdcAccessibility
/*     */   {
/* 504 */     private static Set<String> bads = new HashSet<>();
/*     */     
/*     */     private static synchronized void addBad(String param1String) {
/* 507 */       if (KdcComm.DEBUG) {
/* 508 */         System.out.println(">>> KdcAccessibility: add " + param1String);
/*     */       }
/* 510 */       bads.add(param1String);
/*     */     }
/*     */     
/*     */     private static synchronized void removeBad(String param1String) {
/* 514 */       if (KdcComm.DEBUG) {
/* 515 */         System.out.println(">>> KdcAccessibility: remove " + param1String);
/*     */       }
/* 517 */       bads.remove(param1String);
/*     */     }
/*     */     
/*     */     private static synchronized boolean isBad(String param1String) {
/* 521 */       return bads.contains(param1String);
/*     */     }
/*     */     
/*     */     private static synchronized void reset() {
/* 525 */       if (KdcComm.DEBUG) {
/* 526 */         System.out.println(">>> KdcAccessibility: reset");
/*     */       }
/* 528 */       bads.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     private static synchronized List<String> list(String param1String) {
/* 533 */       StringTokenizer stringTokenizer = new StringTokenizer(param1String);
/* 534 */       ArrayList<String> arrayList = new ArrayList();
/* 535 */       if (KdcComm.badPolicy == KdcComm.BpType.TRY_LAST) {
/* 536 */         ArrayList<String> arrayList1 = new ArrayList();
/* 537 */         while (stringTokenizer.hasMoreTokens()) {
/* 538 */           String str = stringTokenizer.nextToken();
/* 539 */           if (bads.contains(str)) { arrayList1.add(str); continue; }
/* 540 */            arrayList.add(str);
/*     */         } 
/*     */         
/* 543 */         arrayList.addAll(arrayList1);
/*     */       }
/*     */       else {
/*     */         
/* 547 */         while (stringTokenizer.hasMoreTokens()) {
/* 548 */           arrayList.add(stringTokenizer.nextToken());
/*     */         }
/*     */       } 
/* 551 */       return arrayList;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KdcComm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */