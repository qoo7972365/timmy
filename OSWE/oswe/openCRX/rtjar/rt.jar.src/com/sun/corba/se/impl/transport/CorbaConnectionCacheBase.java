/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.pept.transport.Connection;
/*     */ import com.sun.corba.se.pept.transport.ConnectionCache;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.CorbaConnection;
/*     */ import com.sun.corba.se.spi.transport.CorbaConnectionCache;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ public abstract class CorbaConnectionCacheBase
/*     */   implements ConnectionCache, CorbaConnectionCache
/*     */ {
/*     */   protected ORB orb;
/*  52 */   protected long timestamp = 0L;
/*     */   
/*     */   protected String cacheType;
/*     */   
/*     */   protected String monitoringName;
/*     */   protected ORBUtilSystemException wrapper;
/*     */   
/*     */   protected CorbaConnectionCacheBase(ORB paramORB, String paramString1, String paramString2) {
/*  60 */     this.orb = paramORB;
/*  61 */     this.cacheType = paramString1;
/*  62 */     this.monitoringName = paramString2;
/*  63 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.transport");
/*  64 */     registerWithMonitoring();
/*  65 */     dprintCreation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCacheType() {
/*  75 */     return this.cacheType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void stampTime(Connection paramConnection) {
/*  81 */     paramConnection.setTimeStamp(this.timestamp++);
/*     */   }
/*     */ 
/*     */   
/*     */   public long numberOfConnections() {
/*  86 */     synchronized (backingStore()) {
/*  87 */       return values().size();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() {
/*  92 */     synchronized (backingStore()) {
/*  93 */       for (CorbaConnection corbaConnection : values()) {
/*  94 */         ((CorbaConnection)corbaConnection).closeConnectionResources();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long numberOfIdleConnections() {
/* 101 */     long l = 0L;
/* 102 */     synchronized (backingStore()) {
/* 103 */       Iterator<Connection> iterator = values().iterator();
/* 104 */       while (iterator.hasNext()) {
/* 105 */         if (!((Connection)iterator.next()).isBusy()) {
/* 106 */           l++;
/*     */         }
/*     */       } 
/*     */     } 
/* 110 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   public long numberOfBusyConnections() {
/* 115 */     long l = 0L;
/* 116 */     synchronized (backingStore()) {
/* 117 */       Iterator<Connection> iterator = values().iterator();
/* 118 */       while (iterator.hasNext()) {
/* 119 */         if (((Connection)iterator.next()).isBusy()) {
/* 120 */           l++;
/*     */         }
/*     */       } 
/*     */     } 
/* 124 */     return l;
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
/*     */   public synchronized boolean reclaim() {
/*     */     try {
/* 147 */       long l = numberOfConnections();
/*     */       
/* 149 */       if (this.orb.transportDebugFlag) {
/* 150 */         dprint(".reclaim->: " + l + " (" + this.orb
/*     */             
/* 152 */             .getORBData().getHighWaterMark() + "/" + this.orb
/*     */             
/* 154 */             .getORBData().getLowWaterMark() + "/" + this.orb
/*     */             
/* 156 */             .getORBData().getNumberToReclaim() + ")");
/*     */       }
/*     */ 
/*     */       
/* 160 */       if (l <= this.orb.getORBData().getHighWaterMark() || l < this.orb
/* 161 */         .getORBData().getLowWaterMark()) {
/* 162 */         return false;
/*     */       }
/*     */       
/* 165 */       Object object = backingStore();
/* 166 */       synchronized (object) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 171 */         for (byte b = 0; b < this.orb.getORBData().getNumberToReclaim(); b++) {
/* 172 */           Connection connection = null;
/* 173 */           long l1 = Long.MAX_VALUE;
/* 174 */           Iterator<Connection> iterator = values().iterator();
/*     */ 
/*     */           
/* 177 */           while (iterator.hasNext()) {
/* 178 */             Connection connection1 = iterator.next();
/* 179 */             if (!connection1.isBusy() && connection1.getTimeStamp() < l1) {
/* 180 */               connection = connection1;
/* 181 */               l1 = connection1.getTimeStamp();
/*     */             } 
/*     */           } 
/*     */           
/* 185 */           if (connection == null) {
/* 186 */             return false;
/*     */           }
/*     */           
/*     */           try {
/* 190 */             if (this.orb.transportDebugFlag) {
/* 191 */               dprint(".reclaim: closing: " + connection);
/*     */             }
/* 193 */             connection.close();
/* 194 */           } catch (Exception exception) {}
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 199 */         if (this.orb.transportDebugFlag) {
/* 200 */           dprint(".reclaim: connections reclaimed (" + (l - 
/* 201 */               numberOfConnections()) + ")");
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       return true;
/*     */     } finally {
/* 211 */       if (this.orb.transportDebugFlag) {
/* 212 */         dprint(".reclaim<-: " + numberOfConnections());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMonitoringName() {
/* 224 */     return this.monitoringName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Collection values();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object backingStore();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void registerWithMonitoring();
/*     */ 
/*     */   
/*     */   protected void dprintCreation() {
/* 241 */     if (this.orb.transportDebugFlag) {
/* 242 */       dprint(".constructor: cacheType: " + getCacheType() + " monitoringName: " + 
/* 243 */           getMonitoringName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dprintStatistics() {
/* 249 */     if (this.orb.transportDebugFlag) {
/* 250 */       dprint(".stats: " + 
/* 251 */           numberOfConnections() + "/total " + 
/* 252 */           numberOfBusyConnections() + "/busy " + 
/* 253 */           numberOfIdleConnections() + "/idle (" + this.orb
/*     */           
/* 255 */           .getORBData().getHighWaterMark() + "/" + this.orb
/* 256 */           .getORBData().getLowWaterMark() + "/" + this.orb
/* 257 */           .getORBData().getNumberToReclaim() + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString) {
/* 264 */     ORBUtility.dprint("CorbaConnectionCacheBase", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/CorbaConnectionCacheBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */