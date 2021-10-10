/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.UnaryOperator;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AtomicReferenceFieldUpdater<T, V>
/*     */ {
/*     */   @CallerSensitive
/*     */   public static <U, W> AtomicReferenceFieldUpdater<U, W> newUpdater(Class<U> paramClass, Class<W> paramClass1, String paramString) {
/* 109 */     return new AtomicReferenceFieldUpdaterImpl<>(paramClass, paramClass1, paramString, 
/* 110 */         Reflection.getCallerClass());
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
/*     */   public abstract boolean compareAndSet(T paramT, V paramV1, V paramV2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean weakCompareAndSet(T paramT, V paramV1, V paramV2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void set(T paramT, V paramV);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void lazySet(T paramT, V paramV);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract V get(T paramT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V getAndSet(T paramT, V paramV) {
/*     */     while (true) {
/* 191 */       V v = get(paramT);
/* 192 */       if (compareAndSet(paramT, v, paramV)) {
/* 193 */         return v;
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
/*     */   public final V getAndUpdate(T paramT, UnaryOperator<V> paramUnaryOperator) {
/*     */     while (true) {
/* 210 */       V v1 = get(paramT);
/* 211 */       V v2 = paramUnaryOperator.apply(v1);
/* 212 */       if (compareAndSet(paramT, v1, v2)) {
/* 213 */         return v1;
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
/*     */   public final V updateAndGet(T paramT, UnaryOperator<V> paramUnaryOperator) {
/*     */     while (true) {
/* 230 */       V v1 = get(paramT);
/* 231 */       V v2 = paramUnaryOperator.apply(v1);
/* 232 */       if (compareAndSet(paramT, v1, v2)) {
/* 233 */         return v2;
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
/*     */   public final V getAndAccumulate(T paramT, V paramV, BinaryOperator<V> paramBinaryOperator) {
/*     */     while (true) {
/* 255 */       V v1 = get(paramT);
/* 256 */       V v2 = paramBinaryOperator.apply(v1, paramV);
/* 257 */       if (compareAndSet(paramT, v1, v2)) {
/* 258 */         return v1;
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
/*     */   public final V accumulateAndGet(T paramT, V paramV, BinaryOperator<V> paramBinaryOperator) {
/*     */     while (true) {
/* 280 */       V v1 = get(paramT);
/* 281 */       V v2 = paramBinaryOperator.apply(v1, paramV);
/* 282 */       if (compareAndSet(paramT, v1, v2))
/* 283 */         return v2; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class AtomicReferenceFieldUpdaterImpl<T, V> extends AtomicReferenceFieldUpdater<T, V> {
/* 288 */     private static final Unsafe U = Unsafe.getUnsafe();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final long offset;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Class<?> cclass;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Class<T> tclass;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Class<V> vclass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     AtomicReferenceFieldUpdaterImpl(final Class<T> tclass, Class<V> param1Class1, final String fieldName, Class<?> param1Class2) {
/*     */       Field field;
/*     */       Class<?> clazz;
/*     */       int i;
/*     */       try {
/* 320 */         field = AccessController.<Field>doPrivileged(new PrivilegedExceptionAction<Field>()
/*     */             {
/*     */               public Field run() throws NoSuchFieldException {
/* 323 */                 return tclass.getDeclaredField(fieldName);
/*     */               }
/*     */             });
/* 326 */         i = field.getModifiers();
/* 327 */         ReflectUtil.ensureMemberAccess(param1Class2, tclass, null, i);
/*     */         
/* 329 */         ClassLoader classLoader1 = tclass.getClassLoader();
/* 330 */         ClassLoader classLoader2 = param1Class2.getClassLoader();
/* 331 */         if (classLoader2 != null && classLoader2 != classLoader1 && (classLoader1 == null || 
/* 332 */           !isAncestor(classLoader1, classLoader2))) {
/* 333 */           ReflectUtil.checkPackageAccess(tclass);
/*     */         }
/* 335 */         clazz = field.getType();
/* 336 */       } catch (PrivilegedActionException privilegedActionException) {
/* 337 */         throw new RuntimeException(privilegedActionException.getException());
/* 338 */       } catch (Exception exception) {
/* 339 */         throw new RuntimeException(exception);
/*     */       } 
/*     */       
/* 342 */       if (param1Class1 != clazz)
/* 343 */         throw new ClassCastException(); 
/* 344 */       if (param1Class1.isPrimitive()) {
/* 345 */         throw new IllegalArgumentException("Must be reference type");
/*     */       }
/* 347 */       if (!Modifier.isVolatile(i)) {
/* 348 */         throw new IllegalArgumentException("Must be volatile type");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 357 */       this
/*     */         
/* 359 */         .cclass = (Modifier.isProtected(i) && tclass.isAssignableFrom(param1Class2) && !isSamePackage(tclass, param1Class2)) ? param1Class2 : tclass;
/*     */       
/* 361 */       this.tclass = tclass;
/* 362 */       this.vclass = param1Class1;
/* 363 */       this.offset = U.objectFieldOffset(field);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isAncestor(ClassLoader param1ClassLoader1, ClassLoader param1ClassLoader2) {
/* 372 */       ClassLoader classLoader = param1ClassLoader1;
/*     */       while (true) {
/* 374 */         classLoader = classLoader.getParent();
/* 375 */         if (param1ClassLoader2 == classLoader) {
/* 376 */           return true;
/*     */         }
/* 378 */         if (classLoader == null) {
/* 379 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isSamePackage(Class<?> param1Class1, Class<?> param1Class2) {
/* 387 */       return (param1Class1.getClassLoader() == param1Class2.getClassLoader() && 
/* 388 */         Objects.equals(getPackageName(param1Class1), getPackageName(param1Class2)));
/*     */     }
/*     */     
/*     */     private static String getPackageName(Class<?> param1Class) {
/* 392 */       String str = param1Class.getName();
/* 393 */       int i = str.lastIndexOf('.');
/* 394 */       return (i != -1) ? str.substring(0, i) : "";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final void accessCheck(T param1T) {
/* 402 */       if (!this.cclass.isInstance(param1T)) {
/* 403 */         throwAccessCheckException(param1T);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final void throwAccessCheckException(T param1T) {
/* 411 */       if (this.cclass == this.tclass) {
/* 412 */         throw new ClassCastException();
/*     */       }
/* 414 */       throw new RuntimeException(new IllegalAccessException("Class " + this.cclass
/*     */ 
/*     */             
/* 417 */             .getName() + " can not access a protected member of class " + this.tclass
/*     */             
/* 419 */             .getName() + " using an instance of " + param1T
/*     */             
/* 421 */             .getClass().getName()));
/*     */     }
/*     */     
/*     */     private final void valueCheck(V param1V) {
/* 425 */       if (param1V != null && !this.vclass.isInstance(param1V))
/* 426 */         throwCCE(); 
/*     */     }
/*     */     
/*     */     static void throwCCE() {
/* 430 */       throw new ClassCastException();
/*     */     }
/*     */     
/*     */     public final boolean compareAndSet(T param1T, V param1V1, V param1V2) {
/* 434 */       accessCheck(param1T);
/* 435 */       valueCheck(param1V2);
/* 436 */       return U.compareAndSwapObject(param1T, this.offset, param1V1, param1V2);
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean weakCompareAndSet(T param1T, V param1V1, V param1V2) {
/* 441 */       accessCheck(param1T);
/* 442 */       valueCheck(param1V2);
/* 443 */       return U.compareAndSwapObject(param1T, this.offset, param1V1, param1V2);
/*     */     }
/*     */     
/*     */     public final void set(T param1T, V param1V) {
/* 447 */       accessCheck(param1T);
/* 448 */       valueCheck(param1V);
/* 449 */       U.putObjectVolatile(param1T, this.offset, param1V);
/*     */     }
/*     */     
/*     */     public final void lazySet(T param1T, V param1V) {
/* 453 */       accessCheck(param1T);
/* 454 */       valueCheck(param1V);
/* 455 */       U.putOrderedObject(param1T, this.offset, param1V);
/*     */     }
/*     */ 
/*     */     
/*     */     public final V get(T param1T) {
/* 460 */       accessCheck(param1T);
/* 461 */       return (V)U.getObjectVolatile(param1T, this.offset);
/*     */     }
/*     */ 
/*     */     
/*     */     public final V getAndSet(T param1T, V param1V) {
/* 466 */       accessCheck(param1T);
/* 467 */       valueCheck(param1V);
/* 468 */       return (V)U.getAndSetObject(param1T, this.offset, param1V);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/AtomicReferenceFieldUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */