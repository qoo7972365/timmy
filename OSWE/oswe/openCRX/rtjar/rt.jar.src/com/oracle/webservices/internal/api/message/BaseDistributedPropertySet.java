/*     */ package com.oracle.webservices.internal.api.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.IdentityHashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseDistributedPropertySet
/*     */   extends BasePropertySet
/*     */   implements DistributedPropertySet
/*     */ {
/*  84 */   private final Map<Class<? extends PropertySet>, PropertySet> satellites = new IdentityHashMap<>();
/*     */   
/*     */   private final Map<String, Object> viewthis;
/*     */ 
/*     */   
/*     */   public BaseDistributedPropertySet() {
/*  90 */     this.viewthis = super.createView();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSatellite(@NotNull PropertySet satellite) {
/*  95 */     addSatellite((Class)satellite.getClass(), satellite);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSatellite(@NotNull Class<? extends PropertySet> keyClass, @NotNull PropertySet satellite) {
/* 100 */     this.satellites.put(keyClass, satellite);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeSatellite(PropertySet satellite) {
/* 105 */     this.satellites.remove(satellite.getClass());
/*     */   }
/*     */   
/*     */   public void copySatelliteInto(@NotNull DistributedPropertySet r) {
/* 109 */     for (Map.Entry<Class<? extends PropertySet>, PropertySet> entry : this.satellites.entrySet()) {
/* 110 */       r.addSatellite(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void copySatelliteInto(MessageContext r) {
/* 116 */     copySatelliteInto(r);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <T extends PropertySet> T getSatellite(Class<T> satelliteClass) {
/* 121 */     PropertySet propertySet = this.satellites.get(satelliteClass);
/* 122 */     if (propertySet != null) {
/* 123 */       return (T)propertySet;
/*     */     }
/*     */     
/* 126 */     for (PropertySet child : this.satellites.values()) {
/* 127 */       if (satelliteClass.isInstance(child)) {
/* 128 */         return satelliteClass.cast(child);
/*     */       }
/*     */       
/* 131 */       if (DistributedPropertySet.class.isInstance(child)) {
/* 132 */         propertySet = ((DistributedPropertySet)DistributedPropertySet.class.cast(child)).getSatellite(satelliteClass);
/* 133 */         if (propertySet != null) {
/* 134 */           return (T)propertySet;
/*     */         }
/*     */       } 
/*     */     } 
/* 138 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Class<? extends PropertySet>, PropertySet> getSatellites() {
/* 143 */     return this.satellites;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object key) {
/* 149 */     for (PropertySet child : this.satellites.values()) {
/* 150 */       if (child.supports(key)) {
/* 151 */         return child.get(key);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 156 */     return super.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object put(String key, Object value) {
/* 162 */     for (PropertySet child : this.satellites.values()) {
/* 163 */       if (child.supports(key)) {
/* 164 */         return child.put(key, value);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 169 */     return super.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 174 */     if (this.viewthis.containsKey(key))
/* 175 */       return true; 
/* 176 */     for (PropertySet child : this.satellites.values()) {
/* 177 */       if (child.containsKey(key)) {
/* 178 */         return true;
/*     */       }
/*     */     } 
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supports(Object key) {
/* 187 */     for (PropertySet child : this.satellites.values()) {
/* 188 */       if (child.supports(key)) {
/* 189 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 193 */     return super.supports(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object remove(Object key) {
/* 199 */     for (PropertySet child : this.satellites.values()) {
/* 200 */       if (child.supports(key)) {
/* 201 */         return child.remove(key);
/*     */       }
/*     */     } 
/*     */     
/* 205 */     return super.remove(key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createEntrySet(Set<Map.Entry<String, Object>> core) {
/* 210 */     super.createEntrySet(core);
/* 211 */     for (PropertySet child : this.satellites.values()) {
/* 212 */       ((BasePropertySet)child).createEntrySet(core);
/*     */     }
/*     */   }
/*     */   
/*     */   protected Map<String, Object> asMapLocal() {
/* 217 */     return this.viewthis;
/*     */   }
/*     */   
/*     */   protected boolean supportsLocal(Object key) {
/* 221 */     return super.supports(key);
/*     */   }
/*     */   
/*     */   class DistributedMapView
/*     */     extends AbstractMap<String, Object> {
/*     */     public Object get(Object key) {
/* 227 */       for (PropertySet child : BaseDistributedPropertySet.this.satellites.values()) {
/* 228 */         if (child.supports(key)) {
/* 229 */           return child.get(key);
/*     */         }
/*     */       } 
/*     */       
/* 233 */       return BaseDistributedPropertySet.this.viewthis.get(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 238 */       int size = BaseDistributedPropertySet.this.viewthis.size();
/* 239 */       for (PropertySet child : BaseDistributedPropertySet.this.satellites.values()) {
/* 240 */         size += child.asMap().size();
/*     */       }
/* 242 */       return size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object key) {
/* 247 */       if (BaseDistributedPropertySet.this.viewthis.containsKey(key))
/* 248 */         return true; 
/* 249 */       for (PropertySet child : BaseDistributedPropertySet.this.satellites.values()) {
/* 250 */         if (child.containsKey(key))
/* 251 */           return true; 
/*     */       } 
/* 253 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<String, Object>> entrySet() {
/* 258 */       Set<Map.Entry<String, Object>> entries = new HashSet<>();
/* 259 */       for (PropertySet child : BaseDistributedPropertySet.this.satellites.values()) {
/* 260 */         for (Map.Entry<String, Object> entry : child.asMap().entrySet())
/*     */         {
/*     */           
/* 263 */           entries.add(new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), entry.getValue()));
/*     */         }
/*     */       } 
/* 266 */       for (Map.Entry<String, Object> entry : (Iterable<Map.Entry<String, Object>>)BaseDistributedPropertySet.this.viewthis.entrySet())
/*     */       {
/*     */         
/* 269 */         entries.add(new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), entry.getValue()));
/*     */       }
/*     */       
/* 272 */       return entries;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object put(String key, Object value) {
/* 277 */       for (PropertySet child : BaseDistributedPropertySet.this.satellites.values()) {
/* 278 */         if (child.supports(key)) {
/* 279 */           return child.put(key, value);
/*     */         }
/*     */       } 
/*     */       
/* 283 */       return BaseDistributedPropertySet.this.viewthis.put(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 288 */       BaseDistributedPropertySet.this.satellites.clear();
/* 289 */       BaseDistributedPropertySet.this.viewthis.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object remove(Object key) {
/* 294 */       for (PropertySet child : BaseDistributedPropertySet.this.satellites.values()) {
/* 295 */         if (child.supports(key)) {
/* 296 */           return child.remove(key);
/*     */         }
/*     */       } 
/*     */       
/* 300 */       return BaseDistributedPropertySet.this.viewthis.remove(key);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Map<String, Object> createView() {
/* 306 */     return new DistributedMapView();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/webservices/internal/api/message/BaseDistributedPropertySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */