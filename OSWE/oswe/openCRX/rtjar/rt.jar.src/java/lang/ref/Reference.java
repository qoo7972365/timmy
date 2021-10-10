/*     */ package java.lang.ref;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import sun.misc.Cleaner;
/*     */ import sun.misc.JavaLangRefAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Reference<T>
/*     */ {
/*     */   private T referent;
/*     */   volatile ReferenceQueue<? super T> queue;
/*     */   volatile Reference next;
/*     */   private transient Reference<T> discovered;
/*     */   
/*     */   private static class Lock
/*     */   {
/*     */     private Lock() {}
/*     */   }
/*     */   
/* 117 */   private static Lock lock = new Lock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   private static Reference<Object> pending = null;
/*     */ 
/*     */   
/*     */   private static class ReferenceHandler
/*     */     extends Thread
/*     */   {
/*     */     private static void ensureClassInitialized(Class<?> param1Class) {
/*     */       try {
/* 133 */         Class.forName(param1Class.getName(), true, param1Class.getClassLoader());
/* 134 */       } catch (ClassNotFoundException classNotFoundException) {
/* 135 */         throw (Error)(new NoClassDefFoundError(classNotFoundException.getMessage())).initCause(classNotFoundException);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 143 */       ensureClassInitialized(InterruptedException.class);
/* 144 */       ensureClassInitialized(Cleaner.class);
/*     */     }
/*     */     
/*     */     ReferenceHandler(ThreadGroup param1ThreadGroup, String param1String) {
/* 148 */       super(param1ThreadGroup, param1String);
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       while (true) {
/* 153 */         Reference.tryHandlePending(true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean tryHandlePending(boolean paramBoolean) {
/*     */     Reference<Object> reference;
/*     */     Cleaner cleaner;
/*     */     try {
/* 178 */       synchronized (lock) {
/* 179 */         if (pending != null) {
/* 180 */           reference = pending;
/*     */ 
/*     */           
/* 183 */           cleaner = (reference instanceof Cleaner) ? (Cleaner)reference : null;
/*     */           
/* 185 */           pending = reference.discovered;
/* 186 */           reference.discovered = null;
/*     */         }
/*     */         else {
/*     */           
/* 190 */           if (paramBoolean) {
/* 191 */             lock.wait();
/*     */           }
/*     */           
/* 194 */           return paramBoolean;
/*     */         } 
/*     */       } 
/* 197 */     } catch (OutOfMemoryError outOfMemoryError) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       Thread.yield();
/*     */       
/* 204 */       return true;
/* 205 */     } catch (InterruptedException interruptedException) {
/*     */       
/* 207 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 211 */     if (cleaner != null) {
/* 212 */       cleaner.clean();
/* 213 */       return true;
/*     */     } 
/*     */     
/* 216 */     ReferenceQueue<? super Object> referenceQueue = reference.queue;
/* 217 */     if (referenceQueue != ReferenceQueue.NULL) referenceQueue.enqueue(reference); 
/* 218 */     return true;
/*     */   }
/*     */   
/*     */   static {
/* 222 */     ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
/* 223 */     ThreadGroup threadGroup2 = threadGroup1;
/* 224 */     while (threadGroup2 != null) {
/* 225 */       threadGroup1 = threadGroup2; threadGroup2 = threadGroup1.getParent();
/* 226 */     }  ReferenceHandler referenceHandler = new ReferenceHandler(threadGroup1, "Reference Handler");
/*     */ 
/*     */ 
/*     */     
/* 230 */     referenceHandler.setPriority(10);
/* 231 */     referenceHandler.setDaemon(true);
/* 232 */     referenceHandler.start();
/*     */ 
/*     */     
/* 235 */     SharedSecrets.setJavaLangRefAccess(new JavaLangRefAccess()
/*     */         {
/*     */           public boolean tryHandlePendingReference() {
/* 238 */             return Reference.tryHandlePending(false);
/*     */           }
/*     */         });
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
/*     */   public T get() {
/* 254 */     return this.referent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 265 */     this.referent = null;
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
/*     */   public boolean isEnqueued() {
/* 281 */     return (this.queue == ReferenceQueue.ENQUEUED);
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
/*     */   public boolean enqueue() {
/* 296 */     return this.queue.enqueue(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Reference(T paramT) {
/* 303 */     this(paramT, null);
/*     */   }
/*     */   
/*     */   Reference(T paramT, ReferenceQueue<? super T> paramReferenceQueue) {
/* 307 */     this.referent = paramT;
/* 308 */     this.queue = (paramReferenceQueue == null) ? (ReferenceQueue)ReferenceQueue.NULL : paramReferenceQueue;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/ref/Reference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */