/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.MenuItem;
/*     */ import java.awt.MenuShortcut;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.peer.MenuItemPeer;
/*     */ import sun.awt.AWTAccessor;
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
/*     */ public class XMenuItemPeer
/*     */   implements MenuItemPeer
/*     */ {
/*     */   private XBaseMenuWindow container;
/*     */   private MenuItem target;
/*     */   private Rectangle bounds;
/*     */   private Point textOrigin;
/*     */   private static final int SEPARATOR_WIDTH = 20;
/*     */   private static final int SEPARATOR_HEIGHT = 5;
/*     */   private TextMetrics textMetrics;
/*     */   
/*     */   static class TextMetrics
/*     */     implements Cloneable
/*     */   {
/*     */     private Dimension textDimension;
/*     */     private int shortcutWidth;
/*     */     private int textBaseline;
/*     */     
/*     */     TextMetrics(Dimension param1Dimension, int param1Int1, int param1Int2) {
/* 104 */       this.textDimension = param1Dimension;
/* 105 */       this.shortcutWidth = param1Int1;
/* 106 */       this.textBaseline = param1Int2;
/*     */     }
/*     */     
/*     */     public Object clone() {
/*     */       try {
/* 111 */         return super.clone();
/* 112 */       } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 113 */         throw new InternalError(cloneNotSupportedException);
/*     */       } 
/*     */     }
/*     */     
/*     */     Dimension getTextDimension() {
/* 118 */       return this.textDimension;
/*     */     }
/*     */     
/*     */     int getShortcutWidth() {
/* 122 */       return this.shortcutWidth;
/*     */     }
/*     */     
/*     */     int getTextBaseline() {
/* 126 */       return this.textBaseline;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XMenuItemPeer(MenuItem paramMenuItem) {
/* 136 */     this.target = paramMenuItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font paramFont) {
/* 153 */     resetTextMetrics();
/* 154 */     repaintIfShowing();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLabel(String paramString) {
/* 160 */     resetTextMetrics();
/* 161 */     repaintIfShowing();
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean paramBoolean) {
/* 165 */     repaintIfShowing();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enable() {
/* 173 */     setEnabled(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disable() {
/* 181 */     setEnabled(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MenuItem getTarget() {
/* 191 */     return this.target;
/*     */   }
/*     */   
/*     */   Font getTargetFont() {
/* 195 */     if (this.target == null) {
/* 196 */       return XWindow.getDefaultFont();
/*     */     }
/* 198 */     return AWTAccessor.getMenuComponentAccessor().getFont_NoClientCode(this.target);
/*     */   }
/*     */   
/*     */   String getTargetLabel() {
/* 202 */     if (this.target == null) {
/* 203 */       return "";
/*     */     }
/* 205 */     String str = AWTAccessor.getMenuItemAccessor().getLabel(this.target);
/* 206 */     return (str == null) ? "" : str;
/*     */   }
/*     */   
/*     */   boolean isTargetEnabled() {
/* 210 */     if (this.target == null) {
/* 211 */       return false;
/*     */     }
/* 213 */     return AWTAccessor.getMenuItemAccessor().isEnabled(this.target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isTargetItemEnabled() {
/* 222 */     if (this.target == null) {
/* 223 */       return false;
/*     */     }
/* 225 */     return AWTAccessor.getMenuItemAccessor().isItemEnabled(this.target);
/*     */   }
/*     */   
/*     */   String getTargetActionCommand() {
/* 229 */     if (this.target == null) {
/* 230 */       return "";
/*     */     }
/* 232 */     return AWTAccessor.getMenuItemAccessor().getActionCommandImpl(this.target);
/*     */   }
/*     */   
/*     */   MenuShortcut getTargetShortcut() {
/* 236 */     if (this.target == null) {
/* 237 */       return null;
/*     */     }
/* 239 */     return AWTAccessor.getMenuItemAccessor().getShortcut(this.target);
/*     */   }
/*     */ 
/*     */   
/*     */   String getShortcutText() {
/* 244 */     if (this.container == null) {
/* 245 */       return null;
/*     */     }
/* 247 */     if (this.container.getRootMenuWindow() instanceof XPopupMenuPeer) {
/* 248 */       return null;
/*     */     }
/* 250 */     MenuShortcut menuShortcut = getTargetShortcut();
/*     */     
/* 252 */     return (menuShortcut == null) ? null : menuShortcut.toString();
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
/*     */   void setContainer(XBaseMenuWindow paramXBaseMenuWindow) {
/* 269 */     synchronized (XBaseMenuWindow.getMenuTreeLock()) {
/* 270 */       this.container = paramXBaseMenuWindow;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XBaseMenuWindow getContainer() {
/* 278 */     return this.container;
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
/*     */   boolean isSeparator() {
/* 292 */     return getTargetLabel().equals("-");
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
/*     */   boolean isContainerShowing() {
/* 306 */     if (this.container == null) {
/* 307 */       return false;
/*     */     }
/* 309 */     return this.container.isShowing();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void repaintIfShowing() {
/* 316 */     if (isContainerShowing()) {
/* 317 */       this.container.postPaintEvent();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void action(long paramLong) {
/* 327 */     if (!isSeparator() && isTargetItemEnabled()) {
/* 328 */       XWindow.postEventStatic(new ActionEvent(this.target, 1001, 
/* 329 */             getTargetActionCommand(), paramLong, 0));
/*     */     }
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
/*     */   TextMetrics getTextMetrics() {
/* 346 */     TextMetrics textMetrics = this.textMetrics;
/* 347 */     if (textMetrics == null) {
/* 348 */       textMetrics = calcTextMetrics();
/* 349 */       this.textMetrics = textMetrics;
/*     */     } 
/* 351 */     return textMetrics;
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
/*     */   TextMetrics calcTextMetrics() {
/* 397 */     if (this.container == null) {
/* 398 */       return null;
/*     */     }
/* 400 */     if (isSeparator()) {
/* 401 */       return new TextMetrics(new Dimension(20, 5), 0, 0);
/*     */     }
/* 403 */     Graphics graphics = this.container.getGraphics();
/* 404 */     if (graphics == null) {
/* 405 */       return null;
/*     */     }
/*     */     try {
/* 408 */       graphics.setFont(getTargetFont());
/* 409 */       FontMetrics fontMetrics = graphics.getFontMetrics();
/* 410 */       String str1 = getTargetLabel();
/* 411 */       int i = fontMetrics.stringWidth(str1);
/* 412 */       int j = fontMetrics.getHeight();
/* 413 */       Dimension dimension = new Dimension(i, j);
/* 414 */       int k = fontMetrics.getHeight() - fontMetrics.getAscent();
/* 415 */       String str2 = getShortcutText();
/* 416 */       boolean bool = (str2 == null) ? false : fontMetrics.stringWidth(str2);
/* 417 */       return new TextMetrics(dimension, bool, k);
/*     */     } finally {
/* 419 */       graphics.dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   void resetTextMetrics() {
/* 424 */     this.textMetrics = null;
/* 425 */     if (this.container != null) {
/* 426 */       this.container.updateSize();
/*     */     }
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
/*     */   void map(Rectangle paramRectangle, Point paramPoint) {
/* 443 */     this.bounds = paramRectangle;
/* 444 */     this.textOrigin = paramPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Rectangle getBounds() {
/* 451 */     return this.bounds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Point getTextOrigin() {
/* 458 */     return this.textOrigin;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XMenuItemPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */