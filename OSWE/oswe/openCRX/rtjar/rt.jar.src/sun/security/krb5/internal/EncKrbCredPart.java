/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.Asn1Exception;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncKrbCredPart
/*     */ {
/*  62 */   public KrbCredInfo[] ticketInfo = null;
/*     */   
/*     */   public KerberosTime timeStamp;
/*     */   
/*     */   private Integer nonce;
/*     */   
/*     */   private Integer usec;
/*     */   
/*     */   private HostAddress sAddress;
/*     */   
/*     */   private HostAddresses rAddress;
/*     */ 
/*     */   
/*     */   public EncKrbCredPart(KrbCredInfo[] paramArrayOfKrbCredInfo, KerberosTime paramKerberosTime, Integer paramInteger1, Integer paramInteger2, HostAddress paramHostAddress, HostAddresses paramHostAddresses) throws IOException {
/*  76 */     if (paramArrayOfKrbCredInfo != null) {
/*  77 */       this.ticketInfo = new KrbCredInfo[paramArrayOfKrbCredInfo.length];
/*  78 */       for (byte b = 0; b < paramArrayOfKrbCredInfo.length; b++) {
/*  79 */         if (paramArrayOfKrbCredInfo[b] == null) {
/*  80 */           throw new IOException("Cannot create a EncKrbCredPart");
/*     */         }
/*  82 */         this.ticketInfo[b] = (KrbCredInfo)paramArrayOfKrbCredInfo[b].clone();
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     this.timeStamp = paramKerberosTime;
/*  87 */     this.usec = paramInteger1;
/*  88 */     this.nonce = paramInteger2;
/*  89 */     this.sAddress = paramHostAddress;
/*  90 */     this.rAddress = paramHostAddresses;
/*     */   }
/*     */ 
/*     */   
/*     */   public EncKrbCredPart(byte[] paramArrayOfbyte) throws Asn1Exception, IOException, RealmException {
/*  95 */     init(new DerValue(paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */   
/*     */   public EncKrbCredPart(DerValue paramDerValue) throws Asn1Exception, IOException, RealmException {
/* 100 */     init(paramDerValue);
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
/*     */   private void init(DerValue paramDerValue) throws Asn1Exception, IOException, RealmException {
/* 115 */     this.nonce = null;
/* 116 */     this.timeStamp = null;
/* 117 */     this.usec = null;
/* 118 */     this.sAddress = null;
/* 119 */     this.rAddress = null;
/* 120 */     if ((paramDerValue.getTag() & 0x1F) != 29 || paramDerValue
/* 121 */       .isApplication() != true || paramDerValue
/* 122 */       .isConstructed() != true) {
/* 123 */       throw new Asn1Exception(906);
/*     */     }
/* 125 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 126 */     if (derValue1.getTag() != 48) {
/* 127 */       throw new Asn1Exception(906);
/*     */     }
/*     */     
/* 130 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 131 */     if ((derValue2.getTag() & 0x1F) == 0) {
/* 132 */       DerValue[] arrayOfDerValue = derValue2.getData().getSequence(1);
/* 133 */       this.ticketInfo = new KrbCredInfo[arrayOfDerValue.length];
/* 134 */       for (byte b = 0; b < arrayOfDerValue.length; b++) {
/* 135 */         this.ticketInfo[b] = new KrbCredInfo(arrayOfDerValue[b]);
/*     */       }
/*     */     } else {
/* 138 */       throw new Asn1Exception(906);
/*     */     } 
/* 140 */     if (derValue1.getData().available() > 0 && (
/* 141 */       (byte)derValue1.getData().peekByte() & 0x1F) == 1) {
/* 142 */       derValue2 = derValue1.getData().getDerValue();
/* 143 */       this.nonce = new Integer(derValue2.getData().getBigInteger().intValue());
/*     */     } 
/*     */     
/* 146 */     if (derValue1.getData().available() > 0) {
/* 147 */       this.timeStamp = KerberosTime.parse(derValue1.getData(), (byte)2, true);
/*     */     }
/* 149 */     if (derValue1.getData().available() > 0 && (
/* 150 */       (byte)derValue1.getData().peekByte() & 0x1F) == 3) {
/* 151 */       derValue2 = derValue1.getData().getDerValue();
/* 152 */       this.usec = new Integer(derValue2.getData().getBigInteger().intValue());
/*     */     } 
/*     */     
/* 155 */     if (derValue1.getData().available() > 0) {
/* 156 */       this.sAddress = HostAddress.parse(derValue1.getData(), (byte)4, true);
/*     */     }
/* 158 */     if (derValue1.getData().available() > 0) {
/* 159 */       this.rAddress = HostAddresses.parse(derValue1.getData(), (byte)5, true);
/*     */     }
/* 161 */     if (derValue1.getData().available() > 0) {
/* 162 */       throw new Asn1Exception(906);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 174 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 175 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 176 */     DerValue[] arrayOfDerValue = new DerValue[this.ticketInfo.length];
/* 177 */     for (byte b = 0; b < this.ticketInfo.length; b++) {
/* 178 */       arrayOfDerValue[b] = new DerValue(this.ticketInfo[b].asn1Encode());
/*     */     }
/* 180 */     derOutputStream2.putSequence(arrayOfDerValue);
/* 181 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/*     */ 
/*     */     
/* 184 */     if (this.nonce != null) {
/* 185 */       derOutputStream2 = new DerOutputStream();
/* 186 */       derOutputStream2.putInteger(BigInteger.valueOf(this.nonce.intValue()));
/* 187 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/*     */     } 
/*     */     
/* 190 */     if (this.timeStamp != null) {
/* 191 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), this.timeStamp
/* 192 */           .asn1Encode());
/*     */     }
/* 194 */     if (this.usec != null) {
/* 195 */       derOutputStream2 = new DerOutputStream();
/* 196 */       derOutputStream2.putInteger(BigInteger.valueOf(this.usec.intValue()));
/* 197 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)3), derOutputStream2);
/*     */     } 
/*     */     
/* 200 */     if (this.sAddress != null) {
/* 201 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)4), this.sAddress
/* 202 */           .asn1Encode());
/*     */     }
/* 204 */     if (this.rAddress != null) {
/* 205 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)5), this.rAddress
/* 206 */           .asn1Encode());
/*     */     }
/* 208 */     derOutputStream2 = new DerOutputStream();
/* 209 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 210 */     derOutputStream1 = new DerOutputStream();
/* 211 */     derOutputStream1.write(DerValue.createTag((byte)64, true, (byte)29), derOutputStream2);
/*     */     
/* 213 */     return derOutputStream1.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/EncKrbCredPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */