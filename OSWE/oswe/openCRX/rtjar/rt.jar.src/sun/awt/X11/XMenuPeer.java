/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.Menu;
/*     */ import java.awt.MenuItem;
/*     */ import java.awt.peer.MenuPeer;
/*     */ import java.util.Vector;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.util.logging.PlatformLogger;
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
/*     */ public class XMenuPeer
/*     */   extends XMenuItemPeer
/*     */   implements MenuPeer
/*     */ {
/*  41 */   private static PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XMenuPeer");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XMenuWindow menuWindow;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XMenuPeer(Menu paramMenu) {
/*  54 */     super(paramMenu);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setContainer(XBaseMenuWindow paramXBaseMenuWindow) {
/*  63 */     super.setContainer(paramXBaseMenuWindow);
/*  64 */     this.menuWindow = new XMenuWindow(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/*  82 */     if (this.menuWindow != null) {
/*  83 */       this.menuWindow.dispose();
/*     */     }
/*  85 */     super.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font paramFont) {
/*  95 */     resetTextMetrics();
/*     */     
/*  97 */     XMenuWindow xMenuWindow = getMenuWindow();
/*  98 */     if (xMenuWindow != null) {
/*  99 */       xMenuWindow.setItemsFont(paramFont);
/*     */     }
/*     */     
/* 102 */     repaintIfShowing();
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
/*     */   public void addSeparator() {
/* 114 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 115 */       log.finer("addSeparator is not implemented");
/*     */     }
/*     */   }
/*     */   
/*     */   public void addItem(MenuItem paramMenuItem) {
/* 120 */     XMenuWindow xMenuWindow = getMenuWindow();
/* 121 */     if (xMenuWindow != null) {
/* 122 */       xMenuWindow.addItem(paramMenuItem);
/*     */     }
/* 124 */     else if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 125 */       log.fine("Attempt to use XMenuWindowPeer without window");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void delItem(int paramInt) {
/* 131 */     XMenuWindow xMenuWindow = getMenuWindow();
/* 132 */     if (xMenuWindow != null) {
/* 133 */       xMenuWindow.delItem(paramInt);
/*     */     }
/* 135 */     else if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 136 */       log.fine("Attempt to use XMenuWindowPeer without window");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Vector getTargetItems() {
/* 147 */     return AWTAccessor.getMenuAccessor().getItems((Menu)getTarget());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isSeparator() {
/* 156 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   String getShortcutText() {
/* 162 */     return null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   XMenuWindow getMenuWindow() {
/* 177 */     return this.menuWindow;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XMenuPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */