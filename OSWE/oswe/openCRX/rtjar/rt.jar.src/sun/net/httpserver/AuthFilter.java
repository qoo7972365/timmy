/*    */ package sun.net.httpserver;
/*    */ 
/*    */ import com.sun.net.httpserver.Authenticator;
/*    */ import com.sun.net.httpserver.Filter;
/*    */ import com.sun.net.httpserver.HttpExchange;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthFilter
/*    */   extends Filter
/*    */ {
/*    */   private Authenticator authenticator;
/*    */   
/*    */   public AuthFilter(Authenticator paramAuthenticator) {
/* 43 */     this.authenticator = paramAuthenticator;
/*    */   }
/*    */   
/*    */   public String description() {
/* 47 */     return "Authentication filter";
/*    */   }
/*    */   
/*    */   public void setAuthenticator(Authenticator paramAuthenticator) {
/* 51 */     this.authenticator = paramAuthenticator;
/*    */   }
/*    */   
/*    */   public void consumeInput(HttpExchange paramHttpExchange) throws IOException {
/* 55 */     InputStream inputStream = paramHttpExchange.getRequestBody();
/* 56 */     byte[] arrayOfByte = new byte[4096];
/* 57 */     while (inputStream.read(arrayOfByte) != -1);
/* 58 */     inputStream.close();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doFilter(HttpExchange paramHttpExchange, Filter.Chain paramChain) throws IOException {
/* 66 */     if (this.authenticator != null) {
/* 67 */       Authenticator.Result result = this.authenticator.authenticate(paramHttpExchange);
/* 68 */       if (result instanceof Authenticator.Success) {
/* 69 */         Authenticator.Success success = (Authenticator.Success)result;
/* 70 */         ExchangeImpl exchangeImpl = ExchangeImpl.get(paramHttpExchange);
/* 71 */         exchangeImpl.setPrincipal(success.getPrincipal());
/* 72 */         paramChain.doFilter(paramHttpExchange);
/* 73 */       } else if (result instanceof Authenticator.Retry) {
/* 74 */         Authenticator.Retry retry = (Authenticator.Retry)result;
/* 75 */         consumeInput(paramHttpExchange);
/* 76 */         paramHttpExchange.sendResponseHeaders(retry.getResponseCode(), -1L);
/* 77 */       } else if (result instanceof Authenticator.Failure) {
/* 78 */         Authenticator.Failure failure = (Authenticator.Failure)result;
/* 79 */         consumeInput(paramHttpExchange);
/* 80 */         paramHttpExchange.sendResponseHeaders(failure.getResponseCode(), -1L);
/*    */       } 
/*    */     } else {
/* 83 */       paramChain.doFilter(paramHttpExchange);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/AuthFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */