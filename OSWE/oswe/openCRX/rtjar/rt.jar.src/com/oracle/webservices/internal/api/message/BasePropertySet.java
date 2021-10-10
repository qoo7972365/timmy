/*     */ package com.oracle.webservices.internal.api.message;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
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
/*     */ public abstract class BasePropertySet
/*     */   implements PropertySet
/*     */ {
/*     */   private Map<String, Object> mapView;
/*     */   
/*     */   protected abstract PropertyMap getPropertyMap();
/*     */   
/*     */   protected static class PropertyMap
/*     */     extends HashMap<String, Accessor>
/*     */   {
/*  72 */     transient BasePropertySet.PropertyMapEntry[] cachedEntries = null;
/*     */     
/*     */     BasePropertySet.PropertyMapEntry[] getPropertyMapEntries() {
/*  75 */       if (this.cachedEntries == null) {
/*  76 */         this.cachedEntries = createPropertyMapEntries();
/*     */       }
/*  78 */       return this.cachedEntries;
/*     */     }
/*     */     
/*     */     private BasePropertySet.PropertyMapEntry[] createPropertyMapEntries() {
/*  82 */       BasePropertySet.PropertyMapEntry[] modelEntries = new BasePropertySet.PropertyMapEntry[size()];
/*  83 */       int i = 0;
/*  84 */       for (Map.Entry<String, BasePropertySet.Accessor> e : entrySet()) {
/*  85 */         modelEntries[i++] = new BasePropertySet.PropertyMapEntry(e.getKey(), e.getValue());
/*     */       }
/*  87 */       return modelEntries;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PropertyMapEntry
/*     */   {
/*     */     String key;
/*     */     BasePropertySet.Accessor value;
/*     */     
/*     */     public PropertyMapEntry(String k, BasePropertySet.Accessor v) {
/*  97 */       this.key = k; this.value = v;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static PropertyMap parse(final Class clazz) {
/* 129 */     return AccessController.<PropertyMap>doPrivileged(new PrivilegedAction<PropertyMap>()
/*     */         {
/*     */           public BasePropertySet.PropertyMap run() {
/* 132 */             BasePropertySet.PropertyMap props = new BasePropertySet.PropertyMap();
/* 133 */             for (Class c = clazz; c != null; c = c.getSuperclass()) {
/* 134 */               for (Field f : c.getDeclaredFields()) {
/* 135 */                 PropertySet.Property cp = f.<PropertySet.Property>getAnnotation(PropertySet.Property.class);
/* 136 */                 if (cp != null) {
/* 137 */                   for (String value : cp.value()) {
/* 138 */                     props.put(value, new BasePropertySet.FieldAccessor(f, value));
/*     */                   }
/*     */                 }
/*     */               } 
/* 142 */               for (Method m : c.getDeclaredMethods()) {
/* 143 */                 PropertySet.Property cp = m.<PropertySet.Property>getAnnotation(PropertySet.Property.class);
/* 144 */                 if (cp != null) {
/* 145 */                   Method setter; String name = m.getName();
/* 146 */                   assert name.startsWith("get") || name.startsWith("is");
/*     */ 
/*     */                   
/* 149 */                   String setName = name.startsWith("is") ? ("set" + name.substring(2)) : ('s' + name.substring(1));
/*     */                   
/*     */                   try {
/* 152 */                     setter = clazz.getMethod(setName, new Class[] { m.getReturnType() });
/* 153 */                   } catch (NoSuchMethodException e) {
/* 154 */                     setter = null;
/*     */                   } 
/* 156 */                   for (String value : cp.value()) {
/* 157 */                     props.put(value, new BasePropertySet.MethodAccessor(m, setter, value));
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 163 */             return props;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class FieldAccessor
/*     */     implements Accessor
/*     */   {
/*     */     private final Field f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected FieldAccessor(Field f, String name) {
/* 190 */       this.f = f;
/* 191 */       f.setAccessible(true);
/* 192 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 197 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasValue(PropertySet props) {
/* 202 */       return (get(props) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object get(PropertySet props) {
/*     */       try {
/* 208 */         return this.f.get(props);
/* 209 */       } catch (IllegalAccessException e) {
/* 210 */         throw new AssertionError();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(PropertySet props, Object value) {
/*     */       try {
/* 217 */         this.f.set(props, value);
/* 218 */       } catch (IllegalAccessException e) {
/* 219 */         throw new AssertionError();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class MethodAccessor
/*     */     implements Accessor
/*     */   {
/*     */     @NotNull
/*     */     private final Method getter;
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private final Method setter;
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     protected MethodAccessor(Method getter, Method setter, String value) {
/* 241 */       this.getter = getter;
/* 242 */       this.setter = setter;
/* 243 */       this.name = value;
/* 244 */       getter.setAccessible(true);
/* 245 */       if (setter != null) {
/* 246 */         setter.setAccessible(true);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 252 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasValue(PropertySet props) {
/* 257 */       return (get(props) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object get(PropertySet props) {
/*     */       try {
/* 263 */         return this.getter.invoke(props, new Object[0]);
/* 264 */       } catch (IllegalAccessException e) {
/* 265 */         throw new AssertionError();
/* 266 */       } catch (InvocationTargetException e) {
/* 267 */         handle(e);
/* 268 */         return Integer.valueOf(0);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(PropertySet props, Object value) {
/* 274 */       if (this.setter == null) {
/* 275 */         throw new ReadOnlyPropertyException(getName());
/*     */       }
/*     */       try {
/* 278 */         this.setter.invoke(props, new Object[] { value });
/* 279 */       } catch (IllegalAccessException e) {
/* 280 */         throw new AssertionError();
/* 281 */       } catch (InvocationTargetException e) {
/* 282 */         handle(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Exception handle(InvocationTargetException e) {
/* 292 */       Throwable t = e.getTargetException();
/* 293 */       if (t instanceof Error) {
/* 294 */         throw (Error)t;
/*     */       }
/* 296 */       if (t instanceof RuntimeException) {
/* 297 */         throw (RuntimeException)t;
/*     */       }
/* 299 */       throw new Error(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final class MapView
/*     */     extends HashMap<String, Object>
/*     */   {
/*     */     boolean extensible;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MapView(boolean extensible) {
/* 318 */       super((BasePropertySet.this.getPropertyMap().getPropertyMapEntries()).length);
/* 319 */       this.extensible = extensible;
/* 320 */       initialize();
/*     */     }
/*     */ 
/*     */     
/*     */     public void initialize() {
/* 325 */       BasePropertySet.PropertyMapEntry[] entries = BasePropertySet.this.getPropertyMap().getPropertyMapEntries();
/* 326 */       for (BasePropertySet.PropertyMapEntry entry : entries) {
/* 327 */         super.put(entry.key, entry.value);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Object get(Object key) {
/* 333 */       Object o = super.get(key);
/* 334 */       if (o instanceof BasePropertySet.Accessor) {
/* 335 */         return ((BasePropertySet.Accessor)o).get(BasePropertySet.this);
/*     */       }
/* 337 */       return o;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<String, Object>> entrySet() {
/* 343 */       Set<Map.Entry<String, Object>> entries = new HashSet<>();
/* 344 */       for (String key : keySet()) {
/* 345 */         entries.add(new AbstractMap.SimpleImmutableEntry<>(key, get(key)));
/*     */       }
/* 347 */       return entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object put(String key, Object value) {
/* 353 */       Object o = super.get(key);
/* 354 */       if (o != null && o instanceof BasePropertySet.Accessor) {
/*     */         
/* 356 */         Object oldValue = ((BasePropertySet.Accessor)o).get(BasePropertySet.this);
/* 357 */         ((BasePropertySet.Accessor)o).set(BasePropertySet.this, value);
/* 358 */         return oldValue;
/*     */       } 
/*     */ 
/*     */       
/* 362 */       if (this.extensible) {
/* 363 */         return super.put(key, value);
/*     */       }
/* 365 */       throw new IllegalStateException("Unknown property [" + key + "] for PropertySet [" + BasePropertySet.this
/* 366 */           .getClass().getName() + "]");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {
/* 373 */       for (String key : keySet()) {
/* 374 */         remove(key);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object remove(Object key) {
/* 381 */       Object o = super.get(key);
/* 382 */       if (o instanceof BasePropertySet.Accessor) {
/* 383 */         ((BasePropertySet.Accessor)o).set(BasePropertySet.this, null);
/*     */       }
/* 385 */       return super.remove(key);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 391 */     Accessor sp = getPropertyMap().get(key);
/* 392 */     if (sp != null) {
/* 393 */       return (sp.get(this) != null);
/*     */     }
/* 395 */     return false;
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
/*     */   public Object get(Object key) {
/* 408 */     Accessor sp = getPropertyMap().get(key);
/* 409 */     if (sp != null) {
/* 410 */       return sp.get(this);
/*     */     }
/* 412 */     throw new IllegalArgumentException("Undefined property " + key);
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
/*     */   public Object put(String key, Object value) {
/* 430 */     Accessor sp = getPropertyMap().get(key);
/* 431 */     if (sp != null) {
/* 432 */       Object old = sp.get(this);
/* 433 */       sp.set(this, value);
/* 434 */       return old;
/*     */     } 
/* 436 */     throw new IllegalArgumentException("Undefined property " + key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supports(Object key) {
/* 445 */     return getPropertyMap().containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object remove(Object key) {
/* 450 */     Accessor sp = getPropertyMap().get(key);
/* 451 */     if (sp != null) {
/* 452 */       Object old = sp.get(this);
/* 453 */       sp.set(this, null);
/* 454 */       return old;
/*     */     } 
/* 456 */     throw new IllegalArgumentException("Undefined property " + key);
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final Map<String, Object> createMapView() {
/* 480 */     final Set<Map.Entry<String, Object>> core = new HashSet<>();
/* 481 */     createEntrySet(core);
/*     */     
/* 483 */     return new AbstractMap<String, Object>()
/*     */       {
/*     */         public Set<Map.Entry<String, Object>> entrySet() {
/* 486 */           return core;
/*     */         }
/*     */       };
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
/*     */   public Map<String, Object> asMap() {
/* 505 */     if (this.mapView == null) {
/* 506 */       this.mapView = createView();
/*     */     }
/* 508 */     return this.mapView;
/*     */   }
/*     */   
/*     */   protected Map<String, Object> createView() {
/* 512 */     return new MapView(mapAllowsAdditionalProperties());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean mapAllowsAdditionalProperties() {
/* 522 */     return false;
/*     */   }
/*     */   
/*     */   protected void createEntrySet(Set<Map.Entry<String, Object>> core) {
/* 526 */     for (Map.Entry<String, Accessor> e : getPropertyMap().entrySet()) {
/* 527 */       core.add(new Map.Entry<String, Object>()
/*     */           {
/*     */             public String getKey() {
/* 530 */               return (String)e.getKey();
/*     */             }
/*     */ 
/*     */             
/*     */             public Object getValue() {
/* 535 */               return ((BasePropertySet.Accessor)e.getValue()).get(BasePropertySet.this);
/*     */             }
/*     */ 
/*     */             
/*     */             public Object setValue(Object value) {
/* 540 */               BasePropertySet.Accessor acc = (BasePropertySet.Accessor)e.getValue();
/* 541 */               Object old = acc.get(BasePropertySet.this);
/* 542 */               acc.set(BasePropertySet.this, value);
/* 543 */               return old;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static interface Accessor {
/*     */     String getName();
/*     */     
/*     */     boolean hasValue(PropertySet param1PropertySet);
/*     */     
/*     */     Object get(PropertySet param1PropertySet);
/*     */     
/*     */     void set(PropertySet param1PropertySet, Object param1Object);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/webservices/internal/api/message/BasePropertySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */