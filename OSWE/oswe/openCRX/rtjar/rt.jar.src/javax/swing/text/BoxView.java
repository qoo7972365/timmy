/*      */ package javax.swing.text;
/*      */ 
/*      */ import java.awt.Container;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import javax.swing.SizeRequirements;
/*      */ import javax.swing.event.DocumentEvent;
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
/*      */ public class BoxView
/*      */   extends CompositeView
/*      */ {
/*      */   int majorAxis;
/*      */   int majorSpan;
/*      */   int minorSpan;
/*      */   boolean majorReqValid;
/*      */   boolean minorReqValid;
/*      */   SizeRequirements majorRequest;
/*      */   SizeRequirements minorRequest;
/*      */   boolean majorAllocValid;
/*      */   int[] majorOffsets;
/*      */   int[] majorSpans;
/*      */   boolean minorAllocValid;
/*      */   int[] minorOffsets;
/*      */   int[] minorSpans;
/*      */   Rectangle tempRect;
/*      */   
/*      */   public BoxView(Element paramElement, int paramInt) {
/*   70 */     super(paramElement);
/*   71 */     this.tempRect = new Rectangle();
/*   72 */     this.majorAxis = paramInt;
/*      */     
/*   74 */     this.majorOffsets = new int[0];
/*   75 */     this.majorSpans = new int[0];
/*   76 */     this.majorReqValid = false;
/*   77 */     this.majorAllocValid = false;
/*   78 */     this.minorOffsets = new int[0];
/*   79 */     this.minorSpans = new int[0];
/*   80 */     this.minorReqValid = false;
/*   81 */     this.minorAllocValid = false;
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
/*      */   public int getAxis() {
/*   94 */     return this.majorAxis;
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
/*      */   public void setAxis(int paramInt) {
/*  106 */     boolean bool = (paramInt != this.majorAxis) ? true : false;
/*  107 */     this.majorAxis = paramInt;
/*  108 */     if (bool) {
/*  109 */       preferenceChanged((View)null, true, true);
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
/*      */   public void layoutChanged(int paramInt) {
/*  128 */     if (paramInt == this.majorAxis) {
/*  129 */       this.majorAllocValid = false;
/*      */     } else {
/*  131 */       this.minorAllocValid = false;
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
/*      */   protected boolean isLayoutValid(int paramInt) {
/*  143 */     if (paramInt == this.majorAxis) {
/*  144 */       return this.majorAllocValid;
/*      */     }
/*  146 */     return this.minorAllocValid;
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
/*      */   protected void paintChild(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
/*  160 */     View view = getView(paramInt);
/*  161 */     view.paint(paramGraphics, paramRectangle);
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
/*      */   public void replace(int paramInt1, int paramInt2, View[] paramArrayOfView) {
/*  181 */     super.replace(paramInt1, paramInt2, paramArrayOfView);
/*      */ 
/*      */     
/*  184 */     boolean bool = (paramArrayOfView != null) ? paramArrayOfView.length : false;
/*  185 */     this.majorOffsets = updateLayoutArray(this.majorOffsets, paramInt1, bool);
/*  186 */     this.majorSpans = updateLayoutArray(this.majorSpans, paramInt1, bool);
/*  187 */     this.majorReqValid = false;
/*  188 */     this.majorAllocValid = false;
/*  189 */     this.minorOffsets = updateLayoutArray(this.minorOffsets, paramInt1, bool);
/*  190 */     this.minorSpans = updateLayoutArray(this.minorSpans, paramInt1, bool);
/*  191 */     this.minorReqValid = false;
/*  192 */     this.minorAllocValid = false;
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
/*      */   int[] updateLayoutArray(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/*  210 */     int i = getViewCount();
/*  211 */     int[] arrayOfInt = new int[i];
/*      */     
/*  213 */     System.arraycopy(paramArrayOfint, 0, arrayOfInt, 0, paramInt1);
/*  214 */     System.arraycopy(paramArrayOfint, paramInt1, arrayOfInt, paramInt1 + paramInt2, i - paramInt2 - paramInt1);
/*      */     
/*  216 */     return arrayOfInt;
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
/*      */   protected void forwardUpdate(DocumentEvent.ElementChange paramElementChange, DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/*  239 */     boolean bool = isLayoutValid(this.majorAxis);
/*  240 */     super.forwardUpdate(paramElementChange, paramDocumentEvent, paramShape, paramViewFactory);
/*      */ 
/*      */     
/*  243 */     if (bool && !isLayoutValid(this.majorAxis)) {
/*      */ 
/*      */ 
/*      */       
/*  247 */       Container container = getContainer();
/*  248 */       if (paramShape != null && container != null) {
/*  249 */         int i = paramDocumentEvent.getOffset();
/*  250 */         int j = getViewIndexAtPosition(i);
/*  251 */         Rectangle rectangle = getInsideAllocation(paramShape);
/*  252 */         if (this.majorAxis == 0) {
/*  253 */           rectangle.x += this.majorOffsets[j];
/*  254 */           rectangle.width -= this.majorOffsets[j];
/*      */         } else {
/*  256 */           rectangle.y += this.minorOffsets[j];
/*  257 */           rectangle.height -= this.minorOffsets[j];
/*      */         } 
/*  259 */         container.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preferenceChanged(View paramView, boolean paramBoolean1, boolean paramBoolean2) {
/*  276 */     boolean bool1 = (this.majorAxis == 0) ? paramBoolean1 : paramBoolean2;
/*  277 */     boolean bool2 = (this.majorAxis == 0) ? paramBoolean2 : paramBoolean1;
/*  278 */     if (bool1) {
/*  279 */       this.majorReqValid = false;
/*  280 */       this.majorAllocValid = false;
/*      */     } 
/*  282 */     if (bool2) {
/*  283 */       this.minorReqValid = false;
/*  284 */       this.minorAllocValid = false;
/*      */     } 
/*  286 */     super.preferenceChanged(paramView, paramBoolean1, paramBoolean2);
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
/*      */   public int getResizeWeight(int paramInt) {
/*  298 */     checkRequests(paramInt);
/*  299 */     if (paramInt == this.majorAxis) {
/*  300 */       if (this.majorRequest.preferred != this.majorRequest.minimum || this.majorRequest.preferred != this.majorRequest.maximum)
/*      */       {
/*  302 */         return 1;
/*      */       }
/*      */     }
/*  305 */     else if (this.minorRequest.preferred != this.minorRequest.minimum || this.minorRequest.preferred != this.minorRequest.maximum) {
/*      */       
/*  307 */       return 1;
/*      */     } 
/*      */     
/*  310 */     return 0;
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
/*      */   void setSpanOnAxis(int paramInt, float paramFloat) {
/*  322 */     if (paramInt == this.majorAxis) {
/*  323 */       if (this.majorSpan != (int)paramFloat) {
/*  324 */         this.majorAllocValid = false;
/*      */       }
/*  326 */       if (!this.majorAllocValid) {
/*      */         
/*  328 */         this.majorSpan = (int)paramFloat;
/*  329 */         checkRequests(this.majorAxis);
/*  330 */         layoutMajorAxis(this.majorSpan, paramInt, this.majorOffsets, this.majorSpans);
/*  331 */         this.majorAllocValid = true;
/*      */ 
/*      */         
/*  334 */         updateChildSizes();
/*      */       } 
/*      */     } else {
/*  337 */       if ((int)paramFloat != this.minorSpan) {
/*  338 */         this.minorAllocValid = false;
/*      */       }
/*  340 */       if (!this.minorAllocValid) {
/*      */         
/*  342 */         this.minorSpan = (int)paramFloat;
/*  343 */         checkRequests(paramInt);
/*  344 */         layoutMinorAxis(this.minorSpan, paramInt, this.minorOffsets, this.minorSpans);
/*  345 */         this.minorAllocValid = true;
/*      */ 
/*      */         
/*  348 */         updateChildSizes();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void updateChildSizes() {
/*  357 */     int i = getViewCount();
/*  358 */     if (this.majorAxis == 0) {
/*  359 */       for (byte b = 0; b < i; b++) {
/*  360 */         View view = getView(b);
/*  361 */         view.setSize(this.majorSpans[b], this.minorSpans[b]);
/*      */       } 
/*      */     } else {
/*  364 */       for (byte b = 0; b < i; b++) {
/*  365 */         View view = getView(b);
/*  366 */         view.setSize(this.minorSpans[b], this.majorSpans[b]);
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
/*      */   
/*      */   float getSpanOnAxis(int paramInt) {
/*  380 */     if (paramInt == this.majorAxis) {
/*  381 */       return this.majorSpan;
/*      */     }
/*  383 */     return this.minorSpan;
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
/*      */   public void setSize(float paramFloat1, float paramFloat2) {
/*  397 */     layout(Math.max(0, (int)(paramFloat1 - getLeftInset() - getRightInset())), 
/*  398 */         Math.max(0, (int)(paramFloat2 - getTopInset() - getBottomInset())));
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
/*      */   public void paint(Graphics paramGraphics, Shape paramShape) {
/*  414 */     Rectangle rectangle1 = (paramShape instanceof Rectangle) ? (Rectangle)paramShape : paramShape.getBounds();
/*  415 */     int i = getViewCount();
/*  416 */     int j = rectangle1.x + getLeftInset();
/*  417 */     int k = rectangle1.y + getTopInset();
/*  418 */     Rectangle rectangle2 = paramGraphics.getClipBounds();
/*  419 */     for (byte b = 0; b < i; b++) {
/*  420 */       this.tempRect.x = j + getOffset(0, b);
/*  421 */       this.tempRect.y = k + getOffset(1, b);
/*  422 */       this.tempRect.width = getSpan(0, b);
/*  423 */       this.tempRect.height = getSpan(1, b);
/*  424 */       int m = this.tempRect.x, n = m + this.tempRect.width;
/*  425 */       int i1 = this.tempRect.y, i2 = i1 + this.tempRect.height;
/*  426 */       int i3 = rectangle2.x, i4 = i3 + rectangle2.width;
/*  427 */       int i5 = rectangle2.y, i6 = i5 + rectangle2.height;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  432 */       if (n >= i3 && i2 >= i5 && i4 >= m && i6 >= i1) {
/*  433 */         paintChild(paramGraphics, this.tempRect, b);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getChildAllocation(int paramInt, Shape paramShape) {
/*  452 */     if (paramShape != null) {
/*  453 */       Shape shape = super.getChildAllocation(paramInt, paramShape);
/*  454 */       if (shape != null && !isAllocationValid()) {
/*      */ 
/*      */         
/*  457 */         Rectangle rectangle = (shape instanceof Rectangle) ? (Rectangle)shape : shape.getBounds();
/*  458 */         if (rectangle.width == 0 && rectangle.height == 0) {
/*  459 */           return null;
/*      */         }
/*      */       } 
/*  462 */       return shape;
/*      */     } 
/*  464 */     return null;
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
/*      */   public Shape modelToView(int paramInt, Shape paramShape, Position.Bias paramBias) throws BadLocationException {
/*  480 */     if (!isAllocationValid()) {
/*  481 */       Rectangle rectangle = paramShape.getBounds();
/*  482 */       setSize(rectangle.width, rectangle.height);
/*      */     } 
/*  484 */     return super.modelToView(paramInt, paramShape, paramBias);
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
/*      */   public int viewToModel(float paramFloat1, float paramFloat2, Shape paramShape, Position.Bias[] paramArrayOfBias) {
/*  499 */     if (!isAllocationValid()) {
/*  500 */       Rectangle rectangle = paramShape.getBounds();
/*  501 */       setSize(rectangle.width, rectangle.height);
/*      */     } 
/*  503 */     return super.viewToModel(paramFloat1, paramFloat2, paramShape, paramArrayOfBias);
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
/*      */   public float getAlignment(int paramInt) {
/*  524 */     checkRequests(paramInt);
/*  525 */     if (paramInt == this.majorAxis) {
/*  526 */       return this.majorRequest.alignment;
/*      */     }
/*  528 */     return this.minorRequest.alignment;
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
/*      */   public float getPreferredSpan(int paramInt) {
/*  545 */     checkRequests(paramInt);
/*      */     
/*  547 */     float f = (paramInt == 0) ? (getLeftInset() + getRightInset()) : (getTopInset() + getBottomInset());
/*  548 */     if (paramInt == this.majorAxis) {
/*  549 */       return this.majorRequest.preferred + f;
/*      */     }
/*  551 */     return this.minorRequest.preferred + f;
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
/*      */   public float getMinimumSpan(int paramInt) {
/*  568 */     checkRequests(paramInt);
/*      */     
/*  570 */     float f = (paramInt == 0) ? (getLeftInset() + getRightInset()) : (getTopInset() + getBottomInset());
/*  571 */     if (paramInt == this.majorAxis) {
/*  572 */       return this.majorRequest.minimum + f;
/*      */     }
/*  574 */     return this.minorRequest.minimum + f;
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
/*      */   public float getMaximumSpan(int paramInt) {
/*  591 */     checkRequests(paramInt);
/*      */     
/*  593 */     float f = (paramInt == 0) ? (getLeftInset() + getRightInset()) : (getTopInset() + getBottomInset());
/*  594 */     if (paramInt == this.majorAxis) {
/*  595 */       return this.majorRequest.maximum + f;
/*      */     }
/*  597 */     return this.minorRequest.maximum + f;
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
/*      */   protected boolean isAllocationValid() {
/*  610 */     return (this.majorAllocValid && this.minorAllocValid);
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
/*      */   protected boolean isBefore(int paramInt1, int paramInt2, Rectangle paramRectangle) {
/*  623 */     if (this.majorAxis == 0) {
/*  624 */       return (paramInt1 < paramRectangle.x);
/*      */     }
/*  626 */     return (paramInt2 < paramRectangle.y);
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
/*      */   protected boolean isAfter(int paramInt1, int paramInt2, Rectangle paramRectangle) {
/*  640 */     if (this.majorAxis == 0) {
/*  641 */       return (paramInt1 > paramRectangle.width + paramRectangle.x);
/*      */     }
/*  643 */     return (paramInt2 > paramRectangle.height + paramRectangle.y);
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
/*      */   protected View getViewAtPoint(int paramInt1, int paramInt2, Rectangle paramRectangle) {
/*  657 */     int i = getViewCount();
/*  658 */     if (this.majorAxis == 0) {
/*  659 */       if (paramInt1 < paramRectangle.x + this.majorOffsets[0]) {
/*  660 */         childAllocation(0, paramRectangle);
/*  661 */         return getView(0);
/*      */       } 
/*  663 */       for (byte b1 = 0; b1 < i; b1++) {
/*  664 */         if (paramInt1 < paramRectangle.x + this.majorOffsets[b1]) {
/*  665 */           childAllocation(b1 - 1, paramRectangle);
/*  666 */           return getView(b1 - 1);
/*      */         } 
/*      */       } 
/*  669 */       childAllocation(i - 1, paramRectangle);
/*  670 */       return getView(i - 1);
/*      */     } 
/*  672 */     if (paramInt2 < paramRectangle.y + this.majorOffsets[0]) {
/*  673 */       childAllocation(0, paramRectangle);
/*  674 */       return getView(0);
/*      */     } 
/*  676 */     for (byte b = 0; b < i; b++) {
/*  677 */       if (paramInt2 < paramRectangle.y + this.majorOffsets[b]) {
/*  678 */         childAllocation(b - 1, paramRectangle);
/*  679 */         return getView(b - 1);
/*      */       } 
/*      */     } 
/*  682 */     childAllocation(i - 1, paramRectangle);
/*  683 */     return getView(i - 1);
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
/*      */   protected void childAllocation(int paramInt, Rectangle paramRectangle) {
/*  695 */     paramRectangle.x += getOffset(0, paramInt);
/*  696 */     paramRectangle.y += getOffset(1, paramInt);
/*  697 */     paramRectangle.width = getSpan(0, paramInt);
/*  698 */     paramRectangle.height = getSpan(1, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void layout(int paramInt1, int paramInt2) {
/*  708 */     setSpanOnAxis(0, paramInt1);
/*  709 */     setSpanOnAxis(1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWidth() {
/*      */     int i;
/*  719 */     if (this.majorAxis == 0) {
/*  720 */       i = this.majorSpan;
/*      */     } else {
/*  722 */       i = this.minorSpan;
/*      */     } 
/*  724 */     i += getLeftInset() - getRightInset();
/*  725 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeight() {
/*      */     int i;
/*  735 */     if (this.majorAxis == 1) {
/*  736 */       i = this.majorSpan;
/*      */     } else {
/*  738 */       i = this.minorSpan;
/*      */     } 
/*  740 */     i += getTopInset() - getBottomInset();
/*  741 */     return i;
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
/*      */   protected void layoutMajorAxis(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/*  765 */     long l1 = 0L;
/*  766 */     int i = getViewCount();
/*  767 */     for (byte b1 = 0; b1 < i; b1++) {
/*  768 */       View view = getView(b1);
/*  769 */       paramArrayOfint2[b1] = (int)view.getPreferredSpan(paramInt2);
/*  770 */       l1 += paramArrayOfint2[b1];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  779 */     long l2 = paramInt1 - l1;
/*  780 */     float f = 0.0F;
/*  781 */     int[] arrayOfInt = null;
/*      */     
/*  783 */     if (l2 != 0L) {
/*  784 */       long l = 0L;
/*  785 */       arrayOfInt = new int[i];
/*  786 */       for (byte b = 0; b < i; b++) {
/*  787 */         int k; View view = getView(b);
/*      */         
/*  789 */         if (l2 < 0L) {
/*  790 */           k = (int)view.getMinimumSpan(paramInt2);
/*  791 */           arrayOfInt[b] = paramArrayOfint2[b] - k;
/*      */         } else {
/*  793 */           k = (int)view.getMaximumSpan(paramInt2);
/*  794 */           arrayOfInt[b] = k - paramArrayOfint2[b];
/*      */         } 
/*  796 */         l += k;
/*      */       } 
/*      */       
/*  799 */       float f1 = (float)Math.abs(l - l1);
/*  800 */       f = (float)l2 / f1;
/*  801 */       f = Math.min(f, 1.0F);
/*  802 */       f = Math.max(f, -1.0F);
/*      */     } 
/*      */ 
/*      */     
/*  806 */     int j = 0;
/*  807 */     for (byte b2 = 0; b2 < i; b2++) {
/*  808 */       paramArrayOfint1[b2] = j;
/*  809 */       if (l2 != 0L) {
/*  810 */         float f1 = f * arrayOfInt[b2];
/*  811 */         paramArrayOfint2[b2] = paramArrayOfint2[b2] + Math.round(f1);
/*      */       } 
/*  813 */       j = (int)Math.min(j + paramArrayOfint2[b2], 2147483647L);
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
/*      */   protected void layoutMinorAxis(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/*  834 */     int i = getViewCount();
/*  835 */     for (byte b = 0; b < i; b++) {
/*  836 */       View view = getView(b);
/*  837 */       int j = (int)view.getMaximumSpan(paramInt2);
/*  838 */       if (j < paramInt1) {
/*      */         
/*  840 */         float f = view.getAlignment(paramInt2);
/*  841 */         paramArrayOfint1[b] = (int)((paramInt1 - j) * f);
/*  842 */         paramArrayOfint2[b] = j;
/*      */       } else {
/*      */         
/*  845 */         int k = (int)view.getMinimumSpan(paramInt2);
/*  846 */         paramArrayOfint1[b] = 0;
/*  847 */         paramArrayOfint2[b] = Math.max(k, paramInt1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SizeRequirements calculateMajorAxisRequirements(int paramInt, SizeRequirements paramSizeRequirements) {
/*  864 */     float f1 = 0.0F;
/*  865 */     float f2 = 0.0F;
/*  866 */     float f3 = 0.0F;
/*      */     
/*  868 */     int i = getViewCount();
/*  869 */     for (byte b = 0; b < i; b++) {
/*  870 */       View view = getView(b);
/*  871 */       f1 += view.getMinimumSpan(paramInt);
/*  872 */       f2 += view.getPreferredSpan(paramInt);
/*  873 */       f3 += view.getMaximumSpan(paramInt);
/*      */     } 
/*      */     
/*  876 */     if (paramSizeRequirements == null) {
/*  877 */       paramSizeRequirements = new SizeRequirements();
/*      */     }
/*  879 */     paramSizeRequirements.alignment = 0.5F;
/*  880 */     paramSizeRequirements.minimum = (int)f1;
/*  881 */     paramSizeRequirements.preferred = (int)f2;
/*  882 */     paramSizeRequirements.maximum = (int)f3;
/*  883 */     return paramSizeRequirements;
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
/*      */   protected SizeRequirements calculateMinorAxisRequirements(int paramInt, SizeRequirements paramSizeRequirements) {
/*  897 */     int i = 0;
/*  898 */     long l = 0L;
/*  899 */     int j = Integer.MAX_VALUE;
/*  900 */     int k = getViewCount();
/*  901 */     for (byte b = 0; b < k; b++) {
/*  902 */       View view = getView(b);
/*  903 */       i = Math.max((int)view.getMinimumSpan(paramInt), i);
/*  904 */       l = Math.max((int)view.getPreferredSpan(paramInt), l);
/*  905 */       j = Math.max((int)view.getMaximumSpan(paramInt), j);
/*      */     } 
/*      */     
/*  908 */     if (paramSizeRequirements == null) {
/*  909 */       paramSizeRequirements = new SizeRequirements();
/*  910 */       paramSizeRequirements.alignment = 0.5F;
/*      */     } 
/*  912 */     paramSizeRequirements.preferred = (int)l;
/*  913 */     paramSizeRequirements.minimum = i;
/*  914 */     paramSizeRequirements.maximum = j;
/*  915 */     return paramSizeRequirements;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkRequests(int paramInt) {
/*  925 */     if (paramInt != 0 && paramInt != 1) {
/*  926 */       throw new IllegalArgumentException("Invalid axis: " + paramInt);
/*      */     }
/*  928 */     if (paramInt == this.majorAxis) {
/*  929 */       if (!this.majorReqValid) {
/*  930 */         this.majorRequest = calculateMajorAxisRequirements(paramInt, this.majorRequest);
/*      */         
/*  932 */         this.majorReqValid = true;
/*      */       } 
/*  934 */     } else if (!this.minorReqValid) {
/*  935 */       this.minorRequest = calculateMinorAxisRequirements(paramInt, this.minorRequest);
/*  936 */       this.minorReqValid = true;
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
/*      */   protected void baselineLayout(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/*  956 */     int i = (int)(paramInt1 * getAlignment(paramInt2));
/*  957 */     int j = paramInt1 - i;
/*      */     
/*  959 */     int k = getViewCount();
/*      */     
/*  961 */     for (byte b = 0; b < k; b++) {
/*  962 */       float f2; View view = getView(b);
/*  963 */       float f1 = view.getAlignment(paramInt2);
/*      */ 
/*      */       
/*  966 */       if (view.getResizeWeight(paramInt2) > 0) {
/*      */ 
/*      */ 
/*      */         
/*  970 */         float f3 = view.getMinimumSpan(paramInt2);
/*      */         
/*  972 */         float f4 = view.getMaximumSpan(paramInt2);
/*      */         
/*  974 */         if (f1 == 0.0F) {
/*      */           
/*  976 */           f2 = Math.max(Math.min(f4, j), f3);
/*  977 */         } else if (f1 == 1.0F) {
/*      */           
/*  979 */           f2 = Math.max(Math.min(f4, i), f3);
/*      */         } else {
/*      */           
/*  982 */           float f = Math.min(i / f1, j / (1.0F - f1));
/*      */ 
/*      */           
/*  985 */           f2 = Math.max(Math.min(f4, f), f3);
/*      */         } 
/*      */       } else {
/*      */         
/*  989 */         f2 = view.getPreferredSpan(paramInt2);
/*      */       } 
/*      */       
/*  992 */       paramArrayOfint1[b] = i - (int)(f2 * f1);
/*  993 */       paramArrayOfint2[b] = (int)f2;
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
/*      */   protected SizeRequirements baselineRequirements(int paramInt, SizeRequirements paramSizeRequirements) {
/* 1007 */     SizeRequirements sizeRequirements1 = new SizeRequirements();
/* 1008 */     SizeRequirements sizeRequirements2 = new SizeRequirements();
/*      */     
/* 1010 */     if (paramSizeRequirements == null) {
/* 1011 */       paramSizeRequirements = new SizeRequirements();
/*      */     }
/*      */     
/* 1014 */     paramSizeRequirements.alignment = 0.5F;
/*      */     
/* 1016 */     int i = getViewCount();
/*      */ 
/*      */ 
/*      */     
/* 1020 */     for (byte b = 0; b < i; b++) {
/* 1021 */       View view = getView(b);
/* 1022 */       float f1 = view.getAlignment(paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1028 */       float f2 = view.getPreferredSpan(paramInt);
/* 1029 */       int j = (int)(f1 * f2);
/* 1030 */       int k = (int)(f2 - j);
/* 1031 */       sizeRequirements1.preferred = Math.max(j, sizeRequirements1.preferred);
/* 1032 */       sizeRequirements2.preferred = Math.max(k, sizeRequirements2.preferred);
/*      */       
/* 1034 */       if (view.getResizeWeight(paramInt) > 0) {
/*      */ 
/*      */         
/* 1037 */         f2 = view.getMinimumSpan(paramInt);
/* 1038 */         j = (int)(f1 * f2);
/* 1039 */         k = (int)(f2 - j);
/* 1040 */         sizeRequirements1.minimum = Math.max(j, sizeRequirements1.minimum);
/* 1041 */         sizeRequirements2.minimum = Math.max(k, sizeRequirements2.minimum);
/*      */         
/* 1043 */         f2 = view.getMaximumSpan(paramInt);
/* 1044 */         j = (int)(f1 * f2);
/* 1045 */         k = (int)(f2 - j);
/* 1046 */         sizeRequirements1.maximum = Math.max(j, sizeRequirements1.maximum);
/* 1047 */         sizeRequirements2.maximum = Math.max(k, sizeRequirements2.maximum);
/*      */       } else {
/*      */         
/* 1050 */         sizeRequirements1.minimum = Math.max(j, sizeRequirements1.minimum);
/* 1051 */         sizeRequirements2.minimum = Math.max(k, sizeRequirements2.minimum);
/* 1052 */         sizeRequirements1.maximum = Math.max(j, sizeRequirements1.maximum);
/* 1053 */         sizeRequirements2.maximum = Math.max(k, sizeRequirements2.maximum);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1060 */     paramSizeRequirements.preferred = (int)Math.min(sizeRequirements1.preferred + sizeRequirements2.preferred, 2147483647L);
/*      */ 
/*      */ 
/*      */     
/* 1064 */     if (paramSizeRequirements.preferred > 0) {
/* 1065 */       paramSizeRequirements.alignment = sizeRequirements1.preferred / paramSizeRequirements.preferred;
/*      */     }
/*      */ 
/*      */     
/* 1069 */     if (paramSizeRequirements.alignment == 0.0F) {
/*      */ 
/*      */       
/* 1072 */       paramSizeRequirements.minimum = sizeRequirements2.minimum;
/* 1073 */       paramSizeRequirements.maximum = sizeRequirements2.maximum;
/* 1074 */     } else if (paramSizeRequirements.alignment == 1.0F) {
/*      */ 
/*      */       
/* 1077 */       paramSizeRequirements.minimum = sizeRequirements1.minimum;
/* 1078 */       paramSizeRequirements.maximum = sizeRequirements1.maximum;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1083 */       paramSizeRequirements.minimum = Math.round(Math.max(sizeRequirements1.minimum / paramSizeRequirements.alignment, sizeRequirements2.minimum / (1.0F - paramSizeRequirements.alignment)));
/*      */ 
/*      */       
/* 1086 */       paramSizeRequirements.maximum = Math.round(Math.min(sizeRequirements1.maximum / paramSizeRequirements.alignment, sizeRequirements2.maximum / (1.0F - paramSizeRequirements.alignment)));
/*      */     } 
/*      */ 
/*      */     
/* 1090 */     return paramSizeRequirements;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getOffset(int paramInt1, int paramInt2) {
/* 1100 */     int[] arrayOfInt = (paramInt1 == this.majorAxis) ? this.majorOffsets : this.minorOffsets;
/* 1101 */     return arrayOfInt[paramInt2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getSpan(int paramInt1, int paramInt2) {
/* 1111 */     int[] arrayOfInt = (paramInt1 == this.majorAxis) ? this.majorSpans : this.minorSpans;
/* 1112 */     return arrayOfInt[paramInt2];
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
/*      */   protected boolean flipEastAndWestAtEnds(int paramInt, Position.Bias paramBias) {
/* 1146 */     if (this.majorAxis == 1) {
/*      */       
/* 1148 */       int i = (paramBias == Position.Bias.Backward) ? Math.max(0, paramInt - 1) : paramInt;
/* 1149 */       int j = getViewIndexAtPosition(i);
/* 1150 */       if (j != -1) {
/* 1151 */         View view = getView(j);
/* 1152 */         if (view != null && view instanceof CompositeView) {
/* 1153 */           return ((CompositeView)view).flipEastAndWestAtEnds(paramInt, paramBias);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1158 */     return false;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/BoxView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */