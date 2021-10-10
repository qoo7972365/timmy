/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Timestamp;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.CertPathParameters;
/*     */ import java.security.cert.CertSelector;
/*     */ import java.security.cert.CertStore;
/*     */ import java.security.cert.CertStoreException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.PKIXBuilderParameters;
/*     */ import java.security.cert.PKIXCertPathChecker;
/*     */ import java.security.cert.PKIXParameters;
/*     */ import java.security.cert.TrustAnchor;
/*     */ import java.security.cert.X509CertSelector;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.security.interfaces.DSAPublicKey;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PKIX
/*     */ {
/*  43 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isDSAPublicKeyWithoutParams(PublicKey paramPublicKey) {
/*  48 */     return (paramPublicKey instanceof DSAPublicKey && ((DSAPublicKey)paramPublicKey)
/*  49 */       .getParams() == null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static ValidatorParams checkParams(CertPath paramCertPath, CertPathParameters paramCertPathParameters) throws InvalidAlgorithmParameterException {
/*  55 */     if (!(paramCertPathParameters instanceof PKIXParameters)) {
/*  56 */       throw new InvalidAlgorithmParameterException("inappropriate params, must be an instance of PKIXParameters");
/*     */     }
/*     */     
/*  59 */     return new ValidatorParams(paramCertPath, (PKIXParameters)paramCertPathParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static BuilderParams checkBuilderParams(CertPathParameters paramCertPathParameters) throws InvalidAlgorithmParameterException {
/*  65 */     if (!(paramCertPathParameters instanceof PKIXBuilderParameters)) {
/*  66 */       throw new InvalidAlgorithmParameterException("inappropriate params, must be an instance of PKIXBuilderParameters");
/*     */     }
/*     */     
/*  69 */     return new BuilderParams((PKIXBuilderParameters)paramCertPathParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   static class ValidatorParams
/*     */   {
/*     */     private final PKIXParameters params;
/*     */     
/*     */     private CertPath certPath;
/*     */     
/*     */     private List<PKIXCertPathChecker> checkers;
/*     */     
/*     */     private List<CertStore> stores;
/*     */     
/*     */     private boolean gotDate;
/*     */     
/*     */     private Date date;
/*     */     private Set<String> policies;
/*     */     private boolean gotConstraints;
/*     */     private CertSelector constraints;
/*     */     private Set<TrustAnchor> anchors;
/*     */     private List<X509Certificate> certs;
/*     */     private Timestamp timestamp;
/*     */     private String variant;
/*     */     
/*     */     ValidatorParams(CertPath param1CertPath, PKIXParameters param1PKIXParameters) throws InvalidAlgorithmParameterException {
/*  95 */       this(param1PKIXParameters);
/*  96 */       if (!param1CertPath.getType().equals("X.509") && !param1CertPath.getType().equals("X509")) {
/*  97 */         throw new InvalidAlgorithmParameterException("inappropriate CertPath type specified, must be X.509 or X509");
/*     */       }
/*     */       
/* 100 */       this.certPath = param1CertPath;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     ValidatorParams(PKIXParameters param1PKIXParameters) throws InvalidAlgorithmParameterException {
/* 106 */       if (param1PKIXParameters instanceof PKIXExtendedParameters) {
/* 107 */         this.timestamp = ((PKIXExtendedParameters)param1PKIXParameters).getTimestamp();
/* 108 */         this.variant = ((PKIXExtendedParameters)param1PKIXParameters).getVariant();
/*     */       } 
/*     */       
/* 111 */       this.anchors = param1PKIXParameters.getTrustAnchors();
/*     */ 
/*     */       
/* 114 */       for (TrustAnchor trustAnchor : this.anchors) {
/* 115 */         if (trustAnchor.getNameConstraints() != null) {
/* 116 */           throw new InvalidAlgorithmParameterException("name constraints in trust anchor not supported");
/*     */         }
/*     */       } 
/*     */       
/* 120 */       this.params = param1PKIXParameters;
/*     */     }
/*     */     
/*     */     CertPath certPath() {
/* 124 */       return this.certPath;
/*     */     }
/*     */     
/*     */     void setCertPath(CertPath param1CertPath) {
/* 128 */       this.certPath = param1CertPath;
/*     */     }
/*     */     List<X509Certificate> certificates() {
/* 131 */       if (this.certs == null) {
/* 132 */         if (this.certPath == null) {
/* 133 */           this.certs = Collections.emptyList();
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 139 */           ArrayList<Certificate> arrayList = new ArrayList<>(this.certPath.getCertificates());
/* 140 */           Collections.reverse(arrayList);
/* 141 */           this.certs = (List)arrayList;
/*     */         } 
/*     */       }
/* 144 */       return this.certs;
/*     */     }
/*     */     List<PKIXCertPathChecker> certPathCheckers() {
/* 147 */       if (this.checkers == null)
/* 148 */         this.checkers = this.params.getCertPathCheckers(); 
/* 149 */       return this.checkers;
/*     */     }
/*     */     List<CertStore> certStores() {
/* 152 */       if (this.stores == null)
/* 153 */         this.stores = this.params.getCertStores(); 
/* 154 */       return this.stores;
/*     */     }
/*     */     Date date() {
/* 157 */       if (!this.gotDate) {
/* 158 */         this.date = this.params.getDate();
/* 159 */         if (this.date == null)
/* 160 */           this.date = new Date(); 
/* 161 */         this.gotDate = true;
/*     */       } 
/* 163 */       return this.date;
/*     */     }
/*     */     Set<String> initialPolicies() {
/* 166 */       if (this.policies == null)
/* 167 */         this.policies = this.params.getInitialPolicies(); 
/* 168 */       return this.policies;
/*     */     }
/*     */     CertSelector targetCertConstraints() {
/* 171 */       if (!this.gotConstraints) {
/* 172 */         this.constraints = this.params.getTargetCertConstraints();
/* 173 */         this.gotConstraints = true;
/*     */       } 
/* 175 */       return this.constraints;
/*     */     }
/*     */     Set<TrustAnchor> trustAnchors() {
/* 178 */       return this.anchors;
/*     */     }
/*     */     boolean revocationEnabled() {
/* 181 */       return this.params.isRevocationEnabled();
/*     */     }
/*     */     boolean policyMappingInhibited() {
/* 184 */       return this.params.isPolicyMappingInhibited();
/*     */     }
/*     */     boolean explicitPolicyRequired() {
/* 187 */       return this.params.isExplicitPolicyRequired();
/*     */     }
/*     */     boolean policyQualifiersRejected() {
/* 190 */       return this.params.getPolicyQualifiersRejected();
/*     */     }
/* 192 */     String sigProvider() { return this.params.getSigProvider(); } boolean anyPolicyInhibited() {
/* 193 */       return this.params.isAnyPolicyInhibited();
/*     */     }
/*     */ 
/*     */     
/*     */     PKIXParameters getPKIXParameters() {
/* 198 */       return this.params;
/*     */     }
/*     */     
/*     */     Timestamp timestamp() {
/* 202 */       return this.timestamp;
/*     */     }
/*     */     
/*     */     String variant() {
/* 206 */       return this.variant;
/*     */     }
/*     */   }
/*     */   
/*     */   static class BuilderParams
/*     */     extends ValidatorParams
/*     */   {
/*     */     private PKIXBuilderParameters params;
/*     */     private List<CertStore> stores;
/*     */     private X500Principal targetSubject;
/*     */     
/*     */     BuilderParams(PKIXBuilderParameters param1PKIXBuilderParameters) throws InvalidAlgorithmParameterException {
/* 218 */       super(param1PKIXBuilderParameters);
/* 219 */       checkParams(param1PKIXBuilderParameters);
/*     */     }
/*     */ 
/*     */     
/*     */     private void checkParams(PKIXBuilderParameters param1PKIXBuilderParameters) throws InvalidAlgorithmParameterException {
/* 224 */       CertSelector certSelector = targetCertConstraints();
/* 225 */       if (!(certSelector instanceof X509CertSelector)) {
/* 226 */         throw new InvalidAlgorithmParameterException("the targetCertConstraints parameter must be an X509CertSelector");
/*     */       }
/*     */ 
/*     */       
/* 230 */       this.params = param1PKIXBuilderParameters;
/* 231 */       this.targetSubject = getTargetSubject(
/* 232 */           certStores(), (X509CertSelector)targetCertConstraints());
/*     */     }
/*     */     List<CertStore> certStores() {
/* 235 */       if (this.stores == null) {
/*     */         
/* 237 */         this.stores = new ArrayList<>(this.params.getCertStores());
/* 238 */         Collections.sort(this.stores, new PKIX.CertStoreComparator());
/*     */       } 
/* 240 */       return this.stores;
/*     */     }
/* 242 */     int maxPathLength() { return this.params.getMaxPathLength(); }
/* 243 */     PKIXBuilderParameters params() { return this.params; } X500Principal targetSubject() {
/* 244 */       return this.targetSubject;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static X500Principal getTargetSubject(List<CertStore> param1List, X509CertSelector param1X509CertSelector) throws InvalidAlgorithmParameterException {
/* 254 */       X500Principal x500Principal = param1X509CertSelector.getSubject();
/* 255 */       if (x500Principal != null) {
/* 256 */         return x500Principal;
/*     */       }
/* 258 */       X509Certificate x509Certificate = param1X509CertSelector.getCertificate();
/* 259 */       if (x509Certificate != null) {
/* 260 */         x500Principal = x509Certificate.getSubjectX500Principal();
/*     */       }
/* 262 */       if (x500Principal != null) {
/* 263 */         return x500Principal;
/*     */       }
/* 265 */       for (CertStore certStore : param1List) {
/*     */ 
/*     */         
/*     */         try {
/* 269 */           Collection<? extends Certificate> collection = certStore.getCertificates(param1X509CertSelector);
/* 270 */           if (!collection.isEmpty()) {
/*     */             
/* 272 */             X509Certificate x509Certificate1 = collection.iterator().next();
/* 273 */             return x509Certificate1.getSubjectX500Principal();
/*     */           } 
/* 275 */         } catch (CertStoreException certStoreException) {
/*     */           
/* 277 */           if (PKIX.debug != null) {
/* 278 */             PKIX.debug.println("BuilderParams.getTargetSubjectDN: non-fatal exception retrieving certs: " + certStoreException);
/*     */             
/* 280 */             certStoreException.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       } 
/* 284 */       throw new InvalidAlgorithmParameterException("Could not determine unique target subject");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class CertStoreTypeException
/*     */     extends CertStoreException
/*     */   {
/*     */     private static final long serialVersionUID = 7463352639238322556L;
/*     */     
/*     */     private final String type;
/*     */ 
/*     */     
/*     */     CertStoreTypeException(String param1String, CertStoreException param1CertStoreException) {
/* 299 */       super(param1CertStoreException.getMessage(), param1CertStoreException.getCause());
/* 300 */       this.type = param1String;
/*     */     }
/*     */     String getType() {
/* 303 */       return this.type;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class CertStoreComparator
/*     */     implements Comparator<CertStore>
/*     */   {
/*     */     private CertStoreComparator() {}
/*     */     
/*     */     public int compare(CertStore param1CertStore1, CertStore param1CertStore2) {
/* 314 */       if (param1CertStore1.getType().equals("Collection") || param1CertStore1
/* 315 */         .getCertStoreParameters() instanceof java.security.cert.CollectionCertStoreParameters)
/*     */       {
/* 317 */         return -1;
/*     */       }
/* 319 */       return 1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/PKIX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */