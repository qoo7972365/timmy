/*     */ package java.security.cert;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.PublicKey;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.x509.NameConstraintsExtension;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TrustAnchor
/*     */ {
/*     */   private final PublicKey pubKey;
/*     */   private final String caName;
/*     */   private final X500Principal caPrincipal;
/*     */   private final X509Certificate trustedCert;
/*     */   private byte[] ncBytes;
/*     */   private NameConstraintsExtension nc;
/*     */   
/*     */   public TrustAnchor(X509Certificate paramX509Certificate, byte[] paramArrayOfbyte) {
/* 125 */     if (paramX509Certificate == null) {
/* 126 */       throw new NullPointerException("the trustedCert parameter must be non-null");
/*     */     }
/* 128 */     this.trustedCert = paramX509Certificate;
/* 129 */     this.pubKey = null;
/* 130 */     this.caName = null;
/* 131 */     this.caPrincipal = null;
/* 132 */     setNameConstraints(paramArrayOfbyte);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrustAnchor(X500Principal paramX500Principal, PublicKey paramPublicKey, byte[] paramArrayOfbyte) {
/* 164 */     if (paramX500Principal == null || paramPublicKey == null) {
/* 165 */       throw new NullPointerException();
/*     */     }
/* 167 */     this.trustedCert = null;
/* 168 */     this.caPrincipal = paramX500Principal;
/* 169 */     this.caName = paramX500Principal.getName();
/* 170 */     this.pubKey = paramPublicKey;
/* 171 */     setNameConstraints(paramArrayOfbyte);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrustAnchor(String paramString, PublicKey paramPublicKey, byte[] paramArrayOfbyte) {
/* 207 */     if (paramPublicKey == null) {
/* 208 */       throw new NullPointerException("the pubKey parameter must be non-null");
/*     */     }
/* 210 */     if (paramString == null) {
/* 211 */       throw new NullPointerException("the caName parameter must be non-null");
/*     */     }
/* 213 */     if (paramString.length() == 0) {
/* 214 */       throw new IllegalArgumentException("the caName parameter must be a non-empty String");
/*     */     }
/*     */     
/* 217 */     this.caPrincipal = new X500Principal(paramString);
/* 218 */     this.pubKey = paramPublicKey;
/* 219 */     this.caName = paramString;
/* 220 */     this.trustedCert = null;
/* 221 */     setNameConstraints(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final X509Certificate getTrustedCert() {
/* 231 */     return this.trustedCert;
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
/*     */   public final X500Principal getCA() {
/* 243 */     return this.caPrincipal;
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
/*     */   public final String getCAName() {
/* 255 */     return this.caName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PublicKey getCAPublicKey() {
/* 266 */     return this.pubKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setNameConstraints(byte[] paramArrayOfbyte) {
/* 273 */     if (paramArrayOfbyte == null) {
/* 274 */       this.ncBytes = null;
/* 275 */       this.nc = null;
/*     */     } else {
/* 277 */       this.ncBytes = (byte[])paramArrayOfbyte.clone();
/*     */       
/*     */       try {
/* 280 */         this.nc = new NameConstraintsExtension(Boolean.FALSE, paramArrayOfbyte);
/* 281 */       } catch (IOException iOException) {
/*     */         
/* 283 */         IllegalArgumentException illegalArgumentException = new IllegalArgumentException(iOException.getMessage());
/* 284 */         illegalArgumentException.initCause(iOException);
/* 285 */         throw illegalArgumentException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] getNameConstraints() {
/* 311 */     return (this.ncBytes == null) ? null : (byte[])this.ncBytes.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 320 */     StringBuffer stringBuffer = new StringBuffer();
/* 321 */     stringBuffer.append("[\n");
/* 322 */     if (this.pubKey != null) {
/* 323 */       stringBuffer.append("  Trusted CA Public Key: " + this.pubKey.toString() + "\n");
/* 324 */       stringBuffer.append("  Trusted CA Issuer Name: " + 
/* 325 */           String.valueOf(this.caName) + "\n");
/*     */     } else {
/* 327 */       stringBuffer.append("  Trusted CA cert: " + this.trustedCert.toString() + "\n");
/*     */     } 
/* 329 */     if (this.nc != null)
/* 330 */       stringBuffer.append("  Name Constraints: " + this.nc.toString() + "\n"); 
/* 331 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/TrustAnchor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */