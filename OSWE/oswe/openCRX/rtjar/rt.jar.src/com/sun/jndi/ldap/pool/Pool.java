/*     */ package com.sun.jndi.ldap.pool;
/*     */ 
/*     */ import com.sun.jndi.ldap.LdapPoolManager;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.naming.NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Pool
/*     */ {
/*  80 */   static final boolean debug = LdapPoolManager.debug;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private static final ReferenceQueue<ConnectionsRef> queue = new ReferenceQueue<>();
/*     */ 
/*     */   
/*  88 */   private static final Collection<Reference<ConnectionsRef>> weakRefs = Collections.synchronizedList(new LinkedList<>());
/*     */   
/*     */   private final int maxSize;
/*     */   private final int prefSize;
/*     */   private final int initSize;
/*     */   private final Map<Object, ConnectionsRef> map;
/*     */   
/*     */   public Pool(int paramInt1, int paramInt2, int paramInt3) {
/*  96 */     this.map = new WeakHashMap<>();
/*  97 */     this.prefSize = paramInt2;
/*  98 */     this.maxSize = paramInt3;
/*  99 */     this.initSize = paramInt1;
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
/*     */   public PooledConnection getPooledConnection(Object paramObject, long paramLong, PooledConnectionFactory paramPooledConnectionFactory) throws NamingException {
/*     */     Connections connections;
/* 120 */     d("get(): ", paramObject);
/* 121 */     if (debug) {
/* 122 */       synchronized (this.map) {
/* 123 */         d("size: ", this.map.size());
/*     */       } 
/*     */     }
/*     */     
/* 127 */     expungeStaleConnections();
/*     */ 
/*     */     
/* 130 */     synchronized (this.map) {
/* 131 */       connections = getConnections(paramObject);
/* 132 */       if (connections == null) {
/* 133 */         d("get(): creating new connections list for ", paramObject);
/*     */ 
/*     */         
/* 136 */         connections = new Connections(paramObject, this.initSize, this.prefSize, this.maxSize, paramPooledConnectionFactory);
/*     */         
/* 138 */         ConnectionsRef connectionsRef = new ConnectionsRef(connections);
/* 139 */         this.map.put(paramObject, connectionsRef);
/*     */ 
/*     */         
/* 142 */         ConnectionsWeakRef connectionsWeakRef = new ConnectionsWeakRef(connectionsRef, queue);
/*     */ 
/*     */ 
/*     */         
/* 146 */         weakRefs.add(connectionsWeakRef);
/*     */       } 
/* 148 */       d("get(): size after: ", this.map.size());
/*     */     } 
/*     */     
/* 151 */     return connections.get(paramLong, paramPooledConnectionFactory);
/*     */   }
/*     */   
/*     */   private Connections getConnections(Object paramObject) {
/* 155 */     ConnectionsRef connectionsRef = this.map.get(paramObject);
/* 156 */     return (connectionsRef != null) ? connectionsRef.getConnections() : null;
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
/*     */   public void expire(long paramLong) {
/*     */     ArrayList arrayList;
/* 170 */     synchronized (this.map) {
/* 171 */       arrayList = new ArrayList(this.map.values());
/*     */     } 
/*     */     
/* 174 */     ArrayList<ConnectionsRef> arrayList1 = new ArrayList();
/*     */     
/* 176 */     for (ConnectionsRef connectionsRef : arrayList) {
/* 177 */       Connections connections = connectionsRef.getConnections();
/* 178 */       if (connections.expire(paramLong)) {
/* 179 */         d("expire(): removing ", connections);
/* 180 */         arrayList1.add(connectionsRef);
/*     */       } 
/*     */     } 
/*     */     
/* 184 */     synchronized (this.map) {
/* 185 */       this.map.values().removeAll(arrayList1);
/*     */     } 
/*     */     
/* 188 */     expungeStaleConnections();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void expungeStaleConnections() {
/* 197 */     ConnectionsWeakRef connectionsWeakRef = null;
/* 198 */     while ((connectionsWeakRef = (ConnectionsWeakRef)queue.poll()) != null) {
/*     */       
/* 200 */       Connections connections = connectionsWeakRef.getConnections();
/*     */       
/* 202 */       if (debug) {
/* 203 */         System.err.println("weak reference cleanup: Closing Connections:" + connections);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 208 */       connections.close();
/* 209 */       weakRefs.remove(connectionsWeakRef);
/* 210 */       connectionsWeakRef.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showStats(PrintStream paramPrintStream) {
/* 219 */     paramPrintStream.println("===== Pool start ======================");
/* 220 */     paramPrintStream.println("maximum pool size: " + this.maxSize);
/* 221 */     paramPrintStream.println("preferred pool size: " + this.prefSize);
/* 222 */     paramPrintStream.println("initial pool size: " + this.initSize);
/*     */     
/* 224 */     synchronized (this.map) {
/* 225 */       paramPrintStream.println("current pool size: " + this.map.size());
/*     */       
/* 227 */       for (Map.Entry<Object, ConnectionsRef> entry : this.map.entrySet()) {
/* 228 */         Object object = entry.getKey();
/* 229 */         Connections connections = ((ConnectionsRef)entry.getValue()).getConnections();
/* 230 */         paramPrintStream.println("   " + object + ":" + connections.getStats());
/*     */       } 
/*     */     } 
/*     */     
/* 234 */     paramPrintStream.println("====== Pool end =====================");
/*     */   }
/*     */   
/*     */   public String toString() {
/* 238 */     synchronized (this.map) {
/* 239 */       return super.toString() + " " + this.map.toString();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void d(String paramString, int paramInt) {
/* 244 */     if (debug) {
/* 245 */       System.err.println(this + "." + paramString + paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */   private void d(String paramString, Object paramObject) {
/* 250 */     if (debug)
/* 251 */       System.err.println(this + "." + paramString + paramObject); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/pool/Pool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */