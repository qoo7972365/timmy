/*    */ package java.lang;
/*    */ 
/*    */ import sun.misc.Signal;
/*    */ import sun.misc.SignalHandler;
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
/*    */ class Terminator
/*    */ {
/* 42 */   private static SignalHandler handler = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static void setup() {
/* 49 */     if (handler != null)
/* 50 */       return;  SignalHandler signalHandler = new SignalHandler() {
/*    */         public void handle(Signal param1Signal) {
/* 52 */           Shutdown.exit(param1Signal.getNumber() + 128);
/*    */         }
/*    */       };
/* 55 */     handler = signalHandler;
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 60 */       Signal.handle(new Signal("HUP"), signalHandler);
/* 61 */     } catch (IllegalArgumentException illegalArgumentException) {}
/*    */     
/*    */     try {
/* 64 */       Signal.handle(new Signal("INT"), signalHandler);
/* 65 */     } catch (IllegalArgumentException illegalArgumentException) {}
/*    */     
/*    */     try {
/* 68 */       Signal.handle(new Signal("TERM"), signalHandler);
/* 69 */     } catch (IllegalArgumentException illegalArgumentException) {}
/*    */   }
/*    */   
/*    */   static void teardown() {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/Terminator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */