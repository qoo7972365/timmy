/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Objects;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ import java.util.function.LongUnaryOperator;
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
/*     */ public abstract class AtomicLongFieldUpdater<T>
/*     */ {
/*     */   @CallerSensitive
/*     */   public static <U> AtomicLongFieldUpdater<U> newUpdater(Class<U> paramClass, String paramString) {
/*  87 */     Class<?> clazz = Reflection.getCallerClass();
/*  88 */     if (AtomicLong.VM_SUPPORTS_LONG_CAS) {
/*  89 */       return new CASUpdater<>(paramClass, paramString, clazz);
/*     */     }
/*  91 */     return new LockedUpdater<>(paramClass, paramString, clazz);
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
/*     */   public long getAndSet(T paramT, long paramLong) {
/*     */     while (true) {
/* 176 */       long l = get(paramT);
/* 177 */       if (compareAndSet(paramT, l, paramLong)) {
/* 178 */         return l;
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
/*     */   public long getAndIncrement(T paramT) {
/*     */     while (true) {
/* 191 */       long l1 = get(paramT);
/* 192 */       long l2 = l1 + 1L;
/* 193 */       if (compareAndSet(paramT, l1, l2)) {
/* 194 */         return l1;
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
/*     */   public long getAndDecrement(T paramT) {
/*     */     while (true) {
/* 207 */       long l1 = get(paramT);
/* 208 */       long l2 = l1 - 1L;
/* 209 */       if (compareAndSet(paramT, l1, l2)) {
/* 210 */         return l1;
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
/*     */   public long getAndAdd(T paramT, long paramLong) {
/*     */     while (true) {
/* 224 */       long l1 = get(paramT);
/* 225 */       long l2 = l1 + paramLong;
/* 226 */       if (compareAndSet(paramT, l1, l2)) {
/* 227 */         return l1;
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
/*     */   public long incrementAndGet(T paramT) {
/*     */     while (true) {
/* 240 */       long l1 = get(paramT);
/* 241 */       long l2 = l1 + 1L;
/* 242 */       if (compareAndSet(paramT, l1, l2)) {
/* 243 */         return l2;
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
/*     */   public long decrementAndGet(T paramT) {
/*     */     while (true) {
/* 256 */       long l1 = get(paramT);
/* 257 */       long l2 = l1 - 1L;
/* 258 */       if (compareAndSet(paramT, l1, l2)) {
/* 259 */         return l2;
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
/*     */   public long addAndGet(T paramT, long paramLong) {
/*     */     while (true) {
/* 273 */       long l1 = get(paramT);
/* 274 */       long l2 = l1 + paramLong;
/* 275 */       if (compareAndSet(paramT, l1, l2)) {
/* 276 */         return l2;
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
/*     */   public final long getAndUpdate(T paramT, LongUnaryOperator paramLongUnaryOperator) {
/*     */     while (true) {
/* 293 */       long l1 = get(paramT);
/* 294 */       long l2 = paramLongUnaryOperator.applyAsLong(l1);
/* 295 */       if (compareAndSet(paramT, l1, l2)) {
/* 296 */         return l1;
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
/*     */   public final long updateAndGet(T paramT, LongUnaryOperator paramLongUnaryOperator) {
/*     */     while (true) {
/* 313 */       long l1 = get(paramT);
/* 314 */       long l2 = paramLongUnaryOperator.applyAsLong(l1);
/* 315 */       if (compareAndSet(paramT, l1, l2)) {
/* 316 */         return l2;
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
/*     */   public final long getAndAccumulate(T paramT, long paramLong, LongBinaryOperator paramLongBinaryOperator) {
/*     */     while (true) {
/* 338 */       long l1 = get(paramT);
/* 339 */       long l2 = paramLongBinaryOperator.applyAsLong(l1, paramLong);
/* 340 */       if (compareAndSet(paramT, l1, l2)) {
/* 341 */         return l1;
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
/*     */   public final long accumulateAndGet(T paramT, long paramLong, LongBinaryOperator paramLongBinaryOperator) {
/*     */     while (true) {
/* 363 */       long l1 = get(paramT);
/* 364 */       long l2 = paramLongBinaryOperator.applyAsLong(l1, paramLong);
/* 365 */       if (compareAndSet(paramT, l1, l2))
/* 366 */         return l2; 
/*     */     } 
/*     */   }
/*     */   
/* 370 */   private static final class CASUpdater<T> extends AtomicLongFieldUpdater<T> { private static final Unsafe U = Unsafe.getUnsafe();
/*     */ 
/*     */     
/*     */     private final long offset;
/*     */ 
/*     */     
/*     */     private final Class<?> cclass;
/*     */     
/*     */     private final Class<T> tclass;
/*     */ 
/*     */     
/*     */     CASUpdater(final Class<T> tclass, final String fieldName, Class<?> param1Class1) {
/*     */       Field field;
/*     */       int i;
/*     */       try {
/* 385 */         field = AccessController.<Field>doPrivileged(new PrivilegedExceptionAction<Field>()
/*     */             {
/*     */               public Field run() throws NoSuchFieldException {
/* 388 */                 return tclass.getDeclaredField(fieldName);
/*     */               }
/*     */             });
/* 391 */         i = field.getModifiers();
/* 392 */         ReflectUtil.ensureMemberAccess(param1Class1, tclass, null, i);
/*     */         
/* 394 */         ClassLoader classLoader1 = tclass.getClassLoader();
/* 395 */         ClassLoader classLoader2 = param1Class1.getClassLoader();
/* 396 */         if (classLoader2 != null && classLoader2 != classLoader1 && (classLoader1 == null || 
/* 397 */           !isAncestor(classLoader1, classLoader2))) {
/* 398 */           ReflectUtil.checkPackageAccess(tclass);
/*     */         }
/* 400 */       } catch (PrivilegedActionException privilegedActionException) {
/* 401 */         throw new RuntimeException(privilegedActionException.getException());
/* 402 */       } catch (Exception exception) {
/* 403 */         throw new RuntimeException(exception);
/*     */       } 
/*     */       
/* 406 */       if (field.getType() != long.class) {
/* 407 */         throw new IllegalArgumentException("Must be long type");
/*     */       }
/* 409 */       if (!Modifier.isVolatile(i)) {
/* 410 */         throw new IllegalArgumentException("Must be volatile type");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 419 */       this
/*     */         
/* 421 */         .cclass = (Modifier.isProtected(i) && tclass.isAssignableFrom(param1Class1) && !AtomicLongFieldUpdater.isSamePackage(tclass, param1Class1)) ? param1Class1 : tclass;
/*     */       
/* 423 */       this.tclass = tclass;
/* 424 */       this.offset = U.objectFieldOffset(field);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final void accessCheck(T param1T) {
/* 432 */       if (!this.cclass.isInstance(param1T)) {
/* 433 */         throwAccessCheckException(param1T);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final void throwAccessCheckException(T param1T) {
/* 441 */       if (this.cclass == this.tclass) {
/* 442 */         throw new ClassCastException();
/*     */       }
/* 444 */       throw new RuntimeException(new IllegalAccessException("Class " + this.cclass
/*     */ 
/*     */             
/* 447 */             .getName() + " can not access a protected member of class " + this.tclass
/*     */             
/* 449 */             .getName() + " using an instance of " + param1T
/*     */             
/* 451 */             .getClass().getName()));
/*     */     }
/*     */     
/*     */     public final boolean compareAndSet(T param1T, long param1Long1, long param1Long2) {
/* 455 */       accessCheck(param1T);
/* 456 */       return U.compareAndSwapLong(param1T, this.offset, param1Long1, param1Long2);
/*     */     }
/*     */     
/*     */     public final boolean weakCompareAndSet(T param1T, long param1Long1, long param1Long2) {
/* 460 */       accessCheck(param1T);
/* 461 */       return U.compareAndSwapLong(param1T, this.offset, param1Long1, param1Long2);
/*     */     }
/*     */     
/*     */     public final void set(T param1T, long param1Long) {
/* 465 */       accessCheck(param1T);
/* 466 */       U.putLongVolatile(param1T, this.offset, param1Long);
/*     */     }
/*     */     
/*     */     public final void lazySet(T param1T, long param1Long) {
/* 470 */       accessCheck(param1T);
/* 471 */       U.putOrderedLong(param1T, this.offset, param1Long);
/*     */     }
/*     */     
/*     */     public final long get(T param1T) {
/* 475 */       accessCheck(param1T);
/* 476 */       return U.getLongVolatile(param1T, this.offset);
/*     */     }
/*     */     
/*     */     public final long getAndSet(T param1T, long param1Long) {
/* 480 */       accessCheck(param1T);
/* 481 */       return U.getAndSetLong(param1T, this.offset, param1Long);
/*     */     }
/*     */     
/*     */     public final long getAndAdd(T param1T, long param1Long) {
/* 485 */       accessCheck(param1T);
/* 486 */       return U.getAndAddLong(param1T, this.offset, param1Long);
/*     */     }
/*     */     
/*     */     public final long getAndIncrement(T param1T) {
/* 490 */       return getAndAdd(param1T, 1L);
/*     */     }
/*     */     
/*     */     public final long getAndDecrement(T param1T) {
/* 494 */       return getAndAdd(param1T, -1L);
/*     */     }
/*     */     
/*     */     public final long incrementAndGet(T param1T) {
/* 498 */       return getAndAdd(param1T, 1L) + 1L;
/*     */     }
/*     */     
/*     */     public final long decrementAndGet(T param1T) {
/* 502 */       return getAndAdd(param1T, -1L) - 1L;
/*     */     }
/*     */     
/*     */     public final long addAndGet(T param1T, long param1Long) {
/* 506 */       return getAndAdd(param1T, param1Long) + param1Long;
/*     */     } }
/*     */ 
/*     */   
/*     */   private static final class LockedUpdater<T> extends AtomicLongFieldUpdater<T> {
/* 511 */     private static final Unsafe U = Unsafe.getUnsafe();
/*     */ 
/*     */     
/*     */     private final long offset;
/*     */ 
/*     */     
/*     */     private final Class<?> cclass;
/*     */     
/*     */     private final Class<T> tclass;
/*     */ 
/*     */     
/*     */     LockedUpdater(final Class<T> tclass, final String fieldName, Class<?> param1Class1) {
/* 523 */       Field field = null;
/* 524 */       int i = 0;
/*     */       try {
/* 526 */         field = AccessController.<Field>doPrivileged(new PrivilegedExceptionAction<Field>()
/*     */             {
/*     */               public Field run() throws NoSuchFieldException {
/* 529 */                 return tclass.getDeclaredField(fieldName);
/*     */               }
/*     */             });
/* 532 */         i = field.getModifiers();
/* 533 */         ReflectUtil.ensureMemberAccess(param1Class1, tclass, null, i);
/*     */         
/* 535 */         ClassLoader classLoader1 = tclass.getClassLoader();
/* 536 */         ClassLoader classLoader2 = param1Class1.getClassLoader();
/* 537 */         if (classLoader2 != null && classLoader2 != classLoader1 && (classLoader1 == null || 
/* 538 */           !isAncestor(classLoader1, classLoader2))) {
/* 539 */           ReflectUtil.checkPackageAccess(tclass);
/*     */         }
/* 541 */       } catch (PrivilegedActionException privilegedActionException) {
/* 542 */         throw new RuntimeException(privilegedActionException.getException());
/* 543 */       } catch (Exception exception) {
/* 544 */         throw new RuntimeException(exception);
/*     */       } 
/*     */       
/* 547 */       if (field.getType() != long.class) {
/* 548 */         throw new IllegalArgumentException("Must be long type");
/*     */       }
/* 550 */       if (!Modifier.isVolatile(i)) {
/* 551 */         throw new IllegalArgumentException("Must be volatile type");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 560 */       this
/*     */         
/* 562 */         .cclass = (Modifier.isProtected(i) && tclass.isAssignableFrom(param1Class1) && !AtomicLongFieldUpdater.isSamePackage(tclass, param1Class1)) ? param1Class1 : tclass;
/*     */       
/* 564 */       this.tclass = tclass;
/* 565 */       this.offset = U.objectFieldOffset(field);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final void accessCheck(T param1T) {
/* 573 */       if (!this.cclass.isInstance(param1T)) {
/* 574 */         throw accessCheckException(param1T);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final RuntimeException accessCheckException(T param1T) {
/* 582 */       if (this.cclass == this.tclass) {
/* 583 */         return new ClassCastException();
/*     */       }
/* 585 */       return new RuntimeException(new IllegalAccessException("Class " + this.cclass
/*     */ 
/*     */             
/* 588 */             .getName() + " can not access a protected member of class " + this.tclass
/*     */             
/* 590 */             .getName() + " using an instance of " + param1T
/*     */             
/* 592 */             .getClass().getName()));
/*     */     }
/*     */     
/*     */     public final boolean compareAndSet(T param1T, long param1Long1, long param1Long2) {
/* 596 */       accessCheck(param1T);
/* 597 */       synchronized (this) {
/* 598 */         long l = U.getLong(param1T, this.offset);
/* 599 */         if (l != param1Long1)
/* 600 */           return false; 
/* 601 */         U.putLong(param1T, this.offset, param1Long2);
/* 602 */         return true;
/*     */       } 
/*     */     }
/*     */     
/*     */     public final boolean weakCompareAndSet(T param1T, long param1Long1, long param1Long2) {
/* 607 */       return compareAndSet(param1T, param1Long1, param1Long2);
/*     */     }
/*     */     
/*     */     public final void set(T param1T, long param1Long) {
/* 611 */       accessCheck(param1T);
/* 612 */       synchronized (this) {
/* 613 */         U.putLong(param1T, this.offset, param1Long);
/*     */       } 
/*     */     }
/*     */     
/*     */     public final void lazySet(T param1T, long param1Long) {
/* 618 */       set(param1T, param1Long);
/*     */     }
/*     */     
/*     */     public final long get(T param1T) {
/* 622 */       accessCheck(param1T);
/* 623 */       synchronized (this) {
/* 624 */         return U.getLong(param1T, this.offset);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isAncestor(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2) {
/* 635 */     ClassLoader classLoader = paramClassLoader1;
/*     */     while (true) {
/* 637 */       classLoader = classLoader.getParent();
/* 638 */       if (paramClassLoader2 == classLoader) {
/* 639 */         return true;
/*     */       }
/* 641 */       if (classLoader == null) {
/* 642 */         return false;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isSamePackage(Class<?> paramClass1, Class<?> paramClass2) {
/* 650 */     return (paramClass1.getClassLoader() == paramClass2.getClassLoader() && 
/* 651 */       Objects.equals(getPackageName(paramClass1), getPackageName(paramClass2)));
/*     */   }
/*     */   
/*     */   private static String getPackageName(Class<?> paramClass) {
/* 655 */     String str = paramClass.getName();
/* 656 */     int i = str.lastIndexOf('.');
/* 657 */     return (i != -1) ? str.substring(0, i) : "";
/*     */   }
/*     */   
/*     */   public abstract boolean compareAndSet(T paramT, long paramLong1, long paramLong2);
/*     */   
/*     */   public abstract boolean weakCompareAndSet(T paramT, long paramLong1, long paramLong2);
/*     */   
/*     */   public abstract void set(T paramT, long paramLong);
/*     */   
/*     */   public abstract void lazySet(T paramT, long paramLong);
/*     */   
/*     */   public abstract long get(T paramT);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/AtomicLongFieldUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */