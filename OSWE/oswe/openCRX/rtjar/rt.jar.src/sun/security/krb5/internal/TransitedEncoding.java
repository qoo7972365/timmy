/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.Asn1Exception;
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
/*     */ public class TransitedEncoding
/*     */ {
/*     */   public int trType;
/*     */   public byte[] contents;
/*     */   
/*     */   public TransitedEncoding(int paramInt, byte[] paramArrayOfbyte) {
/*  60 */     this.trType = paramInt;
/*  61 */     this.contents = paramArrayOfbyte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransitedEncoding(DerValue paramDerValue) throws Asn1Exception, IOException {
/*  72 */     if (paramDerValue.getTag() != 48) {
/*  73 */       throw new Asn1Exception(906);
/*     */     }
/*     */     
/*  76 */     DerValue derValue = paramDerValue.getData().getDerValue();
/*  77 */     if ((derValue.getTag() & 0x1F) == 0) {
/*  78 */       this.trType = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/*     */       
/*  81 */       throw new Asn1Exception(906);
/*  82 */     }  derValue = paramDerValue.getData().getDerValue();
/*     */     
/*  84 */     if ((derValue.getTag() & 0x1F) == 1) {
/*  85 */       this.contents = derValue.getData().getOctetString();
/*     */     } else {
/*     */       
/*  88 */       throw new Asn1Exception(906);
/*  89 */     }  if (derValue.getData().available() > 0) {
/*  90 */       throw new Asn1Exception(906);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 100 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 101 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 102 */     derOutputStream2.putInteger(BigInteger.valueOf(this.trType));
/* 103 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/* 104 */     derOutputStream2 = new DerOutputStream();
/* 105 */     derOutputStream2.putOctetString(this.contents);
/* 106 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/* 107 */     derOutputStream2 = new DerOutputStream();
/* 108 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 109 */     return derOutputStream2.toByteArray();
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
/*     */   public static TransitedEncoding parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 125 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/* 126 */       return null; 
/* 127 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 128 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 129 */       throw new Asn1Exception(906);
/*     */     }
/*     */     
/* 132 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 133 */     return new TransitedEncoding(derValue2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/TransitedEncoding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */