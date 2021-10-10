/*     */ package java.security.cert;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SignatureException;
/*     */ import java.util.Arrays;
/*     */ import sun.security.x509.X509CertImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3585440601605666277L;
/*     */   private final String type;
/*  70 */   private int hash = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Certificate(String paramString) {
/*  82 */     this.type = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getType() {
/*  91 */     return this.type;
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
/*     */   public boolean equals(Object paramObject) {
/* 106 */     if (this == paramObject) {
/* 107 */       return true;
/*     */     }
/* 109 */     if (!(paramObject instanceof Certificate)) {
/* 110 */       return false;
/*     */     }
/*     */     try {
/* 113 */       byte[] arrayOfByte1 = X509CertImpl.getEncodedInternal(this);
/* 114 */       byte[] arrayOfByte2 = X509CertImpl.getEncodedInternal((Certificate)paramObject);
/*     */       
/* 116 */       return Arrays.equals(arrayOfByte1, arrayOfByte2);
/* 117 */     } catch (CertificateException certificateException) {
/* 118 */       return false;
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
/* 129 */     int i = this.hash;
/* 130 */     if (i == -1) {
/*     */       try {
/* 132 */         i = Arrays.hashCode(X509CertImpl.getEncodedInternal(this));
/* 133 */       } catch (CertificateException certificateException) {
/* 134 */         i = 0;
/*     */       } 
/* 136 */       this.hash = i;
/*     */     } 
/* 138 */     return i;
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
/*     */   public abstract byte[] getEncoded() throws CertificateEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void verify(PublicKey paramPublicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void verify(PublicKey paramPublicKey, String paramString) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verify(PublicKey paramPublicKey, Provider paramProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
/* 219 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String toString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract PublicKey getPublicKey();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class CertificateRep
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -8563758940495660020L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String type;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private byte[] data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected CertificateRep(String param1String, byte[] param1ArrayOfbyte) {
/* 258 */       this.type = param1String;
/* 259 */       this.data = param1ArrayOfbyte;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object readResolve() throws ObjectStreamException {
/*     */       try {
/* 274 */         CertificateFactory certificateFactory = CertificateFactory.getInstance(this.type);
/* 275 */         return certificateFactory
/* 276 */           .generateCertificate(new ByteArrayInputStream(this.data));
/* 277 */       } catch (CertificateException certificateException) {
/* 278 */         throw new NotSerializableException("java.security.cert.Certificate: " + this.type + ": " + certificateException
/*     */ 
/*     */ 
/*     */             
/* 282 */             .getMessage());
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
/*     */   protected Object writeReplace() throws ObjectStreamException {
/*     */     try {
/* 298 */       return new CertificateRep(this.type, getEncoded());
/* 299 */     } catch (CertificateException certificateException) {
/* 300 */       throw new NotSerializableException("java.security.cert.Certificate: " + this.type + ": " + certificateException
/*     */ 
/*     */ 
/*     */           
/* 304 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/Certificate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */