/*     */ package com.sun.xml.internal.ws.transport.http;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.PropertySet;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.Component;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.NonAnonymousResponseProcessor;
/*     */ import com.sun.xml.internal.ws.api.ha.HaInfo;
/*     */ import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.internal.ws.api.server.AbstractServerAsyncTransport;
/*     */ import com.sun.xml.internal.ws.api.server.Adapter;
/*     */ import com.sun.xml.internal.ws.api.server.BoundEndpoint;
/*     */ import com.sun.xml.internal.ws.api.server.DocumentAddressResolver;
/*     */ import com.sun.xml.internal.ws.api.server.Module;
/*     */ import com.sun.xml.internal.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocument;
/*     */ import com.sun.xml.internal.ws.api.server.ServiceDefinition;
/*     */ import com.sun.xml.internal.ws.api.server.TransportBackChannel;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.api.server.WebServiceContextDelegate;
/*     */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.internal.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.internal.ws.server.UnsupportedMediaException;
/*     */ import com.sun.xml.internal.ws.util.ByteArrayBuffer;
/*     */ import com.sun.xml.internal.ws.util.Pool;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpAdapter
/*     */   extends Adapter<HttpAdapter.HttpToolkit>
/*     */ {
/*  92 */   private static final Logger LOGGER = Logger.getLogger(HttpAdapter.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Map<String, SDDocument> wsdls;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<SDDocument, String> revWsdls;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   private ServiceDefinition serviceDefinition = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final HttpAdapterList<? extends HttpAdapter> owner;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String urlPattern;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean stickyCookie;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean disableJreplicaCookie = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpAdapter createAlone(WSEndpoint<?> endpoint) {
/* 137 */     return (new DummyList()).createAdapter("", "", endpoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpAdapter(WSEndpoint endpoint, HttpAdapterList<? extends HttpAdapter> owner) {
/* 148 */     this(endpoint, owner, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpAdapter(WSEndpoint endpoint, HttpAdapterList<? extends HttpAdapter> owner, String urlPattern) {
/* 154 */     super(endpoint);
/* 155 */     this.owner = owner;
/* 156 */     this.urlPattern = urlPattern;
/*     */     
/* 158 */     initWSDLMap(endpoint.getServiceDefinition());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceDefinition getServiceDefinition() {
/* 167 */     return this.serviceDefinition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void initWSDLMap(ServiceDefinition sdef) {
/* 176 */     this.serviceDefinition = sdef;
/* 177 */     if (sdef == null) {
/* 178 */       this.wsdls = Collections.emptyMap();
/* 179 */       this.revWsdls = Collections.emptyMap();
/*     */     } else {
/* 181 */       this.wsdls = new HashMap<>();
/*     */ 
/*     */       
/* 184 */       Map<String, SDDocument> systemIds = new TreeMap<>();
/* 185 */       for (SDDocument sdd : sdef) {
/* 186 */         if (sdd == sdef.getPrimary()) {
/* 187 */           this.wsdls.put("wsdl", sdd);
/* 188 */           this.wsdls.put("WSDL", sdd); continue;
/*     */         } 
/* 190 */         systemIds.put(sdd.getURL().toString(), sdd);
/*     */       } 
/*     */ 
/*     */       
/* 194 */       int wsdlnum = 1;
/* 195 */       int xsdnum = 1;
/* 196 */       for (Map.Entry<String, SDDocument> e : systemIds.entrySet()) {
/* 197 */         SDDocument sdd = e.getValue();
/* 198 */         if (sdd.isWSDL()) {
/* 199 */           this.wsdls.put("wsdl=" + wsdlnum++, sdd);
/*     */         }
/* 201 */         if (sdd.isSchema()) {
/* 202 */           this.wsdls.put("xsd=" + xsdnum++, sdd);
/*     */         }
/*     */       } 
/*     */       
/* 206 */       this.revWsdls = new HashMap<>();
/* 207 */       for (Map.Entry<String, SDDocument> e : this.wsdls.entrySet()) {
/* 208 */         if (!((String)e.getKey()).equals("WSDL")) {
/* 209 */           this.revWsdls.put(e.getValue(), e.getKey());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValidPath() {
/* 220 */     if (this.urlPattern.endsWith("/*")) {
/* 221 */       return this.urlPattern.substring(0, this.urlPattern.length() - 2);
/*     */     }
/* 223 */     return this.urlPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpToolkit createToolkit() {
/* 229 */     return new HttpToolkit();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(@NotNull WSHTTPConnection connection) throws IOException {
/* 251 */     if (handleGet(connection)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 256 */     Pool<HttpToolkit> currentPool = getPool();
/*     */     
/* 258 */     HttpToolkit tk = (HttpToolkit)currentPool.take();
/*     */     try {
/* 260 */       tk.handle(connection);
/*     */     } finally {
/* 262 */       currentPool.recycle(tk);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean handleGet(@NotNull WSHTTPConnection connection) throws IOException {
/* 267 */     if (connection.getRequestMethod().equals("GET")) {
/*     */       
/* 269 */       for (Component c : this.endpoint.getComponents()) {
/* 270 */         HttpMetadataPublisher spi = (HttpMetadataPublisher)c.getSPI(HttpMetadataPublisher.class);
/* 271 */         if (spi != null && spi.handleMetadataRequest(this, connection)) {
/* 272 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 276 */       if (isMetadataQuery(connection.getQueryString())) {
/*     */         
/* 278 */         publishWSDL(connection);
/* 279 */         return true;
/*     */       } 
/*     */       
/* 282 */       WSBinding wSBinding = getEndpoint().getBinding();
/* 283 */       if (!(wSBinding instanceof javax.xml.ws.http.HTTPBinding)) {
/*     */         
/* 285 */         writeWebServicesHtmlPage(connection);
/* 286 */         return true;
/*     */       } 
/* 288 */     } else if (connection.getRequestMethod().equals("HEAD")) {
/* 289 */       connection.getInput().close();
/* 290 */       WSBinding wSBinding = getEndpoint().getBinding();
/* 291 */       if (isMetadataQuery(connection.getQueryString())) {
/* 292 */         SDDocument doc = this.wsdls.get(connection.getQueryString());
/* 293 */         connection.setStatus((doc != null) ? 200 : 404);
/*     */ 
/*     */         
/* 296 */         connection.getOutput().close();
/* 297 */         connection.close();
/* 298 */         return true;
/* 299 */       }  if (!(wSBinding instanceof javax.xml.ws.http.HTTPBinding)) {
/* 300 */         connection.setStatus(404);
/* 301 */         connection.getOutput().close();
/* 302 */         connection.close();
/* 303 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 308 */     return false;
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
/*     */   private Packet decodePacket(@NotNull WSHTTPConnection con, @NotNull Codec codec) throws IOException {
/* 321 */     String ct = con.getRequestHeader("Content-Type");
/* 322 */     InputStream in = con.getInput();
/* 323 */     Packet packet = new Packet();
/* 324 */     packet.soapAction = fixQuotesAroundSoapAction(con.getRequestHeader("SOAPAction"));
/* 325 */     packet.wasTransportSecure = con.isSecure();
/* 326 */     packet.acceptableMimeTypes = con.getRequestHeader("Accept");
/* 327 */     packet.addSatellite((PropertySet)con);
/* 328 */     addSatellites(packet);
/* 329 */     packet.isAdapterDeliversNonAnonymousResponse = true;
/* 330 */     packet.component = (Component)this;
/* 331 */     packet.transportBackChannel = new Oneway(con);
/* 332 */     packet.webServiceContextDelegate = con.getWebServiceContextDelegate();
/* 333 */     packet.setState(Packet.State.ServerRequest);
/* 334 */     if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 335 */       ByteArrayBuffer buf = new ByteArrayBuffer();
/* 336 */       buf.write(in);
/* 337 */       in.close();
/* 338 */       dump(buf, "HTTP request", con.getRequestHeaders());
/* 339 */       in = buf.newInputStream();
/*     */     } 
/* 341 */     codec.decode(in, ct, packet);
/* 342 */     return packet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addSatellites(Packet packet) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fixQuotesAroundSoapAction(String soapAction) {
/* 356 */     if (soapAction != null && (!soapAction.startsWith("\"") || !soapAction.endsWith("\""))) {
/* 357 */       if (LOGGER.isLoggable(Level.INFO)) {
/* 358 */         LOGGER.log(Level.INFO, "Received WS-I BP non-conformant Unquoted SoapAction HTTP header: {0}", soapAction);
/*     */       }
/* 360 */       String fixedSoapAction = soapAction;
/* 361 */       if (!soapAction.startsWith("\"")) {
/* 362 */         fixedSoapAction = "\"" + fixedSoapAction;
/*     */       }
/* 364 */       if (!soapAction.endsWith("\"")) {
/* 365 */         fixedSoapAction = fixedSoapAction + "\"";
/*     */       }
/* 367 */       return fixedSoapAction;
/*     */     } 
/* 369 */     return soapAction;
/*     */   }
/*     */   
/*     */   protected NonAnonymousResponseProcessor getNonAnonymousResponseProcessor() {
/* 373 */     return NonAnonymousResponseProcessor.getDefault();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeClientError(int connStatus, @NotNull OutputStream os, @NotNull Packet packet) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isClientErrorStatus(int connStatus) {
/* 387 */     return (connStatus == 403);
/*     */   }
/*     */   
/*     */   private boolean isNonAnonymousUri(EndpointAddress addr) {
/* 391 */     return (addr != null && !addr.toString().equals(AddressingVersion.W3C.anonymousUri) && 
/* 392 */       !addr.toString().equals(AddressingVersion.MEMBER.anonymousUri));
/*     */   }
/*     */   
/*     */   private void encodePacket(@NotNull Packet packet, @NotNull WSHTTPConnection con, @NotNull Codec codec) throws IOException {
/* 396 */     if (isNonAnonymousUri(packet.endpointAddress) && packet.getMessage() != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */         
/* 402 */         packet = getNonAnonymousResponseProcessor().process(packet);
/* 403 */       } catch (RuntimeException re) {
/*     */ 
/*     */         
/* 406 */         SOAPVersion soapVersion = packet.getBinding().getSOAPVersion();
/* 407 */         Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, null, re);
/* 408 */         packet = packet.createServerResponse(faultMsg, packet.endpoint.getPort(), null, packet.endpoint.getBinding());
/*     */       } 
/*     */     }
/*     */     
/* 412 */     if (con.isClosed()) {
/*     */       return;
/*     */     }
/* 415 */     Message responseMessage = packet.getMessage();
/* 416 */     addStickyCookie(con);
/* 417 */     addReplicaCookie(con, packet);
/* 418 */     if (responseMessage == null) {
/* 419 */       if (!con.isClosed()) {
/*     */ 
/*     */         
/* 422 */         if (con.getStatus() == 0) {
/* 423 */           con.setStatus(202);
/*     */         }
/* 425 */         OutputStream os = con.getProtocol().contains("1.1") ? con.getOutput() : (OutputStream)new Http10OutputStream(con);
/* 426 */         if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 427 */           ByteArrayBuffer buf = new ByteArrayBuffer();
/* 428 */           codec.encode(packet, (OutputStream)buf);
/* 429 */           dump(buf, "HTTP response " + con.getStatus(), con.getResponseHeaders());
/* 430 */           buf.writeTo(os);
/*     */         } else {
/* 432 */           codec.encode(packet, os);
/*     */         } 
/*     */         
/*     */         try {
/* 436 */           os.close();
/* 437 */         } catch (IOException e) {
/* 438 */           throw new WebServiceException(e);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 442 */       if (con.getStatus() == 0)
/*     */       {
/*     */         
/* 445 */         con.setStatus(responseMessage.isFault() ? 500 : 200);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 450 */       if (isClientErrorStatus(con.getStatus())) {
/* 451 */         OutputStream os = con.getOutput();
/* 452 */         if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 453 */           ByteArrayBuffer buf = new ByteArrayBuffer();
/* 454 */           writeClientError(con.getStatus(), (OutputStream)buf, packet);
/* 455 */           dump(buf, "HTTP response " + con.getStatus(), con.getResponseHeaders());
/* 456 */           buf.writeTo(os);
/*     */         } else {
/* 458 */           writeClientError(con.getStatus(), os, packet);
/*     */         } 
/* 460 */         os.close();
/*     */         
/*     */         return;
/*     */       } 
/* 464 */       ContentType contentType = codec.getStaticContentType(packet);
/* 465 */       if (contentType != null) {
/* 466 */         con.setContentTypeResponseHeader(contentType.getContentType());
/* 467 */         OutputStream os = con.getProtocol().contains("1.1") ? con.getOutput() : (OutputStream)new Http10OutputStream(con);
/* 468 */         if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 469 */           ByteArrayBuffer buf = new ByteArrayBuffer();
/* 470 */           codec.encode(packet, (OutputStream)buf);
/* 471 */           dump(buf, "HTTP response " + con.getStatus(), con.getResponseHeaders());
/* 472 */           buf.writeTo(os);
/*     */         } else {
/* 474 */           codec.encode(packet, os);
/*     */         } 
/* 476 */         os.close();
/*     */       } else {
/*     */         
/* 479 */         ByteArrayBuffer buf = new ByteArrayBuffer();
/* 480 */         contentType = codec.encode(packet, (OutputStream)buf);
/* 481 */         con.setContentTypeResponseHeader(contentType.getContentType());
/* 482 */         if (dump || LOGGER.isLoggable(Level.FINER)) {
/* 483 */           dump(buf, "HTTP response " + con.getStatus(), con.getResponseHeaders());
/*     */         }
/* 485 */         OutputStream os = con.getOutput();
/* 486 */         buf.writeTo(os);
/* 487 */         os.close();
/*     */       } 
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
/*     */   
/*     */   private void addStickyCookie(WSHTTPConnection con) {
/* 505 */     if (this.stickyCookie) {
/* 506 */       String proxyJroute = con.getRequestHeader("proxy-jroute");
/* 507 */       if (proxyJroute == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 512 */       String jrouteId = con.getCookie("JROUTE");
/* 513 */       if (jrouteId == null || !jrouteId.equals(proxyJroute))
/*     */       {
/* 515 */         con.setCookie("JROUTE", proxyJroute);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addReplicaCookie(WSHTTPConnection con, Packet packet) {
/* 521 */     if (this.stickyCookie) {
/* 522 */       HaInfo haInfo = null;
/* 523 */       if (packet.supports("com.sun.xml.internal.ws.api.message.packet.hainfo")) {
/* 524 */         haInfo = (HaInfo)packet.get("com.sun.xml.internal.ws.api.message.packet.hainfo");
/*     */       }
/* 526 */       if (haInfo != null) {
/* 527 */         con.setCookie("METRO_KEY", haInfo.getKey());
/* 528 */         if (!this.disableJreplicaCookie) {
/* 529 */           con.setCookie("JREPLICA", haInfo.getReplicaInstance());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invokeAsync(WSHTTPConnection con) throws IOException {
/* 536 */     invokeAsync(con, NO_OP_COMPLETION_CALLBACK);
/*     */   }
/*     */   
/*     */   public void invokeAsync(final WSHTTPConnection con, final CompletionCallback callback) throws IOException {
/*     */     Packet request;
/* 541 */     if (handleGet(con)) {
/* 542 */       callback.onCompletion();
/*     */       return;
/*     */     } 
/* 545 */     final Pool<HttpToolkit> currentPool = getPool();
/* 546 */     final HttpToolkit tk = (HttpToolkit)currentPool.take();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 551 */       request = decodePacket(con, tk.codec);
/* 552 */     } catch (ExceptionHasMessage e) {
/* 553 */       LOGGER.log(Level.SEVERE, e.getMessage(), (Throwable)e);
/* 554 */       Packet response = new Packet();
/* 555 */       response.setMessage(e.getFaultMessage());
/* 556 */       encodePacket(response, con, tk.codec);
/* 557 */       currentPool.recycle(tk);
/* 558 */       con.close();
/* 559 */       callback.onCompletion();
/*     */       return;
/* 561 */     } catch (UnsupportedMediaException e) {
/* 562 */       LOGGER.log(Level.SEVERE, e.getMessage(), (Throwable)e);
/* 563 */       Packet response = new Packet();
/* 564 */       con.setStatus(415);
/* 565 */       encodePacket(response, con, tk.codec);
/* 566 */       currentPool.recycle(tk);
/* 567 */       con.close();
/* 568 */       callback.onCompletion();
/*     */       
/*     */       return;
/*     */     } 
/* 572 */     this.endpoint.process(request, new WSEndpoint.CompletionCallback()
/*     */         {
/*     */           public void onCompletion(@NotNull Packet response) {
/*     */             try {
/*     */               try {
/* 577 */                 HttpAdapter.this.encodePacket(response, con, tk.codec);
/* 578 */               } catch (IOException ioe) {
/* 579 */                 HttpAdapter.LOGGER.log(Level.SEVERE, ioe.getMessage(), ioe);
/*     */               } 
/* 581 */               currentPool.recycle(tk);
/*     */             } finally {
/* 583 */               con.close();
/* 584 */               callback.onCompletion();
/*     */             } 
/*     */           }
/*     */         }null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 592 */   public static final CompletionCallback NO_OP_COMPLETION_CALLBACK = new CompletionCallback()
/*     */     {
/*     */       public void onCompletion() {}
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final class AsyncTransport
/*     */     extends AbstractServerAsyncTransport<WSHTTPConnection>
/*     */   {
/*     */     public AsyncTransport() {
/* 607 */       super(HttpAdapter.this.endpoint);
/*     */     }
/*     */     
/*     */     public void handleAsync(WSHTTPConnection con) throws IOException {
/* 611 */       handle(con);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void encodePacket(WSHTTPConnection con, @NotNull Packet packet, @NotNull Codec codec) throws IOException {
/* 616 */       HttpAdapter.this.encodePacket(packet, con, codec);
/*     */     }
/*     */     @Nullable
/*     */     protected String getAcceptableMimeTypes(WSHTTPConnection con) {
/* 620 */       return null;
/*     */     }
/*     */     @Nullable
/*     */     protected TransportBackChannel getTransportBackChannel(WSHTTPConnection con) {
/* 624 */       return new HttpAdapter.Oneway(con);
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     protected PropertySet getPropertySet(WSHTTPConnection con) {
/* 629 */       return (PropertySet)con;
/*     */     }
/*     */     @NotNull
/*     */     protected WebServiceContextDelegate getWebServiceContextDelegate(WSHTTPConnection con) {
/* 633 */       return con.getWebServiceContextDelegate();
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Oneway implements TransportBackChannel {
/*     */     WSHTTPConnection con;
/*     */     boolean closed;
/*     */     
/*     */     Oneway(WSHTTPConnection con) {
/* 642 */       this.con = con;
/*     */     }
/*     */     
/*     */     public void close() {
/* 646 */       if (!this.closed) {
/* 647 */         this.closed = true;
/*     */         
/* 649 */         if (this.con.getStatus() == 0)
/*     */         {
/*     */           
/* 652 */           this.con.setStatus(202);
/*     */         }
/*     */         
/* 655 */         OutputStream output = null;
/*     */         try {
/* 657 */           output = this.con.getOutput();
/* 658 */         } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */         
/* 662 */         if (HttpAdapter.dump || HttpAdapter.LOGGER.isLoggable(Level.FINER)) {
/*     */           try {
/* 664 */             ByteArrayBuffer buf = new ByteArrayBuffer();
/* 665 */             HttpAdapter.dump(buf, "HTTP response " + this.con.getStatus(), this.con.getResponseHeaders());
/* 666 */           } catch (Exception e) {
/* 667 */             throw new WebServiceException(e.toString(), e);
/*     */           } 
/*     */         }
/*     */         
/* 671 */         if (output != null) {
/*     */           try {
/* 673 */             output.close();
/* 674 */           } catch (IOException e) {
/* 675 */             throw new WebServiceException(e);
/*     */           } 
/*     */         }
/* 678 */         this.con.close();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 683 */   final class HttpToolkit extends Adapter.Toolkit { HttpToolkit() { super(HttpAdapter.this); } public void handle(WSHTTPConnection con) throws IOException {
/*     */       try {
/*     */         Packet packet;
/* 686 */         boolean invoke = false;
/*     */         
/*     */         try {
/* 689 */           packet = HttpAdapter.this.decodePacket(con, this.codec);
/* 690 */           invoke = true;
/* 691 */         } catch (Exception e) {
/* 692 */           packet = new Packet();
/* 693 */           if (e instanceof ExceptionHasMessage) {
/* 694 */             HttpAdapter.LOGGER.log(Level.SEVERE, e.getMessage(), e);
/* 695 */             packet.setMessage(((ExceptionHasMessage)e).getFaultMessage());
/* 696 */           } else if (e instanceof UnsupportedMediaException) {
/* 697 */             HttpAdapter.LOGGER.log(Level.SEVERE, e.getMessage(), e);
/* 698 */             con.setStatus(415);
/*     */           } else {
/* 700 */             HttpAdapter.LOGGER.log(Level.SEVERE, e.getMessage(), e);
/* 701 */             con.setStatus(500);
/*     */           } 
/*     */         } 
/* 704 */         if (invoke) {
/*     */           try {
/* 706 */             packet = this.head.process(packet, con.getWebServiceContextDelegate(), packet.transportBackChannel);
/*     */           }
/* 708 */           catch (Throwable e) {
/* 709 */             HttpAdapter.LOGGER.log(Level.SEVERE, e.getMessage(), e);
/* 710 */             if (!con.isClosed()) {
/* 711 */               HttpAdapter.this.writeInternalServerError(con);
/*     */             }
/*     */             return;
/*     */           } 
/*     */         }
/* 716 */         HttpAdapter.this.encodePacket(packet, con, this.codec);
/*     */       } finally {
/* 718 */         if (!con.isClosed()) {
/* 719 */           if (HttpAdapter.LOGGER.isLoggable(Level.FINE)) {
/* 720 */             HttpAdapter.LOGGER.log(Level.FINE, "Closing HTTP Connection with status: {0}", Integer.valueOf(con.getStatus()));
/*     */           }
/* 722 */           con.close();
/*     */         } 
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isMetadataQuery(String query) {
/* 740 */     return (query != null && (query.equals("WSDL") || query.startsWith("wsdl") || query.startsWith("xsd=")));
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
/*     */   public void publishWSDL(@NotNull WSHTTPConnection con) throws IOException {
/* 753 */     con.getInput().close();
/*     */     
/* 755 */     SDDocument doc = this.wsdls.get(con.getQueryString());
/* 756 */     if (doc == null) {
/* 757 */       writeNotFoundErrorPage(con, "Invalid Request");
/*     */       
/*     */       return;
/*     */     } 
/* 761 */     con.setStatus(200);
/* 762 */     con.setContentTypeResponseHeader("text/xml;charset=utf-8");
/*     */     
/* 764 */     OutputStream os = con.getProtocol().contains("1.1") ? con.getOutput() : (OutputStream)new Http10OutputStream(con);
/*     */     
/* 766 */     PortAddressResolver portAddressResolver = getPortAddressResolver(con.getBaseAddress());
/* 767 */     DocumentAddressResolver resolver = getDocumentAddressResolver(portAddressResolver);
/*     */     
/* 769 */     doc.writeTo(portAddressResolver, resolver, os);
/* 770 */     os.close();
/*     */   }
/*     */   
/*     */   public PortAddressResolver getPortAddressResolver(String baseAddress) {
/* 774 */     return this.owner.createPortAddressResolver(baseAddress, this.endpoint.getImplementationClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public DocumentAddressResolver getDocumentAddressResolver(PortAddressResolver portAddressResolver) {
/* 779 */     final String address = portAddressResolver.getAddressFor(this.endpoint.getServiceName(), this.endpoint.getPortName().getLocalPart());
/* 780 */     assert address != null;
/* 781 */     return new DocumentAddressResolver()
/*     */       {
/*     */         public String getRelativeAddressFor(@NotNull SDDocument current, @NotNull SDDocument referenced)
/*     */         {
/* 785 */           assert HttpAdapter.this.revWsdls.containsKey(referenced);
/* 786 */           return address + '?' + (String)HttpAdapter.this.revWsdls.get(referenced);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Http10OutputStream
/*     */     extends ByteArrayBuffer
/*     */   {
/*     */     private final WSHTTPConnection con;
/*     */ 
/*     */     
/*     */     Http10OutputStream(WSHTTPConnection con) {
/* 799 */       this.con = con;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 804 */       super.close();
/* 805 */       this.con.setContentLengthResponseHeader(size());
/* 806 */       OutputStream os = this.con.getOutput();
/* 807 */       writeTo(os);
/* 808 */       os.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private void writeNotFoundErrorPage(WSHTTPConnection con, String message) throws IOException {
/* 813 */     con.setStatus(404);
/* 814 */     con.setContentTypeResponseHeader("text/html; charset=utf-8");
/*     */     
/* 816 */     PrintWriter out = new PrintWriter(new OutputStreamWriter(con.getOutput(), "UTF-8"));
/* 817 */     out.println("<html>");
/* 818 */     out.println("<head><title>");
/* 819 */     out.println(WsservletMessages.SERVLET_HTML_TITLE());
/* 820 */     out.println("</title></head>");
/* 821 */     out.println("<body>");
/* 822 */     out.println(WsservletMessages.SERVLET_HTML_NOT_FOUND(message));
/* 823 */     out.println("</body>");
/* 824 */     out.println("</html>");
/* 825 */     out.close();
/*     */   }
/*     */   
/*     */   private void writeInternalServerError(WSHTTPConnection con) throws IOException {
/* 829 */     con.setStatus(500);
/* 830 */     con.getOutput().close();
/*     */   }
/*     */   
/*     */   private static final class DummyList extends HttpAdapterList<HttpAdapter> { private DummyList() {}
/*     */     
/*     */     protected HttpAdapter createHttpAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
/* 836 */       return new HttpAdapter(endpoint, this, urlPattern);
/*     */     } }
/*     */ 
/*     */   
/*     */   private static void dump(ByteArrayBuffer buf, String caption, Map<String, List<String>> headers) throws IOException {
/* 841 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 842 */     PrintWriter pw = new PrintWriter(baos, true);
/* 843 */     pw.println("---[" + caption + "]---");
/* 844 */     if (headers != null) {
/* 845 */       for (Map.Entry<String, List<String>> header : headers.entrySet()) {
/* 846 */         if (((List)header.getValue()).isEmpty()) {
/*     */ 
/*     */           
/* 849 */           pw.println(header.getValue()); continue;
/*     */         } 
/* 851 */         for (String value : header.getValue()) {
/* 852 */           pw.println((String)header.getKey() + ": " + value);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 857 */     if (buf.size() > dump_threshold) {
/* 858 */       byte[] b = buf.getRawData();
/* 859 */       baos.write(b, 0, dump_threshold);
/* 860 */       pw.println();
/* 861 */       pw.println(WsservletMessages.MESSAGE_TOO_LONG(HttpAdapter.class.getName() + ".dumpTreshold"));
/*     */     } else {
/* 863 */       buf.writeTo(baos);
/*     */     } 
/* 865 */     pw.println("--------------------");
/*     */     
/* 867 */     String msg = baos.toString();
/* 868 */     if (dump) {
/* 869 */       System.out.println(msg);
/*     */     }
/* 871 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 872 */       LOGGER.log(Level.FINER, msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeWebServicesHtmlPage(WSHTTPConnection con) throws IOException {
/* 880 */     if (!publishStatusPage) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 886 */     con.getInput().close();
/*     */ 
/*     */     
/* 889 */     con.setStatus(200);
/* 890 */     con.setContentTypeResponseHeader("text/html; charset=utf-8");
/*     */     
/* 892 */     PrintWriter out = new PrintWriter(new OutputStreamWriter(con.getOutput(), "UTF-8"));
/* 893 */     out.println("<html>");
/* 894 */     out.println("<head><title>");
/*     */     
/* 896 */     out.println(WsservletMessages.SERVLET_HTML_TITLE());
/* 897 */     out.println("</title></head>");
/* 898 */     out.println("<body>");
/*     */     
/* 900 */     out.println(WsservletMessages.SERVLET_HTML_TITLE_2());
/*     */ 
/*     */     
/* 903 */     Module module = (Module)getEndpoint().getContainer().getSPI(Module.class);
/* 904 */     List<BoundEndpoint> endpoints = Collections.emptyList();
/* 905 */     if (module != null) {
/* 906 */       endpoints = module.getBoundEndpoints();
/*     */     }
/*     */     
/* 909 */     if (endpoints.isEmpty()) {
/*     */       
/* 911 */       out.println(WsservletMessages.SERVLET_HTML_NO_INFO_AVAILABLE());
/*     */     } else {
/* 913 */       out.println("<table width='100%' border='1'>");
/* 914 */       out.println("<tr>");
/* 915 */       out.println("<td>");
/*     */       
/* 917 */       out.println(WsservletMessages.SERVLET_HTML_COLUMN_HEADER_PORT_NAME());
/* 918 */       out.println("</td>");
/*     */       
/* 920 */       out.println("<td>");
/*     */       
/* 922 */       out.println(WsservletMessages.SERVLET_HTML_COLUMN_HEADER_INFORMATION());
/* 923 */       out.println("</td>");
/* 924 */       out.println("</tr>");
/*     */       
/* 926 */       for (BoundEndpoint a : endpoints) {
/* 927 */         String endpointAddress = a.getAddress(con.getBaseAddress()).toString();
/* 928 */         out.println("<tr>");
/*     */         
/* 930 */         out.println("<td>");
/* 931 */         out.println(WsservletMessages.SERVLET_HTML_ENDPOINT_TABLE(a
/* 932 */               .getEndpoint().getServiceName(), a
/* 933 */               .getEndpoint().getPortName()));
/*     */         
/* 935 */         out.println("</td>");
/*     */         
/* 937 */         out.println("<td>");
/* 938 */         out.println(WsservletMessages.SERVLET_HTML_INFORMATION_TABLE(endpointAddress, a
/*     */               
/* 940 */               .getEndpoint().getImplementationClass().getName()));
/*     */         
/* 942 */         out.println("</td>");
/*     */         
/* 944 */         out.println("</tr>");
/*     */       } 
/* 946 */       out.println("</table>");
/*     */     } 
/* 948 */     out.println("</body>");
/* 949 */     out.println("</html>");
/* 950 */     out.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static volatile boolean dump = false;
/*     */ 
/*     */   
/* 958 */   public static volatile int dump_threshold = 4096;
/*     */   
/*     */   public static volatile boolean publishStatusPage = true;
/*     */   
/*     */   public static synchronized void setPublishStatus(boolean publish) {
/* 963 */     publishStatusPage = publish;
/*     */   }
/*     */   
/*     */   static {
/*     */     try {
/* 968 */       dump = Boolean.getBoolean(HttpAdapter.class.getName() + ".dump");
/* 969 */     } catch (SecurityException se) {
/* 970 */       if (LOGGER.isLoggable(Level.CONFIG)) {
/* 971 */         LOGGER.log(Level.CONFIG, "Cannot read ''{0}'' property, using defaults.", new Object[] { HttpAdapter.class
/* 972 */               .getName() + ".dump" });
/*     */       }
/*     */     } 
/*     */     try {
/* 976 */       dump_threshold = Integer.getInteger(HttpAdapter.class.getName() + ".dumpTreshold", 4096).intValue();
/* 977 */     } catch (SecurityException se) {
/* 978 */       if (LOGGER.isLoggable(Level.CONFIG)) {
/* 979 */         LOGGER.log(Level.CONFIG, "Cannot read ''{0}'' property, using defaults.", new Object[] { HttpAdapter.class
/* 980 */               .getName() + ".dumpTreshold" });
/*     */       }
/*     */     } 
/*     */     try {
/* 984 */       setPublishStatus(Boolean.getBoolean(HttpAdapter.class.getName() + ".publishStatusPage"));
/* 985 */     } catch (SecurityException se) {
/* 986 */       if (LOGGER.isLoggable(Level.CONFIG)) {
/* 987 */         LOGGER.log(Level.CONFIG, "Cannot read ''{0}'' property, using defaults.", new Object[] { HttpAdapter.class
/* 988 */               .getName() + ".publishStatusPage" });
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setDump(boolean dumpMessages) {
/* 994 */     dump = dumpMessages;
/*     */   }
/*     */   
/*     */   public static interface CompletionCallback {
/*     */     void onCompletion();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/HttpAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */