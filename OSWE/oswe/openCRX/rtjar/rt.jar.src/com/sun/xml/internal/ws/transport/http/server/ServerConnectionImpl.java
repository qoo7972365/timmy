/*     */ package com.sun.xml.internal.ws.transport.http.server;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.internal.api.message.PropertySet.Property;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.net.httpserver.Headers;
/*     */ import com.sun.net.httpserver.HttpExchange;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.api.server.WebServiceContextDelegate;
/*     */ import com.sun.xml.internal.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.internal.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.internal.ws.transport.http.WSHTTPConnection;
/*     */ import com.sun.xml.internal.ws.util.ReadAllStream;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URI;
/*     */ import java.security.Principal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ final class ServerConnectionImpl
/*     */   extends WSHTTPConnection
/*     */   implements WebServiceContextDelegate
/*     */ {
/*     */   private final HttpExchange httpExchange;
/*     */   private int status;
/*     */   private final HttpAdapter adapter;
/*     */   private LWHSInputStream in;
/*     */   private OutputStream out;
/*     */   
/*     */   public ServerConnectionImpl(@NotNull HttpAdapter adapter, @NotNull HttpExchange httpExchange) {
/*  73 */     this.adapter = adapter;
/*  74 */     this.httpExchange = httpExchange;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.headers", "com.sun.xml.internal.ws.api.message.packet.inbound.transport.headers"})
/*     */   @NotNull
/*     */   public Map<String, List<String>> getRequestHeaders() {
/*  80 */     return this.httpExchange.getRequestHeaders();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestHeader(String headerName) {
/*  85 */     return this.httpExchange.getRequestHeaders().getFirst(headerName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponseHeaders(Map<String, List<String>> headers) {
/*  90 */     Headers r = this.httpExchange.getResponseHeaders();
/*  91 */     r.clear();
/*  92 */     for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
/*  93 */       String name = entry.getKey();
/*  94 */       List<String> values = entry.getValue();
/*     */       
/*  96 */       if (!"Content-Length".equalsIgnoreCase(name) && !"Content-Type".equalsIgnoreCase(name)) {
/*  97 */         r.put(name, new ArrayList<>(values));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponseHeader(String key, List<String> value) {
/* 104 */     this.httpExchange.getResponseHeaders().put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getRequestHeaderNames() {
/* 109 */     return this.httpExchange.getRequestHeaders().keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getRequestHeaderValues(String headerName) {
/* 114 */     return this.httpExchange.getRequestHeaders().get(headerName);
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.response.headers", "com.sun.xml.internal.ws.api.message.packet.outbound.transport.headers"})
/*     */   public Map<String, List<String>> getResponseHeaders() {
/* 120 */     return this.httpExchange.getResponseHeaders();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentTypeResponseHeader(@NotNull String value) {
/* 125 */     this.httpExchange.getResponseHeaders().set("Content-Type", value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(int status) {
/* 130 */     this.status = status;
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.response.code"})
/*     */   public int getStatus() {
/* 136 */     return this.status;
/*     */   }
/*     */   @NotNull
/*     */   public InputStream getInput() {
/* 140 */     if (this.in == null) {
/* 141 */       this.in = new LWHSInputStream(this.httpExchange.getRequestBody());
/*     */     }
/* 143 */     return this.in;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LWHSInputStream
/*     */     extends FilterInputStream
/*     */   {
/*     */     boolean closed;
/*     */     boolean readAll;
/*     */     
/*     */     LWHSInputStream(InputStream in) {
/* 154 */       super(in);
/*     */     }
/*     */     
/*     */     void readAll() throws IOException {
/* 158 */       if (!this.closed && !this.readAll) {
/* 159 */         ReadAllStream all = new ReadAllStream();
/* 160 */         all.readAll(this.in, 4000000L);
/* 161 */         this.in.close();
/* 162 */         this.in = (InputStream)all;
/* 163 */         this.readAll = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 169 */       if (!this.closed) {
/* 170 */         readAll();
/* 171 */         super.close();
/* 172 */         this.closed = true;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public OutputStream getOutput() throws IOException {
/* 180 */     if (this.out == null) {
/* 181 */       String lenHeader = this.httpExchange.getResponseHeaders().getFirst("Content-Length");
/* 182 */       int length = (lenHeader != null) ? Integer.parseInt(lenHeader) : 0;
/* 183 */       this.httpExchange.sendResponseHeaders(getStatus(), length);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 188 */       this.out = new FilterOutputStream(this.httpExchange.getResponseBody()) {
/*     */           boolean closed;
/*     */           
/*     */           public void close() throws IOException {
/* 192 */             if (!this.closed) {
/* 193 */               this.closed = true;
/*     */ 
/*     */               
/* 196 */               ServerConnectionImpl.this.in.readAll();
/*     */               try {
/* 198 */                 super.close();
/* 199 */               } catch (IOException iOException) {}
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void write(byte[] buf, int start, int len) throws IOException {
/* 208 */             this.out.write(buf, start, len);
/*     */           }
/*     */         };
/*     */     } 
/* 212 */     return this.out;
/*     */   }
/*     */   @NotNull
/*     */   public WebServiceContextDelegate getWebServiceContextDelegate() {
/* 216 */     return this;
/*     */   }
/*     */   
/*     */   public Principal getUserPrincipal(Packet request) {
/* 220 */     return this.httpExchange.getPrincipal();
/*     */   }
/*     */   
/*     */   public boolean isUserInRole(Packet request, String role) {
/* 224 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getEPRAddress(Packet request, WSEndpoint endpoint) {
/* 230 */     PortAddressResolver resolver = this.adapter.owner.createPortAddressResolver(getBaseAddress(), endpoint.getImplementationClass());
/* 231 */     String address = resolver.getAddressFor(endpoint.getServiceName(), endpoint.getPortName().getLocalPart());
/* 232 */     if (address == null)
/* 233 */       throw new WebServiceException(WsservletMessages.SERVLET_NO_ADDRESS_AVAILABLE(endpoint.getPortName())); 
/* 234 */     return address;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWSDLAddress(@NotNull Packet request, @NotNull WSEndpoint endpoint) {
/* 239 */     String eprAddress = getEPRAddress(request, endpoint);
/* 240 */     if (this.adapter.getEndpoint().getPort() != null) {
/* 241 */       return eprAddress + "?wsdl";
/*     */     }
/* 243 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSecure() {
/* 248 */     return this.httpExchange instanceof com.sun.net.httpserver.HttpsExchange;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.method"})
/*     */   @NotNull
/*     */   public String getRequestMethod() {
/* 254 */     return this.httpExchange.getRequestMethod();
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.querystring"})
/*     */   public String getQueryString() {
/* 260 */     URI requestUri = this.httpExchange.getRequestURI();
/* 261 */     String query = requestUri.getQuery();
/* 262 */     if (query != null)
/* 263 */       return query; 
/* 264 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.pathinfo"})
/*     */   public String getPathInfo() {
/* 270 */     URI requestUri = this.httpExchange.getRequestURI();
/* 271 */     String reqPath = requestUri.getPath();
/* 272 */     String ctxtPath = this.httpExchange.getHttpContext().getPath();
/* 273 */     if (reqPath.length() > ctxtPath.length()) {
/* 274 */       return reqPath.substring(ctxtPath.length());
/*     */     }
/* 276 */     return null;
/*     */   }
/*     */   
/*     */   @Property({"com.sun.xml.internal.ws.http.exchange"})
/*     */   public HttpExchange getExchange() {
/* 281 */     return this.httpExchange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getBaseAddress() {
/* 292 */     StringBuilder strBuf = new StringBuilder();
/* 293 */     strBuf.append((this.httpExchange instanceof com.sun.net.httpserver.HttpsExchange) ? "https" : "http");
/* 294 */     strBuf.append("://");
/*     */     
/* 296 */     String hostHeader = this.httpExchange.getRequestHeaders().getFirst("Host");
/* 297 */     if (hostHeader != null) {
/* 298 */       strBuf.append(hostHeader);
/*     */     } else {
/* 300 */       strBuf.append(this.httpExchange.getLocalAddress().getHostName());
/* 301 */       strBuf.append(":");
/* 302 */       strBuf.append(this.httpExchange.getLocalAddress().getPort());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 307 */     return strBuf.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getProtocol() {
/* 312 */     return this.httpExchange.getProtocol();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentLengthResponseHeader(int value) {
/* 317 */     this.httpExchange.getResponseHeaders().set("Content-Length", "" + value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestURI() {
/* 322 */     return this.httpExchange.getRequestURI().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestScheme() {
/* 327 */     return (this.httpExchange instanceof com.sun.net.httpserver.HttpsExchange) ? "https" : "http";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getServerName() {
/* 332 */     return this.httpExchange.getLocalAddress().getHostName();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getServerPort() {
/* 337 */     return this.httpExchange.getLocalAddress().getPort();
/*     */   }
/*     */   
/*     */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 341 */     return model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 347 */   private static final BasePropertySet.PropertyMap model = parse(ServerConnectionImpl.class);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/server/ServerConnectionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */