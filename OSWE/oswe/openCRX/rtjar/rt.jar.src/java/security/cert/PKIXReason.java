/*    */ package java.security.cert;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum PKIXReason
/*    */   implements CertPathValidatorException.Reason
/*    */ {
/* 40 */   NAME_CHAINING,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   INVALID_KEY_USAGE,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   INVALID_POLICY,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   NO_TRUST_ANCHOR,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 61 */   UNRECOGNIZED_CRIT_EXT,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 66 */   NOT_CA_CERT,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 71 */   PATH_TOO_LONG,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 76 */   INVALID_NAME;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/PKIXReason.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */