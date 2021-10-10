/*     */ package com.sun.xml.internal.ws.transport.http.server;
/*     */ 
/*     */ import com.sun.net.httpserver.HttpContext;
/*     */ import com.sun.net.httpserver.HttpServer;
/*     */ import com.sun.xml.internal.ws.server.ServerRtException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ServerMgr
/*     */ {
/*  49 */   private static final ServerMgr serverMgr = new ServerMgr();
/*     */   
/*  51 */   private static final Logger logger = Logger.getLogger("com.sun.xml.internal.ws.server.http");
/*     */   
/*  53 */   private final Map<InetSocketAddress, ServerState> servers = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ServerMgr getInstance() {
/*  62 */     return serverMgr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HttpContext createContext(String address) {
/*     */     try {
/*     */       ServerState state;
/*  74 */       URL url = new URL(address);
/*  75 */       int port = url.getPort();
/*  76 */       if (port == -1) {
/*  77 */         port = url.getDefaultPort();
/*     */       }
/*  79 */       InetSocketAddress inetAddress = new InetSocketAddress(url.getHost(), port);
/*     */       
/*  81 */       synchronized (this.servers) {
/*  82 */         state = this.servers.get(inetAddress);
/*  83 */         if (state == null) {
/*  84 */           int finalPortNum = port;
/*  85 */           for (ServerState s : this.servers.values()) {
/*  86 */             if (s.getServer()
/*  87 */               .getAddress()
/*  88 */               .getPort() == finalPortNum) {
/*  89 */               state = s;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*  94 */           if (!inetAddress.getAddress().isAnyLocalAddress() || state == null) {
/*     */             
/*  96 */             logger.fine("Creating new HTTP Server at " + inetAddress);
/*     */             
/*  98 */             HttpServer httpServer = HttpServer.create(inetAddress, 0);
/*  99 */             httpServer.setExecutor(Executors.newCachedThreadPool());
/* 100 */             String path = url.toURI().getPath();
/* 101 */             logger.fine("Creating HTTP Context at = " + path);
/* 102 */             HttpContext httpContext = httpServer.createContext(path);
/* 103 */             httpServer.start();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 108 */             inetAddress = httpServer.getAddress();
/*     */             
/* 110 */             logger.fine("HTTP server started = " + inetAddress);
/* 111 */             state = new ServerState(httpServer, path);
/* 112 */             this.servers.put(inetAddress, state);
/* 113 */             return httpContext;
/*     */           } 
/*     */         } 
/*     */       } 
/* 117 */       HttpServer server = state.getServer();
/*     */       
/* 119 */       if (state.getPaths().contains(url.getPath())) {
/* 120 */         String err = "Context with URL path " + url.getPath() + " already exists on the server " + server.getAddress();
/* 121 */         logger.fine(err);
/* 122 */         throw new IllegalArgumentException(err);
/*     */       } 
/*     */       
/* 125 */       logger.fine("Creating HTTP Context at = " + url.getPath());
/* 126 */       HttpContext context = server.createContext(url.getPath());
/* 127 */       state.oneMoreContext(url.getPath());
/* 128 */       return context;
/* 129 */     } catch (Exception e) {
/* 130 */       throw new ServerRtException("server.rt.err", new Object[] { e });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeContext(HttpContext context) {
/* 139 */     InetSocketAddress inetAddress = context.getServer().getAddress();
/* 140 */     synchronized (this.servers) {
/* 141 */       ServerState state = this.servers.get(inetAddress);
/* 142 */       int instances = state.noOfContexts();
/* 143 */       if (instances < 2) {
/* 144 */         ((ExecutorService)state.getServer().getExecutor()).shutdown();
/* 145 */         state.getServer().stop(0);
/* 146 */         this.servers.remove(inetAddress);
/*     */       } else {
/* 148 */         state.getServer().removeContext(context);
/* 149 */         state.oneLessContext(context.getPath());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class ServerState {
/*     */     private final HttpServer server;
/*     */     private int instances;
/* 157 */     private Set<String> paths = new HashSet<>();
/*     */     
/*     */     ServerState(HttpServer server, String path) {
/* 160 */       this.server = server;
/* 161 */       this.instances = 1;
/* 162 */       this.paths.add(path);
/*     */     }
/*     */     
/*     */     public HttpServer getServer() {
/* 166 */       return this.server;
/*     */     }
/*     */     
/*     */     public void oneMoreContext(String path) {
/* 170 */       this.instances++;
/* 171 */       this.paths.add(path);
/*     */     }
/*     */     
/*     */     public void oneLessContext(String path) {
/* 175 */       this.instances--;
/* 176 */       this.paths.remove(path);
/*     */     }
/*     */     
/*     */     public int noOfContexts() {
/* 180 */       return this.instances;
/*     */     }
/*     */     
/*     */     public Set<String> getPaths() {
/* 184 */       return this.paths;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/server/ServerMgr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */