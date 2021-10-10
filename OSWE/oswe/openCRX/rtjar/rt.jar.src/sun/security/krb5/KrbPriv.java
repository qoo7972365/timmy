/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.internal.EncKrbPrivPart;
/*     */ import sun.security.krb5.internal.HostAddress;
/*     */ import sun.security.krb5.internal.KRBPriv;
/*     */ import sun.security.krb5.internal.KdcErrException;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.KrbApErrException;
/*     */ import sun.security.krb5.internal.SeqNumber;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class KrbPriv
/*     */   extends KrbAppMessage
/*     */ {
/*     */   private byte[] obuf;
/*     */   private byte[] userData;
/*     */   
/*     */   private KrbPriv(byte[] paramArrayOfbyte, Credentials paramCredentials, EncryptionKey paramEncryptionKey, KerberosTime paramKerberosTime, SeqNumber paramSeqNumber, HostAddress paramHostAddress1, HostAddress paramHostAddress2) throws KrbException, IOException {
/*  53 */     EncryptionKey encryptionKey = null;
/*  54 */     if (paramEncryptionKey != null) {
/*  55 */       encryptionKey = paramEncryptionKey;
/*     */     } else {
/*  57 */       encryptionKey = paramCredentials.key;
/*     */     } 
/*  59 */     this.obuf = mk_priv(paramArrayOfbyte, encryptionKey, paramKerberosTime, paramSeqNumber, paramHostAddress1, paramHostAddress2);
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
/*     */   private KrbPriv(byte[] paramArrayOfbyte, Credentials paramCredentials, EncryptionKey paramEncryptionKey, SeqNumber paramSeqNumber, HostAddress paramHostAddress1, HostAddress paramHostAddress2, boolean paramBoolean1, boolean paramBoolean2) throws KrbException, IOException {
/*  79 */     KRBPriv kRBPriv = new KRBPriv(paramArrayOfbyte);
/*  80 */     EncryptionKey encryptionKey = null;
/*  81 */     if (paramEncryptionKey != null) {
/*  82 */       encryptionKey = paramEncryptionKey;
/*     */     } else {
/*  84 */       encryptionKey = paramCredentials.key;
/*  85 */     }  this.userData = rd_priv(kRBPriv, encryptionKey, paramSeqNumber, paramHostAddress1, paramHostAddress2, paramBoolean1, paramBoolean2, paramCredentials.client);
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
/*     */   public byte[] getMessage() throws KrbException {
/*  97 */     return this.obuf;
/*     */   }
/*     */   
/*     */   public byte[] getData() {
/* 101 */     return this.userData;
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
/*     */   private byte[] mk_priv(byte[] paramArrayOfbyte, EncryptionKey paramEncryptionKey, KerberosTime paramKerberosTime, SeqNumber paramSeqNumber, HostAddress paramHostAddress1, HostAddress paramHostAddress2) throws Asn1Exception, IOException, KdcErrException, KrbCryptoException {
/* 113 */     Integer integer1 = null;
/* 114 */     Integer integer2 = null;
/*     */     
/* 116 */     if (paramKerberosTime != null) {
/* 117 */       integer1 = new Integer(paramKerberosTime.getMicroSeconds());
/*     */     }
/* 119 */     if (paramSeqNumber != null) {
/* 120 */       integer2 = new Integer(paramSeqNumber.current());
/* 121 */       paramSeqNumber.step();
/*     */     } 
/*     */     
/* 124 */     EncKrbPrivPart encKrbPrivPart = new EncKrbPrivPart(paramArrayOfbyte, paramKerberosTime, integer1, integer2, paramHostAddress1, paramHostAddress2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     byte[] arrayOfByte = encKrbPrivPart.asn1Encode();
/*     */     
/* 135 */     EncryptedData encryptedData = new EncryptedData(paramEncryptionKey, arrayOfByte, 13);
/*     */ 
/*     */ 
/*     */     
/* 139 */     KRBPriv kRBPriv = new KRBPriv(encryptedData);
/*     */     
/* 141 */     arrayOfByte = kRBPriv.asn1Encode();
/*     */     
/* 143 */     return kRBPriv.asn1Encode();
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
/*     */   private byte[] rd_priv(KRBPriv paramKRBPriv, EncryptionKey paramEncryptionKey, SeqNumber paramSeqNumber, HostAddress paramHostAddress1, HostAddress paramHostAddress2, boolean paramBoolean1, boolean paramBoolean2, PrincipalName paramPrincipalName) throws Asn1Exception, KdcErrException, KrbApErrException, IOException, KrbCryptoException {
/* 157 */     byte[] arrayOfByte1 = paramKRBPriv.encPart.decrypt(paramEncryptionKey, 13);
/*     */     
/* 159 */     byte[] arrayOfByte2 = paramKRBPriv.encPart.reset(arrayOfByte1);
/* 160 */     DerValue derValue = new DerValue(arrayOfByte2);
/* 161 */     EncKrbPrivPart encKrbPrivPart = new EncKrbPrivPart(derValue);
/*     */     
/* 163 */     check(encKrbPrivPart.timestamp, encKrbPrivPart.usec, encKrbPrivPart.seqNumber, encKrbPrivPart.sAddress, encKrbPrivPart.rAddress, paramSeqNumber, paramHostAddress1, paramHostAddress2, paramBoolean1, paramBoolean2, paramPrincipalName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     return encKrbPrivPart.userData;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KrbPriv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */