/*    */ package sun.awt.X11;
/*    */ 
/*    */ import java.awt.GraphicsEnvironment;
/*    */ import sun.awt.PlatformFont;
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
/*    */ public class XFontPeer
/*    */   extends PlatformFont
/*    */ {
/*    */   private String xfsname;
/*    */   
/*    */   static {
/* 38 */     if (!GraphicsEnvironment.isHeadless()) {
/* 39 */       initIDs();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XFontPeer(String paramString, int paramInt) {
/* 50 */     super(paramString, paramInt);
/*    */   }
/*    */   
/*    */   protected char getMissingGlyphCharacter() {
/* 54 */     return '❏';
/*    */   }
/*    */   
/*    */   private static native void initIDs();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XFontPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */