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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymbolTable
/*     */ {
/*     */   protected static final int TABLE_SIZE = 101;
/*     */   protected static final int MAX_HASH_COLLISIONS = 40;
/*     */   protected static final int MULTIPLIERS_SIZE = 32;
/*     */   protected static final int MULTIPLIERS_MASK = 31;
/*  71 */   protected Entry[] fBuckets = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int fTableSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected transient int fCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int fThreshold;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float fLoadFactor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int fCollisionThreshold;
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
/*     */   public SymbolTable(int initialCapacity, float loadFactor) {
/* 113 */     if (initialCapacity < 0) {
/* 114 */       throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
/*     */     }
/*     */     
/* 117 */     if (loadFactor <= 0.0F || Float.isNaN(loadFactor)) {
/* 118 */       throw new IllegalArgumentException("Illegal Load: " + loadFactor);
/*     */     }
/*     */     
/* 121 */     if (initialCapacity == 0) {
/* 122 */       initialCapacity = 1;
/*     */     }
/*     */     
/* 125 */     this.fLoadFactor = loadFactor;
/* 126 */     this.fTableSize = initialCapacity;
/* 127 */     this.fBuckets = new Entry[this.fTableSize];
/* 128 */     this.fThreshold = (int)(this.fTableSize * loadFactor);
/* 129 */     this.fCollisionThreshold = (int)(40.0F * loadFactor);
/* 130 */     this.fCount = 0;
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
/*     */   public SymbolTable(int initialCapacity) {
/* 142 */     this(initialCapacity, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SymbolTable() {
/* 150 */     this(101, 0.75F);
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
/*     */   public String addSymbol(String symbol) {
/* 168 */     int collisionCount = 0;
/* 169 */     int bucket = hash(symbol) % this.fTableSize;
/* 170 */     for (Entry entry = this.fBuckets[bucket]; entry != null; entry = entry.next) {
/* 171 */       if (entry.symbol.equals(symbol)) {
/* 172 */         return entry.symbol;
/*     */       }
/* 174 */       collisionCount++;
/*     */     } 
/* 176 */     return addSymbol0(symbol, bucket, collisionCount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String addSymbol0(String symbol, int bucket, int collisionCount) {
/* 182 */     if (this.fCount >= this.fThreshold) {
/*     */       
/* 184 */       rehash();
/* 185 */       bucket = hash(symbol) % this.fTableSize;
/*     */     }
/* 187 */     else if (collisionCount >= this.fCollisionThreshold) {
/*     */ 
/*     */       
/* 190 */       rebalance();
/* 191 */       bucket = hash(symbol) % this.fTableSize;
/*     */     } 
/*     */ 
/*     */     
/* 195 */     Entry entry = new Entry(symbol, this.fBuckets[bucket]);
/* 196 */     this.fBuckets[bucket] = entry;
/* 197 */     this.fCount++;
/* 198 */     return entry.symbol;
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
/*     */   public String addSymbol(char[] buffer, int offset, int length) {
/* 215 */     int collisionCount = 0;
/* 216 */     int bucket = hash(buffer, offset, length) % this.fTableSize;
/* 217 */     for (Entry entry = this.fBuckets[bucket]; entry != null; entry = entry.next) {
/* 218 */       if (length == entry.characters.length)
/* 219 */       { int i = 0; while (true) { if (i < length) {
/* 220 */             if (buffer[offset + i] != entry.characters[i]) {
/* 221 */               collisionCount++; break;
/*     */             }  i++;
/*     */             continue;
/*     */           } 
/* 225 */           return entry.symbol; }
/*     */          }
/* 227 */       else { collisionCount++; }
/*     */     
/* 229 */     }  return addSymbol0(buffer, offset, length, bucket, collisionCount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String addSymbol0(char[] buffer, int offset, int length, int bucket, int collisionCount) {
/* 235 */     if (this.fCount >= this.fThreshold) {
/*     */       
/* 237 */       rehash();
/* 238 */       bucket = hash(buffer, offset, length) % this.fTableSize;
/*     */     }
/* 240 */     else if (collisionCount >= this.fCollisionThreshold) {
/*     */ 
/*     */       
/* 243 */       rebalance();
/* 244 */       bucket = hash(buffer, offset, length) % this.fTableSize;
/*     */     } 
/*     */ 
/*     */     
/* 248 */     Entry entry = new Entry(buffer, offset, length, this.fBuckets[bucket]);
/* 249 */     this.fBuckets[bucket] = entry;
/* 250 */     this.fCount++;
/* 251 */     return entry.symbol;
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
/*     */   public int hash(String symbol) {
/* 264 */     if (this.fHashMultipliers == null) {
/* 265 */       return symbol.hashCode() & Integer.MAX_VALUE;
/*     */     }
/* 267 */     return hash0(symbol);
/*     */   }
/*     */   
/*     */   private int hash0(String symbol) {
/* 271 */     int code = 0;
/* 272 */     int length = symbol.length();
/* 273 */     int[] multipliers = this.fHashMultipliers;
/* 274 */     for (int i = 0; i < length; i++) {
/* 275 */       code = code * multipliers[i & 0x1F] + symbol.charAt(i);
/*     */     }
/* 277 */     return code & Integer.MAX_VALUE;
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
/*     */   public int hash(char[] buffer, int offset, int length) {
/* 292 */     if (this.fHashMultipliers == null) {
/* 293 */       int code = 0;
/* 294 */       for (int i = 0; i < length; i++) {
/* 295 */         code = code * 31 + buffer[offset + i];
/*     */       }
/* 297 */       return code & Integer.MAX_VALUE;
/*     */     } 
/* 299 */     return hash0(buffer, offset, length);
/*     */   }
/*     */ 
/*     */   
/*     */   private int hash0(char[] buffer, int offset, int length) {
/* 304 */     int code = 0;
/* 305 */     int[] multipliers = this.fHashMultipliers;
/* 306 */     for (int i = 0; i < length; i++) {
/* 307 */       code = code * multipliers[i & 0x1F] + buffer[offset + i];
/*     */     }
/* 309 */     return code & Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rehash() {
/* 320 */     rehashCommon(this.fBuckets.length * 2 + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rebalance() {
/* 330 */     if (this.fHashMultipliers == null) {
/* 331 */       this.fHashMultipliers = new int[32];
/*     */     }
/* 333 */     PrimeNumberSequenceGenerator.generateSequence(this.fHashMultipliers);
/* 334 */     rehashCommon(this.fBuckets.length);
/*     */   }
/*     */ 
/*     */   
/*     */   private void rehashCommon(int newCapacity) {
/* 339 */     int oldCapacity = this.fBuckets.length;
/* 340 */     Entry[] oldTable = this.fBuckets;
/*     */     
/* 342 */     Entry[] newTable = new Entry[newCapacity];
/*     */     
/* 344 */     this.fThreshold = (int)(newCapacity * this.fLoadFactor);
/* 345 */     this.fBuckets = newTable;
/* 346 */     this.fTableSize = this.fBuckets.length;
/*     */     
/* 348 */     for (int i = oldCapacity; i-- > 0;) {
/* 349 */       for (Entry old = oldTable[i]; old != null; ) {
/* 350 */         Entry e = old;
/* 351 */         old = old.next;
/*     */         
/* 353 */         int index = hash(e.symbol) % newCapacity;
/* 354 */         e.next = newTable[index];
/* 355 */         newTable[index] = e;
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
/*     */   public boolean containsSymbol(String symbol) {
/* 369 */     int bucket = hash(symbol) % this.fTableSize;
/* 370 */     int length = symbol.length();
/* 371 */     for (Entry entry = this.fBuckets[bucket]; entry != null; entry = entry.next) {
/* 372 */       if (length == entry.characters.length) {
/* 373 */         int i = 0; while (true) { if (i < length) {
/* 374 */             if (symbol.charAt(i) != entry.characters[i])
/*     */               break;  i++;
/*     */             continue;
/*     */           } 
/* 378 */           return true; }
/*     */       
/*     */       } 
/*     */     } 
/* 382 */     return false;
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
/*     */   public boolean containsSymbol(char[] buffer, int offset, int length) {
/* 397 */     int bucket = hash(buffer, offset, length) % this.fTableSize;
/* 398 */     for (Entry entry = this.fBuckets[bucket]; entry != null; entry = entry.next) {
/* 399 */       if (length == entry.characters.length) {
/* 400 */         int i = 0; while (true) { if (i < length) {
/* 401 */             if (buffer[offset + i] != entry.characters[i])
/*     */               break;  i++;
/*     */             continue;
/*     */           } 
/* 405 */           return true; }
/*     */       
/*     */       } 
/*     */     } 
/* 409 */     return false;
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
/*     */   protected static final class Entry
/*     */   {
/*     */     public final String symbol;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final char[] characters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Entry next;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Entry(String symbol, Entry next) {
/* 448 */       this.symbol = symbol.intern();
/* 449 */       this.characters = new char[symbol.length()];
/* 450 */       symbol.getChars(0, this.characters.length, this.characters, 0);
/* 451 */       this.next = next;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Entry(char[] ch, int offset, int length, Entry next) {
/* 459 */       this.characters = new char[length];
/* 460 */       System.arraycopy(ch, offset, this.characters, 0, length);
/* 461 */       this.symbol = (new String(this.characters)).intern();
/* 462 */       this.next = next;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/SymbolTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */