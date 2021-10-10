/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ import sun.security.jgss.krb5.Krb5AcceptCredential;
/*     */ import sun.security.krb5.internal.APOptions;
/*     */ import sun.security.krb5.internal.APReq;
/*     */ import sun.security.krb5.internal.Authenticator;
/*     */ import sun.security.krb5.internal.AuthorizationData;
/*     */ import sun.security.krb5.internal.EncTicketPart;
/*     */ import sun.security.krb5.internal.HostAddress;
/*     */ import sun.security.krb5.internal.KRBError;
/*     */ import sun.security.krb5.internal.KdcErrException;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.KrbApErrException;
/*     */ import sun.security.krb5.internal.LocalSeqNumber;
/*     */ import sun.security.krb5.internal.ReplayCache;
/*     */ import sun.security.krb5.internal.SeqNumber;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.crypto.EType;
/*     */ import sun.security.krb5.internal.rcache.AuthTimeWithHash;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KrbApReq
/*     */ {
/*     */   private byte[] obuf;
/*     */   private KerberosTime ctime;
/*     */   private int cusec;
/*     */   private Authenticator authenticator;
/*     */   private Credentials creds;
/*     */   private APReq apReqMessg;
/*  59 */   private static ReplayCache rcache = ReplayCache.getInstance();
/*  60 */   private static boolean DEBUG = Krb5.DEBUG;
/*  61 */   private static final char[] hexConst = "0123456789ABCDEF".toCharArray();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KrbApReq(Credentials paramCredentials, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Checksum paramChecksum) throws Asn1Exception, KrbCryptoException, KrbException, IOException {
/* 109 */     APOptions aPOptions = paramBoolean1 ? new APOptions(2) : new APOptions();
/*     */ 
/*     */     
/* 112 */     if (DEBUG) {
/* 113 */       System.out.println(">>> KrbApReq: APOptions are " + aPOptions);
/*     */     }
/*     */     
/* 116 */     EncryptionKey encryptionKey = paramBoolean2 ? new EncryptionKey(paramCredentials.getSessionKey()) : null;
/*     */ 
/*     */     
/* 119 */     LocalSeqNumber localSeqNumber = new LocalSeqNumber();
/*     */     
/* 121 */     init(aPOptions, paramCredentials, paramChecksum, encryptionKey, localSeqNumber, null, 11);
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
/*     */   public KrbApReq(byte[] paramArrayOfbyte, Krb5AcceptCredential paramKrb5AcceptCredential, InetAddress paramInetAddress) throws KrbException, IOException {
/* 146 */     this.obuf = paramArrayOfbyte;
/* 147 */     if (this.apReqMessg == null)
/* 148 */       decode(); 
/* 149 */     authenticate(paramKrb5AcceptCredential, paramInetAddress);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   KrbApReq(APOptions paramAPOptions, Ticket paramTicket, EncryptionKey paramEncryptionKey1, PrincipalName paramPrincipalName, Checksum paramChecksum, KerberosTime paramKerberosTime, EncryptionKey paramEncryptionKey2, SeqNumber paramSeqNumber, AuthorizationData paramAuthorizationData) throws Asn1Exception, IOException, KdcErrException, KrbCryptoException {
/* 196 */     init(paramAPOptions, paramTicket, paramEncryptionKey1, paramPrincipalName, paramChecksum, paramKerberosTime, paramEncryptionKey2, paramSeqNumber, paramAuthorizationData, 7);
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
/*     */   private void init(APOptions paramAPOptions, Credentials paramCredentials, Checksum paramChecksum, EncryptionKey paramEncryptionKey, SeqNumber paramSeqNumber, AuthorizationData paramAuthorizationData, int paramInt) throws KrbException, IOException {
/* 211 */     this.ctime = KerberosTime.now();
/* 212 */     init(paramAPOptions, paramCredentials.ticket, paramCredentials.key, paramCredentials.client, paramChecksum, this.ctime, paramEncryptionKey, paramSeqNumber, paramAuthorizationData, paramInt);
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
/*     */   private void init(APOptions paramAPOptions, Ticket paramTicket, EncryptionKey paramEncryptionKey1, PrincipalName paramPrincipalName, Checksum paramChecksum, KerberosTime paramKerberosTime, EncryptionKey paramEncryptionKey2, SeqNumber paramSeqNumber, AuthorizationData paramAuthorizationData, int paramInt) throws Asn1Exception, IOException, KdcErrException, KrbCryptoException {
/* 237 */     createMessage(paramAPOptions, paramTicket, paramEncryptionKey1, paramPrincipalName, paramChecksum, paramKerberosTime, paramEncryptionKey2, paramSeqNumber, paramAuthorizationData, paramInt);
/*     */ 
/*     */     
/* 240 */     this.obuf = this.apReqMessg.asn1Encode();
/*     */   }
/*     */ 
/*     */   
/*     */   void decode() throws KrbException, IOException {
/* 245 */     DerValue derValue = new DerValue(this.obuf);
/* 246 */     decode(derValue);
/*     */   }
/*     */   
/*     */   void decode(DerValue paramDerValue) throws KrbException, IOException {
/* 250 */     this.apReqMessg = null;
/*     */     try {
/* 252 */       this.apReqMessg = new APReq(paramDerValue);
/* 253 */     } catch (Asn1Exception asn1Exception) {
/* 254 */       String str2; this.apReqMessg = null;
/* 255 */       KRBError kRBError = new KRBError(paramDerValue);
/* 256 */       String str1 = kRBError.getErrorString();
/*     */       
/* 258 */       if (str1.charAt(str1.length() - 1) == '\000') {
/* 259 */         str2 = str1.substring(0, str1.length() - 1);
/*     */       } else {
/* 261 */         str2 = str1;
/* 262 */       }  KrbException krbException = new KrbException(kRBError.getErrorCode(), str2);
/* 263 */       krbException.initCause(asn1Exception);
/* 264 */       throw krbException;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void authenticate(Krb5AcceptCredential paramKrb5AcceptCredential, InetAddress paramInetAddress) throws KrbException, IOException {
/*     */     byte[] arrayOfByte5;
/* 270 */     int i = this.apReqMessg.ticket.encPart.getEType();
/* 271 */     Integer integer = this.apReqMessg.ticket.encPart.getKeyVersionNumber();
/* 272 */     EncryptionKey[] arrayOfEncryptionKey = paramKrb5AcceptCredential.getKrb5EncryptionKeys(this.apReqMessg.ticket.sname);
/* 273 */     EncryptionKey encryptionKey = EncryptionKey.findKey(i, integer, arrayOfEncryptionKey);
/*     */     
/* 275 */     if (encryptionKey == null) {
/* 276 */       throw new KrbException(400, "Cannot find key of appropriate type to decrypt AP REP - " + 
/*     */           
/* 278 */           EType.toString(i));
/*     */     }
/*     */     
/* 281 */     byte[] arrayOfByte1 = this.apReqMessg.ticket.encPart.decrypt(encryptionKey, 2);
/*     */     
/* 283 */     byte[] arrayOfByte2 = this.apReqMessg.ticket.encPart.reset(arrayOfByte1);
/* 284 */     EncTicketPart encTicketPart = new EncTicketPart(arrayOfByte2);
/*     */     
/* 286 */     checkPermittedEType(encTicketPart.key.getEType());
/*     */     
/* 288 */     byte[] arrayOfByte3 = this.apReqMessg.authenticator.decrypt(encTicketPart.key, 11);
/*     */     
/* 290 */     byte[] arrayOfByte4 = this.apReqMessg.authenticator.reset(arrayOfByte3);
/* 291 */     this.authenticator = new Authenticator(arrayOfByte4);
/* 292 */     this.ctime = this.authenticator.ctime;
/* 293 */     this.cusec = this.authenticator.cusec;
/* 294 */     this.authenticator
/* 295 */       .ctime = this.authenticator.ctime.withMicroSeconds(this.authenticator.cusec);
/*     */     
/* 297 */     if (!this.authenticator.cname.equals(encTicketPart.cname)) {
/* 298 */       throw new KrbApErrException(36);
/*     */     }
/*     */     
/* 301 */     if (!this.authenticator.ctime.inClockSkew()) {
/* 302 */       throw new KrbApErrException(37);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 307 */       arrayOfByte5 = MessageDigest.getInstance("MD5").digest(this.apReqMessg.authenticator.cipher);
/* 308 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 309 */       throw new AssertionError("Impossible");
/*     */     } 
/*     */     
/* 312 */     char[] arrayOfChar = new char[arrayOfByte5.length * 2];
/* 313 */     for (byte b = 0; b < arrayOfByte5.length; b++) {
/* 314 */       arrayOfChar[2 * b] = hexConst[(arrayOfByte5[b] & 0xFF) >> 4];
/* 315 */       arrayOfChar[2 * b + 1] = hexConst[arrayOfByte5[b] & 0xF];
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 320 */     AuthTimeWithHash authTimeWithHash = new AuthTimeWithHash(this.authenticator.cname.toString(), this.apReqMessg.ticket.sname.toString(), this.authenticator.ctime.getSeconds(), this.authenticator.cusec, new String(arrayOfChar));
/*     */ 
/*     */     
/* 323 */     rcache.checkAndStore(KerberosTime.now(), authTimeWithHash);
/*     */     
/* 325 */     if (paramInetAddress != null) {
/*     */       
/* 327 */       HostAddress hostAddress = new HostAddress(paramInetAddress);
/* 328 */       if (encTicketPart.caddr != null && 
/* 329 */         !encTicketPart.caddr.inList(hostAddress)) {
/* 330 */         if (DEBUG) {
/* 331 */           System.out.println(">>> KrbApReq: initiator is " + hostAddress
/* 332 */               .getInetAddress() + ", but caddr is " + 
/*     */               
/* 334 */               Arrays.toString((Object[])encTicketPart.caddr
/* 335 */                 .getInetAddresses()));
/*     */         }
/* 337 */         throw new KrbApErrException(38);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 347 */     KerberosTime kerberosTime = KerberosTime.now();
/*     */     
/* 349 */     if ((encTicketPart.starttime != null && encTicketPart.starttime
/* 350 */       .greaterThanWRTClockSkew(kerberosTime)) || encTicketPart.flags
/* 351 */       .get(7)) {
/* 352 */       throw new KrbApErrException(33);
/*     */     }
/*     */ 
/*     */     
/* 356 */     if (encTicketPart.endtime != null && kerberosTime
/* 357 */       .greaterThanWRTClockSkew(encTicketPart.endtime)) {
/* 358 */       throw new KrbApErrException(32);
/*     */     }
/*     */     
/* 361 */     this.creds = new Credentials(this.apReqMessg.ticket, this.authenticator.cname, null, this.apReqMessg.ticket.sname, null, encTicketPart.key, encTicketPart.flags, encTicketPart.authtime, encTicketPart.starttime, encTicketPart.endtime, encTicketPart.renewTill, encTicketPart.caddr, encTicketPart.authorizationData);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     if (DEBUG) {
/* 376 */       System.out.println(">>> KrbApReq: authenticate succeed.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials getCreds() {
/* 385 */     return this.creds;
/*     */   }
/*     */   
/*     */   KerberosTime getCtime() {
/* 389 */     if (this.ctime != null)
/* 390 */       return this.ctime; 
/* 391 */     return this.authenticator.ctime;
/*     */   }
/*     */   
/*     */   int cusec() {
/* 395 */     return this.cusec;
/*     */   }
/*     */   
/*     */   APOptions getAPOptions() throws KrbException, IOException {
/* 399 */     if (this.apReqMessg == null)
/* 400 */       decode(); 
/* 401 */     if (this.apReqMessg != null)
/* 402 */       return this.apReqMessg.apOptions; 
/* 403 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getMutualAuthRequired() throws KrbException, IOException {
/* 413 */     if (this.apReqMessg == null)
/* 414 */       decode(); 
/* 415 */     if (this.apReqMessg != null)
/* 416 */       return this.apReqMessg.apOptions.get(2); 
/* 417 */     return false;
/*     */   }
/*     */   
/*     */   boolean useSessionKey() throws KrbException, IOException {
/* 421 */     if (this.apReqMessg == null)
/* 422 */       decode(); 
/* 423 */     if (this.apReqMessg != null)
/* 424 */       return this.apReqMessg.apOptions.get(1); 
/* 425 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionKey getSubKey() {
/* 434 */     return this.authenticator.getSubKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getSeqNumber() {
/* 444 */     return this.authenticator.getSeqNumber();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Checksum getChecksum() {
/* 453 */     return this.authenticator.getChecksum();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getMessage() {
/* 460 */     return this.obuf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrincipalName getClient() {
/* 468 */     return this.creds.getClient();
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
/*     */   private void createMessage(APOptions paramAPOptions, Ticket paramTicket, EncryptionKey paramEncryptionKey1, PrincipalName paramPrincipalName, Checksum paramChecksum, KerberosTime paramKerberosTime, EncryptionKey paramEncryptionKey2, SeqNumber paramSeqNumber, AuthorizationData paramAuthorizationData, int paramInt) throws Asn1Exception, IOException, KdcErrException, KrbCryptoException {
/* 484 */     Integer integer = null;
/*     */     
/* 486 */     if (paramSeqNumber != null) {
/* 487 */       integer = new Integer(paramSeqNumber.current());
/*     */     }
/* 489 */     this
/*     */ 
/*     */       
/* 492 */       .authenticator = new Authenticator(paramPrincipalName, paramChecksum, paramKerberosTime.getMicroSeconds(), paramKerberosTime, paramEncryptionKey2, integer, paramAuthorizationData);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 498 */     byte[] arrayOfByte = this.authenticator.asn1Encode();
/*     */     
/* 500 */     EncryptedData encryptedData = new EncryptedData(paramEncryptionKey1, arrayOfByte, paramInt);
/*     */ 
/*     */     
/* 503 */     this.apReqMessg = new APReq(paramAPOptions, paramTicket, encryptedData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkPermittedEType(int paramInt) throws KrbException {
/* 509 */     int[] arrayOfInt = EType.getDefaults("permitted_enctypes");
/* 510 */     if (!EType.isSupported(paramInt, arrayOfInt))
/* 511 */       throw new KrbException(EType.toString(paramInt) + " encryption type not in permitted_enctypes list"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KrbApReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */