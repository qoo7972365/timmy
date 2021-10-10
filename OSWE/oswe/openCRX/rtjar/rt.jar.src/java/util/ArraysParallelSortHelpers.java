/*      */ package java.util;
/*      */ 
/*      */ import java.util.concurrent.CountedCompleter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class ArraysParallelSortHelpers
/*      */ {
/*      */   static final class EmptyCompleter
/*      */     extends CountedCompleter<Void>
/*      */   {
/*      */     static final long serialVersionUID = 2446542900576103244L;
/*      */     
/*      */     EmptyCompleter(CountedCompleter<?> param1CountedCompleter) {
/*   91 */       super(param1CountedCompleter);
/*      */     }
/*      */     
/*      */     public final void compute() {}
/*      */   }
/*      */   
/*      */   static final class Relay extends CountedCompleter<Void> {
/*      */     static final long serialVersionUID = 2446542900576103244L;
/*      */     final CountedCompleter<?> task;
/*      */     
/*      */     Relay(CountedCompleter<?> param1CountedCompleter) {
/*  102 */       super((CountedCompleter<?>)null, 1);
/*  103 */       this.task = param1CountedCompleter;
/*      */     }
/*      */     public final void compute() {}
/*      */     public final void onCompletion(CountedCompleter<?> param1CountedCompleter) {
/*  107 */       this.task.compute();
/*      */     } }
/*      */   
/*      */   static final class FJObject {
/*      */     static final class Sorter<T> extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final T[] a;
/*      */       final T[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       Comparator<? super T> comparator;
/*      */       
/*      */       Sorter(CountedCompleter<?> param2CountedCompleter, T[] param2ArrayOfT1, T[] param2ArrayOfT2, int param2Int1, int param2Int2, int param2Int3, int param2Int4, Comparator<? super T> param2Comparator) {
/*  121 */         super(param2CountedCompleter);
/*  122 */         this.a = param2ArrayOfT1; this.w = param2ArrayOfT2; this.base = param2Int1; this.size = param2Int2;
/*  123 */         this.wbase = param2Int3; this.gran = param2Int4;
/*  124 */         this.comparator = param2Comparator;
/*      */       } public final void compute() {
/*      */         ArraysParallelSortHelpers.EmptyCompleter emptyCompleter;
/*  127 */         Sorter<?> sorter = this;
/*  128 */         Comparator<? super T> comparator = this.comparator;
/*  129 */         T[] arrayOfT1 = this.a, arrayOfT2 = this.w;
/*  130 */         int i = this.base, j = this.size, k = this.wbase, m = this.gran;
/*  131 */         while (j > m) {
/*  132 */           int n = j >>> 1, i1 = n >>> 1, i2 = n + i1;
/*  133 */           ArraysParallelSortHelpers.Relay relay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJObject.Merger<>(sorter, arrayOfT2, arrayOfT1, k, n, k + n, j - n, i, m, comparator));
/*      */           
/*  135 */           ArraysParallelSortHelpers.Relay relay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJObject.Merger<>(relay1, arrayOfT1, arrayOfT2, i + n, i1, i + i2, j - i2, k + n, m, comparator));
/*      */           
/*  137 */           (new Sorter(relay2, arrayOfT1, arrayOfT2, i + i2, j - i2, k + i2, m, comparator)).fork();
/*  138 */           (new Sorter(relay2, arrayOfT1, arrayOfT2, i + n, i1, k + n, m, comparator)).fork();
/*  139 */           ArraysParallelSortHelpers.Relay relay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJObject.Merger<>(relay1, arrayOfT1, arrayOfT2, i, i1, i + i1, n - i1, k, m, comparator));
/*      */           
/*  141 */           (new Sorter(relay3, arrayOfT1, arrayOfT2, i + i1, n - i1, k + i1, m, comparator)).fork();
/*  142 */           emptyCompleter = new ArraysParallelSortHelpers.EmptyCompleter(relay3);
/*  143 */           j = i1;
/*      */         } 
/*  145 */         TimSort.sort(arrayOfT1, i, i + j, comparator, arrayOfT2, k, j);
/*  146 */         emptyCompleter.tryComplete();
/*      */       } }
/*      */     static final class Merger<T> extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L; final T[] a;
/*      */       final T[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       final int rbase;
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       Comparator<? super T> comparator;
/*      */       
/*      */       Merger(CountedCompleter<?> param2CountedCompleter, T[] param2ArrayOfT1, T[] param2ArrayOfT2, int param2Int1, int param2Int2, int param2Int3, int param2Int4, int param2Int5, int param2Int6, Comparator<? super T> param2Comparator) {
/*  159 */         super(param2CountedCompleter);
/*  160 */         this.a = param2ArrayOfT1; this.w = param2ArrayOfT2;
/*  161 */         this.lbase = param2Int1; this.lsize = param2Int2;
/*  162 */         this.rbase = param2Int3; this.rsize = param2Int4;
/*  163 */         this.wbase = param2Int5; this.gran = param2Int6;
/*  164 */         this.comparator = param2Comparator;
/*      */       }
/*      */       
/*      */       public final void compute() {
/*  168 */         Comparator<? super T> comparator = this.comparator;
/*  169 */         T[] arrayOfT1 = this.a, arrayOfT2 = this.w;
/*  170 */         int i = this.lbase, j = this.lsize, k = this.rbase;
/*  171 */         int m = this.rsize, n = this.wbase, i1 = this.gran;
/*  172 */         if (arrayOfT1 == null || arrayOfT2 == null || i < 0 || k < 0 || n < 0 || comparator == null)
/*      */         {
/*  174 */           throw new IllegalStateException(); }  while (true) {
/*      */           int i4, i5;
/*  176 */           if (j >= m) {
/*  177 */             if (j <= i1)
/*      */               break; 
/*  179 */             i5 = m;
/*  180 */             T t = arrayOfT1[(i4 = j >>> 1) + i]; int i6;
/*  181 */             for (i6 = 0; i6 < i5; ) {
/*  182 */               int i7 = i6 + i5 >>> 1;
/*  183 */               if (comparator.compare(t, arrayOfT1[i7 + k]) <= 0) {
/*  184 */                 i5 = i7; continue;
/*      */               } 
/*  186 */               i6 = i7 + 1;
/*      */             } 
/*      */           } else {
/*      */             
/*  190 */             if (m <= i1)
/*      */               break; 
/*  192 */             i4 = j;
/*  193 */             T t = arrayOfT1[(i5 = m >>> 1) + k]; int i6;
/*  194 */             for (i6 = 0; i6 < i4; ) {
/*  195 */               int i7 = i6 + i4 >>> 1;
/*  196 */               if (comparator.compare(t, arrayOfT1[i7 + i]) <= 0) {
/*  197 */                 i4 = i7; continue;
/*      */               } 
/*  199 */               i6 = i7 + 1;
/*      */             } 
/*      */           } 
/*  202 */           Merger merger = new Merger(this, arrayOfT1, arrayOfT2, i + i4, j - i4, k + i5, m - i5, n + i4 + i5, i1, comparator);
/*      */ 
/*      */           
/*  205 */           m = i5;
/*  206 */           j = i4;
/*  207 */           addToPendingCount(1);
/*  208 */           merger.fork();
/*      */         } 
/*      */         
/*  211 */         int i2 = i + j, i3 = k + m;
/*  212 */         while (i < i2 && k < i3) {
/*      */           T t1, t2, t3;
/*  214 */           if (comparator.compare(t2 = arrayOfT1[i], t3 = arrayOfT1[k]) <= 0) {
/*  215 */             i++; t1 = t2;
/*      */           } else {
/*      */             
/*  218 */             k++; t1 = t3;
/*      */           } 
/*  220 */           arrayOfT2[n++] = t1;
/*      */         } 
/*  222 */         if (k < i3) {
/*  223 */           System.arraycopy(arrayOfT1, k, arrayOfT2, n, i3 - k);
/*  224 */         } else if (i < i2) {
/*  225 */           System.arraycopy(arrayOfT1, i, arrayOfT2, n, i2 - i);
/*      */         } 
/*  227 */         tryComplete();
/*      */       } }
/*      */   
/*      */   }
/*      */   
/*      */   static final class FJByte { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final byte[] a;
/*      */       final byte[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Sorter(CountedCompleter<?> param2CountedCompleter, byte[] param2ArrayOfbyte1, byte[] param2ArrayOfbyte2, int param2Int1, int param2Int2, int param2Int3, int param2Int4) {
/*  241 */         super(param2CountedCompleter);
/*  242 */         this.a = param2ArrayOfbyte1; this.w = param2ArrayOfbyte2; this.base = param2Int1; this.size = param2Int2;
/*  243 */         this.wbase = param2Int3; this.gran = param2Int4;
/*      */       } public final void compute() {
/*      */         ArraysParallelSortHelpers.EmptyCompleter emptyCompleter;
/*  246 */         Sorter sorter = this;
/*  247 */         byte[] arrayOfByte1 = this.a, arrayOfByte2 = this.w;
/*  248 */         int i = this.base, j = this.size, k = this.wbase, m = this.gran;
/*  249 */         while (j > m) {
/*  250 */           int n = j >>> 1, i1 = n >>> 1, i2 = n + i1;
/*  251 */           ArraysParallelSortHelpers.Relay relay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJByte.Merger(sorter, arrayOfByte2, arrayOfByte1, k, n, k + n, j - n, i, m));
/*      */           
/*  253 */           ArraysParallelSortHelpers.Relay relay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJByte.Merger(relay1, arrayOfByte1, arrayOfByte2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  255 */           (new Sorter(relay2, arrayOfByte1, arrayOfByte2, i + i2, j - i2, k + i2, m)).fork();
/*  256 */           (new Sorter(relay2, arrayOfByte1, arrayOfByte2, i + n, i1, k + n, m)).fork();
/*  257 */           ArraysParallelSortHelpers.Relay relay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJByte.Merger(relay1, arrayOfByte1, arrayOfByte2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  259 */           (new Sorter(relay3, arrayOfByte1, arrayOfByte2, i + i1, n - i1, k + i1, m)).fork();
/*  260 */           emptyCompleter = new ArraysParallelSortHelpers.EmptyCompleter(relay3);
/*  261 */           j = i1;
/*      */         } 
/*  263 */         DualPivotQuicksort.sort(arrayOfByte1, i, i + j - 1);
/*  264 */         emptyCompleter.tryComplete();
/*      */       } }
/*      */ 
/*      */     
/*      */     static final class Merger extends CountedCompleter<Void> {
/*      */       static final long serialVersionUID = 2446542900576103244L;
/*      */       final byte[] a;
/*      */       final byte[] w;
/*      */       final int lbase;
/*      */       
/*      */       Merger(CountedCompleter<?> param2CountedCompleter, byte[] param2ArrayOfbyte1, byte[] param2ArrayOfbyte2, int param2Int1, int param2Int2, int param2Int3, int param2Int4, int param2Int5, int param2Int6) {
/*  275 */         super(param2CountedCompleter);
/*  276 */         this.a = param2ArrayOfbyte1; this.w = param2ArrayOfbyte2;
/*  277 */         this.lbase = param2Int1; this.lsize = param2Int2;
/*  278 */         this.rbase = param2Int3; this.rsize = param2Int4;
/*  279 */         this.wbase = param2Int5; this.gran = param2Int6;
/*      */       }
/*      */       final int lsize; final int rbase; final int rsize; final int wbase; final int gran;
/*      */       public final void compute() {
/*  283 */         byte[] arrayOfByte1 = this.a, arrayOfByte2 = this.w;
/*  284 */         int i = this.lbase, j = this.lsize, k = this.rbase;
/*  285 */         int m = this.rsize, n = this.wbase, i1 = this.gran;
/*  286 */         if (arrayOfByte1 == null || arrayOfByte2 == null || i < 0 || k < 0 || n < 0)
/*  287 */           throw new IllegalStateException();  while (true) {
/*      */           int i4, i5;
/*  289 */           if (j >= m) {
/*  290 */             if (j <= i1)
/*      */               break; 
/*  292 */             i5 = m;
/*  293 */             byte b = arrayOfByte1[(i4 = j >>> 1) + i]; int i6;
/*  294 */             for (i6 = 0; i6 < i5; ) {
/*  295 */               int i7 = i6 + i5 >>> 1;
/*  296 */               if (b <= arrayOfByte1[i7 + k]) {
/*  297 */                 i5 = i7; continue;
/*      */               } 
/*  299 */               i6 = i7 + 1;
/*      */             } 
/*      */           } else {
/*      */             
/*  303 */             if (m <= i1)
/*      */               break; 
/*  305 */             i4 = j;
/*  306 */             byte b = arrayOfByte1[(i5 = m >>> 1) + k]; int i6;
/*  307 */             for (i6 = 0; i6 < i4; ) {
/*  308 */               int i7 = i6 + i4 >>> 1;
/*  309 */               if (b <= arrayOfByte1[i7 + i]) {
/*  310 */                 i4 = i7; continue;
/*      */               } 
/*  312 */               i6 = i7 + 1;
/*      */             } 
/*      */           } 
/*  315 */           Merger merger = new Merger(this, arrayOfByte1, arrayOfByte2, i + i4, j - i4, k + i5, m - i5, n + i4 + i5, i1);
/*      */ 
/*      */           
/*  318 */           m = i5;
/*  319 */           j = i4;
/*  320 */           addToPendingCount(1);
/*  321 */           merger.fork();
/*      */         } 
/*      */         
/*  324 */         int i2 = i + j, i3 = k + m;
/*  325 */         while (i < i2 && k < i3) {
/*      */           byte b; byte b1, b2;
/*  327 */           if ((b1 = arrayOfByte1[i]) <= (b2 = arrayOfByte1[k])) {
/*  328 */             i++; b = b1;
/*      */           } else {
/*      */             
/*  331 */             k++; b = b2;
/*      */           } 
/*  333 */           arrayOfByte2[n++] = b;
/*      */         } 
/*  335 */         if (k < i3) {
/*  336 */           System.arraycopy(arrayOfByte1, k, arrayOfByte2, n, i3 - k);
/*  337 */         } else if (i < i2) {
/*  338 */           System.arraycopy(arrayOfByte1, i, arrayOfByte2, n, i2 - i);
/*  339 */         }  tryComplete();
/*      */       }
/*      */     } }
/*      */   
/*      */   static final class FJChar { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final char[] a;
/*      */       final char[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Sorter(CountedCompleter<?> param2CountedCompleter, char[] param2ArrayOfchar1, char[] param2ArrayOfchar2, int param2Int1, int param2Int2, int param2Int3, int param2Int4) {
/*  352 */         super(param2CountedCompleter);
/*  353 */         this.a = param2ArrayOfchar1; this.w = param2ArrayOfchar2; this.base = param2Int1; this.size = param2Int2;
/*  354 */         this.wbase = param2Int3; this.gran = param2Int4;
/*      */       } public final void compute() {
/*      */         ArraysParallelSortHelpers.EmptyCompleter emptyCompleter;
/*  357 */         Sorter sorter = this;
/*  358 */         char[] arrayOfChar1 = this.a, arrayOfChar2 = this.w;
/*  359 */         int i = this.base, j = this.size, k = this.wbase, m = this.gran;
/*  360 */         while (j > m) {
/*  361 */           int n = j >>> 1, i1 = n >>> 1, i2 = n + i1;
/*  362 */           ArraysParallelSortHelpers.Relay relay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJChar.Merger(sorter, arrayOfChar2, arrayOfChar1, k, n, k + n, j - n, i, m));
/*      */           
/*  364 */           ArraysParallelSortHelpers.Relay relay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJChar.Merger(relay1, arrayOfChar1, arrayOfChar2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  366 */           (new Sorter(relay2, arrayOfChar1, arrayOfChar2, i + i2, j - i2, k + i2, m)).fork();
/*  367 */           (new Sorter(relay2, arrayOfChar1, arrayOfChar2, i + n, i1, k + n, m)).fork();
/*  368 */           ArraysParallelSortHelpers.Relay relay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJChar.Merger(relay1, arrayOfChar1, arrayOfChar2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  370 */           (new Sorter(relay3, arrayOfChar1, arrayOfChar2, i + i1, n - i1, k + i1, m)).fork();
/*  371 */           emptyCompleter = new ArraysParallelSortHelpers.EmptyCompleter(relay3);
/*  372 */           j = i1;
/*      */         } 
/*  374 */         DualPivotQuicksort.sort(arrayOfChar1, i, i + j - 1, arrayOfChar2, k, j);
/*  375 */         emptyCompleter.tryComplete();
/*      */       } }
/*      */     static final class Merger extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L; final char[] a; final char[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       final int rbase;
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Merger(CountedCompleter<?> param2CountedCompleter, char[] param2ArrayOfchar1, char[] param2ArrayOfchar2, int param2Int1, int param2Int2, int param2Int3, int param2Int4, int param2Int5, int param2Int6) {
/*  386 */         super(param2CountedCompleter);
/*  387 */         this.a = param2ArrayOfchar1; this.w = param2ArrayOfchar2;
/*  388 */         this.lbase = param2Int1; this.lsize = param2Int2;
/*  389 */         this.rbase = param2Int3; this.rsize = param2Int4;
/*  390 */         this.wbase = param2Int5; this.gran = param2Int6;
/*      */       }
/*      */       
/*      */       public final void compute() {
/*  394 */         char[] arrayOfChar1 = this.a, arrayOfChar2 = this.w;
/*  395 */         int i = this.lbase, j = this.lsize, k = this.rbase;
/*  396 */         int m = this.rsize, n = this.wbase, i1 = this.gran;
/*  397 */         if (arrayOfChar1 == null || arrayOfChar2 == null || i < 0 || k < 0 || n < 0)
/*  398 */           throw new IllegalStateException();  while (true) {
/*      */           int i4, i5;
/*  400 */           if (j >= m) {
/*  401 */             if (j <= i1)
/*      */               break; 
/*  403 */             i5 = m;
/*  404 */             char c = arrayOfChar1[(i4 = j >>> 1) + i]; int i6;
/*  405 */             for (i6 = 0; i6 < i5; ) {
/*  406 */               int i7 = i6 + i5 >>> 1;
/*  407 */               if (c <= arrayOfChar1[i7 + k]) {
/*  408 */                 i5 = i7; continue;
/*      */               } 
/*  410 */               i6 = i7 + 1;
/*      */             } 
/*      */           } else {
/*      */             
/*  414 */             if (m <= i1)
/*      */               break; 
/*  416 */             i4 = j;
/*  417 */             char c = arrayOfChar1[(i5 = m >>> 1) + k]; int i6;
/*  418 */             for (i6 = 0; i6 < i4; ) {
/*  419 */               int i7 = i6 + i4 >>> 1;
/*  420 */               if (c <= arrayOfChar1[i7 + i]) {
/*  421 */                 i4 = i7; continue;
/*      */               } 
/*  423 */               i6 = i7 + 1;
/*      */             } 
/*      */           } 
/*  426 */           Merger merger = new Merger(this, arrayOfChar1, arrayOfChar2, i + i4, j - i4, k + i5, m - i5, n + i4 + i5, i1);
/*      */ 
/*      */           
/*  429 */           m = i5;
/*  430 */           j = i4;
/*  431 */           addToPendingCount(1);
/*  432 */           merger.fork();
/*      */         } 
/*      */         
/*  435 */         int i2 = i + j, i3 = k + m;
/*  436 */         while (i < i2 && k < i3) {
/*      */           char c; char c1, c2;
/*  438 */           if ((c1 = arrayOfChar1[i]) <= (c2 = arrayOfChar1[k])) {
/*  439 */             i++; c = c1;
/*      */           } else {
/*      */             
/*  442 */             k++; c = c2;
/*      */           } 
/*  444 */           arrayOfChar2[n++] = c;
/*      */         } 
/*  446 */         if (k < i3) {
/*  447 */           System.arraycopy(arrayOfChar1, k, arrayOfChar2, n, i3 - k);
/*  448 */         } else if (i < i2) {
/*  449 */           System.arraycopy(arrayOfChar1, i, arrayOfChar2, n, i2 - i);
/*  450 */         }  tryComplete();
/*      */       } }
/*      */      }
/*      */   
/*      */   static final class FJShort { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final short[] a;
/*      */       final short[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Sorter(CountedCompleter<?> param2CountedCompleter, short[] param2ArrayOfshort1, short[] param2ArrayOfshort2, int param2Int1, int param2Int2, int param2Int3, int param2Int4) {
/*  463 */         super(param2CountedCompleter);
/*  464 */         this.a = param2ArrayOfshort1; this.w = param2ArrayOfshort2; this.base = param2Int1; this.size = param2Int2;
/*  465 */         this.wbase = param2Int3; this.gran = param2Int4;
/*      */       } public final void compute() {
/*      */         ArraysParallelSortHelpers.EmptyCompleter emptyCompleter;
/*  468 */         Sorter sorter = this;
/*  469 */         short[] arrayOfShort1 = this.a, arrayOfShort2 = this.w;
/*  470 */         int i = this.base, j = this.size, k = this.wbase, m = this.gran;
/*  471 */         while (j > m) {
/*  472 */           int n = j >>> 1, i1 = n >>> 1, i2 = n + i1;
/*  473 */           ArraysParallelSortHelpers.Relay relay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJShort.Merger(sorter, arrayOfShort2, arrayOfShort1, k, n, k + n, j - n, i, m));
/*      */           
/*  475 */           ArraysParallelSortHelpers.Relay relay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJShort.Merger(relay1, arrayOfShort1, arrayOfShort2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  477 */           (new Sorter(relay2, arrayOfShort1, arrayOfShort2, i + i2, j - i2, k + i2, m)).fork();
/*  478 */           (new Sorter(relay2, arrayOfShort1, arrayOfShort2, i + n, i1, k + n, m)).fork();
/*  479 */           ArraysParallelSortHelpers.Relay relay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJShort.Merger(relay1, arrayOfShort1, arrayOfShort2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  481 */           (new Sorter(relay3, arrayOfShort1, arrayOfShort2, i + i1, n - i1, k + i1, m)).fork();
/*  482 */           emptyCompleter = new ArraysParallelSortHelpers.EmptyCompleter(relay3);
/*  483 */           j = i1;
/*      */         } 
/*  485 */         DualPivotQuicksort.sort(arrayOfShort1, i, i + j - 1, arrayOfShort2, k, j);
/*  486 */         emptyCompleter.tryComplete();
/*      */       } }
/*      */     static final class Merger extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L; final short[] a; final short[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       final int rbase;
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Merger(CountedCompleter<?> param2CountedCompleter, short[] param2ArrayOfshort1, short[] param2ArrayOfshort2, int param2Int1, int param2Int2, int param2Int3, int param2Int4, int param2Int5, int param2Int6) {
/*  497 */         super(param2CountedCompleter);
/*  498 */         this.a = param2ArrayOfshort1; this.w = param2ArrayOfshort2;
/*  499 */         this.lbase = param2Int1; this.lsize = param2Int2;
/*  500 */         this.rbase = param2Int3; this.rsize = param2Int4;
/*  501 */         this.wbase = param2Int5; this.gran = param2Int6;
/*      */       }
/*      */       
/*      */       public final void compute() {
/*  505 */         short[] arrayOfShort1 = this.a, arrayOfShort2 = this.w;
/*  506 */         int i = this.lbase, j = this.lsize, k = this.rbase;
/*  507 */         int m = this.rsize, n = this.wbase, i1 = this.gran;
/*  508 */         if (arrayOfShort1 == null || arrayOfShort2 == null || i < 0 || k < 0 || n < 0)
/*  509 */           throw new IllegalStateException();  while (true) {
/*      */           int i4, i5;
/*  511 */           if (j >= m) {
/*  512 */             if (j <= i1)
/*      */               break; 
/*  514 */             i5 = m;
/*  515 */             short s = arrayOfShort1[(i4 = j >>> 1) + i]; int i6;
/*  516 */             for (i6 = 0; i6 < i5; ) {
/*  517 */               int i7 = i6 + i5 >>> 1;
/*  518 */               if (s <= arrayOfShort1[i7 + k]) {
/*  519 */                 i5 = i7; continue;
/*      */               } 
/*  521 */               i6 = i7 + 1;
/*      */             } 
/*      */           } else {
/*      */             
/*  525 */             if (m <= i1)
/*      */               break; 
/*  527 */             i4 = j;
/*  528 */             short s = arrayOfShort1[(i5 = m >>> 1) + k]; int i6;
/*  529 */             for (i6 = 0; i6 < i4; ) {
/*  530 */               int i7 = i6 + i4 >>> 1;
/*  531 */               if (s <= arrayOfShort1[i7 + i]) {
/*  532 */                 i4 = i7; continue;
/*      */               } 
/*  534 */               i6 = i7 + 1;
/*      */             } 
/*      */           } 
/*  537 */           Merger merger = new Merger(this, arrayOfShort1, arrayOfShort2, i + i4, j - i4, k + i5, m - i5, n + i4 + i5, i1);
/*      */ 
/*      */           
/*  540 */           m = i5;
/*  541 */           j = i4;
/*  542 */           addToPendingCount(1);
/*  543 */           merger.fork();
/*      */         } 
/*      */         
/*  546 */         int i2 = i + j, i3 = k + m;
/*  547 */         while (i < i2 && k < i3) {
/*      */           short s; short s1, s2;
/*  549 */           if ((s1 = arrayOfShort1[i]) <= (s2 = arrayOfShort1[k])) {
/*  550 */             i++; s = s1;
/*      */           } else {
/*      */             
/*  553 */             k++; s = s2;
/*      */           } 
/*  555 */           arrayOfShort2[n++] = s;
/*      */         } 
/*  557 */         if (k < i3) {
/*  558 */           System.arraycopy(arrayOfShort1, k, arrayOfShort2, n, i3 - k);
/*  559 */         } else if (i < i2) {
/*  560 */           System.arraycopy(arrayOfShort1, i, arrayOfShort2, n, i2 - i);
/*  561 */         }  tryComplete();
/*      */       } }
/*      */      }
/*      */   
/*      */   static final class FJInt { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final int[] a;
/*      */       final int[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Sorter(CountedCompleter<?> param2CountedCompleter, int[] param2ArrayOfint1, int[] param2ArrayOfint2, int param2Int1, int param2Int2, int param2Int3, int param2Int4) {
/*  574 */         super(param2CountedCompleter);
/*  575 */         this.a = param2ArrayOfint1; this.w = param2ArrayOfint2; this.base = param2Int1; this.size = param2Int2;
/*  576 */         this.wbase = param2Int3; this.gran = param2Int4;
/*      */       } public final void compute() {
/*      */         ArraysParallelSortHelpers.EmptyCompleter emptyCompleter;
/*  579 */         Sorter sorter = this;
/*  580 */         int[] arrayOfInt1 = this.a, arrayOfInt2 = this.w;
/*  581 */         int i = this.base, j = this.size, k = this.wbase, m = this.gran;
/*  582 */         while (j > m) {
/*  583 */           int n = j >>> 1, i1 = n >>> 1, i2 = n + i1;
/*  584 */           ArraysParallelSortHelpers.Relay relay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJInt.Merger(sorter, arrayOfInt2, arrayOfInt1, k, n, k + n, j - n, i, m));
/*      */           
/*  586 */           ArraysParallelSortHelpers.Relay relay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJInt.Merger(relay1, arrayOfInt1, arrayOfInt2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  588 */           (new Sorter(relay2, arrayOfInt1, arrayOfInt2, i + i2, j - i2, k + i2, m)).fork();
/*  589 */           (new Sorter(relay2, arrayOfInt1, arrayOfInt2, i + n, i1, k + n, m)).fork();
/*  590 */           ArraysParallelSortHelpers.Relay relay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJInt.Merger(relay1, arrayOfInt1, arrayOfInt2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  592 */           (new Sorter(relay3, arrayOfInt1, arrayOfInt2, i + i1, n - i1, k + i1, m)).fork();
/*  593 */           emptyCompleter = new ArraysParallelSortHelpers.EmptyCompleter(relay3);
/*  594 */           j = i1;
/*      */         } 
/*  596 */         DualPivotQuicksort.sort(arrayOfInt1, i, i + j - 1, arrayOfInt2, k, j);
/*  597 */         emptyCompleter.tryComplete();
/*      */       } }
/*      */     static final class Merger extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L; final int[] a; final int[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       final int rbase;
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Merger(CountedCompleter<?> param2CountedCompleter, int[] param2ArrayOfint1, int[] param2ArrayOfint2, int param2Int1, int param2Int2, int param2Int3, int param2Int4, int param2Int5, int param2Int6) {
/*  608 */         super(param2CountedCompleter);
/*  609 */         this.a = param2ArrayOfint1; this.w = param2ArrayOfint2;
/*  610 */         this.lbase = param2Int1; this.lsize = param2Int2;
/*  611 */         this.rbase = param2Int3; this.rsize = param2Int4;
/*  612 */         this.wbase = param2Int5; this.gran = param2Int6;
/*      */       }
/*      */       
/*      */       public final void compute() {
/*  616 */         int[] arrayOfInt1 = this.a, arrayOfInt2 = this.w;
/*  617 */         int i = this.lbase, j = this.lsize, k = this.rbase;
/*  618 */         int m = this.rsize, n = this.wbase, i1 = this.gran;
/*  619 */         if (arrayOfInt1 == null || arrayOfInt2 == null || i < 0 || k < 0 || n < 0)
/*  620 */           throw new IllegalStateException();  while (true) {
/*      */           int i4, i5;
/*  622 */           if (j >= m) {
/*  623 */             if (j <= i1)
/*      */               break; 
/*  625 */             i5 = m;
/*  626 */             int i6 = arrayOfInt1[(i4 = j >>> 1) + i]; int i7;
/*  627 */             for (i7 = 0; i7 < i5; ) {
/*  628 */               int i8 = i7 + i5 >>> 1;
/*  629 */               if (i6 <= arrayOfInt1[i8 + k]) {
/*  630 */                 i5 = i8; continue;
/*      */               } 
/*  632 */               i7 = i8 + 1;
/*      */             } 
/*      */           } else {
/*      */             
/*  636 */             if (m <= i1)
/*      */               break; 
/*  638 */             i4 = j;
/*  639 */             int i6 = arrayOfInt1[(i5 = m >>> 1) + k]; int i7;
/*  640 */             for (i7 = 0; i7 < i4; ) {
/*  641 */               int i8 = i7 + i4 >>> 1;
/*  642 */               if (i6 <= arrayOfInt1[i8 + i]) {
/*  643 */                 i4 = i8; continue;
/*      */               } 
/*  645 */               i7 = i8 + 1;
/*      */             } 
/*      */           } 
/*  648 */           Merger merger = new Merger(this, arrayOfInt1, arrayOfInt2, i + i4, j - i4, k + i5, m - i5, n + i4 + i5, i1);
/*      */ 
/*      */           
/*  651 */           m = i5;
/*  652 */           j = i4;
/*  653 */           addToPendingCount(1);
/*  654 */           merger.fork();
/*      */         } 
/*      */         
/*  657 */         int i2 = i + j, i3 = k + m;
/*  658 */         while (i < i2 && k < i3) {
/*      */           int i4, i5, i6;
/*  660 */           if ((i5 = arrayOfInt1[i]) <= (i6 = arrayOfInt1[k])) {
/*  661 */             i++; i4 = i5;
/*      */           } else {
/*      */             
/*  664 */             k++; i4 = i6;
/*      */           } 
/*  666 */           arrayOfInt2[n++] = i4;
/*      */         } 
/*  668 */         if (k < i3) {
/*  669 */           System.arraycopy(arrayOfInt1, k, arrayOfInt2, n, i3 - k);
/*  670 */         } else if (i < i2) {
/*  671 */           System.arraycopy(arrayOfInt1, i, arrayOfInt2, n, i2 - i);
/*  672 */         }  tryComplete();
/*      */       } }
/*      */      }
/*      */   
/*      */   static final class FJLong { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final long[] a;
/*      */       final long[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Sorter(CountedCompleter<?> param2CountedCompleter, long[] param2ArrayOflong1, long[] param2ArrayOflong2, int param2Int1, int param2Int2, int param2Int3, int param2Int4) {
/*  685 */         super(param2CountedCompleter);
/*  686 */         this.a = param2ArrayOflong1; this.w = param2ArrayOflong2; this.base = param2Int1; this.size = param2Int2;
/*  687 */         this.wbase = param2Int3; this.gran = param2Int4;
/*      */       } public final void compute() {
/*      */         ArraysParallelSortHelpers.EmptyCompleter emptyCompleter;
/*  690 */         Sorter sorter = this;
/*  691 */         long[] arrayOfLong1 = this.a, arrayOfLong2 = this.w;
/*  692 */         int i = this.base, j = this.size, k = this.wbase, m = this.gran;
/*  693 */         while (j > m) {
/*  694 */           int n = j >>> 1, i1 = n >>> 1, i2 = n + i1;
/*  695 */           ArraysParallelSortHelpers.Relay relay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJLong.Merger(sorter, arrayOfLong2, arrayOfLong1, k, n, k + n, j - n, i, m));
/*      */           
/*  697 */           ArraysParallelSortHelpers.Relay relay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJLong.Merger(relay1, arrayOfLong1, arrayOfLong2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  699 */           (new Sorter(relay2, arrayOfLong1, arrayOfLong2, i + i2, j - i2, k + i2, m)).fork();
/*  700 */           (new Sorter(relay2, arrayOfLong1, arrayOfLong2, i + n, i1, k + n, m)).fork();
/*  701 */           ArraysParallelSortHelpers.Relay relay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJLong.Merger(relay1, arrayOfLong1, arrayOfLong2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  703 */           (new Sorter(relay3, arrayOfLong1, arrayOfLong2, i + i1, n - i1, k + i1, m)).fork();
/*  704 */           emptyCompleter = new ArraysParallelSortHelpers.EmptyCompleter(relay3);
/*  705 */           j = i1;
/*      */         } 
/*  707 */         DualPivotQuicksort.sort(arrayOfLong1, i, i + j - 1, arrayOfLong2, k, j);
/*  708 */         emptyCompleter.tryComplete();
/*      */       } }
/*      */     static final class Merger extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L; final long[] a; final long[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       final int rbase;
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Merger(CountedCompleter<?> param2CountedCompleter, long[] param2ArrayOflong1, long[] param2ArrayOflong2, int param2Int1, int param2Int2, int param2Int3, int param2Int4, int param2Int5, int param2Int6) {
/*  719 */         super(param2CountedCompleter);
/*  720 */         this.a = param2ArrayOflong1; this.w = param2ArrayOflong2;
/*  721 */         this.lbase = param2Int1; this.lsize = param2Int2;
/*  722 */         this.rbase = param2Int3; this.rsize = param2Int4;
/*  723 */         this.wbase = param2Int5; this.gran = param2Int6;
/*      */       }
/*      */       
/*      */       public final void compute() {
/*  727 */         long[] arrayOfLong1 = this.a, arrayOfLong2 = this.w;
/*  728 */         int i = this.lbase, j = this.lsize, k = this.rbase;
/*  729 */         int m = this.rsize, n = this.wbase, i1 = this.gran;
/*  730 */         if (arrayOfLong1 == null || arrayOfLong2 == null || i < 0 || k < 0 || n < 0)
/*  731 */           throw new IllegalStateException();  while (true) {
/*      */           int i4, i5;
/*  733 */           if (j >= m) {
/*  734 */             if (j <= i1)
/*      */               break; 
/*  736 */             i5 = m;
/*  737 */             long l = arrayOfLong1[(i4 = j >>> 1) + i]; int i6;
/*  738 */             for (i6 = 0; i6 < i5; ) {
/*  739 */               int i7 = i6 + i5 >>> 1;
/*  740 */               if (l <= arrayOfLong1[i7 + k]) {
/*  741 */                 i5 = i7; continue;
/*      */               } 
/*  743 */               i6 = i7 + 1;
/*      */             } 
/*      */           } else {
/*      */             
/*  747 */             if (m <= i1)
/*      */               break; 
/*  749 */             i4 = j;
/*  750 */             long l = arrayOfLong1[(i5 = m >>> 1) + k]; int i6;
/*  751 */             for (i6 = 0; i6 < i4; ) {
/*  752 */               int i7 = i6 + i4 >>> 1;
/*  753 */               if (l <= arrayOfLong1[i7 + i]) {
/*  754 */                 i4 = i7; continue;
/*      */               } 
/*  756 */               i6 = i7 + 1;
/*      */             } 
/*      */           } 
/*  759 */           Merger merger = new Merger(this, arrayOfLong1, arrayOfLong2, i + i4, j - i4, k + i5, m - i5, n + i4 + i5, i1);
/*      */ 
/*      */           
/*  762 */           m = i5;
/*  763 */           j = i4;
/*  764 */           addToPendingCount(1);
/*  765 */           merger.fork();
/*      */         } 
/*      */         
/*  768 */         int i2 = i + j, i3 = k + m;
/*  769 */         while (i < i2 && k < i3) {
/*      */           long l1, l2, l3;
/*  771 */           if ((l2 = arrayOfLong1[i]) <= (l3 = arrayOfLong1[k])) {
/*  772 */             i++; l1 = l2;
/*      */           } else {
/*      */             
/*  775 */             k++; l1 = l3;
/*      */           } 
/*  777 */           arrayOfLong2[n++] = l1;
/*      */         } 
/*  779 */         if (k < i3) {
/*  780 */           System.arraycopy(arrayOfLong1, k, arrayOfLong2, n, i3 - k);
/*  781 */         } else if (i < i2) {
/*  782 */           System.arraycopy(arrayOfLong1, i, arrayOfLong2, n, i2 - i);
/*  783 */         }  tryComplete();
/*      */       } }
/*      */      }
/*      */   
/*      */   static final class FJFloat { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final float[] a;
/*      */       final float[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Sorter(CountedCompleter<?> param2CountedCompleter, float[] param2ArrayOffloat1, float[] param2ArrayOffloat2, int param2Int1, int param2Int2, int param2Int3, int param2Int4) {
/*  796 */         super(param2CountedCompleter);
/*  797 */         this.a = param2ArrayOffloat1; this.w = param2ArrayOffloat2; this.base = param2Int1; this.size = param2Int2;
/*  798 */         this.wbase = param2Int3; this.gran = param2Int4;
/*      */       } public final void compute() {
/*      */         ArraysParallelSortHelpers.EmptyCompleter emptyCompleter;
/*  801 */         Sorter sorter = this;
/*  802 */         float[] arrayOfFloat1 = this.a, arrayOfFloat2 = this.w;
/*  803 */         int i = this.base, j = this.size, k = this.wbase, m = this.gran;
/*  804 */         while (j > m) {
/*  805 */           int n = j >>> 1, i1 = n >>> 1, i2 = n + i1;
/*  806 */           ArraysParallelSortHelpers.Relay relay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJFloat.Merger(sorter, arrayOfFloat2, arrayOfFloat1, k, n, k + n, j - n, i, m));
/*      */           
/*  808 */           ArraysParallelSortHelpers.Relay relay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJFloat.Merger(relay1, arrayOfFloat1, arrayOfFloat2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  810 */           (new Sorter(relay2, arrayOfFloat1, arrayOfFloat2, i + i2, j - i2, k + i2, m)).fork();
/*  811 */           (new Sorter(relay2, arrayOfFloat1, arrayOfFloat2, i + n, i1, k + n, m)).fork();
/*  812 */           ArraysParallelSortHelpers.Relay relay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJFloat.Merger(relay1, arrayOfFloat1, arrayOfFloat2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  814 */           (new Sorter(relay3, arrayOfFloat1, arrayOfFloat2, i + i1, n - i1, k + i1, m)).fork();
/*  815 */           emptyCompleter = new ArraysParallelSortHelpers.EmptyCompleter(relay3);
/*  816 */           j = i1;
/*      */         } 
/*  818 */         DualPivotQuicksort.sort(arrayOfFloat1, i, i + j - 1, arrayOfFloat2, k, j);
/*  819 */         emptyCompleter.tryComplete();
/*      */       } }
/*      */     static final class Merger extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L; final float[] a; final float[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       final int rbase;
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Merger(CountedCompleter<?> param2CountedCompleter, float[] param2ArrayOffloat1, float[] param2ArrayOffloat2, int param2Int1, int param2Int2, int param2Int3, int param2Int4, int param2Int5, int param2Int6) {
/*  830 */         super(param2CountedCompleter);
/*  831 */         this.a = param2ArrayOffloat1; this.w = param2ArrayOffloat2;
/*  832 */         this.lbase = param2Int1; this.lsize = param2Int2;
/*  833 */         this.rbase = param2Int3; this.rsize = param2Int4;
/*  834 */         this.wbase = param2Int5; this.gran = param2Int6;
/*      */       }
/*      */       
/*      */       public final void compute() {
/*  838 */         float[] arrayOfFloat1 = this.a, arrayOfFloat2 = this.w;
/*  839 */         int i = this.lbase, j = this.lsize, k = this.rbase;
/*  840 */         int m = this.rsize, n = this.wbase, i1 = this.gran;
/*  841 */         if (arrayOfFloat1 == null || arrayOfFloat2 == null || i < 0 || k < 0 || n < 0)
/*  842 */           throw new IllegalStateException();  while (true) {
/*      */           int i4, i5;
/*  844 */           if (j >= m) {
/*  845 */             if (j <= i1)
/*      */               break; 
/*  847 */             i5 = m;
/*  848 */             float f = arrayOfFloat1[(i4 = j >>> 1) + i]; int i6;
/*  849 */             for (i6 = 0; i6 < i5; ) {
/*  850 */               int i7 = i6 + i5 >>> 1;
/*  851 */               if (f <= arrayOfFloat1[i7 + k]) {
/*  852 */                 i5 = i7; continue;
/*      */               } 
/*  854 */               i6 = i7 + 1;
/*      */             } 
/*      */           } else {
/*      */             
/*  858 */             if (m <= i1)
/*      */               break; 
/*  860 */             i4 = j;
/*  861 */             float f = arrayOfFloat1[(i5 = m >>> 1) + k]; int i6;
/*  862 */             for (i6 = 0; i6 < i4; ) {
/*  863 */               int i7 = i6 + i4 >>> 1;
/*  864 */               if (f <= arrayOfFloat1[i7 + i]) {
/*  865 */                 i4 = i7; continue;
/*      */               } 
/*  867 */               i6 = i7 + 1;
/*      */             } 
/*      */           } 
/*  870 */           Merger merger = new Merger(this, arrayOfFloat1, arrayOfFloat2, i + i4, j - i4, k + i5, m - i5, n + i4 + i5, i1);
/*      */ 
/*      */           
/*  873 */           m = i5;
/*  874 */           j = i4;
/*  875 */           addToPendingCount(1);
/*  876 */           merger.fork();
/*      */         } 
/*      */         
/*  879 */         int i2 = i + j, i3 = k + m;
/*  880 */         while (i < i2 && k < i3) {
/*      */           float f1, f2, f3;
/*  882 */           if ((f2 = arrayOfFloat1[i]) <= (f3 = arrayOfFloat1[k])) {
/*  883 */             i++; f1 = f2;
/*      */           } else {
/*      */             
/*  886 */             k++; f1 = f3;
/*      */           } 
/*  888 */           arrayOfFloat2[n++] = f1;
/*      */         } 
/*  890 */         if (k < i3) {
/*  891 */           System.arraycopy(arrayOfFloat1, k, arrayOfFloat2, n, i3 - k);
/*  892 */         } else if (i < i2) {
/*  893 */           System.arraycopy(arrayOfFloat1, i, arrayOfFloat2, n, i2 - i);
/*  894 */         }  tryComplete();
/*      */       } }
/*      */      }
/*      */   
/*      */   static final class FJDouble { static final class Sorter extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L;
/*      */       final double[] a;
/*      */       final double[] w;
/*      */       final int base;
/*      */       final int size;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Sorter(CountedCompleter<?> param2CountedCompleter, double[] param2ArrayOfdouble1, double[] param2ArrayOfdouble2, int param2Int1, int param2Int2, int param2Int3, int param2Int4) {
/*  907 */         super(param2CountedCompleter);
/*  908 */         this.a = param2ArrayOfdouble1; this.w = param2ArrayOfdouble2; this.base = param2Int1; this.size = param2Int2;
/*  909 */         this.wbase = param2Int3; this.gran = param2Int4;
/*      */       } public final void compute() {
/*      */         ArraysParallelSortHelpers.EmptyCompleter emptyCompleter;
/*  912 */         Sorter sorter = this;
/*  913 */         double[] arrayOfDouble1 = this.a, arrayOfDouble2 = this.w;
/*  914 */         int i = this.base, j = this.size, k = this.wbase, m = this.gran;
/*  915 */         while (j > m) {
/*  916 */           int n = j >>> 1, i1 = n >>> 1, i2 = n + i1;
/*  917 */           ArraysParallelSortHelpers.Relay relay1 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJDouble.Merger(sorter, arrayOfDouble2, arrayOfDouble1, k, n, k + n, j - n, i, m));
/*      */           
/*  919 */           ArraysParallelSortHelpers.Relay relay2 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJDouble.Merger(relay1, arrayOfDouble1, arrayOfDouble2, i + n, i1, i + i2, j - i2, k + n, m));
/*      */           
/*  921 */           (new Sorter(relay2, arrayOfDouble1, arrayOfDouble2, i + i2, j - i2, k + i2, m)).fork();
/*  922 */           (new Sorter(relay2, arrayOfDouble1, arrayOfDouble2, i + n, i1, k + n, m)).fork();
/*  923 */           ArraysParallelSortHelpers.Relay relay3 = new ArraysParallelSortHelpers.Relay(new ArraysParallelSortHelpers.FJDouble.Merger(relay1, arrayOfDouble1, arrayOfDouble2, i, i1, i + i1, n - i1, k, m));
/*      */           
/*  925 */           (new Sorter(relay3, arrayOfDouble1, arrayOfDouble2, i + i1, n - i1, k + i1, m)).fork();
/*  926 */           emptyCompleter = new ArraysParallelSortHelpers.EmptyCompleter(relay3);
/*  927 */           j = i1;
/*      */         } 
/*  929 */         DualPivotQuicksort.sort(arrayOfDouble1, i, i + j - 1, arrayOfDouble2, k, j);
/*  930 */         emptyCompleter.tryComplete();
/*      */       } }
/*      */     static final class Merger extends CountedCompleter<Void> { static final long serialVersionUID = 2446542900576103244L; final double[] a; final double[] w;
/*      */       final int lbase;
/*      */       final int lsize;
/*      */       final int rbase;
/*      */       final int rsize;
/*      */       final int wbase;
/*      */       final int gran;
/*      */       
/*      */       Merger(CountedCompleter<?> param2CountedCompleter, double[] param2ArrayOfdouble1, double[] param2ArrayOfdouble2, int param2Int1, int param2Int2, int param2Int3, int param2Int4, int param2Int5, int param2Int6) {
/*  941 */         super(param2CountedCompleter);
/*  942 */         this.a = param2ArrayOfdouble1; this.w = param2ArrayOfdouble2;
/*  943 */         this.lbase = param2Int1; this.lsize = param2Int2;
/*  944 */         this.rbase = param2Int3; this.rsize = param2Int4;
/*  945 */         this.wbase = param2Int5; this.gran = param2Int6;
/*      */       }
/*      */       
/*      */       public final void compute() {
/*  949 */         double[] arrayOfDouble1 = this.a, arrayOfDouble2 = this.w;
/*  950 */         int i = this.lbase, j = this.lsize, k = this.rbase;
/*  951 */         int m = this.rsize, n = this.wbase, i1 = this.gran;
/*  952 */         if (arrayOfDouble1 == null || arrayOfDouble2 == null || i < 0 || k < 0 || n < 0)
/*  953 */           throw new IllegalStateException();  while (true) {
/*      */           int i4, i5;
/*  955 */           if (j >= m) {
/*  956 */             if (j <= i1)
/*      */               break; 
/*  958 */             i5 = m;
/*  959 */             double d = arrayOfDouble1[(i4 = j >>> 1) + i]; int i6;
/*  960 */             for (i6 = 0; i6 < i5; ) {
/*  961 */               int i7 = i6 + i5 >>> 1;
/*  962 */               if (d <= arrayOfDouble1[i7 + k]) {
/*  963 */                 i5 = i7; continue;
/*      */               } 
/*  965 */               i6 = i7 + 1;
/*      */             } 
/*      */           } else {
/*      */             
/*  969 */             if (m <= i1)
/*      */               break; 
/*  971 */             i4 = j;
/*  972 */             double d = arrayOfDouble1[(i5 = m >>> 1) + k]; int i6;
/*  973 */             for (i6 = 0; i6 < i4; ) {
/*  974 */               int i7 = i6 + i4 >>> 1;
/*  975 */               if (d <= arrayOfDouble1[i7 + i]) {
/*  976 */                 i4 = i7; continue;
/*      */               } 
/*  978 */               i6 = i7 + 1;
/*      */             } 
/*      */           } 
/*  981 */           Merger merger = new Merger(this, arrayOfDouble1, arrayOfDouble2, i + i4, j - i4, k + i5, m - i5, n + i4 + i5, i1);
/*      */ 
/*      */           
/*  984 */           m = i5;
/*  985 */           j = i4;
/*  986 */           addToPendingCount(1);
/*  987 */           merger.fork();
/*      */         } 
/*      */         
/*  990 */         int i2 = i + j, i3 = k + m;
/*  991 */         while (i < i2 && k < i3) {
/*      */           double d1, d2, d3;
/*  993 */           if ((d2 = arrayOfDouble1[i]) <= (d3 = arrayOfDouble1[k])) {
/*  994 */             i++; d1 = d2;
/*      */           } else {
/*      */             
/*  997 */             k++; d1 = d3;
/*      */           } 
/*  999 */           arrayOfDouble2[n++] = d1;
/*      */         } 
/* 1001 */         if (k < i3) {
/* 1002 */           System.arraycopy(arrayOfDouble1, k, arrayOfDouble2, n, i3 - k);
/* 1003 */         } else if (i < i2) {
/* 1004 */           System.arraycopy(arrayOfDouble1, i, arrayOfDouble2, n, i2 - i);
/* 1005 */         }  tryComplete();
/*      */       } }
/*      */      }
/*      */ 
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/ArraysParallelSortHelpers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */