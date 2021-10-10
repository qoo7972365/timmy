/*    */ package sun.applet;
/*    */ 
/*    */ import java.awt.Toolkit;
/*    */ import java.net.URL;
/*    */ import sun.awt.image.URLImageSource;
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
/*    */ class AppletImageRef
/*    */   extends Ref
/*    */ {
/*    */   URL url;
/*    */   
/*    */   AppletImageRef(URL paramURL) {
/* 40 */     this.url = paramURL;
/*    */   }
/*    */   
/*    */   public void flush() {
/* 44 */     super.flush();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object reconstitute() {
/* 51 */     return Toolkit.getDefaultToolkit().createImage(new URLImageSource(this.url));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/AppletImageRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */