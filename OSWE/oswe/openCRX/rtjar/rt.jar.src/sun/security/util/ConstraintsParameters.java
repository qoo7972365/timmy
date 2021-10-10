/*     */ package sun.security.util;
/*     */ 
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.Key;
/*     */ import java.security.Timestamp;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConstraintsParameters
/*     */ {
/*     */   private final String algorithm;
/*     */   private final AlgorithmParameters algParams;
/*     */   private final Key publicKey;
/*     */   private final X509Certificate cert;
/*     */   private final boolean trustedMatch;
/*     */   private final Date pkixDate;
/*     */   private final Timestamp jarTimestamp;
/*     */   private final String variant;
/*     */   
/*     */   public ConstraintsParameters(X509Certificate paramX509Certificate, boolean paramBoolean, Date paramDate, Timestamp paramTimestamp, String paramString) {
/*  72 */     this.cert = paramX509Certificate;
/*  73 */     this.trustedMatch = paramBoolean;
/*  74 */     this.pkixDate = paramDate;
/*  75 */     this.jarTimestamp = paramTimestamp;
/*  76 */     this.variant = (paramString == null) ? "generic" : paramString;
/*  77 */     this.algorithm = null;
/*  78 */     this.algParams = null;
/*  79 */     this.publicKey = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConstraintsParameters(String paramString1, AlgorithmParameters paramAlgorithmParameters, Key paramKey, String paramString2) {
/*  84 */     this.algorithm = paramString1;
/*  85 */     this.algParams = paramAlgorithmParameters;
/*  86 */     this.publicKey = paramKey;
/*  87 */     this.cert = null;
/*  88 */     this.trustedMatch = false;
/*  89 */     this.pkixDate = null;
/*  90 */     this.jarTimestamp = null;
/*  91 */     this.variant = (paramString2 == null) ? "generic" : paramString2;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConstraintsParameters(X509Certificate paramX509Certificate) {
/*  96 */     this(paramX509Certificate, false, null, null, "generic");
/*     */   }
/*     */ 
/*     */   
/*     */   public ConstraintsParameters(Timestamp paramTimestamp) {
/* 101 */     this(null, false, null, paramTimestamp, "generic");
/*     */   }
/*     */   
/*     */   public String getAlgorithm() {
/* 105 */     return this.algorithm;
/*     */   }
/*     */   
/*     */   public AlgorithmParameters getAlgParams() {
/* 109 */     return this.algParams;
/*     */   }
/*     */   
/*     */   public Key getPublicKey() {
/* 113 */     return this.publicKey;
/*     */   }
/*     */   
/*     */   public boolean isTrustedMatch() {
/* 117 */     return this.trustedMatch;
/*     */   }
/*     */   
/*     */   public X509Certificate getCertificate() {
/* 121 */     return this.cert;
/*     */   }
/*     */   
/*     */   public Date getPKIXParamDate() {
/* 125 */     return this.pkixDate;
/*     */   }
/*     */   
/*     */   public Timestamp getJARTimestamp() {
/* 129 */     return this.jarTimestamp;
/*     */   }
/*     */   
/*     */   public String getVariant() {
/* 133 */     return this.variant;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/ConstraintsParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */