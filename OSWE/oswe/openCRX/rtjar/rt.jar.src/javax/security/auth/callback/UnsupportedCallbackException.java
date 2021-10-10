/*    */ package javax.security.auth.callback;
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
/*    */ public class UnsupportedCallbackException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = -6873556327655666839L;
/*    */   private Callback callback;
/*    */   
/*    */   public UnsupportedCallbackException(Callback paramCallback) {
/* 52 */     this.callback = paramCallback;
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
/*    */   public UnsupportedCallbackException(Callback paramCallback, String paramString) {
/* 67 */     super(paramString);
/* 68 */     this.callback = paramCallback;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Callback getCallback() {
/* 79 */     return this.callback;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/callback/UnsupportedCallbackException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */