/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.Checksum;
/*     */ import sun.security.krb5.RealmException;
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
/*     */ public class KRBSafe
/*     */ {
/*     */   public int pvno;
/*     */   public int msgType;
/*     */   public KRBSafeBody safeBody;
/*     */   public Checksum cksum;
/*     */   
/*     */   public KRBSafe(KRBSafeBody paramKRBSafeBody, Checksum paramChecksum) {
/*  66 */     this.pvno = 5;
/*  67 */     this.msgType = 20;
/*  68 */     this.safeBody = paramKRBSafeBody;
/*  69 */     this.cksum = paramChecksum;
/*     */   }
/*     */ 
/*     */   
/*     */   public KRBSafe(byte[] paramArrayOfbyte) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/*  74 */     init(new DerValue(paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */   
/*     */   public KRBSafe(DerValue paramDerValue) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/*  79 */     init(paramDerValue);
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
/*     */   private void init(DerValue paramDerValue) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/*  94 */     if ((paramDerValue.getTag() & 0x1F) != 20 || paramDerValue
/*  95 */       .isApplication() != true || paramDerValue
/*  96 */       .isConstructed() != true)
/*  97 */       throw new Asn1Exception(906); 
/*  98 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/*  99 */     if (derValue1.getTag() != 48)
/* 100 */       throw new Asn1Exception(906); 
/* 101 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 102 */     if ((derValue2.getTag() & 0x1F) == 0) {
/* 103 */       this.pvno = derValue2.getData().getBigInteger().intValue();
/* 104 */       if (this.pvno != 5) {
/* 105 */         throw new KrbApErrException(39);
/*     */       }
/*     */     } else {
/* 108 */       throw new Asn1Exception(906);
/* 109 */     }  derValue2 = derValue1.getData().getDerValue();
/* 110 */     if ((derValue2.getTag() & 0x1F) == 1) {
/* 111 */       this.msgType = derValue2.getData().getBigInteger().intValue();
/* 112 */       if (this.msgType != 20) {
/* 113 */         throw new KrbApErrException(40);
/*     */       }
/*     */     } else {
/*     */       
/* 117 */       throw new Asn1Exception(906);
/* 118 */     }  this.safeBody = KRBSafeBody.parse(derValue1.getData(), (byte)2, false);
/* 119 */     this.cksum = Checksum.parse(derValue1.getData(), (byte)3, false);
/* 120 */     if (derValue1.getData().available() > 0) {
/* 121 */       throw new Asn1Exception(906);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 131 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 132 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 133 */     derOutputStream1.putInteger(BigInteger.valueOf(this.pvno));
/* 134 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)0), derOutputStream1);
/* 135 */     derOutputStream1 = new DerOutputStream();
/* 136 */     derOutputStream1.putInteger(BigInteger.valueOf(this.msgType));
/* 137 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)1), derOutputStream1);
/* 138 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)2), this.safeBody.asn1Encode());
/* 139 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)3), this.cksum.asn1Encode());
/* 140 */     derOutputStream1 = new DerOutputStream();
/* 141 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 142 */     derOutputStream2 = new DerOutputStream();
/* 143 */     derOutputStream2.write(DerValue.createTag((byte)64, true, (byte)20), derOutputStream1);
/* 144 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/KRBSafe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */