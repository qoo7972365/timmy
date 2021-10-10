/*     */ package sun.misc;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GC
/*     */ {
/*     */   private static final long NO_TARGET = 9223372036854775807L;
/*  52 */   private static long latencyTarget = Long.MAX_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private static Thread daemon = null;
/*     */   
/*     */   private static class LatencyLock
/*     */   {
/*     */     private LatencyLock() {}
/*     */   }
/*     */   
/*  64 */   private static Object lock = new LatencyLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Daemon
/*     */     extends Thread
/*     */   {
/*     */     public void run() {
/*     */       while (true) {
/*  91 */         synchronized (GC.lock) {
/*     */           
/*  93 */           long l1 = GC.latencyTarget;
/*  94 */           if (l1 == Long.MAX_VALUE) {
/*     */             
/*  96 */             GC.daemon = null;
/*     */             
/*     */             return;
/*     */           } 
/* 100 */           long l2 = GC.maxObjectInspectionAge();
/* 101 */           if (l2 >= l1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 109 */             System.gc();
/* 110 */             l2 = 0L;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 117 */             GC.lock.wait(l1 - l2);
/* 118 */           } catch (InterruptedException interruptedException) {}
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Daemon(ThreadGroup param1ThreadGroup) {
/* 126 */       super(param1ThreadGroup, "GC Daemon");
/*     */     }
/*     */ 
/*     */     
/*     */     public static void create() {
/* 131 */       PrivilegedAction<Void> privilegedAction = new PrivilegedAction<Void>() {
/*     */           public Void run() {
/* 133 */             ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
/* 134 */             ThreadGroup threadGroup2 = threadGroup1;
/* 135 */             while (threadGroup2 != null) {
/* 136 */               threadGroup1 = threadGroup2; threadGroup2 = threadGroup1.getParent();
/* 137 */             }  GC.Daemon daemon = new GC.Daemon(threadGroup1);
/* 138 */             daemon.setDaemon(true);
/* 139 */             daemon.setPriority(2);
/* 140 */             daemon.start();
/* 141 */             GC.daemon = daemon;
/* 142 */             return null; }
/*     */         };
/* 144 */       AccessController.doPrivileged(privilegedAction);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setLatencyTarget(long paramLong) {
/* 154 */     latencyTarget = paramLong;
/* 155 */     if (daemon == null) {
/*     */       
/* 157 */       Daemon.create();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 162 */       lock.notify();
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
/*     */   public static class LatencyRequest
/*     */     implements Comparable<LatencyRequest>
/*     */   {
/* 177 */     private static long counter = 0L;
/*     */ 
/*     */     
/* 180 */     private static SortedSet<LatencyRequest> requests = null;
/*     */     
/*     */     private long latency;
/*     */     private long id;
/*     */     
/*     */     private static void adjustLatencyIfNeeded() {
/* 186 */       if (requests == null || requests.isEmpty()) {
/* 187 */         if (GC.latencyTarget != Long.MAX_VALUE) {
/* 188 */           GC.setLatencyTarget(Long.MAX_VALUE);
/*     */         }
/*     */       } else {
/* 191 */         LatencyRequest latencyRequest = requests.first();
/* 192 */         if (latencyRequest.latency != GC.latencyTarget) {
/* 193 */           GC.setLatencyTarget(latencyRequest.latency);
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
/*     */     private LatencyRequest(long param1Long) {
/* 207 */       if (param1Long <= 0L) {
/* 208 */         throw new IllegalArgumentException("Non-positive latency: " + param1Long);
/*     */       }
/*     */       
/* 211 */       this.latency = param1Long;
/* 212 */       synchronized (GC.lock) {
/* 213 */         this.id = ++counter;
/* 214 */         if (requests == null) {
/* 215 */           requests = new TreeSet<>();
/*     */         }
/* 217 */         requests.add(this);
/* 218 */         adjustLatencyIfNeeded();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void cancel() {
/* 229 */       synchronized (GC.lock) {
/* 230 */         if (this.latency == Long.MAX_VALUE) {
/* 231 */           throw new IllegalStateException("Request already cancelled");
/*     */         }
/*     */         
/* 234 */         if (!requests.remove(this)) {
/* 235 */           throw new InternalError("Latency request " + this + " not found");
/*     */         }
/*     */         
/* 238 */         if (requests.isEmpty()) requests = null; 
/* 239 */         this.latency = Long.MAX_VALUE;
/* 240 */         adjustLatencyIfNeeded();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int compareTo(LatencyRequest param1LatencyRequest) {
/* 245 */       long l = this.latency - param1LatencyRequest.latency;
/* 246 */       if (l == 0L) l = this.id - param1LatencyRequest.id; 
/* 247 */       return (l < 0L) ? -1 : ((l > 0L) ? 1 : 0);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 251 */       return LatencyRequest.class.getName() + "[" + this.latency + "," + this.id + "]";
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
/*     */   public static LatencyRequest requestLatency(long paramLong) {
/* 271 */     return new LatencyRequest(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long currentLatencyTarget() {
/* 280 */     long l = latencyTarget;
/* 281 */     return (l == Long.MAX_VALUE) ? 0L : l;
/*     */   }
/*     */   
/*     */   public static native long maxObjectInspectionAge();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/GC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */