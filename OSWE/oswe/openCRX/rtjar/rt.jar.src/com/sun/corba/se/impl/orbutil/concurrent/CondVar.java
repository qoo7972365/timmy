/*     */ package com.sun.corba.se.impl.orbutil.concurrent;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CondVar
/*     */ {
/*     */   protected boolean debug_;
/*     */   protected final Sync mutex_;
/*     */   protected final ReentrantMutex remutex_;
/*     */   
/*     */   private int releaseMutex() {
/* 167 */     int i = 1;
/*     */     
/* 169 */     if (this.remutex_ != null) {
/* 170 */       i = this.remutex_.releaseAll();
/*     */     } else {
/* 172 */       this.mutex_.release();
/*     */     } 
/* 174 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private void acquireMutex(int paramInt) throws InterruptedException {
/* 179 */     if (this.remutex_ != null) {
/* 180 */       this.remutex_.acquireAll(paramInt);
/*     */     } else {
/* 182 */       this.mutex_.acquire();
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
/*     */   public CondVar(Sync paramSync, boolean paramBoolean) {
/* 202 */     this.debug_ = paramBoolean;
/* 203 */     this.mutex_ = paramSync;
/* 204 */     if (paramSync instanceof ReentrantMutex) {
/* 205 */       this.remutex_ = (ReentrantMutex)paramSync;
/*     */     } else {
/* 207 */       this.remutex_ = null;
/*     */     } 
/*     */   }
/*     */   public CondVar(Sync paramSync) {
/* 211 */     this(paramSync, false);
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
/*     */   public void await() throws InterruptedException {
/* 226 */     int i = 0;
/* 227 */     if (Thread.interrupted()) {
/* 228 */       throw new InterruptedException();
/*     */     }
/*     */     try {
/* 231 */       if (this.debug_) {
/* 232 */         ORBUtility.dprintTrace(this, "await enter");
/*     */       }
/* 234 */       synchronized (this) {
/* 235 */         i = releaseMutex();
/*     */         try {
/* 237 */           wait();
/* 238 */         } catch (InterruptedException interruptedException) {
/* 239 */           notify();
/* 240 */           throw interruptedException;
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 245 */       boolean bool = false;
/*     */       while (true) {
/*     */         try {
/* 248 */           acquireMutex(i);
/*     */           break;
/* 250 */         } catch (InterruptedException interruptedException) {
/* 251 */           bool = true;
/*     */         } 
/*     */       } 
/*     */       
/* 255 */       if (bool) {
/* 256 */         Thread.currentThread().interrupt();
/*     */       }
/*     */       
/* 259 */       if (this.debug_) {
/* 260 */         ORBUtility.dprintTrace(this, "await exit");
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
/*     */   
/*     */   public boolean timedwait(long paramLong) throws InterruptedException {
/* 283 */     if (Thread.interrupted()) {
/* 284 */       throw new InterruptedException();
/*     */     }
/* 286 */     boolean bool = false;
/* 287 */     int i = 0;
/*     */     
/*     */     try {
/* 290 */       if (this.debug_) {
/* 291 */         ORBUtility.dprintTrace(this, "timedwait enter");
/*     */       }
/* 293 */       synchronized (this) {
/* 294 */         i = releaseMutex();
/*     */         try {
/* 296 */           if (paramLong > 0L) {
/* 297 */             long l = System.currentTimeMillis();
/* 298 */             wait(paramLong);
/* 299 */             bool = (System.currentTimeMillis() - l <= paramLong) ? true : false;
/*     */           } 
/* 301 */         } catch (InterruptedException interruptedException) {
/* 302 */           notify();
/* 303 */           throw interruptedException;
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 308 */       boolean bool1 = false;
/*     */       while (true) {
/*     */         try {
/* 311 */           acquireMutex(i);
/*     */           break;
/* 313 */         } catch (InterruptedException interruptedException) {
/* 314 */           bool1 = true;
/*     */         } 
/*     */       } 
/*     */       
/* 318 */       if (bool1) {
/* 319 */         Thread.currentThread().interrupt();
/*     */       }
/*     */       
/* 322 */       if (this.debug_)
/* 323 */         ORBUtility.dprintTrace(this, "timedwait exit"); 
/*     */     } 
/* 325 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void signal() {
/* 334 */     notify();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void broadcast() {
/* 339 */     notifyAll();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/concurrent/CondVar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */