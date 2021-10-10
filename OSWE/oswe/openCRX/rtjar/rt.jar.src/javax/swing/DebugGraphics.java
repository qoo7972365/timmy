/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Container;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Image;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.image.FilteredImageSource;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.awt.image.ImageProducer;
/*      */ import java.io.PrintStream;
/*      */ import java.text.AttributedCharacterIterator;
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
/*      */ public class DebugGraphics
/*      */   extends Graphics
/*      */ {
/*      */   Graphics graphics;
/*      */   Image buffer;
/*      */   int debugOptions;
/*   53 */   int graphicsID = graphicsCount++;
/*      */   int xOffset;
/*   55 */   private static int graphicsCount = 0; int yOffset;
/*   56 */   private static ImageIcon imageLoadingIcon = new ImageIcon();
/*      */   
/*      */   public static final int LOG_OPTION = 1;
/*      */   
/*      */   public static final int FLASH_OPTION = 2;
/*      */   
/*      */   public static final int BUFFERED_OPTION = 4;
/*      */   
/*      */   public static final int NONE_OPTION = -1;
/*      */ 
/*      */   
/*      */   static {
/*   68 */     JComponent.DEBUG_GRAPHICS_LOADED = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DebugGraphics() {
/*   77 */     this.buffer = null;
/*   78 */     this.xOffset = this.yOffset = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DebugGraphics(Graphics paramGraphics, JComponent paramJComponent) {
/*   89 */     this(paramGraphics);
/*   90 */     setDebugOptions(paramJComponent.shouldDebugGraphics());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DebugGraphics(Graphics paramGraphics) {
/*  100 */     this();
/*  101 */     this.graphics = paramGraphics;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Graphics create() {
/*  110 */     DebugGraphics debugGraphics = new DebugGraphics();
/*  111 */     debugGraphics.graphics = this.graphics.create();
/*  112 */     debugGraphics.debugOptions = this.debugOptions;
/*  113 */     debugGraphics.buffer = this.buffer;
/*      */     
/*  115 */     return debugGraphics;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Graphics create(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  124 */     DebugGraphics debugGraphics = new DebugGraphics();
/*  125 */     debugGraphics.graphics = this.graphics.create(paramInt1, paramInt2, paramInt3, paramInt4);
/*  126 */     debugGraphics.debugOptions = this.debugOptions;
/*  127 */     debugGraphics.buffer = this.buffer;
/*  128 */     this.xOffset += paramInt1;
/*  129 */     this.yOffset += paramInt2;
/*      */     
/*  131 */     return debugGraphics;
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
/*      */   public static void setFlashColor(Color paramColor) {
/*  143 */     (info()).flashColor = paramColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Color flashColor() {
/*  151 */     return (info()).flashColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setFlashTime(int paramInt) {
/*  158 */     (info()).flashTime = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int flashTime() {
/*  166 */     return (info()).flashTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setFlashCount(int paramInt) {
/*  173 */     (info()).flashCount = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int flashCount() {
/*  180 */     return (info()).flashCount;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setLogStream(PrintStream paramPrintStream) {
/*  186 */     (info()).stream = paramPrintStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static PrintStream logStream() {
/*  193 */     return (info()).stream;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFont(Font paramFont) {
/*  199 */     if (debugLog()) {
/*  200 */       info().log(toShortString() + " Setting font: " + paramFont);
/*      */     }
/*  202 */     this.graphics.setFont(paramFont);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getFont() {
/*  209 */     return this.graphics.getFont();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColor(Color paramColor) {
/*  215 */     if (debugLog()) {
/*  216 */       info().log(toShortString() + " Setting color: " + paramColor);
/*      */     }
/*  218 */     this.graphics.setColor(paramColor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getColor() {
/*  225 */     return this.graphics.getColor();
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
/*      */   public FontMetrics getFontMetrics() {
/*  237 */     return this.graphics.getFontMetrics();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FontMetrics getFontMetrics(Font paramFont) {
/*  244 */     return this.graphics.getFontMetrics(paramFont);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void translate(int paramInt1, int paramInt2) {
/*  251 */     if (debugLog()) {
/*  252 */       info().log(toShortString() + " Translating by: " + new Point(paramInt1, paramInt2));
/*      */     }
/*      */     
/*  255 */     this.xOffset += paramInt1;
/*  256 */     this.yOffset += paramInt2;
/*  257 */     this.graphics.translate(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPaintMode() {
/*  264 */     if (debugLog()) {
/*  265 */       info().log(toShortString() + " Setting paint mode");
/*      */     }
/*  267 */     this.graphics.setPaintMode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setXORMode(Color paramColor) {
/*  274 */     if (debugLog()) {
/*  275 */       info().log(toShortString() + " Setting XOR mode: " + paramColor);
/*      */     }
/*  277 */     this.graphics.setXORMode(paramColor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getClipBounds() {
/*  284 */     return this.graphics.getClipBounds();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clipRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  291 */     this.graphics.clipRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*  292 */     if (debugLog()) {
/*  293 */       info().log(toShortString() + " Setting clipRect: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4) + " New clipRect: " + this.graphics
/*      */           
/*  295 */           .getClip());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClip(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  303 */     this.graphics.setClip(paramInt1, paramInt2, paramInt3, paramInt4);
/*  304 */     if (debugLog()) {
/*  305 */       info().log(toShortString() + " Setting new clipRect: " + this.graphics
/*  306 */           .getClip());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getClip() {
/*  314 */     return this.graphics.getClip();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClip(Shape paramShape) {
/*  321 */     this.graphics.setClip(paramShape);
/*  322 */     if (debugLog()) {
/*  323 */       info().log(toShortString() + " Setting new clipRect: " + this.graphics
/*  324 */           .getClip());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  332 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  334 */     if (debugLog()) {
/*  335 */       info().log(toShortString() + " Drawing rect: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  340 */     if (isDrawingBuffer()) {
/*  341 */       if (debugBuffered()) {
/*  342 */         Graphics graphics = debugGraphics();
/*      */         
/*  344 */         graphics.drawRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*  345 */         graphics.dispose();
/*      */       } 
/*  347 */     } else if (debugFlash()) {
/*  348 */       Color color = getColor();
/*  349 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  351 */       for (byte b = 0; b < i; b++) {
/*  352 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  353 */         this.graphics.drawRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*  354 */         Toolkit.getDefaultToolkit().sync();
/*  355 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  357 */       this.graphics.setColor(color);
/*      */     } 
/*  359 */     this.graphics.drawRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  366 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  368 */     if (debugLog()) {
/*  369 */       info().log(toShortString() + " Filling rect: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  374 */     if (isDrawingBuffer()) {
/*  375 */       if (debugBuffered()) {
/*  376 */         Graphics graphics = debugGraphics();
/*      */         
/*  378 */         graphics.fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*  379 */         graphics.dispose();
/*      */       } 
/*  381 */     } else if (debugFlash()) {
/*  382 */       Color color = getColor();
/*  383 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  385 */       for (byte b = 0; b < i; b++) {
/*  386 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  387 */         this.graphics.fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*  388 */         Toolkit.getDefaultToolkit().sync();
/*  389 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  391 */       this.graphics.setColor(color);
/*      */     } 
/*  393 */     this.graphics.fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  400 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  402 */     if (debugLog()) {
/*  403 */       info().log(toShortString() + " Clearing rect: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  408 */     if (isDrawingBuffer()) {
/*  409 */       if (debugBuffered()) {
/*  410 */         Graphics graphics = debugGraphics();
/*      */         
/*  412 */         graphics.clearRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*  413 */         graphics.dispose();
/*      */       } 
/*  415 */     } else if (debugFlash()) {
/*  416 */       Color color = getColor();
/*  417 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  419 */       for (byte b = 0; b < i; b++) {
/*  420 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  421 */         this.graphics.clearRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*  422 */         Toolkit.getDefaultToolkit().sync();
/*  423 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  425 */       this.graphics.setColor(color);
/*      */     } 
/*  427 */     this.graphics.clearRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRoundRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  435 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  437 */     if (debugLog()) {
/*  438 */       info().log(toShortString() + " Drawing round rect: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4) + " arcWidth: " + paramInt5 + " archHeight: " + paramInt6);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  444 */     if (isDrawingBuffer()) {
/*  445 */       if (debugBuffered()) {
/*  446 */         Graphics graphics = debugGraphics();
/*      */         
/*  448 */         graphics.drawRoundRect(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */         
/*  450 */         graphics.dispose();
/*      */       } 
/*  452 */     } else if (debugFlash()) {
/*  453 */       Color color = getColor();
/*  454 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  456 */       for (byte b = 0; b < i; b++) {
/*  457 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  458 */         this.graphics.drawRoundRect(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */         
/*  460 */         Toolkit.getDefaultToolkit().sync();
/*  461 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  463 */       this.graphics.setColor(color);
/*      */     } 
/*  465 */     this.graphics.drawRoundRect(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillRoundRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  473 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  475 */     if (debugLog()) {
/*  476 */       info().log(toShortString() + " Filling round rect: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4) + " arcWidth: " + paramInt5 + " archHeight: " + paramInt6);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  482 */     if (isDrawingBuffer()) {
/*  483 */       if (debugBuffered()) {
/*  484 */         Graphics graphics = debugGraphics();
/*      */         
/*  486 */         graphics.fillRoundRect(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */         
/*  488 */         graphics.dispose();
/*      */       } 
/*  490 */     } else if (debugFlash()) {
/*  491 */       Color color = getColor();
/*  492 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  494 */       for (byte b = 0; b < i; b++) {
/*  495 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  496 */         this.graphics.fillRoundRect(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */         
/*  498 */         Toolkit.getDefaultToolkit().sync();
/*  499 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  501 */       this.graphics.setColor(color);
/*      */     } 
/*  503 */     this.graphics.fillRoundRect(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  510 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  512 */     if (debugLog()) {
/*  513 */       info().log(toShortString() + " Drawing line: from " + 
/*  514 */           pointToString(paramInt1, paramInt2) + " to " + 
/*  515 */           pointToString(paramInt3, paramInt4));
/*      */     }
/*      */     
/*  518 */     if (isDrawingBuffer()) {
/*  519 */       if (debugBuffered()) {
/*  520 */         Graphics graphics = debugGraphics();
/*      */         
/*  522 */         graphics.drawLine(paramInt1, paramInt2, paramInt3, paramInt4);
/*  523 */         graphics.dispose();
/*      */       } 
/*  525 */     } else if (debugFlash()) {
/*  526 */       Color color = getColor();
/*  527 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  529 */       for (byte b = 0; b < i; b++) {
/*  530 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  531 */         this.graphics.drawLine(paramInt1, paramInt2, paramInt3, paramInt4);
/*  532 */         Toolkit.getDefaultToolkit().sync();
/*  533 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  535 */       this.graphics.setColor(color);
/*      */     } 
/*  537 */     this.graphics.drawLine(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw3DRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*  545 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  547 */     if (debugLog()) {
/*  548 */       info().log(toShortString() + " Drawing 3D rect: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4) + " Raised bezel: " + paramBoolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  553 */     if (isDrawingBuffer()) {
/*  554 */       if (debugBuffered()) {
/*  555 */         Graphics graphics = debugGraphics();
/*      */         
/*  557 */         graphics.draw3DRect(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean);
/*  558 */         graphics.dispose();
/*      */       } 
/*  560 */     } else if (debugFlash()) {
/*  561 */       Color color = getColor();
/*  562 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  564 */       for (byte b = 0; b < i; b++) {
/*  565 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  566 */         this.graphics.draw3DRect(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean);
/*  567 */         Toolkit.getDefaultToolkit().sync();
/*  568 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  570 */       this.graphics.setColor(color);
/*      */     } 
/*  572 */     this.graphics.draw3DRect(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fill3DRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*  580 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  582 */     if (debugLog()) {
/*  583 */       info().log(toShortString() + " Filling 3D rect: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4) + " Raised bezel: " + paramBoolean);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  588 */     if (isDrawingBuffer()) {
/*  589 */       if (debugBuffered()) {
/*  590 */         Graphics graphics = debugGraphics();
/*      */         
/*  592 */         graphics.fill3DRect(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean);
/*  593 */         graphics.dispose();
/*      */       } 
/*  595 */     } else if (debugFlash()) {
/*  596 */       Color color = getColor();
/*  597 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  599 */       for (byte b = 0; b < i; b++) {
/*  600 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  601 */         this.graphics.fill3DRect(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean);
/*  602 */         Toolkit.getDefaultToolkit().sync();
/*  603 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  605 */       this.graphics.setColor(color);
/*      */     } 
/*  607 */     this.graphics.fill3DRect(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawOval(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  614 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  616 */     if (debugLog()) {
/*  617 */       info().log(toShortString() + " Drawing oval: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */     }
/*      */ 
/*      */     
/*  621 */     if (isDrawingBuffer()) {
/*  622 */       if (debugBuffered()) {
/*  623 */         Graphics graphics = debugGraphics();
/*      */         
/*  625 */         graphics.drawOval(paramInt1, paramInt2, paramInt3, paramInt4);
/*  626 */         graphics.dispose();
/*      */       } 
/*  628 */     } else if (debugFlash()) {
/*  629 */       Color color = getColor();
/*  630 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  632 */       for (byte b = 0; b < i; b++) {
/*  633 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  634 */         this.graphics.drawOval(paramInt1, paramInt2, paramInt3, paramInt4);
/*  635 */         Toolkit.getDefaultToolkit().sync();
/*  636 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  638 */       this.graphics.setColor(color);
/*      */     } 
/*  640 */     this.graphics.drawOval(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillOval(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  647 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  649 */     if (debugLog()) {
/*  650 */       info().log(toShortString() + " Filling oval: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */     }
/*      */ 
/*      */     
/*  654 */     if (isDrawingBuffer()) {
/*  655 */       if (debugBuffered()) {
/*  656 */         Graphics graphics = debugGraphics();
/*      */         
/*  658 */         graphics.fillOval(paramInt1, paramInt2, paramInt3, paramInt4);
/*  659 */         graphics.dispose();
/*      */       } 
/*  661 */     } else if (debugFlash()) {
/*  662 */       Color color = getColor();
/*  663 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  665 */       for (byte b = 0; b < i; b++) {
/*  666 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  667 */         this.graphics.fillOval(paramInt1, paramInt2, paramInt3, paramInt4);
/*  668 */         Toolkit.getDefaultToolkit().sync();
/*  669 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  671 */       this.graphics.setColor(color);
/*      */     } 
/*  673 */     this.graphics.fillOval(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  681 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  683 */     if (debugLog()) {
/*  684 */       info().log(toShortString() + " Drawing arc: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4) + " startAngle: " + paramInt5 + " arcAngle: " + paramInt6);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  690 */     if (isDrawingBuffer()) {
/*  691 */       if (debugBuffered()) {
/*  692 */         Graphics graphics = debugGraphics();
/*      */         
/*  694 */         graphics.drawArc(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */         
/*  696 */         graphics.dispose();
/*      */       } 
/*  698 */     } else if (debugFlash()) {
/*  699 */       Color color = getColor();
/*  700 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  702 */       for (byte b = 0; b < i; b++) {
/*  703 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  704 */         this.graphics.drawArc(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*  705 */         Toolkit.getDefaultToolkit().sync();
/*  706 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  708 */       this.graphics.setColor(color);
/*      */     } 
/*  710 */     this.graphics.drawArc(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  718 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  720 */     if (debugLog()) {
/*  721 */       info().log(toShortString() + " Filling arc: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4) + " startAngle: " + paramInt5 + " arcAngle: " + paramInt6);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  727 */     if (isDrawingBuffer()) {
/*  728 */       if (debugBuffered()) {
/*  729 */         Graphics graphics = debugGraphics();
/*      */         
/*  731 */         graphics.fillArc(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */         
/*  733 */         graphics.dispose();
/*      */       } 
/*  735 */     } else if (debugFlash()) {
/*  736 */       Color color = getColor();
/*  737 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  739 */       for (byte b = 0; b < i; b++) {
/*  740 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  741 */         this.graphics.fillArc(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*  742 */         Toolkit.getDefaultToolkit().sync();
/*  743 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  745 */       this.graphics.setColor(color);
/*      */     } 
/*  747 */     this.graphics.fillArc(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawPolyline(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*  754 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  756 */     if (debugLog()) {
/*  757 */       info().log(toShortString() + " Drawing polyline:  nPoints: " + paramInt + " X's: " + paramArrayOfint1 + " Y's: " + paramArrayOfint2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  763 */     if (isDrawingBuffer()) {
/*  764 */       if (debugBuffered()) {
/*  765 */         Graphics graphics = debugGraphics();
/*      */         
/*  767 */         graphics.drawPolyline(paramArrayOfint1, paramArrayOfint2, paramInt);
/*  768 */         graphics.dispose();
/*      */       } 
/*  770 */     } else if (debugFlash()) {
/*  771 */       Color color = getColor();
/*  772 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  774 */       for (byte b = 0; b < i; b++) {
/*  775 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  776 */         this.graphics.drawPolyline(paramArrayOfint1, paramArrayOfint2, paramInt);
/*  777 */         Toolkit.getDefaultToolkit().sync();
/*  778 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  780 */       this.graphics.setColor(color);
/*      */     } 
/*  782 */     this.graphics.drawPolyline(paramArrayOfint1, paramArrayOfint2, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawPolygon(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*  789 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  791 */     if (debugLog()) {
/*  792 */       info().log(toShortString() + " Drawing polygon:  nPoints: " + paramInt + " X's: " + paramArrayOfint1 + " Y's: " + paramArrayOfint2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  798 */     if (isDrawingBuffer()) {
/*  799 */       if (debugBuffered()) {
/*  800 */         Graphics graphics = debugGraphics();
/*      */         
/*  802 */         graphics.drawPolygon(paramArrayOfint1, paramArrayOfint2, paramInt);
/*  803 */         graphics.dispose();
/*      */       } 
/*  805 */     } else if (debugFlash()) {
/*  806 */       Color color = getColor();
/*  807 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  809 */       for (byte b = 0; b < i; b++) {
/*  810 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  811 */         this.graphics.drawPolygon(paramArrayOfint1, paramArrayOfint2, paramInt);
/*  812 */         Toolkit.getDefaultToolkit().sync();
/*  813 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  815 */       this.graphics.setColor(color);
/*      */     } 
/*  817 */     this.graphics.drawPolygon(paramArrayOfint1, paramArrayOfint2, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillPolygon(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*  824 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  826 */     if (debugLog()) {
/*  827 */       info().log(toShortString() + " Filling polygon:  nPoints: " + paramInt + " X's: " + paramArrayOfint1 + " Y's: " + paramArrayOfint2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  833 */     if (isDrawingBuffer()) {
/*  834 */       if (debugBuffered()) {
/*  835 */         Graphics graphics = debugGraphics();
/*      */         
/*  837 */         graphics.fillPolygon(paramArrayOfint1, paramArrayOfint2, paramInt);
/*  838 */         graphics.dispose();
/*      */       } 
/*  840 */     } else if (debugFlash()) {
/*  841 */       Color color = getColor();
/*  842 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  844 */       for (byte b = 0; b < i; b++) {
/*  845 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*  846 */         this.graphics.fillPolygon(paramArrayOfint1, paramArrayOfint2, paramInt);
/*  847 */         Toolkit.getDefaultToolkit().sync();
/*  848 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  850 */       this.graphics.setColor(color);
/*      */     } 
/*  852 */     this.graphics.fillPolygon(paramArrayOfint1, paramArrayOfint2, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawString(String paramString, int paramInt1, int paramInt2) {
/*  859 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  861 */     if (debugLog()) {
/*  862 */       info().log(toShortString() + " Drawing string: \"" + paramString + "\" at: " + new Point(paramInt1, paramInt2));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  867 */     if (isDrawingBuffer()) {
/*  868 */       if (debugBuffered()) {
/*  869 */         Graphics graphics = debugGraphics();
/*      */         
/*  871 */         graphics.drawString(paramString, paramInt1, paramInt2);
/*  872 */         graphics.dispose();
/*      */       } 
/*  874 */     } else if (debugFlash()) {
/*  875 */       Color color = getColor();
/*  876 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  878 */       for (byte b = 0; b < i; b++) {
/*  879 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*      */         
/*  881 */         this.graphics.drawString(paramString, paramInt1, paramInt2);
/*  882 */         Toolkit.getDefaultToolkit().sync();
/*  883 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  885 */       this.graphics.setColor(color);
/*      */     } 
/*  887 */     this.graphics.drawString(paramString, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawString(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt1, int paramInt2) {
/*  894 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  896 */     if (debugLog()) {
/*  897 */       info().log(toShortString() + " Drawing text: \"" + paramAttributedCharacterIterator + "\" at: " + new Point(paramInt1, paramInt2));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  902 */     if (isDrawingBuffer()) {
/*  903 */       if (debugBuffered()) {
/*  904 */         Graphics graphics = debugGraphics();
/*      */         
/*  906 */         graphics.drawString(paramAttributedCharacterIterator, paramInt1, paramInt2);
/*  907 */         graphics.dispose();
/*      */       } 
/*  909 */     } else if (debugFlash()) {
/*  910 */       Color color = getColor();
/*  911 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  913 */       for (byte b = 0; b < i; b++) {
/*  914 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*      */         
/*  916 */         this.graphics.drawString(paramAttributedCharacterIterator, paramInt1, paramInt2);
/*  917 */         Toolkit.getDefaultToolkit().sync();
/*  918 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  920 */       this.graphics.setColor(color);
/*      */     } 
/*  922 */     this.graphics.drawString(paramAttributedCharacterIterator, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  929 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  931 */     Font font = this.graphics.getFont();
/*      */     
/*  933 */     if (debugLog()) {
/*  934 */       info().log(toShortString() + " Drawing bytes at: " + new Point(paramInt3, paramInt4));
/*      */     }
/*      */ 
/*      */     
/*  938 */     if (isDrawingBuffer()) {
/*  939 */       if (debugBuffered()) {
/*  940 */         Graphics graphics = debugGraphics();
/*      */         
/*  942 */         graphics.drawBytes(paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramInt4);
/*  943 */         graphics.dispose();
/*      */       } 
/*  945 */     } else if (debugFlash()) {
/*  946 */       Color color = getColor();
/*  947 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  949 */       for (byte b = 0; b < i; b++) {
/*  950 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*      */         
/*  952 */         this.graphics.drawBytes(paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramInt4);
/*  953 */         Toolkit.getDefaultToolkit().sync();
/*  954 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  956 */       this.graphics.setColor(color);
/*      */     } 
/*  958 */     this.graphics.drawBytes(paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawChars(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  965 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/*  967 */     Font font = this.graphics.getFont();
/*      */     
/*  969 */     if (debugLog()) {
/*  970 */       info().log(toShortString() + " Drawing chars at " + new Point(paramInt3, paramInt4));
/*      */     }
/*      */ 
/*      */     
/*  974 */     if (isDrawingBuffer()) {
/*  975 */       if (debugBuffered()) {
/*  976 */         Graphics graphics = debugGraphics();
/*      */         
/*  978 */         graphics.drawChars(paramArrayOfchar, paramInt1, paramInt2, paramInt3, paramInt4);
/*  979 */         graphics.dispose();
/*      */       } 
/*  981 */     } else if (debugFlash()) {
/*  982 */       Color color = getColor();
/*  983 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/*      */       
/*  985 */       for (byte b = 0; b < i; b++) {
/*  986 */         this.graphics.setColor((b % 2 == 0) ? debugGraphicsInfo.flashColor : color);
/*      */         
/*  988 */         this.graphics.drawChars(paramArrayOfchar, paramInt1, paramInt2, paramInt3, paramInt4);
/*  989 */         Toolkit.getDefaultToolkit().sync();
/*  990 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*  992 */       this.graphics.setColor(color);
/*      */     } 
/*  994 */     this.graphics.drawChars(paramArrayOfchar, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
/* 1002 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/* 1004 */     if (debugLog()) {
/* 1005 */       debugGraphicsInfo.log(toShortString() + " Drawing image: " + paramImage + " at: " + new Point(paramInt1, paramInt2));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1010 */     if (isDrawingBuffer()) {
/* 1011 */       if (debugBuffered()) {
/* 1012 */         Graphics graphics = debugGraphics();
/*      */         
/* 1014 */         graphics.drawImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/* 1015 */         graphics.dispose();
/*      */       } 
/* 1017 */     } else if (debugFlash()) {
/* 1018 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/* 1019 */       ImageProducer imageProducer = paramImage.getSource();
/* 1020 */       FilteredImageSource filteredImageSource = new FilteredImageSource(imageProducer, new DebugGraphicsFilter(debugGraphicsInfo.flashColor));
/*      */ 
/*      */ 
/*      */       
/* 1024 */       Image image = Toolkit.getDefaultToolkit().createImage(filteredImageSource);
/* 1025 */       DebugGraphicsObserver debugGraphicsObserver = new DebugGraphicsObserver();
/*      */ 
/*      */ 
/*      */       
/* 1029 */       for (byte b = 0; b < i; b++) {
/* 1030 */         Image image1 = (b % 2 == 0) ? image : paramImage;
/* 1031 */         loadImage(image1);
/* 1032 */         this.graphics.drawImage(image1, paramInt1, paramInt2, debugGraphicsObserver);
/*      */         
/* 1034 */         Toolkit.getDefaultToolkit().sync();
/* 1035 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*      */     } 
/* 1038 */     return this.graphics.drawImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ImageObserver paramImageObserver) {
/* 1046 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/* 1048 */     if (debugLog()) {
/* 1049 */       debugGraphicsInfo.log(toShortString() + " Drawing image: " + paramImage + " at: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1054 */     if (isDrawingBuffer()) {
/* 1055 */       if (debugBuffered()) {
/* 1056 */         Graphics graphics = debugGraphics();
/*      */         
/* 1058 */         graphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramImageObserver);
/* 1059 */         graphics.dispose();
/*      */       } 
/* 1061 */     } else if (debugFlash()) {
/* 1062 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/* 1063 */       ImageProducer imageProducer = paramImage.getSource();
/* 1064 */       FilteredImageSource filteredImageSource = new FilteredImageSource(imageProducer, new DebugGraphicsFilter(debugGraphicsInfo.flashColor));
/*      */ 
/*      */ 
/*      */       
/* 1068 */       Image image = Toolkit.getDefaultToolkit().createImage(filteredImageSource);
/* 1069 */       DebugGraphicsObserver debugGraphicsObserver = new DebugGraphicsObserver();
/*      */ 
/*      */ 
/*      */       
/* 1073 */       for (byte b = 0; b < i; b++) {
/* 1074 */         Image image1 = (b % 2 == 0) ? image : paramImage;
/* 1075 */         loadImage(image1);
/* 1076 */         this.graphics.drawImage(image1, paramInt1, paramInt2, paramInt3, paramInt4, debugGraphicsObserver);
/*      */         
/* 1078 */         Toolkit.getDefaultToolkit().sync();
/* 1079 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*      */     } 
/* 1082 */     return this.graphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramImageObserver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, Color paramColor, ImageObserver paramImageObserver) {
/* 1091 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/* 1093 */     if (debugLog()) {
/* 1094 */       debugGraphicsInfo.log(toShortString() + " Drawing image: " + paramImage + " at: " + new Point(paramInt1, paramInt2) + ", bgcolor: " + paramColor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1100 */     if (isDrawingBuffer()) {
/* 1101 */       if (debugBuffered()) {
/* 1102 */         Graphics graphics = debugGraphics();
/*      */         
/* 1104 */         graphics.drawImage(paramImage, paramInt1, paramInt2, paramColor, paramImageObserver);
/* 1105 */         graphics.dispose();
/*      */       } 
/* 1107 */     } else if (debugFlash()) {
/* 1108 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/* 1109 */       ImageProducer imageProducer = paramImage.getSource();
/* 1110 */       FilteredImageSource filteredImageSource = new FilteredImageSource(imageProducer, new DebugGraphicsFilter(debugGraphicsInfo.flashColor));
/*      */ 
/*      */ 
/*      */       
/* 1114 */       Image image = Toolkit.getDefaultToolkit().createImage(filteredImageSource);
/* 1115 */       DebugGraphicsObserver debugGraphicsObserver = new DebugGraphicsObserver();
/*      */ 
/*      */ 
/*      */       
/* 1119 */       for (byte b = 0; b < i; b++) {
/* 1120 */         Image image1 = (b % 2 == 0) ? image : paramImage;
/* 1121 */         loadImage(image1);
/* 1122 */         this.graphics.drawImage(image1, paramInt1, paramInt2, paramColor, debugGraphicsObserver);
/*      */         
/* 1124 */         Toolkit.getDefaultToolkit().sync();
/* 1125 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*      */     } 
/* 1128 */     return this.graphics.drawImage(paramImage, paramInt1, paramInt2, paramColor, paramImageObserver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor, ImageObserver paramImageObserver) {
/* 1137 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/* 1139 */     if (debugLog()) {
/* 1140 */       debugGraphicsInfo.log(toShortString() + " Drawing image: " + paramImage + " at: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4) + ", bgcolor: " + paramColor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1146 */     if (isDrawingBuffer()) {
/* 1147 */       if (debugBuffered()) {
/* 1148 */         Graphics graphics = debugGraphics();
/*      */         
/* 1150 */         graphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramColor, paramImageObserver);
/*      */         
/* 1152 */         graphics.dispose();
/*      */       } 
/* 1154 */     } else if (debugFlash()) {
/* 1155 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/* 1156 */       ImageProducer imageProducer = paramImage.getSource();
/* 1157 */       FilteredImageSource filteredImageSource = new FilteredImageSource(imageProducer, new DebugGraphicsFilter(debugGraphicsInfo.flashColor));
/*      */ 
/*      */ 
/*      */       
/* 1161 */       Image image = Toolkit.getDefaultToolkit().createImage(filteredImageSource);
/* 1162 */       DebugGraphicsObserver debugGraphicsObserver = new DebugGraphicsObserver();
/*      */ 
/*      */ 
/*      */       
/* 1166 */       for (byte b = 0; b < i; b++) {
/* 1167 */         Image image1 = (b % 2 == 0) ? image : paramImage;
/* 1168 */         loadImage(image1);
/* 1169 */         this.graphics.drawImage(image1, paramInt1, paramInt2, paramInt3, paramInt4, paramColor, debugGraphicsObserver);
/*      */         
/* 1171 */         Toolkit.getDefaultToolkit().sync();
/* 1172 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*      */     } 
/* 1175 */     return this.graphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramColor, paramImageObserver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, ImageObserver paramImageObserver) {
/* 1185 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/* 1187 */     if (debugLog()) {
/* 1188 */       debugGraphicsInfo.log(toShortString() + " Drawing image: " + paramImage + " destination: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4) + " source: " + new Rectangle(paramInt5, paramInt6, paramInt7, paramInt8));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1194 */     if (isDrawingBuffer()) {
/* 1195 */       if (debugBuffered()) {
/* 1196 */         Graphics graphics = debugGraphics();
/*      */         
/* 1198 */         graphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramImageObserver);
/*      */         
/* 1200 */         graphics.dispose();
/*      */       } 
/* 1202 */     } else if (debugFlash()) {
/* 1203 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/* 1204 */       ImageProducer imageProducer = paramImage.getSource();
/* 1205 */       FilteredImageSource filteredImageSource = new FilteredImageSource(imageProducer, new DebugGraphicsFilter(debugGraphicsInfo.flashColor));
/*      */ 
/*      */ 
/*      */       
/* 1209 */       Image image = Toolkit.getDefaultToolkit().createImage(filteredImageSource);
/* 1210 */       DebugGraphicsObserver debugGraphicsObserver = new DebugGraphicsObserver();
/*      */ 
/*      */ 
/*      */       
/* 1214 */       for (byte b = 0; b < i; b++) {
/* 1215 */         Image image1 = (b % 2 == 0) ? image : paramImage;
/* 1216 */         loadImage(image1);
/* 1217 */         this.graphics.drawImage(image1, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, debugGraphicsObserver);
/*      */ 
/*      */         
/* 1220 */         Toolkit.getDefaultToolkit().sync();
/* 1221 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*      */     } 
/* 1224 */     return this.graphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramImageObserver);
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
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Color paramColor, ImageObserver paramImageObserver) {
/* 1236 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */     
/* 1238 */     if (debugLog()) {
/* 1239 */       debugGraphicsInfo.log(toShortString() + " Drawing image: " + paramImage + " destination: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4) + " source: " + new Rectangle(paramInt5, paramInt6, paramInt7, paramInt8) + ", bgcolor: " + paramColor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1246 */     if (isDrawingBuffer()) {
/* 1247 */       if (debugBuffered()) {
/* 1248 */         Graphics graphics = debugGraphics();
/*      */         
/* 1250 */         graphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramColor, paramImageObserver);
/*      */         
/* 1252 */         graphics.dispose();
/*      */       } 
/* 1254 */     } else if (debugFlash()) {
/* 1255 */       int i = debugGraphicsInfo.flashCount * 2 - 1;
/* 1256 */       ImageProducer imageProducer = paramImage.getSource();
/* 1257 */       FilteredImageSource filteredImageSource = new FilteredImageSource(imageProducer, new DebugGraphicsFilter(debugGraphicsInfo.flashColor));
/*      */ 
/*      */ 
/*      */       
/* 1261 */       Image image = Toolkit.getDefaultToolkit().createImage(filteredImageSource);
/* 1262 */       DebugGraphicsObserver debugGraphicsObserver = new DebugGraphicsObserver();
/*      */ 
/*      */ 
/*      */       
/* 1266 */       for (byte b = 0; b < i; b++) {
/* 1267 */         Image image1 = (b % 2 == 0) ? image : paramImage;
/* 1268 */         loadImage(image1);
/* 1269 */         this.graphics.drawImage(image1, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramColor, debugGraphicsObserver);
/*      */ 
/*      */         
/* 1272 */         Toolkit.getDefaultToolkit().sync();
/* 1273 */         sleep(debugGraphicsInfo.flashTime);
/*      */       } 
/*      */     } 
/* 1276 */     return this.graphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramColor, paramImageObserver);
/*      */   }
/*      */ 
/*      */   
/*      */   static void loadImage(Image paramImage) {
/* 1281 */     imageLoadingIcon.loadImage(paramImage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 1290 */     if (debugLog()) {
/* 1291 */       info().log(toShortString() + " Copying area from: " + new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4) + " to: " + new Point(paramInt5, paramInt6));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1296 */     this.graphics.copyArea(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */   }
/*      */   
/*      */   final void sleep(int paramInt) {
/*      */     try {
/* 1301 */       Thread.sleep(paramInt);
/* 1302 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispose() {
/* 1310 */     this.graphics.dispose();
/* 1311 */     this.graphics = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDrawingBuffer() {
/* 1321 */     return (this.buffer != null);
/*      */   }
/*      */   
/*      */   String toShortString() {
/* 1325 */     return "Graphics" + (isDrawingBuffer() ? "<B>" : "") + "(" + this.graphicsID + "-" + this.debugOptions + ")";
/*      */   }
/*      */   
/*      */   String pointToString(int paramInt1, int paramInt2) {
/* 1329 */     return "(" + paramInt1 + ", " + paramInt2 + ")";
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
/*      */   public void setDebugOptions(int paramInt) {
/* 1341 */     if (paramInt != 0) {
/* 1342 */       if (paramInt == -1) {
/* 1343 */         if (this.debugOptions != 0) {
/* 1344 */           System.err.println(toShortString() + " Disabling debug");
/* 1345 */           this.debugOptions = 0;
/*      */         }
/*      */       
/* 1348 */       } else if (this.debugOptions != paramInt) {
/* 1349 */         this.debugOptions |= paramInt;
/* 1350 */         if (debugLog()) {
/* 1351 */           System.err.println(toShortString() + " Enabling debug");
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDebugOptions() {
/* 1362 */     return this.debugOptions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void setDebugOptions(JComponent paramJComponent, int paramInt) {
/* 1369 */     info().setDebugOptions(paramJComponent, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int getDebugOptions(JComponent paramJComponent) {
/* 1375 */     DebugGraphicsInfo debugGraphicsInfo = info();
/* 1376 */     if (debugGraphicsInfo == null) {
/* 1377 */       return 0;
/*      */     }
/* 1379 */     return debugGraphicsInfo.getDebugOptions(paramJComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int shouldComponentDebug(JComponent paramJComponent) {
/* 1388 */     DebugGraphicsInfo debugGraphicsInfo = info();
/* 1389 */     if (debugGraphicsInfo == null) {
/* 1390 */       return 0;
/*      */     }
/* 1392 */     JComponent jComponent = paramJComponent;
/* 1393 */     int i = 0;
/*      */     
/* 1395 */     while (jComponent != null && jComponent instanceof JComponent) {
/* 1396 */       i |= debugGraphicsInfo.getDebugOptions(jComponent);
/* 1397 */       Container container = jComponent.getParent();
/*      */     } 
/*      */     
/* 1400 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int debugComponentCount() {
/* 1408 */     DebugGraphicsInfo debugGraphicsInfo = info();
/* 1409 */     if (debugGraphicsInfo != null && debugGraphicsInfo.componentToDebug != null)
/*      */     {
/* 1411 */       return debugGraphicsInfo.componentToDebug.size();
/*      */     }
/* 1413 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   boolean debugLog() {
/* 1418 */     return ((this.debugOptions & 0x1) == 1);
/*      */   }
/*      */   
/*      */   boolean debugFlash() {
/* 1422 */     return ((this.debugOptions & 0x2) == 2);
/*      */   }
/*      */   
/*      */   boolean debugBuffered() {
/* 1426 */     return ((this.debugOptions & 0x4) == 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Graphics debugGraphics() {
/* 1433 */     DebugGraphicsInfo debugGraphicsInfo = info();
/*      */ 
/*      */     
/* 1436 */     if (debugGraphicsInfo.debugFrame == null) {
/* 1437 */       debugGraphicsInfo.debugFrame = new JFrame();
/* 1438 */       debugGraphicsInfo.debugFrame.setSize(500, 500);
/*      */     } 
/* 1440 */     JFrame jFrame = debugGraphicsInfo.debugFrame;
/* 1441 */     jFrame.show();
/* 1442 */     DebugGraphics debugGraphics = new DebugGraphics(jFrame.getGraphics());
/* 1443 */     debugGraphics.setFont(getFont());
/* 1444 */     debugGraphics.setColor(getColor());
/* 1445 */     debugGraphics.translate(this.xOffset, this.yOffset);
/* 1446 */     debugGraphics.setClip(getClipBounds());
/* 1447 */     if (debugFlash()) {
/* 1448 */       debugGraphics.setDebugOptions(2);
/*      */     }
/* 1450 */     return debugGraphics;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static DebugGraphicsInfo info() {
/* 1457 */     DebugGraphicsInfo debugGraphicsInfo = (DebugGraphicsInfo)SwingUtilities.appContextGet(debugGraphicsInfoKey);
/* 1458 */     if (debugGraphicsInfo == null) {
/* 1459 */       debugGraphicsInfo = new DebugGraphicsInfo();
/* 1460 */       SwingUtilities.appContextPut(debugGraphicsInfoKey, debugGraphicsInfo);
/*      */     } 
/*      */     
/* 1463 */     return debugGraphicsInfo;
/*      */   }
/* 1465 */   private static final Class debugGraphicsInfoKey = DebugGraphicsInfo.class;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/DebugGraphics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */