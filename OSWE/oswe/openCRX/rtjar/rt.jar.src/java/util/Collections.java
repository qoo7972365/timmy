/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.UnaryOperator;
/*      */ import java.util.stream.IntStream;
/*      */ import java.util.stream.Stream;
/*      */ import java.util.stream.StreamSupport;
/*      */ import sun.misc.SharedSecrets;
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
/*      */ public class Collections
/*      */ {
/*      */   private static final int BINARYSEARCH_THRESHOLD = 5000;
/*      */   private static final int REVERSE_THRESHOLD = 18;
/*      */   private static final int SHUFFLE_THRESHOLD = 5;
/*      */   private static final int FILL_THRESHOLD = 25;
/*      */   private static final int ROTATE_THRESHOLD = 100;
/*      */   private static final int COPY_THRESHOLD = 10;
/*      */   private static final int REPLACEALL_THRESHOLD = 11;
/*      */   private static final int INDEXOFSUBLIST_THRESHOLD = 35;
/*      */   private static Random r;
/*      */   
/*      */   public static <T extends Comparable<? super T>> void sort(List<T> paramList) {
/*  143 */     paramList.sort(null);
/*      */   }
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
/*      */   public static <T> void sort(List<T> paramList, Comparator<? super T> paramComparator) {
/*  177 */     paramList.sort(paramComparator);
/*      */   }
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
/*      */   public static <T> int binarySearch(List<? extends Comparable<? super T>> paramList, T paramT) {
/*  214 */     if (paramList instanceof RandomAccess || paramList.size() < 5000) {
/*  215 */       return indexedBinarySearch(paramList, paramT);
/*      */     }
/*  217 */     return iteratorBinarySearch(paramList, paramT);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T> int indexedBinarySearch(List<? extends Comparable<? super T>> paramList, T paramT) {
/*  222 */     int i = 0;
/*  223 */     int j = paramList.size() - 1;
/*      */     
/*  225 */     while (i <= j) {
/*  226 */       int k = i + j >>> 1;
/*  227 */       Comparable<T> comparable = (Comparable)paramList.get(k);
/*  228 */       int m = comparable.compareTo(paramT);
/*      */       
/*  230 */       if (m < 0) {
/*  231 */         i = k + 1; continue;
/*  232 */       }  if (m > 0) {
/*  233 */         j = k - 1; continue;
/*      */       } 
/*  235 */       return k;
/*      */     } 
/*  237 */     return -(i + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> int iteratorBinarySearch(List<? extends Comparable<? super T>> paramList, T paramT) {
/*  243 */     int i = 0;
/*  244 */     int j = paramList.size() - 1;
/*  245 */     ListIterator<? extends Comparable<? super T>> listIterator = paramList.listIterator();
/*      */     
/*  247 */     while (i <= j) {
/*  248 */       int k = i + j >>> 1;
/*  249 */       Comparable<T> comparable = get((ListIterator)listIterator, k);
/*  250 */       int m = comparable.compareTo(paramT);
/*      */       
/*  252 */       if (m < 0) {
/*  253 */         i = k + 1; continue;
/*  254 */       }  if (m > 0) {
/*  255 */         j = k - 1; continue;
/*      */       } 
/*  257 */       return k;
/*      */     } 
/*  259 */     return -(i + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> T get(ListIterator<? extends T> paramListIterator, int paramInt) {
/*  267 */     T t = null;
/*  268 */     int i = paramListIterator.nextIndex();
/*  269 */     if (i <= paramInt) {
/*      */       do {
/*  271 */         t = paramListIterator.next();
/*  272 */       } while (i++ < paramInt);
/*      */     } else {
/*      */       do {
/*  275 */         t = paramListIterator.previous();
/*  276 */       } while (--i > paramInt);
/*      */     } 
/*  278 */     return t;
/*      */   }
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
/*      */   public static <T> int binarySearch(List<? extends T> paramList, T paramT, Comparator<? super T> paramComparator) {
/*  318 */     if (paramComparator == null) {
/*  319 */       return binarySearch((List)paramList, paramT);
/*      */     }
/*  321 */     if (paramList instanceof RandomAccess || paramList.size() < 5000) {
/*  322 */       return indexedBinarySearch(paramList, paramT, paramComparator);
/*      */     }
/*  324 */     return iteratorBinarySearch(paramList, paramT, paramComparator);
/*      */   }
/*      */   
/*      */   private static <T> int indexedBinarySearch(List<? extends T> paramList, T paramT, Comparator<? super T> paramComparator) {
/*  328 */     int i = 0;
/*  329 */     int j = paramList.size() - 1;
/*      */     
/*  331 */     while (i <= j) {
/*  332 */       int k = i + j >>> 1;
/*  333 */       T t = paramList.get(k);
/*  334 */       int m = paramComparator.compare(t, paramT);
/*      */       
/*  336 */       if (m < 0) {
/*  337 */         i = k + 1; continue;
/*  338 */       }  if (m > 0) {
/*  339 */         j = k - 1; continue;
/*      */       } 
/*  341 */       return k;
/*      */     } 
/*  343 */     return -(i + 1);
/*      */   }
/*      */   
/*      */   private static <T> int iteratorBinarySearch(List<? extends T> paramList, T paramT, Comparator<? super T> paramComparator) {
/*  347 */     int i = 0;
/*  348 */     int j = paramList.size() - 1;
/*  349 */     ListIterator<? extends T> listIterator = paramList.listIterator();
/*      */     
/*  351 */     while (i <= j) {
/*  352 */       int k = i + j >>> 1;
/*  353 */       T t = (T)get((ListIterator)listIterator, k);
/*  354 */       int m = paramComparator.compare(t, paramT);
/*      */       
/*  356 */       if (m < 0) {
/*  357 */         i = k + 1; continue;
/*  358 */       }  if (m > 0) {
/*  359 */         j = k - 1; continue;
/*      */       } 
/*  361 */       return k;
/*      */     } 
/*  363 */     return -(i + 1);
/*      */   }
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
/*      */   public static void reverse(List<?> paramList) {
/*  377 */     int i = paramList.size();
/*  378 */     if (i < 18 || paramList instanceof RandomAccess) {
/*  379 */       byte b; int j; int k; for (b = 0, j = i >> 1, k = i - 1; b < j; b++, k--) {
/*  380 */         swap(paramList, b, k);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  385 */       ListIterator<?> listIterator1 = paramList.listIterator();
/*  386 */       ListIterator<?> listIterator2 = paramList.listIterator(i); byte b; int j;
/*  387 */       for (b = 0, j = paramList.size() >> 1; b < j; b++) {
/*  388 */         Object object = listIterator1.next();
/*  389 */         listIterator1.set(listIterator2.previous());
/*  390 */         listIterator2.set(object);
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static void shuffle(List<?> paramList) {
/*  424 */     Random random = r;
/*  425 */     if (random == null)
/*  426 */       r = random = new Random(); 
/*  427 */     shuffle(paramList, random);
/*      */   }
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
/*      */   public static void shuffle(List<?> paramList, Random paramRandom) {
/*  457 */     int i = paramList.size();
/*  458 */     if (i < 5 || paramList instanceof RandomAccess) {
/*  459 */       for (int j = i; j > 1; j--)
/*  460 */         swap(paramList, j - 1, paramRandom.nextInt(j)); 
/*      */     } else {
/*  462 */       Object[] arrayOfObject = paramList.toArray();
/*      */ 
/*      */       
/*  465 */       for (int j = i; j > 1; j--) {
/*  466 */         swap(arrayOfObject, j - 1, paramRandom.nextInt(j));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  472 */       ListIterator<?> listIterator = paramList.listIterator();
/*  473 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/*  474 */         listIterator.next();
/*  475 */         listIterator.set(arrayOfObject[b]);
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static void swap(List<?> paramList, int paramInt1, int paramInt2) {
/*  498 */     List<?> list = paramList;
/*  499 */     list.set(paramInt1, list.set(paramInt2, list.get(paramInt1)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void swap(Object[] paramArrayOfObject, int paramInt1, int paramInt2) {
/*  506 */     Object object = paramArrayOfObject[paramInt1];
/*  507 */     paramArrayOfObject[paramInt1] = paramArrayOfObject[paramInt2];
/*  508 */     paramArrayOfObject[paramInt2] = object;
/*      */   }
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
/*      */   public static <T> void fill(List<? super T> paramList, T paramT) {
/*  524 */     int i = paramList.size();
/*      */     
/*  526 */     if (i < 25 || paramList instanceof RandomAccess) {
/*  527 */       for (byte b = 0; b < i; b++)
/*  528 */         paramList.set(b, paramT); 
/*      */     } else {
/*  530 */       ListIterator<? super T> listIterator = paramList.listIterator();
/*  531 */       for (byte b = 0; b < i; b++) {
/*  532 */         listIterator.next();
/*  533 */         listIterator.set(paramT);
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static <T> void copy(List<? super T> paramList, List<? extends T> paramList1) {
/*  556 */     int i = paramList1.size();
/*  557 */     if (i > paramList.size()) {
/*  558 */       throw new IndexOutOfBoundsException("Source does not fit in dest");
/*      */     }
/*  560 */     if (i < 10 || (paramList1 instanceof RandomAccess && paramList instanceof RandomAccess)) {
/*      */       
/*  562 */       for (byte b = 0; b < i; b++)
/*  563 */         paramList.set(b, paramList1.get(b)); 
/*      */     } else {
/*  565 */       ListIterator<? super T> listIterator = paramList.listIterator();
/*  566 */       ListIterator<? extends T> listIterator1 = paramList1.listIterator();
/*  567 */       for (byte b = 0; b < i; b++) {
/*  568 */         listIterator.next();
/*  569 */         listIterator.set(listIterator1.next());
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static <T extends Comparable<? super T>> T min(Collection<? extends T> paramCollection) {
/*      */     T t1;
/*  597 */     Iterator<? extends T> iterator = paramCollection.iterator();
/*  598 */     T t2 = iterator.next();
/*      */     
/*  600 */     while (iterator.hasNext()) {
/*  601 */       T t = iterator.next();
/*  602 */       if (((Comparable<T>)t).compareTo(t2) < 0)
/*  603 */         t1 = t; 
/*      */     } 
/*  605 */     return t1;
/*      */   }
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
/*      */   public static <T> T min(Collection<? extends T> paramCollection, Comparator<? super T> paramComparator) {
/*      */     T t1;
/*  633 */     if (paramComparator == null) {
/*  634 */       return (T)min((Collection)paramCollection);
/*      */     }
/*  636 */     Iterator<? extends T> iterator = paramCollection.iterator();
/*  637 */     T t2 = iterator.next();
/*      */     
/*  639 */     while (iterator.hasNext()) {
/*  640 */       T t = iterator.next();
/*  641 */       if (paramComparator.compare(t, t2) < 0)
/*  642 */         t1 = t; 
/*      */     } 
/*  644 */     return t1;
/*      */   }
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
/*      */   public static <T extends Comparable<? super T>> T max(Collection<? extends T> paramCollection) {
/*      */     T t1;
/*  670 */     Iterator<? extends T> iterator = paramCollection.iterator();
/*  671 */     T t2 = iterator.next();
/*      */     
/*  673 */     while (iterator.hasNext()) {
/*  674 */       T t = iterator.next();
/*  675 */       if (((Comparable<T>)t).compareTo(t2) > 0)
/*  676 */         t1 = t; 
/*      */     } 
/*  678 */     return t1;
/*      */   }
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
/*      */   public static <T> T max(Collection<? extends T> paramCollection, Comparator<? super T> paramComparator) {
/*      */     T t1;
/*  706 */     if (paramComparator == null) {
/*  707 */       return (T)max((Collection)paramCollection);
/*      */     }
/*  709 */     Iterator<? extends T> iterator = paramCollection.iterator();
/*  710 */     T t2 = iterator.next();
/*      */     
/*  712 */     while (iterator.hasNext()) {
/*  713 */       T t = iterator.next();
/*  714 */       if (paramComparator.compare(t, t2) > 0)
/*  715 */         t1 = t; 
/*      */     } 
/*  717 */     return t1;
/*      */   }
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
/*      */   public static void rotate(List<?> paramList, int paramInt) {
/*  776 */     if (paramList instanceof RandomAccess || paramList.size() < 100) {
/*  777 */       rotate1(paramList, paramInt);
/*      */     } else {
/*  779 */       rotate2(paramList, paramInt);
/*      */     } 
/*      */   }
/*      */   private static <T> void rotate1(List<T> paramList, int paramInt) {
/*  783 */     int i = paramList.size();
/*  784 */     if (i == 0)
/*      */       return; 
/*  786 */     paramInt %= i;
/*  787 */     if (paramInt < 0)
/*  788 */       paramInt += i; 
/*  789 */     if (paramInt == 0)
/*      */       return;  byte b;
/*      */     int j;
/*  792 */     for (b = 0, j = 0; j != i; ) {
/*  793 */       T t = paramList.get(b);
/*  794 */       int k = b;
/*      */       while (true) {
/*  796 */         k += paramInt;
/*  797 */         if (k >= i)
/*  798 */           k -= i; 
/*  799 */         t = paramList.set(k, t);
/*  800 */         j++;
/*  801 */         if (k == b)
/*      */           b++; 
/*      */       } 
/*      */     } 
/*      */   } private static void rotate2(List<?> paramList, int paramInt) {
/*  806 */     int i = paramList.size();
/*  807 */     if (i == 0)
/*      */       return; 
/*  809 */     int j = -paramInt % i;
/*  810 */     if (j < 0)
/*  811 */       j += i; 
/*  812 */     if (j == 0) {
/*      */       return;
/*      */     }
/*  815 */     reverse(paramList.subList(0, j));
/*  816 */     reverse(paramList.subList(j, i));
/*  817 */     reverse(paramList);
/*      */   }
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
/*      */   public static <T> boolean replaceAll(List<T> paramList, T paramT1, T paramT2) {
/*  840 */     boolean bool = false;
/*  841 */     int i = paramList.size();
/*  842 */     if (i < 11 || paramList instanceof RandomAccess) {
/*  843 */       if (paramT1 == null) {
/*  844 */         for (byte b = 0; b < i; b++) {
/*  845 */           if (paramList.get(b) == null) {
/*  846 */             paramList.set(b, paramT2);
/*  847 */             bool = true;
/*      */           } 
/*      */         } 
/*      */       } else {
/*  851 */         for (byte b = 0; b < i; b++) {
/*  852 */           if (paramT1.equals(paramList.get(b))) {
/*  853 */             paramList.set(b, paramT2);
/*  854 */             bool = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*  859 */       ListIterator<T> listIterator = paramList.listIterator();
/*  860 */       if (paramT1 == null) {
/*  861 */         for (byte b = 0; b < i; b++) {
/*  862 */           if (listIterator.next() == null) {
/*  863 */             listIterator.set(paramT2);
/*  864 */             bool = true;
/*      */           } 
/*      */         } 
/*      */       } else {
/*  868 */         for (byte b = 0; b < i; b++) {
/*  869 */           if (paramT1.equals(listIterator.next())) {
/*  870 */             listIterator.set(paramT2);
/*  871 */             bool = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  876 */     return bool;
/*      */   }
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
/*      */   public static int indexOfSubList(List<?> paramList1, List<?> paramList2) {
/*  900 */     int i = paramList1.size();
/*  901 */     int j = paramList2.size();
/*  902 */     int k = i - j;
/*      */     
/*  904 */     if (i < 35 || (paramList1 instanceof RandomAccess && paramList2 instanceof RandomAccess))
/*      */     
/*      */     { 
/*  907 */       for (byte b = 0; b <= k; b++) {
/*  908 */         byte b1 = 0, b2 = b; while (true) { if (b1 < j) {
/*  909 */             if (!eq(paramList2.get(b1), paramList1.get(b2)))
/*      */               break;  b1++; b2++; continue;
/*  911 */           }  return b; }
/*      */       
/*      */       }  }
/*  914 */     else { ListIterator<?> listIterator = paramList1.listIterator();
/*      */       
/*  916 */       for (byte b = 0; b <= k; b++) {
/*  917 */         ListIterator<?> listIterator1 = paramList2.listIterator();
/*  918 */         byte b1 = 0; while (true) { if (b1 < j) {
/*  919 */             if (!eq(listIterator1.next(), listIterator.next())) {
/*      */               
/*  921 */               for (byte b2 = 0; b2 < b1; b2++)
/*  922 */                 listIterator.previous();  break;
/*      */             }  b1++;
/*      */             continue;
/*      */           } 
/*  926 */           return b; }
/*      */       
/*      */       }  }
/*  929 */      return -1;
/*      */   }
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
/*      */   public static int lastIndexOfSubList(List<?> paramList1, List<?> paramList2) {
/*  953 */     int i = paramList1.size();
/*  954 */     int j = paramList2.size();
/*  955 */     int k = i - j;
/*      */     
/*  957 */     if (i < 35 || paramList1 instanceof RandomAccess)
/*      */     
/*      */     { 
/*  960 */       for (int m = k; m >= 0; m--) {
/*  961 */         byte b = 0; int n = m; while (true) { if (b < j) {
/*  962 */             if (!eq(paramList2.get(b), paramList1.get(n)))
/*      */               break;  b++; n++; continue;
/*  964 */           }  return m; }
/*      */       
/*      */       }  }
/*  967 */     else { if (k < 0)
/*  968 */         return -1; 
/*  969 */       ListIterator<?> listIterator = paramList1.listIterator(k);
/*      */       
/*  971 */       for (int m = k; m >= 0; m--) {
/*  972 */         ListIterator<?> listIterator1 = paramList2.listIterator();
/*  973 */         byte b = 0; while (true) { if (b < j) {
/*  974 */             if (!eq(listIterator1.next(), listIterator.next())) {
/*  975 */               if (m != 0)
/*      */               {
/*  977 */                 for (byte b1 = 0; b1 <= b + 1; b1++)
/*  978 */                   listIterator.previous();  }  break;
/*      */             } 
/*      */             b++;
/*      */             continue;
/*      */           } 
/*  983 */           return m; }
/*      */       
/*      */       }  }
/*  986 */      return -1;
/*      */   }
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
/*      */   public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> paramCollection) {
/* 1015 */     return new UnmodifiableCollection<>(paramCollection);
/*      */   }
/*      */ 
/*      */   
/*      */   static class UnmodifiableCollection<E>
/*      */     implements Collection<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1820017752578914078L;
/*      */     
/*      */     final Collection<? extends E> c;
/*      */     
/*      */     UnmodifiableCollection(Collection<? extends E> param1Collection) {
/* 1027 */       if (param1Collection == null)
/* 1028 */         throw new NullPointerException(); 
/* 1029 */       this.c = param1Collection;
/*      */     }
/*      */     
/* 1032 */     public int size() { return this.c.size(); }
/* 1033 */     public boolean isEmpty() { return this.c.isEmpty(); }
/* 1034 */     public boolean contains(Object param1Object) { return this.c.contains(param1Object); }
/* 1035 */     public Object[] toArray() { return this.c.toArray(); }
/* 1036 */     public <T> T[] toArray(T[] param1ArrayOfT) { return this.c.toArray(param1ArrayOfT); } public String toString() {
/* 1037 */       return this.c.toString();
/*      */     }
/*      */     public Iterator<E> iterator() {
/* 1040 */       return new Iterator<E>() {
/* 1041 */           private final Iterator<? extends E> i = Collections.UnmodifiableCollection.this.c.iterator();
/*      */           
/* 1043 */           public boolean hasNext() { return this.i.hasNext(); } public E next() {
/* 1044 */             return this.i.next();
/*      */           } public void remove() {
/* 1046 */             throw new UnsupportedOperationException();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEachRemaining(Consumer<? super E> param2Consumer) {
/* 1051 */             this.i.forEachRemaining(param2Consumer);
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     public boolean add(E param1E) {
/* 1057 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public boolean remove(Object param1Object) {
/* 1060 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean containsAll(Collection<?> param1Collection) {
/* 1064 */       return this.c.containsAll(param1Collection);
/*      */     }
/*      */     public boolean addAll(Collection<? extends E> param1Collection) {
/* 1067 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public boolean removeAll(Collection<?> param1Collection) {
/* 1070 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public boolean retainAll(Collection<?> param1Collection) {
/* 1073 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void clear() {
/* 1076 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 1082 */       this.c.forEach(param1Consumer);
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 1086 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 1091 */       return (Spliterator)this.c.spliterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public Stream<E> stream() {
/* 1096 */       return (Stream)this.c.stream();
/*      */     }
/*      */ 
/*      */     
/*      */     public Stream<E> parallelStream() {
/* 1101 */       return (Stream)this.c.parallelStream();
/*      */     }
/*      */   }
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
/*      */   public static <T> Set<T> unmodifiableSet(Set<? extends T> paramSet) {
/* 1120 */     return new UnmodifiableSet<>(paramSet);
/*      */   }
/*      */   
/*      */   static class UnmodifiableSet<E>
/*      */     extends UnmodifiableCollection<E>
/*      */     implements Set<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -9215047833775013803L;
/*      */     
/*      */     UnmodifiableSet(Set<? extends E> param1Set) {
/* 1130 */       super(param1Set);
/* 1131 */     } public boolean equals(Object param1Object) { return (param1Object == this || this.c.equals(param1Object)); } public int hashCode() {
/* 1132 */       return this.c.hashCode();
/*      */     }
/*      */   }
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
/*      */   public static <T> SortedSet<T> unmodifiableSortedSet(SortedSet<T> paramSortedSet) {
/* 1153 */     return new UnmodifiableSortedSet<>(paramSortedSet);
/*      */   }
/*      */ 
/*      */   
/*      */   static class UnmodifiableSortedSet<E>
/*      */     extends UnmodifiableSet<E>
/*      */     implements SortedSet<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -4929149591599911165L;
/*      */     private final SortedSet<E> ss;
/*      */     
/*      */     UnmodifiableSortedSet(SortedSet<E> param1SortedSet) {
/* 1165 */       super(param1SortedSet); this.ss = param1SortedSet;
/*      */     } public Comparator<? super E> comparator() {
/* 1167 */       return this.ss.comparator();
/*      */     }
/*      */     public SortedSet<E> subSet(E param1E1, E param1E2) {
/* 1170 */       return new UnmodifiableSortedSet(this.ss.subSet(param1E1, param1E2));
/*      */     }
/*      */     public SortedSet<E> headSet(E param1E) {
/* 1173 */       return new UnmodifiableSortedSet(this.ss.headSet(param1E));
/*      */     }
/*      */     public SortedSet<E> tailSet(E param1E) {
/* 1176 */       return new UnmodifiableSortedSet(this.ss.tailSet(param1E));
/*      */     }
/*      */     
/* 1179 */     public E first() { return this.ss.first(); } public E last() {
/* 1180 */       return this.ss.last();
/*      */     }
/*      */   }
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
/*      */   public static <T> NavigableSet<T> unmodifiableNavigableSet(NavigableSet<T> paramNavigableSet) {
/* 1202 */     return new UnmodifiableNavigableSet<>(paramNavigableSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class UnmodifiableNavigableSet<E>
/*      */     extends UnmodifiableSortedSet<E>
/*      */     implements NavigableSet<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -6027448201786391929L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static class EmptyNavigableSet<E>
/*      */       extends UnmodifiableNavigableSet<E>
/*      */       implements Serializable
/*      */     {
/*      */       private static final long serialVersionUID = -6291252904449939134L;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public EmptyNavigableSet() {
/* 1228 */         super(new TreeSet<>());
/*      */       }
/*      */       private Object readResolve() {
/* 1231 */         return Collections.UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
/*      */       }
/*      */     }
/*      */     
/* 1235 */     private static final NavigableSet<?> EMPTY_NAVIGABLE_SET = new EmptyNavigableSet();
/*      */ 
/*      */     
/*      */     private final NavigableSet<E> ns;
/*      */ 
/*      */ 
/*      */     
/*      */     UnmodifiableNavigableSet(NavigableSet<E> param1NavigableSet) {
/* 1243 */       super(param1NavigableSet); this.ns = param1NavigableSet;
/*      */     }
/* 1245 */     public E lower(E param1E) { return this.ns.lower(param1E); }
/* 1246 */     public E floor(E param1E) { return this.ns.floor(param1E); }
/* 1247 */     public E ceiling(E param1E) { return this.ns.ceiling(param1E); }
/* 1248 */     public E higher(E param1E) { return this.ns.higher(param1E); }
/* 1249 */     public E pollFirst() { throw new UnsupportedOperationException(); } public E pollLast() {
/* 1250 */       throw new UnsupportedOperationException();
/*      */     } public NavigableSet<E> descendingSet() {
/* 1252 */       return new UnmodifiableNavigableSet(this.ns.descendingSet());
/*      */     } public Iterator<E> descendingIterator() {
/* 1254 */       return descendingSet().iterator();
/*      */     }
/*      */     public NavigableSet<E> subSet(E param1E1, boolean param1Boolean1, E param1E2, boolean param1Boolean2) {
/* 1257 */       return new UnmodifiableNavigableSet(this.ns
/* 1258 */           .subSet(param1E1, param1Boolean1, param1E2, param1Boolean2));
/*      */     }
/*      */     
/*      */     public NavigableSet<E> headSet(E param1E, boolean param1Boolean) {
/* 1262 */       return new UnmodifiableNavigableSet(this.ns
/* 1263 */           .headSet(param1E, param1Boolean));
/*      */     }
/*      */     
/*      */     public NavigableSet<E> tailSet(E param1E, boolean param1Boolean) {
/* 1267 */       return new UnmodifiableNavigableSet(this.ns
/* 1268 */           .tailSet(param1E, param1Boolean));
/*      */     }
/*      */   }
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
/*      */   public static <T> List<T> unmodifiableList(List<? extends T> paramList) {
/* 1289 */     return (paramList instanceof RandomAccess) ? new UnmodifiableRandomAccessList<>(paramList) : new UnmodifiableList<>(paramList);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class UnmodifiableList<E>
/*      */     extends UnmodifiableCollection<E>
/*      */     implements List<E>
/*      */   {
/*      */     private static final long serialVersionUID = -283967356065247728L;
/*      */     
/*      */     final List<? extends E> list;
/*      */ 
/*      */     
/*      */     UnmodifiableList(List<? extends E> param1List) {
/* 1304 */       super(param1List);
/* 1305 */       this.list = param1List;
/*      */     }
/*      */     
/* 1308 */     public boolean equals(Object param1Object) { return (param1Object == this || this.list.equals(param1Object)); } public int hashCode() {
/* 1309 */       return this.list.hashCode();
/*      */     } public E get(int param1Int) {
/* 1311 */       return this.list.get(param1Int);
/*      */     } public E set(int param1Int, E param1E) {
/* 1313 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(int param1Int, E param1E) {
/* 1316 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public E remove(int param1Int) {
/* 1319 */       throw new UnsupportedOperationException();
/*      */     }
/* 1321 */     public int indexOf(Object param1Object) { return this.list.indexOf(param1Object); } public int lastIndexOf(Object param1Object) {
/* 1322 */       return this.list.lastIndexOf(param1Object);
/*      */     } public boolean addAll(int param1Int, Collection<? extends E> param1Collection) {
/* 1324 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(UnaryOperator<E> param1UnaryOperator) {
/* 1329 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void sort(Comparator<? super E> param1Comparator) {
/* 1333 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public ListIterator<E> listIterator() {
/* 1336 */       return listIterator(0);
/*      */     }
/*      */     public ListIterator<E> listIterator(final int index) {
/* 1339 */       return new ListIterator<E>() {
/* 1340 */           private final ListIterator<? extends E> i = Collections.UnmodifiableList.this.list
/* 1341 */             .listIterator(index);
/*      */           
/* 1343 */           public boolean hasNext() { return this.i.hasNext(); }
/* 1344 */           public E next() { return this.i.next(); }
/* 1345 */           public boolean hasPrevious() { return this.i.hasPrevious(); }
/* 1346 */           public E previous() { return this.i.previous(); }
/* 1347 */           public int nextIndex() { return this.i.nextIndex(); } public int previousIndex() {
/* 1348 */             return this.i.previousIndex();
/*      */           }
/*      */           public void remove() {
/* 1351 */             throw new UnsupportedOperationException();
/*      */           }
/*      */           public void set(E param2E) {
/* 1354 */             throw new UnsupportedOperationException();
/*      */           }
/*      */           public void add(E param2E) {
/* 1357 */             throw new UnsupportedOperationException();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEachRemaining(Consumer<? super E> param2Consumer) {
/* 1362 */             this.i.forEachRemaining(param2Consumer);
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 1368 */       return new UnmodifiableList(this.list.subList(param1Int1, param1Int2));
/*      */     }
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
/*      */     private Object readResolve() {
/* 1384 */       return (this.list instanceof RandomAccess) ? new Collections.UnmodifiableRandomAccessList<>(this.list) : this;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class UnmodifiableRandomAccessList<E>
/*      */     extends UnmodifiableList<E>
/*      */     implements RandomAccess
/*      */   {
/*      */     private static final long serialVersionUID = -2542308836966382001L;
/*      */ 
/*      */     
/*      */     UnmodifiableRandomAccessList(List<? extends E> param1List) {
/* 1397 */       super(param1List);
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 1401 */       return new UnmodifiableRandomAccessList(this.list
/* 1402 */           .subList(param1Int1, param1Int2));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object writeReplace() {
/* 1414 */       return new Collections.UnmodifiableList<>(this.list);
/*      */     }
/*      */   }
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
/*      */   public static <K, V> Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> paramMap) {
/* 1435 */     return new UnmodifiableMap<>(paramMap);
/*      */   }
/*      */   
/*      */   private static class UnmodifiableMap<K, V>
/*      */     implements Map<K, V>, Serializable {
/*      */     private static final long serialVersionUID = -1034234728574286014L;
/*      */     private final Map<? extends K, ? extends V> m;
/*      */     private transient Set<K> keySet;
/*      */     private transient Set<Map.Entry<K, V>> entrySet;
/*      */     private transient Collection<V> values;
/*      */     
/*      */     UnmodifiableMap(Map<? extends K, ? extends V> param1Map) {
/* 1447 */       if (param1Map == null)
/* 1448 */         throw new NullPointerException(); 
/* 1449 */       this.m = param1Map;
/*      */     }
/*      */     
/* 1452 */     public int size() { return this.m.size(); }
/* 1453 */     public boolean isEmpty() { return this.m.isEmpty(); }
/* 1454 */     public boolean containsKey(Object param1Object) { return this.m.containsKey(param1Object); }
/* 1455 */     public boolean containsValue(Object param1Object) { return this.m.containsValue(param1Object); } public V get(Object param1Object) {
/* 1456 */       return this.m.get(param1Object);
/*      */     }
/*      */     public V put(K param1K, V param1V) {
/* 1459 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public V remove(Object param1Object) {
/* 1462 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void putAll(Map<? extends K, ? extends V> param1Map) {
/* 1465 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void clear() {
/* 1468 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 1476 */       if (this.keySet == null)
/* 1477 */         this.keySet = Collections.unmodifiableSet(this.m.keySet()); 
/* 1478 */       return this.keySet;
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 1482 */       if (this.entrySet == null)
/* 1483 */         this.entrySet = new UnmodifiableEntrySet<>(this.m.entrySet()); 
/* 1484 */       return this.entrySet;
/*      */     }
/*      */     
/*      */     public Collection<V> values() {
/* 1488 */       if (this.values == null)
/* 1489 */         this.values = Collections.unmodifiableCollection(this.m.values()); 
/* 1490 */       return this.values;
/*      */     }
/*      */     
/* 1493 */     public boolean equals(Object param1Object) { return (param1Object == this || this.m.equals(param1Object)); }
/* 1494 */     public int hashCode() { return this.m.hashCode(); } public String toString() {
/* 1495 */       return this.m.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V getOrDefault(Object param1Object, V param1V) {
/* 1502 */       return this.m.getOrDefault(param1Object, param1V);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(BiConsumer<? super K, ? super V> param1BiConsumer) {
/* 1507 */       this.m.forEach(param1BiConsumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 1512 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public V putIfAbsent(K param1K, V param1V) {
/* 1517 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object param1Object1, Object param1Object2) {
/* 1522 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean replace(K param1K, V param1V1, V param1V2) {
/* 1527 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public V replace(K param1K, V param1V) {
/* 1532 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public V computeIfAbsent(K param1K, Function<? super K, ? extends V> param1Function) {
/* 1537 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfPresent(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 1543 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V compute(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 1549 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V merge(K param1K, V param1V, BiFunction<? super V, ? super V, ? extends V> param1BiFunction) {
/* 1555 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static class UnmodifiableEntrySet<K, V>
/*      */       extends Collections.UnmodifiableSet<Map.Entry<K, V>>
/*      */     {
/*      */       private static final long serialVersionUID = 7854390611657943733L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       UnmodifiableEntrySet(Set<? extends Map.Entry<? extends K, ? extends V>> param2Set) {
/* 1573 */         super(param2Set);
/*      */       }
/*      */       
/*      */       static <K, V> Consumer<Map.Entry<K, V>> entryConsumer(Consumer<? super Map.Entry<K, V>> param2Consumer) {
/* 1577 */         return param2Entry -> param2Consumer.accept(new UnmodifiableEntry<>(param2Entry));
/*      */       }
/*      */       
/*      */       public void forEach(Consumer<? super Map.Entry<K, V>> param2Consumer) {
/* 1581 */         Objects.requireNonNull(param2Consumer);
/* 1582 */         this.c.forEach(entryConsumer(param2Consumer));
/*      */       }
/*      */       
/*      */       static final class UnmodifiableEntrySetSpliterator<K, V>
/*      */         implements Spliterator<Map.Entry<K, V>> {
/*      */         final Spliterator<Map.Entry<K, V>> s;
/*      */         
/*      */         UnmodifiableEntrySetSpliterator(Spliterator<Map.Entry<K, V>> param3Spliterator) {
/* 1590 */           this.s = param3Spliterator;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> param3Consumer) {
/* 1595 */           Objects.requireNonNull(param3Consumer);
/* 1596 */           return this.s.tryAdvance(Collections.UnmodifiableMap.UnmodifiableEntrySet.entryConsumer(param3Consumer));
/*      */         }
/*      */ 
/*      */         
/*      */         public void forEachRemaining(Consumer<? super Map.Entry<K, V>> param3Consumer) {
/* 1601 */           Objects.requireNonNull(param3Consumer);
/* 1602 */           this.s.forEachRemaining(Collections.UnmodifiableMap.UnmodifiableEntrySet.entryConsumer(param3Consumer));
/*      */         }
/*      */ 
/*      */         
/*      */         public Spliterator<Map.Entry<K, V>> trySplit() {
/* 1607 */           Spliterator<Map.Entry<K, V>> spliterator = this.s.trySplit();
/* 1608 */           return (spliterator == null) ? null : new UnmodifiableEntrySetSpliterator(spliterator);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public long estimateSize() {
/* 1615 */           return this.s.estimateSize();
/*      */         }
/*      */ 
/*      */         
/*      */         public long getExactSizeIfKnown() {
/* 1620 */           return this.s.getExactSizeIfKnown();
/*      */         }
/*      */ 
/*      */         
/*      */         public int characteristics() {
/* 1625 */           return this.s.characteristics();
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasCharacteristics(int param3Int) {
/* 1630 */           return this.s.hasCharacteristics(param3Int);
/*      */         }
/*      */ 
/*      */         
/*      */         public Comparator<? super Map.Entry<K, V>> getComparator() {
/* 1635 */           return this.s.getComparator();
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       public Spliterator<Map.Entry<K, V>> spliterator() {
/* 1641 */         return new UnmodifiableEntrySetSpliterator<>((Spliterator)this.c
/* 1642 */             .spliterator());
/*      */       }
/*      */ 
/*      */       
/*      */       public Stream<Map.Entry<K, V>> stream() {
/* 1647 */         return StreamSupport.stream(spliterator(), false);
/*      */       }
/*      */ 
/*      */       
/*      */       public Stream<Map.Entry<K, V>> parallelStream() {
/* 1652 */         return StreamSupport.stream(spliterator(), true);
/*      */       }
/*      */       
/*      */       public Iterator<Map.Entry<K, V>> iterator() {
/* 1656 */         return new Iterator<Map.Entry<K, V>>() {
/* 1657 */             private final Iterator<? extends Map.Entry<? extends K, ? extends V>> i = Collections.UnmodifiableMap.UnmodifiableEntrySet.this.c.iterator();
/*      */             
/*      */             public boolean hasNext() {
/* 1660 */               return this.i.hasNext();
/*      */             }
/*      */             public Map.Entry<K, V> next() {
/* 1663 */               return new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(this.i.next());
/*      */             }
/*      */             public void remove() {
/* 1666 */               throw new UnsupportedOperationException();
/*      */             }
/*      */           };
/*      */       }
/*      */ 
/*      */       
/*      */       public Object[] toArray() {
/* 1673 */         Object[] arrayOfObject = this.c.toArray();
/* 1674 */         for (byte b = 0; b < arrayOfObject.length; b++)
/* 1675 */           arrayOfObject[b] = new UnmodifiableEntry<>((Map.Entry<?, ?>)arrayOfObject[b]); 
/* 1676 */         return arrayOfObject;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public <T> T[] toArray(T[] param2ArrayOfT) {
/* 1684 */         Object[] arrayOfObject = this.c.toArray((param2ArrayOfT.length == 0) ? (Object[])param2ArrayOfT : Arrays.<Object>copyOf((Object[])param2ArrayOfT, 0));
/*      */         
/* 1686 */         for (byte b = 0; b < arrayOfObject.length; b++) {
/* 1687 */           arrayOfObject[b] = new UnmodifiableEntry<>((Map.Entry<?, ?>)arrayOfObject[b]);
/*      */         }
/* 1689 */         if (arrayOfObject.length > param2ArrayOfT.length) {
/* 1690 */           return (T[])arrayOfObject;
/*      */         }
/* 1692 */         System.arraycopy(arrayOfObject, 0, param2ArrayOfT, 0, arrayOfObject.length);
/* 1693 */         if (param2ArrayOfT.length > arrayOfObject.length)
/* 1694 */           param2ArrayOfT[arrayOfObject.length] = null; 
/* 1695 */         return param2ArrayOfT;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean contains(Object param2Object) {
/* 1705 */         if (!(param2Object instanceof Map.Entry))
/* 1706 */           return false; 
/* 1707 */         return this.c.contains(new UnmodifiableEntry<>((Map.Entry<?, ?>)param2Object));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean containsAll(Collection<?> param2Collection) {
/* 1717 */         for (Object object : param2Collection) {
/* 1718 */           if (!contains(object))
/* 1719 */             return false; 
/*      */         } 
/* 1721 */         return true;
/*      */       }
/*      */       public boolean equals(Object param2Object) {
/* 1724 */         if (param2Object == this) {
/* 1725 */           return true;
/*      */         }
/* 1727 */         if (!(param2Object instanceof Set))
/* 1728 */           return false; 
/* 1729 */         Set<?> set = (Set)param2Object;
/* 1730 */         if (set.size() != this.c.size())
/* 1731 */           return false; 
/* 1732 */         return containsAll(set);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private static class UnmodifiableEntry<K, V>
/*      */         implements Map.Entry<K, V>
/*      */       {
/*      */         private Map.Entry<? extends K, ? extends V> e;
/*      */ 
/*      */ 
/*      */         
/*      */         UnmodifiableEntry(Map.Entry<? extends K, ? extends V> param3Entry) {
/* 1746 */           this.e = Objects.<Map.Entry<? extends K, ? extends V>>requireNonNull(param3Entry);
/*      */         }
/* 1748 */         public K getKey() { return this.e.getKey(); } public V getValue() {
/* 1749 */           return this.e.getValue();
/*      */         } public V setValue(V param3V) {
/* 1751 */           throw new UnsupportedOperationException();
/*      */         } public int hashCode() {
/* 1753 */           return this.e.hashCode();
/*      */         } public boolean equals(Object param3Object) {
/* 1755 */           if (this == param3Object)
/* 1756 */             return true; 
/* 1757 */           if (!(param3Object instanceof Map.Entry))
/* 1758 */             return false; 
/* 1759 */           Map.Entry entry = (Map.Entry)param3Object;
/* 1760 */           return (Collections.eq(this.e.getKey(), entry.getKey()) && 
/* 1761 */             Collections.eq(this.e.getValue(), entry.getValue()));
/*      */         } public String toString() {
/* 1763 */           return this.e.toString();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
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
/*      */   public static <K, V> SortedMap<K, V> unmodifiableSortedMap(SortedMap<K, ? extends V> paramSortedMap) {
/* 1787 */     return new UnmodifiableSortedMap<>(paramSortedMap);
/*      */   }
/*      */ 
/*      */   
/*      */   static class UnmodifiableSortedMap<K, V>
/*      */     extends UnmodifiableMap<K, V>
/*      */     implements SortedMap<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -8806743815996713206L;
/*      */     
/*      */     private final SortedMap<K, ? extends V> sm;
/*      */     
/*      */     UnmodifiableSortedMap(SortedMap<K, ? extends V> param1SortedMap) {
/* 1800 */       super(param1SortedMap); this.sm = param1SortedMap; } public Comparator<? super K> comparator() {
/* 1801 */       return this.sm.comparator();
/*      */     } public SortedMap<K, V> subMap(K param1K1, K param1K2) {
/* 1803 */       return new UnmodifiableSortedMap(this.sm.subMap(param1K1, param1K2));
/*      */     } public SortedMap<K, V> headMap(K param1K) {
/* 1805 */       return new UnmodifiableSortedMap(this.sm.headMap(param1K));
/*      */     }
/* 1807 */     public SortedMap<K, V> tailMap(K param1K) { return new UnmodifiableSortedMap(this.sm.tailMap(param1K)); }
/* 1808 */     public K firstKey() { return this.sm.firstKey(); } public K lastKey() {
/* 1809 */       return this.sm.lastKey();
/*      */     }
/*      */   }
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
/*      */   public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> paramNavigableMap) {
/* 1832 */     return new UnmodifiableNavigableMap<>(paramNavigableMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class UnmodifiableNavigableMap<K, V>
/*      */     extends UnmodifiableSortedMap<K, V>
/*      */     implements NavigableMap<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -4858195264774772197L;
/*      */ 
/*      */ 
/*      */     
/*      */     private static class EmptyNavigableMap<K, V>
/*      */       extends UnmodifiableNavigableMap<K, V>
/*      */       implements Serializable
/*      */     {
/*      */       private static final long serialVersionUID = -2239321462712562324L;
/*      */ 
/*      */ 
/*      */       
/*      */       EmptyNavigableMap() {
/* 1855 */         super(new TreeMap<>());
/*      */       }
/*      */       
/*      */       public NavigableSet<K> navigableKeySet() {
/* 1859 */         return Collections.emptyNavigableSet();
/*      */       } private Object readResolve() {
/* 1861 */         return Collections.UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1867 */     private static final EmptyNavigableMap<?, ?> EMPTY_NAVIGABLE_MAP = new EmptyNavigableMap<>();
/*      */ 
/*      */ 
/*      */     
/*      */     private final NavigableMap<K, ? extends V> nm;
/*      */ 
/*      */ 
/*      */     
/*      */     UnmodifiableNavigableMap(NavigableMap<K, ? extends V> param1NavigableMap) {
/* 1876 */       super(param1NavigableMap); this.nm = param1NavigableMap;
/*      */     }
/* 1878 */     public K lowerKey(K param1K) { return this.nm.lowerKey(param1K); }
/* 1879 */     public K floorKey(K param1K) { return this.nm.floorKey(param1K); }
/* 1880 */     public K ceilingKey(K param1K) { return this.nm.ceilingKey(param1K); } public K higherKey(K param1K) {
/* 1881 */       return this.nm.higherKey(param1K);
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V> lowerEntry(K param1K) {
/* 1885 */       Map.Entry<K, ? extends V> entry = this.nm.lowerEntry(param1K);
/* 1886 */       return (null != entry) ? new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(entry) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> floorEntry(K param1K) {
/* 1893 */       Map.Entry<K, ? extends V> entry = this.nm.floorEntry(param1K);
/* 1894 */       return (null != entry) ? new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(entry) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> ceilingEntry(K param1K) {
/* 1901 */       Map.Entry<K, ? extends V> entry = this.nm.ceilingEntry(param1K);
/* 1902 */       return (null != entry) ? new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(entry) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> higherEntry(K param1K) {
/* 1910 */       Map.Entry<K, ? extends V> entry = this.nm.higherEntry(param1K);
/* 1911 */       return (null != entry) ? new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(entry) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> firstEntry() {
/* 1918 */       Map.Entry<K, ? extends V> entry = this.nm.firstEntry();
/* 1919 */       return (null != entry) ? new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(entry) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> lastEntry() {
/* 1926 */       Map.Entry<K, ? extends V> entry = this.nm.lastEntry();
/* 1927 */       return (null != entry) ? new Collections.UnmodifiableMap.UnmodifiableEntrySet.UnmodifiableEntry<>(entry) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> pollFirstEntry() {
/* 1933 */       throw new UnsupportedOperationException();
/*      */     } public Map.Entry<K, V> pollLastEntry() {
/* 1935 */       throw new UnsupportedOperationException();
/*      */     } public NavigableMap<K, V> descendingMap() {
/* 1937 */       return Collections.unmodifiableNavigableMap(this.nm.descendingMap());
/*      */     } public NavigableSet<K> navigableKeySet() {
/* 1939 */       return Collections.unmodifiableNavigableSet(this.nm.navigableKeySet());
/*      */     } public NavigableSet<K> descendingKeySet() {
/* 1941 */       return Collections.unmodifiableNavigableSet(this.nm.descendingKeySet());
/*      */     }
/*      */     public NavigableMap<K, V> subMap(K param1K1, boolean param1Boolean1, K param1K2, boolean param1Boolean2) {
/* 1944 */       return Collections.unmodifiableNavigableMap(this.nm
/* 1945 */           .subMap(param1K1, param1Boolean1, param1K2, param1Boolean2));
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> headMap(K param1K, boolean param1Boolean) {
/* 1949 */       return Collections.unmodifiableNavigableMap(this.nm.headMap(param1K, param1Boolean));
/*      */     } public NavigableMap<K, V> tailMap(K param1K, boolean param1Boolean) {
/* 1951 */       return Collections.unmodifiableNavigableMap(this.nm.tailMap(param1K, param1Boolean));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class EmptyNavigableMap<K, V>
/*      */     extends UnmodifiableNavigableMap<K, V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -2239321462712562324L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     EmptyNavigableMap() {
/*      */       super(new TreeMap<>());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableSet<K> navigableKeySet() {
/*      */       return Collections.emptyNavigableSet();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/*      */       return Collections.UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Collection<T> synchronizedCollection(Collection<T> paramCollection) {
/* 1990 */     return new SynchronizedCollection<>(paramCollection);
/*      */   }
/*      */   
/*      */   static <T> Collection<T> synchronizedCollection(Collection<T> paramCollection, Object paramObject) {
/* 1994 */     return new SynchronizedCollection<>(paramCollection, paramObject);
/*      */   }
/*      */ 
/*      */   
/*      */   static class SynchronizedCollection<E>
/*      */     implements Collection<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 3053995032091335093L;
/*      */     
/*      */     final Collection<E> c;
/*      */     final Object mutex;
/*      */     
/*      */     SynchronizedCollection(Collection<E> param1Collection) {
/* 2007 */       this.c = Objects.<Collection<E>>requireNonNull(param1Collection);
/* 2008 */       this.mutex = this;
/*      */     }
/*      */     
/*      */     SynchronizedCollection(Collection<E> param1Collection, Object param1Object) {
/* 2012 */       this.c = Objects.<Collection<E>>requireNonNull(param1Collection);
/* 2013 */       this.mutex = Objects.requireNonNull(param1Object);
/*      */     }
/*      */     
/*      */     public int size() {
/* 2017 */       synchronized (this.mutex) { return this.c.size(); }
/*      */     
/*      */     } public boolean isEmpty() {
/* 2020 */       synchronized (this.mutex) { return this.c.isEmpty(); }
/*      */     
/*      */     } public boolean contains(Object param1Object) {
/* 2023 */       synchronized (this.mutex) { return this.c.contains(param1Object); }
/*      */     
/*      */     } public Object[] toArray() {
/* 2026 */       synchronized (this.mutex) { return this.c.toArray(); }
/*      */     
/*      */     } public <T> T[] toArray(T[] param1ArrayOfT) {
/* 2029 */       synchronized (this.mutex) { return this.c.toArray(param1ArrayOfT); }
/*      */     
/*      */     }
/*      */     public Iterator<E> iterator() {
/* 2033 */       return this.c.iterator();
/*      */     }
/*      */     
/*      */     public boolean add(E param1E) {
/* 2037 */       synchronized (this.mutex) { return this.c.add(param1E); }
/*      */     
/*      */     } public boolean remove(Object param1Object) {
/* 2040 */       synchronized (this.mutex) { return this.c.remove(param1Object); }
/*      */     
/*      */     }
/*      */     public boolean containsAll(Collection<?> param1Collection) {
/* 2044 */       synchronized (this.mutex) { return this.c.containsAll(param1Collection); }
/*      */     
/*      */     } public boolean addAll(Collection<? extends E> param1Collection) {
/* 2047 */       synchronized (this.mutex) { return this.c.addAll(param1Collection); }
/*      */     
/*      */     } public boolean removeAll(Collection<?> param1Collection) {
/* 2050 */       synchronized (this.mutex) { return this.c.removeAll(param1Collection); }
/*      */     
/*      */     } public boolean retainAll(Collection<?> param1Collection) {
/* 2053 */       synchronized (this.mutex) { return this.c.retainAll(param1Collection); }
/*      */     
/*      */     } public void clear() {
/* 2056 */       synchronized (this.mutex) { this.c.clear(); }
/*      */     
/*      */     } public String toString() {
/* 2059 */       synchronized (this.mutex) { return this.c.toString(); }
/*      */     
/*      */     }
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 2064 */       synchronized (this.mutex) { this.c.forEach(param1Consumer); }
/*      */     
/*      */     }
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 2068 */       synchronized (this.mutex) { return this.c.removeIf(param1Predicate); }
/*      */     
/*      */     }
/*      */     public Spliterator<E> spliterator() {
/* 2072 */       return this.c.spliterator();
/*      */     }
/*      */     
/*      */     public Stream<E> stream() {
/* 2076 */       return this.c.stream();
/*      */     }
/*      */     
/*      */     public Stream<E> parallelStream() {
/* 2080 */       return this.c.parallelStream();
/*      */     }
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 2083 */       synchronized (this.mutex) { param1ObjectOutputStream.defaultWriteObject(); }
/*      */     
/*      */     }
/*      */   }
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
/*      */   public static <T> Set<T> synchronizedSet(Set<T> paramSet) {
/* 2114 */     return new SynchronizedSet<>(paramSet);
/*      */   }
/*      */   
/*      */   static <T> Set<T> synchronizedSet(Set<T> paramSet, Object paramObject) {
/* 2118 */     return new SynchronizedSet<>(paramSet, paramObject);
/*      */   }
/*      */ 
/*      */   
/*      */   static class SynchronizedSet<E>
/*      */     extends SynchronizedCollection<E>
/*      */     implements Set<E>
/*      */   {
/*      */     private static final long serialVersionUID = 487447009682186044L;
/*      */ 
/*      */     
/*      */     SynchronizedSet(Set<E> param1Set) {
/* 2130 */       super(param1Set);
/*      */     }
/*      */     SynchronizedSet(Set<E> param1Set, Object param1Object) {
/* 2133 */       super(param1Set, param1Object);
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 2137 */       if (this == param1Object)
/* 2138 */         return true; 
/* 2139 */       synchronized (this.mutex) { return this.c.equals(param1Object); }
/*      */     
/*      */     } public int hashCode() {
/* 2142 */       synchronized (this.mutex) { return this.c.hashCode(); }
/*      */     
/*      */     }
/*      */   }
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
/*      */   public static <T> SortedSet<T> synchronizedSortedSet(SortedSet<T> paramSortedSet) {
/* 2185 */     return new SynchronizedSortedSet<>(paramSortedSet);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SynchronizedSortedSet<E>
/*      */     extends SynchronizedSet<E>
/*      */     implements SortedSet<E>
/*      */   {
/*      */     private static final long serialVersionUID = 8695801310862127406L;
/*      */     
/*      */     private final SortedSet<E> ss;
/*      */ 
/*      */     
/*      */     SynchronizedSortedSet(SortedSet<E> param1SortedSet) {
/* 2200 */       super(param1SortedSet);
/* 2201 */       this.ss = param1SortedSet;
/*      */     }
/*      */     SynchronizedSortedSet(SortedSet<E> param1SortedSet, Object param1Object) {
/* 2204 */       super(param1SortedSet, param1Object);
/* 2205 */       this.ss = param1SortedSet;
/*      */     }
/*      */     
/*      */     public Comparator<? super E> comparator() {
/* 2209 */       synchronized (this.mutex) { return this.ss.comparator(); }
/*      */     
/*      */     }
/*      */     public SortedSet<E> subSet(E param1E1, E param1E2) {
/* 2213 */       synchronized (this.mutex) {
/* 2214 */         return new SynchronizedSortedSet(this.ss
/* 2215 */             .subSet(param1E1, param1E2), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedSet<E> headSet(E param1E) {
/* 2219 */       synchronized (this.mutex) {
/* 2220 */         return new SynchronizedSortedSet(this.ss.headSet(param1E), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedSet<E> tailSet(E param1E) {
/* 2224 */       synchronized (this.mutex) {
/* 2225 */         return new SynchronizedSortedSet(this.ss.tailSet(param1E), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public E first() {
/* 2230 */       synchronized (this.mutex) { return this.ss.first(); }
/*      */     
/*      */     } public E last() {
/* 2233 */       synchronized (this.mutex) { return this.ss.last(); }
/*      */     
/*      */     }
/*      */   }
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
/*      */   public static <T> NavigableSet<T> synchronizedNavigableSet(NavigableSet<T> paramNavigableSet) {
/* 2278 */     return new SynchronizedNavigableSet<>(paramNavigableSet);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SynchronizedNavigableSet<E>
/*      */     extends SynchronizedSortedSet<E>
/*      */     implements NavigableSet<E>
/*      */   {
/*      */     private static final long serialVersionUID = -5505529816273629798L;
/*      */     
/*      */     private final NavigableSet<E> ns;
/*      */ 
/*      */     
/*      */     SynchronizedNavigableSet(NavigableSet<E> param1NavigableSet) {
/* 2293 */       super(param1NavigableSet);
/* 2294 */       this.ns = param1NavigableSet;
/*      */     }
/*      */     
/*      */     SynchronizedNavigableSet(NavigableSet<E> param1NavigableSet, Object param1Object) {
/* 2298 */       super(param1NavigableSet, param1Object);
/* 2299 */       this.ns = param1NavigableSet;
/*      */     }
/* 2301 */     public E lower(E param1E) { synchronized (this.mutex) { return this.ns.lower(param1E); }
/* 2302 */        } public E floor(E param1E) { synchronized (this.mutex) { return this.ns.floor(param1E); }
/* 2303 */        } public E ceiling(E param1E) { synchronized (this.mutex) { return this.ns.ceiling(param1E); }
/* 2304 */        } public E higher(E param1E) { synchronized (this.mutex) { return this.ns.higher(param1E); }
/* 2305 */        } public E pollFirst() { synchronized (this.mutex) { return this.ns.pollFirst(); }
/* 2306 */        } public E pollLast() { synchronized (this.mutex) { return this.ns.pollLast(); }
/*      */        }
/*      */      public NavigableSet<E> descendingSet() {
/* 2309 */       synchronized (this.mutex) {
/* 2310 */         return new SynchronizedNavigableSet(this.ns.descendingSet(), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public Iterator<E> descendingIterator() {
/* 2315 */       synchronized (this.mutex) { return descendingSet().iterator(); }
/*      */     
/*      */     } public NavigableSet<E> subSet(E param1E1, E param1E2) {
/* 2318 */       synchronized (this.mutex) {
/* 2319 */         return new SynchronizedNavigableSet(this.ns.subSet(param1E1, true, param1E2, false), this.mutex);
/*      */       } 
/*      */     }
/*      */     public NavigableSet<E> headSet(E param1E) {
/* 2323 */       synchronized (this.mutex) {
/* 2324 */         return new SynchronizedNavigableSet(this.ns.headSet(param1E, false), this.mutex);
/*      */       } 
/*      */     }
/*      */     public NavigableSet<E> tailSet(E param1E) {
/* 2328 */       synchronized (this.mutex) {
/* 2329 */         return new SynchronizedNavigableSet(this.ns.tailSet(param1E, true), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableSet<E> subSet(E param1E1, boolean param1Boolean1, E param1E2, boolean param1Boolean2) {
/* 2334 */       synchronized (this.mutex) {
/* 2335 */         return new SynchronizedNavigableSet(this.ns.subSet(param1E1, param1Boolean1, param1E2, param1Boolean2), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableSet<E> headSet(E param1E, boolean param1Boolean) {
/* 2340 */       synchronized (this.mutex) {
/* 2341 */         return new SynchronizedNavigableSet(this.ns.headSet(param1E, param1Boolean), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableSet<E> tailSet(E param1E, boolean param1Boolean) {
/* 2346 */       synchronized (this.mutex) {
/* 2347 */         return new SynchronizedNavigableSet(this.ns.tailSet(param1E, param1Boolean), this.mutex);
/*      */       } 
/*      */     }
/*      */   }
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
/*      */   public static <T> List<T> synchronizedList(List<T> paramList) {
/* 2379 */     return (paramList instanceof RandomAccess) ? new SynchronizedRandomAccessList<>(paramList) : new SynchronizedList<>(paramList);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> List<T> synchronizedList(List<T> paramList, Object paramObject) {
/* 2385 */     return (paramList instanceof RandomAccess) ? new SynchronizedRandomAccessList<>(paramList, paramObject) : new SynchronizedList<>(paramList, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SynchronizedList<E>
/*      */     extends SynchronizedCollection<E>
/*      */     implements List<E>
/*      */   {
/*      */     private static final long serialVersionUID = -7754090372962971524L;
/*      */ 
/*      */     
/*      */     final List<E> list;
/*      */ 
/*      */     
/*      */     SynchronizedList(List<E> param1List) {
/* 2401 */       super(param1List);
/* 2402 */       this.list = param1List;
/*      */     }
/*      */     SynchronizedList(List<E> param1List, Object param1Object) {
/* 2405 */       super(param1List, param1Object);
/* 2406 */       this.list = param1List;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 2410 */       if (this == param1Object)
/* 2411 */         return true; 
/* 2412 */       synchronized (this.mutex) { return this.list.equals(param1Object); }
/*      */     
/*      */     } public int hashCode() {
/* 2415 */       synchronized (this.mutex) { return this.list.hashCode(); }
/*      */     
/*      */     }
/*      */     public E get(int param1Int) {
/* 2419 */       synchronized (this.mutex) { return this.list.get(param1Int); }
/*      */     
/*      */     } public E set(int param1Int, E param1E) {
/* 2422 */       synchronized (this.mutex) { return this.list.set(param1Int, param1E); }
/*      */     
/*      */     } public void add(int param1Int, E param1E) {
/* 2425 */       synchronized (this.mutex) { this.list.add(param1Int, param1E); }
/*      */     
/*      */     } public E remove(int param1Int) {
/* 2428 */       synchronized (this.mutex) { return this.list.remove(param1Int); }
/*      */     
/*      */     }
/*      */     public int indexOf(Object param1Object) {
/* 2432 */       synchronized (this.mutex) { return this.list.indexOf(param1Object); }
/*      */     
/*      */     } public int lastIndexOf(Object param1Object) {
/* 2435 */       synchronized (this.mutex) { return this.list.lastIndexOf(param1Object); }
/*      */     
/*      */     }
/*      */     public boolean addAll(int param1Int, Collection<? extends E> param1Collection) {
/* 2439 */       synchronized (this.mutex) { return this.list.addAll(param1Int, param1Collection); }
/*      */     
/*      */     }
/*      */     public ListIterator<E> listIterator() {
/* 2443 */       return this.list.listIterator();
/*      */     }
/*      */     
/*      */     public ListIterator<E> listIterator(int param1Int) {
/* 2447 */       return this.list.listIterator(param1Int);
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 2451 */       synchronized (this.mutex) {
/* 2452 */         return new SynchronizedList(this.list.subList(param1Int1, param1Int2), this.mutex);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void replaceAll(UnaryOperator<E> param1UnaryOperator) {
/* 2459 */       synchronized (this.mutex) { this.list.replaceAll(param1UnaryOperator); }
/*      */     
/*      */     }
/*      */     public void sort(Comparator<? super E> param1Comparator) {
/* 2463 */       synchronized (this.mutex) { this.list.sort(param1Comparator); }
/*      */     
/*      */     }
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
/*      */     private Object readResolve() {
/* 2479 */       return (this.list instanceof RandomAccess) ? new Collections.SynchronizedRandomAccessList<>(this.list) : this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SynchronizedRandomAccessList<E>
/*      */     extends SynchronizedList<E>
/*      */     implements RandomAccess
/*      */   {
/*      */     private static final long serialVersionUID = 1530674583602358482L;
/*      */ 
/*      */     
/*      */     SynchronizedRandomAccessList(List<E> param1List) {
/* 2493 */       super(param1List);
/*      */     }
/*      */     
/*      */     SynchronizedRandomAccessList(List<E> param1List, Object param1Object) {
/* 2497 */       super(param1List, param1Object);
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 2501 */       synchronized (this.mutex) {
/* 2502 */         return new SynchronizedRandomAccessList(this.list
/* 2503 */             .subList(param1Int1, param1Int2), this.mutex);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object writeReplace() {
/* 2516 */       return new Collections.SynchronizedList<>(this.list);
/*      */     }
/*      */   }
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
/*      */   public static <K, V> Map<K, V> synchronizedMap(Map<K, V> paramMap) {
/* 2550 */     return new SynchronizedMap<>(paramMap);
/*      */   }
/*      */   
/*      */   private static class SynchronizedMap<K, V>
/*      */     implements Map<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1978198479659022715L;
/*      */     private final Map<K, V> m;
/*      */     final Object mutex;
/*      */     private transient Set<K> keySet;
/*      */     private transient Set<Map.Entry<K, V>> entrySet;
/*      */     private transient Collection<V> values;
/*      */     
/*      */     SynchronizedMap(Map<K, V> param1Map) {
/* 2564 */       this.m = Objects.<Map<K, V>>requireNonNull(param1Map);
/* 2565 */       this.mutex = this;
/*      */     }
/*      */     
/*      */     SynchronizedMap(Map<K, V> param1Map, Object param1Object) {
/* 2569 */       this.m = param1Map;
/* 2570 */       this.mutex = param1Object;
/*      */     }
/*      */     
/*      */     public int size() {
/* 2574 */       synchronized (this.mutex) { return this.m.size(); }
/*      */     
/*      */     } public boolean isEmpty() {
/* 2577 */       synchronized (this.mutex) { return this.m.isEmpty(); }
/*      */     
/*      */     } public boolean containsKey(Object param1Object) {
/* 2580 */       synchronized (this.mutex) { return this.m.containsKey(param1Object); }
/*      */     
/*      */     } public boolean containsValue(Object param1Object) {
/* 2583 */       synchronized (this.mutex) { return this.m.containsValue(param1Object); }
/*      */     
/*      */     } public V get(Object param1Object) {
/* 2586 */       synchronized (this.mutex) { return this.m.get(param1Object); }
/*      */     
/*      */     }
/*      */     public V put(K param1K, V param1V) {
/* 2590 */       synchronized (this.mutex) { return this.m.put(param1K, param1V); }
/*      */     
/*      */     } public V remove(Object param1Object) {
/* 2593 */       synchronized (this.mutex) { return this.m.remove(param1Object); }
/*      */     
/*      */     } public void putAll(Map<? extends K, ? extends V> param1Map) {
/* 2596 */       synchronized (this.mutex) { this.m.putAll(param1Map); }
/*      */     
/*      */     } public void clear() {
/* 2599 */       synchronized (this.mutex) { this.m.clear(); }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 2607 */       synchronized (this.mutex) {
/* 2608 */         if (this.keySet == null)
/* 2609 */           this.keySet = new Collections.SynchronizedSet<>(this.m.keySet(), this.mutex); 
/* 2610 */         return this.keySet;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 2615 */       synchronized (this.mutex) {
/* 2616 */         if (this.entrySet == null)
/* 2617 */           this.entrySet = new Collections.SynchronizedSet<>(this.m.entrySet(), this.mutex); 
/* 2618 */         return this.entrySet;
/*      */       } 
/*      */     }
/*      */     
/*      */     public Collection<V> values() {
/* 2623 */       synchronized (this.mutex) {
/* 2624 */         if (this.values == null)
/* 2625 */           this.values = new Collections.SynchronizedCollection<>(this.m.values(), this.mutex); 
/* 2626 */         return this.values;
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 2631 */       if (this == param1Object)
/* 2632 */         return true; 
/* 2633 */       synchronized (this.mutex) { return this.m.equals(param1Object); }
/*      */     
/*      */     } public int hashCode() {
/* 2636 */       synchronized (this.mutex) { return this.m.hashCode(); }
/*      */     
/*      */     } public String toString() {
/* 2639 */       synchronized (this.mutex) { return this.m.toString(); }
/*      */     
/*      */     }
/*      */ 
/*      */     
/*      */     public V getOrDefault(Object param1Object, V param1V) {
/* 2645 */       synchronized (this.mutex) { return this.m.getOrDefault(param1Object, param1V); }
/*      */     
/*      */     }
/*      */     public void forEach(BiConsumer<? super K, ? super V> param1BiConsumer) {
/* 2649 */       synchronized (this.mutex) { this.m.forEach(param1BiConsumer); }
/*      */     
/*      */     }
/*      */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 2653 */       synchronized (this.mutex) { this.m.replaceAll(param1BiFunction); }
/*      */     
/*      */     }
/*      */     public V putIfAbsent(K param1K, V param1V) {
/* 2657 */       synchronized (this.mutex) { return this.m.putIfAbsent(param1K, param1V); }
/*      */     
/*      */     }
/*      */     public boolean remove(Object param1Object1, Object param1Object2) {
/* 2661 */       synchronized (this.mutex) { return this.m.remove(param1Object1, param1Object2); }
/*      */     
/*      */     }
/*      */     public boolean replace(K param1K, V param1V1, V param1V2) {
/* 2665 */       synchronized (this.mutex) { return this.m.replace(param1K, param1V1, param1V2); }
/*      */     
/*      */     }
/*      */     public V replace(K param1K, V param1V) {
/* 2669 */       synchronized (this.mutex) { return this.m.replace(param1K, param1V); }
/*      */     
/*      */     }
/*      */     
/*      */     public V computeIfAbsent(K param1K, Function<? super K, ? extends V> param1Function) {
/* 2674 */       synchronized (this.mutex) { return this.m.computeIfAbsent(param1K, param1Function); }
/*      */     
/*      */     }
/*      */     
/*      */     public V computeIfPresent(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 2679 */       synchronized (this.mutex) { return this.m.computeIfPresent(param1K, param1BiFunction); }
/*      */     
/*      */     }
/*      */     
/*      */     public V compute(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 2684 */       synchronized (this.mutex) { return this.m.compute(param1K, param1BiFunction); }
/*      */     
/*      */     }
/*      */     
/*      */     public V merge(K param1K, V param1V, BiFunction<? super V, ? super V, ? extends V> param1BiFunction) {
/* 2689 */       synchronized (this.mutex) { return this.m.merge(param1K, param1V, param1BiFunction); }
/*      */     
/*      */     }
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 2693 */       synchronized (this.mutex) { param1ObjectOutputStream.defaultWriteObject(); }
/*      */     
/*      */     }
/*      */   }
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
/*      */   public static <K, V> SortedMap<K, V> synchronizedSortedMap(SortedMap<K, V> paramSortedMap) {
/* 2742 */     return new SynchronizedSortedMap<>(paramSortedMap);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SynchronizedSortedMap<K, V>
/*      */     extends SynchronizedMap<K, V>
/*      */     implements SortedMap<K, V>
/*      */   {
/*      */     private static final long serialVersionUID = -8798146769416483793L;
/*      */     
/*      */     private final SortedMap<K, V> sm;
/*      */ 
/*      */     
/*      */     SynchronizedSortedMap(SortedMap<K, V> param1SortedMap) {
/* 2757 */       super(param1SortedMap);
/* 2758 */       this.sm = param1SortedMap;
/*      */     }
/*      */     SynchronizedSortedMap(SortedMap<K, V> param1SortedMap, Object param1Object) {
/* 2761 */       super(param1SortedMap, param1Object);
/* 2762 */       this.sm = param1SortedMap;
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 2766 */       synchronized (this.mutex) { return this.sm.comparator(); }
/*      */     
/*      */     }
/*      */     public SortedMap<K, V> subMap(K param1K1, K param1K2) {
/* 2770 */       synchronized (this.mutex) {
/* 2771 */         return new SynchronizedSortedMap(this.sm
/* 2772 */             .subMap(param1K1, param1K2), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedMap<K, V> headMap(K param1K) {
/* 2776 */       synchronized (this.mutex) {
/* 2777 */         return new SynchronizedSortedMap(this.sm.headMap(param1K), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedMap<K, V> tailMap(K param1K) {
/* 2781 */       synchronized (this.mutex) {
/* 2782 */         return new SynchronizedSortedMap(this.sm.tailMap(param1K), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 2787 */       synchronized (this.mutex) { return this.sm.firstKey(); }
/*      */     
/*      */     } public K lastKey() {
/* 2790 */       synchronized (this.mutex) { return this.sm.lastKey(); }
/*      */     
/*      */     }
/*      */   }
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
/*      */   public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> paramNavigableMap) {
/* 2841 */     return new SynchronizedNavigableMap<>(paramNavigableMap);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class SynchronizedNavigableMap<K, V>
/*      */     extends SynchronizedSortedMap<K, V>
/*      */     implements NavigableMap<K, V>
/*      */   {
/*      */     private static final long serialVersionUID = 699392247599746807L;
/*      */ 
/*      */     
/*      */     private final NavigableMap<K, V> nm;
/*      */ 
/*      */ 
/*      */     
/*      */     SynchronizedNavigableMap(NavigableMap<K, V> param1NavigableMap) {
/* 2858 */       super(param1NavigableMap);
/* 2859 */       this.nm = param1NavigableMap;
/*      */     }
/*      */     SynchronizedNavigableMap(NavigableMap<K, V> param1NavigableMap, Object param1Object) {
/* 2862 */       super(param1NavigableMap, param1Object);
/* 2863 */       this.nm = param1NavigableMap;
/*      */     }
/*      */     
/*      */     public Map.Entry<K, V> lowerEntry(K param1K) {
/* 2867 */       synchronized (this.mutex) { return this.nm.lowerEntry(param1K); }
/*      */     
/* 2869 */     } public K lowerKey(K param1K) { synchronized (this.mutex) { return this.nm.lowerKey(param1K); }
/*      */        }
/* 2871 */     public Map.Entry<K, V> floorEntry(K param1K) { synchronized (this.mutex) { return this.nm.floorEntry(param1K); }
/*      */        }
/* 2873 */     public K floorKey(K param1K) { synchronized (this.mutex) { return this.nm.floorKey(param1K); }
/*      */        }
/* 2875 */     public Map.Entry<K, V> ceilingEntry(K param1K) { synchronized (this.mutex) { return this.nm.ceilingEntry(param1K); }
/*      */        }
/* 2877 */     public K ceilingKey(K param1K) { synchronized (this.mutex) { return this.nm.ceilingKey(param1K); }
/*      */        }
/* 2879 */     public Map.Entry<K, V> higherEntry(K param1K) { synchronized (this.mutex) { return this.nm.higherEntry(param1K); }
/*      */        }
/* 2881 */     public K higherKey(K param1K) { synchronized (this.mutex) { return this.nm.higherKey(param1K); }
/*      */        }
/* 2883 */     public Map.Entry<K, V> firstEntry() { synchronized (this.mutex) { return this.nm.firstEntry(); }
/*      */        }
/* 2885 */     public Map.Entry<K, V> lastEntry() { synchronized (this.mutex) { return this.nm.lastEntry(); }
/*      */        }
/* 2887 */     public Map.Entry<K, V> pollFirstEntry() { synchronized (this.mutex) { return this.nm.pollFirstEntry(); }
/*      */        } public Map.Entry<K, V> pollLastEntry() {
/* 2889 */       synchronized (this.mutex) { return this.nm.pollLastEntry(); }
/*      */     
/*      */     } public NavigableMap<K, V> descendingMap() {
/* 2892 */       synchronized (this.mutex) {
/* 2893 */         return new SynchronizedNavigableMap(this.nm
/* 2894 */             .descendingMap(), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableSet<K> keySet() {
/* 2899 */       return navigableKeySet();
/*      */     }
/*      */     
/*      */     public NavigableSet<K> navigableKeySet() {
/* 2903 */       synchronized (this.mutex) {
/* 2904 */         return new Collections.SynchronizedNavigableSet<>(this.nm.navigableKeySet(), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableSet<K> descendingKeySet() {
/* 2909 */       synchronized (this.mutex) {
/* 2910 */         return new Collections.SynchronizedNavigableSet<>(this.nm.descendingKeySet(), this.mutex);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public SortedMap<K, V> subMap(K param1K1, K param1K2) {
/* 2916 */       synchronized (this.mutex) {
/* 2917 */         return new SynchronizedNavigableMap(this.nm
/* 2918 */             .subMap(param1K1, true, param1K2, false), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedMap<K, V> headMap(K param1K) {
/* 2922 */       synchronized (this.mutex) {
/* 2923 */         return new SynchronizedNavigableMap(this.nm.headMap(param1K, false), this.mutex);
/*      */       } 
/*      */     }
/*      */     public SortedMap<K, V> tailMap(K param1K) {
/* 2927 */       synchronized (this.mutex) {
/* 2928 */         return new SynchronizedNavigableMap(this.nm.tailMap(param1K, true), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> subMap(K param1K1, boolean param1Boolean1, K param1K2, boolean param1Boolean2) {
/* 2933 */       synchronized (this.mutex) {
/* 2934 */         return new SynchronizedNavigableMap(this.nm
/* 2935 */             .subMap(param1K1, param1Boolean1, param1K2, param1Boolean2), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> headMap(K param1K, boolean param1Boolean) {
/* 2940 */       synchronized (this.mutex) {
/* 2941 */         return new SynchronizedNavigableMap(this.nm
/* 2942 */             .headMap(param1K, param1Boolean), this.mutex);
/*      */       } 
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> tailMap(K param1K, boolean param1Boolean) {
/* 2947 */       synchronized (this.mutex) {
/* 2948 */         return new SynchronizedNavigableMap(this.nm
/* 2949 */             .tailMap(param1K, param1Boolean), this.mutex);
/*      */       } 
/*      */     }
/*      */   }
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
/*      */   public static <E> Collection<E> checkedCollection(Collection<E> paramCollection, Class<E> paramClass) {
/* 3019 */     return new CheckedCollection<>(paramCollection, paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   static <T> T[] zeroLengthArray(Class<T> paramClass) {
/* 3024 */     return (T[])Array.newInstance(paramClass, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   static class CheckedCollection<E>
/*      */     implements Collection<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1578914078182001775L;
/*      */     
/*      */     final Collection<E> c;
/*      */     final Class<E> type;
/*      */     private E[] zeroLengthElementArray;
/*      */     
/*      */     E typeCheck(Object param1Object) {
/* 3038 */       if (param1Object != null && !this.type.isInstance(param1Object))
/* 3039 */         throw new ClassCastException(badElementMsg(param1Object)); 
/* 3040 */       return (E)param1Object;
/*      */     }
/*      */     
/*      */     private String badElementMsg(Object param1Object) {
/* 3044 */       return "Attempt to insert " + param1Object.getClass() + " element into collection with element type " + this.type;
/*      */     }
/*      */ 
/*      */     
/*      */     CheckedCollection(Collection<E> param1Collection, Class<E> param1Class) {
/* 3049 */       this.c = Objects.<Collection<E>>requireNonNull(param1Collection, "c");
/* 3050 */       this.type = Objects.<Class<E>>requireNonNull(param1Class, "type");
/*      */     }
/*      */     
/* 3053 */     public int size() { return this.c.size(); }
/* 3054 */     public boolean isEmpty() { return this.c.isEmpty(); }
/* 3055 */     public boolean contains(Object param1Object) { return this.c.contains(param1Object); }
/* 3056 */     public Object[] toArray() { return this.c.toArray(); }
/* 3057 */     public <T> T[] toArray(T[] param1ArrayOfT) { return this.c.toArray(param1ArrayOfT); }
/* 3058 */     public String toString() { return this.c.toString(); }
/* 3059 */     public boolean remove(Object param1Object) { return this.c.remove(param1Object); } public void clear() {
/* 3060 */       this.c.clear();
/*      */     }
/*      */     public boolean containsAll(Collection<?> param1Collection) {
/* 3063 */       return this.c.containsAll(param1Collection);
/*      */     }
/*      */     public boolean removeAll(Collection<?> param1Collection) {
/* 3066 */       return this.c.removeAll(param1Collection);
/*      */     }
/*      */     public boolean retainAll(Collection<?> param1Collection) {
/* 3069 */       return this.c.retainAll(param1Collection);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Iterator<E> iterator() {
/* 3075 */       final Iterator<E> it = this.c.iterator();
/* 3076 */       return new Iterator<E>() {
/* 3077 */           public boolean hasNext() { return it.hasNext(); }
/* 3078 */           public E next() { return it.next(); }
/* 3079 */           public void remove() { it.remove(); }
/*      */         };
/*      */     } public boolean add(E param1E) {
/* 3082 */       return this.c.add(typeCheck(param1E));
/*      */     }
/*      */ 
/*      */     
/*      */     private E[] zeroLengthElementArray() {
/* 3087 */       return (this.zeroLengthElementArray != null) ? this.zeroLengthElementArray : (this
/* 3088 */         .zeroLengthElementArray = Collections.zeroLengthArray(this.type));
/*      */     }
/*      */ 
/*      */     
/*      */     Collection<E> checkedCopyOf(Collection<? extends E> param1Collection) {
/*      */       Object[] arrayOfObject;
/*      */       try {
/* 3095 */         E[] arrayOfE = zeroLengthElementArray();
/* 3096 */         arrayOfObject = param1Collection.toArray((Object[])arrayOfE);
/*      */         
/* 3098 */         if (arrayOfObject.getClass() != arrayOfE.getClass())
/* 3099 */           arrayOfObject = Arrays.copyOf(arrayOfObject, arrayOfObject.length, (Class)arrayOfE.getClass()); 
/* 3100 */       } catch (ArrayStoreException arrayStoreException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3106 */         arrayOfObject = (Object[])param1Collection.toArray().clone();
/* 3107 */         for (Object object : arrayOfObject) {
/* 3108 */           typeCheck(object);
/*      */         }
/*      */       } 
/* 3111 */       return Arrays.asList((E[])arrayOfObject);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean addAll(Collection<? extends E> param1Collection) {
/* 3119 */       return this.c.addAll(checkedCopyOf(param1Collection));
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 3124 */       this.c.forEach(param1Consumer);
/*      */     }
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 3127 */       return this.c.removeIf(param1Predicate);
/*      */     }
/*      */     public Spliterator<E> spliterator() {
/* 3130 */       return this.c.spliterator();
/*      */     } public Stream<E> stream() {
/* 3132 */       return this.c.stream();
/*      */     } public Stream<E> parallelStream() {
/* 3134 */       return this.c.parallelStream();
/*      */     }
/*      */   }
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
/*      */   public static <E> Queue<E> checkedQueue(Queue<E> paramQueue, Class<E> paramClass) {
/* 3165 */     return new CheckedQueue<>(paramQueue, paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   static class CheckedQueue<E>
/*      */     extends CheckedCollection<E>
/*      */     implements Queue<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1433151992604707767L;
/*      */     
/*      */     final Queue<E> queue;
/*      */ 
/*      */     
/*      */     CheckedQueue(Queue<E> param1Queue, Class<E> param1Class) {
/* 3179 */       super(param1Queue, param1Class);
/* 3180 */       this.queue = param1Queue;
/*      */     }
/*      */     
/* 3183 */     public E element() { return this.queue.element(); }
/* 3184 */     public boolean equals(Object param1Object) { return (param1Object == this || this.c.equals(param1Object)); }
/* 3185 */     public int hashCode() { return this.c.hashCode(); }
/* 3186 */     public E peek() { return this.queue.peek(); }
/* 3187 */     public E poll() { return this.queue.poll(); }
/* 3188 */     public E remove() { return this.queue.remove(); } public boolean offer(E param1E) {
/* 3189 */       return this.queue.offer(typeCheck(param1E));
/*      */     }
/*      */   }
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
/*      */   public static <E> Set<E> checkedSet(Set<E> paramSet, Class<E> paramClass) {
/* 3220 */     return new CheckedSet<>(paramSet, paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   static class CheckedSet<E>
/*      */     extends CheckedCollection<E>
/*      */     implements Set<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 4694047833775013803L;
/*      */     
/*      */     CheckedSet(Set<E> param1Set, Class<E> param1Class) {
/* 3231 */       super(param1Set, param1Class);
/*      */     }
/* 3233 */     public boolean equals(Object param1Object) { return (param1Object == this || this.c.equals(param1Object)); } public int hashCode() {
/* 3234 */       return this.c.hashCode();
/*      */     }
/*      */   }
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
/*      */   public static <E> SortedSet<E> checkedSortedSet(SortedSet<E> paramSortedSet, Class<E> paramClass) {
/* 3267 */     return new CheckedSortedSet<>(paramSortedSet, paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   static class CheckedSortedSet<E>
/*      */     extends CheckedSet<E>
/*      */     implements SortedSet<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1599911165492914959L;
/*      */     
/*      */     private final SortedSet<E> ss;
/*      */ 
/*      */     
/*      */     CheckedSortedSet(SortedSet<E> param1SortedSet, Class<E> param1Class) {
/* 3281 */       super(param1SortedSet, param1Class);
/* 3282 */       this.ss = param1SortedSet;
/*      */     }
/*      */     
/* 3285 */     public Comparator<? super E> comparator() { return this.ss.comparator(); }
/* 3286 */     public E first() { return this.ss.first(); } public E last() {
/* 3287 */       return this.ss.last();
/*      */     }
/*      */     public SortedSet<E> subSet(E param1E1, E param1E2) {
/* 3290 */       return Collections.checkedSortedSet(this.ss.subSet(param1E1, param1E2), this.type);
/*      */     }
/*      */     public SortedSet<E> headSet(E param1E) {
/* 3293 */       return Collections.checkedSortedSet(this.ss.headSet(param1E), this.type);
/*      */     }
/*      */     public SortedSet<E> tailSet(E param1E) {
/* 3296 */       return Collections.checkedSortedSet(this.ss.tailSet(param1E), this.type);
/*      */     }
/*      */   }
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
/*      */   public static <E> NavigableSet<E> checkedNavigableSet(NavigableSet<E> paramNavigableSet, Class<E> paramClass) {
/* 3330 */     return new CheckedNavigableSet<>(paramNavigableSet, paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   static class CheckedNavigableSet<E>
/*      */     extends CheckedSortedSet<E>
/*      */     implements NavigableSet<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -5429120189805438922L;
/*      */     
/*      */     private final NavigableSet<E> ns;
/*      */ 
/*      */     
/*      */     CheckedNavigableSet(NavigableSet<E> param1NavigableSet, Class<E> param1Class) {
/* 3344 */       super(param1NavigableSet, param1Class);
/* 3345 */       this.ns = param1NavigableSet;
/*      */     }
/*      */     
/* 3348 */     public E lower(E param1E) { return this.ns.lower(param1E); }
/* 3349 */     public E floor(E param1E) { return this.ns.floor(param1E); }
/* 3350 */     public E ceiling(E param1E) { return this.ns.ceiling(param1E); }
/* 3351 */     public E higher(E param1E) { return this.ns.higher(param1E); }
/* 3352 */     public E pollFirst() { return this.ns.pollFirst(); } public E pollLast() {
/* 3353 */       return this.ns.pollLast();
/*      */     } public NavigableSet<E> descendingSet() {
/* 3355 */       return Collections.checkedNavigableSet(this.ns.descendingSet(), this.type);
/*      */     } public Iterator<E> descendingIterator() {
/* 3357 */       return Collections.<E>checkedNavigableSet(this.ns.descendingSet(), this.type).iterator();
/*      */     }
/*      */     public NavigableSet<E> subSet(E param1E1, E param1E2) {
/* 3360 */       return Collections.checkedNavigableSet(this.ns.subSet(param1E1, true, param1E2, false), this.type);
/*      */     }
/*      */     public NavigableSet<E> headSet(E param1E) {
/* 3363 */       return Collections.checkedNavigableSet(this.ns.headSet(param1E, false), this.type);
/*      */     }
/*      */     public NavigableSet<E> tailSet(E param1E) {
/* 3366 */       return Collections.checkedNavigableSet(this.ns.tailSet(param1E, true), this.type);
/*      */     }
/*      */     
/*      */     public NavigableSet<E> subSet(E param1E1, boolean param1Boolean1, E param1E2, boolean param1Boolean2) {
/* 3370 */       return Collections.checkedNavigableSet(this.ns.subSet(param1E1, param1Boolean1, param1E2, param1Boolean2), this.type);
/*      */     }
/*      */     
/*      */     public NavigableSet<E> headSet(E param1E, boolean param1Boolean) {
/* 3374 */       return Collections.checkedNavigableSet(this.ns.headSet(param1E, param1Boolean), this.type);
/*      */     }
/*      */     
/*      */     public NavigableSet<E> tailSet(E param1E, boolean param1Boolean) {
/* 3378 */       return Collections.checkedNavigableSet(this.ns.tailSet(param1E, param1Boolean), this.type);
/*      */     }
/*      */   }
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
/*      */   public static <E> List<E> checkedList(List<E> paramList, Class<E> paramClass) {
/* 3410 */     return (paramList instanceof RandomAccess) ? new CheckedRandomAccessList<>(paramList, paramClass) : new CheckedList<>(paramList, paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class CheckedList<E>
/*      */     extends CheckedCollection<E>
/*      */     implements List<E>
/*      */   {
/*      */     private static final long serialVersionUID = 65247728283967356L;
/*      */ 
/*      */     
/*      */     final List<E> list;
/*      */ 
/*      */     
/*      */     CheckedList(List<E> param1List, Class<E> param1Class) {
/* 3426 */       super(param1List, param1Class);
/* 3427 */       this.list = param1List;
/*      */     }
/*      */     
/* 3430 */     public boolean equals(Object param1Object) { return (param1Object == this || this.list.equals(param1Object)); }
/* 3431 */     public int hashCode() { return this.list.hashCode(); }
/* 3432 */     public E get(int param1Int) { return this.list.get(param1Int); }
/* 3433 */     public E remove(int param1Int) { return this.list.remove(param1Int); }
/* 3434 */     public int indexOf(Object param1Object) { return this.list.indexOf(param1Object); } public int lastIndexOf(Object param1Object) {
/* 3435 */       return this.list.lastIndexOf(param1Object);
/*      */     }
/*      */     public E set(int param1Int, E param1E) {
/* 3438 */       return this.list.set(param1Int, typeCheck(param1E));
/*      */     }
/*      */     
/*      */     public void add(int param1Int, E param1E) {
/* 3442 */       this.list.add(param1Int, typeCheck(param1E));
/*      */     }
/*      */     
/*      */     public boolean addAll(int param1Int, Collection<? extends E> param1Collection) {
/* 3446 */       return this.list.addAll(param1Int, checkedCopyOf(param1Collection));
/*      */     } public ListIterator<E> listIterator() {
/* 3448 */       return listIterator(0);
/*      */     }
/*      */     public ListIterator<E> listIterator(int param1Int) {
/* 3451 */       final ListIterator<E> i = this.list.listIterator(param1Int);
/*      */       
/* 3453 */       return new ListIterator<E>() {
/* 3454 */           public boolean hasNext() { return i.hasNext(); }
/* 3455 */           public E next() { return i.next(); }
/* 3456 */           public boolean hasPrevious() { return i.hasPrevious(); }
/* 3457 */           public E previous() { return i.previous(); }
/* 3458 */           public int nextIndex() { return i.nextIndex(); }
/* 3459 */           public int previousIndex() { return i.previousIndex(); } public void remove() {
/* 3460 */             i.remove();
/*      */           }
/*      */           public void set(E param2E) {
/* 3463 */             i.set(Collections.CheckedList.this.typeCheck(param2E));
/*      */           }
/*      */           
/*      */           public void add(E param2E) {
/* 3467 */             i.add(Collections.CheckedList.this.typeCheck(param2E));
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEachRemaining(Consumer<? super E> param2Consumer) {
/* 3472 */             i.forEachRemaining(param2Consumer);
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 3478 */       return new CheckedList(this.list.subList(param1Int1, param1Int2), this.type);
/*      */     }
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
/*      */     public void replaceAll(UnaryOperator<E> param1UnaryOperator) {
/* 3491 */       Objects.requireNonNull(param1UnaryOperator);
/* 3492 */       this.list.replaceAll(param1Object -> typeCheck(param1UnaryOperator.apply(param1Object)));
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super E> param1Comparator) {
/* 3497 */       this.list.sort(param1Comparator);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class CheckedRandomAccessList<E>
/*      */     extends CheckedList<E>
/*      */     implements RandomAccess
/*      */   {
/*      */     private static final long serialVersionUID = 1638200125423088369L;
/*      */ 
/*      */     
/*      */     CheckedRandomAccessList(List<E> param1List, Class<E> param1Class) {
/* 3510 */       super(param1List, param1Class);
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 3514 */       return new CheckedRandomAccessList(this.list
/* 3515 */           .subList(param1Int1, param1Int2), this.type);
/*      */     }
/*      */   }
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
/*      */   public static <K, V> Map<K, V> checkedMap(Map<K, V> paramMap, Class<K> paramClass, Class<V> paramClass1) {
/* 3558 */     return new CheckedMap<>(paramMap, paramClass, paramClass1);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class CheckedMap<K, V>
/*      */     implements Map<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 5742860141034234728L;
/*      */     
/*      */     private final Map<K, V> m;
/*      */     
/*      */     final Class<K> keyType;
/*      */     
/*      */     final Class<V> valueType;
/*      */     private transient Set<Map.Entry<K, V>> entrySet;
/*      */     
/*      */     private void typeCheck(Object param1Object1, Object param1Object2) {
/* 3575 */       if (param1Object1 != null && !this.keyType.isInstance(param1Object1)) {
/* 3576 */         throw new ClassCastException(badKeyMsg(param1Object1));
/*      */       }
/* 3578 */       if (param1Object2 != null && !this.valueType.isInstance(param1Object2)) {
/* 3579 */         throw new ClassCastException(badValueMsg(param1Object2));
/*      */       }
/*      */     }
/*      */     
/*      */     private BiFunction<? super K, ? super V, ? extends V> typeCheck(BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 3584 */       Objects.requireNonNull(param1BiFunction);
/* 3585 */       return (param1Object1, param1Object2) -> {
/*      */           Object object = param1BiFunction.apply(param1Object1, param1Object2);
/*      */           typeCheck(param1Object1, object);
/*      */           return object;
/*      */         };
/*      */     }
/*      */     
/*      */     private String badKeyMsg(Object param1Object) {
/* 3593 */       return "Attempt to insert " + param1Object.getClass() + " key into map with key type " + this.keyType;
/*      */     }
/*      */ 
/*      */     
/*      */     private String badValueMsg(Object param1Object) {
/* 3598 */       return "Attempt to insert " + param1Object.getClass() + " value into map with value type " + this.valueType;
/*      */     }
/*      */ 
/*      */     
/*      */     CheckedMap(Map<K, V> param1Map, Class<K> param1Class, Class<V> param1Class1) {
/* 3603 */       this.m = Objects.<Map<K, V>>requireNonNull(param1Map);
/* 3604 */       this.keyType = Objects.<Class<K>>requireNonNull(param1Class);
/* 3605 */       this.valueType = Objects.<Class<V>>requireNonNull(param1Class1);
/*      */     }
/*      */     
/* 3608 */     public int size() { return this.m.size(); }
/* 3609 */     public boolean isEmpty() { return this.m.isEmpty(); }
/* 3610 */     public boolean containsKey(Object param1Object) { return this.m.containsKey(param1Object); }
/* 3611 */     public boolean containsValue(Object param1Object) { return this.m.containsValue(param1Object); }
/* 3612 */     public V get(Object param1Object) { return this.m.get(param1Object); }
/* 3613 */     public V remove(Object param1Object) { return this.m.remove(param1Object); }
/* 3614 */     public void clear() { this.m.clear(); }
/* 3615 */     public Set<K> keySet() { return this.m.keySet(); }
/* 3616 */     public Collection<V> values() { return this.m.values(); }
/* 3617 */     public boolean equals(Object param1Object) { return (param1Object == this || this.m.equals(param1Object)); }
/* 3618 */     public int hashCode() { return this.m.hashCode(); } public String toString() {
/* 3619 */       return this.m.toString();
/*      */     }
/*      */     public V put(K param1K, V param1V) {
/* 3622 */       typeCheck(param1K, param1V);
/* 3623 */       return this.m.put(param1K, param1V);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void putAll(Map<? extends K, ? extends V> param1Map) {
/* 3633 */       Object[] arrayOfObject = param1Map.entrySet().toArray();
/* 3634 */       ArrayList arrayList = new ArrayList(arrayOfObject.length);
/* 3635 */       for (Object object1 : arrayOfObject) {
/* 3636 */         Map.Entry entry = (Map.Entry)object1;
/* 3637 */         Object object2 = entry.getKey();
/* 3638 */         Object object3 = entry.getValue();
/* 3639 */         typeCheck(object2, object3);
/* 3640 */         arrayList.add(new AbstractMap.SimpleImmutableEntry<>(object2, object3));
/*      */       } 
/*      */       
/* 3643 */       for (Map.Entry entry : arrayList) {
/* 3644 */         this.m.put((K)entry.getKey(), (V)entry.getValue());
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 3650 */       if (this.entrySet == null)
/* 3651 */         this.entrySet = new CheckedEntrySet<>(this.m.entrySet(), this.valueType); 
/* 3652 */       return this.entrySet;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(BiConsumer<? super K, ? super V> param1BiConsumer) {
/* 3658 */       this.m.forEach(param1BiConsumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 3663 */       this.m.replaceAll(typeCheck(param1BiFunction));
/*      */     }
/*      */ 
/*      */     
/*      */     public V putIfAbsent(K param1K, V param1V) {
/* 3668 */       typeCheck(param1K, param1V);
/* 3669 */       return this.m.putIfAbsent(param1K, param1V);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object param1Object1, Object param1Object2) {
/* 3674 */       return this.m.remove(param1Object1, param1Object2);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean replace(K param1K, V param1V1, V param1V2) {
/* 3679 */       typeCheck(param1K, param1V2);
/* 3680 */       return this.m.replace(param1K, param1V1, param1V2);
/*      */     }
/*      */ 
/*      */     
/*      */     public V replace(K param1K, V param1V) {
/* 3685 */       typeCheck(param1K, param1V);
/* 3686 */       return this.m.replace(param1K, param1V);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfAbsent(K param1K, Function<? super K, ? extends V> param1Function) {
/* 3692 */       Objects.requireNonNull(param1Function);
/* 3693 */       return this.m.computeIfAbsent(param1K, param1Object -> {
/*      */             Object object = param1Function.apply(param1Object);
/*      */             typeCheck(param1Object, object);
/*      */             return object;
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfPresent(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 3703 */       return this.m.computeIfPresent(param1K, typeCheck(param1BiFunction));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V compute(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 3709 */       return this.m.compute(param1K, typeCheck(param1BiFunction));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V merge(K param1K, V param1V, BiFunction<? super V, ? super V, ? extends V> param1BiFunction) {
/* 3715 */       Objects.requireNonNull(param1BiFunction);
/* 3716 */       return this.m.merge(param1K, param1V, (param1Object1, param1Object2) -> {
/*      */             Object object = param1BiFunction.apply(param1Object1, param1Object2);
/*      */             typeCheck(null, object);
/*      */             return object;
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static class CheckedEntrySet<K, V>
/*      */       implements Set<Map.Entry<K, V>>
/*      */     {
/*      */       private final Set<Map.Entry<K, V>> s;
/*      */ 
/*      */       
/*      */       private final Class<V> valueType;
/*      */ 
/*      */ 
/*      */       
/*      */       CheckedEntrySet(Set<Map.Entry<K, V>> param2Set, Class<V> param2Class) {
/* 3736 */         this.s = param2Set;
/* 3737 */         this.valueType = param2Class;
/*      */       }
/*      */       
/* 3740 */       public int size() { return this.s.size(); }
/* 3741 */       public boolean isEmpty() { return this.s.isEmpty(); }
/* 3742 */       public String toString() { return this.s.toString(); }
/* 3743 */       public int hashCode() { return this.s.hashCode(); } public void clear() {
/* 3744 */         this.s.clear();
/*      */       }
/*      */       public boolean add(Map.Entry<K, V> param2Entry) {
/* 3747 */         throw new UnsupportedOperationException();
/*      */       }
/*      */       public boolean addAll(Collection<? extends Map.Entry<K, V>> param2Collection) {
/* 3750 */         throw new UnsupportedOperationException();
/*      */       }
/*      */       
/*      */       public Iterator<Map.Entry<K, V>> iterator() {
/* 3754 */         final Iterator<Map.Entry<K, V>> i = this.s.iterator();
/* 3755 */         final Class<V> valueType = this.valueType;
/*      */         
/* 3757 */         return new Iterator<Map.Entry<K, V>>() {
/* 3758 */             public boolean hasNext() { return i.hasNext(); } public void remove() {
/* 3759 */               i.remove();
/*      */             }
/*      */             public Map.Entry<K, V> next() {
/* 3762 */               return Collections.CheckedMap.CheckedEntrySet.checkedEntry(i.next(), valueType);
/*      */             }
/*      */           };
/*      */       }
/*      */ 
/*      */       
/*      */       public Object[] toArray() {
/* 3769 */         Object[] arrayOfObject1 = this.s.toArray();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3775 */         Object[] arrayOfObject2 = CheckedEntry.class.isInstance(arrayOfObject1
/* 3776 */             .getClass().getComponentType()) ? arrayOfObject1 : new Object[arrayOfObject1.length];
/*      */ 
/*      */         
/* 3779 */         for (byte b = 0; b < arrayOfObject1.length; b++) {
/* 3780 */           arrayOfObject2[b] = checkedEntry((Map.Entry<?, ?>)arrayOfObject1[b], this.valueType);
/*      */         }
/* 3782 */         return arrayOfObject2;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public <T> T[] toArray(T[] param2ArrayOfT) {
/* 3790 */         Object[] arrayOfObject = this.s.toArray((param2ArrayOfT.length == 0) ? (Object[])param2ArrayOfT : Arrays.<Object>copyOf((Object[])param2ArrayOfT, 0));
/*      */         
/* 3792 */         for (byte b = 0; b < arrayOfObject.length; b++) {
/* 3793 */           arrayOfObject[b] = checkedEntry((Map.Entry<?, ?>)arrayOfObject[b], this.valueType);
/*      */         }
/* 3795 */         if (arrayOfObject.length > param2ArrayOfT.length) {
/* 3796 */           return (T[])arrayOfObject;
/*      */         }
/* 3798 */         System.arraycopy(arrayOfObject, 0, param2ArrayOfT, 0, arrayOfObject.length);
/* 3799 */         if (param2ArrayOfT.length > arrayOfObject.length)
/* 3800 */           param2ArrayOfT[arrayOfObject.length] = null; 
/* 3801 */         return param2ArrayOfT;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean contains(Object param2Object) {
/* 3811 */         if (!(param2Object instanceof Map.Entry))
/* 3812 */           return false; 
/* 3813 */         Map.Entry<?, ?> entry = (Map.Entry)param2Object;
/* 3814 */         return this.s.contains((entry instanceof CheckedEntry) ? entry : 
/* 3815 */             checkedEntry(entry, this.valueType));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean containsAll(Collection<?> param2Collection) {
/* 3824 */         for (Object object : param2Collection) {
/* 3825 */           if (!contains(object))
/* 3826 */             return false; 
/* 3827 */         }  return true;
/*      */       }
/*      */       
/*      */       public boolean remove(Object param2Object) {
/* 3831 */         if (!(param2Object instanceof Map.Entry))
/* 3832 */           return false; 
/* 3833 */         return this.s.remove(new AbstractMap.SimpleImmutableEntry<>((Map.Entry<?, ?>)param2Object));
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean removeAll(Collection<?> param2Collection) {
/* 3838 */         return batchRemove(param2Collection, false);
/*      */       }
/*      */       public boolean retainAll(Collection<?> param2Collection) {
/* 3841 */         return batchRemove(param2Collection, true);
/*      */       }
/*      */       private boolean batchRemove(Collection<?> param2Collection, boolean param2Boolean) {
/* 3844 */         Objects.requireNonNull(param2Collection);
/* 3845 */         boolean bool = false;
/* 3846 */         Iterator<Map.Entry<K, V>> iterator = iterator();
/* 3847 */         while (iterator.hasNext()) {
/* 3848 */           if (param2Collection.contains(iterator.next()) != param2Boolean) {
/* 3849 */             iterator.remove();
/* 3850 */             bool = true;
/*      */           } 
/*      */         } 
/* 3853 */         return bool;
/*      */       }
/*      */       
/*      */       public boolean equals(Object param2Object) {
/* 3857 */         if (param2Object == this)
/* 3858 */           return true; 
/* 3859 */         if (!(param2Object instanceof Set))
/* 3860 */           return false; 
/* 3861 */         Set<?> set = (Set)param2Object;
/* 3862 */         return (set.size() == this.s.size() && 
/* 3863 */           containsAll(set));
/*      */       }
/*      */ 
/*      */       
/*      */       static <K, V, T> CheckedEntry<K, V, T> checkedEntry(Map.Entry<K, V> param2Entry, Class<T> param2Class) {
/* 3868 */         return new CheckedEntry<>(param2Entry, param2Class);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       private static class CheckedEntry<K, V, T>
/*      */         implements Map.Entry<K, V>
/*      */       {
/*      */         private final Map.Entry<K, V> e;
/*      */ 
/*      */         
/*      */         private final Class<T> valueType;
/*      */ 
/*      */         
/*      */         CheckedEntry(Map.Entry<K, V> param3Entry, Class<T> param3Class) {
/* 3883 */           this.e = Objects.<Map.Entry<K, V>>requireNonNull(param3Entry);
/* 3884 */           this.valueType = Objects.<Class<T>>requireNonNull(param3Class);
/*      */         }
/*      */         
/* 3887 */         public K getKey() { return this.e.getKey(); }
/* 3888 */         public V getValue() { return this.e.getValue(); }
/* 3889 */         public int hashCode() { return this.e.hashCode(); } public String toString() {
/* 3890 */           return this.e.toString();
/*      */         }
/*      */         public V setValue(V param3V) {
/* 3893 */           if (param3V != null && !this.valueType.isInstance(param3V))
/* 3894 */             throw new ClassCastException(badValueMsg(param3V)); 
/* 3895 */           return this.e.setValue(param3V);
/*      */         }
/*      */         
/*      */         private String badValueMsg(Object param3Object) {
/* 3899 */           return "Attempt to insert " + param3Object.getClass() + " value into map with value type " + this.valueType;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean equals(Object param3Object) {
/* 3904 */           if (param3Object == this)
/* 3905 */             return true; 
/* 3906 */           if (!(param3Object instanceof Map.Entry))
/* 3907 */             return false; 
/* 3908 */           return this.e.equals(new AbstractMap.SimpleImmutableEntry<>((Map.Entry<?, ?>)param3Object));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
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
/*      */   public static <K, V> SortedMap<K, V> checkedSortedMap(SortedMap<K, V> paramSortedMap, Class<K> paramClass, Class<V> paramClass1) {
/* 3954 */     return new CheckedSortedMap<>(paramSortedMap, paramClass, paramClass1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class CheckedSortedMap<K, V>
/*      */     extends CheckedMap<K, V>
/*      */     implements SortedMap<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1599671320688067438L;
/*      */     
/*      */     private final SortedMap<K, V> sm;
/*      */ 
/*      */     
/*      */     CheckedSortedMap(SortedMap<K, V> param1SortedMap, Class<K> param1Class, Class<V> param1Class1) {
/* 3969 */       super(param1SortedMap, param1Class, param1Class1);
/* 3970 */       this.sm = param1SortedMap;
/*      */     }
/*      */     
/* 3973 */     public Comparator<? super K> comparator() { return this.sm.comparator(); }
/* 3974 */     public K firstKey() { return this.sm.firstKey(); } public K lastKey() {
/* 3975 */       return this.sm.lastKey();
/*      */     }
/*      */     public SortedMap<K, V> subMap(K param1K1, K param1K2) {
/* 3978 */       return Collections.checkedSortedMap(this.sm.subMap(param1K1, param1K2), this.keyType, this.valueType);
/*      */     }
/*      */     
/*      */     public SortedMap<K, V> headMap(K param1K) {
/* 3982 */       return Collections.checkedSortedMap(this.sm.headMap(param1K), this.keyType, this.valueType);
/*      */     }
/*      */     public SortedMap<K, V> tailMap(K param1K) {
/* 3985 */       return Collections.checkedSortedMap(this.sm.tailMap(param1K), this.keyType, this.valueType);
/*      */     }
/*      */   }
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
/*      */   public static <K, V> NavigableMap<K, V> checkedNavigableMap(NavigableMap<K, V> paramNavigableMap, Class<K> paramClass, Class<V> paramClass1) {
/* 4028 */     return new CheckedNavigableMap<>(paramNavigableMap, paramClass, paramClass1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class CheckedNavigableMap<K, V>
/*      */     extends CheckedSortedMap<K, V>
/*      */     implements NavigableMap<K, V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -4852462692372534096L;
/*      */     
/*      */     private final NavigableMap<K, V> nm;
/*      */ 
/*      */     
/*      */     CheckedNavigableMap(NavigableMap<K, V> param1NavigableMap, Class<K> param1Class, Class<V> param1Class1) {
/* 4043 */       super(param1NavigableMap, param1Class, param1Class1);
/* 4044 */       this.nm = param1NavigableMap;
/*      */     }
/*      */     
/* 4047 */     public Comparator<? super K> comparator() { return this.nm.comparator(); }
/* 4048 */     public K firstKey() { return this.nm.firstKey(); } public K lastKey() {
/* 4049 */       return this.nm.lastKey();
/*      */     }
/*      */     public Map.Entry<K, V> lowerEntry(K param1K) {
/* 4052 */       Map.Entry<K, V> entry = this.nm.lowerEntry(param1K);
/* 4053 */       return (null != entry) ? new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public K lowerKey(K param1K) {
/* 4058 */       return this.nm.lowerKey(param1K);
/*      */     }
/*      */     public Map.Entry<K, V> floorEntry(K param1K) {
/* 4061 */       Map.Entry<K, V> entry = this.nm.floorEntry(param1K);
/* 4062 */       return (null != entry) ? new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public K floorKey(K param1K) {
/* 4067 */       return this.nm.floorKey(param1K);
/*      */     }
/*      */     public Map.Entry<K, V> ceilingEntry(K param1K) {
/* 4070 */       Map.Entry<K, V> entry = this.nm.ceilingEntry(param1K);
/* 4071 */       return (null != entry) ? new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public K ceilingKey(K param1K) {
/* 4076 */       return this.nm.ceilingKey(param1K);
/*      */     }
/*      */     public Map.Entry<K, V> higherEntry(K param1K) {
/* 4079 */       Map.Entry<K, V> entry = this.nm.higherEntry(param1K);
/* 4080 */       return (null != entry) ? new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public K higherKey(K param1K) {
/* 4085 */       return this.nm.higherKey(param1K);
/*      */     }
/*      */     public Map.Entry<K, V> firstEntry() {
/* 4088 */       Map.Entry<K, V> entry = this.nm.firstEntry();
/* 4089 */       return (null != entry) ? new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> lastEntry() {
/* 4095 */       Map.Entry<K, V> entry = this.nm.lastEntry();
/* 4096 */       return (null != entry) ? new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> pollFirstEntry() {
/* 4102 */       Map.Entry<K, V> entry = this.nm.pollFirstEntry();
/* 4103 */       return (null == entry) ? null : new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Map.Entry<K, V> pollLastEntry() {
/* 4109 */       Map.Entry<K, V> entry = this.nm.pollLastEntry();
/* 4110 */       return (null == entry) ? null : new Collections.CheckedMap.CheckedEntrySet.CheckedEntry<>(entry, this.valueType);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> descendingMap() {
/* 4116 */       return Collections.checkedNavigableMap(this.nm.descendingMap(), this.keyType, this.valueType);
/*      */     }
/*      */     
/*      */     public NavigableSet<K> keySet() {
/* 4120 */       return navigableKeySet();
/*      */     }
/*      */     
/*      */     public NavigableSet<K> navigableKeySet() {
/* 4124 */       return Collections.checkedNavigableSet(this.nm.navigableKeySet(), this.keyType);
/*      */     }
/*      */     
/*      */     public NavigableSet<K> descendingKeySet() {
/* 4128 */       return Collections.checkedNavigableSet(this.nm.descendingKeySet(), this.keyType);
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> subMap(K param1K1, K param1K2) {
/* 4133 */       return Collections.checkedNavigableMap(this.nm.subMap(param1K1, true, param1K2, false), this.keyType, this.valueType);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> headMap(K param1K) {
/* 4139 */       return Collections.checkedNavigableMap(this.nm.headMap(param1K, false), this.keyType, this.valueType);
/*      */     }
/*      */ 
/*      */     
/*      */     public NavigableMap<K, V> tailMap(K param1K) {
/* 4144 */       return Collections.checkedNavigableMap(this.nm.tailMap(param1K, true), this.keyType, this.valueType);
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> subMap(K param1K1, boolean param1Boolean1, K param1K2, boolean param1Boolean2) {
/* 4148 */       return Collections.checkedNavigableMap(this.nm.subMap(param1K1, param1Boolean1, param1K2, param1Boolean2), this.keyType, this.valueType);
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> headMap(K param1K, boolean param1Boolean) {
/* 4152 */       return Collections.checkedNavigableMap(this.nm.headMap(param1K, param1Boolean), this.keyType, this.valueType);
/*      */     }
/*      */     
/*      */     public NavigableMap<K, V> tailMap(K param1K, boolean param1Boolean) {
/* 4156 */       return Collections.checkedNavigableMap(this.nm.tailMap(param1K, param1Boolean), this.keyType, this.valueType);
/*      */     }
/*      */   }
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
/*      */   public static <T> Iterator<T> emptyIterator() {
/* 4183 */     return EmptyIterator.EMPTY_ITERATOR;
/*      */   }
/*      */   private static class EmptyIterator<E> implements Iterator<E> { private EmptyIterator() {}
/*      */     
/* 4187 */     static final EmptyIterator<Object> EMPTY_ITERATOR = new EmptyIterator();
/*      */     
/*      */     public boolean hasNext() {
/* 4190 */       return false;
/* 4191 */     } public E next() { throw new NoSuchElementException(); } public void remove() {
/* 4192 */       throw new IllegalStateException();
/*      */     }
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 4195 */       Objects.requireNonNull(param1Consumer);
/*      */     } }
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
/*      */   public static <T> ListIterator<T> emptyListIterator() {
/* 4227 */     return EmptyListIterator.EMPTY_ITERATOR;
/*      */   }
/*      */   
/*      */   private static class EmptyListIterator<E>
/*      */     extends EmptyIterator<E>
/*      */     implements ListIterator<E>
/*      */   {
/* 4234 */     static final EmptyListIterator<Object> EMPTY_ITERATOR = new EmptyListIterator();
/*      */     
/*      */     public boolean hasPrevious() {
/* 4237 */       return false;
/* 4238 */     } public E previous() { throw new NoSuchElementException(); }
/* 4239 */     public int nextIndex() { return 0; }
/* 4240 */     public int previousIndex() { return -1; }
/* 4241 */     public void set(E param1E) { throw new IllegalStateException(); } public void add(E param1E) {
/* 4242 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
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
/*      */   public static <T> Enumeration<T> emptyEnumeration() {
/* 4264 */     return EmptyEnumeration.EMPTY_ENUMERATION;
/*      */   }
/*      */   
/*      */   private static class EmptyEnumeration<E> implements Enumeration<E> {
/* 4268 */     static final EmptyEnumeration<Object> EMPTY_ENUMERATION = new EmptyEnumeration();
/*      */     
/*      */     public boolean hasMoreElements() {
/* 4271 */       return false; } public E nextElement() {
/* 4272 */       throw new NoSuchElementException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4281 */   public static final Set EMPTY_SET = new EmptySet();
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
/*      */   public static final <T> Set<T> emptySet() {
/* 4304 */     return EMPTY_SET;
/*      */   }
/*      */   
/*      */   private static class EmptySet<E>
/*      */     extends AbstractSet<E>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1582296315990362920L;
/*      */     
/*      */     private EmptySet() {}
/*      */     
/*      */     public Iterator<E> iterator() {
/* 4316 */       return Collections.emptyIterator();
/*      */     }
/* 4318 */     public int size() { return 0; } public boolean isEmpty() {
/* 4319 */       return true;
/*      */     }
/* 4321 */     public boolean contains(Object param1Object) { return false; } public boolean containsAll(Collection<?> param1Collection) {
/* 4322 */       return param1Collection.isEmpty();
/*      */     } public Object[] toArray() {
/* 4324 */       return new Object[0];
/*      */     }
/*      */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 4327 */       if (param1ArrayOfT.length > 0)
/* 4328 */         param1ArrayOfT[0] = null; 
/* 4329 */       return param1ArrayOfT;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 4335 */       Objects.requireNonNull(param1Consumer);
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 4339 */       Objects.requireNonNull(param1Predicate);
/* 4340 */       return false;
/*      */     }
/*      */     public Spliterator<E> spliterator() {
/* 4343 */       return Spliterators.emptySpliterator();
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/* 4347 */       return Collections.EMPTY_SET;
/*      */     }
/*      */   }
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
/*      */   public static <E> SortedSet<E> emptySortedSet() {
/* 4369 */     return (SortedSet)UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
/*      */   }
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
/*      */   public static <E> NavigableSet<E> emptyNavigableSet() {
/* 4390 */     return (NavigableSet)UnmodifiableNavigableSet.EMPTY_NAVIGABLE_SET;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4399 */   public static final List EMPTY_LIST = new EmptyList();
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
/*      */   public static final <T> List<T> emptyList() {
/* 4423 */     return EMPTY_LIST;
/*      */   }
/*      */   
/*      */   private static class EmptyList<E>
/*      */     extends AbstractList<E>
/*      */     implements RandomAccess, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 8842843931221139166L;
/*      */     
/*      */     private EmptyList() {}
/*      */     
/*      */     public Iterator<E> iterator() {
/* 4435 */       return Collections.emptyIterator();
/*      */     }
/*      */     public ListIterator<E> listIterator() {
/* 4438 */       return Collections.emptyListIterator();
/*      */     }
/*      */     
/* 4441 */     public int size() { return 0; } public boolean isEmpty() {
/* 4442 */       return true;
/*      */     }
/* 4444 */     public boolean contains(Object param1Object) { return false; } public boolean containsAll(Collection<?> param1Collection) {
/* 4445 */       return param1Collection.isEmpty();
/*      */     } public Object[] toArray() {
/* 4447 */       return new Object[0];
/*      */     }
/*      */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 4450 */       if (param1ArrayOfT.length > 0)
/* 4451 */         param1ArrayOfT[0] = null; 
/* 4452 */       return param1ArrayOfT;
/*      */     }
/*      */     
/*      */     public E get(int param1Int) {
/* 4456 */       throw new IndexOutOfBoundsException("Index: " + param1Int);
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 4460 */       return (param1Object instanceof List && ((List)param1Object).isEmpty());
/*      */     }
/*      */     public int hashCode() {
/* 4463 */       return 1;
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 4467 */       Objects.requireNonNull(param1Predicate);
/* 4468 */       return false;
/*      */     }
/*      */     
/*      */     public void replaceAll(UnaryOperator<E> param1UnaryOperator) {
/* 4472 */       Objects.requireNonNull(param1UnaryOperator);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super E> param1Comparator) {}
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 4481 */       Objects.requireNonNull(param1Consumer);
/*      */     }
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 4485 */       return Spliterators.emptySpliterator();
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/* 4489 */       return Collections.EMPTY_LIST;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4500 */   public static final Map EMPTY_MAP = new EmptyMap<>();
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
/*      */   public static final <K, V> Map<K, V> emptyMap() {
/* 4522 */     return EMPTY_MAP;
/*      */   }
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
/*      */   public static final <K, V> SortedMap<K, V> emptySortedMap() {
/* 4543 */     return (SortedMap)UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
/*      */   }
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
/*      */   public static final <K, V> NavigableMap<K, V> emptyNavigableMap() {
/* 4564 */     return (NavigableMap)UnmodifiableNavigableMap.EMPTY_NAVIGABLE_MAP;
/*      */   }
/*      */   
/*      */   private static class EmptyMap<K, V>
/*      */     extends AbstractMap<K, V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 6428348081105594320L;
/*      */     
/*      */     private EmptyMap() {}
/*      */     
/*      */     public int size() {
/* 4576 */       return 0;
/* 4577 */     } public boolean isEmpty() { return true; }
/* 4578 */     public boolean containsKey(Object param1Object) { return false; }
/* 4579 */     public boolean containsValue(Object param1Object) { return false; }
/* 4580 */     public V get(Object param1Object) { return null; }
/* 4581 */     public Set<K> keySet() { return Collections.emptySet(); }
/* 4582 */     public Collection<V> values() { return Collections.emptySet(); } public Set<Map.Entry<K, V>> entrySet() {
/* 4583 */       return Collections.emptySet();
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/* 4586 */       return (param1Object instanceof Map && ((Map)param1Object).isEmpty());
/*      */     }
/*      */     public int hashCode() {
/* 4589 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V getOrDefault(Object param1Object, V param1V) {
/* 4595 */       return param1V;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(BiConsumer<? super K, ? super V> param1BiConsumer) {
/* 4600 */       Objects.requireNonNull(param1BiConsumer);
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 4605 */       Objects.requireNonNull(param1BiFunction);
/*      */     }
/*      */ 
/*      */     
/*      */     public V putIfAbsent(K param1K, V param1V) {
/* 4610 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object param1Object1, Object param1Object2) {
/* 4615 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean replace(K param1K, V param1V1, V param1V2) {
/* 4620 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public V replace(K param1K, V param1V) {
/* 4625 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfAbsent(K param1K, Function<? super K, ? extends V> param1Function) {
/* 4631 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfPresent(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 4637 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V compute(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 4643 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V merge(K param1K, V param1V, BiFunction<? super V, ? super V, ? extends V> param1BiFunction) {
/* 4649 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 4654 */       return Collections.EMPTY_MAP;
/*      */     }
/*      */   }
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
/*      */   public static <T> Set<T> singleton(T paramT) {
/* 4669 */     return new SingletonSet<>(paramT);
/*      */   }
/*      */   
/*      */   static <E> Iterator<E> singletonIterator(final E e) {
/* 4673 */     return new Iterator<E>() { private boolean hasNext = true;
/*      */         
/*      */         public boolean hasNext() {
/* 4676 */           return this.hasNext;
/*      */         }
/*      */         public E next() {
/* 4679 */           if (this.hasNext) {
/* 4680 */             this.hasNext = false;
/* 4681 */             return (E)e;
/*      */           } 
/* 4683 */           throw new NoSuchElementException();
/*      */         }
/*      */         public void remove() {
/* 4686 */           throw new UnsupportedOperationException();
/*      */         }
/*      */         
/*      */         public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 4690 */           Objects.requireNonNull(param1Consumer);
/* 4691 */           if (this.hasNext) {
/* 4692 */             param1Consumer.accept((E)e);
/* 4693 */             this.hasNext = false;
/*      */           } 
/*      */         } }
/*      */       ;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> Spliterator<T> singletonSpliterator(final T element) {
/* 4706 */     return new Spliterator<T>() {
/* 4707 */         long est = 1L;
/*      */ 
/*      */         
/*      */         public Spliterator<T> trySplit() {
/* 4711 */           return null;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean tryAdvance(Consumer<? super T> param1Consumer) {
/* 4716 */           Objects.requireNonNull(param1Consumer);
/* 4717 */           if (this.est > 0L) {
/* 4718 */             this.est--;
/* 4719 */             param1Consumer.accept((T)element);
/* 4720 */             return true;
/*      */           } 
/* 4722 */           return false;
/*      */         }
/*      */ 
/*      */         
/*      */         public void forEachRemaining(Consumer<? super T> param1Consumer) {
/* 4727 */           tryAdvance(param1Consumer);
/*      */         }
/*      */ 
/*      */         
/*      */         public long estimateSize() {
/* 4732 */           return this.est;
/*      */         }
/*      */ 
/*      */         
/*      */         public int characteristics() {
/* 4737 */           boolean bool = (element != null) ? true : false;
/*      */           
/* 4739 */           return bool | 0x40 | 0x4000 | 0x400 | 0x1 | 0x10;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SingletonSet<E>
/*      */     extends AbstractSet<E>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 3193687207550431679L;
/*      */     
/*      */     private final E element;
/*      */ 
/*      */     
/*      */     SingletonSet(E param1E) {
/* 4756 */       this.element = param1E;
/*      */     }
/*      */     public Iterator<E> iterator() {
/* 4759 */       return Collections.singletonIterator(this.element);
/*      */     }
/*      */     public int size() {
/* 4762 */       return 1;
/*      */     } public boolean contains(Object param1Object) {
/* 4764 */       return Collections.eq(param1Object, this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 4769 */       param1Consumer.accept(this.element);
/*      */     }
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 4773 */       return Collections.singletonSpliterator(this.element);
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 4777 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
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
/*      */   public static <T> List<T> singletonList(T paramT) {
/* 4791 */     return new SingletonList<>(paramT);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SingletonList<E>
/*      */     extends AbstractList<E>
/*      */     implements RandomAccess, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 3093736618740652951L;
/*      */     
/*      */     private final E element;
/*      */ 
/*      */     
/*      */     SingletonList(E param1E) {
/* 4805 */       this.element = param1E;
/*      */     }
/*      */     public Iterator<E> iterator() {
/* 4808 */       return Collections.singletonIterator(this.element);
/*      */     }
/*      */     public int size() {
/* 4811 */       return 1;
/*      */     } public boolean contains(Object param1Object) {
/* 4813 */       return Collections.eq(param1Object, this.element);
/*      */     }
/*      */     public E get(int param1Int) {
/* 4816 */       if (param1Int != 0)
/* 4817 */         throw new IndexOutOfBoundsException("Index: " + param1Int + ", Size: 1"); 
/* 4818 */       return this.element;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 4824 */       param1Consumer.accept(this.element);
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 4828 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void replaceAll(UnaryOperator<E> param1UnaryOperator) {
/* 4832 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void sort(Comparator<? super E> param1Comparator) {}
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 4839 */       return Collections.singletonSpliterator(this.element);
/*      */     }
/*      */   }
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
/*      */   public static <K, V> Map<K, V> singletonMap(K paramK, V paramV) {
/* 4856 */     return new SingletonMap<>(paramK, paramV);
/*      */   }
/*      */   
/*      */   private static class SingletonMap<K, V>
/*      */     extends AbstractMap<K, V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -6979724477215052911L;
/*      */     private final K k;
/*      */     private final V v;
/*      */     private transient Set<K> keySet;
/*      */     private transient Set<Map.Entry<K, V>> entrySet;
/*      */     private transient Collection<V> values;
/*      */     
/*      */     SingletonMap(K param1K, V param1V) {
/* 4871 */       this.k = param1K;
/* 4872 */       this.v = param1V;
/*      */     }
/*      */     
/* 4875 */     public int size() { return 1; }
/* 4876 */     public boolean isEmpty() { return false; }
/* 4877 */     public boolean containsKey(Object param1Object) { return Collections.eq(param1Object, this.k); }
/* 4878 */     public boolean containsValue(Object param1Object) { return Collections.eq(param1Object, this.v); } public V get(Object param1Object) {
/* 4879 */       return Collections.eq(param1Object, this.k) ? this.v : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<K> keySet() {
/* 4886 */       if (this.keySet == null)
/* 4887 */         this.keySet = Collections.singleton(this.k); 
/* 4888 */       return this.keySet;
/*      */     }
/*      */     
/*      */     public Set<Map.Entry<K, V>> entrySet() {
/* 4892 */       if (this.entrySet == null) {
/* 4893 */         this.entrySet = Collections.singleton(new AbstractMap.SimpleImmutableEntry<>(this.k, this.v));
/*      */       }
/* 4895 */       return this.entrySet;
/*      */     }
/*      */     
/*      */     public Collection<V> values() {
/* 4899 */       if (this.values == null)
/* 4900 */         this.values = Collections.singleton(this.v); 
/* 4901 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V getOrDefault(Object param1Object, V param1V) {
/* 4907 */       return Collections.eq(param1Object, this.k) ? this.v : param1V;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(BiConsumer<? super K, ? super V> param1BiConsumer) {
/* 4912 */       param1BiConsumer.accept(this.k, this.v);
/*      */     }
/*      */ 
/*      */     
/*      */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 4917 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public V putIfAbsent(K param1K, V param1V) {
/* 4922 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object param1Object1, Object param1Object2) {
/* 4927 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean replace(K param1K, V param1V1, V param1V2) {
/* 4932 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public V replace(K param1K, V param1V) {
/* 4937 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfAbsent(K param1K, Function<? super K, ? extends V> param1Function) {
/* 4943 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V computeIfPresent(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 4949 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V compute(K param1K, BiFunction<? super K, ? super V, ? extends V> param1BiFunction) {
/* 4955 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V merge(K param1K, V param1V, BiFunction<? super V, ? super V, ? extends V> param1BiFunction) {
/* 4961 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
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
/*      */   public static <T> List<T> nCopies(int paramInt, T paramT) {
/* 4985 */     if (paramInt < 0)
/* 4986 */       throw new IllegalArgumentException("List length = " + paramInt); 
/* 4987 */     return new CopiesList<>(paramInt, paramT);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class CopiesList<E>
/*      */     extends AbstractList<E>
/*      */     implements RandomAccess, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2739099268398711800L;
/*      */     
/*      */     final int n;
/*      */     
/*      */     final E element;
/*      */ 
/*      */     
/*      */     CopiesList(int param1Int, E param1E) {
/* 5003 */       assert param1Int >= 0;
/* 5004 */       this.n = param1Int;
/* 5005 */       this.element = param1E;
/*      */     }
/*      */     
/*      */     public int size() {
/* 5009 */       return this.n;
/*      */     }
/*      */     
/*      */     public boolean contains(Object param1Object) {
/* 5013 */       return (this.n != 0 && Collections.eq(param1Object, this.element));
/*      */     }
/*      */     
/*      */     public int indexOf(Object param1Object) {
/* 5017 */       return contains(param1Object) ? 0 : -1;
/*      */     }
/*      */     
/*      */     public int lastIndexOf(Object param1Object) {
/* 5021 */       return contains(param1Object) ? (this.n - 1) : -1;
/*      */     }
/*      */     
/*      */     public E get(int param1Int) {
/* 5025 */       if (param1Int < 0 || param1Int >= this.n) {
/* 5026 */         throw new IndexOutOfBoundsException("Index: " + param1Int + ", Size: " + this.n);
/*      */       }
/* 5028 */       return this.element;
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/* 5032 */       Object[] arrayOfObject = new Object[this.n];
/* 5033 */       if (this.element != null)
/* 5034 */         Arrays.fill(arrayOfObject, 0, this.n, this.element); 
/* 5035 */       return arrayOfObject;
/*      */     }
/*      */ 
/*      */     
/*      */     public <T> T[] toArray(T[] param1ArrayOfT) {
/* 5040 */       int i = this.n;
/* 5041 */       if (param1ArrayOfT.length < i) {
/*      */         
/* 5043 */         param1ArrayOfT = (T[])Array.newInstance(param1ArrayOfT.getClass().getComponentType(), i);
/* 5044 */         if (this.element != null)
/* 5045 */           Arrays.fill((Object[])param1ArrayOfT, 0, i, this.element); 
/*      */       } else {
/* 5047 */         Arrays.fill((Object[])param1ArrayOfT, 0, i, this.element);
/* 5048 */         if (param1ArrayOfT.length > i)
/* 5049 */           param1ArrayOfT[i] = null; 
/*      */       } 
/* 5051 */       return param1ArrayOfT;
/*      */     }
/*      */     
/*      */     public List<E> subList(int param1Int1, int param1Int2) {
/* 5055 */       if (param1Int1 < 0)
/* 5056 */         throw new IndexOutOfBoundsException("fromIndex = " + param1Int1); 
/* 5057 */       if (param1Int2 > this.n)
/* 5058 */         throw new IndexOutOfBoundsException("toIndex = " + param1Int2); 
/* 5059 */       if (param1Int1 > param1Int2) {
/* 5060 */         throw new IllegalArgumentException("fromIndex(" + param1Int1 + ") > toIndex(" + param1Int2 + ")");
/*      */       }
/* 5062 */       return new CopiesList(param1Int2 - param1Int1, this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 5067 */       if (this.n == 0) return 1;
/*      */ 
/*      */ 
/*      */       
/* 5071 */       int i = 31;
/* 5072 */       int j = 1;
/* 5073 */       for (int k = Integer.numberOfLeadingZeros(this.n) + 1; k < 32; k++) {
/* 5074 */         j *= i + 1;
/* 5075 */         i *= i;
/* 5076 */         if (this.n << k < 0) {
/* 5077 */           i *= 31;
/* 5078 */           j = j * 31 + 1;
/*      */         } 
/*      */       } 
/* 5081 */       return i + j * ((this.element == null) ? 0 : this.element.hashCode());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 5086 */       if (param1Object == this)
/* 5087 */         return true; 
/* 5088 */       if (param1Object instanceof CopiesList) {
/* 5089 */         CopiesList copiesList = (CopiesList)param1Object;
/* 5090 */         return (this.n == copiesList.n && (this.n == 0 || Collections.eq(this.element, copiesList.element)));
/*      */       } 
/* 5092 */       if (!(param1Object instanceof List)) {
/* 5093 */         return false;
/*      */       }
/* 5095 */       int i = this.n;
/* 5096 */       E e = this.element;
/* 5097 */       Iterator iterator = ((List)param1Object).iterator();
/* 5098 */       if (e == null) {
/* 5099 */         while (iterator.hasNext() && i-- > 0) {
/* 5100 */           if (iterator.next() != null)
/* 5101 */             return false; 
/*      */         } 
/*      */       } else {
/* 5104 */         while (iterator.hasNext() && i-- > 0) {
/* 5105 */           if (!e.equals(iterator.next()))
/* 5106 */             return false; 
/*      */         } 
/*      */       } 
/* 5109 */       return (i == 0 && !iterator.hasNext());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Stream<E> stream() {
/* 5115 */       return IntStream.range(0, this.n).mapToObj(param1Int -> this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public Stream<E> parallelStream() {
/* 5120 */       return IntStream.range(0, this.n).parallel().mapToObj(param1Int -> this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 5125 */       return stream().spliterator();
/*      */     }
/*      */     
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws IOException, ClassNotFoundException {
/* 5129 */       param1ObjectInputStream.defaultReadObject();
/* 5130 */       SharedSecrets.getJavaOISAccess().checkArray(param1ObjectInputStream, Object[].class, this.n);
/*      */     }
/*      */   }
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
/*      */   public static <T> Comparator<T> reverseOrder() {
/* 5156 */     return ReverseComparator.REVERSE_ORDER;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ReverseComparator
/*      */     implements Comparator<Comparable<Object>>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7207038068494060240L;
/*      */ 
/*      */     
/* 5167 */     static final ReverseComparator REVERSE_ORDER = new ReverseComparator();
/*      */ 
/*      */     
/*      */     public int compare(Comparable<Object> param1Comparable1, Comparable<Object> param1Comparable2) {
/* 5171 */       return param1Comparable2.compareTo(param1Comparable1);
/*      */     }
/*      */     private Object readResolve() {
/* 5174 */       return Collections.reverseOrder();
/*      */     }
/*      */     
/*      */     public Comparator<Comparable<Object>> reversed() {
/* 5178 */       return Comparator.naturalOrder();
/*      */     }
/*      */   }
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
/*      */   public static <T> Comparator<T> reverseOrder(Comparator<T> paramComparator) {
/* 5200 */     if (paramComparator == null) {
/* 5201 */       return reverseOrder();
/*      */     }
/* 5203 */     if (paramComparator instanceof ReverseComparator2) {
/* 5204 */       return ((ReverseComparator2)paramComparator).cmp;
/*      */     }
/* 5206 */     return new ReverseComparator2<>(paramComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ReverseComparator2<T>
/*      */     implements Comparator<T>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 4374092139857L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final Comparator<T> cmp;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ReverseComparator2(Comparator<T> param1Comparator) {
/* 5227 */       assert param1Comparator != null;
/* 5228 */       this.cmp = param1Comparator;
/*      */     }
/*      */     
/*      */     public int compare(T param1T1, T param1T2) {
/* 5232 */       return this.cmp.compare(param1T2, param1T1);
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 5236 */       return (param1Object == this || (param1Object instanceof ReverseComparator2 && this.cmp
/*      */         
/* 5238 */         .equals(((ReverseComparator2)param1Object).cmp)));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 5242 */       return this.cmp.hashCode() ^ Integer.MIN_VALUE;
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<T> reversed() {
/* 5247 */       return this.cmp;
/*      */     }
/*      */   }
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
/*      */   public static <T> Enumeration<T> enumeration(final Collection<T> c) {
/* 5262 */     return new Enumeration<T>() {
/* 5263 */         private final Iterator<T> i = c.iterator();
/*      */         
/*      */         public boolean hasMoreElements() {
/* 5266 */           return this.i.hasNext();
/*      */         }
/*      */         
/*      */         public T nextElement() {
/* 5270 */           return this.i.next();
/*      */         }
/*      */       };
/*      */   }
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
/*      */   public static <T> ArrayList<T> list(Enumeration<T> paramEnumeration) {
/* 5292 */     ArrayList<T> arrayList = new ArrayList();
/* 5293 */     while (paramEnumeration.hasMoreElements())
/* 5294 */       arrayList.add(paramEnumeration.nextElement()); 
/* 5295 */     return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean eq(Object paramObject1, Object paramObject2) {
/* 5304 */     return (paramObject1 == null) ? ((paramObject2 == null)) : paramObject1.equals(paramObject2);
/*      */   }
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
/*      */   public static int frequency(Collection<?> paramCollection, Object paramObject) {
/* 5321 */     byte b = 0;
/* 5322 */     if (paramObject == null)
/* 5323 */     { for (Object object : paramCollection) {
/* 5324 */         if (object == null)
/* 5325 */           b++; 
/*      */       }  }
/* 5327 */     else { for (Object object : paramCollection) {
/* 5328 */         if (paramObject.equals(object))
/* 5329 */           b++; 
/*      */       }  }
/* 5331 */      return b;
/*      */   }
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
/*      */   public static boolean disjoint(Collection<?> paramCollection1, Collection<?> paramCollection2) {
/* 5375 */     Collection<?> collection1 = paramCollection2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5381 */     Collection<?> collection2 = paramCollection1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5388 */     if (paramCollection1 instanceof Set) {
/*      */ 
/*      */       
/* 5391 */       collection2 = paramCollection2;
/* 5392 */       collection1 = paramCollection1;
/* 5393 */     } else if (!(paramCollection2 instanceof Set)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5400 */       int i = paramCollection1.size();
/* 5401 */       int j = paramCollection2.size();
/* 5402 */       if (i == 0 || j == 0)
/*      */       {
/* 5404 */         return true;
/*      */       }
/*      */       
/* 5407 */       if (i > j) {
/* 5408 */         collection2 = paramCollection2;
/* 5409 */         collection1 = paramCollection1;
/*      */       } 
/*      */     } 
/*      */     
/* 5413 */     for (Object object : collection2) {
/* 5414 */       if (collection1.contains(object))
/*      */       {
/* 5416 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 5421 */     return true;
/*      */   }
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
/*      */   @SafeVarargs
/*      */   public static <T> boolean addAll(Collection<? super T> paramCollection, T... paramVarArgs) {
/* 5453 */     boolean bool = false;
/* 5454 */     for (T t : paramVarArgs)
/* 5455 */       bool |= paramCollection.add(t); 
/* 5456 */     return bool;
/*      */   }
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
/*      */   public static <E> Set<E> newSetFromMap(Map<E, Boolean> paramMap) {
/* 5491 */     return new SetFromMap<>(paramMap);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SetFromMap<E>
/*      */     extends AbstractSet<E>
/*      */     implements Set<E>, Serializable
/*      */   {
/*      */     private final Map<E, Boolean> m;
/*      */     private transient Set<E> s;
/*      */     private static final long serialVersionUID = 2454657854757543876L;
/*      */     
/*      */     SetFromMap(Map<E, Boolean> param1Map) {
/* 5504 */       if (!param1Map.isEmpty())
/* 5505 */         throw new IllegalArgumentException("Map is non-empty"); 
/* 5506 */       this.m = param1Map;
/* 5507 */       this.s = param1Map.keySet();
/*      */     }
/*      */     
/* 5510 */     public void clear() { this.m.clear(); }
/* 5511 */     public int size() { return this.m.size(); }
/* 5512 */     public boolean isEmpty() { return this.m.isEmpty(); }
/* 5513 */     public boolean contains(Object param1Object) { return this.m.containsKey(param1Object); }
/* 5514 */     public boolean remove(Object param1Object) { return (this.m.remove(param1Object) != null); }
/* 5515 */     public boolean add(E param1E) { return (this.m.put(param1E, Boolean.TRUE) == null); }
/* 5516 */     public Iterator<E> iterator() { return this.s.iterator(); }
/* 5517 */     public Object[] toArray() { return this.s.toArray(); }
/* 5518 */     public <T> T[] toArray(T[] param1ArrayOfT) { return this.s.toArray(param1ArrayOfT); }
/* 5519 */     public String toString() { return this.s.toString(); }
/* 5520 */     public int hashCode() { return this.s.hashCode(); }
/* 5521 */     public boolean equals(Object param1Object) { return (param1Object == this || this.s.equals(param1Object)); }
/* 5522 */     public boolean containsAll(Collection<?> param1Collection) { return this.s.containsAll(param1Collection); }
/* 5523 */     public boolean removeAll(Collection<?> param1Collection) { return this.s.removeAll(param1Collection); } public boolean retainAll(Collection<?> param1Collection) {
/* 5524 */       return this.s.retainAll(param1Collection);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 5530 */       this.s.forEach(param1Consumer);
/*      */     }
/*      */     
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 5534 */       return this.s.removeIf(param1Predicate);
/*      */     }
/*      */     
/*      */     public Spliterator<E> spliterator() {
/* 5538 */       return this.s.spliterator();
/*      */     } public Stream<E> stream() {
/* 5540 */       return this.s.stream();
/*      */     } public Stream<E> parallelStream() {
/* 5542 */       return this.s.parallelStream();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws IOException, ClassNotFoundException {
/* 5549 */       param1ObjectInputStream.defaultReadObject();
/* 5550 */       this.s = this.m.keySet();
/*      */     }
/*      */   }
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
/*      */   public static <T> Queue<T> asLifoQueue(Deque<T> paramDeque) {
/* 5573 */     return new AsLIFOQueue<>(paramDeque);
/*      */   }
/*      */   
/*      */   static class AsLIFOQueue<E>
/*      */     extends AbstractQueue<E>
/*      */     implements Queue<E>, Serializable {
/*      */     private static final long serialVersionUID = 1802017725587941708L;
/*      */     private final Deque<E> q;
/*      */     
/*      */     AsLIFOQueue(Deque<E> param1Deque) {
/* 5583 */       this.q = param1Deque;
/* 5584 */     } public boolean add(E param1E) { this.q.addFirst(param1E); return true; }
/* 5585 */     public boolean offer(E param1E) { return this.q.offerFirst(param1E); }
/* 5586 */     public E poll() { return this.q.pollFirst(); }
/* 5587 */     public E remove() { return this.q.removeFirst(); }
/* 5588 */     public E peek() { return this.q.peekFirst(); }
/* 5589 */     public E element() { return this.q.getFirst(); }
/* 5590 */     public void clear() { this.q.clear(); }
/* 5591 */     public int size() { return this.q.size(); }
/* 5592 */     public boolean isEmpty() { return this.q.isEmpty(); }
/* 5593 */     public boolean contains(Object param1Object) { return this.q.contains(param1Object); }
/* 5594 */     public boolean remove(Object param1Object) { return this.q.remove(param1Object); }
/* 5595 */     public Iterator<E> iterator() { return this.q.iterator(); }
/* 5596 */     public Object[] toArray() { return this.q.toArray(); }
/* 5597 */     public <T> T[] toArray(T[] param1ArrayOfT) { return (T[])this.q.toArray((Object[])param1ArrayOfT); }
/* 5598 */     public String toString() { return this.q.toString(); }
/* 5599 */     public boolean containsAll(Collection<?> param1Collection) { return this.q.containsAll(param1Collection); }
/* 5600 */     public boolean removeAll(Collection<?> param1Collection) { return this.q.removeAll(param1Collection); } public boolean retainAll(Collection<?> param1Collection) {
/* 5601 */       return this.q.retainAll(param1Collection);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super E> param1Consumer) {
/* 5606 */       this.q.forEach(param1Consumer);
/*      */     }
/*      */     public boolean removeIf(Predicate<? super E> param1Predicate) {
/* 5609 */       return this.q.removeIf(param1Predicate);
/*      */     }
/*      */     public Spliterator<E> spliterator() {
/* 5612 */       return this.q.spliterator();
/*      */     } public Stream<E> stream() {
/* 5614 */       return this.q.stream();
/*      */     } public Stream<E> parallelStream() {
/* 5616 */       return this.q.parallelStream();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Collections.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */