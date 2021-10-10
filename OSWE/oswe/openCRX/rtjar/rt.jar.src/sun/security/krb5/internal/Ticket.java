/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.EncryptedData;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.Realm;
/*     */ import sun.security.krb5.RealmException;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ public class Ticket
/*     */   implements Cloneable
/*     */ {
/*     */   public int tkt_vno;
/*     */   public PrincipalName sname;
/*     */   public EncryptedData encPart;
/*     */   
/*     */   private Ticket() {}
/*     */   
/*     */   public Object clone() {
/*  70 */     Ticket ticket = new Ticket();
/*  71 */     ticket.sname = (PrincipalName)this.sname.clone();
/*  72 */     ticket.encPart = (EncryptedData)this.encPart.clone();
/*  73 */     ticket.tkt_vno = this.tkt_vno;
/*  74 */     return ticket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Ticket(PrincipalName paramPrincipalName, EncryptedData paramEncryptedData) {
/*  81 */     this.tkt_vno = 5;
/*  82 */     this.sname = paramPrincipalName;
/*  83 */     this.encPart = paramEncryptedData;
/*     */   }
/*     */ 
/*     */   
/*     */   public Ticket(byte[] paramArrayOfbyte) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/*  88 */     init(new DerValue(paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */   
/*     */   public Ticket(DerValue paramDerValue) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/*  93 */     init(paramDerValue);
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
/*     */   private void init(DerValue paramDerValue) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/* 109 */     if ((paramDerValue.getTag() & 0x1F) != 1 || paramDerValue
/* 110 */       .isApplication() != true || paramDerValue
/* 111 */       .isConstructed() != true)
/* 112 */       throw new Asn1Exception(906); 
/* 113 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 114 */     if (derValue1.getTag() != 48)
/* 115 */       throw new Asn1Exception(906); 
/* 116 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 117 */     if ((derValue2.getTag() & 0x1F) != 0)
/* 118 */       throw new Asn1Exception(906); 
/* 119 */     this.tkt_vno = derValue2.getData().getBigInteger().intValue();
/* 120 */     if (this.tkt_vno != 5)
/* 121 */       throw new KrbApErrException(39); 
/* 122 */     Realm realm = Realm.parse(derValue1.getData(), (byte)1, false);
/* 123 */     this.sname = PrincipalName.parse(derValue1.getData(), (byte)2, false, realm);
/* 124 */     this.encPart = EncryptedData.parse(derValue1.getData(), (byte)3, false);
/* 125 */     if (derValue1.getData().available() > 0) {
/* 126 */       throw new Asn1Exception(906);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 136 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 137 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 138 */     DerValue[] arrayOfDerValue = new DerValue[4];
/* 139 */     derOutputStream2.putInteger(BigInteger.valueOf(this.tkt_vno));
/* 140 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/* 141 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), this.sname.getRealm().asn1Encode());
/* 142 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), this.sname.asn1Encode());
/* 143 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)3), this.encPart.asn1Encode());
/* 144 */     derOutputStream2 = new DerOutputStream();
/* 145 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 146 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 147 */     derOutputStream3.write(DerValue.createTag((byte)64, true, (byte)1), derOutputStream2);
/* 148 */     return derOutputStream3.toByteArray();
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
/*     */   public static Ticket parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException, RealmException, KrbApErrException {
/* 163 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/* 164 */       return null; 
/* 165 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 166 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 167 */       throw new Asn1Exception(906);
/*     */     }
/*     */     
/* 170 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 171 */     return new Ticket(derValue2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/Ticket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */