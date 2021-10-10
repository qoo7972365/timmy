/*     */ package com.sun.xml.internal.ws.addressing;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.PropertySet;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.addressing.model.ActionNotSupportedException;
/*     */ import com.sun.xml.internal.ws.addressing.model.InvalidAddressingHeaderException;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.NonAnonymousResponseProcessor;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.api.message.Messages;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.client.Stub;
/*     */ import com.sun.xml.internal.ws.message.FaultDetailHeader;
/*     */ import com.sun.xml.internal.ws.resources.AddressingMessages;
/*     */ import java.net.URI;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPFault;
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
/*     */ public class WsaServerTube
/*     */   extends WsaTube
/*     */ {
/*     */   private WSEndpoint endpoint;
/*     */   private WSEndpointReference replyTo;
/*     */   private WSEndpointReference faultTo;
/*     */   private boolean isAnonymousRequired = false;
/*     */   protected boolean isEarlyBackchannelCloseAllowed = true;
/*     */   private WSDLBoundOperation wbo;
/*     */   public static final String REQUEST_MESSAGE_ID = "com.sun.xml.internal.ws.addressing.request.messageID";
/*     */   
/*     */   public WsaServerTube(WSEndpoint endpoint, @NotNull WSDLPort wsdlPort, WSBinding binding, Tube next) {
/*  84 */     super(wsdlPort, binding, next);
/*  85 */     this.endpoint = endpoint;
/*     */   }
/*     */   
/*     */   public WsaServerTube(WsaServerTube that, TubeCloner cloner) {
/*  89 */     super(that, cloner);
/*  90 */     this.endpoint = that.endpoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public WsaServerTube copy(TubeCloner cloner) {
/*  95 */     return new WsaServerTube(this, cloner);
/*     */   }
/*     */   @NotNull
/*     */   public NextAction processRequest(Packet request) {
/*     */     String msgId;
/* 100 */     Message msg = request.getMessage();
/* 101 */     if (msg == null) {
/* 102 */       return doInvoke(this.next, request);
/*     */     }
/*     */ 
/*     */     
/* 106 */     request.addSatellite((PropertySet)new WsaPropertyBag(this.addressingVersion, this.soapVersion, request));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     MessageHeaders hl = request.getMessage().getHeaders();
/*     */     
/*     */     try {
/* 115 */       this.replyTo = AddressingUtils.getReplyTo(hl, this.addressingVersion, this.soapVersion);
/* 116 */       this.faultTo = AddressingUtils.getFaultTo(hl, this.addressingVersion, this.soapVersion);
/* 117 */       msgId = AddressingUtils.getMessageID(hl, this.addressingVersion, this.soapVersion);
/* 118 */     } catch (InvalidAddressingHeaderException e) {
/*     */       
/* 120 */       LOGGER.log(Level.WARNING, this.addressingVersion.getInvalidMapText() + ", Problem header:" + e.getProblemHeader() + ", Reason: " + e.getSubsubcode(), (Throwable)e);
/*     */ 
/*     */       
/* 123 */       hl.remove(e.getProblemHeader());
/*     */       
/* 125 */       SOAPFault soapFault = this.helper.createInvalidAddressingHeaderFault(e, this.addressingVersion);
/*     */       
/* 127 */       if (this.wsdlPort != null && request.getMessage().isOneWay(this.wsdlPort)) {
/* 128 */         Packet packet = request.createServerResponse(null, this.wsdlPort, null, this.binding);
/* 129 */         return doReturnWith(packet);
/*     */       } 
/*     */       
/* 132 */       Message m = Messages.create(soapFault);
/* 133 */       if (this.soapVersion == SOAPVersion.SOAP_11) {
/* 134 */         FaultDetailHeader s11FaultDetailHeader = new FaultDetailHeader(this.addressingVersion, this.addressingVersion.problemHeaderQNameTag.getLocalPart(), e.getProblemHeader());
/* 135 */         m.getHeaders().add((Header)s11FaultDetailHeader);
/*     */       } 
/*     */       
/* 138 */       Packet response = request.createServerResponse(m, this.wsdlPort, null, this.binding);
/* 139 */       return doReturnWith(response);
/*     */     } 
/*     */ 
/*     */     
/* 143 */     if (this.replyTo == null) {
/* 144 */       this.replyTo = this.addressingVersion.anonymousEpr;
/*     */     }
/* 146 */     if (this.faultTo == null) {
/* 147 */       this.faultTo = this.replyTo;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     request.put("com.sun.xml.internal.ws.addressing.WsaPropertyBag.ReplyToFromRequest", this.replyTo);
/* 154 */     request.put("com.sun.xml.internal.ws.addressing.WsaPropertyBag.FaultToFromRequest", this.faultTo);
/* 155 */     request.put("com.sun.xml.internal.ws.addressing.WsaPropertyBag.MessageIdFromRequest", msgId);
/*     */     
/* 157 */     this.wbo = getWSDLBoundOperation(request);
/* 158 */     this.isAnonymousRequired = isAnonymousRequired(this.wbo);
/*     */     
/* 160 */     Packet p = validateInboundHeaders(request);
/*     */ 
/*     */     
/* 163 */     if (p.getMessage() == null) {
/* 164 */       return doReturnWith(p);
/*     */     }
/*     */ 
/*     */     
/* 168 */     if (p.getMessage().isFault()) {
/*     */ 
/*     */       
/* 171 */       if (this.isEarlyBackchannelCloseAllowed && !this.isAnonymousRequired && 
/*     */         
/* 173 */         !this.faultTo.isAnonymous() && request.transportBackChannel != null) {
/* 174 */         request.transportBackChannel.close();
/*     */       }
/* 176 */       return processResponse(p);
/*     */     } 
/*     */ 
/*     */     
/* 180 */     if (this.isEarlyBackchannelCloseAllowed && !this.isAnonymousRequired && 
/*     */       
/* 182 */       !this.replyTo.isAnonymous() && !this.faultTo.isAnonymous() && request.transportBackChannel != null)
/*     */     {
/* 184 */       request.transportBackChannel.close();
/*     */     }
/* 186 */     return doInvoke(this.next, p);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isAnonymousRequired(@Nullable WSDLBoundOperation wbo) {
/* 191 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkAnonymousSemantics(WSDLBoundOperation wbo, WSEndpointReference replyTo, WSEndpointReference faultTo) {}
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processException(Throwable t) {
/* 200 */     Packet response = Fiber.current().getPacket();
/* 201 */     ThrowableContainerPropertySet tc = (ThrowableContainerPropertySet)response.getSatellite(ThrowableContainerPropertySet.class);
/* 202 */     if (tc == null) {
/* 203 */       tc = new ThrowableContainerPropertySet(t);
/* 204 */       response.addSatellite((PropertySet)tc);
/* 205 */     } else if (t != tc.getThrowable()) {
/*     */ 
/*     */       
/* 208 */       tc.setThrowable(t);
/*     */     } 
/* 210 */     return processResponse(response.endpoint.createServiceResponseForException(tc, response, this.soapVersion, this.wsdlPort, response.endpoint
/* 211 */           .getSEIModel(), this.binding));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public NextAction processResponse(Packet response) {
/*     */     EndpointAddress adrs;
/* 217 */     Message msg = response.getMessage();
/* 218 */     if (msg == null) {
/* 219 */       return doReturnWith(response);
/*     */     }
/*     */     
/* 222 */     String to = AddressingUtils.getTo(msg.getHeaders(), this.addressingVersion, this.soapVersion);
/*     */     
/* 224 */     if (to != null) {
/* 225 */       this.replyTo = this.faultTo = new WSEndpointReference(to, this.addressingVersion);
/*     */     }
/*     */     
/* 228 */     if (this.replyTo == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 234 */       this
/* 235 */         .replyTo = (WSEndpointReference)response.get("com.sun.xml.internal.ws.addressing.WsaPropertyBag.ReplyToFromRequest");
/*     */     }
/*     */     
/* 238 */     if (this.faultTo == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 244 */       this
/* 245 */         .faultTo = (WSEndpointReference)response.get("com.sun.xml.internal.ws.addressing.WsaPropertyBag.FaultToFromRequest");
/*     */     }
/*     */     
/* 248 */     WSEndpointReference target = msg.isFault() ? this.faultTo : this.replyTo;
/* 249 */     if (target == null && response.proxy instanceof Stub) {
/* 250 */       target = ((Stub)response.proxy).getWSEndpointReference();
/*     */     }
/* 252 */     if (target == null || target.isAnonymous() || this.isAnonymousRequired) {
/* 253 */       return doReturnWith(response);
/*     */     }
/* 255 */     if (target.isNone()) {
/*     */       
/* 257 */       response.setMessage(null);
/* 258 */       return doReturnWith(response);
/*     */     } 
/*     */     
/* 261 */     if (this.wsdlPort != null && response.getMessage().isOneWay(this.wsdlPort)) {
/*     */       
/* 263 */       LOGGER.fine(AddressingMessages.NON_ANONYMOUS_RESPONSE_ONEWAY());
/* 264 */       return doReturnWith(response);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     if (this.wbo != null || response.soapAction == null) {
/*     */ 
/*     */       
/* 274 */       String action = response.getMessage().isFault() ? this.helper.getFaultAction(this.wbo, response) : this.helper.getOutputAction(this.wbo);
/*     */       
/* 276 */       if (response.soapAction == null || (action != null && 
/*     */         
/* 278 */         !action.equals("http://jax-ws.dev.java.net/addressing/output-action-not-set"))) {
/* 279 */         response.soapAction = action;
/*     */       }
/*     */     } 
/* 282 */     response.expectReply = Boolean.valueOf(false);
/*     */ 
/*     */     
/*     */     try {
/* 286 */       adrs = new EndpointAddress(URI.create(target.getAddress()));
/* 287 */     } catch (NullPointerException e) {
/* 288 */       throw new WebServiceException(e);
/* 289 */     } catch (IllegalArgumentException e) {
/* 290 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 293 */     response.endpointAddress = adrs;
/*     */     
/* 295 */     if (response.isAdapterDeliversNonAnonymousResponse) {
/* 296 */       return doReturnWith(response);
/*     */     }
/*     */     
/* 299 */     return doReturnWith(NonAnonymousResponseProcessor.getDefault().process(response));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateAction(Packet packet) {
/* 306 */     WSDLBoundOperation wsdlBoundOperation = getWSDLBoundOperation(packet);
/*     */     
/* 308 */     if (wsdlBoundOperation == null) {
/*     */       return;
/*     */     }
/*     */     
/* 312 */     String gotA = AddressingUtils.getAction(packet
/* 313 */         .getMessage().getHeaders(), this.addressingVersion, this.soapVersion);
/*     */ 
/*     */     
/* 316 */     if (gotA == null) {
/* 317 */       throw new WebServiceException(AddressingMessages.VALIDATION_SERVER_NULL_ACTION());
/*     */     }
/*     */     
/* 320 */     String expected = this.helper.getInputAction(packet);
/* 321 */     String soapAction = this.helper.getSOAPAction(packet);
/* 322 */     if (this.helper.isInputActionDefault(packet) && soapAction != null && !soapAction.equals("")) {
/* 323 */       expected = soapAction;
/*     */     }
/*     */     
/* 326 */     if (expected != null && !gotA.equals(expected)) {
/* 327 */       throw new ActionNotSupportedException(gotA);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkMessageAddressingProperties(Packet packet) {
/* 333 */     super.checkMessageAddressingProperties(packet);
/*     */ 
/*     */     
/* 336 */     WSDLBoundOperation wsdlBoundOperation = getWSDLBoundOperation(packet);
/* 337 */     checkAnonymousSemantics(wsdlBoundOperation, this.replyTo, this.faultTo);
/*     */     
/* 339 */     checkNonAnonymousAddresses(this.replyTo, this.faultTo);
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkNonAnonymousAddresses(WSEndpointReference replyTo, WSEndpointReference faultTo) {
/* 344 */     if (!replyTo.isAnonymous()) {
/*     */       try {
/* 346 */         new EndpointAddress(URI.create(replyTo.getAddress()));
/* 347 */       } catch (Exception e) {
/* 348 */         throw new InvalidAddressingHeaderException(this.addressingVersion.replyToTag, this.addressingVersion.invalidAddressTag);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 370 */   private static final Logger LOGGER = Logger.getLogger(WsaServerTube.class.getName());
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/WsaServerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */