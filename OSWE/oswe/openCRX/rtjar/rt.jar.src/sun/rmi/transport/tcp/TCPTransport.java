/*     */ package sun.rmi.transport.tcp;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.UndeclaredThrowableException;
/*     */ import java.net.BindException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.ExportException;
/*     */ import java.rmi.server.LogStream;
/*     */ import java.rmi.server.RMIFailureHandler;
/*     */ import java.rmi.server.RMISocketFactory;
/*     */ import java.rmi.server.ServerNotActiveException;
/*     */ import java.rmi.server.UID;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permissions;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.SynchronousQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.runtime.NewThreadAction;
/*     */ import sun.rmi.transport.Channel;
/*     */ import sun.rmi.transport.Connection;
/*     */ import sun.rmi.transport.DGCAckHandler;
/*     */ import sun.rmi.transport.Endpoint;
/*     */ import sun.rmi.transport.StreamRemoteCall;
/*     */ import sun.rmi.transport.Target;
/*     */ import sun.rmi.transport.Transport;
/*     */ import sun.rmi.transport.proxy.HttpReceiveSocket;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ import sun.security.action.GetLongAction;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCPTransport
/*     */   extends Transport
/*     */ {
/*  94 */   static final Log tcpLog = Log.getLog("sun.rmi.transport.tcp", "tcp", 
/*  95 */       LogStream.parseLevel(AccessController.<String>doPrivileged(new GetPropertyAction("sun.rmi.transport.tcp.logLevel"))));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   private static final int maxConnectionThreads = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("sun.rmi.transport.tcp.maxConnectionThreads", 2147483647))).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private static final long threadKeepAliveTime = ((Long)AccessController.<Long>doPrivileged(new GetLongAction("sun.rmi.transport.tcp.threadKeepAliveTime", 60000L))).longValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   private static final ExecutorService connectionThreadPool = new ThreadPoolExecutor(0, maxConnectionThreads, threadKeepAliveTime, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), new ThreadFactory()
/*     */       {
/*     */ 
/*     */         
/*     */         public Thread newThread(Runnable param1Runnable)
/*     */         {
/* 117 */           return AccessController.<Thread>doPrivileged(new NewThreadAction(param1Runnable, "TCP Connection(idle)", true, true));
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */   
/* 123 */   private static final boolean disableIncomingHttp = ((String)AccessController.<String>doPrivileged(new GetPropertyAction("sun.rmi.server.disableIncomingHttp", "true")))
/*     */     
/* 125 */     .equalsIgnoreCase("true");
/*     */ 
/*     */   
/* 128 */   private static final AtomicInteger connectionCount = new AtomicInteger(0);
/*     */ 
/*     */ 
/*     */   
/* 132 */   private static final ThreadLocal<ConnectionHandler> threadConnectionHandler = new ThreadLocal<>();
/*     */   private static final AccessControlContext NOPERMS_ACC;
/*     */   private final LinkedList<TCPEndpoint> epList;
/*     */   
/*     */   static {
/* 137 */     Permissions permissions = new Permissions();
/* 138 */     ProtectionDomain[] arrayOfProtectionDomain = { new ProtectionDomain(null, permissions) };
/* 139 */     NOPERMS_ACC = new AccessControlContext(arrayOfProtectionDomain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   private int exportCount = 0;
/*     */   
/* 147 */   private ServerSocket server = null;
/*     */   
/* 149 */   private final Map<TCPEndpoint, Reference<TCPChannel>> channelTable = new WeakHashMap<>();
/*     */ 
/*     */ 
/*     */   
/* 153 */   static final RMISocketFactory defaultSocketFactory = RMISocketFactory.getDefaultSocketFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   private static final int connectionReadTimeout = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("sun.rmi.transport.tcp.readTimeout", 7200000))).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TCPTransport(LinkedList<TCPEndpoint> paramLinkedList) {
/* 171 */     this.epList = paramLinkedList;
/* 172 */     if (tcpLog.isLoggable(Log.BRIEF)) {
/* 173 */       tcpLog.log(Log.BRIEF, "Version = 2, ep = " + 
/* 174 */           getEndpoint());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shedConnectionCaches() {
/*     */     ArrayList<TCPChannel> arrayList;
/* 184 */     synchronized (this.channelTable) {
/* 185 */       arrayList = new ArrayList(this.channelTable.values().size());
/* 186 */       for (Reference<TCPChannel> reference : this.channelTable.values()) {
/* 187 */         TCPChannel tCPChannel = reference.get();
/* 188 */         if (tCPChannel != null) {
/* 189 */           arrayList.add(tCPChannel);
/*     */         }
/*     */       } 
/*     */     } 
/* 193 */     for (TCPChannel tCPChannel : arrayList) {
/* 194 */       tCPChannel.shedCache();
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
/*     */   public TCPChannel getChannel(Endpoint paramEndpoint) {
/* 208 */     TCPChannel tCPChannel = null;
/* 209 */     if (paramEndpoint instanceof TCPEndpoint) {
/* 210 */       synchronized (this.channelTable) {
/* 211 */         Reference<TCPChannel> reference = this.channelTable.get(paramEndpoint);
/* 212 */         if (reference != null) {
/* 213 */           tCPChannel = reference.get();
/*     */         }
/* 215 */         if (tCPChannel == null) {
/* 216 */           TCPEndpoint tCPEndpoint = (TCPEndpoint)paramEndpoint;
/* 217 */           tCPChannel = new TCPChannel(this, tCPEndpoint);
/* 218 */           this.channelTable.put(tCPEndpoint, new WeakReference<>(tCPChannel));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 223 */     return tCPChannel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void free(Endpoint paramEndpoint) {
/* 231 */     if (paramEndpoint instanceof TCPEndpoint) {
/* 232 */       synchronized (this.channelTable) {
/* 233 */         Reference<TCPChannel> reference = this.channelTable.remove(paramEndpoint);
/* 234 */         if (reference != null) {
/* 235 */           TCPChannel tCPChannel = reference.get();
/* 236 */           if (tCPChannel != null) {
/* 237 */             tCPChannel.shedCache();
/*     */           }
/*     */         } 
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
/*     */   public void exportObject(Target paramTarget) throws RemoteException {
/* 253 */     synchronized (this) {
/* 254 */       listen();
/* 255 */       this.exportCount++;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     boolean bool = false;
/*     */     try {
/* 265 */       super.exportObject(paramTarget);
/* 266 */       bool = true;
/*     */     } finally {
/* 268 */       if (!bool) {
/* 269 */         synchronized (this) {
/* 270 */           decrementExportCount();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected synchronized void targetUnexported() {
/* 277 */     decrementExportCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void decrementExportCount() {
/* 285 */     assert Thread.holdsLock(this);
/* 286 */     this.exportCount--;
/* 287 */     if (this.exportCount == 0 && getEndpoint().getListenPort() != 0) {
/* 288 */       ServerSocket serverSocket = this.server;
/* 289 */       this.server = null;
/*     */       try {
/* 291 */         serverSocket.close();
/* 292 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkAcceptPermission(AccessControlContext paramAccessControlContext) {
/* 302 */     SecurityManager securityManager = System.getSecurityManager();
/* 303 */     if (securityManager == null) {
/*     */       return;
/*     */     }
/* 306 */     ConnectionHandler connectionHandler = threadConnectionHandler.get();
/* 307 */     if (connectionHandler == null) {
/* 308 */       throw new Error("checkAcceptPermission not in ConnectionHandler thread");
/*     */     }
/*     */     
/* 311 */     connectionHandler.checkAcceptPermission(securityManager, paramAccessControlContext);
/*     */   }
/*     */   
/*     */   private TCPEndpoint getEndpoint() {
/* 315 */     synchronized (this.epList) {
/* 316 */       return this.epList.getLast();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void listen() throws RemoteException {
/* 324 */     assert Thread.holdsLock(this);
/* 325 */     TCPEndpoint tCPEndpoint = getEndpoint();
/* 326 */     int i = tCPEndpoint.getPort();
/*     */     
/* 328 */     if (this.server == null) {
/* 329 */       if (tcpLog.isLoggable(Log.BRIEF)) {
/* 330 */         tcpLog.log(Log.BRIEF, "(port " + i + ") create server socket");
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 335 */         this.server = tCPEndpoint.newServerSocket();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 341 */         Thread thread = AccessController.<Thread>doPrivileged(new NewThreadAction(new AcceptLoop(this.server), "TCP Accept-" + i, true));
/*     */ 
/*     */         
/* 344 */         thread.start();
/* 345 */       } catch (BindException bindException) {
/* 346 */         throw new ExportException("Port already in use: " + i, bindException);
/* 347 */       } catch (IOException iOException) {
/* 348 */         throw new ExportException("Listen failed on port: " + i, iOException);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 353 */       SecurityManager securityManager = System.getSecurityManager();
/* 354 */       if (securityManager != null) {
/* 355 */         securityManager.checkListen(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class AcceptLoop
/*     */     implements Runnable
/*     */   {
/*     */     private final ServerSocket serverSocket;
/*     */ 
/*     */     
/* 368 */     private long lastExceptionTime = 0L;
/*     */     private int recentExceptionCount;
/*     */     
/*     */     AcceptLoop(ServerSocket param1ServerSocket) {
/* 372 */       this.serverSocket = param1ServerSocket;
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       try {
/* 377 */         executeAcceptLoop();
/*     */       } finally {
/*     */ 
/*     */         
/*     */         try {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 386 */           this.serverSocket.close();
/* 387 */         } catch (IOException iOException) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void executeAcceptLoop() {
/* 397 */       if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 398 */         TCPTransport.tcpLog.log(Log.BRIEF, "listening on port " + TCPTransport.this
/* 399 */             .getEndpoint().getPort());
/*     */       }
/*     */       
/*     */       while (true) {
/* 403 */         Socket socket = null;
/*     */         try {
/* 405 */           socket = this.serverSocket.accept();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 410 */           InetAddress inetAddress = socket.getInetAddress();
/*     */           
/* 412 */           String str = (inetAddress != null) ? inetAddress.getHostAddress() : "0.0.0.0";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 420 */             TCPTransport.connectionThreadPool.execute(new TCPTransport.ConnectionHandler(socket, str));
/*     */           }
/* 422 */           catch (RejectedExecutionException rejectedExecutionException) {
/* 423 */             TCPTransport.closeSocket(socket);
/* 424 */             TCPTransport.tcpLog.log(Log.BRIEF, "rejected connection from " + str);
/*     */           }
/*     */         
/*     */         }
/* 428 */         catch (Throwable throwable) {
/*     */ 
/*     */ 
/*     */           
/*     */           try { 
/*     */ 
/*     */ 
/*     */             
/* 436 */             if (this.serverSocket.isClosed())
/*     */             
/*     */             { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 454 */               if (socket != null)
/* 455 */                 TCPTransport.closeSocket(socket);  break; }  try { if (TCPTransport.tcpLog.isLoggable(Level.WARNING)) TCPTransport.tcpLog.log(Level.WARNING, "accept loop for " + this.serverSocket + " throws", throwable);  } catch (Throwable throwable1) {} } finally { if (socket != null) TCPTransport.closeSocket(socket);
/*     */              }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 463 */           if (!(throwable instanceof SecurityException)) {
/*     */             try {
/* 465 */               TCPEndpoint.shedConnectionCaches();
/* 466 */             } catch (Throwable throwable1) {}
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 475 */           if (throwable instanceof Exception || throwable instanceof OutOfMemoryError || throwable instanceof NoClassDefFoundError) {
/*     */ 
/*     */ 
/*     */             
/* 479 */             if (!continueAfterAcceptFailure(throwable))
/*     */               return; 
/*     */             continue;
/*     */           } 
/* 483 */           if (throwable instanceof Error) {
/* 484 */             throw (Error)throwable;
/*     */           }
/* 486 */           throw new UndeclaredThrowableException(throwable);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean continueAfterAcceptFailure(Throwable param1Throwable) {
/* 502 */       RMIFailureHandler rMIFailureHandler = RMISocketFactory.getFailureHandler();
/* 503 */       if (rMIFailureHandler != null) {
/* 504 */         return rMIFailureHandler.failure((param1Throwable instanceof Exception) ? (Exception)param1Throwable : new InvocationTargetException(param1Throwable));
/*     */       }
/*     */       
/* 507 */       throttleLoopOnException();
/* 508 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void throttleLoopOnException() {
/* 518 */       long l = System.currentTimeMillis();
/* 519 */       if (this.lastExceptionTime == 0L || l - this.lastExceptionTime > 5000L) {
/*     */         
/* 521 */         this.lastExceptionTime = l;
/* 522 */         this.recentExceptionCount = 0;
/*     */       
/*     */       }
/* 525 */       else if (++this.recentExceptionCount >= 10) {
/*     */         try {
/* 527 */           Thread.sleep(10000L);
/* 528 */         } catch (InterruptedException interruptedException) {}
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void closeSocket(Socket paramSocket) {
/*     */     try {
/* 538 */       paramSocket.close();
/* 539 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void handleMessages(Connection paramConnection, boolean paramBoolean) {
/* 550 */     int i = getEndpoint().getPort();
/*     */     
/*     */     try {
/* 553 */       DataInputStream dataInputStream = new DataInputStream(paramConnection.getInputStream()); do {
/*     */         StreamRemoteCall streamRemoteCall; DataOutputStream dataOutputStream;
/* 555 */         int j = dataInputStream.read();
/* 556 */         if (j == -1) {
/* 557 */           if (tcpLog.isLoggable(Log.BRIEF)) {
/* 558 */             tcpLog.log(Log.BRIEF, "(port " + i + ") connection closed");
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 564 */         if (tcpLog.isLoggable(Log.BRIEF)) {
/* 565 */           tcpLog.log(Log.BRIEF, "(port " + i + ") op = " + j);
/*     */         }
/*     */ 
/*     */         
/* 569 */         switch (j) {
/*     */           
/*     */           case 80:
/* 572 */             streamRemoteCall = new StreamRemoteCall(paramConnection);
/* 573 */             if (!serviceCall(streamRemoteCall)) {
/*     */               return;
/*     */             }
/*     */             break;
/*     */ 
/*     */           
/*     */           case 82:
/* 580 */             dataOutputStream = new DataOutputStream(paramConnection.getOutputStream());
/* 581 */             dataOutputStream.writeByte(83);
/* 582 */             paramConnection.releaseOutputStream();
/*     */             break;
/*     */           
/*     */           case 84:
/* 586 */             DGCAckHandler.received(UID.read(dataInputStream));
/*     */             break;
/*     */           
/*     */           default:
/* 590 */             throw new IOException("unknown transport op " + j);
/*     */         } 
/* 592 */       } while (paramBoolean);
/*     */     }
/* 594 */     catch (IOException iOException) {
/*     */       
/* 596 */       if (tcpLog.isLoggable(Log.BRIEF)) {
/* 597 */         tcpLog.log(Log.BRIEF, "(port " + i + ") exception: ", iOException);
/*     */       }
/*     */     } finally {
/*     */       
/*     */       try {
/* 602 */         paramConnection.close();
/* 603 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getClientHost() throws ServerNotActiveException {
/* 614 */     ConnectionHandler connectionHandler = threadConnectionHandler.get();
/* 615 */     if (connectionHandler != null) {
/* 616 */       return connectionHandler.getClientHost();
/*     */     }
/* 618 */     throw new ServerNotActiveException("not in a remote call");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ConnectionHandler
/*     */     implements Runnable
/*     */   {
/*     */     private static final int POST = 1347375956;
/*     */ 
/*     */ 
/*     */     
/*     */     private AccessControlContext okContext;
/*     */ 
/*     */     
/*     */     private Map<AccessControlContext, Reference<AccessControlContext>> authCache;
/*     */ 
/*     */     
/* 637 */     private SecurityManager cacheSecurityManager = null;
/*     */     
/*     */     private Socket socket;
/*     */     private String remoteHost;
/*     */     
/*     */     ConnectionHandler(Socket param1Socket, String param1String) {
/* 643 */       this.socket = param1Socket;
/* 644 */       this.remoteHost = param1String;
/*     */     }
/*     */     
/*     */     String getClientHost() {
/* 648 */       return this.remoteHost;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void checkAcceptPermission(SecurityManager param1SecurityManager, AccessControlContext param1AccessControlContext) {
/* 662 */       if (param1SecurityManager != this.cacheSecurityManager) {
/* 663 */         this.okContext = null;
/* 664 */         this.authCache = new WeakHashMap<>();
/*     */         
/* 666 */         this.cacheSecurityManager = param1SecurityManager;
/*     */       } 
/* 668 */       if (param1AccessControlContext.equals(this.okContext) || this.authCache.containsKey(param1AccessControlContext)) {
/*     */         return;
/*     */       }
/* 671 */       InetAddress inetAddress = this.socket.getInetAddress();
/* 672 */       String str = (inetAddress != null) ? inetAddress.getHostAddress() : "*";
/*     */       
/* 674 */       param1SecurityManager.checkAccept(str, this.socket.getPort());
/*     */       
/* 676 */       this.authCache.put(param1AccessControlContext, new SoftReference<>(param1AccessControlContext));
/* 677 */       this.okContext = param1AccessControlContext;
/*     */     }
/*     */     
/*     */     public void run() {
/* 681 */       Thread thread = Thread.currentThread();
/* 682 */       String str = thread.getName();
/*     */       try {
/* 684 */         thread.setName("RMI TCP Connection(" + TCPTransport
/* 685 */             .connectionCount.incrementAndGet() + ")-" + this.remoteHost);
/*     */         
/* 687 */         AccessController.doPrivileged(() -> {
/*     */               run0();
/*     */               return null;
/* 690 */             }TCPTransport.NOPERMS_ACC);
/*     */       } finally {
/* 692 */         thread.setName(str);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void run0() {
/* 697 */       TCPEndpoint tCPEndpoint = TCPTransport.this.getEndpoint();
/* 698 */       int i = tCPEndpoint.getPort();
/*     */       
/* 700 */       TCPTransport.threadConnectionHandler.set(this);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 706 */         this.socket.setTcpNoDelay(true);
/* 707 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 712 */         if (TCPTransport.connectionReadTimeout > 0)
/* 713 */           this.socket.setSoTimeout(TCPTransport.connectionReadTimeout); 
/* 714 */       } catch (Exception exception) {} try {
/*     */         TCPEndpoint tCPEndpoint1; TCPChannel tCPChannel; TCPConnection tCPConnection;
/*     */         String str;
/*     */         int m;
/*     */         ConnectionMultiplexer connectionMultiplexer;
/* 719 */         InputStream inputStream1 = this.socket.getInputStream();
/* 720 */         InputStream inputStream2 = inputStream1.markSupported() ? inputStream1 : new BufferedInputStream(inputStream1);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 725 */         inputStream2.mark(4);
/* 726 */         DataInputStream dataInputStream = new DataInputStream(inputStream2);
/* 727 */         int j = dataInputStream.readInt();
/*     */         
/* 729 */         if (j == 1347375956) {
/* 730 */           if (TCPTransport.disableIncomingHttp) {
/* 731 */             throw new RemoteException("RMI over HTTP is disabled");
/*     */           }
/* 733 */           TCPTransport.tcpLog.log(Log.BRIEF, "decoding HTTP-wrapped call");
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 738 */           inputStream2.reset();
/*     */           
/*     */           try {
/* 741 */             this.socket = (Socket)new HttpReceiveSocket(this.socket, inputStream2, null);
/* 742 */             this.remoteHost = "0.0.0.0";
/* 743 */             inputStream1 = this.socket.getInputStream();
/* 744 */             inputStream2 = new BufferedInputStream(inputStream1);
/* 745 */             dataInputStream = new DataInputStream(inputStream2);
/* 746 */             j = dataInputStream.readInt();
/*     */           }
/* 748 */           catch (IOException iOException) {
/* 749 */             throw new RemoteException("Error HTTP-unwrapping call", iOException);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 757 */         short s = dataInputStream.readShort();
/* 758 */         if (j != 1246907721 || s != 2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 764 */           TCPTransport.closeSocket(this.socket);
/*     */           
/*     */           return;
/*     */         } 
/* 768 */         OutputStream outputStream = this.socket.getOutputStream();
/* 769 */         BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
/*     */         
/* 771 */         DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
/*     */         
/* 773 */         int k = this.socket.getPort();
/*     */         
/* 775 */         if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 776 */           TCPTransport.tcpLog.log(Log.BRIEF, "accepted socket from [" + this.remoteHost + ":" + k + "]");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 785 */         byte b = dataInputStream.readByte();
/* 786 */         switch (b) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 76:
/* 793 */             tCPEndpoint1 = new TCPEndpoint(this.remoteHost, this.socket.getLocalPort(), tCPEndpoint.getClientSocketFactory(), tCPEndpoint.getServerSocketFactory());
/* 794 */             tCPChannel = new TCPChannel(TCPTransport.this, tCPEndpoint1);
/* 795 */             tCPConnection = new TCPConnection(tCPChannel, this.socket, inputStream2, bufferedOutputStream);
/*     */ 
/*     */             
/* 798 */             TCPTransport.this.handleMessages(tCPConnection, false);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 75:
/* 803 */             dataOutputStream.writeByte(78);
/*     */ 
/*     */             
/* 806 */             if (TCPTransport.tcpLog.isLoggable(Log.VERBOSE)) {
/* 807 */               TCPTransport.tcpLog.log(Log.VERBOSE, "(port " + i + ") suggesting " + this.remoteHost + ":" + k);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 812 */             dataOutputStream.writeUTF(this.remoteHost);
/* 813 */             dataOutputStream.writeInt(k);
/* 814 */             dataOutputStream.flush();
/*     */ 
/*     */ 
/*     */             
/* 818 */             str = dataInputStream.readUTF();
/* 819 */             m = dataInputStream.readInt();
/* 820 */             if (TCPTransport.tcpLog.isLoggable(Log.VERBOSE)) {
/* 821 */               TCPTransport.tcpLog.log(Log.VERBOSE, "(port " + i + ") client using " + str + ":" + m);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 829 */             tCPEndpoint1 = new TCPEndpoint(this.remoteHost, this.socket.getLocalPort(), tCPEndpoint.getClientSocketFactory(), tCPEndpoint.getServerSocketFactory());
/* 830 */             tCPChannel = new TCPChannel(TCPTransport.this, tCPEndpoint1);
/* 831 */             tCPConnection = new TCPConnection(tCPChannel, this.socket, inputStream2, bufferedOutputStream);
/*     */ 
/*     */             
/* 834 */             TCPTransport.this.handleMessages(tCPConnection, true);
/*     */             break;
/*     */           
/*     */           case 77:
/* 838 */             if (TCPTransport.tcpLog.isLoggable(Log.VERBOSE)) {
/* 839 */               TCPTransport.tcpLog.log(Log.VERBOSE, "(port " + i + ") accepting multiplex protocol");
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 844 */             dataOutputStream.writeByte(78);
/*     */ 
/*     */             
/* 847 */             if (TCPTransport.tcpLog.isLoggable(Log.VERBOSE)) {
/* 848 */               TCPTransport.tcpLog.log(Log.VERBOSE, "(port " + i + ") suggesting " + this.remoteHost + ":" + k);
/*     */             }
/*     */ 
/*     */             
/* 852 */             dataOutputStream.writeUTF(this.remoteHost);
/* 853 */             dataOutputStream.writeInt(k);
/* 854 */             dataOutputStream.flush();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 859 */             tCPEndpoint1 = new TCPEndpoint(dataInputStream.readUTF(), dataInputStream.readInt(), tCPEndpoint.getClientSocketFactory(), tCPEndpoint.getServerSocketFactory());
/* 860 */             if (TCPTransport.tcpLog.isLoggable(Log.VERBOSE)) {
/* 861 */               TCPTransport.tcpLog.log(Log.VERBOSE, "(port " + i + ") client using " + tCPEndpoint1
/*     */                   
/* 863 */                   .getHost() + ":" + tCPEndpoint1.getPort());
/*     */             }
/*     */ 
/*     */             
/* 867 */             synchronized (TCPTransport.this.channelTable) {
/*     */               
/* 869 */               tCPChannel = TCPTransport.this.getChannel(tCPEndpoint1);
/* 870 */               connectionMultiplexer = new ConnectionMultiplexer(tCPChannel, inputStream2, outputStream, false);
/*     */ 
/*     */               
/* 873 */               tCPChannel.useMultiplexer(connectionMultiplexer);
/*     */             } 
/* 875 */             connectionMultiplexer.run();
/*     */             break;
/*     */ 
/*     */           
/*     */           default:
/* 880 */             dataOutputStream.writeByte(79);
/* 881 */             dataOutputStream.flush();
/*     */             break;
/*     */         } 
/*     */       
/* 885 */       } catch (IOException iOException) {
/*     */         
/* 887 */         TCPTransport.tcpLog.log(Log.BRIEF, "terminated with exception:", iOException);
/*     */       } finally {
/* 889 */         TCPTransport.closeSocket(this.socket);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/tcp/TCPTransport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */