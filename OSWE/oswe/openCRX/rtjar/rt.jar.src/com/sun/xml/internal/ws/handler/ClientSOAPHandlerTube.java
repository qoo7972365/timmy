/*     */ package com.sun.xml.internal.ws.handler;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.client.HandlerConfiguration;
/*     */ import com.sun.xml.internal.ws.message.DataHandlerAttachment;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.handler.MessageContext;
/*     */ import javax.xml.ws.handler.soap.SOAPHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientSOAPHandlerTube
/*     */   extends HandlerTube
/*     */ {
/*     */   private Set<String> roles;
/*     */   
/*     */   public ClientSOAPHandlerTube(WSBinding binding, WSDLPort port, Tube next) {
/*  60 */     super(next, port, binding);
/*  61 */     if (binding.getSOAPVersion() != null);
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
/*     */   public ClientSOAPHandlerTube(WSBinding binding, Tube next, HandlerTube cousinTube) {
/*  76 */     super(next, cousinTube, binding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientSOAPHandlerTube(ClientSOAPHandlerTube that, TubeCloner cloner) {
/*  83 */     super(that, cloner);
/*     */   }
/*     */   
/*     */   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
/*  87 */     return new ClientSOAPHandlerTube(this, cloner);
/*     */   }
/*     */   
/*     */   void setUpProcessor() {
/*  91 */     if (this.handlers == null) {
/*     */ 
/*     */       
/*  94 */       this.handlers = new ArrayList<>();
/*  95 */       HandlerConfiguration handlerConfig = ((BindingImpl)getBinding()).getHandlerConfig();
/*  96 */       List<SOAPHandler> soapSnapShot = handlerConfig.getSoapHandlers();
/*  97 */       if (!soapSnapShot.isEmpty()) {
/*  98 */         this.handlers.addAll(soapSnapShot);
/*  99 */         this.roles = new HashSet<>();
/* 100 */         this.roles.addAll(handlerConfig.getRoles());
/* 101 */         this.processor = new SOAPHandlerProcessor<>(true, this, getBinding(), this.handlers);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   MessageUpdatableContext getContext(Packet packet) {
/* 107 */     SOAPMessageContextImpl context = new SOAPMessageContextImpl(getBinding(), packet, this.roles);
/* 108 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
/*     */     boolean handlerResult;
/* 115 */     Map<String, DataHandler> atts = (Map<String, DataHandler>)context.get("javax.xml.ws.binding.attachments.outbound");
/* 116 */     AttachmentSet attSet = context.packet.getMessage().getAttachments();
/* 117 */     for (Map.Entry<String, DataHandler> entry : atts.entrySet()) {
/* 118 */       String cid = entry.getKey();
/* 119 */       if (attSet.get(cid) == null) {
/* 120 */         DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(cid, atts.get(cid));
/* 121 */         attSet.add((Attachment)dataHandlerAttachment);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 127 */       handlerResult = this.processor.callHandlersRequest(HandlerProcessor.Direction.OUTBOUND, context, !isOneWay);
/* 128 */     } catch (WebServiceException wse) {
/* 129 */       this.remedyActionTaken = true;
/*     */       
/* 131 */       throw wse;
/* 132 */     } catch (RuntimeException re) {
/* 133 */       this.remedyActionTaken = true;
/*     */       
/* 135 */       throw new WebServiceException(re);
/*     */     } 
/*     */     
/* 138 */     if (!handlerResult) {
/* 139 */       this.remedyActionTaken = true;
/*     */     }
/* 141 */     return handlerResult;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
/*     */     try {
/* 148 */       this.processor.callHandlersResponse(HandlerProcessor.Direction.INBOUND, context, handleFault);
/*     */     }
/* 150 */     catch (WebServiceException wse) {
/*     */       
/* 152 */       throw wse;
/* 153 */     } catch (RuntimeException re) {
/* 154 */       throw new WebServiceException(re);
/*     */     } 
/*     */   }
/*     */   
/*     */   void closeHandlers(MessageContext mc) {
/* 159 */     closeClientsideHandlers(mc);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/ClientSOAPHandlerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */