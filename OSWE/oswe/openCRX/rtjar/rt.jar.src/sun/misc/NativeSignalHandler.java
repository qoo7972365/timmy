/*    */ package sun.misc;
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
/*    */ final class NativeSignalHandler
/*    */   implements SignalHandler
/*    */ {
/*    */   private final long handler;
/*    */   
/*    */   long getHandler() {
/* 35 */     return this.handler;
/*    */   }
/*    */   
/*    */   NativeSignalHandler(long paramLong) {
/* 39 */     this.handler = paramLong;
/*    */   }
/*    */   
/*    */   public void handle(Signal paramSignal) {
/* 43 */     handle0(paramSignal.getNumber(), this.handler);
/*    */   }
/*    */   
/*    */   private static native void handle0(int paramInt, long paramLong);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/NativeSignalHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */