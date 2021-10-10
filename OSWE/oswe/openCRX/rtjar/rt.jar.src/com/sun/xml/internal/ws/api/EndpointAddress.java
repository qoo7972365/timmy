/*     */ package com.sun.xml.internal.ws.api;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.Proxy;
/*     */ import java.net.ProxySelector;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EndpointAddress
/*     */ {
/*     */   @Nullable
/*     */   private URL url;
/*     */   private final URI uri;
/*     */   private final String stringForm;
/*     */   private volatile boolean dontUseProxyMethod;
/*     */   private Proxy proxy;
/*     */   
/*     */   public EndpointAddress(URI uri) {
/* 100 */     this.uri = uri;
/* 101 */     this.stringForm = uri.toString();
/*     */     try {
/* 103 */       initURL();
/* 104 */       this.proxy = chooseProxy();
/* 105 */     } catch (MalformedURLException malformedURLException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndpointAddress(String url) throws URISyntaxException {
/* 115 */     this.uri = new URI(url);
/* 116 */     this.stringForm = url;
/*     */     try {
/* 118 */       initURL();
/* 119 */       this.proxy = chooseProxy();
/* 120 */     } catch (MalformedURLException malformedURLException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initURL() throws MalformedURLException {
/* 127 */     String scheme = this.uri.getScheme();
/*     */     
/* 129 */     if (scheme == null) {
/* 130 */       this.url = new URL(this.uri.toString());
/*     */       return;
/*     */     } 
/* 133 */     scheme = scheme.toLowerCase();
/* 134 */     if ("http".equals(scheme) || "https".equals(scheme)) {
/* 135 */       this.url = new URL(this.uri.toASCIIString());
/*     */     } else {
/* 137 */       this.url = this.uri.toURL();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EndpointAddress create(String url) {
/*     */     try {
/* 147 */       return new EndpointAddress(url);
/* 148 */     } catch (URISyntaxException e) {
/* 149 */       throw new WebServiceException("Illegal endpoint address: " + url, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Proxy chooseProxy() {
/* 155 */     ProxySelector sel = AccessController.<ProxySelector>doPrivileged(new PrivilegedAction<ProxySelector>()
/*     */         {
/*     */           public ProxySelector run()
/*     */           {
/* 159 */             return ProxySelector.getDefault();
/*     */           }
/*     */         });
/*     */     
/* 163 */     if (sel == null) {
/* 164 */       return Proxy.NO_PROXY;
/*     */     }
/*     */     
/* 167 */     if (!sel.getClass().getName().equals("sun.net.spi.DefaultProxySelector"))
/*     */     {
/* 169 */       return null;
/*     */     }
/* 171 */     Iterator<Proxy> it = sel.select(this.uri).iterator();
/* 172 */     if (it.hasNext()) {
/* 173 */       return it.next();
/*     */     }
/* 175 */     return Proxy.NO_PROXY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getURL() {
/* 185 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URI getURI() {
/* 195 */     return this.uri;
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
/*     */   public URLConnection openConnection() throws IOException {
/* 212 */     if (this.url == null) {
/* 213 */       throw new WebServiceException("URI=" + this.uri + " doesn't have the corresponding URL");
/*     */     }
/* 215 */     if (this.proxy != null && !this.dontUseProxyMethod) {
/*     */       try {
/* 217 */         return this.url.openConnection(this.proxy);
/* 218 */       } catch (UnsupportedOperationException e) {
/*     */ 
/*     */ 
/*     */         
/* 222 */         this.dontUseProxyMethod = true;
/*     */       } 
/*     */     }
/* 225 */     return this.url.openConnection();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 230 */     return this.stringForm;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/EndpointAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */