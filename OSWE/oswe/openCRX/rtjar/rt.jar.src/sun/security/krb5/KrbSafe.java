/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.internal.HostAddress;
/*     */ import sun.security.krb5.internal.KRBSafe;
/*     */ import sun.security.krb5.internal.KRBSafeBody;
/*     */ import sun.security.krb5.internal.KdcErrException;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.KrbApErrException;
/*     */ import sun.security.krb5.internal.SeqNumber;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class KrbSafe
/*     */   extends KrbAppMessage
/*     */ {
/*     */   private byte[] obuf;
/*     */   private byte[] userData;
/*     */   
/*     */   public KrbSafe(byte[] paramArrayOfbyte, Credentials paramCredentials, EncryptionKey paramEncryptionKey, KerberosTime paramKerberosTime, SeqNumber paramSeqNumber, HostAddress paramHostAddress1, HostAddress paramHostAddress2) throws KrbException, IOException {
/*  52 */     EncryptionKey encryptionKey = null;
/*  53 */     if (paramEncryptionKey != null) {
/*  54 */       encryptionKey = paramEncryptionKey;
/*     */     } else {
/*  56 */       encryptionKey = paramCredentials.key;
/*     */     } 
/*  58 */     this.obuf = mk_safe(paramArrayOfbyte, encryptionKey, paramKerberosTime, paramSeqNumber, paramHostAddress1, paramHostAddress2);
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
/*     */   public KrbSafe(byte[] paramArrayOfbyte, Credentials paramCredentials, EncryptionKey paramEncryptionKey, SeqNumber paramSeqNumber, HostAddress paramHostAddress1, HostAddress paramHostAddress2, boolean paramBoolean1, boolean paramBoolean2) throws KrbException, IOException {
/*  77 */     KRBSafe kRBSafe = new KRBSafe(paramArrayOfbyte);
/*     */     
/*  79 */     EncryptionKey encryptionKey = null;
/*  80 */     if (paramEncryptionKey != null) {
/*  81 */       encryptionKey = paramEncryptionKey;
/*     */     } else {
/*  83 */       encryptionKey = paramCredentials.key;
/*     */     } 
/*  85 */     this.userData = rd_safe(kRBSafe, encryptionKey, paramSeqNumber, paramHostAddress1, paramHostAddress2, paramBoolean1, paramBoolean2, paramCredentials.client);
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
/*     */   public byte[] getMessage() {
/*  98 */     return this.obuf;
/*     */   }
/*     */   
/*     */   public byte[] getData() {
/* 102 */     return this.userData;
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
/*     */   private byte[] mk_safe(byte[] paramArrayOfbyte, EncryptionKey paramEncryptionKey, KerberosTime paramKerberosTime, SeqNumber paramSeqNumber, HostAddress paramHostAddress1, HostAddress paramHostAddress2) throws Asn1Exception, IOException, KdcErrException, KrbApErrException, KrbCryptoException {
/* 114 */     Integer integer1 = null;
/* 115 */     Integer integer2 = null;
/*     */     
/* 117 */     if (paramKerberosTime != null) {
/* 118 */       integer1 = new Integer(paramKerberosTime.getMicroSeconds());
/*     */     }
/* 120 */     if (paramSeqNumber != null) {
/* 121 */       integer2 = new Integer(paramSeqNumber.current());
/* 122 */       paramSeqNumber.step();
/*     */     } 
/*     */     
/* 125 */     KRBSafeBody kRBSafeBody = new KRBSafeBody(paramArrayOfbyte, paramKerberosTime, integer1, integer2, paramHostAddress1, paramHostAddress2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     byte[] arrayOfByte = kRBSafeBody.asn1Encode();
/* 135 */     Checksum checksum = new Checksum(Checksum.SAFECKSUMTYPE_DEFAULT, arrayOfByte, paramEncryptionKey, 15);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     KRBSafe kRBSafe = new KRBSafe(kRBSafeBody, checksum);
/*     */     
/* 144 */     arrayOfByte = kRBSafe.asn1Encode();
/*     */     
/* 146 */     return kRBSafe.asn1Encode();
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
/*     */   private byte[] rd_safe(KRBSafe paramKRBSafe, EncryptionKey paramEncryptionKey, SeqNumber paramSeqNumber, HostAddress paramHostAddress1, HostAddress paramHostAddress2, boolean paramBoolean1, boolean paramBoolean2, PrincipalName paramPrincipalName) throws Asn1Exception, KdcErrException, KrbApErrException, IOException, KrbCryptoException {
/* 160 */     byte[] arrayOfByte = paramKRBSafe.safeBody.asn1Encode();
/*     */     
/* 162 */     if (!paramKRBSafe.cksum.verifyKeyedChecksum(arrayOfByte, paramEncryptionKey, 15))
/*     */     {
/* 164 */       throw new KrbApErrException(41);
/*     */     }
/*     */ 
/*     */     
/* 168 */     check(paramKRBSafe.safeBody.timestamp, paramKRBSafe.safeBody.usec, paramKRBSafe.safeBody.seqNumber, paramKRBSafe.safeBody.sAddress, paramKRBSafe.safeBody.rAddress, paramSeqNumber, paramHostAddress1, paramHostAddress2, paramBoolean1, paramBoolean2, paramPrincipalName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     return paramKRBSafe.safeBody.userData;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KrbSafe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */