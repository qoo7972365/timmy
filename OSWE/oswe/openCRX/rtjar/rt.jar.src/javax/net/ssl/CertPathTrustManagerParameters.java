/*    */ package javax.net.ssl;
/*    */ 
/*    */ import java.security.cert.CertPathParameters;
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
/*    */ public class CertPathTrustManagerParameters
/*    */   implements ManagerFactoryParameters
/*    */ {
/*    */   private final CertPathParameters parameters;
/*    */   
/*    */   public CertPathTrustManagerParameters(CertPathParameters paramCertPathParameters) {
/* 59 */     this.parameters = (CertPathParameters)paramCertPathParameters.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CertPathParameters getParameters() {
/* 68 */     return (CertPathParameters)this.parameters.clone();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/CertPathTrustManagerParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */