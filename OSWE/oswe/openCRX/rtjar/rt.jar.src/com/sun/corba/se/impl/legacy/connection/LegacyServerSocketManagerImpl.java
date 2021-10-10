/*     */ package com.sun.corba.se.impl.legacy.connection;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.legacy.connection.LegacyServerSocketEndPointInfo;
/*     */ import com.sun.corba.se.spi.legacy.connection.LegacyServerSocketManager;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.INTERNAL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LegacyServerSocketManagerImpl
/*     */   implements LegacyServerSocketManager
/*     */ {
/*     */   protected ORB orb;
/*     */   private ORBUtilSystemException wrapper;
/*     */   
/*     */   public LegacyServerSocketManagerImpl(ORB paramORB) {
/*  69 */     this.orb = paramORB;
/*  70 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.transport");
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
/*     */   public int legacyGetTransientServerPort(String paramString) {
/*  82 */     return legacyGetServerPort(paramString, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int legacyGetPersistentServerPort(String paramString) {
/*  88 */     if (this.orb.getORBData().getServerIsORBActivated())
/*     */     {
/*  90 */       return legacyGetServerPort(paramString, true); } 
/*  91 */     if (this.orb.getORBData().getPersistentPortInitialized())
/*     */     {
/*  93 */       return this.orb.getORBData().getPersistentServerPort();
/*     */     }
/*  95 */     throw this.wrapper.persistentServerportNotSet(CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int legacyGetTransientOrPersistentServerPort(String paramString) {
/* 104 */     return legacyGetServerPort(paramString, this.orb
/* 105 */         .getORBData()
/* 106 */         .getServerIsORBActivated());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized LegacyServerSocketEndPointInfo legacyGetEndpoint(String paramString) {
/* 115 */     Iterator iterator = getAcceptorIterator();
/* 116 */     while (iterator.hasNext()) {
/* 117 */       LegacyServerSocketEndPointInfo legacyServerSocketEndPointInfo = cast(iterator.next());
/* 118 */       if (legacyServerSocketEndPointInfo != null && paramString.equals(legacyServerSocketEndPointInfo.getName())) {
/* 119 */         return legacyServerSocketEndPointInfo;
/*     */       }
/*     */     } 
/* 122 */     throw new INTERNAL("No acceptor for: " + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean legacyIsLocalServerPort(int paramInt) {
/* 130 */     Iterator iterator = getAcceptorIterator();
/* 131 */     while (iterator.hasNext()) {
/* 132 */       LegacyServerSocketEndPointInfo legacyServerSocketEndPointInfo = cast(iterator.next());
/* 133 */       if (legacyServerSocketEndPointInfo != null && legacyServerSocketEndPointInfo.getPort() == paramInt) {
/* 134 */         return true;
/*     */       }
/*     */     } 
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int legacyGetServerPort(String paramString, boolean paramBoolean) {
/* 147 */     Iterator iterator = getAcceptorIterator();
/* 148 */     while (iterator.hasNext()) {
/* 149 */       LegacyServerSocketEndPointInfo legacyServerSocketEndPointInfo = cast(iterator.next());
/* 150 */       if (legacyServerSocketEndPointInfo != null && legacyServerSocketEndPointInfo.getType().equals(paramString)) {
/* 151 */         if (paramBoolean) {
/* 152 */           return legacyServerSocketEndPointInfo.getLocatorPort();
/*     */         }
/* 154 */         return legacyServerSocketEndPointInfo.getPort();
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Iterator getAcceptorIterator() {
/* 164 */     Collection collection = this.orb.getCorbaTransportManager().getAcceptors(null, null);
/* 165 */     if (collection != null) {
/* 166 */       return collection.iterator();
/*     */     }
/*     */     
/* 169 */     throw this.wrapper.getServerPortCalledBeforeEndpointsInitialized();
/*     */   }
/*     */ 
/*     */   
/*     */   private LegacyServerSocketEndPointInfo cast(Object paramObject) {
/* 174 */     if (paramObject instanceof LegacyServerSocketEndPointInfo) {
/* 175 */       return (LegacyServerSocketEndPointInfo)paramObject;
/*     */     }
/* 177 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString) {
/* 182 */     ORBUtility.dprint("LegacyServerSocketManagerImpl", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/legacy/connection/LegacyServerSocketManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */