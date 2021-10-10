/*     */ package javax.net.ssl;
/*     */ 
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HttpsURLConnection
/*     */   extends HttpURLConnection
/*     */ {
/*     */   protected HttpsURLConnection(URL paramURL) {
/*  65 */     super(paramURL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     this.hostnameVerifier = defaultHostnameVerifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     this.sslSocketFactory = getDefaultSSLSocketFactory();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getCipherSuite();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Certificate[] getLocalCertificates();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Certificate[] getServerCertificates() throws SSLPeerUnverifiedException;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaultSSLSocketFactory(SSLSocketFactory paramSSLSocketFactory) {
/* 307 */     if (paramSSLSocketFactory == null) {
/* 308 */       throw new IllegalArgumentException("no default SSLSocketFactory specified");
/*     */     }
/*     */ 
/*     */     
/* 312 */     SecurityManager securityManager = System.getSecurityManager();
/* 313 */     if (securityManager != null) {
/* 314 */       securityManager.checkSetFactory();
/*     */     }
/* 316 */     defaultSSLSocketFactory = paramSSLSocketFactory;
/*     */   } public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
/*     */     Certificate[] arrayOfCertificate = getServerCertificates();
/*     */     return ((X509Certificate)arrayOfCertificate[0]).getSubjectX500Principal();
/*     */   } public Principal getLocalPrincipal() {
/*     */     Certificate[] arrayOfCertificate = getLocalCertificates();
/*     */     if (arrayOfCertificate != null)
/*     */       return ((X509Certificate)arrayOfCertificate[0]).getSubjectX500Principal(); 
/*     */     return null;
/*     */   } private static HostnameVerifier defaultHostnameVerifier = new DefaultHostnameVerifier(); protected HostnameVerifier hostnameVerifier; private static class DefaultHostnameVerifier implements HostnameVerifier {
/*     */     private DefaultHostnameVerifier() {} public boolean verify(String param1String, SSLSession param1SSLSession) {
/*     */       return false;
/*     */     } }
/*     */   public static SSLSocketFactory getDefaultSSLSocketFactory() {
/* 330 */     if (defaultSSLSocketFactory == null)
/*     */     {
/* 332 */       defaultSSLSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
/*     */     }
/* 334 */     return defaultSSLSocketFactory;
/*     */   }
/*     */   public static void setDefaultHostnameVerifier(HostnameVerifier paramHostnameVerifier) {
/*     */     if (paramHostnameVerifier == null)
/*     */       throw new IllegalArgumentException("no default HostnameVerifier specified"); 
/*     */     SecurityManager securityManager = System.getSecurityManager();
/*     */     if (securityManager != null)
/*     */       securityManager.checkPermission(new SSLPermission("setHostnameVerifier")); 
/*     */     defaultHostnameVerifier = paramHostnameVerifier;
/*     */   }
/*     */   
/*     */   public static HostnameVerifier getDefaultHostnameVerifier() {
/*     */     return defaultHostnameVerifier;
/*     */   }
/*     */   
/*     */   public void setHostnameVerifier(HostnameVerifier paramHostnameVerifier) {
/*     */     if (paramHostnameVerifier == null)
/*     */       throw new IllegalArgumentException("no HostnameVerifier specified"); 
/*     */     this.hostnameVerifier = paramHostnameVerifier;
/*     */   }
/*     */   
/*     */   public void setSSLSocketFactory(SSLSocketFactory paramSSLSocketFactory) {
/* 356 */     if (paramSSLSocketFactory == null) {
/* 357 */       throw new IllegalArgumentException("no SSLSocketFactory specified");
/*     */     }
/*     */ 
/*     */     
/* 361 */     SecurityManager securityManager = System.getSecurityManager();
/* 362 */     if (securityManager != null) {
/* 363 */       securityManager.checkSetFactory();
/*     */     }
/* 365 */     this.sslSocketFactory = paramSSLSocketFactory;
/*     */   }
/*     */   
/*     */   public HostnameVerifier getHostnameVerifier() {
/*     */     return this.hostnameVerifier;
/*     */   }
/*     */   
/*     */   private static SSLSocketFactory defaultSSLSocketFactory = null;
/*     */   private SSLSocketFactory sslSocketFactory;
/*     */   
/*     */   public SSLSocketFactory getSSLSocketFactory() {
/* 376 */     return this.sslSocketFactory;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/HttpsURLConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */