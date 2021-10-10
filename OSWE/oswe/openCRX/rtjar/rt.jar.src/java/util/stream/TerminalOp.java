/*    */ package java.util.stream;
/*    */ 
/*    */ import java.util.Spliterator;
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
/*    */ interface TerminalOp<E_IN, R>
/*    */ {
/*    */   default StreamShape inputShape() {
/* 53 */     return StreamShape.REFERENCE;
/*    */   }
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
/*    */   default int getOpFlags() {
/* 66 */     return 0;
/*    */   }
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
/*    */   default <P_IN> R evaluateParallel(PipelineHelper<E_IN> paramPipelineHelper, Spliterator<P_IN> paramSpliterator) {
/* 82 */     if (Tripwire.ENABLED)
/* 83 */       Tripwire.trip(getClass(), "{0} triggering TerminalOp.evaluateParallel serial default"); 
/* 84 */     return evaluateSequential(paramPipelineHelper, paramSpliterator);
/*    */   }
/*    */   
/*    */   <P_IN> R evaluateSequential(PipelineHelper<E_IN> paramPipelineHelper, Spliterator<P_IN> paramSpliterator);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/TerminalOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */