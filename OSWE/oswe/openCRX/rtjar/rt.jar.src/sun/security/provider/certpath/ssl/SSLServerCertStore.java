/*     */ package sun.security.provider.certpath.ssl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ import java.net.URI;
/*     */ import java.net.URLConnection;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.Provider;
/*     */ import java.security.cert.CRLSelector;
/*     */ import java.security.cert.CertSelector;
/*     */ import java.security.cert.CertStore;
/*     */ import java.security.cert.CertStoreException;
/*     */ import java.security.cert.CertStoreParameters;
/*     */ import java.security.cert.CertStoreSpi;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509CRL;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509ExtendedTrustManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SSLServerCertStore
/*     */   extends CertStoreSpi
/*     */ {
/*     */   private final URI uri;
/*  69 */   private static final GetChainTrustManager trustManager = new GetChainTrustManager(); private static final SSLSocketFactory socketFactory;
/*  70 */   private static final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
/*     */       public boolean verify(String param1String, SSLSession param1SSLSession) {
/*  72 */         return true;
/*     */       }
/*     */     };
/*     */   
/*     */   static {
/*     */     try {
/*  78 */       SSLContext sSLContext = SSLContext.getInstance("SSL");
/*  79 */       sSLContext.init(null, new TrustManager[] { trustManager }, null);
/*  80 */       sSLSocketFactory = sSLContext.getSocketFactory();
/*  81 */     } catch (GeneralSecurityException generalSecurityException) {
/*  82 */       sSLSocketFactory = null;
/*     */     } 
/*     */     
/*  85 */     socketFactory = sSLSocketFactory;
/*     */   } static {
/*     */     SSLSocketFactory sSLSocketFactory;
/*     */   } SSLServerCertStore(URI paramURI) throws InvalidAlgorithmParameterException {
/*  89 */     super(null);
/*  90 */     this.uri = paramURI;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<X509Certificate> engineGetCertificates(CertSelector paramCertSelector) throws CertStoreException {
/*     */     try {
/*  97 */       URLConnection uRLConnection = this.uri.toURL().openConnection();
/*  98 */       if (uRLConnection instanceof HttpsURLConnection) {
/*  99 */         if (socketFactory == null) {
/* 100 */           throw new CertStoreException("No initialized SSLSocketFactory");
/*     */         }
/*     */ 
/*     */         
/* 104 */         HttpsURLConnection httpsURLConnection = (HttpsURLConnection)uRLConnection;
/* 105 */         httpsURLConnection.setSSLSocketFactory(socketFactory);
/* 106 */         httpsURLConnection.setHostnameVerifier(hostnameVerifier);
/* 107 */         synchronized (trustManager) {
/*     */           try {
/* 109 */             httpsURLConnection.connect();
/* 110 */             return getMatchingCerts(trustManager
/* 111 */                 .serverChain, paramCertSelector);
/* 112 */           } catch (IOException iOException) {
/*     */ 
/*     */             
/* 115 */             if (trustManager.exchangedServerCerts) {
/* 116 */               return getMatchingCerts(trustManager
/* 117 */                   .serverChain, paramCertSelector);
/*     */             }
/*     */ 
/*     */             
/* 121 */             throw iOException;
/*     */           } finally {
/* 123 */             trustManager.cleanup();
/*     */           } 
/*     */         } 
/*     */       } 
/* 127 */     } catch (IOException iOException) {
/* 128 */       throw new CertStoreException(iOException);
/*     */     } 
/*     */     
/* 131 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<X509Certificate> getMatchingCerts(List<X509Certificate> paramList, CertSelector paramCertSelector) {
/* 138 */     if (paramCertSelector == null) {
/* 139 */       return paramList;
/*     */     }
/* 141 */     ArrayList<X509Certificate> arrayList = new ArrayList(paramList.size());
/* 142 */     for (X509Certificate x509Certificate : paramList) {
/* 143 */       if (paramCertSelector.match(x509Certificate)) {
/* 144 */         arrayList.add(x509Certificate);
/*     */       }
/*     */     } 
/* 147 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<X509CRL> engineGetCRLs(CRLSelector paramCRLSelector) throws CertStoreException {
/* 153 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static CertStore getInstance(URI paramURI) throws InvalidAlgorithmParameterException {
/* 159 */     return new CS(new SSLServerCertStore(paramURI), null, "SSLServer", null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class GetChainTrustManager
/*     */     extends X509ExtendedTrustManager
/*     */   {
/* 170 */     private List<X509Certificate> serverChain = Collections.emptyList();
/*     */     
/*     */     private boolean exchangedServerCerts = false;
/*     */     
/*     */     public X509Certificate[] getAcceptedIssuers() {
/* 175 */       return new X509Certificate[0];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkClientTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String) throws CertificateException {
/* 182 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkClientTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String, Socket param1Socket) throws CertificateException {
/* 189 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkClientTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String, SSLEngine param1SSLEngine) throws CertificateException {
/* 196 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkServerTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String) throws CertificateException {
/* 203 */       this.exchangedServerCerts = true;
/* 204 */       this
/*     */         
/* 206 */         .serverChain = (param1ArrayOfX509Certificate == null) ? Collections.<X509Certificate>emptyList() : Arrays.<X509Certificate>asList(param1ArrayOfX509Certificate);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkServerTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String, Socket param1Socket) throws CertificateException {
/* 214 */       checkServerTrusted(param1ArrayOfX509Certificate, param1String);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkServerTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String, SSLEngine param1SSLEngine) throws CertificateException {
/* 221 */       checkServerTrusted(param1ArrayOfX509Certificate, param1String);
/*     */     }
/*     */     
/*     */     void cleanup() {
/* 225 */       this.exchangedServerCerts = false;
/* 226 */       this.serverChain = Collections.emptyList();
/*     */     }
/*     */ 
/*     */     
/*     */     private GetChainTrustManager() {}
/*     */   }
/*     */   
/*     */   private static class CS
/*     */     extends CertStore
/*     */   {
/*     */     protected CS(CertStoreSpi param1CertStoreSpi, Provider param1Provider, String param1String, CertStoreParameters param1CertStoreParameters) {
/* 237 */       super(param1CertStoreSpi, param1Provider, param1String, param1CertStoreParameters);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/certpath/ssl/SSLServerCertStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */