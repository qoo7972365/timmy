/*     */ package com.sun.corba.se.impl.util;
/*     */ 
/*     */ import java.util.Dictionary;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IdentityHashtable
/*     */   extends Dictionary
/*     */ {
/*     */   private transient IdentityHashtableEntry[] table;
/*     */   private transient int count;
/*     */   private int threshold;
/*     */   private float loadFactor;
/*     */   
/*     */   public IdentityHashtable(int paramInt, float paramFloat) {
/*  78 */     if (paramInt <= 0 || paramFloat <= 0.0D) {
/*  79 */       throw new IllegalArgumentException();
/*     */     }
/*  81 */     this.loadFactor = paramFloat;
/*  82 */     this.table = new IdentityHashtableEntry[paramInt];
/*  83 */     this.threshold = (int)(paramInt * paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IdentityHashtable(int paramInt) {
/*  94 */     this(paramInt, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IdentityHashtable() {
/* 104 */     this(101, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 114 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 125 */     return (this.count == 0);
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
/*     */   public Enumeration keys() {
/* 137 */     return new IdentityHashtableEnumerator(this.table, true);
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
/*     */   public Enumeration elements() {
/* 151 */     return new IdentityHashtableEnumerator(this.table, false);
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
/*     */   public boolean contains(Object paramObject) {
/* 168 */     if (paramObject == null) {
/* 169 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 172 */     IdentityHashtableEntry[] arrayOfIdentityHashtableEntry = this.table;
/* 173 */     for (int i = arrayOfIdentityHashtableEntry.length; i-- > 0;) {
/* 174 */       for (IdentityHashtableEntry identityHashtableEntry = arrayOfIdentityHashtableEntry[i]; identityHashtableEntry != null; identityHashtableEntry = identityHashtableEntry.next) {
/* 175 */         if (identityHashtableEntry.value == paramObject) {
/* 176 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 180 */     return false;
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
/*     */   public boolean containsKey(Object paramObject) {
/* 193 */     IdentityHashtableEntry[] arrayOfIdentityHashtableEntry = this.table;
/* 194 */     int i = System.identityHashCode(paramObject);
/* 195 */     int j = (i & Integer.MAX_VALUE) % arrayOfIdentityHashtableEntry.length;
/* 196 */     for (IdentityHashtableEntry identityHashtableEntry = arrayOfIdentityHashtableEntry[j]; identityHashtableEntry != null; identityHashtableEntry = identityHashtableEntry.next) {
/* 197 */       if (identityHashtableEntry.hash == i && identityHashtableEntry.key == paramObject) {
/* 198 */         return true;
/*     */       }
/*     */     } 
/* 201 */     return false;
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
/*     */   public Object get(Object paramObject) {
/* 215 */     IdentityHashtableEntry[] arrayOfIdentityHashtableEntry = this.table;
/* 216 */     int i = System.identityHashCode(paramObject);
/* 217 */     int j = (i & Integer.MAX_VALUE) % arrayOfIdentityHashtableEntry.length;
/* 218 */     for (IdentityHashtableEntry identityHashtableEntry = arrayOfIdentityHashtableEntry[j]; identityHashtableEntry != null; identityHashtableEntry = identityHashtableEntry.next) {
/* 219 */       if (identityHashtableEntry.hash == i && identityHashtableEntry.key == paramObject) {
/* 220 */         return identityHashtableEntry.value;
/*     */       }
/*     */     } 
/* 223 */     return null;
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
/*     */   protected void rehash() {
/* 235 */     int i = this.table.length;
/* 236 */     IdentityHashtableEntry[] arrayOfIdentityHashtableEntry1 = this.table;
/*     */     
/* 238 */     int j = i * 2 + 1;
/* 239 */     IdentityHashtableEntry[] arrayOfIdentityHashtableEntry2 = new IdentityHashtableEntry[j];
/*     */     
/* 241 */     this.threshold = (int)(j * this.loadFactor);
/* 242 */     this.table = arrayOfIdentityHashtableEntry2;
/*     */ 
/*     */ 
/*     */     
/* 246 */     for (int k = i; k-- > 0;) {
/* 247 */       for (IdentityHashtableEntry identityHashtableEntry = arrayOfIdentityHashtableEntry1[k]; identityHashtableEntry != null; ) {
/* 248 */         IdentityHashtableEntry identityHashtableEntry1 = identityHashtableEntry;
/* 249 */         identityHashtableEntry = identityHashtableEntry.next;
/*     */         
/* 251 */         int m = (identityHashtableEntry1.hash & Integer.MAX_VALUE) % j;
/* 252 */         identityHashtableEntry1.next = arrayOfIdentityHashtableEntry2[m];
/* 253 */         arrayOfIdentityHashtableEntry2[m] = identityHashtableEntry1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object put(Object paramObject1, Object paramObject2) {
/* 277 */     if (paramObject2 == null) {
/* 278 */       throw new NullPointerException();
/*     */     }
/*     */ 
/*     */     
/* 282 */     IdentityHashtableEntry[] arrayOfIdentityHashtableEntry = this.table;
/* 283 */     int i = System.identityHashCode(paramObject1);
/* 284 */     int j = (i & Integer.MAX_VALUE) % arrayOfIdentityHashtableEntry.length; IdentityHashtableEntry identityHashtableEntry;
/* 285 */     for (identityHashtableEntry = arrayOfIdentityHashtableEntry[j]; identityHashtableEntry != null; identityHashtableEntry = identityHashtableEntry.next) {
/* 286 */       if (identityHashtableEntry.hash == i && identityHashtableEntry.key == paramObject1) {
/* 287 */         Object object = identityHashtableEntry.value;
/* 288 */         identityHashtableEntry.value = paramObject2;
/* 289 */         return object;
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     if (this.count >= this.threshold) {
/*     */       
/* 295 */       rehash();
/* 296 */       return put(paramObject1, paramObject2);
/*     */     } 
/*     */ 
/*     */     
/* 300 */     identityHashtableEntry = new IdentityHashtableEntry();
/* 301 */     identityHashtableEntry.hash = i;
/* 302 */     identityHashtableEntry.key = paramObject1;
/* 303 */     identityHashtableEntry.value = paramObject2;
/* 304 */     identityHashtableEntry.next = arrayOfIdentityHashtableEntry[j];
/* 305 */     arrayOfIdentityHashtableEntry[j] = identityHashtableEntry;
/* 306 */     this.count++;
/* 307 */     return null;
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
/*     */   public Object remove(Object paramObject) {
/* 320 */     IdentityHashtableEntry[] arrayOfIdentityHashtableEntry = this.table;
/* 321 */     int i = System.identityHashCode(paramObject);
/* 322 */     int j = (i & Integer.MAX_VALUE) % arrayOfIdentityHashtableEntry.length;
/* 323 */     for (IdentityHashtableEntry identityHashtableEntry1 = arrayOfIdentityHashtableEntry[j], identityHashtableEntry2 = null; identityHashtableEntry1 != null; identityHashtableEntry2 = identityHashtableEntry1, identityHashtableEntry1 = identityHashtableEntry1.next) {
/* 324 */       if (identityHashtableEntry1.hash == i && identityHashtableEntry1.key == paramObject) {
/* 325 */         if (identityHashtableEntry2 != null) {
/* 326 */           identityHashtableEntry2.next = identityHashtableEntry1.next;
/*     */         } else {
/* 328 */           arrayOfIdentityHashtableEntry[j] = identityHashtableEntry1.next;
/*     */         } 
/* 330 */         this.count--;
/* 331 */         return identityHashtableEntry1.value;
/*     */       } 
/*     */     } 
/* 334 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 343 */     IdentityHashtableEntry[] arrayOfIdentityHashtableEntry = this.table;
/* 344 */     for (int i = arrayOfIdentityHashtableEntry.length; --i >= 0;)
/* 345 */       arrayOfIdentityHashtableEntry[i] = null; 
/* 346 */     this.count = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 356 */     int i = size() - 1;
/* 357 */     StringBuffer stringBuffer = new StringBuffer();
/* 358 */     Enumeration<E> enumeration1 = keys();
/* 359 */     Enumeration<E> enumeration2 = elements();
/* 360 */     stringBuffer.append("{");
/*     */     
/* 362 */     for (byte b = 0; b <= i; b++) {
/* 363 */       String str1 = enumeration1.nextElement().toString();
/* 364 */       String str2 = enumeration2.nextElement().toString();
/* 365 */       stringBuffer.append(str1 + "=" + str2);
/* 366 */       if (b < i) {
/* 367 */         stringBuffer.append(", ");
/*     */       }
/*     */     } 
/* 370 */     stringBuffer.append("}");
/* 371 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/util/IdentityHashtable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */