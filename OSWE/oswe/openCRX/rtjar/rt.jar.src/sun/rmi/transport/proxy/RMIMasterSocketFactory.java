/*     */ package sun.rmi.transport.proxy;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.NoRouteToHostException;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.rmi.server.LogStream;
/*     */ import java.rmi.server.RMISocketFactory;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.runtime.NewThreadAction;
/*     */ import sun.security.action.GetBooleanAction;
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
/*     */ public class RMIMasterSocketFactory
/*     */   extends RMISocketFactory
/*     */ {
/*  52 */   static int logLevel = LogStream.parseLevel(getLogLevel());
/*     */   
/*     */   private static String getLogLevel() {
/*  55 */     return AccessController.<String>doPrivileged(new GetPropertyAction("sun.rmi.transport.proxy.logLevel"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   static final Log proxyLog = Log.getLog("sun.rmi.transport.tcp.proxy", "transport", logLevel);
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static long connectTimeout = getConnectTimeout();
/*     */   
/*     */   private static long getConnectTimeout() {
/*  68 */     return ((Long)AccessController.<Long>doPrivileged(new GetLongAction("sun.rmi.transport.proxy.connectTimeout", 15000L)))
/*     */       
/*  70 */       .longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final boolean eagerHttpFallback = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.rmi.transport.proxy.eagerHttpFallback")))
/*  76 */     .booleanValue();
/*     */ 
/*     */   
/*  79 */   private Hashtable<String, RMISocketFactory> successTable = new Hashtable<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MaxRememberedHosts = 64;
/*     */ 
/*     */   
/*  86 */   private Vector<String> hostList = new Vector<>(64);
/*     */ 
/*     */   
/*  89 */   protected RMISocketFactory initialFactory = new RMIDirectSocketFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector<RMISocketFactory> altFactoryList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RMIMasterSocketFactory() {
/* 101 */     this.altFactoryList = new Vector<>(2);
/* 102 */     boolean bool = false;
/*     */ 
/*     */     
/*     */     try {
/* 106 */       String str = AccessController.<String>doPrivileged(new GetPropertyAction("http.proxyHost"));
/*     */ 
/*     */       
/* 109 */       if (str == null) {
/* 110 */         str = AccessController.<String>doPrivileged(new GetPropertyAction("proxyHost"));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 115 */       boolean bool1 = ((String)AccessController.<String>doPrivileged(new GetPropertyAction("java.rmi.server.disableHttp", "true"))).equalsIgnoreCase("true");
/*     */       
/* 117 */       if (!bool1 && str != null && str.length() > 0) {
/* 118 */         bool = true;
/*     */       }
/* 120 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 124 */     if (bool) {
/* 125 */       this.altFactoryList.addElement(new RMIHttpToPortSocketFactory());
/* 126 */       this.altFactoryList.addElement(new RMIHttpToCGISocketFactory());
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
/*     */   public Socket createSocket(String paramString, int paramInt) throws IOException {
/* 139 */     if (proxyLog.isLoggable(Log.BRIEF)) {
/* 140 */       proxyLog.log(Log.BRIEF, "host: " + paramString + ", port: " + paramInt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     if (this.altFactoryList.size() == 0) {
/* 148 */       return this.initialFactory.createSocket(paramString, paramInt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     RMISocketFactory rMISocketFactory = this.successTable.get(paramString);
/* 158 */     if (rMISocketFactory != null) {
/* 159 */       if (proxyLog.isLoggable(Log.BRIEF)) {
/* 160 */         proxyLog.log(Log.BRIEF, "previously successful factory found: " + rMISocketFactory);
/*     */       }
/*     */       
/* 163 */       return rMISocketFactory.createSocket(paramString, paramInt);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     Socket socket1 = null;
/* 172 */     Socket socket2 = null;
/*     */ 
/*     */     
/* 175 */     AsyncConnector asyncConnector = new AsyncConnector(this.initialFactory, paramString, paramInt, AccessController.getContext());
/*     */ 
/*     */     
/* 178 */     Throwable throwable = null;
/*     */     
/*     */     try {
/* 181 */       synchronized (asyncConnector) {
/*     */         
/* 183 */         Thread thread = AccessController.<Thread>doPrivileged(new NewThreadAction(asyncConnector, "AsyncConnector", true));
/*     */         
/* 185 */         thread.start();
/*     */         
/*     */         try {
/* 188 */           long l1 = System.currentTimeMillis();
/* 189 */           long l2 = l1 + connectTimeout;
/*     */           do {
/* 191 */             asyncConnector.wait(l2 - l1);
/* 192 */             socket1 = checkConnector(asyncConnector);
/* 193 */             if (socket1 != null)
/*     */               break; 
/* 195 */             l1 = System.currentTimeMillis();
/* 196 */           } while (l1 < l2);
/* 197 */         } catch (InterruptedException interruptedException) {
/* 198 */           throw new InterruptedIOException("interrupted while waiting for connector");
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 204 */       if (socket1 == null) {
/* 205 */         throw new NoRouteToHostException("connect timed out: " + paramString);
/*     */       }
/*     */       
/* 208 */       proxyLog.log(Log.BRIEF, "direct socket connection successful");
/*     */       
/* 210 */       return socket1;
/*     */     }
/* 212 */     catch (UnknownHostException|NoRouteToHostException unknownHostException) {
/* 213 */       throwable = unknownHostException;
/* 214 */     } catch (SocketException socketException) {
/* 215 */       if (eagerHttpFallback) {
/* 216 */         throwable = socketException;
/*     */       } else {
/* 218 */         throw socketException;
/*     */       } 
/*     */     } finally {
/* 221 */       if (throwable != null) {
/*     */         
/* 223 */         if (proxyLog.isLoggable(Log.BRIEF)) {
/* 224 */           proxyLog.log(Log.BRIEF, "direct socket connection failed: ", throwable);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 229 */         for (byte b = 0; b < this.altFactoryList.size(); ) {
/* 230 */           rMISocketFactory = this.altFactoryList.elementAt(b);
/* 231 */           if (proxyLog.isLoggable(Log.BRIEF)) {
/* 232 */             proxyLog.log(Log.BRIEF, "trying with factory: " + rMISocketFactory);
/*     */           }
/*     */ 
/*     */           
/* 236 */           try (Socket null = rMISocketFactory.createSocket(paramString, paramInt)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 242 */             InputStream inputStream = socket.getInputStream();
/* 243 */             int i = inputStream.read();
/* 244 */           } catch (IOException iOException) {
/* 245 */             if (proxyLog.isLoggable(Log.BRIEF)) {
/* 246 */               proxyLog.log(Log.BRIEF, "factory failed: ", iOException);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 251 */           proxyLog.log(Log.BRIEF, "factory succeeded");
/*     */ 
/*     */           
/*     */           try {
/* 255 */             socket2 = rMISocketFactory.createSocket(paramString, paramInt);
/* 256 */           } catch (IOException iOException) {}
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 263 */     synchronized (this.successTable) {
/*     */       
/*     */       try {
/* 266 */         synchronized (asyncConnector) {
/* 267 */           socket1 = checkConnector(asyncConnector);
/*     */         } 
/* 269 */         if (socket1 != null) {
/*     */           
/* 271 */           if (socket2 != null)
/* 272 */             socket2.close(); 
/* 273 */           return socket1;
/*     */         } 
/*     */         
/* 276 */         asyncConnector.notUsed();
/* 277 */       } catch (UnknownHostException|NoRouteToHostException unknownHostException) {
/* 278 */         throwable = unknownHostException;
/* 279 */       } catch (SocketException socketException) {
/* 280 */         if (eagerHttpFallback) {
/* 281 */           throwable = socketException;
/*     */         } else {
/* 283 */           throw socketException;
/*     */         } 
/*     */       } 
/*     */       
/* 287 */       if (socket2 != null) {
/*     */         
/* 289 */         rememberFactory(paramString, rMISocketFactory);
/* 290 */         return socket2;
/*     */       } 
/* 292 */       throw throwable;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void rememberFactory(String paramString, RMISocketFactory paramRMISocketFactory) {
/* 302 */     synchronized (this.successTable) {
/* 303 */       while (this.hostList.size() >= 64) {
/* 304 */         this.successTable.remove(this.hostList.elementAt(0));
/* 305 */         this.hostList.removeElementAt(0);
/*     */       } 
/* 307 */       this.hostList.addElement(paramString);
/* 308 */       this.successTable.put(paramString, paramRMISocketFactory);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Socket checkConnector(AsyncConnector paramAsyncConnector) throws IOException {
/* 319 */     Exception exception = paramAsyncConnector.getException();
/* 320 */     if (exception != null) {
/* 321 */       exception.fillInStackTrace();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 328 */       if (exception instanceof IOException)
/* 329 */         throw (IOException)exception; 
/* 330 */       if (exception instanceof RuntimeException) {
/* 331 */         throw (RuntimeException)exception;
/*     */       }
/* 333 */       throw new Error("internal error: unexpected checked exception: " + exception
/* 334 */           .toString());
/*     */     } 
/*     */     
/* 337 */     return paramAsyncConnector.getSocket();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket createServerSocket(int paramInt) throws IOException {
/* 345 */     return this.initialFactory.createServerSocket(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class AsyncConnector
/*     */     implements Runnable
/*     */   {
/*     */     private RMISocketFactory factory;
/*     */ 
/*     */ 
/*     */     
/*     */     private String host;
/*     */ 
/*     */ 
/*     */     
/*     */     private int port;
/*     */ 
/*     */ 
/*     */     
/*     */     private AccessControlContext acc;
/*     */ 
/*     */     
/* 369 */     private Exception exception = null;
/*     */ 
/*     */     
/* 372 */     private Socket socket = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean cleanUp = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     AsyncConnector(RMISocketFactory param1RMISocketFactory, String param1String, int param1Int, AccessControlContext param1AccessControlContext) {
/* 383 */       this.factory = param1RMISocketFactory;
/* 384 */       this.host = param1String;
/* 385 */       this.port = param1Int;
/* 386 */       this.acc = param1AccessControlContext;
/* 387 */       SecurityManager securityManager = System.getSecurityManager();
/* 388 */       if (securityManager != null) {
/* 389 */         securityManager.checkConnect(param1String, param1Int);
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 409 */         Socket socket = this.factory.createSocket(this.host, this.port);
/* 410 */         synchronized (this) {
/* 411 */           this.socket = socket;
/* 412 */           notify();
/*     */         } 
/* 414 */         RMIMasterSocketFactory.this.rememberFactory(this.host, this.factory);
/* 415 */         synchronized (this) {
/* 416 */           if (this.cleanUp) {
/*     */             try {
/* 418 */               this.socket.close();
/* 419 */             } catch (IOException iOException) {}
/*     */           }
/*     */         } 
/* 422 */       } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 427 */         synchronized (this) {
/* 428 */           this.exception = exception;
/* 429 */           notify();
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
/*     */ 
/*     */ 
/*     */     
/*     */     private synchronized Exception getException() {
/* 448 */       return this.exception;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private synchronized Socket getSocket() {
/* 455 */       return this.socket;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized void notUsed() {
/* 463 */       if (this.socket != null) {
/*     */         try {
/* 465 */           this.socket.close();
/* 466 */         } catch (IOException iOException) {}
/*     */       }
/*     */       
/* 469 */       this.cleanUp = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/proxy/RMIMasterSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */