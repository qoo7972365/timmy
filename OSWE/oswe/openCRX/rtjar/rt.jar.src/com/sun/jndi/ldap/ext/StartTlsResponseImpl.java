/*     */ package com.sun.jndi.ldap.ext;
/*     */ 
/*     */ import com.sun.jndi.ldap.Connection;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import javax.naming.ldap.StartTlsResponse;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.SSLPeerUnverifiedException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import sun.security.util.HostnameChecker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StartTlsResponseImpl
/*     */   extends StartTlsResponse
/*     */ {
/*     */   private static final boolean debug = false;
/*     */   private static final int DNSNAME_TYPE = 2;
/*  75 */   private transient String hostname = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private transient Connection ldapConnection = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private transient InputStream originalInputStream = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private transient OutputStream originalOutputStream = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private transient SSLSocket sslSocket = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   private transient SSLSocketFactory defaultFactory = null;
/* 101 */   private transient SSLSocketFactory currentFactory = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private transient String[] suites = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   private transient HostnameVerifier verifier = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient boolean isClosed = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -1126624615143411328L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabledCipherSuites(String[] paramArrayOfString) {
/* 139 */     this.suites = (paramArrayOfString == null) ? null : (String[])paramArrayOfString.clone();
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
/*     */   public void setHostnameVerifier(HostnameVerifier paramHostnameVerifier) {
/* 154 */     this.verifier = paramHostnameVerifier;
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
/*     */   public SSLSession negotiate() throws IOException {
/* 170 */     return negotiate(null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SSLSession negotiate(SSLSocketFactory paramSSLSocketFactory) throws IOException {
/* 205 */     if (this.isClosed && this.sslSocket != null) {
/* 206 */       throw new IOException("TLS connection is closed.");
/*     */     }
/*     */     
/* 209 */     if (paramSSLSocketFactory == null) {
/* 210 */       paramSSLSocketFactory = getDefaultFactory();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     SSLSession sSLSession = startHandshake(paramSSLSocketFactory).getSession();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     SSLPeerUnverifiedException sSLPeerUnverifiedException = null;
/*     */     try {
/* 225 */       if (verify(this.hostname, sSLSession)) {
/* 226 */         this.isClosed = false;
/* 227 */         return sSLSession;
/*     */       } 
/* 229 */     } catch (SSLPeerUnverifiedException sSLPeerUnverifiedException1) {
/*     */       
/* 231 */       sSLPeerUnverifiedException = sSLPeerUnverifiedException1;
/*     */     } 
/* 233 */     if (this.verifier != null && this.verifier
/* 234 */       .verify(this.hostname, sSLSession)) {
/* 235 */       this.isClosed = false;
/* 236 */       return sSLSession;
/*     */     } 
/*     */ 
/*     */     
/* 240 */     close();
/* 241 */     sSLSession.invalidate();
/* 242 */     if (sSLPeerUnverifiedException == null) {
/* 243 */       sSLPeerUnverifiedException = new SSLPeerUnverifiedException("hostname of the server '" + this.hostname + "' does not match the hostname in the server's certificate.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 248 */     throw sSLPeerUnverifiedException;
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
/*     */   public void close() throws IOException {
/* 260 */     if (this.isClosed) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     this.ldapConnection.replaceStreams(this.originalInputStream, this.originalOutputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 276 */     this.sslSocket.close();
/*     */     
/* 278 */     this.isClosed = true;
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
/*     */   public void setConnection(Connection paramConnection, String paramString) {
/* 290 */     this.ldapConnection = paramConnection;
/* 291 */     this.hostname = (paramString != null) ? paramString : paramConnection.host;
/* 292 */     this.originalInputStream = paramConnection.inStream;
/* 293 */     this.originalOutputStream = paramConnection.outStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SSLSocketFactory getDefaultFactory() throws IOException {
/* 304 */     if (this.defaultFactory != null) {
/* 305 */       return this.defaultFactory;
/*     */     }
/*     */     
/* 308 */     return this
/* 309 */       .defaultFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
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
/*     */   private SSLSocket startHandshake(SSLSocketFactory paramSSLSocketFactory) throws IOException {
/* 323 */     if (this.ldapConnection == null) {
/* 324 */       throw new IllegalStateException("LDAP connection has not been set. TLS requires an existing LDAP connection.");
/*     */     }
/*     */ 
/*     */     
/* 328 */     if (paramSSLSocketFactory != this.currentFactory) {
/*     */       
/* 330 */       this.sslSocket = (SSLSocket)paramSSLSocketFactory.createSocket(this.ldapConnection.sock, this.ldapConnection.host, this.ldapConnection.port, false);
/*     */       
/* 332 */       this.currentFactory = paramSSLSocketFactory;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     if (this.suites != null) {
/* 340 */       this.sslSocket.setEnabledCipherSuites(this.suites);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 353 */       this.sslSocket.startHandshake();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 360 */       this.ldapConnection.replaceStreams(this.sslSocket.getInputStream(), this.sslSocket
/* 361 */           .getOutputStream());
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 366 */     catch (IOException iOException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 372 */       this.sslSocket.close();
/* 373 */       this.isClosed = true;
/* 374 */       throw iOException;
/*     */     } 
/*     */     
/* 377 */     return this.sslSocket;
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
/*     */   private boolean verify(String paramString, SSLSession paramSSLSession) throws SSLPeerUnverifiedException {
/* 397 */     Certificate[] arrayOfCertificate = null;
/*     */ 
/*     */     
/* 400 */     if (paramString != null && paramString.startsWith("[") && paramString
/* 401 */       .endsWith("]")) {
/* 402 */       paramString = paramString.substring(1, paramString.length() - 1);
/*     */     }
/*     */     try {
/* 405 */       HostnameChecker hostnameChecker = HostnameChecker.getInstance((byte)2);
/*     */ 
/*     */       
/* 408 */       if (paramSSLSession.getCipherSuite().startsWith("TLS_KRB5")) {
/* 409 */         Principal principal = getPeerPrincipal(paramSSLSession);
/* 410 */         if (!HostnameChecker.match(paramString, principal)) {
/* 411 */           throw new SSLPeerUnverifiedException("hostname of the kerberos principal:" + principal + " does not match the hostname:" + paramString);
/*     */         }
/*     */       } else {
/*     */         X509Certificate x509Certificate;
/*     */ 
/*     */ 
/*     */         
/* 418 */         arrayOfCertificate = paramSSLSession.getPeerCertificates();
/*     */         
/* 420 */         if (arrayOfCertificate[0] instanceof X509Certificate) {
/* 421 */           x509Certificate = (X509Certificate)arrayOfCertificate[0];
/*     */         } else {
/* 423 */           throw new SSLPeerUnverifiedException("Received a non X509Certificate from the server");
/*     */         } 
/*     */         
/* 426 */         hostnameChecker.match(paramString, x509Certificate);
/*     */       } 
/*     */ 
/*     */       
/* 430 */       return true;
/* 431 */     } catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 437 */       String str = paramSSLSession.getCipherSuite();
/* 438 */       if (str != null && str.indexOf("_anon_") != -1) {
/* 439 */         return true;
/*     */       }
/* 441 */       throw sSLPeerUnverifiedException;
/* 442 */     } catch (CertificateException certificateException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 447 */       throw (SSLPeerUnverifiedException)(new SSLPeerUnverifiedException("hostname of the server '" + paramString + "' does not match the hostname in the server's certificate."))
/*     */ 
/*     */ 
/*     */         
/* 451 */         .initCause(certificateException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Principal getPeerPrincipal(SSLSession paramSSLSession) throws SSLPeerUnverifiedException {
/*     */     Principal principal;
/*     */     try {
/* 462 */       principal = paramSSLSession.getPeerPrincipal();
/* 463 */     } catch (AbstractMethodError abstractMethodError) {
/*     */ 
/*     */       
/* 466 */       principal = null;
/*     */     } 
/* 468 */     return principal;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/ext/StartTlsResponseImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */