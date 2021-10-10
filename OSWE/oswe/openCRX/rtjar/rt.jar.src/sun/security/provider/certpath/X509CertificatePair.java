/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.security.interfaces.DSAPublicKey;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.provider.X509Factory;
/*     */ import sun.security.util.Cache;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509CertificatePair
/*     */ {
/*     */   private static final byte TAG_FORWARD = 0;
/*     */   private static final byte TAG_REVERSE = 1;
/*     */   private X509Certificate forward;
/*     */   private X509Certificate reverse;
/*     */   private byte[] encoded;
/*  83 */   private static final Cache<Object, X509CertificatePair> cache = Cache.newSoftMemoryCache(750);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509CertificatePair() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509CertificatePair(X509Certificate paramX509Certificate1, X509Certificate paramX509Certificate2) throws CertificateException {
/* 102 */     if (paramX509Certificate1 == null && paramX509Certificate2 == null) {
/* 103 */       throw new CertificateException("at least one of certificate pair must be non-null");
/*     */     }
/*     */ 
/*     */     
/* 107 */     this.forward = paramX509Certificate1;
/* 108 */     this.reverse = paramX509Certificate2;
/*     */     
/* 110 */     checkPair();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private X509CertificatePair(byte[] paramArrayOfbyte) throws CertificateException {
/*     */     try {
/* 120 */       parse(new DerValue(paramArrayOfbyte));
/* 121 */       this.encoded = paramArrayOfbyte;
/* 122 */     } catch (IOException iOException) {
/* 123 */       throw new CertificateException(iOException.toString());
/*     */     } 
/* 125 */     checkPair();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void clearCache() {
/* 132 */     cache.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized X509CertificatePair generateCertificatePair(byte[] paramArrayOfbyte) throws CertificateException {
/* 141 */     Cache.EqualByteArray equalByteArray = new Cache.EqualByteArray(paramArrayOfbyte);
/* 142 */     X509CertificatePair x509CertificatePair = cache.get(equalByteArray);
/* 143 */     if (x509CertificatePair != null) {
/* 144 */       return x509CertificatePair;
/*     */     }
/* 146 */     x509CertificatePair = new X509CertificatePair(paramArrayOfbyte);
/* 147 */     equalByteArray = new Cache.EqualByteArray(x509CertificatePair.encoded);
/* 148 */     cache.put(equalByteArray, x509CertificatePair);
/* 149 */     return x509CertificatePair;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForward(X509Certificate paramX509Certificate) throws CertificateException {
/* 156 */     checkPair();
/* 157 */     this.forward = paramX509Certificate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReverse(X509Certificate paramX509Certificate) throws CertificateException {
/* 164 */     checkPair();
/* 165 */     this.reverse = paramX509Certificate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate getForward() {
/* 174 */     return this.forward;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate getReverse() {
/* 183 */     return this.reverse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getEncoded() throws CertificateEncodingException {
/*     */     try {
/* 194 */       if (this.encoded == null) {
/* 195 */         DerOutputStream derOutputStream = new DerOutputStream();
/* 196 */         emit(derOutputStream);
/* 197 */         this.encoded = derOutputStream.toByteArray();
/*     */       } 
/* 199 */     } catch (IOException iOException) {
/* 200 */       throw new CertificateEncodingException(iOException.toString());
/*     */     } 
/* 202 */     return this.encoded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 212 */     StringBuilder stringBuilder = new StringBuilder();
/* 213 */     stringBuilder.append("X.509 Certificate Pair: [\n");
/* 214 */     if (this.forward != null)
/* 215 */       stringBuilder.append("  Forward: ").append(this.forward).append("\n"); 
/* 216 */     if (this.reverse != null)
/* 217 */       stringBuilder.append("  Reverse: ").append(this.reverse).append("\n"); 
/* 218 */     stringBuilder.append("]");
/* 219 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse(DerValue paramDerValue) throws IOException, CertificateException {
/* 226 */     if (paramDerValue.tag != 48) {
/* 227 */       throw new IOException("Sequence tag missing for X509CertificatePair");
/*     */     }
/*     */ 
/*     */     
/* 231 */     while (paramDerValue.data != null && paramDerValue.data.available() != 0) {
/* 232 */       DerValue derValue = paramDerValue.data.getDerValue();
/* 233 */       short s = (short)(byte)(derValue.tag & 0x1F);
/* 234 */       switch (s) {
/*     */         case 0:
/* 236 */           if (derValue.isContextSpecific() && derValue.isConstructed()) {
/* 237 */             if (this.forward != null) {
/* 238 */               throw new IOException("Duplicate forward certificate in X509CertificatePair");
/*     */             }
/*     */             
/* 241 */             derValue = derValue.data.getDerValue();
/* 242 */             this
/* 243 */               .forward = X509Factory.intern(new X509CertImpl(derValue.toByteArray()));
/*     */           } 
/*     */           continue;
/*     */         case 1:
/* 247 */           if (derValue.isContextSpecific() && derValue.isConstructed()) {
/* 248 */             if (this.reverse != null) {
/* 249 */               throw new IOException("Duplicate reverse certificate in X509CertificatePair");
/*     */             }
/*     */             
/* 252 */             derValue = derValue.data.getDerValue();
/* 253 */             this
/* 254 */               .reverse = X509Factory.intern(new X509CertImpl(derValue.toByteArray()));
/*     */           } 
/*     */           continue;
/*     */       } 
/* 258 */       throw new IOException("Invalid encoding of X509CertificatePair");
/*     */     } 
/*     */ 
/*     */     
/* 262 */     if (this.forward == null && this.reverse == null) {
/* 263 */       throw new CertificateException("at least one of certificate pair must be non-null");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void emit(DerOutputStream paramDerOutputStream) throws IOException, CertificateEncodingException {
/* 272 */     DerOutputStream derOutputStream = new DerOutputStream();
/*     */     
/* 274 */     if (this.forward != null) {
/* 275 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 276 */       derOutputStream1.putDerValue(new DerValue(this.forward.getEncoded()));
/* 277 */       derOutputStream.write(DerValue.createTag(-128, true, (byte)0), derOutputStream1);
/*     */     } 
/*     */ 
/*     */     
/* 281 */     if (this.reverse != null) {
/* 282 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/* 283 */       derOutputStream1.putDerValue(new DerValue(this.reverse.getEncoded()));
/* 284 */       derOutputStream.write(DerValue.createTag(-128, true, (byte)1), derOutputStream1);
/*     */     } 
/*     */ 
/*     */     
/* 288 */     paramDerOutputStream.write((byte)48, derOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkPair() throws CertificateException {
/* 297 */     if (this.forward == null || this.reverse == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     X500Principal x500Principal1 = this.forward.getSubjectX500Principal();
/* 305 */     X500Principal x500Principal2 = this.forward.getIssuerX500Principal();
/* 306 */     X500Principal x500Principal3 = this.reverse.getSubjectX500Principal();
/* 307 */     X500Principal x500Principal4 = this.reverse.getIssuerX500Principal();
/* 308 */     if (!x500Principal2.equals(x500Principal3) || !x500Principal4.equals(x500Principal1)) {
/* 309 */       throw new CertificateException("subject and issuer names in forward and reverse certificates do not match");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 315 */       PublicKey publicKey = this.reverse.getPublicKey();
/* 316 */       if (!(publicKey instanceof DSAPublicKey) || ((DSAPublicKey)publicKey)
/* 317 */         .getParams() != null) {
/* 318 */         this.forward.verify(publicKey);
/*     */       }
/* 320 */       publicKey = this.forward.getPublicKey();
/* 321 */       if (!(publicKey instanceof DSAPublicKey) || ((DSAPublicKey)publicKey)
/* 322 */         .getParams() != null) {
/* 323 */         this.reverse.verify(publicKey);
/*     */       }
/* 325 */     } catch (GeneralSecurityException generalSecurityException) {
/* 326 */       throw new CertificateException("invalid signature: " + generalSecurityException
/* 327 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/X509CertificatePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */