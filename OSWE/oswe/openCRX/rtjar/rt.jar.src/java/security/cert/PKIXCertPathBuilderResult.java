/*     */ package java.security.cert;
/*     */ 
/*     */ import java.security.PublicKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PKIXCertPathBuilderResult
/*     */   extends PKIXCertPathValidatorResult
/*     */   implements CertPathBuilderResult
/*     */ {
/*     */   private CertPath certPath;
/*     */   
/*     */   public PKIXCertPathBuilderResult(CertPath paramCertPath, TrustAnchor paramTrustAnchor, PolicyNode paramPolicyNode, PublicKey paramPublicKey) {
/*  82 */     super(paramTrustAnchor, paramPolicyNode, paramPublicKey);
/*  83 */     if (paramCertPath == null)
/*  84 */       throw new NullPointerException("certPath must be non-null"); 
/*  85 */     this.certPath = paramCertPath;
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
/*     */   public CertPath getCertPath() {
/*  99 */     return this.certPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     StringBuffer stringBuffer = new StringBuffer();
/* 111 */     stringBuffer.append("PKIXCertPathBuilderResult: [\n");
/* 112 */     stringBuffer.append("  Certification Path: " + this.certPath + "\n");
/* 113 */     stringBuffer.append("  Trust Anchor: " + getTrustAnchor().toString() + "\n");
/* 114 */     stringBuffer.append("  Policy Tree: " + String.valueOf(getPolicyTree()) + "\n");
/* 115 */     stringBuffer.append("  Subject Public Key: " + getPublicKey() + "\n");
/* 116 */     stringBuffer.append("]");
/* 117 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/PKIXCertPathBuilderResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */