/*     */ package sun.nio.ch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NativeThreadSet
/*     */ {
/*     */   private long[] elts;
/*  35 */   private int used = 0;
/*     */   private boolean waitingToEmpty;
/*     */   
/*     */   NativeThreadSet(int paramInt) {
/*  39 */     this.elts = new long[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int add() {
/*  46 */     long l = NativeThread.current();
/*     */     
/*  48 */     if (l == 0L)
/*  49 */       l = -1L; 
/*  50 */     synchronized (this) {
/*  51 */       int i = 0;
/*  52 */       if (this.used >= this.elts.length) {
/*  53 */         int k = this.elts.length;
/*  54 */         int m = k * 2;
/*  55 */         long[] arrayOfLong = new long[m];
/*  56 */         System.arraycopy(this.elts, 0, arrayOfLong, 0, k);
/*  57 */         this.elts = arrayOfLong;
/*  58 */         i = k;
/*     */       } 
/*  60 */       for (int j = i; j < this.elts.length; j++) {
/*  61 */         if (this.elts[j] == 0L) {
/*  62 */           this.elts[j] = l;
/*  63 */           this.used++;
/*  64 */           return j;
/*     */         } 
/*     */       } 
/*     */       assert false;
/*  68 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void remove(int paramInt) {
/*  75 */     synchronized (this) {
/*  76 */       this.elts[paramInt] = 0L;
/*  77 */       this.used--;
/*  78 */       if (this.used == 0 && this.waitingToEmpty) {
/*  79 */         notifyAll();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void signalAndWait() {
/*  86 */     boolean bool = false;
/*  87 */     while (this.used > 0) {
/*  88 */       int i = this.used;
/*  89 */       int j = this.elts.length;
/*  90 */       for (byte b = 0; b < j; b++) {
/*  91 */         long l = this.elts[b];
/*  92 */         if (l != 0L) {
/*     */           
/*  94 */           if (l != -1L)
/*  95 */             NativeThread.signal(l); 
/*  96 */           if (--i == 0)
/*     */             break; 
/*     */         } 
/*  99 */       }  this.waitingToEmpty = true;
/*     */       try {
/* 101 */         wait(50L);
/* 102 */       } catch (InterruptedException interruptedException) {
/* 103 */         bool = true;
/*     */       } finally {
/* 105 */         this.waitingToEmpty = false;
/*     */       } 
/*     */     } 
/* 108 */     if (bool)
/* 109 */       Thread.currentThread().interrupt(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/NativeThreadSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */