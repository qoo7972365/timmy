/*    */ package com.sun.corba.se.impl.monitoring;
/*    */ 
/*    */ import com.sun.corba.se.spi.monitoring.MonitoredObject;
/*    */ import com.sun.corba.se.spi.monitoring.MonitoredObjectFactory;
/*    */ import com.sun.corba.se.spi.monitoring.MonitoringFactories;
/*    */ import com.sun.corba.se.spi.monitoring.MonitoringManager;
/*    */ import com.sun.corba.se.spi.monitoring.MonitoringManagerFactory;
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
/*    */ public class MonitoringManagerImpl
/*    */   implements MonitoringManager
/*    */ {
/*    */   private final MonitoredObject rootMonitoredObject;
/*    */   
/*    */   MonitoringManagerImpl(String paramString1, String paramString2) {
/* 39 */     MonitoredObjectFactory monitoredObjectFactory = MonitoringFactories.getMonitoredObjectFactory();
/* 40 */     this
/* 41 */       .rootMonitoredObject = monitoredObjectFactory.createMonitoredObject(paramString1, paramString2);
/*    */   }
/*    */   
/*    */   public void clearState() {
/* 45 */     this.rootMonitoredObject.clearState();
/*    */   }
/*    */   
/*    */   public MonitoredObject getRootMonitoredObject() {
/* 49 */     return this.rootMonitoredObject;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 54 */     MonitoringManagerFactory monitoringManagerFactory = MonitoringFactories.getMonitoringManagerFactory();
/* 55 */     monitoringManagerFactory.remove(this.rootMonitoredObject.getName());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/monitoring/MonitoringManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */