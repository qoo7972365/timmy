/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Objects;
/*     */ import java.util.function.IntBinaryOperator;
/*     */ import java.util.function.IntUnaryOperator;
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
/*     */ public abstract class AtomicIntegerFieldUpdater<T>
/*     */ {
/*     */   @CallerSensitive
/*     */   public static <U> AtomicIntegerFieldUpdater<U> newUpdater(Class<U> paramClass, String paramString) {
/*  87 */     return new AtomicIntegerFieldUpdaterImpl<>(paramClass, paramString, 
/*  88 */         Reflection.getCallerClass());
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
/*     */   public abstract boolean compareAndSet(T paramT, int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean weakCompareAndSet(T paramT, int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void set(T paramT, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void lazySet(T paramT, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int get(T paramT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAndSet(T paramT, int paramInt) {
/*     */     while (true) {
/* 173 */       int i = get(paramT);
/* 174 */       if (compareAndSet(paramT, i, paramInt)) {
/* 175 */         return i;
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
/*     */   public int getAndIncrement(T paramT) {
/*     */     while (true) {
/* 188 */       int i = get(paramT);
/* 189 */       int j = i + 1;
/* 190 */       if (compareAndSet(paramT, i, j)) {
/* 191 */         return i;
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
/*     */   public int getAndDecrement(T paramT) {
/*     */     while (true) {
/* 204 */       int i = get(paramT);
/* 205 */       int j = i - 1;
/* 206 */       if (compareAndSet(paramT, i, j)) {
/* 207 */         return i;
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
/*     */   public int getAndAdd(T paramT, int paramInt) {
/*     */     while (true) {
/* 221 */       int i = get(paramT);
/* 222 */       int j = i + paramInt;
/* 223 */       if (compareAndSet(paramT, i, j)) {
/* 224 */         return i;
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
/*     */   public int incrementAndGet(T paramT) {
/*     */     while (true) {
/* 237 */       int i = get(paramT);
/* 238 */       int j = i + 1;
/* 239 */       if (compareAndSet(paramT, i, j)) {
/* 240 */         return j;
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
/*     */   public int decrementAndGet(T paramT) {
/*     */     while (true) {
/* 253 */       int i = get(paramT);
/* 254 */       int j = i - 1;
/* 255 */       if (compareAndSet(paramT, i, j)) {
/* 256 */         return j;
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
/*     */   public int addAndGet(T paramT, int paramInt) {
/*     */     while (true) {
/* 270 */       int i = get(paramT);
/* 271 */       int j = i + paramInt;
/* 272 */       if (compareAndSet(paramT, i, j)) {
/* 273 */         return j;
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
/*     */   public final int getAndUpdate(T paramT, IntUnaryOperator paramIntUnaryOperator) {
/*     */     while (true) {
/* 290 */       int i = get(paramT);
/* 291 */       int j = paramIntUnaryOperator.applyAsInt(i);
/* 292 */       if (compareAndSet(paramT, i, j)) {
/* 293 */         return i;
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
/*     */   public final int updateAndGet(T paramT, IntUnaryOperator paramIntUnaryOperator) {
/*     */     while (true) {
/* 310 */       int i = get(paramT);
/* 311 */       int j = paramIntUnaryOperator.applyAsInt(i);
/* 312 */       if (compareAndSet(paramT, i, j)) {
/* 313 */         return j;
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
/*     */   public final int getAndAccumulate(T paramT, int paramInt, IntBinaryOperator paramIntBinaryOperator) {
/*     */     while (true) {
/* 335 */       int i = get(paramT);
/* 336 */       int j = paramIntBinaryOperator.applyAsInt(i, paramInt);
/* 337 */       if (compareAndSet(paramT, i, j)) {
/* 338 */         return i;
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
/*     */   public final int accumulateAndGet(T paramT, int paramInt, IntBinaryOperator paramIntBinaryOperator) {
/*     */     while (true) {
/* 360 */       int i = get(paramT);
/* 361 */       int j = paramIntBinaryOperator.applyAsInt(i, paramInt);
/* 362 */       if (compareAndSet(paramT, i, j)) {
/* 363 */         return j;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class AtomicIntegerFieldUpdaterImpl<T>
/*     */     extends AtomicIntegerFieldUpdater<T>
/*     */   {
/* 371 */     private static final Unsafe U = Unsafe.getUnsafe();
/*     */ 
/*     */     
/*     */     private final long offset;
/*     */ 
/*     */     
/*     */     private final Class<?> cclass;
/*     */ 
/*     */     
/*     */     private final Class<T> tclass;
/*     */ 
/*     */     
/*     */     AtomicIntegerFieldUpdaterImpl(final Class<T> tclass, final String fieldName, Class<?> param1Class1) {
/*     */       Field field;
/*     */       int i;
/*     */       try {
/* 387 */         field = AccessController.<Field>doPrivileged(new PrivilegedExceptionAction<Field>()
/*     */             {
/*     */               public Field run() throws NoSuchFieldException {
/* 390 */                 return tclass.getDeclaredField(fieldName);
/*     */               }
/*     */             });
/* 393 */         i = field.getModifiers();
/* 394 */         ReflectUtil.ensureMemberAccess(param1Class1, tclass, null, i);
/*     */         
/* 396 */         ClassLoader classLoader1 = tclass.getClassLoader();
/* 397 */         ClassLoader classLoader2 = param1Class1.getClassLoader();
/* 398 */         if (classLoader2 != null && classLoader2 != classLoader1 && (classLoader1 == null || 
/* 399 */           !isAncestor(classLoader1, classLoader2))) {
/* 400 */           ReflectUtil.checkPackageAccess(tclass);
/*     */         }
/* 402 */       } catch (PrivilegedActionException privilegedActionException) {
/* 403 */         throw new RuntimeException(privilegedActionException.getException());
/* 404 */       } catch (Exception exception) {
/* 405 */         throw new RuntimeException(exception);
/*     */       } 
/*     */       
/* 408 */       if (field.getType() != int.class) {
/* 409 */         throw new IllegalArgumentException("Must be integer type");
/*     */       }
/* 411 */       if (!Modifier.isVolatile(i)) {
/* 412 */         throw new IllegalArgumentException("Must be volatile type");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 421 */       this
/*     */         
/* 423 */         .cclass = (Modifier.isProtected(i) && tclass.isAssignableFrom(param1Class1) && !isSamePackage(tclass, param1Class1)) ? param1Class1 : tclass;
/*     */       
/* 425 */       this.tclass = tclass;
/* 426 */       this.offset = U.objectFieldOffset(field);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isAncestor(ClassLoader param1ClassLoader1, ClassLoader param1ClassLoader2) {
/* 435 */       ClassLoader classLoader = param1ClassLoader1;
/*     */       while (true) {
/* 437 */         classLoader = classLoader.getParent();
/* 438 */         if (param1ClassLoader2 == classLoader) {
/* 439 */           return true;
/*     */         }
/* 441 */         if (classLoader == null) {
/* 442 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isSamePackage(Class<?> param1Class1, Class<?> param1Class2) {
/* 450 */       return (param1Class1.getClassLoader() == param1Class2.getClassLoader() && 
/* 451 */         Objects.equals(getPackageName(param1Class1), getPackageName(param1Class2)));
/*     */     }
/*     */     
/*     */     private static String getPackageName(Class<?> param1Class) {
/* 455 */       String str = param1Class.getName();
/* 456 */       int i = str.lastIndexOf('.');
/* 457 */       return (i != -1) ? str.substring(0, i) : "";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final void accessCheck(T param1T) {
/* 465 */       if (!this.cclass.isInstance(param1T)) {
/* 466 */         throwAccessCheckException(param1T);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final void throwAccessCheckException(T param1T) {
/* 474 */       if (this.cclass == this.tclass) {
/* 475 */         throw new ClassCastException();
/*     */       }
/* 477 */       throw new RuntimeException(new IllegalAccessException("Class " + this.cclass
/*     */ 
/*     */             
/* 480 */             .getName() + " can not access a protected member of class " + this.tclass
/*     */             
/* 482 */             .getName() + " using an instance of " + param1T
/*     */             
/* 484 */             .getClass().getName()));
/*     */     }
/*     */     
/*     */     public final boolean compareAndSet(T param1T, int param1Int1, int param1Int2) {
/* 488 */       accessCheck(param1T);
/* 489 */       return U.compareAndSwapInt(param1T, this.offset, param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     public final boolean weakCompareAndSet(T param1T, int param1Int1, int param1Int2) {
/* 493 */       accessCheck(param1T);
/* 494 */       return U.compareAndSwapInt(param1T, this.offset, param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     public final void set(T param1T, int param1Int) {
/* 498 */       accessCheck(param1T);
/* 499 */       U.putIntVolatile(param1T, this.offset, param1Int);
/*     */     }
/*     */     
/*     */     public final void lazySet(T param1T, int param1Int) {
/* 503 */       accessCheck(param1T);
/* 504 */       U.putOrderedInt(param1T, this.offset, param1Int);
/*     */     }
/*     */     
/*     */     public final int get(T param1T) {
/* 508 */       accessCheck(param1T);
/* 509 */       return U.getIntVolatile(param1T, this.offset);
/*     */     }
/*     */     
/*     */     public final int getAndSet(T param1T, int param1Int) {
/* 513 */       accessCheck(param1T);
/* 514 */       return U.getAndSetInt(param1T, this.offset, param1Int);
/*     */     }
/*     */     
/*     */     public final int getAndAdd(T param1T, int param1Int) {
/* 518 */       accessCheck(param1T);
/* 519 */       return U.getAndAddInt(param1T, this.offset, param1Int);
/*     */     }
/*     */     
/*     */     public final int getAndIncrement(T param1T) {
/* 523 */       return getAndAdd(param1T, 1);
/*     */     }
/*     */     
/*     */     public final int getAndDecrement(T param1T) {
/* 527 */       return getAndAdd(param1T, -1);
/*     */     }
/*     */     
/*     */     public final int incrementAndGet(T param1T) {
/* 531 */       return getAndAdd(param1T, 1) + 1;
/*     */     }
/*     */     
/*     */     public final int decrementAndGet(T param1T) {
/* 535 */       return getAndAdd(param1T, -1) - 1;
/*     */     }
/*     */     
/*     */     public final int addAndGet(T param1T, int param1Int) {
/* 539 */       return getAndAdd(param1T, param1Int) + param1Int;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/AtomicIntegerFieldUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */