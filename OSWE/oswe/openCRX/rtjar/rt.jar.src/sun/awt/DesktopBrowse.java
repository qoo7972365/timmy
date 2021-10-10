/*    */ package sun.awt;
/*    */ 
/*    */ import java.net.URL;
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
/*    */ public abstract class DesktopBrowse
/*    */ {
/*    */   private static volatile DesktopBrowse mInstance;
/*    */   
/*    */   public static void setInstance(DesktopBrowse paramDesktopBrowse) {
/* 34 */     if (mInstance != null) {
/* 35 */       throw new IllegalStateException("DesktopBrowse instance has already been set.");
/*    */     }
/* 37 */     mInstance = paramDesktopBrowse;
/*    */   }
/*    */   
/*    */   public static DesktopBrowse getInstance() {
/* 41 */     return mInstance;
/*    */   }
/*    */   
/*    */   public abstract void browse(URL paramURL);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/DesktopBrowse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */