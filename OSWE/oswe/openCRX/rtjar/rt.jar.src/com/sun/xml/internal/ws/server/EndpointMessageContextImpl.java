/*     */ package com.sun.xml.internal.ws.server;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ public final class EndpointMessageContextImpl
/*     */   extends AbstractMap<String, Object>
/*     */   implements MessageContext
/*     */ {
/*     */   private Set<Map.Entry<String, Object>> entrySet;
/*     */   private final Packet packet;
/*     */   
/*     */   public EndpointMessageContextImpl(Packet packet) {
/*  67 */     this.packet = packet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object key) {
/*  73 */     if (this.packet.supports(key)) {
/*  74 */       return this.packet.get(key);
/*     */     }
/*  76 */     if (this.packet.getHandlerScopePropertyNames(true).contains(key)) {
/*  77 */       return null;
/*     */     }
/*  79 */     Object value = this.packet.invocationProperties.get(key);
/*     */ 
/*     */     
/*  82 */     if (key.equals("javax.xml.ws.binding.attachments.outbound") || key
/*  83 */       .equals("javax.xml.ws.binding.attachments.inbound")) {
/*  84 */       Map<String, DataHandler> atts = (Map<String, DataHandler>)value;
/*  85 */       if (atts == null)
/*  86 */         atts = new HashMap<>(); 
/*  87 */       AttachmentSet attSet = this.packet.getMessage().getAttachments();
/*  88 */       for (Attachment att : attSet) {
/*  89 */         atts.put(att.getContentId(), att.asDataHandler());
/*     */       }
/*  91 */       return atts;
/*     */     } 
/*  93 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object put(String key, Object value) {
/*  98 */     if (this.packet.supports(key)) {
/*  99 */       return this.packet.put(key, value);
/*     */     }
/* 101 */     Object old = this.packet.invocationProperties.get(key);
/* 102 */     if (old != null) {
/* 103 */       if (this.packet.getHandlerScopePropertyNames(true).contains(key)) {
/* 104 */         throw new IllegalArgumentException("Cannot overwrite property in HANDLER scope");
/*     */       }
/*     */       
/* 107 */       this.packet.invocationProperties.put(key, value);
/* 108 */       return old;
/*     */     } 
/*     */     
/* 111 */     this.packet.invocationProperties.put(key, value);
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object remove(Object key) {
/* 118 */     if (this.packet.supports(key)) {
/* 119 */       return this.packet.remove(key);
/*     */     }
/* 121 */     Object old = this.packet.invocationProperties.get(key);
/* 122 */     if (old != null) {
/* 123 */       if (this.packet.getHandlerScopePropertyNames(true).contains(key)) {
/* 124 */         throw new IllegalArgumentException("Cannot remove property in HANDLER scope");
/*     */       }
/*     */       
/* 127 */       this.packet.invocationProperties.remove(key);
/* 128 */       return old;
/*     */     } 
/*     */     
/* 131 */     return null;
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<String, Object>> entrySet() {
/* 135 */     if (this.entrySet == null) {
/* 136 */       this.entrySet = new EntrySet();
/*     */     }
/* 138 */     return this.entrySet;
/*     */   }
/*     */   
/*     */   public void setScope(String name, MessageContext.Scope scope) {
/* 142 */     throw new UnsupportedOperationException("All the properties in this context are in APPLICATION scope. Cannot do setScope().");
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageContext.Scope getScope(String name) {
/* 147 */     throw new UnsupportedOperationException("All the properties in this context are in APPLICATION scope. Cannot do getScope().");
/*     */   }
/*     */   
/*     */   private class EntrySet extends AbstractSet<Map.Entry<String, Object>> {
/*     */     private EntrySet() {}
/*     */     
/*     */     public Iterator<Map.Entry<String, Object>> iterator() {
/* 154 */       final Iterator<Map.Entry<String, Object>> it = EndpointMessageContextImpl.this.createBackupMap().entrySet().iterator();
/*     */       
/* 156 */       return new Iterator<Map.Entry<String, Object>>() {
/*     */           Map.Entry<String, Object> cur;
/*     */           
/*     */           public boolean hasNext() {
/* 160 */             return it.hasNext();
/*     */           }
/*     */           
/*     */           public Map.Entry<String, Object> next() {
/* 164 */             this.cur = it.next();
/* 165 */             return this.cur;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 169 */             it.remove();
/* 170 */             EndpointMessageContextImpl.this.remove(this.cur.getKey());
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 176 */       return EndpointMessageContextImpl.this.createBackupMap().size();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<String, Object> createBackupMap() {
/* 182 */     Map<String, Object> backupMap = new HashMap<>();
/* 183 */     backupMap.putAll(this.packet.createMapView());
/* 184 */     Set<String> handlerProps = this.packet.getHandlerScopePropertyNames(true);
/* 185 */     for (Map.Entry<String, Object> e : (Iterable<Map.Entry<String, Object>>)this.packet.invocationProperties.entrySet()) {
/* 186 */       if (!handlerProps.contains(e.getKey())) {
/* 187 */         backupMap.put(e.getKey(), e.getValue());
/*     */       }
/*     */     } 
/* 190 */     return backupMap;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/EndpointMessageContextImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */