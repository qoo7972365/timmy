/*     */ package com.sun.istack.internal;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Pool<T>
/*     */ {
/*     */   @NotNull
/*     */   T take();
/*     */   
/*     */   void recycle(@NotNull T paramT);
/*     */   
/*     */   public static abstract class Impl<T>
/*     */     implements Pool<T>
/*     */   {
/*     */     private volatile WeakReference<ConcurrentLinkedQueue<T>> queue;
/*     */     
/*     */     @NotNull
/*     */     public final T take() {
/*  74 */       T t = getQueue().poll();
/*  75 */       if (t == null) {
/*  76 */         return create();
/*     */       }
/*  78 */       return t;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void recycle(T t) {
/*  85 */       getQueue().offer(t);
/*     */     }
/*     */     
/*     */     private ConcurrentLinkedQueue<T> getQueue() {
/*  89 */       WeakReference<ConcurrentLinkedQueue<T>> q = this.queue;
/*  90 */       if (q != null) {
/*  91 */         ConcurrentLinkedQueue<T> concurrentLinkedQueue = q.get();
/*  92 */         if (concurrentLinkedQueue != null) {
/*  93 */           return concurrentLinkedQueue;
/*     */         }
/*     */       } 
/*     */       
/*  97 */       ConcurrentLinkedQueue<T> d = new ConcurrentLinkedQueue<>();
/*  98 */       this.queue = new WeakReference<>(d);
/*     */       
/* 100 */       return d;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     protected abstract T create();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/istack/internal/Pool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */