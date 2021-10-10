/*    */ package com.sun.org.glassfish.external.statistics.impl;
/*    */ 
/*    */ import com.sun.org.glassfish.external.statistics.Statistic;
/*    */ import com.sun.org.glassfish.external.statistics.Stats;
/*    */ import java.util.ArrayList;
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
/*    */ public final class StatsImpl
/*    */   implements Stats
/*    */ {
/*    */   private final StatisticImpl[] statArray;
/*    */   
/*    */   protected StatsImpl(StatisticImpl[] statisticArray) {
/* 42 */     this.statArray = statisticArray;
/*    */   }
/*    */   
/*    */   public synchronized Statistic getStatistic(String statisticName) {
/* 46 */     Statistic stat = null;
/* 47 */     for (Statistic s : this.statArray) {
/* 48 */       if (s.getName().equals(statisticName)) {
/* 49 */         stat = s;
/*    */         break;
/*    */       } 
/*    */     } 
/* 53 */     return stat;
/*    */   }
/*    */   
/*    */   public synchronized String[] getStatisticNames() {
/* 57 */     ArrayList<String> list = new ArrayList();
/* 58 */     for (Statistic s : this.statArray) {
/* 59 */       list.add(s.getName());
/*    */     }
/* 61 */     String[] strArray = new String[list.size()];
/* 62 */     return list.<String>toArray(strArray);
/*    */   }
/*    */   
/*    */   public synchronized Statistic[] getStatistics() {
/* 66 */     return (Statistic[])this.statArray;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void reset() {
/* 73 */     for (StatisticImpl s : this.statArray)
/* 74 */       s.reset(); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/glassfish/external/statistics/impl/StatsImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */