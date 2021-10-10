/*     */ package sun.net.httpserver;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ServerConfig
/*     */ {
/*     */   private static final int DEFAULT_CLOCK_TICK = 10000;
/*     */   private static final long DEFAULT_IDLE_INTERVAL = 30L;
/*     */   private static final int DEFAULT_MAX_IDLE_CONNECTIONS = 200;
/*     */   private static final long DEFAULT_MAX_REQ_TIME = -1L;
/*     */   private static final long DEFAULT_MAX_RSP_TIME = -1L;
/*     */   private static final long DEFAULT_TIMER_MILLIS = 1000L;
/*     */   private static final int DEFAULT_MAX_REQ_HEADERS = 200;
/*     */   private static final long DEFAULT_DRAIN_AMOUNT = 65536L;
/*     */   private static int clockTick;
/*     */   private static long idleInterval;
/*     */   private static long drainAmount;
/*     */   private static int maxIdleConnections;
/*     */   private static int maxReqHeaders;
/*     */   private static long maxReqTime;
/*     */   private static long maxRspTime;
/*     */   private static long timerMillis;
/*     */   private static boolean debug;
/*     */   private static boolean noDelay;
/*     */   
/*     */   static {
/*  67 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run()
/*     */           {
/*  71 */             ServerConfig.idleInterval = Long.getLong("sun.net.httpserver.idleInterval", 30L).longValue() * 1000L;
/*     */ 
/*     */             
/*  74 */             ServerConfig.clockTick = Integer.getInteger("sun.net.httpserver.clockTick", 10000).intValue();
/*     */ 
/*     */             
/*  77 */             ServerConfig.maxIdleConnections = Integer.getInteger("sun.net.httpserver.maxIdleConnections", 200).intValue();
/*     */ 
/*     */ 
/*     */             
/*  81 */             ServerConfig.drainAmount = Long.getLong("sun.net.httpserver.drainAmount", 65536L).longValue();
/*     */ 
/*     */             
/*  84 */             ServerConfig.maxReqHeaders = Integer.getInteger("sun.net.httpserver.maxReqHeaders", 200).intValue();
/*     */ 
/*     */ 
/*     */             
/*  88 */             ServerConfig.maxReqTime = Long.getLong("sun.net.httpserver.maxReqTime", -1L).longValue();
/*     */ 
/*     */             
/*  91 */             ServerConfig.maxRspTime = Long.getLong("sun.net.httpserver.maxRspTime", -1L).longValue();
/*     */ 
/*     */             
/*  94 */             ServerConfig.timerMillis = Long.getLong("sun.net.httpserver.timerMillis", 1000L).longValue();
/*     */ 
/*     */             
/*  97 */             ServerConfig.debug = Boolean.getBoolean("sun.net.httpserver.debug");
/*     */             
/*  99 */             ServerConfig.noDelay = Boolean.getBoolean("sun.net.httpserver.nodelay");
/*     */             
/* 101 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void checkLegacyProperties(final Logger logger) {
/* 112 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 115 */             if (System.getProperty("sun.net.httpserver.readTimeout") != null)
/*     */             {
/*     */               
/* 118 */               logger.warning("sun.net.httpserver.readTimeout property is no longer used. Use sun.net.httpserver.maxReqTime instead.");
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 123 */             if (System.getProperty("sun.net.httpserver.writeTimeout") != null)
/*     */             {
/*     */               
/* 126 */               logger.warning("sun.net.httpserver.writeTimeout property is no longer used. Use sun.net.httpserver.maxRspTime instead.");
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 131 */             if (System.getProperty("sun.net.httpserver.selCacheTimeout") != null)
/*     */             {
/*     */               
/* 134 */               logger.warning("sun.net.httpserver.selCacheTimeout property is no longer used.");
/*     */             }
/*     */ 
/*     */             
/* 138 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean debugEnabled() {
/* 145 */     return debug;
/*     */   }
/*     */   
/*     */   static long getIdleInterval() {
/* 149 */     return idleInterval;
/*     */   }
/*     */   
/*     */   static int getClockTick() {
/* 153 */     return clockTick;
/*     */   }
/*     */   
/*     */   static int getMaxIdleConnections() {
/* 157 */     return maxIdleConnections;
/*     */   }
/*     */   
/*     */   static long getDrainAmount() {
/* 161 */     return drainAmount;
/*     */   }
/*     */   
/*     */   static int getMaxReqHeaders() {
/* 165 */     return maxReqHeaders;
/*     */   }
/*     */   
/*     */   static long getMaxReqTime() {
/* 169 */     return maxReqTime;
/*     */   }
/*     */   
/*     */   static long getMaxRspTime() {
/* 173 */     return maxRspTime;
/*     */   }
/*     */   
/*     */   static long getTimerMillis() {
/* 177 */     return timerMillis;
/*     */   }
/*     */   
/*     */   static boolean noDelay() {
/* 181 */     return noDelay;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/ServerConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */