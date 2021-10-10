/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbException;
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
/*     */ public class EncTicketPart
/*     */ {
/*     */   public TicketFlags flags;
/*     */   public EncryptionKey key;
/*     */   public PrincipalName cname;
/*     */   public TransitedEncoding transited;
/*     */   public KerberosTime authtime;
/*     */   public KerberosTime starttime;
/*     */   public KerberosTime endtime;
/*     */   public KerberosTime renewTill;
/*     */   public HostAddresses caddr;
/*     */   public AuthorizationData authorizationData;
/*     */   
/*     */   public EncTicketPart(TicketFlags paramTicketFlags, EncryptionKey paramEncryptionKey, PrincipalName paramPrincipalName, TransitedEncoding paramTransitedEncoding, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, KerberosTime paramKerberosTime4, HostAddresses paramHostAddresses, AuthorizationData paramAuthorizationData) {
/*  88 */     this.flags = paramTicketFlags;
/*  89 */     this.key = paramEncryptionKey;
/*  90 */     this.cname = paramPrincipalName;
/*  91 */     this.transited = paramTransitedEncoding;
/*  92 */     this.authtime = paramKerberosTime1;
/*  93 */     this.starttime = paramKerberosTime2;
/*  94 */     this.endtime = paramKerberosTime3;
/*  95 */     this.renewTill = paramKerberosTime4;
/*  96 */     this.caddr = paramHostAddresses;
/*  97 */     this.authorizationData = paramAuthorizationData;
/*     */   }
/*     */ 
/*     */   
/*     */   public EncTicketPart(byte[] paramArrayOfbyte) throws Asn1Exception, KrbException, IOException {
/* 102 */     init(new DerValue(paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */   
/*     */   public EncTicketPart(DerValue paramDerValue) throws Asn1Exception, KrbException, IOException {
/* 107 */     init(paramDerValue);
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
/*     */   private static String getHexBytes(byte[] paramArrayOfbyte, int paramInt) throws IOException {
/* 120 */     StringBuffer stringBuffer = new StringBuffer();
/* 121 */     for (byte b = 0; b < paramInt; b++) {
/*     */       
/* 123 */       int i = paramArrayOfbyte[b] >> 4 & 0xF;
/* 124 */       int j = paramArrayOfbyte[b] & 0xF;
/*     */       
/* 126 */       stringBuffer.append(Integer.toHexString(i));
/* 127 */       stringBuffer.append(Integer.toHexString(j));
/* 128 */       stringBuffer.append(' ');
/*     */     } 
/* 130 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(DerValue paramDerValue) throws Asn1Exception, IOException, RealmException {
/* 137 */     this.renewTill = null;
/* 138 */     this.caddr = null;
/* 139 */     this.authorizationData = null;
/* 140 */     if ((paramDerValue.getTag() & 0x1F) != 3 || paramDerValue
/* 141 */       .isApplication() != true || paramDerValue
/* 142 */       .isConstructed() != true) {
/* 143 */       throw new Asn1Exception(906);
/*     */     }
/* 145 */     DerValue derValue = paramDerValue.getData().getDerValue();
/* 146 */     if (derValue.getTag() != 48) {
/* 147 */       throw new Asn1Exception(906);
/*     */     }
/* 149 */     this.flags = TicketFlags.parse(derValue.getData(), (byte)0, false);
/* 150 */     this.key = EncryptionKey.parse(derValue.getData(), (byte)1, false);
/* 151 */     Realm realm = Realm.parse(derValue.getData(), (byte)2, false);
/* 152 */     this.cname = PrincipalName.parse(derValue.getData(), (byte)3, false, realm);
/* 153 */     this.transited = TransitedEncoding.parse(derValue.getData(), (byte)4, false);
/* 154 */     this.authtime = KerberosTime.parse(derValue.getData(), (byte)5, false);
/* 155 */     this.starttime = KerberosTime.parse(derValue.getData(), (byte)6, true);
/* 156 */     this.endtime = KerberosTime.parse(derValue.getData(), (byte)7, false);
/* 157 */     if (derValue.getData().available() > 0) {
/* 158 */       this.renewTill = KerberosTime.parse(derValue.getData(), (byte)8, true);
/*     */     }
/* 160 */     if (derValue.getData().available() > 0) {
/* 161 */       this.caddr = HostAddresses.parse(derValue.getData(), (byte)9, true);
/*     */     }
/* 163 */     if (derValue.getData().available() > 0) {
/* 164 */       this.authorizationData = AuthorizationData.parse(derValue.getData(), (byte)10, true);
/*     */     }
/* 166 */     if (derValue.getData().available() > 0) {
/* 167 */       throw new Asn1Exception(906);
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
/* 179 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 180 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 181 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), this.flags
/* 182 */         .asn1Encode());
/* 183 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), this.key
/* 184 */         .asn1Encode());
/* 185 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), this.cname
/* 186 */         .getRealm().asn1Encode());
/* 187 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)3), this.cname
/* 188 */         .asn1Encode());
/* 189 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)4), this.transited
/* 190 */         .asn1Encode());
/* 191 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)5), this.authtime
/* 192 */         .asn1Encode());
/* 193 */     if (this.starttime != null) {
/* 194 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)6), this.starttime
/* 195 */           .asn1Encode());
/*     */     }
/* 197 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)7), this.endtime
/* 198 */         .asn1Encode());
/*     */     
/* 200 */     if (this.renewTill != null) {
/* 201 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)8), this.renewTill
/* 202 */           .asn1Encode());
/*     */     }
/*     */     
/* 205 */     if (this.caddr != null) {
/* 206 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)9), this.caddr
/* 207 */           .asn1Encode());
/*     */     }
/*     */     
/* 210 */     if (this.authorizationData != null) {
/* 211 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)10), this.authorizationData
/* 212 */           .asn1Encode());
/*     */     }
/* 214 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 215 */     derOutputStream1 = new DerOutputStream();
/* 216 */     derOutputStream1.write(DerValue.createTag((byte)64, true, (byte)3), derOutputStream2);
/*     */     
/* 218 */     return derOutputStream1.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/EncTicketPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */