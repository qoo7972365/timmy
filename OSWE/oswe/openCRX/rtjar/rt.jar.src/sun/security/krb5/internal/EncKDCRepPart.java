/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.Asn1Exception;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncKDCRepPart
/*     */ {
/*     */   public EncryptionKey key;
/*     */   public LastReq lastReq;
/*     */   public int nonce;
/*     */   public KerberosTime keyExpiration;
/*     */   public TicketFlags flags;
/*     */   public KerberosTime authtime;
/*     */   public KerberosTime starttime;
/*     */   public KerberosTime endtime;
/*     */   public KerberosTime renewTill;
/*     */   public PrincipalName sname;
/*     */   public HostAddresses caddr;
/*     */   public PAData[] pAData;
/*     */   public int msgType;
/*     */   
/*     */   public EncKDCRepPart(EncryptionKey paramEncryptionKey, LastReq paramLastReq, int paramInt1, KerberosTime paramKerberosTime1, TicketFlags paramTicketFlags, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, KerberosTime paramKerberosTime4, KerberosTime paramKerberosTime5, PrincipalName paramPrincipalName, HostAddresses paramHostAddresses, PAData[] paramArrayOfPAData, int paramInt2) {
/*  96 */     this.key = paramEncryptionKey;
/*  97 */     this.lastReq = paramLastReq;
/*  98 */     this.nonce = paramInt1;
/*  99 */     this.keyExpiration = paramKerberosTime1;
/* 100 */     this.flags = paramTicketFlags;
/* 101 */     this.authtime = paramKerberosTime2;
/* 102 */     this.starttime = paramKerberosTime3;
/* 103 */     this.endtime = paramKerberosTime4;
/* 104 */     this.renewTill = paramKerberosTime5;
/* 105 */     this.sname = paramPrincipalName;
/* 106 */     this.caddr = paramHostAddresses;
/* 107 */     this.pAData = paramArrayOfPAData;
/* 108 */     this.msgType = paramInt2;
/*     */   }
/*     */ 
/*     */   
/*     */   public EncKDCRepPart() {}
/*     */ 
/*     */   
/*     */   public EncKDCRepPart(byte[] paramArrayOfbyte, int paramInt) throws Asn1Exception, IOException, RealmException {
/* 116 */     init(new DerValue(paramArrayOfbyte), paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public EncKDCRepPart(DerValue paramDerValue, int paramInt) throws Asn1Exception, IOException, RealmException {
/* 121 */     init(paramDerValue, paramInt);
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
/*     */   protected void init(DerValue paramDerValue, int paramInt) throws Asn1Exception, IOException, RealmException {
/* 138 */     this.msgType = paramDerValue.getTag() & 0x1F;
/* 139 */     if (this.msgType != 25 && this.msgType != 26)
/*     */     {
/* 141 */       throw new Asn1Exception(906);
/*     */     }
/* 143 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 144 */     if (derValue1.getTag() != 48) {
/* 145 */       throw new Asn1Exception(906);
/*     */     }
/* 147 */     this.key = EncryptionKey.parse(derValue1.getData(), (byte)0, false);
/* 148 */     this.lastReq = LastReq.parse(derValue1.getData(), (byte)1, false);
/* 149 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 150 */     if ((derValue2.getTag() & 0x1F) == 2) {
/* 151 */       this.nonce = derValue2.getData().getBigInteger().intValue();
/*     */     } else {
/* 153 */       throw new Asn1Exception(906);
/*     */     } 
/* 155 */     this.keyExpiration = KerberosTime.parse(derValue1.getData(), (byte)3, true);
/* 156 */     this.flags = TicketFlags.parse(derValue1.getData(), (byte)4, false);
/* 157 */     this.authtime = KerberosTime.parse(derValue1.getData(), (byte)5, false);
/* 158 */     this.starttime = KerberosTime.parse(derValue1.getData(), (byte)6, true);
/* 159 */     this.endtime = KerberosTime.parse(derValue1.getData(), (byte)7, false);
/* 160 */     this.renewTill = KerberosTime.parse(derValue1.getData(), (byte)8, true);
/* 161 */     Realm realm = Realm.parse(derValue1.getData(), (byte)9, false);
/* 162 */     this.sname = PrincipalName.parse(derValue1.getData(), (byte)10, false, realm);
/* 163 */     if (derValue1.getData().available() > 0) {
/* 164 */       this.caddr = HostAddresses.parse(derValue1.getData(), (byte)11, true);
/*     */     }
/* 166 */     if (derValue1.getData().available() > 0) {
/* 167 */       this.pAData = PAData.parseSequence(derValue1.getData(), (byte)12, true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode(int paramInt) throws Asn1Exception, IOException {
/* 185 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 186 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 187 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)0), this.key
/* 188 */         .asn1Encode());
/* 189 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)1), this.lastReq
/* 190 */         .asn1Encode());
/* 191 */     derOutputStream2.putInteger(BigInteger.valueOf(this.nonce));
/* 192 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)2), derOutputStream2);
/*     */ 
/*     */     
/* 195 */     if (this.keyExpiration != null) {
/* 196 */       derOutputStream3.write(DerValue.createTag(-128, true, (byte)3), this.keyExpiration
/* 197 */           .asn1Encode());
/*     */     }
/* 199 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)4), this.flags
/* 200 */         .asn1Encode());
/* 201 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)5), this.authtime
/* 202 */         .asn1Encode());
/* 203 */     if (this.starttime != null) {
/* 204 */       derOutputStream3.write(DerValue.createTag(-128, true, (byte)6), this.starttime
/* 205 */           .asn1Encode());
/*     */     }
/* 207 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)7), this.endtime
/* 208 */         .asn1Encode());
/* 209 */     if (this.renewTill != null) {
/* 210 */       derOutputStream3.write(DerValue.createTag(-128, true, (byte)8), this.renewTill
/* 211 */           .asn1Encode());
/*     */     }
/* 213 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)9), this.sname
/* 214 */         .getRealm().asn1Encode());
/* 215 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)10), this.sname
/* 216 */         .asn1Encode());
/* 217 */     if (this.caddr != null) {
/* 218 */       derOutputStream3.write(DerValue.createTag(-128, true, (byte)11), this.caddr
/* 219 */           .asn1Encode());
/*     */     }
/* 221 */     if (this.pAData != null && this.pAData.length > 0) {
/* 222 */       derOutputStream2 = new DerOutputStream();
/* 223 */       for (byte b = 0; b < this.pAData.length; b++) {
/* 224 */         derOutputStream2.write(this.pAData[b].asn1Encode());
/*     */       }
/* 226 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 227 */       derOutputStream.write((byte)48, derOutputStream2);
/* 228 */       derOutputStream3.write(DerValue.createTag(-128, true, (byte)12), derOutputStream);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     derOutputStream2 = new DerOutputStream();
/* 235 */     derOutputStream2.write((byte)48, derOutputStream3);
/* 236 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 237 */     derOutputStream1.write(DerValue.createTag((byte)64, true, (byte)this.msgType), derOutputStream2);
/*     */     
/* 239 */     return derOutputStream1.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/EncKDCRepPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */