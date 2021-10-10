/*    */ package sun.net.httpserver;
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
/*    */ class WriteFinishedEvent
/*    */   extends Event
/*    */ {
/*    */   WriteFinishedEvent(ExchangeImpl paramExchangeImpl) {
/* 30 */     super(paramExchangeImpl);
/* 31 */     assert !paramExchangeImpl.writefinished;
/* 32 */     paramExchangeImpl.writefinished = true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/WriteFinishedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */