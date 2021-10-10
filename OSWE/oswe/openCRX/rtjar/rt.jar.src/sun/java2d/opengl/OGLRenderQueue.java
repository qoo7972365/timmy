/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import sun.java2d.pipe.RenderBuffer;
/*     */ import sun.java2d.pipe.RenderQueue;
/*     */ import sun.misc.ThreadGroupUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OGLRenderQueue
/*     */   extends RenderQueue
/*     */ {
/*     */   private static OGLRenderQueue theInstance;
/*  51 */   private final QueueFlusher flusher = AccessController.<QueueFlusher>doPrivileged(() -> new QueueFlusher(ThreadGroupUtils.getRootThreadGroup()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized OGLRenderQueue getInstance() {
/*  62 */     if (theInstance == null) {
/*  63 */       theInstance = new OGLRenderQueue();
/*     */     }
/*  65 */     return theInstance;
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
/*     */   public static void sync() {
/*  78 */     if (theInstance != null) {
/*  79 */       theInstance.lock();
/*     */       try {
/*  81 */         theInstance.ensureCapacity(4);
/*  82 */         theInstance.getBuffer().putInt(76);
/*  83 */         theInstance.flushNow();
/*     */       } finally {
/*  85 */         theInstance.unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void disposeGraphicsConfig(long paramLong) {
/*  95 */     OGLRenderQueue oGLRenderQueue = getInstance();
/*  96 */     oGLRenderQueue.lock();
/*     */ 
/*     */     
/*     */     try {
/* 100 */       OGLContext.setScratchSurface(paramLong);
/*     */       
/* 102 */       RenderBuffer renderBuffer = oGLRenderQueue.getBuffer();
/* 103 */       oGLRenderQueue.ensureCapacityAndAlignment(12, 4);
/* 104 */       renderBuffer.putInt(74);
/* 105 */       renderBuffer.putLong(paramLong);
/*     */ 
/*     */       
/* 108 */       oGLRenderQueue.flushNow();
/*     */     } finally {
/* 110 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isQueueFlusherThread() {
/* 118 */     return (Thread.currentThread() == (getInstance()).flusher);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flushNow() {
/*     */     try {
/* 124 */       this.flusher.flushNow();
/* 125 */     } catch (Exception exception) {
/* 126 */       System.err.println("exception in flushNow:");
/* 127 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void flushAndInvokeNow(Runnable paramRunnable) {
/*     */     try {
/* 134 */       this.flusher.flushAndInvokeNow(paramRunnable);
/* 135 */     } catch (Exception exception) {
/* 136 */       System.err.println("exception in flushAndInvokeNow:");
/* 137 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void flushBuffer() {
/* 145 */     int i = this.buf.position();
/* 146 */     if (i > 0)
/*     */     {
/* 148 */       flushBuffer(this.buf.getAddress(), i);
/*     */     }
/*     */     
/* 151 */     this.buf.clear();
/*     */     
/* 153 */     this.refSet.clear();
/*     */   }
/*     */   
/*     */   private native void flushBuffer(long paramLong, int paramInt);
/*     */   
/*     */   private class QueueFlusher extends Thread {
/*     */     private boolean needsFlush;
/*     */     
/*     */     public QueueFlusher(ThreadGroup param1ThreadGroup) {
/* 162 */       super(param1ThreadGroup, "Java2D Queue Flusher");
/* 163 */       setDaemon(true);
/* 164 */       setPriority(10);
/* 165 */       start();
/*     */     }
/*     */     private Runnable task; private Error error;
/*     */     
/*     */     public synchronized void flushNow() {
/* 170 */       this.needsFlush = true;
/* 171 */       notify();
/*     */ 
/*     */       
/* 174 */       while (this.needsFlush) {
/*     */         try {
/* 176 */           wait();
/* 177 */         } catch (InterruptedException interruptedException) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 182 */       if (this.error != null) {
/* 183 */         throw this.error;
/*     */       }
/*     */     }
/*     */     
/*     */     public synchronized void flushAndInvokeNow(Runnable param1Runnable) {
/* 188 */       this.task = param1Runnable;
/* 189 */       flushNow();
/*     */     }
/*     */     
/*     */     public synchronized void run() {
/* 193 */       boolean bool = false;
/*     */       while (true) {
/* 195 */         while (!this.needsFlush) {
/*     */           try {
/* 197 */             bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 203 */             wait(100L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 212 */             if (!this.needsFlush && (bool = OGLRenderQueue.this.tryLock())) {
/* 213 */               if (OGLRenderQueue.this.buf.position() > 0) {
/* 214 */                 this.needsFlush = true; continue;
/*     */               } 
/* 216 */               OGLRenderQueue.this.unlock();
/*     */             }
/*     */           
/* 219 */           } catch (InterruptedException interruptedException) {}
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 224 */           this.error = null;
/*     */           
/* 226 */           OGLRenderQueue.this.flushBuffer();
/*     */           
/* 228 */           if (this.task != null) {
/* 229 */             this.task.run();
/*     */           }
/* 231 */         } catch (Error error) {
/* 232 */           this.error = error;
/* 233 */         } catch (Exception exception) {
/* 234 */           System.err.println("exception in QueueFlusher:");
/* 235 */           exception.printStackTrace();
/*     */         } finally {
/* 237 */           if (bool) {
/* 238 */             OGLRenderQueue.this.unlock();
/*     */           }
/* 240 */           this.task = null;
/*     */           
/* 242 */           this.needsFlush = false;
/* 243 */           notify();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/opengl/OGLRenderQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */