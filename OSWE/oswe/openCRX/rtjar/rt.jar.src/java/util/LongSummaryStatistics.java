/*     */ package java.util;
/*     */ 
/*     */ import java.util.function.IntConsumer;
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
/*     */ public class LongSummaryStatistics
/*     */   implements LongConsumer, IntConsumer
/*     */ {
/*     */   private long count;
/*     */   private long sum;
/*  69 */   private long min = Long.MAX_VALUE;
/*  70 */   private long max = Long.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  86 */     accept(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(long paramLong) {
/*  96 */     this.count++;
/*  97 */     this.sum += paramLong;
/*  98 */     this.min = Math.min(this.min, paramLong);
/*  99 */     this.max = Math.max(this.max, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void combine(LongSummaryStatistics paramLongSummaryStatistics) {
/* 110 */     this.count += paramLongSummaryStatistics.count;
/* 111 */     this.sum += paramLongSummaryStatistics.sum;
/* 112 */     this.min = Math.min(this.min, paramLongSummaryStatistics.min);
/* 113 */     this.max = Math.max(this.max, paramLongSummaryStatistics.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getCount() {
/* 122 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getSum() {
/* 132 */     return this.sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getMin() {
/* 142 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getMax() {
/* 152 */     return this.max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getAverage() {
/* 162 */     return (getCount() > 0L) ? (getSum() / getCount()) : 0.0D;
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
/* 174 */     return String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}", new Object[] {
/*     */           
/* 176 */           getClass().getSimpleName(), 
/* 177 */           Long.valueOf(getCount()), 
/* 178 */           Long.valueOf(getSum()), 
/* 179 */           Long.valueOf(getMin()), 
/* 180 */           Double.valueOf(getAverage()), 
/* 181 */           Long.valueOf(getMax())
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/LongSummaryStatistics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */