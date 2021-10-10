/*     */ package java.security.cert;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PKIXParameters
/*     */   implements CertPathParameters
/*     */ {
/*     */   private Set<TrustAnchor> unmodTrustAnchors;
/*     */   private Date date;
/*     */   private List<PKIXCertPathChecker> certPathCheckers;
/*     */   private String sigProvider;
/*     */   private boolean revocationEnabled = true;
/*     */   private Set<String> unmodInitialPolicies;
/*     */   private boolean explicitPolicyRequired = false;
/*     */   private boolean policyMappingInhibited = false;
/*     */   private boolean anyPolicyInhibited = false;
/*     */   private boolean policyQualifiersRejected = true;
/*     */   private List<CertStore> certStores;
/*     */   private CertSelector certSelector;
/*     */   
/*     */   public PKIXParameters(Set<TrustAnchor> paramSet) throws InvalidAlgorithmParameterException {
/* 120 */     setTrustAnchors(paramSet);
/*     */     
/* 122 */     this.unmodInitialPolicies = Collections.emptySet();
/* 123 */     this.certPathCheckers = new ArrayList<>();
/* 124 */     this.certStores = new ArrayList<>();
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
/*     */   public PKIXParameters(KeyStore paramKeyStore) throws KeyStoreException, InvalidAlgorithmParameterException {
/* 144 */     if (paramKeyStore == null) {
/* 145 */       throw new NullPointerException("the keystore parameter must be non-null");
/*     */     }
/* 147 */     HashSet<TrustAnchor> hashSet = new HashSet();
/* 148 */     Enumeration<String> enumeration = paramKeyStore.aliases();
/* 149 */     while (enumeration.hasMoreElements()) {
/* 150 */       String str = enumeration.nextElement();
/* 151 */       if (paramKeyStore.isCertificateEntry(str)) {
/* 152 */         Certificate certificate = paramKeyStore.getCertificate(str);
/* 153 */         if (certificate instanceof X509Certificate)
/* 154 */           hashSet.add(new TrustAnchor((X509Certificate)certificate, null)); 
/*     */       } 
/*     */     } 
/* 157 */     setTrustAnchors(hashSet);
/* 158 */     this.unmodInitialPolicies = Collections.emptySet();
/* 159 */     this.certPathCheckers = new ArrayList<>();
/* 160 */     this.certStores = new ArrayList<>();
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
/*     */   public Set<TrustAnchor> getTrustAnchors() {
/* 173 */     return this.unmodTrustAnchors;
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
/*     */   public void setTrustAnchors(Set<TrustAnchor> paramSet) throws InvalidAlgorithmParameterException {
/* 195 */     if (paramSet == null) {
/* 196 */       throw new NullPointerException("the trustAnchors parameters must be non-null");
/*     */     }
/*     */     
/* 199 */     if (paramSet.isEmpty()) {
/* 200 */       throw new InvalidAlgorithmParameterException("the trustAnchors parameter must be non-empty");
/*     */     }
/*     */     
/* 203 */     for (Iterator<TrustAnchor> iterator = paramSet.iterator(); iterator.hasNext();) {
/* 204 */       if (!(iterator.next() instanceof TrustAnchor)) {
/* 205 */         throw new ClassCastException("all elements of set must be of type java.security.cert.TrustAnchor");
/*     */       }
/*     */     } 
/*     */     
/* 209 */     this
/* 210 */       .unmodTrustAnchors = Collections.unmodifiableSet(new HashSet<>(paramSet));
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
/*     */   public Set<String> getInitialPolicies() {
/* 228 */     return this.unmodInitialPolicies;
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
/*     */   public void setInitialPolicies(Set<String> paramSet) {
/* 251 */     if (paramSet != null) {
/* 252 */       Iterator<String> iterator = paramSet.iterator();
/* 253 */       while (iterator.hasNext()) {
/* 254 */         if (!(iterator.next() instanceof String)) {
/* 255 */           throw new ClassCastException("all elements of set must be of type java.lang.String");
/*     */         }
/*     */       } 
/* 258 */       this
/* 259 */         .unmodInitialPolicies = Collections.unmodifiableSet(new HashSet<>(paramSet));
/*     */     } else {
/* 261 */       this.unmodInitialPolicies = Collections.emptySet();
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
/*     */   public void setCertStores(List<CertStore> paramList) {
/* 282 */     if (paramList == null) {
/* 283 */       this.certStores = new ArrayList<>();
/*     */     } else {
/* 285 */       for (Iterator<CertStore> iterator = paramList.iterator(); iterator.hasNext();) {
/* 286 */         if (!(iterator.next() instanceof CertStore)) {
/* 287 */           throw new ClassCastException("all elements of list must be of type java.security.cert.CertStore");
/*     */         }
/*     */       } 
/*     */       
/* 291 */       this.certStores = new ArrayList<>(paramList);
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
/*     */   public void addCertStore(CertStore paramCertStore) {
/* 303 */     if (paramCertStore != null) {
/* 304 */       this.certStores.add(paramCertStore);
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
/*     */   public List<CertStore> getCertStores() {
/* 318 */     return 
/* 319 */       Collections.unmodifiableList(new ArrayList<>(this.certStores));
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
/*     */   public void setRevocationEnabled(boolean paramBoolean) {
/* 341 */     this.revocationEnabled = paramBoolean;
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
/*     */   public boolean isRevocationEnabled() {
/* 355 */     return this.revocationEnabled;
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
/*     */   public void setExplicitPolicyRequired(boolean paramBoolean) {
/* 367 */     this.explicitPolicyRequired = paramBoolean;
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
/*     */   public boolean isExplicitPolicyRequired() {
/* 379 */     return this.explicitPolicyRequired;
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
/*     */   public void setPolicyMappingInhibited(boolean paramBoolean) {
/* 391 */     this.policyMappingInhibited = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPolicyMappingInhibited() {
/* 402 */     return this.policyMappingInhibited;
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
/*     */   public void setAnyPolicyInhibited(boolean paramBoolean) {
/* 415 */     this.anyPolicyInhibited = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnyPolicyInhibited() {
/* 426 */     return this.anyPolicyInhibited;
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
/*     */   public void setPolicyQualifiersRejected(boolean paramBoolean) {
/* 453 */     this.policyQualifiersRejected = paramBoolean;
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
/*     */   public boolean getPolicyQualifiersRejected() {
/* 471 */     return this.policyQualifiersRejected;
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
/*     */   public Date getDate() {
/* 485 */     if (this.date == null) {
/* 486 */       return null;
/*     */     }
/* 488 */     return (Date)this.date.clone();
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
/*     */   public void setDate(Date paramDate) {
/* 503 */     if (paramDate != null) {
/* 504 */       this.date = (Date)paramDate.clone();
/*     */     } else {
/* 506 */       paramDate = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCertPathCheckers(List<PKIXCertPathChecker> paramList) {
/* 546 */     if (paramList != null) {
/* 547 */       ArrayList<PKIXCertPathChecker> arrayList = new ArrayList();
/*     */       
/* 549 */       for (PKIXCertPathChecker pKIXCertPathChecker : paramList) {
/* 550 */         arrayList.add((PKIXCertPathChecker)pKIXCertPathChecker.clone());
/*     */       }
/* 552 */       this.certPathCheckers = arrayList;
/*     */     } else {
/* 554 */       this.certPathCheckers = new ArrayList<>();
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
/*     */   public List<PKIXCertPathChecker> getCertPathCheckers() {
/* 570 */     ArrayList<PKIXCertPathChecker> arrayList = new ArrayList();
/* 571 */     for (PKIXCertPathChecker pKIXCertPathChecker : this.certPathCheckers) {
/* 572 */       arrayList.add((PKIXCertPathChecker)pKIXCertPathChecker.clone());
/*     */     }
/* 574 */     return Collections.unmodifiableList(arrayList);
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
/*     */   public void addCertPathChecker(PKIXCertPathChecker paramPKIXCertPathChecker) {
/* 589 */     if (paramPKIXCertPathChecker != null) {
/* 590 */       this.certPathCheckers.add((PKIXCertPathChecker)paramPKIXCertPathChecker.clone());
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
/*     */   public String getSigProvider() {
/* 602 */     return this.sigProvider;
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
/*     */   public void setSigProvider(String paramString) {
/* 615 */     this.sigProvider = paramString;
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
/*     */   public CertSelector getTargetCertConstraints() {
/* 631 */     if (this.certSelector != null) {
/* 632 */       return (CertSelector)this.certSelector.clone();
/*     */     }
/* 634 */     return null;
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
/*     */   public void setTargetCertConstraints(CertSelector paramCertSelector) {
/* 652 */     if (paramCertSelector != null) {
/* 653 */       this.certSelector = (CertSelector)paramCertSelector.clone();
/*     */     } else {
/* 655 */       this.certSelector = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 666 */       PKIXParameters pKIXParameters = (PKIXParameters)super.clone();
/*     */ 
/*     */       
/* 669 */       if (this.certStores != null) {
/* 670 */         pKIXParameters.certStores = new ArrayList<>(this.certStores);
/*     */       }
/* 672 */       if (this.certPathCheckers != null) {
/* 673 */         pKIXParameters
/* 674 */           .certPathCheckers = new ArrayList<>(this.certPathCheckers.size());
/* 675 */         for (PKIXCertPathChecker pKIXCertPathChecker : this.certPathCheckers) {
/* 676 */           pKIXParameters.certPathCheckers.add((PKIXCertPathChecker)pKIXCertPathChecker
/* 677 */               .clone());
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 683 */       return pKIXParameters;
/* 684 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 686 */       throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 696 */     StringBuffer stringBuffer = new StringBuffer();
/* 697 */     stringBuffer.append("[\n");
/*     */ 
/*     */     
/* 700 */     if (this.unmodTrustAnchors != null) {
/* 701 */       stringBuffer.append("  Trust Anchors: " + this.unmodTrustAnchors.toString() + "\n");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 706 */     if (this.unmodInitialPolicies != null) {
/* 707 */       if (this.unmodInitialPolicies.isEmpty()) {
/* 708 */         stringBuffer.append("  Initial Policy OIDs: any\n");
/*     */       } else {
/* 710 */         stringBuffer.append("  Initial Policy OIDs: [" + this.unmodInitialPolicies
/* 711 */             .toString() + "]\n");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 716 */     stringBuffer.append("  Validity Date: " + String.valueOf(this.date) + "\n");
/* 717 */     stringBuffer.append("  Signature Provider: " + String.valueOf(this.sigProvider) + "\n");
/* 718 */     stringBuffer.append("  Default Revocation Enabled: " + this.revocationEnabled + "\n");
/* 719 */     stringBuffer.append("  Explicit Policy Required: " + this.explicitPolicyRequired + "\n");
/* 720 */     stringBuffer.append("  Policy Mapping Inhibited: " + this.policyMappingInhibited + "\n");
/* 721 */     stringBuffer.append("  Any Policy Inhibited: " + this.anyPolicyInhibited + "\n");
/* 722 */     stringBuffer.append("  Policy Qualifiers Rejected: " + this.policyQualifiersRejected + "\n");
/*     */ 
/*     */     
/* 725 */     stringBuffer.append("  Target Cert Constraints: " + String.valueOf(this.certSelector) + "\n");
/*     */ 
/*     */     
/* 728 */     if (this.certPathCheckers != null)
/* 729 */       stringBuffer.append("  Certification Path Checkers: [" + this.certPathCheckers
/* 730 */           .toString() + "]\n"); 
/* 731 */     if (this.certStores != null)
/* 732 */       stringBuffer.append("  CertStores: [" + this.certStores.toString() + "]\n"); 
/* 733 */     stringBuffer.append("]");
/* 734 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/PKIXParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */