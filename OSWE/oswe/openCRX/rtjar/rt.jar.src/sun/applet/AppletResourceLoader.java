/*    */ package sun.applet;
/*    */ 
/*    */ import java.awt.Image;
/*    */ import java.net.URL;
/*    */ import sun.misc.Ref;
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
/*    */ public class AppletResourceLoader
/*    */ {
/*    */   public static Image getImage(URL paramURL) {
/* 38 */     return AppletViewer.getCachedImage(paramURL);
/*    */   }
/*    */   
/*    */   public static Ref getImageRef(URL paramURL) {
/* 42 */     return AppletViewer.getCachedImageRef(paramURL);
/*    */   }
/*    */   
/*    */   public static void flushImages() {
/* 46 */     AppletViewer.flushImageCache();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/AppletResourceLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */