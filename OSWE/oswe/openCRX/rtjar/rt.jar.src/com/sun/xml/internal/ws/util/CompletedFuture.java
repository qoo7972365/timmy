/*    */ package com.sun.xml.internal.ws.util;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CompletedFuture<T>
/*    */   implements Future<T>
/*    */ {
/*    */   private final T v;
/*    */   private final Throwable re;
/*    */   
/*    */   public CompletedFuture(T v, Throwable re) {
/* 43 */     this.v = v;
/* 44 */     this.re = re;
/*    */   }
/*    */   
/*    */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 48 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 52 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isDone() {
/* 56 */     return true;
/*    */   }
/*    */   
/*    */   public T get() throws ExecutionException {
/* 60 */     if (this.re != null) {
/* 61 */       throw new ExecutionException(this.re);
/*    */     }
/* 63 */     return this.v;
/*    */   }
/*    */   
/*    */   public T get(long timeout, TimeUnit unit) throws ExecutionException {
/* 67 */     return get();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/CompletedFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */