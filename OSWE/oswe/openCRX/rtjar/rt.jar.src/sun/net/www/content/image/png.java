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
/*    */ public class png
/*    */   extends ContentHandler
/*    */ {
/*    */   public Object getContent(URLConnection paramURLConnection) throws IOException {
/* 36 */     return new URLImageSource(paramURLConnection);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getContent(URLConnection paramURLConnection, Class[] paramArrayOfClass) throws IOException {
/* 41 */     Class[] arrayOfClass = paramArrayOfClass;
/* 42 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 43 */       if (arrayOfClass[b].isAssignableFrom(URLImageSource.class)) {
/* 44 */         return new URLImageSource(paramURLConnection);
/*    */       }
/* 46 */       if (arrayOfClass[b].isAssignableFrom(Image.class)) {
/* 47 */         Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 48 */         return toolkit.createImage(new URLImageSource(paramURLConnection));
/*    */       } 
/*    */     } 
/* 51 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/content/image/png.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */