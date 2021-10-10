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
/*     */ 
/*     */ public class KRBPriv
/*     */ {
/*     */   public int pvno;
/*     */   public int msgType;
/*     */   public EncryptedData encPart;
/*     */   
/*     */   public KRBPriv(EncryptedData paramEncryptedData) {
/*  64 */     this.pvno = 5;
/*  65 */     this.msgType = 21;
/*  66 */     this.encPart = paramEncryptedData;
/*     */   }
/*     */ 
/*     */   
/*     */   public KRBPriv(byte[] paramArrayOfbyte) throws Asn1Exception, KrbApErrException, IOException {
/*  71 */     init(new DerValue(paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */   
/*     */   public KRBPriv(DerValue paramDerValue) throws Asn1Exception, KrbApErrException, IOException {
/*  76 */     init(paramDerValue);
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
/*     */   private void init(DerValue paramDerValue) throws Asn1Exception, KrbApErrException, IOException {
/*  91 */     if ((paramDerValue.getTag() & 0x1F) != 21 || paramDerValue
/*  92 */       .isApplication() != true || paramDerValue
/*  93 */       .isConstructed() != true)
/*  94 */       throw new Asn1Exception(906); 
/*  95 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/*  96 */     if (derValue1.getTag() != 48)
/*  97 */       throw new Asn1Exception(906); 
/*  98 */     DerValue derValue2 = derValue1.getData().getDerValue();
/*  99 */     if ((derValue2.getTag() & 0x1F) == 0) {
/* 100 */       this.pvno = derValue2.getData().getBigInteger().intValue();
/* 101 */       if (this.pvno != 5) {
/* 102 */         throw new KrbApErrException(39);
/*     */       }
/*     */     } else {
/*     */       
/* 106 */       throw new Asn1Exception(906);
/* 107 */     }  derValue2 = derValue1.getData().getDerValue();
/* 108 */     if ((derValue2.getTag() & 0x1F) == 1) {
/* 109 */       this.msgType = derValue2.getData().getBigInteger().intValue();
/* 110 */       if (this.msgType != 21) {
/* 111 */         throw new KrbApErrException(40);
/*     */       }
/*     */     } else {
/* 114 */       throw new Asn1Exception(906);
/* 115 */     }  this.encPart = EncryptedData.parse(derValue1.getData(), (byte)3, false);
/* 116 */     if (derValue1.getData().available() > 0) {
/* 117 */       throw new Asn1Exception(906);
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
/* 128 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 129 */     derOutputStream1.putInteger(BigInteger.valueOf(this.pvno));
/* 130 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 131 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)0), derOutputStream1);
/* 132 */     derOutputStream1 = new DerOutputStream();
/* 133 */     derOutputStream1.putInteger(BigInteger.valueOf(this.msgType));
/* 134 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)1), derOutputStream1);
/* 135 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)3), this.encPart.asn1Encode());
/* 136 */     derOutputStream1 = new DerOutputStream();
/* 137 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 138 */     derOutputStream2 = new DerOutputStream();
/* 139 */     derOutputStream2.write(DerValue.createTag((byte)64, true, (byte)21), derOutputStream1);
/* 140 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/KRBPriv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */