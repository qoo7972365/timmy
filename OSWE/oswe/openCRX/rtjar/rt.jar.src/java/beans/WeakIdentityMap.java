/*     */ package java.beans;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class WeakIdentityMap<T>
/*     */ {
/*     */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*  47 */   private static final Object NULL = new Object();
/*     */   
/*  49 */   private final ReferenceQueue<Object> queue = new ReferenceQueue();
/*     */   
/*  51 */   private volatile Entry<T>[] table = newTable(8);
/*  52 */   private int threshold = 6;
/*  53 */   private int size = 0;
/*     */   
/*     */   public T get(Object paramObject) {
/*  56 */     removeStaleEntries();
/*  57 */     if (paramObject == null) {
/*  58 */       paramObject = NULL;
/*     */     }
/*  60 */     int i = paramObject.hashCode();
/*  61 */     Entry<T>[] arrayOfEntry = this.table;
/*     */ 
/*     */     
/*  64 */     int j = getIndex((Entry<?>[])arrayOfEntry, i);
/*  65 */     for (Entry<T> entry = arrayOfEntry[j]; entry != null; entry = entry.next) {
/*  66 */       if (entry.isMatched(paramObject, i)) {
/*  67 */         return entry.value;
/*     */       }
/*     */     } 
/*  70 */     synchronized (NULL) {
/*     */ 
/*     */       
/*  73 */       j = getIndex((Entry<?>[])this.table, i); Entry<T> entry1;
/*  74 */       for (entry1 = this.table[j]; entry1 != null; entry1 = entry1.next) {
/*  75 */         if (entry1.isMatched(paramObject, i)) {
/*  76 */           return entry1.value;
/*     */         }
/*     */       } 
/*  79 */       entry1 = (Entry<T>)create(paramObject);
/*  80 */       this.table[j] = new Entry<>(paramObject, i, (T)entry1, this.queue, this.table[j]);
/*  81 */       if (++this.size >= this.threshold) {
/*  82 */         if (this.table.length == 1073741824) {
/*  83 */           this.threshold = Integer.MAX_VALUE;
/*     */         } else {
/*     */           
/*  86 */           removeStaleEntries();
/*  87 */           Entry[] arrayOfEntry1 = (Entry[])newTable(this.table.length * 2);
/*  88 */           transfer(this.table, (Entry<T>[])arrayOfEntry1);
/*     */ 
/*     */ 
/*     */           
/*  92 */           if (this.size >= this.threshold / 2) {
/*  93 */             this.table = (Entry<T>[])arrayOfEntry1;
/*  94 */             this.threshold *= 2;
/*     */           } else {
/*     */             
/*  97 */             transfer((Entry<T>[])arrayOfEntry1, this.table);
/*     */           } 
/*     */         } 
/*     */       }
/* 101 */       return (T)entry1;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract T create(Object paramObject);
/*     */   
/*     */   private void removeStaleEntries() {
/* 108 */     Reference<?> reference = this.queue.poll();
/* 109 */     if (reference != null)
/* 110 */       synchronized (NULL) {
/*     */         
/*     */         while (true) {
/* 113 */           Entry<T> entry1 = (Entry)reference;
/* 114 */           int i = getIndex((Entry<?>[])this.table, entry1.hash);
/*     */           
/* 116 */           Entry<T> entry2 = this.table[i];
/* 117 */           Entry<T> entry3 = entry2;
/* 118 */           while (entry3 != null) {
/* 119 */             Entry<T> entry = entry3.next;
/* 120 */             if (entry3 == entry1) {
/* 121 */               if (entry2 == entry1) {
/* 122 */                 this.table[i] = entry;
/*     */               } else {
/*     */                 
/* 125 */                 entry2.next = entry;
/*     */               } 
/* 127 */               entry1.value = null;
/* 128 */               entry1.next = null;
/* 129 */               this.size--;
/*     */               break;
/*     */             } 
/* 132 */             entry2 = entry3;
/* 133 */             entry3 = entry;
/*     */           } 
/* 135 */           reference = this.queue.poll();
/*     */           
/* 137 */           if (reference == null)
/*     */             break; 
/*     */         } 
/*     */       }  
/*     */   }
/*     */   private void transfer(Entry<T>[] paramArrayOfEntry1, Entry<T>[] paramArrayOfEntry2) {
/* 143 */     for (byte b = 0; b < paramArrayOfEntry1.length; b++) {
/* 144 */       Entry<T> entry = paramArrayOfEntry1[b];
/* 145 */       paramArrayOfEntry1[b] = null;
/* 146 */       while (entry != null) {
/* 147 */         Entry<T> entry1 = entry.next;
/* 148 */         Object object = entry.get();
/* 149 */         if (object == null) {
/* 150 */           entry.value = null;
/* 151 */           entry.next = null;
/* 152 */           this.size--;
/*     */         } else {
/*     */           
/* 155 */           int i = getIndex((Entry<?>[])paramArrayOfEntry2, entry.hash);
/* 156 */           entry.next = paramArrayOfEntry2[i];
/* 157 */           paramArrayOfEntry2[i] = entry;
/*     */         } 
/* 159 */         entry = entry1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Entry<T>[] newTable(int paramInt) {
/* 167 */     return (Entry<T>[])new Entry[paramInt];
/*     */   }
/*     */   
/*     */   private static int getIndex(Entry<?>[] paramArrayOfEntry, int paramInt) {
/* 171 */     return paramInt & paramArrayOfEntry.length - 1;
/*     */   }
/*     */   
/*     */   private static class Entry<T> extends WeakReference<Object> {
/*     */     private final int hash;
/*     */     private volatile T value;
/*     */     private volatile Entry<T> next;
/*     */     
/*     */     Entry(Object param1Object, int param1Int, T param1T, ReferenceQueue<Object> param1ReferenceQueue, Entry<T> param1Entry) {
/* 180 */       super(param1Object, param1ReferenceQueue);
/* 181 */       this.hash = param1Int;
/* 182 */       this.value = param1T;
/* 183 */       this.next = param1Entry;
/*     */     }
/*     */     
/*     */     boolean isMatched(Object param1Object, int param1Int) {
/* 187 */       return (this.hash == param1Int && param1Object == get());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/WeakIdentityMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */