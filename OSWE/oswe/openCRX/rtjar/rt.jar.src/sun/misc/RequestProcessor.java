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
/*    */ public class RequestProcessor
/*    */   implements Runnable
/*    */ {
/*    */   private static Queue<Request> requestQueue;
/*    */   private static Thread dispatcher;
/*    */   
/*    */   public static void postRequest(Request paramRequest) {
/* 47 */     lazyInitialize();
/* 48 */     requestQueue.enqueue(paramRequest);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 55 */     lazyInitialize();
/*    */     while (true) {
/*    */       try {
/* 58 */         Request request = requestQueue.dequeue();
/*    */         try {
/* 60 */           request.execute();
/* 61 */         } catch (Throwable throwable) {}
/*    */ 
/*    */       
/*    */       }
/* 65 */       catch (InterruptedException interruptedException) {}
/*    */     } 
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
/*    */   public static synchronized void startProcessing() {
/* 79 */     if (dispatcher == null) {
/* 80 */       dispatcher = new Thread(new RequestProcessor(), "Request Processor");
/* 81 */       dispatcher.setPriority(7);
/* 82 */       dispatcher.start();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static synchronized void lazyInitialize() {
/* 91 */     if (requestQueue == null)
/* 92 */       requestQueue = new Queue<>(); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/RequestProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */