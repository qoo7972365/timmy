/*     */ package com.sun.xml.internal.ws.handler;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.handler.MessageHandler;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientMessageHandlerTube
/*     */   extends HandlerTube
/*     */ {
/*     */   private SEIModel seiModel;
/*     */   private Set<String> roles;
/*     */   
/*     */   public ClientMessageHandlerTube(@Nullable SEIModel seiModel, WSBinding binding, WSDLPort port, Tube next) {
/*  61 */     super(next, port, binding);
/*  62 */     this.seiModel = seiModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientMessageHandlerTube(ClientMessageHandlerTube that, TubeCloner cloner) {
/*  69 */     super(that, cloner);
/*  70 */     this.seiModel = that.seiModel;
/*     */   }
/*     */   
/*     */   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
/*  74 */     return new ClientMessageHandlerTube(this, cloner);
/*     */   }
/*     */ 
/*     */   
/*     */   void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
/*     */     try {
/*  80 */       this.processor.callHandlersResponse(HandlerProcessor.Direction.INBOUND, context, handleFault);
/*     */     }
/*  82 */     catch (WebServiceException wse) {
/*     */       
/*  84 */       throw wse;
/*  85 */     } catch (RuntimeException re) {
/*  86 */       throw new WebServiceException(re);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
/*     */     boolean handlerResult;
/*  94 */     Map<String, DataHandler> atts = (Map<String, DataHandler>)context.get("javax.xml.ws.binding.attachments.outbound");
/*  95 */     AttachmentSet attSet = context.packet.getMessage().getAttachments();
/*  96 */     for (Map.Entry<String, DataHandler> entry : atts.entrySet()) {
/*  97 */       String cid = entry.getKey();
/*  98 */       if (attSet.get(cid) == null) {
/*  99 */         DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(cid, atts.get(cid));
/* 100 */         attSet.add((Attachment)dataHandlerAttachment);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 106 */       handlerResult = this.processor.callHandlersRequest(HandlerProcessor.Direction.OUTBOUND, context, !isOneWay);
/* 107 */     } catch (WebServiceException wse) {
/* 108 */       this.remedyActionTaken = true;
/*     */       
/* 110 */       throw wse;
/* 111 */     } catch (RuntimeException re) {
/* 112 */       this.remedyActionTaken = true;
/*     */       
/* 114 */       throw new WebServiceException(re);
/*     */     } 
/*     */     
/* 117 */     if (!handlerResult) {
/* 118 */       this.remedyActionTaken = true;
/*     */     }
/* 120 */     return handlerResult;
/*     */   }
/*     */   
/*     */   void closeHandlers(MessageContext mc) {
/* 124 */     closeClientsideHandlers(mc);
/*     */   }
/*     */ 
/*     */   
/*     */   void setUpProcessor() {
/* 129 */     if (this.handlers == null) {
/*     */ 
/*     */       
/* 132 */       this.handlers = new ArrayList<>();
/* 133 */       HandlerConfiguration handlerConfig = ((BindingImpl)getBinding()).getHandlerConfig();
/* 134 */       List<MessageHandler> msgHandlersSnapShot = handlerConfig.getMessageHandlers();
/* 135 */       if (!msgHandlersSnapShot.isEmpty()) {
/* 136 */         this.handlers.addAll(msgHandlersSnapShot);
/* 137 */         this.roles = new HashSet<>();
/* 138 */         this.roles.addAll(handlerConfig.getRoles());
/* 139 */         this.processor = new SOAPHandlerProcessor<>(true, this, getBinding(), this.handlers);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MessageUpdatableContext getContext(Packet p) {
/* 147 */     MessageHandlerContextImpl context = new MessageHandlerContextImpl(this.seiModel, getBinding(), this.port, p, this.roles);
/* 148 */     return context;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/ClientMessageHandlerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */