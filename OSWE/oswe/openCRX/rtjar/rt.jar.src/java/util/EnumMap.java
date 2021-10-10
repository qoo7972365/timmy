/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnumMap<K extends Enum<K>, V>
/*     */   extends AbstractMap<K, V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private final Class<K> keyType;
/*     */   private transient K[] keyUniverse;
/*     */   private transient Object[] vals;
/* 104 */   private transient int size = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private static final Object NULL = new Object() {
/*     */       public int hashCode() {
/* 111 */         return 0;
/*     */       }
/*     */       
/*     */       public String toString() {
/* 115 */         return "java.util.EnumMap.NULL";
/*     */       }
/*     */     };
/*     */   
/*     */   private Object maskNull(Object paramObject) {
/* 120 */     return (paramObject == null) ? NULL : paramObject;
/*     */   }
/*     */ 
/*     */   
/*     */   private V unmaskNull(Object paramObject) {
/* 125 */     return (paramObject == NULL) ? null : (V)paramObject;
/*     */   }
/*     */   
/* 128 */   private static final Enum<?>[] ZERO_LENGTH_ENUM_ARRAY = (Enum<?>[])new Enum[0];
/*     */ 
/*     */   
/*     */   private transient Set<Map.Entry<K, V>> entrySet;
/*     */   
/*     */   private static final long serialVersionUID = 458661240069192865L;
/*     */ 
/*     */   
/*     */   public EnumMap(Class<K> paramClass) {
/* 137 */     this.keyType = paramClass;
/* 138 */     this.keyUniverse = getKeyUniverse(paramClass);
/* 139 */     this.vals = new Object[this.keyUniverse.length];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumMap(EnumMap<K, ? extends V> paramEnumMap) {
/* 150 */     this.keyType = paramEnumMap.keyType;
/* 151 */     this.keyUniverse = paramEnumMap.keyUniverse;
/* 152 */     this.vals = (Object[])paramEnumMap.vals.clone();
/* 153 */     this.size = paramEnumMap.size;
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
/*     */   public EnumMap(Map<K, ? extends V> paramMap) {
/* 169 */     if (paramMap instanceof EnumMap) {
/* 170 */       EnumMap enumMap = (EnumMap)paramMap;
/* 171 */       this.keyType = enumMap.keyType;
/* 172 */       this.keyUniverse = enumMap.keyUniverse;
/* 173 */       this.vals = (Object[])enumMap.vals.clone();
/* 174 */       this.size = enumMap.size;
/*     */     } else {
/* 176 */       if (paramMap.isEmpty())
/* 177 */         throw new IllegalArgumentException("Specified map is empty"); 
/* 178 */       this.keyType = ((Enum<K>)paramMap.keySet().iterator().next()).getDeclaringClass();
/* 179 */       this.keyUniverse = getKeyUniverse(this.keyType);
/* 180 */       this.vals = new Object[this.keyUniverse.length];
/* 181 */       putAll(paramMap);
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
/*     */   public int size() {
/* 193 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object paramObject) {
/* 204 */     paramObject = maskNull(paramObject);
/*     */     
/* 206 */     for (Object object : this.vals) {
/* 207 */       if (paramObject.equals(object))
/* 208 */         return true; 
/*     */     } 
/* 210 */     return false;
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
/*     */   public boolean containsKey(Object paramObject) {
/* 222 */     return (isValidKey(paramObject) && this.vals[((Enum)paramObject).ordinal()] != null);
/*     */   }
/*     */   
/*     */   private boolean containsMapping(Object paramObject1, Object paramObject2) {
/* 226 */     return (isValidKey(paramObject1) && 
/* 227 */       maskNull(paramObject2).equals(this.vals[((Enum)paramObject1).ordinal()]));
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
/*     */   public V get(Object paramObject) {
/* 246 */     return isValidKey(paramObject) ? 
/* 247 */       unmaskNull(this.vals[((Enum)paramObject).ordinal()]) : null;
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
/*     */   public V put(K paramK, V paramV) {
/* 267 */     typeCheck(paramK);
/*     */     
/* 269 */     int i = paramK.ordinal();
/* 270 */     Object object = this.vals[i];
/* 271 */     this.vals[i] = maskNull(paramV);
/* 272 */     if (object == null)
/* 273 */       this.size++; 
/* 274 */     return unmaskNull(object);
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
/*     */   public V remove(Object paramObject) {
/* 287 */     if (!isValidKey(paramObject))
/* 288 */       return null; 
/* 289 */     int i = ((Enum)paramObject).ordinal();
/* 290 */     Object object = this.vals[i];
/* 291 */     this.vals[i] = null;
/* 292 */     if (object != null)
/* 293 */       this.size--; 
/* 294 */     return unmaskNull(object);
/*     */   }
/*     */   
/*     */   private boolean removeMapping(Object paramObject1, Object paramObject2) {
/* 298 */     if (!isValidKey(paramObject1))
/* 299 */       return false; 
/* 300 */     int i = ((Enum)paramObject1).ordinal();
/* 301 */     if (maskNull(paramObject2).equals(this.vals[i])) {
/* 302 */       this.vals[i] = null;
/* 303 */       this.size--;
/* 304 */       return true;
/*     */     } 
/* 306 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isValidKey(Object paramObject) {
/* 314 */     if (paramObject == null) {
/* 315 */       return false;
/*     */     }
/*     */     
/* 318 */     Class<?> clazz = paramObject.getClass();
/* 319 */     return (clazz == this.keyType || clazz.getSuperclass() == this.keyType);
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
/*     */   public void putAll(Map<? extends K, ? extends V> paramMap) {
/* 334 */     if (paramMap instanceof EnumMap) {
/* 335 */       EnumMap enumMap = (EnumMap)paramMap;
/* 336 */       if (enumMap.keyType != this.keyType) {
/* 337 */         if (enumMap.isEmpty())
/*     */           return; 
/* 339 */         throw new ClassCastException(enumMap.keyType + " != " + this.keyType);
/*     */       } 
/*     */       
/* 342 */       for (byte b = 0; b < this.keyUniverse.length; b++) {
/* 343 */         Object object = enumMap.vals[b];
/* 344 */         if (object != null) {
/* 345 */           if (this.vals[b] == null)
/* 346 */             this.size++; 
/* 347 */           this.vals[b] = object;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 351 */       super.putAll(paramMap);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 359 */     Arrays.fill(this.vals, (Object)null);
/* 360 */     this.size = 0;
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
/*     */   public Set<K> keySet() {
/* 382 */     Set<K> set = this.keySet;
/* 383 */     if (set == null) {
/* 384 */       set = new KeySet();
/* 385 */       this.keySet = set;
/*     */     } 
/* 387 */     return set;
/*     */   }
/*     */   private class KeySet extends AbstractSet<K> { private KeySet() {}
/*     */     
/*     */     public Iterator<K> iterator() {
/* 392 */       return new EnumMap.KeyIterator();
/*     */     }
/*     */     public int size() {
/* 395 */       return EnumMap.this.size;
/*     */     }
/*     */     public boolean contains(Object param1Object) {
/* 398 */       return EnumMap.this.containsKey(param1Object);
/*     */     }
/*     */     public boolean remove(Object param1Object) {
/* 401 */       int i = EnumMap.this.size;
/* 402 */       EnumMap.this.remove(param1Object);
/* 403 */       return (EnumMap.this.size != i);
/*     */     }
/*     */     public void clear() {
/* 406 */       EnumMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
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
/* 421 */     Collection<V> collection = this.values;
/* 422 */     if (collection == null) {
/* 423 */       collection = new Values();
/* 424 */       this.values = collection;
/*     */     } 
/* 426 */     return collection;
/*     */   }
/*     */   private class Values extends AbstractCollection<V> { private Values() {}
/*     */     
/*     */     public Iterator<V> iterator() {
/* 431 */       return new EnumMap.ValueIterator();
/*     */     }
/*     */     public int size() {
/* 434 */       return EnumMap.this.size;
/*     */     }
/*     */     public boolean contains(Object param1Object) {
/* 437 */       return EnumMap.this.containsValue(param1Object);
/*     */     }
/*     */     public boolean remove(Object param1Object) {
/* 440 */       param1Object = EnumMap.this.maskNull(param1Object);
/*     */       
/* 442 */       for (byte b = 0; b < EnumMap.this.vals.length; b++) {
/* 443 */         if (param1Object.equals(EnumMap.this.vals[b])) {
/* 444 */           EnumMap.this.vals[b] = null;
/* 445 */           EnumMap.this.size--;
/* 446 */           return true;
/*     */         } 
/*     */       } 
/* 449 */       return false;
/*     */     }
/*     */     public void clear() {
/* 452 */       EnumMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/* 466 */     Set<Map.Entry<K, V>> set = this.entrySet;
/* 467 */     if (set != null) {
/* 468 */       return set;
/*     */     }
/* 470 */     return this.entrySet = new EntrySet();
/*     */   }
/*     */   private class EntrySet extends AbstractSet<Map.Entry<K, V>> { private EntrySet() {}
/*     */     
/*     */     public Iterator<Map.Entry<K, V>> iterator() {
/* 475 */       return new EnumMap.EntryIterator();
/*     */     }
/*     */     
/*     */     public boolean contains(Object param1Object) {
/* 479 */       if (!(param1Object instanceof Map.Entry))
/* 480 */         return false; 
/* 481 */       Map.Entry entry = (Map.Entry)param1Object;
/* 482 */       return EnumMap.this.containsMapping(entry.getKey(), entry.getValue());
/*     */     }
/*     */     public boolean remove(Object param1Object) {
/* 485 */       if (!(param1Object instanceof Map.Entry))
/* 486 */         return false; 
/* 487 */       Map.Entry entry = (Map.Entry)param1Object;
/* 488 */       return EnumMap.this.removeMapping(entry.getKey(), entry.getValue());
/*     */     }
/*     */     public int size() {
/* 491 */       return EnumMap.this.size;
/*     */     }
/*     */     public void clear() {
/* 494 */       EnumMap.this.clear();
/*     */     }
/*     */     public Object[] toArray() {
/* 497 */       return fillEntryArray(new Object[EnumMap.this.size]);
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 501 */       int i = size();
/* 502 */       if (param1ArrayOfT.length < i)
/*     */       {
/* 504 */         param1ArrayOfT = (T[])Array.newInstance(param1ArrayOfT.getClass().getComponentType(), i); } 
/* 505 */       if (param1ArrayOfT.length > i)
/* 506 */         param1ArrayOfT[i] = null; 
/* 507 */       return (T[])fillEntryArray((Object[])param1ArrayOfT);
/*     */     }
/*     */     private Object[] fillEntryArray(Object[] param1ArrayOfObject) {
/* 510 */       byte b1 = 0;
/* 511 */       for (byte b2 = 0; b2 < EnumMap.this.vals.length; b2++) {
/* 512 */         if (EnumMap.this.vals[b2] != null)
/* 513 */           param1ArrayOfObject[b1++] = new AbstractMap.SimpleEntry<>(EnumMap.this
/* 514 */               .keyUniverse[b2], EnumMap.this.unmaskNull(EnumMap.this.vals[b2])); 
/* 515 */       }  return param1ArrayOfObject;
/*     */     } }
/*     */ 
/*     */   
/*     */   private abstract class EnumMapIterator<T>
/*     */     implements Iterator<T> {
/* 521 */     int index = 0;
/*     */ 
/*     */     
/* 524 */     int lastReturnedIndex = -1;
/*     */     
/*     */     public boolean hasNext() {
/* 527 */       while (this.index < EnumMap.this.vals.length && EnumMap.this.vals[this.index] == null)
/* 528 */         this.index++; 
/* 529 */       return (this.index != EnumMap.this.vals.length);
/*     */     }
/*     */     
/*     */     public void remove() {
/* 533 */       checkLastReturnedIndex();
/*     */       
/* 535 */       if (EnumMap.this.vals[this.lastReturnedIndex] != null) {
/* 536 */         EnumMap.this.vals[this.lastReturnedIndex] = null;
/* 537 */         EnumMap.this.size--;
/*     */       } 
/* 539 */       this.lastReturnedIndex = -1;
/*     */     }
/*     */     private EnumMapIterator() {}
/*     */     private void checkLastReturnedIndex() {
/* 543 */       if (this.lastReturnedIndex < 0)
/* 544 */         throw new IllegalStateException(); 
/*     */     } }
/*     */   
/*     */   private class KeyIterator extends EnumMapIterator<K> { private KeyIterator() {}
/*     */     
/*     */     public K next() {
/* 550 */       if (!hasNext())
/* 551 */         throw new NoSuchElementException(); 
/* 552 */       this.lastReturnedIndex = this.index++;
/* 553 */       return (K)EnumMap.this.keyUniverse[this.lastReturnedIndex];
/*     */     } }
/*     */   
/*     */   private class ValueIterator extends EnumMapIterator<V> { private ValueIterator() {}
/*     */     
/*     */     public V next() {
/* 559 */       if (!hasNext())
/* 560 */         throw new NoSuchElementException(); 
/* 561 */       this.lastReturnedIndex = this.index++;
/* 562 */       return EnumMap.this.unmaskNull(EnumMap.this.vals[this.lastReturnedIndex]);
/*     */     } }
/*     */   
/*     */   private class EntryIterator extends EnumMapIterator<Map.Entry<K, V>> { private Entry lastReturnedEntry;
/*     */     
/*     */     private EntryIterator() {}
/*     */     
/*     */     public Map.Entry<K, V> next() {
/* 570 */       if (!hasNext())
/* 571 */         throw new NoSuchElementException(); 
/* 572 */       this.lastReturnedEntry = new Entry(this.index++);
/* 573 */       return this.lastReturnedEntry;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 577 */       this
/* 578 */         .lastReturnedIndex = (null == this.lastReturnedEntry) ? -1 : this.lastReturnedEntry.index;
/* 579 */       super.remove();
/* 580 */       this.lastReturnedEntry.index = this.lastReturnedIndex;
/* 581 */       this.lastReturnedEntry = null;
/*     */     }
/*     */     
/*     */     private class Entry implements Map.Entry<K, V> {
/*     */       private int index;
/*     */       
/*     */       private Entry(int param2Int) {
/* 588 */         this.index = param2Int;
/*     */       }
/*     */       
/*     */       public K getKey() {
/* 592 */         checkIndexForEntryUse();
/* 593 */         return (K)EnumMap.this.keyUniverse[this.index];
/*     */       }
/*     */       
/*     */       public V getValue() {
/* 597 */         checkIndexForEntryUse();
/* 598 */         return EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
/*     */       }
/*     */       
/*     */       public V setValue(V param2V) {
/* 602 */         checkIndexForEntryUse();
/* 603 */         Object object = EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
/* 604 */         EnumMap.this.vals[this.index] = EnumMap.this.maskNull(param2V);
/* 605 */         return (V)object;
/*     */       }
/*     */       
/*     */       public boolean equals(Object param2Object) {
/* 609 */         if (this.index < 0) {
/* 610 */           return (param2Object == this);
/*     */         }
/* 612 */         if (!(param2Object instanceof Map.Entry)) {
/* 613 */           return false;
/*     */         }
/* 615 */         Map.Entry entry = (Map.Entry)param2Object;
/* 616 */         Object object1 = EnumMap.this.unmaskNull(EnumMap.this.vals[this.index]);
/* 617 */         Object object2 = entry.getValue();
/* 618 */         return (entry.getKey() == EnumMap.this.keyUniverse[this.index] && (object1 == object2 || (object1 != null && object1
/*     */           
/* 620 */           .equals(object2))));
/*     */       }
/*     */       
/*     */       public int hashCode() {
/* 624 */         if (this.index < 0) {
/* 625 */           return super.hashCode();
/*     */         }
/* 627 */         return EnumMap.this.entryHashCode(this.index);
/*     */       }
/*     */       
/*     */       public String toString() {
/* 631 */         if (this.index < 0) {
/* 632 */           return super.toString();
/*     */         }
/* 634 */         return EnumMap.this.keyUniverse[this.index] + "=" + EnumMap.this
/* 635 */           .unmaskNull(EnumMap.this.vals[this.index]);
/*     */       }
/*     */       
/*     */       private void checkIndexForEntryUse() {
/* 639 */         if (this.index < 0) {
/* 640 */           throw new IllegalStateException("Entry was removed");
/*     */         }
/*     */       }
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
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
/* 657 */     if (this == paramObject)
/* 658 */       return true; 
/* 659 */     if (paramObject instanceof EnumMap)
/* 660 */       return equals((EnumMap<?, ?>)paramObject); 
/* 661 */     if (!(paramObject instanceof Map)) {
/* 662 */       return false;
/*     */     }
/* 664 */     Map map = (Map)paramObject;
/* 665 */     if (this.size != map.size()) {
/* 666 */       return false;
/*     */     }
/* 668 */     for (byte b = 0; b < this.keyUniverse.length; b++) {
/* 669 */       if (null != this.vals[b]) {
/* 670 */         K k = this.keyUniverse[b];
/* 671 */         V v = unmaskNull(this.vals[b]);
/* 672 */         if (null == v) {
/* 673 */           if (null != map.get(k) || !map.containsKey(k)) {
/* 674 */             return false;
/*     */           }
/* 676 */         } else if (!v.equals(map.get(k))) {
/* 677 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 682 */     return true;
/*     */   }
/*     */   
/*     */   private boolean equals(EnumMap<?, ?> paramEnumMap) {
/* 686 */     if (paramEnumMap.keyType != this.keyType) {
/* 687 */       return (this.size == 0 && paramEnumMap.size == 0);
/*     */     }
/*     */     
/* 690 */     for (byte b = 0; b < this.keyUniverse.length; b++) {
/* 691 */       Object object1 = this.vals[b];
/* 692 */       Object object2 = paramEnumMap.vals[b];
/* 693 */       if (object2 != object1 && (object2 == null || 
/* 694 */         !object2.equals(object1)))
/* 695 */         return false; 
/*     */     } 
/* 697 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 705 */     int i = 0;
/*     */     
/* 707 */     for (byte b = 0; b < this.keyUniverse.length; b++) {
/* 708 */       if (null != this.vals[b]) {
/* 709 */         i += entryHashCode(b);
/*     */       }
/*     */     } 
/*     */     
/* 713 */     return i;
/*     */   }
/*     */   
/*     */   private int entryHashCode(int paramInt) {
/* 717 */     return this.keyUniverse[paramInt].hashCode() ^ this.vals[paramInt].hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumMap<K, V> clone() {
/* 728 */     EnumMap<K, V> enumMap = null;
/*     */     try {
/* 730 */       enumMap = (EnumMap)super.clone();
/* 731 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 732 */       throw new AssertionError();
/*     */     } 
/* 734 */     enumMap.vals = (Object[])enumMap.vals.clone();
/* 735 */     enumMap.entrySet = null;
/* 736 */     return enumMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void typeCheck(K paramK) {
/* 743 */     Class<?> clazz = paramK.getClass();
/* 744 */     if (clazz != this.keyType && clazz.getSuperclass() != this.keyType) {
/* 745 */       throw new ClassCastException(clazz + " != " + this.keyType);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <K extends Enum<K>> K[] getKeyUniverse(Class<K> paramClass) {
/* 753 */     return (K[])SharedSecrets.getJavaLangAccess()
/* 754 */       .getEnumConstantsShared(paramClass);
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 772 */     paramObjectOutputStream.defaultWriteObject();
/*     */ 
/*     */     
/* 775 */     paramObjectOutputStream.writeInt(this.size);
/*     */ 
/*     */     
/* 778 */     int i = this.size;
/* 779 */     for (byte b = 0; i > 0; b++) {
/* 780 */       if (null != this.vals[b]) {
/* 781 */         paramObjectOutputStream.writeObject(this.keyUniverse[b]);
/* 782 */         paramObjectOutputStream.writeObject(unmaskNull(this.vals[b]));
/* 783 */         i--;
/*     */       } 
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 797 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 799 */     this.keyUniverse = getKeyUniverse(this.keyType);
/* 800 */     this.vals = new Object[this.keyUniverse.length];
/*     */ 
/*     */     
/* 803 */     int i = paramObjectInputStream.readInt();
/*     */ 
/*     */     
/* 806 */     for (byte b = 0; b < i; b++) {
/* 807 */       Enum enum_ = (Enum)paramObjectInputStream.readObject();
/* 808 */       Object object = paramObjectInputStream.readObject();
/* 809 */       put((K)enum_, (V)object);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/EnumMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */