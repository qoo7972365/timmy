/*    */ package com.sun.corba.se.impl.monitoring;
/*    */ 
/*    */ import com.sun.corba.se.spi.monitoring.MonitoredAttributeInfo;
/*    */ import com.sun.corba.se.spi.monitoring.MonitoredAttributeInfoFactory;
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
/*    */ public class MonitoredAttributeInfoFactoryImpl
/*    */   implements MonitoredAttributeInfoFactory
/*    */ {
/*    */   public MonitoredAttributeInfo createMonitoredAttributeInfo(String paramString, Class paramClass, boolean paramBoolean1, boolean paramBoolean2) {
/* 38 */     return new MonitoredAttributeInfoImpl(paramString, paramClass, paramBoolean1, paramBoolean2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/monitoring/MonitoredAttributeInfoFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */