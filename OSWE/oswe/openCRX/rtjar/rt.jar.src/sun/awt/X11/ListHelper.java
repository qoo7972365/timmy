/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ final class ListHelper
/*     */   implements XScrollbarClient
/*     */ {
/*  44 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.ListHelper");
/*     */   
/*  46 */   private final int FOCUS_INSET = 1;
/*     */ 
/*     */   
/*     */   private final int BORDER_WIDTH;
/*     */ 
/*     */   
/*     */   private final int ITEM_MARGIN;
/*     */ 
/*     */   
/*     */   private final int TEXT_SPACE;
/*     */ 
/*     */   
/*     */   private final int SCROLLBAR_WIDTH;
/*     */   
/*     */   private List items;
/*     */   
/*     */   private List selected;
/*     */   
/*     */   private boolean multiSelect;
/*     */   
/*     */   private int focusedIndex;
/*     */   
/*     */   private int maxVisItems;
/*     */   
/*     */   private XVerticalScrollbar vsb;
/*     */   
/*     */   private boolean vsbVis;
/*     */   
/*     */   private XHorizontalScrollbar hsb;
/*     */   
/*     */   private boolean hsbVis;
/*     */   
/*     */   private Font font;
/*     */   
/*     */   private FontMetrics fm;
/*     */   
/*     */   private XWindow peer;
/*     */   
/*     */   private Color[] colors;
/*     */   
/*     */   private boolean mouseDraggedOutVertically = false;
/*     */   
/*     */   private volatile boolean vsbVisibilityChanged = false;
/*     */ 
/*     */   
/*     */   ListHelper(XWindow paramXWindow, Color[] paramArrayOfColor, int paramInt1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Font paramFont, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  92 */     this.peer = paramXWindow;
/*  93 */     this.colors = paramArrayOfColor;
/*  94 */     this.multiSelect = paramBoolean1;
/*  95 */     this.items = new ArrayList(paramInt1);
/*  96 */     this.selected = new ArrayList(1);
/*  97 */     this.selected.add(Integer.valueOf(-1));
/*     */     
/*  99 */     this.maxVisItems = paramInt2;
/* 100 */     if (paramBoolean2) {
/* 101 */       this.vsb = new XVerticalScrollbar(this);
/* 102 */       this.vsb.setValues(0, 0, 0, 0, 1, paramInt2 - 1);
/*     */     } 
/* 104 */     if (paramBoolean3) {
/* 105 */       this.hsb = new XHorizontalScrollbar(this);
/* 106 */       this.hsb.setValues(0, 0, 0, 0, 1, 1);
/*     */     } 
/*     */     
/* 109 */     setFont(paramFont);
/* 110 */     this.TEXT_SPACE = paramInt3;
/* 111 */     this.ITEM_MARGIN = paramInt4;
/* 112 */     this.BORDER_WIDTH = paramInt5;
/* 113 */     this.SCROLLBAR_WIDTH = paramInt6;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getEventSource() {
/* 118 */     return this.peer.getEventSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void add(String paramString) {
/* 126 */     this.items.add(paramString);
/* 127 */     updateScrollbars();
/*     */   }
/*     */   
/*     */   void add(String paramString, int paramInt) {
/* 131 */     this.items.add(paramInt, paramString);
/* 132 */     updateScrollbars();
/*     */   }
/*     */ 
/*     */   
/*     */   void remove(String paramString) {
/* 137 */     this.items.remove(paramString);
/* 138 */     updateScrollbars();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void remove(int paramInt) {
/* 144 */     this.items.remove(paramInt);
/* 145 */     updateScrollbars();
/*     */   }
/*     */ 
/*     */   
/*     */   void removeAll() {
/* 150 */     this.items.removeAll(this.items);
/* 151 */     updateScrollbars();
/*     */   }
/*     */   
/*     */   void setMultiSelect(boolean paramBoolean) {
/* 155 */     this.multiSelect = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void select(int paramInt) {
/* 164 */     if (paramInt > getItemCount() - 1) {
/* 165 */       paramInt = isEmpty() ? -1 : 0;
/*     */     }
/* 167 */     if (this.multiSelect) {
/* 168 */       assert false : "Implement ListHelper.select() for multiselect";
/*     */     }
/* 170 */     else if (getSelectedIndex() != paramInt) {
/* 171 */       this.selected.remove(0);
/* 172 */       this.selected.add(Integer.valueOf(paramInt));
/* 173 */       makeVisible(paramInt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void deselect(int paramInt) {
/*     */     assert false;
/*     */   }
/*     */ 
/*     */   
/*     */   int getSelectedIndex() {
/* 185 */     if (!this.multiSelect) {
/* 186 */       Integer integer = this.selected.get(0);
/* 187 */       return integer.intValue();
/*     */     } 
/* 189 */     return -1;
/*     */   } int[] getSelectedIndexes() {
/*     */     assert false;
/* 192 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean checkVsbVisibilityChangedAndReset() {
/* 199 */     boolean bool = this.vsbVisibilityChanged;
/* 200 */     this.vsbVisibilityChanged = false;
/* 201 */     return bool;
/*     */   }
/*     */   
/*     */   boolean isEmpty() {
/* 205 */     return this.items.isEmpty();
/*     */   }
/*     */   
/*     */   int getItemCount() {
/* 209 */     return this.items.size();
/*     */   }
/*     */   
/*     */   String getItem(int paramInt) {
/* 213 */     return this.items.get(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setFocusedIndex(int paramInt) {
/* 221 */     this.focusedIndex = paramInt;
/*     */   }
/*     */   
/*     */   private boolean isFocusedIndex(int paramInt) {
/* 225 */     return (paramInt == this.focusedIndex);
/*     */   }
/*     */   
/*     */   void setFont(Font paramFont) {
/* 229 */     if (paramFont != this.font) {
/* 230 */       this.font = paramFont;
/* 231 */       this.fm = Toolkit.getDefaultToolkit().getFontMetrics(this.font);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMaxItemWidth() {
/* 240 */     int i = 0;
/* 241 */     int j = getItemCount();
/* 242 */     for (byte b = 0; b < j; b++) {
/* 243 */       int k = this.fm.stringWidth(getItem(b));
/* 244 */       i = Math.max(i, k);
/*     */     } 
/* 246 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getItemHeight() {
/* 253 */     return this.fm.getHeight() + 2 * this.TEXT_SPACE;
/*     */   }
/*     */   
/*     */   int y2index(int paramInt) {
/* 257 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 258 */       log.fine("y=" + paramInt + ", firstIdx=" + firstDisplayedIndex() + ", itemHeight=" + getItemHeight() + ",item_margin=" + this.ITEM_MARGIN);
/*     */     }
/*     */ 
/*     */     
/* 262 */     return firstDisplayedIndex() + (paramInt - 2 * this.ITEM_MARGIN) / (getItemHeight() + 2 * this.ITEM_MARGIN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int firstDisplayedIndex() {
/* 272 */     if (this.vsbVis) {
/* 273 */       return this.vsb.getValue();
/*     */     }
/* 275 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   int lastDisplayedIndex() {
/* 280 */     if (this.hsbVis && 
/* 281 */       !$assertionsDisabled) throw new AssertionError("Implement for horiz scroll bar");
/*     */ 
/*     */     
/* 284 */     return this.vsbVis ? (this.vsb.getValue() + this.maxVisItems - 1) : (getItemCount() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void makeVisible(int paramInt) {
/* 291 */     if (this.vsbVis) {
/* 292 */       if (paramInt < firstDisplayedIndex()) {
/* 293 */         this.vsb.setValue(paramInt);
/*     */       }
/* 295 */       else if (paramInt > lastDisplayedIndex()) {
/* 296 */         this.vsb.setValue(paramInt - this.maxVisItems + 1);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void up() {
/* 303 */     int k, i = getSelectedIndex();
/* 304 */     int j = getItemCount();
/*     */ 
/*     */     
/* 307 */     assert i >= 0;
/*     */     
/* 309 */     if (i == 0) {
/* 310 */       k = j - 1;
/*     */     } else {
/*     */       
/* 313 */       k = --i;
/*     */     } 
/*     */     
/* 316 */     select(k);
/*     */   }
/*     */   
/*     */   void down() {
/* 320 */     int i = (getSelectedIndex() + 1) % getItemCount();
/* 321 */     select(i);
/*     */   }
/*     */ 
/*     */   
/*     */   void pageUp() {
/* 326 */     if (this.vsbVis && firstDisplayedIndex() > 0)
/* 327 */       if (this.multiSelect) {
/* 328 */         assert false : "Implement pageUp() for multiSelect";
/*     */       } else {
/*     */         
/* 331 */         int i = getSelectedIndex() - firstDisplayedIndex();
/*     */         
/* 333 */         int j = firstDisplayedIndex() - this.vsb.getBlockIncrement();
/* 334 */         this.vsb.setValue(j);
/* 335 */         select(firstDisplayedIndex() + i);
/*     */       }  
/*     */   }
/*     */   
/*     */   void pageDown() {
/* 340 */     if (this.vsbVis && lastDisplayedIndex() < getItemCount() - 1)
/* 341 */       if (this.multiSelect) {
/* 342 */         assert false : "Implement pageDown() for multiSelect";
/*     */       } else {
/*     */         
/* 345 */         int i = getSelectedIndex() - firstDisplayedIndex();
/*     */         
/* 347 */         int j = lastDisplayedIndex();
/* 348 */         this.vsb.setValue(j);
/* 349 */         select(firstDisplayedIndex() + i);
/*     */       }  
/*     */   }
/*     */   
/*     */   void home() {}
/*     */   
/*     */   void end() {}
/*     */   
/* 357 */   boolean isVSBVisible() { return this.vsbVis; } boolean isHSBVisible() {
/* 358 */     return this.hsbVis;
/*     */   }
/* 360 */   XVerticalScrollbar getVSB() { return this.vsb; } XHorizontalScrollbar getHSB() {
/* 361 */     return this.hsb;
/*     */   }
/*     */   boolean isInVertSB(Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 364 */     if (this.vsbVis) {
/* 365 */       assert this.vsb != null : "Vert scrollbar is visible, yet is null?";
/* 366 */       int i = this.hsbVis ? (paramRectangle.height - this.SCROLLBAR_WIDTH) : paramRectangle.height;
/* 367 */       return (paramInt1 <= paramRectangle.width && paramInt1 >= paramRectangle.width - this.SCROLLBAR_WIDTH && paramInt2 >= 0 && paramInt2 <= i);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 372 */     return false;
/*     */   }
/*     */   
/*     */   boolean isInHorizSB(Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 376 */     if (this.hsbVis) {
/* 377 */       assert this.hsb != null : "Horiz scrollbar is visible, yet is null?";
/*     */       
/* 379 */       int i = this.vsbVis ? (paramRectangle.width - this.SCROLLBAR_WIDTH) : paramRectangle.width;
/* 380 */       return (paramInt1 <= i && paramInt1 >= 0 && paramInt2 >= paramRectangle.height - this.SCROLLBAR_WIDTH && paramInt2 <= paramRectangle.height);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 385 */     return false;
/*     */   }
/*     */   
/*     */   void handleVSBEvent(MouseEvent paramMouseEvent, Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 389 */     int i = this.hsbVis ? (paramRectangle.height - this.SCROLLBAR_WIDTH) : paramRectangle.height;
/*     */     
/* 391 */     this.vsb.handleMouseEvent(paramMouseEvent.getID(), paramMouseEvent
/* 392 */         .getModifiers(), paramInt1 - paramRectangle.width - this.SCROLLBAR_WIDTH, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateScrollbars() {
/* 402 */     boolean bool = this.vsbVis;
/* 403 */     this.vsbVis = (this.vsb != null && this.items.size() > this.maxVisItems);
/* 404 */     if (this.vsbVis) {
/* 405 */       this.vsb.setValues(this.vsb.getValue(), getNumItemsDisplayed(), this.vsb
/* 406 */           .getMinimum(), this.items.size());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 412 */     this.vsbVisibilityChanged = (this.vsbVis != bool);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getNumItemsDisplayed() {
/* 417 */     return (this.items.size() > this.maxVisItems) ? this.maxVisItems : this.items.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void repaintScrollbarRequest(XScrollbar paramXScrollbar) {
/* 422 */     Graphics graphics = this.peer.getGraphics();
/* 423 */     Rectangle rectangle = this.peer.getBounds();
/* 424 */     if (paramXScrollbar == this.vsb && this.vsbVis) {
/* 425 */       paintVSB(graphics, XComponentPeer.getSystemColors(), rectangle);
/*     */     }
/* 427 */     else if (paramXScrollbar == this.hsb && this.hsbVis) {
/* 428 */       paintHSB(graphics, XComponentPeer.getSystemColors(), rectangle);
/*     */     } 
/* 430 */     graphics.dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyValue(XScrollbar paramXScrollbar, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 435 */     if (paramXScrollbar == this.vsb) {
/* 436 */       int i = this.vsb.getValue();
/* 437 */       this.vsb.setValue(paramInt2);
/* 438 */       boolean bool = (i != this.vsb.getValue()) ? true : false;
/*     */       
/* 440 */       if (this.mouseDraggedOutVertically) {
/* 441 */         int m = getSelectedIndex();
/* 442 */         int n = getSelectedIndex() + paramInt2 - i;
/* 443 */         select(n);
/* 444 */         bool = (bool || getSelectedIndex() != m) ? true : false;
/*     */       } 
/*     */ 
/*     */       
/* 448 */       Graphics graphics = this.peer.getGraphics();
/* 449 */       Rectangle rectangle = this.peer.getBounds();
/* 450 */       int j = paramInt2;
/* 451 */       int k = Math.min(getItemCount() - 1, paramInt2 + this.maxVisItems);
/*     */       
/* 453 */       if (bool) {
/* 454 */         paintItems(graphics, this.colors, rectangle, j, k);
/*     */       }
/* 456 */       graphics.dispose();
/*     */     
/*     */     }
/* 459 */     else if ((XHorizontalScrollbar)paramXScrollbar == this.hsb) {
/* 460 */       this.hsb.setValue(paramInt2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void updateColors(Color[] paramArrayOfColor) {
/* 466 */     this.colors = paramArrayOfColor;
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
/*     */   void paintItems(Graphics paramGraphics, Color[] paramArrayOfColor, Rectangle paramRectangle) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void paintAllItems(Graphics paramGraphics, Color[] paramArrayOfColor, Rectangle paramRectangle) {
/* 491 */     paintItems(paramGraphics, paramArrayOfColor, paramRectangle, 
/* 492 */         firstDisplayedIndex(), lastDisplayedIndex());
/*     */   }
/*     */   
/*     */   private void paintItems(Graphics paramGraphics, Color[] paramArrayOfColor, Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 496 */     this.peer.flush();
/* 497 */     int i = this.BORDER_WIDTH + this.ITEM_MARGIN;
/* 498 */     int j = paramRectangle.width - 2 * this.ITEM_MARGIN - 2 * this.BORDER_WIDTH - (this.vsbVis ? this.SCROLLBAR_WIDTH : 0);
/* 499 */     int k = getItemHeight();
/* 500 */     int m = this.BORDER_WIDTH + this.ITEM_MARGIN;
/*     */     
/* 502 */     for (int n = paramInt1; n <= paramInt2; n++) {
/* 503 */       paintItem(paramGraphics, paramArrayOfColor, getItem(n), i, m, j, k, 
/*     */           
/* 505 */           isItemSelected(n), 
/* 506 */           isFocusedIndex(n));
/* 507 */       m += k + 2 * this.ITEM_MARGIN;
/*     */     } 
/*     */     
/* 510 */     if (this.vsbVis) {
/* 511 */       paintVSB(paramGraphics, XComponentPeer.getSystemColors(), paramRectangle);
/*     */     }
/* 513 */     if (this.hsbVis) {
/* 514 */       paintHSB(paramGraphics, XComponentPeer.getSystemColors(), paramRectangle);
/*     */     }
/* 516 */     this.peer.flush();
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
/*     */   private void paintItem(Graphics paramGraphics, Color[] paramArrayOfColor, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2) {
/* 532 */     if (paramBoolean1) {
/* 533 */       paramGraphics.setColor(paramArrayOfColor[3]);
/*     */     } else {
/*     */       
/* 536 */       paramGraphics.setColor(paramArrayOfColor[0]);
/*     */     } 
/* 538 */     paramGraphics.fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     
/* 540 */     if (paramBoolean2) {
/*     */       
/* 542 */       paramGraphics.setColor(Color.BLACK);
/* 543 */       paramGraphics.drawRect(paramInt1 + 1, paramInt2 + 1, paramInt3 - 2, paramInt4 - 2);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 549 */     if (paramBoolean1) {
/* 550 */       paramGraphics.setColor(paramArrayOfColor[0]);
/*     */     } else {
/*     */       
/* 553 */       paramGraphics.setColor(paramArrayOfColor[3]);
/*     */     } 
/* 555 */     paramGraphics.setFont(this.font);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 560 */     int i = this.fm.getAscent();
/* 561 */     int j = this.fm.getDescent();
/*     */     
/* 563 */     paramGraphics.drawString(paramString, paramInt1 + this.TEXT_SPACE, paramInt2 + (paramInt4 + this.fm.getMaxAscent() - this.fm.getMaxDescent()) / 2);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isItemSelected(int paramInt) {
/* 568 */     Iterator<Integer> iterator = this.selected.iterator();
/* 569 */     while (iterator.hasNext()) {
/* 570 */       Integer integer = iterator.next();
/* 571 */       if (integer.intValue() == paramInt) {
/* 572 */         return true;
/*     */       }
/*     */     } 
/* 575 */     return false;
/*     */   }
/*     */   
/*     */   private void paintVSB(Graphics paramGraphics, Color[] paramArrayOfColor, Rectangle paramRectangle) {
/* 579 */     int i = paramRectangle.height - 2 * this.BORDER_WIDTH - (this.hsbVis ? (this.SCROLLBAR_WIDTH - 2) : 0);
/* 580 */     Graphics graphics = paramGraphics.create();
/*     */     
/* 582 */     paramGraphics.setColor(paramArrayOfColor[0]);
/*     */     try {
/* 584 */       graphics.translate(paramRectangle.width - this.BORDER_WIDTH - this.SCROLLBAR_WIDTH, this.BORDER_WIDTH);
/*     */ 
/*     */       
/* 587 */       this.vsb.setSize(this.SCROLLBAR_WIDTH, paramRectangle.height);
/* 588 */       this.vsb.paint(graphics, paramArrayOfColor, true);
/*     */     } finally {
/* 590 */       graphics.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintHSB(Graphics paramGraphics, Color[] paramArrayOfColor, Rectangle paramRectangle) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean doWheelScroll(XVerticalScrollbar paramXVerticalScrollbar, XHorizontalScrollbar paramXHorizontalScrollbar, MouseWheelEvent paramMouseWheelEvent) {
/*     */     XHorizontalScrollbar xHorizontalScrollbar;
/* 609 */     XVerticalScrollbar xVerticalScrollbar = null;
/*     */ 
/*     */ 
/*     */     
/* 613 */     if (paramXVerticalScrollbar != null) {
/* 614 */       xVerticalScrollbar = paramXVerticalScrollbar;
/*     */     }
/* 616 */     else if (paramXHorizontalScrollbar != null) {
/* 617 */       xHorizontalScrollbar = paramXHorizontalScrollbar;
/*     */     } else {
/*     */       
/* 620 */       return false;
/*     */     } 
/*     */     
/* 623 */     int i = paramMouseWheelEvent.getWheelRotation();
/*     */ 
/*     */     
/* 626 */     if ((i < 0 && xHorizontalScrollbar.getValue() > xHorizontalScrollbar.getMinimum()) || (i > 0 && xHorizontalScrollbar
/* 627 */       .getValue() < xHorizontalScrollbar.getMaximum()) || i != 0) {
/*     */ 
/*     */       
/* 630 */       int k, j = paramMouseWheelEvent.getScrollType();
/*     */       
/* 632 */       if (j == 1) {
/* 633 */         k = i * xHorizontalScrollbar.getBlockIncrement();
/*     */       } else {
/*     */         
/* 636 */         k = paramMouseWheelEvent.getUnitsToScroll() * xHorizontalScrollbar.getUnitIncrement();
/*     */       } 
/* 638 */       xHorizontalScrollbar.setValue(xHorizontalScrollbar.getValue() + k);
/* 639 */       return true;
/*     */     } 
/* 641 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void trackMouseDraggedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 652 */     if (!this.mouseDraggedOutVertically) {
/* 653 */       if (this.vsb.beforeThumb(paramInt1, paramInt2)) {
/* 654 */         this.vsb.setMode(2);
/*     */       } else {
/* 656 */         this.vsb.setMode(1);
/*     */       } 
/*     */     }
/*     */     
/* 660 */     if (!this.mouseDraggedOutVertically && (paramInt2 < 0 || paramInt2 >= paramInt4)) {
/* 661 */       this.mouseDraggedOutVertically = true;
/* 662 */       this.vsb.startScrollingInstance();
/*     */     } 
/*     */     
/* 665 */     if (this.mouseDraggedOutVertically && paramInt2 >= 0 && paramInt2 < paramInt4 && paramInt1 >= 0 && paramInt1 < paramInt3) {
/* 666 */       this.mouseDraggedOutVertically = false;
/* 667 */       this.vsb.stopScrollingInstance();
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
/*     */   void trackMouseReleasedScroll() {
/* 679 */     if (this.mouseDraggedOutVertically) {
/* 680 */       this.mouseDraggedOutVertically = false;
/* 681 */       this.vsb.stopScrollingInstance();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/ListHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */