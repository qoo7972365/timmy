/*      */ package sun.awt.X11;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.List;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.SystemColor;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.InvocationEvent;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseWheelEvent;
/*      */ import java.awt.image.VolatileImage;
/*      */ import java.awt.peer.ListPeer;
/*      */ import java.util.Objects;
/*      */ import java.util.Vector;
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
/*      */ class XListPeer
/*      */   extends XComponentPeer
/*      */   implements ListPeer, XScrollbarClient
/*      */ {
/*   43 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XListPeer");
/*      */   
/*      */   public static final int MARGIN = 2;
/*      */   
/*      */   public static final int SPACE = 1;
/*      */   
/*      */   public static final int SCROLLBAR_AREA = 17;
/*      */   
/*      */   public static final int SCROLLBAR_WIDTH = 13;
/*      */   
/*      */   public static final int NONE = -1;
/*      */   
/*      */   public static final int WINDOW = 0;
/*      */   
/*      */   public static final int VERSCROLLBAR = 1;
/*      */   
/*      */   public static final int HORSCROLLBAR = 2;
/*      */   public static final int DEFAULT_VISIBLE_ROWS = 4;
/*      */   public static final int HORIZ_SCROLL_AMT = 10;
/*      */   private static final int PAINT_VSCROLL = 2;
/*      */   private static final int PAINT_HSCROLL = 4;
/*      */   private static final int PAINT_ITEMS = 8;
/*      */   private static final int PAINT_FOCUS = 16;
/*      */   private static final int PAINT_BACKGROUND = 32;
/*      */   private static final int PAINT_HIDEFOCUS = 64;
/*      */   private static final int PAINT_ALL = 62;
/*      */   private static final int COPY_AREA = 128;
/*      */   XVerticalScrollbar vsb;
/*      */   XHorizontalScrollbar hsb;
/*      */   ListPainter painter;
/*      */   Vector items;
/*      */   boolean multipleSelections;
/*   75 */   int active = -1;
/*      */ 
/*      */   
/*      */   int[] selected;
/*      */ 
/*      */   
/*      */   int fontHeight;
/*      */   
/*      */   int fontAscent;
/*      */   
/*      */   int fontLeading;
/*      */   
/*   87 */   int currentIndex = -1;
/*      */ 
/*      */ 
/*      */   
/*   91 */   int eventIndex = -1;
/*   92 */   int eventType = -1;
/*      */   
/*      */   int focusIndex;
/*      */   
/*      */   int maxLength;
/*      */   
/*      */   boolean vsbVis;
/*      */   
/*      */   boolean hsbVis;
/*      */   
/*      */   int listWidth;
/*      */   
/*      */   int listHeight;
/*      */   
/*  106 */   private int firstTimeVisibleIndex = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean bgColorSet;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean fgColorSet;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean mouseDraggedOutHorizontally = false;
/*      */ 
/*      */   
/*      */   boolean mouseDraggedOutVertically = false;
/*      */ 
/*      */   
/*      */   boolean isScrollBarOriginated = false;
/*      */ 
/*      */   
/*      */   boolean isMousePressed = false;
/*      */ 
/*      */ 
/*      */   
/*      */   XListPeer(List paramList) {
/*  132 */     super(paramList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  139 */     super.preInit(paramXCreateWindowParams);
/*      */ 
/*      */     
/*  142 */     this.items = new Vector();
/*  143 */     createVerScrollbar();
/*  144 */     createHorScrollbar();
/*      */     
/*  146 */     this.painter = new ListPainter();
/*      */ 
/*      */     
/*  149 */     this.bgColorSet = this.target.isBackgroundSet();
/*  150 */     this.fgColorSet = this.target.isForegroundSet();
/*      */   }
/*      */   
/*      */   public void postInit(XCreateWindowParams paramXCreateWindowParams) {
/*  154 */     super.postInit(paramXCreateWindowParams);
/*  155 */     initFontMetrics();
/*      */ 
/*      */ 
/*      */     
/*  159 */     List list = (List)this.target;
/*  160 */     int i = list.getItemCount(); int j;
/*  161 */     for (j = 0; j < i; j++) {
/*  162 */       this.items.addElement(list.getItem(j));
/*      */     }
/*      */ 
/*      */     
/*  166 */     j = list.getVisibleIndex();
/*  167 */     if (j >= 0)
/*      */     {
/*      */       
/*  170 */       this.vsb.setValues(j, 0, 0, this.items.size());
/*      */     }
/*      */ 
/*      */     
/*  174 */     this.maxLength = maxLength();
/*      */ 
/*      */     
/*  177 */     int[] arrayOfInt = list.getSelectedIndexes();
/*  178 */     this.selected = new int[arrayOfInt.length];
/*      */     
/*  180 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/*  181 */       this.selected[b] = arrayOfInt[b];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  187 */     if (arrayOfInt.length > 0) {
/*  188 */       setFocusIndex(arrayOfInt[arrayOfInt.length - 1]);
/*      */     } else {
/*      */       
/*  191 */       setFocusIndex(0);
/*      */     } 
/*      */     
/*  194 */     this.multipleSelections = list.isMultipleMode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void createVerScrollbar() {
/*  202 */     this.vsb = new XVerticalScrollbar(this);
/*  203 */     this.vsb.setValues(0, 0, 0, 0, 1, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void createHorScrollbar() {
/*  211 */     this.hsb = new XHorizontalScrollbar(this);
/*  212 */     this.hsb.setValues(0, 0, 0, 0, 10, 10);
/*      */   }
/*      */ 
/*      */   
/*      */   public void add(String paramString, int paramInt) {
/*  217 */     addItem(paramString, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeAll() {
/*  222 */     clear();
/*  223 */     this.maxLength = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMultipleMode(boolean paramBoolean) {
/*  228 */     setMultipleSelections(paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(int paramInt) {
/*  233 */     return preferredSize(paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize(int paramInt) {
/*  238 */     return minimumSize(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension minimumSize() {
/*  245 */     return minimumSize(4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension preferredSize(int paramInt) {
/*  252 */     return minimumSize(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension minimumSize(int paramInt) {
/*  259 */     FontMetrics fontMetrics = getFontMetrics(getFont());
/*  260 */     initFontMetrics();
/*  261 */     return new Dimension(20 + fontMetrics.stringWidth("0123456789abcde"), 
/*  262 */         getItemHeight() * paramInt + 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void initFontMetrics() {
/*  269 */     FontMetrics fontMetrics = getFontMetrics(getFont());
/*  270 */     this.fontHeight = fontMetrics.getHeight();
/*  271 */     this.fontAscent = fontMetrics.getAscent();
/*  272 */     this.fontLeading = fontMetrics.getLeading();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int maxLength() {
/*  280 */     FontMetrics fontMetrics = getFontMetrics(getFont());
/*  281 */     int i = 0;
/*  282 */     int j = this.items.size();
/*  283 */     for (byte b = 0; b < j; b++) {
/*  284 */       int k = fontMetrics.stringWidth(this.items.elementAt(b));
/*  285 */       i = Math.max(i, k);
/*      */     } 
/*  287 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getItemWidth(int paramInt) {
/*  294 */     FontMetrics fontMetrics = getFontMetrics(getFont());
/*  295 */     return fontMetrics.stringWidth(this.items.elementAt(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int stringLength(String paramString) {
/*  302 */     FontMetrics fontMetrics = getFontMetrics(this.target.getFont());
/*  303 */     return fontMetrics.stringWidth(paramString);
/*      */   }
/*      */   
/*      */   public void setForeground(Color paramColor) {
/*  307 */     this.fgColorSet = true;
/*  308 */     super.setForeground(paramColor);
/*      */   }
/*      */   
/*      */   public void setBackground(Color paramColor) {
/*  312 */     this.bgColorSet = true;
/*  313 */     super.setBackground(paramColor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Color getListBackground(Color[] paramArrayOfColor) {
/*  323 */     if (this.bgColorSet) {
/*  324 */       return paramArrayOfColor[0];
/*      */     }
/*      */     
/*  327 */     return SystemColor.text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Color getListForeground(Color[] paramArrayOfColor) {
/*  335 */     if (this.fgColorSet) {
/*  336 */       return paramArrayOfColor[3];
/*      */     }
/*      */     
/*  339 */     return SystemColor.textText;
/*      */   }
/*      */ 
/*      */   
/*      */   Rectangle getVScrollBarRec() {
/*  344 */     return new Rectangle(this.width - 13, 0, 14, this.height);
/*      */   }
/*      */   
/*      */   Rectangle getHScrollBarRec() {
/*  348 */     return new Rectangle(0, this.height - 13, this.width, 13);
/*      */   }
/*      */   
/*      */   int getFirstVisibleItem() {
/*  352 */     if (this.vsbVis) {
/*  353 */       return this.vsb.getValue();
/*      */     }
/*  355 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   int getLastVisibleItem() {
/*  360 */     if (this.vsbVis) {
/*  361 */       return Math.min(this.items.size() - 1, this.vsb.getValue() + itemsInWindow() - 1);
/*      */     }
/*  363 */     return Math.min(this.items.size() - 1, itemsInWindow() - 1);
/*      */   }
/*      */   
/*      */   public void repaintScrollbarRequest(XScrollbar paramXScrollbar) {
/*  367 */     if (paramXScrollbar == this.hsb) {
/*  368 */       repaint(4);
/*      */     }
/*  370 */     else if (paramXScrollbar == this.vsb) {
/*  371 */       repaint(2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void repaint() {
/*  378 */     repaint(getFirstVisibleItem(), getLastVisibleItem(), 62);
/*      */   }
/*      */   
/*      */   private void repaint(int paramInt) {
/*  382 */     repaint(getFirstVisibleItem(), getLastVisibleItem(), paramInt);
/*      */   }
/*      */   
/*      */   private void repaint(int paramInt1, int paramInt2, int paramInt3) {
/*  386 */     repaint(paramInt1, paramInt2, paramInt3, (Rectangle)null, (Point)null);
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
/*      */   private void repaint(int paramInt1, int paramInt2, int paramInt3, Rectangle paramRectangle, Point paramPoint) {
/*  408 */     Graphics graphics = getGraphics();
/*  409 */     if (graphics != null) {
/*      */       try {
/*  411 */         this.painter.paint(graphics, paramInt1, paramInt2, paramInt3, paramRectangle, paramPoint);
/*  412 */         postPaintEvent(this.target, 0, 0, getWidth(), getHeight());
/*      */       } finally {
/*  414 */         graphics.dispose();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   void paintPeer(Graphics paramGraphics) {
/*  420 */     this.painter.paint(paramGraphics, getFirstVisibleItem(), getLastVisibleItem(), 62);
/*      */   } public boolean isFocusable() {
/*  422 */     return true;
/*      */   }
/*      */   
/*      */   public void focusGained(FocusEvent paramFocusEvent) {
/*  426 */     super.focusGained(paramFocusEvent);
/*  427 */     repaint(16);
/*      */   }
/*      */   
/*      */   public void focusLost(FocusEvent paramFocusEvent) {
/*  431 */     super.focusLost(paramFocusEvent);
/*  432 */     repaint(16);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void layout() {
/*  443 */     assert this.target != null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  452 */     int k = this.vsb.getValue();
/*  453 */     int i = itemsInWindow(false);
/*  454 */     int j = (this.items.size() < i) ? i : this.items.size();
/*  455 */     this.vsb.setValues(this.vsb.getValue(), i, this.vsb.getMinimum(), j);
/*  456 */     boolean bool = vsbIsVisible(false);
/*  457 */     this.listHeight = this.height;
/*      */ 
/*      */     
/*  460 */     this.listWidth = getListWidth();
/*  461 */     i = this.listWidth - 6;
/*  462 */     j = (this.maxLength < i) ? i : this.maxLength;
/*  463 */     this.hsb.setValues(this.hsb.getValue(), i, this.hsb.getMinimum(), j);
/*  464 */     this.hsbVis = hsbIsVisible(this.vsbVis);
/*      */     
/*  466 */     if (this.hsbVis) {
/*      */ 
/*      */       
/*  469 */       this.listHeight = this.height - 17;
/*  470 */       i = itemsInWindow(true);
/*  471 */       j = (this.items.size() < i) ? i : this.items.size();
/*  472 */       this.vsb.setValues(k, i, this.vsb.getMinimum(), j);
/*  473 */       this.vsbVis = vsbIsVisible(true);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  479 */     if (bool != this.vsbVis) {
/*  480 */       this.listWidth = getListWidth();
/*  481 */       i = this.listWidth - 6;
/*  482 */       j = (this.maxLength < i) ? 0 : this.maxLength;
/*  483 */       this.hsb.setValues(this.hsb.getValue(), i, this.hsb.getMinimum(), j);
/*  484 */       this.hsbVis = hsbIsVisible(this.vsbVis);
/*      */     } 
/*      */     
/*  487 */     this.vsb.setSize(13, this.listHeight);
/*  488 */     this.hsb.setSize(this.listWidth, 13);
/*      */     
/*  490 */     this.vsb.setBlockIncrement(itemsInWindow());
/*  491 */     this.hsb.setBlockIncrement(this.width - 6 + (this.vsbVis ? 17 : 0));
/*      */   }
/*      */   
/*      */   int getItemWidth() {
/*  495 */     return this.width - 4 + (this.vsbVis ? 17 : 0);
/*      */   }
/*      */ 
/*      */   
/*      */   int getItemHeight() {
/*  500 */     return this.fontHeight - this.fontLeading + 2;
/*      */   }
/*      */   
/*      */   int getItemX() {
/*  504 */     return 3;
/*      */   }
/*      */   
/*      */   int getItemY(int paramInt) {
/*  508 */     return index2y(paramInt);
/*      */   }
/*      */   
/*      */   int getFocusIndex() {
/*  512 */     return this.focusIndex;
/*      */   }
/*      */   
/*      */   void setFocusIndex(int paramInt) {
/*  516 */     this.focusIndex = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Rectangle getFocusRect() {
/*  526 */     Rectangle rectangle = new Rectangle();
/*      */     
/*  528 */     rectangle.x = 1;
/*  529 */     rectangle.width = getListWidth() - 3;
/*      */ 
/*      */     
/*  532 */     if (isIndexDisplayed(getFocusIndex())) {
/*      */       
/*  534 */       rectangle.y = index2y(getFocusIndex()) - 2;
/*  535 */       rectangle.height = getItemHeight() + 1;
/*      */     } else {
/*      */       
/*  538 */       rectangle.y = 1;
/*  539 */       rectangle.height = this.hsbVis ? (this.height - 17) : this.height;
/*  540 */       rectangle.height -= 3;
/*      */     } 
/*  542 */     return rectangle;
/*      */   }
/*      */   
/*      */   public void handleConfigureNotifyEvent(XEvent paramXEvent) {
/*  546 */     super.handleConfigureNotifyEvent(paramXEvent);
/*      */ 
/*      */     
/*  549 */     this.painter.invalidate();
/*      */   } public boolean handlesWheelScrolling() {
/*  551 */     return true;
/*      */   }
/*      */   
/*      */   void handleJavaMouseEvent(MouseEvent paramMouseEvent) {
/*  555 */     super.handleJavaMouseEvent(paramMouseEvent);
/*  556 */     int i = paramMouseEvent.getID();
/*  557 */     switch (i) {
/*      */       case 501:
/*  559 */         mousePressed(paramMouseEvent);
/*      */         break;
/*      */       case 502:
/*  562 */         mouseReleased(paramMouseEvent);
/*      */         break;
/*      */       case 506:
/*  565 */         mouseDragged(paramMouseEvent);
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   void handleJavaMouseWheelEvent(MouseWheelEvent paramMouseWheelEvent) {
/*  571 */     if (ListHelper.doWheelScroll(this.vsbVis ? this.vsb : null, this.hsbVis ? this.hsb : null, paramMouseWheelEvent))
/*      */     {
/*  573 */       repaint();
/*      */     }
/*      */   }
/*      */   
/*      */   void mousePressed(MouseEvent paramMouseEvent) {
/*  578 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  579 */       log.finer(paramMouseEvent.toString() + ", hsb " + this.hsbVis + ", vsb " + this.vsbVis);
/*      */     }
/*  581 */     if (isEnabled() && paramMouseEvent.getButton() == 1) {
/*  582 */       if (inWindow(paramMouseEvent.getX(), paramMouseEvent.getY())) {
/*  583 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  584 */           log.fine("Mouse press in items area");
/*      */         }
/*  586 */         this.active = 0;
/*  587 */         int i = y2index(paramMouseEvent.getY());
/*  588 */         if (i >= 0) {
/*  589 */           if (this.multipleSelections) {
/*  590 */             if (isSelected(i)) {
/*      */               
/*  592 */               deselectItem(i);
/*  593 */               this.eventIndex = i;
/*  594 */               this.eventType = 2;
/*      */             } else {
/*      */               
/*  597 */               selectItem(i);
/*  598 */               this.eventIndex = i;
/*  599 */               this.eventType = 1;
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/*  608 */             selectItem(i);
/*  609 */             this.eventIndex = i;
/*  610 */             this.eventType = 1;
/*      */           } 
/*      */ 
/*      */           
/*  614 */           setFocusIndex(i);
/*  615 */           repaint(16);
/*      */         }
/*      */         else {
/*      */           
/*  619 */           this.currentIndex = -1;
/*      */         } 
/*  621 */       } else if (inVerticalScrollbar(paramMouseEvent.getX(), paramMouseEvent.getY())) {
/*  622 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  623 */           log.fine("Mouse press in vertical scrollbar");
/*      */         }
/*  625 */         this.active = 1;
/*  626 */         this.vsb.handleMouseEvent(paramMouseEvent.getID(), paramMouseEvent
/*  627 */             .getModifiers(), paramMouseEvent
/*  628 */             .getX() - this.width - 13, paramMouseEvent
/*  629 */             .getY());
/*  630 */       } else if (inHorizontalScrollbar(paramMouseEvent.getX(), paramMouseEvent.getY())) {
/*  631 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  632 */           log.fine("Mouse press in horizontal scrollbar");
/*      */         }
/*  634 */         this.active = 2;
/*  635 */         this.hsb.handleMouseEvent(paramMouseEvent.getID(), paramMouseEvent
/*  636 */             .getModifiers(), paramMouseEvent
/*  637 */             .getX(), paramMouseEvent
/*  638 */             .getY() - this.height - 13);
/*      */       } 
/*      */       
/*  641 */       this.isMousePressed = true;
/*      */     } 
/*      */   }
/*      */   void mouseReleased(MouseEvent paramMouseEvent) {
/*  645 */     if (isEnabled() && paramMouseEvent.getButton() == 1) {
/*      */       
/*  647 */       int i = paramMouseEvent.getClickCount();
/*  648 */       if (this.active == 1) {
/*  649 */         this.vsb.handleMouseEvent(paramMouseEvent.getID(), paramMouseEvent
/*  650 */             .getModifiers(), paramMouseEvent
/*  651 */             .getX() - this.width - 13, paramMouseEvent
/*  652 */             .getY());
/*  653 */       } else if (this.active == 2) {
/*  654 */         this.hsb.handleMouseEvent(paramMouseEvent.getID(), paramMouseEvent
/*  655 */             .getModifiers(), paramMouseEvent
/*  656 */             .getX(), paramMouseEvent
/*  657 */             .getY() - this.height - 13);
/*  658 */       } else if (this.currentIndex >= 0 && i >= 2 && i % 2 == 0) {
/*      */         
/*  660 */         postEvent(new ActionEvent(this.target, 1001, this.items
/*      */               
/*  662 */               .elementAt(this.currentIndex), paramMouseEvent
/*  663 */               .getWhen(), paramMouseEvent
/*  664 */               .getModifiers()));
/*  665 */       } else if (this.active == 0) {
/*      */         
/*  667 */         trackMouseReleasedScroll();
/*      */         
/*  669 */         if (this.eventType == 2) {
/*  670 */           assert this.multipleSelections : "Shouldn't get a deselect for a single-select List";
/*      */           
/*  672 */           deselectItem(this.eventIndex);
/*      */         } 
/*  674 */         if (this.eventType != -1) {
/*  675 */           postEvent(new ItemEvent((List)this.target, 701, 
/*      */                 
/*  677 */                 Integer.valueOf(this.eventIndex), this.eventType));
/*      */         }
/*      */       } 
/*      */       
/*  681 */       this.active = -1;
/*  682 */       this.eventIndex = -1;
/*  683 */       this.eventType = -1;
/*  684 */       this.isMousePressed = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void mouseDragged(MouseEvent paramMouseEvent) {
/*  690 */     if (isEnabled() && (paramMouseEvent
/*  691 */       .getModifiersEx() & 0x400) != 0) {
/*  692 */       if (this.active == 1) {
/*  693 */         this.vsb.handleMouseEvent(paramMouseEvent.getID(), paramMouseEvent
/*  694 */             .getModifiers(), paramMouseEvent
/*  695 */             .getX() - this.width - 13, paramMouseEvent
/*  696 */             .getY());
/*  697 */       } else if (this.active == 2) {
/*  698 */         this.hsb.handleMouseEvent(paramMouseEvent.getID(), paramMouseEvent
/*  699 */             .getModifiers(), paramMouseEvent
/*  700 */             .getX(), paramMouseEvent
/*  701 */             .getY() - this.height - 13);
/*  702 */       } else if (this.active == 0) {
/*  703 */         int i = y2index(paramMouseEvent.getY());
/*  704 */         if (this.multipleSelections) {
/*      */ 
/*      */ 
/*      */           
/*  708 */           if (this.eventType == 2 && 
/*  709 */             i != this.eventIndex) {
/*  710 */             this.eventType = -1;
/*  711 */             this.eventIndex = -1;
/*      */           }
/*      */         
/*      */         }
/*  715 */         else if (this.eventType == 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  721 */           trackMouseDraggedScroll(paramMouseEvent);
/*      */           
/*  723 */           if (i >= 0 && !isSelected(i)) {
/*  724 */             int j = this.eventIndex;
/*  725 */             selectItem(i);
/*  726 */             this.eventIndex = i;
/*  727 */             repaint(j, this.eventIndex, 8);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  732 */         if (i >= 0) {
/*  733 */           setFocusIndex(i);
/*  734 */           repaint(16);
/*      */         } 
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
/*      */   void trackMouseDraggedScroll(MouseEvent paramMouseEvent) {
/*  748 */     if (this.vsb.beforeThumb(paramMouseEvent.getX(), paramMouseEvent.getY())) {
/*  749 */       this.vsb.setMode(2);
/*      */     } else {
/*  751 */       this.vsb.setMode(1);
/*      */     } 
/*      */     
/*  754 */     if (paramMouseEvent.getY() < 0 || paramMouseEvent.getY() >= this.listHeight) {
/*  755 */       if (!this.mouseDraggedOutVertically) {
/*  756 */         this.mouseDraggedOutVertically = true;
/*  757 */         this.vsb.startScrollingInstance();
/*      */       }
/*      */     
/*  760 */     } else if (this.mouseDraggedOutVertically) {
/*  761 */       this.mouseDraggedOutVertically = false;
/*  762 */       this.vsb.stopScrollingInstance();
/*      */     } 
/*      */ 
/*      */     
/*  766 */     if (this.hsb.beforeThumb(paramMouseEvent.getX(), paramMouseEvent.getY())) {
/*  767 */       this.hsb.setMode(2);
/*      */     } else {
/*  769 */       this.hsb.setMode(1);
/*      */     } 
/*      */     
/*  772 */     if (paramMouseEvent.getX() < 0 || paramMouseEvent.getX() >= this.listWidth) {
/*  773 */       if (!this.mouseDraggedOutHorizontally) {
/*  774 */         this.mouseDraggedOutHorizontally = true;
/*  775 */         this.hsb.startScrollingInstance();
/*      */       }
/*      */     
/*  778 */     } else if (this.mouseDraggedOutHorizontally) {
/*  779 */       this.mouseDraggedOutHorizontally = false;
/*  780 */       this.hsb.stopScrollingInstance();
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
/*      */   void trackMouseReleasedScroll() {
/*  793 */     if (this.mouseDraggedOutVertically) {
/*  794 */       this.mouseDraggedOutVertically = false;
/*  795 */       this.vsb.stopScrollingInstance();
/*      */     } 
/*      */     
/*  798 */     if (this.mouseDraggedOutHorizontally) {
/*  799 */       this.mouseDraggedOutHorizontally = false;
/*  800 */       this.hsb.stopScrollingInstance();
/*      */     } 
/*      */   }
/*      */   
/*      */   void handleJavaKeyEvent(KeyEvent paramKeyEvent) {
/*  805 */     switch (paramKeyEvent.getID()) {
/*      */       case 401:
/*  807 */         if (!this.isMousePressed)
/*  808 */           keyPressed(paramKeyEvent); 
/*      */         break;
/*      */     } 
/*      */   }
/*      */   void keyPressed(KeyEvent paramKeyEvent) {
/*      */     int j;
/*      */     boolean bool;
/*  815 */     int k, i = paramKeyEvent.getKeyCode();
/*  816 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  817 */       log.fine(paramKeyEvent.toString());
/*      */     }
/*  819 */     switch (i) {
/*      */       case 38:
/*      */       case 224:
/*  822 */         if (getFocusIndex() > 0) {
/*  823 */           setFocusIndex(getFocusIndex() - 1);
/*  824 */           repaint(64);
/*      */           
/*  826 */           if (!this.multipleSelections) {
/*  827 */             selectItem(getFocusIndex());
/*  828 */             postEvent(new ItemEvent((List)this.target, 701, 
/*      */                   
/*  830 */                   Integer.valueOf(getFocusIndex()), 1));
/*      */           } 
/*      */           
/*  833 */           if (isItemHidden(getFocusIndex())) {
/*  834 */             makeVisible(getFocusIndex());
/*      */             break;
/*      */           } 
/*  837 */           repaint(16);
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 40:
/*      */       case 225:
/*  843 */         if (getFocusIndex() < this.items.size() - 1) {
/*  844 */           setFocusIndex(getFocusIndex() + 1);
/*  845 */           repaint(64);
/*      */           
/*  847 */           if (!this.multipleSelections) {
/*  848 */             selectItem(getFocusIndex());
/*  849 */             postEvent(new ItemEvent((List)this.target, 701, 
/*      */                   
/*  851 */                   Integer.valueOf(getFocusIndex()), 1));
/*      */           } 
/*      */           
/*  854 */           if (isItemHidden(getFocusIndex())) {
/*  855 */             makeVisible(getFocusIndex());
/*      */             break;
/*      */           } 
/*  858 */           repaint(16);
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 33:
/*  864 */         j = this.vsb.getValue();
/*  865 */         this.vsb.setValue(this.vsb.getValue() - this.vsb.getBlockIncrement());
/*  866 */         k = this.vsb.getValue();
/*      */ 
/*      */         
/*  869 */         if (j != k) {
/*  870 */           setFocusIndex(Math.max(getFocusIndex() - itemsInWindow(), 0));
/*  871 */           if (!this.multipleSelections) {
/*  872 */             selectItem(getFocusIndex());
/*  873 */             postEvent(new ItemEvent((List)this.target, 701, 
/*      */                   
/*  875 */                   Integer.valueOf(getFocusIndex()), 1));
/*      */           } 
/*      */         } 
/*      */         
/*  879 */         repaint();
/*      */         break;
/*      */ 
/*      */       
/*      */       case 34:
/*  884 */         j = this.vsb.getValue();
/*  885 */         this.vsb.setValue(this.vsb.getValue() + this.vsb.getBlockIncrement());
/*  886 */         k = this.vsb.getValue();
/*      */ 
/*      */         
/*  889 */         if (j != k) {
/*  890 */           setFocusIndex(Math.min(getFocusIndex() + itemsInWindow(), this.items.size() - 1));
/*  891 */           if (!this.multipleSelections) {
/*  892 */             selectItem(getFocusIndex());
/*  893 */             postEvent(new ItemEvent((List)this.target, 701, 
/*      */                   
/*  895 */                   Integer.valueOf(getFocusIndex()), 1));
/*      */           } 
/*      */         } 
/*      */         
/*  899 */         repaint();
/*      */         break;
/*      */       
/*      */       case 37:
/*      */       case 226:
/*  904 */         if ((this.hsbVis & ((this.hsb.getValue() > 0) ? 1 : 0)) != 0) {
/*  905 */           this.hsb.setValue(this.hsb.getValue() - 10);
/*  906 */           repaint();
/*      */         } 
/*      */         break;
/*      */       case 39:
/*      */       case 227:
/*  911 */         if (this.hsbVis) {
/*  912 */           this.hsb.setValue(this.hsb.getValue() + 10);
/*  913 */           repaint();
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 36:
/*  919 */         if (!paramKeyEvent.isControlDown() || ((List)this.target).getItemCount() <= 0)
/*      */           break; 
/*  921 */         if (this.vsbVis) {
/*  922 */           this.vsb.setValue(this.vsb.getMinimum());
/*      */         }
/*  924 */         setFocusIndex(0);
/*  925 */         if (!this.multipleSelections) {
/*  926 */           selectItem(getFocusIndex());
/*  927 */           postEvent(new ItemEvent((List)this.target, 701, 
/*      */                 
/*  929 */                 Integer.valueOf(getFocusIndex()), 1));
/*      */         } 
/*      */         
/*  932 */         repaint();
/*      */         break;
/*      */       case 35:
/*  935 */         if (!paramKeyEvent.isControlDown() || ((List)this.target).getItemCount() <= 0)
/*      */           break; 
/*  937 */         if (this.vsbVis) {
/*  938 */           this.vsb.setValue(this.vsb.getMaximum());
/*      */         }
/*  940 */         setFocusIndex(this.items.size() - 1);
/*  941 */         if (!this.multipleSelections) {
/*  942 */           selectItem(getFocusIndex());
/*  943 */           postEvent(new ItemEvent((List)this.target, 701, 
/*      */                 
/*  945 */                 Integer.valueOf(getFocusIndex()), 1));
/*      */         } 
/*      */         
/*  948 */         repaint();
/*      */         break;
/*      */ 
/*      */       
/*      */       case 32:
/*  953 */         if (getFocusIndex() < 0 || ((List)this.target).getItemCount() <= 0) {
/*      */           break;
/*      */         }
/*      */         
/*  957 */         bool = isSelected(getFocusIndex());
/*      */ 
/*      */         
/*  960 */         if (this.multipleSelections && bool) {
/*  961 */           deselectItem(getFocusIndex());
/*  962 */           postEvent(new ItemEvent((List)this.target, 701, 
/*      */                 
/*  964 */                 Integer.valueOf(getFocusIndex()), 2));
/*      */           break;
/*      */         } 
/*  967 */         if (!bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  973 */           selectItem(getFocusIndex());
/*  974 */           postEvent(new ItemEvent((List)this.target, 701, 
/*      */                 
/*  976 */                 Integer.valueOf(getFocusIndex()), 1));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 10:
/*  986 */         if (this.selected.length > 0) {
/*  987 */           postEvent(new ActionEvent(this.target, 1001, this.items
/*      */                 
/*  989 */                 .elementAt(getFocusIndex()), paramKeyEvent
/*  990 */                 .getWhen(), paramKeyEvent
/*  991 */                 .getModifiers()));
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
/*      */   public void notifyValue(XScrollbar paramXScrollbar, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 1003 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 1004 */       log.fine("Notify value changed on " + paramXScrollbar + " to " + paramInt2);
/*      */     }
/* 1006 */     int i = paramXScrollbar.getValue();
/* 1007 */     if (paramXScrollbar == this.vsb) {
/* 1008 */       scrollVertical(paramInt2 - i);
/*      */ 
/*      */       
/* 1011 */       int j = this.eventIndex;
/* 1012 */       int k = this.eventIndex + paramInt2 - i;
/* 1013 */       if (this.mouseDraggedOutVertically && !isSelected(k)) {
/* 1014 */         selectItem(k);
/* 1015 */         this.eventIndex = k;
/* 1016 */         repaint(j, this.eventIndex, 8);
/*      */ 
/*      */ 
/*      */         
/* 1020 */         setFocusIndex(k);
/* 1021 */         repaint(16);
/*      */       }
/*      */     
/* 1024 */     } else if ((XHorizontalScrollbar)paramXScrollbar == this.hsb) {
/* 1025 */       scrollHorizontal(paramInt2 - i);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deselectAllItems() {
/* 1034 */     this.selected = new int[0];
/* 1035 */     repaint(8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMultipleSelections(boolean paramBoolean) {
/* 1042 */     if (this.multipleSelections != paramBoolean) {
/* 1043 */       if (!paramBoolean) {
/* 1044 */         boolean bool = isSelected(this.focusIndex) ? this.focusIndex : true;
/* 1045 */         deselectAllItems();
/* 1046 */         if (bool != -1) {
/* 1047 */           selectItem(bool);
/*      */         }
/*      */       } 
/* 1050 */       this.multipleSelections = paramBoolean;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItem(String paramString, int paramInt) {
/* 1060 */     int i = this.maxLength;
/* 1061 */     boolean bool1 = this.hsbVis;
/* 1062 */     boolean bool2 = this.vsbVis;
/*      */     
/* 1064 */     int j = 0;
/* 1065 */     if (paramInt < 0 || paramInt >= this.items.size()) {
/* 1066 */       paramInt = -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1073 */     this.currentIndex = -1;
/*      */     
/* 1075 */     if (paramInt == -1) {
/* 1076 */       this.items.addElement(paramString);
/* 1077 */       paramInt = 0;
/* 1078 */       j = this.items.size() - 1;
/*      */     } else {
/* 1080 */       this.items.insertElementAt(paramString, paramInt);
/* 1081 */       j = paramInt;
/* 1082 */       for (byte b = 0; b < this.selected.length; b++) {
/* 1083 */         if (this.selected[b] >= paramInt) {
/* 1084 */           this.selected[b] = this.selected[b] + 1;
/*      */         }
/*      */       } 
/*      */     } 
/* 1088 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 1089 */       log.finer("Adding item '" + paramString + "' to " + j);
/*      */     }
/*      */ 
/*      */     
/* 1093 */     boolean bool = !isItemHidden(j) ? true : false;
/* 1094 */     this.maxLength = Math.max(this.maxLength, getItemWidth(j));
/* 1095 */     layout();
/*      */     
/* 1097 */     int k = 0;
/* 1098 */     if (this.vsbVis != bool2 || this.hsbVis != bool1) {
/*      */       
/* 1100 */       k = 62;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1105 */       k = (bool ? 8 : 0) | ((this.maxLength != i || bool1 ^ this.hsbVis) ? 4 : 0) | (this.vsb.needsRepaint() ? 2 : 0);
/*      */     } 
/*      */     
/* 1108 */     if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1109 */       log.finest("Last visible: " + getLastVisibleItem() + ", hsb changed : " + (bool1 ^ this.hsbVis) + ", items changed " + bool);
/*      */     }
/*      */     
/* 1112 */     repaint(j, getLastVisibleItem(), k);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void delItems(int paramInt1, int paramInt2) {
/* 1122 */     boolean bool1 = this.hsbVis;
/* 1123 */     boolean bool2 = this.vsbVis;
/* 1124 */     int i = lastItemDisplayed();
/*      */     
/* 1126 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 1127 */       log.fine("Deleting from " + paramInt1 + " to " + paramInt2);
/*      */     }
/*      */     
/* 1130 */     if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1131 */       log.finest("Last displayed item: " + i + ", items in window " + itemsInWindow() + ", size " + this.items
/* 1132 */           .size());
/*      */     }
/*      */     
/* 1135 */     if (this.items.size() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1140 */     if (paramInt1 > paramInt2) {
/* 1141 */       int i1 = paramInt1;
/* 1142 */       paramInt1 = paramInt2;
/* 1143 */       paramInt2 = i1;
/*      */     } 
/*      */ 
/*      */     
/* 1147 */     if (paramInt1 < 0) {
/* 1148 */       paramInt1 = 0;
/*      */     }
/*      */ 
/*      */     
/* 1152 */     if (paramInt2 >= this.items.size()) {
/* 1153 */       paramInt2 = this.items.size() - 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1163 */     int j = (paramInt1 >= getFirstVisibleItem() && paramInt1 <= getLastVisibleItem()) ? 1 : 0;
/*      */     
/*      */     int k;
/* 1166 */     for (k = paramInt1; k <= paramInt2; k++) {
/* 1167 */       this.items.removeElementAt(paramInt1);
/* 1168 */       int i1 = posInSel(k);
/* 1169 */       if (i1 != -1) {
/* 1170 */         int[] arrayOfInt = new int[this.selected.length - 1];
/* 1171 */         System.arraycopy(this.selected, 0, arrayOfInt, 0, i1);
/* 1172 */         System.arraycopy(this.selected, i1 + 1, arrayOfInt, i1, this.selected.length - i1 + 1);
/* 1173 */         this.selected = arrayOfInt;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1179 */     k = paramInt2 - paramInt1 + 1; int m;
/* 1180 */     for (m = 0; m < this.selected.length; m++) {
/* 1181 */       if (this.selected[m] > paramInt2) {
/* 1182 */         this.selected[m] = this.selected[m] - k;
/*      */       }
/*      */     } 
/*      */     
/* 1186 */     m = 2;
/*      */     
/* 1188 */     if (getFocusIndex() > paramInt2) {
/* 1189 */       setFocusIndex(getFocusIndex() - paramInt2 - paramInt1 + 1);
/* 1190 */       m |= 0x10;
/* 1191 */     } else if (getFocusIndex() >= paramInt1 && getFocusIndex() <= paramInt2) {
/*      */ 
/*      */ 
/*      */       
/* 1195 */       boolean bool = (this.items.size() > 0) ? false : true;
/* 1196 */       setFocusIndex(Math.max(paramInt1 - 1, bool));
/* 1197 */       m |= 0x10;
/*      */     } 
/*      */     
/* 1200 */     if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1201 */       log.finest("Multiple selections: " + this.multipleSelections);
/*      */     }
/*      */ 
/*      */     
/* 1205 */     if (this.vsb.getValue() >= paramInt1) {
/* 1206 */       if (this.vsb.getValue() <= paramInt2) {
/* 1207 */         this.vsb.setValue(paramInt2 + 1 - k);
/*      */       } else {
/* 1209 */         this.vsb.setValue(this.vsb.getValue() - k);
/*      */       } 
/*      */     }
/*      */     
/* 1213 */     int n = this.maxLength;
/* 1214 */     this.maxLength = maxLength();
/* 1215 */     if (this.maxLength != n)
/*      */     {
/*      */       
/* 1218 */       m |= 0x4;
/*      */     }
/* 1220 */     layout();
/* 1221 */     j |= (bool2 ^ this.vsbVis || bool1 ^ this.hsbVis) ? 1 : 0;
/* 1222 */     if (j != 0) {
/* 1223 */       m |= 0x3E;
/*      */     }
/* 1225 */     repaint(paramInt1, i, m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void select(int paramInt) {
/* 1233 */     setFocusIndex(paramInt);
/* 1234 */     repaint(16);
/* 1235 */     selectItem(paramInt);
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
/*      */   void selectItem(int paramInt) {
/* 1249 */     this.currentIndex = paramInt;
/*      */     
/* 1251 */     if (isSelected(paramInt)) {
/*      */       return;
/*      */     }
/* 1254 */     if (!this.multipleSelections) {
/* 1255 */       if (this.selected.length == 0) {
/* 1256 */         this.selected = new int[1];
/* 1257 */         this.selected[0] = paramInt;
/*      */       } else {
/*      */         
/* 1260 */         int i = this.selected[0];
/* 1261 */         this.selected[0] = paramInt;
/* 1262 */         if (!isItemHidden(i))
/*      */         {
/* 1264 */           repaint(i, i, 8);
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/* 1269 */       int[] arrayOfInt = new int[this.selected.length + 1];
/* 1270 */       byte b = 0;
/* 1271 */       while (b < this.selected.length && paramInt > this.selected[b]) {
/* 1272 */         arrayOfInt[b] = this.selected[b];
/* 1273 */         b++;
/*      */       } 
/* 1275 */       arrayOfInt[b] = paramInt;
/* 1276 */       System.arraycopy(this.selected, b, arrayOfInt, b + 1, this.selected.length - b);
/* 1277 */       this.selected = arrayOfInt;
/*      */     } 
/* 1279 */     if (!isItemHidden(paramInt))
/*      */     {
/* 1281 */       repaint(paramInt, paramInt, 8);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deselect(int paramInt) {
/* 1290 */     deselectItem(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void deselectItem(int paramInt) {
/* 1298 */     if (!isSelected(paramInt)) {
/*      */       return;
/*      */     }
/* 1301 */     if (!this.multipleSelections) {
/*      */ 
/*      */       
/* 1304 */       this.selected = new int[0];
/*      */     } else {
/* 1306 */       int i = posInSel(paramInt);
/* 1307 */       int[] arrayOfInt = new int[this.selected.length - 1];
/* 1308 */       System.arraycopy(this.selected, 0, arrayOfInt, 0, i);
/* 1309 */       System.arraycopy(this.selected, i + 1, arrayOfInt, i, this.selected.length - i + 1);
/* 1310 */       this.selected = arrayOfInt;
/*      */     } 
/* 1312 */     this.currentIndex = paramInt;
/* 1313 */     if (!isItemHidden(paramInt))
/*      */     {
/* 1315 */       repaint(paramInt, paramInt, 8);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void makeVisible(int paramInt) {
/* 1325 */     if (paramInt < 0 || paramInt >= this.items.size()) {
/*      */       return;
/*      */     }
/* 1328 */     if (isItemHidden(paramInt))
/*      */     {
/* 1330 */       if (paramInt < this.vsb.getValue()) {
/* 1331 */         scrollVertical(paramInt - this.vsb.getValue());
/*      */       
/*      */       }
/* 1334 */       else if (paramInt > lastItemDisplayed()) {
/* 1335 */         int i = paramInt - lastItemDisplayed();
/* 1336 */         scrollVertical(i);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/* 1345 */     this.selected = new int[0];
/* 1346 */     this.items = new Vector();
/* 1347 */     this.currentIndex = -1;
/*      */ 
/*      */     
/* 1350 */     setFocusIndex(-1);
/* 1351 */     this.vsb.setValue(0);
/* 1352 */     this.maxLength = 0;
/* 1353 */     layout();
/* 1354 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getSelectedIndexes() {
/* 1361 */     return this.selected;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int index2y(int paramInt) {
/* 1371 */     int i = getItemHeight();
/*      */ 
/*      */     
/* 1374 */     return 2 + (paramInt - this.vsb.getValue()) * i + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean validY(int paramInt) {
/* 1382 */     int i = itemsDisplayed();
/* 1383 */     int j = i * getItemHeight() + 2;
/*      */     
/* 1385 */     if (i == itemsInWindow()) {
/* 1386 */       j += 2;
/*      */     }
/*      */     
/* 1389 */     if (paramInt < 0 || paramInt >= j) {
/* 1390 */       return false;
/*      */     }
/*      */     
/* 1393 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int posInSel(int paramInt) {
/* 1401 */     for (byte b = 0; b < this.selected.length; b++) {
/* 1402 */       if (paramInt == this.selected[b]) {
/* 1403 */         return b;
/*      */       }
/*      */     } 
/* 1406 */     return -1;
/*      */   }
/*      */   
/*      */   boolean isIndexDisplayed(int paramInt) {
/* 1410 */     int i = lastItemDisplayed();
/*      */     
/* 1412 */     return (paramInt <= i && paramInt >= 
/* 1413 */       Math.max(0, i - itemsInWindow() + 1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int lastItemDisplayed() {
/* 1420 */     int i = itemsInWindow();
/* 1421 */     return Math.min(this.items.size() - 1, this.vsb.getValue() + i - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isItemHidden(int paramInt) {
/* 1429 */     return (paramInt < this.vsb.getValue() || paramInt >= this.vsb
/* 1430 */       .getValue() + itemsInWindow());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getListWidth() {
/* 1438 */     return this.vsbVis ? (this.width - 17) : this.width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int itemsDisplayed() {
/* 1446 */     return Math.min(this.items.size() - this.vsb.getValue(), itemsInWindow());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void scrollVertical(int paramInt) {
/* 1455 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 1456 */       log.fine("Scrolling vertically by " + paramInt);
/*      */     }
/* 1458 */     int i = itemsInWindow();
/* 1459 */     int j = getItemHeight();
/* 1460 */     int k = paramInt * j;
/*      */     
/* 1462 */     if (this.vsb.getValue() < -paramInt) {
/* 1463 */       paramInt = -this.vsb.getValue();
/*      */     }
/* 1465 */     this.vsb.setValue(this.vsb.getValue() + paramInt);
/*      */     
/* 1467 */     Rectangle rectangle = null;
/* 1468 */     Point point = null;
/* 1469 */     int m = 0, n = 0;
/* 1470 */     int i1 = 90;
/* 1471 */     if (paramInt > 0) {
/* 1472 */       if (paramInt < i) {
/* 1473 */         rectangle = new Rectangle(2, 2 + k, this.width - 17, j * (i - paramInt - 1) - 1);
/* 1474 */         point = new Point(0, -k);
/* 1475 */         i1 |= 0x80;
/*      */       } 
/* 1477 */       m = this.vsb.getValue() + i - paramInt - 1;
/* 1478 */       n = this.vsb.getValue() + i - 1;
/*      */     }
/* 1480 */     else if (paramInt < 0) {
/* 1481 */       if (paramInt + itemsInWindow() > 0) {
/* 1482 */         rectangle = new Rectangle(2, 2, this.width - 17, j * (i + paramInt));
/* 1483 */         point = new Point(0, -k);
/* 1484 */         i1 |= 0x80;
/*      */       } 
/* 1486 */       m = this.vsb.getValue();
/* 1487 */       n = Math.min(getLastVisibleItem(), this.vsb.getValue() + -paramInt);
/*      */     } 
/* 1489 */     repaint(m, n, i1, rectangle, point);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void scrollHorizontal(int paramInt) {
/* 1497 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 1498 */       log.fine("Scrolling horizontally by " + this.y);
/*      */     }
/* 1500 */     int i = getListWidth();
/* 1501 */     i -= 6;
/* 1502 */     int j = this.height - 21;
/* 1503 */     this.hsb.setValue(this.hsb.getValue() + paramInt);
/*      */     
/* 1505 */     int k = 12;
/*      */     
/* 1507 */     Rectangle rectangle = null;
/* 1508 */     Point point = null;
/* 1509 */     if (paramInt < 0) {
/* 1510 */       rectangle = new Rectangle(3, 2, i + paramInt, j);
/* 1511 */       point = new Point(-paramInt, 0);
/* 1512 */       k |= 0x80;
/* 1513 */     } else if (paramInt > 0) {
/* 1514 */       rectangle = new Rectangle(3 + paramInt, 2, i - paramInt, j);
/* 1515 */       point = new Point(-paramInt, 0);
/* 1516 */       k |= 0x80;
/*      */     } 
/* 1518 */     repaint(this.vsb.getValue(), lastItemDisplayed(), k, rectangle, point);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int y2index(int paramInt) {
/* 1525 */     if (!validY(paramInt)) {
/* 1526 */       return -1;
/*      */     }
/*      */     
/* 1529 */     int i = (paramInt - 2) / getItemHeight() + this.vsb.getValue();
/* 1530 */     int j = lastItemDisplayed();
/*      */     
/* 1532 */     if (i > j) {
/* 1533 */       i = j;
/*      */     }
/*      */     
/* 1536 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isSelected(int paramInt) {
/* 1544 */     if (this.eventType == 1 && paramInt == this.eventIndex) {
/* 1545 */       return true;
/*      */     }
/* 1547 */     for (byte b = 0; b < this.selected.length; b++) {
/* 1548 */       if (this.selected[b] == paramInt) {
/* 1549 */         return true;
/*      */       }
/*      */     } 
/* 1552 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int itemsInWindow(boolean paramBoolean) {
/*      */     int i;
/* 1561 */     if (paramBoolean) {
/* 1562 */       i = this.height - 21;
/*      */     } else {
/* 1564 */       i = this.height - 4;
/*      */     } 
/* 1566 */     return i / getItemHeight();
/*      */   }
/*      */   
/*      */   int itemsInWindow() {
/* 1570 */     return itemsInWindow(this.hsbVis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean inHorizontalScrollbar(int paramInt1, int paramInt2) {
/* 1577 */     int i = getListWidth();
/* 1578 */     int j = this.height - 13;
/* 1579 */     return (this.hsbVis && paramInt1 >= 0 && paramInt1 <= i && paramInt2 > j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean inVerticalScrollbar(int paramInt1, int paramInt2) {
/* 1586 */     int i = this.width - 13;
/* 1587 */     int j = this.hsbVis ? (this.height - 17) : this.height;
/* 1588 */     return (this.vsbVis && paramInt1 > i && paramInt2 >= 0 && paramInt2 <= j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean inWindow(int paramInt1, int paramInt2) {
/* 1595 */     int i = getListWidth();
/* 1596 */     int j = this.hsbVis ? (this.height - 17) : this.height;
/* 1597 */     return (paramInt1 >= 0 && paramInt1 <= i && paramInt2 >= 0 && paramInt2 <= j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean vsbIsVisible(boolean paramBoolean) {
/* 1605 */     return (this.items.size() > itemsInWindow(paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean hsbIsVisible(boolean paramBoolean) {
/* 1613 */     int i = this.width - 6 + (paramBoolean ? 17 : 0);
/* 1614 */     return (this.maxLength > i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean prePostEvent(AWTEvent paramAWTEvent) {
/* 1622 */     if (paramAWTEvent instanceof MouseEvent) {
/* 1623 */       return prePostMouseEvent((MouseEvent)paramAWTEvent);
/*      */     }
/* 1625 */     return super.prePostEvent(paramAWTEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean prePostMouseEvent(MouseEvent paramMouseEvent) {
/* 1636 */     if (getToplevelXWindow().isModalBlocked()) {
/* 1637 */       return false;
/*      */     }
/*      */     
/* 1640 */     int i = paramMouseEvent.getID();
/*      */     
/* 1642 */     if (i != 503) {
/*      */ 
/*      */       
/* 1645 */       if ((i == 506 || i == 502) && this.isScrollBarOriginated) {
/*      */ 
/*      */ 
/*      */         
/* 1649 */         if (i == 502) {
/* 1650 */           this.isScrollBarOriginated = false;
/*      */         }
/* 1652 */         handleJavaMouseEventOnEDT(paramMouseEvent);
/* 1653 */         return true;
/* 1654 */       }  if ((i == 501 || i == 500) && (
/*      */         
/* 1656 */         inVerticalScrollbar(paramMouseEvent.getX(), paramMouseEvent.getY()) || 
/* 1657 */         inHorizontalScrollbar(paramMouseEvent.getX(), paramMouseEvent.getY()))) {
/*      */         
/* 1659 */         if (i == 501) {
/* 1660 */           this.isScrollBarOriginated = true;
/*      */         }
/* 1662 */         handleJavaMouseEventOnEDT(paramMouseEvent);
/* 1663 */         return true;
/*      */       } 
/* 1665 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void handleJavaMouseEventOnEDT(final MouseEvent me) {
/* 1672 */     InvocationEvent invocationEvent = new InvocationEvent(this.target, new Runnable() {
/*      */           public void run() {
/* 1674 */             XListPeer.this.handleJavaMouseEvent(me);
/*      */           }
/*      */         });
/* 1677 */     postEvent(invocationEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFont(Font paramFont) {
/* 1686 */     if (!Objects.equals(getFont(), paramFont)) {
/* 1687 */       super.setFont(paramFont);
/* 1688 */       initFontMetrics();
/* 1689 */       layout();
/* 1690 */       repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class ListPainter
/*      */   {
/*      */     VolatileImage buffer;
/*      */     
/*      */     Color[] colors;
/*      */     
/*      */     private Rectangle prevFocusRect;
/*      */ 
/*      */     
/*      */     private Color getListForeground() {
/* 1706 */       if (XListPeer.this.fgColorSet) {
/* 1707 */         return this.colors[3];
/*      */       }
/*      */       
/* 1710 */       return SystemColor.textText;
/*      */     }
/*      */     
/*      */     private Color getListBackground() {
/* 1714 */       if (XListPeer.this.bgColorSet) {
/* 1715 */         return this.colors[0];
/*      */       }
/*      */       
/* 1718 */       return SystemColor.text;
/*      */     }
/*      */ 
/*      */     
/*      */     private Color getDisabledColor() {
/* 1723 */       Color color1 = getListBackground();
/* 1724 */       Color color2 = getListForeground();
/* 1725 */       return color1.equals(Color.BLACK) ? color2.darker() : color1.darker();
/*      */     }
/*      */     
/*      */     private boolean createBuffer() {
/* 1729 */       VolatileImage volatileImage = null;
/* 1730 */       XToolkit.awtLock();
/*      */       try {
/* 1732 */         volatileImage = this.buffer;
/*      */       } finally {
/* 1734 */         XToolkit.awtUnlock();
/*      */       } 
/*      */       
/* 1737 */       if (volatileImage == null) {
/* 1738 */         if (XListPeer.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 1739 */           XListPeer.log.fine("Creating buffer " + XListPeer.this.width + "x" + XListPeer.this.height);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1744 */         volatileImage = XListPeer.this.graphicsConfig.createCompatibleVolatileImage(XListPeer.this.width + 1, XListPeer.this.height + 1);
/*      */       } 
/*      */       
/* 1747 */       XToolkit.awtLock();
/*      */       try {
/* 1749 */         if (this.buffer == null) {
/* 1750 */           this.buffer = volatileImage;
/* 1751 */           return true;
/*      */         } 
/*      */       } finally {
/* 1754 */         XToolkit.awtUnlock();
/*      */       } 
/* 1756 */       return false;
/*      */     }
/*      */     
/*      */     public void invalidate() {
/* 1760 */       XToolkit.awtLock();
/*      */       try {
/* 1762 */         if (this.buffer != null) {
/* 1763 */           this.buffer.flush();
/*      */         }
/* 1765 */         this.buffer = null;
/*      */       } finally {
/* 1767 */         XToolkit.awtUnlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     private void paint(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3) {
/* 1772 */       paint(param1Graphics, param1Int1, param1Int2, param1Int3, null, null);
/*      */     }
/*      */ 
/*      */     
/*      */     private void paint(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, Rectangle param1Rectangle, Point param1Point) {
/* 1777 */       if (XListPeer.log.isLoggable(PlatformLogger.Level.FINER)) {
/* 1778 */         XListPeer.log.finer("Repaint from " + param1Int1 + " to " + param1Int2 + " options " + param1Int3);
/*      */       }
/* 1780 */       if (param1Int1 > param1Int2) {
/* 1781 */         int i = param1Int2;
/* 1782 */         param1Int2 = param1Int1;
/* 1783 */         param1Int1 = i;
/*      */       } 
/* 1785 */       if (param1Int1 < 0) {
/* 1786 */         param1Int1 = 0;
/*      */       }
/* 1788 */       this.colors = XListPeer.this.getGUIcolors();
/* 1789 */       VolatileImage volatileImage = null; while (true) {
/*      */         Graphics2D graphics2D;
/* 1791 */         XToolkit.awtLock();
/*      */         try {
/* 1793 */           if (createBuffer())
/*      */           {
/* 1795 */             param1Int3 = 62;
/*      */           }
/* 1797 */           volatileImage = this.buffer;
/*      */         } finally {
/* 1799 */           XToolkit.awtUnlock();
/*      */         } 
/* 1801 */         switch (volatileImage.validate(XListPeer.this.getGraphicsConfiguration())) {
/*      */           case 2:
/* 1803 */             invalidate();
/* 1804 */             param1Int3 = 62;
/*      */             break;
/*      */           case 1:
/* 1807 */             param1Int3 = 62;
/*      */           default:
/* 1809 */             graphics2D = volatileImage.createGraphics();
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/* 1814 */               graphics2D.setFont(XListPeer.this.getFont());
/*      */ 
/*      */ 
/*      */               
/* 1818 */               if ((param1Int3 & 0x40) != 0) {
/* 1819 */                 paintFocus(graphics2D, 64);
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1828 */               if ((param1Int3 & 0x80) != 0) {
/* 1829 */                 graphics2D.copyArea(param1Rectangle.x, param1Rectangle.y, param1Rectangle.width, param1Rectangle.height, param1Point.x, param1Point.y);
/*      */               }
/*      */               
/* 1832 */               if ((param1Int3 & 0x20) != 0) {
/* 1833 */                 paintBackground(graphics2D);
/*      */                 
/* 1835 */                 param1Int1 = XListPeer.this.getFirstVisibleItem();
/* 1836 */                 param1Int2 = XListPeer.this.getLastVisibleItem();
/*      */               } 
/* 1838 */               if ((param1Int3 & 0x8) != 0) {
/* 1839 */                 paintItems(graphics2D, param1Int1, param1Int2, param1Int3);
/*      */               }
/* 1841 */               if ((param1Int3 & 0x2) != 0 && XListPeer.this.vsbVis) {
/* 1842 */                 graphics2D.setClip(XListPeer.this.getVScrollBarRec());
/* 1843 */                 paintVerScrollbar(graphics2D, true);
/*      */               } 
/* 1845 */               if ((param1Int3 & 0x4) != 0 && XListPeer.this.hsbVis) {
/* 1846 */                 graphics2D.setClip(XListPeer.this.getHScrollBarRec());
/* 1847 */                 paintHorScrollbar(graphics2D, true);
/*      */               } 
/* 1849 */               if ((param1Int3 & 0x10) != 0) {
/* 1850 */                 paintFocus(graphics2D, 16);
/*      */               }
/*      */             } finally {
/* 1853 */               graphics2D.dispose();
/*      */             }  break;
/* 1855 */         }  if (!volatileImage.contentsLost()) {
/* 1856 */           param1Graphics.drawImage(volatileImage, 0, 0, null);
/*      */           return;
/*      */         } 
/*      */       }  } private void paintBackground(Graphics param1Graphics) {
/* 1860 */       param1Graphics.setColor(SystemColor.window);
/* 1861 */       param1Graphics.fillRect(0, 0, XListPeer.this.width, XListPeer.this.height);
/* 1862 */       param1Graphics.setColor(getListBackground());
/* 1863 */       param1Graphics.fillRect(0, 0, XListPeer.this.listWidth, XListPeer.this.listHeight);
/* 1864 */       XListPeer.this.draw3DRect(param1Graphics, XComponentPeer.getSystemColors(), 0, 0, XListPeer.this.listWidth - 1, XListPeer.this.listHeight - 1, false);
/*      */     }
/*      */     
/*      */     private void paintItems(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3) {
/* 1868 */       if (XListPeer.log.isLoggable(PlatformLogger.Level.FINER)) {
/* 1869 */         XListPeer.log.finer("Painting items from " + param1Int1 + " to " + param1Int2 + ", focused " + XListPeer.this.focusIndex + ", first " + XListPeer.this.getFirstVisibleItem() + ", last " + XListPeer.this.getLastVisibleItem());
/*      */       }
/*      */       
/* 1872 */       param1Int1 = Math.max(XListPeer.this.getFirstVisibleItem(), param1Int1);
/* 1873 */       if (param1Int1 > param1Int2) {
/* 1874 */         int j = param1Int2;
/* 1875 */         param1Int2 = param1Int1;
/* 1876 */         param1Int1 = j;
/*      */       } 
/* 1878 */       param1Int1 = Math.max(XListPeer.this.getFirstVisibleItem(), param1Int1);
/* 1879 */       param1Int2 = Math.min(param1Int2, XListPeer.this.items.size() - 1);
/*      */       
/* 1881 */       if (XListPeer.log.isLoggable(PlatformLogger.Level.FINER)) {
/* 1882 */         XListPeer.log.finer("Actually painting items from " + param1Int1 + " to " + param1Int2 + ", items in window " + XListPeer.this
/* 1883 */             .itemsInWindow());
/*      */       }
/* 1885 */       for (int i = param1Int1; i <= param1Int2; i++) {
/* 1886 */         paintItem(param1Graphics, i);
/*      */       }
/*      */     }
/*      */     
/*      */     private void paintItem(Graphics param1Graphics, int param1Int) {
/* 1891 */       if (XListPeer.log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1892 */         XListPeer.log.finest("Painting item " + param1Int);
/*      */       }
/*      */       
/* 1895 */       if (!XListPeer.this.isItemHidden(param1Int)) {
/* 1896 */         Shape shape = param1Graphics.getClip();
/* 1897 */         int i = XListPeer.this.getItemWidth();
/* 1898 */         int j = XListPeer.this.getItemHeight();
/* 1899 */         int k = XListPeer.this.getItemY(param1Int);
/* 1900 */         int m = XListPeer.this.getItemX();
/* 1901 */         if (XListPeer.log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1902 */           XListPeer.log.finest("Setting clip " + new Rectangle(m, k, i - 2, j - 2));
/*      */         }
/* 1904 */         param1Graphics.setClip(m, k, i - 2, j - 2);
/*      */ 
/*      */ 
/*      */         
/* 1908 */         if (XListPeer.this.isSelected(param1Int)) {
/* 1909 */           if (XListPeer.log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1910 */             XListPeer.log.finest("Painted item is selected");
/*      */           }
/* 1912 */           param1Graphics.setColor(getListForeground());
/*      */         } else {
/* 1914 */           param1Graphics.setColor(getListBackground());
/*      */         } 
/* 1916 */         if (XListPeer.log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1917 */           XListPeer.log.finest("Filling " + new Rectangle(m, k, i, j));
/*      */         }
/* 1919 */         param1Graphics.fillRect(m, k, i, j);
/*      */         
/* 1921 */         if (param1Int <= XListPeer.this.getLastVisibleItem() && param1Int < XListPeer.this.items.size()) {
/* 1922 */           if (!XListPeer.this.isEnabled()) {
/* 1923 */             param1Graphics.setColor(getDisabledColor());
/* 1924 */           } else if (XListPeer.this.isSelected(param1Int)) {
/* 1925 */             param1Graphics.setColor(getListBackground());
/*      */           } else {
/* 1927 */             param1Graphics.setColor(getListForeground());
/*      */           } 
/* 1929 */           String str = XListPeer.this.items.elementAt(param1Int);
/* 1930 */           param1Graphics.drawString(str, m - XListPeer.this.hsb.getValue(), k + XListPeer.this.fontAscent);
/*      */         } else {
/*      */           
/* 1933 */           param1Graphics.setClip(m, k, XListPeer.this.listWidth, j);
/* 1934 */           param1Graphics.setColor(getListBackground());
/* 1935 */           param1Graphics.fillRect(m, k, XListPeer.this.listWidth, j);
/*      */         } 
/* 1937 */         param1Graphics.setClip(shape);
/*      */       } 
/*      */     }
/*      */     
/*      */     void paintScrollBar(XScrollbar param1XScrollbar, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, boolean param1Boolean) {
/* 1942 */       if (XListPeer.log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1943 */         XListPeer.log.finest("Painting scrollbar " + param1XScrollbar + " width " + param1Int3 + " height " + param1Int4 + ", paintAll " + param1Boolean);
/*      */       }
/*      */       
/* 1946 */       param1Graphics.translate(param1Int1, param1Int2);
/* 1947 */       param1XScrollbar.paint(param1Graphics, XComponentPeer.getSystemColors(), param1Boolean);
/* 1948 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void paintHorScrollbar(Graphics param1Graphics, boolean param1Boolean) {
/* 1959 */       int i = XListPeer.this.getListWidth();
/* 1960 */       paintScrollBar(XListPeer.this.hsb, param1Graphics, 0, XListPeer.this.height - 13, i, 13, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void paintVerScrollbar(Graphics param1Graphics, boolean param1Boolean) {
/* 1971 */       int i = XListPeer.this.height - (XListPeer.this.hsbVis ? 15 : 0);
/* 1972 */       paintScrollBar(XListPeer.this.vsb, param1Graphics, XListPeer.this.width - 13, 0, 11, i, param1Boolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void paintFocus(Graphics param1Graphics, int param1Int) {
/* 1978 */       boolean bool = ((param1Int & 0x10) != 0) ? true : false;
/* 1979 */       if (bool && !XListPeer.this.hasFocus()) {
/* 1980 */         bool = false;
/*      */       }
/* 1982 */       if (XListPeer.log.isLoggable(PlatformLogger.Level.FINE)) {
/* 1983 */         XListPeer.log.fine("Painting focus, focus index " + XListPeer.this.getFocusIndex() + ", focus is " + (
/* 1984 */             XListPeer.this.isItemHidden(XListPeer.this.getFocusIndex()) ? "invisible" : "visible") + ", paint focus is " + bool);
/*      */       }
/* 1986 */       Shape shape = param1Graphics.getClip();
/* 1987 */       param1Graphics.setClip(0, 0, XListPeer.this.listWidth, XListPeer.this.listHeight);
/* 1988 */       if (XListPeer.log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1989 */         XListPeer.log.finest("Setting focus clip " + new Rectangle(0, 0, XListPeer.this.listWidth, XListPeer.this.listHeight));
/*      */       }
/* 1991 */       Rectangle rectangle = XListPeer.this.getFocusRect();
/* 1992 */       if (this.prevFocusRect != null) {
/*      */         
/* 1994 */         if (XListPeer.log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1995 */           XListPeer.log.finest("Erasing previous focus rect " + this.prevFocusRect);
/*      */         }
/* 1997 */         param1Graphics.setColor(getListBackground());
/* 1998 */         param1Graphics.drawRect(this.prevFocusRect.x, this.prevFocusRect.y, this.prevFocusRect.width, this.prevFocusRect.height);
/* 1999 */         this.prevFocusRect = null;
/*      */       } 
/* 2001 */       if (bool) {
/*      */         
/* 2003 */         if (XListPeer.log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 2004 */           XListPeer.log.finest("Painting focus rect " + rectangle);
/*      */         }
/* 2006 */         param1Graphics.setColor(getListForeground());
/* 2007 */         param1Graphics.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/* 2008 */         this.prevFocusRect = rectangle;
/*      */       } 
/* 2010 */       param1Graphics.setClip(shape);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XListPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */