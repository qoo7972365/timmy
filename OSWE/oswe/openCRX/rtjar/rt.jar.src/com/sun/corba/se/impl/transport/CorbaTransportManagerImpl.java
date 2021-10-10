/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.oa.poa.Policies;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.pept.transport.Acceptor;
/*     */ import com.sun.corba.se.pept.transport.ByteBufferPool;
/*     */ import com.sun.corba.se.pept.transport.ConnectionCache;
/*     */ import com.sun.corba.se.pept.transport.ContactInfo;
/*     */ import com.sun.corba.se.pept.transport.InboundConnectionCache;
/*     */ import com.sun.corba.se.pept.transport.OutboundConnectionCache;
/*     */ import com.sun.corba.se.pept.transport.Selector;
/*     */ import com.sun.corba.se.spi.ior.IORTemplate;
/*     */ import com.sun.corba.se.spi.ior.ObjectAdapterId;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.CorbaAcceptor;
/*     */ import com.sun.corba.se.spi.transport.CorbaTransportManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class CorbaTransportManagerImpl
/*     */   implements CorbaTransportManager
/*     */ {
/*     */   protected ORB orb;
/*     */   protected List acceptors;
/*     */   protected Map outboundConnectionCaches;
/*     */   protected Map inboundConnectionCaches;
/*     */   protected Selector selector;
/*     */   
/*     */   public CorbaTransportManagerImpl(ORB paramORB) {
/*  75 */     this.orb = paramORB;
/*  76 */     this.acceptors = new ArrayList();
/*  77 */     this.outboundConnectionCaches = new HashMap<>();
/*  78 */     this.inboundConnectionCaches = new HashMap<>();
/*  79 */     this.selector = new SelectorImpl(paramORB);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBufferPool getByteBufferPool(int paramInt) {
/*  89 */     throw new RuntimeException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OutboundConnectionCache getOutboundConnectionCache(ContactInfo paramContactInfo) {
/*  95 */     synchronized (paramContactInfo) {
/*  96 */       if (paramContactInfo.getConnectionCache() == null) {
/*  97 */         OutboundConnectionCache outboundConnectionCache = null;
/*  98 */         synchronized (this.outboundConnectionCaches) {
/*     */           
/* 100 */           outboundConnectionCache = (OutboundConnectionCache)this.outboundConnectionCaches.get(paramContactInfo
/* 101 */               .getConnectionCacheType());
/* 102 */           if (outboundConnectionCache == null) {
/*     */ 
/*     */             
/* 105 */             outboundConnectionCache = new CorbaOutboundConnectionCacheImpl(this.orb, paramContactInfo);
/*     */ 
/*     */             
/* 108 */             this.outboundConnectionCaches.put(paramContactInfo
/* 109 */                 .getConnectionCacheType(), outboundConnectionCache);
/*     */           } 
/*     */         } 
/*     */         
/* 113 */         paramContactInfo.setConnectionCache(outboundConnectionCache);
/*     */       } 
/* 115 */       return paramContactInfo.getConnectionCache();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getOutboundConnectionCaches() {
/* 121 */     return this.outboundConnectionCaches.values();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InboundConnectionCache getInboundConnectionCache(Acceptor paramAcceptor) {
/* 127 */     synchronized (paramAcceptor) {
/* 128 */       if (paramAcceptor.getConnectionCache() == null) {
/* 129 */         InboundConnectionCache inboundConnectionCache = null;
/* 130 */         synchronized (this.inboundConnectionCaches) {
/*     */           
/* 132 */           inboundConnectionCache = (InboundConnectionCache)this.inboundConnectionCaches.get(paramAcceptor
/* 133 */               .getConnectionCacheType());
/* 134 */           if (inboundConnectionCache == null) {
/*     */ 
/*     */             
/* 137 */             inboundConnectionCache = new CorbaInboundConnectionCacheImpl(this.orb, paramAcceptor);
/*     */ 
/*     */             
/* 140 */             this.inboundConnectionCaches.put(paramAcceptor
/* 141 */                 .getConnectionCacheType(), inboundConnectionCache);
/*     */           } 
/*     */         } 
/*     */         
/* 145 */         paramAcceptor.setConnectionCache(inboundConnectionCache);
/*     */       } 
/* 147 */       return paramAcceptor.getConnectionCache();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getInboundConnectionCaches() {
/* 153 */     return this.inboundConnectionCaches.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Selector getSelector(int paramInt) {
/* 158 */     return this.selector;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void registerAcceptor(Acceptor paramAcceptor) {
/* 163 */     if (this.orb.transportDebugFlag) {
/* 164 */       dprint(".registerAcceptor->: " + paramAcceptor);
/*     */     }
/* 166 */     this.acceptors.add(paramAcceptor);
/* 167 */     if (this.orb.transportDebugFlag) {
/* 168 */       dprint(".registerAcceptor<-: " + paramAcceptor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getAcceptors() {
/* 174 */     return getAcceptors(null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void unregisterAcceptor(Acceptor paramAcceptor) {
/* 179 */     this.acceptors.remove(paramAcceptor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 185 */       if (this.orb.transportDebugFlag) {
/* 186 */         dprint(".close->");
/*     */       }
/* 188 */       for (ConnectionCache connectionCache : this.outboundConnectionCaches.values()) {
/* 189 */         ((ConnectionCache)connectionCache).close();
/*     */       }
/* 191 */       for (ConnectionCache connectionCache : this.inboundConnectionCaches.values()) {
/* 192 */         ((ConnectionCache)connectionCache).close();
/* 193 */         unregisterAcceptor(((InboundConnectionCache)connectionCache).getAcceptor());
/*     */       } 
/* 195 */       getSelector(0).close();
/*     */     } finally {
/* 197 */       if (this.orb.transportDebugFlag) {
/* 198 */         dprint(".close<-");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection getAcceptors(String paramString, ObjectAdapterId paramObjectAdapterId) {
/* 215 */     Iterator<Acceptor> iterator = this.acceptors.iterator();
/* 216 */     while (iterator.hasNext()) {
/* 217 */       Acceptor acceptor = iterator.next();
/* 218 */       if (acceptor.initialize() && 
/* 219 */         acceptor.shouldRegisterAcceptEvent()) {
/* 220 */         this.orb.getTransportManager().getSelector(0)
/* 221 */           .registerForEvent(acceptor.getEventHandler());
/*     */       }
/*     */     } 
/*     */     
/* 225 */     return this.acceptors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToIORTemplate(IORTemplate paramIORTemplate, Policies paramPolicies, String paramString1, String paramString2, ObjectAdapterId paramObjectAdapterId) {
/* 236 */     Iterator<CorbaAcceptor> iterator = getAcceptors(paramString2, paramObjectAdapterId).iterator();
/* 237 */     while (iterator.hasNext()) {
/* 238 */       CorbaAcceptor corbaAcceptor = iterator.next();
/* 239 */       corbaAcceptor.addToIORTemplate(paramIORTemplate, paramPolicies, paramString1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString) {
/* 251 */     ORBUtility.dprint("CorbaTransportManagerImpl", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/CorbaTransportManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */