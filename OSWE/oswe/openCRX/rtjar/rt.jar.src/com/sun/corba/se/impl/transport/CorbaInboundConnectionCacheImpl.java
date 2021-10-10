/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.pept.transport.Acceptor;
/*     */ import com.sun.corba.se.pept.transport.Connection;
/*     */ import com.sun.corba.se.pept.transport.InboundConnectionCache;
/*     */ import com.sun.corba.se.spi.monitoring.LongMonitoredAttributeBase;
/*     */ import com.sun.corba.se.spi.monitoring.MonitoredAttribute;
/*     */ import com.sun.corba.se.spi.monitoring.MonitoredObject;
/*     */ import com.sun.corba.se.spi.monitoring.MonitoringFactories;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.CorbaAcceptor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ public class CorbaInboundConnectionCacheImpl
/*     */   extends CorbaConnectionCacheBase
/*     */   implements InboundConnectionCache
/*     */ {
/*     */   protected Collection connectionCache;
/*     */   private Acceptor acceptor;
/*     */   
/*     */   public CorbaInboundConnectionCacheImpl(ORB paramORB, Acceptor paramAcceptor) {
/*  61 */     super(paramORB, paramAcceptor.getConnectionCacheType(), ((CorbaAcceptor)paramAcceptor)
/*  62 */         .getMonitoringName());
/*  63 */     this.connectionCache = new ArrayList();
/*  64 */     this.acceptor = paramAcceptor;
/*  65 */     if (paramORB.transportDebugFlag) {
/*  66 */       dprint(": " + paramAcceptor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*  77 */     super.close();
/*  78 */     if (this.orb.transportDebugFlag) {
/*  79 */       dprint(".close: " + this.acceptor);
/*     */     }
/*  81 */     this.acceptor.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection get(Acceptor paramAcceptor) {
/*  87 */     throw this.wrapper.methodShouldNotBeCalled();
/*     */   }
/*     */   
/*     */   public Acceptor getAcceptor() {
/*  91 */     return this.acceptor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(Acceptor paramAcceptor, Connection paramConnection) {
/*  96 */     if (this.orb.transportDebugFlag) {
/*  97 */       dprint(".put: " + paramAcceptor + " " + paramConnection);
/*     */     }
/*  99 */     synchronized (backingStore()) {
/* 100 */       this.connectionCache.add(paramConnection);
/* 101 */       paramConnection.setConnectionCache(this);
/* 102 */       dprintStatistics();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(Connection paramConnection) {
/* 108 */     if (this.orb.transportDebugFlag) {
/* 109 */       dprint(".remove: " + paramConnection);
/*     */     }
/* 111 */     synchronized (backingStore()) {
/* 112 */       this.connectionCache.remove(paramConnection);
/* 113 */       dprintStatistics();
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
/* 124 */     return this.connectionCache;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object backingStore() {
/* 129 */     return this.connectionCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerWithMonitoring() {
/* 136 */     MonitoredObject monitoredObject1 = this.orb.getMonitoringManager().getRootMonitoredObject();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     MonitoredObject monitoredObject2 = monitoredObject1.getChild("Connections");
/* 143 */     if (monitoredObject2 == null) {
/*     */ 
/*     */       
/* 146 */       monitoredObject2 = MonitoringFactories.getMonitoredObjectFactory().createMonitoredObject("Connections", "Statistics on inbound/outbound connections");
/*     */ 
/*     */       
/* 149 */       monitoredObject1.addChild(monitoredObject2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 154 */     MonitoredObject monitoredObject3 = monitoredObject2.getChild("Inbound");
/*     */     
/* 156 */     if (monitoredObject3 == null) {
/*     */ 
/*     */       
/* 159 */       monitoredObject3 = MonitoringFactories.getMonitoredObjectFactory().createMonitoredObject("Inbound", "Statistics on inbound connections");
/*     */ 
/*     */       
/* 162 */       monitoredObject2.addChild(monitoredObject3);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 167 */     MonitoredObject monitoredObject4 = monitoredObject3.getChild(getMonitoringName());
/* 168 */     if (monitoredObject4 == null) {
/*     */ 
/*     */       
/* 171 */       monitoredObject4 = MonitoringFactories.getMonitoredObjectFactory().createMonitoredObject(
/* 172 */           getMonitoringName(), "Connection statistics");
/*     */       
/* 174 */       monitoredObject3.addChild(monitoredObject4);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     LongMonitoredAttributeBase longMonitoredAttributeBase = new LongMonitoredAttributeBase("NumberOfConnections", "The total number of connections")
/*     */       {
/*     */ 
/*     */         
/*     */         public Object getValue()
/*     */         {
/* 186 */           return new Long(CorbaInboundConnectionCacheImpl.this.numberOfConnections());
/*     */         }
/*     */       };
/* 189 */     monitoredObject4.addAttribute((MonitoredAttribute)longMonitoredAttributeBase);
/*     */ 
/*     */     
/* 192 */     longMonitoredAttributeBase = new LongMonitoredAttributeBase("NumberOfIdleConnections", "The number of idle connections")
/*     */       {
/*     */ 
/*     */         
/*     */         public Object getValue()
/*     */         {
/* 198 */           return new Long(CorbaInboundConnectionCacheImpl.this.numberOfIdleConnections());
/*     */         }
/*     */       };
/* 201 */     monitoredObject4.addAttribute((MonitoredAttribute)longMonitoredAttributeBase);
/*     */ 
/*     */     
/* 204 */     longMonitoredAttributeBase = new LongMonitoredAttributeBase("NumberOfBusyConnections", "The number of busy connections")
/*     */       {
/*     */ 
/*     */         
/*     */         public Object getValue()
/*     */         {
/* 210 */           return new Long(CorbaInboundConnectionCacheImpl.this.numberOfBusyConnections());
/*     */         }
/*     */       };
/* 213 */     monitoredObject4.addAttribute((MonitoredAttribute)longMonitoredAttributeBase);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString) {
/* 218 */     ORBUtility.dprint("CorbaInboundConnectionCacheImpl", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/CorbaInboundConnectionCacheImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */