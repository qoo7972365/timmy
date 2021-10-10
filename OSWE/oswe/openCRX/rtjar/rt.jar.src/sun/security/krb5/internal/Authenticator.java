/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Vector;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.Checksum;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Authenticator
/*     */ {
/*     */   public int authenticator_vno;
/*     */   public PrincipalName cname;
/*     */   Checksum cksum;
/*     */   public int cusec;
/*     */   public KerberosTime ctime;
/*     */   EncryptionKey subKey;
/*     */   Integer seqNumber;
/*     */   public AuthorizationData authorizationData;
/*     */   
/*     */   public Authenticator(PrincipalName paramPrincipalName, Checksum paramChecksum, int paramInt, KerberosTime paramKerberosTime, EncryptionKey paramEncryptionKey, Integer paramInteger, AuthorizationData paramAuthorizationData) {
/*  80 */     this.authenticator_vno = 5;
/*  81 */     this.cname = paramPrincipalName;
/*  82 */     this.cksum = paramChecksum;
/*  83 */     this.cusec = paramInt;
/*  84 */     this.ctime = paramKerberosTime;
/*  85 */     this.subKey = paramEncryptionKey;
/*  86 */     this.seqNumber = paramInteger;
/*  87 */     this.authorizationData = paramAuthorizationData;
/*     */   }
/*     */ 
/*     */   
/*     */   public Authenticator(byte[] paramArrayOfbyte) throws Asn1Exception, IOException, KrbApErrException, RealmException {
/*  92 */     init(new DerValue(paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */   
/*     */   public Authenticator(DerValue paramDerValue) throws Asn1Exception, IOException, KrbApErrException, RealmException {
/*  97 */     init(paramDerValue);
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
/*     */   private void init(DerValue paramDerValue) throws Asn1Exception, IOException, KrbApErrException, RealmException {
/* 114 */     if ((paramDerValue.getTag() & 0x1F) != 2 || paramDerValue
/* 115 */       .isApplication() != true || paramDerValue
/* 116 */       .isConstructed() != true) {
/* 117 */       throw new Asn1Exception(906);
/*     */     }
/* 119 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 120 */     if (derValue1.getTag() != 48) {
/* 121 */       throw new Asn1Exception(906);
/*     */     }
/* 123 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 124 */     if ((derValue2.getTag() & 0x1F) != 0) {
/* 125 */       throw new Asn1Exception(906);
/*     */     }
/* 127 */     this.authenticator_vno = derValue2.getData().getBigInteger().intValue();
/* 128 */     if (this.authenticator_vno != 5) {
/* 129 */       throw new KrbApErrException(39);
/*     */     }
/* 131 */     Realm realm = Realm.parse(derValue1.getData(), (byte)1, false);
/* 132 */     this.cname = PrincipalName.parse(derValue1.getData(), (byte)2, false, realm);
/* 133 */     this.cksum = Checksum.parse(derValue1.getData(), (byte)3, true);
/* 134 */     derValue2 = derValue1.getData().getDerValue();
/* 135 */     if ((derValue2.getTag() & 0x1F) == 4) {
/* 136 */       this.cusec = derValue2.getData().getBigInteger().intValue();
/*     */     } else {
/* 138 */       throw new Asn1Exception(906);
/*     */     } 
/* 140 */     this.ctime = KerberosTime.parse(derValue1.getData(), (byte)5, false);
/* 141 */     if (derValue1.getData().available() > 0) {
/* 142 */       this.subKey = EncryptionKey.parse(derValue1.getData(), (byte)6, true);
/*     */     } else {
/* 144 */       this.subKey = null;
/* 145 */       this.seqNumber = null;
/* 146 */       this.authorizationData = null;
/*     */     } 
/* 148 */     if (derValue1.getData().available() > 0) {
/* 149 */       if ((derValue1.getData().peekByte() & 0x1F) == 7) {
/* 150 */         derValue2 = derValue1.getData().getDerValue();
/* 151 */         if ((derValue2.getTag() & 0x1F) == 7) {
/* 152 */           this.seqNumber = new Integer(derValue2.getData().getBigInteger().intValue());
/*     */         }
/*     */       } 
/*     */     } else {
/* 156 */       this.seqNumber = null;
/* 157 */       this.authorizationData = null;
/*     */     } 
/* 159 */     if (derValue1.getData().available() > 0) {
/* 160 */       this.authorizationData = AuthorizationData.parse(derValue1.getData(), (byte)8, true);
/*     */     } else {
/* 162 */       this.authorizationData = null;
/*     */     } 
/* 164 */     if (derValue1.getData().available() > 0) {
/* 165 */       throw new Asn1Exception(906);
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
/* 176 */     Vector<DerValue> vector = new Vector();
/* 177 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 178 */     derOutputStream1.putInteger(BigInteger.valueOf(this.authenticator_vno));
/* 179 */     vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)0), derOutputStream1.toByteArray()));
/* 180 */     vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)1), this.cname.getRealm().asn1Encode()));
/* 181 */     vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)2), this.cname.asn1Encode()));
/* 182 */     if (this.cksum != null) {
/* 183 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)3), this.cksum.asn1Encode()));
/*     */     }
/* 185 */     derOutputStream1 = new DerOutputStream();
/* 186 */     derOutputStream1.putInteger(BigInteger.valueOf(this.cusec));
/* 187 */     vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)4), derOutputStream1.toByteArray()));
/* 188 */     vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)5), this.ctime.asn1Encode()));
/* 189 */     if (this.subKey != null) {
/* 190 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)6), this.subKey.asn1Encode()));
/*     */     }
/* 192 */     if (this.seqNumber != null) {
/* 193 */       derOutputStream1 = new DerOutputStream();
/*     */       
/* 195 */       derOutputStream1.putInteger(BigInteger.valueOf(this.seqNumber.longValue()));
/* 196 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)7), derOutputStream1.toByteArray()));
/*     */     } 
/* 198 */     if (this.authorizationData != null) {
/* 199 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)8), this.authorizationData.asn1Encode()));
/*     */     }
/* 201 */     DerValue[] arrayOfDerValue = new DerValue[vector.size()];
/* 202 */     vector.copyInto((Object[])arrayOfDerValue);
/* 203 */     derOutputStream1 = new DerOutputStream();
/* 204 */     derOutputStream1.putSequence(arrayOfDerValue);
/* 205 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 206 */     derOutputStream2.write(DerValue.createTag((byte)64, true, (byte)2), derOutputStream1);
/* 207 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */   
/*     */   public final Checksum getChecksum() {
/* 211 */     return this.cksum;
/*     */   }
/*     */   
/*     */   public final Integer getSeqNumber() {
/* 215 */     return this.seqNumber;
/*     */   }
/*     */   
/*     */   public final EncryptionKey getSubKey() {
/* 219 */     return this.subKey;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/Authenticator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */