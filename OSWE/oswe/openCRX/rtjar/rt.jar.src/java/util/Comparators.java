/*    */ package java.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Comparators
/*    */ {
/*    */   private Comparators() {
/* 39 */     throw new AssertionError("no instances");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   enum NaturalOrderComparator
/*    */     implements Comparator<Comparable<Object>>
/*    */   {
/* 48 */     INSTANCE;
/*    */ 
/*    */     
/*    */     public int compare(Comparable<Object> param1Comparable1, Comparable<Object> param1Comparable2) {
/* 52 */       return param1Comparable1.compareTo(param1Comparable2);
/*    */     }
/*    */ 
/*    */     
/*    */     public Comparator<Comparable<Object>> reversed() {
/* 57 */       return Comparator.reverseOrder();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   static final class NullComparator<T>
/*    */     implements Comparator<T>, Serializable
/*    */   {
/*    */     private static final long serialVersionUID = -7569533591570686392L;
/*    */     
/*    */     private final boolean nullFirst;
/*    */     
/*    */     private final Comparator<T> real;
/*    */     
/*    */     NullComparator(boolean param1Boolean, Comparator<? super T> param1Comparator) {
/* 72 */       this.nullFirst = param1Boolean;
/* 73 */       this.real = (Comparator)param1Comparator;
/*    */     }
/*    */ 
/*    */     
/*    */     public int compare(T param1T1, T param1T2) {
/* 78 */       if (param1T1 == null)
/* 79 */         return (param1T2 == null) ? 0 : (this.nullFirst ? -1 : 1); 
/* 80 */       if (param1T2 == null) {
/* 81 */         return this.nullFirst ? 1 : -1;
/*    */       }
/* 83 */       return (this.real == null) ? 0 : this.real.compare(param1T1, param1T2);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public Comparator<T> thenComparing(Comparator<? super T> param1Comparator) {
/* 89 */       Objects.requireNonNull(param1Comparator);
/* 90 */       return new NullComparator(this.nullFirst, (this.real == null) ? param1Comparator : this.real.thenComparing(param1Comparator));
/*    */     }
/*    */ 
/*    */     
/*    */     public Comparator<T> reversed() {
/* 95 */       return new NullComparator(!this.nullFirst, (this.real == null) ? null : this.real.reversed());
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Comparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */