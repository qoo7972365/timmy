/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.util.DerOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PAEncTSEnc
/*     */ {
/*     */   public KerberosTime pATimeStamp;
/*     */   public Integer pAUSec;
/*     */   
/*     */   public PAEncTSEnc(KerberosTime paramKerberosTime, Integer paramInteger) {
/*  63 */     this.pATimeStamp = paramKerberosTime;
/*  64 */     this.pAUSec = paramInteger;
/*     */   }
/*     */   
/*     */   public PAEncTSEnc() {
/*  68 */     KerberosTime kerberosTime = KerberosTime.now();
/*  69 */     this.pATimeStamp = kerberosTime;
/*  70 */     this.pAUSec = new Integer(kerberosTime.getMicroSeconds());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PAEncTSEnc(DerValue paramDerValue) throws Asn1Exception, IOException {
/*  81 */     if (paramDerValue.getTag() != 48) {
/*  82 */       throw new Asn1Exception(906);
/*     */     }
/*  84 */     this.pATimeStamp = KerberosTime.parse(paramDerValue.getData(), (byte)0, false);
/*  85 */     if (paramDerValue.getData().available() > 0) {
/*  86 */       DerValue derValue = paramDerValue.getData().getDerValue();
/*  87 */       if ((derValue.getTag() & 0x1F) == 1) {
/*  88 */         this.pAUSec = new Integer(derValue.getData().getBigInteger().intValue());
/*     */       } else {
/*  90 */         throw new Asn1Exception(906);
/*     */       } 
/*  92 */     }  if (paramDerValue.getData().available() > 0) {
/*  93 */       throw new Asn1Exception(906);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 104 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 105 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 106 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), this.pATimeStamp.asn1Encode());
/* 107 */     if (this.pAUSec != null) {
/* 108 */       derOutputStream2 = new DerOutputStream();
/* 109 */       derOutputStream2.putInteger(BigInteger.valueOf(this.pAUSec.intValue()));
/* 110 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/*     */     } 
/* 112 */     derOutputStream2 = new DerOutputStream();
/* 113 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 114 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/PAEncTSEnc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */