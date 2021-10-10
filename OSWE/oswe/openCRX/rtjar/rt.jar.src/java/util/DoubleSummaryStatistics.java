/*     */ package java.util;
/*     */ 
/*     */ import java.util.function.DoubleConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleSummaryStatistics
/*     */   implements DoubleConsumer
/*     */ {
/*     */   private long count;
/*     */   private double sum;
/*     */   private double sumCompensation;
/*     */   private double simpleSum;
/*  68 */   private double min = Double.POSITIVE_INFINITY;
/*  69 */   private double max = Double.NEGATIVE_INFINITY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(double paramDouble) {
/*  85 */     this.count++;
/*  86 */     this.simpleSum += paramDouble;
/*  87 */     sumWithCompensation(paramDouble);
/*  88 */     this.min = Math.min(this.min, paramDouble);
/*  89 */     this.max = Math.max(this.max, paramDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void combine(DoubleSummaryStatistics paramDoubleSummaryStatistics) {
/* 100 */     this.count += paramDoubleSummaryStatistics.count;
/* 101 */     this.simpleSum += paramDoubleSummaryStatistics.simpleSum;
/* 102 */     sumWithCompensation(paramDoubleSummaryStatistics.sum);
/* 103 */     sumWithCompensation(paramDoubleSummaryStatistics.sumCompensation);
/* 104 */     this.min = Math.min(this.min, paramDoubleSummaryStatistics.min);
/* 105 */     this.max = Math.max(this.max, paramDoubleSummaryStatistics.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sumWithCompensation(double paramDouble) {
/* 113 */     double d1 = paramDouble - this.sumCompensation;
/* 114 */     double d2 = this.sum + d1;
/* 115 */     this.sumCompensation = d2 - this.sum - d1;
/* 116 */     this.sum = d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getCount() {
/* 125 */     return this.count;
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
/*     */   public final double getSum() {
/* 153 */     double d = this.sum + this.sumCompensation;
/* 154 */     if (Double.isNaN(d) && Double.isInfinite(this.simpleSum))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 159 */       return this.simpleSum;
/*     */     }
/* 161 */     return d;
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
/*     */   public final double getMin() {
/* 175 */     return this.min;
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
/*     */   public final double getMax() {
/* 189 */     return this.max;
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
/*     */   public final double getAverage() {
/* 212 */     return (getCount() > 0L) ? (getSum() / getCount()) : 0.0D;
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
/* 224 */     return String.format("%s{count=%d, sum=%f, min=%f, average=%f, max=%f}", new Object[] {
/*     */           
/* 226 */           getClass().getSimpleName(), 
/* 227 */           Long.valueOf(getCount()), 
/* 228 */           Double.valueOf(getSum()), 
/* 229 */           Double.valueOf(getMin()), 
/* 230 */           Double.valueOf(getAverage()), 
/* 231 */           Double.valueOf(getMax())
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/DoubleSummaryStatistics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */