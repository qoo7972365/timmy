/*     */ package com.sun.xml.internal.ws.transport.http.server;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.internal.api.message.PropertySet.Property;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.api.server.WebServiceContextDelegate;
/*     */ import com.sun.xml.internal.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.internal.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.internal.ws.transport.http.WSHTTPConnection;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Principal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.spi.http.HttpExchange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PortableConnectionImpl
/*     */   extends WSHTTPConnection
/*     */   implements WebServiceContextDelegate
/*     */ {
/*     */   private final HttpExchange httpExchange;
/*     */   private int status;
/*     */   private final HttpAdapter adapter;
/*     */   private boolean outputWritten;
/*     */   
/*     */   public PortableConnectionImpl(@NotNull HttpAdapter adapter, @NotNull HttpExchange httpExchange) {
/*  64 */     this.adapter = adapter;
/*  65 */     this.httpExchange = httpExchange;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.headers", "com.sun.xml.internal.ws.api.message.packet.inbound.transport.headers"})
/*     */   @NotNull
/*     */   public Map<String, List<String>> getRequestHeaders() {
/*  71 */     return this.httpExchange.getRequestHeaders();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestHeader(String headerName) {
/*  76 */     return this.httpExchange.getRequestHeader(headerName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponseHeaders(Map<String, List<String>> headers) {
/*  81 */     Map<String, List<String>> r = this.httpExchange.getResponseHeaders();
/*  82 */     r.clear();
/*  83 */     for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
/*  84 */       String name = entry.getKey();
/*  85 */       List<String> values = entry.getValue();
/*     */       
/*  87 */       if (!name.equalsIgnoreCase("Content-Length") && !name.equalsIgnoreCase("Content-Type")) {
/*  88 */         r.put(name, new ArrayList<>(values));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponseHeader(String key, List<String> value) {
/*  95 */     this.httpExchange.getResponseHeaders().put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getRequestHeaderNames() {
/* 100 */     return this.httpExchange.getRequestHeaders().keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getRequestHeaderValues(String headerName) {
/* 105 */     return (List<String>)this.httpExchange.getRequestHeaders().get(headerName);
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.response.headers", "com.sun.xml.internal.ws.api.message.packet.outbound.transport.headers"})
/*     */   public Map<String, List<String>> getResponseHeaders() {
/* 111 */     return this.httpExchange.getResponseHeaders();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentTypeResponseHeader(@NotNull String value) {
/* 116 */     this.httpExchange.addResponseHeader("Content-Type", value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(int status) {
/* 121 */     this.status = status;
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.response.code"})
/*     */   public int getStatus() {
/* 127 */     return this.status;
/*     */   }
/*     */   @NotNull
/*     */   public InputStream getInput() throws IOException {
/* 131 */     return this.httpExchange.getRequestBody();
/*     */   }
/*     */   @NotNull
/*     */   public OutputStream getOutput() throws IOException {
/* 135 */     assert !this.outputWritten;
/* 136 */     this.outputWritten = true;
/*     */     
/* 138 */     this.httpExchange.setStatus(getStatus());
/* 139 */     return this.httpExchange.getResponseBody();
/*     */   }
/*     */   @NotNull
/*     */   public WebServiceContextDelegate getWebServiceContextDelegate() {
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Principal getUserPrincipal(Packet request) {
/* 148 */     return this.httpExchange.getUserPrincipal();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUserInRole(Packet request, String role) {
/* 153 */     return this.httpExchange.isUserInRole(role);
/*     */   }
/*     */   @NotNull
/*     */   public String getEPRAddress(Packet request, WSEndpoint endpoint) {
/* 157 */     PortAddressResolver resolver = this.adapter.owner.createPortAddressResolver(getBaseAddress(), endpoint.getImplementationClass());
/* 158 */     String address = resolver.getAddressFor(endpoint.getServiceName(), endpoint.getPortName().getLocalPart());
/* 159 */     if (address == null) {
/* 160 */       throw new WebServiceException(WsservletMessages.SERVLET_NO_ADDRESS_AVAILABLE(endpoint.getPortName()));
/*     */     }
/* 162 */     return address;
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.servlet.context"})
/*     */   public Object getServletContext() {
/* 167 */     return this.httpExchange.getAttribute("javax.xml.ws.servlet.context");
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.servlet.response"})
/*     */   public Object getServletResponse() {
/* 172 */     return this.httpExchange.getAttribute("javax.xml.ws.servlet.response");
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.servlet.request"})
/*     */   public Object getServletRequest() {
/* 177 */     return this.httpExchange.getAttribute("javax.xml.ws.servlet.request");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWSDLAddress(@NotNull Packet request, @NotNull WSEndpoint endpoint) {
/* 182 */     String eprAddress = getEPRAddress(request, endpoint);
/* 183 */     if (this.adapter.getEndpoint().getPort() != null) {
/* 184 */       return eprAddress + "?wsdl";
/*     */     }
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSecure() {
/* 192 */     return this.httpExchange.getScheme().equals("https");
/*     */   }
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.method"})
/*     */   @NotNull
/*     */   public String getRequestMethod() {
/* 198 */     return this.httpExchange.getRequestMethod();
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.querystring"})
/*     */   public String getQueryString() {
/* 204 */     return this.httpExchange.getQueryString();
/*     */   }
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.http.request.pathinfo"})
/*     */   public String getPathInfo() {
/* 210 */     return this.httpExchange.getPathInfo();
/*     */   }
/*     */   
/*     */   @Property({"com.sun.xml.internal.ws.http.exchange"})
/*     */   public HttpExchange getExchange() {
/* 215 */     return this.httpExchange;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getBaseAddress() {
/* 220 */     StringBuilder sb = new StringBuilder();
/* 221 */     sb.append(this.httpExchange.getScheme());
/* 222 */     sb.append("://");
/* 223 */     sb.append(this.httpExchange.getLocalAddress().getHostName());
/* 224 */     sb.append(":");
/* 225 */     sb.append(this.httpExchange.getLocalAddress().getPort());
/* 226 */     sb.append(this.httpExchange.getContextPath());
/* 227 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getProtocol() {
/* 232 */     return this.httpExchange.getProtocol();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentLengthResponseHeader(int value) {
/* 237 */     this.httpExchange.addResponseHeader("Content-Length", "" + value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestURI() {
/* 242 */     return this.httpExchange.getRequestURI().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRequestScheme() {
/* 247 */     return this.httpExchange.getScheme();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getServerName() {
/* 252 */     return this.httpExchange.getLocalAddress().getHostName();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getServerPort() {
/* 257 */     return this.httpExchange.getLocalAddress().getPort();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 262 */     return model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 268 */   private static final BasePropertySet.PropertyMap model = parse(PortableConnectionImpl.class);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/server/PortableConnectionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */