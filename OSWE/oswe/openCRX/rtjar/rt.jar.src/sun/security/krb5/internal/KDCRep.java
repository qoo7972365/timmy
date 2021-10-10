/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.EncryptedData;
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
/*     */ public class KDCRep
/*     */ {
/*     */   public PrincipalName cname;
/*     */   public Ticket ticket;
/*     */   public EncryptedData encPart;
/*     */   public EncKDCRepPart encKDCRepPart;
/*     */   private int pvno;
/*     */   private int msgType;
/*  70 */   public PAData[] pAData = null;
/*  71 */   private boolean DEBUG = Krb5.DEBUG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KDCRep(PAData[] paramArrayOfPAData, PrincipalName paramPrincipalName, Ticket paramTicket, EncryptedData paramEncryptedData, int paramInt) throws IOException {
/*  79 */     this.pvno = 5;
/*  80 */     this.msgType = paramInt;
/*  81 */     if (paramArrayOfPAData != null) {
/*  82 */       this.pAData = new PAData[paramArrayOfPAData.length];
/*  83 */       for (byte b = 0; b < paramArrayOfPAData.length; b++) {
/*  84 */         if (paramArrayOfPAData[b] == null) {
/*  85 */           throw new IOException("Cannot create a KDCRep");
/*     */         }
/*  87 */         this.pAData[b] = (PAData)paramArrayOfPAData[b].clone();
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     this.cname = paramPrincipalName;
/*  92 */     this.ticket = paramTicket;
/*  93 */     this.encPart = paramEncryptedData;
/*     */   }
/*     */ 
/*     */   
/*     */   public KDCRep() {}
/*     */ 
/*     */   
/*     */   public KDCRep(byte[] paramArrayOfbyte, int paramInt) throws Asn1Exception, KrbApErrException, RealmException, IOException {
/* 101 */     init(new DerValue(paramArrayOfbyte), paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public KDCRep(DerValue paramDerValue, int paramInt) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/* 106 */     init(paramDerValue, paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init(DerValue paramDerValue, int paramInt) throws Asn1Exception, RealmException, IOException, KrbApErrException {
/* 133 */     if ((paramDerValue.getTag() & 0x1F) != paramInt) {
/* 134 */       if (this.DEBUG) {
/* 135 */         System.out.println(">>> KDCRep: init() encoding tag is " + paramDerValue
/*     */             
/* 137 */             .getTag() + " req type is " + paramInt);
/*     */       }
/*     */       
/* 140 */       throw new Asn1Exception(906);
/*     */     } 
/* 142 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 143 */     if (derValue1.getTag() != 48) {
/* 144 */       throw new Asn1Exception(906);
/*     */     }
/* 146 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 147 */     if ((derValue2.getTag() & 0x1F) == 0) {
/* 148 */       this.pvno = derValue2.getData().getBigInteger().intValue();
/* 149 */       if (this.pvno != 5) {
/* 150 */         throw new KrbApErrException(39);
/*     */       }
/*     */     } else {
/* 153 */       throw new Asn1Exception(906);
/*     */     } 
/* 155 */     derValue2 = derValue1.getData().getDerValue();
/* 156 */     if ((derValue2.getTag() & 0x1F) == 1) {
/* 157 */       this.msgType = derValue2.getData().getBigInteger().intValue();
/* 158 */       if (this.msgType != paramInt) {
/* 159 */         throw new KrbApErrException(40);
/*     */       }
/*     */     } else {
/* 162 */       throw new Asn1Exception(906);
/*     */     } 
/* 164 */     if ((derValue1.getData().peekByte() & 0x1F) == 2) {
/* 165 */       derValue2 = derValue1.getData().getDerValue();
/* 166 */       DerValue[] arrayOfDerValue = derValue2.getData().getSequence(1);
/* 167 */       this.pAData = new PAData[arrayOfDerValue.length];
/* 168 */       for (byte b = 0; b < arrayOfDerValue.length; b++) {
/* 169 */         this.pAData[b] = new PAData(arrayOfDerValue[b]);
/*     */       }
/*     */     } else {
/* 172 */       this.pAData = null;
/*     */     } 
/* 174 */     Realm realm = Realm.parse(derValue1.getData(), (byte)3, false);
/* 175 */     this.cname = PrincipalName.parse(derValue1.getData(), (byte)4, false, realm);
/* 176 */     this.ticket = Ticket.parse(derValue1.getData(), (byte)5, false);
/* 177 */     this.encPart = EncryptedData.parse(derValue1.getData(), (byte)6, false);
/* 178 */     if (derValue1.getData().available() > 0) {
/* 179 */       throw new Asn1Exception(906);
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 192 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 193 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 194 */     derOutputStream2.putInteger(BigInteger.valueOf(this.pvno));
/* 195 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/*     */     
/* 197 */     derOutputStream2 = new DerOutputStream();
/* 198 */     derOutputStream2.putInteger(BigInteger.valueOf(this.msgType));
/* 199 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/*     */     
/* 201 */     if (this.pAData != null && this.pAData.length > 0) {
/* 202 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 203 */       for (byte b = 0; b < this.pAData.length; b++) {
/* 204 */         derOutputStream.write(this.pAData[b].asn1Encode());
/*     */       }
/* 206 */       derOutputStream2 = new DerOutputStream();
/* 207 */       derOutputStream2.write((byte)48, derOutputStream);
/* 208 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), derOutputStream2);
/*     */     } 
/*     */     
/* 211 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)3), this.cname
/* 212 */         .getRealm().asn1Encode());
/* 213 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)4), this.cname
/* 214 */         .asn1Encode());
/* 215 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)5), this.ticket
/* 216 */         .asn1Encode());
/* 217 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)6), this.encPart
/* 218 */         .asn1Encode());
/* 219 */     derOutputStream2 = new DerOutputStream();
/* 220 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 221 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/KDCRep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */