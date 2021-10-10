/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Vector;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.EncryptedData;
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
/*     */ public class KDCReqBody
/*     */ {
/*     */   public KDCOptions kdcOptions;
/*     */   public PrincipalName cname;
/*     */   public PrincipalName sname;
/*     */   public KerberosTime from;
/*     */   public KerberosTime till;
/*     */   public KerberosTime rtime;
/*     */   public HostAddresses addresses;
/*     */   private int nonce;
/*  82 */   private int[] eType = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EncryptedData encAuthorizationData;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Ticket[] additionalTickets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KDCReqBody(KDCOptions paramKDCOptions, PrincipalName paramPrincipalName1, PrincipalName paramPrincipalName2, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, int paramInt, int[] paramArrayOfint, HostAddresses paramHostAddresses, EncryptedData paramEncryptedData, Ticket[] paramArrayOfTicket) throws IOException {
/*  99 */     this.kdcOptions = paramKDCOptions;
/* 100 */     this.cname = paramPrincipalName1;
/* 101 */     this.sname = paramPrincipalName2;
/* 102 */     this.from = paramKerberosTime1;
/* 103 */     this.till = paramKerberosTime2;
/* 104 */     this.rtime = paramKerberosTime3;
/* 105 */     this.nonce = paramInt;
/* 106 */     if (paramArrayOfint != null) {
/* 107 */       this.eType = (int[])paramArrayOfint.clone();
/*     */     }
/* 109 */     this.addresses = paramHostAddresses;
/* 110 */     this.encAuthorizationData = paramEncryptedData;
/* 111 */     if (paramArrayOfTicket != null) {
/* 112 */       this.additionalTickets = new Ticket[paramArrayOfTicket.length];
/* 113 */       for (byte b = 0; b < paramArrayOfTicket.length; b++) {
/* 114 */         if (paramArrayOfTicket[b] == null) {
/* 115 */           throw new IOException("Cannot create a KDCReqBody");
/*     */         }
/* 117 */         this.additionalTickets[b] = (Ticket)paramArrayOfTicket[b].clone();
/*     */       } 
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
/*     */   public KDCReqBody(DerValue paramDerValue, int paramInt) throws Asn1Exception, RealmException, KrbException, IOException {
/* 135 */     this.addresses = null;
/* 136 */     this.encAuthorizationData = null;
/* 137 */     this.additionalTickets = null;
/* 138 */     if (paramDerValue.getTag() != 48) {
/* 139 */       throw new Asn1Exception(906);
/*     */     }
/* 141 */     this.kdcOptions = KDCOptions.parse(paramDerValue.getData(), (byte)0, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     this.cname = PrincipalName.parse(paramDerValue.getData(), (byte)1, true, new Realm("PLACEHOLDER"));
/*     */     
/* 149 */     if (paramInt != 10 && this.cname != null) {
/* 150 */       throw new Asn1Exception(906);
/*     */     }
/* 152 */     Realm realm = Realm.parse(paramDerValue.getData(), (byte)2, false);
/* 153 */     if (this.cname != null) {
/* 154 */       this
/* 155 */         .cname = new PrincipalName(this.cname.getNameType(), this.cname.getNameStrings(), realm);
/*     */     }
/* 157 */     this.sname = PrincipalName.parse(paramDerValue.getData(), (byte)3, true, realm);
/* 158 */     this.from = KerberosTime.parse(paramDerValue.getData(), (byte)4, true);
/* 159 */     this.till = KerberosTime.parse(paramDerValue.getData(), (byte)5, false);
/* 160 */     this.rtime = KerberosTime.parse(paramDerValue.getData(), (byte)6, true);
/* 161 */     DerValue derValue = paramDerValue.getData().getDerValue();
/* 162 */     if ((derValue.getTag() & 0x1F) == 7) {
/* 163 */       this.nonce = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/* 165 */       throw new Asn1Exception(906);
/*     */     } 
/* 167 */     derValue = paramDerValue.getData().getDerValue();
/* 168 */     Vector<Integer> vector = new Vector();
/* 169 */     if ((derValue.getTag() & 0x1F) == 8) {
/* 170 */       DerValue derValue1 = derValue.getData().getDerValue();
/*     */       
/* 172 */       if (derValue1.getTag() == 48) {
/* 173 */         while (derValue1.getData().available() > 0) {
/* 174 */           vector.addElement(Integer.valueOf(derValue1.getData().getBigInteger().intValue()));
/*     */         }
/* 176 */         this.eType = new int[vector.size()];
/* 177 */         for (byte b = 0; b < vector.size(); b++) {
/* 178 */           this.eType[b] = ((Integer)vector.elementAt(b)).intValue();
/*     */         }
/*     */       } else {
/* 181 */         throw new Asn1Exception(906);
/*     */       } 
/*     */     } else {
/* 184 */       throw new Asn1Exception(906);
/*     */     } 
/* 186 */     if (paramDerValue.getData().available() > 0) {
/* 187 */       this.addresses = HostAddresses.parse(paramDerValue.getData(), (byte)9, true);
/*     */     }
/* 189 */     if (paramDerValue.getData().available() > 0) {
/* 190 */       this.encAuthorizationData = EncryptedData.parse(paramDerValue.getData(), (byte)10, true);
/*     */     }
/* 192 */     if (paramDerValue.getData().available() > 0) {
/* 193 */       Vector<Ticket> vector1 = new Vector();
/* 194 */       derValue = paramDerValue.getData().getDerValue();
/* 195 */       if ((derValue.getTag() & 0x1F) == 11) {
/* 196 */         DerValue derValue1 = derValue.getData().getDerValue();
/* 197 */         if (derValue1.getTag() == 48) {
/* 198 */           while (derValue1.getData().available() > 0) {
/* 199 */             vector1.addElement(new Ticket(derValue1.getData().getDerValue()));
/*     */           }
/*     */         } else {
/* 202 */           throw new Asn1Exception(906);
/*     */         } 
/* 204 */         if (vector1.size() > 0) {
/* 205 */           this.additionalTickets = new Ticket[vector1.size()];
/* 206 */           vector1.copyInto((Object[])this.additionalTickets);
/*     */         } 
/*     */       } else {
/* 209 */         throw new Asn1Exception(906);
/*     */       } 
/*     */     } 
/* 212 */     if (paramDerValue.getData().available() > 0) {
/* 213 */       throw new Asn1Exception(906);
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
/*     */   public byte[] asn1Encode(int paramInt) throws Asn1Exception, IOException {
/* 226 */     Vector<DerValue> vector = new Vector();
/* 227 */     vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)0), this.kdcOptions.asn1Encode()));
/* 228 */     if (paramInt == 10 && 
/* 229 */       this.cname != null) {
/* 230 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)1), this.cname.asn1Encode()));
/*     */     }
/*     */     
/* 233 */     if (this.sname != null) {
/* 234 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)2), this.sname.getRealm().asn1Encode()));
/* 235 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)3), this.sname.asn1Encode()));
/* 236 */     } else if (this.cname != null) {
/* 237 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)2), this.cname.getRealm().asn1Encode()));
/*     */     } 
/* 239 */     if (this.from != null) {
/* 240 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)4), this.from.asn1Encode()));
/*     */     }
/* 242 */     vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)5), this.till.asn1Encode()));
/* 243 */     if (this.rtime != null) {
/* 244 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)6), this.rtime.asn1Encode()));
/*     */     }
/* 246 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 247 */     derOutputStream1.putInteger(BigInteger.valueOf(this.nonce));
/* 248 */     vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)7), derOutputStream1.toByteArray()));
/*     */     
/* 250 */     derOutputStream1 = new DerOutputStream();
/* 251 */     for (byte b = 0; b < this.eType.length; b++) {
/* 252 */       derOutputStream1.putInteger(BigInteger.valueOf(this.eType[b]));
/*     */     }
/* 254 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 255 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 256 */     vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)8), derOutputStream2.toByteArray()));
/* 257 */     if (this.addresses != null) {
/* 258 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)9), this.addresses.asn1Encode()));
/*     */     }
/* 260 */     if (this.encAuthorizationData != null) {
/* 261 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)10), this.encAuthorizationData.asn1Encode()));
/*     */     }
/* 263 */     if (this.additionalTickets != null && this.additionalTickets.length > 0) {
/* 264 */       derOutputStream1 = new DerOutputStream();
/* 265 */       for (byte b1 = 0; b1 < this.additionalTickets.length; b1++) {
/* 266 */         derOutputStream1.write(this.additionalTickets[b1].asn1Encode());
/*     */       }
/* 268 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 269 */       derOutputStream.write((byte)48, derOutputStream1);
/* 270 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)11), derOutputStream.toByteArray()));
/*     */     } 
/* 272 */     DerValue[] arrayOfDerValue = new DerValue[vector.size()];
/* 273 */     vector.copyInto((Object[])arrayOfDerValue);
/* 274 */     derOutputStream1 = new DerOutputStream();
/* 275 */     derOutputStream1.putSequence(arrayOfDerValue);
/* 276 */     return derOutputStream1.toByteArray();
/*     */   }
/*     */   
/*     */   public int getNonce() {
/* 280 */     return this.nonce;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/KDCReqBody.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */