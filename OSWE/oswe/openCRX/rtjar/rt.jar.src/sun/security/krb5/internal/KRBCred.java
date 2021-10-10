/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Vector;
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
/*     */ public class KRBCred
/*     */ {
/*  61 */   public Ticket[] tickets = null;
/*     */   public EncryptedData encPart;
/*     */   private int pvno;
/*     */   private int msgType;
/*     */   
/*     */   public KRBCred(Ticket[] paramArrayOfTicket, EncryptedData paramEncryptedData) throws IOException {
/*  67 */     this.pvno = 5;
/*  68 */     this.msgType = 22;
/*  69 */     if (paramArrayOfTicket != null) {
/*  70 */       this.tickets = new Ticket[paramArrayOfTicket.length];
/*  71 */       for (byte b = 0; b < paramArrayOfTicket.length; b++) {
/*  72 */         if (paramArrayOfTicket[b] == null) {
/*  73 */           throw new IOException("Cannot create a KRBCred");
/*     */         }
/*  75 */         this.tickets[b] = (Ticket)paramArrayOfTicket[b].clone();
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     this.encPart = paramEncryptedData;
/*     */   }
/*     */ 
/*     */   
/*     */   public KRBCred(byte[] paramArrayOfbyte) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/*  84 */     init(new DerValue(paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */   
/*     */   public KRBCred(DerValue paramDerValue) throws Asn1Exception, RealmException, KrbApErrException, IOException {
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(DerValue paramDerValue) throws Asn1Exception, RealmException, KrbApErrException, IOException {
/* 103 */     if ((paramDerValue.getTag() & 0x1F) != 22 || paramDerValue
/* 104 */       .isApplication() != true || paramDerValue
/* 105 */       .isConstructed() != true) {
/* 106 */       throw new Asn1Exception(906);
/*     */     }
/*     */     
/* 109 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 110 */     if (derValue1.getTag() != 48) {
/* 111 */       throw new Asn1Exception(906);
/*     */     }
/* 113 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 114 */     if ((derValue2.getTag() & 0x1F) == 0) {
/* 115 */       this.pvno = derValue2.getData().getBigInteger().intValue();
/* 116 */       if (this.pvno != 5) {
/* 117 */         throw new KrbApErrException(39);
/*     */       }
/*     */     } else {
/* 120 */       throw new Asn1Exception(906);
/*     */     } 
/* 122 */     derValue2 = derValue1.getData().getDerValue();
/* 123 */     if ((derValue2.getTag() & 0x1F) == 1) {
/* 124 */       this.msgType = derValue2.getData().getBigInteger().intValue();
/* 125 */       if (this.msgType != 22) {
/* 126 */         throw new KrbApErrException(40);
/*     */       }
/*     */     } else {
/* 129 */       throw new Asn1Exception(906);
/*     */     } 
/* 131 */     derValue2 = derValue1.getData().getDerValue();
/* 132 */     if ((derValue2.getTag() & 0x1F) == 2) {
/* 133 */       DerValue derValue = derValue2.getData().getDerValue();
/* 134 */       if (derValue.getTag() != 48) {
/* 135 */         throw new Asn1Exception(906);
/*     */       }
/* 137 */       Vector<Ticket> vector = new Vector();
/* 138 */       while (derValue.getData().available() > 0) {
/* 139 */         vector.addElement(new Ticket(derValue.getData().getDerValue()));
/*     */       }
/* 141 */       if (vector.size() > 0) {
/* 142 */         this.tickets = new Ticket[vector.size()];
/* 143 */         vector.copyInto((Object[])this.tickets);
/*     */       } 
/*     */     } else {
/* 146 */       throw new Asn1Exception(906);
/*     */     } 
/* 148 */     this.encPart = EncryptedData.parse(derValue1.getData(), (byte)3, false);
/*     */     
/* 150 */     if (derValue1.getData().available() > 0) {
/* 151 */       throw new Asn1Exception(906);
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
/* 163 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 164 */     derOutputStream1.putInteger(BigInteger.valueOf(this.pvno));
/* 165 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 166 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)0), derOutputStream1);
/*     */     
/* 168 */     derOutputStream1 = new DerOutputStream();
/* 169 */     derOutputStream1.putInteger(BigInteger.valueOf(this.msgType));
/* 170 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)1), derOutputStream1);
/*     */     
/* 172 */     derOutputStream1 = new DerOutputStream();
/* 173 */     for (byte b = 0; b < this.tickets.length; b++) {
/* 174 */       derOutputStream1.write(this.tickets[b].asn1Encode());
/*     */     }
/* 176 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 177 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 178 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)2), derOutputStream2);
/*     */     
/* 180 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)3), this.encPart
/* 181 */         .asn1Encode());
/* 182 */     derOutputStream2 = new DerOutputStream();
/* 183 */     derOutputStream2.write((byte)48, derOutputStream3);
/* 184 */     derOutputStream3 = new DerOutputStream();
/* 185 */     derOutputStream3.write(DerValue.createTag((byte)64, true, (byte)22), derOutputStream2);
/*     */     
/* 187 */     return derOutputStream3.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/KRBCred.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */