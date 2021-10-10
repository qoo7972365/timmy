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
/*     */ public class PKIXCertPathValidatorResult
/*     */   implements CertPathValidatorResult
/*     */ {
/*     */   private TrustAnchor trustAnchor;
/*     */   private PolicyNode policyTree;
/*     */   private PublicKey subjectPublicKey;
/*     */   
/*     */   public PKIXCertPathValidatorResult(TrustAnchor paramTrustAnchor, PolicyNode paramPolicyNode, PublicKey paramPublicKey) {
/*  79 */     if (paramPublicKey == null)
/*  80 */       throw new NullPointerException("subjectPublicKey must be non-null"); 
/*  81 */     if (paramTrustAnchor == null)
/*  82 */       throw new NullPointerException("trustAnchor must be non-null"); 
/*  83 */     this.trustAnchor = paramTrustAnchor;
/*  84 */     this.policyTree = paramPolicyNode;
/*  85 */     this.subjectPublicKey = paramPublicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrustAnchor getTrustAnchor() {
/*  95 */     return this.trustAnchor;
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
/*     */   public PolicyNode getPolicyTree() {
/* 116 */     return this.policyTree;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PublicKey getPublicKey() {
/* 126 */     return this.subjectPublicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 136 */       return super.clone();
/* 137 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 139 */       throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
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
/*     */   public String toString() {
/* 151 */     StringBuffer stringBuffer = new StringBuffer();
/* 152 */     stringBuffer.append("PKIXCertPathValidatorResult: [\n");
/* 153 */     stringBuffer.append("  Trust Anchor: " + this.trustAnchor.toString() + "\n");
/* 154 */     stringBuffer.append("  Policy Tree: " + String.valueOf(this.policyTree) + "\n");
/* 155 */     stringBuffer.append("  Subject Public Key: " + this.subjectPublicKey + "\n");
/* 156 */     stringBuffer.append("]");
/* 157 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/PKIXCertPathValidatorResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */