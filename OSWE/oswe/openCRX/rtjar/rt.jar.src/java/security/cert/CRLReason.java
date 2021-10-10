/*     */ package java.security.cert;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum CRLReason
/*     */ {
/*  44 */   UNSPECIFIED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   KEY_COMPROMISE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   CA_COMPROMISE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   AFFILIATION_CHANGED,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   SUPERSEDED,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   CESSATION_OF_OPERATION,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   CERTIFICATE_HOLD,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   UNUSED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   REMOVE_FROM_CRL,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   PRIVILEGE_WITHDRAWN,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   AA_COMPROMISE;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/CRLReason.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */