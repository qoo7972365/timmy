/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.EncryptedData;
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
/*     */ public class APRep
/*     */ {
/*     */   public int pvno;
/*     */   public int msgType;
/*     */   public EncryptedData encPart;
/*     */   
/*     */   public APRep(EncryptedData paramEncryptedData) {
/*  63 */     this.pvno = 5;
/*  64 */     this.msgType = 15;
/*  65 */     this.encPart = paramEncryptedData;
/*     */   }
/*     */ 
/*     */   
/*     */   public APRep(byte[] paramArrayOfbyte) throws Asn1Exception, KrbApErrException, IOException {
/*  70 */     init(new DerValue(paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */   
/*     */   public APRep(DerValue paramDerValue) throws Asn1Exception, KrbApErrException, IOException {
/*  75 */     init(paramDerValue);
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
/*     */   private void init(DerValue paramDerValue) throws Asn1Exception, KrbApErrException, IOException {
/*  89 */     if ((paramDerValue.getTag() & 0x1F) != 15 || paramDerValue
/*  90 */       .isApplication() != true || paramDerValue
/*  91 */       .isConstructed() != true) {
/*  92 */       throw new Asn1Exception(906);
/*     */     }
/*  94 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/*  95 */     if (derValue1.getTag() != 48) {
/*  96 */       throw new Asn1Exception(906);
/*     */     }
/*  98 */     DerValue derValue2 = derValue1.getData().getDerValue();
/*  99 */     if ((derValue2.getTag() & 0x1F) != 0) {
/* 100 */       throw new Asn1Exception(906);
/*     */     }
/* 102 */     this.pvno = derValue2.getData().getBigInteger().intValue();
/* 103 */     if (this.pvno != 5) {
/* 104 */       throw new KrbApErrException(39);
/*     */     }
/* 106 */     derValue2 = derValue1.getData().getDerValue();
/* 107 */     if ((derValue2.getTag() & 0x1F) != 1) {
/* 108 */       throw new Asn1Exception(906);
/*     */     }
/* 110 */     this.msgType = derValue2.getData().getBigInteger().intValue();
/* 111 */     if (this.msgType != 15) {
/* 112 */       throw new KrbApErrException(40);
/*     */     }
/* 114 */     this.encPart = EncryptedData.parse(derValue1.getData(), (byte)2, false);
/* 115 */     if (derValue1.getData().available() > 0) {
/* 116 */       throw new Asn1Exception(906);
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
/* 127 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 128 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 129 */     derOutputStream2.putInteger(BigInteger.valueOf(this.pvno));
/* 130 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/* 131 */     derOutputStream2 = new DerOutputStream();
/* 132 */     derOutputStream2.putInteger(BigInteger.valueOf(this.msgType));
/* 133 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/* 134 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), this.encPart.asn1Encode());
/* 135 */     derOutputStream2 = new DerOutputStream();
/* 136 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 137 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 138 */     derOutputStream3.write(DerValue.createTag((byte)64, true, (byte)15), derOutputStream2);
/* 139 */     return derOutputStream3.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/APRep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */