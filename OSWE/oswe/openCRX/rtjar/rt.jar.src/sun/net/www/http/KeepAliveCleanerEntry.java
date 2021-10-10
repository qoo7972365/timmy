/*    */ package sun.net.www.http;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class KeepAliveCleanerEntry
/*    */ {
/*    */   KeepAliveStream kas;
/*    */   HttpClient hc;
/*    */   
/*    */   public KeepAliveCleanerEntry(KeepAliveStream paramKeepAliveStream, HttpClient paramHttpClient) {
/* 36 */     this.kas = paramKeepAliveStream;
/* 37 */     this.hc = paramHttpClient;
/*    */   }
/*    */   
/*    */   protected KeepAliveStream getKeepAliveStream() {
/* 41 */     return this.kas;
/*    */   }
/*    */   
/*    */   protected HttpClient getHttpClient() {
/* 45 */     return this.hc;
/*    */   }
/*    */   
/*    */   protected void setQueuedForCleanup() {
/* 49 */     this.kas.queuedForCleanup = true;
/*    */   }
/*    */   
/*    */   protected boolean getQueuedForCleanup() {
/* 53 */     return this.kas.queuedForCleanup;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/http/KeepAliveCleanerEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */