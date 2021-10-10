/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.KrbException;
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
/*     */ public class KDCReq
/*     */ {
/*     */   public KDCReqBody reqBody;
/*  62 */   public PAData[] pAData = null;
/*     */   
/*     */   private int pvno;
/*     */   private int msgType;
/*     */   
/*     */   public KDCReq(PAData[] paramArrayOfPAData, KDCReqBody paramKDCReqBody, int paramInt) throws IOException {
/*  68 */     this.pvno = 5;
/*  69 */     this.msgType = paramInt;
/*  70 */     if (paramArrayOfPAData != null) {
/*  71 */       this.pAData = new PAData[paramArrayOfPAData.length];
/*  72 */       for (byte b = 0; b < paramArrayOfPAData.length; b++) {
/*  73 */         if (paramArrayOfPAData[b] == null) {
/*  74 */           throw new IOException("Cannot create a KDCRep");
/*     */         }
/*  76 */         this.pAData[b] = (PAData)paramArrayOfPAData[b].clone();
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     this.reqBody = paramKDCReqBody;
/*     */   }
/*     */ 
/*     */   
/*     */   public KDCReq() {}
/*     */ 
/*     */   
/*     */   public KDCReq(byte[] paramArrayOfbyte, int paramInt) throws Asn1Exception, IOException, KrbException {
/*  88 */     init(new DerValue(paramArrayOfbyte), paramInt);
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
/*     */   public KDCReq(DerValue paramDerValue, int paramInt) throws Asn1Exception, IOException, KrbException {
/* 102 */     init(paramDerValue, paramInt);
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
/*     */   protected void init(DerValue paramDerValue, int paramInt) throws Asn1Exception, IOException, KrbException {
/* 120 */     if ((paramDerValue.getTag() & 0x1F) != paramInt) {
/* 121 */       throw new Asn1Exception(906);
/*     */     }
/* 123 */     DerValue derValue1 = paramDerValue.getData().getDerValue();
/* 124 */     if (derValue1.getTag() != 48) {
/* 125 */       throw new Asn1Exception(906);
/*     */     }
/* 127 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 128 */     if ((derValue2.getTag() & 0x1F) == 1) {
/* 129 */       BigInteger bigInteger = derValue2.getData().getBigInteger();
/* 130 */       this.pvno = bigInteger.intValue();
/* 131 */       if (this.pvno != 5) {
/* 132 */         throw new KrbApErrException(39);
/*     */       }
/*     */     } else {
/* 135 */       throw new Asn1Exception(906);
/*     */     } 
/* 137 */     derValue2 = derValue1.getData().getDerValue();
/* 138 */     if ((derValue2.getTag() & 0x1F) == 2) {
/* 139 */       BigInteger bigInteger = derValue2.getData().getBigInteger();
/* 140 */       this.msgType = bigInteger.intValue();
/* 141 */       if (this.msgType != paramInt) {
/* 142 */         throw new KrbApErrException(40);
/*     */       }
/*     */     } else {
/* 145 */       throw new Asn1Exception(906);
/*     */     } 
/* 147 */     this.pAData = PAData.parseSequence(derValue1.getData(), (byte)3, true);
/* 148 */     derValue2 = derValue1.getData().getDerValue();
/* 149 */     if ((derValue2.getTag() & 0x1F) == 4) {
/* 150 */       DerValue derValue = derValue2.getData().getDerValue();
/* 151 */       this.reqBody = new KDCReqBody(derValue, this.msgType);
/*     */     } else {
/* 153 */       throw new Asn1Exception(906);
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 167 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 168 */     derOutputStream1.putInteger(BigInteger.valueOf(this.pvno));
/* 169 */     DerOutputStream derOutputStream3 = new DerOutputStream();
/* 170 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)1), derOutputStream1);
/*     */     
/* 172 */     derOutputStream1 = new DerOutputStream();
/* 173 */     derOutputStream1.putInteger(BigInteger.valueOf(this.msgType));
/* 174 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)2), derOutputStream1);
/*     */     
/* 176 */     if (this.pAData != null && this.pAData.length > 0) {
/* 177 */       derOutputStream1 = new DerOutputStream();
/* 178 */       for (byte b = 0; b < this.pAData.length; b++) {
/* 179 */         derOutputStream1.write(this.pAData[b].asn1Encode());
/*     */       }
/* 181 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 182 */       derOutputStream.write((byte)48, derOutputStream1);
/* 183 */       derOutputStream3.write(DerValue.createTag(-128, true, (byte)3), derOutputStream);
/*     */     } 
/*     */     
/* 186 */     derOutputStream3.write(DerValue.createTag(-128, true, (byte)4), this.reqBody
/* 187 */         .asn1Encode(this.msgType));
/* 188 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 189 */     derOutputStream2.write((byte)48, derOutputStream3);
/* 190 */     derOutputStream3 = new DerOutputStream();
/* 191 */     derOutputStream3.write(DerValue.createTag((byte)64, true, (byte)this.msgType), derOutputStream2);
/*     */     
/* 193 */     return derOutputStream3.toByteArray();
/*     */   }
/*     */   
/*     */   public byte[] asn1EncodeReqBody() throws Asn1Exception, IOException {
/* 197 */     return this.reqBody.asn1Encode(this.msgType);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/KDCReq.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */