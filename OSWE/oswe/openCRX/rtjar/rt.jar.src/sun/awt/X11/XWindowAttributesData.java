/*    */ package sun.awt.X11;
/*    */ 
/*    */ import java.util.List;
/*    */ import sun.awt.IconInfo;
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
/*    */ class XWindowAttributesData
/*    */ {
/* 29 */   static int NORMAL = 0;
/* 30 */   static int ICONIC = 1;
/* 31 */   static int MAXIMIZED = 2;
/*    */   
/* 33 */   static int AWT_DECOR_NONE = 0;
/* 34 */   static int AWT_DECOR_ALL = 1;
/* 35 */   static int AWT_DECOR_BORDER = 2;
/* 36 */   static int AWT_DECOR_RESIZEH = 4;
/* 37 */   static int AWT_DECOR_TITLE = 8;
/* 38 */   static int AWT_DECOR_MENU = 16;
/* 39 */   static int AWT_DECOR_MINIMIZE = 32;
/* 40 */   static int AWT_DECOR_MAXIMIZE = 64;
/* 41 */   static int AWT_UNOBSCURED = 0;
/* 42 */   static int AWT_PARTIALLY_OBSCURED = 1;
/* 43 */   static int AWT_FULLY_OBSCURED = 2;
/* 44 */   static int AWT_UNKNOWN_OBSCURITY = 3;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean nativeDecor = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean initialFocus = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean isResizable = false;
/*    */ 
/*    */ 
/*    */   
/* 63 */   int initialState = NORMAL;
/* 64 */   int visibilityState = AWT_UNKNOWN_OBSCURITY;
/* 65 */   String title = null;
/* 66 */   List<IconInfo> icons = null;
/*    */   boolean iconsInherited = true;
/* 68 */   int decorations = 0;
/* 69 */   int functions = 0;
/*    */   boolean initialResizability = true;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XWindowAttributesData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */