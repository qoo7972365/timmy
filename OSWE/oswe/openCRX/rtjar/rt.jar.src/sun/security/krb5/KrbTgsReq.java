/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.time.Instant;
/*     */ import java.util.Arrays;
/*     */ import sun.security.krb5.internal.APOptions;
/*     */ import sun.security.krb5.internal.AuthorizationData;
/*     */ import sun.security.krb5.internal.HostAddresses;
/*     */ import sun.security.krb5.internal.KDCOptions;
/*     */ import sun.security.krb5.internal.KDCReqBody;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.PAData;
/*     */ import sun.security.krb5.internal.TGSReq;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.crypto.EType;
/*     */ import sun.security.krb5.internal.crypto.Nonce;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KrbTgsReq
/*     */ {
/*     */   private PrincipalName princName;
/*     */   private PrincipalName clientAlias;
/*     */   private PrincipalName servName;
/*     */   private PrincipalName serverAlias;
/*     */   private TGSReq tgsReqMessg;
/*     */   private KerberosTime ctime;
/*  53 */   private Ticket secondTicket = null;
/*     */   
/*     */   private boolean useSubkey = false;
/*     */   EncryptionKey tgsReqKey;
/*  57 */   private static final boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */   
/*     */   private byte[] obuf;
/*     */ 
/*     */   
/*     */   private byte[] ibuf;
/*     */ 
/*     */ 
/*     */   
/*     */   public KrbTgsReq(KDCOptions paramKDCOptions, Credentials paramCredentials, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, PrincipalName paramPrincipalName3, PrincipalName paramPrincipalName4, Ticket[] paramArrayOfTicket, PAData[] paramArrayOfPAData) throws KrbException, IOException {
/*  68 */     this(paramKDCOptions, paramCredentials, paramPrincipalName1, paramPrincipalName2, paramPrincipalName3, paramPrincipalName4, null, null, null, null, null, null, paramArrayOfTicket, null, paramArrayOfPAData);
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
/*     */   KrbTgsReq(KDCOptions paramKDCOptions, Credentials paramCredentials, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, int[] paramArrayOfint, HostAddresses paramHostAddresses, AuthorizationData paramAuthorizationData, Ticket[] paramArrayOfTicket, EncryptionKey paramEncryptionKey) throws KrbException, IOException {
/*  99 */     this(paramKDCOptions, paramCredentials, paramCredentials.getClient(), paramCredentials.getClientAlias(), paramPrincipalName1, paramPrincipalName2, paramKerberosTime1, paramKerberosTime2, paramKerberosTime3, paramArrayOfint, paramHostAddresses, paramAuthorizationData, paramArrayOfTicket, paramEncryptionKey, null);
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
/*     */   private KrbTgsReq(KDCOptions paramKDCOptions, Credentials paramCredentials, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, PrincipalName paramPrincipalName3, PrincipalName paramPrincipalName4, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, int[] paramArrayOfint, HostAddresses paramHostAddresses, AuthorizationData paramAuthorizationData, Ticket[] paramArrayOfTicket, EncryptionKey paramEncryptionKey, PAData[] paramArrayOfPAData) throws KrbException, IOException {
/* 121 */     this.princName = paramPrincipalName1;
/* 122 */     this.clientAlias = paramPrincipalName2;
/* 123 */     this.servName = paramPrincipalName3;
/* 124 */     this.serverAlias = paramPrincipalName4;
/* 125 */     this.ctime = KerberosTime.now();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     if (paramKDCOptions.get(1) && 
/* 131 */       !paramCredentials.flags.get(1)) {
/* 132 */       paramKDCOptions.set(1, false);
/*     */     }
/* 134 */     if (paramKDCOptions.get(2) && 
/* 135 */       !paramCredentials.flags.get(1)) {
/* 136 */       throw new KrbException(101);
/*     */     }
/* 138 */     if (paramKDCOptions.get(3) && 
/* 139 */       !paramCredentials.flags.get(3)) {
/* 140 */       throw new KrbException(101);
/*     */     }
/* 142 */     if (paramKDCOptions.get(4) && 
/* 143 */       !paramCredentials.flags.get(3)) {
/* 144 */       throw new KrbException(101);
/*     */     }
/* 146 */     if (paramKDCOptions.get(5) && 
/* 147 */       !paramCredentials.flags.get(5)) {
/* 148 */       throw new KrbException(101);
/*     */     }
/* 150 */     if (paramKDCOptions.get(8) && 
/* 151 */       !paramCredentials.flags.get(8)) {
/* 152 */       throw new KrbException(101);
/*     */     }
/*     */     
/* 155 */     if (paramKDCOptions.get(6))
/* 156 */     { if (!paramCredentials.flags.get(6)) {
/* 157 */         throw new KrbException(101);
/*     */       } }
/* 159 */     else if (paramKerberosTime1 != null) { paramKerberosTime1 = null; }
/*     */     
/* 161 */     if (paramKDCOptions.get(8))
/* 162 */     { if (!paramCredentials.flags.get(8)) {
/* 163 */         throw new KrbException(101);
/*     */       } }
/* 165 */     else if (paramKerberosTime3 != null) { paramKerberosTime3 = null; }
/*     */     
/* 167 */     if (paramKDCOptions.get(28) || paramKDCOptions.get(14)) {
/* 168 */       if (paramArrayOfTicket == null) {
/* 169 */         throw new KrbException(101);
/*     */       }
/*     */ 
/*     */       
/* 173 */       this.secondTicket = paramArrayOfTicket[0];
/*     */     }
/* 175 */     else if (paramArrayOfTicket != null) {
/* 176 */       paramArrayOfTicket = null;
/*     */     } 
/*     */     
/* 179 */     this.tgsReqMessg = createRequest(paramKDCOptions, paramCredentials.ticket, paramCredentials.key, this.ctime, this.princName, this.servName, paramKerberosTime1, paramKerberosTime2, paramKerberosTime3, paramArrayOfint, paramHostAddresses, paramAuthorizationData, paramArrayOfTicket, paramEncryptionKey, paramArrayOfPAData);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     this.obuf = this.tgsReqMessg.asn1Encode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     if (paramCredentials.flags.get(2)) {
/* 206 */       paramKDCOptions.set(2, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void send() throws IOException, KrbException {
/* 217 */     String str = null;
/* 218 */     if (this.servName != null)
/* 219 */       str = this.servName.getRealmString(); 
/* 220 */     KdcComm kdcComm = new KdcComm(str);
/* 221 */     this.ibuf = kdcComm.send(this.obuf);
/*     */   }
/*     */ 
/*     */   
/*     */   public KrbTgsRep getReply() throws KrbException, IOException {
/* 226 */     return new KrbTgsRep(this.ibuf, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials sendAndGetCreds() throws IOException, KrbException {
/* 234 */     KrbTgsRep krbTgsRep = null;
/* 235 */     Object object = null;
/* 236 */     send();
/* 237 */     krbTgsRep = getReply();
/* 238 */     return krbTgsRep.getCreds();
/*     */   }
/*     */   
/*     */   KerberosTime getCtime() {
/* 242 */     return this.ctime;
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
/*     */   private TGSReq createRequest(KDCOptions paramKDCOptions, Ticket paramTicket, EncryptionKey paramEncryptionKey1, KerberosTime paramKerberosTime1, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, KerberosTime paramKerberosTime4, int[] paramArrayOfint, HostAddresses paramHostAddresses, AuthorizationData paramAuthorizationData, Ticket[] paramArrayOfTicket, EncryptionKey paramEncryptionKey2, PAData[] paramArrayOfPAData) throws IOException, KrbException, UnknownHostException {
/*     */     PAData[] arrayOfPAData;
/* 262 */     KerberosTime kerberosTime = null;
/* 263 */     if (paramKerberosTime3 == null) {
/* 264 */       String str = Config.getInstance().get(new String[] { "libdefaults", "ticket_lifetime" });
/* 265 */       if (str != null) {
/* 266 */         kerberosTime = new KerberosTime(Instant.now().plusSeconds(Config.duration(str)));
/*     */       } else {
/* 268 */         kerberosTime = new KerberosTime(0L);
/*     */       } 
/*     */     } else {
/* 271 */       kerberosTime = paramKerberosTime3;
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
/* 283 */     this.tgsReqKey = paramEncryptionKey1;
/*     */     
/* 285 */     int[] arrayOfInt = null;
/* 286 */     if (paramArrayOfint == null) {
/* 287 */       arrayOfInt = EType.getDefaults("default_tgs_enctypes");
/*     */     } else {
/* 289 */       arrayOfInt = paramArrayOfint;
/*     */     } 
/*     */     
/* 292 */     EncryptionKey encryptionKey = null;
/* 293 */     EncryptedData encryptedData = null;
/* 294 */     if (paramAuthorizationData != null) {
/* 295 */       byte[] arrayOfByte = paramAuthorizationData.asn1Encode();
/* 296 */       if (paramEncryptionKey2 != null) {
/* 297 */         encryptionKey = paramEncryptionKey2;
/* 298 */         this.tgsReqKey = paramEncryptionKey2;
/* 299 */         this.useSubkey = true;
/* 300 */         encryptedData = new EncryptedData(encryptionKey, arrayOfByte, 5);
/*     */       } else {
/*     */         
/* 303 */         encryptedData = new EncryptedData(paramEncryptionKey1, arrayOfByte, 4);
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
/* 314 */     KDCReqBody kDCReqBody = new KDCReqBody(paramKDCOptions, paramPrincipalName1, paramPrincipalName2, paramKerberosTime2, kerberosTime, paramKerberosTime4, Nonce.value(), arrayOfInt, paramHostAddresses, encryptedData, paramArrayOfTicket);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     byte[] arrayOfByte1 = kDCReqBody.asn1Encode(12);
/*     */ 
/*     */     
/* 323 */     Checksum checksum = new Checksum(Checksum.CKSUMTYPE_DEFAULT, arrayOfByte1, paramEncryptionKey1, 6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 337 */     byte[] arrayOfByte2 = (new KrbApReq(new APOptions(), paramTicket, paramEncryptionKey1, paramPrincipalName1, checksum, paramKerberosTime1, encryptionKey, null, null)).getMessage();
/*     */     
/* 339 */     PAData pAData = new PAData(1, arrayOfByte2);
/*     */     
/* 341 */     if (paramArrayOfPAData != null) {
/* 342 */       arrayOfPAData = Arrays.<PAData>copyOf(paramArrayOfPAData, paramArrayOfPAData.length + 1);
/* 343 */       arrayOfPAData[paramArrayOfPAData.length] = pAData;
/*     */     } else {
/* 345 */       arrayOfPAData = new PAData[] { pAData };
/*     */     } 
/* 347 */     return new TGSReq(arrayOfPAData, kDCReqBody);
/*     */   }
/*     */   
/*     */   TGSReq getMessage() {
/* 351 */     return this.tgsReqMessg;
/*     */   }
/*     */   
/*     */   Ticket getSecondTicket() {
/* 355 */     return this.secondTicket;
/*     */   }
/*     */   
/*     */   PrincipalName getClientAlias() {
/* 359 */     return this.clientAlias;
/*     */   }
/*     */   
/*     */   PrincipalName getServerAlias() {
/* 363 */     return this.serverAlias;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void debug(String paramString) {}
/*     */ 
/*     */   
/*     */   boolean usedSubkey() {
/* 371 */     return this.useSubkey;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KrbTgsReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */