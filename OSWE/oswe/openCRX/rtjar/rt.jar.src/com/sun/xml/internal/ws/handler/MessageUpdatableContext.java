/*     */ package com.sun.xml.internal.ws.handler;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public abstract class MessageUpdatableContext
/*     */   implements MessageContext
/*     */ {
/*     */   final Packet packet;
/*     */   private MessageContextImpl ctxt;
/*     */   
/*     */   public MessageUpdatableContext(Packet packet) {
/*  46 */     this.ctxt = new MessageContextImpl(packet);
/*  47 */     this.packet = packet;
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
/*     */   Message getPacketMessage() {
/*  60 */     updateMessage();
/*  61 */     return this.packet.getMessage();
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
/*     */   public final void updatePacket() {
/*  75 */     updateMessage();
/*     */   }
/*     */   
/*     */   MessageContextImpl getMessageContext() {
/*  79 */     return this.ctxt;
/*     */   }
/*     */   
/*     */   public void setScope(String name, MessageContext.Scope scope) {
/*  83 */     this.ctxt.setScope(name, scope);
/*     */   }
/*     */   
/*     */   public MessageContext.Scope getScope(String name) {
/*  87 */     return this.ctxt.getScope(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  93 */     this.ctxt.clear();
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object obj) {
/*  97 */     return this.ctxt.containsKey(obj);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object obj) {
/* 101 */     return this.ctxt.containsValue(obj);
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<String, Object>> entrySet() {
/* 105 */     return this.ctxt.entrySet();
/*     */   }
/*     */   
/*     */   public Object get(Object obj) {
/* 109 */     return this.ctxt.get(obj);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 113 */     return this.ctxt.isEmpty();
/*     */   }
/*     */   
/*     */   public Set<String> keySet() {
/* 117 */     return this.ctxt.keySet();
/*     */   }
/*     */   
/*     */   public Object put(String str, Object obj) {
/* 121 */     return this.ctxt.put(str, obj);
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends Object> map) {
/* 125 */     this.ctxt.putAll(map);
/*     */   }
/*     */   
/*     */   public Object remove(Object obj) {
/* 129 */     return this.ctxt.remove(obj);
/*     */   }
/*     */   
/*     */   public int size() {
/* 133 */     return this.ctxt.size();
/*     */   }
/*     */   
/*     */   public Collection<Object> values() {
/* 137 */     return this.ctxt.values();
/*     */   }
/*     */   
/*     */   abstract void updateMessage();
/*     */   
/*     */   abstract void setPacketMessage(Message paramMessage);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/MessageUpdatableContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */