/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymbolHash
/*     */ {
/*     */   protected static final int TABLE_SIZE = 101;
/*     */   protected static final int MAX_HASH_COLLISIONS = 40;
/*     */   protected static final int MULTIPLIERS_SIZE = 32;
/*     */   protected static final int MULTIPLIERS_MASK = 31;
/*     */   protected int fTableSize;
/*     */   protected Entry[] fBuckets;
/*  59 */   protected int fNum = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int[] fHashMultipliers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SymbolHash() {
/*  73 */     this(101);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SymbolHash(int size) {
/*  82 */     this.fTableSize = size;
/*  83 */     this.fBuckets = new Entry[this.fTableSize];
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
/*     */   public void put(Object key, Object value) {
/* 101 */     int collisionCount = 0;
/* 102 */     int hash = hash(key);
/* 103 */     int bucket = hash % this.fTableSize; Entry entry;
/* 104 */     for (entry = this.fBuckets[bucket]; entry != null; entry = entry.next) {
/* 105 */       if (key.equals(entry.key)) {
/*     */         
/* 107 */         entry.value = value;
/*     */         return;
/*     */       } 
/* 110 */       collisionCount++;
/*     */     } 
/*     */     
/* 113 */     if (this.fNum >= this.fTableSize) {
/*     */ 
/*     */       
/* 116 */       rehash();
/* 117 */       bucket = hash % this.fTableSize;
/*     */     }
/* 119 */     else if (collisionCount >= 40 && key instanceof String) {
/*     */ 
/*     */       
/* 122 */       rebalance();
/* 123 */       bucket = hash(key) % this.fTableSize;
/*     */     } 
/*     */ 
/*     */     
/* 127 */     entry = new Entry(key, value, this.fBuckets[bucket]);
/* 128 */     this.fBuckets[bucket] = entry;
/* 129 */     this.fNum++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object key) {
/* 139 */     int bucket = hash(key) % this.fTableSize;
/* 140 */     Entry entry = search(key, bucket);
/* 141 */     if (entry != null) {
/* 142 */       return entry.value;
/*     */     }
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 153 */     return this.fNum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValues(Object[] elements, int from) {
/* 164 */     for (int i = 0, j = 0; i < this.fTableSize && j < this.fNum; i++) {
/* 165 */       for (Entry entry = this.fBuckets[i]; entry != null; entry = entry.next) {
/* 166 */         elements[from + j] = entry.value;
/* 167 */         j++;
/*     */       } 
/*     */     } 
/* 170 */     return this.fNum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getEntries() {
/* 177 */     Object[] entries = new Object[this.fNum << 1];
/* 178 */     for (int i = 0, j = 0; i < this.fTableSize && j < this.fNum << 1; i++) {
/* 179 */       for (Entry entry = this.fBuckets[i]; entry != null; entry = entry.next) {
/* 180 */         entries[j] = entry.key;
/* 181 */         entries[++j] = entry.value;
/* 182 */         j++;
/*     */       } 
/*     */     } 
/* 185 */     return entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SymbolHash makeClone() {
/* 192 */     SymbolHash newTable = new SymbolHash(this.fTableSize);
/* 193 */     newTable.fNum = this.fNum;
/* 194 */     newTable.fHashMultipliers = (this.fHashMultipliers != null) ? (int[])this.fHashMultipliers.clone() : null;
/* 195 */     for (int i = 0; i < this.fTableSize; i++) {
/* 196 */       if (this.fBuckets[i] != null) {
/* 197 */         newTable.fBuckets[i] = this.fBuckets[i].makeClone();
/*     */       }
/*     */     } 
/* 200 */     return newTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 208 */     for (int i = 0; i < this.fTableSize; i++) {
/* 209 */       this.fBuckets[i] = null;
/*     */     }
/* 211 */     this.fNum = 0;
/* 212 */     this.fHashMultipliers = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entry search(Object key, int bucket) {
/* 217 */     for (Entry entry = this.fBuckets[bucket]; entry != null; entry = entry.next) {
/* 218 */       if (key.equals(entry.key))
/* 219 */         return entry; 
/*     */     } 
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int hash(Object key) {
/* 230 */     if (this.fHashMultipliers == null || !(key instanceof String)) {
/* 231 */       return key.hashCode() & Integer.MAX_VALUE;
/*     */     }
/* 233 */     return hash0((String)key);
/*     */   }
/*     */   
/*     */   private int hash0(String symbol) {
/* 237 */     int code = 0;
/* 238 */     int length = symbol.length();
/* 239 */     int[] multipliers = this.fHashMultipliers;
/* 240 */     for (int i = 0; i < length; i++) {
/* 241 */       code = code * multipliers[i & 0x1F] + symbol.charAt(i);
/*     */     }
/* 243 */     return code & Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rehash() {
/* 253 */     rehashCommon((this.fBuckets.length << 1) + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rebalance() {
/* 263 */     if (this.fHashMultipliers == null) {
/* 264 */       this.fHashMultipliers = new int[32];
/*     */     }
/* 266 */     PrimeNumberSequenceGenerator.generateSequence(this.fHashMultipliers);
/* 267 */     rehashCommon(this.fBuckets.length);
/*     */   }
/*     */ 
/*     */   
/*     */   private void rehashCommon(int newCapacity) {
/* 272 */     int oldCapacity = this.fBuckets.length;
/* 273 */     Entry[] oldTable = this.fBuckets;
/*     */     
/* 275 */     Entry[] newTable = new Entry[newCapacity];
/*     */     
/* 277 */     this.fBuckets = newTable;
/* 278 */     this.fTableSize = this.fBuckets.length;
/*     */     
/* 280 */     for (int i = oldCapacity; i-- > 0;) {
/* 281 */       for (Entry old = oldTable[i]; old != null; ) {
/* 282 */         Entry e = old;
/* 283 */         old = old.next;
/*     */         
/* 285 */         int index = hash(e.key) % newCapacity;
/* 286 */         e.next = newTable[index];
/* 287 */         newTable[index] = e;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final class Entry
/*     */   {
/*     */     public Object key;
/*     */ 
/*     */     
/*     */     public Object value;
/*     */ 
/*     */     
/*     */     public Entry next;
/*     */ 
/*     */ 
/*     */     
/*     */     public Entry() {
/* 308 */       this.key = null;
/* 309 */       this.value = null;
/* 310 */       this.next = null;
/*     */     }
/*     */     
/*     */     public Entry(Object key, Object value, Entry next) {
/* 314 */       this.key = key;
/* 315 */       this.value = value;
/* 316 */       this.next = next;
/*     */     }
/*     */     
/*     */     public Entry makeClone() {
/* 320 */       Entry entry = new Entry();
/* 321 */       entry.key = this.key;
/* 322 */       entry.value = this.value;
/* 323 */       if (this.next != null)
/* 324 */         entry.next = this.next.makeClone(); 
/* 325 */       return entry;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/SymbolHash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */