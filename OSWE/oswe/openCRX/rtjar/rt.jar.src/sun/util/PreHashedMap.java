/*     */ package sun.util;
/*     */ 
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
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
/*     */ public abstract class PreHashedMap<V>
/*     */   extends AbstractMap<String, V>
/*     */ {
/*     */   private final int rows;
/*     */   private final int size;
/*     */   private final int shift;
/*     */   private final int mask;
/*     */   private final Object[] ht;
/*     */   
/*     */   protected PreHashedMap(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 110 */     this.rows = paramInt1;
/* 111 */     this.size = paramInt2;
/* 112 */     this.shift = paramInt3;
/* 113 */     this.mask = paramInt4;
/* 114 */     this.ht = new Object[paramInt1];
/* 115 */     init(this.ht);
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
/*     */   private V toV(Object paramObject) {
/* 131 */     return (V)paramObject;
/*     */   }
/*     */   
/*     */   public V get(Object paramObject) {
/* 135 */     int i = paramObject.hashCode() >> this.shift & this.mask;
/* 136 */     Object[] arrayOfObject = (Object[])this.ht[i];
/* 137 */     if (arrayOfObject == null) return null; 
/*     */     while (true) {
/* 139 */       if (arrayOfObject[0].equals(paramObject))
/* 140 */         return toV(arrayOfObject[1]); 
/* 141 */       if (arrayOfObject.length < 3)
/* 142 */         return null; 
/* 143 */       arrayOfObject = (Object[])arrayOfObject[2];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V put(String paramString, V paramV) {
/* 152 */     int i = paramString.hashCode() >> this.shift & this.mask;
/* 153 */     Object[] arrayOfObject = (Object[])this.ht[i];
/* 154 */     if (arrayOfObject == null)
/* 155 */       throw new UnsupportedOperationException(paramString); 
/*     */     while (true) {
/* 157 */       if (arrayOfObject[0].equals(paramString)) {
/* 158 */         V v = toV(arrayOfObject[1]);
/* 159 */         arrayOfObject[1] = paramV;
/* 160 */         return v;
/*     */       } 
/* 162 */       if (arrayOfObject.length < 3)
/* 163 */         throw new UnsupportedOperationException(paramString); 
/* 164 */       arrayOfObject = (Object[])arrayOfObject[2];
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<String> keySet() {
/* 169 */     return new AbstractSet<String>()
/*     */       {
/*     */         public int size() {
/* 172 */           return PreHashedMap.this.size;
/*     */         }
/*     */         
/*     */         public Iterator<String> iterator() {
/* 176 */           return new Iterator<String>() {
/* 177 */               private int i = -1;
/* 178 */               Object[] a = null;
/* 179 */               String cur = null;
/*     */               
/*     */               private boolean findNext() {
/* 182 */                 if (this.a != null) {
/* 183 */                   if (this.a.length == 3) {
/* 184 */                     this.a = (Object[])this.a[2];
/* 185 */                     this.cur = (String)this.a[0];
/* 186 */                     return true;
/*     */                   } 
/* 188 */                   this.i++;
/* 189 */                   this.a = null;
/*     */                 } 
/* 191 */                 this.cur = null;
/* 192 */                 if (this.i >= PreHashedMap.this.rows)
/* 193 */                   return false; 
/* 194 */                 if (this.i < 0 || PreHashedMap.this.ht[this.i] == null) {
/*     */                   do {
/* 196 */                     if (++this.i >= PreHashedMap.this.rows)
/* 197 */                       return false; 
/* 198 */                   } while (PreHashedMap.this.ht[this.i] == null);
/*     */                 }
/* 200 */                 this.a = (Object[])PreHashedMap.this.ht[this.i];
/* 201 */                 this.cur = (String)this.a[0];
/* 202 */                 return true;
/*     */               }
/*     */               
/*     */               public boolean hasNext() {
/* 206 */                 if (this.cur != null)
/* 207 */                   return true; 
/* 208 */                 return findNext();
/*     */               }
/*     */               
/*     */               public String next() {
/* 212 */                 if (this.cur == null && 
/* 213 */                   !findNext()) {
/* 214 */                   throw new NoSuchElementException();
/*     */                 }
/* 216 */                 String str = this.cur;
/* 217 */                 this.cur = null;
/* 218 */                 return str;
/*     */               }
/*     */               
/*     */               public void remove() {
/* 222 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, V>> entrySet() {
/* 231 */     return new AbstractSet<Map.Entry<String, V>>()
/*     */       {
/*     */         public int size() {
/* 234 */           return PreHashedMap.this.size;
/*     */         }
/*     */         
/*     */         public Iterator<Map.Entry<String, V>> iterator() {
/* 238 */           return new Iterator<Map.Entry<String, V>>() {
/* 239 */               final Iterator<String> i = PreHashedMap.this.keySet().iterator();
/*     */               
/*     */               public boolean hasNext() {
/* 242 */                 return this.i.hasNext();
/*     */               }
/*     */               
/*     */               public Map.Entry<String, V> next() {
/* 246 */                 return new Map.Entry<String, V>() {
/* 247 */                     String k = PreHashedMap.null.null.this.i.next();
/* 248 */                     public String getKey() { return this.k; } public V getValue() {
/* 249 */                       return PreHashedMap.this.get(this.k);
/*     */                     } public int hashCode() {
/* 251 */                       Object object = PreHashedMap.this.get(this.k);
/* 252 */                       return this.k.hashCode() + ((object == null) ? 0 : object
/*     */ 
/*     */                         
/* 255 */                         .hashCode());
/*     */                     }
/*     */                     public boolean equals(Object param3Object) {
/* 258 */                       if (param3Object == this)
/* 259 */                         return true; 
/* 260 */                       if (!(param3Object instanceof Map.Entry))
/* 261 */                         return false; 
/* 262 */                       Map.Entry entry = (Map.Entry)param3Object;
/* 263 */                       if ((getKey() == null) ? (entry
/* 264 */                         .getKey() == null) : 
/* 265 */                         getKey()
/* 266 */                         .equals(entry.getKey()))
/*     */                       {
/* 268 */                         if ((getValue() == null) ? (entry
/* 269 */                           .getValue() == null) : 
/* 270 */                           getValue()
/* 271 */                           .equals(entry.getValue())); } 
/*     */                       return false;
/*     */                     } public V setValue(V param3V) {
/* 274 */                       throw new UnsupportedOperationException();
/*     */                     }
/*     */                   };
/*     */               }
/*     */               
/*     */               public void remove() {
/* 280 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected abstract void init(Object[] paramArrayOfObject);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/PreHashedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */