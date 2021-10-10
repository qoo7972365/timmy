/*    */ package com.sun.corba.se.spi.monitoring;
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
/*    */ public abstract class StringMonitoredAttributeBase
/*    */   extends MonitoredAttributeBase
/*    */ {
/*    */   public StringMonitoredAttributeBase(String paramString1, String paramString2) {
/* 64 */     super(paramString1);
/*    */     
/* 66 */     MonitoredAttributeInfoFactory monitoredAttributeInfoFactory = MonitoringFactories.getMonitoredAttributeInfoFactory();
/* 67 */     MonitoredAttributeInfo monitoredAttributeInfo = monitoredAttributeInfoFactory.createMonitoredAttributeInfo(paramString2, String.class, false, false);
/*    */     
/* 69 */     setMonitoredAttributeInfo(monitoredAttributeInfo);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/monitoring/StringMonitoredAttributeBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */