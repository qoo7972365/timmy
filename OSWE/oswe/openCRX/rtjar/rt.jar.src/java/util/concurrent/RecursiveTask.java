/*    */ package java.util.concurrent;
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
/*    */ public abstract class RecursiveTask<V>
/*    */   extends ForkJoinTask<V>
/*    */ {
/*    */   private static final long serialVersionUID = 5232453952276485270L;
/*    */   V result;
/*    */   
/*    */   protected abstract V compute();
/*    */   
/*    */   public final V getRawResult() {
/* 83 */     return this.result;
/*    */   }
/*    */   
/*    */   protected final void setRawResult(V paramV) {
/* 87 */     this.result = paramV;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final boolean exec() {
/* 94 */     this.result = compute();
/* 95 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/RecursiveTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */