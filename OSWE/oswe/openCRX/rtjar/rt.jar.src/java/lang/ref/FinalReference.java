/*    */ package java.lang.ref;
/*    */ 
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.ReferenceQueue;
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
/*    */ class FinalReference<T>
/*    */   extends Reference<T>
/*    */ {
/*    */   public FinalReference(T paramT, ReferenceQueue<? super T> paramReferenceQueue) {
/* 34 */     super(paramT, paramReferenceQueue);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/ref/FinalReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */