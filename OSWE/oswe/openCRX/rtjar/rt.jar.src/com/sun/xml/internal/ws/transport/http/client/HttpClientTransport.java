/*     */ package com.sun.xml.internal.ws.transport.http.client;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.client.ClientTransportException;
/*     */ import com.sun.xml.internal.ws.resources.ClientMessages;
/*     */ import com.sun.xml.internal.ws.transport.Headers;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ public class HttpClientTransport
/*     */ {
/*  64 */   private static final byte[] THROW_AWAY_BUFFER = new byte[8192];
/*     */   int statusCode;
/*     */   
/*     */   static {
/*     */     try {
/*  69 */       JAXBContext.newInstance(new Class[0]).createUnmarshaller();
/*  70 */     } catch (JAXBException jAXBException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   String statusMessage;
/*     */   
/*     */   int contentLength;
/*     */   
/*     */   private final Map<String, List<String>> reqHeaders;
/*  79 */   private Map<String, List<String>> respHeaders = null;
/*     */   
/*     */   private OutputStream outputStream;
/*     */   private boolean https;
/*  83 */   private HttpURLConnection httpConnection = null;
/*     */   
/*     */   private final EndpointAddress endpoint;
/*     */   private final Packet context;
/*     */   private final Integer chunkSize;
/*     */   
/*     */   public HttpClientTransport(@NotNull Packet packet, @NotNull Map<String, List<String>> reqHeaders) {
/*  90 */     this.endpoint = packet.endpointAddress;
/*  91 */     this.context = packet;
/*  92 */     this.reqHeaders = reqHeaders;
/*  93 */     this.chunkSize = (Integer)this.context.invocationProperties.get("com.sun.xml.internal.ws.transport.http.client.streaming.chunk.size");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   OutputStream getOutput() {
/*     */     try {
/* 101 */       createHttpConnection();
/*     */       
/* 103 */       if (requiresOutputStream()) {
/* 104 */         this.outputStream = this.httpConnection.getOutputStream();
/* 105 */         if (this.chunkSize != null) {
/* 106 */           this.outputStream = new WSChunkedOuputStream(this.outputStream, this.chunkSize.intValue());
/*     */         }
/* 108 */         List<String> contentEncoding = this.reqHeaders.get("Content-Encoding");
/*     */         
/* 110 */         if (contentEncoding != null && ((String)contentEncoding.get(0)).contains("gzip")) {
/* 111 */           this.outputStream = new GZIPOutputStream(this.outputStream);
/*     */         }
/*     */       } 
/* 114 */       this.httpConnection.connect();
/* 115 */     } catch (Exception ex) {
/* 116 */       throw new ClientTransportException(
/* 117 */           ClientMessages.localizableHTTP_CLIENT_FAILED(ex), ex);
/*     */     } 
/*     */     
/* 120 */     return this.outputStream;
/*     */   }
/*     */   
/*     */   void closeOutput() throws IOException {
/* 124 */     if (this.outputStream != null) {
/* 125 */       this.outputStream.close();
/* 126 */       this.outputStream = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   InputStream getInput() {
/*     */     InputStream in;
/*     */     try {
/* 138 */       in = readResponse();
/* 139 */       if (in != null) {
/* 140 */         String contentEncoding = this.httpConnection.getContentEncoding();
/* 141 */         if (contentEncoding != null && contentEncoding.contains("gzip")) {
/* 142 */           in = new GZIPInputStream(in);
/*     */         }
/*     */       } 
/* 145 */     } catch (IOException e) {
/* 146 */       throw new ClientTransportException(ClientMessages.localizableHTTP_STATUS_CODE(Integer.valueOf(this.statusCode), this.statusMessage), e);
/*     */     } 
/* 148 */     return in;
/*     */   }
/*     */   
/*     */   public Map<String, List<String>> getHeaders() {
/* 152 */     if (this.respHeaders != null) {
/* 153 */       return this.respHeaders;
/*     */     }
/* 155 */     this.respHeaders = (Map<String, List<String>>)new Headers();
/* 156 */     this.respHeaders.putAll(this.httpConnection.getHeaderFields());
/* 157 */     return this.respHeaders;
/*     */   }
/*     */   @Nullable
/*     */   protected InputStream readResponse() {
/*     */     InputStream is;
/*     */     try {
/* 163 */       is = this.httpConnection.getInputStream();
/* 164 */     } catch (IOException ioe) {
/* 165 */       is = this.httpConnection.getErrorStream();
/*     */     } 
/* 167 */     if (is == null) {
/* 168 */       return is;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 173 */     final InputStream temp = is;
/* 174 */     return new FilterInputStream(temp)
/*     */       {
/*     */         boolean closed;
/*     */ 
/*     */         
/*     */         public void close() throws IOException {
/* 180 */           if (!this.closed) {
/* 181 */             this.closed = true;
/* 182 */             while (temp.read(HttpClientTransport.THROW_AWAY_BUFFER) != -1);
/* 183 */             super.close();
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected void readResponseCodeAndMessage() {
/*     */     try {
/* 191 */       this.statusCode = this.httpConnection.getResponseCode();
/* 192 */       this.statusMessage = this.httpConnection.getResponseMessage();
/* 193 */       this.contentLength = this.httpConnection.getContentLength();
/* 194 */     } catch (IOException ioe) {
/* 195 */       throw new WebServiceException(ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected HttpURLConnection openConnection(Packet packet) {
/* 201 */     return null;
/*     */   }
/*     */   
/*     */   protected boolean checkHTTPS(HttpURLConnection connection) {
/* 205 */     if (connection instanceof HttpsURLConnection) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       String verificationProperty = (String)this.context.invocationProperties.get("com.sun.xml.internal.ws.client.http.HostnameVerificationProperty");
/* 213 */       if (verificationProperty != null && 
/* 214 */         verificationProperty.equalsIgnoreCase("true")) {
/* 215 */         ((HttpsURLConnection)connection).setHostnameVerifier(new HttpClientVerifier());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 221 */       HostnameVerifier verifier = (HostnameVerifier)this.context.invocationProperties.get("com.sun.xml.internal.ws.transport.https.client.hostname.verifier");
/* 222 */       if (verifier != null) {
/* 223 */         ((HttpsURLConnection)connection).setHostnameVerifier(verifier);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 228 */       SSLSocketFactory sslSocketFactory = (SSLSocketFactory)this.context.invocationProperties.get("com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory");
/* 229 */       if (sslSocketFactory != null) {
/* 230 */         ((HttpsURLConnection)connection).setSSLSocketFactory(sslSocketFactory);
/*     */       }
/*     */       
/* 233 */       return true;
/*     */     } 
/* 235 */     return false;
/*     */   }
/*     */   
/*     */   private void createHttpConnection() throws IOException {
/* 239 */     this.httpConnection = openConnection(this.context);
/*     */     
/* 241 */     if (this.httpConnection == null) {
/* 242 */       this.httpConnection = (HttpURLConnection)this.endpoint.openConnection();
/*     */     }
/* 244 */     String scheme = this.endpoint.getURI().getScheme();
/* 245 */     if (scheme.equals("https")) {
/* 246 */       this.https = true;
/*     */     }
/* 248 */     if (checkHTTPS(this.httpConnection)) {
/* 249 */       this.https = true;
/*     */     }
/*     */ 
/*     */     
/* 253 */     this.httpConnection.setAllowUserInteraction(true);
/*     */ 
/*     */     
/* 256 */     this.httpConnection.setDoOutput(true);
/* 257 */     this.httpConnection.setDoInput(true);
/*     */     
/* 259 */     String requestMethod = (String)this.context.invocationProperties.get("javax.xml.ws.http.request.method");
/* 260 */     String method = (requestMethod != null) ? requestMethod : "POST";
/* 261 */     this.httpConnection.setRequestMethod(method);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     Integer reqTimeout = (Integer)this.context.invocationProperties.get("com.sun.xml.internal.ws.request.timeout");
/* 273 */     if (reqTimeout != null) {
/* 274 */       this.httpConnection.setReadTimeout(reqTimeout.intValue());
/*     */     }
/*     */     
/* 277 */     Integer connectTimeout = (Integer)this.context.invocationProperties.get("com.sun.xml.internal.ws.connect.timeout");
/* 278 */     if (connectTimeout != null) {
/* 279 */       this.httpConnection.setConnectTimeout(connectTimeout.intValue());
/*     */     }
/*     */     
/* 282 */     Integer chunkSize = (Integer)this.context.invocationProperties.get("com.sun.xml.internal.ws.transport.http.client.streaming.chunk.size");
/* 283 */     if (chunkSize != null) {
/* 284 */       this.httpConnection.setChunkedStreamingMode(chunkSize.intValue());
/*     */     }
/*     */ 
/*     */     
/* 288 */     for (Map.Entry<String, List<String>> entry : this.reqHeaders.entrySet()) {
/* 289 */       if ("Content-Length".equals(entry.getKey()))
/* 290 */         continue;  for (String value : entry.getValue()) {
/* 291 */         this.httpConnection.addRequestProperty(entry.getKey(), value);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isSecure() {
/* 297 */     return this.https;
/*     */   }
/*     */   
/*     */   protected void setStatusCode(int statusCode) {
/* 301 */     this.statusCode = statusCode;
/*     */   }
/*     */   
/*     */   private boolean requiresOutputStream() {
/* 305 */     return (!this.httpConnection.getRequestMethod().equalsIgnoreCase("GET") && 
/* 306 */       !this.httpConnection.getRequestMethod().equalsIgnoreCase("HEAD") && 
/* 307 */       !this.httpConnection.getRequestMethod().equalsIgnoreCase("DELETE"));
/*     */   }
/*     */   @Nullable
/*     */   String getContentType() {
/* 311 */     return this.httpConnection.getContentType();
/*     */   }
/*     */   
/*     */   public int getContentLength() {
/* 315 */     return this.httpConnection.getContentLength();
/*     */   }
/*     */   
/*     */   private static class HttpClientVerifier implements HostnameVerifier {
/*     */     private HttpClientVerifier() {}
/*     */     
/*     */     public boolean verify(String s, SSLSession sslSession) {
/* 322 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class LocalhostHttpClientVerifier implements HostnameVerifier {
/*     */     public boolean verify(String s, SSLSession sslSession) {
/* 328 */       return ("localhost".equalsIgnoreCase(s) || "127.0.0.1".equals(s));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class WSChunkedOuputStream
/*     */     extends FilterOutputStream
/*     */   {
/*     */     final int chunkSize;
/*     */ 
/*     */ 
/*     */     
/*     */     WSChunkedOuputStream(OutputStream actual, int chunkSize) {
/* 342 */       super(actual);
/* 343 */       this.chunkSize = chunkSize;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(byte[] b, int off, int len) throws IOException {
/* 348 */       while (len > 0) {
/* 349 */         int sent = (len > this.chunkSize) ? this.chunkSize : len;
/* 350 */         this.out.write(b, off, sent);
/* 351 */         len -= sent;
/* 352 */         off += sent;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/client/HttpClientTransport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */