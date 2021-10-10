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
/*     */ 
/*     */ public class EncKrbPrivPart
/*     */ {
/*  60 */   public byte[] userData = null;
/*     */   
/*     */   public KerberosTime timestamp;
/*     */   
/*     */   public Integer usec;
/*     */   
/*     */   public Integer seqNumber;
/*     */   
/*     */   public HostAddress sAddress;
/*     */   
/*     */   public HostAddress rAddress;
/*     */ 
/*     */   
/*     */   public EncKrbPrivPart(byte[] paramArrayOfbyte, KerberosTime paramKerberosTime, Integer paramInteger1, Integer paramInteger2, HostAddress paramHostAddress1, HostAddress paramHostAddress2) {
/*  74 */     if (paramArrayOfbyte != null) {
/*  75 */       this.userData = (byte[])paramArrayOfbyte.clone();
/*     */     }
/*  77 */     this.timestamp = paramKerberosTime;
/*  78 */     this.usec = paramInteger1;
/*  79 */     this.seqNumber = paramInteger2;
/*  80 */     this.sAddress = paramHostAddress1;
/*  81 */     this.rAddress = paramHostAddress2;
/*     */   }
/*     */   
/*     */   public EncKrbPrivPart(byte[] paramArrayOfbyte) throws Asn1Exception, IOException {
/*  85 */     init(new DerValue(paramArrayOfbyte));
/*     */   }
/*     */   
/*     */   public EncKrbPrivPart(DerValue paramDerValue) throws Asn1Exception, IOException {
/*  89 */     init(paramDerValue);
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
/* 100 */     if ((paramDerValue.getTag() & 0x1F) != 28 || paramDerValue
/* 101 */       .isApplication() != true || paramDerValue
/* 102 */       .isConstructed() != true) {
/* 103 */       throw new Asn1Exception(906);
/*     */     }
/* 105 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 106 */     if (derValue1.getTag() != 48) {
/* 107 */       throw new Asn1Exception(906);
/*     */     }
/* 109 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 110 */     if ((derValue2.getTag() & 0x1F) == 0) {
/* 111 */       this.userData = derValue2.getData().getOctetString();
/*     */     } else {
/* 113 */       throw new Asn1Exception(906);
/*     */     } 
/* 115 */     this.timestamp = KerberosTime.parse(derValue1.getData(), (byte)1, true);
/* 116 */     if ((derValue1.getData().peekByte() & 0x1F) == 2) {
/* 117 */       derValue2 = derValue1.getData().getDerValue();
/* 118 */       this.usec = new Integer(derValue2.getData().getBigInteger().intValue());
/*     */     } else {
/* 120 */       this.usec = null;
/*     */     } 
/* 122 */     if ((derValue1.getData().peekByte() & 0x1F) == 3) {
/* 123 */       derValue2 = derValue1.getData().getDerValue();
/* 124 */       this.seqNumber = new Integer(derValue2.getData().getBigInteger().intValue());
/*     */     } else {
/* 126 */       this.seqNumber = null;
/*     */     } 
/* 128 */     this.sAddress = HostAddress.parse(derValue1.getData(), (byte)4, false);
/* 129 */     if (derValue1.getData().available() > 0) {
/* 130 */       this.rAddress = HostAddress.parse(derValue1.getData(), (byte)5, true);
/*     */     }
/* 132 */     if (derValue1.getData().available() > 0) {
/* 133 */       throw new Asn1Exception(906);
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
/* 144 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 145 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 147 */     derOutputStream1.putOctetString(this.userData);
/* 148 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)0), derOutputStream1);
/* 149 */     if (this.timestamp != null) {
/* 150 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)1), this.timestamp.asn1Encode());
/*     */     }
/* 152 */     if (this.usec != null) {
/* 153 */       derOutputStream1 = new DerOutputStream();
/* 154 */       derOutputStream1.putInteger(BigInteger.valueOf(this.usec.intValue()));
/* 155 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)2), derOutputStream1);
/*     */     } 
/* 157 */     if (this.seqNumber != null) {
/* 158 */       derOutputStream1 = new DerOutputStream();
/*     */       
/* 160 */       derOutputStream1.putInteger(BigInteger.valueOf(this.seqNumber.longValue()));
/* 161 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)3), derOutputStream1);
/*     */     } 
/* 163 */     derOutputStream2.write(DerValue.createTag(-128, true, (byte)4), this.sAddress.asn1Encode());
/* 164 */     if (this.rAddress != null) {
/* 165 */       derOutputStream2.write(DerValue.createTag(-128, true, (byte)5), this.rAddress.asn1Encode());
/*     */     }
/* 167 */     derOutputStream1 = new DerOutputStream();
/* 168 */     derOutputStream1.write((byte)48, derOutputStream2);
/* 169 */     derOutputStream2 = new DerOutputStream();
/* 170 */     derOutputStream2.write(DerValue.createTag((byte)64, true, (byte)28), derOutputStream1);
/* 171 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/EncKrbPrivPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */