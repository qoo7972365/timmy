/*     */ package com.sun.xml.internal.ws.addressing;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.PropertySet;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.addressing.model.ActionNotSupportedException;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.internal.ws.resources.AddressingMessages;
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
/*     */ public class WsaClientTube
/*     */   extends WsaTube
/*     */ {
/*     */   protected boolean expectReply = true;
/*     */   
/*     */   public WsaClientTube(WSDLPort wsdlPort, WSBinding binding, Tube next) {
/*  54 */     super(wsdlPort, binding, next);
/*     */   }
/*     */   
/*     */   public WsaClientTube(WsaClientTube that, TubeCloner cloner) {
/*  58 */     super(that, cloner);
/*     */   }
/*     */   
/*     */   public WsaClientTube copy(TubeCloner cloner) {
/*  62 */     return new WsaClientTube(this, cloner);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public NextAction processRequest(Packet request) {
/*  67 */     this.expectReply = request.expectReply.booleanValue();
/*  68 */     return doInvoke(this.next, request);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processResponse(Packet response) {
/*  74 */     if (response.getMessage() != null) {
/*  75 */       response = validateInboundHeaders(response);
/*  76 */       response.addSatellite((PropertySet)new WsaPropertyBag(this.addressingVersion, this.soapVersion, response));
/*     */       
/*  78 */       String msgId = AddressingUtils.getMessageID(response.getMessage().getHeaders(), this.addressingVersion, this.soapVersion);
/*     */       
/*  80 */       response.put("com.sun.xml.internal.ws.addressing.WsaPropertyBag.MessageIdFromRequest", msgId);
/*     */     } 
/*     */     
/*  83 */     return doReturnWith(response);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateAction(Packet packet) {
/*  91 */     WSDLBoundOperation wbo = getWSDLBoundOperation(packet);
/*     */     
/*  93 */     if (wbo == null)
/*     */       return; 
/*  95 */     String gotA = AddressingUtils.getAction(packet
/*  96 */         .getMessage().getHeaders(), this.addressingVersion, this.soapVersion);
/*     */     
/*  98 */     if (gotA == null) {
/*  99 */       throw new WebServiceException(AddressingMessages.VALIDATION_CLIENT_NULL_ACTION());
/*     */     }
/* 101 */     String expected = this.helper.getOutputAction(packet);
/*     */     
/* 103 */     if (expected != null && !gotA.equals(expected))
/* 104 */       throw new ActionNotSupportedException(gotA); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/WsaClientTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */