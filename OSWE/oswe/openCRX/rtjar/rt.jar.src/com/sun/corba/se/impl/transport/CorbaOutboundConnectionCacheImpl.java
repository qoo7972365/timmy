/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.pept.transport.Connection;
/*     */ import com.sun.corba.se.pept.transport.ContactInfo;
/*     */ import com.sun.corba.se.pept.transport.OutboundConnectionCache;
/*     */ import com.sun.corba.se.spi.monitoring.LongMonitoredAttributeBase;
/*     */ import com.sun.corba.se.spi.monitoring.MonitoredAttribute;
/*     */ import com.sun.corba.se.spi.monitoring.MonitoredObject;
/*     */ import com.sun.corba.se.spi.monitoring.MonitoringFactories;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfo;
/*     */ import java.util.Collection;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CorbaOutboundConnectionCacheImpl
/*     */   extends CorbaConnectionCacheBase
/*     */   implements OutboundConnectionCache
/*     */ {
/*     */   protected Hashtable connectionCache;
/*     */   
/*     */   public CorbaOutboundConnectionCacheImpl(ORB paramORB, ContactInfo paramContactInfo) {
/*  60 */     super(paramORB, paramContactInfo.getConnectionCacheType(), ((CorbaContactInfo)paramContactInfo)
/*  61 */         .getMonitoringName());
/*  62 */     this.connectionCache = new Hashtable<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection get(ContactInfo paramContactInfo) {
/*  72 */     if (this.orb.transportDebugFlag) {
/*  73 */       dprint(".get: " + paramContactInfo + " " + paramContactInfo.hashCode());
/*     */     }
/*  75 */     synchronized (backingStore()) {
/*  76 */       dprintStatistics();
/*  77 */       return (Connection)this.connectionCache.get(paramContactInfo);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(ContactInfo paramContactInfo, Connection paramConnection) {
/*  83 */     if (this.orb.transportDebugFlag) {
/*  84 */       dprint(".put: " + paramContactInfo + " " + paramContactInfo.hashCode() + " " + paramConnection);
/*     */     }
/*     */     
/*  87 */     synchronized (backingStore()) {
/*  88 */       this.connectionCache.put(paramContactInfo, paramConnection);
/*  89 */       paramConnection.setConnectionCache(this);
/*  90 */       dprintStatistics();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(ContactInfo paramContactInfo) {
/*  96 */     if (this.orb.transportDebugFlag) {
/*  97 */       dprint(".remove: " + paramContactInfo + " " + paramContactInfo.hashCode());
/*     */     }
/*  99 */     synchronized (backingStore()) {
/* 100 */       if (paramContactInfo != null) {
/* 101 */         this.connectionCache.remove(paramContactInfo);
/*     */       }
/* 103 */       dprintStatistics();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection values() {
/* 114 */     return this.connectionCache.values();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object backingStore() {
/* 119 */     return this.connectionCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerWithMonitoring() {
/* 126 */     MonitoredObject monitoredObject1 = this.orb.getMonitoringManager().getRootMonitoredObject();
/*     */ 
/*     */ 
/*     */     
/* 130 */     MonitoredObject monitoredObject2 = monitoredObject1.getChild("Connections");
/* 131 */     if (monitoredObject2 == null) {
/*     */ 
/*     */       
/* 134 */       monitoredObject2 = MonitoringFactories.getMonitoredObjectFactory().createMonitoredObject("Connections", "Statistics on inbound/outbound connections");
/*     */ 
/*     */       
/* 137 */       monitoredObject1.addChild(monitoredObject2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 142 */     MonitoredObject monitoredObject3 = monitoredObject2.getChild("Outbound");
/*     */     
/* 144 */     if (monitoredObject3 == null) {
/*     */ 
/*     */       
/* 147 */       monitoredObject3 = MonitoringFactories.getMonitoredObjectFactory().createMonitoredObject("Outbound", "Statistics on outbound connections");
/*     */ 
/*     */       
/* 150 */       monitoredObject2.addChild(monitoredObject3);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 155 */     MonitoredObject monitoredObject4 = monitoredObject3.getChild(getMonitoringName());
/* 156 */     if (monitoredObject4 == null) {
/*     */ 
/*     */       
/* 159 */       monitoredObject4 = MonitoringFactories.getMonitoredObjectFactory().createMonitoredObject(
/* 160 */           getMonitoringName(), "Connection statistics");
/*     */       
/* 162 */       monitoredObject3.addChild(monitoredObject4);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     LongMonitoredAttributeBase longMonitoredAttributeBase = new LongMonitoredAttributeBase("NumberOfConnections", "The total number of connections")
/*     */       {
/*     */ 
/*     */         
/*     */         public Object getValue()
/*     */         {
/* 174 */           return new Long(CorbaOutboundConnectionCacheImpl.this.numberOfConnections());
/*     */         }
/*     */       };
/* 177 */     monitoredObject4.addAttribute((MonitoredAttribute)longMonitoredAttributeBase);
/*     */ 
/*     */     
/* 180 */     longMonitoredAttributeBase = new LongMonitoredAttributeBase("NumberOfIdleConnections", "The number of idle connections")
/*     */       {
/*     */ 
/*     */         
/*     */         public Object getValue()
/*     */         {
/* 186 */           return new Long(CorbaOutboundConnectionCacheImpl.this.numberOfIdleConnections());
/*     */         }
/*     */       };
/* 189 */     monitoredObject4.addAttribute((MonitoredAttribute)longMonitoredAttributeBase);
/*     */ 
/*     */     
/* 192 */     longMonitoredAttributeBase = new LongMonitoredAttributeBase("NumberOfBusyConnections", "The number of busy connections")
/*     */       {
/*     */ 
/*     */         
/*     */         public Object getValue()
/*     */         {
/* 198 */           return new Long(CorbaOutboundConnectionCacheImpl.this.numberOfBusyConnections());
/*     */         }
/*     */       };
/* 201 */     monitoredObject4.addAttribute((MonitoredAttribute)longMonitoredAttributeBase);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString) {
/* 206 */     ORBUtility.dprint("CorbaOutboundConnectionCacheImpl", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/CorbaOutboundConnectionCacheImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */