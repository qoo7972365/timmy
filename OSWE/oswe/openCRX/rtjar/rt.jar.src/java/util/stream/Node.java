/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.LongConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ interface Node<T>
/*     */ {
/*     */   default int getChildCount() {
/*  89 */     return 0;
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
/*     */   default Node<T> getChild(int paramInt) {
/* 104 */     throw new IndexOutOfBoundsException();
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
/*     */   default Node<T> truncate(long paramLong1, long paramLong2, IntFunction<T[]> paramIntFunction) {
/* 121 */     if (paramLong1 == 0L && paramLong2 == count())
/* 122 */       return this; 
/* 123 */     Spliterator<T> spliterator = spliterator();
/* 124 */     long l = paramLong2 - paramLong1;
/* 125 */     Builder<T> builder = Nodes.builder(l, paramIntFunction);
/* 126 */     builder.begin(l); byte b;
/* 127 */     for (b = 0; b < paramLong1 && spliterator.tryAdvance(paramObject -> {  }); b++);
/* 128 */     for (b = 0; b < l && spliterator.tryAdvance(builder); b++);
/* 129 */     builder.end();
/* 130 */     return builder.build();
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
/*     */   default StreamShape getShape() {
/* 174 */     return StreamShape.REFERENCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Spliterator<T> spliterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void forEach(Consumer<? super T> paramConsumer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   T[] asArray(IntFunction<T[]> paramIntFunction);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void copyInto(T[] paramArrayOfT, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long count();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Builder<T>
/*     */     extends Sink<T>
/*     */   {
/*     */     Node<T> build();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static interface OfInt
/*     */       extends Builder<Integer>, Sink.OfInt
/*     */     {
/*     */       Node.OfInt build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static interface OfLong
/*     */       extends Builder<Long>, Sink.OfLong
/*     */     {
/*     */       Node.OfLong build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static interface OfDouble
/*     */       extends Builder<Double>, Sink.OfDouble
/*     */     {
/*     */       Node.OfDouble build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface OfPrimitive<T, T_CONS, T_ARR, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, T_NODE extends OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE>>
/*     */     extends Node<T>
/*     */   {
/*     */     default T_NODE getChild(int param1Int) {
/* 249 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default T[] asArray(IntFunction<T[]> param1IntFunction) {
/* 264 */       if (Tripwire.ENABLED) {
/* 265 */         Tripwire.trip(getClass(), "{0} calling Node.OfPrimitive.asArray");
/*     */       }
/* 267 */       long l = count();
/* 268 */       if (l >= 2147483639L)
/* 269 */         throw new IllegalArgumentException("Stream size exceeds max array size"); 
/* 270 */       Object[] arrayOfObject = (Object[])param1IntFunction.apply((int)count());
/* 271 */       copyInto((T[])arrayOfObject, 0);
/* 272 */       return (T[])arrayOfObject;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     T_SPLITR spliterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void forEach(T_CONS param1T_CONS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     T_NODE truncate(long param1Long1, long param1Long2, IntFunction<T[]> param1IntFunction);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     T_ARR asPrimitiveArray();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     T_ARR newArray(int param1Int);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void copyInto(T_ARR param1T_ARR, int param1Int);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface OfInt
/*     */     extends OfPrimitive<Integer, IntConsumer, int[], Spliterator.OfInt, OfInt>
/*     */   {
/*     */     default void forEach(Consumer<? super Integer> param1Consumer) {
/* 325 */       if (param1Consumer instanceof IntConsumer) {
/* 326 */         forEach((IntConsumer)param1Consumer);
/*     */       } else {
/*     */         
/* 329 */         if (Tripwire.ENABLED)
/* 330 */           Tripwire.trip(getClass(), "{0} calling Node.OfInt.forEachRemaining(Consumer)"); 
/* 331 */         spliterator().forEachRemaining(param1Consumer);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default void copyInto(Integer[] param1ArrayOfInteger, int param1Int) {
/* 345 */       if (Tripwire.ENABLED) {
/* 346 */         Tripwire.trip(getClass(), "{0} calling Node.OfInt.copyInto(Integer[], int)");
/*     */       }
/* 348 */       int[] arrayOfInt = asPrimitiveArray();
/* 349 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 350 */         param1ArrayOfInteger[param1Int + b] = Integer.valueOf(arrayOfInt[b]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     default OfInt truncate(long param1Long1, long param1Long2, IntFunction<Integer[]> param1IntFunction) {
/* 356 */       if (param1Long1 == 0L && param1Long2 == count())
/* 357 */         return this; 
/* 358 */       long l = param1Long2 - param1Long1;
/* 359 */       Spliterator.OfInt ofInt = spliterator();
/* 360 */       Node.Builder.OfInt ofInt1 = Nodes.intBuilder(l);
/* 361 */       ofInt1.begin(l); byte b;
/* 362 */       for (b = 0; b < param1Long1 && ofInt.tryAdvance(param1Int -> {  }); b++);
/* 363 */       for (b = 0; b < l && ofInt.tryAdvance(ofInt1); b++);
/* 364 */       ofInt1.end();
/* 365 */       return ofInt1.build();
/*     */     }
/*     */ 
/*     */     
/*     */     default int[] newArray(int param1Int) {
/* 370 */       return new int[param1Int];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default StreamShape getShape() {
/* 379 */       return StreamShape.INT_VALUE;
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
/*     */   public static interface OfLong
/*     */     extends OfPrimitive<Long, LongConsumer, long[], Spliterator.OfLong, OfLong>
/*     */   {
/*     */     default void forEach(Consumer<? super Long> param1Consumer) {
/* 398 */       if (param1Consumer instanceof LongConsumer) {
/* 399 */         forEach((LongConsumer)param1Consumer);
/*     */       } else {
/*     */         
/* 402 */         if (Tripwire.ENABLED)
/* 403 */           Tripwire.trip(getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)"); 
/* 404 */         spliterator().forEachRemaining(param1Consumer);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default void copyInto(Long[] param1ArrayOfLong, int param1Int) {
/* 418 */       if (Tripwire.ENABLED) {
/* 419 */         Tripwire.trip(getClass(), "{0} calling Node.OfInt.copyInto(Long[], int)");
/*     */       }
/* 421 */       long[] arrayOfLong = asPrimitiveArray();
/* 422 */       for (byte b = 0; b < arrayOfLong.length; b++) {
/* 423 */         param1ArrayOfLong[param1Int + b] = Long.valueOf(arrayOfLong[b]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     default OfLong truncate(long param1Long1, long param1Long2, IntFunction<Long[]> param1IntFunction) {
/* 429 */       if (param1Long1 == 0L && param1Long2 == count())
/* 430 */         return this; 
/* 431 */       long l = param1Long2 - param1Long1;
/* 432 */       Spliterator.OfLong ofLong = spliterator();
/* 433 */       Node.Builder.OfLong ofLong1 = Nodes.longBuilder(l);
/* 434 */       ofLong1.begin(l); byte b;
/* 435 */       for (b = 0; b < param1Long1 && ofLong.tryAdvance(param1Long -> {  }); b++);
/* 436 */       for (b = 0; b < l && ofLong.tryAdvance(ofLong1); b++);
/* 437 */       ofLong1.end();
/* 438 */       return ofLong1.build();
/*     */     }
/*     */ 
/*     */     
/*     */     default long[] newArray(int param1Int) {
/* 443 */       return new long[param1Int];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default StreamShape getShape() {
/* 452 */       return StreamShape.LONG_VALUE;
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
/*     */   public static interface OfDouble
/*     */     extends OfPrimitive<Double, DoubleConsumer, double[], Spliterator.OfDouble, OfDouble>
/*     */   {
/*     */     default void forEach(Consumer<? super Double> param1Consumer) {
/* 471 */       if (param1Consumer instanceof DoubleConsumer) {
/* 472 */         forEach((DoubleConsumer)param1Consumer);
/*     */       } else {
/*     */         
/* 475 */         if (Tripwire.ENABLED)
/* 476 */           Tripwire.trip(getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)"); 
/* 477 */         spliterator().forEachRemaining(param1Consumer);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default void copyInto(Double[] param1ArrayOfDouble, int param1Int) {
/* 493 */       if (Tripwire.ENABLED) {
/* 494 */         Tripwire.trip(getClass(), "{0} calling Node.OfDouble.copyInto(Double[], int)");
/*     */       }
/* 496 */       double[] arrayOfDouble = asPrimitiveArray();
/* 497 */       for (byte b = 0; b < arrayOfDouble.length; b++) {
/* 498 */         param1ArrayOfDouble[param1Int + b] = Double.valueOf(arrayOfDouble[b]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     default OfDouble truncate(long param1Long1, long param1Long2, IntFunction<Double[]> param1IntFunction) {
/* 504 */       if (param1Long1 == 0L && param1Long2 == count())
/* 505 */         return this; 
/* 506 */       long l = param1Long2 - param1Long1;
/* 507 */       Spliterator.OfDouble ofDouble = spliterator();
/* 508 */       Node.Builder.OfDouble ofDouble1 = Nodes.doubleBuilder(l);
/* 509 */       ofDouble1.begin(l); byte b;
/* 510 */       for (b = 0; b < param1Long1 && ofDouble.tryAdvance(param1Double -> {  }); b++);
/* 511 */       for (b = 0; b < l && ofDouble.tryAdvance(ofDouble1); b++);
/* 512 */       ofDouble1.end();
/* 513 */       return ofDouble1.build();
/*     */     }
/*     */ 
/*     */     
/*     */     default double[] newArray(int param1Int) {
/* 518 */       return new double[param1Int];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default StreamShape getShape() {
/* 528 */       return StreamShape.DOUBLE_VALUE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/Node.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */