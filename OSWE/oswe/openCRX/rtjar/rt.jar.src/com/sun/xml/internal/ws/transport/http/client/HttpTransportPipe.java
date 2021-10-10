/*     */ package com.sun.xml.internal.ws.transport.http.client;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.PropertySet;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.internal.ws.client.ClientTransportException;
/*     */ import com.sun.xml.internal.ws.developer.HttpConfigFeature;
/*     */ import com.sun.xml.internal.ws.resources.ClientMessages;
/*     */ import com.sun.xml.internal.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.internal.ws.transport.Headers;
/*     */ import com.sun.xml.internal.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.internal.ws.util.ByteArrayBuffer;
/*     */ import com.sun.xml.internal.ws.util.RuntimeVersion;
/*     */ import com.sun.xml.internal.ws.util.StreamUtils;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.CookieHandler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.DatatypeConverter;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpTransportPipe
/*     */   extends AbstractTubeImpl
/*     */ {
/*     */   static {
/*     */     boolean b;
/*     */   }
/*     */   
/*  69 */   private static final List<String> USER_AGENT = Collections.singletonList(RuntimeVersion.VERSION.toString());
/*  70 */   private static final Logger LOGGER = Logger.getLogger(HttpTransportPipe.class.getName());
/*     */   
/*     */   public static boolean dump;
/*     */   
/*     */   private final Codec codec;
/*     */   
/*     */   private final WSBinding binding;
/*     */   
/*     */   private final CookieHandler cookieJar;
/*     */   
/*     */   private final boolean sticky;
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  85 */       b = Boolean.getBoolean(HttpTransportPipe.class.getName() + ".dump");
/*  86 */     } catch (Throwable t) {
/*  87 */       b = false;
/*     */     } 
/*  89 */     dump = b;
/*     */   }
/*     */   
/*     */   public HttpTransportPipe(Codec codec, WSBinding binding) {
/*  93 */     this.codec = codec;
/*  94 */     this.binding = binding;
/*  95 */     this.sticky = isSticky(binding);
/*  96 */     HttpConfigFeature configFeature = (HttpConfigFeature)binding.getFeature(HttpConfigFeature.class);
/*  97 */     if (configFeature == null) {
/*  98 */       configFeature = new HttpConfigFeature();
/*     */     }
/* 100 */     this.cookieJar = configFeature.getCookieHandler();
/*     */   }
/*     */   
/*     */   private static boolean isSticky(WSBinding binding) {
/* 104 */     boolean tSticky = false;
/* 105 */     WebServiceFeature[] features = binding.getFeatures().toArray();
/* 106 */     for (WebServiceFeature f : features) {
/* 107 */       if (f instanceof com.sun.xml.internal.ws.api.ha.StickyFeature) {
/* 108 */         tSticky = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 112 */     return tSticky;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HttpTransportPipe(HttpTransportPipe that, TubeCloner cloner) {
/* 119 */     this(that.codec.copy(), that.binding);
/* 120 */     cloner.add((Tube)that, (Tube)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(@NotNull Throwable t) {
/* 125 */     return doThrow(t);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(@NotNull Packet request) {
/* 130 */     return doReturnWith(process(request));
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(@NotNull Packet response) {
/* 135 */     return doReturnWith(response);
/*     */   }
/*     */   
/*     */   protected HttpClientTransport getTransport(Packet request, Map<String, List<String>> reqHeaders) {
/* 139 */     return new HttpClientTransport(request, reqHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet process(Packet request) {
/*     */     try {
/* 147 */       Headers<String, List<String>> headers = new Headers();
/*     */       
/* 149 */       Map<String, List<String>> userHeaders = (Map<String, List<String>>)request.invocationProperties.get("javax.xml.ws.http.request.headers");
/* 150 */       boolean addUserAgent = true;
/* 151 */       if (userHeaders != null) {
/*     */         
/* 153 */         headers.putAll(userHeaders);
/*     */         
/* 155 */         if (userHeaders.get("User-Agent") != null) {
/* 156 */           addUserAgent = false;
/*     */         }
/*     */       } 
/* 159 */       if (addUserAgent) {
/* 160 */         headers.put("User-Agent", USER_AGENT);
/*     */       }
/*     */       
/* 163 */       addBasicAuth(request, (Map<String, List<String>>)headers);
/* 164 */       addCookies(request, (Map<String, List<String>>)headers);
/*     */       
/* 166 */       HttpClientTransport con = getTransport(request, (Map<String, List<String>>)headers);
/* 167 */       request.addSatellite((PropertySet)new HttpResponseProperties(con));
/*     */       
/* 169 */       ContentType ct = this.codec.getStaticContentType(request);
/* 170 */       if (ct == null) {
/* 171 */         ByteArrayBuffer buf = new ByteArrayBuffer();
/*     */         
/* 173 */         ct = this.codec.encode(request, (OutputStream)buf);
/*     */         
/* 175 */         headers.put("Content-Length", Collections.singletonList(Integer.toString(buf.size())));
/* 176 */         headers.put("Content-Type", Collections.singletonList(ct.getContentType()));
/* 177 */         if (ct.getAcceptHeader() != null) {
/* 178 */           headers.put("Accept", Collections.singletonList(ct.getAcceptHeader()));
/*     */         }
/* 180 */         if (this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/* 181 */           writeSOAPAction((Map<String, List<String>>)headers, ct.getSOAPActionHeader());
/*     */         }
/*     */         
/* 184 */         if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 185 */           dump(buf, "HTTP request", (Map<String, List<String>>)headers);
/*     */         }
/*     */         
/* 188 */         buf.writeTo(con.getOutput());
/*     */       } else {
/*     */         
/* 191 */         headers.put("Content-Type", Collections.singletonList(ct.getContentType()));
/* 192 */         if (ct.getAcceptHeader() != null) {
/* 193 */           headers.put("Accept", Collections.singletonList(ct.getAcceptHeader()));
/*     */         }
/* 195 */         if (this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/* 196 */           writeSOAPAction((Map<String, List<String>>)headers, ct.getSOAPActionHeader());
/*     */         }
/*     */         
/* 199 */         if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 200 */           ByteArrayBuffer buf = new ByteArrayBuffer();
/* 201 */           this.codec.encode(request, (OutputStream)buf);
/* 202 */           dump(buf, "HTTP request - " + request.endpointAddress, (Map<String, List<String>>)headers);
/* 203 */           OutputStream out = con.getOutput();
/* 204 */           if (out != null) {
/* 205 */             buf.writeTo(out);
/*     */           }
/*     */         } else {
/* 208 */           OutputStream os = con.getOutput();
/* 209 */           if (os != null) {
/* 210 */             this.codec.encode(request, os);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 215 */       con.closeOutput();
/*     */       
/* 217 */       return createResponsePacket(request, con);
/* 218 */     } catch (WebServiceException wex) {
/* 219 */       throw wex;
/* 220 */     } catch (Exception ex) {
/* 221 */       throw new WebServiceException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Packet createResponsePacket(Packet request, HttpClientTransport con) throws IOException {
/* 226 */     con.readResponseCodeAndMessage();
/* 227 */     recordCookies(request, con);
/*     */     
/* 229 */     InputStream responseStream = con.getInput();
/* 230 */     if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 231 */       ByteArrayBuffer buf = new ByteArrayBuffer();
/* 232 */       if (responseStream != null) {
/* 233 */         buf.write(responseStream);
/* 234 */         responseStream.close();
/*     */       } 
/* 236 */       dump(buf, "HTTP response - " + request.endpointAddress + " - " + con.statusCode, con.getHeaders());
/* 237 */       responseStream = buf.newInputStream();
/*     */     } 
/*     */ 
/*     */     
/* 241 */     int cl = con.contentLength;
/* 242 */     InputStream tempIn = null;
/* 243 */     if (cl == -1) {
/* 244 */       tempIn = StreamUtils.hasSomeData(responseStream);
/* 245 */       if (tempIn != null) {
/* 246 */         responseStream = tempIn;
/*     */       }
/*     */     } 
/* 249 */     if ((cl == 0 || (cl == -1 && tempIn == null)) && 
/* 250 */       responseStream != null) {
/* 251 */       responseStream.close();
/* 252 */       responseStream = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     checkStatusCode(responseStream, con);
/*     */     
/* 261 */     Packet reply = request.createClientResponse(null);
/* 262 */     reply.wasTransportSecure = con.isSecure();
/* 263 */     if (responseStream != null) {
/* 264 */       String contentType = con.getContentType();
/* 265 */       if (contentType != null && contentType.contains("text/html") && this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/* 266 */         throw new ClientTransportException(ClientMessages.localizableHTTP_STATUS_CODE(Integer.valueOf(con.statusCode), con.statusMessage));
/*     */       }
/* 268 */       this.codec.decode(responseStream, contentType, reply);
/*     */     } 
/* 270 */     return reply;
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
/*     */   private void checkStatusCode(InputStream in, HttpClientTransport con) throws IOException {
/* 282 */     int statusCode = con.statusCode;
/* 283 */     String statusMessage = con.statusMessage;
/*     */     
/* 285 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/* 286 */       if (this.binding.getSOAPVersion() == SOAPVersion.SOAP_12) {
/*     */         
/* 288 */         if (statusCode == 200 || statusCode == 202 || isErrorCode(statusCode)) {
/*     */           
/* 290 */           if (isErrorCode(statusCode) && in == null)
/*     */           {
/* 292 */             throw new ClientTransportException(ClientMessages.localizableHTTP_STATUS_CODE(Integer.valueOf(statusCode), statusMessage));
/*     */           }
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/* 298 */       } else if (statusCode == 200 || statusCode == 202 || statusCode == 500) {
/*     */         
/* 300 */         if (statusCode == 500 && in == null)
/*     */         {
/* 302 */           throw new ClientTransportException(ClientMessages.localizableHTTP_STATUS_CODE(Integer.valueOf(statusCode), statusMessage));
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 307 */       if (in != null) {
/* 308 */         in.close();
/*     */       }
/* 310 */       throw new ClientTransportException(ClientMessages.localizableHTTP_STATUS_CODE(Integer.valueOf(statusCode), statusMessage));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isErrorCode(int code) {
/* 317 */     return (code == 500 || code == 400);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addCookies(Packet context, Map<String, List<String>> reqHeaders) throws IOException {
/* 322 */     Boolean shouldMaintainSessionProperty = (Boolean)context.invocationProperties.get("javax.xml.ws.session.maintain");
/* 323 */     if (shouldMaintainSessionProperty != null && !shouldMaintainSessionProperty.booleanValue()) {
/*     */       return;
/*     */     }
/* 326 */     if (this.sticky || (shouldMaintainSessionProperty != null && shouldMaintainSessionProperty.booleanValue())) {
/* 327 */       Map<String, List<String>> rememberedCookies = this.cookieJar.get(context.endpointAddress.getURI(), reqHeaders);
/* 328 */       processCookieHeaders(reqHeaders, rememberedCookies, "Cookie");
/* 329 */       processCookieHeaders(reqHeaders, rememberedCookies, "Cookie2");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processCookieHeaders(Map<String, List<String>> requestHeaders, Map<String, List<String>> rememberedCookies, String cookieHeader) {
/* 334 */     List<String> jarCookies = rememberedCookies.get(cookieHeader);
/* 335 */     if (jarCookies != null && !jarCookies.isEmpty()) {
/* 336 */       List<String> resultCookies = mergeUserCookies(jarCookies, requestHeaders.get(cookieHeader));
/* 337 */       requestHeaders.put(cookieHeader, resultCookies);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> mergeUserCookies(List<String> rememberedCookies, List<String> userCookies) {
/* 344 */     if (userCookies == null || userCookies.isEmpty()) {
/* 345 */       return rememberedCookies;
/*     */     }
/*     */     
/* 348 */     Map<String, String> map = new HashMap<>();
/* 349 */     cookieListToMap(rememberedCookies, map);
/* 350 */     cookieListToMap(userCookies, map);
/*     */     
/* 352 */     return new ArrayList<>(map.values());
/*     */   }
/*     */   
/*     */   private void cookieListToMap(List<String> cookieList, Map<String, String> targetMap) {
/* 356 */     for (String cookie : cookieList) {
/* 357 */       int index = cookie.indexOf("=");
/* 358 */       String cookieName = cookie.substring(0, index);
/* 359 */       targetMap.put(cookieName, cookie);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void recordCookies(Packet context, HttpClientTransport con) throws IOException {
/* 365 */     Boolean shouldMaintainSessionProperty = (Boolean)context.invocationProperties.get("javax.xml.ws.session.maintain");
/* 366 */     if (shouldMaintainSessionProperty != null && !shouldMaintainSessionProperty.booleanValue()) {
/*     */       return;
/*     */     }
/* 369 */     if (this.sticky || (shouldMaintainSessionProperty != null && shouldMaintainSessionProperty.booleanValue())) {
/* 370 */       this.cookieJar.put(context.endpointAddress.getURI(), con.getHeaders());
/*     */     }
/*     */   }
/*     */   
/*     */   private void addBasicAuth(Packet context, Map<String, List<String>> reqHeaders) {
/* 375 */     String user = (String)context.invocationProperties.get("javax.xml.ws.security.auth.username");
/* 376 */     if (user != null) {
/* 377 */       String pw = (String)context.invocationProperties.get("javax.xml.ws.security.auth.password");
/* 378 */       if (pw != null) {
/* 379 */         StringBuilder buf = new StringBuilder(user);
/* 380 */         buf.append(":");
/* 381 */         buf.append(pw);
/* 382 */         String creds = DatatypeConverter.printBase64Binary(buf.toString().getBytes());
/* 383 */         reqHeaders.put("Authorization", Collections.singletonList("Basic " + creds));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeSOAPAction(Map<String, List<String>> reqHeaders, String soapAction) {
/* 394 */     if (SOAPVersion.SOAP_12.equals(this.binding.getSOAPVersion())) {
/*     */       return;
/*     */     }
/* 397 */     if (soapAction != null) {
/* 398 */       reqHeaders.put("SOAPAction", Collections.singletonList(soapAction));
/*     */     } else {
/* 400 */       reqHeaders.put("SOAPAction", Collections.singletonList("\"\""));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void preDestroy() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpTransportPipe copy(TubeCloner cloner) {
/* 411 */     return new HttpTransportPipe(this, cloner);
/*     */   }
/*     */ 
/*     */   
/*     */   private void dump(ByteArrayBuffer buf, String caption, Map<String, List<String>> headers) throws IOException {
/* 416 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 417 */     PrintWriter pw = new PrintWriter(baos, true);
/* 418 */     pw.println("---[" + caption + "]---");
/* 419 */     for (Map.Entry<String, List<String>> header : headers.entrySet()) {
/* 420 */       if (((List)header.getValue()).isEmpty()) {
/*     */ 
/*     */         
/* 423 */         pw.println(header.getValue()); continue;
/*     */       } 
/* 425 */       for (String value : header.getValue()) {
/* 426 */         pw.println((String)header.getKey() + ": " + value);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 431 */     if (buf.size() > HttpAdapter.dump_threshold) {
/* 432 */       byte[] b = buf.getRawData();
/* 433 */       baos.write(b, 0, HttpAdapter.dump_threshold);
/* 434 */       pw.println();
/* 435 */       pw.println(WsservletMessages.MESSAGE_TOO_LONG(HttpAdapter.class.getName() + ".dumpTreshold"));
/*     */     } else {
/* 437 */       buf.writeTo(baos);
/*     */     } 
/* 439 */     pw.println("--------------------");
/*     */     
/* 441 */     String msg = baos.toString();
/* 442 */     if (dump) {
/* 443 */       System.out.println(msg);
/*     */     }
/* 445 */     if (LOGGER.isLoggable(Level.FINER))
/* 446 */       LOGGER.log(Level.FINER, msg); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/client/HttpTransportPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */