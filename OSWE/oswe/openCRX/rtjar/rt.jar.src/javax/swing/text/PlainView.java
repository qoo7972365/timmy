/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlainView
/*     */   extends View
/*     */   implements TabExpander
/*     */ {
/*     */   protected FontMetrics metrics;
/*     */   Element longLine;
/*     */   Font font;
/*     */   Segment lineBuffer;
/*     */   int tabSize;
/*     */   int tabBase;
/*     */   int sel0;
/*     */   int sel1;
/*     */   Color unselected;
/*     */   Color selected;
/*     */   int firstLineOffset;
/*     */   
/*     */   public PlainView(Element paramElement) {
/*  48 */     super(paramElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getTabSize() {
/*  57 */     Integer integer = (Integer)getDocument().getProperty("tabSize");
/*  58 */     return (integer != null) ? integer.intValue() : 8;
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
/*     */   protected void drawLine(int paramInt1, Graphics paramGraphics, int paramInt2, int paramInt3) {
/*  77 */     Element element = getElement().getElement(paramInt1);
/*     */ 
/*     */     
/*     */     try {
/*  81 */       if (element.isLeaf()) {
/*  82 */         drawElement(paramInt1, element, paramGraphics, paramInt2, paramInt3);
/*     */       } else {
/*     */         
/*  85 */         int i = element.getElementCount();
/*  86 */         for (byte b = 0; b < i; b++) {
/*  87 */           Element element1 = element.getElement(b);
/*  88 */           paramInt2 = drawElement(paramInt1, element1, paramGraphics, paramInt2, paramInt3);
/*     */         } 
/*     */       } 
/*  91 */     } catch (BadLocationException badLocationException) {
/*  92 */       throw new StateInvariantError("Can't render line: " + paramInt1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int drawElement(int paramInt1, Element paramElement, Graphics paramGraphics, int paramInt2, int paramInt3) throws BadLocationException {
/*  97 */     int i = paramElement.getStartOffset();
/*  98 */     int j = paramElement.getEndOffset();
/*  99 */     j = Math.min(getDocument().getLength(), j);
/*     */     
/* 101 */     if (paramInt1 == 0) {
/* 102 */       paramInt2 += this.firstLineOffset;
/*     */     }
/* 104 */     AttributeSet attributeSet = paramElement.getAttributes();
/* 105 */     if (Utilities.isComposedTextAttributeDefined(attributeSet)) {
/* 106 */       paramGraphics.setColor(this.unselected);
/* 107 */       paramInt2 = Utilities.drawComposedText(this, attributeSet, paramGraphics, paramInt2, paramInt3, i - paramElement
/* 108 */           .getStartOffset(), j - paramElement
/* 109 */           .getStartOffset());
/*     */     }
/* 111 */     else if (this.sel0 == this.sel1 || this.selected == this.unselected) {
/*     */       
/* 113 */       paramInt2 = drawUnselectedText(paramGraphics, paramInt2, paramInt3, i, j);
/* 114 */     } else if (i >= this.sel0 && i <= this.sel1 && j >= this.sel0 && j <= this.sel1) {
/* 115 */       paramInt2 = drawSelectedText(paramGraphics, paramInt2, paramInt3, i, j);
/* 116 */     } else if (this.sel0 >= i && this.sel0 <= j) {
/* 117 */       if (this.sel1 >= i && this.sel1 <= j) {
/* 118 */         paramInt2 = drawUnselectedText(paramGraphics, paramInt2, paramInt3, i, this.sel0);
/* 119 */         paramInt2 = drawSelectedText(paramGraphics, paramInt2, paramInt3, this.sel0, this.sel1);
/* 120 */         paramInt2 = drawUnselectedText(paramGraphics, paramInt2, paramInt3, this.sel1, j);
/*     */       } else {
/* 122 */         paramInt2 = drawUnselectedText(paramGraphics, paramInt2, paramInt3, i, this.sel0);
/* 123 */         paramInt2 = drawSelectedText(paramGraphics, paramInt2, paramInt3, this.sel0, j);
/*     */       } 
/* 125 */     } else if (this.sel1 >= i && this.sel1 <= j) {
/* 126 */       paramInt2 = drawSelectedText(paramGraphics, paramInt2, paramInt3, i, this.sel1);
/* 127 */       paramInt2 = drawUnselectedText(paramGraphics, paramInt2, paramInt3, this.sel1, j);
/*     */     } else {
/* 129 */       paramInt2 = drawUnselectedText(paramGraphics, paramInt2, paramInt3, i, j);
/*     */     } 
/*     */ 
/*     */     
/* 133 */     return paramInt2;
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
/*     */   protected int drawUnselectedText(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws BadLocationException {
/* 150 */     paramGraphics.setColor(this.unselected);
/* 151 */     Document document = getDocument();
/* 152 */     Segment segment = SegmentCache.getSharedSegment();
/* 153 */     document.getText(paramInt3, paramInt4 - paramInt3, segment);
/* 154 */     int i = Utilities.drawTabbedText(this, segment, paramInt1, paramInt2, paramGraphics, this, paramInt3);
/* 155 */     SegmentCache.releaseSharedSegment(segment);
/* 156 */     return i;
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
/*     */   protected int drawSelectedText(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws BadLocationException {
/* 175 */     paramGraphics.setColor(this.selected);
/* 176 */     Document document = getDocument();
/* 177 */     Segment segment = SegmentCache.getSharedSegment();
/* 178 */     document.getText(paramInt3, paramInt4 - paramInt3, segment);
/* 179 */     int i = Utilities.drawTabbedText(this, segment, paramInt1, paramInt2, paramGraphics, this, paramInt3);
/* 180 */     SegmentCache.releaseSharedSegment(segment);
/* 181 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Segment getLineBuffer() {
/* 191 */     if (this.lineBuffer == null) {
/* 192 */       this.lineBuffer = new Segment();
/*     */     }
/* 194 */     return this.lineBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateMetrics() {
/* 204 */     Container container = getContainer();
/* 205 */     Font font = container.getFont();
/* 206 */     if (this.font != font) {
/*     */ 
/*     */       
/* 209 */       calculateLongestLine();
/* 210 */       this.tabSize = getTabSize() * this.metrics.charWidth('m');
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
/*     */   
/*     */   public float getPreferredSpan(int paramInt) {
/* 228 */     updateMetrics();
/* 229 */     switch (paramInt) {
/*     */       case 0:
/* 231 */         return getLineWidth(this.longLine);
/*     */       case 1:
/* 233 */         return (getElement().getElementCount() * this.metrics.getHeight());
/*     */     } 
/* 235 */     throw new IllegalArgumentException("Invalid axis: " + paramInt);
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
/*     */   public void paint(Graphics paramGraphics, Shape paramShape) {
/*     */     byte b1, b2, b3;
/* 250 */     Shape shape = paramShape;
/* 251 */     paramShape = adjustPaintRegion(paramShape);
/* 252 */     Rectangle rectangle1 = (Rectangle)paramShape;
/* 253 */     this.tabBase = rectangle1.x;
/* 254 */     JTextComponent jTextComponent = (JTextComponent)getContainer();
/* 255 */     Highlighter highlighter = jTextComponent.getHighlighter();
/* 256 */     paramGraphics.setFont(jTextComponent.getFont());
/* 257 */     this.sel0 = jTextComponent.getSelectionStart();
/* 258 */     this.sel1 = jTextComponent.getSelectionEnd();
/* 259 */     this
/* 260 */       .unselected = jTextComponent.isEnabled() ? jTextComponent.getForeground() : jTextComponent.getDisabledTextColor();
/* 261 */     Caret caret = jTextComponent.getCaret();
/* 262 */     this
/* 263 */       .selected = (caret.isSelectionVisible() && highlighter != null) ? jTextComponent.getSelectedTextColor() : this.unselected;
/* 264 */     updateMetrics();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     Rectangle rectangle2 = paramGraphics.getClipBounds();
/* 271 */     int i = this.metrics.getHeight();
/* 272 */     int j = rectangle1.y + rectangle1.height - rectangle2.y + rectangle2.height;
/* 273 */     int k = rectangle2.y - rectangle1.y;
/*     */ 
/*     */     
/* 276 */     if (i > 0) {
/* 277 */       b1 = Math.max(0, j / i);
/* 278 */       b2 = Math.max(0, k / i);
/* 279 */       b3 = rectangle1.height / i;
/* 280 */       if (rectangle1.height % i != 0) {
/* 281 */         b3++;
/*     */       }
/*     */     } else {
/* 284 */       b1 = b2 = b3 = 0;
/*     */     } 
/*     */ 
/*     */     
/* 288 */     Rectangle rectangle3 = lineToRect(paramShape, b2);
/* 289 */     int m = rectangle3.y + this.metrics.getAscent();
/* 290 */     int n = rectangle3.x;
/* 291 */     Element element = getElement();
/* 292 */     int i1 = element.getElementCount();
/* 293 */     int i2 = Math.min(i1, b3 - b1);
/* 294 */     i1--;
/* 295 */     LayeredHighlighter layeredHighlighter = (highlighter instanceof LayeredHighlighter) ? (LayeredHighlighter)highlighter : null;
/*     */     
/* 297 */     for (byte b4 = b2; b4 < i2; b4++) {
/* 298 */       if (layeredHighlighter != null) {
/* 299 */         Element element1 = element.getElement(b4);
/* 300 */         if (b4 == i1) {
/* 301 */           layeredHighlighter.paintLayeredHighlights(paramGraphics, element1.getStartOffset(), element1
/* 302 */               .getEndOffset(), shape, jTextComponent, this);
/*     */         }
/*     */         else {
/*     */           
/* 306 */           layeredHighlighter.paintLayeredHighlights(paramGraphics, element1.getStartOffset(), element1
/* 307 */               .getEndOffset() - 1, shape, jTextComponent, this);
/*     */         } 
/*     */       } 
/*     */       
/* 311 */       drawLine(b4, paramGraphics, n, m);
/* 312 */       m += i;
/* 313 */       if (b4 == 0)
/*     */       {
/*     */ 
/*     */         
/* 317 */         n -= this.firstLineOffset;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Shape adjustPaintRegion(Shape paramShape) {
/* 328 */     return paramShape;
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
/*     */   public Shape modelToView(int paramInt, Shape paramShape, Position.Bias paramBias) throws BadLocationException {
/* 344 */     Document document = getDocument();
/* 345 */     Element element1 = getElement();
/* 346 */     int i = element1.getElementIndex(paramInt);
/* 347 */     if (i < 0) {
/* 348 */       return lineToRect(paramShape, 0);
/*     */     }
/* 350 */     Rectangle rectangle = lineToRect(paramShape, i);
/*     */ 
/*     */     
/* 353 */     this.tabBase = rectangle.x;
/* 354 */     Element element2 = element1.getElement(i);
/* 355 */     int j = element2.getStartOffset();
/* 356 */     Segment segment = SegmentCache.getSharedSegment();
/* 357 */     document.getText(j, paramInt - j, segment);
/* 358 */     int k = Utilities.getTabbedTextWidth(segment, this.metrics, this.tabBase, this, j);
/* 359 */     SegmentCache.releaseSharedSegment(segment);
/*     */ 
/*     */     
/* 362 */     rectangle.x += k;
/* 363 */     rectangle.width = 1;
/* 364 */     rectangle.height = this.metrics.getHeight();
/* 365 */     return rectangle;
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
/*     */   public int viewToModel(float paramFloat1, float paramFloat2, Shape paramShape, Position.Bias[] paramArrayOfBias) {
/* 381 */     paramArrayOfBias[0] = Position.Bias.Forward;
/*     */     
/* 383 */     Rectangle rectangle = paramShape.getBounds();
/* 384 */     Document document = getDocument();
/* 385 */     int i = (int)paramFloat1;
/* 386 */     int j = (int)paramFloat2;
/* 387 */     if (j < rectangle.y)
/*     */     {
/*     */       
/* 390 */       return getStartOffset(); } 
/* 391 */     if (j > rectangle.y + rectangle.height)
/*     */     {
/*     */       
/* 394 */       return getEndOffset() - 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 401 */     Element element1 = document.getDefaultRootElement();
/* 402 */     int k = this.metrics.getHeight();
/*     */ 
/*     */     
/* 405 */     int m = (k > 0) ? Math.abs((j - rectangle.y) / k) : (element1.getElementCount() - 1);
/* 406 */     if (m >= element1.getElementCount()) {
/* 407 */       return getEndOffset() - 1;
/*     */     }
/* 409 */     Element element2 = element1.getElement(m);
/* 410 */     boolean bool = false;
/* 411 */     if (m == 0) {
/* 412 */       rectangle.x += this.firstLineOffset;
/* 413 */       rectangle.width -= this.firstLineOffset;
/*     */     } 
/* 415 */     if (i < rectangle.x)
/*     */     {
/* 417 */       return element2.getStartOffset(); } 
/* 418 */     if (i > rectangle.x + rectangle.width)
/*     */     {
/* 420 */       return element2.getEndOffset() - 1;
/*     */     }
/*     */     
/*     */     try {
/* 424 */       int n = element2.getStartOffset();
/* 425 */       int i1 = element2.getEndOffset() - 1;
/* 426 */       Segment segment = SegmentCache.getSharedSegment();
/* 427 */       document.getText(n, i1 - n, segment);
/* 428 */       this.tabBase = rectangle.x;
/* 429 */       int i2 = n + Utilities.getTabbedTextOffset(segment, this.metrics, this.tabBase, i, this, n);
/*     */       
/* 431 */       SegmentCache.releaseSharedSegment(segment);
/* 432 */       return i2;
/* 433 */     } catch (BadLocationException badLocationException) {
/*     */       
/* 435 */       return -1;
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
/*     */   public void insertUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 451 */     updateDamage(paramDocumentEvent, paramShape, paramViewFactory);
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
/*     */   public void removeUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 464 */     updateDamage(paramDocumentEvent, paramShape, paramViewFactory);
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
/*     */   public void changedUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 477 */     updateDamage(paramDocumentEvent, paramShape, paramViewFactory);
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
/*     */   public void setSize(float paramFloat1, float paramFloat2) {
/* 489 */     super.setSize(paramFloat1, paramFloat2);
/* 490 */     updateMetrics();
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
/*     */   public float nextTabStop(float paramFloat, int paramInt) {
/* 506 */     if (this.tabSize == 0) {
/* 507 */       return paramFloat;
/*     */     }
/* 509 */     int i = ((int)paramFloat - this.tabBase) / this.tabSize;
/* 510 */     return (this.tabBase + (i + 1) * this.tabSize);
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
/*     */   protected void updateDamage(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 526 */     Container container = getContainer();
/* 527 */     updateMetrics();
/* 528 */     Element element = getElement();
/* 529 */     DocumentEvent.ElementChange elementChange = paramDocumentEvent.getChange(element);
/*     */     
/* 531 */     Element[] arrayOfElement1 = (elementChange != null) ? elementChange.getChildrenAdded() : null;
/* 532 */     Element[] arrayOfElement2 = (elementChange != null) ? elementChange.getChildrenRemoved() : null;
/* 533 */     if ((arrayOfElement1 != null && arrayOfElement1.length > 0) || (arrayOfElement2 != null && arrayOfElement2.length > 0)) {
/*     */ 
/*     */       
/* 536 */       if (arrayOfElement1 != null) {
/* 537 */         int i = getLineWidth(this.longLine);
/* 538 */         for (byte b = 0; b < arrayOfElement1.length; b++) {
/* 539 */           int j = getLineWidth(arrayOfElement1[b]);
/* 540 */           if (j > i) {
/* 541 */             i = j;
/* 542 */             this.longLine = arrayOfElement1[b];
/*     */           } 
/*     */         } 
/*     */       } 
/* 546 */       if (arrayOfElement2 != null) {
/* 547 */         for (byte b = 0; b < arrayOfElement2.length; b++) {
/* 548 */           if (arrayOfElement2[b] == this.longLine) {
/* 549 */             calculateLongestLine();
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 554 */       preferenceChanged(null, true, true);
/* 555 */       container.repaint();
/*     */     } else {
/* 557 */       Element element1 = getElement();
/* 558 */       int i = element1.getElementIndex(paramDocumentEvent.getOffset());
/* 559 */       damageLineRange(i, i, paramShape, container);
/* 560 */       if (paramDocumentEvent.getType() == DocumentEvent.EventType.INSERT) {
/*     */ 
/*     */         
/* 563 */         int j = getLineWidth(this.longLine);
/* 564 */         Element element2 = element1.getElement(i);
/* 565 */         if (element2 == this.longLine) {
/* 566 */           preferenceChanged(null, true, false);
/* 567 */         } else if (getLineWidth(element2) > j) {
/* 568 */           this.longLine = element2;
/* 569 */           preferenceChanged(null, true, false);
/*     */         } 
/* 571 */       } else if (paramDocumentEvent.getType() == DocumentEvent.EventType.REMOVE && 
/* 572 */         element1.getElement(i) == this.longLine) {
/*     */         
/* 574 */         calculateLongestLine();
/* 575 */         preferenceChanged(null, true, false);
/*     */       } 
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
/*     */   protected void damageLineRange(int paramInt1, int paramInt2, Shape paramShape, Component paramComponent) {
/* 593 */     if (paramShape != null) {
/* 594 */       Rectangle rectangle1 = lineToRect(paramShape, paramInt1);
/* 595 */       Rectangle rectangle2 = lineToRect(paramShape, paramInt2);
/* 596 */       if (rectangle1 != null && rectangle2 != null) {
/* 597 */         Rectangle rectangle = rectangle1.union(rectangle2);
/* 598 */         paramComponent.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       } else {
/* 600 */         paramComponent.repaint();
/*     */       } 
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
/*     */   protected Rectangle lineToRect(Shape paramShape, int paramInt) {
/* 614 */     Rectangle rectangle = null;
/* 615 */     updateMetrics();
/* 616 */     if (this.metrics != null) {
/* 617 */       Rectangle rectangle1 = paramShape.getBounds();
/* 618 */       if (paramInt == 0) {
/* 619 */         rectangle1.x += this.firstLineOffset;
/* 620 */         rectangle1.width -= this.firstLineOffset;
/*     */       } 
/*     */       
/* 623 */       rectangle = new Rectangle(rectangle1.x, rectangle1.y + paramInt * this.metrics.getHeight(), rectangle1.width, this.metrics.getHeight());
/*     */     } 
/* 625 */     return rectangle;
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
/*     */   private void calculateLongestLine() {
/* 637 */     Container container = getContainer();
/* 638 */     this.font = container.getFont();
/* 639 */     this.metrics = container.getFontMetrics(this.font);
/* 640 */     Document document = getDocument();
/* 641 */     Element element = getElement();
/* 642 */     int i = element.getElementCount();
/* 643 */     int j = -1;
/* 644 */     for (byte b = 0; b < i; b++) {
/* 645 */       Element element1 = element.getElement(b);
/* 646 */       int k = getLineWidth(element1);
/* 647 */       if (k > j) {
/* 648 */         j = k;
/* 649 */         this.longLine = element1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getLineWidth(Element paramElement) {
/*     */     boolean bool;
/* 660 */     if (paramElement == null) {
/* 661 */       return 0;
/*     */     }
/* 663 */     int i = paramElement.getStartOffset();
/* 664 */     int j = paramElement.getEndOffset();
/*     */     
/* 666 */     Segment segment = SegmentCache.getSharedSegment();
/*     */     try {
/* 668 */       paramElement.getDocument().getText(i, j - i, segment);
/* 669 */       bool = Utilities.getTabbedTextWidth(segment, this.metrics, this.tabBase, this, i);
/* 670 */     } catch (BadLocationException badLocationException) {
/* 671 */       bool = false;
/*     */     } 
/* 673 */     SegmentCache.releaseSharedSegment(segment);
/* 674 */     return bool;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/PlainView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */