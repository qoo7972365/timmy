/*     */ package sun.misc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TimerThread
/*     */   extends Thread
/*     */ {
/*     */   public static boolean debug = false;
/*     */   static TimerThread timerThread;
/*     */   static boolean notified = false;
/*     */   
/*     */   protected TimerThread() {
/* 366 */     super("TimerThread");
/* 367 */     timerThread = this;
/* 368 */     start();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void run() {
/*     */     while (true) {
/* 375 */       while (timerQueue == null) {
/*     */         try {
/* 377 */           wait();
/* 378 */         } catch (InterruptedException interruptedException) {}
/*     */       } 
/*     */ 
/*     */       
/* 382 */       notified = false;
/* 383 */       long l = timerQueue.sleepUntil - System.currentTimeMillis();
/* 384 */       if (l > 0L) {
/*     */         try {
/* 386 */           wait(l);
/* 387 */         } catch (InterruptedException interruptedException) {}
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 392 */       if (!notified) {
/* 393 */         Timer timer = timerQueue;
/* 394 */         timerQueue = timerQueue.next;
/* 395 */         TimerTickThread timerTickThread = TimerTickThread.call(timer, timer.sleepUntil);
/*     */         
/* 397 */         if (debug) {
/* 398 */           long l1 = System.currentTimeMillis() - timer.sleepUntil;
/* 399 */           System.out.println("tick(" + timerTickThread.getName() + "," + timer.interval + "," + l1 + ")");
/*     */           
/* 401 */           if (l1 > 250L) {
/* 402 */             System.out.println("*** BIG DELAY ***");
/*     */           }
/*     */         } 
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
/* 416 */   static Timer timerQueue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void enqueue(Timer paramTimer) {
/* 427 */     Timer timer1 = null;
/* 428 */     Timer timer2 = timerQueue;
/*     */     
/* 430 */     if (timer2 == null || paramTimer.sleepUntil <= timer2.sleepUntil) {
/*     */       
/* 432 */       paramTimer.next = timerQueue;
/* 433 */       timerQueue = paramTimer;
/* 434 */       notified = true;
/* 435 */       timerThread.notify();
/*     */     } else {
/*     */       do {
/* 438 */         timer1 = timer2;
/* 439 */         timer2 = timer2.next;
/* 440 */       } while (timer2 != null && paramTimer.sleepUntil > timer2.sleepUntil);
/*     */       
/* 442 */       paramTimer.next = timer2;
/* 443 */       timer1.next = paramTimer;
/*     */     } 
/* 445 */     if (debug) {
/* 446 */       long l = System.currentTimeMillis();
/*     */       
/* 448 */       System.out.print(Thread.currentThread().getName() + ": enqueue " + paramTimer.interval + ": ");
/*     */       
/* 450 */       timer2 = timerQueue;
/* 451 */       while (timer2 != null) {
/* 452 */         long l1 = timer2.sleepUntil - l;
/* 453 */         System.out.print(timer2.interval + "(" + l1 + ") ");
/* 454 */         timer2 = timer2.next;
/*     */       } 
/* 456 */       System.out.println();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean dequeue(Timer paramTimer) {
/* 466 */     Timer timer1 = null;
/* 467 */     Timer timer2 = timerQueue;
/*     */     
/* 469 */     while (timer2 != null && timer2 != paramTimer) {
/* 470 */       timer1 = timer2;
/* 471 */       timer2 = timer2.next;
/*     */     } 
/* 473 */     if (timer2 == null) {
/* 474 */       if (debug) {
/* 475 */         System.out.println(Thread.currentThread().getName() + ": dequeue " + paramTimer.interval + ": no-op");
/*     */       }
/*     */       
/* 478 */       return false;
/* 479 */     }  if (timer1 == null) {
/* 480 */       timerQueue = paramTimer.next;
/* 481 */       notified = true;
/* 482 */       timerThread.notify();
/*     */     } else {
/* 484 */       timer1.next = paramTimer.next;
/*     */     } 
/* 486 */     paramTimer.next = null;
/* 487 */     if (debug) {
/* 488 */       long l = System.currentTimeMillis();
/*     */       
/* 490 */       System.out.print(Thread.currentThread().getName() + ": dequeue " + paramTimer.interval + ": ");
/*     */       
/* 492 */       timer2 = timerQueue;
/* 493 */       while (timer2 != null) {
/* 494 */         long l1 = timer2.sleepUntil - l;
/* 495 */         System.out.print(timer2.interval + "(" + l1 + ") ");
/* 496 */         timer2 = timer2.next;
/*     */       } 
/* 498 */       System.out.println();
/*     */     } 
/* 500 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void requeue(Timer paramTimer) {
/* 511 */     if (!paramTimer.stopped) {
/* 512 */       long l = System.currentTimeMillis();
/* 513 */       if (paramTimer.regular) {
/* 514 */         paramTimer.sleepUntil += paramTimer.interval;
/*     */       } else {
/* 516 */         paramTimer.sleepUntil = l + paramTimer.interval;
/*     */       } 
/* 518 */       enqueue(paramTimer);
/* 519 */     } else if (debug) {
/* 520 */       System.out.println(Thread.currentThread().getName() + ": requeue " + paramTimer.interval + ": no-op");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/TimerThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */