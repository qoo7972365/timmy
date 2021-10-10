/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMenuWindow
/*     */   extends XBaseMenuWindow
/*     */ {
/*  45 */   private static PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XMenuWindow");
/*     */   
/*     */   private XMenuPeer menuPeer;
/*     */   
/*     */   private static final int WINDOW_SPACING_LEFT = 2;
/*     */   
/*     */   private static final int WINDOW_SPACING_RIGHT = 2;
/*     */   
/*     */   private static final int WINDOW_SPACING_TOP = 2;
/*     */   
/*     */   private static final int WINDOW_SPACING_BOTTOM = 2;
/*     */   
/*     */   private static final int WINDOW_ITEM_INDENT = 15;
/*     */   
/*     */   private static final int WINDOW_ITEM_MARGIN_LEFT = 2;
/*     */   
/*     */   private static final int WINDOW_ITEM_MARGIN_RIGHT = 2;
/*     */   
/*     */   private static final int WINDOW_ITEM_MARGIN_TOP = 2;
/*     */   
/*     */   private static final int WINDOW_ITEM_MARGIN_BOTTOM = 2;
/*     */   
/*     */   private static final int WINDOW_SHORTCUT_SPACING = 10;
/*     */   
/*     */   private static final int CHECKMARK_SIZE = 128;
/*  70 */   private static final int[] CHECKMARK_X = new int[] { 1, 25, 56, 124, 124, 85, 64 };
/*  71 */   private static final int[] CHECKMARK_Y = new int[] { 59, 35, 67, 0, 12, 66, 123 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class MappingData
/*     */     extends XBaseMenuWindow.MappingData
/*     */   {
/*     */     private Rectangle captionRect;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Dimension desiredSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int leftMarkWidth;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int shortcutOrigin;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int rightMarkOrigin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MappingData(XMenuItemPeer[] param1ArrayOfXMenuItemPeer, Rectangle param1Rectangle, Dimension param1Dimension, int param1Int1, int param1Int2, int param1Int3) {
/* 110 */       super(param1ArrayOfXMenuItemPeer);
/* 111 */       this.captionRect = param1Rectangle;
/* 112 */       this.desiredSize = param1Dimension;
/* 113 */       this.leftMarkWidth = param1Int1;
/* 114 */       this.shortcutOrigin = param1Int2;
/* 115 */       this.rightMarkOrigin = param1Int3;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MappingData() {
/* 123 */       this.desiredSize = new Dimension(0, 0);
/* 124 */       this.leftMarkWidth = 0;
/* 125 */       this.shortcutOrigin = 0;
/* 126 */       this.rightMarkOrigin = 0;
/*     */     }
/*     */     
/*     */     public Rectangle getCaptionRect() {
/* 130 */       return this.captionRect;
/*     */     }
/*     */     
/*     */     public Dimension getDesiredSize() {
/* 134 */       return this.desiredSize;
/*     */     }
/*     */     
/*     */     public int getShortcutOrigin() {
/* 138 */       return this.shortcutOrigin;
/*     */     }
/*     */     
/*     */     public int getLeftMarkWidth() {
/* 142 */       return this.leftMarkWidth;
/*     */     }
/*     */     
/*     */     public int getRightMarkOrigin() {
/* 146 */       return this.rightMarkOrigin;
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
/*     */   XMenuWindow(XMenuPeer paramXMenuPeer) {
/* 163 */     if (paramXMenuPeer != null) {
/* 164 */       this.menuPeer = paramXMenuPeer;
/* 165 */       this.target = (paramXMenuPeer.getContainer()).target;
/*     */       
/* 167 */       Vector vector = null;
/* 168 */       vector = getMenuTargetItems();
/* 169 */       reloadItems(vector);
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
/*     */   void postInit(XCreateWindowParams paramXCreateWindowParams) {
/* 182 */     super.postInit(paramXCreateWindowParams);
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
/*     */   protected XBaseMenuWindow getParentMenuWindow() {
/* 198 */     return (this.menuPeer != null) ? this.menuPeer.getContainer() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MappingData map() {
/* 207 */     if (!isCreated()) {
/* 208 */       return new MappingData(new XMenuItemPeer[0], new Rectangle(0, 0, 0, 0), new Dimension(0, 0), 0, 0, 0);
/*     */     }
/*     */     
/* 211 */     XMenuItemPeer[] arrayOfXMenuItemPeer = copyItems();
/* 212 */     int i = arrayOfXMenuItemPeer.length;
/*     */     
/* 214 */     Dimension dimension = getCaptionSize();
/* 215 */     int j = (dimension != null) ? dimension.width : 0;
/* 216 */     int k = 0;
/* 217 */     int m = 0;
/* 218 */     int n = 0;
/* 219 */     XMenuItemPeer.TextMetrics[] arrayOfTextMetrics = new XMenuItemPeer.TextMetrics[i]; int i1;
/* 220 */     for (i1 = 0; i1 < i; i1++) {
/* 221 */       XMenuItemPeer xMenuItemPeer = arrayOfXMenuItemPeer[i1];
/* 222 */       arrayOfTextMetrics[i1] = arrayOfXMenuItemPeer[i1].getTextMetrics();
/* 223 */       Dimension dimension1 = arrayOfTextMetrics[i1].getTextDimension();
/* 224 */       if (dimension1 != null) {
/* 225 */         if (arrayOfXMenuItemPeer[i1] instanceof XCheckboxMenuItemPeer) {
/* 226 */           k = Math.max(k, dimension1.height);
/* 227 */         } else if (arrayOfXMenuItemPeer[i1] instanceof XMenuPeer) {
/* 228 */           m = Math.max(m, dimension1.height);
/*     */         } 
/* 230 */         j = Math.max(j, dimension1.width);
/* 231 */         n = Math.max(n, arrayOfTextMetrics[i1].getShortcutWidth());
/*     */       } 
/*     */     } 
/*     */     
/* 235 */     i1 = 2;
/* 236 */     int i2 = 4 + k + j;
/* 237 */     if (n > 0) {
/* 238 */       i2 += 10;
/*     */     }
/* 240 */     int i3 = i2 + n;
/* 241 */     int i4 = i3 + m + 2;
/* 242 */     int i5 = 2 + i4 + 2;
/*     */     
/* 244 */     Rectangle rectangle = null;
/* 245 */     if (dimension != null) {
/* 246 */       rectangle = new Rectangle(2, i1, i4, dimension.height);
/* 247 */       i1 += dimension.height;
/*     */     } else {
/* 249 */       rectangle = new Rectangle(2, i1, j, 0);
/*     */     } 
/*     */     int i6;
/* 252 */     for (i6 = 0; i6 < i; i6++) {
/* 253 */       XMenuItemPeer xMenuItemPeer = arrayOfXMenuItemPeer[i6];
/* 254 */       XMenuItemPeer.TextMetrics textMetrics = arrayOfTextMetrics[i6];
/* 255 */       Dimension dimension1 = textMetrics.getTextDimension();
/* 256 */       if (dimension1 != null) {
/* 257 */         int i7 = 2 + dimension1.height + 2;
/* 258 */         Rectangle rectangle1 = new Rectangle(2, i1, i4, i7);
/* 259 */         int i8 = (i7 + dimension1.height) / 2 - textMetrics.getTextBaseline();
/* 260 */         Point point = new Point(4 + k, i1 + i8);
/* 261 */         i1 += i7;
/* 262 */         xMenuItemPeer.map(rectangle1, point);
/*     */       }
/*     */       else {
/*     */         
/* 266 */         Rectangle rectangle1 = new Rectangle(2, i1, 0, 0);
/* 267 */         Point point = new Point(4 + k, i1);
/* 268 */         xMenuItemPeer.map(rectangle1, point);
/*     */       } 
/*     */     } 
/* 271 */     i6 = i1 + 2;
/* 272 */     return new MappingData(arrayOfXMenuItemPeer, rectangle, new Dimension(i5, i6), k, i2, i3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Rectangle getSubmenuBounds(Rectangle paramRectangle, Dimension paramDimension) {
/* 280 */     Rectangle rectangle1 = toGlobal(paramRectangle);
/* 281 */     Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
/*     */     
/* 283 */     Rectangle rectangle2 = fitWindowRight(rectangle1, paramDimension, dimension);
/* 284 */     if (rectangle2 != null) {
/* 285 */       return rectangle2;
/*     */     }
/* 287 */     rectangle2 = fitWindowBelow(rectangle1, paramDimension, dimension);
/* 288 */     if (rectangle2 != null) {
/* 289 */       return rectangle2;
/*     */     }
/* 291 */     rectangle2 = fitWindowAbove(rectangle1, paramDimension, dimension);
/* 292 */     if (rectangle2 != null) {
/* 293 */       return rectangle2;
/*     */     }
/* 295 */     rectangle2 = fitWindowLeft(rectangle1, paramDimension, dimension);
/* 296 */     if (rectangle2 != null) {
/* 297 */       return rectangle2;
/*     */     }
/* 299 */     return fitWindowToScreen(paramDimension, dimension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateSize() {
/* 307 */     resetMapping();
/* 308 */     if (isShowing()) {
/* 309 */       XToolkit.executeOnEventHandlerThread(this.target, new Runnable() {
/*     */             public void run() {
/* 311 */               Dimension dimension = XMenuWindow.this.getDesiredSize();
/* 312 */               XMenuWindow.this.reshape(XMenuWindow.this.x, XMenuWindow.this.y, dimension.width, dimension.height);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Dimension getCaptionSize() {
/* 331 */     return null;
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
/*     */   protected void paintCaption(Graphics paramGraphics, Rectangle paramRectangle) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XMenuPeer getMenuPeer() {
/* 352 */     return this.menuPeer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Vector getMenuTargetItems() {
/* 360 */     return this.menuPeer.getTargetItems();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Dimension getDesiredSize() {
/* 367 */     MappingData mappingData = (MappingData)getMappingData();
/* 368 */     return mappingData.getDesiredSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isCreated() {
/* 375 */     return (getWindow() != 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean ensureCreated() {
/* 382 */     if (!isCreated()) {
/* 383 */       XCreateWindowParams xCreateWindowParams = getDelayedParams();
/* 384 */       xCreateWindowParams.remove("delayed");
/* 385 */       xCreateWindowParams.add("overrideRedirect", Boolean.TRUE);
/* 386 */       xCreateWindowParams.add("target", this.target);
/* 387 */       init(xCreateWindowParams);
/*     */     } 
/* 389 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void show(Rectangle paramRectangle) {
/* 399 */     if (!isCreated()) {
/*     */       return;
/*     */     }
/* 402 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 403 */       log.finer("showing menu window + " + getWindow() + " at " + paramRectangle);
/*     */     }
/* 405 */     XToolkit.awtLock();
/*     */     try {
/* 407 */       reshape(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/* 408 */       xSetVisible(true);
/*     */ 
/*     */       
/* 411 */       toFront();
/* 412 */       selectItem(getFirstSelectableItem(), false);
/*     */     } finally {
/* 414 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void hide() {
/* 422 */     selectItem((XMenuItemPeer)null, false);
/* 423 */     xSetVisible(false);
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
/*     */   public void paintPeer(Graphics paramGraphics) {
/* 437 */     resetColors();
/* 438 */     int i = getWidth();
/* 439 */     int j = getHeight();
/*     */     
/* 441 */     flush();
/*     */     
/* 443 */     paramGraphics.setColor(getBackgroundColor());
/* 444 */     paramGraphics.fillRect(1, 1, i - 2, j - 2);
/* 445 */     draw3DRect(paramGraphics, 0, 0, i, j, true);
/*     */ 
/*     */     
/* 448 */     MappingData mappingData = (MappingData)getMappingData();
/*     */ 
/*     */     
/* 451 */     paintCaption(paramGraphics, mappingData.getCaptionRect());
/*     */ 
/*     */     
/* 454 */     XMenuItemPeer[] arrayOfXMenuItemPeer = mappingData.getItems();
/* 455 */     Dimension dimension = mappingData.getDesiredSize();
/* 456 */     XMenuItemPeer xMenuItemPeer = getSelectedItem();
/* 457 */     for (byte b = 0; b < arrayOfXMenuItemPeer.length; b++) {
/* 458 */       XMenuItemPeer xMenuItemPeer1 = arrayOfXMenuItemPeer[b];
/* 459 */       XMenuItemPeer.TextMetrics textMetrics = xMenuItemPeer1.getTextMetrics();
/* 460 */       Rectangle rectangle = xMenuItemPeer1.getBounds();
/* 461 */       if (xMenuItemPeer1.isSeparator()) {
/* 462 */         draw3DRect(paramGraphics, rectangle.x, rectangle.y + rectangle.height / 2, rectangle.width, 2, false);
/*     */       } else {
/*     */         
/* 465 */         paramGraphics.setFont(xMenuItemPeer1.getTargetFont());
/* 466 */         Point point = xMenuItemPeer1.getTextOrigin();
/* 467 */         Dimension dimension1 = textMetrics.getTextDimension();
/* 468 */         if (xMenuItemPeer1 == xMenuItemPeer) {
/* 469 */           paramGraphics.setColor(getSelectedColor());
/* 470 */           paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/* 471 */           draw3DRect(paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height, false);
/*     */         } 
/* 473 */         paramGraphics.setColor(xMenuItemPeer1.isTargetItemEnabled() ? getForegroundColor() : getDisabledColor());
/* 474 */         paramGraphics.drawString(xMenuItemPeer1.getTargetLabel(), point.x, point.y);
/* 475 */         String str = xMenuItemPeer1.getShortcutText();
/* 476 */         if (str != null) {
/* 477 */           paramGraphics.drawString(str, mappingData.getShortcutOrigin(), point.y);
/*     */         }
/* 479 */         if (xMenuItemPeer1 instanceof XMenuPeer) {
/*     */           
/* 481 */           int k = dimension1.height * 4 / 5;
/* 482 */           int m = dimension1.height * 4 / 5;
/* 483 */           int n = rectangle.x + rectangle.width - k - 2 - 2;
/* 484 */           int i1 = rectangle.y + (rectangle.height - m) / 2;
/*     */           
/* 486 */           paramGraphics.setColor(xMenuItemPeer1.isTargetItemEnabled() ? getDarkShadowColor() : getDisabledColor());
/* 487 */           paramGraphics.drawLine(n, i1 + m, n + k, i1 + m / 2);
/* 488 */           paramGraphics.setColor(xMenuItemPeer1.isTargetItemEnabled() ? getLightShadowColor() : getDisabledColor());
/* 489 */           paramGraphics.drawLine(n, i1, n + k, i1 + m / 2);
/* 490 */           paramGraphics.drawLine(n, i1, n, i1 + m);
/* 491 */         } else if (xMenuItemPeer1 instanceof XCheckboxMenuItemPeer) {
/*     */           
/* 493 */           int k = dimension1.height * 4 / 5;
/* 494 */           int m = dimension1.height * 4 / 5;
/* 495 */           byte b1 = 4;
/* 496 */           int n = rectangle.y + (rectangle.height - m) / 2;
/* 497 */           boolean bool = ((XCheckboxMenuItemPeer)xMenuItemPeer1).getTargetState();
/*     */           
/* 499 */           if (bool) {
/* 500 */             paramGraphics.setColor(getSelectedColor());
/* 501 */             paramGraphics.fillRect(b1, n, k, m);
/* 502 */             draw3DRect(paramGraphics, b1, n, k, m, false);
/* 503 */             int[] arrayOfInt1 = new int[CHECKMARK_X.length];
/* 504 */             int[] arrayOfInt2 = new int[CHECKMARK_X.length];
/* 505 */             for (byte b2 = 0; b2 < CHECKMARK_X.length; b2++) {
/* 506 */               arrayOfInt1[b2] = b1 + CHECKMARK_X[b2] * k / 128;
/* 507 */               arrayOfInt2[b2] = n + CHECKMARK_Y[b2] * m / 128;
/*     */             } 
/* 509 */             paramGraphics.setColor(xMenuItemPeer1.isTargetItemEnabled() ? getForegroundColor() : getDisabledColor());
/* 510 */             paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, CHECKMARK_X.length);
/*     */           } else {
/* 512 */             paramGraphics.setColor(getBackgroundColor());
/* 513 */             paramGraphics.fillRect(b1, n, k, m);
/* 514 */             draw3DRect(paramGraphics, b1, n, k, m, true);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 519 */     flush();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XMenuWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */