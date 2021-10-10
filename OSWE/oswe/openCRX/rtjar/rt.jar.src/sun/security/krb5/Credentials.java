/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.krb5.internal.AuthorizationData;
/*     */ import sun.security.krb5.internal.CredentialsUtil;
/*     */ import sun.security.krb5.internal.HostAddresses;
/*     */ import sun.security.krb5.internal.KDCOptions;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.TicketFlags;
/*     */ import sun.security.krb5.internal.ccache.Credentials;
/*     */ import sun.security.krb5.internal.ccache.CredentialsCache;
/*     */ import sun.security.krb5.internal.crypto.EType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Credentials
/*     */ {
/*     */   Ticket ticket;
/*     */   PrincipalName client;
/*     */   PrincipalName clientAlias;
/*     */   PrincipalName server;
/*     */   PrincipalName serverAlias;
/*     */   EncryptionKey key;
/*     */   TicketFlags flags;
/*     */   KerberosTime authTime;
/*     */   KerberosTime startTime;
/*     */   KerberosTime endTime;
/*     */   KerberosTime renewTill;
/*     */   HostAddresses cAddr;
/*     */   AuthorizationData authzData;
/*  62 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */   
/*     */   private static CredentialsCache cache;
/*     */   static boolean alreadyLoaded = false;
/*     */   private static boolean alreadyTried = false;
/*  67 */   private Credentials proxy = null;
/*     */   
/*     */   public Credentials getProxy() {
/*  70 */     return this.proxy;
/*     */   }
/*     */   
/*     */   public Credentials setProxy(Credentials paramCredentials) {
/*  74 */     this.proxy = paramCredentials;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native Credentials acquireDefaultNativeCreds(int[] paramArrayOfint);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials(Ticket paramTicket, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, PrincipalName paramPrincipalName3, PrincipalName paramPrincipalName4, EncryptionKey paramEncryptionKey, TicketFlags paramTicketFlags, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, KerberosTime paramKerberosTime4, HostAddresses paramHostAddresses, AuthorizationData paramAuthorizationData) {
/*  94 */     this(paramTicket, paramPrincipalName1, paramPrincipalName2, paramPrincipalName3, paramPrincipalName4, paramEncryptionKey, paramTicketFlags, paramKerberosTime1, paramKerberosTime2, paramKerberosTime3, paramKerberosTime4, paramHostAddresses);
/*     */ 
/*     */     
/*  97 */     this.authzData = paramAuthorizationData;
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
/*     */   public Credentials(Ticket paramTicket, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, PrincipalName paramPrincipalName3, PrincipalName paramPrincipalName4, EncryptionKey paramEncryptionKey, TicketFlags paramTicketFlags, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, KerberosTime paramKerberosTime4, HostAddresses paramHostAddresses) {
/* 112 */     this.ticket = paramTicket;
/* 113 */     this.client = paramPrincipalName1;
/* 114 */     this.clientAlias = paramPrincipalName2;
/* 115 */     this.server = paramPrincipalName3;
/* 116 */     this.serverAlias = paramPrincipalName4;
/* 117 */     this.key = paramEncryptionKey;
/* 118 */     this.flags = paramTicketFlags;
/* 119 */     this.authTime = paramKerberosTime1;
/* 120 */     this.startTime = paramKerberosTime2;
/* 121 */     this.endTime = paramKerberosTime3;
/* 122 */     this.renewTill = paramKerberosTime4;
/* 123 */     this.cAddr = paramHostAddresses;
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
/*     */   public Credentials(byte[] paramArrayOfbyte1, String paramString1, String paramString2, String paramString3, String paramString4, byte[] paramArrayOfbyte2, int paramInt, boolean[] paramArrayOfboolean, Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4, InetAddress[] paramArrayOfInetAddress) throws KrbException, IOException {
/* 139 */     this(new Ticket(paramArrayOfbyte1), new PrincipalName(paramString1, 1), (paramString2 == null) ? null : new PrincipalName(paramString2, 1), new PrincipalName(paramString3, 2), (paramString4 == null) ? null : new PrincipalName(paramString4, 2), new EncryptionKey(paramInt, paramArrayOfbyte2), (paramArrayOfboolean == null) ? null : new TicketFlags(paramArrayOfboolean), (paramDate1 == null) ? null : new KerberosTime(paramDate1), (paramDate2 == null) ? null : new KerberosTime(paramDate2), (paramDate3 == null) ? null : new KerberosTime(paramDate3), (paramDate4 == null) ? null : new KerberosTime(paramDate4), null);
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
/*     */   public final PrincipalName getClient() {
/* 167 */     return this.client;
/*     */   }
/*     */   
/*     */   public final PrincipalName getClientAlias() {
/* 171 */     return this.clientAlias;
/*     */   }
/*     */   
/*     */   public final PrincipalName getServer() {
/* 175 */     return this.server;
/*     */   }
/*     */   
/*     */   public final PrincipalName getServerAlias() {
/* 179 */     return this.serverAlias;
/*     */   }
/*     */   
/*     */   public final EncryptionKey getSessionKey() {
/* 183 */     return this.key;
/*     */   }
/*     */   
/*     */   public final Date getAuthTime() {
/* 187 */     if (this.authTime != null) {
/* 188 */       return this.authTime.toDate();
/*     */     }
/* 190 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Date getStartTime() {
/* 195 */     if (this.startTime != null)
/*     */     {
/* 197 */       return this.startTime.toDate();
/*     */     }
/* 199 */     return null;
/*     */   }
/*     */   
/*     */   public final Date getEndTime() {
/* 203 */     if (this.endTime != null)
/*     */     {
/* 205 */       return this.endTime.toDate();
/*     */     }
/* 207 */     return null;
/*     */   }
/*     */   
/*     */   public final Date getRenewTill() {
/* 211 */     if (this.renewTill != null)
/*     */     {
/* 213 */       return this.renewTill.toDate();
/*     */     }
/* 215 */     return null;
/*     */   }
/*     */   
/*     */   public final boolean[] getFlags() {
/* 219 */     if (this.flags == null)
/* 220 */       return null; 
/* 221 */     return this.flags.toBooleanArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public final InetAddress[] getClientAddresses() {
/* 226 */     if (this.cAddr == null) {
/* 227 */       return null;
/*     */     }
/* 229 */     return this.cAddr.getInetAddresses();
/*     */   }
/*     */   
/*     */   public final byte[] getEncoded() {
/* 233 */     byte[] arrayOfByte = null;
/*     */     try {
/* 235 */       arrayOfByte = this.ticket.asn1Encode();
/* 236 */     } catch (Asn1Exception asn1Exception) {
/* 237 */       if (DEBUG)
/* 238 */         System.out.println(asn1Exception); 
/* 239 */     } catch (IOException iOException) {
/* 240 */       if (DEBUG)
/* 241 */         System.out.println(iOException); 
/*     */     } 
/* 243 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public boolean isForwardable() {
/* 247 */     return this.flags.get(1);
/*     */   }
/*     */   
/*     */   public boolean isRenewable() {
/* 251 */     return this.flags.get(8);
/*     */   }
/*     */   
/*     */   public Ticket getTicket() {
/* 255 */     return this.ticket;
/*     */   }
/*     */   
/*     */   public TicketFlags getTicketFlags() {
/* 259 */     return this.flags;
/*     */   }
/*     */   
/*     */   public AuthorizationData getAuthzData() {
/* 263 */     return this.authzData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkDelegate() {
/* 271 */     return this.flags.get(13);
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
/*     */   public void resetDelegate() {
/* 283 */     this.flags.set(13, false);
/*     */   }
/*     */   
/*     */   public Credentials renew() throws KrbException, IOException {
/* 287 */     KDCOptions kDCOptions = new KDCOptions();
/* 288 */     kDCOptions.set(30, true);
/*     */ 
/*     */ 
/*     */     
/* 292 */     kDCOptions.set(8, true);
/*     */     
/* 294 */     return (new KrbTgsReq(kDCOptions, this, this.server, this.serverAlias, null, null, null, null, this.cAddr, null, null, null))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 305 */       .sendAndGetCreds();
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
/*     */   public static Credentials acquireTGTFromCache(PrincipalName paramPrincipalName, String paramString) throws KrbException, IOException {
/* 323 */     if (paramString == null) {
/*     */       
/* 325 */       String str = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"));
/*     */       
/* 327 */       if (str.toUpperCase(Locale.ENGLISH).startsWith("WINDOWS") || str
/* 328 */         .toUpperCase(Locale.ENGLISH).contains("OS X")) {
/* 329 */         Credentials credentials1 = acquireDefaultCreds();
/* 330 */         if (credentials1 == null) {
/* 331 */           if (DEBUG) {
/* 332 */             System.out.println(">>> Found no TGT's in LSA");
/*     */           }
/* 334 */           return null;
/*     */         } 
/* 336 */         if (paramPrincipalName != null) {
/* 337 */           if (credentials1.getClient().equals(paramPrincipalName)) {
/* 338 */             if (DEBUG) {
/* 339 */               System.out.println(">>> Obtained TGT from LSA: " + credentials1);
/*     */             }
/*     */             
/* 342 */             return credentials1;
/*     */           } 
/* 344 */           if (DEBUG) {
/* 345 */             System.out.println(">>> LSA contains TGT for " + credentials1
/* 346 */                 .getClient() + " not " + paramPrincipalName);
/*     */           }
/*     */ 
/*     */           
/* 350 */           return null;
/*     */         } 
/*     */         
/* 353 */         if (DEBUG) {
/* 354 */           System.out.println(">>> Obtained TGT from LSA: " + credentials1);
/*     */         }
/*     */         
/* 357 */         return credentials1;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 367 */     CredentialsCache credentialsCache = CredentialsCache.getInstance(paramPrincipalName, paramString);
/*     */     
/* 369 */     if (credentialsCache == null) {
/* 370 */       return null;
/*     */     }
/*     */     
/* 373 */     Credentials credentials = credentialsCache.getInitialCreds();
/*     */     
/* 375 */     if (credentials == null) {
/* 376 */       return null;
/*     */     }
/*     */     
/* 379 */     if (EType.isSupported(credentials.key.getEType())) {
/* 380 */       return credentials;
/*     */     }
/* 382 */     if (DEBUG) {
/* 383 */       System.out.println(">>> unsupported key type found the default TGT: " + credentials.key
/*     */           
/* 385 */           .getEType());
/*     */     }
/* 387 */     return null;
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
/*     */   public static synchronized Credentials acquireDefaultCreds() {
/* 414 */     Credentials credentials = null;
/*     */     
/* 416 */     if (cache == null) {
/* 417 */       cache = CredentialsCache.getInstance();
/*     */     }
/* 419 */     if (cache != null) {
/* 420 */       Credentials credentials1 = cache.getInitialCreds();
/* 421 */       if (credentials1 != null) {
/* 422 */         if (DEBUG) {
/* 423 */           System.out.println(">>> KrbCreds found the default ticket granting ticket in credential cache.");
/*     */         }
/*     */         
/* 426 */         if (EType.isSupported(credentials1.key.getEType())) {
/* 427 */           credentials = credentials1;
/*     */         }
/* 429 */         else if (DEBUG) {
/* 430 */           System.out.println(">>> unsupported key type found the default TGT: " + credentials1.key
/*     */               
/* 432 */               .getEType());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 437 */     if (credentials == null) {
/*     */ 
/*     */ 
/*     */       
/* 441 */       if (!alreadyTried) {
/*     */         
/*     */         try {
/* 444 */           ensureLoaded();
/* 445 */         } catch (Exception exception) {
/* 446 */           if (DEBUG) {
/* 447 */             System.out.println("Can not load credentials cache");
/* 448 */             exception.printStackTrace();
/*     */           } 
/* 450 */           alreadyTried = true;
/*     */         } 
/*     */       }
/* 453 */       if (alreadyLoaded) {
/*     */         
/* 455 */         if (DEBUG) {
/* 456 */           System.out.println(">> Acquire default native Credentials");
/*     */         }
/*     */         try {
/* 459 */           credentials = acquireDefaultNativeCreds(
/* 460 */               EType.getDefaults("default_tkt_enctypes"));
/* 461 */         } catch (KrbException krbException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 466 */     return credentials;
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
/*     */   public static Credentials acquireServiceCreds(String paramString, Credentials paramCredentials) throws KrbException, IOException {
/* 490 */     return CredentialsUtil.acquireServiceCreds(paramString, paramCredentials);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Credentials acquireS4U2selfCreds(PrincipalName paramPrincipalName, Credentials paramCredentials) throws KrbException, IOException {
/* 495 */     return CredentialsUtil.acquireS4U2selfCreds(paramPrincipalName, paramCredentials);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Credentials acquireS4U2proxyCreds(String paramString, Ticket paramTicket, PrincipalName paramPrincipalName, Credentials paramCredentials) throws KrbException, IOException {
/* 501 */     return CredentialsUtil.acquireS4U2proxyCreds(paramString, paramTicket, paramPrincipalName, paramCredentials);
/*     */   }
/*     */ 
/*     */   
/*     */   public CredentialsCache getCache() {
/* 506 */     return cache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void printDebug(Credentials paramCredentials) {
/* 513 */     System.out.println(">>> DEBUG: ----Credentials----");
/* 514 */     System.out.println("\tclient: " + paramCredentials.client.toString());
/* 515 */     if (paramCredentials.clientAlias != null)
/* 516 */       System.out.println("\tclient alias: " + paramCredentials.clientAlias.toString()); 
/* 517 */     System.out.println("\tserver: " + paramCredentials.server.toString());
/* 518 */     if (paramCredentials.serverAlias != null)
/* 519 */       System.out.println("\tserver alias: " + paramCredentials.serverAlias.toString()); 
/* 520 */     System.out.println("\tticket: sname: " + paramCredentials.ticket.sname.toString());
/* 521 */     if (paramCredentials.startTime != null) {
/* 522 */       System.out.println("\tstartTime: " + paramCredentials.startTime.getTime());
/*     */     }
/* 524 */     System.out.println("\tendTime: " + paramCredentials.endTime.getTime());
/* 525 */     System.out.println("        ----Credentials end----");
/*     */   }
/*     */ 
/*     */   
/*     */   static void ensureLoaded() {
/* 530 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 533 */             if (System.getProperty("os.name").contains("OS X")) {
/* 534 */               System.loadLibrary("osxkrb5");
/*     */             } else {
/* 536 */               System.loadLibrary("w2k_lsa_auth");
/*     */             } 
/* 538 */             return null;
/*     */           }
/*     */         });
/* 541 */     alreadyLoaded = true;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 545 */     StringBuffer stringBuffer = new StringBuffer("Credentials:");
/* 546 */     stringBuffer.append("\n      client=").append(this.client);
/* 547 */     if (this.clientAlias != null)
/* 548 */       stringBuffer.append("\n      clientAlias=").append(this.clientAlias); 
/* 549 */     stringBuffer.append("\n      server=").append(this.server);
/* 550 */     if (this.serverAlias != null)
/* 551 */       stringBuffer.append("\n      serverAlias=").append(this.serverAlias); 
/* 552 */     if (this.authTime != null) {
/* 553 */       stringBuffer.append("\n    authTime=").append(this.authTime);
/*     */     }
/* 555 */     if (this.startTime != null) {
/* 556 */       stringBuffer.append("\n   startTime=").append(this.startTime);
/*     */     }
/* 558 */     stringBuffer.append("\n     endTime=").append(this.endTime);
/* 559 */     stringBuffer.append("\n   renewTill=").append(this.renewTill);
/* 560 */     stringBuffer.append("\n       flags=").append(this.flags);
/* 561 */     stringBuffer.append("\nEType (skey)=").append(this.key.getEType());
/* 562 */     stringBuffer.append("\n   (tkt key)=").append(this.ticket.encPart.eType);
/* 563 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public Credentials toCCacheCreds() {
/* 567 */     return new Credentials(
/* 568 */         getClient(), getServer(), 
/* 569 */         getSessionKey(), 
/* 570 */         date2kt(getAuthTime()), 
/* 571 */         date2kt(getStartTime()), 
/* 572 */         date2kt(getEndTime()), 
/* 573 */         date2kt(getRenewTill()), false, this.flags, new HostAddresses(
/*     */ 
/*     */           
/* 576 */           getClientAddresses()), 
/* 577 */         getAuthzData(), 
/* 578 */         getTicket(), null);
/*     */   }
/*     */ 
/*     */   
/*     */   private static KerberosTime date2kt(Date paramDate) {
/* 583 */     return (paramDate == null) ? null : new KerberosTime(paramDate);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/Credentials.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */