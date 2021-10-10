/*    */ package java.security;
/*    */ 
/*    */ import javax.security.auth.Subject;
/*    */ import javax.security.auth.callback.CallbackHandler;
/*    */ import javax.security.auth.login.LoginException;
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
/*    */ public abstract class AuthProvider
/*    */   extends Provider
/*    */ {
/*    */   private static final long serialVersionUID = 4197859053084546461L;
/*    */   
/*    */   protected AuthProvider(String paramString1, double paramDouble, String paramString2) {
/* 55 */     super(paramString1, paramDouble, paramString2);
/*    */   }
/*    */   
/*    */   public abstract void login(Subject paramSubject, CallbackHandler paramCallbackHandler) throws LoginException;
/*    */   
/*    */   public abstract void logout() throws LoginException;
/*    */   
/*    */   public abstract void setCallbackHandler(CallbackHandler paramCallbackHandler);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/AuthProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */