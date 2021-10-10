/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Menu;
/*     */ import java.awt.MenuBar;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.peer.MenuBarPeer;
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
/*     */ public class XMenuBarPeer
/*     */   extends XBaseMenuWindow
/*     */   implements MenuBarPeer
/*     */ {
/*  43 */   private static PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XMenuBarPeer");
/*     */ 
/*     */ 
/*     */   
/*     */   private XFramePeer framePeer;
/*     */ 
/*     */ 
/*     */   
/*     */   private MenuBar menuBarTarget;
/*     */ 
/*     */   
/*  54 */   private XMenuPeer helpMenu = null;
/*     */   
/*     */   private static final int BAR_SPACING_TOP = 3;
/*     */   
/*     */   private static final int BAR_SPACING_BOTTOM = 3;
/*     */   
/*     */   private static final int BAR_SPACING_LEFT = 3;
/*     */   
/*     */   private static final int BAR_SPACING_RIGHT = 3;
/*     */   
/*     */   private static final int BAR_ITEM_SPACING = 2;
/*     */   
/*     */   private static final int BAR_ITEM_MARGIN_LEFT = 10;
/*     */   
/*     */   private static final int BAR_ITEM_MARGIN_RIGHT = 10;
/*     */   
/*     */   private static final int BAR_ITEM_MARGIN_TOP = 2;
/*     */   
/*     */   private static final int BAR_ITEM_MARGIN_BOTTOM = 2;
/*     */   
/*     */   static final int W_DIFF = 12;
/*     */   static final int H_DIFF = 23;
/*     */   
/*     */   static class MappingData
/*     */     extends XBaseMenuWindow.MappingData
/*     */   {
/*     */     int desiredHeight;
/*     */     
/*     */     MappingData(XMenuItemPeer[] param1ArrayOfXMenuItemPeer, int param1Int) {
/*  83 */       super(param1ArrayOfXMenuItemPeer);
/*  84 */       this.desiredHeight = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MappingData() {
/*  92 */       this.desiredHeight = 0;
/*     */     }
/*     */     
/*     */     public int getDesiredHeight() {
/*  96 */       return this.desiredHeight;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XMenuBarPeer(MenuBar paramMenuBar) {
/* 106 */     this.menuBarTarget = paramMenuBar;
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
/*     */   public void setFont(Font paramFont) {
/* 119 */     resetMapping();
/* 120 */     setItemsFont(paramFont);
/* 121 */     postPaintEvent();
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
/*     */   public void addMenu(Menu paramMenu) {
/* 151 */     addItem(paramMenu);
/* 152 */     postPaintEvent();
/*     */   }
/*     */   
/*     */   public void delMenu(int paramInt) {
/* 156 */     synchronized (getMenuTreeLock()) {
/* 157 */       XMenuItemPeer xMenuItemPeer = getItem(paramInt);
/* 158 */       if (xMenuItemPeer != null && xMenuItemPeer == this.helpMenu) {
/* 159 */         this.helpMenu = null;
/*     */       }
/* 161 */       delItem(paramInt);
/*     */     } 
/* 163 */     postPaintEvent();
/*     */   }
/*     */   
/*     */   public void addHelpMenu(Menu paramMenu) {
/* 167 */     XMenuPeer xMenuPeer = (XMenuPeer)paramMenu.getPeer();
/* 168 */     synchronized (getMenuTreeLock()) {
/* 169 */       this.helpMenu = xMenuPeer;
/*     */     } 
/* 171 */     postPaintEvent();
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
/*     */   public void init(Frame paramFrame) {
/* 183 */     this.target = paramFrame;
/* 184 */     this.framePeer = (XFramePeer)paramFrame.getPeer();
/* 185 */     XCreateWindowParams xCreateWindowParams = getDelayedParams();
/* 186 */     xCreateWindowParams.remove("delayed");
/* 187 */     xCreateWindowParams.add("parent window", this.framePeer.getShell());
/* 188 */     xCreateWindowParams.add("target", paramFrame);
/* 189 */     init(xCreateWindowParams);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void postInit(XCreateWindowParams paramXCreateWindowParams) {
/* 196 */     super.postInit(paramXCreateWindowParams);
/*     */ 
/*     */     
/* 199 */     Vector<Menu> vector = AWTAccessor.getMenuBarAccessor().getMenus(this.menuBarTarget);
/*     */     
/* 201 */     Menu menu = AWTAccessor.getMenuBarAccessor().getHelpMenu(this.menuBarTarget);
/* 202 */     reloadItems(vector);
/* 203 */     if (menu != null) {
/* 204 */       addHelpMenu(menu);
/*     */     }
/* 206 */     xSetVisible(true);
/* 207 */     toFront();
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
/*     */   protected XBaseMenuWindow getParentMenuWindow() {
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MappingData map() {
/* 228 */     XMenuItemPeer[] arrayOfXMenuItemPeer1 = copyItems();
/* 229 */     int i = arrayOfXMenuItemPeer1.length;
/* 230 */     XMenuPeer xMenuPeer = this.helpMenu;
/* 231 */     byte b = -1;
/*     */     
/* 233 */     if (xMenuPeer != null) {
/*     */       
/* 235 */       for (byte b2 = 0; b2 < i; b2++) {
/* 236 */         if (arrayOfXMenuItemPeer1[b2] == xMenuPeer) {
/* 237 */           b = b2;
/*     */           break;
/*     */         } 
/*     */       } 
/* 241 */       if (b != -1 && b != i - 1) {
/* 242 */         System.arraycopy(arrayOfXMenuItemPeer1, b + 1, arrayOfXMenuItemPeer1, b, i - 1 - b);
/* 243 */         arrayOfXMenuItemPeer1[i - 1] = xMenuPeer;
/*     */       } 
/*     */     } 
/*     */     
/* 247 */     int j = 0;
/* 248 */     XMenuItemPeer.TextMetrics[] arrayOfTextMetrics = new XMenuItemPeer.TextMetrics[i]; int k;
/* 249 */     for (k = 0; k < i; k++) {
/* 250 */       arrayOfTextMetrics[k] = arrayOfXMenuItemPeer1[k].getTextMetrics();
/* 251 */       if (arrayOfTextMetrics[k] != null) {
/* 252 */         Dimension dimension = arrayOfTextMetrics[k].getTextDimension();
/* 253 */         if (dimension != null) {
/* 254 */           j = Math.max(j, dimension.height);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 259 */     k = 0;
/* 260 */     int m = 2 + j + 2;
/* 261 */     int n = i;
/* 262 */     for (byte b1 = 0; b1 < i; b1++) {
/* 263 */       XMenuItemPeer xMenuItemPeer = arrayOfXMenuItemPeer1[b1];
/* 264 */       XMenuItemPeer.TextMetrics textMetrics = arrayOfTextMetrics[b1];
/* 265 */       if (textMetrics != null) {
/*     */ 
/*     */         
/* 268 */         Dimension dimension = textMetrics.getTextDimension();
/* 269 */         if (dimension != null) {
/* 270 */           int i1 = 10 + dimension.width + 10;
/*     */ 
/*     */ 
/*     */           
/* 274 */           if (k + i1 > this.width && b1 > 0) {
/* 275 */             n = b1;
/*     */             
/*     */             break;
/*     */           } 
/* 279 */           if (b1 == i - 1 && b != -1) {
/* 280 */             k = Math.max(k, this.width - i1 - 3);
/*     */           }
/* 282 */           Rectangle rectangle = new Rectangle(k, 3, i1, m);
/*     */           
/* 284 */           int i2 = (j + dimension.height) / 2 - textMetrics.getTextBaseline();
/* 285 */           Point point = new Point(k + 10, 5 + i2);
/* 286 */           k += i1 + 2;
/* 287 */           xMenuItemPeer.map(rectangle, point);
/*     */         } 
/*     */       } 
/* 290 */     }  XMenuItemPeer[] arrayOfXMenuItemPeer2 = new XMenuItemPeer[n];
/* 291 */     System.arraycopy(arrayOfXMenuItemPeer1, 0, arrayOfXMenuItemPeer2, 0, n);
/* 292 */     return new MappingData(arrayOfXMenuItemPeer2, 3 + m + 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Rectangle getSubmenuBounds(Rectangle paramRectangle, Dimension paramDimension) {
/* 300 */     Rectangle rectangle1 = toGlobal(paramRectangle);
/* 301 */     Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
/*     */     
/* 303 */     Rectangle rectangle2 = fitWindowBelow(rectangle1, paramDimension, dimension);
/* 304 */     if (rectangle2 != null) {
/* 305 */       return rectangle2;
/*     */     }
/* 307 */     rectangle2 = fitWindowAbove(rectangle1, paramDimension, dimension);
/* 308 */     if (rectangle2 != null) {
/* 309 */       return rectangle2;
/*     */     }
/* 311 */     rectangle2 = fitWindowRight(rectangle1, paramDimension, dimension);
/* 312 */     if (rectangle2 != null) {
/* 313 */       return rectangle2;
/*     */     }
/* 315 */     rectangle2 = fitWindowLeft(rectangle1, paramDimension, dimension);
/* 316 */     if (rectangle2 != null) {
/* 317 */       return rectangle2;
/*     */     }
/* 319 */     return fitWindowToScreen(paramDimension, dimension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateSize() {
/* 328 */     resetMapping();
/* 329 */     if (this.framePeer != null) {
/* 330 */       this.framePeer.reshapeMenubarPeer();
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
/*     */   int getDesiredHeight() {
/* 344 */     MappingData mappingData = (MappingData)getMappingData();
/* 345 */     return mappingData.getDesiredHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isFramePeerEnabled() {
/* 354 */     if (this.framePeer != null) {
/* 355 */       return this.framePeer.isEnabled();
/*     */     }
/* 357 */     return false;
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
/*     */   protected void doDispose() {
/* 370 */     super.doDispose();
/* 371 */     XToolkit.targetDisposedPeer(this.menuBarTarget, this);
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
/*     */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 385 */     if (paramInt3 != this.width || paramInt4 != this.height) {
/* 386 */       resetMapping();
/*     */     }
/* 388 */     super.reshape(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void ungrabInputImpl() {
/* 396 */     selectItem((XMenuItemPeer)null, false);
/* 397 */     super.ungrabInputImpl();
/* 398 */     postPaintEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintPeer(Graphics paramGraphics) {
/* 407 */     resetColors();
/*     */     
/* 409 */     int i = getWidth();
/* 410 */     int j = getHeight();
/*     */     
/* 412 */     flush();
/*     */     
/* 414 */     paramGraphics.setColor(getBackgroundColor());
/* 415 */     paramGraphics.fillRect(1, 1, i - 2, j - 2);
/*     */     
/* 417 */     draw3DRect(paramGraphics, 0, 0, i, j, true);
/*     */ 
/*     */     
/* 420 */     MappingData mappingData = (MappingData)getMappingData();
/* 421 */     XMenuItemPeer[] arrayOfXMenuItemPeer = mappingData.getItems();
/* 422 */     XMenuItemPeer xMenuItemPeer = getSelectedItem();
/* 423 */     for (byte b = 0; b < arrayOfXMenuItemPeer.length; b++) {
/* 424 */       XMenuItemPeer xMenuItemPeer1 = arrayOfXMenuItemPeer[b];
/*     */       
/* 426 */       paramGraphics.setFont(xMenuItemPeer1.getTargetFont());
/* 427 */       Rectangle rectangle = xMenuItemPeer1.getBounds();
/* 428 */       Point point = xMenuItemPeer1.getTextOrigin();
/* 429 */       if (xMenuItemPeer1 == xMenuItemPeer) {
/* 430 */         paramGraphics.setColor(getSelectedColor());
/* 431 */         paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/* 432 */         draw3DRect(paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height, false);
/*     */       } 
/* 434 */       if (isFramePeerEnabled() && xMenuItemPeer1.isTargetItemEnabled()) {
/* 435 */         paramGraphics.setColor(getForegroundColor());
/*     */       } else {
/* 437 */         paramGraphics.setColor(getDisabledColor());
/*     */       } 
/* 439 */       paramGraphics.drawString(xMenuItemPeer1.getTargetLabel(), point.x, point.y);
/*     */     } 
/* 441 */     flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void print(Graphics paramGraphics) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleEvent(AWTEvent paramAWTEvent) {
/* 459 */     if (this.framePeer != null && paramAWTEvent
/* 460 */       .getID() != 800)
/*     */     {
/* 462 */       if (this.framePeer.isModalBlocked()) {
/*     */         return;
/*     */       }
/*     */     }
/* 466 */     switch (paramAWTEvent.getID()) {
/*     */ 
/*     */       
/*     */       case 500:
/*     */       case 501:
/*     */       case 502:
/*     */       case 503:
/*     */       case 504:
/*     */       case 505:
/*     */       case 506:
/* 476 */         if (isFramePeerEnabled()) {
/* 477 */           doHandleJavaMouseEvent((MouseEvent)paramAWTEvent);
/*     */         }
/*     */         return;
/*     */ 
/*     */       
/*     */       case 401:
/*     */       case 402:
/* 484 */         if (isFramePeerEnabled()) {
/* 485 */           doHandleJavaKeyEvent((KeyEvent)paramAWTEvent);
/*     */         }
/*     */         return;
/*     */     } 
/* 489 */     super.handleEvent(paramAWTEvent);
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
/*     */   void handleF10KeyPress(KeyEvent paramKeyEvent) {
/* 507 */     int i = paramKeyEvent.getModifiers();
/* 508 */     if ((i & 0x8) != 0 || (i & 0x1) != 0 || (i & 0x2) != 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 513 */     grabInput();
/* 514 */     selectItem(getFirstSelectableItem(), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleKeyPress(XEvent paramXEvent) {
/* 523 */     XKeyEvent xKeyEvent = paramXEvent.get_xkey();
/* 524 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 525 */       log.fine(xKeyEvent.toString());
/*     */     }
/* 527 */     if (isEventDisabled(paramXEvent)) {
/*     */       return;
/*     */     }
/* 530 */     Component component = getEventSource();
/*     */ 
/*     */     
/* 533 */     handleKeyPress(xKeyEvent);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XMenuBarPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */