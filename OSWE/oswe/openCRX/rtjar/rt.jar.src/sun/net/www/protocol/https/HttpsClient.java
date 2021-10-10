/*     */ package sun.net.www.protocol.https;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Proxy;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.net.URL;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.AccessController;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.net.ssl.HandshakeCompletedEvent;
/*     */ import javax.net.ssl.HandshakeCompletedListener;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.SSLParameters;
/*     */ import javax.net.ssl.SSLPeerUnverifiedException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.security.cert.X509Certificate;
/*     */ import sun.net.www.http.HttpClient;
/*     */ import sun.net.www.protocol.http.HttpURLConnection;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.ssl.SSLSocketImpl;
/*     */ import sun.security.util.HostnameChecker;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class HttpsClient
/*     */   extends HttpClient
/*     */   implements HandshakeCompletedListener
/*     */ {
/*     */   private static final int httpsPortNumber = 443;
/*     */   private static final String defaultHVCanonicalName = "javax.net.ssl.HttpsURLConnection.DefaultHostnameVerifier";
/*     */   private HostnameVerifier hv;
/*     */   private SSLSocketFactory sslSocketFactory;
/*     */   private SSLSession session;
/*     */   
/*     */   protected int getDefaultPort() {
/* 122 */     return 443;
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
/*     */   private String[] getCipherSuites() {
/* 142 */     String arrayOfString[], str = AccessController.<String>doPrivileged(new GetPropertyAction("https.cipherSuites"));
/*     */ 
/*     */     
/* 145 */     if (str == null || "".equals(str)) {
/* 146 */       arrayOfString = null;
/*     */     } else {
/*     */       
/* 149 */       Vector<String> vector = new Vector();
/*     */       
/* 151 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 152 */       while (stringTokenizer.hasMoreTokens())
/* 153 */         vector.addElement(stringTokenizer.nextToken()); 
/* 154 */       arrayOfString = new String[vector.size()];
/* 155 */       for (byte b = 0; b < arrayOfString.length; b++)
/* 156 */         arrayOfString[b] = vector.elementAt(b); 
/*     */     } 
/* 158 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] getProtocols() {
/* 166 */     String arrayOfString[], str = AccessController.<String>doPrivileged(new GetPropertyAction("https.protocols"));
/*     */ 
/*     */     
/* 169 */     if (str == null || "".equals(str)) {
/* 170 */       arrayOfString = null;
/*     */     } else {
/*     */       
/* 173 */       Vector<String> vector = new Vector();
/*     */       
/* 175 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 176 */       while (stringTokenizer.hasMoreTokens())
/* 177 */         vector.addElement(stringTokenizer.nextToken()); 
/* 178 */       arrayOfString = new String[vector.size()];
/* 179 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 180 */         arrayOfString[b] = vector.elementAt(b);
/*     */       }
/*     */     } 
/* 183 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   private String getUserAgent() {
/* 187 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("https.agent"));
/*     */     
/* 189 */     if (str == null || str.length() == 0) {
/* 190 */       str = "JSSE";
/*     */     }
/* 192 */     return str;
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
/*     */   private HttpsClient(SSLSocketFactory paramSSLSocketFactory, URL paramURL) throws IOException {
/* 217 */     this(paramSSLSocketFactory, paramURL, (String)null, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HttpsClient(SSLSocketFactory paramSSLSocketFactory, URL paramURL, String paramString, int paramInt) throws IOException {
/* 226 */     this(paramSSLSocketFactory, paramURL, paramString, paramInt, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HttpsClient(SSLSocketFactory paramSSLSocketFactory, URL paramURL, String paramString, int paramInt1, int paramInt2) throws IOException {
/* 236 */     this(paramSSLSocketFactory, paramURL, (paramString == null) ? null : 
/*     */         
/* 238 */         HttpClient.newHttpProxy(paramString, paramInt1, "https"), paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HttpsClient(SSLSocketFactory paramSSLSocketFactory, URL paramURL, Proxy paramProxy, int paramInt) throws IOException {
/* 248 */     PlatformLogger platformLogger = HttpURLConnection.getHttpLogger();
/* 249 */     if (platformLogger.isLoggable(PlatformLogger.Level.FINEST)) {
/* 250 */       platformLogger.finest("Creating new HttpsClient with url:" + paramURL + " and proxy:" + paramProxy + " with connect timeout:" + paramInt);
/*     */     }
/*     */     
/* 253 */     this.proxy = paramProxy;
/* 254 */     setSSLSocketFactory(paramSSLSocketFactory);
/* 255 */     this.proxyDisabled = true;
/*     */     
/* 257 */     this.host = paramURL.getHost();
/* 258 */     this.url = paramURL;
/* 259 */     this.port = paramURL.getPort();
/* 260 */     if (this.port == -1) {
/* 261 */       this.port = getDefaultPort();
/*     */     }
/* 263 */     setConnectTimeout(paramInt);
/* 264 */     openServer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static HttpClient New(SSLSocketFactory paramSSLSocketFactory, URL paramURL, HostnameVerifier paramHostnameVerifier, HttpURLConnection paramHttpURLConnection) throws IOException {
/* 274 */     return New(paramSSLSocketFactory, paramURL, paramHostnameVerifier, true, paramHttpURLConnection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static HttpClient New(SSLSocketFactory paramSSLSocketFactory, URL paramURL, HostnameVerifier paramHostnameVerifier, boolean paramBoolean, HttpURLConnection paramHttpURLConnection) throws IOException {
/* 281 */     return New(paramSSLSocketFactory, paramURL, paramHostnameVerifier, (String)null, -1, paramBoolean, paramHttpURLConnection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static HttpClient New(SSLSocketFactory paramSSLSocketFactory, URL paramURL, HostnameVerifier paramHostnameVerifier, String paramString, int paramInt, HttpURLConnection paramHttpURLConnection) throws IOException {
/* 291 */     return New(paramSSLSocketFactory, paramURL, paramHostnameVerifier, paramString, paramInt, true, paramHttpURLConnection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static HttpClient New(SSLSocketFactory paramSSLSocketFactory, URL paramURL, HostnameVerifier paramHostnameVerifier, String paramString, int paramInt, boolean paramBoolean, HttpURLConnection paramHttpURLConnection) throws IOException {
/* 298 */     return New(paramSSLSocketFactory, paramURL, paramHostnameVerifier, paramString, paramInt, paramBoolean, -1, paramHttpURLConnection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static HttpClient New(SSLSocketFactory paramSSLSocketFactory, URL paramURL, HostnameVerifier paramHostnameVerifier, String paramString, int paramInt1, boolean paramBoolean, int paramInt2, HttpURLConnection paramHttpURLConnection) throws IOException {
/* 307 */     return New(paramSSLSocketFactory, paramURL, paramHostnameVerifier, (paramString == null) ? null : 
/*     */         
/* 309 */         HttpClient.newHttpProxy(paramString, paramInt1, "https"), paramBoolean, paramInt2, paramHttpURLConnection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static HttpClient New(SSLSocketFactory paramSSLSocketFactory, URL paramURL, HostnameVerifier paramHostnameVerifier, Proxy paramProxy, boolean paramBoolean, int paramInt, HttpURLConnection paramHttpURLConnection) throws IOException {
/* 318 */     if (paramProxy == null) {
/* 319 */       paramProxy = Proxy.NO_PROXY;
/*     */     }
/* 321 */     PlatformLogger platformLogger = HttpURLConnection.getHttpLogger();
/* 322 */     if (platformLogger.isLoggable(PlatformLogger.Level.FINEST)) {
/* 323 */       platformLogger.finest("Looking for HttpClient for URL " + paramURL + " and proxy value of " + paramProxy);
/*     */     }
/*     */     
/* 326 */     HttpsClient httpsClient = null;
/* 327 */     if (paramBoolean) {
/*     */       
/* 329 */       httpsClient = (HttpsClient)kac.get(paramURL, paramSSLSocketFactory);
/* 330 */       if (httpsClient != null && paramHttpURLConnection != null && paramHttpURLConnection
/* 331 */         .streaming() && paramHttpURLConnection
/* 332 */         .getRequestMethod() == "POST" && 
/* 333 */         !httpsClient.available()) {
/* 334 */         httpsClient = null;
/*     */       }
/*     */       
/* 337 */       if (httpsClient != null) {
/* 338 */         if ((httpsClient.proxy != null && httpsClient.proxy.equals(paramProxy)) || (httpsClient.proxy == null && paramProxy == Proxy.NO_PROXY)) {
/*     */           
/* 340 */           synchronized (httpsClient) {
/* 341 */             httpsClient.cachedHttpClient = true;
/* 342 */             assert httpsClient.inCache;
/* 343 */             httpsClient.inCache = false;
/* 344 */             if (paramHttpURLConnection != null && httpsClient.needsTunneling())
/* 345 */               paramHttpURLConnection.setTunnelState(HttpURLConnection.TunnelState.TUNNELING); 
/* 346 */             if (platformLogger.isLoggable(PlatformLogger.Level.FINEST)) {
/* 347 */               platformLogger.finest("KeepAlive stream retrieved from the cache, " + httpsClient);
/*     */             
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 355 */           synchronized (httpsClient) {
/* 356 */             if (platformLogger.isLoggable(PlatformLogger.Level.FINEST)) {
/* 357 */               platformLogger.finest("Not returning this connection to cache: " + httpsClient);
/*     */             }
/* 359 */             httpsClient.inCache = false;
/* 360 */             httpsClient.closeServer();
/*     */           } 
/* 362 */           httpsClient = null;
/*     */         } 
/*     */       }
/*     */     } 
/* 366 */     if (httpsClient == null) {
/* 367 */       httpsClient = new HttpsClient(paramSSLSocketFactory, paramURL, paramProxy, paramInt);
/*     */     } else {
/* 369 */       SecurityManager securityManager = System.getSecurityManager();
/* 370 */       if (securityManager != null) {
/* 371 */         if (httpsClient.proxy == Proxy.NO_PROXY || httpsClient.proxy == null) {
/* 372 */           securityManager.checkConnect(InetAddress.getByName(paramURL.getHost()).getHostAddress(), paramURL.getPort());
/*     */         } else {
/* 374 */           securityManager.checkConnect(paramURL.getHost(), paramURL.getPort());
/*     */         } 
/*     */       }
/* 377 */       httpsClient.url = paramURL;
/*     */     } 
/* 379 */     httpsClient.setHostnameVerifier(paramHostnameVerifier);
/*     */     
/* 381 */     return httpsClient;
/*     */   }
/*     */ 
/*     */   
/*     */   void setHostnameVerifier(HostnameVerifier paramHostnameVerifier) {
/* 386 */     this.hv = paramHostnameVerifier;
/*     */   }
/*     */   
/*     */   void setSSLSocketFactory(SSLSocketFactory paramSSLSocketFactory) {
/* 390 */     this.sslSocketFactory = paramSSLSocketFactory;
/*     */   }
/*     */   
/*     */   SSLSocketFactory getSSLSocketFactory() {
/* 394 */     return this.sslSocketFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Socket createSocket() throws IOException {
/*     */     try {
/* 405 */       return this.sslSocketFactory.createSocket();
/* 406 */     } catch (SocketException socketException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 413 */       Throwable throwable = socketException.getCause();
/* 414 */       if (throwable != null && throwable instanceof UnsupportedOperationException) {
/* 415 */         return super.createSocket();
/*     */       }
/* 417 */       throw socketException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsTunneling() {
/* 425 */     return (this.proxy != null && this.proxy.type() != Proxy.Type.DIRECT && this.proxy
/* 426 */       .type() != Proxy.Type.SOCKS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void afterConnect() throws IOException, UnknownHostException {
/* 431 */     if (!isCachedConnection()) {
/* 432 */       SSLSocket sSLSocket = null;
/* 433 */       SSLSocketFactory sSLSocketFactory = this.sslSocketFactory;
/*     */       try {
/* 435 */         if (!(this.serverSocket instanceof SSLSocket)) {
/* 436 */           sSLSocket = (SSLSocket)sSLSocketFactory.createSocket(this.serverSocket, this.host, this.port, true);
/*     */         } else {
/*     */           
/* 439 */           sSLSocket = (SSLSocket)this.serverSocket;
/* 440 */           if (sSLSocket instanceof SSLSocketImpl) {
/* 441 */             ((SSLSocketImpl)sSLSocket).setHost(this.host);
/*     */           }
/*     */         } 
/* 444 */       } catch (IOException iOException) {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 449 */           sSLSocket = (SSLSocket)sSLSocketFactory.createSocket(this.host, this.port);
/* 450 */         } catch (IOException iOException1) {
/* 451 */           throw iOException;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 460 */       String[] arrayOfString1 = getProtocols();
/* 461 */       String[] arrayOfString2 = getCipherSuites();
/* 462 */       if (arrayOfString1 != null) {
/* 463 */         sSLSocket.setEnabledProtocols(arrayOfString1);
/*     */       }
/* 465 */       if (arrayOfString2 != null) {
/* 466 */         sSLSocket.setEnabledCipherSuites(arrayOfString2);
/*     */       }
/* 468 */       sSLSocket.addHandshakeCompletedListener(this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 518 */       boolean bool = true;
/*     */       
/* 520 */       String str = sSLSocket.getSSLParameters().getEndpointIdentificationAlgorithm();
/* 521 */       if (str != null && str.length() != 0) {
/* 522 */         if (str.equalsIgnoreCase("HTTPS"))
/*     */         {
/*     */ 
/*     */           
/* 526 */           bool = false;
/*     */         }
/*     */       } else {
/*     */         
/* 530 */         boolean bool1 = false;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 535 */         if (this.hv != null) {
/* 536 */           String str1 = this.hv.getClass().getCanonicalName();
/* 537 */           if (str1 != null && str1
/* 538 */             .equalsIgnoreCase("javax.net.ssl.HttpsURLConnection.DefaultHostnameVerifier")) {
/* 539 */             bool1 = true;
/*     */           
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 545 */           bool1 = true;
/*     */         } 
/*     */         
/* 548 */         if (bool1) {
/*     */ 
/*     */           
/* 551 */           SSLParameters sSLParameters = sSLSocket.getSSLParameters();
/* 552 */           sSLParameters.setEndpointIdentificationAlgorithm("HTTPS");
/* 553 */           sSLSocket.setSSLParameters(sSLParameters);
/*     */           
/* 555 */           bool = false;
/*     */         } 
/*     */       } 
/*     */       
/* 559 */       sSLSocket.startHandshake();
/* 560 */       this.session = sSLSocket.getSession();
/*     */       
/* 562 */       this.serverSocket = sSLSocket;
/*     */       try {
/* 564 */         this
/* 565 */           .serverOutput = new PrintStream(new BufferedOutputStream(this.serverSocket.getOutputStream()), false, encoding);
/*     */       }
/* 567 */       catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 568 */         throw new InternalError(encoding + " encoding not found");
/*     */       } 
/*     */ 
/*     */       
/* 572 */       if (bool) {
/* 573 */         checkURLSpoofing(this.hv);
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 579 */       this.session = ((SSLSocket)this.serverSocket).getSession();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkURLSpoofing(HostnameVerifier paramHostnameVerifier) throws IOException {
/* 590 */     String str1 = this.url.getHost();
/*     */ 
/*     */     
/* 593 */     if (str1 != null && str1.startsWith("[") && str1.endsWith("]")) {
/* 594 */       str1 = str1.substring(1, str1.length() - 1);
/*     */     }
/*     */     
/* 597 */     Certificate[] arrayOfCertificate = null;
/* 598 */     String str2 = this.session.getCipherSuite();
/*     */     try {
/* 600 */       HostnameChecker hostnameChecker = HostnameChecker.getInstance((byte)1);
/*     */ 
/*     */ 
/*     */       
/* 604 */       if (str2.startsWith("TLS_KRB5")) {
/* 605 */         if (!HostnameChecker.match(str1, getPeerPrincipal())) {
/* 606 */           throw new SSLPeerUnverifiedException("Hostname checker failed for Kerberos");
/*     */         }
/*     */       } else {
/*     */         X509Certificate x509Certificate;
/*     */ 
/*     */         
/* 612 */         arrayOfCertificate = this.session.getPeerCertificates();
/*     */ 
/*     */         
/* 615 */         if (arrayOfCertificate[0] instanceof X509Certificate) {
/*     */           
/* 617 */           x509Certificate = (X509Certificate)arrayOfCertificate[0];
/*     */         } else {
/* 619 */           throw new SSLPeerUnverifiedException("");
/*     */         } 
/* 621 */         hostnameChecker.match(str1, x509Certificate);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/* 627 */     } catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 634 */     catch (CertificateException certificateException) {}
/*     */ 
/*     */ 
/*     */     
/* 638 */     if (str2 != null && str2.indexOf("_anon_") != -1)
/*     */       return; 
/* 640 */     if (paramHostnameVerifier != null && paramHostnameVerifier
/* 641 */       .verify(str1, this.session)) {
/*     */       return;
/*     */     }
/*     */     
/* 645 */     this.serverSocket.close();
/* 646 */     this.session.invalidate();
/*     */     
/* 648 */     throw new IOException("HTTPS hostname wrong:  should be <" + this.url
/* 649 */         .getHost() + ">");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void putInKeepAliveCache() {
/* 654 */     if (this.inCache) {
/* 655 */       assert false : "Duplicate put to keep alive cache";
/*     */       return;
/*     */     } 
/* 658 */     this.inCache = true;
/* 659 */     kac.put(this.url, this.sslSocketFactory, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeIdleConnection() {
/* 667 */     HttpClient httpClient = kac.get(this.url, this.sslSocketFactory);
/* 668 */     if (httpClient != null) {
/* 669 */       httpClient.closeServer();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getCipherSuite() {
/* 677 */     return this.session.getCipherSuite();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Certificate[] getLocalCertificates() {
/* 685 */     return this.session.getLocalCertificates();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
/* 696 */     return this.session.getPeerCertificates();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   X509Certificate[] getServerCertificateChain() throws SSLPeerUnverifiedException {
/* 706 */     return this.session.getPeerCertificateChain();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
/*     */     Principal principal;
/*     */     try {
/* 719 */       principal = this.session.getPeerPrincipal();
/* 720 */     } catch (AbstractMethodError abstractMethodError) {
/*     */ 
/*     */ 
/*     */       
/* 724 */       Certificate[] arrayOfCertificate = this.session.getPeerCertificates();
/* 725 */       principal = ((X509Certificate)arrayOfCertificate[0]).getSubjectX500Principal();
/*     */     } 
/* 727 */     return principal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Principal getLocalPrincipal() {
/*     */     Principal principal;
/*     */     try {
/* 738 */       principal = this.session.getLocalPrincipal();
/* 739 */     } catch (AbstractMethodError abstractMethodError) {
/* 740 */       principal = null;
/*     */ 
/*     */ 
/*     */       
/* 744 */       Certificate[] arrayOfCertificate = this.session.getLocalCertificates();
/* 745 */       if (arrayOfCertificate != null) {
/* 746 */         principal = ((X509Certificate)arrayOfCertificate[0]).getSubjectX500Principal();
/*     */       }
/*     */     } 
/* 749 */     return principal;
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
/*     */   public void handshakeCompleted(HandshakeCompletedEvent paramHandshakeCompletedEvent) {
/* 762 */     this.session = paramHandshakeCompletedEvent.getSession();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getProxyHostUsed() {
/* 771 */     if (!needsTunneling()) {
/* 772 */       return null;
/*     */     }
/* 774 */     return super.getProxyHostUsed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProxyPortUsed() {
/* 784 */     return (this.proxy == null || this.proxy.type() == Proxy.Type.DIRECT || this.proxy
/* 785 */       .type() == Proxy.Type.SOCKS) ? -1 : ((InetSocketAddress)this.proxy
/* 786 */       .address()).getPort();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/https/HttpsClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */