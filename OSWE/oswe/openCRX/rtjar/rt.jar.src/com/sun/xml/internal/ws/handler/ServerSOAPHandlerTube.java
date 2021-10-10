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
/*     */ public class ServerSOAPHandlerTube
/*     */   extends HandlerTube
/*     */ {
/*     */   private Set<String> roles;
/*     */   
/*     */   public ServerSOAPHandlerTube(WSBinding binding, WSDLPort port, Tube next) {
/*  59 */     super(next, port, binding);
/*  60 */     if (binding.getSOAPVersion() != null);
/*     */ 
/*     */ 
/*     */     
/*  64 */     setUpHandlersOnce();
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
/*     */   public ServerSOAPHandlerTube(WSBinding binding, Tube next, HandlerTube cousinTube) {
/*  76 */     super(next, cousinTube, binding);
/*  77 */     setUpHandlersOnce();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerSOAPHandlerTube(ServerSOAPHandlerTube that, TubeCloner cloner) {
/*  84 */     super(that, cloner);
/*  85 */     this.handlers = that.handlers;
/*  86 */     this.roles = that.roles;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
/*  91 */     return new ServerSOAPHandlerTube(this, cloner);
/*     */   }
/*     */   
/*     */   private void setUpHandlersOnce() {
/*  95 */     this.handlers = new ArrayList<>();
/*  96 */     HandlerConfiguration handlerConfig = ((BindingImpl)getBinding()).getHandlerConfig();
/*  97 */     List<SOAPHandler> soapSnapShot = handlerConfig.getSoapHandlers();
/*  98 */     if (!soapSnapShot.isEmpty()) {
/*  99 */       this.handlers.addAll(soapSnapShot);
/* 100 */       this.roles = new HashSet<>();
/* 101 */       this.roles.addAll(handlerConfig.getRoles());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void resetProcessor() {
/* 106 */     this.processor = null;
/*     */   }
/*     */   
/*     */   void setUpProcessor() {
/* 110 */     if (!this.handlers.isEmpty() && this.processor == null)
/* 111 */       this.processor = new SOAPHandlerProcessor<>(false, this, getBinding(), this.handlers); 
/*     */   }
/*     */   MessageUpdatableContext getContext(Packet packet) {
/* 114 */     SOAPMessageContextImpl context = new SOAPMessageContextImpl(getBinding(), packet, this.roles);
/* 115 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
/*     */     boolean handlerResult;
/*     */     try {
/* 123 */       handlerResult = this.processor.callHandlersRequest(HandlerProcessor.Direction.INBOUND, context, !isOneWay);
/*     */     }
/* 125 */     catch (RuntimeException re) {
/* 126 */       this.remedyActionTaken = true;
/* 127 */       throw re;
/*     */     } 
/*     */     
/* 130 */     if (!handlerResult) {
/* 131 */       this.remedyActionTaken = true;
/*     */     }
/* 133 */     return handlerResult;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
/* 139 */     Map<String, DataHandler> atts = (Map<String, DataHandler>)context.get("javax.xml.ws.binding.attachments.outbound");
/* 140 */     AttachmentSet attSet = context.packet.getMessage().getAttachments();
/* 141 */     for (Map.Entry<String, DataHandler> entry : atts.entrySet()) {
/* 142 */       String cid = entry.getKey();
/* 143 */       if (attSet.get(cid) == null) {
/* 144 */         DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(cid, atts.get(cid));
/* 145 */         attSet.add((Attachment)dataHandlerAttachment);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 151 */       this.processor.callHandlersResponse(HandlerProcessor.Direction.OUTBOUND, context, handleFault);
/*     */     }
/* 153 */     catch (WebServiceException wse) {
/*     */       
/* 155 */       throw wse;
/* 156 */     } catch (RuntimeException re) {
/* 157 */       throw re;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void closeHandlers(MessageContext mc) {
/* 163 */     closeServersideHandlers(mc);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/ServerSOAPHandlerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */