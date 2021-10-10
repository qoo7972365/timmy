/*    */ package sun.font;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FontManagerFactory
/*    */ {
/* 50 */   private static FontManager instance = null;
/*    */   private static final String DEFAULT_CLASS;
/*    */   
/*    */   static {
/* 54 */     if (FontUtilities.isWindows) {
/* 55 */       DEFAULT_CLASS = "sun.awt.Win32FontManager";
/* 56 */     } else if (FontUtilities.isMacOSX) {
/* 57 */       DEFAULT_CLASS = "sun.font.CFontManager";
/*    */     } else {
/* 59 */       DEFAULT_CLASS = "sun.awt.X11FontManager";
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized FontManager getInstance() {
/* 70 */     if (instance != null) {
/* 71 */       return instance;
/*    */     }
/*    */     
/* 74 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */         {
/*    */           public Object run()
/*    */           {
/*    */             try {
/* 79 */               String str = System.getProperty("sun.font.fontmanager", FontManagerFactory
/* 80 */                   .DEFAULT_CLASS);
/* 81 */               ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 82 */               Class<?> clazz = Class.forName(str, true, classLoader);
/* 83 */               FontManagerFactory.instance = (FontManager)clazz.newInstance();
/* 84 */             } catch (ClassNotFoundException|InstantiationException|IllegalAccessException classNotFoundException) {
/*    */ 
/*    */               
/* 87 */               throw new InternalError(classNotFoundException);
/*    */             } 
/*    */             
/* 90 */             return null;
/*    */           }
/*    */         });
/*    */     
/* 94 */     return instance;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/FontManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */