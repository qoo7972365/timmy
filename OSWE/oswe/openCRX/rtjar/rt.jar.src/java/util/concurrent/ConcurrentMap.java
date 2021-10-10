/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ConcurrentMap<K, V>
/*     */   extends Map<K, V>
/*     */ {
/*     */   default V getOrDefault(Object paramObject, V paramV) {
/*     */     V v;
/*  80 */     return ((v = get(paramObject)) != null) ? v : paramV;
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
/*     */   default void forEach(BiConsumer<? super K, ? super V> paramBiConsumer) {
/* 103 */     Objects.requireNonNull(paramBiConsumer);
/* 104 */     for (Map.Entry<K, V> entry : entrySet()) {
/*     */       Object object1, object2;
/*     */       
/*     */       try {
/* 108 */         object1 = entry.getKey();
/* 109 */         object2 = entry.getValue();
/* 110 */       } catch (IllegalStateException illegalStateException) {
/*     */         continue;
/*     */       } 
/*     */       
/* 114 */       paramBiConsumer.accept((K)object1, (V)object2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void replaceAll(BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 276 */     Objects.requireNonNull(paramBiFunction);
/* 277 */     forEach((paramObject1, paramObject2) -> {
/*     */           while (!replace((K)paramObject1, (V)paramObject2, paramBiFunction.apply(paramObject1, paramObject2))) {
/*     */             if ((paramObject2 = get(paramObject1)) == null) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         });
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
/*     */   default V computeIfAbsent(K paramK, Function<? super K, ? extends V> paramFunction) {
/* 321 */     Objects.requireNonNull(paramFunction); V v1, v2; return ((
/*     */       
/* 323 */       v1 = get(paramK)) == null && (
/* 324 */       v2 = paramFunction.apply(paramK)) != null && (
/* 325 */       v1 = putIfAbsent(paramK, v2)) == null) ? v2 : v1;
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
/*     */   default V computeIfPresent(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/* 364 */     Objects.requireNonNull(paramBiFunction);
/*     */     V v;
/* 366 */     while ((v = get(paramK)) != null) {
/* 367 */       V v1 = paramBiFunction.apply(paramK, v);
/* 368 */       if (v1 != null) {
/* 369 */         if (replace(paramK, v, v1))
/* 370 */           return v1;  continue;
/* 371 */       }  if (remove(paramK, v))
/* 372 */         return null; 
/*     */     } 
/* 374 */     return v;
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
/*     */   default V compute(K paramK, BiFunction<? super K, ? super V, ? extends V> paramBiFunction) {
/*     */     V v2;
/* 418 */     Objects.requireNonNull(paramBiFunction);
/* 419 */     V v1 = get(paramK);
/*     */     while (true) {
/* 421 */       v2 = paramBiFunction.apply(paramK, v1);
/* 422 */       if (v2 == null) {
/*     */         
/* 424 */         if (v1 != null || containsKey(paramK)) {
/*     */           
/* 426 */           if (remove(paramK, v1))
/*     */           {
/* 428 */             return null;
/*     */           }
/*     */ 
/*     */           
/* 432 */           v1 = get(paramK);
/*     */           continue;
/*     */         } 
/* 435 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 439 */       if (v1 != null) {
/*     */         
/* 441 */         if (replace(paramK, v1, v2))
/*     */         {
/* 443 */           return v2;
/*     */         }
/*     */ 
/*     */         
/* 447 */         v1 = get(paramK);
/*     */         continue;
/*     */       } 
/* 450 */       if ((v1 = putIfAbsent(paramK, v2)) == null)
/*     */         break; 
/* 452 */     }  return v2;
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
/*     */   default V merge(K paramK, V paramV, BiFunction<? super V, ? super V, ? extends V> paramBiFunction) {
/* 497 */     Objects.requireNonNull(paramBiFunction);
/* 498 */     Objects.requireNonNull(paramV);
/* 499 */     V v = get(paramK);
/*     */     while (true) {
/* 501 */       while (v != null) {
/* 502 */         V v1 = paramBiFunction.apply(v, paramV);
/* 503 */         if (v1 != null) {
/* 504 */           if (replace(paramK, v, v1))
/* 505 */             return v1; 
/* 506 */         } else if (remove(paramK, v)) {
/* 507 */           return null;
/*     */         } 
/* 509 */         v = get(paramK);
/*     */       } 
/* 511 */       if ((v = putIfAbsent(paramK, paramV)) == null)
/* 512 */         return paramV; 
/*     */     } 
/*     */   }
/*     */   
/*     */   V putIfAbsent(K paramK, V paramV);
/*     */   
/*     */   boolean remove(Object paramObject1, Object paramObject2);
/*     */   
/*     */   boolean replace(K paramK, V paramV1, V paramV2);
/*     */   
/*     */   V replace(K paramK, V paramV);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ConcurrentMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */