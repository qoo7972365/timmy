/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Event;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.PopupMenu;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.peer.PopupMenuPeer;
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
/*     */ public class XPopupMenuPeer
/*     */   extends XMenuWindow
/*     */   implements PopupMenuPeer
/*     */ {
/*  42 */   private static PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XBaseMenuWindow");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XComponentPeer componentPeer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PopupMenu popupMenuTarget;
/*     */ 
/*     */ 
/*     */   
/*  56 */   private XMenuPeer showingMousePressedSubmenu = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int CAPTION_MARGIN_TOP = 4;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int CAPTION_SEPARATOR_HEIGHT = 6;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XPopupMenuPeer(PopupMenu paramPopupMenu) {
/*  70 */     super((XMenuPeer)null);
/*  71 */     this.popupMenuTarget = paramPopupMenu;
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
/*     */   public void setFont(Font paramFont) {
/*  83 */     resetMapping();
/*  84 */     setItemsFont(paramFont);
/*  85 */     postPaintEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLabel(String paramString) {
/*  92 */     resetMapping();
/*  93 */     postPaintEvent();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean paramBoolean) {
/*  98 */     postPaintEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enable() {
/* 106 */     setEnabled(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disable() {
/* 114 */     setEnabled(false);
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
/* 126 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 127 */       log.finer("addSeparator is not implemented");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show(Event paramEvent) {
/* 135 */     this.target = (Component)paramEvent.target;
/*     */     
/* 137 */     Vector vector = getMenuTargetItems();
/* 138 */     if (vector != null) {
/* 139 */       reloadItems(vector);
/*     */       
/* 141 */       Point point1 = this.target.getLocationOnScreen();
/* 142 */       Point point2 = new Point(point1.x + paramEvent.x, point1.y + paramEvent.y);
/*     */ 
/*     */       
/* 145 */       if (!ensureCreated()) {
/*     */         return;
/*     */       }
/* 148 */       Dimension dimension = getDesiredSize();
/*     */ 
/*     */       
/* 151 */       Rectangle rectangle = getWindowBounds(point2, dimension);
/* 152 */       reshape(rectangle);
/* 153 */       xSetVisible(true);
/* 154 */       toFront();
/* 155 */       selectItem((XMenuItemPeer)null, false);
/* 156 */       grabInput();
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
/*     */   Font getTargetFont() {
/* 168 */     if (this.popupMenuTarget == null) {
/* 169 */       return XWindow.getDefaultFont();
/*     */     }
/* 171 */     return AWTAccessor.getMenuComponentAccessor()
/* 172 */       .getFont_NoClientCode(this.popupMenuTarget);
/*     */   }
/*     */ 
/*     */   
/*     */   String getTargetLabel() {
/* 177 */     if (this.target == null) {
/* 178 */       return "";
/*     */     }
/* 180 */     return AWTAccessor.getMenuItemAccessor().getLabel(this.popupMenuTarget);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isTargetEnabled() {
/* 185 */     if (this.popupMenuTarget == null) {
/* 186 */       return false;
/*     */     }
/* 188 */     return AWTAccessor.getMenuItemAccessor().isEnabled(this.popupMenuTarget);
/*     */   }
/*     */   
/*     */   Vector getMenuTargetItems() {
/* 192 */     if (this.popupMenuTarget == null) {
/* 193 */       return null;
/*     */     }
/* 195 */     return AWTAccessor.getMenuAccessor().getItems(this.popupMenuTarget);
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
/*     */   protected Rectangle getWindowBounds(Point paramPoint, Dimension paramDimension) {
/* 217 */     Rectangle rectangle1 = new Rectangle(paramPoint.x, paramPoint.y, 0, 0);
/* 218 */     Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
/*     */     
/* 220 */     Rectangle rectangle2 = fitWindowRight(rectangle1, paramDimension, dimension);
/* 221 */     if (rectangle2 != null) {
/* 222 */       return rectangle2;
/*     */     }
/* 224 */     rectangle2 = fitWindowLeft(rectangle1, paramDimension, dimension);
/* 225 */     if (rectangle2 != null) {
/* 226 */       return rectangle2;
/*     */     }
/* 228 */     rectangle2 = fitWindowBelow(rectangle1, paramDimension, dimension);
/* 229 */     if (rectangle2 != null) {
/* 230 */       return rectangle2;
/*     */     }
/* 232 */     rectangle2 = fitWindowAbove(rectangle1, paramDimension, dimension);
/* 233 */     if (rectangle2 != null) {
/* 234 */       return rectangle2;
/*     */     }
/* 236 */     return fitWindowToScreen(paramDimension, dimension);
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
/*     */   protected Dimension getCaptionSize() {
/* 250 */     String str = getTargetLabel();
/* 251 */     if (str.equals("")) {
/* 252 */       return null;
/*     */     }
/* 254 */     Graphics graphics = getGraphics();
/* 255 */     if (graphics == null) {
/* 256 */       return null;
/*     */     }
/*     */     try {
/* 259 */       graphics.setFont(getTargetFont());
/* 260 */       FontMetrics fontMetrics = graphics.getFontMetrics();
/* 261 */       String str1 = getTargetLabel();
/* 262 */       int i = fontMetrics.stringWidth(str1);
/* 263 */       int j = 4 + fontMetrics.getHeight() + 6;
/* 264 */       Dimension dimension = new Dimension(i, j);
/* 265 */       return dimension;
/*     */     } finally {
/* 267 */       graphics.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintCaption(Graphics paramGraphics, Rectangle paramRectangle) {
/* 277 */     String str1 = getTargetLabel();
/* 278 */     if (str1.equals("")) {
/*     */       return;
/*     */     }
/* 281 */     paramGraphics.setFont(getTargetFont());
/* 282 */     FontMetrics fontMetrics = paramGraphics.getFontMetrics();
/* 283 */     String str2 = getTargetLabel();
/* 284 */     int i = fontMetrics.stringWidth(str2);
/* 285 */     int j = paramRectangle.x + (paramRectangle.width - i) / 2;
/* 286 */     int k = paramRectangle.y + 4 + fontMetrics.getAscent();
/* 287 */     int m = paramRectangle.y + paramRectangle.height - 3;
/* 288 */     paramGraphics.setColor(isTargetEnabled() ? getForegroundColor() : getDisabledColor());
/* 289 */     paramGraphics.drawString(str1, j, k);
/* 290 */     draw3DRect(paramGraphics, paramRectangle.x, m, paramRectangle.width, 2, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDispose() {
/* 299 */     super.doDispose();
/* 300 */     XToolkit.targetDisposedPeer(this.popupMenuTarget, this);
/*     */   }
/*     */   
/*     */   protected void handleEvent(AWTEvent paramAWTEvent) {
/* 304 */     switch (paramAWTEvent.getID()) {
/*     */       case 500:
/*     */       case 501:
/*     */       case 502:
/*     */       case 503:
/*     */       case 504:
/*     */       case 505:
/*     */       case 506:
/* 312 */         doHandleJavaMouseEvent((MouseEvent)paramAWTEvent);
/*     */         return;
/*     */       case 401:
/*     */       case 402:
/* 316 */         doHandleJavaKeyEvent((KeyEvent)paramAWTEvent);
/*     */         return;
/*     */     } 
/* 319 */     super.handleEvent(paramAWTEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void ungrabInputImpl() {
/* 330 */     hide();
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
/*     */   public void handleKeyPress(XEvent paramXEvent) {
/* 345 */     XKeyEvent xKeyEvent = paramXEvent.get_xkey();
/* 346 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 347 */       log.fine(xKeyEvent.toString());
/*     */     }
/* 349 */     if (isEventDisabled(paramXEvent)) {
/*     */       return;
/*     */     }
/* 352 */     Component component = getEventSource();
/* 353 */     handleKeyPress(xKeyEvent);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XPopupMenuPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */