/*     */ package javax.security.cert;
/*     */ 
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SignatureException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Certificate
/*     */ {
/*     */   public boolean equals(Object paramObject) {
/*  77 */     if (this == paramObject)
/*  78 */       return true; 
/*  79 */     if (!(paramObject instanceof Certificate))
/*  80 */       return false; 
/*     */     try {
/*  82 */       byte[] arrayOfByte1 = getEncoded();
/*  83 */       byte[] arrayOfByte2 = ((Certificate)paramObject).getEncoded();
/*     */       
/*  85 */       if (arrayOfByte1.length != arrayOfByte2.length)
/*  86 */         return false; 
/*  87 */       for (byte b = 0; b < arrayOfByte1.length; b++) {
/*  88 */         if (arrayOfByte1[b] != arrayOfByte2[b])
/*  89 */           return false; 
/*  90 */       }  return true;
/*  91 */     } catch (CertificateException certificateException) {
/*  92 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 103 */     int i = 0;
/*     */     try {
/* 105 */       byte[] arrayOfByte = getEncoded();
/* 106 */       for (byte b = 1; b < arrayOfByte.length; b++) {
/* 107 */         i += arrayOfByte[b] * b;
/*     */       }
/* 109 */       return i;
/* 110 */     } catch (CertificateException certificateException) {
/* 111 */       return i;
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract byte[] getEncoded() throws CertificateEncodingException;
/*     */   
/*     */   public abstract void verify(PublicKey paramPublicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
/*     */   
/*     */   public abstract void verify(PublicKey paramPublicKey, String paramString) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
/*     */   
/*     */   public abstract String toString();
/*     */   
/*     */   public abstract PublicKey getPublicKey();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/cert/Certificate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */