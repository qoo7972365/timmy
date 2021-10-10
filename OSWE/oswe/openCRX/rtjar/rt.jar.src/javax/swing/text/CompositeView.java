/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CompositeView
/*     */   extends View
/*     */ {
/*     */   public CompositeView(Element paramElement) {
/*  84 */     super(paramElement);
/*  85 */     this.children = new View[1];
/*  86 */     this.nchildren = 0;
/*  87 */     this.childAlloc = new Rectangle();
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
/*     */   protected void loadChildren(ViewFactory paramViewFactory) {
/* 102 */     if (paramViewFactory == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 107 */     Element element = getElement();
/* 108 */     int i = element.getElementCount();
/* 109 */     if (i > 0) {
/* 110 */       View[] arrayOfView = new View[i];
/* 111 */       for (byte b = 0; b < i; b++) {
/* 112 */         arrayOfView[b] = paramViewFactory.create(element.getElement(b));
/*     */       }
/* 114 */       replace(0, 0, arrayOfView);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(View paramView) {
/* 136 */     super.setParent(paramView);
/* 137 */     if (paramView != null && this.nchildren == 0) {
/* 138 */       ViewFactory viewFactory = getViewFactory();
/* 139 */       loadChildren(viewFactory);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getViewCount() {
/* 150 */     return this.nchildren;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View getView(int paramInt) {
/* 160 */     return this.children[paramInt];
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
/*     */   public void replace(int paramInt1, int paramInt2, View[] paramArrayOfView) {
/* 181 */     if (paramArrayOfView == null) {
/* 182 */       paramArrayOfView = ZERO;
/*     */     }
/*     */     
/*     */     int i;
/* 186 */     for (i = paramInt1; i < paramInt1 + paramInt2; i++) {
/* 187 */       if (this.children[i].getParent() == this)
/*     */       {
/*     */         
/* 190 */         this.children[i].setParent(null);
/*     */       }
/* 192 */       this.children[i] = null;
/*     */     } 
/*     */ 
/*     */     
/* 196 */     i = paramArrayOfView.length - paramInt2;
/* 197 */     int j = paramInt1 + paramInt2;
/* 198 */     int k = this.nchildren - j;
/* 199 */     int m = j + i;
/* 200 */     if (this.nchildren + i >= this.children.length) {
/*     */       
/* 202 */       int n = Math.max(2 * this.children.length, this.nchildren + i);
/* 203 */       View[] arrayOfView = new View[n];
/* 204 */       System.arraycopy(this.children, 0, arrayOfView, 0, paramInt1);
/* 205 */       System.arraycopy(paramArrayOfView, 0, arrayOfView, paramInt1, paramArrayOfView.length);
/* 206 */       System.arraycopy(this.children, j, arrayOfView, m, k);
/* 207 */       this.children = arrayOfView;
/*     */     } else {
/*     */       
/* 210 */       System.arraycopy(this.children, j, this.children, m, k);
/* 211 */       System.arraycopy(paramArrayOfView, 0, this.children, paramInt1, paramArrayOfView.length);
/*     */     } 
/* 213 */     this.nchildren += i;
/*     */ 
/*     */     
/* 216 */     for (byte b = 0; b < paramArrayOfView.length; b++) {
/* 217 */       paramArrayOfView[b].setParent(this);
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
/*     */   public Shape getChildAllocation(int paramInt, Shape paramShape) {
/* 231 */     Rectangle rectangle = getInsideAllocation(paramShape);
/* 232 */     childAllocation(paramInt, rectangle);
/* 233 */     return rectangle;
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
/*     */   public Shape modelToView(int paramInt, Shape paramShape, Position.Bias paramBias) throws BadLocationException {
/* 250 */     boolean bool = (paramBias == Position.Bias.Backward) ? true : false;
/* 251 */     int i = bool ? Math.max(0, paramInt - 1) : paramInt;
/* 252 */     if (bool && i < getStartOffset()) {
/* 253 */       return null;
/*     */     }
/* 255 */     int j = getViewIndexAtPosition(i);
/* 256 */     if (j != -1 && j < getViewCount()) {
/* 257 */       View view = getView(j);
/* 258 */       if (view != null && i >= view.getStartOffset() && i < view
/* 259 */         .getEndOffset()) {
/* 260 */         Shape shape1 = getChildAllocation(j, paramShape);
/* 261 */         if (shape1 == null)
/*     */         {
/* 263 */           return null;
/*     */         }
/* 265 */         Shape shape2 = view.modelToView(paramInt, shape1, paramBias);
/* 266 */         if (shape2 == null && view.getEndOffset() == paramInt && 
/* 267 */           ++j < getViewCount()) {
/* 268 */           view = getView(j);
/* 269 */           shape2 = view.modelToView(paramInt, getChildAllocation(j, paramShape), paramBias);
/*     */         } 
/*     */         
/* 272 */         return shape2;
/*     */       } 
/*     */     } 
/* 275 */     throw new BadLocationException("Position not represented by view", paramInt);
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
/*     */   public Shape modelToView(int paramInt1, Position.Bias paramBias1, int paramInt2, Position.Bias paramBias2, Shape paramShape) throws BadLocationException {
/* 301 */     if (paramInt1 == getStartOffset() && paramInt2 == getEndOffset()) {
/* 302 */       return paramShape;
/*     */     }
/* 304 */     Rectangle rectangle1 = getInsideAllocation(paramShape);
/* 305 */     Rectangle rectangle2 = new Rectangle(rectangle1);
/* 306 */     View view1 = getViewAtPosition((paramBias1 == Position.Bias.Backward) ? 
/* 307 */         Math.max(0, paramInt1 - 1) : paramInt1, rectangle2);
/* 308 */     Rectangle rectangle3 = new Rectangle(rectangle1);
/* 309 */     View view2 = getViewAtPosition((paramBias2 == Position.Bias.Backward) ? 
/* 310 */         Math.max(0, paramInt2 - 1) : paramInt2, rectangle3);
/* 311 */     if (view1 == view2) {
/* 312 */       if (view1 == null) {
/* 313 */         return paramShape;
/*     */       }
/*     */       
/* 316 */       return view1.modelToView(paramInt1, paramBias1, paramInt2, paramBias2, rectangle2);
/*     */     } 
/*     */     
/* 319 */     int i = getViewCount();
/* 320 */     byte b = 0;
/* 321 */     while (b < i) {
/*     */       View view;
/*     */ 
/*     */ 
/*     */       
/* 326 */       if ((view = getView(b)) == view1 || view == view2) {
/*     */         View view3;
/*     */         
/* 329 */         Rectangle rectangle4, rectangle5 = new Rectangle();
/* 330 */         if (view == view1) {
/*     */ 
/*     */           
/* 333 */           rectangle4 = view1.modelToView(paramInt1, paramBias1, view1.getEndOffset(), Position.Bias.Backward, rectangle2).getBounds();
/* 334 */           view3 = view2;
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 339 */           rectangle4 = view2.modelToView(view2.getStartOffset(), Position.Bias.Forward, paramInt2, paramBias2, rectangle3).getBounds();
/* 340 */           view3 = view1;
/*     */         } 
/*     */ 
/*     */         
/* 344 */         while (++b < i && (
/* 345 */           view = getView(b)) != view3) {
/* 346 */           rectangle5.setBounds(rectangle1);
/* 347 */           childAllocation(b, rectangle5);
/* 348 */           rectangle4.add(rectangle5);
/*     */         } 
/*     */ 
/*     */         
/* 352 */         if (view3 != null) {
/*     */           Shape shape;
/* 354 */           if (view3 == view2) {
/* 355 */             shape = view2.modelToView(view2.getStartOffset(), Position.Bias.Forward, paramInt2, paramBias2, rectangle3);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 360 */             shape = view1.modelToView(paramInt1, paramBias1, view1.getEndOffset(), Position.Bias.Backward, rectangle2);
/*     */           } 
/*     */           
/* 363 */           if (shape instanceof Rectangle) {
/* 364 */             rectangle4.add((Rectangle)shape);
/*     */           } else {
/*     */             
/* 367 */             rectangle4.add(shape.getBounds());
/*     */           } 
/*     */         } 
/* 370 */         return rectangle4;
/*     */       } 
/* 372 */       b++;
/*     */     } 
/* 374 */     throw new BadLocationException("Position not represented by view", paramInt1);
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
/*     */   public int viewToModel(float paramFloat1, float paramFloat2, Shape paramShape, Position.Bias[] paramArrayOfBias) {
/* 391 */     Rectangle rectangle = getInsideAllocation(paramShape);
/* 392 */     if (isBefore((int)paramFloat1, (int)paramFloat2, rectangle)) {
/*     */       
/* 394 */       int i = -1;
/*     */ 
/*     */       
/* 397 */       try { i = getNextVisualPositionFrom(-1, Position.Bias.Forward, paramShape, 3, paramArrayOfBias); }
/*     */       
/* 399 */       catch (BadLocationException badLocationException) {  }
/* 400 */       catch (IllegalArgumentException illegalArgumentException) {}
/* 401 */       if (i == -1) {
/* 402 */         i = getStartOffset();
/* 403 */         paramArrayOfBias[0] = Position.Bias.Forward;
/*     */       } 
/* 405 */       return i;
/* 406 */     }  if (isAfter((int)paramFloat1, (int)paramFloat2, rectangle)) {
/*     */       
/* 408 */       int i = -1;
/*     */       
/* 410 */       try { i = getNextVisualPositionFrom(-1, Position.Bias.Forward, paramShape, 7, paramArrayOfBias); }
/*     */       
/* 412 */       catch (BadLocationException badLocationException) {  }
/* 413 */       catch (IllegalArgumentException illegalArgumentException) {}
/*     */       
/* 415 */       if (i == -1) {
/*     */         
/* 417 */         i = getEndOffset() - 1;
/* 418 */         paramArrayOfBias[0] = Position.Bias.Forward;
/*     */       } 
/* 420 */       return i;
/*     */     } 
/*     */     
/* 423 */     View view = getViewAtPoint((int)paramFloat1, (int)paramFloat2, rectangle);
/* 424 */     if (view != null) {
/* 425 */       return view.viewToModel(paramFloat1, paramFloat2, rectangle, paramArrayOfBias);
/*     */     }
/*     */     
/* 428 */     return -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextVisualPositionFrom(int paramInt1, Position.Bias paramBias, Shape paramShape, int paramInt2, Position.Bias[] paramArrayOfBias) throws BadLocationException {
/* 466 */     if (paramInt1 < -1) {
/* 467 */       throw new BadLocationException("invalid position", paramInt1);
/*     */     }
/* 469 */     Rectangle rectangle = getInsideAllocation(paramShape);
/*     */     
/* 471 */     switch (paramInt2) {
/*     */       case 1:
/* 473 */         return getNextNorthSouthVisualPositionFrom(paramInt1, paramBias, paramShape, paramInt2, paramArrayOfBias);
/*     */       
/*     */       case 5:
/* 476 */         return getNextNorthSouthVisualPositionFrom(paramInt1, paramBias, paramShape, paramInt2, paramArrayOfBias);
/*     */       
/*     */       case 3:
/* 479 */         return getNextEastWestVisualPositionFrom(paramInt1, paramBias, paramShape, paramInt2, paramArrayOfBias);
/*     */       
/*     */       case 7:
/* 482 */         return getNextEastWestVisualPositionFrom(paramInt1, paramBias, paramShape, paramInt2, paramArrayOfBias);
/*     */     } 
/*     */     
/* 485 */     throw new IllegalArgumentException("Bad direction: " + paramInt2);
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
/*     */   public int getViewIndex(int paramInt, Position.Bias paramBias) {
/* 501 */     if (paramBias == Position.Bias.Backward) {
/* 502 */       paramInt--;
/*     */     }
/* 504 */     if (paramInt >= getStartOffset() && paramInt < getEndOffset()) {
/* 505 */       return getViewIndexAtPosition(paramInt);
/*     */     }
/* 507 */     return -1;
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
/*     */   protected abstract boolean isBefore(int paramInt1, int paramInt2, Rectangle paramRectangle);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isAfter(int paramInt1, int paramInt2, Rectangle paramRectangle);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract View getViewAtPoint(int paramInt1, int paramInt2, Rectangle paramRectangle);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void childAllocation(int paramInt, Rectangle paramRectangle);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected View getViewAtPosition(int paramInt, Rectangle paramRectangle) {
/* 565 */     int i = getViewIndexAtPosition(paramInt);
/* 566 */     if (i >= 0 && i < getViewCount()) {
/* 567 */       View view = getView(i);
/* 568 */       if (paramRectangle != null) {
/* 569 */         childAllocation(i, paramRectangle);
/*     */       }
/* 571 */       return view;
/*     */     } 
/* 573 */     return null;
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
/*     */   protected int getViewIndexAtPosition(int paramInt) {
/* 586 */     Element element = getElement();
/* 587 */     return element.getElementIndex(paramInt);
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
/*     */   protected Rectangle getInsideAllocation(Shape paramShape) {
/* 609 */     if (paramShape != null) {
/*     */       Rectangle rectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 615 */       if (paramShape instanceof Rectangle) {
/* 616 */         rectangle = (Rectangle)paramShape;
/*     */       } else {
/* 618 */         rectangle = paramShape.getBounds();
/*     */       } 
/*     */       
/* 621 */       this.childAlloc.setBounds(rectangle);
/* 622 */       this.childAlloc.x += getLeftInset();
/* 623 */       this.childAlloc.y += getTopInset();
/* 624 */       this.childAlloc.width -= getLeftInset() + getRightInset();
/* 625 */       this.childAlloc.height -= getTopInset() + getBottomInset();
/* 626 */       return this.childAlloc;
/*     */     } 
/* 628 */     return null;
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
/*     */   protected void setParagraphInsets(AttributeSet paramAttributeSet) {
/* 641 */     this.top = (short)(int)StyleConstants.getSpaceAbove(paramAttributeSet);
/* 642 */     this.left = (short)(int)StyleConstants.getLeftIndent(paramAttributeSet);
/* 643 */     this.bottom = (short)(int)StyleConstants.getSpaceBelow(paramAttributeSet);
/* 644 */     this.right = (short)(int)StyleConstants.getRightIndent(paramAttributeSet);
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
/*     */   protected void setInsets(short paramShort1, short paramShort2, short paramShort3, short paramShort4) {
/* 656 */     this.top = paramShort1;
/* 657 */     this.left = paramShort2;
/* 658 */     this.right = paramShort4;
/* 659 */     this.bottom = paramShort3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected short getLeftInset() {
/* 668 */     return this.left;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected short getRightInset() {
/* 677 */     return this.right;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected short getTopInset() {
/* 686 */     return this.top;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected short getBottomInset() {
/* 695 */     return this.bottom;
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
/*     */   protected int getNextNorthSouthVisualPositionFrom(int paramInt1, Position.Bias paramBias, Shape paramShape, int paramInt2, Position.Bias[] paramArrayOfBias) throws BadLocationException {
/* 726 */     return Utilities.getNextVisualPositionFrom(this, paramInt1, paramBias, paramShape, paramInt2, paramArrayOfBias);
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
/*     */   protected int getNextEastWestVisualPositionFrom(int paramInt1, Position.Bias paramBias, Shape paramShape, int paramInt2, Position.Bias[] paramArrayOfBias) throws BadLocationException {
/* 757 */     return Utilities.getNextVisualPositionFrom(this, paramInt1, paramBias, paramShape, paramInt2, paramArrayOfBias);
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
/*     */   protected boolean flipEastAndWestAtEnds(int paramInt, Position.Bias paramBias) {
/* 786 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 793 */   private static View[] ZERO = new View[0];
/*     */   private View[] children;
/*     */   private int nchildren;
/*     */   private short left;
/*     */   private short right;
/*     */   private short top;
/*     */   private short bottom;
/*     */   private Rectangle childAlloc;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/CompositeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */