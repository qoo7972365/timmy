/*     */ package com.sun.corba.se.spi.monitoring;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatisticsAccumulator
/*     */ {
/*  54 */   protected double max = Double.MIN_VALUE;
/*     */ 
/*     */   
/*  57 */   protected double min = Double.MAX_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double sampleSum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double sampleSquareSum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long sampleCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String unit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sample(double paramDouble) {
/*  90 */     this.sampleCount++;
/*  91 */     if (paramDouble < this.min) this.min = paramDouble; 
/*  92 */     if (paramDouble > this.max) this.max = paramDouble; 
/*  93 */     this.sampleSum += paramDouble;
/*  94 */     this.sampleSquareSum += paramDouble * paramDouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 104 */     return toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 112 */     return "Minimum Value = " + this.min + " " + this.unit + " Maximum Value = " + this.max + " " + this.unit + " Average Value = " + 
/*     */       
/* 114 */       computeAverage() + " " + this.unit + " Standard Deviation = " + 
/* 115 */       computeStandardDeviation() + " " + this.unit + " Samples Collected = " + this.sampleCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double computeAverage() {
/* 123 */     return this.sampleSum / this.sampleCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double computeStandardDeviation() {
/* 134 */     double d = this.sampleSum * this.sampleSum;
/* 135 */     return Math.sqrt((this.sampleSquareSum - d / this.sampleCount) / (this.sampleCount - 1L));
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
/*     */   public StatisticsAccumulator(String paramString) {
/* 155 */     this.unit = paramString;
/* 156 */     this.sampleCount = 0L;
/* 157 */     this.sampleSum = 0.0D;
/* 158 */     this.sampleSquareSum = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void clearState() {
/* 166 */     this.min = Double.MAX_VALUE;
/* 167 */     this.max = Double.MIN_VALUE;
/* 168 */     this.sampleCount = 0L;
/* 169 */     this.sampleSum = 0.0D;
/* 170 */     this.sampleSquareSum = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unitTestValidate(String paramString, double paramDouble1, double paramDouble2, long paramLong, double paramDouble3, double paramDouble4) {
/* 180 */     if (!paramString.equals(this.unit)) {
/* 181 */       throw new RuntimeException("Unit is not same as expected Unit\nUnit = " + this.unit + "ExpectedUnit = " + paramString);
/*     */     }
/*     */ 
/*     */     
/* 185 */     if (this.min != paramDouble1) {
/* 186 */       throw new RuntimeException("Minimum value is not same as expected minimum value\nMin Value = " + this.min + "Expected Min Value = " + paramDouble1);
/*     */     }
/*     */ 
/*     */     
/* 190 */     if (this.max != paramDouble2) {
/* 191 */       throw new RuntimeException("Maximum value is not same as expected maximum value\nMax Value = " + this.max + "Expected Max Value = " + paramDouble2);
/*     */     }
/*     */ 
/*     */     
/* 195 */     if (this.sampleCount != paramLong) {
/* 196 */       throw new RuntimeException("Sample count is not same as expected Sample Count\nSampleCount = " + this.sampleCount + "Expected Sample Count = " + paramLong);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 201 */     if (computeAverage() != paramDouble3) {
/* 202 */       throw new RuntimeException("Average is not same as expected Average\nAverage = " + 
/*     */           
/* 204 */           computeAverage() + "Expected Average = " + paramDouble3);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     double d = Math.abs(
/* 212 */         computeStandardDeviation() - paramDouble4);
/* 213 */     if (d > 1.0D)
/* 214 */       throw new RuntimeException("Standard Deviation is not same as expected Std Deviation\nStandard Dev = " + 
/*     */           
/* 216 */           computeStandardDeviation() + "Expected Standard Dev = " + paramDouble4); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/monitoring/StatisticsAccumulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */