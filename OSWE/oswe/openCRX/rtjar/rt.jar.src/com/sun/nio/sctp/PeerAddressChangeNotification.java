/*    */ package com.sun.nio.sctp;
/*    */ 
/*    */ import java.net.SocketAddress;
/*    */ import jdk.Exported;
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
/*    */ @Exported
/*    */ public abstract class PeerAddressChangeNotification
/*    */   implements Notification
/*    */ {
/*    */   public abstract SocketAddress address();
/*    */   
/*    */   public abstract Association association();
/*    */   
/*    */   public abstract AddressChangeEvent event();
/*    */   
/*    */   @Exported
/*    */   public enum AddressChangeEvent
/*    */   {
/* 55 */     ADDR_AVAILABLE,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     ADDR_UNREACHABLE,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 66 */     ADDR_REMOVED,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 71 */     ADDR_ADDED,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 76 */     ADDR_MADE_PRIMARY,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 81 */     ADDR_CONFIRMED;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/nio/sctp/PeerAddressChangeNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */