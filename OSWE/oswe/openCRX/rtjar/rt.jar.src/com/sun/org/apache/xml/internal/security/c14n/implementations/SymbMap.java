/*     */ package com.sun.org.apache.xml.internal.security.c14n.implementations;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SymbMap
/*     */   implements Cloneable
/*     */ {
/* 318 */   int free = 23;
/*     */   NameSpaceSymbEntry[] entries;
/*     */   String[] keys;
/*     */   
/*     */   SymbMap() {
/* 323 */     this.entries = new NameSpaceSymbEntry[this.free];
/* 324 */     this.keys = new String[this.free];
/*     */   }
/*     */   
/*     */   void put(String paramString, NameSpaceSymbEntry paramNameSpaceSymbEntry) {
/* 328 */     int i = index(paramString);
/* 329 */     String str = this.keys[i];
/* 330 */     this.keys[i] = paramString;
/* 331 */     this.entries[i] = paramNameSpaceSymbEntry;
/* 332 */     if ((str == null || !str.equals(paramString)) && --this.free == 0) {
/* 333 */       this.free = this.entries.length;
/* 334 */       int j = this.free << 2;
/* 335 */       rehash(j);
/*     */     } 
/*     */   }
/*     */   
/*     */   List<NameSpaceSymbEntry> entrySet() {
/* 340 */     ArrayList<NameSpaceSymbEntry> arrayList = new ArrayList();
/* 341 */     for (byte b = 0; b < this.entries.length; b++) {
/* 342 */       if (this.entries[b] != null && !"".equals((this.entries[b]).uri)) {
/* 343 */         arrayList.add(this.entries[b]);
/*     */       }
/*     */     } 
/* 346 */     return arrayList;
/*     */   }
/*     */   
/*     */   protected int index(Object paramObject) {
/* 350 */     String[] arrayOfString = this.keys;
/* 351 */     int i = arrayOfString.length;
/*     */     
/* 353 */     int j = (paramObject.hashCode() & Integer.MAX_VALUE) % i;
/* 354 */     String str = arrayOfString[j];
/*     */     
/* 356 */     if (str == null || str.equals(paramObject)) {
/* 357 */       return j;
/*     */     }
/* 359 */     i--;
/*     */     do {
/* 361 */       j = (j == i) ? 0 : ++j;
/* 362 */       str = arrayOfString[j];
/* 363 */     } while (str != null && !str.equals(paramObject));
/* 364 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rehash(int paramInt) {
/* 373 */     int i = this.keys.length;
/* 374 */     String[] arrayOfString = this.keys;
/* 375 */     NameSpaceSymbEntry[] arrayOfNameSpaceSymbEntry = this.entries;
/*     */     
/* 377 */     this.keys = new String[paramInt];
/* 378 */     this.entries = new NameSpaceSymbEntry[paramInt];
/*     */     
/* 380 */     for (int j = i; j-- > 0;) {
/* 381 */       if (arrayOfString[j] != null) {
/* 382 */         String str = arrayOfString[j];
/* 383 */         int k = index(str);
/* 384 */         this.keys[k] = str;
/* 385 */         this.entries[k] = arrayOfNameSpaceSymbEntry[j];
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   NameSpaceSymbEntry get(String paramString) {
/* 391 */     return this.entries[index(paramString)];
/*     */   }
/*     */   
/*     */   protected Object clone() {
/*     */     try {
/* 396 */       SymbMap symbMap = (SymbMap)super.clone();
/* 397 */       symbMap.entries = new NameSpaceSymbEntry[this.entries.length];
/* 398 */       System.arraycopy(this.entries, 0, symbMap.entries, 0, this.entries.length);
/* 399 */       symbMap.keys = new String[this.keys.length];
/* 400 */       System.arraycopy(this.keys, 0, symbMap.keys, 0, this.keys.length);
/*     */       
/* 402 */       return symbMap;
/* 403 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 404 */       cloneNotSupportedException.printStackTrace();
/*     */       
/* 406 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/implementations/SymbMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */