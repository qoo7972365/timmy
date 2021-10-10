/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.pept.transport.Connection;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*     */ import com.sun.corba.se.spi.transport.SocketInfo;
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
/*     */ public class SocketOrChannelContactInfoImpl
/*     */   extends CorbaContactInfoBase
/*     */   implements SocketInfo
/*     */ {
/*     */   protected boolean isHashCodeCached = false;
/*     */   protected int cachedHashCode;
/*     */   protected String socketType;
/*     */   protected String hostname;
/*     */   protected int port;
/*     */   
/*     */   protected SocketOrChannelContactInfoImpl() {}
/*     */   
/*     */   protected SocketOrChannelContactInfoImpl(ORB paramORB, CorbaContactInfoList paramCorbaContactInfoList) {
/*  65 */     this.orb = paramORB;
/*  66 */     this.contactInfoList = paramCorbaContactInfoList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketOrChannelContactInfoImpl(ORB paramORB, CorbaContactInfoList paramCorbaContactInfoList, String paramString1, String paramString2, int paramInt) {
/*  76 */     this(paramORB, paramCorbaContactInfoList);
/*  77 */     this.socketType = paramString1;
/*  78 */     this.hostname = paramString2;
/*  79 */     this.port = paramInt;
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
/*     */   public SocketOrChannelContactInfoImpl(ORB paramORB, CorbaContactInfoList paramCorbaContactInfoList, IOR paramIOR, short paramShort, String paramString1, String paramString2, int paramInt) {
/*  92 */     this(paramORB, paramCorbaContactInfoList, paramString1, paramString2, paramInt);
/*  93 */     this.effectiveTargetIOR = paramIOR;
/*  94 */     this.addressingDisposition = paramShort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConnectionBased() {
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCacheConnection() {
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getConnectionCacheType() {
/* 114 */     return "SocketOrChannelConnectionCache";
/*     */   }
/*     */ 
/*     */   
/*     */   public Connection createConnection() {
/* 119 */     return (Connection)new SocketOrChannelConnectionImpl(this.orb, this, this.socketType, this.hostname, this.port);
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
/*     */   public String getMonitoringName() {
/* 132 */     return "SocketConnections";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 142 */     return this.socketType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHost() {
/* 147 */     return this.hostname;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 152 */     return this.port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 162 */     if (!this.isHashCodeCached) {
/* 163 */       this.cachedHashCode = this.socketType.hashCode() ^ this.hostname.hashCode() ^ this.port;
/* 164 */       this.isHashCodeCached = true;
/*     */     } 
/* 166 */     return this.cachedHashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 171 */     if (paramObject == null)
/* 172 */       return false; 
/* 173 */     if (!(paramObject instanceof SocketOrChannelContactInfoImpl)) {
/* 174 */       return false;
/*     */     }
/*     */     
/* 177 */     SocketOrChannelContactInfoImpl socketOrChannelContactInfoImpl = (SocketOrChannelContactInfoImpl)paramObject;
/*     */ 
/*     */     
/* 180 */     if (this.port != socketOrChannelContactInfoImpl.port) {
/* 181 */       return false;
/*     */     }
/* 183 */     if (!this.hostname.equals(socketOrChannelContactInfoImpl.hostname)) {
/* 184 */       return false;
/*     */     }
/* 186 */     if (this.socketType == null) {
/* 187 */       if (socketOrChannelContactInfoImpl.socketType != null) {
/* 188 */         return false;
/*     */       }
/* 190 */     } else if (!this.socketType.equals(socketOrChannelContactInfoImpl.socketType)) {
/* 191 */       return false;
/*     */     } 
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 198 */     return "SocketOrChannelContactInfoImpl[" + this.socketType + " " + this.hostname + " " + this.port + "]";
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
/*     */   protected void dprint(String paramString) {
/* 213 */     ORBUtility.dprint("SocketOrChannelContactInfoImpl", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/SocketOrChannelContactInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */