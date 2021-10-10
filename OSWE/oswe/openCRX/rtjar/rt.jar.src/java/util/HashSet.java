/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HashSet<E>
/*     */   extends AbstractSet<E>
/*     */   implements Set<E>, Cloneable, Serializable
/*     */ {
/*     */   static final long serialVersionUID = -5024744406713321676L;
/*     */   private transient HashMap<E, Object> map;
/*  99 */   private static final Object PRESENT = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashSet() {
/* 106 */     this.map = new HashMap<>();
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
/*     */   public HashSet(Collection<? extends E> paramCollection) {
/* 119 */     this.map = new HashMap<>(Math.max((int)(paramCollection.size() / 0.75F) + 1, 16));
/* 120 */     addAll(paramCollection);
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
/*     */   public HashSet(int paramInt, float paramFloat) {
/* 133 */     this.map = new HashMap<>(paramInt, paramFloat);
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
/*     */   public HashSet(int paramInt) {
/* 145 */     this.map = new HashMap<>(paramInt);
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
/*     */   HashSet(int paramInt, float paramFloat, boolean paramBoolean) {
/* 162 */     this.map = new LinkedHashMap<>(paramInt, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/* 173 */     return this.map.keySet().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 182 */     return this.map.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 191 */     return this.map.isEmpty();
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
/*     */   public boolean contains(Object paramObject) {
/* 204 */     return this.map.containsKey(paramObject);
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
/*     */   public boolean add(E paramE) {
/* 220 */     return (this.map.put(paramE, PRESENT) == null);
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
/*     */   public boolean remove(Object paramObject) {
/* 236 */     return (this.map.remove(paramObject) == PRESENT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 244 */     this.map.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 256 */       HashSet hashSet = (HashSet)super.clone();
/* 257 */       hashSet.map = (HashMap<E, Object>)this.map.clone();
/* 258 */       return hashSet;
/* 259 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 260 */       throw new InternalError(cloneNotSupportedException);
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 277 */     paramObjectOutputStream.defaultWriteObject();
/*     */ 
/*     */     
/* 280 */     paramObjectOutputStream.writeInt(this.map.capacity());
/* 281 */     paramObjectOutputStream.writeFloat(this.map.loadFactor());
/*     */ 
/*     */     
/* 284 */     paramObjectOutputStream.writeInt(this.map.size());
/*     */ 
/*     */     
/* 287 */     for (E e : this.map.keySet()) {
/* 288 */       paramObjectOutputStream.writeObject(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 298 */     paramObjectInputStream.defaultReadObject();
/*     */ 
/*     */     
/* 301 */     int i = paramObjectInputStream.readInt();
/* 302 */     if (i < 0) {
/* 303 */       throw new InvalidObjectException("Illegal capacity: " + i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 308 */     float f = paramObjectInputStream.readFloat();
/* 309 */     if (f <= 0.0F || Float.isNaN(f)) {
/* 310 */       throw new InvalidObjectException("Illegal load factor: " + f);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 315 */     int j = paramObjectInputStream.readInt();
/* 316 */     if (j < 0) {
/* 317 */       throw new InvalidObjectException("Illegal size: " + j);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 322 */     i = (int)Math.min(j * Math.min(1.0F / f, 4.0F), 1.07374182E9F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     SharedSecrets.getJavaOISAccess()
/* 331 */       .checkArray(paramObjectInputStream, Map.Entry[].class, HashMap.tableSizeFor(i));
/*     */ 
/*     */     
/* 334 */     this.map = (this instanceof LinkedHashSet) ? new LinkedHashMap<>(i, f) : new HashMap<>(i, f);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     for (byte b = 0; b < j; b++) {
/*     */       
/* 341 */       Object object = paramObjectInputStream.readObject();
/* 342 */       this.map.put((E)object, PRESENT);
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
/*     */   public Spliterator<E> spliterator() {
/* 359 */     return new HashMap.KeySpliterator<>(this.map, 0, -1, 0, 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/HashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */