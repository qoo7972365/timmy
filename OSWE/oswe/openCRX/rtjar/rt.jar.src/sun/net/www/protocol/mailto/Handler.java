/*     */ package sun.net.www.protocol.mailto;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLStreamHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Handler
/*     */   extends URLStreamHandler
/*     */ {
/*     */   public synchronized URLConnection openConnection(URL paramURL) {
/* 103 */     return new MailToURLConnection(paramURL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseURL(URL paramURL, String paramString, int paramInt1, int paramInt2) {
/* 118 */     String str1 = paramURL.getProtocol();
/* 119 */     String str2 = "";
/* 120 */     int i = paramURL.getPort();
/* 121 */     String str3 = "";
/*     */     
/* 123 */     if (paramInt1 < paramInt2) {
/* 124 */       str3 = paramString.substring(paramInt1, paramInt2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 129 */     boolean bool = false;
/* 130 */     if (str3 == null || str3.equals("")) {
/* 131 */       bool = true;
/*     */     } else {
/* 133 */       boolean bool1 = true;
/* 134 */       for (byte b = 0; b < str3.length(); b++) {
/* 135 */         if (!Character.isWhitespace(str3.charAt(b)))
/* 136 */           bool1 = false; 
/* 137 */       }  if (bool1)
/* 138 */         bool = true; 
/*     */     } 
/* 140 */     if (bool)
/* 141 */       throw new RuntimeException("No email address"); 
/* 142 */     setURLHandler(paramURL, str1, str2, i, str3, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setURLHandler(URL paramURL, String paramString1, String paramString2, int paramInt, String paramString3, String paramString4) {
/* 156 */     setURL(paramURL, paramString1, paramString2, paramInt, paramString3, null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/mailto/Handler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */