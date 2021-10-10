/*     */ package sun.security.provider.certpath;
/*     */ 
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.Timestamp;
/*     */ import java.security.cert.CertSelector;
/*     */ import java.security.cert.CertStore;
/*     */ import java.security.cert.PKIXBuilderParameters;
/*     */ import java.security.cert.PKIXCertPathChecker;
/*     */ import java.security.cert.TrustAnchor;
/*     */ import java.util.Date;
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
/*     */ public class PKIXExtendedParameters
/*     */   extends PKIXBuilderParameters
/*     */ {
/*     */   private final PKIXBuilderParameters p;
/*     */   private Timestamp jarTimestamp;
/*     */   private final String variant;
/*     */   
/*     */   public PKIXExtendedParameters(PKIXBuilderParameters paramPKIXBuilderParameters, Timestamp paramTimestamp, String paramString) throws InvalidAlgorithmParameterException {
/*  55 */     super(paramPKIXBuilderParameters.getTrustAnchors(), (CertSelector)null);
/*  56 */     this.p = paramPKIXBuilderParameters;
/*  57 */     this.jarTimestamp = paramTimestamp;
/*  58 */     this.variant = paramString;
/*     */   }
/*     */   
/*     */   public Timestamp getTimestamp() {
/*  62 */     return this.jarTimestamp;
/*     */   }
/*     */   public void setTimestamp(Timestamp paramTimestamp) {
/*  65 */     this.jarTimestamp = paramTimestamp;
/*     */   }
/*     */   
/*     */   public String getVariant() {
/*  69 */     return this.variant;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDate(Date paramDate) {
/*  74 */     this.p.setDate(paramDate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCertPathChecker(PKIXCertPathChecker paramPKIXCertPathChecker) {
/*  79 */     this.p.addCertPathChecker(paramPKIXCertPathChecker);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxPathLength(int paramInt) {
/*  84 */     this.p.setMaxPathLength(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxPathLength() {
/*  89 */     return this.p.getMaxPathLength();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  94 */     return this.p.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<TrustAnchor> getTrustAnchors() {
/*  99 */     return this.p.getTrustAnchors();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTrustAnchors(Set<TrustAnchor> paramSet) throws InvalidAlgorithmParameterException {
/* 106 */     if (this.p == null) {
/*     */       return;
/*     */     }
/* 109 */     this.p.setTrustAnchors(paramSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getInitialPolicies() {
/* 114 */     return this.p.getInitialPolicies();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInitialPolicies(Set<String> paramSet) {
/* 119 */     this.p.setInitialPolicies(paramSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCertStores(List<CertStore> paramList) {
/* 124 */     this.p.setCertStores(paramList);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCertStore(CertStore paramCertStore) {
/* 129 */     this.p.addCertStore(paramCertStore);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<CertStore> getCertStores() {
/* 134 */     return this.p.getCertStores();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRevocationEnabled(boolean paramBoolean) {
/* 139 */     this.p.setRevocationEnabled(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRevocationEnabled() {
/* 144 */     return this.p.isRevocationEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExplicitPolicyRequired(boolean paramBoolean) {
/* 149 */     this.p.setExplicitPolicyRequired(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExplicitPolicyRequired() {
/* 154 */     return this.p.isExplicitPolicyRequired();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPolicyMappingInhibited(boolean paramBoolean) {
/* 159 */     this.p.setPolicyMappingInhibited(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPolicyMappingInhibited() {
/* 164 */     return this.p.isPolicyMappingInhibited();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAnyPolicyInhibited(boolean paramBoolean) {
/* 169 */     this.p.setAnyPolicyInhibited(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAnyPolicyInhibited() {
/* 174 */     return this.p.isAnyPolicyInhibited();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPolicyQualifiersRejected(boolean paramBoolean) {
/* 179 */     this.p.setPolicyQualifiersRejected(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getPolicyQualifiersRejected() {
/* 184 */     return this.p.getPolicyQualifiersRejected();
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getDate() {
/* 189 */     return this.p.getDate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCertPathCheckers(List<PKIXCertPathChecker> paramList) {
/* 194 */     this.p.setCertPathCheckers(paramList);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PKIXCertPathChecker> getCertPathCheckers() {
/* 199 */     return this.p.getCertPathCheckers();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSigProvider() {
/* 204 */     return this.p.getSigProvider();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSigProvider(String paramString) {
/* 209 */     this.p.setSigProvider(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public CertSelector getTargetCertConstraints() {
/* 214 */     return this.p.getTargetCertConstraints();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetCertConstraints(CertSelector paramCertSelector) {
/* 220 */     if (this.p == null) {
/*     */       return;
/*     */     }
/* 223 */     this.p.setTargetCertConstraints(paramCertSelector);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/PKIXExtendedParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */