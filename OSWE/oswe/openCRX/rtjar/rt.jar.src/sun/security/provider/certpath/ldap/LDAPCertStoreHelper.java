/*    */ package sun.security.provider.certpath.ldap;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URI;
/*    */ import java.security.InvalidAlgorithmParameterException;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.security.cert.CertStore;
/*    */ import java.security.cert.CertStoreException;
/*    */ import java.security.cert.X509CRLSelector;
/*    */ import java.security.cert.X509CertSelector;
/*    */ import java.util.Collection;
/*    */ import javax.security.auth.x500.X500Principal;
/*    */ import sun.security.provider.certpath.CertStoreHelper;
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
/*    */ public final class LDAPCertStoreHelper
/*    */   extends CertStoreHelper
/*    */ {
/*    */   public CertStore getCertStore(URI paramURI) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/* 54 */     return LDAPCertStore.getInstance(LDAPCertStore.getParameters(paramURI));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public X509CertSelector wrap(X509CertSelector paramX509CertSelector, X500Principal paramX500Principal, String paramString) throws IOException {
/* 63 */     return new LDAPCertStore.LDAPCertSelector(paramX509CertSelector, paramX500Principal, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public X509CRLSelector wrap(X509CRLSelector paramX509CRLSelector, Collection<X500Principal> paramCollection, String paramString) throws IOException {
/* 72 */     return new LDAPCertStore.LDAPCRLSelector(paramX509CRLSelector, paramCollection, paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCausedByNetworkIssue(CertStoreException paramCertStoreException) {
/* 77 */     Throwable throwable = paramCertStoreException.getCause();
/* 78 */     return (throwable != null && (throwable instanceof javax.naming.ServiceUnavailableException || throwable instanceof javax.naming.CommunicationException));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/ldap/LDAPCertStoreHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */