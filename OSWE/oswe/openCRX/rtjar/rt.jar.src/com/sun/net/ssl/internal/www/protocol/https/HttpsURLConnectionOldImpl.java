/*     */ package com.sun.net.ssl.internal.www.protocol.https;
/*     */ 
/*     */ import com.sun.net.ssl.HttpsURLConnection;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.ProtocolException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.security.Permission;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.net.ssl.SSLPeerUnverifiedException;
/*     */ import javax.security.cert.X509Certificate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpsURLConnectionOldImpl
/*     */   extends HttpsURLConnection
/*     */ {
/*     */   private DelegateHttpsURLConnection delegate;
/*     */   
/*     */   HttpsURLConnectionOldImpl(URL paramURL, Handler paramHandler) throws IOException {
/*  76 */     this(paramURL, null, paramHandler);
/*     */   }
/*     */   
/*     */   static URL checkURL(URL paramURL) throws IOException {
/*  80 */     if (paramURL != null && 
/*  81 */       paramURL.toExternalForm().indexOf('\n') > -1) {
/*  82 */       throw new MalformedURLException("Illegal character in URL");
/*     */     }
/*     */     
/*  85 */     return paramURL;
/*     */   }
/*     */ 
/*     */   
/*     */   HttpsURLConnectionOldImpl(URL paramURL, Proxy paramProxy, Handler paramHandler) throws IOException {
/*  90 */     super(checkURL(paramURL));
/*  91 */     this.delegate = new DelegateHttpsURLConnection(this.url, paramProxy, paramHandler, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setNewClient(URL paramURL) throws IOException {
/* 101 */     this.delegate.setNewClient(paramURL, false);
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
/* 113 */     this.delegate.setNewClient(paramURL, paramBoolean);
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
/* 127 */     this.delegate.setProxiedClient(paramURL, paramString, paramInt);
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
/* 143 */     this.delegate.setProxiedClient(paramURL, paramString, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect() throws IOException {
/* 151 */     this.delegate.connect();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isConnected() {
/* 160 */     return this.delegate.isConnected();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setConnected(boolean paramBoolean) {
/* 169 */     this.delegate.setConnected(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCipherSuite() {
/* 176 */     return this.delegate.getCipherSuite();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Certificate[] getLocalCertificates() {
/* 185 */     return this.delegate.getLocalCertificates();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
/* 195 */     return this.delegate.getServerCertificates();
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
/* 208 */       return this.delegate.getServerCertificateChain();
/* 209 */     } catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
/*     */ 
/*     */ 
/*     */       
/* 213 */       return null;
/*     */     } 
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
/* 229 */     return this.delegate.getOutputStream();
/*     */   }
/*     */   
/*     */   public synchronized InputStream getInputStream() throws IOException {
/* 233 */     return this.delegate.getInputStream();
/*     */   }
/*     */   
/*     */   public InputStream getErrorStream() {
/* 237 */     return this.delegate.getErrorStream();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect() {
/* 244 */     this.delegate.disconnect();
/*     */   }
/*     */   
/*     */   public boolean usingProxy() {
/* 248 */     return this.delegate.usingProxy();
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
/* 262 */     return this.delegate.getHeaderFields();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeaderField(String paramString) {
/* 270 */     return this.delegate.getHeaderField(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeaderField(int paramInt) {
/* 278 */     return this.delegate.getHeaderField(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHeaderFieldKey(int paramInt) {
/* 286 */     return this.delegate.getHeaderFieldKey(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRequestProperty(String paramString1, String paramString2) {
/* 295 */     this.delegate.setRequestProperty(paramString1, paramString2);
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
/* 310 */     this.delegate.addRequestProperty(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResponseCode() throws IOException {
/* 317 */     return this.delegate.getResponseCode();
/*     */   }
/*     */   
/*     */   public String getRequestProperty(String paramString) {
/* 321 */     return this.delegate.getRequestProperty(paramString);
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
/* 337 */     return this.delegate.getRequestProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInstanceFollowRedirects(boolean paramBoolean) {
/* 345 */     this.delegate.setInstanceFollowRedirects(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getInstanceFollowRedirects() {
/* 349 */     return this.delegate.getInstanceFollowRedirects();
/*     */   }
/*     */   
/*     */   public void setRequestMethod(String paramString) throws ProtocolException {
/* 353 */     this.delegate.setRequestMethod(paramString);
/*     */   }
/*     */   
/*     */   public String getRequestMethod() {
/* 357 */     return this.delegate.getRequestMethod();
/*     */   }
/*     */   
/*     */   public String getResponseMessage() throws IOException {
/* 361 */     return this.delegate.getResponseMessage();
/*     */   }
/*     */   
/*     */   public long getHeaderFieldDate(String paramString, long paramLong) {
/* 365 */     return this.delegate.getHeaderFieldDate(paramString, paramLong);
/*     */   }
/*     */   
/*     */   public Permission getPermission() throws IOException {
/* 369 */     return this.delegate.getPermission();
/*     */   }
/*     */   
/*     */   public URL getURL() {
/* 373 */     return this.delegate.getURL();
/*     */   }
/*     */   
/*     */   public int getContentLength() {
/* 377 */     return this.delegate.getContentLength();
/*     */   }
/*     */   
/*     */   public long getContentLengthLong() {
/* 381 */     return this.delegate.getContentLengthLong();
/*     */   }
/*     */   
/*     */   public String getContentType() {
/* 385 */     return this.delegate.getContentType();
/*     */   }
/*     */   
/*     */   public String getContentEncoding() {
/* 389 */     return this.delegate.getContentEncoding();
/*     */   }
/*     */   
/*     */   public long getExpiration() {
/* 393 */     return this.delegate.getExpiration();
/*     */   }
/*     */   
/*     */   public long getDate() {
/* 397 */     return this.delegate.getDate();
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 401 */     return this.delegate.getLastModified();
/*     */   }
/*     */   
/*     */   public int getHeaderFieldInt(String paramString, int paramInt) {
/* 405 */     return this.delegate.getHeaderFieldInt(paramString, paramInt);
/*     */   }
/*     */   
/*     */   public long getHeaderFieldLong(String paramString, long paramLong) {
/* 409 */     return this.delegate.getHeaderFieldLong(paramString, paramLong);
/*     */   }
/*     */   
/*     */   public Object getContent() throws IOException {
/* 413 */     return this.delegate.getContent();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getContent(Class[] paramArrayOfClass) throws IOException {
/* 418 */     return this.delegate.getContent(paramArrayOfClass);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 422 */     return this.delegate.toString();
/*     */   }
/*     */   
/*     */   public void setDoInput(boolean paramBoolean) {
/* 426 */     this.delegate.setDoInput(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getDoInput() {
/* 430 */     return this.delegate.getDoInput();
/*     */   }
/*     */   
/*     */   public void setDoOutput(boolean paramBoolean) {
/* 434 */     this.delegate.setDoOutput(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getDoOutput() {
/* 438 */     return this.delegate.getDoOutput();
/*     */   }
/*     */   
/*     */   public void setAllowUserInteraction(boolean paramBoolean) {
/* 442 */     this.delegate.setAllowUserInteraction(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getAllowUserInteraction() {
/* 446 */     return this.delegate.getAllowUserInteraction();
/*     */   }
/*     */   
/*     */   public void setUseCaches(boolean paramBoolean) {
/* 450 */     this.delegate.setUseCaches(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getUseCaches() {
/* 454 */     return this.delegate.getUseCaches();
/*     */   }
/*     */   
/*     */   public void setIfModifiedSince(long paramLong) {
/* 458 */     this.delegate.setIfModifiedSince(paramLong);
/*     */   }
/*     */   
/*     */   public long getIfModifiedSince() {
/* 462 */     return this.delegate.getIfModifiedSince();
/*     */   }
/*     */   
/*     */   public boolean getDefaultUseCaches() {
/* 466 */     return this.delegate.getDefaultUseCaches();
/*     */   }
/*     */   
/*     */   public void setDefaultUseCaches(boolean paramBoolean) {
/* 470 */     this.delegate.setDefaultUseCaches(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 479 */     this.delegate.dispose();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 483 */     return this.delegate.equals(paramObject);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 487 */     return this.delegate.hashCode();
/*     */   }
/*     */   
/*     */   public void setConnectTimeout(int paramInt) {
/* 491 */     this.delegate.setConnectTimeout(paramInt);
/*     */   }
/*     */   
/*     */   public int getConnectTimeout() {
/* 495 */     return this.delegate.getConnectTimeout();
/*     */   }
/*     */   
/*     */   public void setReadTimeout(int paramInt) {
/* 499 */     this.delegate.setReadTimeout(paramInt);
/*     */   }
/*     */   
/*     */   public int getReadTimeout() {
/* 503 */     return this.delegate.getReadTimeout();
/*     */   }
/*     */   
/*     */   public void setFixedLengthStreamingMode(int paramInt) {
/* 507 */     this.delegate.setFixedLengthStreamingMode(paramInt);
/*     */   }
/*     */   
/*     */   public void setFixedLengthStreamingMode(long paramLong) {
/* 511 */     this.delegate.setFixedLengthStreamingMode(paramLong);
/*     */   }
/*     */   
/*     */   public void setChunkedStreamingMode(int paramInt) {
/* 515 */     this.delegate.setChunkedStreamingMode(paramInt);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/ssl/internal/www/protocol/https/HttpsURLConnectionOldImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */