/*    */ package sun.net.www.content.text;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.ContentHandler;
/*    */ import java.net.URLConnection;
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
/*    */ public class plain
/*    */   extends ContentHandler
/*    */ {
/*    */   public Object getContent(URLConnection paramURLConnection) {
/*    */     try {
/* 42 */       InputStream inputStream = paramURLConnection.getInputStream();
/* 43 */       return new PlainTextInputStream(paramURLConnection.getInputStream());
/* 44 */     } catch (IOException iOException) {
/* 45 */       return "Error reading document:\n" + iOException.toString();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/content/text/plain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */