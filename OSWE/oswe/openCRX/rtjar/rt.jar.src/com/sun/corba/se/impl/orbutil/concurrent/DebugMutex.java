/*     */ package com.sun.corba.se.impl.orbutil.concurrent;
/*     */ 
/*     */ import org.omg.CORBA.INTERNAL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DebugMutex
/*     */   implements Sync
/*     */ {
/*     */   protected boolean inuse_ = false;
/* 143 */   protected Thread holder_ = null;
/*     */   
/*     */   public void acquire() throws InterruptedException {
/* 146 */     if (Thread.interrupted()) throw new InterruptedException(); 
/* 147 */     synchronized (this) {
/* 148 */       Thread thread = Thread.currentThread();
/* 149 */       if (this.holder_ == thread) {
/* 150 */         throw new INTERNAL("Attempt to acquire Mutex by thread holding the Mutex");
/*     */       }
/*     */       
/*     */       try {
/* 154 */         for (; this.inuse_; wait());
/* 155 */         this.inuse_ = true;
/* 156 */         this.holder_ = Thread.currentThread();
/*     */       }
/* 158 */       catch (InterruptedException interruptedException) {
/* 159 */         notify();
/* 160 */         throw interruptedException;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void release() {
/* 166 */     Thread thread = Thread.currentThread();
/* 167 */     if (thread != this.holder_) {
/* 168 */       throw new INTERNAL("Attempt to release Mutex by thread not holding the Mutex");
/*     */     }
/* 170 */     this.holder_ = null;
/* 171 */     this.inuse_ = false;
/* 172 */     notify();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attempt(long paramLong) throws InterruptedException {
/* 177 */     if (Thread.interrupted()) throw new InterruptedException(); 
/* 178 */     synchronized (this) {
/* 179 */       Thread thread = Thread.currentThread();
/*     */       
/* 181 */       if (!this.inuse_) {
/* 182 */         this.inuse_ = true;
/* 183 */         this.holder_ = thread;
/* 184 */         return true;
/* 185 */       }  if (paramLong <= 0L) {
/* 186 */         return false;
/*     */       }
/* 188 */       long l1 = paramLong;
/* 189 */       long l2 = System.currentTimeMillis();
/*     */       try {
/*     */         while (true) {
/* 192 */           wait(l1);
/* 193 */           if (!this.inuse_) {
/* 194 */             this.inuse_ = true;
/* 195 */             this.holder_ = thread;
/* 196 */             return true;
/*     */           } 
/*     */           
/* 199 */           l1 = paramLong - System.currentTimeMillis() - l2;
/* 200 */           if (l1 <= 0L) {
/* 201 */             return false;
/*     */           }
/*     */         }
/*     */       
/* 205 */       } catch (InterruptedException interruptedException) {
/* 206 */         notify();
/* 207 */         throw interruptedException;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/concurrent/DebugMutex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */