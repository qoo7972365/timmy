/*     */ package com.sun.jndi.ldap.pool;
/*     */ 
/*     */ import com.sun.jndi.ldap.LdapPoolManager;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.naming.CommunicationException;
/*     */ import javax.naming.InterruptedNamingException;
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
/*     */ final class Connections
/*     */   implements PoolCallback
/*     */ {
/*  66 */   private static final boolean debug = Pool.debug;
/*  67 */   private static final boolean trace = LdapPoolManager.trace;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_SIZE = 10;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int maxSize;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int prefSize;
/*     */ 
/*     */   
/*     */   private final List<ConnectionDesc> conns;
/*     */ 
/*     */   
/*     */   private boolean closed = false;
/*     */ 
/*     */   
/*     */   private Reference<Object> ref;
/*     */ 
/*     */ 
/*     */   
/*     */   Connections(Object paramObject, int paramInt1, int paramInt2, int paramInt3, PooledConnectionFactory paramPooledConnectionFactory) throws NamingException {
/*  93 */     this.maxSize = paramInt3;
/*  94 */     if (paramInt3 > 0) {
/*     */       
/*  96 */       this.prefSize = Math.min(paramInt2, paramInt3);
/*  97 */       paramInt1 = Math.min(paramInt1, paramInt3);
/*     */     } else {
/*  99 */       this.prefSize = paramInt2;
/*     */     } 
/* 101 */     this.conns = new ArrayList<>((paramInt3 > 0) ? paramInt3 : 10);
/*     */ 
/*     */ 
/*     */     
/* 105 */     this.ref = new SoftReference(paramObject);
/*     */     
/* 107 */     d("init size=", paramInt1);
/* 108 */     d("max size=", paramInt3);
/* 109 */     d("preferred size=", paramInt2);
/*     */ 
/*     */ 
/*     */     
/* 113 */     for (byte b = 0; b < paramInt1; b++) {
/* 114 */       PooledConnection pooledConnection = paramPooledConnectionFactory.createPooledConnection(this);
/* 115 */       td("Create ", pooledConnection, paramPooledConnectionFactory);
/* 116 */       this.conns.add(new ConnectionDesc(pooledConnection));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized PooledConnection get(long paramLong, PooledConnectionFactory paramPooledConnectionFactory) throws NamingException {
/* 139 */     long l1 = (paramLong > 0L) ? System.currentTimeMillis() : 0L;
/* 140 */     long l2 = paramLong;
/*     */     
/* 142 */     d("get(): before"); PooledConnection pooledConnection;
/* 143 */     while ((pooledConnection = getOrCreateConnection(paramPooledConnectionFactory)) == null) {
/* 144 */       if (paramLong > 0L && l2 <= 0L) {
/* 145 */         throw new CommunicationException("Timeout exceeded while waiting for a connection: " + paramLong + "ms");
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 150 */         d("get(): waiting");
/* 151 */         if (l2 > 0L) {
/* 152 */           wait(l2);
/*     */         } else {
/* 154 */           wait();
/*     */         } 
/* 156 */       } catch (InterruptedException interruptedException) {
/* 157 */         throw new InterruptedNamingException("Interrupted while waiting for a connection");
/*     */       } 
/*     */ 
/*     */       
/* 161 */       if (paramLong > 0L) {
/* 162 */         long l = System.currentTimeMillis();
/* 163 */         l2 = paramLong - l - l1;
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     d("get(): after");
/* 168 */     return pooledConnection;
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
/*     */   private PooledConnection getOrCreateConnection(PooledConnectionFactory paramPooledConnectionFactory) throws NamingException {
/* 180 */     int i = this.conns.size();
/* 181 */     PooledConnection pooledConnection = null;
/*     */     
/* 183 */     if (this.prefSize <= 0 || i >= this.prefSize)
/*     */     {
/*     */ 
/*     */       
/* 187 */       for (byte b = 0; b < i; b++) {
/* 188 */         ConnectionDesc connectionDesc = this.conns.get(b);
/* 189 */         if ((pooledConnection = connectionDesc.tryUse()) != null) {
/* 190 */           d("get(): use ", pooledConnection);
/* 191 */           td("Use ", pooledConnection);
/* 192 */           return pooledConnection;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 198 */     if (this.maxSize > 0 && i >= this.maxSize) {
/* 199 */       return null;
/*     */     }
/*     */     
/* 202 */     pooledConnection = paramPooledConnectionFactory.createPooledConnection(this);
/* 203 */     td("Create and use ", pooledConnection, paramPooledConnectionFactory);
/* 204 */     this.conns.add(new ConnectionDesc(pooledConnection, true));
/*     */     
/* 206 */     return pooledConnection;
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
/*     */   public synchronized boolean releasePooledConnection(PooledConnection paramPooledConnection) {
/*     */     ConnectionDesc connectionDesc;
/* 219 */     int i = this.conns.indexOf(connectionDesc = new ConnectionDesc(paramPooledConnection));
/*     */     
/* 221 */     d("release(): ", paramPooledConnection);
/*     */     
/* 223 */     if (i >= 0) {
/*     */ 
/*     */       
/* 226 */       if (this.closed || (this.prefSize > 0 && this.conns.size() > this.prefSize)) {
/*     */ 
/*     */         
/* 229 */         d("release(): closing ", paramPooledConnection);
/* 230 */         td("Close ", paramPooledConnection);
/*     */ 
/*     */         
/* 233 */         this.conns.remove(connectionDesc);
/* 234 */         paramPooledConnection.closeConnection();
/*     */       } else {
/*     */         
/* 237 */         d("release(): release ", paramPooledConnection);
/* 238 */         td("Release ", paramPooledConnection);
/*     */ 
/*     */         
/* 241 */         connectionDesc = this.conns.get(i);
/*     */         
/* 243 */         connectionDesc.release();
/*     */       } 
/* 245 */       notifyAll();
/* 246 */       d("release(): notify");
/* 247 */       return true;
/*     */     } 
/* 249 */     return false;
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
/*     */   public synchronized boolean removePooledConnection(PooledConnection paramPooledConnection) {
/* 264 */     if (this.conns.remove(new ConnectionDesc(paramPooledConnection))) {
/* 265 */       d("remove(): ", paramPooledConnection);
/*     */       
/* 267 */       notifyAll();
/*     */       
/* 269 */       d("remove(): notify");
/* 270 */       td("Remove ", paramPooledConnection);
/*     */       
/* 272 */       if (this.conns.isEmpty())
/*     */       {
/*     */         
/* 275 */         this.ref = null;
/*     */       }
/*     */       
/* 278 */       return true;
/*     */     } 
/* 280 */     d("remove(): not found ", paramPooledConnection);
/* 281 */     return false;
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
/*     */   boolean expire(long paramLong) {
/*     */     ArrayList<ConnectionDesc> arrayList1;
/* 294 */     synchronized (this) {
/* 295 */       arrayList1 = new ArrayList<>(this.conns);
/*     */     } 
/* 297 */     ArrayList<ConnectionDesc> arrayList2 = new ArrayList();
/*     */     
/* 299 */     for (ConnectionDesc connectionDesc : arrayList1) {
/* 300 */       d("expire(): ", connectionDesc);
/* 301 */       if (connectionDesc.expire(paramLong)) {
/* 302 */         arrayList2.add(connectionDesc);
/* 303 */         td("expire(): Expired ", connectionDesc);
/*     */       } 
/*     */     } 
/*     */     
/* 307 */     synchronized (this) {
/* 308 */       this.conns.removeAll(arrayList2);
/*     */ 
/*     */ 
/*     */       
/* 312 */       return this.conns.isEmpty();
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
/*     */   synchronized void close() {
/* 324 */     expire(System.currentTimeMillis());
/* 325 */     this.closed = true;
/*     */   }
/*     */   String getStats() {
/*     */     int i;
/* 329 */     byte b1 = 0;
/* 330 */     byte b2 = 0;
/* 331 */     byte b3 = 0;
/* 332 */     long l = 0L;
/*     */ 
/*     */     
/* 335 */     synchronized (this) {
/* 336 */       i = this.conns.size();
/*     */ 
/*     */       
/* 339 */       for (byte b = 0; b < i; b++) {
/* 340 */         ConnectionDesc connectionDesc = this.conns.get(b);
/* 341 */         l += connectionDesc.getUseCount();
/* 342 */         switch (connectionDesc.getState()) {
/*     */           case 0:
/* 344 */             b2++;
/*     */             break;
/*     */           case 1:
/* 347 */             b1++;
/*     */             break;
/*     */           case 2:
/* 350 */             b3++; break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 354 */     return "size=" + i + "; use=" + l + "; busy=" + b2 + "; idle=" + b1 + "; expired=" + b3;
/*     */   }
/*     */ 
/*     */   
/*     */   private void d(String paramString, Object paramObject) {
/* 359 */     if (debug) {
/* 360 */       d(paramString + paramObject);
/*     */     }
/*     */   }
/*     */   
/*     */   private void d(String paramString, int paramInt) {
/* 365 */     if (debug) {
/* 366 */       d(paramString + paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */   private void d(String paramString) {
/* 371 */     if (debug) {
/* 372 */       System.err.println(this + "." + paramString + "; size: " + this.conns.size());
/*     */     }
/*     */   }
/*     */   
/*     */   private void td(String paramString, Object paramObject1, Object paramObject2) {
/* 377 */     if (trace)
/* 378 */       td(paramString + paramObject1 + "[" + paramObject2 + "]"); 
/*     */   }
/*     */   
/*     */   private void td(String paramString, Object paramObject) {
/* 382 */     if (trace)
/* 383 */       td(paramString + paramObject); 
/*     */   }
/*     */   
/*     */   private void td(String paramString) {
/* 387 */     if (trace)
/* 388 */       System.err.println(paramString); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/pool/Connections.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */