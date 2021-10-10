/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.time.Instant;
/*     */ import java.util.Arrays;
/*     */ import sun.security.krb5.internal.ASReq;
/*     */ import sun.security.krb5.internal.HostAddresses;
/*     */ import sun.security.krb5.internal.KDCOptions;
/*     */ import sun.security.krb5.internal.KDCReqBody;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.PAData;
/*     */ import sun.security.krb5.internal.PAEncTSEnc;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KrbAsReq
/*     */ {
/*     */   private ASReq asReqMessg;
/*  48 */   private boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KrbAsReq(EncryptionKey paramEncryptionKey, KDCOptions paramKDCOptions, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, int[] paramArrayOfint, HostAddresses paramHostAddresses, PAData[] paramArrayOfPAData) throws KrbException, IOException {
/*  67 */     if (paramKDCOptions == null) {
/*  68 */       paramKDCOptions = new KDCOptions();
/*     */     }
/*     */ 
/*     */     
/*  72 */     if (paramKDCOptions.get(2) || paramKDCOptions
/*  73 */       .get(4) || paramKDCOptions
/*  74 */       .get(28) || paramKDCOptions
/*  75 */       .get(30) || paramKDCOptions
/*  76 */       .get(31))
/*     */     {
/*     */       
/*  79 */       throw new KrbException(101);
/*     */     }
/*  81 */     if (!paramKDCOptions.get(6))
/*     */     {
/*     */ 
/*     */       
/*  85 */       if (paramKerberosTime1 != null) paramKerberosTime1 = null;
/*     */     
/*     */     }
/*  88 */     PAData[] arrayOfPAData = null;
/*  89 */     if (paramEncryptionKey != null) {
/*  90 */       PAEncTSEnc pAEncTSEnc = new PAEncTSEnc();
/*  91 */       byte[] arrayOfByte = pAEncTSEnc.asn1Encode();
/*  92 */       EncryptedData encryptedData = new EncryptedData(paramEncryptionKey, arrayOfByte, 1);
/*     */       
/*  94 */       arrayOfPAData = new PAData[1];
/*  95 */       arrayOfPAData[0] = new PAData(2, encryptedData
/*  96 */           .asn1Encode());
/*     */     } 
/*  98 */     if (paramArrayOfPAData != null && paramArrayOfPAData.length > 0) {
/*  99 */       if (arrayOfPAData == null) {
/* 100 */         arrayOfPAData = new PAData[paramArrayOfPAData.length];
/*     */       } else {
/* 102 */         arrayOfPAData = Arrays.<PAData>copyOf(arrayOfPAData, arrayOfPAData.length + paramArrayOfPAData.length);
/*     */       } 
/* 104 */       System.arraycopy(paramArrayOfPAData, 0, arrayOfPAData, arrayOfPAData.length - paramArrayOfPAData.length, paramArrayOfPAData.length);
/*     */     } 
/*     */ 
/*     */     
/* 108 */     if (paramPrincipalName1.getRealm() == null) {
/* 109 */       throw new RealmException(601, "default realm not specified ");
/*     */     }
/*     */ 
/*     */     
/* 113 */     if (this.DEBUG) {
/* 114 */       System.out.println(">>> KrbAsReq creating message");
/*     */     }
/*     */     
/* 117 */     Config config = Config.getInstance();
/*     */ 
/*     */     
/* 120 */     if (paramHostAddresses == null && config.useAddresses()) {
/* 121 */       paramHostAddresses = HostAddresses.getLocalAddresses();
/*     */     }
/*     */     
/* 124 */     if (paramPrincipalName2 == null) {
/* 125 */       String str = paramPrincipalName1.getRealmAsString();
/* 126 */       paramPrincipalName2 = PrincipalName.tgsService(str, str);
/*     */     } 
/*     */     
/* 129 */     if (paramKerberosTime2 == null) {
/* 130 */       String str = config.get(new String[] { "libdefaults", "ticket_lifetime" });
/* 131 */       if (str != null) {
/* 132 */         paramKerberosTime2 = new KerberosTime(Instant.now().plusSeconds(Config.duration(str)));
/*     */       } else {
/* 134 */         paramKerberosTime2 = new KerberosTime(0L);
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     if (paramKerberosTime3 == null) {
/* 139 */       String str = config.get(new String[] { "libdefaults", "renew_lifetime" });
/* 140 */       if (str != null) {
/* 141 */         paramKerberosTime3 = new KerberosTime(Instant.now().plusSeconds(Config.duration(str)));
/*     */       }
/*     */     } 
/*     */     
/* 145 */     if (paramKerberosTime3 != null) {
/* 146 */       paramKDCOptions.set(8, true);
/* 147 */       if (paramKerberosTime2.greaterThan(paramKerberosTime3)) {
/* 148 */         paramKerberosTime3 = paramKerberosTime2;
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
/* 159 */     KDCReqBody kDCReqBody = new KDCReqBody(paramKDCOptions, paramPrincipalName1, paramPrincipalName2, paramKerberosTime1, paramKerberosTime2, paramKerberosTime3, Nonce.value(), paramArrayOfint, paramHostAddresses, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     this.asReqMessg = new ASReq(arrayOfPAData, kDCReqBody);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] encoding() throws IOException, Asn1Exception {
/* 171 */     return this.asReqMessg.asn1Encode();
/*     */   }
/*     */ 
/*     */   
/*     */   ASReq getMessage() {
/* 176 */     return this.asReqMessg;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KrbAsReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */