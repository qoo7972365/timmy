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
/*     */ public class PKIXTimestampParameters
/*     */   extends PKIXBuilderParameters
/*     */ {
/*     */   private final PKIXBuilderParameters p;
/*     */   private Timestamp jarTimestamp;
/*     */   
/*     */   public PKIXTimestampParameters(PKIXBuilderParameters paramPKIXBuilderParameters, Timestamp paramTimestamp) throws InvalidAlgorithmParameterException {
/*  52 */     super(paramPKIXBuilderParameters.getTrustAnchors(), (CertSelector)null);
/*  53 */     this.p = paramPKIXBuilderParameters;
/*  54 */     this.jarTimestamp = paramTimestamp;
/*     */   }
/*     */   
/*     */   public Timestamp getTimestamp() {
/*  58 */     return this.jarTimestamp;
/*     */   }
/*     */   public void setTimestamp(Timestamp paramTimestamp) {
/*  61 */     this.jarTimestamp = paramTimestamp;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDate(Date paramDate) {
/*  66 */     this.p.setDate(paramDate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCertPathChecker(PKIXCertPathChecker paramPKIXCertPathChecker) {
/*  71 */     this.p.addCertPathChecker(paramPKIXCertPathChecker);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxPathLength(int paramInt) {
/*  76 */     this.p.setMaxPathLength(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxPathLength() {
/*  81 */     return this.p.getMaxPathLength();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  86 */     return this.p.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<TrustAnchor> getTrustAnchors() {
/*  91 */     return this.p.getTrustAnchors();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTrustAnchors(Set<TrustAnchor> paramSet) throws InvalidAlgorithmParameterException {
/*  98 */     if (this.p == null) {
/*     */       return;
/*     */     }
/* 101 */     this.p.setTrustAnchors(paramSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getInitialPolicies() {
/* 106 */     return this.p.getInitialPolicies();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInitialPolicies(Set<String> paramSet) {
/* 111 */     this.p.setInitialPolicies(paramSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCertStores(List<CertStore> paramList) {
/* 116 */     this.p.setCertStores(paramList);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCertStore(CertStore paramCertStore) {
/* 121 */     this.p.addCertStore(paramCertStore);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<CertStore> getCertStores() {
/* 126 */     return this.p.getCertStores();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRevocationEnabled(boolean paramBoolean) {
/* 131 */     this.p.setRevocationEnabled(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRevocationEnabled() {
/* 136 */     return this.p.isRevocationEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExplicitPolicyRequired(boolean paramBoolean) {
/* 141 */     this.p.setExplicitPolicyRequired(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExplicitPolicyRequired() {
/* 146 */     return this.p.isExplicitPolicyRequired();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPolicyMappingInhibited(boolean paramBoolean) {
/* 151 */     this.p.setPolicyMappingInhibited(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPolicyMappingInhibited() {
/* 156 */     return this.p.isPolicyMappingInhibited();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAnyPolicyInhibited(boolean paramBoolean) {
/* 161 */     this.p.setAnyPolicyInhibited(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAnyPolicyInhibited() {
/* 166 */     return this.p.isAnyPolicyInhibited();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPolicyQualifiersRejected(boolean paramBoolean) {
/* 171 */     this.p.setPolicyQualifiersRejected(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getPolicyQualifiersRejected() {
/* 176 */     return this.p.getPolicyQualifiersRejected();
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getDate() {
/* 181 */     return this.p.getDate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCertPathCheckers(List<PKIXCertPathChecker> paramList) {
/* 186 */     this.p.setCertPathCheckers(paramList);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PKIXCertPathChecker> getCertPathCheckers() {
/* 191 */     return this.p.getCertPathCheckers();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSigProvider() {
/* 196 */     return this.p.getSigProvider();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSigProvider(String paramString) {
/* 201 */     this.p.setSigProvider(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public CertSelector getTargetCertConstraints() {
/* 206 */     return this.p.getTargetCertConstraints();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetCertConstraints(CertSelector paramCertSelector) {
/* 212 */     if (this.p == null) {
/*     */       return;
/*     */     }
/* 215 */     this.p.setTargetCertConstraints(paramCertSelector);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/PKIXTimestampParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */