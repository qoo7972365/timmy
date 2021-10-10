/*     */ package com.sun.xml.internal.ws.api;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.BasePropertySet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PropertySet
/*     */   extends BasePropertySet
/*     */ {
/*     */   protected static class PropertyMap
/*     */     extends BasePropertySet.PropertyMap {}
/*     */   
/*     */   protected static PropertyMap parse(Class clazz) {
/*  53 */     BasePropertySet.PropertyMap pm = BasePropertySet.parse(clazz);
/*  54 */     PropertyMap map = new PropertyMap();
/*  55 */     map.putAll((Map)pm);
/*  56 */     return map;
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
/*     */   public Object get(Object key) {
/*  68 */     BasePropertySet.Accessor sp = (BasePropertySet.Accessor)getPropertyMap().get(key);
/*  69 */     if (sp != null)
/*  70 */       return sp.get((com.oracle.webservices.internal.api.message.PropertySet)this); 
/*  71 */     throw new IllegalArgumentException("Undefined property " + key);
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
/*     */   public Object put(String key, Object value) {
/*  88 */     BasePropertySet.Accessor sp = (BasePropertySet.Accessor)getPropertyMap().get(key);
/*  89 */     if (sp != null) {
/*  90 */       Object old = sp.get((com.oracle.webservices.internal.api.message.PropertySet)this);
/*  91 */       sp.set((com.oracle.webservices.internal.api.message.PropertySet)this, value);
/*  92 */       return old;
/*     */     } 
/*  94 */     throw new IllegalArgumentException("Undefined property " + key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supports(Object key) {
/*  99 */     return getPropertyMap().containsKey(key);
/*     */   }
/*     */   
/*     */   public Object remove(Object key) {
/* 103 */     BasePropertySet.Accessor sp = (BasePropertySet.Accessor)getPropertyMap().get(key);
/* 104 */     if (sp != null) {
/* 105 */       Object old = sp.get((com.oracle.webservices.internal.api.message.PropertySet)this);
/* 106 */       sp.set((com.oracle.webservices.internal.api.message.PropertySet)this, null);
/* 107 */       return old;
/*     */     } 
/* 109 */     throw new IllegalArgumentException("Undefined property " + key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createEntrySet(Set<Map.Entry<String, Object>> core) {
/* 114 */     for (Map.Entry<String, BasePropertySet.Accessor> e : (Iterable<Map.Entry<String, BasePropertySet.Accessor>>)getPropertyMap().entrySet()) {
/* 115 */       core.add(new Map.Entry<String, Object>() {
/*     */             public String getKey() {
/* 117 */               return (String)e.getKey();
/*     */             }
/*     */             
/*     */             public Object getValue() {
/* 121 */               return ((BasePropertySet.Accessor)e.getValue()).get((com.oracle.webservices.internal.api.message.PropertySet)PropertySet.this);
/*     */             }
/*     */             
/*     */             public Object setValue(Object value) {
/* 125 */               BasePropertySet.Accessor acc = (BasePropertySet.Accessor)e.getValue();
/* 126 */               Object old = acc.get((com.oracle.webservices.internal.api.message.PropertySet)PropertySet.this);
/* 127 */               acc.set((com.oracle.webservices.internal.api.message.PropertySet)PropertySet.this, value);
/* 128 */               return old;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract PropertyMap getPropertyMap();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/PropertySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */