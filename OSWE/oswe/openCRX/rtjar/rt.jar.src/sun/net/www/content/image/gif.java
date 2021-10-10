/*    */ package sun.net.www.content.image;
/*    */ 
/*    */ import java.awt.Image;
/*    */ import java.awt.Toolkit;
/*    */ import java.io.IOException;
/*    */ import java.net.ContentHandler;
/*    */ import java.net.URLConnection;
/*    */ import sun.awt.image.URLImageSource;
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
/*    */ public class gif
/*    */   extends ContentHandler
/*    */ {
/*    */   public Object getContent(URLConnection paramURLConnection) throws IOException {
/* 37 */     return new URLImageSource(paramURLConnection);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getContent(URLConnection paramURLConnection, Class[] paramArrayOfClass) throws IOException {
/* 42 */     Class[] arrayOfClass = paramArrayOfClass;
/* 43 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 44 */       if (arrayOfClass[b].isAssignableFrom(URLImageSource.class)) {
/* 45 */         return new URLImageSource(paramURLConnection);
/*    */       }
/* 47 */       if (arrayOfClass[b].isAssignableFrom(Image.class)) {
/* 48 */         Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 49 */         return toolkit.createImage(new URLImageSource(paramURLConnection));
/*    */       } 
/*    */     } 
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/content/image/gif.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */