/*     */ package sun.rmi.transport.tcp;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.net.Socket;
/*     */ import java.rmi.ConnectIOException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.runtime.RuntimeUtil;
/*     */ import sun.rmi.transport.Channel;
/*     */ import sun.rmi.transport.Connection;
/*     */ import sun.rmi.transport.Endpoint;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ import sun.security.action.GetLongAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCPChannel
/*     */   implements Channel
/*     */ {
/*     */   private final TCPEndpoint ep;
/*     */   private final TCPTransport tr;
/*  66 */   private final List<TCPConnection> freeList = new ArrayList<>();
/*     */ 
/*     */   
/*  69 */   private Future<?> reaper = null;
/*     */ 
/*     */   
/*     */   private boolean usingMultiplexer = false;
/*     */   
/*  74 */   private ConnectionMultiplexer multiplexer = null;
/*     */ 
/*     */   
/*     */   private ConnectionAcceptor acceptor;
/*     */ 
/*     */   
/*     */   private AccessControlContext okContext;
/*     */ 
/*     */   
/*     */   private WeakHashMap<AccessControlContext, Reference<AccessControlContext>> authcache;
/*     */ 
/*     */   
/*  86 */   private SecurityManager cacheSecurityManager = null;
/*     */ 
/*     */ 
/*     */   
/*  90 */   private static final long idleTimeout = ((Long)AccessController.<Long>doPrivileged(new GetLongAction("sun.rmi.transport.connectionTimeout", 15000L))).longValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private static final int handshakeTimeout = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("sun.rmi.transport.tcp.handshakeTimeout", 60000))).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private static final int responseTimeout = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("sun.rmi.transport.tcp.responseTimeout", 0))).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private static final ScheduledExecutorService scheduler = ((RuntimeUtil)AccessController.<RuntimeUtil>doPrivileged(new RuntimeUtil.GetInstanceAction()))
/* 107 */     .getScheduler();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TCPChannel(TCPTransport paramTCPTransport, TCPEndpoint paramTCPEndpoint) {
/* 113 */     this.tr = paramTCPTransport;
/* 114 */     this.ep = paramTCPEndpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Endpoint getEndpoint() {
/* 121 */     return this.ep;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkConnectPermission() throws SecurityException {
/* 131 */     SecurityManager securityManager = System.getSecurityManager();
/* 132 */     if (securityManager == null) {
/*     */       return;
/*     */     }
/* 135 */     if (securityManager != this.cacheSecurityManager) {
/*     */       
/* 137 */       this.okContext = null;
/* 138 */       this.authcache = new WeakHashMap<>();
/*     */       
/* 140 */       this.cacheSecurityManager = securityManager;
/*     */     } 
/*     */     
/* 143 */     AccessControlContext accessControlContext = AccessController.getContext();
/*     */ 
/*     */ 
/*     */     
/* 147 */     if (this.okContext == null || (
/* 148 */       !this.okContext.equals(accessControlContext) && !this.authcache.containsKey(accessControlContext))) {
/*     */       
/* 150 */       securityManager.checkConnect(this.ep.getHost(), this.ep.getPort());
/* 151 */       this.authcache.put(accessControlContext, new SoftReference<>(accessControlContext));
/*     */     } 
/*     */ 
/*     */     
/* 155 */     this.okContext = accessControlContext;
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
/*     */   public Connection newConnection() throws RemoteException {
/*     */     TCPConnection tCPConnection;
/*     */     do {
/* 170 */       tCPConnection = null;
/*     */       
/* 172 */       synchronized (this.freeList) {
/* 173 */         int i = this.freeList.size() - 1;
/*     */         
/* 175 */         if (i >= 0) {
/*     */ 
/*     */ 
/*     */           
/* 179 */           checkConnectPermission();
/* 180 */           tCPConnection = this.freeList.get(i);
/* 181 */           this.freeList.remove(i);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 189 */       if (tCPConnection == null)
/*     */         continue; 
/* 191 */       if (!tCPConnection.isDead()) {
/* 192 */         TCPTransport.tcpLog.log(Log.BRIEF, "reuse connection");
/* 193 */         return tCPConnection;
/*     */       } 
/*     */ 
/*     */       
/* 197 */       free(tCPConnection, false);
/*     */     }
/* 199 */     while (tCPConnection != null);
/*     */ 
/*     */     
/* 202 */     return createConnection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection createConnection() throws RemoteException {
/*     */     TCPConnection tCPConnection;
/* 213 */     TCPTransport.tcpLog.log(Log.BRIEF, "create connection");
/*     */     
/* 215 */     if (!this.usingMultiplexer) {
/* 216 */       Socket socket = this.ep.newSocket();
/* 217 */       tCPConnection = new TCPConnection(this, socket);
/*     */ 
/*     */       
/*     */       try {
/* 221 */         DataOutputStream dataOutputStream = new DataOutputStream(tCPConnection.getOutputStream());
/* 222 */         writeTransportHeader(dataOutputStream);
/*     */ 
/*     */         
/* 225 */         if (!tCPConnection.isReusable()) {
/* 226 */           dataOutputStream.writeByte(76);
/*     */         } else {
/* 228 */           dataOutputStream.writeByte(75);
/* 229 */           dataOutputStream.flush();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 236 */           int i = 0;
/*     */           try {
/* 238 */             i = socket.getSoTimeout();
/* 239 */             socket.setSoTimeout(handshakeTimeout);
/* 240 */           } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 245 */           DataInputStream dataInputStream = new DataInputStream(tCPConnection.getInputStream());
/* 246 */           byte b = dataInputStream.readByte();
/* 247 */           if (b != 78) {
/* 248 */             throw new ConnectIOException((b == 79) ? "JRMP StreamProtocol not supported by server" : "non-JRMP server at remote endpoint");
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 254 */           String str = dataInputStream.readUTF();
/* 255 */           int j = dataInputStream.readInt();
/* 256 */           if (TCPTransport.tcpLog.isLoggable(Log.VERBOSE)) {
/* 257 */             TCPTransport.tcpLog.log(Log.VERBOSE, "server suggested " + str + ":" + j);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 263 */           TCPEndpoint.setLocalHost(str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 270 */           TCPEndpoint tCPEndpoint = TCPEndpoint.getLocalEndpoint(0, null, null);
/* 271 */           dataOutputStream.writeUTF(tCPEndpoint.getHost());
/* 272 */           dataOutputStream.writeInt(tCPEndpoint.getPort());
/* 273 */           if (TCPTransport.tcpLog.isLoggable(Log.VERBOSE)) {
/* 274 */             TCPTransport.tcpLog.log(Log.VERBOSE, "using " + tCPEndpoint
/* 275 */                 .getHost() + ":" + tCPEndpoint.getPort());
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 291 */             socket.setSoTimeout((i != 0) ? i : responseTimeout);
/*     */           
/*     */           }
/* 294 */           catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */           
/* 298 */           dataOutputStream.flush();
/*     */         } 
/* 300 */       } catch (IOException iOException) {
/*     */         try {
/* 302 */           tCPConnection.close();
/* 303 */         } catch (Exception exception) {}
/* 304 */         if (iOException instanceof RemoteException) {
/* 305 */           throw (RemoteException)iOException;
/*     */         }
/* 307 */         throw new ConnectIOException("error during JRMP connection establishment", iOException);
/*     */       } 
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/* 313 */         tCPConnection = this.multiplexer.openConnection();
/* 314 */       } catch (IOException iOException) {
/* 315 */         synchronized (this) {
/* 316 */           this.usingMultiplexer = false;
/* 317 */           this.multiplexer = null;
/*     */         } 
/* 319 */         throw new ConnectIOException("error opening virtual connection over multiplexed connection", iOException);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 324 */     return tCPConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void free(Connection paramConnection, boolean paramBoolean) {
/* 334 */     if (paramConnection == null)
/*     */       return; 
/* 336 */     if (paramBoolean && paramConnection.isReusable()) {
/* 337 */       long l = System.currentTimeMillis();
/* 338 */       TCPConnection tCPConnection = (TCPConnection)paramConnection;
/*     */       
/* 340 */       TCPTransport.tcpLog.log(Log.BRIEF, "reuse connection");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 346 */       synchronized (this.freeList) {
/* 347 */         this.freeList.add(tCPConnection);
/* 348 */         if (this.reaper == null) {
/* 349 */           TCPTransport.tcpLog.log(Log.BRIEF, "create reaper");
/*     */           
/* 351 */           this.reaper = scheduler.scheduleWithFixedDelay(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 354 */                   TCPTransport.tcpLog.log(Log.VERBOSE, "wake up");
/*     */                   
/* 356 */                   TCPChannel.this.freeCachedConnections();
/*     */                 }
/*     */               }idleTimeout, idleTimeout, TimeUnit.MILLISECONDS);
/*     */         } 
/*     */       } 
/*     */       
/* 362 */       tCPConnection.setLastUseTime(l);
/* 363 */       tCPConnection.setExpiration(l + idleTimeout);
/*     */     } else {
/* 365 */       TCPTransport.tcpLog.log(Log.BRIEF, "close connection");
/*     */       
/*     */       try {
/* 368 */         paramConnection.close();
/* 369 */       } catch (IOException iOException) {}
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
/*     */   private void writeTransportHeader(DataOutputStream paramDataOutputStream) throws RemoteException {
/*     */     try {
/* 382 */       DataOutputStream dataOutputStream = new DataOutputStream(paramDataOutputStream);
/*     */       
/* 384 */       dataOutputStream.writeInt(1246907721);
/* 385 */       dataOutputStream.writeShort(2);
/* 386 */     } catch (IOException iOException) {
/* 387 */       throw new ConnectIOException("error writing JRMP transport header", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void useMultiplexer(ConnectionMultiplexer paramConnectionMultiplexer) {
/* 398 */     this.multiplexer = paramConnectionMultiplexer;
/*     */     
/* 400 */     this.usingMultiplexer = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void acceptMultiplexConnection(Connection paramConnection) {
/* 407 */     if (this.acceptor == null) {
/* 408 */       this.acceptor = new ConnectionAcceptor(this.tr);
/* 409 */       this.acceptor.startNewAcceptor();
/*     */     } 
/* 411 */     this.acceptor.accept(paramConnection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shedCache() {
/*     */     Connection[] arrayOfConnection;
/* 421 */     synchronized (this.freeList) {
/* 422 */       arrayOfConnection = this.freeList.<Connection>toArray(new Connection[this.freeList.size()]);
/* 423 */       this.freeList.clear();
/*     */     } 
/*     */ 
/*     */     
/* 427 */     for (int i = arrayOfConnection.length; --i >= 0; ) {
/* 428 */       Connection connection = arrayOfConnection[i];
/* 429 */       arrayOfConnection[i] = null;
/*     */       try {
/* 431 */         connection.close();
/* 432 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void freeCachedConnections() {
/* 442 */     synchronized (this.freeList) {
/* 443 */       int i = this.freeList.size();
/*     */       
/* 445 */       if (i > 0) {
/* 446 */         long l = System.currentTimeMillis();
/* 447 */         ListIterator<TCPConnection> listIterator = this.freeList.listIterator(i);
/*     */         
/* 449 */         while (listIterator.hasPrevious()) {
/* 450 */           TCPConnection tCPConnection = listIterator.previous();
/* 451 */           if (tCPConnection.expired(l)) {
/* 452 */             TCPTransport.tcpLog.log(Log.VERBOSE, "connection timeout expired");
/*     */ 
/*     */             
/*     */             try {
/* 456 */               tCPConnection.close();
/* 457 */             } catch (IOException iOException) {}
/*     */ 
/*     */             
/* 460 */             listIterator.remove();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 465 */       if (this.freeList.isEmpty()) {
/* 466 */         this.reaper.cancel(false);
/* 467 */         this.reaper = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/tcp/TCPChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */