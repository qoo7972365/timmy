/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.BufferedImage;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.X11GraphicsConfig;
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
/*     */ abstract class XScrollbar
/*     */ {
/*  40 */   private static PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XScrollbar");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private static XScrollRepeater scroller = new XScrollRepeater(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private XScrollRepeater i_scroller = new XScrollRepeater(null);
/*     */   
/*     */   private static final int MIN_THUMB_H = 5;
/*     */   
/*     */   private static final int ARROW_IND = 1;
/*     */   
/*     */   XScrollbarClient sb;
/*     */   
/*     */   private int val;
/*     */   
/*     */   private int min;
/*     */   
/*     */   private int max;
/*     */   
/*     */   private int vis;
/*     */   
/*     */   private int line;
/*     */   
/*     */   private int page;
/*     */   
/*     */   private boolean needsRepaint = true;
/*     */   private boolean pressed = false;
/*     */   private boolean dragging = false;
/*     */   Polygon firstArrow;
/*     */   Polygon secondArrow;
/*     */   int width;
/*     */   int height;
/*     */   int barWidth;
/*     */   int barLength;
/*     */   int arrowArea;
/*     */   int alignment;
/*     */   public static final int ALIGNMENT_VERTICAL = 1;
/*     */   public static final int ALIGNMENT_HORIZONTAL = 2;
/*     */   int mode;
/*     */   Point thumbOffset;
/*     */   private Rectangle prevThumb;
/*     */   
/*     */   public XScrollbar(int paramInt, XScrollbarClient paramXScrollbarClient) {
/*  89 */     this.sb = paramXScrollbarClient;
/*  90 */     this.alignment = paramInt;
/*     */   }
/*     */   
/*     */   public boolean needsRepaint() {
/*  94 */     return this.needsRepaint;
/*     */   }
/*     */   
/*     */   void notifyValue(int paramInt) {
/*  98 */     notifyValue(paramInt, false);
/*     */   }
/*     */   
/*     */   void notifyValue(int paramInt, final boolean isAdjusting) {
/* 102 */     if (paramInt < this.min) {
/* 103 */       paramInt = this.min;
/* 104 */     } else if (paramInt > this.max - this.vis) {
/* 105 */       paramInt = this.max - this.vis;
/*     */     } 
/* 107 */     final int value = paramInt;
/* 108 */     final int mode = this.mode;
/* 109 */     if (this.sb != null && (i != this.val || !this.pressed)) {
/* 110 */       SunToolkit.executeOnEventHandlerThread(this.sb.getEventSource(), new Runnable() {
/*     */             public void run() {
/* 112 */               XScrollbar.this.sb.notifyValue(XScrollbar.this, mode, value, isAdjusting);
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract void rebuildArrows();
/*     */   
/*     */   public void setSize(int paramInt1, int paramInt2) {
/* 121 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 122 */       log.finer("Setting scroll bar " + this + " size to " + paramInt1 + "x" + paramInt2);
/*     */     }
/* 124 */     this.width = paramInt1;
/* 125 */     this.height = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Polygon createArrowShape(boolean paramBoolean1, boolean paramBoolean2) {
/* 132 */     Polygon polygon = new Polygon();
/*     */ 
/*     */     
/* 135 */     if (paramBoolean1) {
/* 136 */       int i = this.width / 2 - getArrowWidth() / 2;
/* 137 */       boolean bool = paramBoolean2 ? true : (this.barLength - 1);
/* 138 */       int j = paramBoolean2 ? getArrowWidth() : (this.barLength - getArrowWidth() - 1);
/* 139 */       polygon.addPoint(i + getArrowWidth() / 2, bool);
/* 140 */       polygon.addPoint(i + getArrowWidth(), j);
/* 141 */       polygon.addPoint(i, j);
/* 142 */       polygon.addPoint(i + getArrowWidth() / 2, bool);
/*     */     } else {
/* 144 */       int i = this.height / 2 - getArrowWidth() / 2;
/* 145 */       boolean bool = paramBoolean2 ? true : (this.barLength - 1);
/* 146 */       int j = paramBoolean2 ? getArrowWidth() : (this.barLength - getArrowWidth() - 1);
/* 147 */       polygon.addPoint(bool, i + getArrowWidth() / 2);
/* 148 */       polygon.addPoint(j, i + getArrowWidth());
/* 149 */       polygon.addPoint(j, i);
/* 150 */       polygon.addPoint(bool, i + getArrowWidth() / 2);
/*     */     } 
/* 152 */     return polygon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Rectangle getThumbArea();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void paint(Graphics paramGraphics, Color[] paramArrayOfColor, boolean paramBoolean) {
/* 169 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 170 */       log.finer("Painting scrollbar " + this);
/*     */     }
/*     */     
/* 173 */     boolean bool = false;
/* 174 */     Graphics2D graphics2D = null;
/* 175 */     BufferedImage bufferedImage = null;
/* 176 */     if (!(paramGraphics instanceof Graphics2D)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 181 */       X11GraphicsConfig x11GraphicsConfig = (X11GraphicsConfig)this.sb.getEventSource().getGraphicsConfiguration();
/* 182 */       bufferedImage = x11GraphicsConfig.createCompatibleImage(this.width, this.height);
/* 183 */       graphics2D = bufferedImage.createGraphics();
/* 184 */       bool = true;
/*     */     } else {
/* 186 */       graphics2D = (Graphics2D)paramGraphics;
/*     */     } 
/*     */     try {
/* 189 */       Rectangle rectangle = calculateThumbRect();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 195 */       this.prevThumb = rectangle;
/*     */ 
/*     */       
/* 198 */       Color color1 = paramArrayOfColor[0];
/* 199 */       Color color2 = new Color(MotifColorUtilities.calculateSelectFromBackground(color1.getRed(), color1.getGreen(), color1.getBlue()));
/* 200 */       Color color3 = new Color(MotifColorUtilities.calculateBottomShadowFromBackground(color1.getRed(), color1.getGreen(), color1.getBlue()));
/* 201 */       Color color4 = new Color(MotifColorUtilities.calculateTopShadowFromBackground(color1.getRed(), color1.getGreen(), color1.getBlue()));
/*     */       
/* 203 */       XToolkit.awtLock();
/*     */       try {
/* 205 */         XlibWrapper.XFlush(XToolkit.getDisplay());
/*     */       } finally {
/* 207 */         XToolkit.awtUnlock();
/*     */       } 
/*     */       
/* 210 */       if (paramBoolean) {
/*     */         
/* 212 */         graphics2D.setColor(color2);
/* 213 */         if (this.alignment == 2) {
/* 214 */           graphics2D.fillRect(0, 0, rectangle.x, this.height);
/* 215 */           graphics2D.fillRect(rectangle.x + rectangle.width, 0, this.width - rectangle.x + rectangle.width, this.height);
/*     */         } else {
/* 217 */           graphics2D.fillRect(0, 0, this.width, rectangle.y);
/* 218 */           graphics2D.fillRect(0, rectangle.y + rectangle.height, this.width, this.height - rectangle.y + rectangle.height);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 224 */         graphics2D.setColor(color3);
/* 225 */         graphics2D.drawLine(0, 0, this.width - 1, 0);
/* 226 */         graphics2D.drawLine(0, 0, 0, this.height - 1);
/*     */         
/* 228 */         graphics2D.setColor(color4);
/* 229 */         graphics2D.drawLine(1, this.height - 1, this.width - 1, this.height - 1);
/* 230 */         graphics2D.drawLine(this.width - 1, 1, this.width - 1, this.height - 1);
/*     */       } else {
/*     */         
/* 233 */         graphics2D.setColor(color2);
/* 234 */         Rectangle rectangle1 = getThumbArea();
/* 235 */         graphics2D.fill(rectangle1);
/*     */       } 
/*     */       
/* 238 */       if (paramBoolean)
/*     */       {
/* 240 */         paintArrows(graphics2D, paramArrayOfColor[0], color3, color4);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 245 */       graphics2D.setColor(paramArrayOfColor[0]);
/* 246 */       graphics2D.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       
/* 248 */       graphics2D.setColor(color4);
/* 249 */       graphics2D.drawLine(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y);
/*     */       
/* 251 */       graphics2D.drawLine(rectangle.x, rectangle.y, rectangle.x, rectangle.y + rectangle.height);
/*     */ 
/*     */       
/* 254 */       graphics2D.setColor(color3);
/* 255 */       graphics2D.drawLine(rectangle.x + 1, rectangle.y + rectangle.height, rectangle.x + rectangle.width, rectangle.y + rectangle.height);
/*     */ 
/*     */ 
/*     */       
/* 259 */       graphics2D.drawLine(rectangle.x + rectangle.width, rectangle.y + 1, rectangle.x + rectangle.width, rectangle.y + rectangle.height);
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 264 */       if (bool) {
/* 265 */         graphics2D.dispose();
/*     */       }
/*     */     } 
/* 268 */     if (bool) {
/* 269 */       paramGraphics.drawImage(bufferedImage, 0, 0, null);
/*     */     }
/* 271 */     XToolkit.awtLock();
/*     */     try {
/* 273 */       XlibWrapper.XFlush(XToolkit.getDisplay());
/*     */     } finally {
/* 275 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void paintArrows(Graphics2D paramGraphics2D, Color paramColor1, Color paramColor2, Color paramColor3) {
/* 281 */     paramGraphics2D.setColor(paramColor1);
/*     */ 
/*     */     
/* 284 */     if (this.pressed && this.mode == 2) {
/* 285 */       paramGraphics2D.fill(this.firstArrow);
/* 286 */       paramGraphics2D.setColor(paramColor3);
/* 287 */       paramGraphics2D.drawLine(this.firstArrow.xpoints[0], this.firstArrow.ypoints[0], this.firstArrow.xpoints[1], this.firstArrow.ypoints[1]);
/*     */       
/* 289 */       paramGraphics2D.drawLine(this.firstArrow.xpoints[1], this.firstArrow.ypoints[1], this.firstArrow.xpoints[2], this.firstArrow.ypoints[2]);
/*     */       
/* 291 */       paramGraphics2D.setColor(paramColor2);
/* 292 */       paramGraphics2D.drawLine(this.firstArrow.xpoints[2], this.firstArrow.ypoints[2], this.firstArrow.xpoints[0], this.firstArrow.ypoints[0]);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 297 */       paramGraphics2D.fill(this.firstArrow);
/* 298 */       paramGraphics2D.setColor(paramColor2);
/* 299 */       paramGraphics2D.drawLine(this.firstArrow.xpoints[0], this.firstArrow.ypoints[0], this.firstArrow.xpoints[1], this.firstArrow.ypoints[1]);
/*     */       
/* 301 */       paramGraphics2D.drawLine(this.firstArrow.xpoints[1], this.firstArrow.ypoints[1], this.firstArrow.xpoints[2], this.firstArrow.ypoints[2]);
/*     */       
/* 303 */       paramGraphics2D.setColor(paramColor3);
/* 304 */       paramGraphics2D.drawLine(this.firstArrow.xpoints[2], this.firstArrow.ypoints[2], this.firstArrow.xpoints[0], this.firstArrow.ypoints[0]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 309 */     paramGraphics2D.setColor(paramColor1);
/*     */     
/* 311 */     if (this.pressed && this.mode == 1) {
/* 312 */       paramGraphics2D.fill(this.secondArrow);
/* 313 */       paramGraphics2D.setColor(paramColor3);
/* 314 */       paramGraphics2D.drawLine(this.secondArrow.xpoints[0], this.secondArrow.ypoints[0], this.secondArrow.xpoints[1], this.secondArrow.ypoints[1]);
/*     */       
/* 316 */       paramGraphics2D.setColor(paramColor2);
/* 317 */       paramGraphics2D.drawLine(this.secondArrow.xpoints[1], this.secondArrow.ypoints[1], this.secondArrow.xpoints[2], this.secondArrow.ypoints[2]);
/*     */       
/* 319 */       paramGraphics2D.drawLine(this.secondArrow.xpoints[2], this.secondArrow.ypoints[2], this.secondArrow.xpoints[0], this.secondArrow.ypoints[0]);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 324 */       paramGraphics2D.fill(this.secondArrow);
/* 325 */       paramGraphics2D.setColor(paramColor2);
/* 326 */       paramGraphics2D.drawLine(this.secondArrow.xpoints[0], this.secondArrow.ypoints[0], this.secondArrow.xpoints[1], this.secondArrow.ypoints[1]);
/*     */       
/* 328 */       paramGraphics2D.setColor(paramColor3);
/* 329 */       paramGraphics2D.drawLine(this.secondArrow.xpoints[1], this.secondArrow.ypoints[1], this.secondArrow.xpoints[2], this.secondArrow.ypoints[2]);
/*     */       
/* 331 */       paramGraphics2D.drawLine(this.secondArrow.xpoints[2], this.secondArrow.ypoints[2], this.secondArrow.xpoints[0], this.secondArrow.ypoints[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void startScrolling() {
/* 342 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 343 */       log.finer("Start scrolling on " + this);
/*     */     }
/*     */     
/* 346 */     scroll();
/*     */ 
/*     */     
/* 349 */     if (scroller == null) {
/*     */ 
/*     */       
/* 352 */       scroller = new XScrollRepeater(this);
/*     */     } else {
/* 354 */       scroller.setScrollbar(this);
/*     */     } 
/* 356 */     scroller.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void startScrollingInstance() {
/* 364 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 365 */       log.finer("Start scrolling on " + this);
/*     */     }
/*     */     
/* 368 */     scroll();
/*     */     
/* 370 */     this.i_scroller.setScrollbar(this);
/* 371 */     this.i_scroller.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void stopScrollingInstance() {
/* 379 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 380 */       log.finer("Stop scrolling on " + this);
/*     */     }
/*     */     
/* 383 */     this.i_scroller.stop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMode(int paramInt) {
/* 391 */     this.mode = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void scroll() {
/* 399 */     switch (this.mode) {
/*     */       case 2:
/* 401 */         notifyValue(this.val - this.line);
/*     */         return;
/*     */       
/*     */       case 1:
/* 405 */         notifyValue(this.val + this.line);
/*     */         return;
/*     */       
/*     */       case 3:
/* 409 */         notifyValue(this.val - this.page);
/*     */         return;
/*     */       
/*     */       case 4:
/* 413 */         notifyValue(this.val + this.page);
/*     */         return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isInArrow(int paramInt1, int paramInt2) {
/* 422 */     int i = (this.alignment == 2) ? paramInt1 : paramInt2;
/* 423 */     int j = getArrowAreaWidth();
/*     */     
/* 425 */     if (i < j || i > this.barLength - j + 1) {
/* 426 */       return true;
/*     */     }
/* 428 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isInThumb(int paramInt1, int paramInt2) {
/* 438 */     Rectangle rectangle = calculateThumbRect();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 443 */     rectangle.x--;
/* 444 */     rectangle.width += 3;
/* 445 */     rectangle.height++;
/* 446 */     return rectangle.contains(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract boolean beforeThumb(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     Rectangle rectangle;
/* 463 */     if ((paramInt2 & 0x10) == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 467 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*     */       String str;
/* 469 */       switch (paramInt1) {
/*     */         case 501:
/* 471 */           str = "press";
/*     */           break;
/*     */         case 502:
/* 474 */           str = "release";
/*     */           break;
/*     */         case 506:
/* 477 */           str = "drag";
/*     */           break;
/*     */         default:
/* 480 */           str = "other"; break;
/*     */       } 
/* 482 */       log.finer("Mouse " + str + " event in scroll bar " + this + "x = " + paramInt3 + ", y = " + paramInt4 + ", on arrow: " + 
/*     */           
/* 484 */           isInArrow(paramInt3, paramInt4) + ", on thumb: " + 
/* 485 */           isInThumb(paramInt3, paramInt4) + ", before thumb: " + beforeThumb(paramInt3, paramInt4) + ", thumb rect" + 
/* 486 */           calculateThumbRect());
/*     */     } 
/* 488 */     switch (paramInt1) {
/*     */       case 501:
/* 490 */         if (isInArrow(paramInt3, paramInt4)) {
/* 491 */           this.pressed = true;
/* 492 */           if (beforeThumb(paramInt3, paramInt4)) {
/* 493 */             this.mode = 2;
/*     */           } else {
/* 495 */             this.mode = 1;
/*     */           } 
/* 497 */           this.sb.repaintScrollbarRequest(this);
/* 498 */           startScrolling();
/*     */           
/*     */           break;
/*     */         } 
/* 502 */         if (isInThumb(paramInt3, paramInt4)) {
/* 503 */           this.mode = 5;
/*     */         } else {
/* 505 */           if (beforeThumb(paramInt3, paramInt4)) {
/* 506 */             this.mode = 3;
/*     */           } else {
/* 508 */             this.mode = 4;
/*     */           } 
/* 510 */           startScrolling();
/*     */         } 
/* 512 */         rectangle = calculateThumbRect();
/* 513 */         this.thumbOffset = new Point(paramInt3 - rectangle.x, paramInt4 - rectangle.y);
/*     */         break;
/*     */       
/*     */       case 502:
/* 517 */         this.pressed = false;
/* 518 */         this.sb.repaintScrollbarRequest(this);
/* 519 */         scroller.stop();
/* 520 */         if (this.dragging) {
/* 521 */           handleTrackEvent(paramInt3, paramInt4, false);
/* 522 */           this.dragging = false;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 506:
/* 527 */         this.dragging = true;
/* 528 */         handleTrackEvent(paramInt3, paramInt4, true);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   private void handleTrackEvent(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 533 */     if (this.mode == 5) {
/* 534 */       notifyValue(calculateCursorOffset(paramInt1, paramInt2), paramBoolean);
/*     */     }
/*     */   }
/*     */   
/*     */   private int calculateCursorOffset(int paramInt1, int paramInt2) {
/* 539 */     if (this.alignment == 2) {
/* 540 */       if (this.dragging) {
/* 541 */         return Math.max(0, (int)((paramInt1 - this.thumbOffset.x + getArrowAreaWidth()) / getScaleFactor())) + this.min;
/*     */       }
/* 543 */       return Math.max(0, (int)((paramInt1 - getArrowAreaWidth()) / getScaleFactor())) + this.min;
/*     */     } 
/* 545 */     if (this.dragging) {
/* 546 */       return Math.max(0, (int)((paramInt2 - this.thumbOffset.y + getArrowAreaWidth()) / getScaleFactor())) + this.min;
/*     */     }
/* 548 */     return Math.max(0, (int)((paramInt2 - getArrowAreaWidth()) / getScaleFactor())) + this.min;
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
/*     */   synchronized void setValues(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 581 */     if (paramInt4 <= paramInt3) {
/* 582 */       paramInt4 = paramInt3 + 1;
/*     */     }
/* 584 */     if (paramInt2 > paramInt4 - paramInt3) {
/* 585 */       paramInt2 = paramInt4 - paramInt3;
/*     */     }
/* 587 */     if (paramInt2 < 1) {
/* 588 */       paramInt2 = 1;
/*     */     }
/* 590 */     if (paramInt1 < paramInt3) {
/* 591 */       paramInt1 = paramInt3;
/*     */     }
/* 593 */     if (paramInt1 > paramInt4 - paramInt2) {
/* 594 */       paramInt1 = paramInt4 - paramInt2;
/*     */     }
/*     */     
/* 597 */     this.val = paramInt1;
/* 598 */     this.vis = paramInt2;
/* 599 */     this.min = paramInt3;
/* 600 */     this.max = paramInt4;
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
/*     */   synchronized void setValues(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 618 */     setValues(paramInt1, paramInt2, paramInt3, paramInt4);
/* 619 */     setUnitIncrement(paramInt5);
/* 620 */     setBlockIncrement(paramInt6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getValue() {
/* 629 */     return this.val;
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
/*     */   synchronized void setValue(int paramInt) {
/* 644 */     setValues(paramInt, this.vis, this.min, this.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMinimum() {
/* 653 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void setMinimum(int paramInt) {
/* 664 */     setValues(this.val, this.vis, paramInt, this.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMaximum() {
/* 673 */     return this.max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void setMaximum(int paramInt) {
/* 684 */     setValues(this.val, this.vis, this.min, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getVisibleAmount() {
/* 691 */     return this.vis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void setVisibleAmount(int paramInt) {
/* 700 */     setValues(this.val, paramInt, this.min, this.max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void setUnitIncrement(int paramInt) {
/* 710 */     this.line = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getUnitIncrement() {
/* 717 */     return this.line;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void setBlockIncrement(int paramInt) {
/* 727 */     this.page = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getBlockIncrement() {
/* 734 */     return this.page;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getArrowWidth() {
/* 741 */     return getArrowAreaWidth() - 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getArrowAreaWidth() {
/* 748 */     return this.arrowArea;
/*     */   }
/*     */   
/*     */   void calculateArrowWidth() {
/* 752 */     if (this.barLength < 2 * this.barWidth + 5 + 2) {
/* 753 */       this.arrowArea = (this.barLength - 5 + 2) / 2 - 1;
/*     */     } else {
/*     */       
/* 756 */       this.arrowArea = this.barWidth - 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getScaleFactor() {
/* 765 */     return (this.barLength - 2 * getArrowAreaWidth()) / Math.max(1, this.max - this.min);
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
/*     */   protected Rectangle calculateThumbRect() {
/*     */     int i;
/*     */     byte b;
/* 783 */     int j = 0;
/* 784 */     int k = getArrowAreaWidth();
/* 785 */     Rectangle rectangle = new Rectangle(0, 0, 0, 0);
/*     */     
/* 787 */     float f2 = (this.barLength - 2 * k - 1);
/*     */     
/* 789 */     if (this.alignment == 2) {
/* 790 */       i = 5;
/* 791 */       b = this.height - 3;
/*     */     } else {
/*     */       
/* 794 */       i = this.width - 3;
/* 795 */       b = 5;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 800 */     float f1 = (this.max - this.min);
/*     */ 
/*     */     
/* 803 */     float f3 = f2 / f1;
/*     */ 
/*     */ 
/*     */     
/* 807 */     float f4 = this.vis * f3;
/*     */     
/* 809 */     if (this.alignment == 2) {
/*     */       
/* 811 */       int m = (int)(f4 + 0.5D);
/* 812 */       int n = i;
/* 813 */       if (m > n) {
/* 814 */         rectangle.width = m;
/*     */       } else {
/*     */         
/* 817 */         rectangle.width = n;
/* 818 */         j = n;
/*     */       } 
/* 820 */       rectangle.height = b;
/*     */     } else {
/*     */       
/* 823 */       rectangle.width = i;
/*     */ 
/*     */       
/* 826 */       int m = (int)(f4 + 0.5D);
/* 827 */       byte b1 = b;
/* 828 */       if (m > b1) {
/* 829 */         rectangle.height = m;
/*     */       } else {
/*     */         
/* 832 */         rectangle.height = b1;
/* 833 */         j = b1;
/*     */       } 
/*     */     } 
/*     */     
/* 837 */     if (j != 0) {
/* 838 */       f2 -= j;
/* 839 */       f1 -= this.vis;
/* 840 */       f3 = f2 / f1;
/*     */     } 
/*     */     
/* 843 */     if (this.alignment == 2) {
/* 844 */       rectangle.x = (int)(((this.val - this.min) * f3) + 0.5D) + k;
/*     */ 
/*     */       
/* 847 */       rectangle.y = 1;
/*     */     }
/*     */     else {
/*     */       
/* 851 */       rectangle.x = 1;
/* 852 */       rectangle.y = (int)(((this.val - this.min) * f3) + 0.5D) + k;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 860 */     return rectangle;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 864 */     return getClass() + "[" + this.width + "x" + this.height + "," + this.barWidth + "x" + this.barLength + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XScrollbar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */