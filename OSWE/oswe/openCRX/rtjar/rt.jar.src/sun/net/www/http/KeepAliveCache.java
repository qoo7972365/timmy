/*     */ package sun.net.www.http;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeepAliveCache
/*     */   extends HashMap<KeepAliveKey, ClientVector>
/*     */   implements Runnable
/*     */ {
/*     */   private static final long serialVersionUID = -2937172892064557949L;
/*     */   static final int MAX_CONNECTIONS = 5;
/*  52 */   static int result = -1;
/*     */   static int getMaxConnections() {
/*  54 */     if (result == -1) {
/*     */ 
/*     */ 
/*     */       
/*  58 */       result = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("http.maxConnections", 5))).intValue();
/*  59 */       if (result <= 0)
/*  60 */         result = 5; 
/*     */     } 
/*  62 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   static final int LIFETIME = 5000;
/*  67 */   private Thread keepAliveTimer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void put(URL paramURL, Object paramObject, HttpClient paramHttpClient) {
/*  80 */     boolean bool = (this.keepAliveTimer == null) ? true : false;
/*  81 */     if (!bool && 
/*  82 */       !this.keepAliveTimer.isAlive()) {
/*  83 */       bool = true;
/*     */     }
/*     */     
/*  86 */     if (bool) {
/*  87 */       clear();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       final KeepAliveCache cache = this;
/*  95 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */           {
/*     */             
/*     */             public Void run()
/*     */             {
/* 100 */               ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
/* 101 */               ThreadGroup threadGroup2 = null;
/* 102 */               while ((threadGroup2 = threadGroup1.getParent()) != null) {
/* 103 */                 threadGroup1 = threadGroup2;
/*     */               }
/*     */               
/* 106 */               KeepAliveCache.this.keepAliveTimer = new Thread(threadGroup1, cache, "Keep-Alive-Timer");
/* 107 */               KeepAliveCache.this.keepAliveTimer.setDaemon(true);
/* 108 */               KeepAliveCache.this.keepAliveTimer.setPriority(8);
/*     */ 
/*     */               
/* 111 */               KeepAliveCache.this.keepAliveTimer.setContextClassLoader(null);
/* 112 */               KeepAliveCache.this.keepAliveTimer.start();
/* 113 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 118 */     KeepAliveKey keepAliveKey = new KeepAliveKey(paramURL, paramObject);
/* 119 */     ClientVector clientVector = (ClientVector)get(keepAliveKey);
/*     */     
/* 121 */     if (clientVector == null) {
/* 122 */       int i = paramHttpClient.getKeepAliveTimeout();
/* 123 */       clientVector = new ClientVector((i > 0) ? (i * 1000) : 5000);
/*     */       
/* 125 */       clientVector.put(paramHttpClient);
/* 126 */       put((K)keepAliveKey, (V)clientVector);
/*     */     } else {
/* 128 */       clientVector.put(paramHttpClient);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void remove(HttpClient paramHttpClient, Object paramObject) {
/* 134 */     KeepAliveKey keepAliveKey = new KeepAliveKey(paramHttpClient.url, paramObject);
/* 135 */     ClientVector clientVector = (ClientVector)get(keepAliveKey);
/* 136 */     if (clientVector != null) {
/* 137 */       clientVector.remove(paramHttpClient);
/* 138 */       if (clientVector.empty()) {
/* 139 */         removeVector(keepAliveKey);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void removeVector(KeepAliveKey paramKeepAliveKey) {
/* 148 */     remove(paramKeepAliveKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized HttpClient get(URL paramURL, Object paramObject) {
/* 156 */     KeepAliveKey keepAliveKey = new KeepAliveKey(paramURL, paramObject);
/* 157 */     ClientVector clientVector = (ClientVector)get(keepAliveKey);
/* 158 */     if (clientVector == null) {
/* 159 */       return null;
/*     */     }
/* 161 */     return clientVector.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     do {
/*     */       try {
/* 172 */         Thread.sleep(5000L);
/* 173 */       } catch (InterruptedException interruptedException) {}
/* 174 */       synchronized (this) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 185 */         long l = System.currentTimeMillis();
/*     */         
/* 187 */         ArrayList<KeepAliveKey> arrayList = new ArrayList();
/*     */ 
/*     */         
/* 190 */         for (KeepAliveKey keepAliveKey : keySet()) {
/* 191 */           ClientVector clientVector = get(keepAliveKey);
/* 192 */           synchronized (clientVector) {
/*     */             byte b;
/*     */             
/* 195 */             for (b = 0; b < clientVector.size(); ) {
/* 196 */               KeepAliveEntry keepAliveEntry = clientVector.elementAt(b);
/* 197 */               if (l - keepAliveEntry.idleStartTime > clientVector.nap) {
/* 198 */                 HttpClient httpClient = keepAliveEntry.hc;
/* 199 */                 httpClient.closeServer();
/*     */                 
/*     */                 b++;
/*     */               } 
/*     */             } 
/* 204 */             clientVector.subList(0, b).clear();
/*     */             
/* 206 */             if (clientVector.size() == 0) {
/* 207 */               arrayList.add(keepAliveKey);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 212 */         for (KeepAliveKey keepAliveKey : arrayList) {
/* 213 */           removeVector(keepAliveKey);
/*     */         }
/*     */       } 
/* 216 */     } while (size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 226 */     throw new NotSerializableException();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 231 */     throw new NotSerializableException();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/http/KeepAliveCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */