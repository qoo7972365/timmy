/*    */ package sun.net.www.protocol.http.spnego;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.Authenticator;
/*    */ import java.net.PasswordAuthentication;
/*    */ import java.util.Arrays;
/*    */ import javax.security.auth.callback.Callback;
/*    */ import javax.security.auth.callback.CallbackHandler;
/*    */ import javax.security.auth.callback.NameCallback;
/*    */ import javax.security.auth.callback.PasswordCallback;
/*    */ import javax.security.auth.callback.UnsupportedCallbackException;
/*    */ import sun.net.www.protocol.http.HttpCallerInfo;
/*    */ import sun.security.jgss.LoginConfigImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NegotiateCallbackHandler
/*    */   implements CallbackHandler
/*    */ {
/*    */   private String username;
/*    */   private char[] password;
/*    */   private boolean answered;
/*    */   private final HttpCallerInfo hci;
/*    */   
/*    */   public NegotiateCallbackHandler(HttpCallerInfo paramHttpCallerInfo) {
/* 59 */     this.hci = paramHttpCallerInfo;
/*    */   }
/*    */   
/*    */   private void getAnswer() {
/* 63 */     if (!this.answered) {
/* 64 */       this.answered = true;
/*    */       
/* 66 */       if (LoginConfigImpl.HTTP_USE_GLOBAL_CREDS) {
/*    */         
/* 68 */         PasswordAuthentication passwordAuthentication = Authenticator.requestPasswordAuthentication(this.hci.host, this.hci.addr, this.hci.port, this.hci.protocol, this.hci.prompt, this.hci.scheme, this.hci.url, this.hci.authType);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 76 */         if (passwordAuthentication != null) {
/* 77 */           this.username = passwordAuthentication.getUserName();
/* 78 */           this.password = passwordAuthentication.getPassword();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(Callback[] paramArrayOfCallback) throws UnsupportedCallbackException, IOException {
/* 86 */     for (byte b = 0; b < paramArrayOfCallback.length; b++) {
/* 87 */       Callback callback = paramArrayOfCallback[b];
/*    */       
/* 89 */       if (callback instanceof NameCallback) {
/* 90 */         getAnswer();
/* 91 */         ((NameCallback)callback).setName(this.username);
/* 92 */       } else if (callback instanceof PasswordCallback) {
/* 93 */         getAnswer();
/* 94 */         ((PasswordCallback)callback).setPassword(this.password);
/* 95 */         if (this.password != null) Arrays.fill(this.password, ' '); 
/*    */       } else {
/* 97 */         throw new UnsupportedCallbackException(callback, "Call back not supported");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/spnego/NegotiateCallbackHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */