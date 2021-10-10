/*     */ package sun.misc;
/*     */ 
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SoftCache
/*     */   extends AbstractMap
/*     */   implements Map
/*     */ {
/*     */   private Map hash;
/*     */   
/*     */   private static class ValueCell
/*     */     extends SoftReference
/*     */   {
/* 119 */     private static Object INVALID_KEY = new Object();
/* 120 */     private static int dropped = 0;
/*     */     private Object key;
/*     */     
/*     */     private ValueCell(Object param1Object1, Object param1Object2, ReferenceQueue<? super T> param1ReferenceQueue) {
/* 124 */       super((T)param1Object2, param1ReferenceQueue);
/* 125 */       this.key = param1Object1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static ValueCell create(Object param1Object1, Object param1Object2, ReferenceQueue param1ReferenceQueue) {
/* 131 */       if (param1Object2 == null) return null; 
/* 132 */       return new ValueCell(param1Object1, param1Object2, param1ReferenceQueue);
/*     */     }
/*     */     
/*     */     private static Object strip(Object param1Object, boolean param1Boolean) {
/* 136 */       if (param1Object == null) return null; 
/* 137 */       ValueCell valueCell = (ValueCell)param1Object;
/* 138 */       T t = valueCell.get();
/* 139 */       if (param1Boolean) valueCell.drop(); 
/* 140 */       return t;
/*     */     }
/*     */     
/*     */     private boolean isValid() {
/* 144 */       return (this.key != INVALID_KEY);
/*     */     }
/*     */     
/*     */     private void drop() {
/* 148 */       clear();
/* 149 */       this.key = INVALID_KEY;
/* 150 */       dropped++;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   private ReferenceQueue queue = new ReferenceQueue();
/*     */ 
/*     */ 
/*     */   
/*     */   private Set entrySet;
/*     */ 
/*     */ 
/*     */   
/*     */   private void processQueue() {
/*     */     ValueCell valueCell;
/* 170 */     while ((valueCell = (ValueCell)this.queue.poll()) != null) {
/* 171 */       if (valueCell.isValid()) { this.hash.remove(valueCell.key);
/*     */         continue; }
/*     */       
/*     */       ValueCell.dropped--;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 224 */     return entrySet().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 231 */     return entrySet().isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object paramObject) {
/* 242 */     return (ValueCell.strip(this.hash.get(paramObject), false) != null);
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
/*     */   protected Object fill(Object paramObject) {
/* 266 */     return null;
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
/*     */   public Object get(Object paramObject) {
/* 286 */     processQueue();
/* 287 */     Object object = this.hash.get(paramObject);
/* 288 */     if (object == null) {
/* 289 */       object = fill(paramObject);
/* 290 */       if (object != null) {
/* 291 */         this.hash.put(paramObject, ValueCell.create(paramObject, object, this.queue));
/* 292 */         return object;
/*     */       } 
/*     */     } 
/* 295 */     return ValueCell.strip(object, false);
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
/*     */   public Object put(Object paramObject1, Object paramObject2) {
/* 313 */     processQueue();
/* 314 */     ValueCell valueCell = ValueCell.create(paramObject1, paramObject2, this.queue);
/* 315 */     return ValueCell.strip(this.hash.put(paramObject1, valueCell), true);
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
/* 328 */     processQueue();
/* 329 */     return ValueCell.strip(this.hash.remove(paramObject), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 336 */     processQueue();
/* 337 */     this.hash.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean valEquals(Object paramObject1, Object paramObject2) {
/* 344 */     return (paramObject1 == null) ? ((paramObject2 == null)) : paramObject1.equals(paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class Entry
/*     */     implements Map.Entry
/*     */   {
/*     */     private Map.Entry ent;
/*     */     
/*     */     private Object value;
/*     */ 
/*     */     
/*     */     Entry(Map.Entry param1Entry, Object param1Object) {
/* 358 */       this.ent = param1Entry;
/* 359 */       this.value = param1Object;
/*     */     }
/*     */     
/*     */     public Object getKey() {
/* 363 */       return this.ent.getKey();
/*     */     }
/*     */     
/*     */     public Object getValue() {
/* 367 */       return this.value;
/*     */     }
/*     */     
/*     */     public Object setValue(Object param1Object) {
/* 371 */       return this.ent.setValue(SoftCache.ValueCell.create(this.ent.getKey(), param1Object, SoftCache.this.queue));
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 375 */       if (!(param1Object instanceof Map.Entry)) return false; 
/* 376 */       Map.Entry entry = (Map.Entry)param1Object;
/* 377 */       return (SoftCache.valEquals(this.ent.getKey(), entry.getKey()) && SoftCache
/* 378 */         .valEquals(this.value, entry.getValue()));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/*     */       Object object;
/* 383 */       return (((object = getKey()) == null) ? 0 : object.hashCode()) ^ ((this.value == null) ? 0 : this.value
/* 384 */         .hashCode());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class EntrySet
/*     */     extends AbstractSet
/*     */   {
/* 392 */     Set hashEntries = SoftCache.this.hash.entrySet();
/*     */ 
/*     */     
/*     */     public Iterator iterator() {
/* 396 */       return new Iterator() {
/* 397 */           Iterator hashIterator = SoftCache.EntrySet.this.hashEntries.iterator();
/* 398 */           SoftCache.Entry next = null;
/*     */           
/*     */           public boolean hasNext() {
/* 401 */             while (this.hashIterator.hasNext()) {
/* 402 */               Map.Entry entry = this.hashIterator.next();
/* 403 */               SoftCache.ValueCell valueCell = (SoftCache.ValueCell)entry.getValue();
/* 404 */               T t = null;
/* 405 */               if (valueCell != null && (t = valueCell.get()) == null) {
/*     */                 continue;
/*     */               }
/*     */               
/* 409 */               this.next = new SoftCache.Entry(entry, t);
/* 410 */               return true;
/*     */             } 
/* 412 */             return false;
/*     */           }
/*     */           
/*     */           public Object next() {
/* 416 */             if (this.next == null && !hasNext())
/* 417 */               throw new NoSuchElementException(); 
/* 418 */             SoftCache.Entry entry = this.next;
/* 419 */             this.next = null;
/* 420 */             return entry;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 424 */             this.hashIterator.remove();
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 431 */       return !iterator().hasNext();
/*     */     }
/*     */     
/*     */     public int size() {
/* 435 */       byte b = 0;
/* 436 */       for (Iterator iterator = iterator(); iterator.hasNext(); ) { b++; iterator.next(); }
/* 437 */        return b;
/*     */     }
/*     */     
/*     */     public boolean remove(Object param1Object) {
/* 441 */       SoftCache.this.processQueue();
/* 442 */       if (param1Object instanceof SoftCache.Entry) return this.hashEntries.remove(((SoftCache.Entry)param1Object).ent); 
/* 443 */       return false;
/*     */     }
/*     */     
/*     */     private EntrySet() {}
/*     */   }
/*     */   
/* 449 */   public SoftCache(int paramInt, float paramFloat) { this.entrySet = null; this.hash = new HashMap<>(paramInt, paramFloat); } public SoftCache(int paramInt) { this.entrySet = null; this.hash = new HashMap<>(paramInt); } public SoftCache() { this.entrySet = null;
/*     */     this.hash = new HashMap<>(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set entrySet() {
/* 455 */     if (this.entrySet == null) this.entrySet = new EntrySet(); 
/* 456 */     return this.entrySet;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/SoftCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */