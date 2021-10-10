/*    */ package sun.rmi.transport.tcp;
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
/*    */ class MultiplexConnectionInfo
/*    */ {
/*    */   int id;
/* 39 */   MultiplexInputStream in = null;
/*    */ 
/*    */   
/* 42 */   MultiplexOutputStream out = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean closed = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   MultiplexConnectionInfo(int paramInt) {
/* 53 */     this.id = paramInt;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/tcp/MultiplexConnectionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */