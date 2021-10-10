/*     */ package java.lang.ref;
/*     */ 
/*     */ import java.lang.ref.FinalReference;
/*     */ import java.lang.ref.Finalizer;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.misc.JavaLangAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.misc.VM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Finalizer
/*     */   extends FinalReference<Object>
/*     */ {
/*  38 */   private static ReferenceQueue<Object> queue = new ReferenceQueue();
/*  39 */   private static Finalizer unfinalized = null;
/*  40 */   private static final Object lock = new Object();
/*     */   
/*  42 */   private Finalizer next = null, prev = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasBeenFinalized() {
/*  47 */     return (this.next == this);
/*     */   }
/*     */   
/*     */   private void add() {
/*  51 */     synchronized (lock) {
/*  52 */       if (unfinalized != null) {
/*  53 */         this.next = unfinalized;
/*  54 */         unfinalized.prev = this;
/*     */       } 
/*  56 */       unfinalized = this;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void remove() {
/*  61 */     synchronized (lock) {
/*  62 */       if (unfinalized == this) {
/*  63 */         if (this.next != null) {
/*  64 */           unfinalized = this.next;
/*     */         } else {
/*  66 */           unfinalized = this.prev;
/*     */         } 
/*     */       }
/*  69 */       if (this.next != null) {
/*  70 */         this.next.prev = this.prev;
/*     */       }
/*  72 */       if (this.prev != null) {
/*  73 */         this.prev.next = this.next;
/*     */       }
/*  75 */       this.next = this;
/*  76 */       this.prev = this;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Finalizer(Object paramObject) {
/*  81 */     super(paramObject, queue);
/*  82 */     add();
/*     */   }
/*     */   
/*     */   static ReferenceQueue<Object> getQueue() {
/*  86 */     return queue;
/*     */   }
/*     */ 
/*     */   
/*     */   static void register(Object paramObject) {
/*  91 */     new Finalizer(paramObject);
/*     */   }
/*     */   
/*     */   private void runFinalizer(JavaLangAccess paramJavaLangAccess) {
/*  95 */     synchronized (this) {
/*  96 */       if (hasBeenFinalized())
/*  97 */         return;  remove();
/*     */     } 
/*     */     try {
/* 100 */       Object object = get();
/* 101 */       if (object != null && !(object instanceof Enum)) {
/* 102 */         paramJavaLangAccess.invokeFinalize(object);
/*     */ 
/*     */ 
/*     */         
/* 106 */         object = null;
/*     */       } 
/* 108 */     } catch (Throwable throwable) {}
/* 109 */     clear();
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
/*     */   private static void forkSecondaryFinalizer(final Runnable proc) {
/* 126 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 129 */             ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
/* 130 */             ThreadGroup threadGroup2 = threadGroup1;
/* 131 */             while (threadGroup2 != null) {
/* 132 */               threadGroup1 = threadGroup2; threadGroup2 = threadGroup1.getParent();
/* 133 */             }  Thread thread = new Thread(threadGroup1, proc, "Secondary finalizer");
/* 134 */             thread.start();
/*     */             try {
/* 136 */               thread.join();
/* 137 */             } catch (InterruptedException interruptedException) {
/* 138 */               Thread.currentThread().interrupt();
/*     */             } 
/* 140 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static void runFinalization() {
/* 146 */     if (!VM.isBooted()) {
/*     */       return;
/*     */     }
/*     */     
/* 150 */     forkSecondaryFinalizer(new Runnable() {
/*     */           private volatile boolean running;
/*     */           
/*     */           public void run() {
/* 154 */             if (this.running)
/*     */               return; 
/* 156 */             JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
/* 157 */             this.running = true;
/*     */             while (true) {
/* 159 */               Finalizer finalizer = (Finalizer)Finalizer.queue.poll();
/* 160 */               if (finalizer == null)
/* 161 */                 break;  finalizer.runFinalizer(javaLangAccess);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   static void runAllFinalizers() {
/* 169 */     if (!VM.isBooted()) {
/*     */       return;
/*     */     }
/*     */     
/* 173 */     forkSecondaryFinalizer(new Runnable() {
/*     */           private volatile boolean running;
/*     */           
/*     */           public void run() {
/* 177 */             if (this.running)
/*     */               return; 
/* 179 */             JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
/* 180 */             this.running = true;
/*     */             while (true) {
/*     */               Finalizer finalizer;
/* 183 */               synchronized (Finalizer.lock) {
/* 184 */                 finalizer = Finalizer.unfinalized;
/* 185 */                 if (finalizer == null)
/* 186 */                   break;  Finalizer.unfinalized = finalizer.next;
/*     */               } 
/* 188 */               finalizer.runFinalizer(javaLangAccess);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static class FinalizerThread extends Thread { FinalizerThread(ThreadGroup param1ThreadGroup) {
/* 195 */       super(param1ThreadGroup, "Finalizer");
/*     */     }
/*     */     private volatile boolean running;
/*     */     public void run() {
/* 199 */       if (this.running) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 204 */       while (!VM.isBooted()) {
/*     */         
/*     */         try {
/* 207 */           VM.awaitBooted();
/* 208 */         } catch (InterruptedException interruptedException) {}
/*     */       } 
/*     */ 
/*     */       
/* 212 */       JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
/* 213 */       this.running = true; while (true) {
/*     */         try {
/*     */           while (true)
/* 216 */           { Finalizer finalizer = (Finalizer)Finalizer.queue.remove();
/* 217 */             finalizer.runFinalizer(javaLangAccess); }  break;
/* 218 */         } catch (InterruptedException interruptedException) {}
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 226 */     ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
/* 227 */     ThreadGroup threadGroup2 = threadGroup1;
/* 228 */     while (threadGroup2 != null) {
/* 229 */       threadGroup1 = threadGroup2; threadGroup2 = threadGroup1.getParent();
/* 230 */     }  FinalizerThread finalizerThread = new FinalizerThread(threadGroup1);
/* 231 */     finalizerThread.setPriority(8);
/* 232 */     finalizerThread.setDaemon(true);
/* 233 */     finalizerThread.start();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/ref/Finalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */