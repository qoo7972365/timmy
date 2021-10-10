/*    */ package sun.net.www.protocol.netdoc;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLStreamHandler;
/*    */ import java.security.AccessController;
/*    */ import sun.security.action.GetBooleanAction;
/*    */ import sun.security.action.GetPropertyAction;
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
/*    */ public class Handler
/*    */   extends URLStreamHandler
/*    */ {
/*    */   static URL base;
/*    */   
/*    */   public synchronized URLConnection openConnection(URL paramURL) throws IOException {
/* 54 */     URLConnection uRLConnection = null;
/*    */ 
/*    */     
/* 57 */     Boolean bool = AccessController.<Boolean>doPrivileged(new GetBooleanAction("newdoc.localonly"));
/*    */     
/* 59 */     boolean bool1 = bool.booleanValue();
/*    */     
/* 61 */     String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("doc.url"));
/*    */ 
/*    */     
/* 64 */     String str2 = paramURL.getFile();
/* 65 */     if (!bool1) {
/*    */       URL uRL; try {
/* 67 */         if (base == null) {
/* 68 */           base = new URL(str1);
/*    */         }
/* 70 */         uRL = new URL(base, str2);
/* 71 */       } catch (MalformedURLException malformedURLException) {
/* 72 */         uRL = null;
/*    */       } 
/* 74 */       if (uRL != null) {
/* 75 */         uRLConnection = uRL.openConnection();
/*    */       }
/*    */     } 
/*    */     
/* 79 */     if (uRLConnection == null) {
/*    */       try {
/* 81 */         URL uRL = new URL("file", "~", str2);
/*    */         
/* 83 */         uRLConnection = uRL.openConnection();
/* 84 */         InputStream inputStream = uRLConnection.getInputStream();
/* 85 */       } catch (MalformedURLException malformedURLException) {
/* 86 */         uRLConnection = null;
/* 87 */       } catch (IOException iOException) {
/* 88 */         uRLConnection = null;
/*    */       } 
/*    */     }
/*    */     
/* 92 */     if (uRLConnection == null) {
/* 93 */       throw new IOException("Can't find file for URL: " + paramURL
/* 94 */           .toExternalForm());
/*    */     }
/* 96 */     return uRLConnection;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/netdoc/Handler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */