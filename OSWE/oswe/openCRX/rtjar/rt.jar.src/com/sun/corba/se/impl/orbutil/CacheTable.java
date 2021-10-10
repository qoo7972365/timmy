/*     */ package com.sun.corba.se.impl.orbutil;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CacheTable
/*     */ {
/*     */   private boolean noReverseMap;
/*     */   static final int INITIAL_SIZE = 16;
/*     */   static final int MAX_SIZE = 1073741824;
/*     */   int size;
/*     */   int entryCount;
/*     */   private Entry[] map;
/*     */   private Entry[] rmap;
/*     */   private ORB orb;
/*     */   private ORBUtilSystemException wrapper;
/*     */   
/*     */   class Entry
/*     */   {
/*     */     Object key;
/*     */     int val;
/*     */     Entry next;
/*     */     Entry rnext;
/*     */     
/*     */     public Entry(Object param1Object, int param1Int) {
/*  42 */       this.key = param1Object;
/*  43 */       this.val = param1Int;
/*  44 */       this.next = null;
/*  45 */       this.rnext = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CacheTable() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CacheTable(ORB paramORB, boolean paramBoolean) {
/*  63 */     this.orb = paramORB;
/*  64 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.encoding");
/*     */     
/*  66 */     this.noReverseMap = paramBoolean;
/*  67 */     this.size = 16;
/*  68 */     this.entryCount = 0;
/*  69 */     initTables();
/*     */   }
/*     */   private void initTables() {
/*  72 */     this.map = new Entry[this.size];
/*  73 */     this.rmap = this.noReverseMap ? null : new Entry[this.size];
/*     */   }
/*     */   private void grow() {
/*  76 */     if (this.size == 1073741824)
/*     */       return; 
/*  78 */     Entry[] arrayOfEntry = this.map;
/*  79 */     int i = this.size;
/*  80 */     this.size <<= 1;
/*  81 */     initTables();
/*     */     
/*  83 */     for (byte b = 0; b < i; b++) {
/*  84 */       for (Entry entry = arrayOfEntry[b]; entry != null; entry = entry.next) {
/*  85 */         put_table(entry.key, entry.val);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private int moduloTableSize(int paramInt) {
/*  91 */     paramInt += paramInt << 9 ^ 0xFFFFFFFF;
/*  92 */     paramInt ^= paramInt >>> 14;
/*  93 */     paramInt += paramInt << 4;
/*  94 */     paramInt ^= paramInt >>> 10;
/*  95 */     return paramInt & this.size - 1;
/*     */   }
/*     */   private int hash(Object paramObject) {
/*  98 */     return moduloTableSize(System.identityHashCode(paramObject));
/*     */   }
/*     */   private int hash(int paramInt) {
/* 101 */     return moduloTableSize(paramInt);
/*     */   }
/*     */   public final void put(Object paramObject, int paramInt) {
/* 104 */     if (put_table(paramObject, paramInt)) {
/* 105 */       this.entryCount++;
/* 106 */       if (this.entryCount > this.size * 3 / 4)
/* 107 */         grow(); 
/*     */     } 
/*     */   }
/*     */   private boolean put_table(Object paramObject, int paramInt) {
/* 111 */     int i = hash(paramObject); Entry entry;
/* 112 */     for (entry = this.map[i]; entry != null; entry = entry.next) {
/* 113 */       if (entry.key == paramObject) {
/* 114 */         if (entry.val != paramInt) {
/* 115 */           throw this.wrapper.duplicateIndirectionOffset();
/*     */         }
/*     */ 
/*     */         
/* 119 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 124 */     entry = new Entry(paramObject, paramInt);
/* 125 */     entry.next = this.map[i];
/* 126 */     this.map[i] = entry;
/* 127 */     if (!this.noReverseMap) {
/* 128 */       int j = hash(paramInt);
/* 129 */       entry.rnext = this.rmap[j];
/* 130 */       this.rmap[j] = entry;
/*     */     } 
/* 132 */     return true;
/*     */   }
/*     */   public final boolean containsKey(Object paramObject) {
/* 135 */     return (getVal(paramObject) != -1);
/*     */   }
/*     */   public final int getVal(Object paramObject) {
/* 138 */     int i = hash(paramObject);
/* 139 */     for (Entry entry = this.map[i]; entry != null; entry = entry.next) {
/* 140 */       if (entry.key == paramObject)
/* 141 */         return entry.val; 
/*     */     } 
/* 143 */     return -1;
/*     */   }
/*     */   public final boolean containsVal(int paramInt) {
/* 146 */     return (getKey(paramInt) != null);
/*     */   }
/*     */   public final boolean containsOrderedVal(int paramInt) {
/* 149 */     return containsVal(paramInt);
/*     */   }
/*     */   public final Object getKey(int paramInt) {
/* 152 */     int i = hash(paramInt);
/* 153 */     for (Entry entry = this.rmap[i]; entry != null; entry = entry.rnext) {
/* 154 */       if (entry.val == paramInt)
/* 155 */         return entry.key; 
/*     */     } 
/* 157 */     return null;
/*     */   }
/*     */   public void done() {
/* 160 */     this.map = null;
/* 161 */     this.rmap = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/CacheTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */