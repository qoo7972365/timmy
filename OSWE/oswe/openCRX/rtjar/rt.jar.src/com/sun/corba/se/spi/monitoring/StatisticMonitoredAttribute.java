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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatisticMonitoredAttribute
/*     */   extends MonitoredAttributeBase
/*     */ {
/*     */   private StatisticsAccumulator statisticsAccumulator;
/*     */   private Object mutex;
/*     */   
/*     */   public StatisticMonitoredAttribute(String paramString1, String paramString2, StatisticsAccumulator paramStatisticsAccumulator, Object paramObject) {
/*  88 */     super(paramString1);
/*     */     
/*  90 */     MonitoredAttributeInfoFactory monitoredAttributeInfoFactory = MonitoringFactories.getMonitoredAttributeInfoFactory();
/*  91 */     MonitoredAttributeInfo monitoredAttributeInfo = monitoredAttributeInfoFactory.createMonitoredAttributeInfo(paramString2, String.class, false, true);
/*     */ 
/*     */     
/*  94 */     setMonitoredAttributeInfo(monitoredAttributeInfo);
/*  95 */     this.statisticsAccumulator = paramStatisticsAccumulator;
/*  96 */     this.mutex = paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 107 */     synchronized (this.mutex) {
/* 108 */       return this.statisticsAccumulator.getValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearState() {
/* 117 */     synchronized (this.mutex) {
/* 118 */       this.statisticsAccumulator.clearState();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatisticsAccumulator getStatisticsAccumulator() {
/* 128 */     return this.statisticsAccumulator;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/monitoring/StatisticMonitoredAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */