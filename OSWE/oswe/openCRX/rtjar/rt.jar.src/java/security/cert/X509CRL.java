/*     */ package java.security.cert;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Principal;
/*     */ import java.security.Provider;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SignatureException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.x509.X509CRLImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class X509CRL
/*     */   extends CRL
/*     */   implements X509Extension
/*     */ {
/*     */   private transient X500Principal issuerPrincipal;
/*     */   
/*     */   protected X509CRL() {
/* 120 */     super("X.509");
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
/*     */   public boolean equals(Object paramObject) {
/* 136 */     if (this == paramObject) {
/* 137 */       return true;
/*     */     }
/* 139 */     if (!(paramObject instanceof X509CRL)) {
/* 140 */       return false;
/*     */     }
/*     */     try {
/* 143 */       byte[] arrayOfByte1 = X509CRLImpl.getEncodedInternal(this);
/* 144 */       byte[] arrayOfByte2 = X509CRLImpl.getEncodedInternal((X509CRL)paramObject);
/*     */       
/* 146 */       return Arrays.equals(arrayOfByte1, arrayOfByte2);
/* 147 */     } catch (CRLException cRLException) {
/* 148 */       return false;
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
/* 159 */     int i = 0;
/*     */     try {
/* 161 */       byte[] arrayOfByte = X509CRLImpl.getEncodedInternal(this);
/* 162 */       for (byte b = 1; b < arrayOfByte.length; b++) {
/* 163 */         i += arrayOfByte[b] * b;
/*     */       }
/* 165 */       return i;
/* 166 */     } catch (CRLException cRLException) {
/* 167 */       return i;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract byte[] getEncoded() throws CRLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void verify(PublicKey paramPublicKey) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void verify(PublicKey paramPublicKey, String paramString) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verify(PublicKey paramPublicKey, Provider paramProvider) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
/* 244 */     X509CRLImpl.verify(this, paramPublicKey, paramProvider);
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
/*     */   public abstract int getVersion();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Principal getIssuerDN();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X500Principal getIssuerX500Principal() {
/* 315 */     if (this.issuerPrincipal == null) {
/* 316 */       this.issuerPrincipal = X509CRLImpl.getIssuerX500Principal(this);
/*     */     }
/* 318 */     return this.issuerPrincipal;
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
/*     */   public abstract Date getThisUpdate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Date getNextUpdate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract X509CRLEntry getRevokedCertificate(BigInteger paramBigInteger);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509CRLEntry getRevokedCertificate(X509Certificate paramX509Certificate) {
/* 373 */     X500Principal x500Principal1 = paramX509Certificate.getIssuerX500Principal();
/* 374 */     X500Principal x500Principal2 = getIssuerX500Principal();
/* 375 */     if (!x500Principal1.equals(x500Principal2)) {
/* 376 */       return null;
/*     */     }
/* 378 */     return getRevokedCertificate(paramX509Certificate.getSerialNumber());
/*     */   }
/*     */   
/*     */   public abstract Set<? extends X509CRLEntry> getRevokedCertificates();
/*     */   
/*     */   public abstract byte[] getTBSCertList() throws CRLException;
/*     */   
/*     */   public abstract byte[] getSignature();
/*     */   
/*     */   public abstract String getSigAlgName();
/*     */   
/*     */   public abstract String getSigAlgOID();
/*     */   
/*     */   public abstract byte[] getSigAlgParams();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/X509CRL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */