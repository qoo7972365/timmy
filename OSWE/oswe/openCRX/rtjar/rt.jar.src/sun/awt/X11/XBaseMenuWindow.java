/*      */ package sun.awt.X11;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.MenuItem;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.SystemColor;
/*      */ import java.awt.event.InvocationEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.PaintEvent;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Vector;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.java2d.SurfaceData;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class XBaseMenuWindow
/*      */   extends XWindow
/*      */ {
/*   52 */   private static PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XBaseMenuWindow");
/*      */ 
/*      */   
/*      */   private Color backgroundColor;
/*      */ 
/*      */   
/*      */   private Color foregroundColor;
/*      */ 
/*      */   
/*      */   private Color lightShadowColor;
/*      */ 
/*      */   
/*      */   private Color darkShadowColor;
/*      */ 
/*      */   
/*      */   private Color selectedColor;
/*      */   
/*      */   private Color disabledColor;
/*      */   
/*      */   private ArrayList<XMenuItemPeer> items;
/*      */   
/*   73 */   private int selectedIndex = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   private XMenuPeer showingSubmenu = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   92 */   private static Object menuTreeLock = new Object();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  108 */   private XMenuPeer showingMousePressedSubmenu = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  116 */   protected Point grabInputPoint = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean hasPointerMoved = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AppContext disposeAppContext;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MappingData mappingData;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class MappingData
/*      */     implements Cloneable
/*      */   {
/*      */     private XMenuItemPeer[] items;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MappingData(XMenuItemPeer[] param1ArrayOfXMenuItemPeer) {
/*  147 */       this.items = param1ArrayOfXMenuItemPeer;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MappingData() {
/*  155 */       this.items = new XMenuItemPeer[0];
/*      */     }
/*      */     
/*      */     public Object clone() {
/*      */       try {
/*  160 */         return super.clone();
/*  161 */       } catch (CloneNotSupportedException cloneNotSupportedException) {
/*  162 */         throw new InternalError(cloneNotSupportedException);
/*      */       } 
/*      */     }
/*      */     
/*      */     public XMenuItemPeer[] getItems() {
/*  167 */       return this.items;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XBaseMenuWindow() {
/*  177 */     super(new XCreateWindowParams(new Object[] { "delayed", Boolean.TRUE }));
/*      */ 
/*      */     
/*  180 */     this.disposeAppContext = AppContext.getAppContext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void instantPreInit(XCreateWindowParams paramXCreateWindowParams) {
/*  233 */     super.instantPreInit(paramXCreateWindowParams);
/*  234 */     this.items = new ArrayList<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Object getMenuTreeLock() {
/*  247 */     return menuTreeLock;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetMapping() {
/*  255 */     this.mappingData = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void postPaintEvent() {
/*  262 */     if (isShowing()) {
/*  263 */       PaintEvent paintEvent = new PaintEvent(this.target, 800, new Rectangle(0, 0, this.width, this.height));
/*      */       
/*  265 */       postEvent(paintEvent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XMenuItemPeer getItem(int paramInt) {
/*  280 */     if (paramInt >= 0) {
/*  281 */       synchronized (getMenuTreeLock()) {
/*  282 */         if (this.items.size() > paramInt) {
/*  283 */           return this.items.get(paramInt);
/*      */         }
/*      */       } 
/*      */     }
/*  287 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XMenuItemPeer[] copyItems() {
/*  294 */     synchronized (getMenuTreeLock()) {
/*  295 */       return this.items.<XMenuItemPeer>toArray(new XMenuItemPeer[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XMenuItemPeer getSelectedItem() {
/*  304 */     synchronized (getMenuTreeLock()) {
/*  305 */       if (this.selectedIndex >= 0 && 
/*  306 */         this.items.size() > this.selectedIndex) {
/*  307 */         return this.items.get(this.selectedIndex);
/*      */       }
/*      */       
/*  310 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XMenuPeer getShowingSubmenu() {
/*  318 */     synchronized (getMenuTreeLock()) {
/*  319 */       return this.showingSubmenu;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItem(MenuItem paramMenuItem) {
/*  330 */     XMenuItemPeer xMenuItemPeer = (XMenuItemPeer)paramMenuItem.getPeer();
/*  331 */     if (xMenuItemPeer != null) {
/*  332 */       xMenuItemPeer.setContainer(this);
/*  333 */       synchronized (getMenuTreeLock()) {
/*  334 */         this.items.add(xMenuItemPeer);
/*      */       }
/*      */     
/*  337 */     } else if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  338 */       log.fine("WARNING: Attempt to add menu item without a peer");
/*      */     } 
/*      */     
/*  341 */     updateSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void delItem(int paramInt) {
/*  349 */     synchronized (getMenuTreeLock()) {
/*  350 */       if (this.selectedIndex == paramInt) {
/*  351 */         selectItem((XMenuItemPeer)null, false);
/*  352 */       } else if (this.selectedIndex > paramInt) {
/*  353 */         this.selectedIndex--;
/*      */       } 
/*  355 */       if (paramInt < this.items.size()) {
/*  356 */         this.items.remove(paramInt);
/*      */       }
/*  358 */       else if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  359 */         log.fine("WARNING: Attempt to remove non-existing menu item, index : " + paramInt + ", item count : " + this.items.size());
/*      */       } 
/*      */     } 
/*      */     
/*  363 */     updateSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reloadItems(Vector paramVector) {
/*  371 */     synchronized (getMenuTreeLock()) {
/*  372 */       this.items.clear();
/*  373 */       MenuItem[] arrayOfMenuItem = (MenuItem[])paramVector.toArray((Object[])new MenuItem[0]);
/*  374 */       int i = arrayOfMenuItem.length;
/*  375 */       for (byte b = 0; b < i; b++) {
/*  376 */         addItem(arrayOfMenuItem[b]);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void selectItem(XMenuItemPeer paramXMenuItemPeer, boolean paramBoolean) {
/*  389 */     synchronized (getMenuTreeLock()) {
/*  390 */       XMenuPeer xMenuPeer1 = getShowingSubmenu();
/*  391 */       boolean bool = (paramXMenuItemPeer != null) ? this.items.indexOf(paramXMenuItemPeer) : true;
/*  392 */       if (this.selectedIndex != bool) {
/*  393 */         if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/*  394 */           log.finest("Selected index changed, was : " + this.selectedIndex + ", new : " + bool);
/*      */         }
/*  396 */         this.selectedIndex = bool;
/*  397 */         postPaintEvent();
/*      */       } 
/*  399 */       final XMenuPeer submenuToShow = (paramBoolean && paramXMenuItemPeer instanceof XMenuPeer) ? (XMenuPeer)paramXMenuItemPeer : null;
/*  400 */       if (xMenuPeer2 != xMenuPeer1) {
/*  401 */         XToolkit.executeOnEventHandlerThread(this.target, new Runnable() {
/*      */               public void run() {
/*  403 */                 XBaseMenuWindow.this.doShowSubmenu(submenuToShow);
/*      */               }
/*      */             });
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doShowSubmenu(XMenuPeer paramXMenuPeer) {
/*  418 */     XMenuWindow xMenuWindow = (paramXMenuPeer != null) ? paramXMenuPeer.getMenuWindow() : null;
/*  419 */     Dimension dimension = null;
/*  420 */     Rectangle rectangle = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  426 */     if (xMenuWindow != null) {
/*  427 */       xMenuWindow.ensureCreated();
/*      */     }
/*  429 */     XToolkit.awtLock();
/*      */     try {
/*  431 */       synchronized (getMenuTreeLock()) {
/*  432 */         if (this.showingSubmenu != paramXMenuPeer) {
/*  433 */           if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/*  434 */             log.finest("Changing showing submenu");
/*      */           }
/*  436 */           if (this.showingSubmenu != null) {
/*  437 */             XMenuWindow xMenuWindow1 = this.showingSubmenu.getMenuWindow();
/*  438 */             if (xMenuWindow1 != null) {
/*  439 */               xMenuWindow1.hide();
/*      */             }
/*      */           } 
/*  442 */           if (paramXMenuPeer != null) {
/*  443 */             dimension = xMenuWindow.getDesiredSize();
/*  444 */             rectangle = xMenuWindow.getParentMenuWindow().getSubmenuBounds(paramXMenuPeer.getBounds(), dimension);
/*  445 */             xMenuWindow.show(rectangle);
/*      */           } 
/*  447 */           this.showingSubmenu = paramXMenuPeer;
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  451 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   final void setItemsFont(Font paramFont) {
/*  456 */     XMenuItemPeer[] arrayOfXMenuItemPeer = copyItems();
/*  457 */     int i = arrayOfXMenuItemPeer.length;
/*  458 */     for (byte b = 0; b < i; b++) {
/*  459 */       arrayOfXMenuItemPeer[b].setFont(paramFont);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MappingData getMappingData() {
/*  477 */     MappingData mappingData = this.mappingData;
/*  478 */     if (mappingData == null) {
/*  479 */       mappingData = map();
/*  480 */       this.mappingData = mappingData;
/*      */     } 
/*  482 */     return (MappingData)mappingData.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XMenuItemPeer getItemFromPoint(Point paramPoint) {
/*  491 */     XMenuItemPeer[] arrayOfXMenuItemPeer = getMappingData().getItems();
/*  492 */     int i = arrayOfXMenuItemPeer.length;
/*  493 */     for (byte b = 0; b < i; b++) {
/*  494 */       if (arrayOfXMenuItemPeer[b].getBounds().contains(paramPoint)) {
/*  495 */         return arrayOfXMenuItemPeer[b];
/*      */       }
/*      */     } 
/*  498 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XMenuItemPeer getNextSelectableItem() {
/*  509 */     XMenuItemPeer[] arrayOfXMenuItemPeer = getMappingData().getItems();
/*  510 */     XMenuItemPeer xMenuItemPeer = getSelectedItem();
/*  511 */     int i = arrayOfXMenuItemPeer.length;
/*      */     
/*  513 */     byte b = -1; byte b1;
/*  514 */     for (b1 = 0; b1 < i; b1++) {
/*  515 */       if (arrayOfXMenuItemPeer[b1] == xMenuItemPeer) {
/*  516 */         b = b1;
/*      */         break;
/*      */       } 
/*      */     } 
/*  520 */     b1 = (b == i - 1) ? 0 : (b + 1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  525 */     for (byte b2 = 0; b2 < i; b2++) {
/*  526 */       XMenuItemPeer xMenuItemPeer1 = arrayOfXMenuItemPeer[b1];
/*  527 */       if (!xMenuItemPeer1.isSeparator() && xMenuItemPeer1.isTargetItemEnabled()) {
/*  528 */         return xMenuItemPeer1;
/*      */       }
/*  530 */       b1++;
/*  531 */       if (b1 >= i) {
/*  532 */         b1 = 0;
/*      */       }
/*      */     } 
/*      */     
/*  536 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XMenuItemPeer getPrevSelectableItem() {
/*  544 */     XMenuItemPeer[] arrayOfXMenuItemPeer = getMappingData().getItems();
/*  545 */     XMenuItemPeer xMenuItemPeer = getSelectedItem();
/*  546 */     int i = arrayOfXMenuItemPeer.length;
/*      */     
/*  548 */     int j = -1; int k;
/*  549 */     for (k = 0; k < i; k++) {
/*  550 */       if (arrayOfXMenuItemPeer[k] == xMenuItemPeer) {
/*  551 */         j = k;
/*      */         break;
/*      */       } 
/*      */     } 
/*  555 */     k = (j <= 0) ? (i - 1) : (j - 1);
/*      */     
/*  557 */     for (byte b = 0; b < i; b++) {
/*  558 */       XMenuItemPeer xMenuItemPeer1 = arrayOfXMenuItemPeer[k];
/*  559 */       if (!xMenuItemPeer1.isSeparator() && xMenuItemPeer1.isTargetItemEnabled()) {
/*  560 */         return xMenuItemPeer1;
/*      */       }
/*  562 */       k--;
/*  563 */       if (k < 0) {
/*  564 */         k = i - 1;
/*      */       }
/*      */     } 
/*      */     
/*  568 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XMenuItemPeer getFirstSelectableItem() {
/*  576 */     XMenuItemPeer[] arrayOfXMenuItemPeer = getMappingData().getItems();
/*  577 */     int i = arrayOfXMenuItemPeer.length;
/*  578 */     for (byte b = 0; b < i; b++) {
/*  579 */       XMenuItemPeer xMenuItemPeer = arrayOfXMenuItemPeer[b];
/*  580 */       if (!xMenuItemPeer.isSeparator() && xMenuItemPeer.isTargetItemEnabled()) {
/*  581 */         return xMenuItemPeer;
/*      */       }
/*      */     } 
/*      */     
/*  585 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XBaseMenuWindow getShowingLeaf() {
/*  600 */     synchronized (getMenuTreeLock()) {
/*  601 */       XBaseMenuWindow xBaseMenuWindow = this;
/*  602 */       XMenuPeer xMenuPeer = xBaseMenuWindow.getShowingSubmenu();
/*  603 */       while (xMenuPeer != null) {
/*  604 */         xBaseMenuWindow = xMenuPeer.getMenuWindow();
/*  605 */         xMenuPeer = xBaseMenuWindow.getShowingSubmenu();
/*      */       } 
/*  607 */       return xBaseMenuWindow;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XBaseMenuWindow getRootMenuWindow() {
/*  616 */     synchronized (getMenuTreeLock()) {
/*  617 */       XBaseMenuWindow xBaseMenuWindow1 = this;
/*  618 */       XBaseMenuWindow xBaseMenuWindow2 = xBaseMenuWindow1.getParentMenuWindow();
/*  619 */       while (xBaseMenuWindow2 != null) {
/*  620 */         xBaseMenuWindow1 = xBaseMenuWindow2;
/*  621 */         xBaseMenuWindow2 = xBaseMenuWindow1.getParentMenuWindow();
/*      */       } 
/*  623 */       return xBaseMenuWindow1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XBaseMenuWindow getMenuWindowFromPoint(Point paramPoint) {
/*  634 */     synchronized (getMenuTreeLock()) {
/*  635 */       XBaseMenuWindow xBaseMenuWindow = getShowingLeaf();
/*  636 */       while (xBaseMenuWindow != null) {
/*  637 */         Rectangle rectangle = new Rectangle(xBaseMenuWindow.toGlobal(new Point(0, 0)), xBaseMenuWindow.getSize());
/*  638 */         if (rectangle.contains(paramPoint)) {
/*  639 */           return xBaseMenuWindow;
/*      */         }
/*  641 */         xBaseMenuWindow = xBaseMenuWindow.getParentMenuWindow();
/*      */       } 
/*  643 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Rectangle fitWindowBelow(Rectangle paramRectangle, Dimension paramDimension1, Dimension paramDimension2) {
/*  667 */     int i = paramDimension1.width;
/*  668 */     int j = paramDimension1.height;
/*      */ 
/*      */ 
/*      */     
/*  672 */     int k = (paramRectangle.x > 0) ? paramRectangle.x : 0;
/*  673 */     byte b = (paramRectangle.y + paramRectangle.height > 0) ? (paramRectangle.y + paramRectangle.height) : 0;
/*  674 */     if (b + j <= paramDimension2.height) {
/*      */       
/*  676 */       if (i > paramDimension2.width) {
/*  677 */         i = paramDimension2.width;
/*      */       }
/*  679 */       if (k + i > paramDimension2.width) {
/*  680 */         k = paramDimension2.width - i;
/*      */       }
/*  682 */       return new Rectangle(k, b, i, j);
/*      */     } 
/*  684 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Rectangle fitWindowAbove(Rectangle paramRectangle, Dimension paramDimension1, Dimension paramDimension2) {
/*  696 */     int i = paramDimension1.width;
/*  697 */     int j = paramDimension1.height;
/*      */ 
/*      */ 
/*      */     
/*  701 */     int k = (paramRectangle.x > 0) ? paramRectangle.x : 0;
/*  702 */     int m = (paramRectangle.y > paramDimension2.height) ? (paramDimension2.height - j) : (paramRectangle.y - j);
/*  703 */     if (m >= 0) {
/*      */       
/*  705 */       if (i > paramDimension2.width) {
/*  706 */         i = paramDimension2.width;
/*      */       }
/*  708 */       if (k + i > paramDimension2.width) {
/*  709 */         k = paramDimension2.width - i;
/*      */       }
/*  711 */       return new Rectangle(k, m, i, j);
/*      */     } 
/*  713 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Rectangle fitWindowRight(Rectangle paramRectangle, Dimension paramDimension1, Dimension paramDimension2) {
/*  725 */     int i = paramDimension1.width;
/*  726 */     int j = paramDimension1.height;
/*      */ 
/*      */ 
/*      */     
/*  730 */     byte b = (paramRectangle.x + paramRectangle.width > 0) ? (paramRectangle.x + paramRectangle.width) : 0;
/*  731 */     int k = (paramRectangle.y > 0) ? paramRectangle.y : 0;
/*  732 */     if (b + i <= paramDimension2.width) {
/*      */       
/*  734 */       if (j > paramDimension2.height) {
/*  735 */         j = paramDimension2.height;
/*      */       }
/*  737 */       if (k + j > paramDimension2.height) {
/*  738 */         k = paramDimension2.height - j;
/*      */       }
/*  740 */       return new Rectangle(b, k, i, j);
/*      */     } 
/*  742 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Rectangle fitWindowLeft(Rectangle paramRectangle, Dimension paramDimension1, Dimension paramDimension2) {
/*  754 */     int i = paramDimension1.width;
/*  755 */     int j = paramDimension1.height;
/*      */ 
/*      */ 
/*      */     
/*  759 */     int k = (paramRectangle.x < paramDimension2.width) ? (paramRectangle.x - i) : (paramDimension2.width - i);
/*  760 */     int m = (paramRectangle.y > 0) ? paramRectangle.y : 0;
/*  761 */     if (k >= 0) {
/*      */       
/*  763 */       if (j > paramDimension2.height) {
/*  764 */         j = paramDimension2.height;
/*      */       }
/*  766 */       if (m + j > paramDimension2.height) {
/*  767 */         m = paramDimension2.height - j;
/*      */       }
/*  769 */       return new Rectangle(k, m, i, j);
/*      */     } 
/*  771 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Rectangle fitWindowToScreen(Dimension paramDimension1, Dimension paramDimension2) {
/*  783 */     int i = (paramDimension1.width < paramDimension2.width) ? paramDimension1.width : paramDimension2.width;
/*  784 */     int j = (paramDimension1.height < paramDimension2.height) ? paramDimension1.height : paramDimension2.height;
/*  785 */     return new Rectangle(0, 0, i, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void resetColors() {
/*  803 */     replaceColors((this.target == null) ? SystemColor.window : this.target.getBackground());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void replaceColors(Color paramColor) {
/*  813 */     if (paramColor != this.backgroundColor) {
/*  814 */       this.backgroundColor = paramColor;
/*      */       
/*  816 */       int i = paramColor.getRed();
/*  817 */       int j = paramColor.getGreen();
/*  818 */       int k = paramColor.getBlue();
/*      */       
/*  820 */       this.foregroundColor = new Color(MotifColorUtilities.calculateForegroundFromBackground(i, j, k));
/*  821 */       this.lightShadowColor = new Color(MotifColorUtilities.calculateTopShadowFromBackground(i, j, k));
/*  822 */       this.darkShadowColor = new Color(MotifColorUtilities.calculateBottomShadowFromBackground(i, j, k));
/*  823 */       this.selectedColor = new Color(MotifColorUtilities.calculateSelectFromBackground(i, j, k));
/*  824 */       this.disabledColor = paramColor.equals(Color.BLACK) ? this.foregroundColor.darker() : paramColor.darker();
/*      */     } 
/*      */   }
/*      */   
/*      */   Color getBackgroundColor() {
/*  829 */     return this.backgroundColor;
/*      */   }
/*      */   
/*      */   Color getForegroundColor() {
/*  833 */     return this.foregroundColor;
/*      */   }
/*      */   
/*      */   Color getLightShadowColor() {
/*  837 */     return this.lightShadowColor;
/*      */   }
/*      */   
/*      */   Color getDarkShadowColor() {
/*  841 */     return this.darkShadowColor;
/*      */   }
/*      */   
/*      */   Color getSelectedColor() {
/*  845 */     return this.selectedColor;
/*      */   }
/*      */   
/*      */   Color getDisabledColor() {
/*  849 */     return this.disabledColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void draw3DRect(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*  868 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/*      */       return;
/*      */     }
/*  871 */     Color color = paramGraphics.getColor();
/*  872 */     paramGraphics.setColor(paramBoolean ? getLightShadowColor() : getDarkShadowColor());
/*  873 */     paramGraphics.drawLine(paramInt1, paramInt2, paramInt1, paramInt2 + paramInt4 - 1);
/*  874 */     paramGraphics.drawLine(paramInt1 + 1, paramInt2, paramInt1 + paramInt3 - 1, paramInt2);
/*  875 */     paramGraphics.setColor(paramBoolean ? getDarkShadowColor() : getLightShadowColor());
/*  876 */     paramGraphics.drawLine(paramInt1 + 1, paramInt2 + paramInt4 - 1, paramInt1 + paramInt3 - 1, paramInt2 + paramInt4 - 1);
/*  877 */     paramGraphics.drawLine(paramInt1 + paramInt3 - 1, paramInt2 + 1, paramInt1 + paramInt3 - 1, paramInt2 + paramInt4 - 1);
/*  878 */     paramGraphics.setColor(color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isEventDisabled(XEvent paramXEvent) {
/*  891 */     switch (paramXEvent.get_type()) {
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 12:
/*      */       case 13:
/*      */       case 17:
/*  900 */         return super.isEventDisabled(paramXEvent);
/*      */     } 
/*  902 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispose() {
/*  910 */     setDisposed(true);
/*      */     
/*  912 */     SunToolkit.invokeLaterOnAppContext(this.disposeAppContext, new Runnable() {
/*      */           public void run() {
/*  914 */             XBaseMenuWindow.this.doDispose();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doDispose() {
/*  924 */     xSetVisible(false);
/*  925 */     SurfaceData surfaceData = this.surfaceData;
/*  926 */     this.surfaceData = null;
/*  927 */     if (surfaceData != null) {
/*  928 */       surfaceData.invalidate();
/*      */     }
/*  930 */     destroy();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void postEvent(final AWTEvent event) {
/*  940 */     InvocationEvent invocationEvent = new InvocationEvent(event.getSource(), new Runnable() {
/*      */           public void run() {
/*  942 */             XBaseMenuWindow.this.handleEvent(event);
/*      */           }
/*      */         });
/*  945 */     super.postEvent(invocationEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleEvent(AWTEvent paramAWTEvent) {
/*  954 */     switch (paramAWTEvent.getID()) {
/*      */       case 800:
/*  956 */         doHandleJavaPaintEvent((PaintEvent)paramAWTEvent);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean grabInput() {
/*      */     int i, j;
/*      */     boolean bool;
/*  969 */     XToolkit.awtLock();
/*      */     try {
/*  971 */       long l = XlibWrapper.RootWindow(XToolkit.getDisplay(), 
/*  972 */           getScreenNumber());
/*  973 */       bool = XlibWrapper.XQueryPointer(XToolkit.getDisplay(), l, XlibWrapper.larg1, XlibWrapper.larg2, XlibWrapper.larg3, XlibWrapper.larg4, XlibWrapper.larg5, XlibWrapper.larg6, XlibWrapper.larg7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  981 */       i = Native.getInt(XlibWrapper.larg3);
/*  982 */       j = Native.getInt(XlibWrapper.larg4);
/*  983 */       bool &= super.grabInput();
/*      */     } finally {
/*  985 */       XToolkit.awtUnlock();
/*      */     } 
/*  987 */     if (bool) {
/*      */       
/*  989 */       this.grabInputPoint = new Point(i, j);
/*  990 */       this.hasPointerMoved = false;
/*      */     } else {
/*  992 */       this.grabInputPoint = null;
/*  993 */       this.hasPointerMoved = true;
/*      */     } 
/*  995 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void doHandleJavaPaintEvent(PaintEvent paramPaintEvent) {
/* 1007 */     Rectangle rectangle = paramPaintEvent.getUpdateRect();
/* 1008 */     repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void doHandleJavaMouseEvent(MouseEvent paramMouseEvent) {
/* 1024 */     if (!XToolkit.isLeftMouseButton(paramMouseEvent) && !XToolkit.isRightMouseButton(paramMouseEvent)) {
/*      */       return;
/*      */     }
/*      */     
/* 1028 */     XBaseWindow xBaseWindow = XAwtState.getGrabWindow();
/*      */     
/* 1030 */     Point point = paramMouseEvent.getLocationOnScreen();
/* 1031 */     if (!this.hasPointerMoved)
/*      */     {
/* 1033 */       if (this.grabInputPoint == null || 
/* 1034 */         Math.abs(point.x - this.grabInputPoint.x) > getMouseMovementSmudge() || 
/* 1035 */         Math.abs(point.y - this.grabInputPoint.y) > getMouseMovementSmudge()) {
/* 1036 */         this.hasPointerMoved = true;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 1041 */     XBaseMenuWindow xBaseMenuWindow1 = getMenuWindowFromPoint(point);
/*      */     
/* 1043 */     XMenuItemPeer xMenuItemPeer = (xBaseMenuWindow1 != null) ? xBaseMenuWindow1.getItemFromPoint(xBaseMenuWindow1.toLocal(point)) : null;
/*      */     
/* 1045 */     XBaseMenuWindow xBaseMenuWindow2 = getShowingLeaf();
/* 1046 */     switch (paramMouseEvent.getID()) {
/*      */ 
/*      */       
/*      */       case 501:
/* 1050 */         this.showingMousePressedSubmenu = null;
/* 1051 */         if (xBaseWindow == this && xBaseMenuWindow1 == null) {
/*      */ 
/*      */           
/* 1054 */           ungrabInput();
/*      */           break;
/*      */         } 
/* 1057 */         grabInput();
/* 1058 */         if (xMenuItemPeer != null && !xMenuItemPeer.isSeparator() && xMenuItemPeer.isTargetItemEnabled()) {
/*      */           
/* 1060 */           if (xBaseMenuWindow1.getShowingSubmenu() == xMenuItemPeer)
/*      */           {
/*      */ 
/*      */             
/* 1064 */             this.showingMousePressedSubmenu = (XMenuPeer)xMenuItemPeer;
/*      */           }
/* 1066 */           xBaseMenuWindow1.selectItem(xMenuItemPeer, true);
/*      */           break;
/*      */         } 
/* 1069 */         if (xBaseMenuWindow1 != null) {
/* 1070 */           xBaseMenuWindow1.selectItem((XMenuItemPeer)null, false);
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 502:
/* 1077 */         if (xMenuItemPeer != null && !xMenuItemPeer.isSeparator() && xMenuItemPeer.isTargetItemEnabled()) {
/* 1078 */           if (xMenuItemPeer instanceof XMenuPeer) {
/* 1079 */             if (this.showingMousePressedSubmenu == xMenuItemPeer)
/*      */             {
/*      */               
/* 1082 */               if (xBaseMenuWindow1 instanceof XMenuBarPeer) {
/* 1083 */                 ungrabInput();
/*      */               } else {
/* 1085 */                 xBaseMenuWindow1.selectItem(xMenuItemPeer, false);
/*      */               } 
/*      */             }
/*      */           } else {
/*      */             
/* 1090 */             xMenuItemPeer.action(paramMouseEvent.getWhen());
/* 1091 */             ungrabInput();
/*      */           }
/*      */         
/*      */         }
/* 1095 */         else if (this.hasPointerMoved || xBaseMenuWindow1 instanceof XMenuBarPeer) {
/* 1096 */           ungrabInput();
/*      */         } 
/*      */         
/* 1099 */         this.showingMousePressedSubmenu = null;
/*      */         break;
/*      */       case 506:
/* 1102 */         if (xBaseMenuWindow1 != null) {
/*      */ 
/*      */           
/* 1105 */           if (xMenuItemPeer != null && !xMenuItemPeer.isSeparator() && xMenuItemPeer.isTargetItemEnabled()) {
/* 1106 */             if (xBaseWindow == this)
/* 1107 */               xBaseMenuWindow1.selectItem(xMenuItemPeer, true); 
/*      */             break;
/*      */           } 
/* 1110 */           xBaseMenuWindow1.selectItem((XMenuItemPeer)null, false);
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/* 1115 */         if (xBaseMenuWindow2 != null) {
/* 1116 */           xBaseMenuWindow2.selectItem((XMenuItemPeer)null, false);
/*      */         }
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void doHandleJavaKeyEvent(KeyEvent paramKeyEvent) {
/*      */     XBaseMenuWindow xBaseMenuWindow2;
/* 1130 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 1131 */       log.finer(paramKeyEvent.toString());
/*      */     }
/* 1133 */     if (paramKeyEvent.getID() != 401) {
/*      */       return;
/*      */     }
/* 1136 */     int i = paramKeyEvent.getKeyCode();
/* 1137 */     XBaseMenuWindow xBaseMenuWindow1 = getShowingLeaf();
/* 1138 */     XMenuItemPeer xMenuItemPeer = xBaseMenuWindow1.getSelectedItem();
/* 1139 */     switch (i) {
/*      */       case 38:
/*      */       case 224:
/* 1142 */         if (!(xBaseMenuWindow1 instanceof XMenuBarPeer))
/*      */         {
/*      */           
/* 1145 */           xBaseMenuWindow1.selectItem(xBaseMenuWindow1.getPrevSelectableItem(), false);
/*      */         }
/*      */         break;
/*      */       case 40:
/*      */       case 225:
/* 1150 */         if (xBaseMenuWindow1 instanceof XMenuBarPeer) {
/*      */           
/* 1152 */           selectItem(getSelectedItem(), true);
/*      */           break;
/*      */         } 
/* 1155 */         xBaseMenuWindow1.selectItem(xBaseMenuWindow1.getNextSelectableItem(), false);
/*      */         break;
/*      */       
/*      */       case 37:
/*      */       case 226:
/* 1160 */         if (xBaseMenuWindow1 instanceof XMenuBarPeer) {
/*      */ 
/*      */           
/* 1163 */           selectItem(getPrevSelectableItem(), false); break;
/* 1164 */         }  if (xBaseMenuWindow1.getParentMenuWindow() instanceof XMenuBarPeer) {
/*      */ 
/*      */ 
/*      */           
/* 1168 */           selectItem(getPrevSelectableItem(), true);
/*      */           
/*      */           break;
/*      */         } 
/* 1172 */         xBaseMenuWindow2 = xBaseMenuWindow1.getParentMenuWindow();
/*      */         
/* 1174 */         if (xBaseMenuWindow2 != null) {
/* 1175 */           xBaseMenuWindow2.selectItem(xBaseMenuWindow2.getSelectedItem(), false);
/*      */         }
/*      */         break;
/*      */       
/*      */       case 39:
/*      */       case 227:
/* 1181 */         if (xBaseMenuWindow1 instanceof XMenuBarPeer) {
/*      */ 
/*      */           
/* 1184 */           selectItem(getNextSelectableItem(), false); break;
/* 1185 */         }  if (xMenuItemPeer instanceof XMenuPeer) {
/*      */ 
/*      */           
/* 1188 */           xBaseMenuWindow1.selectItem(xMenuItemPeer, true); break;
/* 1189 */         }  if (this instanceof XMenuBarPeer)
/*      */         {
/*      */ 
/*      */           
/* 1193 */           selectItem(getNextSelectableItem(), true);
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case 10:
/*      */       case 32:
/* 1200 */         if (xMenuItemPeer instanceof XMenuPeer) {
/* 1201 */           xBaseMenuWindow1.selectItem(xMenuItemPeer, true); break;
/* 1202 */         }  if (xMenuItemPeer != null) {
/* 1203 */           xMenuItemPeer.action(paramKeyEvent.getWhen());
/* 1204 */           ungrabInput();
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 27:
/* 1214 */         if (xBaseMenuWindow1 instanceof XMenuBarPeer || xBaseMenuWindow1.getParentMenuWindow() instanceof XMenuBarPeer) {
/* 1215 */           ungrabInput(); break;
/* 1216 */         }  if (xBaseMenuWindow1 instanceof XPopupMenuPeer) {
/* 1217 */           ungrabInput(); break;
/*      */         } 
/* 1219 */         xBaseMenuWindow2 = xBaseMenuWindow1.getParentMenuWindow();
/* 1220 */         xBaseMenuWindow2.selectItem(xBaseMenuWindow2.getSelectedItem(), false);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 121:
/* 1226 */         ungrabInput();
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected abstract XBaseMenuWindow getParentMenuWindow();
/*      */   
/*      */   protected abstract MappingData map();
/*      */   
/*      */   protected abstract Rectangle getSubmenuBounds(Rectangle paramRectangle, Dimension paramDimension);
/*      */   
/*      */   protected abstract void updateSize();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XBaseMenuWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */