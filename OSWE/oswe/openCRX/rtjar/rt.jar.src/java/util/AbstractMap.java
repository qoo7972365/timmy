/*     */ package java.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractMap<K, V>
/*     */   implements Map<K, V>
/*     */ {
/*     */   transient Set<K> keySet;
/*     */   transient Collection<V> values;
/*     */   
/*     */   public int size() {
/*  85 */     return entrySet().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  95 */     return (size() == 0);
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
/*     */   public boolean containsValue(Object paramObject) {
/* 112 */     Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
/* 113 */     if (paramObject == null) {
/* 114 */       while (iterator.hasNext()) {
/* 115 */         Map.Entry entry = iterator.next();
/* 116 */         if (entry.getValue() == null)
/* 117 */           return true; 
/*     */       } 
/*     */     } else {
/* 120 */       while (iterator.hasNext()) {
/* 121 */         Map.Entry entry = iterator.next();
/* 122 */         if (paramObject.equals(entry.getValue()))
/* 123 */           return true; 
/*     */       } 
/*     */     } 
/* 126 */     return false;
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
/*     */   public boolean containsKey(Object paramObject) {
/* 144 */     Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
/* 145 */     if (paramObject == null) {
/* 146 */       while (iterator.hasNext()) {
/* 147 */         Map.Entry entry = iterator.next();
/* 148 */         if (entry.getKey() == null)
/* 149 */           return true; 
/*     */       } 
/*     */     } else {
/* 152 */       while (iterator.hasNext()) {
/* 153 */         Map.Entry entry = iterator.next();
/* 154 */         if (paramObject.equals(entry.getKey()))
/* 155 */           return true; 
/*     */       } 
/*     */     } 
/* 158 */     return false;
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
/*     */   public V get(Object paramObject) {
/* 176 */     Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
/* 177 */     if (paramObject == null) {
/* 178 */       while (iterator.hasNext()) {
/* 179 */         Map.Entry entry = iterator.next();
/* 180 */         if (entry.getKey() == null)
/* 181 */           return (V)entry.getValue(); 
/*     */       } 
/*     */     } else {
/* 184 */       while (iterator.hasNext()) {
/* 185 */         Map.Entry entry = iterator.next();
/* 186 */         if (paramObject.equals(entry.getKey()))
/* 187 */           return (V)entry.getValue(); 
/*     */       } 
/*     */     } 
/* 190 */     return null;
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
/*     */   public V put(K paramK, V paramV) {
/* 209 */     throw new UnsupportedOperationException();
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
/*     */   public V remove(Object paramObject) {
/* 235 */     Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
/* 236 */     Map.Entry entry = null;
/* 237 */     if (paramObject == null) {
/* 238 */       while (entry == null && iterator.hasNext()) {
/* 239 */         Map.Entry entry1 = iterator.next();
/* 240 */         if (entry1.getKey() == null)
/* 241 */           entry = entry1; 
/*     */       } 
/*     */     } else {
/* 244 */       while (entry == null && iterator.hasNext()) {
/* 245 */         Map.Entry entry1 = iterator.next();
/* 246 */         if (paramObject.equals(entry1.getKey())) {
/* 247 */           entry = entry1;
/*     */         }
/*     */       } 
/*     */     } 
/* 251 */     Object object = null;
/* 252 */     if (entry != null) {
/* 253 */       object = entry.getValue();
/* 254 */       iterator.remove();
/*     */     } 
/* 256 */     return (V)object;
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
/*     */   public void putAll(Map<? extends K, ? extends V> paramMap) {
/* 280 */     for (Map.Entry<? extends K, ? extends V> entry : paramMap.entrySet()) {
/* 281 */       put((K)entry.getKey(), (V)entry.getValue());
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
/*     */   public void clear() {
/* 297 */     entrySet().clear();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<K> keySet() {
/* 347 */     Set<K> set = this.keySet;
/* 348 */     if (set == null) {
/* 349 */       set = new AbstractSet<K>() {
/*     */           public Iterator<K> iterator() {
/* 351 */             return new Iterator<K>() {
/* 352 */                 private Iterator<Map.Entry<K, V>> i = AbstractMap.this.entrySet().iterator();
/*     */                 
/*     */                 public boolean hasNext() {
/* 355 */                   return this.i.hasNext();
/*     */                 }
/*     */                 
/*     */                 public K next() {
/* 359 */                   return (K)((Map.Entry)this.i.next()).getKey();
/*     */                 }
/*     */                 
/*     */                 public void remove() {
/* 363 */                   this.i.remove();
/*     */                 }
/*     */               };
/*     */           }
/*     */           
/*     */           public int size() {
/* 369 */             return AbstractMap.this.size();
/*     */           }
/*     */           
/*     */           public boolean isEmpty() {
/* 373 */             return AbstractMap.this.isEmpty();
/*     */           }
/*     */           
/*     */           public void clear() {
/* 377 */             AbstractMap.this.clear();
/*     */           }
/*     */           
/*     */           public boolean contains(Object param1Object) {
/* 381 */             return AbstractMap.this.containsKey(param1Object);
/*     */           }
/*     */         };
/* 384 */       this.keySet = set;
/*     */     } 
/* 386 */     return set;
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
/*     */   public Collection<V> values() {
/* 406 */     Collection<V> collection = this.values;
/* 407 */     if (collection == null) {
/* 408 */       collection = new AbstractCollection<V>() {
/*     */           public Iterator<V> iterator() {
/* 410 */             return new Iterator<V>() {
/* 411 */                 private Iterator<Map.Entry<K, V>> i = AbstractMap.this.entrySet().iterator();
/*     */                 
/*     */                 public boolean hasNext() {
/* 414 */                   return this.i.hasNext();
/*     */                 }
/*     */                 
/*     */                 public V next() {
/* 418 */                   return (V)((Map.Entry)this.i.next()).getValue();
/*     */                 }
/*     */                 
/*     */                 public void remove() {
/* 422 */                   this.i.remove();
/*     */                 }
/*     */               };
/*     */           }
/*     */           
/*     */           public int size() {
/* 428 */             return AbstractMap.this.size();
/*     */           }
/*     */           
/*     */           public boolean isEmpty() {
/* 432 */             return AbstractMap.this.isEmpty();
/*     */           }
/*     */           
/*     */           public void clear() {
/* 436 */             AbstractMap.this.clear();
/*     */           }
/*     */           
/*     */           public boolean contains(Object param1Object) {
/* 440 */             return AbstractMap.this.containsValue(param1Object);
/*     */           }
/*     */         };
/* 443 */       this.values = collection;
/*     */     } 
/* 445 */     return collection;
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
/*     */   public boolean equals(Object paramObject) {
/* 476 */     if (paramObject == this) {
/* 477 */       return true;
/*     */     }
/* 479 */     if (!(paramObject instanceof Map))
/* 480 */       return false; 
/* 481 */     Map map = (Map)paramObject;
/* 482 */     if (map.size() != size()) {
/* 483 */       return false;
/*     */     }
/*     */     try {
/* 486 */       Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
/* 487 */       while (iterator.hasNext()) {
/* 488 */         Map.Entry entry = iterator.next();
/* 489 */         Object object1 = entry.getKey();
/* 490 */         Object object2 = entry.getValue();
/* 491 */         if (object2 == null) {
/* 492 */           if (map.get(object1) != null || !map.containsKey(object1))
/* 493 */             return false;  continue;
/*     */         } 
/* 495 */         if (!object2.equals(map.get(object1))) {
/* 496 */           return false;
/*     */         }
/*     */       } 
/* 499 */     } catch (ClassCastException classCastException) {
/* 500 */       return false;
/* 501 */     } catch (NullPointerException nullPointerException) {
/* 502 */       return false;
/*     */     } 
/*     */     
/* 505 */     return true;
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
/*     */   public int hashCode() {
/* 527 */     int i = 0;
/* 528 */     Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
/* 529 */     while (iterator.hasNext())
/* 530 */       i += ((Map.Entry)iterator.next()).hashCode(); 
/* 531 */     return i;
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
/*     */   public String toString() {
/* 547 */     Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
/* 548 */     if (!iterator.hasNext()) {
/* 549 */       return "{}";
/*     */     }
/* 551 */     StringBuilder stringBuilder = new StringBuilder();
/* 552 */     stringBuilder.append('{');
/*     */     while (true) {
/* 554 */       Map.Entry entry = iterator.next();
/* 555 */       Object object1 = entry.getKey();
/* 556 */       Object object2 = entry.getValue();
/* 557 */       stringBuilder.append((object1 == this) ? "(this Map)" : object1);
/* 558 */       stringBuilder.append('=');
/* 559 */       stringBuilder.append((object2 == this) ? "(this Map)" : object2);
/* 560 */       if (!iterator.hasNext())
/* 561 */         return stringBuilder.append('}').toString(); 
/* 562 */       stringBuilder.append(',').append(' ');
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 573 */     AbstractMap abstractMap = (AbstractMap)super.clone();
/* 574 */     abstractMap.keySet = null;
/* 575 */     abstractMap.values = null;
/* 576 */     return abstractMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean eq(Object paramObject1, Object paramObject2) {
/* 586 */     return (paramObject1 == null) ? ((paramObject2 == null)) : paramObject1.equals(paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Set<Map.Entry<K, V>> entrySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SimpleEntry<K, V>
/*     */     implements Map.Entry<K, V>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -8499721149061103585L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final K key;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private V value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SimpleEntry(K param1K, V param1V) {
/* 623 */       this.key = param1K;
/* 624 */       this.value = param1V;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SimpleEntry(Map.Entry<? extends K, ? extends V> param1Entry) {
/* 634 */       this.key = param1Entry.getKey();
/* 635 */       this.value = param1Entry.getValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public K getKey() {
/* 644 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public V getValue() {
/* 653 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public V setValue(V param1V) {
/* 664 */       V v = this.value;
/* 665 */       this.value = param1V;
/* 666 */       return v;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 691 */       if (!(param1Object instanceof Map.Entry))
/* 692 */         return false; 
/* 693 */       Map.Entry entry = (Map.Entry)param1Object;
/* 694 */       return (AbstractMap.eq(this.key, entry.getKey()) && AbstractMap.eq(this.value, entry.getValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 711 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value
/* 712 */         .hashCode());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 724 */       return (new StringBuilder()).append(this.key).append("=").append(this.value).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SimpleImmutableEntry<K, V>
/*     */     implements Map.Entry<K, V>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7138329143949025153L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final K key;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final V value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SimpleImmutableEntry(K param1K, V param1V) {
/* 753 */       this.key = param1K;
/* 754 */       this.value = param1V;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SimpleImmutableEntry(Map.Entry<? extends K, ? extends V> param1Entry) {
/* 764 */       this.key = param1Entry.getKey();
/* 765 */       this.value = param1Entry.getValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public K getKey() {
/* 774 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public V getValue() {
/* 783 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public V setValue(V param1V) {
/* 797 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 822 */       if (!(param1Object instanceof Map.Entry))
/* 823 */         return false; 
/* 824 */       Map.Entry entry = (Map.Entry)param1Object;
/* 825 */       return (AbstractMap.eq(this.key, entry.getKey()) && AbstractMap.eq(this.value, entry.getValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 842 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value
/* 843 */         .hashCode());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 855 */       return (new StringBuilder()).append(this.key).append("=").append(this.value).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/AbstractMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */