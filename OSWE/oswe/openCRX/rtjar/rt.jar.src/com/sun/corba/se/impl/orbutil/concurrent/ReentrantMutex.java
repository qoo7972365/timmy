/*     */ package com.sun.corba.se.impl.orbutil.concurrent;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReentrantMutex
/*     */   implements Sync
/*     */ {
/* 146 */   protected Thread holder_ = null;
/*     */ 
/*     */   
/* 149 */   protected int counter_ = 0;
/*     */   
/*     */   protected boolean debug = false;
/*     */ 
/*     */   
/*     */   public ReentrantMutex() {
/* 155 */     this(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReentrantMutex(boolean paramBoolean) {
/* 160 */     this.debug = paramBoolean;
/*     */   }
/*     */   
/*     */   public void acquire() throws InterruptedException {
/* 164 */     if (Thread.interrupted()) {
/* 165 */       throw new InterruptedException();
/*     */     }
/* 167 */     synchronized (this) {
/*     */       try {
/* 169 */         if (this.debug) {
/* 170 */           ORBUtility.dprintTrace(this, "acquire enter: holder_=" + 
/*     */               
/* 172 */               ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
/*     */         }
/*     */         
/* 175 */         Thread thread = Thread.currentThread();
/* 176 */         if (this.holder_ != thread) {
/*     */           try {
/* 178 */             while (this.counter_ > 0) {
/* 179 */               wait();
/*     */             }
/*     */             
/* 182 */             if (this.counter_ != 0) {
/* 183 */               throw new INTERNAL("counter not 0 when first acquiring mutex");
/*     */             }
/*     */             
/* 186 */             this.holder_ = thread;
/* 187 */           } catch (InterruptedException interruptedException) {
/* 188 */             notify();
/* 189 */             throw interruptedException;
/*     */           } 
/*     */         }
/*     */         
/* 193 */         this.counter_++;
/*     */       } finally {
/* 195 */         if (this.debug) {
/* 196 */           ORBUtility.dprintTrace(this, "acquire exit: holder_=" + 
/* 197 */               ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void acquireAll(int paramInt) throws InterruptedException {
/* 205 */     if (Thread.interrupted()) {
/* 206 */       throw new InterruptedException();
/*     */     }
/* 208 */     synchronized (this) {
/*     */       try {
/* 210 */         if (this.debug) {
/* 211 */           ORBUtility.dprintTrace(this, "acquireAll enter: count=" + paramInt + " holder_=" + 
/*     */               
/* 213 */               ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
/*     */         }
/* 215 */         Thread thread = Thread.currentThread();
/* 216 */         if (this.holder_ == thread) {
/* 217 */           throw new INTERNAL("Cannot acquireAll while holding the mutex");
/*     */         }
/*     */         
/*     */         try {
/* 221 */           while (this.counter_ > 0) {
/* 222 */             wait();
/*     */           }
/*     */           
/* 225 */           if (this.counter_ != 0) {
/* 226 */             throw new INTERNAL("counter not 0 when first acquiring mutex");
/*     */           }
/*     */           
/* 229 */           this.holder_ = thread;
/* 230 */         } catch (InterruptedException interruptedException) {
/* 231 */           notify();
/* 232 */           throw interruptedException;
/*     */         } 
/*     */ 
/*     */         
/* 236 */         this.counter_ = paramInt;
/*     */       } finally {
/* 238 */         if (this.debug) {
/* 239 */           ORBUtility.dprintTrace(this, "acquireAll exit: count=" + paramInt + " holder_=" + 
/* 240 */               ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void release() {
/*     */     try {
/* 249 */       if (this.debug) {
/* 250 */         ORBUtility.dprintTrace(this, "release enter:  holder_=" + 
/* 251 */             ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
/*     */       }
/*     */       
/* 254 */       Thread thread = Thread.currentThread();
/* 255 */       if (thread != this.holder_) {
/* 256 */         throw new INTERNAL("Attempt to release Mutex by thread not holding the Mutex");
/*     */       }
/*     */       
/* 259 */       this.counter_--;
/*     */       
/* 261 */       if (this.counter_ == 0) {
/* 262 */         this.holder_ = null;
/* 263 */         notify();
/*     */       } 
/*     */     } finally {
/* 266 */       if (this.debug) {
/* 267 */         ORBUtility.dprintTrace(this, "release exit:  holder_=" + 
/* 268 */             ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized int releaseAll() {
/*     */     try {
/* 276 */       if (this.debug) {
/* 277 */         ORBUtility.dprintTrace(this, "releaseAll enter:  holder_=" + 
/* 278 */             ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
/*     */       }
/*     */       
/* 281 */       Thread thread = Thread.currentThread();
/* 282 */       if (thread != this.holder_) {
/* 283 */         throw new INTERNAL("Attempt to releaseAll Mutex by thread not holding the Mutex");
/*     */       }
/*     */       
/* 286 */       int i = this.counter_;
/* 287 */       this.counter_ = 0;
/* 288 */       this.holder_ = null;
/* 289 */       notify();
/* 290 */       return i;
/*     */     } finally {
/* 292 */       if (this.debug) {
/* 293 */         ORBUtility.dprintTrace(this, "releaseAll exit:  holder_=" + 
/* 294 */             ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean attempt(long paramLong) throws InterruptedException {
/* 300 */     if (Thread.interrupted()) {
/* 301 */       throw new InterruptedException();
/*     */     }
/* 303 */     synchronized (this) {
/*     */       try {
/* 305 */         if (this.debug) {
/* 306 */           ORBUtility.dprintTrace(this, "attempt enter: msecs=" + paramLong + " holder_=" + 
/*     */               
/* 308 */               ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
/*     */         }
/*     */         
/* 311 */         Thread thread = Thread.currentThread();
/*     */         
/* 313 */         if (this.counter_ == 0) {
/* 314 */           this.holder_ = thread;
/* 315 */           this.counter_ = 1;
/* 316 */           return true;
/* 317 */         }  if (paramLong <= 0L) {
/* 318 */           return false;
/*     */         }
/* 320 */         long l1 = paramLong;
/* 321 */         long l2 = System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 343 */         if (this.debug)
/* 344 */           ORBUtility.dprintTrace(this, "attempt exit:  holder_=" + 
/* 345 */               ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/concurrent/ReentrantMutex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */