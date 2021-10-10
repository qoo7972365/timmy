/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.EncryptedData;
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
/*     */ public class APReq
/*     */ {
/*     */   public int pvno;
/*     */   public int msgType;
/*     */   public APOptions apOptions;
/*     */   public Ticket ticket;
/*     */   public EncryptedData authenticator;
/*     */   
/*     */   public APReq(APOptions paramAPOptions, Ticket paramTicket, EncryptedData paramEncryptedData) {
/*  69 */     this.pvno = 5;
/*  70 */     this.msgType = 14;
/*  71 */     this.apOptions = paramAPOptions;
/*  72 */     this.ticket = paramTicket;
/*  73 */     this.authenticator = paramEncryptedData;
/*     */   }
/*     */   
/*     */   public APReq(byte[] paramArrayOfbyte) throws Asn1Exception, IOException, KrbApErrException, RealmException {
/*  77 */     init(new DerValue(paramArrayOfbyte));
/*     */   }
/*     */   
/*     */   public APReq(DerValue paramDerValue) throws Asn1Exception, IOException, KrbApErrException, RealmException {
/*  81 */     init(paramDerValue);
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
/*     */   private void init(DerValue paramDerValue) throws Asn1Exception, IOException, KrbApErrException, RealmException {
/*  95 */     if ((paramDerValue.getTag() & 0x1F) != 14 || paramDerValue
/*  96 */       .isApplication() != true || paramDerValue
/*  97 */       .isConstructed() != true) {
/*  98 */       throw new Asn1Exception(906);
/*     */     }
/* 100 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 101 */     if (derValue1.getTag() != 48) {
/* 102 */       throw new Asn1Exception(906);
/*     */     }
/* 104 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 105 */     if ((derValue2.getTag() & 0x1F) != 0) {
/* 106 */       throw new Asn1Exception(906);
/*     */     }
/* 108 */     this.pvno = derValue2.getData().getBigInteger().intValue();
/* 109 */     if (this.pvno != 5) {
/* 110 */       throw new KrbApErrException(39);
/*     */     }
/* 112 */     derValue2 = derValue1.getData().getDerValue();
/* 113 */     if ((derValue2.getTag() & 0x1F) != 1) {
/* 114 */       throw new Asn1Exception(906);
/*     */     }
/* 116 */     this.msgType = derValue2.getData().getBigInteger().intValue();
/* 117 */     if (this.msgType != 14) {
/* 118 */       throw new KrbApErrException(40);
/*     */     }
/* 120 */     this.apOptions = APOptions.parse(derValue1.getData(), (byte)2, false);
/* 121 */     this.ticket = Ticket.parse(derValue1.getData(), (byte)3, false);
/* 122 */     this.authenticator = EncryptedData.parse(derValue1.getData(), (byte)4, false);
/* 123 */     if (derValue1.getData().available() > 0) {
/* 124 */       throw new Asn1Exception(906);
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
/* 135 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 136 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 137 */     derOutputStream2.putInteger(BigInteger.valueOf(this.pvno));
/* 138 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/* 139 */     derOutputStream2 = new DerOutputStream();
/* 140 */     derOutputStream2.putInteger(BigInteger.valueOf(this.msgType));
/* 141 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/* 142 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), this.apOptions.asn1Encode());
/* 143 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)3), this.ticket.asn1Encode());
/* 144 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)4), this.authenticator.asn1Encode());
/* 145 */     derOutputStream2 = new DerOutputStream();
/* 146 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 147 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 148 */     derOutputStream3.write(DerValue.createTag((byte)64, true, (byte)14), derOutputStream2);
/* 149 */     return derOutputStream3.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/APReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */