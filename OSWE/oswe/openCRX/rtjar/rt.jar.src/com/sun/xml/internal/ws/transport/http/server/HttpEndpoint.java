/*     */ package com.sun.xml.internal.ws.transport.http.server;
/*     */ 
/*     */ import com.sun.net.httpserver.HttpContext;
/*     */ import com.sun.xml.internal.ws.api.server.HttpEndpoint;
/*     */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*     */ import com.sun.xml.internal.ws.server.ServerRtException;
/*     */ import com.sun.xml.internal.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.internal.ws.transport.http.HttpAdapterList;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.xml.ws.spi.http.HttpContext;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HttpEndpoint
/*     */   extends HttpEndpoint
/*     */ {
/*     */   private String address;
/*     */   private HttpContext httpContext;
/*     */   private final HttpAdapter adapter;
/*     */   private final Executor executor;
/*     */   
/*     */   public HttpEndpoint(Executor executor, HttpAdapter adapter) {
/*  58 */     this.executor = executor;
/*  59 */     this.adapter = adapter;
/*     */   }
/*     */   
/*     */   public void publish(String address) {
/*  63 */     this.address = address;
/*  64 */     this.httpContext = ServerMgr.getInstance().createContext(address);
/*  65 */     publish(this.httpContext);
/*     */   }
/*     */   
/*     */   public void publish(Object serverContext) {
/*  69 */     if (serverContext instanceof HttpContext) {
/*  70 */       setHandler((HttpContext)serverContext);
/*     */       return;
/*     */     } 
/*  73 */     if (serverContext instanceof HttpContext) {
/*  74 */       this.httpContext = (HttpContext)serverContext;
/*  75 */       setHandler(this.httpContext);
/*     */       return;
/*     */     } 
/*  78 */     throw new ServerRtException(ServerMessages.NOT_KNOW_HTTP_CONTEXT_TYPE(serverContext
/*  79 */           .getClass(), HttpContext.class, HttpContext.class), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   HttpAdapterList getAdapterOwner() {
/*  84 */     return this.adapter.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getEPRAddress() {
/*  92 */     if (this.address == null)
/*  93 */       return this.httpContext.getServer().getAddress().toString(); 
/*     */     try {
/*  95 */       URL u = new URL(this.address);
/*  96 */       if (u.getPort() == 0) {
/*  97 */         return (new URL(u.getProtocol(), u.getHost(), this.httpContext
/*  98 */             .getServer().getAddress().getPort(), u.getFile())).toString();
/*     */       }
/* 100 */     } catch (MalformedURLException malformedURLException) {}
/* 101 */     return this.address;
/*     */   }
/*     */   
/*     */   public void stop() {
/* 105 */     if (this.httpContext != null) {
/* 106 */       if (this.address == null) {
/*     */ 
/*     */         
/* 109 */         this.httpContext.getServer().removeContext(this.httpContext);
/*     */       } else {
/*     */         
/* 112 */         ServerMgr.getInstance().removeContext(this.httpContext);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 117 */     this.adapter.getEndpoint().dispose();
/*     */   }
/*     */   
/*     */   private void setHandler(HttpContext context) {
/* 121 */     context.setHandler(new WSHttpHandler(this.adapter, this.executor));
/*     */   }
/*     */   
/*     */   private void setHandler(HttpContext context) {
/* 125 */     context.setHandler(new PortableHttpHandler(this.adapter, this.executor));
/*     */   }
/*     */   
/*     */   public <T extends javax.xml.ws.EndpointReference> T getEndpointReference(Class<T> clazz, Element... referenceParameters) {
/* 129 */     String eprAddress = getEPRAddress();
/* 130 */     return clazz.cast(this.adapter.getEndpoint().getEndpointReference(clazz, eprAddress, eprAddress + "?wsdl", referenceParameters));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/server/HttpEndpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */