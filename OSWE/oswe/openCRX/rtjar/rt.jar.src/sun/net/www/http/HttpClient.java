/*      */ package sun.net.www.http;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.CacheRequest;
/*      */ import java.net.CookieHandler;
/*      */ import java.net.InetAddress;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.Proxy;
/*      */ import java.net.SocketException;
/*      */ import java.net.SocketTimeoutException;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.net.UnknownHostException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.Locale;
/*      */ import sun.net.NetworkClient;
/*      */ import sun.net.ProgressSource;
/*      */ import sun.net.www.HeaderParser;
/*      */ import sun.net.www.MessageHeader;
/*      */ import sun.net.www.MeteredStream;
/*      */ import sun.net.www.ParseUtil;
/*      */ import sun.net.www.URLConnection;
/*      */ import sun.net.www.protocol.http.HttpURLConnection;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HttpClient
/*      */   extends NetworkClient
/*      */ {
/*      */   protected boolean cachedHttpClient = false;
/*      */   protected boolean inCache;
/*      */   MessageHeader requests;
/*   55 */   PosterOutputStream poster = null;
/*      */   
/*      */   boolean streaming;
/*      */   
/*      */   boolean failedOnce = false;
/*      */   
/*      */   private boolean ignoreContinue = true;
/*      */   
/*      */   private static final int HTTP_CONTINUE = 100;
/*      */   
/*      */   static final int httpPortNumber = 80;
/*      */   
/*      */   protected boolean proxyDisabled;
/*      */ 
/*      */   
/*      */   protected int getDefaultPort() {
/*   71 */     return 80;
/*      */   }
/*      */   private static int getDefaultPort(String paramString) {
/*   74 */     if ("http".equalsIgnoreCase(paramString))
/*   75 */       return 80; 
/*   76 */     if ("https".equalsIgnoreCase(paramString))
/*   77 */       return 443; 
/*   78 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean usingProxy = false;
/*      */ 
/*      */   
/*      */   protected String host;
/*      */ 
/*      */   
/*      */   protected int port;
/*      */ 
/*      */   
/*   93 */   protected static KeepAliveCache kac = new KeepAliveCache();
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean keepAliveProp = true;
/*      */ 
/*      */   
/*      */   private static boolean retryPostProp = true;
/*      */ 
/*      */   
/*      */   private static final boolean cacheNTLMProp;
/*      */ 
/*      */   
/*      */   private static final boolean cacheSPNEGOProp;
/*      */ 
/*      */   
/*      */   volatile boolean keepingAlive = false;
/*      */ 
/*      */   
/*      */   volatile boolean disableKeepAlive;
/*      */ 
/*      */   
/*  115 */   int keepAliveConnections = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  125 */   int keepAliveTimeout = 0;
/*      */ 
/*      */   
/*  128 */   private CacheRequest cacheRequest = null;
/*      */ 
/*      */   
/*      */   protected URL url;
/*      */ 
/*      */   
/*      */   public boolean reuse = false;
/*      */ 
/*      */   
/*  137 */   private HttpCapture capture = null;
/*      */   
/*  139 */   private static final PlatformLogger logger = HttpURLConnection.getHttpLogger();
/*      */   private static void logFinest(String paramString) {
/*  141 */     if (logger.isLoggable(PlatformLogger.Level.FINEST)) {
/*  142 */       logger.finest(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static synchronized void resetProperties() {}
/*      */ 
/*      */ 
/*      */   
/*      */   int getKeepAliveTimeout() {
/*  155 */     return this.keepAliveTimeout;
/*      */   }
/*      */   
/*      */   static {
/*  159 */     String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("http.keepAlive"));
/*      */ 
/*      */     
/*  162 */     String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.net.http.retryPost"));
/*      */ 
/*      */     
/*  165 */     String str3 = AccessController.<String>doPrivileged(new GetPropertyAction("jdk.ntlm.cache"));
/*      */ 
/*      */     
/*  168 */     String str4 = AccessController.<String>doPrivileged(new GetPropertyAction("jdk.spnego.cache"));
/*      */ 
/*      */     
/*  171 */     if (str1 != null) {
/*  172 */       keepAliveProp = Boolean.valueOf(str1).booleanValue();
/*      */     } else {
/*  174 */       keepAliveProp = true;
/*      */     } 
/*      */     
/*  177 */     if (str2 != null) {
/*  178 */       retryPostProp = Boolean.valueOf(str2).booleanValue();
/*      */     } else {
/*  180 */       retryPostProp = true;
/*      */     } 
/*      */     
/*  183 */     if (str3 != null) {
/*  184 */       cacheNTLMProp = Boolean.parseBoolean(str3);
/*      */     } else {
/*  186 */       cacheNTLMProp = true;
/*      */     } 
/*      */     
/*  189 */     if (str4 != null) {
/*  190 */       cacheSPNEGOProp = Boolean.parseBoolean(str4);
/*      */     } else {
/*  192 */       cacheSPNEGOProp = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getHttpKeepAliveSet() {
/*  201 */     return keepAliveProp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private HttpClient(URL paramURL) throws IOException {
/*  210 */     this(paramURL, (String)null, -1, false);
/*      */   }
/*      */ 
/*      */   
/*      */   protected HttpClient(URL paramURL, boolean paramBoolean) throws IOException {
/*  215 */     this(paramURL, (String)null, -1, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpClient(URL paramURL, String paramString, int paramInt) throws IOException {
/*  228 */     this(paramURL, paramString, paramInt, false);
/*      */   }
/*      */   
/*      */   protected HttpClient(URL paramURL, Proxy paramProxy, int paramInt) throws IOException {
/*  232 */     this.proxy = (paramProxy == null) ? Proxy.NO_PROXY : paramProxy;
/*  233 */     this.host = paramURL.getHost();
/*  234 */     this.url = paramURL;
/*  235 */     this.port = paramURL.getPort();
/*  236 */     if (this.port == -1) {
/*  237 */       this.port = getDefaultPort();
/*      */     }
/*  239 */     setConnectTimeout(paramInt);
/*      */     
/*  241 */     this.capture = HttpCapture.getCapture(paramURL);
/*  242 */     openServer();
/*      */   }
/*      */ 
/*      */   
/*      */   protected static Proxy newHttpProxy(String paramString1, int paramInt, String paramString2) {
/*  247 */     if (paramString1 == null || paramString2 == null)
/*  248 */       return Proxy.NO_PROXY; 
/*  249 */     int i = (paramInt < 0) ? getDefaultPort(paramString2) : paramInt;
/*  250 */     InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved(paramString1, i);
/*  251 */     return new Proxy(Proxy.Type.HTTP, inetSocketAddress);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private HttpClient(URL paramURL, String paramString, int paramInt, boolean paramBoolean) throws IOException {
/*  269 */     this(paramURL, paramBoolean ? Proxy.NO_PROXY : 
/*  270 */         newHttpProxy(paramString, paramInt, "http"), -1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpClient(URL paramURL, String paramString, int paramInt1, boolean paramBoolean, int paramInt2) throws IOException {
/*  276 */     this(paramURL, paramBoolean ? Proxy.NO_PROXY : 
/*  277 */         newHttpProxy(paramString, paramInt1, "http"), paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static HttpClient New(URL paramURL) throws IOException {
/*  286 */     return New(paramURL, Proxy.NO_PROXY, -1, true, (HttpURLConnection)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public static HttpClient New(URL paramURL, boolean paramBoolean) throws IOException {
/*  291 */     return New(paramURL, Proxy.NO_PROXY, -1, paramBoolean, (HttpURLConnection)null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static HttpClient New(URL paramURL, Proxy paramProxy, int paramInt, boolean paramBoolean, HttpURLConnection paramHttpURLConnection) throws IOException {
/*  297 */     if (paramProxy == null) {
/*  298 */       paramProxy = Proxy.NO_PROXY;
/*      */     }
/*  300 */     HttpClient httpClient = null;
/*      */     
/*  302 */     if (paramBoolean) {
/*  303 */       httpClient = kac.get(paramURL, null);
/*  304 */       if (httpClient != null && paramHttpURLConnection != null && paramHttpURLConnection
/*  305 */         .streaming() && paramHttpURLConnection
/*  306 */         .getRequestMethod() == "POST" && 
/*  307 */         !httpClient.available()) {
/*  308 */         httpClient.inCache = false;
/*  309 */         httpClient.closeServer();
/*  310 */         httpClient = null;
/*      */       } 
/*      */ 
/*      */       
/*  314 */       if (httpClient != null) {
/*  315 */         if ((httpClient.proxy != null && httpClient.proxy.equals(paramProxy)) || (httpClient.proxy == null && paramProxy == null)) {
/*      */           
/*  317 */           synchronized (httpClient) {
/*  318 */             httpClient.cachedHttpClient = true;
/*  319 */             assert httpClient.inCache;
/*  320 */             httpClient.inCache = false;
/*  321 */             if (paramHttpURLConnection != null && httpClient.needsTunneling())
/*  322 */               paramHttpURLConnection.setTunnelState(HttpURLConnection.TunnelState.TUNNELING); 
/*  323 */             logFinest("KeepAlive stream retrieved from the cache, " + httpClient);
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  330 */           synchronized (httpClient) {
/*  331 */             httpClient.inCache = false;
/*  332 */             httpClient.closeServer();
/*      */           } 
/*  334 */           httpClient = null;
/*      */         } 
/*      */       }
/*      */     } 
/*  338 */     if (httpClient == null) {
/*  339 */       httpClient = new HttpClient(paramURL, paramProxy, paramInt);
/*      */     } else {
/*  341 */       SecurityManager securityManager = System.getSecurityManager();
/*  342 */       if (securityManager != null) {
/*  343 */         if (httpClient.proxy == Proxy.NO_PROXY || httpClient.proxy == null) {
/*  344 */           securityManager.checkConnect(InetAddress.getByName(paramURL.getHost()).getHostAddress(), paramURL.getPort());
/*      */         } else {
/*  346 */           securityManager.checkConnect(paramURL.getHost(), paramURL.getPort());
/*      */         } 
/*      */       }
/*  349 */       httpClient.url = paramURL;
/*      */     } 
/*  351 */     return httpClient;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static HttpClient New(URL paramURL, Proxy paramProxy, int paramInt, HttpURLConnection paramHttpURLConnection) throws IOException {
/*  357 */     return New(paramURL, paramProxy, paramInt, true, paramHttpURLConnection);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static HttpClient New(URL paramURL, String paramString, int paramInt, boolean paramBoolean) throws IOException {
/*  363 */     return New(paramURL, newHttpProxy(paramString, paramInt, "http"), -1, paramBoolean, (HttpURLConnection)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static HttpClient New(URL paramURL, String paramString, int paramInt1, boolean paramBoolean, int paramInt2, HttpURLConnection paramHttpURLConnection) throws IOException {
/*  371 */     return New(paramURL, newHttpProxy(paramString, paramInt1, "http"), paramInt2, paramBoolean, paramHttpURLConnection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void finished() {
/*  386 */     if (this.reuse)
/*      */       return; 
/*  388 */     this.keepAliveConnections--;
/*  389 */     this.poster = null;
/*  390 */     if (this.keepAliveConnections > 0 && isKeepingAlive() && 
/*  391 */       !this.serverOutput.checkError()) {
/*      */ 
/*      */ 
/*      */       
/*  395 */       putInKeepAliveCache();
/*      */     } else {
/*  397 */       closeServer();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected synchronized boolean available() {
/*  402 */     boolean bool = true;
/*  403 */     int i = -1;
/*      */     
/*      */     try {
/*      */       try {
/*  407 */         i = this.serverSocket.getSoTimeout();
/*  408 */         this.serverSocket.setSoTimeout(1);
/*      */         
/*  410 */         BufferedInputStream bufferedInputStream = new BufferedInputStream(this.serverSocket.getInputStream());
/*  411 */         int j = bufferedInputStream.read();
/*  412 */         if (j == -1) {
/*  413 */           logFinest("HttpClient.available(): read returned -1: not available");
/*      */           
/*  415 */           bool = false;
/*      */         } 
/*  417 */       } catch (SocketTimeoutException socketTimeoutException) {
/*  418 */         logFinest("HttpClient.available(): SocketTimeout: its available");
/*      */       } finally {
/*      */         
/*  421 */         if (i != -1)
/*  422 */           this.serverSocket.setSoTimeout(i); 
/*      */       } 
/*  424 */     } catch (IOException iOException) {
/*  425 */       logFinest("HttpClient.available(): SocketException: not available");
/*      */       
/*  427 */       bool = false;
/*      */     } 
/*  429 */     return bool;
/*      */   }
/*      */   
/*      */   protected synchronized void putInKeepAliveCache() {
/*  433 */     if (this.inCache) {
/*  434 */       assert false : "Duplicate put to keep alive cache";
/*      */       return;
/*      */     } 
/*  437 */     this.inCache = true;
/*  438 */     kac.put(this.url, null, this);
/*      */   }
/*      */   
/*      */   protected synchronized boolean isInKeepAliveCache() {
/*  442 */     return this.inCache;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeIdleConnection() {
/*  450 */     HttpClient httpClient = kac.get(this.url, null);
/*  451 */     if (httpClient != null) {
/*  452 */       httpClient.closeServer();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openServer(String paramString, int paramInt) throws IOException {
/*  463 */     this.serverSocket = doConnect(paramString, paramInt);
/*      */     try {
/*  465 */       OutputStream outputStream = this.serverSocket.getOutputStream();
/*  466 */       if (this.capture != null) {
/*  467 */         outputStream = new HttpCaptureOutputStream(outputStream, this.capture);
/*      */       }
/*  469 */       this.serverOutput = new PrintStream(new BufferedOutputStream(outputStream), false, encoding);
/*      */     
/*      */     }
/*  472 */     catch (UnsupportedEncodingException unsupportedEncodingException) {
/*  473 */       throw new InternalError(encoding + " encoding not found", unsupportedEncodingException);
/*      */     } 
/*  475 */     this.serverSocket.setTcpNoDelay(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean needsTunneling() {
/*  483 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean isCachedConnection() {
/*  490 */     return this.cachedHttpClient;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void afterConnect() throws IOException, UnknownHostException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void privilegedOpenServer(final InetSocketAddress server) throws IOException {
/*      */     try {
/*  512 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */           {
/*      */             public Void run() throws IOException {
/*  515 */               HttpClient.this.openServer(server.getHostString(), server.getPort());
/*  516 */               return null;
/*      */             }
/*      */           });
/*  519 */     } catch (PrivilegedActionException privilegedActionException) {
/*  520 */       throw (IOException)privilegedActionException.getException();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void superOpenServer(String paramString, int paramInt) throws IOException, UnknownHostException {
/*  531 */     super.openServer(paramString, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void openServer() throws IOException {
/*  538 */     SecurityManager securityManager = System.getSecurityManager();
/*      */     
/*  540 */     if (securityManager != null) {
/*  541 */       securityManager.checkConnect(this.host, this.port);
/*      */     }
/*      */     
/*  544 */     if (this.keepingAlive) {
/*      */       return;
/*      */     }
/*      */     
/*  548 */     if (this.url.getProtocol().equals("http") || this.url
/*  549 */       .getProtocol().equals("https")) {
/*      */       
/*  551 */       if (this.proxy != null && this.proxy.type() == Proxy.Type.HTTP) {
/*  552 */         URLConnection.setProxiedHost(this.host);
/*  553 */         privilegedOpenServer((InetSocketAddress)this.proxy.address());
/*  554 */         this.usingProxy = true;
/*      */         
/*      */         return;
/*      */       } 
/*  558 */       openServer(this.host, this.port);
/*  559 */       this.usingProxy = false;
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  567 */     if (this.proxy != null && this.proxy.type() == Proxy.Type.HTTP) {
/*  568 */       URLConnection.setProxiedHost(this.host);
/*  569 */       privilegedOpenServer((InetSocketAddress)this.proxy.address());
/*  570 */       this.usingProxy = true;
/*      */       
/*      */       return;
/*      */     } 
/*  574 */     super.openServer(this.host, this.port);
/*  575 */     this.usingProxy = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getURLFile() throws IOException {
/*      */     String str;
/*  588 */     if (this.usingProxy && !this.proxyDisabled) {
/*      */ 
/*      */ 
/*      */       
/*  592 */       StringBuffer stringBuffer = new StringBuffer(128);
/*  593 */       stringBuffer.append(this.url.getProtocol());
/*  594 */       stringBuffer.append(":");
/*  595 */       if (this.url.getAuthority() != null && this.url.getAuthority().length() > 0) {
/*  596 */         stringBuffer.append("//");
/*  597 */         stringBuffer.append(this.url.getAuthority());
/*      */       } 
/*  599 */       if (this.url.getPath() != null) {
/*  600 */         stringBuffer.append(this.url.getPath());
/*      */       }
/*  602 */       if (this.url.getQuery() != null) {
/*  603 */         stringBuffer.append('?');
/*  604 */         stringBuffer.append(this.url.getQuery());
/*      */       } 
/*      */       
/*  607 */       str = stringBuffer.toString();
/*      */     } else {
/*  609 */       str = this.url.getFile();
/*      */       
/*  611 */       if (str == null || str.length() == 0) {
/*  612 */         str = "/";
/*  613 */       } else if (str.charAt(0) == '?') {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  621 */         str = "/" + str;
/*      */       } 
/*      */     } 
/*      */     
/*  625 */     if (str.indexOf('\n') == -1) {
/*  626 */       return str;
/*      */     }
/*  628 */     throw new MalformedURLException("Illegal character in URL");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void writeRequests(MessageHeader paramMessageHeader) {
/*  636 */     this.requests = paramMessageHeader;
/*  637 */     this.requests.print(this.serverOutput);
/*  638 */     this.serverOutput.flush();
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeRequests(MessageHeader paramMessageHeader, PosterOutputStream paramPosterOutputStream) throws IOException {
/*  643 */     this.requests = paramMessageHeader;
/*  644 */     this.requests.print(this.serverOutput);
/*  645 */     this.poster = paramPosterOutputStream;
/*  646 */     if (this.poster != null)
/*  647 */       this.poster.writeTo(this.serverOutput); 
/*  648 */     this.serverOutput.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeRequests(MessageHeader paramMessageHeader, PosterOutputStream paramPosterOutputStream, boolean paramBoolean) throws IOException {
/*  654 */     this.streaming = paramBoolean;
/*  655 */     writeRequests(paramMessageHeader, paramPosterOutputStream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean parseHTTP(MessageHeader paramMessageHeader, ProgressSource paramProgressSource, HttpURLConnection paramHttpURLConnection) throws IOException {
/*      */     try {
/*  673 */       this.serverInput = this.serverSocket.getInputStream();
/*  674 */       if (this.capture != null) {
/*  675 */         this.serverInput = new HttpCaptureInputStream(this.serverInput, this.capture);
/*      */       }
/*  677 */       this.serverInput = new BufferedInputStream(this.serverInput);
/*  678 */       return parseHTTPHeader(paramMessageHeader, paramProgressSource, paramHttpURLConnection);
/*  679 */     } catch (SocketTimeoutException socketTimeoutException) {
/*      */ 
/*      */       
/*  682 */       if (this.ignoreContinue) {
/*  683 */         closeServer();
/*      */       }
/*  685 */       throw socketTimeoutException;
/*  686 */     } catch (IOException iOException) {
/*  687 */       closeServer();
/*  688 */       this.cachedHttpClient = false;
/*  689 */       if (!this.failedOnce && this.requests != null) {
/*  690 */         this.failedOnce = true;
/*  691 */         if (!getRequestMethod().equals("CONNECT") && !this.streaming && (
/*      */           
/*  693 */           !paramHttpURLConnection.getRequestMethod().equals("POST") || retryPostProp)) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  698 */           openServer();
/*  699 */           if (needsTunneling()) {
/*  700 */             MessageHeader messageHeader = this.requests;
/*  701 */             paramHttpURLConnection.doTunneling();
/*  702 */             this.requests = messageHeader;
/*      */           } 
/*  704 */           afterConnect();
/*  705 */           writeRequests(this.requests, this.poster);
/*  706 */           return parseHTTP(paramMessageHeader, paramProgressSource, paramHttpURLConnection);
/*      */         } 
/*      */       } 
/*  709 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean parseHTTPHeader(MessageHeader paramMessageHeader, ProgressSource paramProgressSource, HttpURLConnection paramHttpURLConnection) throws IOException {
/*  725 */     this.keepAliveConnections = -1;
/*  726 */     this.keepAliveTimeout = 0;
/*      */     
/*  728 */     boolean bool = false;
/*  729 */     byte[] arrayOfByte = new byte[8];
/*      */     
/*      */     try {
/*  732 */       int j = 0;
/*  733 */       this.serverInput.mark(10);
/*  734 */       while (j < 8) {
/*  735 */         int k = this.serverInput.read(arrayOfByte, j, 8 - j);
/*  736 */         if (k < 0) {
/*      */           break;
/*      */         }
/*  739 */         j += k;
/*      */       } 
/*  741 */       String str1 = null;
/*  742 */       String str2 = null;
/*  743 */       bool = (arrayOfByte[0] == 72 && arrayOfByte[1] == 84 && arrayOfByte[2] == 84 && arrayOfByte[3] == 80 && arrayOfByte[4] == 47 && arrayOfByte[5] == 49 && arrayOfByte[6] == 46) ? true : false;
/*      */ 
/*      */       
/*  746 */       this.serverInput.reset();
/*  747 */       if (bool)
/*  748 */       { paramMessageHeader.parseHeader(this.serverInput);
/*      */ 
/*      */ 
/*      */         
/*  752 */         CookieHandler cookieHandler = paramHttpURLConnection.getCookieHandler();
/*  753 */         if (cookieHandler != null) {
/*  754 */           URI uRI = ParseUtil.toURI(this.url);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  759 */           if (uRI != null) {
/*  760 */             cookieHandler.put(uRI, paramMessageHeader.getHeaders());
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  769 */         if (this.usingProxy) {
/*  770 */           str1 = paramMessageHeader.findValue("Proxy-Connection");
/*  771 */           str2 = paramMessageHeader.findValue("Proxy-Authenticate");
/*      */         } 
/*  773 */         if (str1 == null) {
/*  774 */           str1 = paramMessageHeader.findValue("Connection");
/*  775 */           str2 = paramMessageHeader.findValue("WWW-Authenticate");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  786 */         int k = !this.disableKeepAlive ? 1 : 0;
/*  787 */         if (k && (!cacheNTLMProp || !cacheSPNEGOProp) && str2 != null) {
/*      */           
/*  789 */           str2 = str2.toLowerCase(Locale.US);
/*  790 */           if (!cacheNTLMProp) {
/*  791 */             k &= !str2.startsWith("ntlm ") ? 1 : 0;
/*      */           }
/*  793 */           if (!cacheSPNEGOProp) {
/*  794 */             k &= !str2.startsWith("negotiate ") ? 1 : 0;
/*  795 */             k &= !str2.startsWith("kerberos ") ? 1 : 0;
/*      */           } 
/*      */         } 
/*  798 */         this.disableKeepAlive |= (k == 0) ? 1 : 0;
/*      */         
/*  800 */         if (str1 != null && str1.toLowerCase(Locale.US).equals("keep-alive")) {
/*      */ 
/*      */ 
/*      */           
/*  804 */           if (this.disableKeepAlive) {
/*  805 */             this.keepAliveConnections = 1;
/*      */           } else {
/*      */             
/*  808 */             HeaderParser headerParser = new HeaderParser(paramMessageHeader.findValue("Keep-Alive"));
/*      */             
/*  810 */             this.keepAliveConnections = headerParser.findInt("max", this.usingProxy ? 50 : 5);
/*  811 */             this.keepAliveTimeout = headerParser.findInt("timeout", this.usingProxy ? 60 : 5);
/*      */           } 
/*  813 */         } else if (arrayOfByte[7] != 48) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  818 */           if (str1 != null || this.disableKeepAlive) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  824 */             this.keepAliveConnections = 1;
/*      */           } else {
/*  826 */             this.keepAliveConnections = 5;
/*      */           } 
/*      */         }  }
/*  829 */       else { if (j != 8) {
/*  830 */           if (!this.failedOnce && this.requests != null) {
/*  831 */             this.failedOnce = true;
/*  832 */             if (!getRequestMethod().equals("CONNECT") && !this.streaming && (
/*      */               
/*  834 */               !paramHttpURLConnection.getRequestMethod().equals("POST") || retryPostProp)) {
/*      */ 
/*      */ 
/*      */               
/*  838 */               closeServer();
/*  839 */               this.cachedHttpClient = false;
/*  840 */               openServer();
/*  841 */               if (needsTunneling()) {
/*  842 */                 MessageHeader messageHeader = this.requests;
/*  843 */                 paramHttpURLConnection.doTunneling();
/*  844 */                 this.requests = messageHeader;
/*      */               } 
/*  846 */               afterConnect();
/*  847 */               writeRequests(this.requests, this.poster);
/*  848 */               return parseHTTP(paramMessageHeader, paramProgressSource, paramHttpURLConnection);
/*      */             } 
/*      */           } 
/*  851 */           throw new SocketException("Unexpected end of file from server");
/*      */         } 
/*      */         
/*  854 */         paramMessageHeader.set("Content-type", "unknown/unknown"); }
/*      */     
/*  856 */     } catch (IOException iOException) {
/*  857 */       throw iOException;
/*      */     } 
/*      */     
/*  860 */     int i = -1;
/*      */     
/*      */     try {
/*  863 */       String str1 = paramMessageHeader.getValue(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  869 */       int j = str1.indexOf(' ');
/*  870 */       while (str1.charAt(j) == ' ')
/*  871 */         j++; 
/*  872 */       i = Integer.parseInt(str1.substring(j, j + 3));
/*  873 */     } catch (Exception exception) {}
/*      */     
/*  875 */     if (i == 100 && this.ignoreContinue) {
/*  876 */       paramMessageHeader.reset();
/*  877 */       return parseHTTPHeader(paramMessageHeader, paramProgressSource, paramHttpURLConnection);
/*      */     } 
/*      */     
/*  880 */     long l = -1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  888 */     String str = paramMessageHeader.findValue("Transfer-Encoding");
/*  889 */     if (str != null && str.equalsIgnoreCase("chunked")) {
/*  890 */       this.serverInput = new ChunkedInputStream(this.serverInput, this, paramMessageHeader);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  896 */       if (this.keepAliveConnections <= 1) {
/*  897 */         this.keepAliveConnections = 1;
/*  898 */         this.keepingAlive = false;
/*      */       } else {
/*  900 */         this.keepingAlive = !this.disableKeepAlive;
/*      */       } 
/*  902 */       this.failedOnce = false;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  912 */       String str1 = paramMessageHeader.findValue("content-length");
/*  913 */       if (str1 != null) {
/*      */         try {
/*  915 */           l = Long.parseLong(str1);
/*  916 */         } catch (NumberFormatException numberFormatException) {
/*  917 */           l = -1L;
/*      */         } 
/*      */       }
/*  920 */       String str2 = this.requests.getKey(0);
/*      */       
/*  922 */       if ((str2 != null && str2
/*  923 */         .startsWith("HEAD")) || i == 304 || i == 204)
/*      */       {
/*      */         
/*  926 */         l = 0L;
/*      */       }
/*      */       
/*  929 */       if (this.keepAliveConnections > 1 && (l >= 0L || i == 304 || i == 204)) {
/*      */ 
/*      */ 
/*      */         
/*  933 */         this.keepingAlive = !this.disableKeepAlive;
/*  934 */         this.failedOnce = false;
/*  935 */       } else if (this.keepingAlive) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  940 */         this.keepingAlive = false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  946 */     if (l > 0L) {
/*      */ 
/*      */ 
/*      */       
/*  950 */       if (paramProgressSource != null)
/*      */       {
/*  952 */         paramProgressSource.setContentType(paramMessageHeader.findValue("content-type"));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  958 */       boolean bool1 = (isKeepingAlive() || this.disableKeepAlive) ? true : false;
/*  959 */       if (bool1) {
/*      */         
/*  961 */         logFinest("KeepAlive stream used: " + this.url);
/*  962 */         this.serverInput = new KeepAliveStream(this.serverInput, paramProgressSource, l, this);
/*  963 */         this.failedOnce = false;
/*      */       } else {
/*      */         
/*  966 */         this.serverInput = new MeteredStream(this.serverInput, paramProgressSource, l);
/*      */       }
/*      */     
/*  969 */     } else if (l == -1L) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  974 */       if (paramProgressSource != null)
/*      */       {
/*      */         
/*  977 */         paramProgressSource.setContentType(paramMessageHeader.findValue("content-type"));
/*      */ 
/*      */ 
/*      */         
/*  981 */         this.serverInput = new MeteredStream(this.serverInput, paramProgressSource, l);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  991 */     else if (paramProgressSource != null) {
/*  992 */       paramProgressSource.finishTracking();
/*      */     } 
/*      */     
/*  995 */     return bool;
/*      */   }
/*      */   
/*      */   public synchronized InputStream getInputStream() {
/*  999 */     return this.serverInput;
/*      */   }
/*      */   
/*      */   public OutputStream getOutputStream() {
/* 1003 */     return this.serverOutput;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1008 */     return getClass().getName() + "(" + this.url + ")";
/*      */   }
/*      */   
/*      */   public final boolean isKeepingAlive() {
/* 1012 */     return (getHttpKeepAliveSet() && this.keepingAlive);
/*      */   }
/*      */   
/*      */   public void setCacheRequest(CacheRequest paramCacheRequest) {
/* 1016 */     this.cacheRequest = paramCacheRequest;
/*      */   }
/*      */   
/*      */   CacheRequest getCacheRequest() {
/* 1020 */     return this.cacheRequest;
/*      */   }
/*      */   
/*      */   String getRequestMethod() {
/* 1024 */     if (this.requests != null) {
/* 1025 */       String str = this.requests.getKey(0);
/* 1026 */       if (str != null) {
/* 1027 */         return str.split("\\s+")[0];
/*      */       }
/*      */     } 
/* 1030 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalize() throws Throwable {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDoNotRetry(boolean paramBoolean) {
/* 1041 */     this.failedOnce = paramBoolean;
/*      */   }
/*      */   
/*      */   public void setIgnoreContinue(boolean paramBoolean) {
/* 1045 */     this.ignoreContinue = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeServer() {
/*      */     try {
/* 1052 */       this.keepingAlive = false;
/* 1053 */       this.serverSocket.close();
/* 1054 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProxyHostUsed() {
/* 1062 */     if (!this.usingProxy) {
/* 1063 */       return null;
/*      */     }
/* 1065 */     return ((InetSocketAddress)this.proxy.address()).getHostString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getProxyPortUsed() {
/* 1074 */     if (this.usingProxy)
/* 1075 */       return ((InetSocketAddress)this.proxy.address()).getPort(); 
/* 1076 */     return -1;
/*      */   }
/*      */   
/*      */   protected HttpClient() {}
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/http/HttpClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */