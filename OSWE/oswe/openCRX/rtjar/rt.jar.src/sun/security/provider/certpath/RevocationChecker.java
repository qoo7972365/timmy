/*      */ package sun.security.provider.certpath;
/*      */ import java.io.IOException;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.security.InvalidAlgorithmParameterException;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PublicKey;
/*      */ import java.security.Security;
/*      */ import java.security.cert.CRL;
/*      */ import java.security.cert.CRLException;
/*      */ import java.security.cert.CRLReason;
/*      */ import java.security.cert.CertPathBuilder;
/*      */ import java.security.cert.CertPathValidatorException;
/*      */ import java.security.cert.CertStore;
/*      */ import java.security.cert.CertStoreException;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateException;
/*      */ import java.security.cert.CertificateRevokedException;
/*      */ import java.security.cert.Extension;
/*      */ import java.security.cert.PKIXBuilderParameters;
/*      */ import java.security.cert.PKIXCertPathBuilderResult;
/*      */ import java.security.cert.PKIXRevocationChecker;
/*      */ import java.security.cert.TrustAnchor;
/*      */ import java.security.cert.X509CRL;
/*      */ import java.security.cert.X509CRLEntry;
/*      */ import java.security.cert.X509CRLSelector;
/*      */ import java.security.cert.X509CertSelector;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.x509.AccessDescription;
/*      */ import sun.security.x509.AuthorityInfoAccessExtension;
/*      */ import sun.security.x509.CRLDistributionPointsExtension;
/*      */ import sun.security.x509.DistributionPoint;
/*      */ import sun.security.x509.PKIXExtensions;
/*      */ import sun.security.x509.X500Name;
/*      */ import sun.security.x509.X509CRLEntryImpl;
/*      */ import sun.security.x509.X509CertImpl;
/*      */ 
/*      */ class RevocationChecker extends PKIXRevocationChecker {
/*   52 */   private static final Debug debug = Debug.getInstance("certpath");
/*      */   
/*      */   private TrustAnchor anchor;
/*      */   private PKIX.ValidatorParams params;
/*      */   private boolean onlyEE;
/*      */   private boolean softFail;
/*      */   private boolean crlDP;
/*      */   private URI responderURI;
/*      */   private X509Certificate responderCert;
/*      */   private List<CertStore> certStores;
/*      */   private Map<X509Certificate, byte[]> ocspResponses;
/*      */   private List<Extension> ocspExtensions;
/*      */   private final boolean legacy;
/*   65 */   private LinkedList<CertPathValidatorException> softFailExceptions = new LinkedList<>();
/*      */   
/*      */   private OCSPResponse.IssuerInfo issuerInfo;
/*      */   private PublicKey prevPubKey;
/*      */   private boolean crlSignFlag;
/*      */   private int certIndex;
/*      */   
/*      */   private enum Mode
/*      */   {
/*   74 */     PREFER_OCSP, PREFER_CRLS, ONLY_CRLS, ONLY_OCSP; }
/*   75 */   private Mode mode = Mode.PREFER_OCSP; private static final long MAX_CLOCK_SKEW = 900000L; private static final String HEX_DIGITS = "0123456789ABCDEFabcdef";
/*      */   private static class RevocationProperties {
/*      */     boolean onlyEE;
/*      */     boolean ocspEnabled;
/*      */     boolean crlDPEnabled;
/*      */     String ocspUrl;
/*      */     String ocspSubject;
/*      */     String ocspIssuer;
/*      */     String ocspSerial;
/*      */     
/*      */     private RevocationProperties() {} }
/*      */   
/*      */   RevocationChecker() {
/*   88 */     this.legacy = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   RevocationChecker(TrustAnchor paramTrustAnchor, PKIX.ValidatorParams paramValidatorParams) throws CertPathValidatorException {
/*   94 */     this.legacy = true;
/*   95 */     init(paramTrustAnchor, paramValidatorParams);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void init(TrustAnchor paramTrustAnchor, PKIX.ValidatorParams paramValidatorParams) throws CertPathValidatorException {
/*  101 */     RevocationProperties revocationProperties = getRevocationProperties();
/*  102 */     URI uRI = getOcspResponder();
/*  103 */     this.responderURI = (uRI == null) ? toURI(revocationProperties.ocspUrl) : uRI;
/*  104 */     X509Certificate x509Certificate = getOcspResponderCert();
/*  105 */     this
/*  106 */       .responderCert = (x509Certificate == null) ? getResponderCert(revocationProperties, paramValidatorParams.trustAnchors(), paramValidatorParams
/*  107 */         .certStores()) : x509Certificate;
/*      */     
/*  109 */     Set<PKIXRevocationChecker.Option> set = getOptions();
/*  110 */     for (PKIXRevocationChecker.Option option : set) {
/*  111 */       switch (option) {
/*      */         case PREFER_OCSP:
/*      */         case ONLY_OCSP:
/*      */         case PREFER_CRLS:
/*      */         case ONLY_CRLS:
/*      */           continue;
/*      */       } 
/*  118 */       throw new CertPathValidatorException("Unrecognized revocation parameter option: " + option);
/*      */     } 
/*      */ 
/*      */     
/*  122 */     this.softFail = set.contains(PKIXRevocationChecker.Option.SOFT_FAIL);
/*      */ 
/*      */     
/*  125 */     if (this.legacy) {
/*  126 */       this.mode = revocationProperties.ocspEnabled ? Mode.PREFER_OCSP : Mode.ONLY_CRLS;
/*  127 */       this.onlyEE = revocationProperties.onlyEE;
/*      */     } else {
/*  129 */       if (set.contains(PKIXRevocationChecker.Option.NO_FALLBACK)) {
/*  130 */         if (set.contains(PKIXRevocationChecker.Option.PREFER_CRLS)) {
/*  131 */           this.mode = Mode.ONLY_CRLS;
/*      */         } else {
/*  133 */           this.mode = Mode.ONLY_OCSP;
/*      */         } 
/*  135 */       } else if (set.contains(PKIXRevocationChecker.Option.PREFER_CRLS)) {
/*  136 */         this.mode = Mode.PREFER_CRLS;
/*      */       } 
/*  138 */       this.onlyEE = set.contains(PKIXRevocationChecker.Option.ONLY_END_ENTITY);
/*      */     } 
/*  140 */     if (this.legacy) {
/*  141 */       this.crlDP = revocationProperties.crlDPEnabled;
/*      */     } else {
/*  143 */       this.crlDP = true;
/*      */     } 
/*  145 */     this.ocspResponses = getOcspResponses();
/*  146 */     this.ocspExtensions = getOcspExtensions();
/*      */     
/*  148 */     this.anchor = paramTrustAnchor;
/*  149 */     this.params = paramValidatorParams;
/*  150 */     this.certStores = new ArrayList<>(paramValidatorParams.certStores());
/*      */     try {
/*  152 */       this.certStores.add(CertStore.getInstance("Collection", new CollectionCertStoreParameters(paramValidatorParams
/*  153 */               .certificates())));
/*  154 */     } catch (InvalidAlgorithmParameterException|NoSuchAlgorithmException invalidAlgorithmParameterException) {
/*      */ 
/*      */ 
/*      */       
/*  158 */       if (debug != null) {
/*  159 */         debug.println("RevocationChecker: error creating Collection CertStore: " + invalidAlgorithmParameterException);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static URI toURI(String paramString) throws CertPathValidatorException {
/*      */     try {
/*  169 */       if (paramString != null) {
/*  170 */         return new URI(paramString);
/*      */       }
/*  172 */       return null;
/*  173 */     } catch (URISyntaxException uRISyntaxException) {
/*  174 */       throw new CertPathValidatorException("cannot parse ocsp.responderURL property", uRISyntaxException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static RevocationProperties getRevocationProperties() {
/*  180 */     return AccessController.<RevocationProperties>doPrivileged(new PrivilegedAction<RevocationProperties>()
/*      */         {
/*      */           public RevocationChecker.RevocationProperties run() {
/*  183 */             RevocationChecker.RevocationProperties revocationProperties = new RevocationChecker.RevocationProperties();
/*  184 */             String str1 = Security.getProperty("com.sun.security.onlyCheckRevocationOfEECert");
/*      */             
/*  186 */             revocationProperties
/*  187 */               .onlyEE = (str1 != null && str1.equalsIgnoreCase("true"));
/*  188 */             String str2 = Security.getProperty("ocsp.enable");
/*  189 */             revocationProperties
/*  190 */               .ocspEnabled = (str2 != null && str2.equalsIgnoreCase("true"));
/*  191 */             revocationProperties.ocspUrl = Security.getProperty("ocsp.responderURL");
/*  192 */             revocationProperties
/*  193 */               .ocspSubject = Security.getProperty("ocsp.responderCertSubjectName");
/*  194 */             revocationProperties
/*  195 */               .ocspIssuer = Security.getProperty("ocsp.responderCertIssuerName");
/*  196 */             revocationProperties
/*  197 */               .ocspSerial = Security.getProperty("ocsp.responderCertSerialNumber");
/*  198 */             revocationProperties
/*  199 */               .crlDPEnabled = Boolean.getBoolean("com.sun.security.enableCRLDP");
/*  200 */             return revocationProperties;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static X509Certificate getResponderCert(RevocationProperties paramRevocationProperties, Set<TrustAnchor> paramSet, List<CertStore> paramList) throws CertPathValidatorException {
/*  211 */     if (paramRevocationProperties.ocspSubject != null)
/*  212 */       return getResponderCert(paramRevocationProperties.ocspSubject, paramSet, paramList); 
/*  213 */     if (paramRevocationProperties.ocspIssuer != null && paramRevocationProperties.ocspSerial != null) {
/*  214 */       return getResponderCert(paramRevocationProperties.ocspIssuer, paramRevocationProperties.ocspSerial, paramSet, paramList);
/*      */     }
/*  216 */     if (paramRevocationProperties.ocspIssuer != null || paramRevocationProperties.ocspSerial != null) {
/*  217 */       throw new CertPathValidatorException("Must specify both ocsp.responderCertIssuerName and ocsp.responderCertSerialNumber properties");
/*      */     }
/*      */ 
/*      */     
/*  221 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static X509Certificate getResponderCert(String paramString, Set<TrustAnchor> paramSet, List<CertStore> paramList) throws CertPathValidatorException {
/*  229 */     X509CertSelector x509CertSelector = new X509CertSelector();
/*      */     try {
/*  231 */       x509CertSelector.setSubject(new X500Principal(paramString));
/*  232 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  233 */       throw new CertPathValidatorException("cannot parse ocsp.responderCertSubjectName property", illegalArgumentException);
/*      */     } 
/*      */     
/*  236 */     return getResponderCert(x509CertSelector, paramSet, paramList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static X509Certificate getResponderCert(String paramString1, String paramString2, Set<TrustAnchor> paramSet, List<CertStore> paramList) throws CertPathValidatorException {
/*  245 */     X509CertSelector x509CertSelector = new X509CertSelector();
/*      */     try {
/*  247 */       x509CertSelector.setIssuer(new X500Principal(paramString1));
/*  248 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  249 */       throw new CertPathValidatorException("cannot parse ocsp.responderCertIssuerName property", illegalArgumentException);
/*      */     } 
/*      */     
/*      */     try {
/*  253 */       x509CertSelector.setSerialNumber(new BigInteger(stripOutSeparators(paramString2), 16));
/*  254 */     } catch (NumberFormatException numberFormatException) {
/*  255 */       throw new CertPathValidatorException("cannot parse ocsp.responderCertSerialNumber property", numberFormatException);
/*      */     } 
/*      */     
/*  258 */     return getResponderCert(x509CertSelector, paramSet, paramList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static X509Certificate getResponderCert(X509CertSelector paramX509CertSelector, Set<TrustAnchor> paramSet, List<CertStore> paramList) throws CertPathValidatorException {
/*  267 */     for (TrustAnchor trustAnchor : paramSet) {
/*  268 */       X509Certificate x509Certificate = trustAnchor.getTrustedCert();
/*  269 */       if (x509Certificate == null) {
/*      */         continue;
/*      */       }
/*  272 */       if (paramX509CertSelector.match(x509Certificate)) {
/*  273 */         return x509Certificate;
/*      */       }
/*      */     } 
/*      */     
/*  277 */     for (CertStore certStore : paramList) {
/*      */       
/*      */       try {
/*  280 */         Collection<? extends Certificate> collection = certStore.getCertificates(paramX509CertSelector);
/*  281 */         if (!collection.isEmpty()) {
/*  282 */           return collection.iterator().next();
/*      */         }
/*  284 */       } catch (CertStoreException certStoreException) {
/*      */         
/*  286 */         if (debug != null) {
/*  287 */           debug.println("CertStore exception:" + certStoreException);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  292 */     throw new CertPathValidatorException("Cannot find the responder's certificate (set using the OCSP security properties).");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void init(boolean paramBoolean) throws CertPathValidatorException {
/*  299 */     if (paramBoolean) {
/*  300 */       throw new CertPathValidatorException("forward checking not supported");
/*      */     }
/*      */     
/*  303 */     if (this.anchor != null) {
/*  304 */       this.issuerInfo = new OCSPResponse.IssuerInfo(this.anchor);
/*  305 */       this.prevPubKey = this.issuerInfo.getPublicKey();
/*      */     } 
/*      */     
/*  308 */     this.crlSignFlag = true;
/*  309 */     if (this.params != null && this.params.certPath() != null) {
/*  310 */       this.certIndex = this.params.certPath().getCertificates().size() - 1;
/*      */     } else {
/*  312 */       this.certIndex = -1;
/*      */     } 
/*  314 */     this.softFailExceptions.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isForwardCheckingSupported() {
/*  319 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<String> getSupportedExtensions() {
/*  324 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<CertPathValidatorException> getSoftFailExceptions() {
/*  329 */     return Collections.unmodifiableList(this.softFailExceptions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void check(Certificate paramCertificate, Collection<String> paramCollection) throws CertPathValidatorException {
/*  336 */     check((X509Certificate)paramCertificate, paramCollection, this.prevPubKey, this.crlSignFlag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void check(X509Certificate paramX509Certificate, Collection<String> paramCollection, PublicKey paramPublicKey, boolean paramBoolean) throws CertPathValidatorException {
/*  345 */     if (debug != null) {
/*  346 */       debug.println("RevocationChecker.check: checking cert\n  SN: " + 
/*  347 */           Debug.toHexString(paramX509Certificate.getSerialNumber()) + "\n  Subject: " + paramX509Certificate
/*  348 */           .getSubjectX500Principal() + "\n  Issuer: " + paramX509Certificate
/*  349 */           .getIssuerX500Principal());
/*      */     }
/*      */     try {
/*  352 */       if (this.onlyEE && paramX509Certificate.getBasicConstraints() != -1) {
/*  353 */         if (debug != null) {
/*  354 */           debug.println("Skipping revocation check; cert is not an end entity cert");
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*  359 */       switch (this.mode) {
/*      */         case PREFER_OCSP:
/*      */         case ONLY_OCSP:
/*  362 */           checkOCSP(paramX509Certificate, paramCollection);
/*      */           break;
/*      */         case PREFER_CRLS:
/*      */         case ONLY_CRLS:
/*  366 */           checkCRLs(paramX509Certificate, paramCollection, (Set<X509Certificate>)null, paramPublicKey, paramBoolean);
/*      */           break;
/*      */       } 
/*      */     
/*  370 */     } catch (CertPathValidatorException certPathValidatorException1) {
/*  371 */       if (certPathValidatorException1.getReason() == CertPathValidatorException.BasicReason.REVOKED) {
/*  372 */         throw certPathValidatorException1;
/*      */       }
/*  374 */       boolean bool = isSoftFailException(certPathValidatorException1);
/*  375 */       if (bool) {
/*  376 */         if (this.mode == Mode.ONLY_OCSP || this.mode == Mode.ONLY_CRLS) {
/*      */           return;
/*      */         }
/*      */       }
/*  380 */       else if (this.mode == Mode.ONLY_OCSP || this.mode == Mode.ONLY_CRLS) {
/*  381 */         throw certPathValidatorException1;
/*      */       } 
/*      */       
/*  384 */       CertPathValidatorException certPathValidatorException2 = certPathValidatorException1;
/*      */       
/*  386 */       if (debug != null) {
/*  387 */         debug.println("RevocationChecker.check() " + certPathValidatorException1.getMessage());
/*  388 */         debug.println("RevocationChecker.check() preparing to failover");
/*      */       } 
/*      */       try {
/*  391 */         switch (this.mode) {
/*      */           case PREFER_OCSP:
/*  393 */             checkCRLs(paramX509Certificate, paramCollection, (Set<X509Certificate>)null, paramPublicKey, paramBoolean);
/*      */             break;
/*      */           
/*      */           case PREFER_CRLS:
/*  397 */             checkOCSP(paramX509Certificate, paramCollection);
/*      */             break;
/*      */         } 
/*  400 */       } catch (CertPathValidatorException certPathValidatorException) {
/*  401 */         if (debug != null) {
/*  402 */           debug.println("RevocationChecker.check() failover failed");
/*  403 */           debug.println("RevocationChecker.check() " + certPathValidatorException.getMessage());
/*      */         } 
/*  405 */         if (certPathValidatorException.getReason() == CertPathValidatorException.BasicReason.REVOKED) {
/*  406 */           throw certPathValidatorException;
/*      */         }
/*  408 */         if (!isSoftFailException(certPathValidatorException)) {
/*  409 */           certPathValidatorException2.addSuppressed(certPathValidatorException);
/*  410 */           throw certPathValidatorException2;
/*      */         } 
/*      */         
/*  413 */         if (!bool) {
/*  414 */           throw certPathValidatorException2;
/*      */         }
/*      */       } 
/*      */     } finally {
/*      */       
/*  419 */       updateState(paramX509Certificate);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isSoftFailException(CertPathValidatorException paramCertPathValidatorException) {
/*  424 */     if (this.softFail && paramCertPathValidatorException
/*  425 */       .getReason() == CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  430 */       CertPathValidatorException certPathValidatorException = new CertPathValidatorException(paramCertPathValidatorException.getMessage(), paramCertPathValidatorException.getCause(), this.params.certPath(), this.certIndex, paramCertPathValidatorException.getReason());
/*  431 */       this.softFailExceptions.addFirst(certPathValidatorException);
/*  432 */       return true;
/*      */     } 
/*  434 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateState(X509Certificate paramX509Certificate) throws CertPathValidatorException {
/*  440 */     this.issuerInfo = new OCSPResponse.IssuerInfo(this.anchor, paramX509Certificate);
/*      */ 
/*      */     
/*  443 */     PublicKey publicKey = paramX509Certificate.getPublicKey();
/*  444 */     if (PKIX.isDSAPublicKeyWithoutParams(publicKey))
/*      */     {
/*  446 */       publicKey = BasicChecker.makeInheritedParamsKey(publicKey, this.prevPubKey);
/*      */     }
/*  448 */     this.prevPubKey = publicKey;
/*  449 */     this.crlSignFlag = certCanSignCrl(paramX509Certificate);
/*  450 */     if (this.certIndex > 0) {
/*  451 */       this.certIndex--;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkCRLs(X509Certificate paramX509Certificate, Collection<String> paramCollection, Set<X509Certificate> paramSet, PublicKey paramPublicKey, boolean paramBoolean) throws CertPathValidatorException {
/*  464 */     checkCRLs(paramX509Certificate, paramPublicKey, (X509Certificate)null, paramBoolean, true, paramSet, this.params
/*  465 */         .trustAnchors());
/*      */   }
/*      */   
/*      */   static boolean isCausedByNetworkIssue(String paramString, CertStoreException paramCertStoreException) {
/*      */     boolean bool;
/*  470 */     Throwable throwable = paramCertStoreException.getCause();
/*      */     
/*  472 */     switch (paramString) {
/*      */       case "LDAP":
/*  474 */         if (throwable != null) {
/*      */           
/*  476 */           String str = throwable.getClass().getName();
/*      */           
/*  478 */           bool = (str.equals("javax.naming.ServiceUnavailableException") || str.equals("javax.naming.CommunicationException")) ? true : false;
/*      */         } else {
/*  480 */           bool = false;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  493 */         return bool;case "SSLServer": bool = (throwable != null && throwable instanceof IOException) ? true : false; return bool;case "URI": bool = (throwable != null && throwable instanceof IOException) ? true : false; return bool;
/*      */     } 
/*      */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkCRLs(X509Certificate paramX509Certificate1, PublicKey paramPublicKey, X509Certificate paramX509Certificate2, boolean paramBoolean1, boolean paramBoolean2, Set<X509Certificate> paramSet, Set<TrustAnchor> paramSet1) throws CertPathValidatorException {
/*  503 */     if (debug != null) {
/*  504 */       debug.println("RevocationChecker.checkCRLs() ---checking revocation status ...");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  511 */     if (paramSet != null && paramSet.contains(paramX509Certificate1)) {
/*  512 */       if (debug != null) {
/*  513 */         debug.println("RevocationChecker.checkCRLs() circular dependency");
/*      */       }
/*      */       
/*  516 */       throw new CertPathValidatorException("Could not determine revocation status", null, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  521 */     HashSet<X509CRL> hashSet1 = new HashSet();
/*  522 */     HashSet<X509CRL> hashSet2 = new HashSet();
/*  523 */     X509CRLSelector x509CRLSelector = new X509CRLSelector();
/*  524 */     x509CRLSelector.setCertificateChecking(paramX509Certificate1);
/*  525 */     CertPathHelper.setDateAndTime(x509CRLSelector, this.params.date(), 900000L);
/*      */ 
/*      */     
/*  528 */     CertPathValidatorException certPathValidatorException = null;
/*  529 */     for (CertStore certStore : this.certStores) {
/*      */       try {
/*  531 */         for (CRL cRL : certStore.getCRLs(x509CRLSelector)) {
/*  532 */           hashSet1.add((X509CRL)cRL);
/*      */         }
/*  534 */       } catch (CertStoreException certStoreException) {
/*  535 */         if (debug != null) {
/*  536 */           debug.println("RevocationChecker.checkCRLs() CertStoreException: " + certStoreException
/*  537 */               .getMessage());
/*      */         }
/*  539 */         if (certPathValidatorException == null && 
/*  540 */           isCausedByNetworkIssue(certStore.getType(), certStoreException))
/*      */         {
/*  542 */           certPathValidatorException = new CertPathValidatorException("Unable to determine revocation status due to network error", certStoreException, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  550 */     if (debug != null) {
/*  551 */       debug.println("RevocationChecker.checkCRLs() possible crls.size() = " + hashSet1
/*  552 */           .size());
/*      */     }
/*  554 */     boolean[] arrayOfBoolean = new boolean[9];
/*  555 */     if (!hashSet1.isEmpty())
/*      */     {
/*      */       
/*  558 */       hashSet2.addAll(verifyPossibleCRLs(hashSet1, paramX509Certificate1, paramPublicKey, paramBoolean1, arrayOfBoolean, paramSet1));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  563 */     if (debug != null) {
/*  564 */       debug.println("RevocationChecker.checkCRLs() approved crls.size() = " + hashSet2
/*  565 */           .size());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  570 */     if (!hashSet2.isEmpty() && 
/*  571 */       Arrays.equals(arrayOfBoolean, ALL_REASONS)) {
/*      */       
/*  573 */       checkApprovedCRLs(paramX509Certificate1, hashSet2);
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*  578 */         if (this.crlDP) {
/*  579 */           hashSet2.addAll(DistributionPointFetcher.getCRLs(x509CRLSelector, paramBoolean1, paramPublicKey, paramX509Certificate2, this.params
/*      */                 
/*  581 */                 .sigProvider(), this.certStores, arrayOfBoolean, paramSet1, null, this.params
/*  582 */                 .variant()));
/*      */         }
/*  584 */       } catch (CertStoreException certStoreException) {
/*  585 */         if (certStoreException instanceof PKIX.CertStoreTypeException) {
/*  586 */           PKIX.CertStoreTypeException certStoreTypeException = (PKIX.CertStoreTypeException)certStoreException;
/*  587 */           if (isCausedByNetworkIssue(certStoreTypeException.getType(), certStoreException)) {
/*  588 */             throw new CertPathValidatorException("Unable to determine revocation status due to network error", certStoreException, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  594 */         throw new CertPathValidatorException(certStoreException);
/*      */       } 
/*  596 */       if (!hashSet2.isEmpty() && 
/*  597 */         Arrays.equals(arrayOfBoolean, ALL_REASONS)) {
/*      */         
/*  599 */         checkApprovedCRLs(paramX509Certificate1, hashSet2);
/*      */       } else {
/*  601 */         if (paramBoolean2) {
/*      */           try {
/*  603 */             verifyWithSeparateSigningKey(paramX509Certificate1, paramPublicKey, paramBoolean1, paramSet);
/*      */             
/*      */             return;
/*  606 */           } catch (CertPathValidatorException certPathValidatorException1) {
/*  607 */             if (certPathValidatorException != null)
/*      */             {
/*      */ 
/*      */ 
/*      */               
/*  612 */               throw certPathValidatorException;
/*      */             }
/*  614 */             throw certPathValidatorException1;
/*      */           } 
/*      */         }
/*  617 */         if (certPathValidatorException != null)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  622 */           throw certPathValidatorException;
/*      */         }
/*  624 */         throw new CertPathValidatorException("Could not determine revocation status", null, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkApprovedCRLs(X509Certificate paramX509Certificate, Set<X509CRL> paramSet) throws CertPathValidatorException {
/*  637 */     if (debug != null) {
/*  638 */       BigInteger bigInteger = paramX509Certificate.getSerialNumber();
/*  639 */       debug.println("RevocationChecker.checkApprovedCRLs() starting the final sweep...");
/*      */       
/*  641 */       debug.println("RevocationChecker.checkApprovedCRLs() cert SN: " + bigInteger
/*  642 */           .toString());
/*      */     } 
/*      */     
/*  645 */     CRLReason cRLReason = CRLReason.UNSPECIFIED;
/*  646 */     X509CRLEntryImpl x509CRLEntryImpl = null;
/*  647 */     for (X509CRL x509CRL : paramSet) {
/*  648 */       X509CRLEntry x509CRLEntry = x509CRL.getRevokedCertificate(paramX509Certificate);
/*  649 */       if (x509CRLEntry != null) {
/*      */         try {
/*  651 */           x509CRLEntryImpl = X509CRLEntryImpl.toImpl(x509CRLEntry);
/*  652 */         } catch (CRLException cRLException) {
/*  653 */           throw new CertPathValidatorException(cRLException);
/*      */         } 
/*  655 */         if (debug != null) {
/*  656 */           debug.println("RevocationChecker.checkApprovedCRLs() CRL entry: " + x509CRLEntryImpl
/*  657 */               .toString());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  665 */         Set<String> set = x509CRLEntryImpl.getCriticalExtensionOIDs();
/*  666 */         if (set != null && !set.isEmpty()) {
/*      */           
/*  668 */           set.remove(PKIXExtensions.ReasonCode_Id.toString());
/*  669 */           set.remove(PKIXExtensions.CertificateIssuer_Id.toString());
/*  670 */           if (!set.isEmpty()) {
/*  671 */             throw new CertPathValidatorException("Unrecognized critical extension(s) in revoked CRL entry");
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  677 */         cRLReason = x509CRLEntryImpl.getRevocationReason();
/*  678 */         if (cRLReason == null) {
/*  679 */           cRLReason = CRLReason.UNSPECIFIED;
/*      */         }
/*  681 */         Date date = x509CRLEntryImpl.getRevocationDate();
/*  682 */         if (date.before(this.params.date())) {
/*      */ 
/*      */           
/*  685 */           CertificateRevokedException certificateRevokedException = new CertificateRevokedException(date, cRLReason, x509CRL.getIssuerX500Principal(), x509CRLEntryImpl.getExtensions());
/*  686 */           throw new CertPathValidatorException(certificateRevokedException
/*  687 */               .getMessage(), certificateRevokedException, null, -1, CertPathValidatorException.BasicReason.REVOKED);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkOCSP(X509Certificate paramX509Certificate, Collection<String> paramCollection) throws CertPathValidatorException {
/*  697 */     X509CertImpl x509CertImpl = null;
/*      */     try {
/*  699 */       x509CertImpl = X509CertImpl.toImpl(paramX509Certificate);
/*  700 */     } catch (CertificateException certificateException) {
/*  701 */       throw new CertPathValidatorException(certificateException);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  708 */     OCSPResponse oCSPResponse = null;
/*  709 */     CertId certId = null;
/*      */     
/*      */     try {
/*  712 */       certId = new CertId(this.issuerInfo.getName(), this.issuerInfo.getPublicKey(), x509CertImpl.getSerialNumberObject());
/*      */ 
/*      */       
/*  715 */       byte[] arrayOfByte = this.ocspResponses.get(paramX509Certificate);
/*  716 */       if (arrayOfByte != null) {
/*  717 */         if (debug != null) {
/*  718 */           debug.println("Found cached OCSP response");
/*      */         }
/*  720 */         oCSPResponse = new OCSPResponse(arrayOfByte);
/*      */ 
/*      */         
/*  723 */         byte[] arrayOfByte1 = null;
/*  724 */         for (Extension extension : this.ocspExtensions) {
/*  725 */           if (extension.getId().equals("1.3.6.1.5.5.7.48.1.2")) {
/*  726 */             arrayOfByte1 = extension.getValue();
/*      */           }
/*      */         } 
/*  729 */         oCSPResponse.verify(Collections.singletonList(certId), this.issuerInfo, this.responderCert, this.params
/*  730 */             .date(), arrayOfByte1, this.params.variant());
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  735 */         URI uRI = (this.responderURI != null) ? this.responderURI : OCSP.getResponderURI(x509CertImpl);
/*  736 */         if (uRI == null) {
/*  737 */           throw new CertPathValidatorException("Certificate does not specify OCSP responder", null, null, -1);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  742 */         oCSPResponse = OCSP.check(Collections.singletonList(certId), uRI, this.issuerInfo, this.responderCert, (Date)null, this.ocspExtensions, this.params
/*      */             
/*  744 */             .variant());
/*      */       } 
/*  746 */     } catch (IOException iOException) {
/*  747 */       throw new CertPathValidatorException("Unable to determine revocation status due to network error", iOException, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  753 */     OCSPResponse.SingleResponse singleResponse = oCSPResponse.getSingleResponse(certId);
/*  754 */     OCSP.RevocationStatus.CertStatus certStatus = singleResponse.getCertStatus();
/*  755 */     if (certStatus == OCSP.RevocationStatus.CertStatus.REVOKED) {
/*  756 */       Date date = singleResponse.getRevocationTime();
/*  757 */       if (date.before(this.params.date()))
/*      */       {
/*      */ 
/*      */         
/*  761 */         CertificateRevokedException certificateRevokedException = new CertificateRevokedException(date, singleResponse.getRevocationReason(), oCSPResponse.getSignerCertificate().getSubjectX500Principal(), singleResponse.getSingleExtensions());
/*  762 */         throw new CertPathValidatorException(certificateRevokedException.getMessage(), certificateRevokedException, null, -1, CertPathValidatorException.BasicReason.REVOKED);
/*      */       }
/*      */     
/*  765 */     } else if (certStatus == OCSP.RevocationStatus.CertStatus.UNKNOWN) {
/*  766 */       throw new CertPathValidatorException("Certificate's revocation status is unknown", null, this.params
/*      */           
/*  768 */           .certPath(), -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String stripOutSeparators(String paramString) {
/*  778 */     char[] arrayOfChar = paramString.toCharArray();
/*  779 */     StringBuilder stringBuilder = new StringBuilder();
/*  780 */     for (byte b = 0; b < arrayOfChar.length; b++) {
/*  781 */       if ("0123456789ABCDEFabcdef".indexOf(arrayOfChar[b]) != -1) {
/*  782 */         stringBuilder.append(arrayOfChar[b]);
/*      */       }
/*      */     } 
/*  785 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean certCanSignCrl(X509Certificate paramX509Certificate) {
/*  799 */     boolean[] arrayOfBoolean = paramX509Certificate.getKeyUsage();
/*  800 */     if (arrayOfBoolean != null) {
/*  801 */       return arrayOfBoolean[6];
/*      */     }
/*  803 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  818 */   private static final boolean[] ALL_REASONS = new boolean[] { true, true, true, true, true, true, true, true, true };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Collection<X509CRL> verifyPossibleCRLs(Set<X509CRL> paramSet, X509Certificate paramX509Certificate, PublicKey paramPublicKey, boolean paramBoolean, boolean[] paramArrayOfboolean, Set<TrustAnchor> paramSet1) throws CertPathValidatorException {
/*      */     try {
/*  829 */       X509CertImpl x509CertImpl = X509CertImpl.toImpl(paramX509Certificate);
/*  830 */       if (debug != null) {
/*  831 */         debug.println("RevocationChecker.verifyPossibleCRLs: Checking CRLDPs for " + x509CertImpl
/*      */             
/*  833 */             .getSubjectX500Principal());
/*      */       }
/*      */       
/*  836 */       CRLDistributionPointsExtension cRLDistributionPointsExtension = x509CertImpl.getCRLDistributionPointsExtension();
/*  837 */       List<DistributionPoint> list = null;
/*  838 */       if (cRLDistributionPointsExtension == null) {
/*      */ 
/*      */ 
/*      */         
/*  842 */         X500Name x500Name = (X500Name)x509CertImpl.getIssuerDN();
/*      */         
/*  844 */         DistributionPoint distributionPoint = new DistributionPoint((new GeneralNames()).add(new GeneralName(x500Name)), null, null);
/*      */         
/*  846 */         list = Collections.singletonList(distributionPoint);
/*      */       } else {
/*  848 */         list = cRLDistributionPointsExtension.get("points");
/*      */       } 
/*  850 */       HashSet<X509CRL> hashSet = new HashSet();
/*  851 */       for (DistributionPoint distributionPoint : list) {
/*  852 */         for (X509CRL x509CRL : paramSet) {
/*  853 */           if (DistributionPointFetcher.verifyCRL(x509CertImpl, distributionPoint, x509CRL, paramArrayOfboolean, paramBoolean, paramPublicKey, null, this.params
/*      */               
/*  855 */               .sigProvider(), paramSet1, this.certStores, this.params
/*  856 */               .date(), this.params.variant()))
/*      */           {
/*  858 */             hashSet.add(x509CRL);
/*      */           }
/*      */         } 
/*  861 */         if (Arrays.equals(paramArrayOfboolean, ALL_REASONS))
/*      */           break; 
/*      */       } 
/*  864 */       return hashSet;
/*  865 */     } catch (CertificateException|CRLException|IOException certificateException) {
/*  866 */       if (debug != null) {
/*  867 */         debug.println("Exception while verifying CRL: " + certificateException.getMessage());
/*  868 */         certificateException.printStackTrace();
/*      */       } 
/*  870 */       return Collections.emptySet();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifyWithSeparateSigningKey(X509Certificate paramX509Certificate, PublicKey paramPublicKey, boolean paramBoolean, Set<X509Certificate> paramSet) throws CertPathValidatorException {
/*  902 */     String str = "revocation status";
/*  903 */     if (debug != null) {
/*  904 */       debug.println("RevocationChecker.verifyWithSeparateSigningKey() ---checking " + str + "...");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  912 */     if (paramSet != null && paramSet.contains(paramX509Certificate)) {
/*  913 */       if (debug != null) {
/*  914 */         debug.println("RevocationChecker.verifyWithSeparateSigningKey() circular dependency");
/*      */       }
/*      */ 
/*      */       
/*  918 */       throw new CertPathValidatorException("Could not determine revocation status", null, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  927 */     if (!paramBoolean) {
/*  928 */       buildToNewKey(paramX509Certificate, (PublicKey)null, paramSet);
/*      */     } else {
/*  930 */       buildToNewKey(paramX509Certificate, paramPublicKey, paramSet);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  948 */   private static final boolean[] CRL_SIGN_USAGE = new boolean[] { false, false, false, false, false, false, true };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildToNewKey(X509Certificate paramX509Certificate, PublicKey paramPublicKey, Set<X509Certificate> paramSet) throws CertPathValidatorException {
/*      */     PKIXBuilderParameters pKIXBuilderParameters;
/*  956 */     if (debug != null) {
/*  957 */       debug.println("RevocationChecker.buildToNewKey() starting work");
/*      */     }
/*      */     
/*  960 */     HashSet<PublicKey> hashSet = new HashSet();
/*  961 */     if (paramPublicKey != null) {
/*  962 */       hashSet.add(paramPublicKey);
/*      */     }
/*  964 */     RejectKeySelector rejectKeySelector = new RejectKeySelector(hashSet);
/*  965 */     rejectKeySelector.setSubject(paramX509Certificate.getIssuerX500Principal());
/*  966 */     rejectKeySelector.setKeyUsage(CRL_SIGN_USAGE);
/*      */ 
/*      */ 
/*      */     
/*  970 */     Set<TrustAnchor> set = (Set<TrustAnchor>)((this.anchor == null) ? this.params.trustAnchors() : Collections.<TrustAnchor>singleton(this.anchor));
/*      */ 
/*      */     
/*      */     try {
/*  974 */       pKIXBuilderParameters = new PKIXBuilderParameters(set, rejectKeySelector);
/*  975 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/*  976 */       throw new RuntimeException(invalidAlgorithmParameterException);
/*      */     } 
/*  978 */     pKIXBuilderParameters.setInitialPolicies(this.params.initialPolicies());
/*  979 */     pKIXBuilderParameters.setCertStores(this.certStores);
/*  980 */     pKIXBuilderParameters
/*  981 */       .setExplicitPolicyRequired(this.params.explicitPolicyRequired());
/*  982 */     pKIXBuilderParameters
/*  983 */       .setPolicyMappingInhibited(this.params.policyMappingInhibited());
/*  984 */     pKIXBuilderParameters.setAnyPolicyInhibited(this.params.anyPolicyInhibited());
/*      */ 
/*      */ 
/*      */     
/*  988 */     pKIXBuilderParameters.setDate(this.params.date());
/*      */     
/*  990 */     pKIXBuilderParameters.setCertPathCheckers(this.params
/*  991 */         .getPKIXParameters().getCertPathCheckers());
/*  992 */     pKIXBuilderParameters.setSigProvider(this.params.sigProvider());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  997 */     pKIXBuilderParameters.setRevocationEnabled(false);
/*      */ 
/*      */     
/* 1000 */     if (Builder.USE_AIA == true) {
/* 1001 */       X509CertImpl x509CertImpl = null;
/*      */       try {
/* 1003 */         x509CertImpl = X509CertImpl.toImpl(paramX509Certificate);
/* 1004 */       } catch (CertificateException certificateException) {
/*      */         
/* 1006 */         if (debug != null) {
/* 1007 */           debug.println("RevocationChecker.buildToNewKey: error decoding cert: " + certificateException);
/*      */         }
/*      */       } 
/*      */       
/* 1011 */       AuthorityInfoAccessExtension authorityInfoAccessExtension = null;
/* 1012 */       if (x509CertImpl != null) {
/* 1013 */         authorityInfoAccessExtension = x509CertImpl.getAuthorityInfoAccessExtension();
/*      */       }
/* 1015 */       if (authorityInfoAccessExtension != null) {
/* 1016 */         List<AccessDescription> list = authorityInfoAccessExtension.getAccessDescriptions();
/* 1017 */         if (list != null) {
/* 1018 */           for (AccessDescription accessDescription : list) {
/* 1019 */             CertStore certStore = URICertStore.getInstance(accessDescription);
/* 1020 */             if (certStore != null) {
/* 1021 */               if (debug != null) {
/* 1022 */                 debug.println("adding AIAext CertStore");
/*      */               }
/* 1024 */               pKIXBuilderParameters.addCertStore(certStore);
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1031 */     CertPathBuilder certPathBuilder = null;
/*      */     try {
/* 1033 */       certPathBuilder = CertPathBuilder.getInstance("PKIX");
/* 1034 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 1035 */       throw new CertPathValidatorException(noSuchAlgorithmException);
/*      */     } 
/*      */     try {
/*      */       while (true)
/* 1039 */       { if (debug != null) {
/* 1040 */           debug.println("RevocationChecker.buildToNewKey() about to try build ...");
/*      */         }
/*      */ 
/*      */         
/* 1044 */         PKIXCertPathBuilderResult pKIXCertPathBuilderResult = (PKIXCertPathBuilderResult)certPathBuilder.build(pKIXBuilderParameters);
/*      */         
/* 1046 */         if (debug != null) {
/* 1047 */           debug.println("RevocationChecker.buildToNewKey() about to check revocation ...");
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1052 */         if (paramSet == null) {
/* 1053 */           paramSet = new HashSet<>();
/*      */         }
/* 1055 */         paramSet.add(paramX509Certificate);
/* 1056 */         TrustAnchor trustAnchor = pKIXCertPathBuilderResult.getTrustAnchor();
/* 1057 */         PublicKey publicKey1 = trustAnchor.getCAPublicKey();
/* 1058 */         if (publicKey1 == null) {
/* 1059 */           publicKey1 = trustAnchor.getTrustedCert().getPublicKey();
/*      */         }
/* 1061 */         boolean bool = true;
/*      */         
/* 1063 */         List<? extends Certificate> list = pKIXCertPathBuilderResult.getCertPath().getCertificates();
/*      */         try {
/* 1065 */           for (int i = list.size() - 1; i >= 0; i--) {
/* 1066 */             X509Certificate x509Certificate1 = (X509Certificate)list.get(i);
/*      */             
/* 1068 */             if (debug != null) {
/* 1069 */               debug.println("RevocationChecker.buildToNewKey() index " + i + " checking " + x509Certificate1);
/*      */             }
/*      */ 
/*      */             
/* 1073 */             checkCRLs(x509Certificate1, publicKey1, (X509Certificate)null, bool, true, paramSet, set);
/*      */             
/* 1075 */             bool = certCanSignCrl(x509Certificate1);
/* 1076 */             publicKey1 = x509Certificate1.getPublicKey();
/*      */           } 
/* 1078 */         } catch (CertPathValidatorException certPathValidatorException) {
/*      */           
/* 1080 */           hashSet.add(pKIXCertPathBuilderResult.getPublicKey());
/*      */           
/*      */           continue;
/*      */         } 
/* 1084 */         if (debug != null) {
/* 1085 */           debug.println("RevocationChecker.buildToNewKey() got key " + pKIXCertPathBuilderResult
/* 1086 */               .getPublicKey());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1092 */         PublicKey publicKey2 = pKIXCertPathBuilderResult.getPublicKey();
/*      */         
/* 1094 */         X509Certificate x509Certificate = list.isEmpty() ? null : (X509Certificate)list.get(0);
/*      */         
/* 1096 */         try { checkCRLs(paramX509Certificate, publicKey2, x509Certificate, true, false, (Set<X509Certificate>)null, this.params
/* 1097 */               .trustAnchors());
/*      */           
/*      */           return; }
/* 1100 */         catch (CertPathValidatorException certPathValidatorException)
/*      */         
/* 1102 */         { if (certPathValidatorException.getReason() == CertPathValidatorException.BasicReason.REVOKED) {
/* 1103 */             throw certPathValidatorException;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1108 */           hashSet.add(publicKey2); }  } 
/* 1109 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/* 1110 */       throw new CertPathValidatorException(invalidAlgorithmParameterException);
/* 1111 */     } catch (CertPathBuilderException certPathBuilderException) {
/* 1112 */       throw new CertPathValidatorException("Could not determine revocation status", null, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RevocationChecker clone() {
/* 1121 */     RevocationChecker revocationChecker = (RevocationChecker)super.clone();
/*      */ 
/*      */     
/* 1124 */     revocationChecker.softFailExceptions = new LinkedList<>(this.softFailExceptions);
/* 1125 */     return revocationChecker;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class RejectKeySelector
/*      */     extends X509CertSelector
/*      */   {
/*      */     private final Set<PublicKey> badKeySet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     RejectKeySelector(Set<PublicKey> param1Set) {
/* 1146 */       this.badKeySet = param1Set;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean match(Certificate param1Certificate) {
/* 1158 */       if (!super.match(param1Certificate)) {
/* 1159 */         return false;
/*      */       }
/* 1161 */       if (this.badKeySet.contains(param1Certificate.getPublicKey())) {
/* 1162 */         if (RevocationChecker.debug != null)
/* 1163 */           RevocationChecker.debug.println("RejectKeySelector.match: bad key"); 
/* 1164 */         return false;
/*      */       } 
/*      */       
/* 1167 */       if (RevocationChecker.debug != null)
/* 1168 */         RevocationChecker.debug.println("RejectKeySelector.match: returning true"); 
/* 1169 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1180 */       StringBuilder stringBuilder = new StringBuilder();
/* 1181 */       stringBuilder.append("RejectKeySelector: [\n");
/* 1182 */       stringBuilder.append(super.toString());
/* 1183 */       stringBuilder.append(this.badKeySet);
/* 1184 */       stringBuilder.append("]");
/* 1185 */       return stringBuilder.toString();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/RevocationChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */