/*     */ package com.sun.xml.internal.ws.handler;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.client.HandlerConfiguration;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public abstract class HandlerTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   HandlerTube cousinTube;
/*     */   protected List<Handler> handlers;
/*     */   HandlerProcessor processor;
/*     */   boolean remedyActionTaken = false;
/*     */   @Nullable
/*     */   protected final WSDLPort port;
/*     */   boolean requestProcessingSucessful = false;
/*     */   private WSBinding binding;
/*     */   private HandlerConfiguration hc;
/*     */   private HandlerTubeExchange exchange;
/*     */   
/*     */   public HandlerTube(Tube next, WSDLPort port, WSBinding binding) {
/*  60 */     super(next);
/*  61 */     this.port = port;
/*  62 */     this.binding = binding;
/*     */   }
/*     */   
/*     */   public HandlerTube(Tube next, HandlerTube cousinTube, WSBinding binding) {
/*  66 */     super(next);
/*  67 */     this.cousinTube = cousinTube;
/*  68 */     this.binding = binding;
/*  69 */     if (cousinTube != null) {
/*  70 */       this.port = cousinTube.port;
/*     */     } else {
/*  72 */       this.port = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HandlerTube(HandlerTube that, TubeCloner cloner) {
/*  80 */     super(that, cloner);
/*  81 */     if (that.cousinTube != null) {
/*  82 */       this.cousinTube = (HandlerTube)cloner.copy((Tube)that.cousinTube);
/*     */     }
/*  84 */     this.port = that.port;
/*  85 */     this.binding = that.binding;
/*     */   }
/*     */   
/*     */   protected WSBinding getBinding() {
/*  89 */     return this.binding;
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/*  94 */     setupExchange();
/*     */     
/*  96 */     if (isHandleFalse()) {
/*     */ 
/*     */       
/*  99 */       this.remedyActionTaken = true;
/* 100 */       return doInvoke(this.next, request);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 105 */     setUpProcessorInternal();
/*     */     
/* 107 */     MessageUpdatableContext context = getContext(request);
/* 108 */     boolean isOneWay = checkOneWay(request);
/*     */     try {
/* 110 */       if (!isHandlerChainEmpty()) {
/*     */         
/* 112 */         boolean handlerResult = callHandlersOnRequest(context, isOneWay);
/*     */         
/* 114 */         context.updatePacket();
/*     */         
/* 116 */         if (!isOneWay && !handlerResult) {
/* 117 */           return doReturnWith(request);
/*     */         }
/*     */       } 
/* 120 */       this.requestProcessingSucessful = true;
/*     */       
/* 122 */       return doInvoke(this.next, request);
/* 123 */     } catch (RuntimeException re) {
/* 124 */       if (isOneWay) {
/*     */         
/* 126 */         if (request.transportBackChannel != null) {
/* 127 */           request.transportBackChannel.close();
/*     */         }
/* 129 */         request.setMessage(null);
/* 130 */         return doReturnWith(request);
/*     */       } 
/* 132 */       throw re;
/*     */     } finally {
/* 134 */       if (!this.requestProcessingSucessful) {
/* 135 */         initiateClosing(context.getMessageContext());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 143 */     setupExchange();
/* 144 */     MessageUpdatableContext context = getContext(response);
/*     */     try {
/* 146 */       if (isHandleFalse() || response.getMessage() == null)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 151 */         return doReturnWith(response);
/*     */       }
/*     */       
/* 154 */       setUpProcessorInternal();
/*     */       
/* 156 */       boolean isFault = isHandleFault(response);
/* 157 */       if (!isHandlerChainEmpty())
/*     */       {
/* 159 */         callHandlersOnResponse(context, isFault);
/*     */       }
/*     */     } finally {
/* 162 */       initiateClosing(context.getMessageContext());
/*     */     } 
/*     */     
/* 165 */     context.updatePacket();
/*     */     
/* 167 */     return doReturnWith(response);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable t) {
/*     */     try {
/* 174 */       return doThrow(t);
/*     */     } finally {
/* 176 */       Packet packet = Fiber.current().getPacket();
/* 177 */       MessageUpdatableContext context = getContext(packet);
/* 178 */       initiateClosing(context.getMessageContext());
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
/*     */   protected void initiateClosing(MessageContext mc) {}
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
/*     */   public final void close(MessageContext msgContext) {
/* 216 */     if (this.requestProcessingSucessful && 
/* 217 */       this.cousinTube != null) {
/* 218 */       this.cousinTube.close(msgContext);
/*     */     }
/*     */ 
/*     */     
/* 222 */     if (this.processor != null) {
/* 223 */       closeHandlers(msgContext);
/*     */     }
/*     */     
/* 226 */     this.exchange = null;
/* 227 */     this.requestProcessingSucessful = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void closeHandlers(MessageContext paramMessageContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeClientsideHandlers(MessageContext msgContext) {
/* 243 */     if (this.processor == null)
/*     */       return; 
/* 245 */     if (this.remedyActionTaken) {
/*     */ 
/*     */ 
/*     */       
/* 249 */       this.processor.closeHandlers(msgContext, this.processor.getIndex(), 0);
/* 250 */       this.processor.setIndex(-1);
/*     */       
/* 252 */       this.remedyActionTaken = false;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 257 */       this.processor.closeHandlers(msgContext, this.handlers.size() - 1, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeServersideHandlers(MessageContext msgContext) {
/* 266 */     if (this.processor == null)
/*     */       return; 
/* 268 */     if (this.remedyActionTaken) {
/*     */ 
/*     */ 
/*     */       
/* 272 */       this.processor.closeHandlers(msgContext, this.processor.getIndex(), this.handlers.size() - 1);
/* 273 */       this.processor.setIndex(-1);
/*     */       
/* 275 */       this.remedyActionTaken = false;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 280 */       this.processor.closeHandlers(msgContext, 0, this.handlers.size() - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   abstract void callHandlersOnResponse(MessageUpdatableContext paramMessageUpdatableContext, boolean paramBoolean);
/*     */   
/*     */   abstract boolean callHandlersOnRequest(MessageUpdatableContext paramMessageUpdatableContext, boolean paramBoolean);
/*     */   
/*     */   private boolean checkOneWay(Packet packet) {
/* 290 */     if (this.port != null)
/*     */     {
/* 292 */       return packet.getMessage().isOneWay(this.port);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     return (packet.expectReply == null || !packet.expectReply.booleanValue());
/*     */   }
/*     */ 
/*     */   
/*     */   private void setUpProcessorInternal() {
/* 303 */     HandlerConfiguration hc = ((BindingImpl)this.binding).getHandlerConfig();
/* 304 */     if (hc != this.hc)
/* 305 */       resetProcessor(); 
/* 306 */     this.hc = hc;
/*     */     
/* 308 */     setUpProcessor();
/*     */   }
/*     */   
/*     */   abstract void setUpProcessor();
/*     */   
/*     */   protected void resetProcessor() {
/* 314 */     this.handlers = null;
/*     */   }
/*     */   
/*     */   public final boolean isHandlerChainEmpty() {
/* 318 */     return this.handlers.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isHandleFault(Packet packet) {
/* 323 */     if (this.cousinTube != null) {
/* 324 */       return this.exchange.isHandleFault();
/*     */     }
/* 326 */     boolean isFault = packet.getMessage().isFault();
/* 327 */     this.exchange.setHandleFault(isFault);
/* 328 */     return isFault;
/*     */   }
/*     */   abstract MessageUpdatableContext getContext(Packet paramPacket);
/*     */   
/*     */   final void setHandleFault() {
/* 333 */     this.exchange.setHandleFault(true);
/*     */   }
/*     */   
/*     */   private boolean isHandleFalse() {
/* 337 */     return this.exchange.isHandleFalse();
/*     */   }
/*     */   
/*     */   final void setHandleFalse() {
/* 341 */     this.exchange.setHandleFalse();
/*     */   }
/*     */   
/*     */   private void setupExchange() {
/* 345 */     if (this.exchange == null) {
/* 346 */       this.exchange = new HandlerTubeExchange();
/* 347 */       if (this.cousinTube != null) {
/* 348 */         this.cousinTube.exchange = this.exchange;
/*     */       }
/*     */     }
/* 351 */     else if (this.cousinTube != null) {
/* 352 */       this.cousinTube.exchange = this.exchange;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class HandlerTubeExchange
/*     */   {
/*     */     private boolean handleFalse;
/*     */ 
/*     */     
/*     */     private boolean handleFault;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isHandleFault() {
/* 368 */       return this.handleFault;
/*     */     }
/*     */     
/*     */     void setHandleFault(boolean isFault) {
/* 372 */       this.handleFault = isFault;
/*     */     }
/*     */     
/*     */     public boolean isHandleFalse() {
/* 376 */       return this.handleFalse;
/*     */     }
/*     */     
/*     */     void setHandleFalse() {
/* 380 */       this.handleFalse = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/HandlerTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */