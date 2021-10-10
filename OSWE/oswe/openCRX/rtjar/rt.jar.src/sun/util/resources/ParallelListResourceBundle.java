/*     */ package sun.util.resources;
/*     */ 
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicMarkableReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ParallelListResourceBundle
/*     */   extends ResourceBundle
/*     */ {
/*     */   private volatile ConcurrentMap<String, Object> lookup;
/*     */   private volatile Set<String> keyset;
/*  51 */   private final AtomicMarkableReference<Object[][]> parallelContents = new AtomicMarkableReference(null, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object[][] getContents();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ResourceBundle getParent() {
/*  78 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParallelContents(OpenListResourceBundle paramOpenListResourceBundle) {
/*  89 */     if (paramOpenListResourceBundle == null) {
/*  90 */       this.parallelContents.compareAndSet(null, null, false, true);
/*     */     } else {
/*  92 */       this.parallelContents.compareAndSet(null, paramOpenListResourceBundle.getContents(), false, false);
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
/*     */   boolean areParallelContentsComplete() {
/* 104 */     if (this.parallelContents.isMarked()) {
/* 105 */       return true;
/*     */     }
/* 107 */     boolean[] arrayOfBoolean = new boolean[1];
/* 108 */     Object[][] arrayOfObject = this.parallelContents.get(arrayOfBoolean);
/* 109 */     return (arrayOfObject != null || arrayOfBoolean[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object handleGetObject(String paramString) {
/* 114 */     if (paramString == null) {
/* 115 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 118 */     loadLookupTablesIfNecessary();
/* 119 */     return this.lookup.get(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Enumeration<String> getKeys() {
/* 124 */     return Collections.enumeration(keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(String paramString) {
/* 129 */     return keySet().contains(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<String> handleKeySet() {
/* 134 */     loadLookupTablesIfNecessary();
/* 135 */     return this.lookup.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/*     */     Set<String> set;
/* 142 */     while ((set = this.keyset) == null) {
/* 143 */       set = new KeySet(handleKeySet(), this.parent);
/* 144 */       synchronized (this) {
/* 145 */         if (this.keyset == null) {
/* 146 */           this.keyset = set;
/*     */         }
/*     */       } 
/*     */     } 
/* 150 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void resetKeySet() {
/* 158 */     this.keyset = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void loadLookupTablesIfNecessary() {
/* 165 */     ConcurrentMap<String, Object> concurrentMap = this.lookup;
/* 166 */     if (concurrentMap == null) {
/* 167 */       concurrentMap = new ConcurrentHashMap<>();
/* 168 */       for (Object[] arrayOfObject1 : getContents()) {
/* 169 */         concurrentMap.put((String)arrayOfObject1[0], arrayOfObject1[1]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 174 */     Object[][] arrayOfObject = this.parallelContents.getReference();
/* 175 */     if (arrayOfObject != null) {
/* 176 */       for (Object[] arrayOfObject1 : arrayOfObject) {
/* 177 */         concurrentMap.putIfAbsent((String)arrayOfObject1[0], arrayOfObject1[1]);
/*     */       }
/* 179 */       this.parallelContents.set(null, true);
/*     */     } 
/* 181 */     if (this.lookup == null) {
/* 182 */       synchronized (this) {
/* 183 */         if (this.lookup == null) {
/* 184 */           this.lookup = concurrentMap;
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class KeySet
/*     */     extends AbstractSet<String>
/*     */   {
/*     */     private final Set<String> set;
/*     */     
/*     */     private final ResourceBundle parent;
/*     */     
/*     */     private KeySet(Set<String> param1Set, ResourceBundle param1ResourceBundle) {
/* 199 */       this.set = param1Set;
/* 200 */       this.parent = param1ResourceBundle;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object param1Object) {
/* 205 */       if (this.set.contains(param1Object)) {
/* 206 */         return true;
/*     */       }
/* 208 */       return (this.parent != null) ? this.parent.containsKey((String)param1Object) : false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<String> iterator() {
/* 213 */       if (this.parent == null) {
/* 214 */         return this.set.iterator();
/*     */       }
/* 216 */       return new Iterator<String>() {
/* 217 */           private Iterator<String> itr = ParallelListResourceBundle.KeySet.this.set.iterator();
/*     */           
/*     */           private boolean usingParent;
/*     */           
/*     */           public boolean hasNext() {
/* 222 */             if (this.itr.hasNext()) {
/* 223 */               return true;
/*     */             }
/* 225 */             if (!this.usingParent) {
/* 226 */               HashSet<String> hashSet = new HashSet<>(ParallelListResourceBundle.KeySet.this.parent.keySet());
/* 227 */               hashSet.removeAll(ParallelListResourceBundle.KeySet.this.set);
/* 228 */               this.itr = hashSet.iterator();
/* 229 */               this.usingParent = true;
/*     */             } 
/* 231 */             return this.itr.hasNext();
/*     */           }
/*     */ 
/*     */           
/*     */           public String next() {
/* 236 */             if (hasNext()) {
/* 237 */               return this.itr.next();
/*     */             }
/* 239 */             throw new NoSuchElementException();
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 244 */             throw new UnsupportedOperationException();
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 251 */       if (this.parent == null) {
/* 252 */         return this.set.size();
/*     */       }
/* 254 */       HashSet<String> hashSet = new HashSet<>(this.set);
/* 255 */       hashSet.addAll(this.parent.keySet());
/* 256 */       return hashSet.size();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/resources/ParallelListResourceBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */