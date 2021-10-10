/*     */ package sun.security.tools.keytool;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Date;
/*     */ import java.util.Random;
/*     */ import sun.security.pkcs10.PKCS10;
/*     */ import sun.security.x509.AlgorithmId;
/*     */ import sun.security.x509.CertificateAlgorithmId;
/*     */ import sun.security.x509.CertificateExtensions;
/*     */ import sun.security.x509.CertificateSerialNumber;
/*     */ import sun.security.x509.CertificateValidity;
/*     */ import sun.security.x509.CertificateVersion;
/*     */ import sun.security.x509.CertificateX509Key;
/*     */ import sun.security.x509.X500Name;
/*     */ import sun.security.x509.X509CertImpl;
/*     */ import sun.security.x509.X509CertInfo;
/*     */ import sun.security.x509.X509Key;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CertAndKeyGen
/*     */ {
/*     */   private SecureRandom prng;
/*     */   private String sigAlg;
/*     */   private KeyPairGenerator keyGen;
/*     */   private PublicKey publicKey;
/*     */   private PrivateKey privateKey;
/*     */   
/*     */   public CertAndKeyGen(String paramString1, String paramString2) throws NoSuchAlgorithmException {
/*  76 */     this.keyGen = KeyPairGenerator.getInstance(paramString1);
/*  77 */     this.sigAlg = paramString2;
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
/*     */   public CertAndKeyGen(String paramString1, String paramString2, String paramString3) throws NoSuchAlgorithmException, NoSuchProviderException {
/*  94 */     if (paramString3 == null) {
/*  95 */       this.keyGen = KeyPairGenerator.getInstance(paramString1);
/*     */     } else {
/*     */       try {
/*  98 */         this.keyGen = KeyPairGenerator.getInstance(paramString1, paramString3);
/*  99 */       } catch (Exception exception) {
/*     */         
/* 101 */         this.keyGen = KeyPairGenerator.getInstance(paramString1);
/*     */       } 
/*     */     } 
/* 104 */     this.sigAlg = paramString2;
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
/*     */   public void setRandom(SecureRandom paramSecureRandom) {
/* 117 */     this.prng = paramSecureRandom;
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
/*     */   public void generate(int paramInt) throws InvalidKeyException {
/*     */     KeyPair keyPair;
/*     */     try {
/* 145 */       if (this.prng == null) {
/* 146 */         this.prng = new SecureRandom();
/*     */       }
/* 148 */       this.keyGen.initialize(paramInt, this.prng);
/* 149 */       keyPair = this.keyGen.generateKeyPair();
/*     */     }
/* 151 */     catch (Exception exception) {
/* 152 */       throw new IllegalArgumentException(exception.getMessage());
/*     */     } 
/*     */     
/* 155 */     this.publicKey = keyPair.getPublic();
/* 156 */     this.privateKey = keyPair.getPrivate();
/*     */ 
/*     */ 
/*     */     
/* 160 */     if (!"X.509".equalsIgnoreCase(this.publicKey.getFormat())) {
/* 161 */       throw new IllegalArgumentException("publicKey's is not X.509, but " + this.publicKey
/* 162 */           .getFormat());
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
/*     */   public X509Key getPublicKey() {
/* 179 */     if (!(this.publicKey instanceof X509Key)) {
/* 180 */       return null;
/*     */     }
/* 182 */     return (X509Key)this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey getPublicKeyAnyway() {
/* 193 */     return this.publicKey;
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
/*     */   public PrivateKey getPrivateKey() {
/* 206 */     return this.privateKey;
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
/*     */   public X509Certificate getSelfCertificate(X500Name paramX500Name, Date paramDate, long paramLong) throws CertificateException, InvalidKeyException, SignatureException, NoSuchAlgorithmException, NoSuchProviderException {
/* 233 */     return getSelfCertificate(paramX500Name, paramDate, paramLong, null);
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
/*     */   public X509Certificate getSelfCertificate(X500Name paramX500Name, Date paramDate, long paramLong, CertificateExtensions paramCertificateExtensions) throws CertificateException, InvalidKeyException, SignatureException, NoSuchAlgorithmException, NoSuchProviderException {
/*     */     try {
/* 246 */       Date date = new Date();
/* 247 */       date.setTime(paramDate.getTime() + paramLong * 1000L);
/*     */       
/* 249 */       CertificateValidity certificateValidity = new CertificateValidity(paramDate, date);
/*     */ 
/*     */       
/* 252 */       X509CertInfo x509CertInfo = new X509CertInfo();
/*     */       
/* 254 */       x509CertInfo.set("version", new CertificateVersion(2));
/*     */       
/* 256 */       x509CertInfo.set("serialNumber", new CertificateSerialNumber((new Random())
/* 257 */             .nextInt() & Integer.MAX_VALUE));
/* 258 */       AlgorithmId algorithmId = AlgorithmId.get(this.sigAlg);
/* 259 */       x509CertInfo.set("algorithmID", new CertificateAlgorithmId(algorithmId));
/*     */       
/* 261 */       x509CertInfo.set("subject", paramX500Name);
/* 262 */       x509CertInfo.set("key", new CertificateX509Key(this.publicKey));
/* 263 */       x509CertInfo.set("validity", certificateValidity);
/* 264 */       x509CertInfo.set("issuer", paramX500Name);
/* 265 */       if (paramCertificateExtensions != null) x509CertInfo.set("extensions", paramCertificateExtensions);
/*     */       
/* 267 */       X509CertImpl x509CertImpl = new X509CertImpl(x509CertInfo);
/* 268 */       x509CertImpl.sign(this.privateKey, this.sigAlg);
/*     */       
/* 270 */       return x509CertImpl;
/*     */     }
/* 272 */     catch (IOException iOException) {
/* 273 */       throw new CertificateEncodingException("getSelfCert: " + iOException
/* 274 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Certificate getSelfCertificate(X500Name paramX500Name, long paramLong) throws CertificateException, InvalidKeyException, SignatureException, NoSuchAlgorithmException, NoSuchProviderException {
/* 283 */     return getSelfCertificate(paramX500Name, new Date(), paramLong);
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
/*     */   public PKCS10 getCertRequest(X500Name paramX500Name) throws InvalidKeyException, SignatureException {
/* 303 */     PKCS10 pKCS10 = new PKCS10(this.publicKey);
/*     */     
/*     */     try {
/* 306 */       Signature signature = Signature.getInstance(this.sigAlg);
/* 307 */       signature.initSign(this.privateKey);
/* 308 */       pKCS10.encodeAndSign(paramX500Name, signature);
/*     */     }
/* 310 */     catch (CertificateException certificateException) {
/* 311 */       throw new SignatureException(this.sigAlg + " CertificateException");
/*     */     }
/* 313 */     catch (IOException iOException) {
/* 314 */       throw new SignatureException(this.sigAlg + " IOException");
/*     */     }
/* 316 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */       
/* 318 */       throw new SignatureException(this.sigAlg + " unavailable?");
/*     */     } 
/* 320 */     return pKCS10;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/tools/keytool/CertAndKeyGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */