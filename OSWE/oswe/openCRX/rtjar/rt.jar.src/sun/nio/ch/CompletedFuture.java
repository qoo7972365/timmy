/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ final class CompletedFuture<V>
/*    */   implements Future<V>
/*    */ {
/*    */   private final V result;
/*    */   private final Throwable exc;
/*    */   
/*    */   private CompletedFuture(V paramV, Throwable paramThrowable) {
/* 43 */     this.result = paramV;
/* 44 */     this.exc = paramThrowable;
/*    */   }
/*    */   
/*    */   static <V> CompletedFuture<V> withResult(V paramV) {
/* 48 */     return new CompletedFuture<>(paramV, null);
/*    */   }
/*    */ 
/*    */   
/*    */   static <V> CompletedFuture<V> withFailure(Throwable paramThrowable) {
/* 53 */     if (!(paramThrowable instanceof IOException) && !(paramThrowable instanceof SecurityException))
/* 54 */       paramThrowable = new IOException(paramThrowable); 
/* 55 */     return new CompletedFuture<>(null, paramThrowable);
/*    */   }
/*    */   
/*    */   static <V> CompletedFuture<V> withResult(V paramV, Throwable paramThrowable) {
/* 59 */     if (paramThrowable == null) {
/* 60 */       return withResult(paramV);
/*    */     }
/* 62 */     return withFailure(paramThrowable);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public V get() throws ExecutionException {
/* 68 */     if (this.exc != null)
/* 69 */       throw new ExecutionException(this.exc); 
/* 70 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public V get(long paramLong, TimeUnit paramTimeUnit) throws ExecutionException {
/* 75 */     if (paramTimeUnit == null)
/* 76 */       throw new NullPointerException(); 
/* 77 */     if (this.exc != null)
/* 78 */       throw new ExecutionException(this.exc); 
/* 79 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDone() {
/* 89 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean cancel(boolean paramBoolean) {
/* 94 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/CompletedFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */