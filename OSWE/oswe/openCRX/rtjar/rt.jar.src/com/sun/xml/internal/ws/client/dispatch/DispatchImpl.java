/*     */ package com.sun.xml.internal.ws.client.dispatch;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.internal.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.api.server.ContainerResolver;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.client.AsyncInvoker;
/*     */ import com.sun.xml.internal.ws.client.AsyncResponseImpl;
/*     */ import com.sun.xml.internal.ws.client.RequestContext;
/*     */ import com.sun.xml.internal.ws.client.ResponseContext;
/*     */ import com.sun.xml.internal.ws.client.ResponseContextReceiver;
/*     */ import com.sun.xml.internal.ws.client.Stub;
/*     */ import com.sun.xml.internal.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.internal.ws.encoding.soap.DeserializationException;
/*     */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.internal.ws.message.AttachmentSetImpl;
/*     */ import com.sun.xml.internal.ws.message.DataHandlerAttachment;
/*     */ import com.sun.xml.internal.ws.resources.DispatchMessages;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.AsyncHandler;
/*     */ import javax.xml.ws.Dispatch;
/*     */ import javax.xml.ws.Response;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DispatchImpl<T>
/*     */   extends Stub
/*     */   implements Dispatch<T>
/*     */ {
/*  94 */   private static final Logger LOGGER = Logger.getLogger(DispatchImpl.class.getName());
/*     */   
/*     */   final Service.Mode mode;
/*     */   
/*     */   final SOAPVersion soapVersion;
/*     */   
/*     */   final boolean allowFaultResponseMsg;
/*     */   
/*     */   static final long AWAIT_TERMINATION_TIME = 800L;
/*     */   
/*     */   static final String HTTP_REQUEST_METHOD_GET = "GET";
/*     */   
/*     */   static final String HTTP_REQUEST_METHOD_POST = "POST";
/*     */   static final String HTTP_REQUEST_METHOD_PUT = "PUT";
/*     */   
/*     */   @Deprecated
/*     */   protected DispatchImpl(QName port, Service.Mode mode, WSServiceDelegate owner, Tube pipe, BindingImpl binding, @Nullable WSEndpointReference epr) {
/* 111 */     super(port, owner, pipe, binding, (owner.getWsdlService() != null) ? owner.getWsdlService().get(port) : null, owner.getEndpointAddress(port), epr);
/* 112 */     this.mode = mode;
/* 113 */     this.soapVersion = binding.getSOAPVersion();
/* 114 */     this.allowFaultResponseMsg = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DispatchImpl(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, @Nullable WSEndpointReference epr) {
/* 123 */     this(portInfo, mode, binding, epr, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DispatchImpl(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, @Nullable WSEndpointReference epr, boolean allowFaultResponseMsg) {
/* 133 */     this(portInfo, mode, binding, (Tube)null, epr, allowFaultResponseMsg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DispatchImpl(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, Tube pipe, @Nullable WSEndpointReference epr, boolean allowFaultResponseMsg) {
/* 144 */     super(portInfo, binding, pipe, portInfo.getEndpointAddress(), epr);
/* 145 */     this.mode = mode;
/* 146 */     this.soapVersion = binding.getSOAPVersion();
/* 147 */     this.allowFaultResponseMsg = allowFaultResponseMsg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DispatchImpl(WSPortInfo portInfo, Service.Mode mode, Tube pipe, BindingImpl binding, @Nullable WSEndpointReference epr, boolean allowFaultResponseMsg) {
/* 158 */     super(portInfo, binding, pipe, portInfo.getEndpointAddress(), epr);
/* 159 */     this.mode = mode;
/* 160 */     this.soapVersion = binding.getSOAPVersion();
/* 161 */     this.allowFaultResponseMsg = allowFaultResponseMsg;
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
/*     */   public final Response<T> invokeAsync(T param) {
/* 178 */     Container old = ContainerResolver.getDefault().enterContainer(this.owner.getContainer());
/*     */     try {
/* 180 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 181 */         dumpParam(param, "invokeAsync(T)");
/*     */       }
/* 183 */       AsyncInvoker invoker = new DispatchAsyncInvoker(param);
/* 184 */       AsyncResponseImpl<T> ft = new AsyncResponseImpl((Runnable)invoker, null);
/* 185 */       invoker.setReceiver(ft);
/* 186 */       ft.run();
/* 187 */       return (Response<T>)ft;
/*     */     } finally {
/* 189 */       ContainerResolver.getDefault().exitContainer(old);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void dumpParam(T param, String method) {
/* 194 */     if (param instanceof Packet) {
/* 195 */       Packet message = (Packet)param;
/*     */ 
/*     */ 
/*     */       
/* 199 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 200 */         AddressingVersion av = getBinding().getAddressingVersion();
/* 201 */         SOAPVersion sv = getBinding().getSOAPVersion();
/*     */ 
/*     */         
/* 204 */         String action = (av != null && message.getMessage() != null) ? AddressingUtils.getAction(message.getMessage().getHeaders(), av, sv) : null;
/*     */ 
/*     */         
/* 207 */         String msgId = (av != null && message.getMessage() != null) ? AddressingUtils.getMessageID(message.getMessage().getHeaders(), av, sv) : null;
/* 208 */         LOGGER.fine("In DispatchImpl." + method + " for message with action: " + action + " and msg ID: " + msgId + " msg: " + message.getMessage());
/*     */         
/* 210 */         if (message.getMessage() == null)
/* 211 */           LOGGER.fine("Dispatching null message for action: " + action + " and msg ID: " + msgId); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public final Future<?> invokeAsync(T param, AsyncHandler<T> asyncHandler) {
/* 217 */     Container old = ContainerResolver.getDefault().enterContainer(this.owner.getContainer());
/*     */     try {
/* 219 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 220 */         dumpParam(param, "invokeAsync(T, AsyncHandler<T>)");
/*     */       }
/* 222 */       AsyncInvoker invoker = new DispatchAsyncInvoker(param);
/* 223 */       AsyncResponseImpl<T> ft = new AsyncResponseImpl((Runnable)invoker, asyncHandler);
/* 224 */       invoker.setReceiver(ft);
/* 225 */       invoker.setNonNullAsyncHandlerGiven((asyncHandler != null));
/*     */       
/* 227 */       ft.run();
/* 228 */       return (Future<?>)ft;
/*     */     } finally {
/* 230 */       ContainerResolver.getDefault().exitContainer(old);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T doInvoke(T in, RequestContext rc, ResponseContextReceiver receiver) {
/* 241 */     Packet response = null;
/*     */     try {
/*     */       try {
/* 244 */         checkNullAllowed(in, rc, (WSBinding)this.binding, this.mode);
/*     */         
/* 246 */         Packet message = createPacket(in);
/* 247 */         message.setState(Packet.State.ClientRequest);
/* 248 */         resolveEndpointAddress(message, rc);
/* 249 */         setProperties(message, true);
/* 250 */         response = process(message, rc, receiver);
/* 251 */         Message msg = response.getMessage();
/*     */ 
/*     */         
/* 254 */         if (msg != null && msg.isFault() && !this.allowFaultResponseMsg) {
/*     */           
/* 256 */           SOAPFaultBuilder faultBuilder = SOAPFaultBuilder.create(msg);
/*     */ 
/*     */           
/* 259 */           throw (SOAPFaultException)faultBuilder.createException(null);
/*     */         } 
/* 261 */       } catch (JAXBException e) {
/*     */         
/* 263 */         throw new DeserializationException(DispatchMessages.INVALID_RESPONSE_DESERIALIZATION(), new Object[] { e });
/* 264 */       } catch (WebServiceException e) {
/*     */         
/* 266 */         throw e;
/* 267 */       } catch (Throwable e) {
/*     */ 
/*     */ 
/*     */         
/* 271 */         throw new WebServiceException(e);
/*     */       } 
/*     */       
/* 274 */       return toReturnValue(response);
/*     */     } finally {
/*     */       
/* 277 */       if (response != null && response.transportBackChannel != null)
/* 278 */         response.transportBackChannel.close(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public final T invoke(T in) {
/* 283 */     Container old = ContainerResolver.getDefault().enterContainer(this.owner.getContainer());
/*     */     try {
/* 285 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 286 */         dumpParam(in, "invoke(T)");
/*     */       }
/*     */       
/* 289 */       return doInvoke(in, this.requestContext, (ResponseContextReceiver)this);
/*     */     } finally {
/* 291 */       ContainerResolver.getDefault().exitContainer(old);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void invokeOneWay(T in) {
/* 296 */     Container old = ContainerResolver.getDefault().enterContainer(this.owner.getContainer());
/*     */     try {
/* 298 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 299 */         dumpParam(in, "invokeOneWay(T)");
/*     */       }
/*     */       
/*     */       try {
/* 303 */         checkNullAllowed(in, this.requestContext, (WSBinding)this.binding, this.mode);
/*     */         
/* 305 */         Packet request = createPacket(in);
/* 306 */         request.setState(Packet.State.ClientRequest);
/* 307 */         setProperties(request, false);
/* 308 */         process(request, this.requestContext, (ResponseContextReceiver)this);
/* 309 */       } catch (WebServiceException e) {
/*     */         
/* 311 */         throw e;
/* 312 */       } catch (Throwable e) {
/*     */ 
/*     */ 
/*     */         
/* 316 */         throw new WebServiceException(e);
/*     */       } 
/*     */     } finally {
/* 319 */       ContainerResolver.getDefault().exitContainer(old);
/*     */     } 
/*     */   }
/*     */   
/*     */   void setProperties(Packet packet, boolean expectReply) {
/* 324 */     packet.expectReply = Boolean.valueOf(expectReply);
/*     */   }
/*     */   
/*     */   static boolean isXMLHttp(@NotNull WSBinding binding) {
/* 328 */     return binding.getBindingId().equals(BindingID.XML_HTTP);
/*     */   }
/*     */   
/*     */   static boolean isPAYLOADMode(@NotNull Service.Mode mode) {
/* 332 */     return (mode == Service.Mode.PAYLOAD);
/*     */   }
/*     */ 
/*     */   
/*     */   static void checkNullAllowed(@Nullable Object in, RequestContext rc, WSBinding binding, Service.Mode mode) {
/* 337 */     if (in != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 342 */     if (isXMLHttp(binding)) {
/* 343 */       if (methodNotOk(rc)) {
/* 344 */         throw new WebServiceException(DispatchMessages.INVALID_NULLARG_XMLHTTP_REQUEST_METHOD("POST", "GET"));
/*     */       }
/* 346 */     } else if (mode == Service.Mode.MESSAGE) {
/* 347 */       throw new WebServiceException(DispatchMessages.INVALID_NULLARG_SOAP_MSGMODE(mode.name(), Service.Mode.PAYLOAD.toString()));
/*     */     } 
/*     */   }
/*     */   
/*     */   static boolean methodNotOk(@NotNull RequestContext rc) {
/* 352 */     String requestMethod = (String)rc.get("javax.xml.ws.http.request.method");
/* 353 */     String request = (requestMethod == null) ? "POST" : requestMethod;
/*     */     
/* 355 */     return ("POST".equalsIgnoreCase(request) || "PUT".equalsIgnoreCase(request));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void checkValidSOAPMessageDispatch(WSBinding binding, Service.Mode mode) {
/* 360 */     if (isXMLHttp(binding))
/* 361 */       throw new WebServiceException(DispatchMessages.INVALID_SOAPMESSAGE_DISPATCH_BINDING("http://www.w3.org/2004/08/wsdl/http", "http://schemas.xmlsoap.org/wsdl/soap/http or http://www.w3.org/2003/05/soap/bindings/HTTP/")); 
/* 362 */     if (isPAYLOADMode(mode)) {
/* 363 */       throw new WebServiceException(DispatchMessages.INVALID_SOAPMESSAGE_DISPATCH_MSGMODE(mode.name(), Service.Mode.MESSAGE.toString()));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void checkValidDataSourceDispatch(WSBinding binding, Service.Mode mode) {
/* 368 */     if (!isXMLHttp(binding))
/* 369 */       throw new WebServiceException(DispatchMessages.INVALID_DATASOURCE_DISPATCH_BINDING("SOAP/HTTP", "http://www.w3.org/2004/08/wsdl/http")); 
/* 370 */     if (isPAYLOADMode(mode))
/* 371 */       throw new WebServiceException(DispatchMessages.INVALID_DATASOURCE_DISPATCH_MSGMODE(mode.name(), Service.Mode.MESSAGE.toString())); 
/*     */   }
/*     */   @NotNull
/*     */   public final QName getPortName() {
/* 375 */     return this.portname;
/*     */   }
/*     */   void resolveEndpointAddress(@NotNull Packet message, @NotNull RequestContext requestContext) {
/*     */     String endpoint;
/* 379 */     boolean p = message.packetTakesPriorityOverRequestContext;
/*     */ 
/*     */ 
/*     */     
/* 383 */     if (p && message.endpointAddress != null) {
/* 384 */       endpoint = message.endpointAddress.toString();
/*     */     } else {
/* 386 */       endpoint = (String)requestContext.get("javax.xml.ws.service.endpoint.address");
/*     */     } 
/*     */     
/* 389 */     if (endpoint == null) {
/* 390 */       if (message.endpointAddress == null) throw new WebServiceException(DispatchMessages.INVALID_NULLARG_URI()); 
/* 391 */       endpoint = message.endpointAddress.toString();
/*     */     } 
/*     */     
/* 394 */     String pathInfo = null;
/* 395 */     String queryString = null;
/* 396 */     if (p && message.invocationProperties.get("javax.xml.ws.http.request.pathinfo") != null) {
/* 397 */       pathInfo = (String)message.invocationProperties.get("javax.xml.ws.http.request.pathinfo");
/* 398 */     } else if (requestContext.get("javax.xml.ws.http.request.pathinfo") != null) {
/* 399 */       pathInfo = (String)requestContext.get("javax.xml.ws.http.request.pathinfo");
/*     */     } 
/*     */     
/* 402 */     if (p && message.invocationProperties.get("javax.xml.ws.http.request.querystring") != null) {
/* 403 */       queryString = (String)message.invocationProperties.get("javax.xml.ws.http.request.querystring");
/* 404 */     } else if (requestContext.get("javax.xml.ws.http.request.querystring") != null) {
/* 405 */       queryString = (String)requestContext.get("javax.xml.ws.http.request.querystring");
/*     */     } 
/*     */     
/* 408 */     if (pathInfo != null || queryString != null) {
/* 409 */       pathInfo = checkPath(pathInfo);
/* 410 */       queryString = checkQuery(queryString);
/* 411 */       if (endpoint != null) {
/*     */         try {
/* 413 */           URI endpointURI = new URI(endpoint);
/* 414 */           endpoint = resolveURI(endpointURI, pathInfo, queryString);
/* 415 */         } catch (URISyntaxException e) {
/* 416 */           throw new WebServiceException(DispatchMessages.INVALID_URI(endpoint));
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 425 */     requestContext.put("javax.xml.ws.service.endpoint.address", endpoint);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   protected String resolveURI(@NotNull URI endpointURI, @Nullable String pathInfo, @Nullable String queryString) {
/* 431 */     String query = null;
/* 432 */     String fragment = null;
/* 433 */     if (queryString != null) {
/*     */       URI result;
/*     */       try {
/* 436 */         URI tp = new URI(null, null, endpointURI.getPath(), queryString, null);
/* 437 */         result = endpointURI.resolve(tp);
/* 438 */       } catch (URISyntaxException e) {
/* 439 */         throw new WebServiceException(DispatchMessages.INVALID_QUERY_STRING(queryString));
/*     */       } 
/* 441 */       query = result.getQuery();
/* 442 */       fragment = result.getFragment();
/*     */     } 
/*     */     
/* 445 */     String path = (pathInfo != null) ? pathInfo : endpointURI.getPath();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 454 */       StringBuilder spec = new StringBuilder();
/* 455 */       if (path != null) {
/* 456 */         spec.append(path);
/*     */       }
/* 458 */       if (query != null) {
/* 459 */         spec.append("?");
/* 460 */         spec.append(query);
/*     */       } 
/* 462 */       if (fragment != null) {
/* 463 */         spec.append("#");
/* 464 */         spec.append(fragment);
/*     */       } 
/* 466 */       return (new URL(endpointURI.toURL(), spec.toString())).toExternalForm();
/* 467 */     } catch (MalformedURLException e) {
/* 468 */       throw new WebServiceException(DispatchMessages.INVALID_URI_RESOLUTION(path));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String checkPath(@Nullable String path) {
/* 474 */     return (path == null || path.startsWith("/")) ? path : ("/" + path);
/*     */   }
/*     */   
/*     */   private static String checkQuery(@Nullable String query) {
/* 478 */     if (query == null) return null;
/*     */     
/* 480 */     if (query.indexOf('?') == 0)
/* 481 */       throw new WebServiceException(DispatchMessages.INVALID_QUERY_LEADING_CHAR(query)); 
/* 482 */     return query;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AttachmentSet setOutboundAttachments() {
/* 488 */     HashMap<String, DataHandler> attachments = (HashMap<String, DataHandler>)getRequestContext().get("javax.xml.ws.binding.attachments.outbound");
/*     */     
/* 490 */     if (attachments != null) {
/* 491 */       List<Attachment> alist = new ArrayList<>();
/* 492 */       for (Map.Entry<String, DataHandler> att : attachments.entrySet()) {
/* 493 */         DataHandlerAttachment dha = new DataHandlerAttachment(att.getKey(), att.getValue());
/* 494 */         alist.add(dha);
/*     */       } 
/* 496 */       return (AttachmentSet)new AttachmentSetImpl(alist);
/*     */     } 
/* 498 */     return (AttachmentSet)new AttachmentSetImpl();
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
/*     */   private class Invoker
/*     */     implements Callable
/*     */   {
/*     */     private final T param;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 521 */     private final RequestContext rc = DispatchImpl.this.requestContext.copy();
/*     */ 
/*     */ 
/*     */     
/*     */     private ResponseContextReceiver receiver;
/*     */ 
/*     */ 
/*     */     
/*     */     Invoker(T param) {
/* 530 */       this.param = param;
/*     */     }
/*     */     
/*     */     public T call() throws Exception {
/* 534 */       if (DispatchImpl.LOGGER.isLoggable(Level.FINE)) {
/* 535 */         DispatchImpl.this.dumpParam(this.param, "call()");
/*     */       }
/* 537 */       return DispatchImpl.this.doInvoke(this.param, this.rc, this.receiver);
/*     */     }
/*     */     
/*     */     void setReceiver(ResponseContextReceiver receiver) {
/* 541 */       this.receiver = receiver;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class DispatchAsyncInvoker
/*     */     extends AsyncInvoker
/*     */   {
/*     */     private final T param;
/*     */     
/* 552 */     private final RequestContext rc = DispatchImpl.this.requestContext.copy();
/*     */     
/*     */     DispatchAsyncInvoker(T param) {
/* 555 */       this.param = param;
/*     */     }
/*     */     
/*     */     public void do_run() {
/* 559 */       DispatchImpl.checkNullAllowed(this.param, this.rc, (WSBinding)DispatchImpl.this.binding, DispatchImpl.this.mode);
/* 560 */       Packet message = DispatchImpl.this.createPacket(this.param);
/* 561 */       message.setState(Packet.State.ClientRequest);
/* 562 */       message.nonNullAsyncHandlerGiven = Boolean.valueOf(this.nonNullAsyncHandlerGiven);
/* 563 */       DispatchImpl.this.resolveEndpointAddress(message, this.rc);
/* 564 */       DispatchImpl.this.setProperties(message, true);
/*     */       
/* 566 */       String action = null;
/* 567 */       String msgId = null;
/* 568 */       if (DispatchImpl.LOGGER.isLoggable(Level.FINE)) {
/* 569 */         AddressingVersion av = DispatchImpl.this.getBinding().getAddressingVersion();
/* 570 */         SOAPVersion sv = DispatchImpl.this.getBinding().getSOAPVersion();
/*     */ 
/*     */         
/* 573 */         action = (av != null && message.getMessage() != null) ? AddressingUtils.getAction(message.getMessage().getHeaders(), av, sv) : null;
/*     */ 
/*     */         
/* 576 */         msgId = (av != null && message.getMessage() != null) ? AddressingUtils.getMessageID(message.getMessage().getHeaders(), av, sv) : null;
/* 577 */         DispatchImpl.LOGGER.fine("In DispatchAsyncInvoker.do_run for async message with action: " + action + " and msg ID: " + msgId);
/*     */       } 
/*     */       
/* 580 */       final String actionUse = action;
/* 581 */       final String msgIdUse = msgId;
/*     */       
/* 583 */       Fiber.CompletionCallback callback = new Fiber.CompletionCallback()
/*     */         {
/*     */           public void onCompletion(@NotNull Packet response) {
/* 586 */             if (DispatchImpl.LOGGER.isLoggable(Level.FINE)) {
/* 587 */               DispatchImpl.LOGGER.fine("Done with processAsync in DispatchAsyncInvoker.do_run, and setting response for async message with action: " + actionUse + " and msg ID: " + msgIdUse);
/*     */             }
/*     */             
/* 590 */             Message msg = response.getMessage();
/*     */             
/* 592 */             if (DispatchImpl.LOGGER.isLoggable(Level.FINE)) {
/* 593 */               DispatchImpl.LOGGER.fine("Done with processAsync in DispatchAsyncInvoker.do_run, and setting response for async message with action: " + actionUse + " and msg ID: " + msgIdUse + " msg: " + msg);
/*     */             }
/*     */             
/*     */             try {
/* 597 */               if (msg != null && msg.isFault() && !DispatchImpl.this.allowFaultResponseMsg) {
/*     */                 
/* 599 */                 SOAPFaultBuilder faultBuilder = SOAPFaultBuilder.create(msg);
/*     */ 
/*     */                 
/* 602 */                 throw (SOAPFaultException)faultBuilder.createException(null);
/*     */               } 
/* 604 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.setResponseContext(new ResponseContext(response));
/* 605 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.set(DispatchImpl.this.toReturnValue(response), null);
/* 606 */             } catch (JAXBException e) {
/*     */               
/* 608 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.set(null, (Throwable)new DeserializationException(DispatchMessages.INVALID_RESPONSE_DESERIALIZATION(), new Object[] { e }));
/* 609 */             } catch (WebServiceException e) {
/*     */               
/* 611 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.set(null, (Throwable)e);
/* 612 */             } catch (Throwable e) {
/*     */ 
/*     */ 
/*     */               
/* 616 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.set(null, (Throwable)new WebServiceException(e));
/*     */             } 
/*     */           }
/*     */           public void onCompletion(@NotNull Throwable error) {
/* 620 */             if (DispatchImpl.LOGGER.isLoggable(Level.FINE)) {
/* 621 */               DispatchImpl.LOGGER.fine("Done with processAsync in DispatchAsyncInvoker.do_run, and setting response for async message with action: " + actionUse + " and msg ID: " + msgIdUse + " Throwable: " + error.toString());
/*     */             }
/* 623 */             if (error instanceof WebServiceException) {
/* 624 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.set(null, error);
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 629 */               DispatchImpl.DispatchAsyncInvoker.this.responseImpl.set(null, (Throwable)new WebServiceException(error));
/*     */             } 
/*     */           }
/*     */         };
/* 633 */       DispatchImpl.this.processAsync(this.responseImpl, message, this.rc, callback);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setOutboundHeaders(Object... headers) {
/* 638 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Dispatch<Source> createSourceDispatch(QName port, Service.Mode mode, WSServiceDelegate owner, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/* 647 */     if (isXMLHttp((WSBinding)binding)) {
/* 648 */       return new RESTSourceDispatch(port, mode, owner, pipe, binding, epr);
/*     */     }
/* 650 */     return new SOAPSourceDispatch(port, mode, owner, pipe, binding, epr);
/*     */   }
/*     */   
/*     */   public static Dispatch<Source> createSourceDispatch(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
/* 654 */     if (isXMLHttp((WSBinding)binding)) {
/* 655 */       return new RESTSourceDispatch(portInfo, mode, binding, epr);
/*     */     }
/* 657 */     return new SOAPSourceDispatch(portInfo, mode, binding, epr);
/*     */   }
/*     */   
/*     */   abstract Packet createPacket(T paramT);
/*     */   
/*     */   abstract T toReturnValue(Packet paramPacket);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/dispatch/DispatchImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */