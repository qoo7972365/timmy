/*     */ package sun.net.www.http;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.LinkedList;
/*     */ import sun.net.NetProperties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class KeepAliveStreamCleaner
/*     */   extends LinkedList<KeepAliveCleanerEntry>
/*     */   implements Runnable
/*     */ {
/*  51 */   protected static int MAX_DATA_REMAINING = 512;
/*     */ 
/*     */   
/*  54 */   protected static int MAX_CAPACITY = 10;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int TIMEOUT = 5000;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_RETRIES = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  68 */     int i = ((Integer)AccessController.<Integer>doPrivileged(new PrivilegedAction<Integer>() { public Integer run() { return NetProperties.getInteger("http.KeepAlive.remainingData", KeepAliveStreamCleaner.MAX_DATA_REMAINING); } })).intValue() * 1024;
/*  69 */     MAX_DATA_REMAINING = i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     int j = ((Integer)AccessController.<Integer>doPrivileged(new PrivilegedAction<Integer>() { public Integer run() { return NetProperties.getInteger("http.KeepAlive.queuedConnections", KeepAliveStreamCleaner.MAX_CAPACITY); } })).intValue();
/*  77 */     MAX_CAPACITY = j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean offer(KeepAliveCleanerEntry paramKeepAliveCleanerEntry) {
/*  84 */     if (size() >= MAX_CAPACITY) {
/*  85 */       return false;
/*     */     }
/*  87 */     return super.offer(paramKeepAliveCleanerEntry);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  93 */     KeepAliveCleanerEntry keepAliveCleanerEntry = null;
/*     */     
/*     */     do {
/*     */       try {
/*  97 */         synchronized (this) {
/*  98 */           long l1 = System.currentTimeMillis();
/*  99 */           long l2 = 5000L;
/* 100 */           while ((keepAliveCleanerEntry = poll()) == null) {
/* 101 */             wait(l2);
/*     */             
/* 103 */             long l3 = System.currentTimeMillis();
/* 104 */             long l4 = l3 - l1;
/* 105 */             if (l4 > l2) {
/*     */               
/* 107 */               keepAliveCleanerEntry = poll();
/*     */               break;
/*     */             } 
/* 110 */             l1 = l3;
/* 111 */             l2 -= l4;
/*     */           } 
/*     */         } 
/*     */         
/* 115 */         if (keepAliveCleanerEntry == null) {
/*     */           break;
/*     */         }
/* 118 */         KeepAliveStream keepAliveStream = keepAliveCleanerEntry.getKeepAliveStream();
/*     */         
/* 120 */         if (keepAliveStream != null) {
/* 121 */           synchronized (keepAliveStream) {
/* 122 */             HttpClient httpClient = keepAliveCleanerEntry.getHttpClient();
/*     */             try {
/* 124 */               if (httpClient != null && !httpClient.isInKeepAliveCache()) {
/* 125 */                 int i = httpClient.getReadTimeout();
/* 126 */                 httpClient.setReadTimeout(5000);
/* 127 */                 long l = keepAliveStream.remainingToRead();
/* 128 */                 if (l > 0L) {
/* 129 */                   long l1 = 0L;
/* 130 */                   byte b = 0;
/* 131 */                   while (l1 < l && b < 5) {
/* 132 */                     l -= l1;
/* 133 */                     l1 = keepAliveStream.skip(l);
/* 134 */                     if (l1 == 0L)
/* 135 */                       b++; 
/*     */                   } 
/* 137 */                   l -= l1;
/*     */                 } 
/* 139 */                 if (l == 0L)
/* 140 */                 { httpClient.setReadTimeout(i);
/* 141 */                   httpClient.finished(); }
/*     */                 else
/* 143 */                 { httpClient.closeServer(); } 
/*     */               } 
/* 145 */             } catch (IOException iOException) {
/* 146 */               httpClient.closeServer();
/*     */             } finally {
/* 148 */               keepAliveStream.setClosed();
/*     */             } 
/*     */           } 
/*     */         }
/* 152 */       } catch (InterruptedException interruptedException) {}
/* 153 */     } while (keepAliveCleanerEntry != null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/http/KeepAliveStreamCleaner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */