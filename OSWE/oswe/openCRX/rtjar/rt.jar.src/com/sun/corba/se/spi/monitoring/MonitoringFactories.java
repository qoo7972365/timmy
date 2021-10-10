/*     */ package com.sun.corba.se.spi.monitoring;
/*     */ 
/*     */ import com.sun.corba.se.impl.monitoring.MonitoredAttributeInfoFactoryImpl;
/*     */ import com.sun.corba.se.impl.monitoring.MonitoredObjectFactoryImpl;
/*     */ import com.sun.corba.se.impl.monitoring.MonitoringManagerFactoryImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MonitoringFactories
/*     */ {
/*  47 */   private static final MonitoredObjectFactoryImpl monitoredObjectFactory = new MonitoredObjectFactoryImpl();
/*     */ 
/*     */   
/*  50 */   private static final MonitoredAttributeInfoFactoryImpl monitoredAttributeInfoFactory = new MonitoredAttributeInfoFactoryImpl();
/*     */   
/*  52 */   private static final MonitoringManagerFactoryImpl monitoringManagerFactory = new MonitoringManagerFactoryImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MonitoredObjectFactory getMonitoredObjectFactory() {
/*  69 */     return (MonitoredObjectFactory)monitoredObjectFactory;
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
/*     */   public static MonitoredAttributeInfoFactory getMonitoredAttributeInfoFactory() {
/*  91 */     return (MonitoredAttributeInfoFactory)monitoredAttributeInfoFactory;
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
/*     */   public static MonitoringManagerFactory getMonitoringManagerFactory() {
/* 109 */     return (MonitoringManagerFactory)monitoringManagerFactory;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/monitoring/MonitoringFactories.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */