/*     */ package com.sun.xml.internal.ws.handler;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.activation.DataHandler;
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
/*     */ class MessageContextImpl
/*     */   implements MessageContext
/*     */ {
/*     */   private final Set<String> handlerScopeProps;
/*     */   private final Packet packet;
/*     */   private final Map<String, Object> asMapIncludingInvocationProperties;
/*     */   
/*     */   public MessageContextImpl(Packet packet) {
/*  52 */     this.packet = packet;
/*  53 */     this.asMapIncludingInvocationProperties = packet.asMapIncludingInvocationProperties();
/*  54 */     this.handlerScopeProps = packet.getHandlerScopePropertyNames(false);
/*     */   }
/*     */   
/*     */   protected void updatePacket() {
/*  58 */     throw new UnsupportedOperationException("wrong call");
/*     */   }
/*     */   
/*     */   public void setScope(String name, MessageContext.Scope scope) {
/*  62 */     if (!containsKey(name))
/*  63 */       throw new IllegalArgumentException("Property " + name + " does not exist."); 
/*  64 */     if (scope == MessageContext.Scope.APPLICATION) {
/*  65 */       this.handlerScopeProps.remove(name);
/*     */     } else {
/*  67 */       this.handlerScopeProps.add(name);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageContext.Scope getScope(String name) {
/*  73 */     if (!containsKey(name))
/*  74 */       throw new IllegalArgumentException("Property " + name + " does not exist."); 
/*  75 */     if (this.handlerScopeProps.contains(name)) {
/*  76 */       return MessageContext.Scope.HANDLER;
/*     */     }
/*  78 */     return MessageContext.Scope.APPLICATION;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  83 */     return this.asMapIncludingInvocationProperties.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  87 */     return this.asMapIncludingInvocationProperties.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object key) {
/*  91 */     return this.asMapIncludingInvocationProperties.containsKey(key);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object value) {
/*  95 */     return this.asMapIncludingInvocationProperties.containsValue(value);
/*     */   }
/*     */   
/*     */   public Object put(String key, Object value) {
/*  99 */     if (!this.asMapIncludingInvocationProperties.containsKey(key))
/*     */     {
/* 101 */       this.handlerScopeProps.add(key);
/*     */     }
/* 103 */     return this.asMapIncludingInvocationProperties.put(key, value);
/*     */   }
/*     */   public Object get(Object key) {
/* 106 */     if (key == null)
/* 107 */       return null; 
/* 108 */     Object value = this.asMapIncludingInvocationProperties.get(key);
/*     */     
/* 110 */     if (key.equals("javax.xml.ws.binding.attachments.outbound") || key
/* 111 */       .equals("javax.xml.ws.binding.attachments.inbound")) {
/* 112 */       Map<String, DataHandler> atts = (Map<String, DataHandler>)value;
/* 113 */       if (atts == null)
/* 114 */         atts = new HashMap<>(); 
/* 115 */       AttachmentSet attSet = this.packet.getMessage().getAttachments();
/* 116 */       for (Attachment att : attSet) {
/* 117 */         String cid = att.getContentId();
/* 118 */         if (cid.indexOf("@jaxws.sun.com") == -1) {
/* 119 */           Object a = atts.get(cid);
/* 120 */           if (a == null) {
/* 121 */             a = atts.get("<" + cid + ">");
/* 122 */             if (a == null) atts.put(att.getContentId(), att.asDataHandler()); 
/*     */           }  continue;
/*     */         } 
/* 125 */         atts.put(att.getContentId(), att.asDataHandler());
/*     */       } 
/*     */       
/* 128 */       return atts;
/*     */     } 
/* 130 */     return value;
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends Object> t) {
/* 134 */     for (String key : t.keySet()) {
/* 135 */       if (!this.asMapIncludingInvocationProperties.containsKey(key))
/*     */       {
/* 137 */         this.handlerScopeProps.add(key);
/*     */       }
/*     */     } 
/* 140 */     this.asMapIncludingInvocationProperties.putAll(t);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 144 */     this.asMapIncludingInvocationProperties.clear();
/*     */   }
/*     */   public Object remove(Object key) {
/* 147 */     this.handlerScopeProps.remove(key);
/* 148 */     return this.asMapIncludingInvocationProperties.remove(key);
/*     */   }
/*     */   public Set<String> keySet() {
/* 151 */     return this.asMapIncludingInvocationProperties.keySet();
/*     */   }
/*     */   public Set<Map.Entry<String, Object>> entrySet() {
/* 154 */     return this.asMapIncludingInvocationProperties.entrySet();
/*     */   }
/*     */   public Collection<Object> values() {
/* 157 */     return this.asMapIncludingInvocationProperties.values();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/MessageContextImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */