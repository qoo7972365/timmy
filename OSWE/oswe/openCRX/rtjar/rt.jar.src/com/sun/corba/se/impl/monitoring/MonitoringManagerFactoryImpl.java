/*    */ package com.sun.corba.se.impl.monitoring;
/*    */ 
/*    */ import com.sun.corba.se.spi.monitoring.MonitoringManager;
/*    */ import com.sun.corba.se.spi.monitoring.MonitoringManagerFactory;
/*    */ import java.util.HashMap;
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
/*    */ public class MonitoringManagerFactoryImpl
/*    */   implements MonitoringManagerFactory
/*    */ {
/* 34 */   private HashMap monitoringManagerTable = new HashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized MonitoringManager createMonitoringManager(String paramString1, String paramString2) {
/* 39 */     MonitoringManagerImpl monitoringManagerImpl = null;
/* 40 */     monitoringManagerImpl = (MonitoringManagerImpl)this.monitoringManagerTable.get(paramString1);
/* 41 */     if (monitoringManagerImpl == null) {
/* 42 */       monitoringManagerImpl = new MonitoringManagerImpl(paramString1, paramString2);
/* 43 */       this.monitoringManagerTable.put(paramString1, monitoringManagerImpl);
/*    */     } 
/* 45 */     return monitoringManagerImpl;
/*    */   }
/*    */   
/*    */   public synchronized void remove(String paramString) {
/* 49 */     this.monitoringManagerTable.remove(paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/monitoring/MonitoringManagerFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */