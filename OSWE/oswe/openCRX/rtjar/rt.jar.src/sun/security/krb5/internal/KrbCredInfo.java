/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Vector;
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
/*     */ public class KrbCredInfo
/*     */ {
/*     */   public EncryptionKey key;
/*     */   public PrincipalName pname;
/*     */   public TicketFlags flags;
/*     */   public KerberosTime authtime;
/*     */   public KerberosTime starttime;
/*     */   public KerberosTime endtime;
/*     */   public KerberosTime renewTill;
/*     */   public PrincipalName sname;
/*     */   public HostAddresses caddr;
/*     */   
/*     */   private KrbCredInfo() {}
/*     */   
/*     */   public KrbCredInfo(EncryptionKey paramEncryptionKey, PrincipalName paramPrincipalName1, TicketFlags paramTicketFlags, KerberosTime paramKerberosTime1, KerberosTime paramKerberosTime2, KerberosTime paramKerberosTime3, KerberosTime paramKerberosTime4, PrincipalName paramPrincipalName2, HostAddresses paramHostAddresses) {
/*  89 */     this.key = paramEncryptionKey;
/*  90 */     this.pname = paramPrincipalName1;
/*  91 */     this.flags = paramTicketFlags;
/*  92 */     this.authtime = paramKerberosTime1;
/*  93 */     this.starttime = paramKerberosTime2;
/*  94 */     this.endtime = paramKerberosTime3;
/*  95 */     this.renewTill = paramKerberosTime4;
/*  96 */     this.sname = paramPrincipalName2;
/*  97 */     this.caddr = paramHostAddresses;
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
/*     */   public KrbCredInfo(DerValue paramDerValue) throws Asn1Exception, IOException, RealmException {
/* 109 */     if (paramDerValue.getTag() != 48) {
/* 110 */       throw new Asn1Exception(906);
/*     */     }
/* 112 */     this.pname = null;
/* 113 */     this.flags = null;
/* 114 */     this.authtime = null;
/* 115 */     this.starttime = null;
/* 116 */     this.endtime = null;
/* 117 */     this.renewTill = null;
/* 118 */     this.sname = null;
/* 119 */     this.caddr = null;
/* 120 */     this.key = EncryptionKey.parse(paramDerValue.getData(), (byte)0, false);
/* 121 */     Realm realm1 = null, realm2 = null;
/* 122 */     if (paramDerValue.getData().available() > 0)
/* 123 */       realm1 = Realm.parse(paramDerValue.getData(), (byte)1, true); 
/* 124 */     if (paramDerValue.getData().available() > 0)
/* 125 */       this.pname = PrincipalName.parse(paramDerValue.getData(), (byte)2, true, realm1); 
/* 126 */     if (paramDerValue.getData().available() > 0)
/* 127 */       this.flags = TicketFlags.parse(paramDerValue.getData(), (byte)3, true); 
/* 128 */     if (paramDerValue.getData().available() > 0)
/* 129 */       this.authtime = KerberosTime.parse(paramDerValue.getData(), (byte)4, true); 
/* 130 */     if (paramDerValue.getData().available() > 0)
/* 131 */       this.starttime = KerberosTime.parse(paramDerValue.getData(), (byte)5, true); 
/* 132 */     if (paramDerValue.getData().available() > 0)
/* 133 */       this.endtime = KerberosTime.parse(paramDerValue.getData(), (byte)6, true); 
/* 134 */     if (paramDerValue.getData().available() > 0)
/* 135 */       this.renewTill = KerberosTime.parse(paramDerValue.getData(), (byte)7, true); 
/* 136 */     if (paramDerValue.getData().available() > 0)
/* 137 */       realm2 = Realm.parse(paramDerValue.getData(), (byte)8, true); 
/* 138 */     if (paramDerValue.getData().available() > 0)
/* 139 */       this.sname = PrincipalName.parse(paramDerValue.getData(), (byte)9, true, realm2); 
/* 140 */     if (paramDerValue.getData().available() > 0)
/* 141 */       this.caddr = HostAddresses.parse(paramDerValue.getData(), (byte)10, true); 
/* 142 */     if (paramDerValue.getData().available() > 0) {
/* 143 */       throw new Asn1Exception(906);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 153 */     Vector<DerValue> vector = new Vector();
/* 154 */     vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)0), this.key.asn1Encode()));
/* 155 */     if (this.pname != null) {
/* 156 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)1), this.pname.getRealm().asn1Encode()));
/* 157 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)2), this.pname.asn1Encode()));
/*     */     } 
/* 159 */     if (this.flags != null)
/* 160 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)3), this.flags.asn1Encode())); 
/* 161 */     if (this.authtime != null)
/* 162 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)4), this.authtime.asn1Encode())); 
/* 163 */     if (this.starttime != null)
/* 164 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)5), this.starttime.asn1Encode())); 
/* 165 */     if (this.endtime != null)
/* 166 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)6), this.endtime.asn1Encode())); 
/* 167 */     if (this.renewTill != null)
/* 168 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)7), this.renewTill.asn1Encode())); 
/* 169 */     if (this.sname != null) {
/* 170 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)8), this.sname.getRealm().asn1Encode()));
/* 171 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)9), this.sname.asn1Encode()));
/*     */     } 
/* 173 */     if (this.caddr != null)
/* 174 */       vector.addElement(new DerValue(DerValue.createTag(-128, true, (byte)10), this.caddr.asn1Encode())); 
/* 175 */     DerValue[] arrayOfDerValue = new DerValue[vector.size()];
/* 176 */     vector.copyInto((Object[])arrayOfDerValue);
/* 177 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 178 */     derOutputStream.putSequence(arrayOfDerValue);
/* 179 */     return derOutputStream.toByteArray();
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 183 */     KrbCredInfo krbCredInfo = new KrbCredInfo();
/* 184 */     krbCredInfo.key = (EncryptionKey)this.key.clone();
/*     */     
/* 186 */     if (this.pname != null)
/* 187 */       krbCredInfo.pname = (PrincipalName)this.pname.clone(); 
/* 188 */     if (this.flags != null)
/* 189 */       krbCredInfo.flags = (TicketFlags)this.flags.clone(); 
/* 190 */     krbCredInfo.authtime = this.authtime;
/* 191 */     krbCredInfo.starttime = this.starttime;
/* 192 */     krbCredInfo.endtime = this.endtime;
/* 193 */     krbCredInfo.renewTill = this.renewTill;
/* 194 */     if (this.sname != null)
/* 195 */       krbCredInfo.sname = (PrincipalName)this.sname.clone(); 
/* 196 */     if (this.caddr != null)
/* 197 */       krbCredInfo.caddr = (HostAddresses)this.caddr.clone(); 
/* 198 */     return krbCredInfo;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/KrbCredInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */