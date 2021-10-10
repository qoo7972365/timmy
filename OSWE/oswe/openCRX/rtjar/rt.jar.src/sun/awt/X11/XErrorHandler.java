/*    */ package sun.awt.X11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class XErrorHandler
/*    */ {
/*    */   public abstract int handleError(long paramLong, XErrorEvent paramXErrorEvent);
/*    */   
/*    */   public static class XBaseErrorHandler
/*    */     extends XErrorHandler
/*    */   {
/*    */     public int handleError(long param1Long, XErrorEvent param1XErrorEvent) {
/* 41 */       return XErrorHandlerUtil.SAVED_XERROR_HANDLER(param1Long, param1XErrorEvent);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class IgnoreBadWindowHandler
/*    */     extends XBaseErrorHandler
/*    */   {
/*    */     public int handleError(long param1Long, XErrorEvent param1XErrorEvent) {
/* 53 */       if (param1XErrorEvent.get_error_code() == 3) {
/* 54 */         return 0;
/*    */       }
/* 56 */       return super.handleError(param1Long, param1XErrorEvent);
/*    */     }
/*    */     
/* 59 */     private static IgnoreBadWindowHandler theInstance = new IgnoreBadWindowHandler();
/*    */     public static IgnoreBadWindowHandler getInstance() {
/* 61 */       return theInstance;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class VerifyChangePropertyHandler
/*    */     extends XBaseErrorHandler {
/*    */     public int handleError(long param1Long, XErrorEvent param1XErrorEvent) {
/* 68 */       if (param1XErrorEvent.get_request_code() == 18) {
/* 69 */         return 0;
/*    */       }
/* 71 */       return super.handleError(param1Long, param1XErrorEvent);
/*    */     }
/*    */     
/* 74 */     private static VerifyChangePropertyHandler theInstance = new VerifyChangePropertyHandler();
/*    */     public static VerifyChangePropertyHandler getInstance() {
/* 76 */       return theInstance;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XErrorHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */