/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.Asn1Exception;
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
/*     */ public class MethodData
/*     */ {
/*     */   private int methodType;
/*  50 */   private byte[] methodData = null;
/*     */   
/*     */   public MethodData(int paramInt, byte[] paramArrayOfbyte) {
/*  53 */     this.methodType = paramInt;
/*  54 */     if (paramArrayOfbyte != null) {
/*  55 */       this.methodData = (byte[])paramArrayOfbyte.clone();
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
/*     */   public MethodData(DerValue paramDerValue) throws Asn1Exception, IOException {
/*  67 */     if (paramDerValue.getTag() != 48) {
/*  68 */       throw new Asn1Exception(906);
/*     */     }
/*  70 */     DerValue derValue = paramDerValue.getData().getDerValue();
/*  71 */     if ((derValue.getTag() & 0x1F) == 0) {
/*  72 */       BigInteger bigInteger = derValue.getData().getBigInteger();
/*  73 */       this.methodType = bigInteger.intValue();
/*     */     } else {
/*     */       
/*  76 */       throw new Asn1Exception(906);
/*  77 */     }  if (paramDerValue.getData().available() > 0) {
/*  78 */       derValue = paramDerValue.getData().getDerValue();
/*  79 */       if ((derValue.getTag() & 0x1F) == 1) {
/*  80 */         this.methodData = derValue.getData().getOctetString();
/*     */       } else {
/*  82 */         throw new Asn1Exception(906);
/*     */       } 
/*  84 */     }  if (paramDerValue.getData().available() > 0) {
/*  85 */       throw new Asn1Exception(906);
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
/*  96 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/*  97 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*  98 */     derOutputStream2.putInteger(BigInteger.valueOf(this.methodType));
/*  99 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/* 100 */     if (this.methodData != null) {
/* 101 */       derOutputStream2 = new DerOutputStream();
/* 102 */       derOutputStream2.putOctetString(this.methodData);
/* 103 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/*     */     } 
/*     */     
/* 106 */     derOutputStream2 = new DerOutputStream();
/* 107 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 108 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/MethodData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */