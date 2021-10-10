/*     */ package javax.swing;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RowFilter<M, I>
/*     */ {
/*     */   public enum ComparisonType
/*     */   {
/* 110 */     BEFORE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     AFTER,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     EQUAL,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     NOT_EQUAL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkIndices(int[] paramArrayOfint) {
/* 136 */     for (int i = paramArrayOfint.length - 1; i >= 0; i--) {
/* 137 */       if (paramArrayOfint[i] < 0) {
/* 138 */         throw new IllegalArgumentException("Index must be >= 0");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <M, I> RowFilter<M, I> regexFilter(String paramString, int... paramVarArgs) {
/* 176 */     return new RegexFilter(Pattern.compile(paramString), paramVarArgs);
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
/*     */   public static <M, I> RowFilter<M, I> dateFilter(ComparisonType paramComparisonType, Date paramDate, int... paramVarArgs) {
/* 204 */     return new DateFilter(paramComparisonType, paramDate.getTime(), paramVarArgs);
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
/*     */   public static <M, I> RowFilter<M, I> numberFilter(ComparisonType paramComparisonType, Number paramNumber, int... paramVarArgs) {
/* 227 */     return new NumberFilter(paramComparisonType, paramNumber, paramVarArgs);
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
/*     */   public static <M, I> RowFilter<M, I> orFilter(Iterable<? extends RowFilter<? super M, ? super I>> paramIterable) {
/* 253 */     return new OrFilter<>(paramIterable);
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
/*     */   public static <M, I> RowFilter<M, I> andFilter(Iterable<? extends RowFilter<? super M, ? super I>> paramIterable) {
/* 279 */     return new AndFilter<>(paramIterable);
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
/*     */   public static <M, I> RowFilter<M, I> notFilter(RowFilter<M, I> paramRowFilter) {
/* 292 */     return new NotFilter<>(paramRowFilter);
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
/*     */   public abstract boolean include(Entry<? extends M, ? extends I> paramEntry);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class Entry<M, I>
/*     */   {
/*     */     public abstract M getModel();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract int getValueCount();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract Object getValue(int param1Int);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getStringValue(int param1Int) {
/* 384 */       Object object = getValue(param1Int);
/* 385 */       return (object == null) ? "" : object.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract I getIdentifier();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class GeneralFilter
/*     */     extends RowFilter<Object, Object>
/*     */   {
/*     */     private int[] columns;
/*     */ 
/*     */ 
/*     */     
/*     */     GeneralFilter(int[] param1ArrayOfint) {
/* 404 */       RowFilter.checkIndices(param1ArrayOfint);
/* 405 */       this.columns = param1ArrayOfint;
/*     */     }
/*     */     
/*     */     public boolean include(RowFilter.Entry<? extends Object, ? extends Object> param1Entry) {
/* 409 */       int i = param1Entry.getValueCount();
/* 410 */       if (this.columns.length > 0) {
/* 411 */         for (int j = this.columns.length - 1; j >= 0; j--) {
/* 412 */           int k = this.columns[j];
/* 413 */           if (k < i && 
/* 414 */             include(param1Entry, k)) {
/* 415 */             return true;
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 421 */         while (--i >= 0) {
/* 422 */           if (include(param1Entry, i)) {
/* 423 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/* 427 */       return false;
/*     */     }
/*     */     
/*     */     protected abstract boolean include(RowFilter.Entry<? extends Object, ? extends Object> param1Entry, int param1Int);
/*     */   }
/*     */   
/*     */   private static class RegexFilter
/*     */     extends GeneralFilter
/*     */   {
/*     */     private Matcher matcher;
/*     */     
/*     */     RegexFilter(Pattern param1Pattern, int[] param1ArrayOfint) {
/* 439 */       super(param1ArrayOfint);
/* 440 */       if (param1Pattern == null) {
/* 441 */         throw new IllegalArgumentException("Pattern must be non-null");
/*     */       }
/* 443 */       this.matcher = param1Pattern.matcher("");
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean include(RowFilter.Entry<? extends Object, ? extends Object> param1Entry, int param1Int) {
/* 448 */       this.matcher.reset(param1Entry.getStringValue(param1Int));
/* 449 */       return this.matcher.find();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DateFilter
/*     */     extends GeneralFilter {
/*     */     private long date;
/*     */     private RowFilter.ComparisonType type;
/*     */     
/*     */     DateFilter(RowFilter.ComparisonType param1ComparisonType, long param1Long, int[] param1ArrayOfint) {
/* 459 */       super(param1ArrayOfint);
/* 460 */       if (param1ComparisonType == null) {
/* 461 */         throw new IllegalArgumentException("type must be non-null");
/*     */       }
/* 463 */       this.type = param1ComparisonType;
/* 464 */       this.date = param1Long;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean include(RowFilter.Entry<? extends Object, ? extends Object> param1Entry, int param1Int) {
/* 469 */       Object object = param1Entry.getValue(param1Int);
/*     */       
/* 471 */       if (object instanceof Date) {
/* 472 */         long l = ((Date)object).getTime();
/* 473 */         switch (this.type) {
/*     */           case BEFORE:
/* 475 */             return (l < this.date);
/*     */           case AFTER:
/* 477 */             return (l > this.date);
/*     */           case EQUAL:
/* 479 */             return (l == this.date);
/*     */           case NOT_EQUAL:
/* 481 */             return (l != this.date);
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 486 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class NumberFilter
/*     */     extends GeneralFilter
/*     */   {
/*     */     private boolean isComparable;
/*     */     private Number number;
/*     */     private RowFilter.ComparisonType type;
/*     */     
/*     */     NumberFilter(RowFilter.ComparisonType param1ComparisonType, Number param1Number, int[] param1ArrayOfint) {
/* 499 */       super(param1ArrayOfint);
/* 500 */       if (param1ComparisonType == null || param1Number == null) {
/* 501 */         throw new IllegalArgumentException("type and number must be non-null");
/*     */       }
/*     */       
/* 504 */       this.type = param1ComparisonType;
/* 505 */       this.number = param1Number;
/* 506 */       this.isComparable = param1Number instanceof Comparable;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean include(RowFilter.Entry<? extends Object, ? extends Object> param1Entry, int param1Int) {
/* 512 */       Object object = param1Entry.getValue(param1Int);
/*     */       
/* 514 */       if (object instanceof Number) {
/* 515 */         int i; boolean bool = true;
/*     */         
/* 517 */         Class<?> clazz = object.getClass();
/* 518 */         if (this.number.getClass() == clazz && this.isComparable) {
/* 519 */           i = ((Comparable<Object>)this.number).compareTo(object);
/*     */         } else {
/*     */           
/* 522 */           i = longCompare((Number)object);
/*     */         } 
/* 524 */         switch (this.type) {
/*     */           case BEFORE:
/* 526 */             return (i > 0);
/*     */           case AFTER:
/* 528 */             return (i < 0);
/*     */           case EQUAL:
/* 530 */             return (i == 0);
/*     */           case NOT_EQUAL:
/* 532 */             return (i != 0);
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 537 */       return false;
/*     */     }
/*     */     
/*     */     private int longCompare(Number param1Number) {
/* 541 */       long l = this.number.longValue() - param1Number.longValue();
/*     */       
/* 543 */       if (l < 0L) {
/* 544 */         return -1;
/*     */       }
/* 546 */       if (l > 0L) {
/* 547 */         return 1;
/*     */       }
/* 549 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class OrFilter<M, I>
/*     */     extends RowFilter<M, I>
/*     */   {
/* 558 */     List<RowFilter<? super M, ? super I>> filters = new ArrayList<>(); OrFilter(Iterable<? extends RowFilter<? super M, ? super I>> param1Iterable) {
/* 559 */       for (RowFilter<? super M, ? super I> rowFilter : param1Iterable) {
/* 560 */         if (rowFilter == null) {
/* 561 */           throw new IllegalArgumentException("Filter must be non-null");
/*     */         }
/*     */         
/* 564 */         this.filters.add(rowFilter);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean include(RowFilter.Entry<? extends M, ? extends I> param1Entry) {
/* 569 */       for (RowFilter<M, I> rowFilter : this.filters) {
/* 570 */         if (rowFilter.include(param1Entry)) {
/* 571 */           return true;
/*     */         }
/*     */       } 
/* 574 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class AndFilter<M, I>
/*     */     extends OrFilter<M, I> {
/*     */     AndFilter(Iterable<? extends RowFilter<? super M, ? super I>> param1Iterable) {
/* 581 */       super(param1Iterable);
/*     */     }
/*     */     
/*     */     public boolean include(RowFilter.Entry<? extends M, ? extends I> param1Entry) {
/* 585 */       for (RowFilter<M, I> rowFilter : this.filters) {
/* 586 */         if (!rowFilter.include(param1Entry)) {
/* 587 */           return false;
/*     */         }
/*     */       } 
/* 590 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class NotFilter<M, I>
/*     */     extends RowFilter<M, I> {
/*     */     private RowFilter<M, I> filter;
/*     */     
/*     */     NotFilter(RowFilter<M, I> param1RowFilter) {
/* 599 */       if (param1RowFilter == null) {
/* 600 */         throw new IllegalArgumentException("filter must be non-null");
/*     */       }
/*     */       
/* 603 */       this.filter = param1RowFilter;
/*     */     }
/*     */     
/*     */     public boolean include(RowFilter.Entry<? extends M, ? extends I> param1Entry) {
/* 607 */       return !this.filter.include(param1Entry);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/RowFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */