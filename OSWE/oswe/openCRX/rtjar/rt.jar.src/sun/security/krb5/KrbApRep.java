/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.internal.APRep;
/*     */ import sun.security.krb5.internal.EncAPRepPart;
/*     */ import sun.security.krb5.internal.KRBError;
/*     */ import sun.security.krb5.internal.KdcErrException;
/*     */ import sun.security.krb5.internal.KerberosTime;
/*     */ import sun.security.krb5.internal.KrbApErrException;
/*     */ import sun.security.krb5.internal.LocalSeqNumber;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KrbApRep
/*     */ {
/*     */   private byte[] obuf;
/*     */   private byte[] ibuf;
/*     */   private EncAPRepPart encPart;
/*     */   private APRep apRepMessg;
/*     */   
/*     */   public KrbApRep(KrbApReq paramKrbApReq, boolean paramBoolean, EncryptionKey paramEncryptionKey) throws KrbException, IOException {
/*  60 */     LocalSeqNumber localSeqNumber = new LocalSeqNumber();
/*     */     
/*  62 */     init(paramKrbApReq, paramEncryptionKey, localSeqNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KrbApRep(byte[] paramArrayOfbyte, Credentials paramCredentials, KrbApReq paramKrbApReq) throws KrbException, IOException {
/*  73 */     this(paramArrayOfbyte, paramCredentials);
/*  74 */     authenticate(paramKrbApReq);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(KrbApReq paramKrbApReq, EncryptionKey paramEncryptionKey, SeqNumber paramSeqNumber) throws KrbException, IOException {
/*  81 */     createMessage(
/*  82 */         (paramKrbApReq.getCreds()).key, paramKrbApReq
/*  83 */         .getCtime(), paramKrbApReq
/*  84 */         .cusec(), paramEncryptionKey, paramSeqNumber);
/*     */ 
/*     */     
/*  87 */     this.obuf = this.apRepMessg.asn1Encode();
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
/*     */   private KrbApRep(byte[] paramArrayOfbyte, Credentials paramCredentials) throws KrbException, IOException {
/* 100 */     this(new DerValue(paramArrayOfbyte), paramCredentials);
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
/*     */   private KrbApRep(DerValue paramDerValue, Credentials paramCredentials) throws KrbException, IOException {
/* 112 */     APRep aPRep = null;
/*     */     try {
/* 114 */       aPRep = new APRep(paramDerValue);
/* 115 */     } catch (Asn1Exception asn1Exception) {
/* 116 */       String str2; aPRep = null;
/* 117 */       KRBError kRBError = new KRBError(paramDerValue);
/* 118 */       String str1 = kRBError.getErrorString();
/*     */       
/* 120 */       if (str1.charAt(str1.length() - 1) == '\000') {
/* 121 */         str2 = str1.substring(0, str1.length() - 1);
/*     */       } else {
/* 123 */         str2 = str1;
/* 124 */       }  KrbException krbException = new KrbException(kRBError.getErrorCode(), str2);
/* 125 */       krbException.initCause(asn1Exception);
/* 126 */       throw krbException;
/*     */     } 
/*     */     
/* 129 */     byte[] arrayOfByte1 = aPRep.encPart.decrypt(paramCredentials.key, 12);
/*     */     
/* 131 */     byte[] arrayOfByte2 = aPRep.encPart.reset(arrayOfByte1);
/*     */     
/* 133 */     paramDerValue = new DerValue(arrayOfByte2);
/* 134 */     this.encPart = new EncAPRepPart(paramDerValue);
/*     */   }
/*     */ 
/*     */   
/*     */   private void authenticate(KrbApReq paramKrbApReq) throws KrbException, IOException {
/* 139 */     if (this.encPart.ctime.getSeconds() != paramKrbApReq.getCtime().getSeconds() || this.encPart.cusec != paramKrbApReq
/* 140 */       .getCtime().getMicroSeconds()) {
/* 141 */       throw new KrbApErrException(46);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionKey getSubKey() {
/* 151 */     return this.encPart.getSubKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getSeqNumber() {
/* 161 */     return this.encPart.getSeqNumber();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getMessage() {
/* 168 */     return this.obuf;
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
/*     */   private void createMessage(EncryptionKey paramEncryptionKey1, KerberosTime paramKerberosTime, int paramInt, EncryptionKey paramEncryptionKey2, SeqNumber paramSeqNumber) throws Asn1Exception, IOException, KdcErrException, KrbCryptoException {
/* 180 */     Integer integer = null;
/*     */     
/* 182 */     if (paramSeqNumber != null) {
/* 183 */       integer = new Integer(paramSeqNumber.current());
/*     */     }
/* 185 */     this.encPart = new EncAPRepPart(paramKerberosTime, paramInt, paramEncryptionKey2, integer);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     byte[] arrayOfByte = this.encPart.asn1Encode();
/*     */     
/* 192 */     EncryptedData encryptedData = new EncryptedData(paramEncryptionKey1, arrayOfByte, 12);
/*     */ 
/*     */     
/* 195 */     this.apRepMessg = new APRep(encryptedData);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/KrbApRep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */