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
/*    */ public interface SignalHandler
/*    */ {
/* 42 */   public static final SignalHandler SIG_DFL = new NativeSignalHandler(0L);
/*    */ 
/*    */ 
/*    */   
/* 46 */   public static final SignalHandler SIG_IGN = new NativeSignalHandler(1L);
/*    */   
/*    */   void handle(Signal paramSignal);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/SignalHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */