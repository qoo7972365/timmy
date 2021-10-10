/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Adjustable;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.ScrollPane;
/*     */ import java.awt.ScrollPaneAdjustable;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.peer.ScrollPanePeer;
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
/*     */ class XScrollPanePeer
/*     */   extends XComponentPeer
/*     */   implements ScrollPanePeer, XScrollbarClient
/*     */ {
/*     */   public static final int MARGIN = 1;
/*  46 */   public static final int SCROLLBAR = XToolkit.getUIDefaults().getInt("ScrollBar.defaultWidth"); public static final int SPACE = 2; public static final int SCROLLBAR_INSET = 2; public static final int VERTICAL = 1; public static final int HORIZONTAL = 2;
/*     */   XVerticalScrollbar vsb;
/*     */   XHorizontalScrollbar hsb;
/*     */   XWindow clip;
/*     */   int hsbSpace;
/*     */   int vsbSpace;
/*     */   int vval;
/*  53 */   int active = 1;
/*     */   int hval;
/*     */   int vmax;
/*     */   int hmax;
/*     */   
/*     */   static class XScrollPaneContentWindow extends XWindow { XScrollPaneContentWindow(ScrollPane param1ScrollPane, long param1Long) {
/*  59 */       super(param1ScrollPane, param1Long);
/*     */     }
/*     */     public String getWMName() {
/*  62 */       return "ScrollPane content";
/*     */     } }
/*     */ 
/*     */   
/*     */   XScrollPanePeer(ScrollPane paramScrollPane) {
/*  67 */     super(paramScrollPane);
/*     */ 
/*     */ 
/*     */     
/*  71 */     this.clip = null;
/*     */ 
/*     */     
/*  74 */     XScrollPaneContentWindow xScrollPaneContentWindow = new XScrollPaneContentWindow(paramScrollPane, this.window);
/*  75 */     this.clip = xScrollPaneContentWindow;
/*     */     
/*  77 */     this.vsb = new XVerticalScrollbar(this);
/*     */     
/*  79 */     this.hsb = new XHorizontalScrollbar(this);
/*     */     
/*  81 */     if (paramScrollPane.getScrollbarDisplayPolicy() == 1) {
/*  82 */       this.vsbSpace = this.hsbSpace = SCROLLBAR;
/*     */     } else {
/*  84 */       this.vsbSpace = this.hsbSpace = 0;
/*     */     } 
/*     */     
/*  87 */     int i = 1;
/*  88 */     Adjustable adjustable1 = paramScrollPane.getVAdjustable();
/*  89 */     if (adjustable1 != null) {
/*  90 */       i = adjustable1.getUnitIncrement();
/*     */     }
/*  92 */     int j = this.height - this.hsbSpace;
/*  93 */     this.vsb.setValues(0, j, 0, j, i, Math.max(1, (int)(j * 0.9D)));
/*  94 */     this.vsb.setSize(this.vsbSpace - 2, j);
/*     */     
/*  96 */     i = 1;
/*  97 */     Adjustable adjustable2 = paramScrollPane.getHAdjustable();
/*  98 */     if (adjustable2 != null) {
/*  99 */       i = adjustable2.getUnitIncrement();
/*     */     }
/* 101 */     int k = this.width - this.vsbSpace;
/* 102 */     this.hsb.setValues(0, k, 0, k, i, Math.max(1, (int)(k * 0.9D)));
/* 103 */     this.hsb.setSize(k, this.hsbSpace - 2);
/*     */     
/* 105 */     setViewportSize();
/* 106 */     this.clip.xSetVisible(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getContentWindow() {
/* 113 */     return (this.clip == null) ? this.window : this.clip.getWindow();
/*     */   }
/*     */   
/*     */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 117 */     super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */     
/* 119 */     if (this.clip == null)
/* 120 */       return;  setScrollbarSpace();
/* 121 */     setViewportSize();
/* 122 */     repaint();
/*     */   }
/*     */   
/*     */   public Insets getInsets() {
/* 126 */     return new Insets(1, 1, 1 + this.hsbSpace, 1 + this.vsbSpace);
/*     */   }
/*     */   
/*     */   public int getHScrollbarHeight() {
/* 130 */     return SCROLLBAR;
/*     */   }
/*     */   
/*     */   public int getVScrollbarWidth() {
/* 134 */     return SCROLLBAR;
/*     */   }
/*     */   
/*     */   public void childResized(int paramInt1, int paramInt2) {
/* 138 */     if (setScrollbarSpace()) {
/* 139 */       setViewportSize();
/*     */     }
/* 141 */     repaint();
/*     */   }
/*     */   
/*     */   Dimension getChildSize() {
/* 145 */     ScrollPane scrollPane = (ScrollPane)this.target;
/* 146 */     if (scrollPane.countComponents() > 0) {
/* 147 */       Component component = scrollPane.getComponent(0);
/* 148 */       return component.size();
/*     */     } 
/* 150 */     return new Dimension(0, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean setScrollbarSpace() {
/* 155 */     ScrollPane scrollPane = (ScrollPane)this.target;
/* 156 */     boolean bool1 = false;
/* 157 */     int i = scrollPane.getScrollbarDisplayPolicy();
/*     */     
/* 159 */     if (i == 2) {
/* 160 */       return bool1;
/*     */     }
/* 162 */     Dimension dimension = getChildSize();
/*     */     
/* 164 */     if (i == 0) {
/* 165 */       int j = this.hsbSpace;
/* 166 */       int k = this.vsbSpace;
/* 167 */       this.hsbSpace = (dimension.width <= this.width - 2) ? 0 : SCROLLBAR;
/* 168 */       this.vsbSpace = (dimension.height <= this.height - 2) ? 0 : SCROLLBAR;
/*     */       
/* 170 */       if (this.hsbSpace == 0 && this.vsbSpace != 0) {
/* 171 */         this.hsbSpace = (dimension.width <= this.width - SCROLLBAR - 2) ? 0 : SCROLLBAR;
/*     */       }
/* 173 */       if (this.vsbSpace == 0 && this.hsbSpace != 0) {
/* 174 */         this.vsbSpace = (dimension.height <= this.height - SCROLLBAR - 2) ? 0 : SCROLLBAR;
/*     */       }
/* 176 */       if (j != this.hsbSpace || k != this.vsbSpace) {
/* 177 */         bool1 = true;
/*     */       }
/*     */     } 
/* 180 */     if (this.vsbSpace > 0) {
/* 181 */       int j = this.height - 2 - this.hsbSpace;
/* 182 */       int k = Math.max(dimension.height, j);
/* 183 */       this.vsb.setValues(this.vsb.getValue(), j, 0, k);
/* 184 */       this.vsb.setBlockIncrement((int)(this.vsb.getVisibleAmount() * 0.9D));
/* 185 */       this.vsb.setSize(this.vsbSpace - 2, this.height - this.hsbSpace);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     if (this.hsbSpace > 0) {
/* 192 */       int j = this.width - 2 - this.vsbSpace;
/* 193 */       int k = Math.max(dimension.width, j);
/* 194 */       this.hsb.setValues(this.hsb.getValue(), j, 0, k);
/* 195 */       this.hsb.setBlockIncrement((int)(this.hsb.getVisibleAmount() * 0.9D));
/* 196 */       this.hsb.setSize(this.width - this.vsbSpace, this.hsbSpace - 2);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     boolean bool2 = false;
/*     */ 
/*     */ 
/*     */     
/* 213 */     Point point = new Point(0, 0);
/*     */     
/* 215 */     if (((ScrollPane)this.target).getComponentCount() > 0) {
/*     */       
/* 217 */       point = ((ScrollPane)this.target).getComponent(0).location();
/*     */       
/* 219 */       if (this.vsbSpace == 0 && point.y < 0) {
/* 220 */         point.y = 0;
/* 221 */         bool2 = true;
/*     */       } 
/*     */       
/* 224 */       if (this.hsbSpace == 0 && point.x < 0) {
/* 225 */         point.x = 0;
/* 226 */         bool2 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 230 */     if (bool2) {
/* 231 */       scroll(this.x, this.y, 3);
/*     */     }
/* 233 */     return bool1;
/*     */   }
/*     */   
/*     */   void setViewportSize() {
/* 237 */     this.clip.xSetBounds(1, 1, this.width - 2 - this.vsbSpace, this.height - 2 - this.hsbSpace);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnitIncrement(Adjustable paramAdjustable, int paramInt) {
/* 243 */     if (paramAdjustable.getOrientation() == 1) {
/* 244 */       this.vsb.setUnitIncrement(paramInt);
/*     */     } else {
/*     */       
/* 247 */       this.hsb.setUnitIncrement(paramInt);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setValue(Adjustable paramAdjustable, int paramInt) {
/* 252 */     if (paramAdjustable.getOrientation() == 1) {
/* 253 */       scroll(-1, paramInt, 1);
/*     */     } else {
/*     */       
/* 256 */       scroll(paramInt, -1, 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setScrollPosition(int paramInt1, int paramInt2) {
/* 261 */     scroll(paramInt1, paramInt2, 3);
/*     */   }
/*     */   
/*     */   void scroll(int paramInt1, int paramInt2, int paramInt3) {
/* 265 */     scroll(paramInt1, paramInt2, paramInt3, 5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void scroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     int i, j;
/* 272 */     checkSecurity();
/* 273 */     ScrollPane scrollPane = (ScrollPane)this.target;
/* 274 */     Component component = getScrollChild();
/* 275 */     if (component == null) {
/*     */       return;
/*     */     }
/*     */     
/* 279 */     Color[] arrayOfColor = getGUIcolors();
/*     */     
/* 281 */     if (scrollPane.getScrollbarDisplayPolicy() == 2) {
/* 282 */       i = -paramInt1;
/* 283 */       j = -paramInt2;
/*     */     } else {
/* 285 */       Point point = component.location();
/* 286 */       i = point.x;
/* 287 */       j = point.y;
/*     */       
/* 289 */       if ((paramInt3 & 0x2) != 0) {
/* 290 */         this.hsb.setValue(Math.min(paramInt1, this.hsb.getMaximum() - this.hsb.getVisibleAmount()));
/* 291 */         ScrollPaneAdjustable scrollPaneAdjustable = (ScrollPaneAdjustable)scrollPane.getHAdjustable();
/* 292 */         setAdjustableValue(scrollPaneAdjustable, this.hsb.getValue(), paramInt4);
/* 293 */         i = -this.hsb.getValue();
/* 294 */         Graphics graphics = getGraphics();
/* 295 */         if (graphics != null) {
/*     */           try {
/* 297 */             paintHorScrollbar(graphics, arrayOfColor, true);
/*     */           } finally {
/* 299 */             graphics.dispose();
/*     */           } 
/*     */         }
/*     */       } 
/* 303 */       if ((paramInt3 & 0x1) != 0) {
/* 304 */         this.vsb.setValue(Math.min(paramInt2, this.vsb.getMaximum() - this.vsb.getVisibleAmount()));
/* 305 */         ScrollPaneAdjustable scrollPaneAdjustable = (ScrollPaneAdjustable)scrollPane.getVAdjustable();
/* 306 */         setAdjustableValue(scrollPaneAdjustable, this.vsb.getValue(), paramInt4);
/* 307 */         j = -this.vsb.getValue();
/* 308 */         Graphics graphics = getGraphics();
/* 309 */         if (graphics != null) {
/*     */           try {
/* 311 */             paintVerScrollbar(graphics, arrayOfColor, true);
/*     */           } finally {
/* 313 */             graphics.dispose();
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 318 */     component.move(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setAdjustableValue(ScrollPaneAdjustable paramScrollPaneAdjustable, int paramInt1, int paramInt2) {
/* 323 */     AWTAccessor.getScrollPaneAdjustableAccessor().setTypedValue(paramScrollPaneAdjustable, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   void paintPeer(Graphics paramGraphics) {
/* 328 */     Color[] arrayOfColor = getGUIcolors();
/* 329 */     paramGraphics.setColor(arrayOfColor[0]);
/* 330 */     int i = this.height - this.hsbSpace;
/* 331 */     int j = this.width - this.vsbSpace;
/* 332 */     paramGraphics.fillRect(0, 0, j, i);
/*     */     
/* 334 */     paramGraphics.fillRect(j, i, this.vsbSpace, this.hsbSpace);
/*     */     
/* 336 */     draw3DRect(paramGraphics, arrayOfColor, 0, 0, j - 1, i - 1, false);
/*     */     
/* 338 */     paintScrollBars(paramGraphics, arrayOfColor);
/*     */   }
/*     */   private void paintScrollBars(Graphics paramGraphics, Color[] paramArrayOfColor) {
/* 341 */     if (this.vsbSpace > 0) {
/* 342 */       paintVerScrollbar(paramGraphics, paramArrayOfColor, true);
/*     */     }
/*     */ 
/*     */     
/* 346 */     if (this.hsbSpace > 0) {
/* 347 */       paintHorScrollbar(paramGraphics, paramArrayOfColor, true);
/*     */     }
/*     */   }
/*     */   
/*     */   void repaintScrollBars() {
/* 352 */     Graphics graphics = getGraphics();
/* 353 */     Color[] arrayOfColor = getGUIcolors();
/* 354 */     if (graphics != null)
/*     */       try {
/* 356 */         paintScrollBars(graphics, arrayOfColor);
/*     */       } finally {
/* 358 */         graphics.dispose();
/*     */       }  
/*     */   }
/*     */   
/*     */   public void repaintScrollbarRequest(XScrollbar paramXScrollbar) {
/* 363 */     Graphics graphics = getGraphics();
/* 364 */     Color[] arrayOfColor = getGUIcolors();
/* 365 */     if (graphics != null)
/*     */       try {
/* 367 */         if (paramXScrollbar == this.vsb) {
/* 368 */           paintVerScrollbar(graphics, arrayOfColor, true);
/* 369 */         } else if (paramXScrollbar == this.hsb) {
/* 370 */           paintHorScrollbar(graphics, arrayOfColor, true);
/*     */         } 
/*     */       } finally {
/* 373 */         graphics.dispose();
/*     */       }  
/*     */   }
/*     */   
/*     */   public void handleEvent(AWTEvent paramAWTEvent) {
/* 378 */     super.handleEvent(paramAWTEvent);
/*     */     
/* 380 */     int i = paramAWTEvent.getID();
/* 381 */     switch (i) {
/*     */       case 800:
/*     */       case 801:
/* 384 */         repaintScrollBars();
/*     */         break;
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
/*     */   void paintHorScrollbar(Graphics paramGraphics, Color[] paramArrayOfColor, boolean paramBoolean) {
/* 398 */     if (this.hsbSpace <= 0) {
/*     */       return;
/*     */     }
/* 401 */     Graphics graphics = paramGraphics.create();
/* 402 */     paramGraphics.setColor(paramArrayOfColor[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 407 */     int i = this.width - this.vsbSpace - 2;
/* 408 */     paramGraphics.fillRect(1, this.height - SCROLLBAR, i, 2);
/* 409 */     paramGraphics.fillRect(0, this.height - SCROLLBAR, 1, SCROLLBAR);
/* 410 */     paramGraphics.fillRect(1 + i, this.height - SCROLLBAR, 1, SCROLLBAR);
/*     */     
/*     */     try {
/* 413 */       graphics.translate(1, this.height - SCROLLBAR - 2);
/* 414 */       this.hsb.paint(graphics, paramArrayOfColor, paramBoolean);
/*     */     } finally {
/*     */       
/* 417 */       graphics.dispose();
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
/*     */   void paintVerScrollbar(Graphics paramGraphics, Color[] paramArrayOfColor, boolean paramBoolean) {
/* 434 */     if (this.vsbSpace <= 0) {
/*     */       return;
/*     */     }
/* 437 */     Graphics graphics = paramGraphics.create();
/* 438 */     paramGraphics.setColor(paramArrayOfColor[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 443 */     int i = this.height - this.hsbSpace - 2;
/* 444 */     paramGraphics.fillRect(this.width - SCROLLBAR, 1, 2, i);
/* 445 */     paramGraphics.fillRect(this.width - SCROLLBAR, 0, SCROLLBAR, 1);
/* 446 */     paramGraphics.fillRect(this.width - SCROLLBAR, 1 + i, SCROLLBAR, 1);
/*     */     
/*     */     try {
/* 449 */       graphics.translate(this.width - SCROLLBAR - 2, 1);
/* 450 */       this.vsb.paint(graphics, paramArrayOfColor, paramBoolean);
/*     */     } finally {
/*     */       
/* 453 */       graphics.dispose();
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
/*     */   public void handleJavaMouseEvent(MouseEvent paramMouseEvent) {
/* 469 */     super.handleJavaMouseEvent(paramMouseEvent);
/* 470 */     int i = paramMouseEvent.getModifiers();
/* 471 */     int j = paramMouseEvent.getID();
/* 472 */     int k = paramMouseEvent.getX();
/* 473 */     int m = paramMouseEvent.getY();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 478 */     if ((i & 0x10) == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 482 */     switch (j) {
/*     */       case 501:
/* 484 */         if (inVerticalScrollbar(k, m)) {
/* 485 */           this.active = 1;
/* 486 */           int n = this.height - this.hsbSpace - 2;
/* 487 */           this.vsb.handleMouseEvent(j, i, k - this.width - SCROLLBAR + 2, m - 1); break;
/* 488 */         }  if (inHorizontalScrollbar(k, m)) {
/* 489 */           this.active = 2;
/* 490 */           int n = this.width - 2 - this.vsbSpace;
/* 491 */           this.hsb.handleMouseEvent(j, i, k - 1, m - this.height - SCROLLBAR + 2);
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 502:
/* 499 */         if (this.active == 1) {
/* 500 */           this.vsb.handleMouseEvent(j, i, k, m); break;
/* 501 */         }  if (this.active == 2) {
/* 502 */           this.hsb.handleMouseEvent(j, i, k, m);
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 506:
/* 508 */         if (this.active == 1) {
/* 509 */           int n = this.height - 2 - this.hsbSpace;
/* 510 */           this.vsb.handleMouseEvent(j, i, k - this.width - SCROLLBAR + 2, m - 1); break;
/* 511 */         }  if (this.active == 2) {
/* 512 */           int n = this.width - 2 - this.vsbSpace;
/* 513 */           this.hsb.handleMouseEvent(j, i, k - 1, m - this.height - SCROLLBAR + 2);
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyValue(XScrollbar paramXScrollbar, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 523 */     if (paramXScrollbar == this.vsb) {
/* 524 */       scroll(-1, paramInt2, 1, paramInt1);
/* 525 */     } else if ((XHorizontalScrollbar)paramXScrollbar == this.hsb) {
/* 526 */       scroll(paramInt2, -1, 2, paramInt1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean inVerticalScrollbar(int paramInt1, int paramInt2) {
/* 534 */     if (this.vsbSpace <= 0) {
/* 535 */       return false;
/*     */     }
/* 537 */     int i = this.height - 1 - this.hsbSpace;
/* 538 */     return (paramInt1 >= this.width - SCROLLBAR - 2 && paramInt1 < this.width && paramInt2 >= 1 && paramInt2 < i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean inHorizontalScrollbar(int paramInt1, int paramInt2) {
/* 545 */     if (this.hsbSpace <= 0) {
/* 546 */       return false;
/*     */     }
/* 548 */     int i = this.width - 1 - this.vsbSpace;
/* 549 */     return (paramInt1 >= 1 && paramInt1 < i && paramInt2 >= this.height - SCROLLBAR - 2 && paramInt2 < this.height);
/*     */   }
/*     */   
/*     */   private Component getScrollChild() {
/* 553 */     ScrollPane scrollPane = (ScrollPane)this.target;
/* 554 */     Component component = null;
/*     */     try {
/* 556 */       component = scrollPane.getComponent(0);
/* 557 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*     */ 
/*     */     
/* 560 */     return component;
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
/*     */   public void print(Graphics paramGraphics) {
/*     */     Dimension dimension2;
/* 573 */     ScrollPane scrollPane = (ScrollPane)this.target;
/* 574 */     Dimension dimension1 = scrollPane.size();
/* 575 */     Color color1 = scrollPane.getBackground();
/* 576 */     Color color2 = scrollPane.getForeground();
/* 577 */     Point point = scrollPane.getScrollPosition();
/* 578 */     Component component = getScrollChild();
/*     */     
/* 580 */     if (component != null) {
/* 581 */       dimension2 = component.size();
/*     */     } else {
/* 583 */       dimension2 = new Dimension(0, 0);
/*     */     } 
/* 585 */     int i = scrollPane.getScrollbarDisplayPolicy();
/*     */ 
/*     */     
/* 588 */     switch (i) {
/*     */       case 2:
/* 590 */         this.hsbSpace = this.vsbSpace = 0;
/*     */         break;
/*     */       case 1:
/* 593 */         this.hsbSpace = this.vsbSpace = SCROLLBAR;
/*     */         break;
/*     */       case 0:
/* 596 */         this.hsbSpace = (dimension2.width <= dimension1.width - 2) ? 0 : SCROLLBAR;
/* 597 */         this.vsbSpace = (dimension2.height <= dimension1.height - 2) ? 0 : SCROLLBAR;
/*     */         
/* 599 */         if (this.hsbSpace == 0 && this.vsbSpace != 0) {
/* 600 */           this.hsbSpace = (dimension2.width <= dimension1.width - SCROLLBAR - 2) ? 0 : SCROLLBAR;
/*     */         }
/* 602 */         if (this.vsbSpace == 0 && this.hsbSpace != 0) {
/* 603 */           this.vsbSpace = (dimension2.height <= dimension1.height - SCROLLBAR - 2) ? 0 : SCROLLBAR;
/*     */         }
/*     */         break;
/*     */     } 
/* 607 */     int i2 = 0, i1 = i2, n = i1, m = n; boolean bool2 = m, bool1 = bool2; int k = bool1, j = k;
/*     */     
/* 609 */     if (this.vsbSpace > 0) {
/* 610 */       bool1 = false;
/* 611 */       j = dimension1.height - 2 - this.hsbSpace;
/* 612 */       m = Math.max(dimension2.height - j, 0);
/* 613 */       i1 = point.y;
/*     */     } 
/* 615 */     if (this.hsbSpace > 0) {
/* 616 */       bool2 = false;
/* 617 */       k = dimension1.width - 2 - this.vsbSpace;
/* 618 */       n = Math.max(dimension2.width - k, 0);
/* 619 */       i2 = point.x;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 624 */     int i3 = dimension1.width - this.vsbSpace;
/* 625 */     int i4 = dimension1.height - this.hsbSpace;
/*     */     
/* 627 */     paramGraphics.setColor(color1);
/* 628 */     paramGraphics.fillRect(0, 0, dimension1.width, dimension1.height);
/*     */     
/* 630 */     if (this.hsbSpace > 0) {
/* 631 */       int i5 = dimension1.width - this.vsbSpace;
/* 632 */       paramGraphics.fillRect(1, dimension1.height - SCROLLBAR - 3, i5 - 1, SCROLLBAR - 3);
/* 633 */       Graphics graphics = paramGraphics.create();
/*     */       try {
/* 635 */         graphics.translate(0, dimension1.height - SCROLLBAR - 2);
/* 636 */         drawScrollbar(graphics, color1, SCROLLBAR - 2, i5, bool2, n, i2, k, true);
/*     */       } finally {
/*     */         
/* 639 */         graphics.dispose();
/*     */       } 
/*     */     } 
/* 642 */     if (this.vsbSpace > 0) {
/* 643 */       int i5 = dimension1.height - this.hsbSpace;
/* 644 */       paramGraphics.fillRect(dimension1.width - SCROLLBAR - 3, 1, SCROLLBAR - 3, i5 - 1);
/* 645 */       Graphics graphics = paramGraphics.create();
/*     */       try {
/* 647 */         graphics.translate(dimension1.width - SCROLLBAR - 2, 0);
/* 648 */         drawScrollbar(graphics, color1, SCROLLBAR - 2, i5, bool1, m, i1, j, false);
/*     */       } finally {
/*     */         
/* 651 */         graphics.dispose();
/*     */       } 
/*     */     } 
/*     */     
/* 655 */     draw3DRect(paramGraphics, color1, 0, 0, i3 - 1, i4 - 1, false);
/*     */     
/* 657 */     this.target.print(paramGraphics);
/* 658 */     scrollPane.printComponents(paramGraphics);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XScrollPanePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */