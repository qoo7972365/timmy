/*    */ package com.sun.jndi.ldap.pool;
/*    */ 
/*    */ import java.lang.ref.ReferenceQueue;
/*    */ import java.lang.ref.WeakReference;
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
/*    */ class ConnectionsWeakRef
/*    */   extends WeakReference<ConnectionsRef>
/*    */ {
/*    */   private final Connections conns;
/*    */   
/*    */   ConnectionsWeakRef(ConnectionsRef paramConnectionsRef, ReferenceQueue<? super ConnectionsRef> paramReferenceQueue) {
/* 64 */     super(paramConnectionsRef, paramReferenceQueue);
/* 65 */     this.conns = paramConnectionsRef.getConnections();
/*    */   }
/*    */   
/*    */   Connections getConnections() {
/* 69 */     return this.conns;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/pool/ConnectionsWeakRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */