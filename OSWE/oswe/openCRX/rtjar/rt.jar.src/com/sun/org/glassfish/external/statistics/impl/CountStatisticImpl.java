/*     */ package com.sun.org.glassfish.external.statistics.impl;
/*     */ 
/*     */ import com.sun.org.glassfish.external.statistics.CountStatistic;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CountStatisticImpl
/*     */   extends StatisticImpl
/*     */   implements CountStatistic, InvocationHandler
/*     */ {
/*  38 */   private long count = 0L;
/*     */   
/*     */   private final long initCount;
/*     */   
/*  42 */   private final CountStatistic cs = (CountStatistic)Proxy.newProxyInstance(CountStatistic.class
/*  43 */       .getClassLoader(), new Class[] { CountStatistic.class }, this);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CountStatisticImpl(long countVal, String name, String unit, String desc, long sampleTime, long startTime) {
/*  49 */     super(name, unit, desc, startTime, sampleTime);
/*  50 */     this.count = countVal;
/*  51 */     this.initCount = countVal;
/*     */   }
/*     */   
/*     */   public CountStatisticImpl(String name, String unit, String desc) {
/*  55 */     this(0L, name, unit, desc, -1L, System.currentTimeMillis());
/*     */   }
/*     */   
/*     */   public synchronized CountStatistic getStatistic() {
/*  59 */     return this.cs;
/*     */   }
/*     */   
/*     */   public synchronized Map getStaticAsMap() {
/*  63 */     Map<String, Long> m = super.getStaticAsMap();
/*  64 */     m.put("count", Long.valueOf(getCount()));
/*  65 */     return m;
/*     */   }
/*     */   
/*     */   public synchronized String toString() {
/*  69 */     return super.toString() + NEWLINE + "Count: " + getCount();
/*     */   }
/*     */   
/*     */   public synchronized long getCount() {
/*  73 */     return this.count;
/*     */   }
/*     */   
/*     */   public synchronized void setCount(long countVal) {
/*  77 */     this.count = countVal;
/*  78 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public synchronized void increment() {
/*  82 */     this.count++;
/*  83 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public synchronized void increment(long delta) {
/*  87 */     this.count += delta;
/*  88 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public synchronized void decrement() {
/*  92 */     this.count--;
/*  93 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void reset() {
/*  98 */     super.reset();
/*  99 */     this.count = this.initCount;
/* 100 */     this.sampleTime = -1L;
/*     */   }
/*     */   
/*     */   public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
/*     */     Object result;
/* 105 */     checkMethod(m);
/*     */ 
/*     */     
/*     */     try {
/* 109 */       result = m.invoke(this, args);
/* 110 */     } catch (InvocationTargetException e) {
/* 111 */       throw e.getTargetException();
/* 112 */     } catch (Exception e) {
/* 113 */       throw new RuntimeException("unexpected invocation exception: " + e
/* 114 */           .getMessage());
/*     */     } 
/* 116 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/glassfish/external/statistics/impl/CountStatisticImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */