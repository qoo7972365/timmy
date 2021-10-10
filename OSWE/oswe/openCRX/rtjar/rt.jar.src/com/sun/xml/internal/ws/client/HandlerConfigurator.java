/*     */ package com.sun.xml.internal.ws.client;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.handler.HandlerChainsModel;
/*     */ import com.sun.xml.internal.ws.util.HandlerAnnotationInfo;
/*     */ import com.sun.xml.internal.ws.util.HandlerAnnotationProcessor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.handler.HandlerResolver;
/*     */ import javax.xml.ws.handler.PortInfo;
/*     */ import javax.xml.ws.soap.SOAPBinding;
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
/*     */ abstract class HandlerConfigurator
/*     */ {
/*     */   abstract void configureHandlers(@NotNull WSPortInfo paramWSPortInfo, @NotNull BindingImpl paramBindingImpl);
/*     */   
/*     */   abstract HandlerResolver getResolver();
/*     */   
/*     */   static final class HandlerResolverImpl
/*     */     extends HandlerConfigurator
/*     */   {
/*     */     @Nullable
/*     */     private final HandlerResolver resolver;
/*     */     
/*     */     public HandlerResolverImpl(HandlerResolver resolver) {
/*  81 */       this.resolver = resolver;
/*     */     }
/*     */ 
/*     */     
/*     */     void configureHandlers(@NotNull WSPortInfo port, @NotNull BindingImpl binding) {
/*  86 */       if (this.resolver != null) {
/*  87 */         binding.setHandlerChain(this.resolver.getHandlerChain((PortInfo)port));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     HandlerResolver getResolver() {
/*  94 */       return this.resolver;
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
/*     */   static final class AnnotationConfigurator
/*     */     extends HandlerConfigurator
/*     */   {
/*     */     private final HandlerChainsModel handlerModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     private final Map<WSPortInfo, HandlerAnnotationInfo> chainMap = new HashMap<>();
/* 120 */     private static final Logger logger = Logger.getLogger("com.sun.xml.internal.ws.handler");
/*     */ 
/*     */     
/*     */     AnnotationConfigurator(WSServiceDelegate delegate) {
/* 124 */       this.handlerModel = HandlerAnnotationProcessor.buildHandlerChainsModel(delegate.getServiceClass());
/* 125 */       assert this.handlerModel != null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void configureHandlers(WSPortInfo port, BindingImpl binding) {
/* 131 */       HandlerAnnotationInfo chain = this.chainMap.get(port);
/*     */       
/* 133 */       if (chain == null) {
/* 134 */         logGetChain(port);
/*     */         
/* 136 */         chain = this.handlerModel.getHandlersForPortInfo((PortInfo)port);
/* 137 */         this.chainMap.put(port, chain);
/*     */       } 
/*     */       
/* 140 */       if (binding instanceof SOAPBinding) {
/* 141 */         ((SOAPBinding)binding).setRoles(chain.getRoles());
/*     */       }
/*     */       
/* 144 */       logSetChain(port, chain);
/* 145 */       binding.setHandlerChain(chain.getHandlers());
/*     */     }
/*     */     
/*     */     HandlerResolver getResolver() {
/* 149 */       return new HandlerResolver() {
/*     */           public List<Handler> getHandlerChain(PortInfo portInfo) {
/* 151 */             return new ArrayList<>(HandlerConfigurator.AnnotationConfigurator.this
/* 152 */                 .handlerModel.getHandlersForPortInfo(portInfo).getHandlers());
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     private void logSetChain(WSPortInfo info, HandlerAnnotationInfo chain) {
/* 158 */       logger.finer("Setting chain of length " + chain.getHandlers().size() + " for port info");
/*     */       
/* 160 */       logPortInfo(info, Level.FINER);
/*     */     }
/*     */ 
/*     */     
/*     */     private void logGetChain(WSPortInfo info) {
/* 165 */       logger.fine("No handler chain found for port info:");
/* 166 */       logPortInfo(info, Level.FINE);
/* 167 */       logger.fine("Existing handler chains:");
/* 168 */       if (this.chainMap.isEmpty()) {
/* 169 */         logger.fine("none");
/*     */       } else {
/* 171 */         for (WSPortInfo key : this.chainMap.keySet()) {
/* 172 */           logger.fine(((HandlerAnnotationInfo)this.chainMap.get(key)).getHandlers().size() + " handlers for port info ");
/*     */           
/* 174 */           logPortInfo(key, Level.FINE);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void logPortInfo(WSPortInfo info, Level level) {
/* 180 */       logger.log(level, "binding: " + info.getBindingID() + "\nservice: " + info
/* 181 */           .getServiceName() + "\nport: " + info
/* 182 */           .getPortName());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/HandlerConfigurator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */