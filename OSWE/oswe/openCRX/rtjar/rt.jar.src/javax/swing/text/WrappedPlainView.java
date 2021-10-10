/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.lang.ref.SoftReference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrappedPlainView
/*     */   extends BoxView
/*     */   implements TabExpander
/*     */ {
/*     */   FontMetrics metrics;
/*     */   Segment lineBuffer;
/*     */   boolean widthChanging;
/*     */   int tabBase;
/*     */   int tabSize;
/*     */   boolean wordWrap;
/*     */   int sel0;
/*     */   int sel1;
/*     */   Color unselected;
/*     */   Color selected;
/*     */   
/*     */   public WrappedPlainView(Element paramElement) {
/*  62 */     this(paramElement, false);
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
/*     */   public WrappedPlainView(Element paramElement, boolean paramBoolean) {
/*  74 */     super(paramElement, 1);
/*  75 */     this.wordWrap = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getTabSize() {
/*  84 */     Integer integer = (Integer)getDocument().getProperty("tabSize");
/*  85 */     return (integer != null) ? integer.intValue() : 8;
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
/*     */   protected void drawLine(int paramInt1, int paramInt2, Graphics paramGraphics, int paramInt3, int paramInt4) {
/* 105 */     Element element1 = getElement();
/* 106 */     Element element2 = element1.getElement(element1.getElementIndex(paramInt1));
/*     */ 
/*     */     
/*     */     try {
/* 110 */       if (element2.isLeaf()) {
/* 111 */         drawText(element2, paramInt1, paramInt2, paramGraphics, paramInt3, paramInt4);
/*     */       } else {
/*     */         
/* 114 */         int i = element2.getElementIndex(paramInt1);
/* 115 */         int j = element2.getElementIndex(paramInt2);
/* 116 */         for (; i <= j; i++) {
/* 117 */           Element element = element2.getElement(i);
/* 118 */           int k = Math.max(element.getStartOffset(), paramInt1);
/* 119 */           int m = Math.min(element.getEndOffset(), paramInt2);
/* 120 */           paramInt3 = drawText(element, k, m, paramGraphics, paramInt3, paramInt4);
/*     */         } 
/*     */       } 
/* 123 */     } catch (BadLocationException badLocationException) {
/* 124 */       throw new StateInvariantError("Can't render: " + paramInt1 + "," + paramInt2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int drawText(Element paramElement, int paramInt1, int paramInt2, Graphics paramGraphics, int paramInt3, int paramInt4) throws BadLocationException {
/* 129 */     paramInt2 = Math.min(getDocument().getLength(), paramInt2);
/* 130 */     AttributeSet attributeSet = paramElement.getAttributes();
/*     */     
/* 132 */     if (Utilities.isComposedTextAttributeDefined(attributeSet)) {
/* 133 */       paramGraphics.setColor(this.unselected);
/* 134 */       paramInt3 = Utilities.drawComposedText(this, attributeSet, paramGraphics, paramInt3, paramInt4, paramInt1 - paramElement
/* 135 */           .getStartOffset(), paramInt2 - paramElement
/* 136 */           .getStartOffset());
/*     */     }
/* 138 */     else if (this.sel0 == this.sel1 || this.selected == this.unselected) {
/*     */       
/* 140 */       paramInt3 = drawUnselectedText(paramGraphics, paramInt3, paramInt4, paramInt1, paramInt2);
/* 141 */     } else if (paramInt1 >= this.sel0 && paramInt1 <= this.sel1 && paramInt2 >= this.sel0 && paramInt2 <= this.sel1) {
/* 142 */       paramInt3 = drawSelectedText(paramGraphics, paramInt3, paramInt4, paramInt1, paramInt2);
/* 143 */     } else if (this.sel0 >= paramInt1 && this.sel0 <= paramInt2) {
/* 144 */       if (this.sel1 >= paramInt1 && this.sel1 <= paramInt2) {
/* 145 */         paramInt3 = drawUnselectedText(paramGraphics, paramInt3, paramInt4, paramInt1, this.sel0);
/* 146 */         paramInt3 = drawSelectedText(paramGraphics, paramInt3, paramInt4, this.sel0, this.sel1);
/* 147 */         paramInt3 = drawUnselectedText(paramGraphics, paramInt3, paramInt4, this.sel1, paramInt2);
/*     */       } else {
/* 149 */         paramInt3 = drawUnselectedText(paramGraphics, paramInt3, paramInt4, paramInt1, this.sel0);
/* 150 */         paramInt3 = drawSelectedText(paramGraphics, paramInt3, paramInt4, this.sel0, paramInt2);
/*     */       } 
/* 152 */     } else if (this.sel1 >= paramInt1 && this.sel1 <= paramInt2) {
/* 153 */       paramInt3 = drawSelectedText(paramGraphics, paramInt3, paramInt4, paramInt1, this.sel1);
/* 154 */       paramInt3 = drawUnselectedText(paramGraphics, paramInt3, paramInt4, this.sel1, paramInt2);
/*     */     } else {
/* 156 */       paramInt3 = drawUnselectedText(paramGraphics, paramInt3, paramInt4, paramInt1, paramInt2);
/*     */     } 
/*     */ 
/*     */     
/* 160 */     return paramInt3;
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
/* 177 */     paramGraphics.setColor(this.unselected);
/* 178 */     Document document = getDocument();
/* 179 */     Segment segment = SegmentCache.getSharedSegment();
/* 180 */     document.getText(paramInt3, paramInt4 - paramInt3, segment);
/* 181 */     int i = Utilities.drawTabbedText(this, segment, paramInt1, paramInt2, paramGraphics, this, paramInt3);
/* 182 */     SegmentCache.releaseSharedSegment(segment);
/* 183 */     return i;
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
/* 202 */     paramGraphics.setColor(this.selected);
/* 203 */     Document document = getDocument();
/* 204 */     Segment segment = SegmentCache.getSharedSegment();
/* 205 */     document.getText(paramInt3, paramInt4 - paramInt3, segment);
/* 206 */     int i = Utilities.drawTabbedText(this, segment, paramInt1, paramInt2, paramGraphics, this, paramInt3);
/* 207 */     SegmentCache.releaseSharedSegment(segment);
/* 208 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Segment getLineBuffer() {
/* 218 */     if (this.lineBuffer == null) {
/* 219 */       this.lineBuffer = new Segment();
/*     */     }
/* 221 */     return this.lineBuffer;
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
/*     */   protected int calculateBreakPosition(int paramInt1, int paramInt2) {
/*     */     int i;
/* 234 */     Segment segment = SegmentCache.getSharedSegment();
/* 235 */     loadText(segment, paramInt1, paramInt2);
/* 236 */     int j = getWidth();
/* 237 */     if (this.wordWrap) {
/* 238 */       i = paramInt1 + Utilities.getBreakLocation(segment, this.metrics, this.tabBase, this.tabBase + j, this, paramInt1);
/*     */     }
/*     */     else {
/*     */       
/* 242 */       i = paramInt1 + Utilities.getTabbedTextOffset(segment, this.metrics, this.tabBase, this.tabBase + j, this, paramInt1, false);
/*     */     } 
/*     */ 
/*     */     
/* 246 */     SegmentCache.releaseSharedSegment(segment);
/* 247 */     return i;
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
/*     */   protected void loadChildren(ViewFactory paramViewFactory) {
/* 261 */     Element element = getElement();
/* 262 */     int i = element.getElementCount();
/* 263 */     if (i > 0) {
/* 264 */       View[] arrayOfView = new View[i];
/* 265 */       for (byte b = 0; b < i; b++) {
/* 266 */         arrayOfView[b] = new WrappedLine(element.getElement(b));
/*     */       }
/* 268 */       replace(0, 0, arrayOfView);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateChildren(DocumentEvent paramDocumentEvent, Shape paramShape) {
/* 277 */     Element element = getElement();
/* 278 */     DocumentEvent.ElementChange elementChange = paramDocumentEvent.getChange(element);
/* 279 */     if (elementChange != null) {
/*     */       
/* 281 */       Element[] arrayOfElement1 = elementChange.getChildrenRemoved();
/* 282 */       Element[] arrayOfElement2 = elementChange.getChildrenAdded();
/* 283 */       View[] arrayOfView = new View[arrayOfElement2.length];
/* 284 */       for (byte b = 0; b < arrayOfElement2.length; b++) {
/* 285 */         arrayOfView[b] = new WrappedLine(arrayOfElement2[b]);
/*     */       }
/* 287 */       replace(elementChange.getIndex(), arrayOfElement1.length, arrayOfView);
/*     */ 
/*     */       
/* 290 */       if (paramShape != null) {
/* 291 */         preferenceChanged((View)null, true, true);
/* 292 */         getContainer().repaint();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 297 */     updateMetrics();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void loadText(Segment paramSegment, int paramInt1, int paramInt2) {
/*     */     try {
/* 308 */       Document document = getDocument();
/* 309 */       document.getText(paramInt1, paramInt2 - paramInt1, paramSegment);
/* 310 */     } catch (BadLocationException badLocationException) {
/* 311 */       throw new StateInvariantError("Can't get line text");
/*     */     } 
/*     */   }
/*     */   
/*     */   final void updateMetrics() {
/* 316 */     Container container = getContainer();
/* 317 */     Font font = container.getFont();
/* 318 */     this.metrics = container.getFontMetrics(font);
/* 319 */     this.tabSize = getTabSize() * this.metrics.charWidth('m');
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
/* 335 */     if (this.tabSize == 0)
/* 336 */       return paramFloat; 
/* 337 */     int i = ((int)paramFloat - this.tabBase) / this.tabSize;
/* 338 */     return (this.tabBase + (i + 1) * this.tabSize);
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
/*     */   public void paint(Graphics paramGraphics, Shape paramShape) {
/* 356 */     Rectangle rectangle = (Rectangle)paramShape;
/* 357 */     this.tabBase = rectangle.x;
/* 358 */     JTextComponent jTextComponent = (JTextComponent)getContainer();
/* 359 */     this.sel0 = jTextComponent.getSelectionStart();
/* 360 */     this.sel1 = jTextComponent.getSelectionEnd();
/* 361 */     this
/* 362 */       .unselected = jTextComponent.isEnabled() ? jTextComponent.getForeground() : jTextComponent.getDisabledTextColor();
/* 363 */     Caret caret = jTextComponent.getCaret();
/* 364 */     this
/* 365 */       .selected = (caret.isSelectionVisible() && jTextComponent.getHighlighter() != null) ? jTextComponent.getSelectedTextColor() : this.unselected;
/* 366 */     paramGraphics.setFont(jTextComponent.getFont());
/*     */ 
/*     */     
/* 369 */     super.paint(paramGraphics, paramShape);
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
/* 381 */     updateMetrics();
/* 382 */     if ((int)paramFloat1 != getWidth()) {
/*     */ 
/*     */       
/* 385 */       preferenceChanged((View)null, true, true);
/* 386 */       this.widthChanging = true;
/*     */     } 
/* 388 */     super.setSize(paramFloat1, paramFloat2);
/* 389 */     this.widthChanging = false;
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
/*     */   public float getPreferredSpan(int paramInt) {
/* 408 */     updateMetrics();
/* 409 */     return super.getPreferredSpan(paramInt);
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
/*     */   public float getMinimumSpan(int paramInt) {
/* 428 */     updateMetrics();
/* 429 */     return super.getMinimumSpan(paramInt);
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
/*     */   public float getMaximumSpan(int paramInt) {
/* 448 */     updateMetrics();
/* 449 */     return super.getMaximumSpan(paramInt);
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
/*     */   public void insertUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 463 */     updateChildren(paramDocumentEvent, paramShape);
/*     */ 
/*     */     
/* 466 */     Rectangle rectangle = (paramShape != null && isAllocationValid()) ? getInsideAllocation(paramShape) : null;
/* 467 */     int i = paramDocumentEvent.getOffset();
/* 468 */     View view = getViewAtPosition(i, rectangle);
/* 469 */     if (view != null) {
/* 470 */       view.insertUpdate(paramDocumentEvent, rectangle, paramViewFactory);
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
/*     */   public void removeUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 485 */     updateChildren(paramDocumentEvent, paramShape);
/*     */ 
/*     */     
/* 488 */     Rectangle rectangle = (paramShape != null && isAllocationValid()) ? getInsideAllocation(paramShape) : null;
/* 489 */     int i = paramDocumentEvent.getOffset();
/* 490 */     View view = getViewAtPosition(i, rectangle);
/* 491 */     if (view != null) {
/* 492 */       view.removeUpdate(paramDocumentEvent, rectangle, paramViewFactory);
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
/*     */   public void changedUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 506 */     updateChildren(paramDocumentEvent, paramShape);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class WrappedLine
/*     */     extends View
/*     */   {
/*     */     int lineCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SoftReference<int[]> lineCache;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     WrappedLine(Element param1Element) {
/* 534 */       super(param1Element);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 851 */       this.lineCache = null;
/*     */       this.lineCount = -1;
/*     */     }
/*     */     
/*     */     public float getPreferredSpan(int param1Int) {
/*     */       float f;
/*     */       switch (param1Int) {
/*     */         case 0:
/*     */           f = WrappedPlainView.this.getWidth();
/*     */           if (f == 2.14748365E9F)
/*     */             return 100.0F; 
/*     */           return f;
/*     */         case 1:
/*     */           if (this.lineCount < 0 || WrappedPlainView.this.widthChanging)
/*     */             breakLines(getStartOffset()); 
/*     */           return (this.lineCount * WrappedPlainView.this.metrics.getHeight());
/*     */       } 
/*     */       throw new IllegalArgumentException("Invalid axis: " + param1Int);
/*     */     }
/*     */     
/*     */     public void paint(Graphics param1Graphics, Shape param1Shape) {
/*     */       Rectangle rectangle = (Rectangle)param1Shape;
/*     */       int i = rectangle.y + WrappedPlainView.this.metrics.getAscent();
/*     */       int j = rectangle.x;
/*     */       JTextComponent jTextComponent = (JTextComponent)getContainer();
/*     */       Highlighter highlighter = jTextComponent.getHighlighter();
/*     */       LayeredHighlighter layeredHighlighter = (highlighter instanceof LayeredHighlighter) ? (LayeredHighlighter)highlighter : null;
/*     */       int k = getStartOffset();
/*     */       int m = getEndOffset();
/*     */       int n = k;
/*     */       int[] arrayOfInt = getLineEnds();
/*     */       for (byte b = 0; b < this.lineCount; b++) {
/*     */         int i1 = (arrayOfInt == null) ? m : (k + arrayOfInt[b]);
/*     */         if (layeredHighlighter != null) {
/*     */           int i2 = (i1 == m) ? (i1 - 1) : i1;
/*     */           layeredHighlighter.paintLayeredHighlights(param1Graphics, n, i2, param1Shape, jTextComponent, this);
/*     */         } 
/*     */         WrappedPlainView.this.drawLine(n, i1, param1Graphics, j, i);
/*     */         n = i1;
/*     */         i += WrappedPlainView.this.metrics.getHeight();
/*     */       } 
/*     */     }
/*     */     
/*     */     public Shape modelToView(int param1Int, Shape param1Shape, Position.Bias param1Bias) throws BadLocationException {
/*     */       Rectangle rectangle = param1Shape.getBounds();
/*     */       rectangle.height = WrappedPlainView.this.metrics.getHeight();
/*     */       rectangle.width = 1;
/*     */       int i = getStartOffset();
/*     */       if (param1Int < i || param1Int > getEndOffset())
/*     */         throw new BadLocationException("Position out of range", param1Int); 
/*     */       int j = (param1Bias == Position.Bias.Forward) ? param1Int : Math.max(i, param1Int - 1);
/*     */       int k = 0;
/*     */       int[] arrayOfInt = getLineEnds();
/*     */       if (arrayOfInt != null) {
/*     */         k = findLine(j - i);
/*     */         if (k > 0)
/*     */           i += arrayOfInt[k - 1]; 
/*     */         rectangle.y += rectangle.height * k;
/*     */       } 
/*     */       if (param1Int > i) {
/*     */         Segment segment = SegmentCache.getSharedSegment();
/*     */         WrappedPlainView.this.loadText(segment, i, param1Int);
/*     */         rectangle.x += Utilities.getTabbedTextWidth(segment, WrappedPlainView.this.metrics, rectangle.x, WrappedPlainView.this, i);
/*     */         SegmentCache.releaseSharedSegment(segment);
/*     */       } 
/*     */       return rectangle;
/*     */     }
/*     */     
/*     */     public int viewToModel(float param1Float1, float param1Float2, Shape param1Shape, Position.Bias[] param1ArrayOfBias) {
/*     */       int n;
/*     */       param1ArrayOfBias[0] = Position.Bias.Forward;
/*     */       Rectangle rectangle = (Rectangle)param1Shape;
/*     */       int i = (int)param1Float1;
/*     */       int j = (int)param1Float2;
/*     */       if (j < rectangle.y)
/*     */         return getStartOffset(); 
/*     */       if (j > rectangle.y + rectangle.height)
/*     */         return getEndOffset() - 1; 
/*     */       rectangle.height = WrappedPlainView.this.metrics.getHeight();
/*     */       int k = (rectangle.height > 0) ? ((j - rectangle.y) / rectangle.height) : (this.lineCount - 1);
/*     */       if (k >= this.lineCount)
/*     */         return getEndOffset() - 1; 
/*     */       int m = getStartOffset();
/*     */       if (this.lineCount == 1) {
/*     */         n = getEndOffset();
/*     */       } else {
/*     */         int[] arrayOfInt = getLineEnds();
/*     */         n = m + arrayOfInt[k];
/*     */         if (k > 0)
/*     */           m += arrayOfInt[k - 1]; 
/*     */       } 
/*     */       if (i < rectangle.x)
/*     */         return m; 
/*     */       if (i > rectangle.x + rectangle.width)
/*     */         return n - 1; 
/*     */       Segment segment = SegmentCache.getSharedSegment();
/*     */       WrappedPlainView.this.loadText(segment, m, n);
/*     */       int i1 = Utilities.getTabbedTextOffset(segment, WrappedPlainView.this.metrics, rectangle.x, i, WrappedPlainView.this, m);
/*     */       SegmentCache.releaseSharedSegment(segment);
/*     */       return Math.min(m + i1, n - 1);
/*     */     }
/*     */     
/*     */     public void insertUpdate(DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/*     */       update(param1DocumentEvent, param1Shape);
/*     */     }
/*     */     
/*     */     public void removeUpdate(DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/*     */       update(param1DocumentEvent, param1Shape);
/*     */     }
/*     */     
/*     */     private void update(DocumentEvent param1DocumentEvent, Shape param1Shape) {
/*     */       int i = this.lineCount;
/*     */       breakLines(param1DocumentEvent.getOffset());
/*     */       if (i != this.lineCount) {
/*     */         WrappedPlainView.this.preferenceChanged(this, false, true);
/*     */         getContainer().repaint();
/*     */       } else if (param1Shape != null) {
/*     */         Container container = getContainer();
/*     */         Rectangle rectangle = (Rectangle)param1Shape;
/*     */         container.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       } 
/*     */     }
/*     */     
/*     */     final int[] getLineEnds() {
/*     */       if (this.lineCache == null)
/*     */         return null; 
/*     */       int[] arrayOfInt = this.lineCache.get();
/*     */       if (arrayOfInt == null)
/*     */         return breakLines(getStartOffset()); 
/*     */       return arrayOfInt;
/*     */     }
/*     */     
/*     */     final int[] breakLines(int param1Int) {
/*     */       int[] arrayOfInt1 = (this.lineCache == null) ? null : this.lineCache.get();
/*     */       int[] arrayOfInt2 = arrayOfInt1;
/*     */       int i = getStartOffset();
/*     */       int j = 0;
/*     */       if (arrayOfInt1 != null) {
/*     */         j = findLine(param1Int - i);
/*     */         if (j > 0)
/*     */           j--; 
/*     */       } 
/*     */       int k = (j == 0) ? i : (i + arrayOfInt1[j - 1]);
/*     */       int m = getEndOffset();
/*     */       while (k < m) {
/*     */         int n = WrappedPlainView.this.calculateBreakPosition(k, m);
/*     */         k = (n == k) ? ++n : n;
/*     */         if (j == 0 && k >= m) {
/*     */           this.lineCache = null;
/*     */           arrayOfInt1 = null;
/*     */           j = 1;
/*     */           break;
/*     */         } 
/*     */         if (arrayOfInt1 == null || j >= arrayOfInt1.length) {
/*     */           double d = (m - i) / (k - i);
/*     */           int i1 = (int)Math.ceil((j + 1) * d);
/*     */           i1 = Math.max(i1, j + 2);
/*     */           int[] arrayOfInt = new int[i1];
/*     */           if (arrayOfInt1 != null)
/*     */             System.arraycopy(arrayOfInt1, 0, arrayOfInt, 0, j); 
/*     */           arrayOfInt1 = arrayOfInt;
/*     */         } 
/*     */         arrayOfInt1[j++] = k - i;
/*     */       } 
/*     */       this.lineCount = j;
/*     */       if (this.lineCount > 1) {
/*     */         int n = this.lineCount + this.lineCount / 3;
/*     */         if (arrayOfInt1.length > n) {
/*     */           int[] arrayOfInt = new int[n];
/*     */           System.arraycopy(arrayOfInt1, 0, arrayOfInt, 0, this.lineCount);
/*     */           arrayOfInt1 = arrayOfInt;
/*     */         } 
/*     */       } 
/*     */       if (arrayOfInt1 != null && arrayOfInt1 != arrayOfInt2)
/*     */         this.lineCache = (SoftReference)new SoftReference<>(arrayOfInt1); 
/*     */       return arrayOfInt1;
/*     */     }
/*     */     
/*     */     private int findLine(int param1Int) {
/*     */       int[] arrayOfInt = this.lineCache.get();
/*     */       if (param1Int < arrayOfInt[0])
/*     */         return 0; 
/*     */       if (param1Int > arrayOfInt[this.lineCount - 1])
/*     */         return this.lineCount; 
/*     */       return findLine(arrayOfInt, param1Int, 0, this.lineCount - 1);
/*     */     }
/*     */     
/*     */     private int findLine(int[] param1ArrayOfint, int param1Int1, int param1Int2, int param1Int3) {
/*     */       if (param1Int3 - param1Int2 <= 1)
/*     */         return param1Int3; 
/*     */       int i = (param1Int3 + param1Int2) / 2;
/*     */       return (param1Int1 < param1ArrayOfint[i]) ? findLine(param1ArrayOfint, param1Int1, param1Int2, i) : findLine(param1ArrayOfint, param1Int1, i, param1Int3);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/WrappedPlainView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */