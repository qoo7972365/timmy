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
/*    */ public class x_xpixmap
/*    */   extends ContentHandler
/*    */ {
/*    */   public Object getContent(URLConnection paramURLConnection) throws IOException {
/* 35 */     return new URLImageSource(paramURLConnection);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getContent(URLConnection paramURLConnection, Class[] paramArrayOfClass) throws IOException {
/* 40 */     Class[] arrayOfClass = paramArrayOfClass;
/* 41 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 42 */       if (arrayOfClass[b].isAssignableFrom(URLImageSource.class)) {
/* 43 */         return new URLImageSource(paramURLConnection);
/*    */       }
/* 45 */       if (arrayOfClass[b].isAssignableFrom(Image.class)) {
/* 46 */         Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 47 */         return toolkit.createImage(new URLImageSource(paramURLConnection));
/*    */       } 
/*    */     } 
/* 50 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/content/image/x_xpixmap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */