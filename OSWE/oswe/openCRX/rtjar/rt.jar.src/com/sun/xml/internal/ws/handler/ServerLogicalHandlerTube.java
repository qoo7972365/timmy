/*     */ package com.sun.xml.internal.ws.handler;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
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
/*     */ import com.sun.xml.internal.ws.message.DataHandlerAttachment;
/*     */ import com.sun.xml.internal.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.handler.LogicalHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerLogicalHandlerTube
/*     */   extends HandlerTube
/*     */ {
/*     */   private SEIModel seiModel;
/*     */   
/*     */   public ServerLogicalHandlerTube(WSBinding binding, SEIModel seiModel, WSDLPort port, Tube next) {
/*  64 */     super(next, port, binding);
/*  65 */     this.seiModel = seiModel;
/*  66 */     setUpHandlersOnce();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerLogicalHandlerTube(WSBinding binding, SEIModel seiModel, Tube next, HandlerTube cousinTube) {
/*  77 */     super(next, cousinTube, binding);
/*  78 */     this.seiModel = seiModel;
/*  79 */     setUpHandlersOnce();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerLogicalHandlerTube(ServerLogicalHandlerTube that, TubeCloner cloner) {
/*  87 */     super(that, cloner);
/*  88 */     this.seiModel = that.seiModel;
/*  89 */     this.handlers = that.handlers;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initiateClosing(MessageContext mc) {
/*  95 */     if (getBinding().getSOAPVersion() != null) {
/*  96 */       super.initiateClosing(mc);
/*     */     } else {
/*  98 */       close(mc);
/*  99 */       super.initiateClosing(mc);
/*     */     } 
/*     */   }
/*     */   
/*     */   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
/* 104 */     return new ServerLogicalHandlerTube(this, cloner);
/*     */   }
/*     */   
/*     */   private void setUpHandlersOnce() {
/* 108 */     this.handlers = new ArrayList<>();
/* 109 */     List<LogicalHandler> logicalSnapShot = ((BindingImpl)getBinding()).getHandlerConfig().getLogicalHandlers();
/* 110 */     if (!logicalSnapShot.isEmpty()) {
/* 111 */       this.handlers.addAll(logicalSnapShot);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void resetProcessor() {
/* 116 */     this.processor = null;
/*     */   }
/*     */   
/*     */   void setUpProcessor() {
/* 120 */     if (!this.handlers.isEmpty() && this.processor == null) {
/* 121 */       if (getBinding().getSOAPVersion() == null) {
/* 122 */         this.processor = new XMLHandlerProcessor<>(this, getBinding(), this.handlers);
/*     */       } else {
/*     */         
/* 125 */         this.processor = new SOAPHandlerProcessor<>(false, this, getBinding(), this.handlers);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   MessageUpdatableContext getContext(Packet packet) {
/* 131 */     return new LogicalMessageContextImpl(getBinding(), getBindingContext(), packet);
/*     */   }
/*     */   
/*     */   private BindingContext getBindingContext() {
/* 135 */     return (this.seiModel != null && this.seiModel instanceof AbstractSEIModelImpl) ? ((AbstractSEIModelImpl)this.seiModel)
/* 136 */       .getBindingContext() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
/*     */     boolean handlerResult;
/*     */     try {
/* 144 */       handlerResult = this.processor.callHandlersRequest(HandlerProcessor.Direction.INBOUND, context, !isOneWay);
/*     */     }
/* 146 */     catch (RuntimeException re) {
/* 147 */       this.remedyActionTaken = true;
/* 148 */       throw re;
/*     */     } 
/* 150 */     if (!handlerResult) {
/* 151 */       this.remedyActionTaken = true;
/*     */     }
/* 153 */     return handlerResult;
/*     */   }
/*     */ 
/*     */   
/*     */   void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
/* 158 */     Map<String, DataHandler> atts = (Map<String, DataHandler>)context.get("javax.xml.ws.binding.attachments.outbound");
/* 159 */     AttachmentSet attSet = context.packet.getMessage().getAttachments();
/* 160 */     for (Map.Entry<String, DataHandler> entry : atts.entrySet()) {
/* 161 */       String cid = entry.getKey();
/* 162 */       DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(cid, atts.get(cid));
/* 163 */       attSet.add((Attachment)dataHandlerAttachment);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 168 */       this.processor.callHandlersResponse(HandlerProcessor.Direction.OUTBOUND, context, handleFault);
/*     */     }
/* 170 */     catch (WebServiceException wse) {
/*     */       
/* 172 */       throw wse;
/* 173 */     } catch (RuntimeException re) {
/* 174 */       throw re;
/*     */     } 
/*     */   }
/*     */   
/*     */   void closeHandlers(MessageContext mc) {
/* 179 */     closeServersideHandlers(mc);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/ServerLogicalHandlerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */