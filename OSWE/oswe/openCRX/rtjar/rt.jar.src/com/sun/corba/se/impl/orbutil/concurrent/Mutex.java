/*     */ package com.sun.corba.se.impl.orbutil.concurrent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Mutex
/*     */   implements Sync
/*     */ {
/*     */   protected boolean inuse_ = false;
/*     */   
/*     */   public void acquire() throws InterruptedException {
/* 140 */     if (Thread.interrupted()) throw new InterruptedException(); 
/* 141 */     synchronized (this) { while (true) {
/*     */         try {
/* 143 */           if (this.inuse_) { wait(); continue; }
/* 144 */            this.inuse_ = true;
/*     */         }
/* 146 */         catch (InterruptedException interruptedException) {
/* 147 */           notify();
/* 148 */           throw interruptedException;
/*     */         } 
/*     */         break;
/*     */       }  }
/*     */   
/*     */   } public synchronized void release() {
/* 154 */     this.inuse_ = false;
/* 155 */     notify();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attempt(long paramLong) throws InterruptedException {
/* 160 */     if (Thread.interrupted()) throw new InterruptedException(); 
/* 161 */     synchronized (this) {
/* 162 */       if (!this.inuse_) {
/* 163 */         this.inuse_ = true;
/* 164 */         return true;
/*     */       } 
/* 166 */       if (paramLong <= 0L) {
/* 167 */         return false;
/*     */       }
/* 169 */       long l1 = paramLong;
/* 170 */       long l2 = System.currentTimeMillis();
/*     */       try {
/*     */         while (true) {
/* 173 */           wait(l1);
/* 174 */           if (!this.inuse_) {
/* 175 */             this.inuse_ = true;
/* 176 */             return true;
/*     */           } 
/*     */           
/* 179 */           l1 = paramLong - System.currentTimeMillis() - l2;
/* 180 */           if (l1 <= 0L) {
/* 181 */             return false;
/*     */           }
/*     */         }
/*     */       
/* 185 */       } catch (InterruptedException interruptedException) {
/* 186 */         notify();
/* 187 */         throw interruptedException;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/concurrent/Mutex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */