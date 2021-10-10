/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Security;
/*     */ import java.util.Map;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SunEntries
/*     */ {
/*  83 */   private static final boolean useLegacyDSA = Boolean.parseBoolean(
/*  84 */       GetPropertyAction.privilegedGetProperty("jdk.security.legacyDSAKeyPairGenerator"));
/*     */ 
/*     */   
/*     */   private static final String PROP_EGD = "java.security.egd";
/*     */ 
/*     */   
/*     */   private static final String PROP_RNDSOURCE = "securerandom.source";
/*     */ 
/*     */   
/*     */   static final String URL_DEV_RANDOM = "file:/dev/random";
/*     */ 
/*     */   
/*     */   static final String URL_DEV_URANDOM = "file:/dev/urandom";
/*     */ 
/*     */ 
/*     */   
/*     */   static void putEntries(Map<Object, Object> paramMap) {
/* 101 */     boolean bool = NativePRNG.isAvailable();
/*     */     
/* 103 */     boolean bool1 = (seedSource.equals("file:/dev/urandom") || seedSource.equals("file:/dev/random")) ? true : false;
/*     */     
/* 105 */     if (bool && bool1) {
/* 106 */       paramMap.put("SecureRandom.NativePRNG", "sun.security.provider.NativePRNG");
/*     */     }
/*     */     
/* 109 */     paramMap.put("SecureRandom.SHA1PRNG", "sun.security.provider.SecureRandom");
/*     */     
/* 111 */     if (bool && !bool1) {
/* 112 */       paramMap.put("SecureRandom.NativePRNG", "sun.security.provider.NativePRNG");
/*     */     }
/*     */ 
/*     */     
/* 116 */     if (NativePRNG.Blocking.isAvailable()) {
/* 117 */       paramMap.put("SecureRandom.NativePRNGBlocking", "sun.security.provider.NativePRNG$Blocking");
/*     */     }
/*     */ 
/*     */     
/* 121 */     if (NativePRNG.NonBlocking.isAvailable()) {
/* 122 */       paramMap.put("SecureRandom.NativePRNGNonBlocking", "sun.security.provider.NativePRNG$NonBlocking");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     paramMap.put("Signature.SHA1withDSA", "sun.security.provider.DSA$SHA1withDSA");
/*     */     
/* 131 */     paramMap.put("Signature.NONEwithDSA", "sun.security.provider.DSA$RawDSA");
/* 132 */     paramMap.put("Alg.Alias.Signature.RawDSA", "NONEwithDSA");
/* 133 */     paramMap.put("Signature.SHA224withDSA", "sun.security.provider.DSA$SHA224withDSA");
/*     */     
/* 135 */     paramMap.put("Signature.SHA256withDSA", "sun.security.provider.DSA$SHA256withDSA");
/*     */ 
/*     */     
/* 138 */     String str1 = "java.security.interfaces.DSAPublicKey|java.security.interfaces.DSAPrivateKey";
/*     */     
/* 140 */     paramMap.put("Signature.SHA1withDSA SupportedKeyClasses", str1);
/* 141 */     paramMap.put("Signature.NONEwithDSA SupportedKeyClasses", str1);
/* 142 */     paramMap.put("Signature.SHA224withDSA SupportedKeyClasses", str1);
/* 143 */     paramMap.put("Signature.SHA256withDSA SupportedKeyClasses", str1);
/*     */     
/* 145 */     paramMap.put("Alg.Alias.Signature.DSA", "SHA1withDSA");
/* 146 */     paramMap.put("Alg.Alias.Signature.DSS", "SHA1withDSA");
/* 147 */     paramMap.put("Alg.Alias.Signature.SHA/DSA", "SHA1withDSA");
/* 148 */     paramMap.put("Alg.Alias.Signature.SHA-1/DSA", "SHA1withDSA");
/* 149 */     paramMap.put("Alg.Alias.Signature.SHA1/DSA", "SHA1withDSA");
/* 150 */     paramMap.put("Alg.Alias.Signature.SHAwithDSA", "SHA1withDSA");
/* 151 */     paramMap.put("Alg.Alias.Signature.DSAWithSHA1", "SHA1withDSA");
/* 152 */     paramMap.put("Alg.Alias.Signature.OID.1.2.840.10040.4.3", "SHA1withDSA");
/*     */     
/* 154 */     paramMap.put("Alg.Alias.Signature.1.2.840.10040.4.3", "SHA1withDSA");
/* 155 */     paramMap.put("Alg.Alias.Signature.1.3.14.3.2.13", "SHA1withDSA");
/* 156 */     paramMap.put("Alg.Alias.Signature.1.3.14.3.2.27", "SHA1withDSA");
/* 157 */     paramMap.put("Alg.Alias.Signature.OID.2.16.840.1.101.3.4.3.1", "SHA224withDSA");
/*     */     
/* 159 */     paramMap.put("Alg.Alias.Signature.2.16.840.1.101.3.4.3.1", "SHA224withDSA");
/* 160 */     paramMap.put("Alg.Alias.Signature.OID.2.16.840.1.101.3.4.3.2", "SHA256withDSA");
/*     */     
/* 162 */     paramMap.put("Alg.Alias.Signature.2.16.840.1.101.3.4.3.2", "SHA256withDSA");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     String str2 = "sun.security.provider.DSAKeyPairGenerator$";
/* 168 */     str2 = str2 + (useLegacyDSA ? "Legacy" : "Current");
/* 169 */     paramMap.put("KeyPairGenerator.DSA", str2);
/* 170 */     paramMap.put("Alg.Alias.KeyPairGenerator.OID.1.2.840.10040.4.1", "DSA");
/* 171 */     paramMap.put("Alg.Alias.KeyPairGenerator.1.2.840.10040.4.1", "DSA");
/* 172 */     paramMap.put("Alg.Alias.KeyPairGenerator.1.3.14.3.2.12", "DSA");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     paramMap.put("MessageDigest.MD2", "sun.security.provider.MD2");
/* 178 */     paramMap.put("MessageDigest.MD5", "sun.security.provider.MD5");
/* 179 */     paramMap.put("MessageDigest.SHA", "sun.security.provider.SHA");
/*     */     
/* 181 */     paramMap.put("Alg.Alias.MessageDigest.SHA-1", "SHA");
/* 182 */     paramMap.put("Alg.Alias.MessageDigest.SHA1", "SHA");
/* 183 */     paramMap.put("Alg.Alias.MessageDigest.1.3.14.3.2.26", "SHA");
/* 184 */     paramMap.put("Alg.Alias.MessageDigest.OID.1.3.14.3.2.26", "SHA");
/*     */     
/* 186 */     paramMap.put("MessageDigest.SHA-224", "sun.security.provider.SHA2$SHA224");
/* 187 */     paramMap.put("Alg.Alias.MessageDigest.2.16.840.1.101.3.4.2.4", "SHA-224");
/* 188 */     paramMap.put("Alg.Alias.MessageDigest.OID.2.16.840.1.101.3.4.2.4", "SHA-224");
/*     */ 
/*     */     
/* 191 */     paramMap.put("MessageDigest.SHA-256", "sun.security.provider.SHA2$SHA256");
/* 192 */     paramMap.put("Alg.Alias.MessageDigest.2.16.840.1.101.3.4.2.1", "SHA-256");
/* 193 */     paramMap.put("Alg.Alias.MessageDigest.OID.2.16.840.1.101.3.4.2.1", "SHA-256");
/*     */     
/* 195 */     paramMap.put("MessageDigest.SHA-384", "sun.security.provider.SHA5$SHA384");
/* 196 */     paramMap.put("Alg.Alias.MessageDigest.2.16.840.1.101.3.4.2.2", "SHA-384");
/* 197 */     paramMap.put("Alg.Alias.MessageDigest.OID.2.16.840.1.101.3.4.2.2", "SHA-384");
/*     */     
/* 199 */     paramMap.put("MessageDigest.SHA-512", "sun.security.provider.SHA5$SHA512");
/* 200 */     paramMap.put("Alg.Alias.MessageDigest.2.16.840.1.101.3.4.2.3", "SHA-512");
/* 201 */     paramMap.put("Alg.Alias.MessageDigest.OID.2.16.840.1.101.3.4.2.3", "SHA-512");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     paramMap.put("AlgorithmParameterGenerator.DSA", "sun.security.provider.DSAParameterGenerator");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     paramMap.put("AlgorithmParameters.DSA", "sun.security.provider.DSAParameters");
/*     */     
/* 215 */     paramMap.put("Alg.Alias.AlgorithmParameters.OID.1.2.840.10040.4.1", "DSA");
/* 216 */     paramMap.put("Alg.Alias.AlgorithmParameters.1.2.840.10040.4.1", "DSA");
/* 217 */     paramMap.put("Alg.Alias.AlgorithmParameters.1.3.14.3.2.12", "DSA");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     paramMap.put("KeyFactory.DSA", "sun.security.provider.DSAKeyFactory");
/* 223 */     paramMap.put("Alg.Alias.KeyFactory.OID.1.2.840.10040.4.1", "DSA");
/* 224 */     paramMap.put("Alg.Alias.KeyFactory.1.2.840.10040.4.1", "DSA");
/* 225 */     paramMap.put("Alg.Alias.KeyFactory.1.3.14.3.2.12", "DSA");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     paramMap.put("CertificateFactory.X.509", "sun.security.provider.X509Factory");
/*     */     
/* 232 */     paramMap.put("Alg.Alias.CertificateFactory.X509", "X.509");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     paramMap.put("KeyStore.JKS", "sun.security.provider.JavaKeyStore$DualFormatJKS");
/*     */     
/* 239 */     paramMap.put("KeyStore.CaseExactJKS", "sun.security.provider.JavaKeyStore$CaseExactJKS");
/*     */     
/* 241 */     paramMap.put("KeyStore.DKS", "sun.security.provider.DomainKeyStore$DKS");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     paramMap.put("Policy.JavaPolicy", "sun.security.provider.PolicySpiFile");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 251 */     paramMap.put("Configuration.JavaLoginConfig", "sun.security.provider.ConfigFile$Spi");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     paramMap.put("CertPathBuilder.PKIX", "sun.security.provider.certpath.SunCertPathBuilder");
/*     */     
/* 259 */     paramMap.put("CertPathBuilder.PKIX ValidationAlgorithm", "RFC5280");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 265 */     paramMap.put("CertPathValidator.PKIX", "sun.security.provider.certpath.PKIXCertPathValidator");
/*     */     
/* 267 */     paramMap.put("CertPathValidator.PKIX ValidationAlgorithm", "RFC5280");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 273 */     paramMap.put("CertStore.LDAP", "sun.security.provider.certpath.ldap.LDAPCertStore");
/*     */     
/* 275 */     paramMap.put("CertStore.LDAP LDAPSchema", "RFC2587");
/* 276 */     paramMap.put("CertStore.Collection", "sun.security.provider.certpath.CollectionCertStore");
/*     */     
/* 278 */     paramMap.put("CertStore.com.sun.security.IndexedCollection", "sun.security.provider.certpath.IndexedCollectionCertStore");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     paramMap.put("Signature.NONEwithDSA KeySize", "1024");
/* 285 */     paramMap.put("Signature.SHA1withDSA KeySize", "1024");
/* 286 */     paramMap.put("Signature.SHA224withDSA KeySize", "2048");
/* 287 */     paramMap.put("Signature.SHA256withDSA KeySize", "2048");
/*     */     
/* 289 */     paramMap.put("KeyPairGenerator.DSA KeySize", "2048");
/* 290 */     paramMap.put("AlgorithmParameterGenerator.DSA KeySize", "2048");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     paramMap.put("Signature.SHA1withDSA ImplementedIn", "Software");
/* 296 */     paramMap.put("KeyPairGenerator.DSA ImplementedIn", "Software");
/* 297 */     paramMap.put("MessageDigest.MD5 ImplementedIn", "Software");
/* 298 */     paramMap.put("MessageDigest.SHA ImplementedIn", "Software");
/* 299 */     paramMap.put("AlgorithmParameterGenerator.DSA ImplementedIn", "Software");
/*     */     
/* 301 */     paramMap.put("AlgorithmParameters.DSA ImplementedIn", "Software");
/* 302 */     paramMap.put("KeyFactory.DSA ImplementedIn", "Software");
/* 303 */     paramMap.put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
/* 304 */     paramMap.put("CertificateFactory.X.509 ImplementedIn", "Software");
/* 305 */     paramMap.put("KeyStore.JKS ImplementedIn", "Software");
/* 306 */     paramMap.put("CertPathValidator.PKIX ImplementedIn", "Software");
/* 307 */     paramMap.put("CertPathBuilder.PKIX ImplementedIn", "Software");
/* 308 */     paramMap.put("CertStore.LDAP ImplementedIn", "Software");
/* 309 */     paramMap.put("CertStore.Collection ImplementedIn", "Software");
/* 310 */     paramMap.put("CertStore.com.sun.security.IndexedCollection ImplementedIn", "Software");
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
/* 326 */   private static final String seedSource = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */       {
/*     */         
/*     */         public String run()
/*     */         {
/* 331 */           String str = System.getProperty("java.security.egd", "");
/* 332 */           if (str.length() != 0) {
/* 333 */             return str;
/*     */           }
/* 335 */           str = Security.getProperty("securerandom.source");
/* 336 */           if (str == null) {
/* 337 */             return "";
/*     */           }
/* 339 */           return str;
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   static String getSeedSource() {
/* 345 */     return seedSource;
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
/*     */   static File getDeviceFile(URL paramURL) throws IOException {
/*     */     try {
/* 361 */       URI uRI = paramURL.toURI();
/* 362 */       if (uRI.isOpaque()) {
/*     */ 
/*     */         
/* 365 */         URI uRI1 = (new File(System.getProperty("user.dir"))).toURI();
/*     */         
/* 367 */         String str = uRI1.toString() + uRI.toString().substring(5);
/* 368 */         return new File(URI.create(str));
/*     */       } 
/* 370 */       return new File(uRI);
/*     */     }
/* 372 */     catch (URISyntaxException uRISyntaxException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 377 */       return new File(paramURL.getPath());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/SunEntries.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */