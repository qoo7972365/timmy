/*     */ package com.sun.xml.internal.ws.handler;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.handler.MessageHandler;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
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
/*     */ public class ServerMessageHandlerTube
/*     */   extends HandlerTube
/*     */ {
/*     */   private SEIModel seiModel;
/*     */   private Set<String> roles;
/*     */   
/*     */   public ServerMessageHandlerTube(SEIModel seiModel, WSBinding binding, Tube next, HandlerTube cousinTube) {
/*  56 */     super(next, cousinTube, binding);
/*  57 */     this.seiModel = seiModel;
/*  58 */     setUpHandlersOnce();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerMessageHandlerTube(ServerMessageHandlerTube that, TubeCloner cloner) {
/*  65 */     super(that, cloner);
/*  66 */     this.seiModel = that.seiModel;
/*  67 */     this.handlers = that.handlers;
/*  68 */     this.roles = that.roles;
/*     */   }
/*     */   
/*     */   private void setUpHandlersOnce() {
/*  72 */     this.handlers = new ArrayList<>();
/*  73 */     HandlerConfiguration handlerConfig = ((BindingImpl)getBinding()).getHandlerConfig();
/*  74 */     List<MessageHandler> msgHandlersSnapShot = handlerConfig.getMessageHandlers();
/*  75 */     if (!msgHandlersSnapShot.isEmpty()) {
/*  76 */       this.handlers.addAll(msgHandlersSnapShot);
/*  77 */       this.roles = new HashSet<>();
/*  78 */       this.roles.addAll(handlerConfig.getRoles());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
/*  84 */     Map<String, DataHandler> atts = (Map<String, DataHandler>)context.get("javax.xml.ws.binding.attachments.outbound");
/*  85 */     AttachmentSet attSet = context.packet.getMessage().getAttachments();
/*  86 */     for (Map.Entry<String, DataHandler> entry : atts.entrySet()) {
/*  87 */       String cid = entry.getKey();
/*  88 */       if (attSet.get(cid) == null) {
/*  89 */         DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(cid, atts.get(cid));
/*  90 */         attSet.add((Attachment)dataHandlerAttachment);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  96 */       this.processor.callHandlersResponse(HandlerProcessor.Direction.OUTBOUND, context, handleFault);
/*     */     }
/*  98 */     catch (WebServiceException wse) {
/*     */       
/* 100 */       throw wse;
/* 101 */     } catch (RuntimeException re) {
/* 102 */       throw re;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
/*     */     boolean handlerResult;
/*     */     try {
/* 111 */       handlerResult = this.processor.callHandlersRequest(HandlerProcessor.Direction.INBOUND, context, !isOneWay);
/*     */     }
/* 113 */     catch (RuntimeException re) {
/* 114 */       this.remedyActionTaken = true;
/* 115 */       throw re;
/*     */     } 
/*     */     
/* 118 */     if (!handlerResult) {
/* 119 */       this.remedyActionTaken = true;
/*     */     }
/* 121 */     return handlerResult;
/*     */   }
/*     */   
/*     */   protected void resetProcessor() {
/* 125 */     this.processor = null;
/*     */   }
/*     */   
/*     */   void setUpProcessor() {
/* 129 */     if (!this.handlers.isEmpty() && this.processor == null) {
/* 130 */       this.processor = new SOAPHandlerProcessor<>(false, this, getBinding(), this.handlers);
/*     */     }
/*     */   }
/*     */   
/*     */   void closeHandlers(MessageContext mc) {
/* 135 */     closeServersideHandlers(mc);
/*     */   }
/*     */   
/*     */   MessageUpdatableContext getContext(Packet packet) {
/* 139 */     MessageHandlerContextImpl context = new MessageHandlerContextImpl(this.seiModel, getBinding(), this.port, packet, this.roles);
/* 140 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initiateClosing(MessageContext mc) {
/* 146 */     close(mc);
/* 147 */     super.initiateClosing(mc);
/*     */   }
/*     */   
/*     */   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
/* 151 */     return new ServerMessageHandlerTube(this, cloner);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/ServerMessageHandlerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */