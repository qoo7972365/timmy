/*     */ package com.sun.xml.internal.ws.addressing;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.addressing.model.InvalidAddressingHeaderException;
/*     */ import com.sun.xml.internal.ws.addressing.model.MissingAddressingHeaderException;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W3CWsaServerTube
/*     */   extends WsaServerTube
/*     */ {
/*     */   private final AddressingFeature af;
/*     */   
/*     */   public W3CWsaServerTube(WSEndpoint endpoint, @NotNull WSDLPort wsdlPort, WSBinding binding, Tube next) {
/*  52 */     super(endpoint, wsdlPort, binding, next);
/*  53 */     this.af = (AddressingFeature)binding.getFeature(AddressingFeature.class);
/*     */   }
/*     */   
/*     */   public W3CWsaServerTube(W3CWsaServerTube that, TubeCloner cloner) {
/*  57 */     super(that, cloner);
/*  58 */     this.af = that.af;
/*     */   }
/*     */ 
/*     */   
/*     */   public W3CWsaServerTube copy(TubeCloner cloner) {
/*  63 */     return new W3CWsaServerTube(this, cloner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkMandatoryHeaders(Packet packet, boolean foundAction, boolean foundTo, boolean foundReplyTo, boolean foundFaultTo, boolean foundMessageId, boolean foundRelatesTo) {
/*  70 */     super.checkMandatoryHeaders(packet, foundAction, foundTo, foundReplyTo, foundFaultTo, foundMessageId, foundRelatesTo);
/*     */ 
/*     */ 
/*     */     
/*  74 */     WSDLBoundOperation wbo = getWSDLBoundOperation(packet);
/*     */     
/*  76 */     if (wbo != null)
/*     */     {
/*  78 */       if (!wbo.getOperation().isOneWay() && !foundMessageId) {
/*  79 */         throw new MissingAddressingHeaderException(this.addressingVersion.messageIDTag, packet);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isAnonymousRequired(@Nullable WSDLBoundOperation wbo) {
/*  87 */     return (getResponseRequirement(wbo) == WSDLBoundOperation.ANONYMOUS.required);
/*     */   }
/*     */   
/*     */   private WSDLBoundOperation.ANONYMOUS getResponseRequirement(@Nullable WSDLBoundOperation wbo) {
/*     */     try {
/*  92 */       if (this.af.getResponses() == AddressingFeature.Responses.ANONYMOUS)
/*  93 */         return WSDLBoundOperation.ANONYMOUS.required; 
/*  94 */       if (this.af.getResponses() == AddressingFeature.Responses.NON_ANONYMOUS) {
/*  95 */         return WSDLBoundOperation.ANONYMOUS.prohibited;
/*     */       }
/*  97 */     } catch (NoSuchMethodError noSuchMethodError) {}
/*     */ 
/*     */ 
/*     */     
/* 101 */     return (wbo != null) ? wbo.getAnonymous() : WSDLBoundOperation.ANONYMOUS.optional;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkAnonymousSemantics(WSDLBoundOperation wbo, WSEndpointReference replyTo, WSEndpointReference faultTo) {
/* 106 */     String replyToValue = null;
/* 107 */     String faultToValue = null;
/*     */     
/* 109 */     if (replyTo != null) {
/* 110 */       replyToValue = replyTo.getAddress();
/*     */     }
/* 112 */     if (faultTo != null)
/* 113 */       faultToValue = faultTo.getAddress(); 
/* 114 */     WSDLBoundOperation.ANONYMOUS responseRequirement = getResponseRequirement(wbo);
/*     */     
/* 116 */     switch (responseRequirement) {
/*     */       case prohibited:
/* 118 */         if (replyToValue != null && replyToValue.equals(this.addressingVersion.anonymousUri)) {
/* 119 */           throw new InvalidAddressingHeaderException(this.addressingVersion.replyToTag, W3CAddressingConstants.ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED);
/*     */         }
/* 121 */         if (faultToValue != null && faultToValue.equals(this.addressingVersion.anonymousUri))
/* 122 */           throw new InvalidAddressingHeaderException(this.addressingVersion.faultToTag, W3CAddressingConstants.ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED); 
/*     */         break;
/*     */       case required:
/* 125 */         if (replyToValue != null && !replyToValue.equals(this.addressingVersion.anonymousUri)) {
/* 126 */           throw new InvalidAddressingHeaderException(this.addressingVersion.replyToTag, W3CAddressingConstants.ONLY_ANONYMOUS_ADDRESS_SUPPORTED);
/*     */         }
/* 128 */         if (faultToValue != null && !faultToValue.equals(this.addressingVersion.anonymousUri))
/* 129 */           throw new InvalidAddressingHeaderException(this.addressingVersion.faultToTag, W3CAddressingConstants.ONLY_ANONYMOUS_ADDRESS_SUPPORTED); 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/W3CWsaServerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */