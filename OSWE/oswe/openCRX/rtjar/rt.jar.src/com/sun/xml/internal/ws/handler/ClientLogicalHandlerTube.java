/*     */ package com.sun.xml.internal.ws.handler;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class ClientLogicalHandlerTube
/*     */   extends HandlerTube
/*     */ {
/*     */   private SEIModel seiModel;
/*     */   
/*     */   public ClientLogicalHandlerTube(WSBinding binding, SEIModel seiModel, WSDLPort port, Tube next) {
/*  58 */     super(next, port, binding);
/*  59 */     this.seiModel = seiModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientLogicalHandlerTube(WSBinding binding, SEIModel seiModel, Tube next, HandlerTube cousinTube) {
/*  70 */     super(next, cousinTube, binding);
/*  71 */     this.seiModel = seiModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientLogicalHandlerTube(ClientLogicalHandlerTube that, TubeCloner cloner) {
/*  79 */     super(that, cloner);
/*  80 */     this.seiModel = that.seiModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initiateClosing(MessageContext mc) {
/*  86 */     close(mc);
/*  87 */     super.initiateClosing(mc);
/*     */   }
/*     */   
/*     */   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
/*  91 */     return new ClientLogicalHandlerTube(this, cloner);
/*     */   }
/*     */   
/*     */   void setUpProcessor() {
/*  95 */     if (this.handlers == null) {
/*     */ 
/*     */       
/*  98 */       this.handlers = new ArrayList<>();
/*  99 */       WSBinding binding = getBinding();
/* 100 */       List<LogicalHandler> logicalSnapShot = ((BindingImpl)binding).getHandlerConfig().getLogicalHandlers();
/* 101 */       if (!logicalSnapShot.isEmpty()) {
/* 102 */         this.handlers.addAll(logicalSnapShot);
/* 103 */         if (binding.getSOAPVersion() == null) {
/* 104 */           this.processor = new XMLHandlerProcessor<>(this, binding, this.handlers);
/*     */         } else {
/*     */           
/* 107 */           this.processor = new SOAPHandlerProcessor<>(true, this, binding, this.handlers);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MessageUpdatableContext getContext(Packet packet) {
/* 116 */     return new LogicalMessageContextImpl(getBinding(), getBindingContext(), packet);
/*     */   }
/*     */   
/*     */   private BindingContext getBindingContext() {
/* 120 */     return (this.seiModel != null && this.seiModel instanceof AbstractSEIModelImpl) ? ((AbstractSEIModelImpl)this.seiModel)
/* 121 */       .getBindingContext() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
/*     */     boolean handlerResult;
/*     */     try {
/* 130 */       handlerResult = this.processor.callHandlersRequest(HandlerProcessor.Direction.OUTBOUND, context, !isOneWay);
/* 131 */     } catch (WebServiceException wse) {
/* 132 */       this.remedyActionTaken = true;
/*     */       
/* 134 */       throw wse;
/* 135 */     } catch (RuntimeException re) {
/* 136 */       this.remedyActionTaken = true;
/*     */       
/* 138 */       throw new WebServiceException(re);
/*     */     } 
/*     */     
/* 141 */     if (!handlerResult) {
/* 142 */       this.remedyActionTaken = true;
/*     */     }
/* 144 */     return handlerResult;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
/*     */     try {
/* 151 */       this.processor.callHandlersResponse(HandlerProcessor.Direction.INBOUND, context, handleFault);
/*     */     }
/* 153 */     catch (WebServiceException wse) {
/*     */       
/* 155 */       throw wse;
/* 156 */     } catch (RuntimeException re) {
/*     */       
/* 158 */       throw new WebServiceException(re);
/*     */     } 
/*     */   }
/*     */   
/*     */   void closeHandlers(MessageContext mc) {
/* 163 */     closeClientsideHandlers(mc);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/ClientLogicalHandlerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */