/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ import sun.awt.AppContext;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultMetalTheme
/*     */   extends MetalTheme
/*     */ {
/*     */   private static final boolean PLAIN_FONTS;
/* 101 */   private static final String[] fontNames = new String[] { "Dialog", "Dialog", "Dialog", "Dialog", "Dialog", "Dialog" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private static final int[] fontStyles = new int[] { 1, 0, 0, 1, 1, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   private static final int[] fontSizes = new int[] { 12, 12, 12, 12, 12, 10 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   private static final String[] defaultNames = new String[] { "swing.plaf.metal.controlFont", "swing.plaf.metal.systemFont", "swing.plaf.metal.userFont", "swing.plaf.metal.controlFont", "swing.plaf.metal.controlFont", "swing.plaf.metal.smallFont" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getDefaultFontName(int paramInt) {
/* 141 */     return fontNames[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getDefaultFontSize(int paramInt) {
/* 148 */     return fontSizes[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getDefaultFontStyle(int paramInt) {
/* 155 */     if (paramInt != 4) {
/* 156 */       Object object = null;
/* 157 */       if (AppContext.getAppContext().get(SwingUtilities2.LAF_STATE_KEY) != null)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 162 */         object = UIManager.get("swing.boldMetal");
/*     */       }
/* 164 */       if (object != null) {
/* 165 */         if (Boolean.FALSE.equals(object)) {
/* 166 */           return 0;
/*     */         }
/*     */       }
/* 169 */       else if (PLAIN_FONTS) {
/* 170 */         return 0;
/*     */       } 
/*     */     } 
/* 173 */     return fontStyles[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getDefaultPropertyName(int paramInt) {
/* 180 */     return defaultNames[paramInt];
/*     */   }
/*     */   
/*     */   static {
/* 184 */     Object object = AccessController.doPrivileged(new GetPropertyAction("swing.boldMetal"));
/*     */     
/* 186 */     if (object == null || !"false".equals(object)) {
/* 187 */       PLAIN_FONTS = false;
/*     */     } else {
/*     */       
/* 190 */       PLAIN_FONTS = true;
/*     */     } 
/*     */   }
/*     */   
/* 194 */   private static final ColorUIResource primary1 = new ColorUIResource(102, 102, 153);
/*     */   
/* 196 */   private static final ColorUIResource primary2 = new ColorUIResource(153, 153, 204);
/*     */   
/* 198 */   private static final ColorUIResource primary3 = new ColorUIResource(204, 204, 255);
/*     */   
/* 200 */   private static final ColorUIResource secondary1 = new ColorUIResource(102, 102, 102);
/*     */   
/* 202 */   private static final ColorUIResource secondary2 = new ColorUIResource(153, 153, 153);
/*     */   
/* 204 */   private static final ColorUIResource secondary3 = new ColorUIResource(204, 204, 204);
/*     */ 
/*     */ 
/*     */   
/*     */   private FontDelegate fontDelegate;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 214 */     return "Steel";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultMetalTheme() {
/* 220 */     install();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getPrimary1() {
/* 229 */     return primary1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getPrimary2() {
/* 237 */     return primary2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getPrimary3() {
/* 245 */     return primary3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getSecondary1() {
/* 253 */     return secondary1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getSecondary2() {
/* 261 */     return secondary2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getSecondary3() {
/* 269 */     return secondary3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontUIResource getControlTextFont() {
/* 281 */     return getFont(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontUIResource getSystemTextFont() {
/* 290 */     return getFont(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontUIResource getUserTextFont() {
/* 299 */     return getFont(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontUIResource getMenuTextFont() {
/* 311 */     return getFont(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontUIResource getWindowTitleFont() {
/* 320 */     return getFont(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontUIResource getSubTextFont() {
/* 329 */     return getFont(5);
/*     */   }
/*     */   
/*     */   private FontUIResource getFont(int paramInt) {
/* 333 */     return this.fontDelegate.getFont(paramInt);
/*     */   }
/*     */   
/*     */   void install() {
/* 337 */     if (MetalLookAndFeel.isWindows() && 
/* 338 */       MetalLookAndFeel.useSystemFonts()) {
/* 339 */       this.fontDelegate = new WindowsFontDelegate();
/*     */     } else {
/*     */       
/* 342 */       this.fontDelegate = new FontDelegate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isSystemTheme() {
/* 350 */     return (getClass() == DefaultMetalTheme.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FontDelegate
/*     */   {
/* 357 */     private static int[] defaultMapping = new int[] { 0, 1, 2, 0, 0, 5 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 366 */     FontUIResource[] fonts = new FontUIResource[6];
/*     */ 
/*     */     
/*     */     public FontUIResource getFont(int param1Int) {
/* 370 */       int i = defaultMapping[param1Int];
/* 371 */       if (this.fonts[param1Int] == null) {
/* 372 */         Font font = getPrivilegedFont(i);
/*     */         
/* 374 */         if (font == null)
/*     */         {
/*     */           
/* 377 */           font = new Font(DefaultMetalTheme.getDefaultFontName(param1Int), DefaultMetalTheme.getDefaultFontStyle(param1Int), DefaultMetalTheme.getDefaultFontSize(param1Int));
/*     */         }
/* 379 */         this.fonts[param1Int] = new FontUIResource(font);
/*     */       } 
/* 381 */       return this.fonts[param1Int];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Font getPrivilegedFont(final int key) {
/* 390 */       return AccessController.<Font>doPrivileged(new PrivilegedAction<Font>()
/*     */           {
/*     */             public Font run() {
/* 393 */               return Font.getFont(DefaultMetalTheme.getDefaultPropertyName(key));
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WindowsFontDelegate
/*     */     extends FontDelegate
/*     */   {
/* 408 */     private MetalFontDesktopProperty[] props = new MetalFontDesktopProperty[6];
/* 409 */     private boolean[] checkedPriviledged = new boolean[6];
/*     */ 
/*     */     
/*     */     public FontUIResource getFont(int param1Int) {
/* 413 */       if (this.fonts[param1Int] != null) {
/* 414 */         return this.fonts[param1Int];
/*     */       }
/* 416 */       if (!this.checkedPriviledged[param1Int]) {
/* 417 */         Font font = getPrivilegedFont(param1Int);
/*     */         
/* 419 */         this.checkedPriviledged[param1Int] = true;
/* 420 */         if (font != null) {
/* 421 */           this.fonts[param1Int] = new FontUIResource(font);
/* 422 */           return this.fonts[param1Int];
/*     */         } 
/*     */       } 
/* 425 */       if (this.props[param1Int] == null) {
/* 426 */         this.props[param1Int] = new MetalFontDesktopProperty(param1Int);
/*     */       }
/*     */ 
/*     */       
/* 430 */       return (FontUIResource)this.props[param1Int].createValue(null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/DefaultMetalTheme.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */