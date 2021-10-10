/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Vector;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.EncryptionKey;
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
/*     */ 
/*     */ 
/*     */ public class EncAPRepPart
/*     */ {
/*     */   public KerberosTime ctime;
/*     */   public int cusec;
/*     */   EncryptionKey subKey;
/*     */   Integer seqNumber;
/*     */   
/*     */   public EncAPRepPart(KerberosTime paramKerberosTime, int paramInt, EncryptionKey paramEncryptionKey, Integer paramInteger) {
/*  69 */     this.ctime = paramKerberosTime;
/*  70 */     this.cusec = paramInt;
/*  71 */     this.subKey = paramEncryptionKey;
/*  72 */     this.seqNumber = paramInteger;
/*     */   }
/*     */ 
/*     */   
/*     */   public EncAPRepPart(byte[] paramArrayOfbyte) throws Asn1Exception, IOException {
/*  77 */     init(new DerValue(paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */   
/*     */   public EncAPRepPart(DerValue paramDerValue) throws Asn1Exception, IOException {
/*  82 */     init(paramDerValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(DerValue paramDerValue) throws Asn1Exception, IOException {
/*  93 */     if ((paramDerValue.getTag() & 0x1F) != 27 || paramDerValue
/*  94 */       .isApplication() != true || paramDerValue
/*  95 */       .isConstructed() != true) {
/*  96 */       throw new Asn1Exception(906);
/*     */     }
/*  98 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/*  99 */     if (derValue1.getTag() != 48) {
/* 100 */       throw new Asn1Exception(906);
/*     */     }
/* 102 */     this.ctime = KerberosTime.parse(derValue1.getData(), (byte)0, true);
/* 103 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 104 */     if ((derValue2.getTag() & 0x1F) == 1) {
/* 105 */       this.cusec = derValue2.getData().getBigInteger().intValue();
/*     */     } else {
/* 107 */       throw new Asn1Exception(906);
/*     */     } 
/* 109 */     if (derValue1.getData().available() > 0) {
/* 110 */       this.subKey = EncryptionKey.parse(derValue1.getData(), (byte)2, true);
/*     */     } else {
/* 112 */       this.subKey = null;
/* 113 */       this.seqNumber = null;
/*     */     } 
/* 115 */     if (derValue1.getData().available() > 0) {
/* 116 */       derValue2 = derValue1.getData().getDerValue();
/* 117 */       if ((derValue2.getTag() & 0x1F) != 3) {
/* 118 */         throw new Asn1Exception(906);
/*     */       }
/* 120 */       this.seqNumber = new Integer(derValue2.getData().getBigInteger().intValue());
/*     */     } else {
/* 122 */       this.seqNumber = null;
/*     */     } 
/* 124 */     if (derValue1.getData().available() > 0) {
/* 125 */       throw new Asn1Exception(906);
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
/* 136 */     Vector<DerValue> vector = new Vector();
/* 137 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 138 */     vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)0), this.ctime
/* 139 */           .asn1Encode()));
/* 140 */     derOutputStream1.putInteger(BigInteger.valueOf(this.cusec));
/* 141 */     vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)1), derOutputStream1
/* 142 */           .toByteArray()));
/* 143 */     if (this.subKey != null) {
/* 144 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)2), this.subKey
/* 145 */             .asn1Encode()));
/*     */     }
/* 147 */     if (this.seqNumber != null) {
/* 148 */       derOutputStream1 = new DerOutputStream();
/*     */       
/* 150 */       derOutputStream1.putInteger(BigInteger.valueOf(this.seqNumber.longValue()));
/* 151 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)3), derOutputStream1
/* 152 */             .toByteArray()));
/*     */     } 
/* 154 */     DerValue[] arrayOfDerValue = new DerValue[vector.size()];
/* 155 */     vector.copyInto((Object[])arrayOfDerValue);
/* 156 */     derOutputStream1 = new DerOutputStream();
/* 157 */     derOutputStream1.putSequence(arrayOfDerValue);
/* 158 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 159 */     derOutputStream2.write(DerValue.createTag((byte)64, true, (byte)27), derOutputStream1);
/*     */     
/* 161 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */   
/*     */   public final EncryptionKey getSubKey() {
/* 165 */     return this.subKey;
/*     */   }
/*     */   
/*     */   public final Integer getSeqNumber() {
/* 169 */     return this.seqNumber;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/EncAPRepPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */