/*     */ package sun.net.httpserver;
/*     */ 
/*     */ import com.sun.net.httpserver.Filter;
/*     */ import com.sun.net.httpserver.Headers;
/*     */ import com.sun.net.httpserver.HttpContext;
/*     */ import com.sun.net.httpserver.HttpExchange;
/*     */ import com.sun.net.httpserver.HttpHandler;
/*     */ import com.sun.net.httpserver.HttpServer;
/*     */ import com.sun.net.httpserver.HttpsConfigurator;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.BindException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ServerImpl
/*     */   implements TimeSource
/*     */ {
/*     */   private String protocol;
/*     */   private boolean https;
/*     */   private Executor executor;
/*     */   private HttpsConfigurator httpsConfig;
/*     */   private SSLContext sslContext;
/*     */   private ContextList contexts;
/*     */   private InetSocketAddress address;
/*     */   private ServerSocketChannel schan;
/*     */   private Selector selector;
/*     */   private SelectionKey listenerKey;
/*     */   private Set<HttpConnection> idleConnections;
/*     */   private Set<HttpConnection> allConnections;
/*     */   private Set<HttpConnection> reqConnections;
/*     */   private Set<HttpConnection> rspConnections;
/*     */   private List<Event> events;
/*  65 */   private Object lolock = new Object();
/*     */   private volatile boolean finished = false;
/*     */   private volatile boolean terminating = false;
/*     */   private boolean bound = false;
/*     */   private boolean started = false;
/*     */   private volatile long time;
/*  71 */   private volatile long subticks = 0L;
/*     */   
/*     */   private volatile long ticks;
/*     */   private HttpServer wrapper;
/*  75 */   static final int CLOCK_TICK = ServerConfig.getClockTick();
/*  76 */   static final long IDLE_INTERVAL = ServerConfig.getIdleInterval();
/*  77 */   static final int MAX_IDLE_CONNECTIONS = ServerConfig.getMaxIdleConnections();
/*  78 */   static final long TIMER_MILLIS = ServerConfig.getTimerMillis();
/*  79 */   static final long MAX_REQ_TIME = getTimeMillis(ServerConfig.getMaxReqTime());
/*  80 */   static final long MAX_RSP_TIME = getTimeMillis(ServerConfig.getMaxRspTime());
/*  81 */   static final boolean timer1Enabled = (MAX_REQ_TIME != -1L || MAX_RSP_TIME != -1L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Timer timer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Timer timer1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Logger logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Dispatcher dispatcher;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException {
/* 126 */     if (this.bound) {
/* 127 */       throw new BindException("HttpServer already bound");
/*     */     }
/* 129 */     if (paramInetSocketAddress == null) {
/* 130 */       throw new NullPointerException("null address");
/*     */     }
/* 132 */     ServerSocket serverSocket = this.schan.socket();
/* 133 */     serverSocket.bind(paramInetSocketAddress, paramInt);
/* 134 */     this.bound = true;
/*     */   }
/*     */   
/*     */   public void start() {
/* 138 */     if (!this.bound || this.started || this.finished) {
/* 139 */       throw new IllegalStateException("server in wrong state");
/*     */     }
/* 141 */     if (this.executor == null) {
/* 142 */       this.executor = new DefaultExecutor();
/*     */     }
/* 144 */     Thread thread = new Thread(this.dispatcher);
/* 145 */     this.started = true;
/* 146 */     thread.start();
/*     */   }
/*     */   
/*     */   public void setExecutor(Executor paramExecutor) {
/* 150 */     if (this.started) {
/* 151 */       throw new IllegalStateException("server already started");
/*     */     }
/* 153 */     this.executor = paramExecutor;
/*     */   }
/*     */   private static class DefaultExecutor implements Executor { private DefaultExecutor() {}
/*     */     
/*     */     public void execute(Runnable param1Runnable) {
/* 158 */       param1Runnable.run();
/*     */     } }
/*     */ 
/*     */   
/*     */   public Executor getExecutor() {
/* 163 */     return this.executor;
/*     */   }
/*     */   
/*     */   public void setHttpsConfigurator(HttpsConfigurator paramHttpsConfigurator) {
/* 167 */     if (paramHttpsConfigurator == null) {
/* 168 */       throw new NullPointerException("null HttpsConfigurator");
/*     */     }
/* 170 */     if (this.started) {
/* 171 */       throw new IllegalStateException("server already started");
/*     */     }
/* 173 */     this.httpsConfig = paramHttpsConfigurator;
/* 174 */     this.sslContext = paramHttpsConfigurator.getSSLContext();
/*     */   }
/*     */   
/*     */   public HttpsConfigurator getHttpsConfigurator() {
/* 178 */     return this.httpsConfig;
/*     */   }
/*     */   
/*     */   public void stop(int paramInt) {
/* 182 */     if (paramInt < 0) {
/* 183 */       throw new IllegalArgumentException("negative delay parameter");
/*     */     }
/* 185 */     this.terminating = true; 
/* 186 */     try { this.schan.close(); } catch (IOException iOException) {}
/* 187 */     this.selector.wakeup();
/* 188 */     long l = System.currentTimeMillis() + (paramInt * 1000);
/* 189 */     while (System.currentTimeMillis() < l) {
/* 190 */       delay();
/* 191 */       if (this.finished) {
/*     */         break;
/*     */       }
/*     */     } 
/* 195 */     this.finished = true;
/* 196 */     this.selector.wakeup();
/* 197 */     synchronized (this.allConnections) {
/* 198 */       for (HttpConnection httpConnection : this.allConnections) {
/* 199 */         httpConnection.close();
/*     */       }
/*     */     } 
/* 202 */     this.allConnections.clear();
/* 203 */     this.idleConnections.clear();
/* 204 */     this.timer.cancel();
/* 205 */     if (timer1Enabled) {
/* 206 */       this.timer1.cancel();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized HttpContextImpl createContext(String paramString, HttpHandler paramHttpHandler) {
/* 213 */     if (paramHttpHandler == null || paramString == null) {
/* 214 */       throw new NullPointerException("null handler, or path parameter");
/*     */     }
/* 216 */     HttpContextImpl httpContextImpl = new HttpContextImpl(this.protocol, paramString, paramHttpHandler, this);
/* 217 */     this.contexts.add(httpContextImpl);
/* 218 */     this.logger.config("context created: " + paramString);
/* 219 */     return httpContextImpl;
/*     */   }
/*     */   
/*     */   public synchronized HttpContextImpl createContext(String paramString) {
/* 223 */     if (paramString == null) {
/* 224 */       throw new NullPointerException("null path parameter");
/*     */     }
/* 226 */     HttpContextImpl httpContextImpl = new HttpContextImpl(this.protocol, paramString, null, this);
/* 227 */     this.contexts.add(httpContextImpl);
/* 228 */     this.logger.config("context created: " + paramString);
/* 229 */     return httpContextImpl;
/*     */   }
/*     */   
/*     */   public synchronized void removeContext(String paramString) throws IllegalArgumentException {
/* 233 */     if (paramString == null) {
/* 234 */       throw new NullPointerException("null path parameter");
/*     */     }
/* 236 */     this.contexts.remove(this.protocol, paramString);
/* 237 */     this.logger.config("context removed: " + paramString);
/*     */   }
/*     */   
/*     */   public synchronized void removeContext(HttpContext paramHttpContext) throws IllegalArgumentException {
/* 241 */     if (!(paramHttpContext instanceof HttpContextImpl)) {
/* 242 */       throw new IllegalArgumentException("wrong HttpContext type");
/*     */     }
/* 244 */     this.contexts.remove((HttpContextImpl)paramHttpContext);
/* 245 */     this.logger.config("context removed: " + paramHttpContext.getPath());
/*     */   }
/*     */   
/*     */   public InetSocketAddress getAddress() {
/* 249 */     return AccessController.<InetSocketAddress>doPrivileged(new PrivilegedAction<InetSocketAddress>()
/*     */         {
/*     */           public InetSocketAddress run() {
/* 252 */             return 
/* 253 */               (InetSocketAddress)ServerImpl.this.schan.socket()
/* 254 */               .getLocalSocketAddress();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   Selector getSelector() {
/* 260 */     return this.selector;
/*     */   }
/*     */   
/*     */   void addEvent(Event paramEvent) {
/* 264 */     synchronized (this.lolock) {
/* 265 */       this.events.add(paramEvent);
/* 266 */       this.selector.wakeup();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   class Dispatcher
/*     */     implements Runnable
/*     */   {
/*     */     private void handleEvent(Event param1Event) {
/* 275 */       ExchangeImpl exchangeImpl = param1Event.exchange;
/* 276 */       HttpConnection httpConnection = exchangeImpl.getConnection();
/*     */       try {
/* 278 */         if (param1Event instanceof WriteFinishedEvent) {
/*     */           
/* 280 */           int i = ServerImpl.this.endExchange();
/* 281 */           if (ServerImpl.this.terminating && i == 0) {
/* 282 */             ServerImpl.this.finished = true;
/*     */           }
/* 284 */           ServerImpl.this.responseCompleted(httpConnection);
/* 285 */           LeftOverInputStream leftOverInputStream = exchangeImpl.getOriginalInputStream();
/* 286 */           if (!leftOverInputStream.isEOF()) {
/* 287 */             exchangeImpl.close = true;
/*     */           }
/* 289 */           if (exchangeImpl.close || ServerImpl.this.idleConnections.size() >= ServerImpl.MAX_IDLE_CONNECTIONS) {
/* 290 */             httpConnection.close();
/* 291 */             ServerImpl.this.allConnections.remove(httpConnection);
/*     */           }
/* 293 */           else if (leftOverInputStream.isDataBuffered()) {
/*     */             
/* 295 */             ServerImpl.this.requestStarted(httpConnection);
/* 296 */             handle(httpConnection.getChannel(), httpConnection);
/*     */           } else {
/* 298 */             this.connsToRegister.add(httpConnection);
/*     */           }
/*     */         
/*     */         } 
/* 302 */       } catch (IOException iOException) {
/* 303 */         ServerImpl.this.logger.log(Level.FINER, "Dispatcher (1)", iOException);
/*     */ 
/*     */         
/* 306 */         httpConnection.close();
/*     */       } 
/*     */     }
/*     */     
/* 310 */     final LinkedList<HttpConnection> connsToRegister = new LinkedList<>();
/*     */ 
/*     */ 
/*     */     
/*     */     void reRegister(HttpConnection param1HttpConnection) {
/*     */       try {
/* 316 */         SocketChannel socketChannel = param1HttpConnection.getChannel();
/* 317 */         socketChannel.configureBlocking(false);
/* 318 */         SelectionKey selectionKey = socketChannel.register(ServerImpl.this.selector, 1);
/* 319 */         selectionKey.attach(param1HttpConnection);
/* 320 */         param1HttpConnection.selectionKey = selectionKey;
/* 321 */         param1HttpConnection.time = ServerImpl.this.getTime() + ServerImpl.IDLE_INTERVAL;
/* 322 */         ServerImpl.this.idleConnections.add(param1HttpConnection);
/* 323 */       } catch (IOException iOException) {
/* 324 */         ServerImpl.dprint(iOException);
/* 325 */         ServerImpl.this.logger.log(Level.FINER, "Dispatcher(8)", iOException);
/* 326 */         param1HttpConnection.close();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void run() {
/* 331 */       while (!ServerImpl.this.finished) {
/*     */         try {
/* 333 */           List list = null;
/* 334 */           synchronized (ServerImpl.this.lolock) {
/* 335 */             if (ServerImpl.this.events.size() > 0) {
/* 336 */               list = ServerImpl.this.events;
/* 337 */               ServerImpl.this.events = new LinkedList();
/*     */             } 
/*     */           } 
/*     */           
/* 341 */           if (list != null) {
/* 342 */             for (Event event : list) {
/* 343 */               handleEvent(event);
/*     */             }
/*     */           }
/*     */           
/* 347 */           for (HttpConnection httpConnection : this.connsToRegister) {
/* 348 */             reRegister(httpConnection);
/*     */           }
/* 350 */           this.connsToRegister.clear();
/*     */           
/* 352 */           ServerImpl.this.selector.select(1000L);
/*     */ 
/*     */           
/* 355 */           Set<SelectionKey> set = ServerImpl.this.selector.selectedKeys();
/* 356 */           Iterator<SelectionKey> iterator = set.iterator();
/* 357 */           while (iterator.hasNext()) {
/* 358 */             SelectionKey selectionKey = iterator.next();
/* 359 */             iterator.remove();
/* 360 */             if (selectionKey.equals(ServerImpl.this.listenerKey)) {
/* 361 */               if (ServerImpl.this.terminating) {
/*     */                 continue;
/*     */               }
/* 364 */               SocketChannel socketChannel = ServerImpl.this.schan.accept();
/*     */ 
/*     */               
/* 367 */               if (ServerConfig.noDelay()) {
/* 368 */                 socketChannel.socket().setTcpNoDelay(true);
/*     */               }
/*     */               
/* 371 */               if (socketChannel == null) {
/*     */                 continue;
/*     */               }
/* 374 */               socketChannel.configureBlocking(false);
/* 375 */               SelectionKey selectionKey1 = socketChannel.register(ServerImpl.this.selector, 1);
/* 376 */               HttpConnection httpConnection = new HttpConnection();
/* 377 */               httpConnection.selectionKey = selectionKey1;
/* 378 */               httpConnection.setChannel(socketChannel);
/* 379 */               selectionKey1.attach(httpConnection);
/* 380 */               ServerImpl.this.requestStarted(httpConnection);
/* 381 */               ServerImpl.this.allConnections.add(httpConnection); continue;
/*     */             } 
/*     */             try {
/* 384 */               if (selectionKey.isReadable()) {
/*     */                 
/* 386 */                 SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
/* 387 */                 HttpConnection httpConnection = (HttpConnection)selectionKey.attachment();
/*     */                 
/* 389 */                 selectionKey.cancel();
/* 390 */                 socketChannel.configureBlocking(true);
/* 391 */                 if (ServerImpl.this.idleConnections.remove(httpConnection))
/*     */                 {
/*     */                   
/* 394 */                   ServerImpl.this.requestStarted(httpConnection);
/*     */                 }
/* 396 */                 handle(socketChannel, httpConnection);
/*     */                 continue;
/*     */               } 
/*     */               assert false;
/* 400 */             } catch (CancelledKeyException cancelledKeyException) {
/* 401 */               handleException(selectionKey, null);
/* 402 */             } catch (IOException iOException) {
/* 403 */               handleException(selectionKey, iOException);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 408 */           ServerImpl.this.selector.selectNow();
/* 409 */         } catch (IOException iOException) {
/* 410 */           ServerImpl.this.logger.log(Level.FINER, "Dispatcher (4)", iOException);
/* 411 */         } catch (Exception exception) {
/* 412 */           ServerImpl.this.logger.log(Level.FINER, "Dispatcher (7)", exception);
/*     */         } 
/*     */       }  
/* 415 */       try { ServerImpl.this.selector.close(); } catch (Exception exception) {}
/*     */     }
/*     */     
/*     */     private void handleException(SelectionKey param1SelectionKey, Exception param1Exception) {
/* 419 */       HttpConnection httpConnection = (HttpConnection)param1SelectionKey.attachment();
/* 420 */       if (param1Exception != null) {
/* 421 */         ServerImpl.this.logger.log(Level.FINER, "Dispatcher (2)", param1Exception);
/*     */       }
/* 423 */       ServerImpl.this.closeConnection(httpConnection);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void handle(SocketChannel param1SocketChannel, HttpConnection param1HttpConnection) throws IOException {
/*     */       try {
/* 430 */         ServerImpl.Exchange exchange = new ServerImpl.Exchange(param1SocketChannel, ServerImpl.this.protocol, param1HttpConnection);
/* 431 */         ServerImpl.this.executor.execute(exchange);
/* 432 */       } catch (HttpError httpError) {
/* 433 */         ServerImpl.this.logger.log(Level.FINER, "Dispatcher (4)", httpError);
/* 434 */         ServerImpl.this.closeConnection(param1HttpConnection);
/* 435 */       } catch (IOException iOException) {
/* 436 */         ServerImpl.this.logger.log(Level.FINER, "Dispatcher (5)", iOException);
/* 437 */         ServerImpl.this.closeConnection(param1HttpConnection);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 442 */   static boolean debug = ServerConfig.debugEnabled(); private int exchangeCount;
/*     */   
/*     */   static synchronized void dprint(String paramString) {
/* 445 */     if (debug) {
/* 446 */       System.out.println(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   static synchronized void dprint(Exception paramException) {
/* 451 */     if (debug) {
/* 452 */       System.out.println(paramException);
/* 453 */       paramException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   Logger getLogger() {
/* 458 */     return this.logger;
/*     */   }
/*     */   
/*     */   private void closeConnection(HttpConnection paramHttpConnection) {
/* 462 */     paramHttpConnection.close();
/* 463 */     this.allConnections.remove(paramHttpConnection);
/* 464 */     switch (paramHttpConnection.getState()) {
/*     */       case REQUEST:
/* 466 */         this.reqConnections.remove(paramHttpConnection);
/*     */         break;
/*     */       case RESPONSE:
/* 469 */         this.rspConnections.remove(paramHttpConnection);
/*     */         break;
/*     */       case IDLE:
/* 472 */         this.idleConnections.remove(paramHttpConnection);
/*     */         break;
/*     */     } 
/* 475 */     assert !this.reqConnections.remove(paramHttpConnection);
/* 476 */     assert !this.rspConnections.remove(paramHttpConnection);
/* 477 */     assert !this.idleConnections.remove(paramHttpConnection);
/*     */   }
/*     */   
/*     */   class Exchange
/*     */     implements Runnable
/*     */   {
/*     */     SocketChannel chan;
/*     */     HttpConnection connection;
/*     */     HttpContextImpl context;
/*     */     InputStream rawin;
/*     */     OutputStream rawout;
/*     */     String protocol;
/*     */     ExchangeImpl tx;
/*     */     HttpContextImpl ctx;
/*     */     boolean rejected = false;
/*     */     
/*     */     Exchange(SocketChannel param1SocketChannel, String param1String, HttpConnection param1HttpConnection) throws IOException {
/* 494 */       this.chan = param1SocketChannel;
/* 495 */       this.connection = param1HttpConnection;
/* 496 */       this.protocol = param1String;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 501 */       this.context = this.connection.getHttpContext();
/*     */       
/* 503 */       SSLEngine sSLEngine = null;
/* 504 */       String str = null;
/* 505 */       SSLStreams sSLStreams = null; try {
/*     */         boolean bool;
/* 507 */         if (this.context != null) {
/* 508 */           this.rawin = this.connection.getInputStream();
/* 509 */           this.rawout = this.connection.getRawOutputStream();
/* 510 */           bool = false;
/*     */         } else {
/*     */           
/* 513 */           bool = true;
/* 514 */           if (ServerImpl.this.https) {
/* 515 */             if (ServerImpl.this.sslContext == null) {
/* 516 */               ServerImpl.this.logger.warning("SSL connection received. No https contxt created");
/* 517 */               throw new HttpError("No SSL context established");
/*     */             } 
/* 519 */             sSLStreams = new SSLStreams(ServerImpl.this, ServerImpl.this.sslContext, this.chan);
/* 520 */             this.rawin = sSLStreams.getInputStream();
/* 521 */             this.rawout = sSLStreams.getOutputStream();
/* 522 */             sSLEngine = sSLStreams.getSSLEngine();
/* 523 */             this.connection.sslStreams = sSLStreams;
/*     */           } else {
/* 525 */             this.rawin = new BufferedInputStream(new Request.ReadStream(ServerImpl.this, this.chan));
/*     */ 
/*     */ 
/*     */             
/* 529 */             this.rawout = new Request.WriteStream(ServerImpl.this, this.chan);
/*     */           } 
/*     */ 
/*     */           
/* 533 */           this.connection.raw = this.rawin;
/* 534 */           this.connection.rawout = this.rawout;
/*     */         } 
/* 536 */         Request request = new Request(this.rawin, this.rawout);
/* 537 */         str = request.requestLine();
/* 538 */         if (str == null) {
/*     */           
/* 540 */           ServerImpl.this.closeConnection(this.connection);
/*     */           return;
/*     */         } 
/* 543 */         int i = str.indexOf(' ');
/* 544 */         if (i == -1) {
/* 545 */           reject(400, str, "Bad request line");
/*     */           
/*     */           return;
/*     */         } 
/* 549 */         String str1 = str.substring(0, i);
/* 550 */         int j = i + 1;
/* 551 */         i = str.indexOf(' ', j);
/* 552 */         if (i == -1) {
/* 553 */           reject(400, str, "Bad request line");
/*     */           
/*     */           return;
/*     */         } 
/* 557 */         String str2 = str.substring(j, i);
/* 558 */         URI uRI = new URI(str2);
/* 559 */         j = i + 1;
/* 560 */         String str3 = str.substring(j);
/* 561 */         Headers headers1 = request.headers();
/* 562 */         String str4 = headers1.getFirst("Transfer-encoding");
/* 563 */         long l = 0L;
/* 564 */         if (str4 != null && str4.equalsIgnoreCase("chunked")) {
/* 565 */           l = -1L;
/*     */         } else {
/* 567 */           str4 = headers1.getFirst("Content-Length");
/* 568 */           if (str4 != null) {
/* 569 */             l = Long.parseLong(str4);
/*     */           }
/* 571 */           if (l == 0L) {
/* 572 */             ServerImpl.this.requestCompleted(this.connection);
/*     */           }
/*     */         } 
/* 575 */         this.ctx = ServerImpl.this.contexts.findContext(this.protocol, uRI.getPath());
/* 576 */         if (this.ctx == null) {
/* 577 */           reject(404, str, "No context found for request");
/*     */           
/*     */           return;
/*     */         } 
/* 581 */         this.connection.setContext(this.ctx);
/* 582 */         if (this.ctx.getHandler() == null) {
/* 583 */           reject(500, str, "No handler for context");
/*     */           
/*     */           return;
/*     */         } 
/* 587 */         this.tx = new ExchangeImpl(str1, uRI, request, l, this.connection);
/*     */ 
/*     */         
/* 590 */         String str5 = headers1.getFirst("Connection");
/* 591 */         Headers headers2 = this.tx.getResponseHeaders();
/*     */         
/* 593 */         if (str5 != null && str5.equalsIgnoreCase("close")) {
/* 594 */           this.tx.close = true;
/*     */         }
/* 596 */         if (str3.equalsIgnoreCase("http/1.0")) {
/* 597 */           this.tx.http10 = true;
/* 598 */           if (str5 == null) {
/* 599 */             this.tx.close = true;
/* 600 */             headers2.set("Connection", "close");
/* 601 */           } else if (str5.equalsIgnoreCase("keep-alive")) {
/* 602 */             headers2.set("Connection", "keep-alive");
/* 603 */             int k = (int)(ServerConfig.getIdleInterval() / 1000L);
/* 604 */             int m = ServerConfig.getMaxIdleConnections();
/* 605 */             String str7 = "timeout=" + k + ", max=" + m;
/* 606 */             headers2.set("Keep-Alive", str7);
/*     */           } 
/*     */         } 
/*     */         
/* 610 */         if (bool) {
/* 611 */           this.connection.setParameters(this.rawin, this.rawout, this.chan, sSLEngine, sSLStreams, ServerImpl.this
/*     */               
/* 613 */               .sslContext, this.protocol, this.ctx, this.rawin);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 621 */         String str6 = headers1.getFirst("Expect");
/* 622 */         if (str6 != null && str6.equalsIgnoreCase("100-continue")) {
/* 623 */           ServerImpl.this.logReply(100, str, null);
/* 624 */           sendReply(100, false, null);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 635 */         List<Filter> list1 = this.ctx.getSystemFilters();
/* 636 */         List<Filter> list2 = this.ctx.getFilters();
/*     */         
/* 638 */         Filter.Chain chain1 = new Filter.Chain(list1, this.ctx.getHandler());
/* 639 */         Filter.Chain chain2 = new Filter.Chain(list2, new LinkHandler(chain1));
/*     */ 
/*     */         
/* 642 */         this.tx.getRequestBody();
/* 643 */         this.tx.getResponseBody();
/* 644 */         if (ServerImpl.this.https) {
/* 645 */           chain2.doFilter(new HttpsExchangeImpl(this.tx));
/*     */         } else {
/* 647 */           chain2.doFilter(new HttpExchangeImpl(this.tx));
/*     */         }
/*     */       
/* 650 */       } catch (IOException iOException) {
/* 651 */         ServerImpl.this.logger.log(Level.FINER, "ServerImpl.Exchange (1)", iOException);
/* 652 */         ServerImpl.this.closeConnection(this.connection);
/* 653 */       } catch (NumberFormatException numberFormatException) {
/* 654 */         reject(400, str, "NumberFormatException thrown");
/*     */       }
/* 656 */       catch (URISyntaxException uRISyntaxException) {
/* 657 */         reject(400, str, "URISyntaxException thrown");
/*     */       }
/* 659 */       catch (Exception exception) {
/* 660 */         ServerImpl.this.logger.log(Level.FINER, "ServerImpl.Exchange (2)", exception);
/* 661 */         ServerImpl.this.closeConnection(this.connection);
/*     */       } 
/*     */     }
/*     */     
/*     */     class LinkHandler
/*     */       implements HttpHandler
/*     */     {
/*     */       Filter.Chain nextChain;
/*     */       
/*     */       LinkHandler(Filter.Chain param2Chain) {
/* 671 */         this.nextChain = param2Chain;
/*     */       }
/*     */       
/*     */       public void handle(HttpExchange param2HttpExchange) throws IOException {
/* 675 */         this.nextChain.doFilter(param2HttpExchange);
/*     */       }
/*     */     }
/*     */     
/*     */     void reject(int param1Int, String param1String1, String param1String2) {
/* 680 */       this.rejected = true;
/* 681 */       ServerImpl.this.logReply(param1Int, param1String1, param1String2);
/* 682 */       sendReply(param1Int, false, "<h1>" + param1Int + 
/* 683 */           Code.msg(param1Int) + "</h1>" + param1String2);
/*     */       
/* 685 */       ServerImpl.this.closeConnection(this.connection);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void sendReply(int param1Int, boolean param1Boolean, String param1String) {
/*     */       try {
/* 692 */         StringBuilder stringBuilder = new StringBuilder(512);
/* 693 */         stringBuilder.append("HTTP/1.1 ")
/* 694 */           .append(param1Int).append(Code.msg(param1Int)).append("\r\n");
/*     */         
/* 696 */         if (param1String != null && param1String.length() != 0) {
/* 697 */           stringBuilder.append("Content-Length: ")
/* 698 */             .append(param1String.length()).append("\r\n")
/* 699 */             .append("Content-Type: text/html\r\n");
/*     */         } else {
/* 701 */           stringBuilder.append("Content-Length: 0\r\n");
/* 702 */           param1String = "";
/*     */         } 
/* 704 */         if (param1Boolean) {
/* 705 */           stringBuilder.append("Connection: close\r\n");
/*     */         }
/* 707 */         stringBuilder.append("\r\n").append(param1String);
/* 708 */         String str = stringBuilder.toString();
/* 709 */         byte[] arrayOfByte = str.getBytes("ISO8859_1");
/* 710 */         this.rawout.write(arrayOfByte);
/* 711 */         this.rawout.flush();
/* 712 */         if (param1Boolean) {
/* 713 */           ServerImpl.this.closeConnection(this.connection);
/*     */         }
/* 715 */       } catch (IOException iOException) {
/* 716 */         ServerImpl.this.logger.log(Level.FINER, "ServerImpl.sendReply", iOException);
/* 717 */         ServerImpl.this.closeConnection(this.connection);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   void logReply(int paramInt, String paramString1, String paramString2) {
/*     */     String str1;
/* 724 */     if (!this.logger.isLoggable(Level.FINE)) {
/*     */       return;
/*     */     }
/* 727 */     if (paramString2 == null) {
/* 728 */       paramString2 = "";
/*     */     }
/*     */     
/* 731 */     if (paramString1.length() > 80) {
/* 732 */       str1 = paramString1.substring(0, 80) + "<TRUNCATED>";
/*     */     } else {
/* 734 */       str1 = paramString1;
/*     */     } 
/*     */     
/* 737 */     String str2 = str1 + " [" + paramInt + " " + Code.msg(paramInt) + "] (" + paramString2 + ")";
/* 738 */     this.logger.fine(str2);
/*     */   }
/*     */   
/*     */   long getTicks() {
/* 742 */     return this.ticks;
/*     */   }
/*     */   
/*     */   public long getTime() {
/* 746 */     return this.time;
/*     */   }
/*     */   
/*     */   void delay() {
/* 750 */     Thread.yield();
/*     */     try {
/* 752 */       Thread.sleep(200L);
/* 753 */     } catch (InterruptedException interruptedException) {}
/*     */   }
/*     */   
/* 756 */   ServerImpl(HttpServer paramHttpServer, String paramString, InetSocketAddress paramInetSocketAddress, int paramInt) throws IOException { this.exchangeCount = 0; this.protocol = paramString; this.wrapper = paramHttpServer; this.logger = Logger.getLogger("com.sun.net.httpserver"); ServerConfig.checkLegacyProperties(this.logger); this.https = paramString.equalsIgnoreCase("https"); this.address = paramInetSocketAddress; this.contexts = new ContextList(); this.schan = ServerSocketChannel.open(); if (paramInetSocketAddress != null) { ServerSocket serverSocket = this.schan.socket(); serverSocket.bind(paramInetSocketAddress, paramInt); this.bound = true; }
/*     */      this.selector = Selector.open(); this.schan.configureBlocking(false); this.listenerKey = this.schan.register(this.selector, 16); this.dispatcher = new Dispatcher(); this.idleConnections = Collections.synchronizedSet(new HashSet<>()); this.allConnections = Collections.synchronizedSet(new HashSet<>()); this.reqConnections = Collections.synchronizedSet(new HashSet<>()); this.rspConnections = Collections.synchronizedSet(new HashSet<>()); this.time = System.currentTimeMillis(); this.timer = new Timer("server-timer", true); this.timer.schedule(new ServerTimerTask(), CLOCK_TICK, CLOCK_TICK); if (timer1Enabled) {
/*     */       this.timer1 = new Timer("server-timer1", true); this.timer1.schedule(new ServerTimerTask1(), TIMER_MILLIS, TIMER_MILLIS); this.logger.config("HttpServer timer1 enabled period in ms:  " + TIMER_MILLIS); this.logger.config("MAX_REQ_TIME:  " + MAX_REQ_TIME); this.logger.config("MAX_RSP_TIME:  " + MAX_RSP_TIME);
/* 759 */     }  this.events = new LinkedList<>(); this.logger.config("HttpServer created " + paramString + " " + paramInetSocketAddress); } synchronized void startExchange() { this.exchangeCount++; }
/*     */ 
/*     */   
/*     */   synchronized int endExchange() {
/* 763 */     this.exchangeCount--;
/* 764 */     assert this.exchangeCount >= 0;
/* 765 */     return this.exchangeCount;
/*     */   }
/*     */   
/*     */   HttpServer getWrapper() {
/* 769 */     return this.wrapper;
/*     */   }
/*     */   
/*     */   void requestStarted(HttpConnection paramHttpConnection) {
/* 773 */     paramHttpConnection.creationTime = getTime();
/* 774 */     paramHttpConnection.setState(HttpConnection.State.REQUEST);
/* 775 */     this.reqConnections.add(paramHttpConnection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void requestCompleted(HttpConnection paramHttpConnection) {
/* 786 */     assert paramHttpConnection.getState() == HttpConnection.State.REQUEST;
/* 787 */     this.reqConnections.remove(paramHttpConnection);
/* 788 */     paramHttpConnection.rspStartedTime = getTime();
/* 789 */     this.rspConnections.add(paramHttpConnection);
/* 790 */     paramHttpConnection.setState(HttpConnection.State.RESPONSE);
/*     */   }
/*     */ 
/*     */   
/*     */   void responseCompleted(HttpConnection paramHttpConnection) {
/* 795 */     assert paramHttpConnection.getState() == HttpConnection.State.RESPONSE;
/* 796 */     this.rspConnections.remove(paramHttpConnection);
/* 797 */     paramHttpConnection.setState(HttpConnection.State.IDLE);
/*     */   }
/*     */ 
/*     */   
/*     */   class ServerTimerTask
/*     */     extends TimerTask
/*     */   {
/*     */     public void run() {
/* 805 */       LinkedList<HttpConnection> linkedList = new LinkedList();
/* 806 */       ServerImpl.this.time = System.currentTimeMillis();
/* 807 */       ServerImpl.this.ticks++;
/* 808 */       synchronized (ServerImpl.this.idleConnections) {
/* 809 */         for (HttpConnection httpConnection : ServerImpl.this.idleConnections) {
/* 810 */           if (httpConnection.time <= ServerImpl.this.time) {
/* 811 */             linkedList.add(httpConnection);
/*     */           }
/*     */         } 
/* 814 */         for (HttpConnection httpConnection : linkedList) {
/* 815 */           ServerImpl.this.idleConnections.remove(httpConnection);
/* 816 */           ServerImpl.this.allConnections.remove(httpConnection);
/* 817 */           httpConnection.close();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   class ServerTimerTask1
/*     */     extends TimerTask
/*     */   {
/*     */     public void run() {
/* 827 */       LinkedList<HttpConnection> linkedList = new LinkedList();
/* 828 */       ServerImpl.this.time = System.currentTimeMillis();
/* 829 */       synchronized (ServerImpl.this.reqConnections) {
/* 830 */         if (ServerImpl.MAX_REQ_TIME != -1L) {
/* 831 */           for (HttpConnection httpConnection : ServerImpl.this.reqConnections) {
/* 832 */             if (httpConnection.creationTime + ServerImpl.TIMER_MILLIS + ServerImpl.MAX_REQ_TIME <= ServerImpl.this.time) {
/* 833 */               linkedList.add(httpConnection);
/*     */             }
/*     */           } 
/* 836 */           for (HttpConnection httpConnection : linkedList) {
/* 837 */             ServerImpl.this.logger.log(Level.FINE, "closing: no request: " + httpConnection);
/* 838 */             ServerImpl.this.reqConnections.remove(httpConnection);
/* 839 */             ServerImpl.this.allConnections.remove(httpConnection);
/* 840 */             httpConnection.close();
/*     */           } 
/*     */         } 
/*     */       } 
/* 844 */       linkedList = new LinkedList<>();
/* 845 */       synchronized (ServerImpl.this.rspConnections) {
/* 846 */         if (ServerImpl.MAX_RSP_TIME != -1L) {
/* 847 */           for (HttpConnection httpConnection : ServerImpl.this.rspConnections) {
/* 848 */             if (httpConnection.rspStartedTime + ServerImpl.TIMER_MILLIS + ServerImpl.MAX_RSP_TIME <= ServerImpl.this.time) {
/* 849 */               linkedList.add(httpConnection);
/*     */             }
/*     */           } 
/* 852 */           for (HttpConnection httpConnection : linkedList) {
/* 853 */             ServerImpl.this.logger.log(Level.FINE, "closing: no response: " + httpConnection);
/* 854 */             ServerImpl.this.rspConnections.remove(httpConnection);
/* 855 */             ServerImpl.this.allConnections.remove(httpConnection);
/* 856 */             httpConnection.close();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   void logStackTrace(String paramString) {
/* 864 */     this.logger.finest(paramString);
/* 865 */     StringBuilder stringBuilder = new StringBuilder();
/* 866 */     StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
/* 867 */     for (byte b = 0; b < arrayOfStackTraceElement.length; b++) {
/* 868 */       stringBuilder.append(arrayOfStackTraceElement[b].toString()).append("\n");
/*     */     }
/* 870 */     this.logger.finest(stringBuilder.toString());
/*     */   }
/*     */   
/*     */   static long getTimeMillis(long paramLong) {
/* 874 */     if (paramLong == -1L) {
/* 875 */       return -1L;
/*     */     }
/* 877 */     return paramLong * 1000L;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/ServerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */