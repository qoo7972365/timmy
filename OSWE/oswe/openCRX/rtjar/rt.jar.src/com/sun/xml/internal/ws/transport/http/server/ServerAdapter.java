/*     */ package com.sun.xml.internal.ws.transport.http.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.server.BoundEndpoint;
/*     */ import com.sun.xml.internal.ws.api.server.Module;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.api.server.WebModule;
/*     */ import com.sun.xml.internal.ws.transport.http.HttpAdapter;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public final class ServerAdapter
/*     */   extends HttpAdapter
/*     */   implements BoundEndpoint
/*     */ {
/*     */   final String name;
/*     */   
/*     */   protected ServerAdapter(String name, String urlPattern, WSEndpoint endpoint, ServerAdapterList owner) {
/*  59 */     super(endpoint, owner, urlPattern);
/*  60 */     this.name = name;
/*     */     
/*  62 */     Module module = (Module)endpoint.getContainer().getSPI(Module.class);
/*  63 */     if (module == null) {
/*  64 */       LOGGER.log(Level.WARNING, "Container {0} doesn''t support {1}", new Object[] { endpoint
/*  65 */             .getContainer(), Module.class });
/*     */     } else {
/*  67 */       module.getBoundEndpoints().add(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  76 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public URI getAddress() {
/*  82 */     WebModule webModule = (WebModule)this.endpoint.getContainer().getSPI(WebModule.class);
/*  83 */     if (webModule == null)
/*     */     {
/*  85 */       throw new WebServiceException("Container " + this.endpoint.getContainer() + " doesn't support " + WebModule.class);
/*     */     }
/*  87 */     return getAddress(webModule.getContextPath());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public URI getAddress(String baseAddress) {
/*  92 */     String adrs = baseAddress + getValidPath();
/*     */     try {
/*  94 */       return new URI(adrs);
/*  95 */     } catch (URISyntaxException e) {
/*     */       
/*  97 */       throw new WebServiceException("Unable to compute address for " + this.endpoint, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 102 */     this.endpoint.dispose();
/*     */   }
/*     */   
/*     */   public String getUrlPattern() {
/* 106 */     return this.urlPattern;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 111 */     return super.toString() + "[name=" + this.name + ']';
/*     */   }
/*     */   
/* 114 */   private static final Logger LOGGER = Logger.getLogger(ServerAdapter.class.getName());
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/server/ServerAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */