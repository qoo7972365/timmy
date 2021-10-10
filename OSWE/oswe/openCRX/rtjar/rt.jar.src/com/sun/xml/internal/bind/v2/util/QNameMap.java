/*     */ package com.sun.xml.internal.bind.v2.util;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.runtime.Name;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class QNameMap<V>
/*     */ {
/*     */   private static final int DEFAULT_INITIAL_CAPACITY = 16;
/*     */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*  64 */   transient Entry<V>[] table = (Entry<V>[])new Entry[16];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   transient int size;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int threshold;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private Set<Entry<V>> entrySet = null;
/*     */   
/*     */   public QNameMap() {
/*  91 */     this.threshold = 12;
/*  92 */     this.table = (Entry<V>[])new Entry[16];
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
/*     */   public void put(String namespaceUri, String localname, V value) {
/* 108 */     assert localname != null;
/* 109 */     assert namespaceUri != null;
/*     */     
/* 111 */     assert localname == localname.intern();
/* 112 */     assert namespaceUri == namespaceUri.intern();
/*     */     
/* 114 */     int hash = hash(localname);
/* 115 */     int i = indexFor(hash, this.table.length);
/*     */     
/* 117 */     for (Entry<V> e = this.table[i]; e != null; e = e.next) {
/* 118 */       if (e.hash == hash && localname == e.localName && namespaceUri == e.nsUri) {
/* 119 */         e.value = value;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 124 */     addEntry(hash, namespaceUri, localname, value, i);
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(QName name, V value) {
/* 129 */     put(name.getNamespaceURI(), name.getLocalPart(), value);
/*     */   }
/*     */   
/*     */   public void put(Name name, V value) {
/* 133 */     put(name.nsUri, name.localName, value);
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
/*     */   public V get(String nsUri, String localPart) {
/* 147 */     Entry<V> e = getEntry(nsUri, localPart);
/* 148 */     if (e == null) return null; 
/* 149 */     return e.value;
/*     */   }
/*     */   
/*     */   public V get(QName name) {
/* 153 */     return get(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 162 */     return this.size;
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
/*     */   public QNameMap<V> putAll(QNameMap<? extends V> map) {
/* 174 */     int numKeysToBeAdded = map.size();
/* 175 */     if (numKeysToBeAdded == 0) {
/* 176 */       return this;
/*     */     }
/*     */     
/* 179 */     if (numKeysToBeAdded > this.threshold) {
/* 180 */       int targetCapacity = numKeysToBeAdded;
/* 181 */       if (targetCapacity > 1073741824)
/* 182 */         targetCapacity = 1073741824; 
/* 183 */       int newCapacity = this.table.length;
/* 184 */       while (newCapacity < targetCapacity)
/* 185 */         newCapacity <<= 1; 
/* 186 */       if (newCapacity > this.table.length) {
/* 187 */         resize(newCapacity);
/*     */       }
/*     */     } 
/* 190 */     for (Entry<? extends V> e : map.entrySet())
/* 191 */       put(e.nsUri, e.localName, e.getValue()); 
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hash(String x) {
/* 201 */     int h = x.hashCode();
/*     */     
/* 203 */     h += h << 9 ^ 0xFFFFFFFF;
/* 204 */     h ^= h >>> 14;
/* 205 */     h += h << 4;
/* 206 */     h ^= h >>> 10;
/* 207 */     return h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexFor(int h, int length) {
/* 214 */     return h & length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEntry(int hash, String nsUri, String localName, V value, int bucketIndex) {
/* 224 */     Entry<V> e = this.table[bucketIndex];
/* 225 */     this.table[bucketIndex] = new Entry<>(hash, nsUri, localName, value, e);
/* 226 */     if (this.size++ >= this.threshold) {
/* 227 */       resize(2 * this.table.length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resize(int newCapacity) {
/* 237 */     Entry<V>[] arrayOfEntry = this.table;
/* 238 */     int oldCapacity = arrayOfEntry.length;
/* 239 */     if (oldCapacity == 1073741824) {
/* 240 */       this.threshold = Integer.MAX_VALUE;
/*     */       
/*     */       return;
/*     */     } 
/* 244 */     Entry[] newTable = new Entry[newCapacity];
/* 245 */     transfer((Entry<V>[])newTable);
/* 246 */     this.table = (Entry<V>[])newTable;
/* 247 */     this.threshold = newCapacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transfer(Entry<V>[] newTable) {
/* 254 */     Entry<V>[] src = this.table;
/* 255 */     int newCapacity = newTable.length;
/* 256 */     for (int j = 0; j < src.length; j++) {
/* 257 */       Entry<V> e = src[j];
/* 258 */       if (e != null) {
/* 259 */         src[j] = null;
/*     */         do {
/* 261 */           Entry<V> next = e.next;
/* 262 */           int i = indexFor(e.hash, newCapacity);
/* 263 */           e.next = newTable[i];
/* 264 */           newTable[i] = e;
/* 265 */           e = next;
/* 266 */         } while (e != null);
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
/*     */   public Entry<V> getOne() {
/* 279 */     for (Entry<V> e : this.table) {
/* 280 */       if (e != null)
/* 281 */         return e; 
/*     */     } 
/* 283 */     return null;
/*     */   }
/*     */   
/*     */   public Collection<QName> keySet() {
/* 287 */     Set<QName> r = new HashSet<>();
/* 288 */     for (Entry<V> e : entrySet()) {
/* 289 */       r.add(e.createQName());
/*     */     }
/* 291 */     return r;
/*     */   }
/*     */   
/*     */   private abstract class HashIterator<E> implements Iterator<E> {
/*     */     QNameMap.Entry<V> next;
/*     */     int index;
/*     */     
/*     */     HashIterator() {
/* 299 */       QNameMap.Entry[] arrayOfEntry = QNameMap.this.table;
/* 300 */       int i = arrayOfEntry.length;
/* 301 */       QNameMap.Entry<V> n = null;
/* 302 */       if (QNameMap.this.size != 0) {
/* 303 */         while (i > 0 && (n = arrayOfEntry[--i]) == null);
/*     */       }
/* 305 */       this.next = n;
/* 306 */       this.index = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 310 */       return (this.next != null);
/*     */     }
/*     */     
/*     */     QNameMap.Entry<V> nextEntry() {
/* 314 */       QNameMap.Entry<V> e = this.next;
/* 315 */       if (e == null) {
/* 316 */         throw new NoSuchElementException();
/*     */       }
/* 318 */       QNameMap.Entry<V> n = e.next;
/* 319 */       QNameMap.Entry[] arrayOfEntry = QNameMap.this.table;
/* 320 */       int i = this.index;
/* 321 */       while (n == null && i > 0)
/* 322 */         n = arrayOfEntry[--i]; 
/* 323 */       this.index = i;
/* 324 */       this.next = n;
/* 325 */       return e;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 329 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean containsKey(String nsUri, String localName) {
/* 334 */     return (getEntry(nsUri, localName) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 342 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Entry<V>
/*     */   {
/*     */     public final String nsUri;
/*     */     
/*     */     public final String localName;
/*     */     
/*     */     V value;
/*     */     
/*     */     final int hash;
/*     */     
/*     */     Entry<V> next;
/*     */ 
/*     */     
/*     */     Entry(int h, String nsUri, String localName, V v, Entry<V> n) {
/* 361 */       this.value = v;
/* 362 */       this.next = n;
/* 363 */       this.nsUri = nsUri;
/* 364 */       this.localName = localName;
/* 365 */       this.hash = h;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public QName createQName() {
/* 372 */       return new QName(this.nsUri, this.localName);
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 376 */       return this.value;
/*     */     }
/*     */     
/*     */     public V setValue(V newValue) {
/* 380 */       V oldValue = this.value;
/* 381 */       this.value = newValue;
/* 382 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 387 */       if (!(o instanceof Entry))
/* 388 */         return false; 
/* 389 */       Entry e = (Entry)o;
/* 390 */       String k1 = this.nsUri;
/* 391 */       String k2 = e.nsUri;
/* 392 */       String k3 = this.localName;
/* 393 */       String k4 = e.localName;
/* 394 */       if (k1 == k2 || (k1 != null && k1.equals(k2) && (k3 == k4 || (k3 != null && k3
/* 395 */         .equals(k4))))) {
/* 396 */         Object v1 = getValue();
/* 397 */         Object v2 = e.getValue();
/* 398 */         if (v1 == v2 || (v1 != null && v1.equals(v2)))
/* 399 */           return true; 
/*     */       } 
/* 401 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 406 */       return this.localName.hashCode() ^ ((this.value == null) ? 0 : this.value
/* 407 */         .hashCode());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 412 */       return '"' + this.nsUri + "\",\"" + this.localName + "\"=" + getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<Entry<V>> entrySet() {
/* 417 */     Set<Entry<V>> es = this.entrySet;
/* 418 */     return (es != null) ? es : (this.entrySet = new EntrySet());
/*     */   }
/*     */   
/*     */   private Iterator<Entry<V>> newEntryIterator() {
/* 422 */     return new EntryIterator();
/*     */   }
/*     */   private class EntryIterator extends HashIterator<Entry<V>> { private EntryIterator() {}
/*     */     
/*     */     public QNameMap.Entry<V> next() {
/* 427 */       return nextEntry();
/*     */     } }
/*     */   
/*     */   private class EntrySet extends AbstractSet<Entry<V>> {
/*     */     public Iterator<QNameMap.Entry<V>> iterator() {
/* 432 */       return QNameMap.this.newEntryIterator();
/*     */     }
/*     */     private EntrySet() {}
/*     */     public boolean contains(Object o) {
/* 436 */       if (!(o instanceof QNameMap.Entry))
/* 437 */         return false; 
/* 438 */       QNameMap.Entry<V> e = (QNameMap.Entry<V>)o;
/* 439 */       QNameMap.Entry<V> candidate = QNameMap.this.getEntry(e.nsUri, e.localName);
/* 440 */       return (candidate != null && candidate.equals(e));
/*     */     }
/*     */     
/*     */     public boolean remove(Object o) {
/* 444 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     public int size() {
/* 447 */       return QNameMap.this.size;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Entry<V> getEntry(String nsUri, String localName) {
/* 453 */     assert nsUri == nsUri.intern();
/* 454 */     assert localName == localName.intern();
/*     */     
/* 456 */     int hash = hash(localName);
/* 457 */     int i = indexFor(hash, this.table.length);
/* 458 */     Entry<V> e = this.table[i];
/* 459 */     while (e != null && (localName != e.localName || nsUri != e.nsUri))
/* 460 */       e = e.next; 
/* 461 */     return e;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 466 */     StringBuilder buf = new StringBuilder();
/* 467 */     buf.append('{');
/*     */     
/* 469 */     for (Entry<V> e : entrySet()) {
/* 470 */       if (buf.length() > 1)
/* 471 */         buf.append(','); 
/* 472 */       buf.append('[');
/* 473 */       buf.append(e);
/* 474 */       buf.append(']');
/*     */     } 
/*     */     
/* 477 */     buf.append('}');
/* 478 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/util/QNameMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */