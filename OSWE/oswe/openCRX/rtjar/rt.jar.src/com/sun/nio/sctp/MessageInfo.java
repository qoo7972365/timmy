/*     */ package com.sun.nio.sctp;
/*     */ 
/*     */ import java.net.SocketAddress;
/*     */ import jdk.Exported;
/*     */ import sun.nio.ch.sctp.MessageInfoImpl;
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
/*     */ @Exported
/*     */ public abstract class MessageInfo
/*     */ {
/*     */   public static MessageInfo createOutgoing(SocketAddress paramSocketAddress, int paramInt) {
/*  95 */     if (paramInt < 0 || paramInt > 65536) {
/*  96 */       throw new IllegalArgumentException("Invalid stream number");
/*     */     }
/*  98 */     return new MessageInfoImpl(null, paramSocketAddress, paramInt);
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
/*     */   public static MessageInfo createOutgoing(Association paramAssociation, SocketAddress paramSocketAddress, int paramInt) {
/* 131 */     if (paramAssociation == null) {
/* 132 */       throw new IllegalArgumentException("association cannot be null");
/*     */     }
/* 134 */     if (paramInt < 0 || paramInt > 65536) {
/* 135 */       throw new IllegalArgumentException("Invalid stream number");
/*     */     }
/* 137 */     return new MessageInfoImpl(paramAssociation, paramSocketAddress, paramInt);
/*     */   }
/*     */   
/*     */   public abstract SocketAddress address();
/*     */   
/*     */   public abstract Association association();
/*     */   
/*     */   public abstract int bytes();
/*     */   
/*     */   public abstract boolean isComplete();
/*     */   
/*     */   public abstract MessageInfo complete(boolean paramBoolean);
/*     */   
/*     */   public abstract boolean isUnordered();
/*     */   
/*     */   public abstract MessageInfo unordered(boolean paramBoolean);
/*     */   
/*     */   public abstract int payloadProtocolID();
/*     */   
/*     */   public abstract MessageInfo payloadProtocolID(int paramInt);
/*     */   
/*     */   public abstract int streamNumber();
/*     */   
/*     */   public abstract MessageInfo streamNumber(int paramInt);
/*     */   
/*     */   public abstract long timeToLive();
/*     */   
/*     */   public abstract MessageInfo timeToLive(long paramLong);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/nio/sctp/MessageInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */