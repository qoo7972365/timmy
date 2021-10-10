/*     */ package java.util.concurrent;
/*     */ 
/*     */ import sun.misc.Contended;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Exchanger<V>
/*     */ {
/*     */   private static final int ASHIFT = 7;
/*     */   private static final int MMASK = 255;
/*     */   private static final int SEQ = 256;
/* 278 */   private static final int NCPU = Runtime.getRuntime().availableProcessors();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 285 */   static final int FULL = (NCPU >= 510) ? 255 : (NCPU >>> 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int SPINS = 1024;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 299 */   private static final Object NULL_ITEM = new Object();
/*     */   private final Participant participant;
/*     */   private volatile Node[] arena;
/*     */   private volatile Node slot;
/*     */   private volatile int bound;
/*     */   private static final Unsafe U;
/*     */   private static final long BOUND;
/* 306 */   private static final Object TIMED_OUT = new Object();
/*     */   private static final long SLOT;
/*     */   private static final long MATCH;
/*     */   private static final long BLOCKER;
/*     */   private static final int ABASE;
/*     */   
/*     */   @Contended
/*     */   static final class Node {
/*     */     int index;
/*     */     int bound;
/*     */     int collides;
/*     */     int hash;
/*     */     Object item;
/*     */     volatile Object match;
/*     */     volatile Thread parked;
/*     */   }
/*     */   
/*     */   static final class Participant extends ThreadLocal<Node> {
/*     */     public Exchanger.Node initialValue() {
/* 325 */       return new Exchanger.Node();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object arenaExchange(Object paramObject, boolean paramBoolean, long paramLong) {
/* 362 */     Node[] arrayOfNode = this.arena;
/* 363 */     Node node = this.participant.get();
/* 364 */     int i = node.index; while (true) {
/*     */       long l;
/* 366 */       Node node1 = (Node)U.getObjectVolatile(arrayOfNode, l = ((i << 7) + ABASE));
/* 367 */       if (node1 != null && U.compareAndSwapObject(arrayOfNode, l, node1, null)) {
/* 368 */         Object object = node1.item;
/* 369 */         node1.match = paramObject;
/* 370 */         Thread thread = node1.parked;
/* 371 */         if (thread != null)
/* 372 */           U.unpark(thread); 
/* 373 */         return object;
/*     */       }  int j, k;
/* 375 */       if (i <= (k = (j = this.bound) & 0xFF) && node1 == null) {
/* 376 */         node.item = paramObject;
/* 377 */         if (U.compareAndSwapObject(arrayOfNode, l, null, node)) {
/* 378 */           long l1 = (paramBoolean && k == 0) ? (System.nanoTime() + paramLong) : 0L;
/* 379 */           Thread thread = Thread.currentThread();
/* 380 */           int m = node.hash; char c = 'Ѐ'; while (true) {
/* 381 */             Object object = node.match;
/* 382 */             if (object != null) {
/* 383 */               U.putOrderedObject(node, MATCH, null);
/* 384 */               node.item = null;
/* 385 */               node.hash = m;
/* 386 */               return object;
/*     */             } 
/* 388 */             if (c > '\000') {
/* 389 */               m ^= m << 1; m ^= m >>> 3; m ^= m << 10;
/* 390 */               if (m == 0) {
/* 391 */                 m = 0x400 | (int)thread.getId(); continue;
/* 392 */               }  if (m < 0 && (--c & 0x1FF) == 0)
/*     */               {
/* 394 */                 Thread.yield(); }  continue;
/*     */             } 
/* 396 */             if (U.getObjectVolatile(arrayOfNode, l) != node) {
/* 397 */               c = 'Ѐ'; continue;
/* 398 */             }  if (!thread.isInterrupted() && k == 0 && (!paramBoolean || (
/*     */               
/* 400 */               paramLong = l1 - System.nanoTime()) > 0L)) {
/* 401 */               U.putObject(thread, BLOCKER, this);
/* 402 */               node.parked = thread;
/* 403 */               if (U.getObjectVolatile(arrayOfNode, l) == node)
/* 404 */                 U.park(false, paramLong); 
/* 405 */               node.parked = null;
/* 406 */               U.putObject(thread, BLOCKER, null); continue;
/*     */             } 
/* 408 */             if (U.getObjectVolatile(arrayOfNode, l) == node && U
/* 409 */               .compareAndSwapObject(arrayOfNode, l, node, null))
/* 410 */             { if (k != 0)
/* 411 */                 U.compareAndSwapInt(this, BOUND, j, j + 256 - 1); 
/* 412 */               node.item = null;
/* 413 */               node.hash = m;
/* 414 */               i = node.index >>>= 1;
/* 415 */               if (Thread.interrupted())
/* 416 */                 return null;  break; } 
/* 417 */           }  if (paramBoolean && k == 0 && paramLong <= 0L) {
/* 418 */             return TIMED_OUT;
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 424 */         node.item = null;
/*     */         continue;
/*     */       } 
/* 427 */       if (node.bound != j) {
/* 428 */         node.bound = j;
/* 429 */         node.collides = 0;
/* 430 */         i = (i != k || k == 0) ? k : (k - 1);
/*     */       } else {
/* 432 */         int m; if ((m = node.collides) < k || k == FULL || 
/* 433 */           !U.compareAndSwapInt(this, BOUND, j, j + 256 + 1))
/* 434 */         { node.collides = m + 1;
/* 435 */           i = (i == 0) ? k : (i - 1); }
/*     */         else
/*     */         
/* 438 */         { i = k + 1; } 
/* 439 */       }  node.index = i;
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
/*     */   private final Object slotExchange(Object paramObject, boolean paramBoolean, long paramLong) {
/* 455 */     Node node = this.participant.get();
/* 456 */     Thread thread = Thread.currentThread();
/* 457 */     if (thread.isInterrupted())
/* 458 */       return null; 
/*     */     while (true) {
/*     */       Node node1;
/* 461 */       while ((node1 = this.slot) != null) {
/* 462 */         if (U.compareAndSwapObject(this, SLOT, node1, null)) {
/* 463 */           Object object1 = node1.item;
/* 464 */           node1.match = paramObject;
/* 465 */           Thread thread1 = node1.parked;
/* 466 */           if (thread1 != null)
/* 467 */             U.unpark(thread1); 
/* 468 */           return object1;
/*     */         } 
/*     */         
/* 471 */         if (NCPU > 1 && this.bound == 0 && U
/* 472 */           .compareAndSwapInt(this, BOUND, 0, 256))
/* 473 */           this.arena = new Node[FULL + 2 << 7]; 
/*     */       } 
/* 475 */       if (this.arena != null) {
/* 476 */         return null;
/*     */       }
/* 478 */       node.item = paramObject;
/* 479 */       if (U.compareAndSwapObject(this, SLOT, null, node))
/*     */         break; 
/* 481 */       node.item = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 486 */     int i = node.hash;
/* 487 */     long l = paramBoolean ? (System.nanoTime() + paramLong) : 0L;
/* 488 */     char c = (NCPU > 1) ? 'Ѐ' : '\001';
/*     */     Object object;
/* 490 */     while ((object = node.match) == null) {
/* 491 */       if (c) {
/* 492 */         i ^= i << 1; i ^= i >>> 3; i ^= i << 10;
/* 493 */         if (i == 0) {
/* 494 */           i = 0x400 | (int)thread.getId(); continue;
/* 495 */         }  if (i < 0 && (--c & 0x1FF) == 0)
/* 496 */           Thread.yield();  continue;
/*     */       } 
/* 498 */       if (this.slot != node) {
/* 499 */         c = 'Ѐ'; continue;
/* 500 */       }  if (!thread.isInterrupted() && this.arena == null && (!paramBoolean || (
/* 501 */         paramLong = l - System.nanoTime()) > 0L)) {
/* 502 */         U.putObject(thread, BLOCKER, this);
/* 503 */         node.parked = thread;
/* 504 */         if (this.slot == node)
/* 505 */           U.park(false, paramLong); 
/* 506 */         node.parked = null;
/* 507 */         U.putObject(thread, BLOCKER, null); continue;
/*     */       } 
/* 509 */       if (U.compareAndSwapObject(this, SLOT, node, null)) {
/* 510 */         object = (paramBoolean && paramLong <= 0L && !thread.isInterrupted()) ? TIMED_OUT : null;
/*     */         break;
/*     */       } 
/*     */     } 
/* 514 */     U.putOrderedObject(node, MATCH, null);
/* 515 */     node.item = null;
/* 516 */     node.hash = i;
/* 517 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exchanger() {
/* 524 */     this.participant = new Participant();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V exchange(V paramV) throws InterruptedException {
/* 563 */     Object object1 = (paramV == null) ? NULL_ITEM : (Object)paramV; Object object;
/* 564 */     if ((this.arena != null || (
/* 565 */       object = slotExchange(object1, false, 0L)) == null) && (
/* 566 */       Thread.interrupted() || (
/* 567 */       object = arenaExchange(object1, false, 0L)) == null))
/* 568 */       throw new InterruptedException(); 
/* 569 */     return (object == NULL_ITEM) ? null : (V)object;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V exchange(V paramV, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, TimeoutException {
/* 618 */     Object object1 = (paramV == null) ? NULL_ITEM : (Object)paramV;
/* 619 */     long l = paramTimeUnit.toNanos(paramLong); Object object;
/* 620 */     if ((this.arena != null || (
/* 621 */       object = slotExchange(object1, true, l)) == null) && (
/* 622 */       Thread.interrupted() || (
/* 623 */       object = arenaExchange(object1, true, l)) == null))
/* 624 */       throw new InterruptedException(); 
/* 625 */     if (object == TIMED_OUT)
/* 626 */       throw new TimeoutException(); 
/* 627 */     return (object == NULL_ITEM) ? null : (V)object;
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
/*     */   static {
/*     */     try {
/* 640 */       U = Unsafe.getUnsafe();
/* 641 */       Class<Exchanger> clazz = Exchanger.class;
/* 642 */       Class<Node> clazz1 = Node.class;
/* 643 */       Class<Node[]> clazz2 = Node[].class;
/* 644 */       Class<Thread> clazz3 = Thread.class;
/*     */       
/* 646 */       BOUND = U.objectFieldOffset(clazz.getDeclaredField("bound"));
/*     */       
/* 648 */       SLOT = U.objectFieldOffset(clazz.getDeclaredField("slot"));
/*     */       
/* 650 */       MATCH = U.objectFieldOffset(clazz1.getDeclaredField("match"));
/*     */       
/* 652 */       BLOCKER = U.objectFieldOffset(clazz3.getDeclaredField("parkBlocker"));
/* 653 */       i = U.arrayIndexScale(clazz2);
/*     */       
/* 655 */       ABASE = U.arrayBaseOffset(clazz2) + 128;
/*     */     }
/* 657 */     catch (Exception exception) {
/* 658 */       throw new Error(exception);
/*     */     } 
/* 660 */     if ((i & i - 1) != 0 || i > 128)
/* 661 */       throw new Error("Unsupported array scale"); 
/*     */   }
/*     */   
/*     */   static {
/*     */     int i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/Exchanger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */