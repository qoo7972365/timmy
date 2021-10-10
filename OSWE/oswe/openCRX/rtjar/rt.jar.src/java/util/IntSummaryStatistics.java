/*     */ package java.util;
/*     */ 
/*     */ import java.util.function.IntConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntSummaryStatistics
/*     */   implements IntConsumer
/*     */ {
/*     */   private long count;
/*     */   private long sum;
/*  68 */   private int min = Integer.MAX_VALUE;
/*  69 */   private int max = Integer.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(int paramInt) {
/*  85 */     this.count++;
/*  86 */     this.sum += paramInt;
/*  87 */     this.min = Math.min(this.min, paramInt);
/*  88 */     this.max = Math.max(this.max, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void combine(IntSummaryStatistics paramIntSummaryStatistics) {
/*  98 */     this.count += paramIntSummaryStatistics.count;
/*  99 */     this.sum += paramIntSummaryStatistics.sum;
/* 100 */     this.min = Math.min(this.min, paramIntSummaryStatistics.min);
/* 101 */     this.max = Math.max(this.max, paramIntSummaryStatistics.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getCount() {
/* 110 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getSum() {
/* 120 */     return this.sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getMin() {
/* 130 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getMax() {
/* 140 */     return this.max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getAverage() {
/* 150 */     return (getCount() > 0L) ? (getSum() / getCount()) : 0.0D;
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
/*     */   public String toString() {
/* 162 */     return String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}", new Object[] {
/*     */           
/* 164 */           getClass().getSimpleName(), 
/* 165 */           Long.valueOf(getCount()), 
/* 166 */           Long.valueOf(getSum()), 
/* 167 */           Integer.valueOf(getMin()), 
/* 168 */           Double.valueOf(getAverage()), 
/* 169 */           Integer.valueOf(getMax())
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/IntSummaryStatistics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */