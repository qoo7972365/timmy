/*     */ package sun.net.www.protocol.https;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.ProtocolException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.security.Permission;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLPeerUnverifiedException;
/*     */ import javax.security.cert.X509Certificate;
/*     */ import sun.net.util.IPAddressUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpsURLConnectionImpl
/*     */   extends HttpsURLConnection
/*     */ {
/*     */   protected DelegateHttpsURLConnection delegate;
/*     */   
/*     */   HttpsURLConnectionImpl(URL paramURL, Handler paramHandler) throws IOException {
/*  81 */     this(paramURL, null, paramHandler);
/*     */   }
/*     */   
/*     */   static URL checkURL(URL paramURL) throws IOException {
/*  85 */     if (paramURL != null && 
/*  86 */       paramURL.toExternalForm().indexOf('\n') > -1) {
/*  87 */       throw new MalformedURLException("Illegal character in URL");
/*     */     }
/*     */     
/*  90 */     String str = IPAddressUtil.checkAuthority(paramURL);
/*  91 */     if (str != null) {
/*  92 */       throw new MalformedURLException(str);
/*     */     }
/*  94 */     return paramURL;
/*     */   }
/*     */ 
/*     */   
/*     */   HttpsURLConnectionImpl(URL paramURL, Proxy paramProxy, Handler paramHandler) throws IOException {
/*  99 */     super(checkURL(paramURL));
/* 100 */     this.delegate = new DelegateHttpsURLConnection(this.url, paramProxy, paramHandler, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpsURLConnectionImpl(URL paramURL) throws IOException {
/* 107 */     super(paramURL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setNewClient(URL paramURL) throws IOException {
/* 117 */     this.delegate.setNewClient(paramURL, false);
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
/*     */   protected void setNewClient(URL paramURL, boolean paramBoolean) throws IOException {
/* 129 */     this.delegate.setNewClient(paramURL, paramBoolean);
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
/*     */   protected void setProxiedClient(URL paramURL, String paramString, int paramInt) throws IOException {
/* 143 */     this.delegate.setProxiedClient(paramURL, paramString, paramInt);
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
/*     */   protected void setProxiedClient(URL paramURL, String paramString, int paramInt, boolean paramBoolean) throws IOException {
/* 159 */     this.delegate.setProxiedClient(paramURL, paramString, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect() throws IOException {
/* 167 */     this.delegate.connect();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isConnected() {
/* 176 */     return this.delegate.isConnected();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setConnected(boolean paramBoolean) {
/* 185 */     this.delegate.setConnected(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCipherSuite() {
/* 192 */     return this.delegate.getCipherSuite();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Certificate[] getLocalCertificates() {
/* 201 */     return this.delegate.getLocalCertificates();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
/* 211 */     return this.delegate.getServerCertificates();
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
/*     */   public X509Certificate[] getServerCertificateChain() {
/*     */     try {
/* 224 */       return this.delegate.getServerCertificateChain();
/* 225 */     } catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
/*     */ 
/*     */ 
/*     */       
/* 229 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
/* 240 */     return this.delegate.getPeerPrincipal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Principal getLocalPrincipal() {
/* 249 */     return this.delegate.getLocalPrincipal();
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
/*     */   public synchronized OutputStream getOutputStream() throws IOException {
/* 264 */     return this.delegate.getOutputStream();
/*     */   }
/*     */   
/*     */   public synchronized InputStream getInputStream() throws IOException {
/* 268 */     return this.delegate.getInputStream();
/*     */   }
/*     */   
/*     */   public InputStream getErrorStream() {
/* 272 */     return this.delegate.getErrorStream();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect() {
/* 279 */     this.delegate.disconnect();
/*     */   }
/*     */   
/*     */   public boolean usingProxy() {
/* 283 */     return this.delegate.usingProxy();
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
/*     */   public Map<String, List<String>> getHeaderFields() {
/* 297 */     return this.delegate.getHeaderFields();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeaderField(String paramString) {
/* 305 */     return this.delegate.getHeaderField(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeaderField(int paramInt) {
/* 313 */     return this.delegate.getHeaderField(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeaderFieldKey(int paramInt) {
/* 321 */     return this.delegate.getHeaderFieldKey(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRequestProperty(String paramString1, String paramString2) {
/* 330 */     this.delegate.setRequestProperty(paramString1, paramString2);
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
/*     */   public void addRequestProperty(String paramString1, String paramString2) {
/* 345 */     this.delegate.addRequestProperty(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResponseCode() throws IOException {
/* 352 */     return this.delegate.getResponseCode();
/*     */   }
/*     */   
/*     */   public String getRequestProperty(String paramString) {
/* 356 */     return this.delegate.getRequestProperty(paramString);
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
/*     */   public Map<String, List<String>> getRequestProperties() {
/* 372 */     return this.delegate.getRequestProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInstanceFollowRedirects(boolean paramBoolean) {
/* 380 */     this.delegate.setInstanceFollowRedirects(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getInstanceFollowRedirects() {
/* 384 */     return this.delegate.getInstanceFollowRedirects();
/*     */   }
/*     */   
/*     */   public void setRequestMethod(String paramString) throws ProtocolException {
/* 388 */     this.delegate.setRequestMethod(paramString);
/*     */   }
/*     */   
/*     */   public String getRequestMethod() {
/* 392 */     return this.delegate.getRequestMethod();
/*     */   }
/*     */   
/*     */   public String getResponseMessage() throws IOException {
/* 396 */     return this.delegate.getResponseMessage();
/*     */   }
/*     */   
/*     */   public long getHeaderFieldDate(String paramString, long paramLong) {
/* 400 */     return this.delegate.getHeaderFieldDate(paramString, paramLong);
/*     */   }
/*     */   
/*     */   public Permission getPermission() throws IOException {
/* 404 */     return this.delegate.getPermission();
/*     */   }
/*     */   
/*     */   public URL getURL() {
/* 408 */     return this.delegate.getURL();
/*     */   }
/*     */   
/*     */   public int getContentLength() {
/* 412 */     return this.delegate.getContentLength();
/*     */   }
/*     */   
/*     */   public long getContentLengthLong() {
/* 416 */     return this.delegate.getContentLengthLong();
/*     */   }
/*     */   
/*     */   public String getContentType() {
/* 420 */     return this.delegate.getContentType();
/*     */   }
/*     */   
/*     */   public String getContentEncoding() {
/* 424 */     return this.delegate.getContentEncoding();
/*     */   }
/*     */   
/*     */   public long getExpiration() {
/* 428 */     return this.delegate.getExpiration();
/*     */   }
/*     */   
/*     */   public long getDate() {
/* 432 */     return this.delegate.getDate();
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 436 */     return this.delegate.getLastModified();
/*     */   }
/*     */   
/*     */   public int getHeaderFieldInt(String paramString, int paramInt) {
/* 440 */     return this.delegate.getHeaderFieldInt(paramString, paramInt);
/*     */   }
/*     */   
/*     */   public long getHeaderFieldLong(String paramString, long paramLong) {
/* 444 */     return this.delegate.getHeaderFieldLong(paramString, paramLong);
/*     */   }
/*     */   
/*     */   public Object getContent() throws IOException {
/* 448 */     return this.delegate.getContent();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getContent(Class[] paramArrayOfClass) throws IOException {
/* 453 */     return this.delegate.getContent(paramArrayOfClass);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 457 */     return this.delegate.toString();
/*     */   }
/*     */   
/*     */   public void setDoInput(boolean paramBoolean) {
/* 461 */     this.delegate.setDoInput(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getDoInput() {
/* 465 */     return this.delegate.getDoInput();
/*     */   }
/*     */   
/*     */   public void setDoOutput(boolean paramBoolean) {
/* 469 */     this.delegate.setDoOutput(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getDoOutput() {
/* 473 */     return this.delegate.getDoOutput();
/*     */   }
/*     */   
/*     */   public void setAllowUserInteraction(boolean paramBoolean) {
/* 477 */     this.delegate.setAllowUserInteraction(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getAllowUserInteraction() {
/* 481 */     return this.delegate.getAllowUserInteraction();
/*     */   }
/*     */   
/*     */   public void setUseCaches(boolean paramBoolean) {
/* 485 */     this.delegate.setUseCaches(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getUseCaches() {
/* 489 */     return this.delegate.getUseCaches();
/*     */   }
/*     */   
/*     */   public void setIfModifiedSince(long paramLong) {
/* 493 */     this.delegate.setIfModifiedSince(paramLong);
/*     */   }
/*     */   
/*     */   public long getIfModifiedSince() {
/* 497 */     return this.delegate.getIfModifiedSince();
/*     */   }
/*     */   
/*     */   public boolean getDefaultUseCaches() {
/* 501 */     return this.delegate.getDefaultUseCaches();
/*     */   }
/*     */   
/*     */   public void setDefaultUseCaches(boolean paramBoolean) {
/* 505 */     this.delegate.setDefaultUseCaches(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 514 */     this.delegate.dispose();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 518 */     return this.delegate.equals(paramObject);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 522 */     return this.delegate.hashCode();
/*     */   }
/*     */   
/*     */   public void setConnectTimeout(int paramInt) {
/* 526 */     this.delegate.setConnectTimeout(paramInt);
/*     */   }
/*     */   
/*     */   public int getConnectTimeout() {
/* 530 */     return this.delegate.getConnectTimeout();
/*     */   }
/*     */   
/*     */   public void setReadTimeout(int paramInt) {
/* 534 */     this.delegate.setReadTimeout(paramInt);
/*     */   }
/*     */   
/*     */   public int getReadTimeout() {
/* 538 */     return this.delegate.getReadTimeout();
/*     */   }
/*     */   
/*     */   public void setFixedLengthStreamingMode(int paramInt) {
/* 542 */     this.delegate.setFixedLengthStreamingMode(paramInt);
/*     */   }
/*     */   
/*     */   public void setFixedLengthStreamingMode(long paramLong) {
/* 546 */     this.delegate.setFixedLengthStreamingMode(paramLong);
/*     */   }
/*     */   
/*     */   public void setChunkedStreamingMode(int paramInt) {
/* 550 */     this.delegate.setChunkedStreamingMode(paramInt);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/https/HttpsURLConnectionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */