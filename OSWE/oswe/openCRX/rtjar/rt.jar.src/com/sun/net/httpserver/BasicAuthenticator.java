/*    */ package com.sun.net.httpserver;
/*    */ 
/*    */ import java.util.Base64;
/*    */ import jdk.Exported;
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
/*    */ @Exported
/*    */ public abstract class BasicAuthenticator
/*    */   extends Authenticator
/*    */ {
/*    */   protected String realm;
/*    */   
/*    */   public BasicAuthenticator(String paramString) {
/* 47 */     this.realm = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getRealm() {
/* 55 */     return this.realm;
/*    */   }
/*    */ 
/*    */   
/*    */   public Authenticator.Result authenticate(HttpExchange paramHttpExchange) {
/* 60 */     Headers headers1 = paramHttpExchange.getRequestHeaders();
/*    */ 
/*    */ 
/*    */     
/* 64 */     String str1 = headers1.getFirst("Authorization");
/* 65 */     if (str1 == null) {
/* 66 */       Headers headers = paramHttpExchange.getResponseHeaders();
/* 67 */       headers.set("WWW-Authenticate", "Basic realm=\"" + this.realm + "\"");
/* 68 */       return new Authenticator.Retry(401);
/*    */     } 
/* 70 */     int i = str1.indexOf(' ');
/* 71 */     if (i == -1 || !str1.substring(0, i).equals("Basic")) {
/* 72 */       return new Authenticator.Failure(401);
/*    */     }
/* 74 */     byte[] arrayOfByte = Base64.getDecoder().decode(str1.substring(i + 1));
/* 75 */     String str2 = new String(arrayOfByte);
/* 76 */     int j = str2.indexOf(':');
/* 77 */     String str3 = str2.substring(0, j);
/* 78 */     String str4 = str2.substring(j + 1);
/*    */     
/* 80 */     if (checkCredentials(str3, str4)) {
/* 81 */       return new Authenticator.Success(new HttpPrincipal(str3, this.realm));
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 89 */     Headers headers2 = paramHttpExchange.getResponseHeaders();
/* 90 */     headers2.set("WWW-Authenticate", "Basic realm=\"" + this.realm + "\"");
/* 91 */     return new Authenticator.Failure(401);
/*    */   }
/*    */   
/*    */   public abstract boolean checkCredentials(String paramString1, String paramString2);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/httpserver/BasicAuthenticator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */