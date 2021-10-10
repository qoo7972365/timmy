/*     */ package com.sun.xml.internal.ws.client;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.handler.MessageHandler;
/*     */ import com.sun.xml.internal.ws.handler.HandlerException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.handler.LogicalHandler;
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
/*     */ public class HandlerConfiguration
/*     */ {
/*     */   private final Set<String> roles;
/*     */   private final List<Handler> handlerChain;
/*     */   private final List<LogicalHandler> logicalHandlers;
/*     */   private final List<SOAPHandler> soapHandlers;
/*     */   private final List<MessageHandler> messageHandlers;
/*     */   private final Set<QName> handlerKnownHeaders;
/*     */   
/*     */   public HandlerConfiguration(Set<String> roles, List<Handler> handlerChain) {
/*  64 */     this.roles = roles;
/*  65 */     this.handlerChain = handlerChain;
/*  66 */     this.logicalHandlers = new ArrayList<>();
/*  67 */     this.soapHandlers = new ArrayList<>();
/*  68 */     this.messageHandlers = new ArrayList<>();
/*  69 */     Set<QName> modHandlerKnownHeaders = new HashSet<>();
/*     */     
/*  71 */     for (Handler handler : handlerChain) {
/*  72 */       if (handler instanceof LogicalHandler) {
/*  73 */         this.logicalHandlers.add((LogicalHandler)handler); continue;
/*  74 */       }  if (handler instanceof SOAPHandler) {
/*  75 */         this.soapHandlers.add((SOAPHandler)handler);
/*  76 */         Set<QName> headers = ((SOAPHandler)handler).getHeaders();
/*  77 */         if (headers != null)
/*  78 */           modHandlerKnownHeaders.addAll(headers);  continue;
/*     */       } 
/*  80 */       if (handler instanceof MessageHandler) {
/*  81 */         this.messageHandlers.add((MessageHandler)handler);
/*  82 */         Set<QName> headers = ((MessageHandler)handler).getHeaders();
/*  83 */         if (headers != null)
/*  84 */           modHandlerKnownHeaders.addAll(headers); 
/*     */         continue;
/*     */       } 
/*  87 */       throw new HandlerException("handler.not.valid.type", new Object[] { handler
/*  88 */             .getClass() });
/*     */     } 
/*     */ 
/*     */     
/*  92 */     this.handlerKnownHeaders = Collections.unmodifiableSet(modHandlerKnownHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HandlerConfiguration(Set<String> roles, HandlerConfiguration oldConfig) {
/* 101 */     this.roles = roles;
/* 102 */     this.handlerChain = oldConfig.handlerChain;
/* 103 */     this.logicalHandlers = oldConfig.logicalHandlers;
/* 104 */     this.soapHandlers = oldConfig.soapHandlers;
/* 105 */     this.messageHandlers = oldConfig.messageHandlers;
/* 106 */     this.handlerKnownHeaders = oldConfig.handlerKnownHeaders;
/*     */   }
/*     */   
/*     */   public Set<String> getRoles() {
/* 110 */     return this.roles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Handler> getHandlerChain() {
/* 118 */     if (this.handlerChain == null)
/* 119 */       return Collections.emptyList(); 
/* 120 */     return new ArrayList<>(this.handlerChain);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<LogicalHandler> getLogicalHandlers() {
/* 125 */     return this.logicalHandlers;
/*     */   }
/*     */   
/*     */   public List<SOAPHandler> getSoapHandlers() {
/* 129 */     return this.soapHandlers;
/*     */   }
/*     */   
/*     */   public List<MessageHandler> getMessageHandlers() {
/* 133 */     return this.messageHandlers;
/*     */   }
/*     */   
/*     */   public Set<QName> getHandlerKnownHeaders() {
/* 137 */     return this.handlerKnownHeaders;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/HandlerConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */