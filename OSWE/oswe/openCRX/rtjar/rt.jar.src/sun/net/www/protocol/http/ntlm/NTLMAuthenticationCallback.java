/*    */ package sun.net.www.protocol.http.ntlm;
/*    */ 
/*    */ import java.net.URL;
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
/*    */ public abstract class NTLMAuthenticationCallback
/*    */ {
/*    */   private static volatile NTLMAuthenticationCallback callback;
/*    */   
/*    */   public static void setNTLMAuthenticationCallback(NTLMAuthenticationCallback paramNTLMAuthenticationCallback) {
/* 40 */     callback = paramNTLMAuthenticationCallback;
/*    */   }
/*    */   
/*    */   public static NTLMAuthenticationCallback getNTLMAuthenticationCallback() {
/* 44 */     return callback;
/*    */   }
/*    */   
/*    */   public abstract boolean isTrustedSite(URL paramURL);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/ntlm/NTLMAuthenticationCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */